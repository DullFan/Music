package com.example.wonder.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.example.wonder.ui.activity.login.LoginActivity
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


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
            //有网络返回true
            return mNetworkInfo.isAvailable
        }
    }
    ////有网络返回false
    return false
}

/**
 * 判断是否使用网络请求
 */
fun isNet(context: Context, launch: () -> Unit) {
    if (isNetConnected(context)) {
        launch.invoke()
    } else {
        "当前无网络".showToast(context)
    }
}

/**
 * 判断是否有登录
 */
fun isCookie(context: Context, launch: () -> Unit) {
    if (KV.decodeInt(MMKVData.U_ID) != 0) {
        launch.invoke()
    } else {
        context.startActivity(Intent(context, LoginActivity::class.java))
        "当前还未登录哦!!!".showToast(context)
    }
}


/**
 * 判断是否有网络或登录
 */
fun isNetCookie(context: Context, launch: () -> Unit) {
    if (isNetConnected(context)) {
        if (KV.decodeInt(MMKVData.U_ID) != 0) {
            launch.invoke()
        } else {
            context.startActivity(Intent(context, LoginActivity::class.java))
            "当前还未登录哦!!!".showToast(context)

        }
    } else {
        "当前无网络".showToast(context)
    }
}

/*
 * 判断服务是否启动,context上下文对象 ，className服务的name
 */
fun isServiceRunning(mContext: Context, className: String?): Boolean {
    var isRunning = false
    val activityManager = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val serviceList = activityManager.getRunningServices(30)
    if (serviceList.size <= 0) {
        return false
    }
    Log.e("OnlineService：", className!!)
    for (i in serviceList.indices) {
        Log.e("serviceName：", serviceList[i].service.className)
        if (serviceList[i].service.className.contains(className)) {
            isRunning = true
            break
        }
    }
    return isRunning
}

/**
 * 将时间戳转换成 分:秒
 */
fun formatTime(length: Long): String {
    val date = Date(length)
    //时间格式化工具
    val sdf = SimpleDateFormat("mm:ss")
    return sdf.format(date)
}


/**
 * 将时间戳转换成 年/月/日
 */
fun formatTimeYear(length: Long): String {
    val date = Date(length)
    //时间格式化工具
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(date)
}


/**
 * 元转万 / 亿元且保留两位小数并四舍五入
 */
fun getNumberWanTwo(value: String): String {
    val formater = DecimalFormat("1")
    val bigDecimal = BigDecimal(value)
    var decimal = BigDecimal("10000")
    formater.roundingMode = RoundingMode.HALF_UP

    if (value.length in 5..8) {
        decimal = bigDecimal.divide(BigDecimal("10000"))
        return "${formater.format(decimal)}万"
    } else if (value.length >= 9) {
        decimal = bigDecimal.divide(BigDecimal("100000000"))
        return "${formater.format(decimal)}亿"
    } else {
        decimal = bigDecimal.divide(BigDecimal("1"))
        return formater.format(decimal)
    }
}