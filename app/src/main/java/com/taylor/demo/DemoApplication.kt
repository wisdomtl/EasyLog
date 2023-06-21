package com.taylor.demo

import android.app.Application
import android.util.Log
import com.google.protobuf.MessageLite
import com.taylor.demo.api.TrackApi
import com.taylor.demo.protobuf.gen.loadSuccess
import com.taylor.easylog.EasyLog
import com.tencent.mmkv.MMKV
import com.zenmen.easylog_su.interceptor.FrameInterceptor
import com.zenmen.easylog_su.interceptor.UploadInterceptor
import com.zenmen.easylog_su.model.Converter
import com.zenmen.easylog_su.model.LogBatch
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
        val list = listOf(
            Data(1, true),
            Data(2, true),
            Data(3, false),
        )
        EasyLog.list(list) { "${it.a} + ${it.b}" }
        EasyLog.log("after list printed")
        EasyLog.map(mapOf("abd" to 11, "ddd" to 2))
        EasyLog.map(mapOf("abd" to mapOf(1 to 2, 3 to 4), "abd" to mapOf(44 to 2, 33 to 4)))

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

    private val scope = CoroutineScope(Dispatchers.IO)

    private val uploader by lazy {
        object : UploadInterceptor.Uploader {
            override fun upload(messages: LogBatch) {
                messages.logs.print { it. }
                Log.d("ttaylor", "DemoApplication.upload[messages]: ")
                //                scope.launch { trackApi.track(messages) }
                //                messages.logList.map { it.id to it.data.unpack(LoadSuccess::class.java) }.print { "${it.first} to ${it.second} and ${it.second.isHitCache}" }.let {
                //                    android.util.Log.i("ttaylor", "DemoApplication.upload() logBatch=${it}");
            }
        }
    }

    private var mCount = 0;

    private fun initEasyLog() {
        EasyLog.simpleInit(5, 10_000, uploader)
        repeat(5) {
            EasyLog.log(loadSuccess {
                duration = 100
                isHitCache = it % 2 == 0
                count = mCount++
            }.convertToByteArray())
        }
    }
}

class MessageLiteConverter(private val messageLite: MessageLite) : Converter {
    override fun pack(): ByteArray {
        return messageLite.toByteArray()
    }
}

fun MessageLite.convertToByteArray(): Converter {
    return MessageLiteConverter(this)
}

data class Data(val a: Int, val b: Boolean)

/**
 * print collection bean in which you interested defined by [map]
 */
fun <T> Collection<T>.print(map: (T) -> String) =
    StringBuilder("[").also { sb ->
        this.forEach { e -> sb.append("\t${map(e)},") }
        sb.append("]")
    }.toString()