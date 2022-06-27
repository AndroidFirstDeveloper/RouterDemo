package com.zjl.wm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zjl.rannotation.RouterPath

@RouterPath(path = "wm/waimai")
class WmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wm)
    }
}