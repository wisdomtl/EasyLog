package com.taylor.demo.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.EasyLog
import com.taylor.easylog.Interceptor
import com.taylor.easylog.getCallStack
import java.lang.StringBuilder

class CallStackInterceptor : Interceptor<Any>() {
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

    override fun log(tag: String, message: Any,  priority: Int, chain: Chain, vararg args: Any) {
        chain.proceed(tag, HEADER, priority)
        chain.proceed(tag,"$LEFT_BORDER$message", priority)
        getCallStack(blackList).forEach {
            val callStack = StringBuilder()
                .append(LEFT_BORDER)
                .append("\t${it}").toString()
            chain.proceed(tag,callStack,priority)
        }
        chain.proceed(tag, FOOTER, priority)
    }
}