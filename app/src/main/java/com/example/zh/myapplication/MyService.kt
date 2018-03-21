package com.example.zh.myapplication

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.IBinder

class MyService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
//        Toast.makeText(this,"OnServiceCreate",Toast.LENGTH_LONG).show()

        val myFilter= IntentFilter()
//        myFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        myFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
//        unregisterReceiver(MyReceiver())
        registerReceiver(MyReceiver(),myFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(MyReceiver())
    }
}
