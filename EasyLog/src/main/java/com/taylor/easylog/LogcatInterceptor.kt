package com.taylor.easylog

import android.util.Log

class LogcatInterceptor : Interceptor<Any>() {
    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) Log.println(priority, tag, message.toString())
        chain.proceed(tag, message, priority, args)
    }

    override fun enable(): Boolean = true
}