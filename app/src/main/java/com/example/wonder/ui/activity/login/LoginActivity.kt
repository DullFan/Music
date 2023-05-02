package com.example.wonder.ui.activity.login

import android.os.Bundle
import androidx.activity.viewModels
import com.example.wonder.base.BaseActivity
import com.example.wonder.databinding.ActivityLoginBinding
import com.example.wonder.ui.activity.MainActivity
import com.example.wonder.utils.*
import com.example.wonder.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {
    val loginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    val viewModel :LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        addStatusBarHeightPadding(loginBinding.loginPaddingBar, this)

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
            if (it.code == 200) {
                viewModel.loginRequest(
                    viewModel.verificationCodeEdit.value.toString(),
                    viewModel.phoneEdit.value.toString(),
                    context = this
                )
            }
        }

        viewModel.loginLiveData.observe(this) {
            loginEvents(it.cookie, it.account.id)
        }
    }

    private fun loginEvents(cookie: String, id: Int) {
        KV.encode(MMKVData.COOKIE, cookie)
        KV.encode(MMKVData.U_ID, id)
        "登录成功".showToast(this)
        viewModel.countDownTimerFlag.value = true
        startA(MainActivity::class.java)
    }
}