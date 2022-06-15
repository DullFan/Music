package com.example.wonder.bean

data class PlayListSongBean(
    val code: Long,
    val privileges: List<PlayListSongPrivilege>,
    val songs: List<PlayListSong>
)

data class PlayListSongPrivilege(
    val chargeInfoList: List<PlayListSongChargeInfo>,
    val cp: Long,
    val cs: Boolean,
    val dl: Long,
    val dlLevel: String,
    val downloadMaxBrLevel: String,
    val downloadMaxbr: Long,
    val fee: Long,
    val fl: Long,
    val flLevel: String,
    val flag: Long,
    val freeTrialPrivilege: PlayListSongFreeTrialPrivilege,
    val id: Long,
    val maxBrLevel: String,
    val maxbr: Long,
    val payed: Long,
    val pl: Long,
    val plLevel: String,
    val playMaxBrLevel: String,
    val playMaxbr: Long,
    val preSell: Boolean,
    val rscl: Any,
    val sp: Long,
    val st: Long,
    val subp: Long,
    val toast: Boolean
)

data class PlayListSong(
    val a: Any,
    val al: PlayListSongAl,
    val alia: List<Any>,
    val ar: List<PlayListSongAr>,
    val cd: String,
    val cf: String,
    val copyright: Long,
    val cp: Long,
    val crbt: Any,
    val djId: Long,
    val dt: Int,
    val entertainmentTags: Any,
    val fee: Int,
    val ftype: Long,
    val h: PlayListSongH,
    val hr: Any,
    val id: Long,
    val l: PlayListSongL,
    val m: PlayListSongM,
    val mark: Long,
    val mst: Long,
    val mv: Long,
    val name: String,
    val no: Long,
    val noCopyrightRcmd: Any,
    val originCoverType: Long,
    val originSongSimpleData: Any,
    val pop: Long,
    val pst: Long,
    val publishTime: Long,
    val resourceState: Boolean,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Long,
    val rurl: Any,
    val s_id: Long,
    val single: Long,
    val songJumpInfo: Any,
    val sq: PlayListSongSq,
    val st: Long,
    val t: Long,
    val tagPicList: Any,
    val v: Long,
    val version: Long
)

data class PlayListSongChargeInfo(
    val chargeMessage: Any,
    val chargeType: Long,
    val chargeUrl: Any,
    val rate: Long
)

data class PlayListSongFreeTrialPrivilege(
    val listenType: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)

data class PlayListSongAl(
    val id: Long,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
)

data class PlayListSongAr(
    val alias: List<Any>,
    val id: Long,
    val name: String,
    val tns: List<Any>
)

data class PlayListSongH(
    val br: Long,
    val fid: Long,
    val size: Long,
    val sr: Long,
    val vd: Double
)

data class PlayListSongL(
    val br: Long,
    val fid: Long,
    val size: Long,
    val sr: Long,
    val vd: Double
)

data class PlayListSongM(
    val br: Long,
    val fid: Long,
    val size: Long,
    val sr: Long,
    val vd: Double
)

data class PlayListSongSq(
    val br: Long,
    val fid: Long,
    val size: Long,
    val sr: Long,
    val vd: Double
)