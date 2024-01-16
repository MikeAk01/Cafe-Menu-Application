package com.example.cafemobileapplication.Customer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import com.example.cafemobileapplication.R
import com.google.firebase.database.DatabaseReference


class CustomerAddItem : AppCompatActivity() {

    //declaring variable
    lateinit var product_image: ImageView
    lateinit var product_name: EditText
    lateinit var product_price: EditText
    lateinit var product_available: CheckBox
    lateinit var redirect: TextView
    lateinit var upload_button: Button
    lateinit var product_image_url: String
    lateinit var referenceDB: DatabaseReference
    /* lateinit var storageDB: StorageReference
    */

    var imageUri: Uri? = null

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_add_items)

        // the widgets


    }




}