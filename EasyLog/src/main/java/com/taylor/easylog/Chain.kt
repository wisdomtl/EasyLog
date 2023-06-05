package com.taylor.easylog

import android.icu.util.ChineseCalendar
import java.lang.reflect.ParameterizedType

class Chain(
    private val interceptors: List<Interceptor<*>>,
    private val index: Int = 0
) {

    fun proceed(message: Any, tag: String, priority: Int) {
        val next = Chain(interceptors, index + 1)// new Chain every time to avoid multi-thread problem(index is val)
        (interceptors.getOrNull(index) as? Interceptor<Any>)?.log(message, tag, priority, next)
    }

    fun proceed(vararg message: Any, tag: String, priority: Int) {
        val next = Chain(interceptors, index + 1)// new Chain every time to avoid multi-thread problem(index is val)
        (interceptors.getOrNull(index) as? Interceptor<Any>)?.logBatch(messages = message, tag, priority, next)
    }
}