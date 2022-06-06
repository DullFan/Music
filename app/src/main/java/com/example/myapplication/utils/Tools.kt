package com.example.myapplication.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.util.regex.Pattern
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout


const val NET_Ease_CLOUND_URL = "http://42.193.118.40:3000"


private var toast: Toast? = null

/**
 * 封装Toast
 */
fun Any.showToast(context: Context?) {
    if (toast == null) {
        toast = Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT)
    } else {
        toast!!.cancel()
        toast = Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT)
    }
    toast!!.show()
}

/**
 * 打Log
 */
fun Any.showLog() {
    Log.e("showLog", "$this")
}

/**
 * 判断字符串是否为空
 */
fun String.isNull(): Boolean {
    return TextUtils.isEmpty(this)
}


/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Int.px(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Int.dp(context: Context): Int {
    // 获取当前手机的像素密度（1个dp对应几个px）
    val scale = context.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt() // 四舍五入取整
}

/**
 * 获取状态栏高度
 */
fun getStatusBarHeight(resources: Resources): Int {
    var result = 0
    val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

/**
 * 给View设置状态栏高度的Padding
 */
fun addStatusBarHeightPadding(view: View, context: Context) {
    //设置状态栏Padding
    view.setPadding(0, getStatusBarHeight(context.resources).dp(context), 0, 0)
}

/**
 * 判断手机号码是否合法
 */
fun regexPhone(phone: String): Boolean {
    if (phone.length != 11) {
        return false
    }
    var mainRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1,2,3,5-9])|(177))\\d{8}$"
    var p = Pattern.compile(mainRegex)
    val m = p.matcher(phone)
    return m.matches()
}

/**
 * 判断网络是否连接
 */
fun isNetConnected(context: Context?): Boolean {
    if (context != null) {
        val mConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) {
            "当前是否有网络：" + mNetworkInfo.isAvailable.showLog()
            return mNetworkInfo.isAvailable
        }
    }
    "当前是否有网络：false".showLog()
    return false
}