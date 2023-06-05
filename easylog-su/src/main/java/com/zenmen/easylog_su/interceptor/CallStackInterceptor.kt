package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.EasyLog
import com.taylor.easylog.Interceptor
import java.lang.StringBuilder

class CallStackInterceptor : Interceptor<Any> {
    companion object {
        private const val HEADER =
            "┌──────────────────────────────────────────────────────────────────────────────────────────────────────"
        private const val FOOTER =
            "└──────────────────────────────────────────────────────────────────────────────────────────────────────"
        private const val LEFT_BORDER = '│'
        private val blackList = listOf(
            CallStackInterceptor::class.java.name,
            EasyLog::class.java.name,
            Chain::class.java.name,
        )
    }

    override fun log(message: Any, tag: String, priority: Int, chain: Chain) {
        chain.proceed(HEADER, tag, priority)
        chain.proceed("$LEFT_BORDER$message", tag, priority)
        getCallStack(blackList).forEach {
            val callStack = StringBuilder()
                .append(LEFT_BORDER)
                .append("\t${it}").toString()
            chain.proceed(callStack, tag, priority)
        }
        chain.proceed(FOOTER, tag, priority)
    }

    override fun enable(): Boolean {
        return true
    }
}