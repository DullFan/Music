package com.example.wonder.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.lifecycle.LiveData
import com.example.wonder.bean.MusicListBean
import com.example.wonder.utils.LiveDataBus
import com.example.wonder.utils.LiveDataBusKey
import com.example.wonder.utils.LiveDataBusKey.MUSIC_IS_FLAG
import com.example.wonder.utils.PassingOnData.oneInFlag
import com.example.wonder.utils.showLog


class MediaService : Service() {
    val mMediaPlayer = MediaPlayer()
    val myBinder = MyBinder()
    var musicUrl = ""
    var musicId = 0
    var musicList = ArrayList<MusicListBean>()

    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    inner class MyBinder : Binder() {

        /**
         * 播放歌曲
         */
        @Synchronized
        fun playMusic() {
            //如果还没开始播放，就播放
            if (!mMediaPlayer.isPlaying) {
                LiveDataBus.with(MUSIC_IS_FLAG, Boolean::class.java).value = true
                mMediaPlayer.start()
            }
        }

        /**
         * 暂停歌曲
         */
        @Synchronized
        fun pauseMusic() {
            //暂停播放
            if (mMediaPlayer.isPlaying) {
                LiveDataBus.with(MUSIC_IS_FLAG, Boolean::class.java).value = false
                mMediaPlayer.pause()
            }
        }

        /**
         * 播放指定音乐
         */
        @Synchronized
        fun designatedMusic(url: String) {
            Handler(Looper.myLooper()!!).post {
                try {
                    mMediaPlayer.reset()
                    mMediaPlayer.setDataSource(url)
                    mMediaPlayer.prepare()
                    if (!oneInFlag) {
                        mMediaPlayer.start();
                        LiveDataBus.with(MUSIC_IS_FLAG, Boolean::class.java).value = true
                    } else {
                        LiveDataBus.with(MUSIC_IS_FLAG, Boolean::class.java).value = false
                    }
                } catch (e: Exception) {
                    Handler(Looper.myLooper()!!).post {
                        mMediaPlayer.reset()
                        mMediaPlayer.setDataSource(url)
                        mMediaPlayer.prepare()
                        mMediaPlayer.start();
                    }
                }
            }
        }

        /**
         * 关闭播放器
         */
        @Synchronized
        fun closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop()
                mMediaPlayer.release()
            }
        }

        /**
         * 获取歌曲长度
         */
        @Synchronized
        fun getProgress(): Int {
            return mMediaPlayer.duration
        }

        /**
         * 获取播放位置
         */
        fun getPlayPosition(): Int {
            return mMediaPlayer.currentPosition
        }

        /**
         * 播放指定位置
         */
        fun seekToPosition(msec: Int) {
            mMediaPlayer.seekTo(msec)
        }

        fun getMediaPlayer(): MediaPlayer {
            return mMediaPlayer
        }
    }
}