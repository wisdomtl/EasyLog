package com.taylor.demo

import android.app.Application
import android.util.Log
import com.google.protobuf.Message
import com.google.protobuf.MessageLite
import com.taylor.demo.api.TrackApi
import com.taylor.demo.protobuf.gen.AdLog.EventBatch
import com.taylor.demo.protobuf.gen.AdLog.LoadFail
import com.taylor.demo.protobuf.gen.AdLog.LoadSuccess
import com.taylor.demo.protobuf.gen.event
import com.taylor.demo.protobuf.gen.loadSuccess
import com.taylor.easylog.EasyLog
import com.tencent.mmkv.MMKV
import com.zenmen.easylog_su.Pipeline
import com.zenmen.easylog_su.simpleInit
import com.taylor.demo.call_adapter.ResultCallAdapterFactory
import com.zenmen.easylog_su.interceptor.FrameInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.protobuf.ProtoConverterFactory
import java.util.concurrent.TimeUnit


class DemoApplication : Application() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        EasyLog.curPriority = EasyLog.NONE
        /**
         * ensure EasyLog do init before other module, which also depends on EasyLog
         */
        initEasyLog()
        EasyLog.log(message = "i am a android develp", priority = EasyLog.ERROR)
        EasyLog.tag("telanx").log(message = "i named telanx", priority = EasyLog.ERROR)
        EasyLog.log(message = "abcdefg", priority = EasyLog.ERROR)
        EasyLog.log(IllegalArgumentException("dfdfdfdfdsfsfdsfdf"), EasyLog.ERROR)
        EasyLog.log("message %s", EasyLog.ERROR, "sss")
//        scope.launch(Dispatchers.IO) {
//            repeat(1000) { EasyLog.log("counting $it") }
//        }
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
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(ProtoConverterFactory.create())
            .build()
    }
    private val trackApi by lazy { retrofit.create(TrackApi::class.java) }

    private val mmkv by lazy {
        MMKV.defaultMMKV()
    }


    private val pipeline by lazy {
        object : Pipeline<Message, EventBatch> {
            override fun pack(logs: List<Message>): EventBatch {
                return logs.fold(EventBatch.newBuilder()) { acc: EventBatch.Builder, any: Message -> acc.addEvents(event { event = com.google.protobuf.Any.pack(any) }) }.build()
            }

            override suspend fun upload(eventBatch: EventBatch): Boolean {
                for (event in eventBatch.eventsList) {
                    when {
                        event.event.`is`(LoadFail::class.java) -> Log.d("ttaylor", "DemoApplication.upload[eventBatch]: loadSuccess=${event.event.unpack(LoadFail::class.java)}")
                        event.event.`is`(LoadSuccess::class.java) -> {
                            Log.d("ttaylor", "DemoApplication.upload[eventBatch]: loadFail=${event.event.unpack(LoadSuccess::class.java)}")
                        }
                        else -> {

                        }
                    }
                }
                //                val result = trackApi.track(eventBatch)
                return true
            }

            override fun toByteArray(log: Message): ByteArray {
                return log.toByteArray()
            }
        }
    }

    private var mCount = 0;

    private fun initEasyLog() {
        EasyLog.simpleInit(5, 10_000, pipeline){log-> log is MessageLite}
        repeat(5) {
            EasyLog.log(loadSuccess {
                duration = 100
                isHitCache = it % 2 == 0
                count = mCount++
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

data class Data(val a: Int, val b: Boolean)