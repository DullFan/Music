package com.example.myapplication.utils

import android.app.Application
import com.example.myapplication.skin.SkinManager

class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        //初始化换肤框架
        SkinManager.init(this)
    }
}