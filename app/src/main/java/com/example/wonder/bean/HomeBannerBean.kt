package com.example.wonder.bean

data class HomeBannerBean(
    val banners: List<HomeBanner>,
    val code: Int
)

data class HomeBanner(
    val adDispatchJson: Any,
    val adLocation: Any,
    val adSource: Any,
    val adid: Any,
    val adurlV2: Any,
    val alg: String,
    val bannerId: String,
    val dynamicVideoData: Any,
    val encodeId: String,
    val event: Any,
    val exclusive: Boolean,
    val extMonitor: Any,
    val extMonitorInfo: Any,
    val monitorBlackList: Any,
    val monitorClick: Any,
    val monitorClickList: List<Any>,
    val monitorImpress: Any,
    val monitorImpressList: List<Any>,
    val monitorType: Any,
    val pic: String,
    val pid: Any,
    val program: Any,
    val requestId: String,
    val s_ctrp: String,
    val scm: String,
    val showAdTag: Boolean,
    val showContext: Any,
    val song: HomeBannerSong,
    val targetId: Long,
    val targetType: Int,
    val titleColor: String,
    val typeTitle: String,
    val url: String,
    val video: Any
)

data class HomeBannerSong(
    val a: Any,
    val al: HomeBannerAl,
    val alg: String,
    val alia: List<Any>,
    val ar: List<HomeBannerAr>,
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
    val h: HomeBannerH,
    val hr: HomeBannerHr,
    val id: Int,
    val l: HomeBannerL,
    val m: HomeBannerM,
    val mark: Int,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val originSongSimpleData: HomeBannerOriginSongSimpleData,
    val pop: Int,
    val privilege: HomeBannerPrivilege,
    val pst: Int,
    val publishTime: Long,
    val resourceState: Boolean,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val s_id: Int,
    val single: Int,
    val songJumpInfo: Any,
    val sq: HomeBannerSq,
    val st: Int,
    val t: Int,
    val tagPicList: Any,
    val tns: List<String>,
    val v: Int,
    val version: Int
)

data class HomeBannerAl(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<String>
)

data class HomeBannerAr(
    val alias: List<Any>,
    val id: Int,
    val name: String,
    val tns: List<Any>
)

data class HomeBannerH(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class HomeBannerHr(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class HomeBannerL(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class HomeBannerM(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class HomeBannerOriginSongSimpleData(
    val albumMeta: HomeBannerAlbumMeta,
    val artists: List<HomeBannerArtist>,
    val name: String,
    val songId: Int
)

data class HomeBannerPrivilege(
    val chargeInfoList: List<HomeBannerChargeInfo>,
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
    val freeTrialPrivilege: HomeBannerFreeTrialPrivilege,
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

data class HomeBannerSq(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class HomeBannerAlbumMeta(
    val id: Int,
    val name: String
)

data class HomeBannerArtist(
    val id: Int,
    val name: String
)

data class HomeBannerChargeInfo(
    val chargeMessage: Any,
    val chargeType: Int,
    val chargeUrl: Any,
    val rate: Int
)

data class HomeBannerFreeTrialPrivilege(
    val listenType: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)