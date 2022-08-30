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

import space.lingu.InfoPolicy;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.Processor;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * @author RollW
 */
public abstract class MessageAnnotationProcessor<A extends Annotation> implements Processor {
    @Override
    public void process(Context context, Element element, InfoPolicy policy) {
        A annotation = element.getAnnotation(provideClass());
        onCall(annotation, context, element, policy);
    }

    protected abstract Class<A> provideClass();

    protected void onCall(A annotation, Context context, Element element, InfoPolicy policy) {
    }
}
