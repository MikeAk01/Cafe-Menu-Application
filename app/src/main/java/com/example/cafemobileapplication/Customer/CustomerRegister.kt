package com.example.cafemobileapplication.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.cafemobileapplication.R
import com.example.cafemobileapplication.databinding.ActivityCustomerRegisterBinding
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerRegister: AppCompatActivity() {
    //declaring variable
    lateinit var realtimeDB: FirebaseDatabase
    lateinit var referenceDB: DatabaseReference
    lateinit var binding: ActivityCustomerRegisterBinding

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Realtime database
        realtimeDB = FirebaseDatabase.getInstance()
        referenceDB = realtimeDB.reference.child("Customer")
        // Initialize binding
        binding = ActivityCustomerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the click event to redirect to login page
        binding.CustomerRedirectLogin.setOnClickListener{
            var send = Intent(this, CustomerLogin::class.java)
            startActivity(send)
        }
        // Function to register a Customer
        customerRegister()
    }

    /**
     * This function will allow an admin to register
     */
    private fun customerRegister() {
        //Create an event listener for the login button
        binding.CustomerRegisterButton.setOnClickListener {
            //Declare variable and assign views values to variables
            var email:String = binding.CustomerRegisterEmail.text.toString()
            var fName:String = binding.CustomerRegisterFName.text.toString()
            var lName:String = binding.CustomerRegisterLName.text.toString()
            var phoneN:String = binding.CustomerRegisterPhoneN.text.toString()
            var userName:String = binding.CustomerRegisterUserName.text.toString()
            var password:String = binding.CustomerRegisterPassword.text.toString()
            var isActive:Boolean = binding.CustomerRegisterIsActive.isChecked
            //Make sure the user has input the fields
            if(fName.isEmpty() || lName.isEmpty() || email.isEmpty() || phoneN.isEmpty() || userName.isEmpty() || password.isEmpty() || isActive == false) {
                Toast.makeText(this@CustomerRegister, "Error: missing fields", Toast.LENGTH_SHORT).show()
                //Handle exception if phone number field is missing
                phoneNException(phoneN)
            }
            else {
                //Turn number from string to int
                var phoneNumber:Int = phoneN.toInt()
                //Validate user inputs
                if (validatePhoneN(phoneNumber.toString()) && validateEmail(email) && validatePassword(password)) {
                    //Create an instance of Customer
                    referenceDB.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(!snapshot.exists()){
                                // Create instance of customer
                                val customerID: String? = referenceDB.push().key
                                val customer = Customer(customerID, fName, lName, email, phoneNumber, userName, password, isActive)
                                //Add values to database
                                referenceDB.child(customerID!!).setValue(customer)
                                Toast.makeText(this@CustomerRegister, "Registration successful", Toast.LENGTH_SHORT).show()
                                var send = Intent(this@CustomerRegister, CustomerLogin::class.java)
                                startActivity(send)
                            }
                            else
                                Toast.makeText(this@CustomerRegister, "Already registered", Toast.LENGTH_SHORT).show()

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@CustomerRegister, "Registration unsuccessful: ${error.message}", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@CustomerRegister, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This function chekcks if the length of the phone number is different than 10
     */
    private fun validatePhoneN(phoneN: String): Boolean {
        if(phoneN.length != 9) {
            Toast.makeText(this@CustomerRegister, "Error: phone number must be 10 digits", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@CustomerRegister, "Error: incorrect email format", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@CustomerRegister, "Error: password must be at least 8 digits", Toast.LENGTH_SHORT).show()
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