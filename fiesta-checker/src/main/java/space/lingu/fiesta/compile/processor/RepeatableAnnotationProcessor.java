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

import space.lingu.NonNull;
import space.lingu.fiesta.compile.ChainType;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.Processor;
import space.lingu.fiesta.compile.TreeElement;

import java.lang.annotation.Annotation;

/**
 * delegate to the real processor
 *
 * @author RollW
 */
public abstract class RepeatableAnnotationProcessor<M extends Annotation, A extends Annotation>
        implements Processor<M> {
    private final MessageAnnotationProcessor<A> minimalProcessor;

    public RepeatableAnnotationProcessor(MessageAnnotationProcessor<A> messageAnnotationProcessor) {
        minimalProcessor = messageAnnotationProcessor;
    }

    @Override
    public final void process(Context context, TreeElement element,
                              ChainType chainType) {
        M annotation = element.getAnnotation(provideClass());
        A[] aAnnotations = extractsMultipleAnnotations(annotation);
        for (A aAnnotation : aAnnotations) {
            minimalProcessor.onCall(aAnnotation, context, element, chainType);
        }
    }

    @NonNull
    protected abstract A[] extractsMultipleAnnotations(M annotation);
}
