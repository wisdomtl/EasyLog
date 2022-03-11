package com.taylor.demo

import android.app.Application
import com.taylor.easylog.EasyLog
import com.taylor.easylog.LogcatInterceptor
import com.taylor.easylog.OkioLogInterceptor
import com.taylor.module1.TaylorSdk

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
        TaylorSdk()
    }

    private fun initEasyLog() {
        EasyLog.addInterceptor(LogcatInterceptor())
        EasyLog.addInterceptor(OkioLogInterceptor.getInstance(this.filesDir.absolutePath))
    }
}