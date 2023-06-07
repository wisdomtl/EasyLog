package com.zenmen.easylog_su

import com.taylor.easylog.EasyLog
import com.taylor.easylog.interceptor.FormatInterceptor
import com.taylor.easylog.interceptor.LogcatInterceptor
import com.zenmen.easylog_su.interceptor.BatchInterceptor
import com.zenmen.easylog_su.interceptor.LinearInterceptor
import com.zenmen.easylog_su.interceptor.LogWrapperInterceptor
import com.zenmen.easylog_su.interceptor.SinkInterceptor
import com.zenmen.easylog_su.interceptor.UploadInterceptor

/**
 * A build-in log chain. The log will be processed like the following:
 * 1. output to logcat
 * 2. log in sequence even if in multi-thread situation
 * 3. be wrapped with an unique id if log is defined by protobuf
 * 4. be stored is sdcard
 * 5. be batched to upload
 */
fun EasyLog.simpleInit(size: Int, duration: Long, sink: SinkInterceptor.Sink, uploader: UploadInterceptor.Uploader) {
    EasyLog.apply {
        addInterceptor(FormatInterceptor())
        addInterceptor(LogcatInterceptor())
        addInterceptor(LinearInterceptor())
        addInterceptor(LogWrapperInterceptor())
        addInterceptor(SinkInterceptor(sink))
        addInterceptor(BatchInterceptor(size, duration))
        addInterceptor(UploadInterceptor(uploader))
    }
}

fun EasyLog.defaultInit() {
    EasyLog.apply {
        addInterceptor(FormatInterceptor())
        addInterceptor(LogcatInterceptor())
    }
}