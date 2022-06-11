package com.example.wonder.utils

import android.app.Application
import com.example.wonder.skin.SkinManager
import com.tencent.mmkv.MMKV

class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        //初始化换肤框架
        SkinManager.init(this)
    }
}