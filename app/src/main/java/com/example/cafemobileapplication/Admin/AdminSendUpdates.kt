package com.example.cafemobileapplication.Admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.Classes.Update
import com.example.cafemobileapplication.Customer.Customer
import com.example.cafemobileapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminSendUpdates : AppCompatActivity() {
    //Variables
    lateinit var customer_email: EditText
    lateinit var update_message: EditText
    lateinit var send_button: Button
    lateinit var redirect: TextView
    lateinit var realtimeDB: FirebaseDatabase
    lateinit var referenceDBCustomer: DatabaseReference
    lateinit var referenceDBUpdate: DatabaseReference
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

        //Initialize Firebase Realtime database
        realtimeDB = FirebaseDatabase.getInstance()
        //Get a reference for the Customer and Update
        referenceDBCustomer = realtimeDB.getReference("Customer")
        referenceDBUpdate = realtimeDB.getReference("Updates")


        //Find the views
        customer_email = findViewById(R.id.admin_sendUpdate_customerEmail)
        update_message = findViewById(R.id.admin_sendUpdate_Message)
        send_button = findViewById(R.id.admin_sendUpdate_btn)

        //Handle the send button event listener
        send_button.setOnClickListener {
            sendMessage()
        }

    }

    /**
     * This function will send the message tiped to the right customer
     */
    private fun sendMessage() {
        //Set the variables
        var customerEmail = customer_email.text.toString()
        var message = update_message.text.toString()

        //Check if there are missing inputs
        if(TextUtils.isEmpty(customerEmail) || TextUtils.isEmpty(message)){
            Toast.makeText(this, "Error: missing fields", Toast.LENGTH_SHORT).show()
        }
        else{
            handleDB(customerEmail, message)
        }
    }

    /**
     * This function wil check if the customer is present in the DB and send the message to the right customer
     */
    private fun handleDB(customerEmail: String, message: String) {
        //Read the database to check the values
        referenceDBCustomer.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(userSnapshot in snapshot.getChildren()) {
                        val customer = userSnapshot.getValue(Customer::class.java)
                        if (customer != null) {
                            if (customer.email.equals(customerEmail)) {
                                //Create instance of Update
                                var customerID: String? = customer.customerID
                                val update = Update(customerID, customerEmail, message)
                                //Add update to database
                                referenceDBUpdate.push().setValue(update)
                                //Message sent successfully
                                Toast.makeText(this@AdminSendUpdates, "Message sent successfully", Toast.LENGTH_SHORT).show()
                                var send = Intent(this@AdminSendUpdates, AdminHomePage::class.java)
                                startActivity(send)

                            } else
                                Toast.makeText(this@AdminSendUpdates, "The customer you are trying to send an update to do not exist!", Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(this@AdminSendUpdates, "No user exist", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminSendUpdates, "Registration unsuccessful: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}