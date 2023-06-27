package com.taylor.easylog.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.taylor.easylog.log

/**
 * An [Interceptor] for print [Map]
 */
class MapInterceptor<K, V> : Interceptor<Map<K, V>>() {
    override fun log(tag: String, message: Map<K, V>, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(message)) chain.proceed(tag, message.log(4), priority, args)
        else chain.proceed(tag, message, priority, args)
    }

}