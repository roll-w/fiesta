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
import space.lingu.Level;
import space.lingu.NonNull;
import space.lingu.fiesta.compile.ChainType;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;


/**
 * {@link Dangerous}
 *
 * @author RollW
 */
public class DangerousProcessor extends MessageAnnotationProcessor<Dangerous> {

    private DangerousProcessor() {
    }

    @Override
    @NonNull
    public Class<Dangerous> provideClass() {
        return Dangerous.class;
    }

    @Override
    protected void onCall(@NonNull Dangerous dangerous,
                          Context context, TreeElement element, ChainType chainType) {
        InfoPolicy annotationPolicy = dangerous.policy();
        if (annotationPolicy == InfoPolicy.NONE) {
            return;
        }
        Level level = dangerous.level();
        if (!chainType.shouldOutput(annotationPolicy)) {
            return;
        }

        if (dangerous.message().isEmpty()) {
            context.getLog().log(level, "Dangerous!",
                    element);
            return;
        }
        context.getLog().log(level, "Dangerous: " + dangerous.message(), element);
    }

    public static DangerousProcessor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        static final DangerousProcessor INSTANCE = new DangerousProcessor();
    }
}
