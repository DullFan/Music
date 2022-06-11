package com.example.wonder.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class MyTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    //是否获取焦点
    override fun isFocused(): Boolean {
        return true
    }
}