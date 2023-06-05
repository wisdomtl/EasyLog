package com.taylor.easylog

import java.lang.IllegalArgumentException

class Chain(
    private val interceptors: List<Interceptor<in Nothing>>,
    private val interceptorClasses: List<Class<*>>,
    private val index: Int = 0
) {

    fun proceed(message: Any, tag: String, priority: Int) {
        if(message::class.java == interceptorClasses.getOrNull(index)) {
            val next = Chain(interceptors, interceptorClasses, index + 1)// new Chain every time to avoid multi-thread problem(index is val)
            (interceptors.getOrNull(index) as? Interceptor<Any>)?.log(message, tag, priority, next)
        }else {
            if (BuildConfig.DEBUG) throw IllegalArgumentException("wrong type parameter in the ${index}th Interceptor, chain break")
        }
    }

    fun proceed(vararg message: Any, tag: String, priority: Int) {
        if(message::class.java == interceptorClasses.getOrNull(index)) {
            val next = Chain(interceptors, interceptorClasses, index + 1)// new Chain every time to avoid multi-thread problem(index is val)
            (interceptors.getOrNull(index) as? Interceptor<Any>)?.logBatch(messages = message, tag, priority, next)
        }else {
            if (BuildConfig.DEBUG) throw IllegalArgumentException("wrong type parameter in the ${index}th Interceptor, chain break")
        }
    }
}