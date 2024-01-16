package com.example.cafemobileapplication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.cafemobileapplication.R
import com.example.cafemobileapplication.databinding.ActivityDisplayAdminRegisterBinding
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminRegister : AppCompatActivity() {
    //Declare variables
    lateinit var realtimeDB: FirebaseDatabase
    lateinit var referenceDB: DatabaseReference
    lateinit var binding:  ActivityDisplayAdminRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize Firebase Realtime database
        realtimeDB = FirebaseDatabase.getInstance()
        referenceDB = realtimeDB.reference.child("Admins")
        //Initialize binding
        binding = ActivityDisplayAdminRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set the click event to redirect to login page
        binding.AdminRedirectLogin.setOnClickListener{
            var send = Intent(this, AdminLogin::class.java)
            startActivity(send)
        }
        //Function to register an Admin
        adminRegister()

    }

    /**
     * This function will allow an admin to register
     */
    private fun adminRegister() {
        //Create an event listener for the login button
        binding.AdminRegisterButton.setOnClickListener {
            //Declare variable and assign views values to variables
            var email:String = binding.AdminRegisterEmail.text.toString()
            var fName:String = binding.AdminRegisterFName.text.toString()
            var lName:String = binding.AdminRegisterLName.text.toString()
            var phoneN:String = binding.AdminRegisterPhoneN.text.toString()
            var userName:String = binding.AdminRegisterUserName.text.toString()
            var password:String = binding.AdminRegisterPassword.text.toString()
            var isActive:Boolean = binding.AdminRegisterIsActive.isChecked
            //Make sure the user has input the fields
            if(fName.isEmpty() || lName.isEmpty() || email.isEmpty() || phoneN.isEmpty() || userName.isEmpty() || password.isEmpty() || isActive == false) {
                Toast.makeText(this@AdminRegister, "Error: missing fields", Toast.LENGTH_SHORT).show()
                //Handle exception if phone number field is missing
                phoneNException(phoneN)
            }
            else {
                //Turn number from string to int
                var phoneNumber:Int = phoneN.toInt()
                //Validate user inputs
                if (validatePhoneN(phoneNumber.toString()) && validateEmail(email) && validatePassword(password)) {
                    //Create an instance of admin
                    referenceDB.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(!snapshot.exists()){
                                // Creating an instance of admin
                                var adminID: String? = referenceDB.push().key
                                val admin = Admin(adminID, fName, lName, email, phoneNumber, userName, password, isActive)
                                //Add values to database
                                referenceDB.child(adminID!!).setValue(admin)
                                Toast.makeText(this@AdminRegister, "Registration successful", Toast.LENGTH_SHORT).show()
                                var send = Intent(this@AdminRegister, AdminLogin::class.java)
                                startActivity(send)
                            }
                            else
                                Toast.makeText(this@AdminRegister, "Already registered", Toast.LENGTH_SHORT).show()

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@AdminRegister, "Registration unsuccessful: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    /**
     * This function is to handle the phone number in case a user inputs a string
     */
    private fun phoneNException(phoneN: String){
        var phoneNumber:Int
        try {
            phoneNumber = phoneN.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this@AdminRegister, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This function chekcks if the length of the phone number is different than 10
     */
    private fun validatePhoneN(phoneN: String): Boolean {
        if(phoneN.length != 9) {
            Toast.makeText(this@AdminRegister, "Error: phone number must be 10 digits", Toast.LENGTH_SHORT).show()
            return false
        }
        else
            return true
    }

    /**
     * This function validates if the email format is correct
     */
    private fun validateEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this@AdminRegister, "Error: incorrect email format", Toast.LENGTH_SHORT).show()
            return false
        }
        else
            return true
    }

    /**
     * This function validates if the email format is correct
     */
    private fun validatePassword(password: String): Boolean {
        if(password.length < 6) {
            Toast.makeText(this@AdminRegister, "Error: password must be at least 8 digits", Toast.LENGTH_SHORT).show()
            return false
        }
        else
            return true
    }


    /**
     * This function will hash the password to store in the database
     */
    fun hashPassword(password: String): String {
        return DigestUtils.sha256Hex(password)
    }



}