package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_proto.proto.gen.LogOuterClass.Log

class UploadInterceptor : Interceptor<Log> {

    override fun logBatch(vararg messages: Log, tag: String, priority: Int, chain: Chain) {
        android.util.Log.e("ttaylor", "UploadInterceptor.logBatch() messages=${messages.fold("") { acc: String, log: Log -> acc + log.toString() }}");
    }

    override fun enable(): Boolean = true
}