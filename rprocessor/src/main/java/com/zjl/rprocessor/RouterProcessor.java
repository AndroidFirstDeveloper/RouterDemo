package com.zjl.rprocessor;

import com.squareup.javapoet.JavaFile;
import com.zjl.rannotation.RouterPath;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class RouterProcessor extends AbstractProcessor {
    private final String TAG = "RouterProcessor";
    //    private final Map<String, PathGroup> moduleMap;
    private Filer filer;
    private Elements elements;
    private final PathGroup pathGroup;

    public RouterProcessor() {
        System.out.println("调用构造函数 RouterProcessor()");
        pathGroup = new PathGroup();
//        moduleMap = new HashMap<>();
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        System.out.println(TAG + ", init");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println(TAG + ", getSupportedSourceVersion");
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println(TAG + ", getSupportedAnnotationTypes");
        Set<String> set = new LinkedHashSet<>();
        set.add(RouterPath.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(TAG + ", process1");
//        System.out.println(TAG+", process2");
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(RouterPath.class);
        for (Element element : elementSet) {
            if (element.getKind() != ElementKind.CLASS) {
//                Log.e(TAG, "process: element.kind != ElementKind.CLASS")
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            String activityName = typeElement.getQualifiedName().toString();
            RouterPath routerAnnotation = typeElement.getAnnotation(RouterPath.class);
            String routerPath = routerAnnotation.path();
            Element packageElement = typeElement.getEnclosingElement();
            if (packageElement.getKind() == ElementKind.PACKAGE) {
//                String packageName = ((PackageElement) packageElement).getQualifiedName().toString();
                String packageName = elements.getPackageOf(packageElement).getQualifiedName().toString();
                String simplePckName = ((PackageElement) packageElement).getSimpleName().toString();
                Element pckEnclosingEle = ((PackageElement) packageElement).getEnclosingElement();
                String pckEnclosingName;
                if (pckEnclosingEle != null) {
                    pckEnclosingName = pckEnclosingEle.getSimpleName().toString();
                } else {
                    pckEnclosingName = "none thing";
                }
                //packageName=com.zjl.food.bbq,simplePckName=bbq,pckEnclosingName=none thing
                System.out.println("packageName=" + packageName + ",simplePckName=" + simplePckName + ",pckEnclosingName=" + pckEnclosingName);
                /*PathGroup pathGroup = moduleMap.get(packageName);
                if (pathGroup == null) {
                    pathGroup = new PathGroup(packageName);
                    moduleMap.put(packageName, pathGroup);
                }*/
                pathGroup.addNewPath(routerPath, typeElement);
                System.out.println(TAG + " routerPath=" + routerPath + ",packageName=" + packageName + ",activityName=" + activityName);
            }
        }

        /*Set<Map.Entry<String, PathGroup>> entries = moduleMap.entrySet();
        System.out.println("map.size=" + entries.size());
        for (Map.Entry<String, PathGroup> entry : entries) {
            PathGroup pathGroup = entry.getValue();
            JavaFile javaFile = JavaFile.builder(pathGroup.getPackageName(), pathGroup.productJavaFile()).build();
            try {
                javaFile.writeTo(filer);
            } catch (Exception e) {
                System.out.println(TAG + " error=" + e.getMessage());
            }
        }*/
        if (pathGroup.getSize() > 0) {
            System.out.println("size>0 create new file");
            JavaFile javaFile = JavaFile.builder("com.zjl.routers", pathGroup.productJavaFile()).build();
            try {
                javaFile.writeTo(filer);
            } catch (Exception e) {
                System.out.println(TAG + " error=" + e.getMessage());
            }
        }
        return true;
    }
}
