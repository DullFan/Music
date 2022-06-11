package com.example.wonder.utils

import androidx.lifecycle.MutableLiveData

/**
 * 全局共享LiveData
 */
object LiveDataBus {
    private val bus: MutableMap<String, MutableLiveData<Any>> by lazy { HashMap() }

    @Synchronized
    fun <T> with(key: String, type: Class<T>): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = MutableLiveData()
        }
        return bus[key] as MutableLiveData<T>
    }
}