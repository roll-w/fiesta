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

import space.lingu.Dangerous;
import space.lingu.InfoPolicy;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;


/**
 * {@link Dangerous}
 *
 * @author RollW
 */
public class DangerousProcessor extends MessageAnnotationProcessor<Dangerous> {

    public DangerousProcessor() {
    }

    @Override
    public Class<Dangerous> provideClass() {
        return Dangerous.class;
    }

    @Override
    protected void onCall(Dangerous dangerous, Context context, TreeElement element, InfoPolicy policy) {
        if (dangerous == null) {
            return;
        }
        InfoPolicy annotationPolicy = dangerous.policy();
        if (annotationPolicy == InfoPolicy.NONE) {
            return;
        }
        if (annotationPolicy == InfoPolicy.ALL || dangerous.policy() == policy) {
            if (dangerous.message().isEmpty()) {
                context.getLog().warn("Dangerous! ", element);
                return;
            }
            context.getLog()
                    .warn("Dangerous: " + dangerous.message(), element);
        }
    }
}
