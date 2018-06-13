package com.example.zh.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.WifiManager

class MyReceiver : BroadcastReceiver() {
    
    val TAG = "Receiver"
    
    override fun onReceive(context : Context, intent : Intent) {
        
        val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
        
        if (networkInfo.isConnected) {
            with(context.getSharedPreferences("config", 0)) {
                val name = getString("name", "")
                val password = getString("password", "")
                if (!checkParams(password, name)) return
                
                WIFIClient(context, name,
                        password, getBoolean("show_tip", true))
                        .login()
            }
        }
    }
    
    private fun checkParams(password : String?, name : String?) : Boolean {
        if (password.isNullOrEmpty() || name.isNullOrEmpty()) {
            return false
        }
        return true
    }
}
