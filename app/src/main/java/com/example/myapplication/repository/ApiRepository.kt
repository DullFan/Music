package com.example.myapplication.repository

import com.example.myapplication.api.NetEaseCloudApi
import com.example.myapplication.bean.RegisterBean
import com.example.myapplication.bean.VerifyTheVerificationCodeBean
import com.example.myapplication.bean.LoginBean
import com.example.myapplication.bean.SendVerificationCodeBean
import com.example.myapplication.entity.APiClient

class ApiRepository {


    suspend fun sendVerificationCodeRequest(phone: String): SendVerificationCodeBean {
        return getApiClient().sendVerificationCodeRequest(phone)
    }

    suspend fun loginRequest(
        captcha: String,
        phone: String,
        countrycode: String
    ): LoginBean {
        return getApiClient().loginRequest(captcha, phone, countrycode)
    }

    suspend fun verifyTheVerificationCodeRequest(
        captcha: String,
        phone: String
    ): VerifyTheVerificationCodeBean {
        return getApiClient().verifyTheVerificationCodeRequest(captcha, phone)
    }

    suspend fun registerRequest(
        captcha: String,
        phone: String,
        password: String,
        nickname: String
    ) : RegisterBean {
        return getApiClient().registerRequest(captcha,phone,password,nickname)
    }

    fun getApiClient(): NetEaseCloudApi {
        return APiClient.instance.instanceRetrofit(NetEaseCloudApi::class.java)
    }
}