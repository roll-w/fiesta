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

package space.lingu.fiesta.compile.processor;

import space.lingu.Fixme;
import space.lingu.Fixmes;
import space.lingu.NonNull;

/**
 * @author RollW
 */
public class FixmesProcessor extends RepeatableAnnotationProcessor<Fixmes, Fixme> {
    private FixmesProcessor(MessageAnnotationProcessor<Fixme> messageAnnotationProcessor) {
        super(messageAnnotationProcessor);
    }

    @NonNull
    @Override
    public Class<Fixmes> provideClass() {
        return Fixmes.class;
    }

    @NonNull
    @Override
    protected Fixme[] extractsMultipleAnnotations(Fixmes annotation) {
        return annotation.value();
    }

    public static FixmesProcessor getInstance() {
        return Singleton.PROCESSOR;
    }

    private static class Singleton {
        static final FixmesProcessor PROCESSOR = new FixmesProcessor(FixmeProcessor.getInstance());
    }
}
