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
import space.lingu.Level;
import space.lingu.NonNull;
import space.lingu.fiesta.compile.ChainType;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;

import java.util.Objects;

/**
 * {@link Experimental}
 *
 * @author RollW
 */
public class ExperimentalProcessor extends MessageAnnotationProcessor<Experimental> {
    private ExperimentalProcessor() {
        super();
    }

    @Override
    protected void onCall(Experimental annotation, Context context,
                          TreeElement element, ChainType chainType) {
        InfoPolicy annotationPolicy = annotation.policy();
        if (annotationPolicy == InfoPolicy.NONE) {
            return;
        }
        String message = getMessage(annotation);
        Level level = annotation.level();
        if (chainType.shouldOutput(annotationPolicy)) {
            context.getLog().log(
                    level,
                    prefix(level) + message,
                    element
            );
        }
    }

    private String prefix(Level level) {
        return level.name() + ": ";
    }

    private String getMessage(Experimental annotation) {
        String value = annotation.value();
        if (Objects.equals(value, Experimental.DEFAULT_HINT)) {
            return annotation.info();
        }
        return value;
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
