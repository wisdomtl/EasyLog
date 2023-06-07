package com.taylor.easylog.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import java.io.PrintWriter
import java.io.StringWriter

open class FormatInterceptor : Interceptor<Any>() {

    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) chain.proceed(tag, getFormatLog(message, *args), priority, *args)
        else chain.proceed(tag, message, priority, *args)
    }

    override fun enable(): Boolean = true

    /**
     * Print [message] with call stack or formatted by [args]
     */
    private fun getFormatLog(message: Any, vararg args: Any) =
        if (message is Throwable)
            getStackTraceString(message)
        else
            if (args.isNotEmpty()) message.toString().format(args)
            else message.toString()

    private fun getStackTraceString(t: Throwable): String {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    private fun String.format(args: Array<out Any>) = if (args.isEmpty()) this else String.format(this, *args)
}