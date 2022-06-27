package com.zjl.rprocessor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class PathGroup {

    private final String TAG = "PathGroup";
    //    private final String packageName;
    private final Map<String, TypeElement> pathMap;
//    private final String bindingClassName;


    //    public PathGroup(String packageName) {
    public PathGroup() {
//        this.packageName = packageName;
        this.pathMap = new HashMap<>();
//        this.bindingClassName = "myRouter_" + System.currentTimeMillis();
    }

//    public String getPackageName() {
//        return this.packageName;
//    }

    public int getSize() {
        return pathMap.size();
    }

    public void addNewPath(String path, TypeElement typeElement) {
        pathMap.put(path, typeElement);
    }

    public TypeSpec productJavaFile() {
        /**根据module名称生成相应的router注册类*/
        System.out.println(TAG + " productJavaFile");
        Set<String> keySet = pathMap.keySet();
        String key = keySet.iterator().next();
        String[] paths = key.split("/");
        System.out.println("paths=" + paths.toString());
        String module = paths[0];
        String className = module + "RouterImpl";
        System.out.println("class name=" + className);
        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get("com.zjl.router", "IRouterLoad"))
                .addMethod(generateMethodWithJavaPoet()).build();
        return typeSpec;
    }

    public MethodSpec generateMethodWithJavaPoet() {
        System.out.println(TAG + " generateMethodWithJavaPoet");
//        ClassName owner = ClassName.bestGuess(classElement.getQualifiedName().toString());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("loadInfo")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
//                .addParameter(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), ClassName.get(String.class)), "routers");
                .addParameter(
                        ParameterizedTypeName.get(
                                ClassName.get(Map.class),
                                ClassName.get(String.class),
                                ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(ClassName.get("android.app", "Activity")))//通配符Class<? extends Activity>
                        ),
                        "routers");
        Set<Map.Entry<String, TypeElement>> entrySet = pathMap.entrySet();
        for (Map.Entry<String, TypeElement> entry : entrySet) {
            TypeElement element = entry.getValue();//获取map中的值
            ClassName className = ClassName.bestGuess(element.getQualifiedName().toString());
            System.out.println(TAG + " name=" + element.getSimpleName() + ",allname=" + element.getQualifiedName() + ",activity class=" + className.getClass() + ",element class=" + element.getClass());
//            methodBuilder.addCode("routers.put(\"" + entry.getKey() + "\"," + element.getClass() + ");");//no
//            methodBuilder.addCode("routers.put(\"" + entry.getKey() + "\"," + className.getClass() + ");\n");//no
            methodBuilder.addCode("routers.put(\"" + entry.getKey() + "\"," + className + ".class);\n");//yes
            try {
                //注解器中无法通过Class.forName("")获取指定类的class对象，一种鸡贼的方法是ClassName+".class"
//                methodBuilder.addCode("routers.put(\"" + entry.getKey() + "\","+Class.forName(element.getQualifiedName().toString())+");\n");//no
            } catch (Exception e) {
                System.out.println("根据完全限定名获取class失败：" + e.getCause());
            }
//            methodBuilder.addCode("routers.put(\"" + entry.getKey() + "\"," + "\"aaaaa\"" + ");");
//            String viewName = element.getSimpleName().toString();
//            String viewType = element.asType().toString();
//            methodBuilder.addCode("routers." + viewName + "=" + "(" + viewType + ")(((android.app.Activity)owner).findViewById(" + id + "));");
        }
        return methodBuilder.build();
    }
}
