package com.example.wonder.utils

import com.tencent.mmkv.MMKV

val KV: MMKV = MMKV.defaultMMKV()

object MMKVData {
    const val COOKIE = "cookie"
    const val U_ID = "id"
}