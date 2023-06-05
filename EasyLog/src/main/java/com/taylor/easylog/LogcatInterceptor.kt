package com.taylor.easylog

import android.util.Log

open class LogcatInterceptor : Interceptor<Any> {
    override fun log(message: Any, tag: String, priority: Int, chain: Chain) {
        if (enable()) Log.println(priority, tag, message.toString())
        chain.proceed(message, tag, priority)
    }

    override fun enable(): Boolean {
        return true
    }
}