package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.proto.gen.LogOuterClass.Log

class SinkInterceptor(private val sink: Sink?) : Interceptor<Log> {
    override fun log(message: Log, tag: String, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) sink?.output(message, tag)
        chain.proceed(message, tag, priority)
    }

    override fun enable(): Boolean = true

    interface Sink {
        fun output(message: Log, tag: String)
    }
}