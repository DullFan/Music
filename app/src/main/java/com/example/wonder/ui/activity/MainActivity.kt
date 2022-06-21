package com.example.wonder.ui.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wonder.R
import com.example.wonder.adapter.BaseFragmentStateAdapter
import com.example.wonder.base.BaseActivity
import com.example.wonder.databinding.ActivityMainBinding
import com.example.wonder.service.MediaService
import com.example.wonder.ui.fragment.CommunityFragment
import com.example.wonder.ui.fragment.MusicFragment
import com.example.wonder.ui.fragment.MyFragment
import com.example.wonder.utils.*
import com.example.wonder.viewmodel.MainViewModel
import kotlin.math.log


class MainActivity : BaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    val intentService by lazy {
        Intent(
            this,
            MediaService::class.java
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    /**
     * 判定服务
     */
    lateinit var mBinder: MediaService.MyBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initService()
        initBottomOnCLick()
        initDataBinding()
        initAnim()

        //初始化播放模式
        LiveDataBus.with(LiveDataBusKey.MUSIC_PLAY_MODE, Int::class.java).value = 0
        activityMainBinding.mainViewPager2.offscreenPageLimit = 2
    }

    /**
     * 设置动画效果
     */
    private fun initAnim() {
        //设置动画
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
            activityMainBinding.mainMusicImg,
            "rotation", 0f, 360f
        )

        objectAnimator.duration = 20000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.repeatMode = ValueAnimator.RESTART
        objectAnimator.interpolator = LinearInterpolator()
        var mCurrentPlayTime = 0L

        LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).observe(this) {
            if (it) {
                objectAnimator.start()
                objectAnimator.currentPlayTime = mCurrentPlayTime
                mCurrentPlayTime = 0L
            } else {
                //保存动画当前执行的时间
                mCurrentPlayTime = objectAnimator.currentPlayTime
                objectAnimator.cancel()
            }
        }

        //设置进度条
        LiveDataBus.with(LiveDataBusKey.PLAYPROGRESS, Int::class.java).observe(this) {
            activityMainBinding.mainMusicRoundProgress.setProgress(it)
        }

        LiveDataBus.with(LiveDataBusKey.MAX_PROGRESS, Int::class.java).observe(this) {
            activityMainBinding.mainMusicRoundProgress.setMax(it)
        }
    }

    /**
     * 启动服务
     */
    private fun initService() {
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mBinder = service as MediaService.MyBinder
                LiveDataBus.with(LiveDataBusKey.BINDER, MediaService.MyBinder::class.java).value =
                    mBinder
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }
        }

        //绑定服务
        if (!isServiceRunning(this, "MediaService")) {
            startService(intentService)
            bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
        }
    }


    /**
     * DataBinding 传递参数
     */
    private fun initDataBinding() {
        val baseFragmentStateAdapter = BaseFragmentStateAdapter(
            this,
            mutableListOf<Fragment>(CommunityFragment(), MusicFragment(), MyFragment())
        )
        activityMainBinding.mainViewPager2.adapter = baseFragmentStateAdapter
        activityMainBinding.mainMusicImg.visibility = View.GONE
        activityMainBinding.mainMusicRoundProgress.visibility = View.GONE
        activityMainBinding.mainMusicPlay.visibility = View.VISIBLE

        activityMainBinding.mainViewPager2.setCurrentItem(1, false)

        //实例化配置项对应的类
        val options = RequestOptions()
        //设置超时
        options.timeout(15 * 1000)
        //加载图片时显示的图片
        options.placeholder(R.drawable.logo)
        //图片加载失败时显示的图片
        options.error(R.drawable.logo)

        LiveDataBus.with(LiveDataBusKey.PIC_URL, String::class.java).observe(this) {
            Glide.with(this).load(it)
                .apply(options)
                .into(activityMainBinding.mainMusicImg)
        }
    }

    /**
     * 初始化
     */
    private fun initBottomOnCLick() {
        //设置ViewPager2不可滑动
        activityMainBinding.mainViewPager2.isUserInputEnabled = false

        activityMainBinding.mainItem1.setOnClickListener {

            if (activityMainBinding.mainViewPager2.currentItem != 0) {
                activityMainBinding.mainItemText1.setTextColor(resources.getColor(R.color.purple_500))

                activityMainBinding.mainItemImg1.setImageResource(R.drawable.bottom_main_menu_item1_select)
                activityMainBinding.mainItemImg2.setImageResource(R.drawable.bottom_main_menu_item3)
                activityMainBinding.mainItemText2.setTextColor(resources.getColor(R.color.grey_text_default))

                activityMainBinding.mainViewPager2.setCurrentItem(0, false)

                activityMainBinding.mainMusicPlay.visibility = View.GONE
                activityMainBinding.mainMusicImg.visibility = View.VISIBLE
                activityMainBinding.mainMusicRoundProgress.visibility = View.VISIBLE
            }

        }


        activityMainBinding.mainItem2.setOnClickListener {
            if (activityMainBinding.mainViewPager2.currentItem != 2) {
                activityMainBinding.mainItemText2.setTextColor(resources.getColor(R.color.purple_500))

                activityMainBinding.mainItemText1.setTextColor(resources.getColor(R.color.grey_text_default))

                activityMainBinding.mainItemImg2.setImageResource(R.drawable.bottom_main_menu_item3_select)
                activityMainBinding.mainItemImg1.setImageResource(R.drawable.bottom_main_menu_item1)

                activityMainBinding.mainViewPager2.setCurrentItem(2, false)

                activityMainBinding.mainMusicPlay.visibility = View.GONE
                activityMainBinding.mainMusicImg.visibility = View.VISIBLE
                activityMainBinding.mainMusicRoundProgress.visibility = View.VISIBLE
            }
        }

        activityMainBinding.mainMusicImg.setOnClickListener {
            if (activityMainBinding.mainViewPager2.currentItem != 1) {
                activityMainBinding.mainMusicPlay.visibility = View.VISIBLE
                activityMainBinding.mainViewPager2.setCurrentItem(1, false)
                activityMainBinding.mainMusicImg.visibility = View.GONE
                activityMainBinding.mainMusicRoundProgress.visibility = View.GONE

                activityMainBinding.mainItemText1.setTextColor(resources.getColor(R.color.grey_text_default))
                activityMainBinding.mainItemImg1.setImageResource(R.drawable.bottom_main_menu_item1)
                activityMainBinding.mainItemImg2.setImageResource(R.drawable.bottom_main_menu_item3)
                activityMainBinding.mainItemText2.setTextColor(resources.getColor(R.color.grey_text_default))
            }
        }

        activityMainBinding.mainMusicPlay.setOnClickListener {
            if (LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).value == true) {
                activityMainBinding.mainMusicPlay.setImageResource(R.drawable.lock_screen_play)
                mBinder.pauseMusic()
            } else {
                PassingOnData.oneInFlag = false
                activityMainBinding.mainMusicPlay.setImageResource(R.drawable.lock_screen_pause)
                mBinder.playMusic()
            }
        }

        LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).observe(this) {
            if (it) {
                activityMainBinding.mainMusicPlay.setImageResource(R.drawable.lock_screen_pause)
            } else {
                activityMainBinding.mainMusicPlay.setImageResource(R.drawable.lock_screen_play)
            }
        }
    }
}