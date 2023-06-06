package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.proto.gen.LogOuterClass.LogBatch

class UploadInterceptor(private val uploader: Uploader?) : Interceptor<LogBatch>() {

    override fun log(message: LogBatch, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) uploader?.upload(message )
    }

    override fun enable(): Boolean = true

    interface Uploader {
        fun upload(messages: LogBatch)
    }
}