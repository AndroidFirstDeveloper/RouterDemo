package com.zjl.hardware

import android.app.Application
import com.zjl.router.ZRouter

class Myapplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ZRouter.init(this)
    }
}