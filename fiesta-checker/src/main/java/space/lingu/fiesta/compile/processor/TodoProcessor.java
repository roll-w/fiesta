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
import space.lingu.Todo;
import space.lingu.fiesta.compile.ChainType;
import space.lingu.fiesta.compile.Context;
import space.lingu.fiesta.compile.TreeElement;

/**
 * @author RollW
 */
public class TodoProcessor extends MessageAnnotationProcessor<Todo> {
    private TodoProcessor() {
        super();
    }

    @Override
    protected void onCall(@NonNull Todo annotation, Context context,
                          TreeElement element, ChainType chainType) {
        if (chainType == ChainType.CALLER) {
            return;
        }
        String since = annotation.since();
        String value = valueOf(annotation);
        StringBuilder builder = new StringBuilder("TODO: ");
        builder.append(value);
        if (!since.isEmpty()) {
            builder.append("\nSince: ").append(since);
        }
        context.getLog().note(builder.toString(), element);
    }


    @SuppressWarnings("deprecation")
    private String valueOf(Todo todo) {
        if (todo.value().isEmpty()) {
            return todo.todo();
        }
        return todo.value();
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
