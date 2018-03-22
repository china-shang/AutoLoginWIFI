package com.example.zh.myapplication

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences("config", 0)

        val name = preferences.getString("name", null)
        nameText?.setText(name)
        val password = preferences.getString("password", null)
        passwordText?.setText(password)

        saveButton.setOnClickListener {
            preferences.edit().apply {
                this.putString("name", nameText.text.toString())
                this.putString("password", passwordText.text.toString())
            }.apply()
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
        }

        checkBox.setOnCheckedChangeListener { _, clicked ->
            preferences.edit().putBoolean("show", clicked).apply()
        }
    }

}
