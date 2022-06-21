package com.example.wonder.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonder.bean.*
import com.example.wonder.repository.ApiRepository
import com.example.wonder.utils.isNet
import com.example.wonder.utils.isNetCookie
import com.example.wonder.utils.showLog
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //API
    var likeMusicListLiveData = MutableLiveData<LikeMusicListBean>()
    var songDetailsLiveData = MutableLiveData<SongDetailsBean>()
    var songMusicLiveData = MutableLiveData<SongMusicBean>()
    var lyricLiveData = MutableLiveData<LyricBean>()
    var homeBannerLiveData = MutableLiveData<HomeBannerBean>()
    var recommendedPlayListLiveData = MutableLiveData<RecommendedPlayList>()
    var myRecommendedPlayListLiveData = MutableLiveData<MyRecommendedPlayListBean>()
    var myPlayListLiveData = MutableLiveData<MyPlaySongListBean>()
    var playListDetailsLiveData = MutableLiveData<PlayListDetailsBean>()
    var playListSongLiveData = MutableLiveData<PlayListSongBean>()
    var userLiveData = MutableLiveData<UserBean>()
    var userSongListLiveData = MutableLiveData<UserSongListBean>()


    fun likeMusicListRequest(uId: Int, context: Context) {
        isNetCookie(context) {
            viewModelScope.launch {
                try {
                    likeMusicListLiveData.value = ApiRepository().likeMusicListRequest(uId)
                } catch (e: Exception) {

                }
            }
        }
    }


    fun songDetailsRequest(ids: String, context: Context) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    songDetailsLiveData.value = ApiRepository().songDetailsRequest(ids)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun songUrlRequest(id: Long, context: Context) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    songMusicLiveData.value = ApiRepository().songUrlRequest(id)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun lyricRequest(id: Long, context: Context) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    lyricLiveData.value = ApiRepository().lyricRequest(id)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun homeBannerRequest(type: Int, context: Context) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    homeBannerLiveData.value = ApiRepository().homeBannerRequest(type)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun recommendedPlayListRequest(
        limit: Int, context: Context
    ) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    recommendedPlayListLiveData.value =
                        ApiRepository().recommendedPlayListRequest(limit)
                } catch (e: Exception) {

                }
            }
        }
    }


    fun myRecommendedPlayListRequest(
        context: Context
    ) {
        isNetCookie(context) {
            viewModelScope.launch {
                try {
                    myRecommendedPlayListLiveData.value =
                        ApiRepository().myRecommendedPlayListRequest()
                } catch (e: Exception) {

                }
            }
        }
    }


    fun myPlayListRequest(
        context: Context
    ) {
        isNetCookie(context) {
            viewModelScope.launch {
                try {
                    myPlayListLiveData.value = ApiRepository().myPlayListRequest()
                } catch (e: Exception) {
                    "我来了".showLog()
                }
            }
        }
    }

    fun playListDetailsRequest(
        id: Long,
        context: Context
    ) {
        isNetCookie(context) {
            viewModelScope.launch {
                playListDetailsLiveData.value = ApiRepository().playListDetailsRequest(id)

            }
        }
    }

    fun playListSongRequest(
        id: Long,
        context: Context
    ) {
        isNetCookie(context) {
            viewModelScope.launch {
                playListSongLiveData.value = ApiRepository().playListSongRequest(id)

            }
        }

    }


    fun userRequest(
        context: Context
    ) {
        isNetCookie(context) {
            viewModelScope.launch {
                userLiveData.value = ApiRepository().userRequest()

            }
        }

    }
    fun userSongListRequest(
        context: Context
    ) {
        isNetCookie(context) {
            viewModelScope.launch {
                userSongListLiveData.value = ApiRepository().userSongListRequest()

            }
        }

    }
}