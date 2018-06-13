package com.example.zh.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class WIFIClient(val context : Context, val name: String,
                 val password : String, val show_tip : Boolean) {
    init {
        if(name.isNullOrEmpty()||password.isNullOrEmpty()){
            Toast.makeText(context,R.string.cantBeEmpty , Toast.LENGTH_SHORT).show()
        }
    }

    private val TAG = "WIFIClient"
    private val URL = "http://10.1.1.3/eportal/InterFace.do?method=login"
    
    fun login() {
        if (!checkWIFI(context)) {
            return
        }
        sendLoginRequest()
    }
    
    private fun sendLoginRequest() {
        val request = object : StringRequest(Method.POST, URL,
                Response.Listener {
                    if (show_tip) {
                        Toast.makeText(context, context.getString(R.string.success_tip),
                                Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    if (show_tip) {
                        Toast.makeText(context, context.getString(R.string.failure_tip),
                                Toast.LENGTH_SHORT).show()
                    }
                }
        ) {
            override fun getParams() : MutableMap<String, String> {
            
                return hashMapOf(
                        "userId" to name,
                        "password" to password,
                        "operatorPwd" to "",
                        "service" to "",
                        "validcode" to "",
                        "queryString" to "version%3D52472d53414d2b20506f7274616c20454e54455250524953455f342e30305f4275696c643230313531323138%26%26wlanuserip%3D10.10.98.41%26wlanacname%3D%26nasip%3D10.1.1.1%26wlanparameter%3D40-e2-10-f4-31-d9%26url%3Dhttp%3A%2F%2F123.123.123.123%2F%26userlocation%3Deth%2F1%2F0%2F0%0A33410%26t%3D"
                )
            }
        }
    
        val queue = Volley.newRequestQueue(context).add(request)
    }
    
    private fun String.isCorrectWIFI() : Boolean {
        return (contains("chinanet", true)
                || contains("cmcc", true)
                || contains("unicom", true))
    }
    
    private fun checkWIFI(context : Context) : Boolean {
        if (context.getSystemService(ConnectivityManager::class.java)
                        .activeNetworkInfo.type != ConnectivityManager.TYPE_WIFI) {
            return false
        }
        return context.getSystemService(WifiManager::class.java)
                .connectionInfo
                .ssid
                .isCorrectWIFI()
    }
    
}
