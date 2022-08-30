/*
 * Copyright (C) 2022 Lingu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package space.lingu.fiesta.compile;

import space.lingu.InfoPolicy;
import space.lingu.NonNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;

/**
 * @author RollW
 */
public abstract class BaseJavacProcessor extends AbstractProcessor {
    private Context context;
    private final List<Processor> mProcessors = new ArrayList<>();
    private final Map<String, ProcessorNamePair> mProcessorNameMap = new HashMap<>();

    protected BaseJavacProcessor() {
        mProcessors.addAll(registerAllProcessors());
        classifyProcessors();
    }

    private void classifyProcessors() {
        mProcessors.forEach(processor -> {
            if (processor == null) {
                throw new IllegalArgumentException("Processor cannot be null.");
            }
            ProcessorNamePair name =
                    mProcessorNameMap.get(processor.provideName());
            if (name == null) {
                name = new ProcessorNamePair(new ArrayList<>(), processor.provideName());
                mProcessorNameMap.put(name.name, name);
            }
            name.processors.add(processor);
        });
    }

    @NonNull
    protected abstract List<Processor> registerAllProcessors();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        context = Context.createContext(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedClass = roundEnv.getElementsAnnotatedWith(annotation);
            final String name = annotation.getQualifiedName().toString();
            ProcessorNamePair processorNamePair = mProcessorNameMap.get(name);
            if (processorNamePair == null) {
                continue;
            }
            for (Element element : annotatedClass) {
                for (Processor processor : processorNamePair.processors) {
                    processor.process(context, element, InfoPolicy.SELF);
                }
            }

        }

        return roundEnv.processingOver();
    }


    private static class ProcessorNamePair {
        private final List<Processor> processors;
        private final String name;

        private ProcessorNamePair(List<Processor> processors, String name) {
            this.processors = processors;
            this.name = name;
        }
    }
}
