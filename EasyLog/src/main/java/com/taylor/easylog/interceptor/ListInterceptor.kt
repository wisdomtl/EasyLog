package com.taylor.easylog.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.taylor.easylog.log

/**
 * An [Interceptor] for print [Iterable]
 */
class ListInterceptor<T>(private val map: ((T) -> String)?) : Interceptor<Iterable<T>>() {
    override fun log(tag: String, message: Iterable<T>, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(message)) {
            val messageList = message.log { map?.invoke(it) ?: it.toString() }
            chain.proceed(tag, messageList, priority, args)
        } else {
            chain.proceed(tag, message, priority, args)
        }
    }

}