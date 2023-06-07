package com.taylor.easylog

import android.os.Build
import com.taylor.easylog.interceptor.FormatInterceptor
import com.taylor.easylog.interceptor.ListInterceptor
import com.taylor.easylog.interceptor.MapInterceptor
import java.util.regex.Pattern


/**
 * A flexible log lib which logging process can be customized through [Interceptor].
 * Inspired by Timber and Logger
 */
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

    /**
     * A transient [Interceptor] only for next logging
     */
    private var onetimeInterceptor: ThreadLocal<Interceptor<*>>? = null

    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    private val MAX_TAG_LENGTH = 23

    /**
     * A transient tag only for next logging
     */
    private var onetimeTag = ThreadLocal<String>()
    private var tag: String?
        get() = onetimeTag.get()?.also { onetimeTag.remove() }
        set(value) {
            onetimeTag.set(value)
        }

    /**
     * Class names exclude from call stack
     */
    private val blackList = listOf(
        EasyLog::class.java.name,
        FormatInterceptor::class.java.name,
        Chain::class.java.name
    )

    /**
     * The entry point for logging POJO
     * @param message the object to be logged
     * @param priority the log level in logcat
     * @param args the formatted args
     */
    fun log(message: Any, priority: Int = VERBOSE, vararg args: Any) {
        chain.proceed(createTag(), message, priority, *args)
        onetimeInterceptor?.takeIf { it.get() != null }?.also { removeInterceptor(it.get()) } // remove one time interceptor
    }

    /**
     * The entry point for logging [Iterable]
     * @param message the Iterator to log
     * @param priority the log level in logcat
     * @param map define what to log of <T>
     */
    fun <T> list(message: Iterable<T>, priority: Int = VERBOSE, map: ((T) -> String)? = null) {
        interceptor(ListInterceptor(map))
        chain.proceed(createTag(), message, priority)
        onetimeInterceptor?.takeIf { it.get() != null }?.also { removeInterceptor(it.get()) } // remove one time interceptor
    }

    /**
     * The entry point for logging [Map]
     * @param message the Iterator to log
     * @param priority the log level in logcat
     */
    fun <K, V> map(message: Map<K, V>, priority: Int = VERBOSE) {
        interceptor(MapInterceptor<K, V>())
        chain.proceed(createTag(), message, priority)
        onetimeInterceptor?.takeIf { it.get() != null }?.also { removeInterceptor(it.get()) } // remove one time interceptor
    }

    /**
     * Add one time tag for the next logging
     */
    fun tag(tag: String): EasyLog {
        interceptors.forEach { it.tag = tag }
        return this
    }

    /**
     * Add [Interceptor] for customizing log process
     */
    fun addInterceptor(interceptor: Interceptor<*>) {
        addInterceptor(interceptors.size, interceptor)
    }

    /**
     * Add [Interceptor] at [index] for customizing log process
     */
    fun addInterceptor(index: Int, interceptor: Interceptor<*>) {
        interceptors.add(index, interceptor)
    }

    /**
     * Add one time [Interceptor]
     */
    fun interceptor(interceptor: Interceptor<*>): EasyLog {
        interceptors.add(0, interceptor)// always add log
        if (onetimeInterceptor == null) onetimeInterceptor = ThreadLocal()
        onetimeInterceptor?.set(interceptor)
        return this
    }

    /**
     * Remove [interceptor]
     */
    fun removeInterceptor(interceptor: Interceptor<*>?) {
        interceptors.remove(interceptor)
    }

    /**
     * Create one-time tag for log, which may be set by [EasyLog.tag] or auto-generate with the host class name
     */
    private fun createTag(): String {
        return tag ?: Throwable().stackTrace
            .first { it.className !in blackList }
            .let(::createStackElementTag)
    }

    /**
     * Generate call stack for [Throwable]
     */
    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className.substringAfterLast('.')
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        // Tag length limit was removed in API 26.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
            tag
        } else {
            tag.substring(0, MAX_TAG_LENGTH)
        }
    }
}