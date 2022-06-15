package com.example.wonder.entity

import android.util.Log
import com.example.wonder.bean.ErrorBean
import com.example.wonder.utils.NET_Ease_CLOUND_URL
import com.example.wonder.utils.PassingOnData
import com.example.wonder.utils.showLog
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.*

import okhttp3.Interceptor
import java.lang.String


class APiClient {

    companion object {
        val instance = APiClient()
    }

    fun <T> instanceRetrofit(apiInterface: Class<T>): T {

        val mOkHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(LoggingInterceptor())
            //添加读取超时时间
            readTimeout(10000, TimeUnit.SECONDS)
            //添加连接超时时间
            connectTimeout(10000, TimeUnit.SECONDS)
            //添加写出超时时间
            writeTimeout(10000, TimeUnit.SECONDS)
        }.build()
        var retrofit = Retrofit.Builder().baseUrl(NET_Ease_CLOUND_URL).client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(apiInterface)
    }
}

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request: Request = chain.request()
        val t1 = System.nanoTime() //请求发起的时间
        Log.i(
            "dt", String.format(
                "发送请求 %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime() //收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response.peekBody(1024 * 1024)
        val format = String.format(
            "接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
            response.request.url,
            responseBody.string(),
            (t2 - t1) / 1e6,
            response.headers
        )

        //TODO 获取网络请求信息
//        format.showLog()

        if(request.url.toString().contains("recommend/songs?cookie")){
            format.showLog()
        }

        if ((format.contains("503") || format.contains("505")) && !request.url.toString()
                .contains("banner?type")
            && !request.url.toString().contains("personalized?limit")
            && !request.url.toString().contains("recommend/resource?cookie")
            && !request.url.toString().contains("recommend/songs?cookie")
            && !request.url.toString().contains("playlist/detail")
            && !request.url.toString().contains("playlist/track/all")
        ) {
            val startIndex = format.indexOf("【") + 1
            val endIndex = format.indexOf("】")
            PassingOnData.errorBean =
                Gson().fromJson(format.substring(startIndex, endIndex), ErrorBean::class.java)
        }

        return response
    }
}
