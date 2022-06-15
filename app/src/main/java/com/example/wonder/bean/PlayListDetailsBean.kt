package com.example.wonder.bean

data class PlayListDetailsBean(
    val code: Long,
    val playlist: Playlist,
    val privileges: List<PlayListDetailsPrivilege>,
    val relatedVideos: Any,
    val resEntrance: Any,
    val sharedPrivilege: Any,
    val urls: Any
)

data class Playlist(
    val adType: Long,
    val backgroundCoverId: Long,
    val backgroundCoverUrl: Any,
    val cloudTrackCount: Long,
    val commentCount: Long,
    val commentThreadId: String,
    val coverImgId: Long,
    val coverImgId_str: String,
    val coverImgUrl: String,
    val createTime: Long,
    val creator: Creator,
    val description: String,
    val englishTitle: Any,
    val highQuality: Boolean,
    val historySharedUsers: Any,
    val id: Long,
    val name: String,
    val newImported: Boolean,
    val officialPlaylistType: Any,
    val opRecommend: Boolean,
    val ordered: Boolean,
    val playCount: Long,
    val privacy: Long,
    val remixVideo: Any,
    val shareCount: Long,
    val sharedUsers: Any,
    val specialType: Long,
    val status: Long,
    val subscribed: Boolean,
    val subscribedCount: Long,
    val subscribers: List<Subscriber>,
    val tags: List<String>,
    val titleImage: Long,
    val titleImageUrl: Any,
    val trackCount: Long,
    val trackIds: List<TrackId>,
    val trackNumberUpdateTime: Long,
    val trackUpdateTime: Long,
    val tracks: List<Track>,
    val updateFrequency: Any,
    val updateTime: Long,
    val userId: Long,
    val videoIds: Any,
    val videos: Any
)

data class PlayListDetailsPrivilege(
    val chargeInfoList: List<PlayListDetailsChargeInfo>,
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
    val freeTrialPrivilege: PlayListDetailsFreeTrialPrivilege,
    val id: Long,
    val maxBrLevel: String,
    val maxbr: Long,
    val paidBigBang: Boolean,
    val payed: Long,
    val pc: Any,
    val pl: Long,
    val plLevel: String,
    val playMaxBrLevel: String,
    val playMaxbr: Long,
    val preSell: Boolean,
    val realPayed: Long,
    val rscl: Any,
    val sp: String,
    val st: String,
    val subp: Long,
    val toast: Boolean
)

data class Creator(
    val accountStatus: Long,
    val anchor: Boolean,
    val authStatus: Long,
    val authenticationTypes: Long,
    val authority: Long,
    val avatarDetail: Any,
    val avatarImgId: Long,
    val avatarImgIdStr: String,
    val avatarImgId_str: String,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundImgIdStr: String,
    val backgroundUrl: String,
    val birthday: Long,
    val city: Long,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Long,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val gender: Long,
    val mutual: Boolean,
    val nickname: String,
    val province: Long,
    val remarkName: Any,
    val signature: String,
    val userId: Long,
    val userType: Long,
    val vipType: Long
)

data class Subscriber(
    val accountStatus: Long,
    val anchor: Boolean,
    val authStatus: Long,
    val authenticationTypes: Long,
    val authority: Long,
    val avatarDetail: Any,
    val avatarImgId: Long,
    val avatarImgIdStr: String,
    val avatarImgId_str: String,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundImgIdStr: String,
    val backgroundUrl: String,
    val birthday: Long,
    val city: Long,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Long,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val gender: Long,
    val mutual: Boolean,
    val nickname: String,
    val province: Long,
    val remarkName: Any,
    val signature: String,
    val userId: Long,
    val userType: Long,
    val vipType: Long
)

data class TrackId(
    val alg: Any,
    val at: Long,
    val id: Long,
    val rcmdReason: String,
    val sc: Any,
    val t: Long,
    val uid: Long,
    val v: Long
)

data class Track(
    val a: Any,
    val al: PlayListDetailsAl,
    val alia: List<String>,
    val ar: List<PlayListDetailsAr>,
    val cd: String,
    val cf: String,
    val copyright: Long,
    val cp: Long,
    val crbt: Any,
    val djId: Long,
    val dt: Long,
    val entertainmentTags: Any,
    val fee: Long,
    val ftype: Long,
    val h: PlayListDetailsH,
    val hr: PlayListDetailsHr,
    val id: Long,
    val l: PlayListDetailsL,
    val m: PlayListDetailsM,
    val mark: Long,
    val mst: Long,
    val mv: Long,
    val name: String,
    val no: Long,
    val noCopyrightRcmd: Any,
    val originCoverType: Long,
    val originSongSimpleData: PlayListDetailsOriginSongSimpleData,
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
    val sq: PlayListDetailsSq,
    val st: Long,
    val t: Long,
    val tagPicList: Any,
    val v: Long,
    val version: Long
)

data class PlayListDetailsAl(
    val id: Long,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
)

data class PlayListDetailsAr(
    val alias: List<Any>,
    val id: Long,
    val name: String,
    val tns: List<Any>
)

data class PlayListDetailsH(
    val br: Long,
    val fid: Long,
    val size: Long,
    val vd: Double
)

data class PlayListDetailsHr(
    val br: Long,
    val fid: Long,
    val size: Long,
    val vd: Double
)

data class PlayListDetailsL(
    val br: Long,
    val fid: Long,
    val size: Long,
    val vd: Double
)

data class PlayListDetailsM(
    val br: Long,
    val fid: Long,
    val size: Long,
    val vd: Double
)

data class PlayListDetailsOriginSongSimpleData(
    val albumMeta: PlayListDetailsAlbumMeta,
    val artists: List<PlayListDetailsArtist>,
    val name: String,
    val songId: Long
)

data class PlayListDetailsSq(
    val br: Long,
    val fid: Long,
    val size: Long,
    val vd: Double
)

data class PlayListDetailsAlbumMeta(
    val id: Long,
    val name: String
)

data class PlayListDetailsArtist(
    val id: Long,
    val name: String
)

data class PlayListDetailsChargeInfo(
    val chargeMessage: Any,
    val chargeType: Long,
    val chargeUrl: Any,
    val rate: Long
)

data class PlayListDetailsFreeTrialPrivilege(
    val listenType: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)