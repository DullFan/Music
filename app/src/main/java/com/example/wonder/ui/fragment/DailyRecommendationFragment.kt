package com.example.wonder.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wonder.R
import com.example.wonder.base.BaseFragment
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.example.wonder.bean.MusicListBean
import com.example.wonder.bean.RecommendedPlayListResult
import com.example.wonder.databinding.FragmentDailyRecommendationBinding
import com.example.wonder.databinding.FragmentPlaylistDetailsBinding
import com.example.wonder.databinding.ItemRvPlayListBinding
import com.example.wonder.databinding.RecommendedPlaylistItemBinding
import com.example.wonder.utils.LiveDataBus
import com.example.wonder.utils.LiveDataBusKey
import com.example.wonder.utils.LiveDataBusKey.RECOMMENDATION
import com.example.wonder.utils.PassingOnData
import com.example.wonder.utils.getNumberWanTwo
import com.example.wonder.viewmodel.MainViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

class DailyRecommendationFragment : BaseFragment() {
    lateinit var viewDataBinding: FragmentDailyRecommendationBinding
    val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
    }

    val mutableList: ArrayList<MusicListBean> by lazy {
        LiveDataBus.with(RECOMMENDATION, ArrayList::class.java).value as ArrayList<MusicListBean>
    }

    lateinit var rvAdapter: BaseRecyclerViewAdapter<MusicListBean>
    var adapterClickFlag = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentDailyRecommendationBinding.inflate(layoutInflater)
        val modeId = LiveDataBus.with(LiveDataBusKey.RECOMMENDATION_ID, Int::class.java).value

        viewDataBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                requireActivity().resources.displayMetrics.widthPixels,
                requireActivity().resources.displayMetrics.heightPixels
            )

        if (modeId == 0) {
            viewDataBinding.recommendedPlaylistItemName.text = "????????????"
            viewDataBinding.playlistDetailsDetails.text =
                "?????????????????????: ????????????${mutableList[0].songTitle}???${mutableList[1].songTitle}???${mutableList[2].songTitle}?????????????????????????????????"
        } else if (modeId == 1) {
            viewDataBinding.recommendedPlaylistItemName.text = "??????????????????"
        }

        Glide.with(this).load(mutableList[0].picUrl)
            .into(viewDataBinding.recommendedPlaylistItemImg)
        Glide.with(this).load(mutableList[0].picUrl).apply(
            RequestOptions.bitmapTransform(
                BlurTransformation(100, 4)
            )
        ).into(viewDataBinding.playlistDetailsImg)
        viewDataBinding.playlistDetailsPlayText.setOnClickListener {
            onClick()
        }

        viewDataBinding.playlistDetailsPlay.setOnClickListener {
            onClick()
        }
        rvAdapter =
            object : BaseRecyclerViewAdapter<MusicListBean>(
                R.layout.item_rv_play_list,
                mutableList
            ) {
                override fun onBind(
                    rvDataBinding: ViewDataBinding,
                    data: MusicListBean,
                    position: Int
                ) {
                    rvDataBinding as ItemRvPlayListBinding
                    rvDataBinding.data = data

                    if (adapterClickFlag) {
                        if (index == position) {
                            rvDataBinding.visFlag = false
                            rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.black_alpha_a1))
                        } else {
                            rvDataBinding.visFlag = true
                            rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.white))
                        }
                    } else {
                        rvDataBinding.visFlag = true
                        rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.white))
                    }

                    rvDataBinding.root.setOnClickListener {
                        index = position

                        LiveDataBus.with(
                            LiveDataBusKey.MUSIC_LIST,
                            ArrayList::class.java
                        ).value =
                            mutableList

                        LiveDataBus.with(
                            LiveDataBusKey.MUSIC_LIST_POSITION,
                            Int::class.java
                        ).value = position

                        LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).value =
                            true

                        PassingOnData.oneInFlag = false
                        rvDataBinding.visFlag = false

                        adapterClickFlag = true

                        val baseRecyclerViewAdapter = LiveDataBus.with(
                            LiveDataBusKey.BASE_ADAPTER,
                            RecyclerView.Adapter::class.java
                        ).value as BaseRecyclerViewAdapter<MusicListBean>

                        baseRecyclerViewAdapter.index = index

                        LiveDataBus.with(
                            LiveDataBusKey.VIEW_PAGER2,
                            ViewPager2::class.java
                        ).value?.currentItem =
                            index
                    }
                }
            }

        viewDataBinding.playlistDetailsPlayRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }

        return viewDataBinding.root
    }

    private fun onClick() {
        LiveDataBus.with(LiveDataBusKey.MUSIC_LIST, ArrayList::class.java).value =
            mutableList
        LiveDataBus.with(
            LiveDataBusKey.MUSIC_LIST_POSITION,
            Int::class.java
        ).value = 0

        PassingOnData.oneInFlag = false
        LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).value = true
        adapterClickFlag = true
        rvAdapter.index = 0
    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            if (enter) {
                return AnimationUtils.loadAnimation(context, R.anim.fragment_in_anim);
            } else {//??????????????????Fragmen????????????replace??????????????????????????????replace???????????????false
                return AnimationUtils.loadAnimation(context, R.anim.fragment_out_anim);
            }
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {//??????????????????????????????????????????hide???detach???
            if (enter) {//?????????replace??????????????????????????????Fragment????????????
                return AnimationUtils.loadAnimation(context, R.anim.fragment_in_anim)
            } else {//Fragment???????????????
                return AnimationUtils.loadAnimation(context, R.anim.fragment_out_anim)
            }
            return null
        }
        return null
    }
}