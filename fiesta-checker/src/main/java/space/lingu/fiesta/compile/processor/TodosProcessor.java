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
import space.lingu.Todos;

/**
 * @author RollW
 */
public class TodosProcessor extends RepeatableAnnotationProcessor<Todos, Todo> {
    public TodosProcessor(MessageAnnotationProcessor<Todo> messageAnnotationProcessor) {
        super(messageAnnotationProcessor);
    }

    @NonNull
    @Override
    public Class<Todos> provideClass() {
        return Todos.class;
    }

    @NonNull
    @Override
    protected Todo[] extractsMultipleAnnotations(Todos annotation) {
        return annotation.value();
    }

    public static TodosProcessor getInstance() {
      return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        static final TodosProcessor INSTANCE = new TodosProcessor(TodoProcessor.getInstance());
    }
}
