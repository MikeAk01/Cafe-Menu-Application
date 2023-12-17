package com.example.cafemobileapplication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cafemobileapplication.R

class AdminLogin : AppCompatActivity() {
    //Declare variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_admin_login)

        //Set the click event to redirect to register page
        var redirect:TextView = findViewById(R.id.Admin_redirect_register)
        redirect.setOnClickListener{
            var send = Intent(this, AdminRegister::class.java)
            startActivity(send)
        }
    }
}