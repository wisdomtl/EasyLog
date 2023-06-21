package com.zenmen.easylog_su.interceptor


import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.model.Converter
import com.zenmen.easylog_su.model.Log
import java.util.*

class LogInterceptor : Interceptor<Converter> {

    override fun log(tag: String, message: Converter, priority: Int, chain: Chain, vararg args: kotlin.Any) {
        val log = Log(UUID.randomUUID().toString(), message)
        chain.proceed(tag, log, priority)
    }

    override fun enable(): Boolean = true
}