package com.example.cafemobileapplication.Customer
import android.content.Intent
import com.example.cafemobileapplication.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemobileapplication.Admin.AdminHomePage
import com.example.cafemobileapplication.Admin.AdminOrderListAdapter
import com.example.cafemobileapplication.Classes.Order
import com.example.cafemobileapplication.Classes.Update
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerConfirmation : AppCompatActivity() {
    //Declare variables
    lateinit var recyclerView: RecyclerView
    lateinit var updateList: ArrayList<Update>
    lateinit var referenceDB: DatabaseReference
    lateinit var adapter: CustomerUpdateListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_confirmation)

        //Create a support action bar to turn back to the admin home page
        var floating_back: FloatingActionButton = findViewById(R.id.floatingBackButton)
        floating_back.setOnClickListener{
            var send = Intent(this, CustomerHomePage::class.java)
            startActivity(send)
        }

        //Get the widget
        recyclerView = findViewById(R.id.customer_updateRecyclerView1)
        //Initialize Database reference
        referenceDB = FirebaseDatabase.getInstance().getReference("Updates")
        //Initialize list
        updateList = ArrayList<Update>()

        //Set recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomerUpdateListAdapter(this, updateList)
        recyclerView.adapter = adapter

        //Work on the DB
        referenceDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot: DataSnapshot in snapshot.children ){
                    val update = userSnapshot.getValue(Update::class.java)
                    if (update != null) {
                        updateList.add(update)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CustomerConfirmation, "Error getting values", Toast.LENGTH_SHORT).show()
            }

        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CustomerHomePage::class.java))
        finish()
    }

}