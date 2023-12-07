package com.example.cafemobileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cafemobileapplication.Admin.DysplayAdminHome
import com.example.cafemobileapplication.Customer.DysplayCustomerHome

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instantiate the button widgets
        val btnCustomer: Button = findViewById(R.id.btnCustomer)
        val btnAdmin:Button = findViewById(R.id.btnAdmin)

        //Start the Customer Home page activity when admin is clicked
        btnCustomer.setOnClickListener{
            var send = Intent(this, DysplayCustomerHome::class.java)
            startActivity(send)
        }

        //Start the Admin Home page activity when admin is clicked
        btnAdmin.setOnClickListener{
            var send = Intent(this, DysplayAdminHome::class.java)
            startActivity(send)
        }


    }
}