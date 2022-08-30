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

package space.lingu.fiesta.compile;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * @author RollW
 */
public final class Log {
    private final Messager messager;

    public Log(Messager messager) {
        this.messager = messager;
    }

    public void log(Diagnostic.Kind kind,
                    CharSequence charSequence) {
        messager.printMessage(kind, charSequence);
    }

    public void log(Diagnostic.Kind kind,
                    CharSequence charSequence,
                    Element element) {
        messager.printMessage(kind, charSequence, element);
    }

    public void log(Diagnostic.Kind kind,
                    CharSequence msg,
                    Element e,
                    AnnotationMirror a) {
        messager.printMessage(kind, msg, e, a);
    }

    public void log(Diagnostic.Kind kind,
                    CharSequence msg,
                    Element e, AnnotationMirror a,
                    AnnotationValue v) {
        messager.printMessage(kind, msg, e, a, v);
    }

    public void error(CharSequence s) {
        log(Diagnostic.Kind.ERROR, s);
    }

    public void note(CharSequence s) {
        log(Diagnostic.Kind.NOTE, s);
    }

    public void warn(CharSequence s) {
        log(Diagnostic.Kind.WARNING, s);
    }

    public void error(CharSequence s, Element element) {
        log(Diagnostic.Kind.ERROR, s, element);
    }

    public void note(CharSequence s, Element element) {
        log(Diagnostic.Kind.NOTE, s, element);
    }

    public void warn(CharSequence s, Element element) {
        log(Diagnostic.Kind.WARNING, s, element);
    }
}
