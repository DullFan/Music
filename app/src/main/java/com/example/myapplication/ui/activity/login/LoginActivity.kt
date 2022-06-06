package com.example.myapplication.ui.activity.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.utils.SpData
import com.example.myapplication.utils.addStatusBarHeightPadding
import com.example.myapplication.utils.isNull
import com.example.myapplication.utils.showToast
import com.example.myapplication.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {
    private val loginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        addStatusBarHeightPadding(loginBinding.loginPaddingBar,this)

        loginBinding.viewModel = viewModel

        loginBinding.utilsViewModel = utilsViewModel

        loginBinding.lifecycleOwner = this

        loginBinding.editText = loginBinding.loginPasswordVerificationCodeText

        loginBinding.textView = loginBinding.loginPrivacyPolicy

        //设置观察者
        viewModel.sendVerificationCodeLiveData.observe(this) {
            if (it.data) {
                "发送成功".showToast(this)
                viewModel.countDownTimer.start()
            } else {
                it.message.showToast(this)
            }
        }

        viewModel.registerLiveData.observe(this) {
            loginEvents(it.token)
        }

        viewModel.loginLiveData.observe(this) {
            loginEvents(it.token)
        }
    }

    private fun loginEvents(token: String) {
        getSpEdit().putString(SpData.TOKEN, token)
        getSpEdit().commit()
        "登录成功".showToast(this)
        viewModel.countDownTimerFlag.value = true
        startA(MainActivity::class.java)
    }
}