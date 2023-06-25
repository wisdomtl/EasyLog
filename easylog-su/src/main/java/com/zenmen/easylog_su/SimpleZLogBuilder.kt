package com.zenmen.easylog_su

import com.taylor.easylog.EasyLog
import com.taylor.easylog.interceptor.FormatInterceptor
import com.taylor.easylog.interceptor.LogcatInterceptor
import com.zenmen.easylog_su.interceptor.*

/**
 * A build-in log chain. The log will be processed like the following:
 * 1. output to logcat
 * 2. log in sequence even if in multi-thread situation
 * 3. be wrapped with an unique id if log is defined by protobuf
 * 4. be stored is sdcard
 * 5. be batched to upload
 */
fun EasyLog.simpleInit(size: Int, duration: Long, batcher: Batcher<*, *>) {
    EasyLog.apply {
        //        addInterceptor(FormatInterceptor())
        //        addInterceptor(LogcatInterceptor())
        addInterceptor(LinearInterceptor())
        addInterceptor(LogInterceptor())
        addInterceptor(SinkInterceptor(batcher))
        addInterceptor(BatchInterceptor(size, duration, batcher))
        addInterceptor(UploadInterceptor(batcher))
    }
}

fun EasyLog.defaultInit() {
    EasyLog.apply {
        addInterceptor(FormatInterceptor())
        addInterceptor(LogcatInterceptor())
    }
}