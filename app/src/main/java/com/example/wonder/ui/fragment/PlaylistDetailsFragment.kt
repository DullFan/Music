package com.example.wonder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wonder.R
import com.example.wonder.base.BaseFragment
import com.example.wonder.base.BaseRecyclerViewAdapter
import com.example.wonder.base.RecyclerViewViewHolder
import com.example.wonder.bean.MusicListBean
import com.example.wonder.databinding.FragmentPlaylistDetailsBinding
import com.example.wonder.databinding.ItemRvPlayListBinding
import com.example.wonder.utils.*
import com.example.wonder.viewmodel.MainViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

class PlaylistDetailsFragment : BaseFragment() {
    var adapterClickFlag = false
    lateinit var viewDataBinding: FragmentPlaylistDetailsBinding
    val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
    }
    private val playlistId: Long =
        LiveDataBus.with(LiveDataBusKey.PLAYLIST_DETAILS, Long::class.java)!!.value!!

    val mutableList = ArrayList<MusicListBean>()

    lateinit var rvAdapter: BaseRecyclerViewAdapter<MusicListBean>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentPlaylistDetailsBinding.inflate(layoutInflater)
        viewDataBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                requireActivity().resources.displayMetrics.widthPixels,
                requireActivity().resources.displayMetrics.heightPixels
            )

        initPlayListDetails()

        initPlayListSong()

        viewDataBinding.playlistDetailsBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return viewDataBinding.root
    }


    /**
     * ??????????????????
     */
    private fun initPlayListSong() {
        viewModel.playListSongRequest(playlistId, requireContext())
        viewModel.playListSongLiveData.observe(viewLifecycleOwner) { it ->
            mutableList.clear()
            it.songs.forEach {
                mutableList.add(
                    MusicListBean(
                        it.id,
                        it.al.picUrl,
                        it.dt,
                        it.name,
                        it.ar.joinToString(separator = "???") { item -> item.name },
                        it.fee
                    )
                )
            }

            viewDataBinding.playlistDetailsPlayText.text = "???????????? (${mutableList.size})"

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

                        if(adapterClickFlag){
                            if(index == position){
                                rvDataBinding.visFlag = false
                                rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.black_alpha_a1))
                            }else{
                                rvDataBinding.visFlag = true
                                rvDataBinding.root.setBackgroundColor(resources.getColor(R.color.white))
                            }
                        }else{
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

                            LiveDataBus.with(LiveDataBusKey.MUSIC_IS_FLAG, Boolean::class.java).value = true

                            PassingOnData.oneInFlag = false
                            rvDataBinding.visFlag = false

                            adapterClickFlag = true

                            val baseRecyclerViewAdapter = LiveDataBus.with(
                                LiveDataBusKey.BASE_ADAPTER,
                                RecyclerView.Adapter::class.java
                            ).value as BaseRecyclerViewAdapter<MusicListBean>

                            baseRecyclerViewAdapter.index = index

                            LiveDataBus.with(LiveDataBusKey.VIEW_PAGER2, ViewPager2::class.java).value?.currentItem =
                                index
                        }
                    }
                }

            viewDataBinding.playlistDetailsPlayRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
            }
        }
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

    /**
     * ??????????????????
     */
    private fun initPlayListDetails() {
        viewModel.playListDetailsRequest(playlistId, requireContext())
        viewModel.playListDetailsLiveData.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.playlist.coverImgUrl)
                .into(viewDataBinding.recommendedPlaylistItemImg)
            Glide.with(this).load(it.playlist.coverImgUrl).apply(
                RequestOptions.bitmapTransform(
                    BlurTransformation(100, 4)
                )
            ).into(viewDataBinding.playlistDetailsImg)

            viewDataBinding.recommendedPlaylistItemPlayCount.text =
                getNumberWanTwo(it.playlist.playCount.toString())

            viewDataBinding.recommendedPlaylistItemName.text = it.playlist.name
            viewDataBinding.playlistDetailsDetails.text = it.playlist.description
        }
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