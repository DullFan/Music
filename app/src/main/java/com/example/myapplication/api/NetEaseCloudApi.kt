package com.example.myapplication.api

import com.example.myapplication.bean.RegisterBean
import com.example.myapplication.bean.VerifyTheVerificationCodeBean
import com.example.myapplication.bean.LoginBean
import com.example.myapplication.bean.SendVerificationCodeBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetEaseCloudApi {

    /**
     * 发送验证码
     */
    @POST("/captcha/sent")
    @FormUrlEncoded
    suspend fun sendVerificationCodeRequest(@Field("phone") phone: String): SendVerificationCodeBean

    /**
     *登录
     */
    @POST("/login/cellphone")
    @FormUrlEncoded
    suspend fun loginRequest(
        @Field("captcha") captcha: String,
        @Field("phone") phone: String,
        @Field("countrycode") countrycode: String
    ): LoginBean

    /**
     * 验证验证码
     */
    @POST("/captcha/verify")
    @FormUrlEncoded
    suspend fun verifyTheVerificationCodeRequest(
        @Field("captcha") captcha: String,
        @Field("phone") phone: String
    ): VerifyTheVerificationCodeBean

    /**
     * 注册
     */
    @POST("/register/cellphone")
    @FormUrlEncoded
    suspend fun registerRequest(
        @Field("captcha") captcha: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("nickname") nickname: String
    ): RegisterBean
}