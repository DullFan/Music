package com.example.wonder.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method

class BusMutableLiveData<T>(): MutableLiveData<T>(){
    //判断是否要启动粘性事件，true为启动粘性，false为不启动粘性
    var isFlag: Boolean = false

    constructor(isFlag:Boolean):this(){
        this.isFlag = isFlag
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        if(isFlag){
            hook(observer)
        }
    }

    private fun hook(observer: Observer<in T>) {
        //得到mLastVersion
        val liveDataClass:Class<LiveData<*>> = LiveData::class.java
        //获取到LiveData的类中的mObservers对象
        val mObserversField: Field = liveDataClass.getDeclaredField("mObservers")
        mObserversField.isAccessible = true
        //获取到这个成员变量的对象
        val mObserversObject:Any = mObserversField.get(this)
        //得到map对象的class对象
        val mObserversClass:Class<*> = mObserversObject.javaClass
        //获取到mObservers对象的get方法
        val get: Method = mObserversClass.getDeclaredMethod("get",Any::class.java)
        get.isAccessible = true
        val invokeEntry:Any = get.invoke(mObserversObject, observer)
        //取到entry中的value
        var observerWraper :Any? = null
        if(invokeEntry != null && invokeEntry is Map.Entry<*,*>){
            observerWraper = invokeEntry.value
        }
        if (invokeEntry == null){
            throw NullPointerException("错误")
        }

        val supperClass:Class<*> = observerWraper?.javaClass?.superclass!!
        val mLastVersion:Field = supperClass.getDeclaredField("mLastVersion")
        mLastVersion.isAccessible = true
        //得到mVersion
        val mVersion:Field = liveDataClass.getDeclaredField("mVersion")
        mVersion.isAccessible = true
        val mVersionValue:Any = mVersion.get(this)
        mLastVersion.set(observerWraper,mVersionValue)
    }
}
