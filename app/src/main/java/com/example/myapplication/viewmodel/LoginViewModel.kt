package com.example.myapplication.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.LoginBean
import com.example.myapplication.bean.RegisterBean
import com.example.myapplication.bean.SendVerificationCodeBean
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.utils.*
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    /**
     * 发送验证码文本
     */
    var countdown = MutableLiveData<String>("发送验证码")

    /**
     * 手机号输入框文本
     */
    var phoneEdit = MutableLiveData<String>("")

    /**
     * 验证码输入框文本
     */
    var verificationCodeEdit = MutableLiveData<String>("")

    /**
     * 密码输入框文本
     */
    var passwordEdit = MutableLiveData<String>("")

    /**
     * 昵称输入框文本
     */
    var usernameEdit = MutableLiveData<String>("")

    /**
     * 登录按钮是否可用
     */
    var buttonIsEnabled = MutableLiveData<Boolean>(false)


    /**
     * 发送验证码是否可用
     */
    var sendCodeIsEnabled = MutableLiveData<Boolean>(false)

    /**
     * CheckBox是否选中
     */
    var checkBoxIsChecked = MutableLiveData<Boolean>(false)

    /**
     * 计时器是否取消
     */
    var countDownTimerFlag = MutableLiveData<Boolean>(false)

    /**
     * 发送验证码后进入60秒冷却期
     */
    var countDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (countDownTimerFlag.value == true) {
                cancel()
                this.onFinish()
            } else {
                sendCodeIsEnabled.value = false
                countdown.value =
                    "已发送  ${(millisUntilFinished / 1000).toInt()}"
            }
        }

        override fun onFinish() {
            sendCodeIsEnabled.value = true
            cancel()
            countdown.value =
                "发送验证码";
        }
    }


    /**
     * API
     */
    var sendVerificationCodeLiveData = MutableLiveData<SendVerificationCodeBean>()
    var loginLiveData = MutableLiveData<LoginBean>()
    var registerLiveData = MutableLiveData<RegisterBean>()


    /**
     * 发送验证码
     */
    fun sendVerificationCodeRequest(phone: String) {
        viewModelScope.launch {
            sendVerificationCodeLiveData.value = ApiRepository().sendVerificationCodeRequest(phone)
        }
    }

    /**
     * 登录
     */
    fun loginRequest(
        captcha: String,
        phone: String,
        countrycode: String = "86",
        context: Context
    ) {
        viewModelScope.launch {
            try {
                loginLiveData.value = ApiRepository().loginRequest(captcha, phone, countrycode)
            } catch (e: Exception) {
                PassingOnData.errorBean.message.showToast(context)
            }
        }
    }

    /**
     * 注册
     */
    fun registerRequest(
        captcha: String,
        phone: String,
        password: String,
        nickname: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                registerLiveData.value =
                    ApiRepository().registerRequest(captcha, phone, password, nickname)
            } catch (e: Exception) {
                PassingOnData.errorBean.message.showToast(context)
            }
        }
    }

    /**
     * 发送验证码
     */
    fun startCountDown(
        context: Context
    ) {
        if (phoneEdit.value?.isNull() == true) {
            "请输入手机号".showToast(context)
            return
        }

        if (!regexPhone(phoneEdit.value!!)) {
            "请输入合法的手机号".showToast(context)
            return
        }

        sendVerificationCodeRequest(phoneEdit.value!!)
    }

    /**
     * 设置登录事件
     */
    fun loginButton(context: Context, editText: EditText) {

        if (checkBoxIsChecked.value == false) {
            "请先阅读并勾选同意用户协议和隐私政策".showToast(context)
            return
        }


        val phone = phoneEdit.value
        val captcha = verificationCodeEdit.value

        if (!phone.let { regexPhone(it!!) }) {
            "请输入合法的手机号".showToast(context)
            return
        }

        if (captcha!!.isNull()) {
            "请输入验证码".showToast(context)
            return
        }
        if (editText.visibility == View.INVISIBLE) {
            loginRequest(captcha, phone!!, context = context)
        } else {
            val password = passwordEdit.value
            val username = usernameEdit.value
            if (password!!.isNull()) {
                "请输入密码".showToast(context)
                return
            }

            if (username!!.isNull()) {
                "请输入昵称".showToast(context)
                return
            }

            if (password.length < 6) {
                "密码不能少于6位".showToast(context)
                return
            }
            registerRequest(captcha, phone!!, password, username, context)
        }
    }

    /**
     * 判断是否开启Button
     */
    fun isButtonEnabled(editText: EditText, text: CharSequence, number: Int) {
        when (number) {
            0 -> phoneEdit.value = text.toString()
            1 -> verificationCodeEdit.value = text.toString()
            2 -> passwordEdit.value = text.toString()
            3 -> usernameEdit.value = text.toString()
        }
        val phone = phoneEdit.value
        val captcha = verificationCodeEdit.value
        val password = passwordEdit.value
        val username = usernameEdit.value
        buttonIsEnabled.value = false

        if(number == 0){
            sendCodeIsEnabled.value = phone!!.length == 11
        }

        if (editText.visibility == View.INVISIBLE) {
            buttonIsEnabled.value =
                phone!!.length == 11 && captcha!!.length >= 4
        } else {
            buttonIsEnabled.value = (phone!!.length == 11
                    && captcha!!.length >= 4
                    && password!!.length >= 6
                    && username!!.isNotEmpty())

            (phone!!.length == 11
                    && captcha!!.length >= 4
                    && password!!.length >= 6
                    && username!!.isNotEmpty())
                .showLog()
        }
    }


    /**
     * 设置用户协议
     */
    fun getUserAgreement(textView:TextView,context:Context):SpannableString {
        val spannableString = SpannableString("我已阅读并同意用户协议与隐私政策")

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                context?.let { "用户协议".showToast(it) }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                context?.let { "隐私政策".showToast(it) }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            clickableSpan,
            7,
            11,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        spannableString.setSpan(
            clickableSpan2,
            12,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )


        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
        return spannableString
    }
}


