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

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * @author RollW
 */
public class JavacTreeElement implements TreeElement {
    private final TreeElementType type;

    // --- log use
    private final Tree tree;
    private final Element element;
    // -----------

    // --- annotation extract use
    private final Element annotatedElement;
    // --------------------------

    private final CompilationUnitTree compilationUnitTree;

    public JavacTreeElement(TreeElementType type,
                            Element element,
                            Tree tree,
                            Element annotatedElement,
                            CompilationUnitTree compilationUnitTree) {
        this.type = type;
        this.tree = tree;
        this.element = element;
        this.annotatedElement = annotatedElement;
        this.compilationUnitTree = compilationUnitTree;
    }

    @Override
    public TreeElementType getType() {
        return type;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> provideClass) {
        if (annotatedElement != null) {
            return annotatedElement.getAnnotation(provideClass);
        }
        if (element == null) {
            return null;
        }

        return element.getAnnotation(provideClass);
    }

    @Override
    public Tree getTree() {
        return tree;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public CompilationUnitTree getCompilationUnitTree() {
        return compilationUnitTree;
    }

    public static TreeElement create(Context context, Element element) {
        return new JavacTreeElement(
                TreeElementType.ELEMENT,
                element,
                context.getTrees().getTree(element),
                element,
                null);
    }

    public static TreeElement create(Context context, Tree tree,
                                     CompilationUnitTree compilationUnitTree) {
        TreePath path = context.getTrees().getPath(compilationUnitTree, tree);
        Element annotatedElement = context.getTrees().getElement(path);
        return new JavacTreeElement(
                TreeElementType.TREE,
                annotatedElement,
                tree,
                annotatedElement,
                compilationUnitTree);
    }

    public static TreeElement create(Context context,
                                     Tree tree,
                                     Element annotatedElement,
                                     CompilationUnitTree compilationUnitTree) {
        TreePath path = context.getTrees().getPath(compilationUnitTree, tree);
        return new JavacTreeElement(
                TreeElementType.TREE,
                context.getTrees().getElement(path),
                tree,
                annotatedElement,
                compilationUnitTree);
    }
}
