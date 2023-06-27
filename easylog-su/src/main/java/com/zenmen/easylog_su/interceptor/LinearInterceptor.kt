package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * An [Interceptor] make log in sequence, free of multi-thread problem
 */
class LinearInterceptor : Interceptor<Any>() {

    private val CHANNEL_CAPACITY = 50
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /**
     * A queue to cache log in memory
     */
    private val channel = Channel<Event>(CHANNEL_CAPACITY)

    init {
        scope.launch {
            channel.consumeEach { event ->
                event.apply { chain.proceed(tag, message, priority) }
            }
        }
    }

    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(message)) {
            scope.launch { channel.send(Event(tag, message, priority, chain)) }
        } else {
            chain.proceed(tag, message, priority)
        }
    }


    data class Event(val tag: String, val message: Any, val priority: Int, val chain: Chain)
}