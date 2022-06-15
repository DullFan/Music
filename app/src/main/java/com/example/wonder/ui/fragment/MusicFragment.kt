package com.example.wonder.ui.fragment

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dirror.lyricviewx.LyricUtil.formatTime
import com.dirror.lyricviewx.LyricViewX
import com.example.wonder.R
import com.example.wonder.R.id.design_bottom_sheet
import com.example.wonder.base.BaseFragment
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.example.wonder.base.RecyclerViewViewHolder
import com.example.wonder.bean.BottomSettingBean
import com.example.wonder.bean.MusicListBean
import com.example.wonder.databinding.*
import com.example.wonder.service.MediaService
import com.example.wonder.utils.*
import com.example.wonder.utils.LiveDataBusKey.BASE_ADAPTER
import com.example.wonder.utils.LiveDataBusKey.MUSIC_LIST
import com.example.wonder.utils.LiveDataBusKey.MUSIC_URL
import com.example.wonder.utils.LiveDataBusKey.VIEW_PAGER2
import com.example.wonder.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MusicFragment : BaseFragment() {
    /**
     *  当前播放列表
     */
    private val mutableList = ArrayList<MusicListBean>()

    private val viewBinding: FragmentMusicBinding by lazy {
        FragmentMusicBinding.inflate(layoutInflater)
    }

    /**
     * 当前中的Item的SeekBar
     */
    lateinit var musicSeekBar: SeekBar

    /**
     * 判定服务
     */
    lateinit var mBinder: MediaService.MyBinder

    /**
     * 歌词控件
     */
    lateinit var musicAlbumLyric: LyricViewX

    /**
     * 防止第一次进入ViewPager2选中时回调
     */
    var viewPager2Flag = false

    /**
     * 当前Item的DataBinding
     */
    lateinit var itemDataBinding: MusicRvItemBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    /**
     * 监听数据变化进度条数据变化，每秒监听一次
     */
    private val mHandler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            if (LiveDataBus.with(
                    LiveDataBusKey.MUSIC_IS_FLAG,
                    Boolean::class.java
                ).value == true && LiveDataBus.with(
                    LiveDataBusKey.MUSIC_SEEK_BAR,
                    Boolean::class.java
                ).value == true
            ) {
                var playPosition = mBinder.getPlayPosition()
                if (playPosition == 0) {
                    playPosition = 1;
                }
                LiveDataBus.with(LiveDataBusKey.PLAYPROGRESS, Int::class.java).value = playPosition
            }
            sendEmptyMessageDelayed(1, 1000)
        }
    }

    /**
     * ViewPager2适配器
     */
    lateinit var adapter: BaseRecyclerViewAdapter<MusicListBean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //初始化服务绑定
        LiveDataBus.with(LiveDataBusKey.BINDER, MediaService.MyBinder::class.java)
            .observe(viewLifecycleOwner) {
                mBinder = it
            }
        LiveDataBus.with(
            LiveDataBusKey.MUSIC_SEEK_BAR,
            Boolean::class.java
        ).value = true

        initLikeMusicList()

        initListener()

        return viewBinding.root
    }

    /**
     * 设置底部弹窗
     */
    private fun initBottomDialog(homeMore: ImageView) {

        val bottomDialogDataBinding: DialogBottomMusicListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_bottom_music_list,
            null,
            false
        )

        val bottomSettingList = mutableListOf<BottomSettingBean>()
        bottomSettingList.add(BottomSettingBean(R.drawable.play_mode_icon_1, "顺序播放"))
        bottomSettingList.add(BottomSettingBean(R.drawable.timed_shutdown, "定时关闭"))
        bottomSettingList.add(BottomSettingBean(R.drawable.add_song_list, "添加歌单"))

        val bottomDialogHORIZONTALVerAdapter = object : BaseRecyclerViewAdapter<BottomSettingBean>(
            R.layout.item_dialog_bottom_item1,
            bottomSettingList
        ) {
            override fun onBind(
                rvDataBinding: ViewDataBinding,
                data: BottomSettingBean,
                position: Int
            ) {
                rvDataBinding as ItemDialogBottomItem1Binding
                rvDataBinding.itemDialogBottomImg.setImageResource(data.img)
                rvDataBinding.itemDialogBottomText.text = data.title
                rvDataBinding.root.setOnClickListener {
                    if (position == 0) {
                        val platMode =
                            LiveDataBus.with(LiveDataBusKey.MUSIC_PLAY_MODE, Int::class.java).value
                        when (platMode) {
                            0 -> {
                                LiveDataBus.with(
                                    LiveDataBusKey.MUSIC_PLAY_MODE,
                                    Int::class.java
                                ).value = 1
                                rvDataBinding.itemDialogBottomImg.setImageResource(R.drawable.play_mode_icon_2)
                                rvDataBinding.itemDialogBottomText.text = "单曲循环"
                            }
                            1 -> {
                                LiveDataBus.with(
                                    LiveDataBusKey.MUSIC_PLAY_MODE,
                                    Int::class.java
                                ).value = 2
                                rvDataBinding.itemDialogBottomImg.setImageResource(R.drawable.play_mode_icon_3)
                                rvDataBinding.itemDialogBottomText.text = "随机播放"
                            }
                            2 -> {
                                LiveDataBus.with(
                                    LiveDataBusKey.MUSIC_PLAY_MODE,
                                    Int::class.java
                                ).value = 0
                                rvDataBinding.itemDialogBottomImg.setImageResource(R.drawable.play_mode_icon_1)
                                rvDataBinding.itemDialogBottomText.text = "顺序播放"
                            }
                        }
                    }
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        bottomDialogDataBinding.dialogBottomMusicListSettingRv.layoutManager = linearLayoutManager
        bottomDialogDataBinding.dialogBottomMusicListSettingRv.adapter =
            bottomDialogHORIZONTALVerAdapter

        val bottomDialogRvListAdapter = object : BaseRecyclerViewAdapter<MusicListBean>(
            R.layout.item_dialog_bottom_item2,
            LiveDataBus.with(MUSIC_LIST, ArrayList::class.java).value as List<MusicListBean>
        ) {

            override fun onBind(
                rvDataBinding: ViewDataBinding,
                data: MusicListBean,
                position: Int
            ) {
                rvDataBinding as ItemDialogBottomItem2Binding
                rvDataBinding.data = data
                if (position == index) {
                    rvDataBinding.itemBottomDialogLottie.visibility = View.VISIBLE
                    rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.white_1a))
                } else {
                    rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.black))
                    rvDataBinding.itemBottomDialogLottie.visibility = View.INVISIBLE
                }
                rvDataBinding.root.setOnClickListener {
                    if (index != position) {
                        index = position
                        adapter.index = position
                        viewBinding.musicVp2.currentItem = position
                    }
                }

                rvDataBinding.itemBottomDialogDel.setOnClickListener {
                    val valueList = LiveDataBus.with(
                        MUSIC_LIST,
                        ArrayList::class.java
                    ).value as ArrayList<MusicListBean>
                    if (dataList.size == 1) {
                        "在删就没了".showLog()
                        return@setOnClickListener
                    }
                    valueList.removeAt(position)
                    LiveDataBus.with(MUSIC_LIST, ArrayList::class.java).value = valueList
                    adapter.dataList = valueList
                    dataList = valueList
                }
            }
        }

        LiveDataBus.with(
            LiveDataBusKey.MUSIC_LIST_POSITION,
            Int::class.java
        ).observe(viewLifecycleOwner) {
            bottomDialogRvListAdapter.index = it
        }

        bottomDialogDataBinding.dialogBottomMusicRv.layoutManager =
            LinearLayoutManager(requireContext())
        bottomDialogDataBinding.dialogBottomMusicRv.adapter = bottomDialogRvListAdapter

        val bottomDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)


        bottomDialog.setContentView(bottomDialogDataBinding.root)

        bottomDialog.delegate.findViewById<FrameLayout>(design_bottom_sheet)
            ?.setBackgroundColor(resources.getColor(android.R.color.transparent))

        homeMore.setOnClickListener {
            bottomDialog.show()
        }

        bottomDialogDataBinding.dialogBottomMusicDismiss.setOnClickListener {
            bottomDialog.dismiss()
        }
    }

    /**
     * 获取喜欢音乐列表
     */
    private fun initLikeMusicList() {
        //获取喜欢音乐的列表
        viewModel.likeMusicListRequest(KV.decodeInt(MMKVData.U_ID), requireContext())

        viewModel.likeMusicListLiveData.observe(viewLifecycleOwner) {
            val ids = it.ids.joinToString(separator = ",") { string -> "$string" }
            viewModel.songDetailsRequest(ids, requireContext())
        }


        viewModel.songDetailsLiveData.observe(viewLifecycleOwner) { it ->
            mutableList.clear()

            it.songs.forEach {
                mutableList.add(
                    MusicListBean(
                        it.id,
                        it.al.picUrl,
                        it.dt,
                        it.name,
                        //TODO 将歌手的名字分开
                        it.ar.joinToString(separator = "、") { item -> item.name },
                        it.fee
                    )
                )
            }

            LiveDataBus.with(MUSIC_LIST, ArrayList::class.java).value = mutableList

            initAdapter()
        }
    }

    /**
     * 设置适配器
     */
    private fun initAdapter() {

        adapter =
            object : BaseRecyclerViewAdapter<MusicListBean>(R.layout.music_rv_item, mutableList) {
                override fun onBind(
                    rvDataBinding: ViewDataBinding,
                    data: MusicListBean,
                    position: Int
                ) {

                    rvDataBinding as MusicRvItemBinding
                    rvDataBinding.data = data
                    //当前选中判断
                    if (index == position) {
                        itemDataBinding = rvDataBinding
                        LiveDataBus.with(
                            LiveDataBusKey.MUSIC_LIST_POSITION,
                            Int::class.java
                        ).value = position

                        LiveDataBus.with(LiveDataBusKey.PIC_URL, String::class.java).value =
                            data.picUrl
                        LiveDataBus.with(LiveDataBusKey.MUSIC_ID, Long::class.java).value =
                            data.id

                        viewModel.songUrlRequest(data.id, requireContext())

                        LiveDataBus.with(LiveDataBusKey.MAX_PROGRESS, Int::class.java).value =
                            data.dt
                        musicSeekBar = rvDataBinding.musicSeekBar
                        initSeekBarListener()
                        initBottomDialog(rvDataBinding.homeMore)
                        musicAlbumLyric = rvDataBinding.musicAlbumLyric

                        //获取歌词
                        viewModel.lyricRequest(data.id, requireContext())
                    }
                }
            }

        LiveDataBus.with(BASE_ADAPTER, RecyclerView.Adapter::class.java).value = adapter
        LiveDataBus.with(VIEW_PAGER2, ViewPager2::class.java).value = viewBinding.musicVp2
        viewBinding.musicVp2.adapter = adapter
        viewBinding.musicVp2.offscreenPageLimit = 2

        LiveDataBus.with(MUSIC_LIST, ArrayList::class.java).observe(viewLifecycleOwner) {
            it as ArrayList<MusicListBean>
            adapter.dataList = it
        }
    }

    private fun initSeekBarListener() {
        //设置进度条
        LiveDataBus.with(LiveDataBusKey.PLAYPROGRESS, Int::class.java)
            .observe(viewLifecycleOwner) {
                if (::musicSeekBar.isInitialized) {
                    musicSeekBar.progress = it
                }
                if (::musicAlbumLyric.isInitialized) {
                    musicAlbumLyric.updateTime(it.toLong())
                }
            }

        LiveDataBus.with(LiveDataBusKey.MAX_PROGRESS, Int::class.java)
            .observe(viewLifecycleOwner) {
                if (::musicSeekBar.isInitialized) {
                    musicSeekBar.max = it
                    if (::itemDataBinding.isInitialized) {
                        itemDataBinding.musicRvItemRight.text = formatTime(it.toLong())
                    }
                } else {
                    LiveDataBus.with(
                        LiveDataBusKey.MAX_PROGRESS,
                        Int::class.java
                    ).value = it
                }
            }

        musicSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            var uponNumber: Int = 0
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                uponNumber = progress
                // TODO 判断前后滑动还未完成
                //只在滑动时监听
                if (LiveDataBus.with(
                        LiveDataBusKey.MUSIC_SEEK_BAR,
                        Boolean::class.java
                    ).value == false
                ) {
                    itemDataBinding.musicRvItemLeft.text = formatTime(progress.toLong())
                    itemDataBinding.musicAlbumLyricTime.updateTime(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                LiveDataBus.with(
                    LiveDataBusKey.MUSIC_SEEK_BAR,
                    Boolean::class.java
                ).value = false

                if (::itemDataBinding.isInitialized) {
                    startAnimView(itemDataBinding.homeLike, true)
                    startAnimView(itemDataBinding.musicAlbumLyric, true)
                    startAnimView(itemDataBinding.homeShare, true)
                    startAnimView(itemDataBinding.homeDownload, true)
                    startAnimView(itemDataBinding.homeMore, true)
                    startAnimView(itemDataBinding.musicSongTitle, true)
                    startAnimView(itemDataBinding.musicSinger, true)

                    startAnimView(itemDataBinding.musicAlbumLyricTime, false)
                    startAnimView(itemDataBinding.musicRvItemTimeLayout, false)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mBinder.seekToPosition(seekBar!!.progress)
                LiveDataBus.with(
                    LiveDataBusKey.MUSIC_SEEK_BAR,
                    Boolean::class.java
                ).value = true

                startAnimView(itemDataBinding.homeLike, false)
                startAnimView(itemDataBinding.musicAlbumLyric, false)
                startAnimView(itemDataBinding.homeShare, false)
                startAnimView(itemDataBinding.homeDownload, false)
                startAnimView(itemDataBinding.homeMore, false)
                startAnimView(itemDataBinding.musicSongTitle, false)
                startAnimView(itemDataBinding.musicSinger, false)
                startAnimView(itemDataBinding.musicAlbumLyricTime, true)
                startAnimView(itemDataBinding.musicRvItemTimeLayout, true)

                mBinder.playMusic()
            }
        })
    }

    /**
     * 监听一首歌是否播放完成
     */
    private fun initListener() {
        viewModel.lyricLiveData.observe(viewLifecycleOwner) {
            musicAlbumLyric.apply {
                loadLyric(it.lrc.lyric)

                setNormalTextSize(50f)
                setNormalColor(resources.getColor(R.color.white_8a))
                setCurrentTextSize(70f)
                setLabel("暂无歌词")
                visibility = View.VISIBLE
            }

            if (::itemDataBinding.isInitialized) {
                itemDataBinding.musicAlbumLyricTime.apply {
                    loadLyric(it.lrc.lyric)
                    setCurrentTextSize(45f)
                    setCurrentColor(resources.getColor(R.color.white_8a))
                    setLabel("暂无歌词")
                }
            }
        }

        viewModel.songMusicLiveData.observe(viewLifecycleOwner) {
            LiveDataBus.with(MUSIC_URL, String::class.java).value = it.data[0].url
            mBinder.designatedMusic(it.data[0].url)
            mHandler.sendEmptyMessage(1)
        }


        LiveDataBus.with(LiveDataBusKey.BINDER, MediaService.MyBinder::class.java)
            .observe(viewLifecycleOwner) {
                //音乐播放结束
                mBinder.getMediaPlayer().setOnCompletionListener {
                    var position =
                        LiveDataBus.with(LiveDataBusKey.MUSIC_LIST_POSITION, Int::class.java).value

                    val playModel = LiveDataBus.with(
                        LiveDataBusKey.MUSIC_PLAY_MODE,
                        Int::class.java
                    ).value

                    when (playModel) {
                        0 -> {
                            position = if (mutableList.size - 1 == position) {
                                0
                            } else {
                                position?.plus(1)
                            }

                            viewBinding.musicVp2.currentItem = position!!

                            adapter.index = position!!
                        }

                        1 -> {
                            adapter.index = position!!
                        }

                        2 -> {
                            val size =
                                LiveDataBus.with(MUSIC_LIST, ArrayList::class.java).value!!.size
                            var first: Int = 0
                            first = (0..size).shuffled().first()
                            //确保不和上次播放的音乐一样
                            while (first == position) {
                                first = (0..size).shuffled().first()
                            }
                            viewBinding.musicVp2.currentItem = first
                            adapter.index = first
                        }
                    }
                }
            }

        viewBinding.musicVp2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (viewPager2Flag) {
                    adapter.index = position
                } else {
                    viewPager2Flag = true
                }
            }
        })
    }

    /**
     * 开启动画，true为退出，false为进入
     */
    fun startAnimView(view: View, animFlag: Boolean) {
        val animation: Animation = if (animFlag) {
            AnimationUtils.loadAnimation(requireContext(), R.anim.item_rv_item_out_anim)
        } else {
            AnimationUtils.loadAnimation(requireContext(), R.anim.item_rv_item_in_anim)
        }
        view.startAnimation(animation)
        if (animFlag) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }
}