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

import space.lingu.Experimental;
import space.lingu.InfoPolicy;
import space.lingu.NonNull;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;

/**
 * {@link Experimental}
 *
 * @author RollW
 */
public class ExperimentalProcessor extends MessageAnnotationProcessor<Experimental> {
    public ExperimentalProcessor() {
        super();
    }

    @Override
    protected void onCall(Experimental annotation, Context context,
                          TreeElement element, InfoPolicy policy) {
        if (annotation == null) {
            return;
        }
        InfoPolicy annotationPolicy = annotation.policy();
        if (annotationPolicy == InfoPolicy.NONE) {
            return;
        }
        if (annotationPolicy == InfoPolicy.ALL || annotation.policy() == policy) {
            context.getLog()
                    .warn("WARN: " + annotation.info(), element);
        }
    }

    @Override
    @NonNull
    public Class<Experimental> provideClass() {
        return Experimental.class;
    }

    public static ExperimentalProcessor getInstance() {
      return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        static final ExperimentalProcessor INSTANCE = new ExperimentalProcessor();
    }
}
