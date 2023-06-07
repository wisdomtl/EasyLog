package com.taylor.demo

import android.app.Application
import com.taylor.demo.api.TrackApi
import com.taylor.demo.protobuf.gen.AdLog.LoadSuccess
import com.taylor.demo.protobuf.gen.loadSuccess
import com.taylor.easylog.EasyLog
import com.taylor.easylog.FormatInterceptor
import com.taylor.easylog.LogcatInterceptor
import com.tencent.mmkv.MMKV

import com.zenmen.easylog_su.interceptor.BatchInterceptor
import com.zenmen.easylog_su.interceptor.FrameInterceptor
import com.zenmen.easylog_su.interceptor.LinearInterceptor
import com.zenmen.easylog_su.interceptor.LogWrapperInterceptor
import com.zenmen.easylog_su.interceptor.SinkInterceptor
import com.zenmen.easylog_su.interceptor.UploadInterceptor
import com.zenmen.easylog_su.proto.gen.LogOuterClass.Log
import com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch
import com.zenmen.easylog_su.simpleInit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.protobuf.ProtoConverterFactory
import java.util.concurrent.TimeUnit


class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        /**
         * ensure EasyLog do init before other module, which also depends on EasyLog
         */
        initEasyLog()
        initTaylorSdk()
        EasyLog.log(message = "i am a android develp", priority = EasyLog.ERROR)
        EasyLog.tag("telanx").log(message = "i named telanx", priority = EasyLog.ERROR)
        EasyLog.log(message = "abcdefg", priority = EasyLog.ERROR)
        EasyLog.log(IllegalArgumentException("dfdfdfdfdsfsfdsfdf"), EasyLog.ERROR)
        EasyLog.log("message %s", EasyLog.ERROR, "sss")
        scope.launch(Dispatchers.IO) {
            repeat(1000) { EasyLog.log("counting $it") }
        }
        EasyLog.interceptor(FrameInterceptor()).log("this is onetime interceptor")
        EasyLog.log("after one time interceptor")
    }

    /**
     * init another module
     */
    private fun initTaylorSdk() {
//        TaylorSdk()
    }

    val okHttpClient by lazy {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .callTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.zenmen.com/")
            .client(okHttpClient)
            .addConverterFactory(ProtoConverterFactory.create())
            .build()
    }
    private val trackApi by lazy { retrofit.create(TrackApi::class.java) }

    private val mmkv by lazy {
        MMKV.defaultMMKV()
    }
    private val sink by lazy {
        object : SinkInterceptor.Sink {
            override fun output(message: Log) {
                mmkv.encode(message.id.toString(), message.toByteArray())
            }
        }
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    private val uploader by lazy {
        object : UploadInterceptor.Uploader {
            override fun upload(messages: LogBatch) {
//                scope.launch { trackApi.track(messages) }
                messages.logList.map { it.id to it.data.unpack(LoadSuccess::class.java) }.print { "${it.first} to ${it.second} and ${it.second.isHitCache}" }.let {
//                    android.util.Log.i("ttaylor", "DemoApplication.upload() logBatch=${it}");
                }
            }
        }
    }

    private fun initEasyLog() {
        EasyLog.simpleInit(5, 10_000, sink, uploader)
        repeat(5) {
            EasyLog.log(loadSuccess {
                duration = 100
                isHitCache = it % 2 == 0
                count = 2
            })
        }
    }
}

/**
 * print collection bean in which you interested defined by [map]
 */
fun <T> Collection<T>.print(map: (T) -> String) =
    StringBuilder("[").also { sb ->
        this.forEach { e -> sb.append("\t${map(e)},") }
        sb.append("]")
    }.toString()