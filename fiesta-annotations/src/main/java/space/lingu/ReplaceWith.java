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

package space.lingu;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies a code fragment that can be used to replace
 * a deprecated function, property or class.
 *
 * @author RollW
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Experimental(value = "This annotation is not stable yet.", policy = InfoPolicy.NONE)
public @interface ReplaceWith {
    /**
     * Provide the replacement expression.
     */
    String value();
}
