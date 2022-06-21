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
        @Query("id") id: Long,
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): SongMusicBean

    /**
     * 获取歌词
     */
    @GET("/lyric")
    suspend fun lyricRequest(@Query("id") id: Long): LyricBean


    /**
     * 获取轮播图
     */
    @GET("/banner")
    suspend fun homeBannerRequest(@Query("type") type: Int): HomeBannerBean


    /**
     * 获取推荐歌单
     */
    @GET("/personalized")
    suspend fun recommendedPlayListRequest(@Query("limit") limit: Int): RecommendedPlayList

    /**
     * 获取每日推荐歌单
     */
    @GET("/recommend/resource")
    suspend fun myRecommendedPlayListRequest(
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): MyRecommendedPlayListBean

    /**
     * 获取每日推荐歌曲
     */
    @GET("/recommend/songs")
    suspend fun myPlayListRequest(
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): MyPlaySongListBean

    /**
     * 获取歌单详情
     */
    @GET("/playlist/detail")
    suspend fun playListDetailsRequest(
        @Query("id") id:Long,
        @Query("s") s:Int = 1,
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): PlayListDetailsBean


    /**
     * 获取歌单所有歌曲
     */
    @GET("/playlist/track/all")
    suspend fun playListSongRequest(
        @Query("id") id:Long,
        @Query("limit") limit:Int = 30,
        @Query("offset") offset:Int = 0,
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): PlayListSongBean

    /**
     * 获取用户详情
     */
    @GET("/user/detail")
    suspend fun userRequest(
        @Query("uid") uid: Int = KV.decodeInt(
            MMKVData.U_ID
        )
    ): UserBean

    /**
     * 获取用户歌单
     */
    @GET("/user/playlist")
    suspend fun userSongListRequest(
        @Query("uid") uid: Int = KV.decodeInt(
            MMKVData.U_ID
        ),
        @Query("cookie") cookie: String = KV.decodeString(
            MMKVData.COOKIE,
            ""
        )
    ): UserSongListBean


}