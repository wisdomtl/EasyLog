package com.taylor.easylog.interceptor

import android.util.Log
import com.taylor.easylog.Chain
import com.taylor.easylog.EasyLog
import com.taylor.easylog.Interceptor
import java.io.PrintWriter
import java.io.StringWriter

/**
 * An [Interceptor] output log to logcat
 */
class LogcatInterceptor : Interceptor<Any>() {
    override fun log(tag: String, message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (isLoggable(message) && EasyLog.curPriority <= priority && EasyLog.curPriority != EasyLog.NONE) Log.println(priority, tag, getFormatLog(message, *args))
        chain.proceed(tag, message, priority, args)
    }

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