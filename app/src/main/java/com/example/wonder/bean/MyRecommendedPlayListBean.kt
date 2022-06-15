package com.example.wonder.bean

data class MyRecommendedPlayListBean(
    val code: Int,
    val featureFirst: Boolean,
    val haveRcmdSongs: Boolean,
    val recommend: List<MyRecommendedPlayListRecommend>
)

data class MyRecommendedPlayListRecommend(
    val alg: String,
    val copywriter: String,
    val createTime: Long,
    val creator: MyRecommendedPlayListCreator,
    val id: Long,
    val name: String,
    val picUrl: String,
    val playcount: Int,
    val trackCount: Int,
    val type: Int,
    val userId: Int
)

data class MyRecommendedPlayListCreator(
    val accountStatus: Int,
    val authStatus: Int,
    val authority: Int,
    val avatarImgId: Long,
    val avatarImgIdStr: String,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundImgIdStr: String,
    val backgroundUrl: String,
    val birthday: Int,
    val city: Int,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Int,
    val expertTags: Any,
    val followed: Boolean,
    val gender: Int,
    val mutual: Boolean,
    val nickname: String,
    val province: Int,
    val remarkName: Any,
    val signature: String,
    val userId: Int,
    val userType: Int,
    val vipType: Int
)