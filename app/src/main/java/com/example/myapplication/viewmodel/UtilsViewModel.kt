package com.example.myapplication.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.RegisterBean
import com.example.myapplication.bean.VerifyTheVerificationCodeBean
import com.example.myapplication.bean.LoginBean
import com.example.myapplication.bean.SendVerificationCodeBean
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.utils.PassingOnData
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.launch

class UtilsViewModel : ViewModel() {
    var verifyTheVerificationCodeLiveData = MutableLiveData<VerifyTheVerificationCodeBean>()

    lateinit var baseActivity:Activity

    /**
     * 验证验证码是否可用
     */
    fun verifyTheVerificationCodeRequest(
        captcha: String,
        phone: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                verifyTheVerificationCodeLiveData.value =
                    ApiRepository().verifyTheVerificationCodeRequest(captcha, phone)
            } catch (e: Exception) {
                PassingOnData.errorBean.message.showToast(context)
            }
        }
    }


    fun onClickFinish(){
        if(::baseActivity.isInitialized){
            baseActivity.finish()
        }
    }
}