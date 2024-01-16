package com.example.cafemobileapplication.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.cafemobileapplication.Admin.AdminLogin
import com.example.cafemobileapplication.Admin.AdminReviewFeedback
import com.example.cafemobileapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DisplayCustomerHome : AppCompatActivity() {

    //declaring variables
    lateinit var c_add_items: CardView
    lateinit var c_browse_menu: CardView
    lateinit var c_modify_order: CardView
    lateinit var c_confirm: CardView
    lateinit var c_write_feedback: CardView
    lateinit var c_logout: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_page)

        //Create a listener when the user click one of the cards
        //view menu
        c_browse_menu = findViewById(R.id.Customer_menu_card)
        c_browse_menu.setOnClickListener {
            var send = Intent(this, CustomerMenu::class.java)
            startActivity(send)
        }

        //add item
        c_add_items = findViewById(R.id.Customer_add_item_card)
        c_add_items.setOnClickListener {
            var send = Intent(this, CustomerAddItem::class.java)
            startActivity(send)
        }

        //modify order
        c_modify_order = findViewById(R.id.Customer_modify_order_card)
        c_modify_order.setOnClickListener {
            var send = Intent(this, CustomerModify::class.java)
            startActivity(send)
        }

       // need to complete the confirmation slot

        //write feedback
        c_write_feedback = findViewById(R.id.Customer_write_feedback_card)
        c_write_feedback.setOnClickListener {
            var send = Intent(this, AdminReviewFeedback::class.java)
            startActivity(send)
        }

        //logout
        c_logout = findViewById(R.id.Customer_logout_card)
        c_logout.setOnClickListener{
            var send = Intent(this, AdminLogin::class.java)
            startActivity(send)
        }

    }
}

