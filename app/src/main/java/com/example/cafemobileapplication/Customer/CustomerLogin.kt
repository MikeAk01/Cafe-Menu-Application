package com.example.cafemobileapplication.Customer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cafemobileapplication.MainActivity
import com.example.cafemobileapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class CustomerLogin : AppCompatActivity() {
    //declaring variables
    lateinit var login_email: EditText
    lateinit var login_password: EditText
    lateinit var login_button: Button
    lateinit var BM_Button: Button
    lateinit var realtimeDB: FirebaseDatabase
    lateinit var referenceDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        //Initialize Firebase Realtime database
        realtimeDB = FirebaseDatabase.getInstance()
        //Get a reference for the Admins node
        referenceDB = realtimeDB.reference.child("Customer")
        //Set the click event to redirect to register page
        var redirect: TextView = findViewById(R.id.Customer_redirect_register)
        redirect.setOnClickListener {
            var send = Intent(this, CustomerRegister::class.java)
            startActivity(send)

        }
        //NEW STUFF HERE
        //Set the back button to go to the main page
        BM_Button = findViewById(R.id.Customer_BM_Button)
        BM_Button.setOnClickListener {
            var send = Intent(this, MainActivity::class.java)
            startActivity(send)
        }

        //function to log customer in
        customerLogin()
    }

    private fun customerLogin() {
        //Initialize the attribute by getting the value from the views
        login_email = findViewById(R.id.Customer_login_Email)
        login_password = findViewById(R.id.Customer_login_Password)
        login_button = findViewById(R.id.Customer_login_Button)


        //Create an event listener for the login from the views
        login_button.setOnClickListener {
            var email: String
            var password: String
            email = login_email.text.toString()
            password = login_password.text.toString()
            //Check if the user input the email
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Error: missing fields", Toast.LENGTH_SHORT).show()
            } else {
                signinCustomer(email, password)
            }

        }
        //function to bring customer back to main
        BackMain()
    }

    private fun BackMain() {
        BM_Button = findViewById(R.id.Customer_BM_Button)


    }
    /**
     * This function is used to check the database for the values in the paramenter.
     * If the values are in the table the user can log in.
     */
    private fun signinCustomer(email: String, password: String) {
        //Read the database to check the values
        referenceDB.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            var admin = userSnapshot.getValue(Customer::class.java)
                            // Check if the password matches
                            if (admin != null && admin.password == password) {
                                // Login successful
                                Toast.makeText(
                                    this@CustomerLogin,
                                    "Login successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                var send =
                                    Intent(this@CustomerLogin, CustomerHomePage::class.java)
                                startActivity(send)
                            }
                        }
                    } else
                        Toast.makeText(
                            this@CustomerLogin,
                            "Login failed: credentials are wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@CustomerLogin,
                        "Registration unsuccessful: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


}

