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
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * This annotation is to remind you that this is an experimental API.
 * Signifies that a public API (public class, method or field) is subject to
 * incompatible changes, or even removal, in a future release.
 * <p>
 * It is generally safe for applications to depend on experimental APIs,
 * at the cost of some extra work during upgrades.
 * However, it is generally inadvisable for libraries
 * (which get included on users' CLASSPATHs, outside the library
 * developers' control) to do so.
 *
 * @author RollW
 */
// @see com.google.common.annotations.Beta in Guava.
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {
        CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD,
        PACKAGE, PARAMETER, TYPE, TYPE_USE, TYPE_PARAMETER}
)
public @interface Experimental {
    String info() default DEFAULT_HINT;

    InfoPolicy policy() default InfoPolicy.CALLER;

    String DEFAULT_HINT = "You are using an experimental API.";
}
