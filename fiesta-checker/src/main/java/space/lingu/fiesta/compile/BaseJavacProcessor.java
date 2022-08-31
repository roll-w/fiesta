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

import com.sun.source.tree.*;
import com.sun.source.util.*;
import space.lingu.InfoPolicy;
import space.lingu.NonNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author RollW
 */
public abstract class BaseJavacProcessor extends AbstractProcessor implements TaskListener {
    private Context context;
    private Trees trees;
    private final List<Processor<?>> mProcessors = new ArrayList<>();
    private final Map<String, ProcessorNamePair> mProcessorNameMap = new HashMap<>();

    protected BaseJavacProcessor() {
        mProcessors.addAll(registerAllProcessors());
        classifyProcessors();
    }

    private void classifyProcessors() {
        mProcessors.forEach(processor -> {
            if (processor == null) {
                throw new IllegalArgumentException("Processor cannot be null.");
            }
            ProcessorNamePair name =
                    mProcessorNameMap.get(processor.provideClass().getCanonicalName());
            if (name == null) {
                name = new ProcessorNamePair(
                        new ArrayList<>(),
                        processor.provideClass().getCanonicalName(),
                        processor.provideClass()
                );
                mProcessorNameMap.put(name.name, name);
            }
            name.processors.add(processor);
        });
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        JavacTask javacTask = JavacTask.instance(processingEnv);
        javacTask.addTaskListener(this);

        trees = Trees.instance(javacTask);
        context = Context.createContext(processingEnv, trees);
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedClass = roundEnv.getElementsAnnotatedWith(annotation);
            final String name = annotation.getQualifiedName().toString();
            ProcessorNamePair processorNamePair = mProcessorNameMap.get(name);
            if (processorNamePair == null) {
                continue;
            }
            for (Element element : annotatedClass) {
                for (Processor<?> processor : processorNamePair.processors) {
                    processor.process(context, JavacTreeElement.create(context, element), InfoPolicy.SELF);
                }
            }
        }
        return roundEnv.processingOver();
    }

    @NonNull
    protected abstract List<Processor<?>> registerAllProcessors();


    @Override
    public void started(TaskEvent e) {
    }

    @Override
    public void finished(TaskEvent e) {
        if (e.getKind() != TaskEvent.Kind.ANALYZE) {
            return;
        }
        e.getCompilationUnit().accept(new AnnotatedTreeScanner(e), null);
    }

    public Context getContext() {
        return context;
    }

    public Trees getTrees() {
        return trees;
    }

    private class AnnotatedTreeScanner extends TreeScanner<Void, Void> {
        final TaskEvent e;

        AnnotatedTreeScanner(TaskEvent event) {
            e = event;
        }

        @Override
        public Void visitMethodInvocation(MethodInvocationTree node, Void unused) {
            TreePath selectPath = trees.getPath(e.getCompilationUnit(), node.getMethodSelect());
            Element selectElement = trees.getElement(selectPath);
            mProcessorNameMap
                    .values()
                    .stream()
                    .filter(pair -> selectElement.getAnnotation(pair.clz) != null)
                    .forEach(pair -> pair.processors.forEach(processor ->
                            processor.process(context,
                                    JavacTreeElement.create(
                                            context,
                                            node.getMethodSelect(),
                                            selectElement,
                                            e.getCompilationUnit()
                                    ),
                                    InfoPolicy.CALLER)));

            return super.visitMethodInvocation(node, unused);
        }

        @Override
        public Void visitNewClass(NewClassTree node, Void unused) {
            TreePath path = trees.getPath(e.getCompilationUnit(), node.getIdentifier());
            commonVisit(node, path);
            return super.visitNewClass(node, unused);
        }

        @Override
        public Void visitMemberReference(MemberReferenceTree node, Void unused) {
            commonVisit(node);
            return super.visitMemberReference(node, unused);
        }

        @Override
        public Void visitVariable(VariableTree node, Void unused) {
            TreePath path = trees.getPath(e.getCompilationUnit(), node.getType());
            commonVisit(node, path);
            return super.visitVariable(node, unused);
        }

        @Override
        public Void visitMemberSelect(MemberSelectTree node, Void unused) {
            TreePath path = trees.getPath(e.getCompilationUnit(), node.getExpression());
            commonVisit(node, path);
            return super.visitMemberSelect(node, unused);
        }


        private void commonVisit(Tree node) {
            TreePath path = trees.getPath(e.getCompilationUnit(), node);
            Element element = trees.getElement(path);
            mProcessorNameMap
                    .values()
                    .stream()
                    .filter(pair -> element != null && element.getAnnotation(pair.clz) != null)
                    .forEach(pair -> pair.processors.forEach(processor ->
                            processor.process(context,
                                    JavacTreeElement.create(
                                            context,
                                            node,
                                            e.getCompilationUnit()
                                    ),
                                    InfoPolicy.CALLER))
                    );
        }

        private void commonVisit(Tree node, TreePath path) {
            Element element = trees.getElement(path);
            // get the element that has annotation
            mProcessorNameMap
                    .values()
                    .stream()
                    .filter(pair -> element != null && element.getAnnotation(pair.clz) != null)
                    .forEach(pair -> pair.processors.forEach(processor ->
                            processor.process(context,
                                    JavacTreeElement.create(
                                            context,
                                            node, element,
                                            e.getCompilationUnit()
                                    ),
                                    InfoPolicy.CALLER))
                    );
        }

    }


    private static class ProcessorNamePair {
        private final List<Processor<?>> processors;
        private final String name;
        private final Class<? extends Annotation> clz;

        private ProcessorNamePair(List<Processor<?>> processors, String name, Class<? extends Annotation> clz) {
            this.processors = processors;
            this.name = name;
            this.clz = clz;
        }
    }




    // -------------------------------------------
    private static <T> T jbUnwrap(Class<? extends T> iface, T wrapper) {
        // if in jb env.
        T unwrapped = null;
        try {
            final Class<?> apiWrappers = wrapper.getClass().getClassLoader().loadClass("org.jetbrains.jps.javac.APIWrappers");
            final Method unwrapMethod = apiWrappers.getDeclaredMethod("unwrap", Class.class, Object.class);
            unwrapped = iface.cast(unwrapMethod.invoke(null, iface, wrapper));
        } catch (Throwable ignored) {
        }
        return unwrapped != null ? unwrapped : wrapper;
    }

}
