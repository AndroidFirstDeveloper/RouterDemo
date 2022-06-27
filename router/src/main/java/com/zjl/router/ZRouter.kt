package com.zjl.router

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent

object ZRouter {
    private val map = HashMap<String, Class<out Activity>>()
    private var initFlag: Boolean = false

    private fun loadRouter() {
//        myRouter().
//        initFlag = true
    }

    //如何调用单例中的成员方法、变量（1、创建单例对象 2、定义伴生对象）
    //其它模块如果不依赖router模块，利用注解处理器能在其它模块创建相应的文件吗
    fun init(application: Application) {
        loadRouter()
        if (initFlag)
            return
        val classNameSet = ClassUtils.get().getRouterImpl2(application, "com.zjl.routers")
        for (name in classNameSet) {
            val routerClass = Class.forName(name)
            val routerLoad = routerClass.newInstance()
            if (routerLoad is IRouterLoad) {
//                Log.e("ZRouter", "init: className=$name")
                routerLoad.loadInfo(map)
            }
        }
    }

    fun routTarget(path: String, context: Context) {
        val target = map[path]
        if (target != null) {
//            Log.e("ZRouter", "routTarget: 启动页面：$path")
            val intent = Intent(context, target)
            context.startActivity(intent)
        } else {
//            Log.e("ZRouter", "routTarget: 启动页面：$path 没有找到,size=" + map.size)
        }
    }
}