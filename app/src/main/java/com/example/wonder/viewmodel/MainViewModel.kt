package com.example.wonder.viewmodel

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonder.bean.*
import com.example.wonder.repository.ApiRepository
import com.example.wonder.utils.isNet
import com.example.wonder.utils.isNetCookie
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //API
    var likeMusicListLiveData = MutableLiveData<LikeMusicListBean>()
    var songDetailsLiveData = MutableLiveData<SongDetailsBean>()
    var songMusicLiveData = MutableLiveData<SongMusicBean>()
    var lyricLiveData = MutableLiveData<LyricBean>()

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
        isNetCookie(context) {
            viewModelScope.launch {
                try {
                    songDetailsLiveData.value = ApiRepository().songDetailsRequest(ids)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun songUrlRequest(id: Int, context: Context) {
        isNetCookie(context) {
            viewModelScope.launch {
                try {
                    songMusicLiveData.value = ApiRepository().songUrlRequest(id)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun lyricRequest(id: Int, context: Context) {
        isNet(context) {
            viewModelScope.launch {
                try {
                    lyricLiveData.value = ApiRepository().lyricRequest(id)
                } catch (e: Exception) {

                }
            }
        }
    }






}