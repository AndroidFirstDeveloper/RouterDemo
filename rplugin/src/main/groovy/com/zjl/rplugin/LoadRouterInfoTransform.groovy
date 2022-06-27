package com.zjl.rplugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.zjl.bytes.RouterClassVisitor
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes


class LoadRouterInfoTransform extends Transform {

    private final Project project
    private final boolean isForApplication

    LoadRouterInfoTransform(Project project, boolean isForApplication) {
        this.project = project
        this.isForApplication = isForApplication
    }

    @Override
    String getName() {
        println "getName"
        return "LoadRouterInfoTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        println "getInputTypes"
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        println "getScopes"
        def scopes = new HashSet()
        scopes.add(QualifiedContent.Scope.PROJECT)
        if (isForApplication) {
            scopes.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES)
        }
        return scopes
    }

    @Override
    boolean isIncremental() {
        println "isIncremental"
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println "transform"
        Collection<TransformInput> inputs = transformInvocation.getInputs()
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        inputs.each {
            TransformInput transformInput ->
                //遍历jar
                transformInput.jarInputs.each { JarInput jarInput ->
                    //重命名输出文件
                    /* def jarName = jarInput.name
                     def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                     if (jarName.endsWith(".jar")) {
                         jarName = jarName.substring(0, jarName.length() - 4)
                     }
                     File copyJarFile = jarInput.file
                     //生成输出路径
                     def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                     //将input的目录复制到output指定目录
                     FileUtils.copyFile(copyJarFile, dest)*/
                    //直接将 jar 包 copy 到输出目录
                    def dest = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                    FileUtils.copyFile(jarInput.file, dest)
                }
                transformInput.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        directoryInput.file.eachFileRecurse {
                            File file ->
                                if (file.absolutePath.endsWith(".class")) {
                                    println "find class " + file.name
                                    ClassReader classReader = new ClassReader(file.bytes)
                                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                                    ClassVisitor visitor = new RouterClassVisitor(Opcodes.ASM5, classWriter)
                                    classReader.accept(visitor, ClassReader.EXPAND_FRAMES)
                                    byte[] bytes = classWriter.toByteArray()
                                    FileOutputStream fos = new FileOutputStream(file.path)
                                    fos.write(bytes)
                                    fos.close()
                                }
                        }
                        def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                        FileUtils.copyDirectory(directoryInput.file, dest)
                }
        }
    }
}