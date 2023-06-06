package com.zenmen.easylog_su.interceptor

import android.os.SystemClock
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.proto.gen.LogOuterClass.Log
import com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch


class BatchInterceptor(private val size: Int, private val duration: Long) : Interceptor<Log> {
    /**
     * A list for counting event
     */
    private val list = mutableListOf<Log>()
    private var lastTime = 0L

    override fun log(message: Log, tag: String, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) {
            list.add(message)
            if (lastTime != 0L && SystemClock.elapsedRealtime() - lastTime >= duration || list.size >= size) {
                val logBatch = list.fold(LogBatch.newBuilder()) { acc: LogBatch.Builder, log: Log -> acc.addLog(log) }.build()
                chain.proceed(logBatch, tag, priority)
                list.clear()
                lastTime = SystemClock.elapsedRealtime()
            }
        } else {
            chain.proceed(message, tag, priority)
        }
    }

    override fun enable(): Boolean = true
}