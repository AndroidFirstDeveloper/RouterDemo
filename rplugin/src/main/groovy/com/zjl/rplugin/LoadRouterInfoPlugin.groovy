package com.zjl.rplugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LoadRouterInfoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "start apply"
        def extension = project.getExtensions().findByType(AppExtension.class)
        def isForApplication = true
        if (extension == null) {
            isForApplication = false
            extension = project.getExtensions().findByType(LibraryExtension.class)
        }
        extension.registerTransform(new LoadRouterInfoTransform(project, isForApplication))
        println "end apply"
    }
}