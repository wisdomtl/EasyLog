package com.taylor.easylog

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern

open class LogcatInterceptor : Interceptor<Any>() {
    private val onetimeTag = ThreadLocal<String>()
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    private val MAX_TAG_LENGTH = 23
    override var tag: String?
        get() = onetimeTag.get()?.also { onetimeTag.remove() }
        set(value) {
            onetimeTag.set(value)
        }

    private val blackList = listOf(
        Interceptor::class.java.name,
        EasyLog::class.java.name,
        LogcatInterceptor::class.java.name,
        Chain::class.java.name
    )

    override fun log(message: Any, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) Log.println(priority, createTag(), getBeautyLog(message, *args))
        chain.proceed(message, priority)
    }


    override fun enable(): Boolean = true

    private fun getBeautyLog(message: Any, vararg args:Any) =
        if (message is Throwable)
            getStackTraceString(message)
        else
            if (args.isNotEmpty()) message.toString().format(args)
            else message.toString()

    private fun createTag(): String? {
        return tag ?: Throwable().stackTrace
            .first { it.className !in blackList }
            .let(::createStackElementTag)
    }

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

    private fun createStackElementTag(element: StackTraceElement): String? {
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