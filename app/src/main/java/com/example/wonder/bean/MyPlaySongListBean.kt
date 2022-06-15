package com.example.wonder.bean

data class MyPlaySongListBean(
    val code: Int,
    val `data`: MyPlaySongListData
)

data class MyPlaySongListData(
    val dailySongs: List<MyPlaySongListDailySong>,
    val orderSongs: List<Any>,
    val recommendReasons: List<MyPlaySongListRecommendReason>
)

data class MyPlaySongListDailySong(
    val a: Any,
    val al: MyPlaySongListAl,
    val alg: String,
    val alia: List<String>,
    val ar: List<MyPlaySongListAr>,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val crbt: Any,
    val djId: Int,
    val dt: Int,
    val entertainmentTags: Any,
    val fee: Int,
    val ftype: Int,
    val h: MyPlaySongListH,
    val hr: MyPlaySongListHr,
    val id: Long,
    val l: MyPlaySongListL,
    val m: MyPlaySongListM,
    val mark: Long,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val originSongSimpleData: MyPlaySongListOriginSongSimpleData,
    val pop: Int,
    val privilege: MyPlaySongListPrivilege,
    val pst: Int,
    val publishTime: Long,
    val reason: String,
    val resourceState: Boolean,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val s_ctrp: String,
    val s_id: Int,
    val single: Int,
    val songJumpInfo: Any,
    val sq: MyPlaySongListSq,
    val st: Int,
    val t: Int,
    val tagPicList: Any,
    val tns: List<String>,
    val v: Int,
    val version: Int
)

data class MyPlaySongListRecommendReason(
    val reason: String,
    val songId: Int
)

data class MyPlaySongListAl(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<String>
)

data class MyPlaySongListAr(
    val alias: List<Any>,
    val id: Int,
    val name: String,
    val tns: List<Any>
)

data class MyPlaySongListH(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class MyPlaySongListHr(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class MyPlaySongListL(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class MyPlaySongListM(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class MyPlaySongListOriginSongSimpleData(
    val albumMeta: MyPlaySongListAlbumMeta,
    val artists: List<MyPlaySongListArtist>,
    val name: String,
    val songId: Int
)

data class MyPlaySongListPrivilege(
    val chargeInfoList: List<MyPlaySongListChargeInfo>,
    val cp: Int,
    val cs: Boolean,
    val dl: Int,
    val dlLevel: String,
    val downloadMaxBrLevel: String,
    val downloadMaxbr: Int,
    val fee: Int,
    val fl: Int,
    val flLevel: String,
    val flag: Int,
    val freeTrialPrivilege: MyPlaySongListFreeTrialPrivilege,
    val id: Int,
    val maxBrLevel: String,
    val maxbr: Int,
    val payed: Int,
    val pl: Int,
    val plLevel: String,
    val playMaxBrLevel: String,
    val playMaxbr: Int,
    val preSell: Boolean,
    val rscl: Any,
    val sp: Int,
    val st: Int,
    val subp: Int,
    val toast: Boolean
)

data class MyPlaySongListSq(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class MyPlaySongListAlbumMeta(
    val id: Int,
    val name: String
)

data class MyPlaySongListArtist(
    val id: Int,
    val name: String
)

data class MyPlaySongListChargeInfo(
    val chargeMessage: Any,
    val chargeType: Int,
    val chargeUrl: Any,
    val rate: Int
)

data class MyPlaySongListFreeTrialPrivilege(
    val listenType: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)