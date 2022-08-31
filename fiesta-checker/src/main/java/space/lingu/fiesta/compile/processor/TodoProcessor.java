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
import space.lingu.Nullable;
import space.lingu.Todo;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;

/**
 * @author RollW
 */
public class TodoProcessor extends MessageAnnotationProcessor<Todo> {
    public TodoProcessor() {
        super();
    }

    @Override
    protected void onCall(@Nullable Todo annotation, Context context,
                          TreeElement element, InfoPolicy policy) {
        if (annotation == null) {
            return;
        }
        if (policy == InfoPolicy.CALLER) {
            return;
        }
        String since = annotation.since();
        StringBuilder builder = new StringBuilder("TODO: ");
        builder.append(annotation.todo());
        if (!since.isEmpty()) {
            builder.append("\nSince: ").append(since);
        }
        context.getLog().note(builder.toString(), element);
    }

    @NonNull
    @Override
    public Class<Todo> provideClass() {
        return Todo.class;
    }

    public static TodoProcessor getInstance() {
      return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        static final TodoProcessor INSTANCE = new TodoProcessor();
    }
}
