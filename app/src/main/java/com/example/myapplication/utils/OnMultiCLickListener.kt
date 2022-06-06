package com.example.myapplication.utils

import android.view.View

/**
 * 防止多次点击触发多次点击事件
 */
abstract class OnMultiCLickListener : View.OnClickListener{
    private val minClickTime = 1000
    private var lastClickTime:Long = 0

    public abstract fun onMultiClick(v:View)

    override fun onClick(v: View) {
        val curClickTime = System.currentTimeMillis()
        if(curClickTime - lastClickTime >= minClickTime){
            lastClickTime = curClickTime
            onMultiClick(v)
        }

    }
}