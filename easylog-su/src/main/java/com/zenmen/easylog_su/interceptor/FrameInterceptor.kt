package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor

class FrameInterceptor : Interceptor<Any> {
    private val HEADER =
        "┌──────────────────────────────────────────────────────────────────────────────────────────────────────"
    private val FOOTER =
        "└──────────────────────────────────────────────────────────────────────────────────────────────────────"
    private val LEFT_BORDER = '│'

    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        val msg = HEADER + "\n" + LEFT_BORDER + message + "\n" + FOOTER
        chain.proceed(tag,msg, priority, args)
    }

    override fun enable(): Boolean = true
}