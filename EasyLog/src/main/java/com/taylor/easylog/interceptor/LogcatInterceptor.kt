package com.taylor.easylog.interceptor

import android.util.Log
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor

/**
 * An [Interceptor] output log to logcat
 */
class LogcatInterceptor : Interceptor<Any> {
    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) Log.println(priority, tag, message.toString())
        chain.proceed(tag, message, priority, args)
    }

    override fun enable(): Boolean = true
}