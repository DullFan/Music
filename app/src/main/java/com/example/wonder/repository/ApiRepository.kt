package com.example.wonder.repository

import com.example.wonder.api.NetEaseCloudApi
import com.example.wonder.bean.*
import com.example.wonder.entity.APiClient
import retrofit2.http.Query

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

    suspend fun likeMusicListRequest(
        uId: Int
    ) : LikeMusicListBean {
        return getApiClient().likeMusicListRequest(uId)
    }

    suspend fun songDetailsRequest(
        ids: String
    ) : SongDetailsBean {
        return getApiClient().songDetailsRequest(ids)
    }

    suspend fun songUrlRequest(
        id: Long
    ) : SongMusicBean {
        return getApiClient().songUrlRequest(id)
    }

    suspend fun lyricRequest(
        id: Long
    ) : LyricBean {
        return getApiClient().lyricRequest(id)
    }

    suspend fun homeBannerRequest(
        type: Int
    ) : HomeBannerBean {
        return getApiClient().homeBannerRequest(type)
    }

    suspend fun recommendedPlayListRequest(
        limit: Int
    ) : RecommendedPlayList{
        return getApiClient().recommendedPlayListRequest(limit)
    }


    suspend fun myPlayListRequest(
    ) : MyPlaySongListBean{
        return getApiClient().myPlayListRequest()
    }


    suspend fun myRecommendedPlayListRequest(
    ) : MyRecommendedPlayListBean{
        return getApiClient().myRecommendedPlayListRequest()
    }

    suspend fun playListDetailsRequest(
        id: Long
    ) : PlayListDetailsBean{
        return getApiClient().playListDetailsRequest(id)
    }

    suspend fun playListSongRequest(
        id: Long
    ) : PlayListSongBean{
        return getApiClient().playListSongRequest(id)
    }

    fun getApiClient(): NetEaseCloudApi {
        return APiClient.instance.instanceRetrofit(NetEaseCloudApi::class.java)
    }



}