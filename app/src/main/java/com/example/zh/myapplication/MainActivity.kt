package com.example.zh.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    
    private var name : String = ""
    private var password : String = ""
    
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        init()
        WIFIClient(this, name, password, checkBox.isChecked).login()
    }
    
    override fun onRestart() {
        super.onRestart()
        WIFIClient(this, name, password, checkBox.isChecked).login()
    }
    
    private fun init() {
        val prf = getSharedPreferences("config", 0)
        
        with(prf.getString("name", null)
        ) {
            if (this != null) {
                name = this
                nameText.setText(name)
            }
        }
        with(prf.getString("password", null)) {
            if (this != null) {
                password = this
                passwordText.setText(password)
            }
        }
        saveButton.setOnClickListener {
            prf.edit().apply {
                putString("name", nameText.text.toString())
                putString("password", passwordText.text.toString())
            }.apply()
            Toast.makeText(this, getString(R.string.save_success_tip), Toast.LENGTH_SHORT).show()
        }
        checkBox.setOnCheckedChangeListener { _, clicked ->
            prf.edit().putBoolean("show_tip", clicked).apply()
        }
    }
}
