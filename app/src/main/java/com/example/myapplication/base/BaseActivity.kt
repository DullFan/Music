package com.example.myapplication.base

import android.app.ActivityManager
import android.content.*
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.utils.*
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.UtilsViewModel

open class BaseActivity : AppCompatActivity() {
    lateinit var intentFilter: IntentFilter
    lateinit var networkChangeReceiver: NetworkChangeReceiver
    var baseActivityFlag = true;
    val utilsViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UtilsViewModel::class.java)
    }


    override fun onResume() {
        super.onResume()
        //设置状态栏字体以及沉浸式
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        utilsViewModel.baseActivity = this

        networkChangeReceiver = NetworkChangeReceiver()
        intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")

        registerReceiver(networkChangeReceiver, intentFilter)
        networkChangeReceiver.setCallBackNetWork(object : NetworkChangeReceiver.CallBackNetWork {
            override fun callBack(str: String?) {
                if (baseActivityFlag) {
                    baseActivityFlag = false
                } else {
                    str?.showToast(this@BaseActivity)
                }
            }
        })
    }


    fun <T> startA(clazz: Class<T>) {
        startActivity(Intent(this, clazz))
    }

    fun getSp(): SharedPreferences {
        return getSharedPreferences(SpData.SP_NAME, MODE_PRIVATE)
    }

    fun getSpEdit(): SharedPreferences.Editor {
        return getSp().edit()
    }


}

class NetworkChangeReceiver : BroadcastReceiver() {
    private var typeName = "" //记录网络状态字符串

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null) {
            // WIFI 和 移动网络都关闭 即没有有效网络
            typeName = "当前无网络连接"
            if (callBackNetWork != null) {
                callBackNetWork!!.callBack(typeName)
            }
            return
        }
        if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
            typeName = "当前正在使用WIFI"
        } else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            typeName = "当前正在使用数据"
        }

        if (callBackNetWork != null) {
            callBackNetWork!!.callBack(typeName)
        }
    }

    private var callBackNetWork: CallBackNetWork? = null

    fun setCallBackNetWork(callBackNetWork: CallBackNetWork?) { //公开接口 能访问接口
        this.callBackNetWork = callBackNetWork
    }

    interface CallBackNetWork {
        fun callBack(str: String?)
    }
}
