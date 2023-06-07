package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor

class BeautyLogInterceptor : Interceptor<Any> {
    override fun log(tag: String, message: Any,  priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) {
            chain.proceed(tag,message.toString().format(args),priority, args)
        }
    }

    override fun enable(): Boolean = true

    private fun String.format(vararg args: Any) = if (args.isEmpty()) this else String.format(this, *args)
}