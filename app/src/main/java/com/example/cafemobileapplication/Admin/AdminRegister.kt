package com.example.cafemobileapplication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cafemobileapplication.R

class AdminRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_admin_register)

        //Set the click event to redirect to login page
        var redirect: TextView = findViewById(R.id.Admin_redirect_login)
        redirect.setOnClickListener{
            var send = Intent(this, AdminLogin::class.java)
            startActivity(send)
        }
    }
}