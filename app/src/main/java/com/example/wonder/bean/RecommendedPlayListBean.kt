package com.example.wonder.bean

data class RecommendedPlayList(
    val category: Int,
    val code: Int,
    val hasTaste: Boolean,
    val result: List<RecommendedPlayListResult>
)

data class RecommendedPlayListResult(
    val alg: String,
    val canDislike: Boolean,
    val copywriter: String,
    val highQuality: Boolean,
    val id: Long,
    val name: String,
    val picUrl: String,
    val playCount: Int,
    val trackCount: Int,
    val trackNumberUpdateTime: Long,
    val type: Int
)