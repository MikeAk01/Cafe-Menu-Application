package com.example.cafemobileapplication.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cafemobileapplication.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DisplayCustomerHome : AppCompatActivity() {

    //declaring variables
    lateinit var c_add_items: CardView
    lateinit var c_browse_menu: CardView
    lateinit var c_modify_order: CardView
    lateinit var c_confirmation: CardView
    lateinit var c_write_feedback: CardView
    lateinit var c_logout: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_customer_home)

        //Create a listener when the user click one of the cards
        //view menu
        c_browse_menu = findViewById(R.id.Customer_menu_card)
        c_browse_menu.setOnClickListener{
            var send = Intent(this, CustomerMenu::class.java)
            startActivity(send)
        }



    }




}

