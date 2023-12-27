package com.example.cafemobileapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cafemobileapplication.Admin.AdminLogin
import com.example.cafemobileapplication.Customer.DisplayCustomerHome

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instantiate the button widgets
        val btnCustomer: Button = findViewById(R.id.btnCustomer)
        val btnAdmin:Button = findViewById(R.id.btnAdmin)
        val backmain:Button = findViewById(R.id.Customer_BM_Button)

        //Start the Customer Home page activity when admin is clicked
        btnCustomer.setOnClickListener{
            var send = Intent(this, DisplayCustomerHome::class.java)
            startActivity(send)
        }

        //Start the Admin Home page activity when admin is clicked
        btnAdmin.setOnClickListener{
            var send = Intent(this, AdminLogin::class.java)
            startActivity(send)
        }

        //TAKE A LOOK AT THIS
        //return the customer from the login page to the main page
        backmain.setOnClickListener {
            var send = Intent(this, MainActivity::class.java)
            startActivity(send)
        }

    }
}