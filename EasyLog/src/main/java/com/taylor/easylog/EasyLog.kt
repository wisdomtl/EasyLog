package com.taylor.easylog

import android.annotation.SuppressLint
import android.content.Context
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

    val interceptors = mutableListOf<Interceptor<in Nothing>>()
    val interceptorClasses = mutableListOf<Class<*>>()
    private val chain = Chain(interceptors, interceptorClasses)


    fun d(message: String, tag: String = "", vararg args: Any) {
        log(DEBUG, message, tag, *args)
    }

    fun e(message: String, tag: String = "", vararg args: Any, throwable: Throwable? = null) {
        log(ERROR, message, tag, *args, throwable = throwable)
    }

    fun w(message: String, tag: String = "", vararg args: Any) {
        log(WARN, message, tag, *args)
    }

    fun i(message: String, tag: String = "", vararg args: Any) {
        log(INFO, message, tag, *args)
    }

    fun v(message: String, tag: String = "", vararg args: Any) {
        log(VERBOSE, message, tag, *args)
    }

    fun wtf(message: String, tag: String = "", vararg args: Any) {
        log(ASSERT, message, tag, *args)
    }

    fun logMessage(message: Any, tag: String, priority: Int = VERBOSE) {
        chain.proceed(message, tag, priority)
    }

    inline fun <reified T> addInterceptor(interceptor: Interceptor<T>) {
        addInterceptor(interceptors.size, interceptor)
    }

    inline fun <reified T> addInterceptor(index: Int, interceptor: Interceptor<T>) {
        interceptors.add(index, interceptor)
        interceptorClasses.add(index, T::class.java)
    }

    fun removeInterceptor(interceptor: Interceptor<*>) {
        interceptors.remove(interceptor)
    }

    @Synchronized
    private fun log(
        priority: Int,
        message: String,
        tag: String,
        vararg args: Any,
        throwable: Throwable? = null
    ) {
        val logMessage = formatLog(message, args, throwable)
        chain.proceed(logMessage, tag, priority)
    }

    private fun formatLog(message: String, args: Array<out Any>, throwable: Throwable?): String {
        var logMessage = message.format(*args)
        if (throwable != null) {
            logMessage += getStackTraceString(throwable)
        }
        return logMessage
    }

    private fun String.format(vararg args: Any) =
        if (args.isNullOrEmpty()) this else String.format(this, *args)

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