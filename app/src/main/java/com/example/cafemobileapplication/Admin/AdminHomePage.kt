package com.example.cafemobileapplication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.cafemobileapplication.Classes.LoggedInAdminList
import com.example.cafemobileapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminHomePage : AppCompatActivity() {
    //Declare variables
    lateinit var c_manage_menu: CardView
    lateinit var c_manage_orders: CardView
    lateinit var c_add_items: CardView
    lateinit var c_send_updates: CardView
    lateinit var c_review_feedback: CardView
    lateinit var c_logout: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)

        //Create a listener when the user click one of the cards
        //Manage menu
        c_manage_menu = findViewById(R.id.admin_manage_menu_card)
        c_manage_menu.setOnClickListener{
            var send = Intent(this, AdminManageMenu::class.java)
            startActivity(send)
        }
        //Manage orders
        c_manage_orders = findViewById(R.id.admin_manage_order_card)
        c_manage_orders.setOnClickListener{
            var send = Intent(this, AdminManageOrders::class.java)
            startActivity(send)
        }
        //Add items
        c_add_items = findViewById(R.id.admin_add_item_card)
        c_add_items.setOnClickListener{
            var send = Intent(this, AdminAddItem::class.java)
            startActivity(send)
        }

        //Send updates
        c_send_updates = findViewById(R.id.admin_send_updates_card)
        c_send_updates.setOnClickListener{
            //Send the logged in email from the admin
            val receivedIntent = intent
            val email = receivedIntent.getStringExtra("EMAIL_EXTRA")
            var intent = Intent(this, AdminSendUpdates::class.java)
            intent.putExtra("EMAIL_EXTRA", email)
            startActivity(intent)
        }
        //Review feedback
        c_review_feedback = findViewById(R.id.admin_review_feedback_card)
        c_review_feedback.setOnClickListener{
            var send = Intent(this, AdminReviewFeedback::class.java)
            startActivity(send)
        }
        //logout
        c_logout = findViewById(R.id.admin_logout_card)
        c_logout.setOnClickListener{
            var send = Intent(this, AdminLogin::class.java)
            startActivity(send)
        }

    }
}