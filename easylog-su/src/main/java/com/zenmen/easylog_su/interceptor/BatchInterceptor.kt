package com.zenmen.easylog_su.interceptor

import android.os.SystemClock
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_proto.proto.gen.LogOuterClass.Log

class BatchInterceptor(private val size: Int, private val duration: Long) : Interceptor<Log> {
    /**
     * A list for counting event
     */
    private val list = mutableListOf<Log>()
    private var lastTime = 0L

    override fun log(message: Log, tag: String, priority: Int, chain: Chain) {
        if (enable()) {
            list.add(message)
            if (lastTime != 0L && SystemClock.elapsedRealtime() - lastTime >= duration || list.size >= size) {
                chain.proceed(message = list.toTypedArray(), tag, priority)
                list.clear()
                lastTime = SystemClock.elapsedRealtime()
            }
        } else {
            chain.proceed(message, tag, priority)
        }
    }

    override fun enable(): Boolean = true
}