package com.example.wonder.viewmodel

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonder.bean.LikeMusicListBean
import com.example.wonder.bean.MusicListBean
import com.example.wonder.bean.SongDetailsBean
import com.example.wonder.bean.SongMusicBean
import com.example.wonder.repository.ApiRepository
import com.example.wonder.utils.isNetCookie
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //API
    var likeMusicListLiveData = MutableLiveData<LikeMusicListBean>()
    var songDetailsLiveData = MutableLiveData<SongDetailsBean>()
    var songMusicLiveData = MutableLiveData<SongMusicBean>()

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






}