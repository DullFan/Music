package com.example.wonder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wonder.R
import com.example.wonder.base.BaseFragment
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.example.wonder.bean.*
import com.example.wonder.databinding.FragmentCommunityBinding
import com.example.wonder.databinding.RecommendedPlaylistItemBinding
import com.example.wonder.utils.*
import com.example.wonder.utils.LiveDataBusKey.PLAYLIST_DETAILS
import com.example.wonder.utils.LiveDataBusKey.RECOMMENDATION
import com.example.wonder.utils.LiveDataBusKey.RECOMMENDATION_ID
import com.example.wonder.viewmodel.MainViewModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.RectangleIndicator

class CommunityFragment : BaseFragment() {
    lateinit var viewDataBinding: FragmentCommunityBinding

    val viewModel: MainViewModel by viewModels()

    lateinit var dailySongs: List<MyPlaySongListDailySong>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCommunityBinding.inflate(layoutInflater)

        initBanner()

        initRecommendedPlaylist()

        isCookie(requireContext()) {
            viewDataBinding.communityCardLayout2.visibility = View.VISIBLE
            viewDataBinding.communityMySongListTitle.visibility = View.VISIBLE
            viewDataBinding.communityMySongList.visibility = View.VISIBLE
            initMyRecommendedPlaylist()
            initMySongPlaylist()
        }

        return viewDataBinding.root
    }

    /**
     * 获取每日推荐歌曲
     */
    private fun initMySongPlaylist() {
        viewDataBinding.communityTodayTime.text = formatTimeYear(System.currentTimeMillis())
        viewModel.myPlayListRequest(requireContext())
        viewModel.myPlayListLiveData.observe(viewLifecycleOwner) {
            dailySongs = it.data.dailySongs
            viewDataBinding.communityTodayMusic01.text = it.data.dailySongs[0].name
            viewDataBinding.communityTodayMusic02.text = it.data.dailySongs[1].name
            viewDataBinding.communityTodayMusic03.text = it.data.dailySongs[2].name
        }



        viewDataBinding.communitySongListPlay.setOnClickListener {
            if (::dailySongs.isInitialized) {
                val mutableList = ArrayList<MusicListBean>()
                dailySongs.forEach {
                    mutableList.add(
                        MusicListBean(
                            it.id,
                            it.al.picUrl,
                            it.dt,
                            it.name,
                            it.ar.joinToString(separator = "、") { item -> item.name },
                            it.fee
                        )
                    )
                }

                PassingOnData.oneInFlag = false
                LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).value = true

                LiveDataBus.with(LiveDataBusKey.MUSIC_LIST, ArrayList::class.java).value =
                    mutableList
            }
        }

        viewDataBinding.communityCardLayout2.setOnClickListener {
            if (::dailySongs.isInitialized) {
                val mutableList = ArrayList<MusicListBean>()
                dailySongs.forEach {
                    mutableList.add(
                        MusicListBean(
                            it.id,
                            it.al.picUrl,
                            it.dt,
                            it.name,
                            it.ar.joinToString(separator = "、") { item -> item.name },
                            it.fee
                        )
                    )
                }

                LiveDataBus.with(RECOMMENDATION, ArrayList::class.java).value = mutableList
                LiveDataBus.with(RECOMMENDATION_ID, Int::class.java).value = 0
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.community_container, DailyRecommendationFragment(), null)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    /**
     * 获取每日推荐歌单
     */
    private fun initMyRecommendedPlaylist() {
        viewModel.myRecommendedPlayListRequest(requireContext())
        viewModel.myRecommendedPlayListLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.communityMySongList.apply {
                val linearLayoutManager = LinearLayoutManager(requireContext())
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = linearLayoutManager
                adapter = object : BaseRecyclerViewAdapter<MyRecommendedPlayListRecommend>(
                    R.layout.recommended_playlist_item,
                    it.recommend
                ) {
                    override fun onBind(
                        rvDataBinding: ViewDataBinding,
                        data: MyRecommendedPlayListRecommend,
                        position: Int
                    ) {
                        rvDataBinding as RecommendedPlaylistItemBinding
                        rvDataBinding.recommendedPlaylistItemPlayCount.text =
                            getNumberWanTwo(data.playcount.toString())
                        Glide.with(requireContext()).load(data.picUrl)
                            .into(rvDataBinding.recommendedPlaylistItemImg)
                        rvDataBinding.recommendedPlaylistItemName.text = data.name

                        rvDataBinding.root.setOnClickListener {
                            LiveDataBus.with(PLAYLIST_DETAILS, Long::class.java).value = data.id
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.community_container, PlaylistDetailsFragment(), null)
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取推荐歌单
     */
    private fun initRecommendedPlaylist() {
        viewModel.recommendedPlayListRequest(30, requireContext())
        viewModel.recommendedPlayListLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.communitySongList.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = object : BaseRecyclerViewAdapter<RecommendedPlayListResult>(
                    R.layout.recommended_playlist_item,
                    it.result
                ) {
                    override fun onBind(
                        rvDataBinding: ViewDataBinding,
                        data: RecommendedPlayListResult,
                        position: Int
                    ) {
                        rvDataBinding as RecommendedPlaylistItemBinding
                        rvDataBinding.recommendedPlaylistItemPlayCount.text =
                            getNumberWanTwo(data.playCount.toString())
                        Glide.with(requireContext()).load(data.picUrl)
                            .into(rvDataBinding.recommendedPlaylistItemImg)
                        rvDataBinding.recommendedPlaylistItemName.text = data.name
                        rvDataBinding.root.setOnClickListener {
                            LiveDataBus.with(PLAYLIST_DETAILS, Long::class.java).value = data.id
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.community_container, PlaylistDetailsFragment(), null)
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                }
            }
        }

    }

    /**
     * 初始化Banner
     */
    private fun initBanner() {
        viewModel.homeBannerRequest(1, requireContext())
        viewModel.homeBannerLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.communityBanner.apply {
                adapter = object : BannerImageAdapter<HomeBanner>(it.banners) {
                    override fun onBindView(
                        holder: BannerImageHolder?,
                        data: HomeBanner?,
                        position: Int,
                        size: Int
                    ) {
                        holder!!.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        Glide.with(requireContext()).load(data!!.pic).into(holder!!.imageView)
                    }
                }

                indicator = RectangleIndicator(requireContext())
                setIndicatorWidth(10, 40)
                setIndicatorSelectedColor(resources.getColor(R.color.white))
                setIndicatorNormalColor(resources.getColor(R.color.white_8a))
            }
        }
    }
}