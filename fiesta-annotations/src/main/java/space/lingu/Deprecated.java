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
 * More detailed {@code Deprecated}.
 *
 * @author RollW
 * @see java.lang.Deprecated
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
@Experimental(value = "This annotation is not stable yet.", policy = InfoPolicy.NONE)
// This annotation definition was referenced from the definitions in kotlin-stdlib/Deprecated
// and java.lang.Deprecated from Java 9 and above.
public @interface Deprecated {
    /**
     * The message explaining the deprecation and
     * recommending an alternative API to use.
     *
     * @return message
     */
    String value() default "";

    /**
     * If present, specifies a code fragment which
     * should be used as a replacement for the
     * deprecated API usage.
     *
     * @return replace with.
     * @see ReplaceWith
     */
    ReplaceWith[] replaceWith() default {};

    /**
     * Returns the version in which the annotated element became deprecated.
     * The version string is in the same format and namespace as the value of
     * the {@code @since} javadoc tag. The default value is the empty
     * string.
     *
     * @return the version string
     */
    String since() default "";

    /**
     * Indicates whether the annotated element is subject to removal in a
     * future version. The default value is {@code false}.
     *
     * @return whether the element is subject to removal
     */
    boolean forRemoval() default false;

    /**
     * The log level of the annotation.
     *
     * @return the level.
     * @see Level
     */
    Level level() default Level.WARN;

    /**
     * Policy for the information output of this annotation at compile time.
     *
     * @return the policy.
     * @see InfoPolicy
     */
    InfoPolicy policy() default InfoPolicy.CALLER;
}
