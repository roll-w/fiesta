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

import com.google.auto.service.AutoService;
import space.lingu.NonNull;
import space.lingu.fiesta.compile.processor.DangerousProcessor;
import space.lingu.fiesta.compile.processor.FiestaProcessor;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RollW
 */
@AutoService(javax.annotation.processing.Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({Annotations.FIESTA, Annotations.DANGEROUS})
public class FiestaMainJavacProcessor extends BaseJavacProcessor {

    @Override
    @NonNull
    protected List<Processor<?>> registerAllProcessors() {
        List<Processor<?>> processors = new ArrayList<>();
        processors.add(new FiestaProcessor());
        processors.add(new DangerousProcessor());
        return processors;
    }
}
