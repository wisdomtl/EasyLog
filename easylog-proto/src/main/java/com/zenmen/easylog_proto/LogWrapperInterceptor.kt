package com.zenmen.easylog_proto


import com.google.protobuf.Message
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_proto.proto.gen.log
import java.util.Date
import com.google.protobuf.Any

class LogWrapperInterceptor : Interceptor<Message> {
    private val date = Date()
    private val nanoTimestamp: Long
        get() = date.time * 1000_000 + System.nanoTime() % 1000_000

    override fun log(message: Message, tag: String, priority: Int, chain: Chain) {
        val log = log {
            id = nanoTimestamp
            data = Any.pack(message)
        }
        chain.proceed(log, tag, priority)
    }

    override fun enable(): Boolean = true
}