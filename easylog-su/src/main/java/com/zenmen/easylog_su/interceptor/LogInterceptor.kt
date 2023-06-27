package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.model.Log
import java.util.UUID

class LogInterceptor : Interceptor<Any>() {
    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(message)) chain.proceed(tag, Log(UUID.randomUUID().toString(), message), priority, args)
    }

}