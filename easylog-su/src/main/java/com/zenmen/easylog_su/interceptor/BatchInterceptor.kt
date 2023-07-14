package com.zenmen.easylog_su.interceptor

import android.os.SystemClock
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.Pipeline
import com.zenmen.easylog_su.model.Log
import com.zenmen.easylog_su.model.LogBatch

/**
 * An [Interceptor] batch [Log] into [LogBatch]
 */
class BatchInterceptor<LOG, LOGS>(
    private val size: Int,
    private val duration: Long,
    private val pipeline: Pipeline<LOG, LOGS>
) : Interceptor<Log<LOG>>() {
    /**
     * A list for counting event
     */
    private val list = mutableListOf<Log<LOG>>()
    private var lastTime = 0L

    override fun log(tag: String, log: Log<LOG>, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(log)) {
            list.add(log)
            if (lastTime != 0L && SystemClock.elapsedRealtime() - lastTime >= duration || list.size >= size) {
                val logs = pipeline.pack(list.map { it.data })
                val logBatch = LogBatch(list.map { it.id }, logs)
                chain.proceed(tag, logBatch, priority, args)
                list.clear()
                lastTime = SystemClock.elapsedRealtime()
            }
        }
    }

}