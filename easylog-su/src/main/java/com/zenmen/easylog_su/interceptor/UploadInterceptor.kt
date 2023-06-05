package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch

class UploadInterceptor(private val uploader: Uploader?) : Interceptor<LogBatch> {

    override fun log(message: LogBatch, tag: String, priority: Int, chain: Chain) {
        if (enable()) uploader?.upload(message, tag)
    }

    override fun enable(): Boolean = true

    interface Uploader {
        fun upload(messages: LogBatch, tag: String)
    }
}