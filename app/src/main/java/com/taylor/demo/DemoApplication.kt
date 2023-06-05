package com.taylor.demo

import android.app.Application
import com.taylor.demo.protobuf.gen.adLog
import com.taylor.easylog.EasyLog
import com.taylor.easylog.LogcatInterceptor
import com.zenmen.easylog_proto.LogWrapperInterceptor
import com.zenmen.easylog_su.interceptor.BatchInterceptor
import com.zenmen.easylog_su.interceptor.LinearInterceptor
import com.zenmen.easylog_su.interceptor.SinkInterceptor
import com.zenmen.easylog_su.interceptor.UploadInterceptor


class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * ensure EasyLog do init before other module, which also depends on EasyLog
         */
        initEasyLog()
        initTaylorSdk()
    }

    /**
     * init another module
     */
    private fun initTaylorSdk() {
//        TaylorSdk()
    }

    private fun initEasyLog() {
        EasyLog.apply {
            addInterceptor(LogcatInterceptor())
            addInterceptor(LinearInterceptor())
            addInterceptor(LogWrapperInterceptor())
            addInterceptor(SinkInterceptor(this@DemoApplication))
            addInterceptor(BatchInterceptor(5, 10_000))
            addInterceptor(UploadInterceptor())
        }
        repeat(5) {
            EasyLog.logMessage(adLog {
                name = "native"
                count = it
                isOpen = true
            }, "ttaylor2222")
        }
    }
}