package com.zenmen.easylog_su.interceptor

import android.os.SystemClock
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.Pipeline
import com.zenmen.easylog_su.model.Log
import com.zenmen.easylog_su.model.LogBatch
import com.zenmen.easylog_su.singleLogDispatcher
import kotlinx.coroutines.*

/**
 * An [Interceptor] batch [Log] into [LogBatch]
 */
class BatchInterceptor<LOG, LOGS>(
    private val size: Int,
    private val interval: Long,
    private val pipeline: Pipeline<LOG, LOGS>
) : Interceptor<Log<LOG>>() {
    /**
     * A list for counting event
     */
    private val list = mutableListOf<Log<LOG>>()
    private var lastFlushTime = 0L
    private val scope = CoroutineScope(SupervisorJob())
    private var flushJob: Job? = null

    override fun log(tag: String, log: Log<LOG>, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(log)) {
            list.add(log)
            flushJob?.cancel()
            if (isOkFlush()) {
                flush(chain, tag, priority)
            } else {
                flushJob = delayFlush(chain, tag, priority)
            }
        }
    }

    private fun isOkFlush() = lastFlushTime != 0L && SystemClock.elapsedRealtime() - lastFlushTime >= interval || list.size >= size

    private fun flush(chain: Chain, tag: String, priority: Int) {
        val logs = pipeline.pack(list.map { it.data })
        val logBatch = LogBatch(list.map { it.id }, logs)
        chain.proceed(tag, logBatch, priority)
        list.clear()
        lastFlushTime = SystemClock.elapsedRealtime()
    }

    private fun delayFlush(chain: Chain, tag: String, priority: Int) = scope.launch(singleLogDispatcher) {
        val delayTime = if (lastFlushTime == 0L) interval else interval - (SystemClock.elapsedRealtime() - lastFlushTime)
        delay(delayTime)
        flush(chain, tag, priority)
    }

}