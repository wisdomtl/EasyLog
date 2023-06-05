package com.zenmen.easylog_su.interceptor

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import okio.BufferedSink
import okio.appendingSink
import okio.buffer
import okio.gzip
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class OkioInterceptor private constructor(private var dir: String) : Interceptor<Any> {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private var startTime = System.currentTimeMillis()
    private var bufferedSink: BufferedSink? = null
    private var logFile = File(getFileName())

    val callback = Handler.Callback { message ->
        val sink = checkSink()
        when (message.what) {
            TYPE_FLUSH -> {
                sink.use {
                    it.flush()
                    bufferedSink = null
                }
            }
            TYPE_LOG -> {
                val log = message.obj as String
                sink.writeUtf8(log)
                sink.writeUtf8("\n")
            }
        }
        false
    }

    companion object {
        private const val TYPE_FLUSH = -1
        private const val TYPE_LOG = 1
        private const val FLUSH_LOG_DELAY_MILLIS = 3000L

        @Volatile
        private var INSTANCE: OkioInterceptor? = null

        fun getInstance(dir: String): OkioInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: OkioInterceptor(dir).apply { INSTANCE = this }
            }
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper, callback)
    }

    override fun log(message: Any, tag: String, priority: Int, chain: Chain) {
        // prevent HandlerThread being killed
        if (!handlerThread.isAlive) handlerThread.start()
        handler.run {
            removeMessages(TYPE_FLUSH)
            obtainMessage(TYPE_LOG, "[$tag] $message").sendToTarget()
            val flushMessage = handler.obtainMessage(TYPE_FLUSH)
            sendMessageDelayed(flushMessage, FLUSH_LOG_DELAY_MILLIS)
        }
        chain.proceed(message, tag, priority)
    }

    override fun enable(): Boolean {
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun getToday(): String =
        SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

    private fun getFileName() = "$dir${File.separator}${getToday()}.log"

    private fun checkSink(): BufferedSink {
        if (bufferedSink == null) {
            bufferedSink = logFile.appendingSink().gzip().buffer()
        }
        return bufferedSink!!
    }
}