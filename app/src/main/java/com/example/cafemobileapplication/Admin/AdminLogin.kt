package com.example.cafemobileapplication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cafemobileapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminLogin : AppCompatActivity() {
    //Declare variables
    lateinit var login_email: EditText
    lateinit var login_password: EditText
    lateinit var login_button: Button
    lateinit var realtimeDB: FirebaseDatabase
    lateinit var referenceDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_admin_login)

        //Initialize Firebase Realtime database
        realtimeDB = FirebaseDatabase.getInstance()
        //Get a reference for the Admins node
        referenceDB = realtimeDB.reference.child("Admins")
        //Set the click event to redirect to register page
        var redirect:TextView = findViewById(R.id.Admin_redirect_register)
        redirect.setOnClickListener{
            var send = Intent(this, AdminRegister::class.java)
            startActivity(send)
        }

        //Function to log in an admin
        adminLogin()
    }

    private fun adminLogin() {
        //Initialize the attribute by getting the value from the views
        login_email = findViewById(R.id.Admin_login_Email)
        login_password = findViewById(R.id.Admin_login_Password)
        login_button = findViewById(R.id.Admin_login_Button)

        //Create an event listener for the login button
        login_button.setOnClickListener {
            var email:String
            var password:String
            email = login_email.text.toString()
            password = login_password.text.toString()
            //Check if the user input the email
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "Error: missing fields", Toast.LENGTH_SHORT).show()
            }
            else{
                signinAdmin(email, password)
            }

        }
    }

    /**
     * This function is used to check the database for the values in the paramenter.
     * If the values are in the table the user can log in.
     */
    private fun signinAdmin(email: String, password: String) {
        //Read the database to check the values
        referenceDB.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        var admin = userSnapshot.getValue(Admin::class.java)
                        // Check if the password matches
                        if (admin != null && admin.password == password) {
                            // Login successful
                            Toast.makeText(this@AdminLogin, "Login successfully", Toast.LENGTH_SHORT).show()
                            var send = Intent(this@AdminLogin, AdminHomePage::class.java)
                            startActivity(send)
                        }
                    }
                }
                else
                    Toast.makeText(this@AdminLogin, "Login failed: credentials are wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminLogin, "Registration unsuccessful: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




}