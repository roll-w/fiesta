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
import space.lingu.NonNull;
import space.lingu.Warning;
import space.lingu.fiesta.compile.ChainType;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;

/**
 * {@link Warning}
 *
 * @author RollW
 */
public class WarningProcessor extends MessageAnnotationProcessor<Warning> {
    private WarningProcessor() {
        super();
    }

    @Override
    protected void onCall(Warning annotation, Context context, TreeElement element, ChainType chainType) {
        if (annotation == null) {
            return;
        }
        InfoPolicy annotationPolicy = annotation.policy();
        if (chainType.shouldOutput(annotationPolicy)) {
            if (annotation.value().isEmpty()) {
                context.getLog().warn("WARNING! ", element);
                return;
            }
            context.getLog()
                    .warn("WARN: " + annotation.value(), element);
        }
    }

    @Override
    @NonNull
    public Class<Warning> provideClass() {
        return Warning.class;
    }

    public static WarningProcessor getInstance() {
      return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        static final WarningProcessor INSTANCE = new WarningProcessor();
    }
}
