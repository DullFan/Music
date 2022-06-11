package com.example.wonder.api

import com.example.wonder.bean.*
import com.example.wonder.utils.KV
import com.example.wonder.utils.MMKVData
import retrofit2.http.*

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

    /**
     * 获取用户喜欢的歌曲列表
     */
    @GET("/likelist")
    suspend fun likeMusicListRequest(
        @Query("uid") uId: Int,
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): LikeMusicListBean

    /**
     * 获取歌曲详情
     */
    @GET("/song/detail")
    suspend fun songDetailsRequest(
        @Query("ids") ids: String,
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): SongDetailsBean

    /**
     * 获取音乐URL
     */
    @GET("/song/url")
    suspend fun songUrlRequest(
        @Query("id")id :Int,
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ):SongMusicBean

    /**
     * 获取歌词
     */
    @GET("/lyric")
    suspend fun lyricRequest(@Query("id")id:Int):LyricBean
}