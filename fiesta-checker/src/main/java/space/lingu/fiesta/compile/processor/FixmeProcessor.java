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

import space.lingu.*;
import space.lingu.fiesta.compile.ChainType;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;

/**
 * {@link Fixme}
 *
 * @author RollW
 */
public class FixmeProcessor extends MessageAnnotationProcessor<Fixme> {
    private FixmeProcessor() {
        super();
    }

    @Override
    protected void onCall(@Nullable Fixme annotation, Context context,
                          TreeElement element, ChainType chainType) {
        if (annotation == null) {
            return;
        }
        if (chainType == ChainType.CALLER) {
            return;
        }

        String since = annotation.since();
        StringBuilder builder = new StringBuilder("FIXME: ");
        builder.append(annotation.fixme());
        if (!since.isEmpty()) {
            builder.append("\nSince: ").append(since);
        }

        Level level = annotation.level();
        switch (level) {
            case WARN:
                context.getLog().warn(builder.toString(), element);
                break;
            case ERROR:
                context.getLog().error(builder.toString(), element);
                break;
            default:
                context.getLog().note(builder.toString(), element);
        }
    }

    @NonNull
    @Override
    public Class<Fixme> provideClass() {
        return Fixme.class;
    }

    public static FixmeProcessor getInstance() {
        return Singleton.PROCESSOR;
    }

    private static final class Singleton {
        static final FixmeProcessor PROCESSOR = new FixmeProcessor();
    }
}
