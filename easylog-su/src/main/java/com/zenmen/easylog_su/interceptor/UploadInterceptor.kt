package com.zenmen.easylog_su.interceptor

import com.taylor.easylog.Chain
import com.taylor.easylog.Interceptor
import com.zenmen.easylog_su.Batcher
import com.zenmen.easylog_su.model.LogBatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class UploadInterceptor<LOG, LOGS>(private val batcher: Batcher<LOG, LOGS>) : Interceptor<LogBatch<LOGS>> {
    private val scope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    override fun log(tag: String, logs: LogBatch<LOGS>, priority: Int, chain: Chain, vararg args: Any) {
        if (enable()) scope.launch {
            val result = batcher.upload(logs.data)
            if (result) SinkInterceptor.mmkv.removeValuesForKeys(logs.ids.toTypedArray())
        }
    }

    override fun enable(): Boolean = true
}