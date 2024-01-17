package com.example.cafemobileapplication.Admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.cafemobileapplication.R

class AdminSendUpdates : AppCompatActivity() {
    //Variables
    lateinit var customer_firstName: EditText
    lateinit var customer_lastName: EditText
    lateinit var send_button: Button
    lateinit var redirect: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_send_updates)

        //Set the click event to redirect to register page
        redirect = findViewById(R.id.admin_sendUpdate_redirect)
        redirect.setOnClickListener{
            var send = Intent(this, AdminHomePage::class.java)
            startActivity(send)
        }

        //Find the views
        customer_firstName = findViewById(R.id.admin_sendUpdate_customerN)
        customer_lastName = findViewById(R.id.admin_sendUpdate_customerL)
        send_button = findViewById(R.id.admin_sendUpdate_btn)


    }

}