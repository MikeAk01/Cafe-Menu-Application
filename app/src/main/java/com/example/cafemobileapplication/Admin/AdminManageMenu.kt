package com.example.cafemobileapplication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminManageMenu : AppCompatActivity() {
    //Declare variables
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<Product>
    lateinit var referenceDB: DatabaseReference
    lateinit var adapter: AdminProductListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_menu)

        //Get the widget
        recyclerView = findViewById(R.id.admin_recyclerView)
        //Initialize Database reference
        referenceDB = FirebaseDatabase.getInstance().getReference("Products")
        //Initialize list
        list = ArrayList<Product>()

        //Set recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminProductListAdapter(this, list)
        recyclerView.adapter = adapter

        //Work on the DB
        referenceDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot: DataSnapshot in snapshot.children ){
                    val product = userSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        list.add(product)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminManageMenu, "Error getting values", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminHomePage::class.java))
        finish()
    }
}