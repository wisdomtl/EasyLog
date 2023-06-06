package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class LinearInterceptor : Interceptor<Any> {

    private val CHANNEL_CAPACITY = 50
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /**
     * A queue to cache log in memory
     */
    private val channel = Channel<Event>(CHANNEL_CAPACITY)

    init {
        scope.launch {
            channel.consumeEach { event ->
                event.apply { chain.proceed(message, tag, priority) }
            }
        }
    }

    override fun log(message: Any, tag: String, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) {
            scope.launch { channel.send(Event(message, tag, priority, chain)) }
        } else {
            chain.proceed(message, tag, priority)
        }
    }


    override fun enable(): Boolean = true

    data class Event(val message: Any, val tag: String, val priority: Int, val chain: Chain)
}