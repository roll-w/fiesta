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

import com.sun.source.util.Trees;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * @author RollW
 */
public final class Log {
    private final Messager messager;
    private final Trees trees;

    public Log(Messager messager, Trees trees) {
        this.messager = messager;
        this.trees = trees;
    }

    public void log(Diagnostic.Kind kind,
                    CharSequence charSequence) {
        messager.printMessage(kind, charSequence);
    }

    public void log(Diagnostic.Kind kind,
                    CharSequence charSequence,
                    TreeElement element) {
        if (element.getType() == TreeElement.TreeElementType.ELEMENT) {
            messager.printMessage(kind, charSequence, element.getElement());
        } else {
            trees.printMessage(kind, charSequence, element.getTree(), element.getCompilationUnitTree());
        }
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

    public void error(CharSequence s, TreeElement element) {
        log(Diagnostic.Kind.ERROR, s, element);
    }

    public void note(CharSequence s, TreeElement element) {
        log(Diagnostic.Kind.NOTE, s, element);
    }

    public void warn(CharSequence s, TreeElement element) {
        log(Diagnostic.Kind.WARNING, s, element);
    }

    public void mandatoryWarn(CharSequence s, TreeElement element) {
        log(Diagnostic.Kind.MANDATORY_WARNING, s, element);
    }
}
