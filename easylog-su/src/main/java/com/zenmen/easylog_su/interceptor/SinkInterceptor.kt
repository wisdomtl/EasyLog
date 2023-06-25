package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.tencent.mmkv.MMKV
import com.zenmen.easylog_su.Batcher
import com.zenmen.easylog_su.model.Log

class SinkInterceptor<LOG>(private val batcher: Batcher<LOG, *>) : Interceptor<Log<LOG>> {
    companion object {
        val mmkv by lazy { MMKV.defaultMMKV() }
    }

    override fun log(tag: String, message: Log<LOG>, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) mmkv.encode(message.id, batcher.toByteArray(message.data))
        chain.proceed(tag, message, priority)
    }

    override fun enable(): Boolean = true

}