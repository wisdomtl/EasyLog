package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.proto.gen.LogOuterClass.Log

class SinkInterceptor(private val sink: Sink?) : Interceptor<Log> {
    override fun log(tag: String, message: Log, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) sink?.output(message)
        chain.proceed(tag, message, priority)
    }

    override fun enable(): Boolean = true

    interface Sink {
        fun output(message: Log)
    }
}