package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.tencent.mmkv.MMKV
import com.zenmen.easylog_su.model.Log

class SinkInterceptor : Interceptor<Log> {
    private val mmkv by lazy { MMKV.defaultMMKV() }
    override fun log(tag: String, message: Log, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) mmkv.encode(message.id, message.converter.pack())
        chain.proceed(tag, message, priority)
    }

    override fun enable(): Boolean = true

}