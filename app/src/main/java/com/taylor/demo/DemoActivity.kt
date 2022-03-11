package com.taylor.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taylor.easylog.EasyLog

class DemoActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EasyLog.v("DemoActivity.onCreate()","taylor")
    }
}