package com.example.wonder.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonder.bean.VerifyTheVerificationCodeBean
import com.example.wonder.repository.ApiRepository
import com.example.wonder.utils.PassingOnData
import com.example.wonder.utils.isNet
import com.example.wonder.utils.showToast
import kotlinx.coroutines.launch

class UtilsViewModel : ViewModel() {
    var verifyTheVerificationCodeLiveData = MutableLiveData<VerifyTheVerificationCodeBean>()

    lateinit var baseActivity: Activity

    /**
     * 验证验证码是否可用
     */
    fun verifyTheVerificationCodeRequest(
        captcha: String,
        phone: String,
        context: Context
    ) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    verifyTheVerificationCodeLiveData.value =
                        ApiRepository().verifyTheVerificationCodeRequest(captcha, phone)
                } catch (e: Exception) {
                    PassingOnData.errorBean.message.showToast(context)
                }
            }
        }
    }


    fun onClickFinish() {
        if (::baseActivity.isInitialized) {
            baseActivity.finish()
        }
    }
}