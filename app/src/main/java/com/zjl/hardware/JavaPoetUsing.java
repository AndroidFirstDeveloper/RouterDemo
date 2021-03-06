package com.zjl.hardware;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Comparable;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.Runnable;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaPoetUsing{

}

/*public abstract class Clazz<T> extends String implements Serializable, Comparable<String>, Map<T, ? extends String> {
    static {
    }

    private int mInt;

    private int[] mArr;

    private File mRef;

    private T mT;

    private List<String> mParameterizedField;

    private List<? extends String> mWildcardField;

    {
    }

    public Clazz() {
    }

    @Override
    public <T> int method(String string, T t, Map<Integer, ? extends T> map) throws IOException,
            RuntimeException {
        int foo = 1;
        String bar = "a string";
        Object obj = new HashMap<Integer, ? extends T>(5);
        baz(new Runnable(String param) {
            @Override
            void run() {
            }
        });
        for (int i = 0; i < 5; i++) {
        }
        while (false) {
        }
        do {
        } while (false);
        if (false) {
        } else if (false) {
        } else {
        }
        try {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return 0;
    }

    class InnerClass {
    }
}*/



/*        package com.walfud.howtojavapoet;


        import com.squareup.javapoet.ClassName;
        import com.squareup.javapoet.CodeBlock;
        import com.squareup.javapoet.FieldSpec;
        import com.squareup.javapoet.JavaFile;
        import com.squareup.javapoet.MethodSpec;
        import com.squareup.javapoet.ParameterizedTypeName;
        import com.squareup.javapoet.TypeName;
        import com.squareup.javapoet.TypeSpec;
        import com.squareup.javapoet.TypeVariableName;
        import com.squareup.javapoet.WildcardTypeName;

        import java.io.File;
        import java.io.IOException;
        import java.io.Serializable;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import javax.lang.model.element.Modifier;

*//**
 * http://android.walfud.com/javapoet-?????????????????????/
 *//*
class HowToJavaPoetDemo {
    public static void main(String[] args) {
        TypeSpec clazz = clazz(builtinTypeField(),          // int
                arrayTypeField(),            // int[]
                refTypeField(),              // File
                typeField(),                 // T
                parameterizedTypeField(),    // List<String>
                wildcardTypeField(),         // List<? extends String>
                constructor(),               // ????????????
                method(code()));             // ????????????
        JavaFile javaFile = JavaFile.builder("com.walfud.howtojavapoet", clazz).build();

        System.out.println(javaFile.toString());
    }

    *//**
     * `public abstract class Clazz<T> extends String implements Serializable, Comparable<String>, Comparable<? extends String> {
     * ...
     * }`
     *
     * @return
     *//*
    public static TypeSpec clazz(FieldSpec builtinTypeField, FieldSpec arrayTypeField, FieldSpec refTypeField,
                                 FieldSpec typeField, FieldSpec parameterizedTypeField, FieldSpec wildcardTypeField,
                                 MethodSpec constructor, MethodSpec methodSpec) {
        return TypeSpec.classBuilder("Clazz")
                // ?????????
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                // ??????
                .addTypeVariable(TypeVariableName.get("T"))

                // ???????????????
                .superclass(String.class)
                .addSuperinterface(Serializable.class)
                .addSuperinterface(ParameterizedTypeName.get(Comparable.class, String.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Map.class),
                        TypeVariableName.get("T"),
                        WildcardTypeName.subtypeOf(String.class)))

                // ????????????
                .addStaticBlock(CodeBlock.builder().build())
                .addInitializerBlock(CodeBlock.builder().build())

                // ??????
                .addField(builtinTypeField)
                .addField(arrayTypeField)
                .addField(refTypeField)
                .addField(typeField)
                .addField(parameterizedTypeField)
                .addField(wildcardTypeField)

                // ?????? ?????????????????????????????????
                .addMethod(constructor)
                .addMethod(methodSpec)

                // ?????????
                .addType(TypeSpec.classBuilder("InnerClass").build())

                .build();
    }

    *//**
     * ????????????
     *//*
    public static FieldSpec builtinTypeField() {
        // private int mInt;
        return FieldSpec.builder(int.class, "mInt", Modifier.PRIVATE).build();
    }

    *//**
     * ????????????
     *//*
    public static FieldSpec arrayTypeField() {
        // private int[] mArr;
        return FieldSpec.builder(int[].class, "mArr", Modifier.PRIVATE).build();
    }

    *//**
     * ???????????? import ?????????
     *//*
    public static FieldSpec refTypeField() {
        // private File mRef;
        return FieldSpec.builder(File.class, "mRef", Modifier.PRIVATE).build();
    }

    *//**
     * ??????
     *//*
    public static FieldSpec typeField() {
        // private File mT;
        return FieldSpec.builder(TypeVariableName.get("T"), "mT", Modifier.PRIVATE).build();
    }

    *//**
     * ???????????????
     *//*
    public static FieldSpec parameterizedTypeField() {
        // private List<String> mParameterizedField;
        return FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class),
                "mParameterizedField",
                Modifier.PRIVATE)
                .build();
    }

    *//**
     * ????????????????????????
     *
     * @return
     *//*
    public static FieldSpec wildcardTypeField() {
        // private List<? extends String> mWildcardField;
        return FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class),
                WildcardTypeName.subtypeOf(String.class)),
                "mWildcardField",
                Modifier.PRIVATE)
                .build();
    }

    *//**
     * ????????????
     *//*
    public static MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    *//**
     * `@Override
     * public <T> Integer method(String string, T t, Map<Integer, ? extends T> map) throws IOException, RuntimeException {
     * ...
     * }`
     *
     * @param codeBlock
     * @return
     *//*
    public static MethodSpec method(CodeBlock codeBlock) {
        return MethodSpec.methodBuilder("method")
                .addAnnotation(Override.class)
                .addTypeVariable(TypeVariableName.get("T"))
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addParameter(String.class, "string")
                .addParameter(TypeVariableName.get("T"), "t")
                .addParameter(ParameterizedTypeName.get(ClassName.get(Map.class),
                        ClassName.get(Integer.class),
                        WildcardTypeName.subtypeOf(TypeVariableName.get("T"))),
                        "map")
                .addException(IOException.class)
                .addException(RuntimeException.class)
                .addCode(codeBlock)
                .build();
    }

    *//**
     * ???method??? ????????????????????????
     *//*
    public static CodeBlock code() {
        return CodeBlock.builder()
                .addStatement("int foo = 1")
                .addStatement("$T bar = $S", String.class, "a string")

                // Object obj = new HashMap<Integer, ? extends T>(5);
                .addStatement("$T obj = new $T(5)",
                        Object.class, ParameterizedTypeName.get(ClassName.get(HashMap.class),
                                ClassName.get(Integer.class),
                                WildcardTypeName.subtypeOf(TypeVariableName.get("T"))))

                // method(new Runnable(String param) {
                //   @Override
                //   void run() {
                //   }
                // });
                .addStatement("baz($L)", TypeSpec.anonymousClassBuilder("$T param", String.class)
                        .superclass(Runnable.class)
                        .addMethod(MethodSpec.methodBuilder("run")
                                .addAnnotation(Override.class)
                                .returns(TypeName.VOID)
                                .build())
                        .build())

                // for
                .beginControlFlow("for (int i = 0; i < 5; i++)")
                .endControlFlow()

                // while
                .beginControlFlow("while (false)")
                .endControlFlow()

                // do... while
                .beginControlFlow("do")
                .endControlFlow("while (false)")

                // if... else if... else...
                .beginControlFlow("if (false)")
                .nextControlFlow("else if (false)")
                .nextControlFlow("else")
                .endControlFlow()

                // try... catch... finally
                .beginControlFlow("try")
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("e.printStackTrace()")
                .nextControlFlow("finally")
                .endControlFlow()

                .addStatement("return 0")
                .build();
    }
}*/


