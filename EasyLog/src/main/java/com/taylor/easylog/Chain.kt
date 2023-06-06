package com.taylor.easylog


class Chain(
    private val interceptors: List<Interceptor<in Nothing>>,
    private val index: Int = 0
) {

    fun proceed(message: Any, priority: Int, vararg args: Any) {
        val next = Chain(interceptors, index + 1)// new Chain every time to avoid multi-thread problem(index is val)
        try {
            (interceptors.getOrNull(index) as? Interceptor<Any>)?.log(message, priority, next,*args)
        } catch (e: Exception) {
            // todo find another interceptor
        }
    }
}