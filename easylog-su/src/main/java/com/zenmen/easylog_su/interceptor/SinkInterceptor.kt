package com.zenmen.easylog_su.interceptor

import android.content.Context
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.tencent.mmkv.MMKV
import com.zenmen.easylog_proto.proto.gen.LogOuterClass.Log

class SinkInterceptor(context: Context) : Interceptor<Log> {
    init {
        MMKV.initialize(context)
    }

    private val mmkv = MMKV.defaultMMKV()

    override fun log(message: Log, tag: String, priority: Int, chain: Chain) {
        if (enable()) mmkv.encode(message.id.toString(), message.toByteArray())
        chain.proceed(message, tag, priority)
    }

    override fun enable(): Boolean = true
}