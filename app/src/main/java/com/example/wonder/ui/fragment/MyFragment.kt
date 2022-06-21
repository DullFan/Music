package com.example.wonder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wonder.R
import com.example.wonder.base.BaseFragment
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.example.wonder.bean.MusicListBean
import com.example.wonder.bean.UserSongListPlaylist
import com.example.wonder.databinding.FragmentMyBinding
import com.example.wonder.databinding.ItemMyRvSongListBinding
import com.example.wonder.utils.*
import com.example.wonder.viewmodel.MainViewModel


class MyFragment : BaseFragment() {

    val viewDataBinding by lazy {
        FragmentMyBinding.inflate(layoutInflater)
    }

    private val mutableList = ArrayList<MusicListBean>()


    val viewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.userRequest(requireContext())

        viewModel.userLiveData.observe(viewLifecycleOwner){
            Glide.with(requireContext()).load(it.profile.avatarUrl).into(viewDataBinding.myHead)
            viewDataBinding.myName.text = it.profile.nickname
            viewDataBinding.myIntroduction.text = it.profile.signature
        }
        initLikeMusicList()
        initUserSongList()

        return viewDataBinding.root
    }

    /**
     * 获取用户歌单
     */
    private fun initUserSongList() {
        viewModel.userSongListRequest(requireContext())
        viewModel.userSongListLiveData.observe(viewLifecycleOwner){
            val userSongList = ArrayList<UserSongListPlaylist>()
            it.playlist.forEachIndexed { index, userSongListPlaylist ->
                if(index != 0){
                    userSongList.add(userSongListPlaylist)
                }
            }
            if(userSongList.size != 0){
                val baseAdapter = object : BaseRecyclerViewAdapter<UserSongListPlaylist>(
                    R.layout.item_my_rv_song_list,
                    userSongList
                ) {
                    override fun onBind(
                        rvDataBinding: ViewDataBinding,
                        data: UserSongListPlaylist,
                        position: Int
                    ) {
                        rvDataBinding as ItemMyRvSongListBinding
                        rvDataBinding.data = data
                        rvDataBinding.itemUserSongListDescribe.text = "${data.trackCount}首"
                        rvDataBinding.root.setOnClickListener {
                            LiveDataBus.with(LiveDataBusKey.PLAYLIST_DETAILS, Long::class.java).value = data.id
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .replace(R.id.my_community_container, PlaylistDetailsFragment(), null)
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                }

                viewDataBinding.mySongListRv.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = baseAdapter
                }
            }
        }

    }

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
                        it.ar.joinToString(separator = "、") { item -> item.name },
                        it.fee
                    )
                )
            }

            viewDataBinding.myLikeNumber.text = "${mutableList.size}"

            viewDataBinding.myLayout1.setOnClickListener {
                LiveDataBus.with(LiveDataBusKey.RECOMMENDATION_ID, Int::class.java).value = 1
                LiveDataBus.with(LiveDataBusKey.RECOMMENDATION, ArrayList::class.java).value = mutableList
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.my_community_container, DailyRecommendationFragment(), null)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }



}