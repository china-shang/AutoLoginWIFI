package com.example.zh.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MyReceiver : BroadcastReceiver() {

    val TAG = "Receiver"

    class WIFIClient(var context: Context, val name: String,
                     val password: String, val show: Boolean) {

        val TAG = "WIFIClient"

        fun login() {

            if (!checkWIFI(context)) {
                return
            }

//            Toast.makeText(context,"checkWIFI Success",Toast.LENGTH_SHORT).show()
            val queue = Volley.newRequestQueue(context)
            val url = "http://10.1.1.3/eportal/InterFace.do?method=login"
            val request = object : StringRequest(Request.Method.POST, url,
                    Response.Listener {
                        Toast.makeText(context, "成功登录WIFI", Toast.LENGTH_SHORT).takeIf { show }?.show()
                    }, Response.ErrorListener {
                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).takeIf { show }?.show()
            }) {
                override fun getParams(): MutableMap<String, String> {
                    return mutableMapOf<String, String>().apply {
                        this["userId"] = name
                        this["password"] = password
                        this["operatorPwd"] = ""
                        this["service"] = ""
                        this["validcode"] = ""
                        this["queryString"] = "version%3D52472d53414d2b20506f7274616c20454e54455250524953455f342e30305f4275696c643230313531323138%26%26wlanuserip%3D10.10.98.41%26wlanacname%3D%26nasip%3D10.1.1.1%26wlanparameter%3D40-e2-10-f4-31-d9%26url%3Dhttp%3A%2F%2F123.123.123.123%2F%26userlocation%3Deth%2F1%2F0%2F0%0A33410%26t%3D"
                    }
                }
            }
            Thread.sleep(2000)
            queue.add(request)
        }

        private fun checkWIFI(context: Context): Boolean {
            return with(context.getSystemService(WifiManager::class.java)
                    .connectionInfo.ssid) {
                Log.d(TAG, this)
//                Toast.makeText(context,"checkWIFI:${this}",Toast.LENGTH_SHORT).show()
                this.contains("chinanet", true)
                        || this.contains("cmcc", true)
                        || this.contains("unicom", true)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {

        val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)

        if (networkInfo.isConnected) {
            val preference = context.getSharedPreferences("config", 0)
            val name = preference.getString("name", null)
            val password = preference.getString("password", null)

            if (password == null || name == null) {
                return
            }
            val wifiClient = WIFIClient(context, name, password, preference.getBoolean("show", true))
            wifiClient.login()
        }
    }
}
