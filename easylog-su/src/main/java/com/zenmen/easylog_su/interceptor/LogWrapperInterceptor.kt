package com.zenmen.easylog_su.interceptor


import com.google.protobuf.Message

import java.util.Date
import com.google.protobuf.Any
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.proto.gen.log

class LogWrapperInterceptor : Interceptor<Message> {
    private val date = Date()
    private val nanoTimestamp: Long
        get() = date.time * 1000_000 + System.nanoTime() % 1000_000

    override fun log(tag: String, message: Message, priority: Int, chain: Chain, vararg args: kotlin.Any) {
        val log = log {
            id = nanoTimestamp
            data = Any.pack(message)
        }
        chain.proceed(tag,log, priority)
    }

    override fun enable(): Boolean = true
}