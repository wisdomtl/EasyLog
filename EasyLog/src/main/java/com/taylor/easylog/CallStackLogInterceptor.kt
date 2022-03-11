package com.taylor.easylog

import java.lang.StringBuilder

class CallStackLogInterceptor(private val deep: Int) : LogInterceptor {
    companion object {
        private const val HEADER = "-------------------------------\n"
        private const val FOOTER = "\n-------------------------------"
    }

    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        val callStackLog = StringBuilder()
            .append(HEADER)
            .append(log)
            .append(getCallStack(listOf(
                CallStackLogInterceptor::class.java.name,
                EasyLog::class.java.name,
                Chain::class.java.name,
            )))
            .append(FOOTER).toString()
        chain.proceed(priority, tag, callStackLog)
    }

    override fun enable(): Boolean {
        return true
    }
}