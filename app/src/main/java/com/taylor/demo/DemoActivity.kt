package com.taylor.demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.taylor.demo.protobuf.gen.loadFail
import com.taylor.easylog.EasyLog
import com.taylor.easylog.R

class DemoActivity : AppCompatActivity() {

    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity)
        findViewById<TextView>(R.id.tv).setOnClickListener {
            EasyLog.log(loadFail {
                message = "fail"
                code = count++
            })
        }
        //        EasyLog.v("DemoActivity.onCreate()","taylor")
    }
}