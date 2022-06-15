package com.example.wonder.utils

object LiveDataBusKey {
    /**
     * 当前播放音乐的图片
     */
    val PIC_URL = "picUrl"

    /**
     * 当前播放音乐的URL
     */
    val MUSIC_URL = "musicUrl"

    /**
     * 当前播放音乐的Id
     */
    val MUSIC_ID = "musicId"

    /**
     * 当前播放音乐的列表
     */
    val MUSIC_LIST = "musicList"

    /**
     * 当前绑定服务的音乐管理类
     */
    val BINDER = "mBinder"

    /**
     * 当前音乐总长度
     */
    val MAX_PROGRESS = "Progress"

    /**
     * 当前音乐播放长度
     */
    val PLAYPROGRESS = "PlayProgress"

    /**
     * 当前播放列表的Position
     */
    val MUSIC_LIST_POSITION = "musicListPosition"

    /**
     * 当前音乐是否在播放
     */
    val MUSIC_IS_FLAG = "music_is_flag"

    /**
     * 在拖动SeekBar时暂停线程
     */
    val MUSIC_SEEK_BAR = "music_seek_bar"


    /**
     * 音乐播放模式
     * 1 --> 顺序播放
     * 2 --> 单曲循环
     * 3 --> 随机播放
     */
    val MUSIC_PLAY_MODE = "MUSIC_PLAY_MODE"

    /**
     * 当前选中的歌单Id
     */
    val PLAYLIST_DETAILS = "playlistDetails"

    /**
     * 存放 Rv适配器
     */
    val BASE_ADAPTER = "base_adapter"

    /**
     * 存放 ViewPager2
     */
    val VIEW_PAGER2 = "VIEW_PAGER2"

    /**
     * 存放 每日30首数据
     */
    val RECOMMENDATION = "RECOMMENDATION"

}