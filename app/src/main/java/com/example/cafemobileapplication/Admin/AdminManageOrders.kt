package com.example.cafemobileapplication.Admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemobileapplication.Classes.Order
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminManageOrders : AppCompatActivity() {
    //Declare variables
    lateinit var recyclerView: RecyclerView
    lateinit var orderList: ArrayList<Order>
    lateinit var referenceDB: DatabaseReference
    lateinit var adapter: AdminOrderListAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_orders)

        //Create a support action bar to turn back to the admin home page
        var floating_back: FloatingActionButton = findViewById(R.id.floatingBackButton)
        floating_back.setOnClickListener{
            var send = Intent(this, AdminHomePage::class.java)
            startActivity(send)
        }

        //Get the widget
        recyclerView = findViewById(R.id.admin_recyclerView2)
        //Initialize Database reference
        referenceDB = FirebaseDatabase.getInstance().getReference("Orders")
        //Initialize list
        orderList = ArrayList<Order>()

        //Set recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminOrderListAdapter(this, orderList)
        recyclerView.adapter = adapter

        //Work on the DB
        referenceDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot: DataSnapshot in snapshot.children ){
                    val order = userSnapshot.getValue(Order::class.java)
                    if (order != null) {
                        orderList.add(order)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminManageOrders, "Error getting values", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminHomePage::class.java))
        finish()
    }

}