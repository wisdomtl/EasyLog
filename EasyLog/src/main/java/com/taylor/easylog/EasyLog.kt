package com.taylor.easylog

import androidx.core.graphics.rotationMatrix
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

object EasyLog {

    /**
     * Priority constant for the println method; use Log.v.
     */
    const val VERBOSE = 2

    /**
     * Priority constant for the println method; use Log.d.
     */
    const val DEBUG = 3

    /**
     * Priority constant for the println method; use Log.i.
     */
    const val INFO = 4

    /**
     * Priority constant for the println method; use Log.w.
     */
    const val WARN = 5

    /**
     * Priority constant for the println method; use Log.e.
     */
    const val ERROR = 6

    /**
     * Priority constant for the println method.
     */
    const val ASSERT = 7

    private val interceptors = mutableListOf<Interceptor<in Nothing>>()
    private val chain = Chain(interceptors)


//    fun d(message: String, tag: String = "", vararg args: Any) {
//        innerLog(DEBUG, message, tag, *args)
//    }
//
//    fun e(message: String, tag: String = "", vararg args: Any, throwable: Throwable? = null) {
//        innerLog(ERROR, message, tag, *args, throwable = throwable)
//    }
//
//    fun w(message: String, tag: String = "", vararg args: Any) {
//        innerLog(WARN, message, tag, *args)
//    }
//
//    fun i(message: String, tag: String = "", vararg args: Any) {
//        innerLog(INFO, message, tag, *args)
//    }
//
//    fun v(message: String, tag: String = "", vararg args: Any) {
//        innerLog(VERBOSE, message, tag, *args)
//    }
//
//    fun wtf(message: String, tag: String = "", vararg args: Any) {
//        innerLog(ASSERT, message, tag, *args)
//    }

    fun log(message: Any,priority: Int = VERBOSE, vararg args: Any ) {
        chain.proceed(message, priority, *args)
    }

    fun tag(tag: String): EasyLog {
        interceptors.forEach { it.tag = tag }
        return this
    }

    fun addInterceptor(interceptor: Interceptor<*>) {
        addInterceptor(interceptors.size, interceptor)
    }

    fun addInterceptor(index: Int, interceptor: Interceptor<*>) {
        interceptors.add(index, interceptor)
    }

    fun removeInterceptor(interceptor: Interceptor<*>) {
        interceptors.remove(interceptor)
    }

    @Synchronized
    private fun innerLog(
        priority: Int,
        message: String,
        tag: String,
        vararg args: Any,
        throwable: Throwable? = null
    ) {
        val logMessage = formatLog(message, args, throwable)
        chain.proceed(logMessage, priority)
    }

    private fun formatLog(message: String, args: Array<out Any>, throwable: Throwable?): String {
        var logMessage = message.format(*args)
        if (throwable != null) {
            logMessage += getStackTraceString(throwable)
        }
        return logMessage
    }


    private fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
}