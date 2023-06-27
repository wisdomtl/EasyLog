package com.zenmen.easylog_su

import com.taylor.easylog.EasyLog
import com.taylor.easylog.interceptor.LogcatInterceptor
import com.zenmen.easylog_su.interceptor.*

/**
 * A build-in log chain.
 * All Logs will be stored in mmkv and uploaded in batch
 */
fun EasyLog.simpleInit(size: Int, duration: Long, pipeline: Pipeline<*, *>) {
    EasyLog.apply {
        addInterceptor(LogcatInterceptor())
        addInterceptor(LinearInterceptor())
        addInterceptor(LogInterceptor())
        addInterceptor(SinkInterceptor(pipeline))
        addInterceptor(BatchInterceptor(size, duration, pipeline))
        addInterceptor(UploadInterceptor(pipeline))
    }
}

fun EasyLog.defaultInit() {
    EasyLog.apply {
        addInterceptor(LogcatInterceptor())
    }
}