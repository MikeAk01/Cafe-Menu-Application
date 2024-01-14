package com.example.cafemobileapplication.Admin

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class AdminAddItem : AppCompatActivity() {
    //Declare variables
    lateinit var product_image: ImageView
    lateinit var product_name: EditText
    lateinit var product_price: EditText
    lateinit var product_available: CheckBox
    lateinit var redirect: TextView
    lateinit var upload_button: Button
    lateinit var product_image_url: String
    lateinit var referenceDB: DatabaseReference
    lateinit var storageDB: StorageReference
    var imageUri: Uri? = null
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_item)

        //Get the widgets
        product_image = findViewById(R.id.admin_product_image)
        upload_button = findViewById(R.id.admin_upload_button)

        //Listener for the textview to go back
        redirect = findViewById(R.id.admin_upload_goBack)
        redirect.setOnClickListener{
            var send = Intent(this, AdminHomePage::class.java)
            startActivity(send)
        }

        //Get reference of firebase  database
        referenceDB = FirebaseDatabase.getInstance().getReference("Products")
        //Get reference of firebase storage
        storageDB = FirebaseStorage.getInstance().getReference()

        //Upload image event listener
        product_image.setOnClickListener{
            val galleryIntent = Intent()
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT)
            galleryIntent.setType("image/*")
            startActivityForResult(galleryIntent, 2)
        }

        //Upload button event listener
        upload_button.setOnClickListener{
            if(imageUri != null){
                uploadToFirebase(imageUri!!)
            }
            else{
                Toast.makeText(this@AdminAddItem, "Error: upload image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * This function uploads the input data to firebase
     */
    private fun uploadToFirebase(uri: Uri) {
        val builder = AlertDialog.Builder(this@AdminAddItem)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_bar_layout)
        val dialog = builder.create()
        var fileReference: StorageReference = storageDB.child(System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
            // Upon successful upload
            fileReference.downloadUrl.addOnSuccessListener { uri ->
                dialog.dismiss()
                addToDatabase(uri)
            }
        }.addOnProgressListener { e ->
            dialog.show()
        }.addOnFailureListener { e ->
            // Upon upload failure
            dialog.dismiss()
            Toast.makeText(this@AdminAddItem, "Error: upload unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(mUri: Uri): String? {
        var cr: ContentResolver = contentResolver
        var mime: MimeTypeMap = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.data!!
            product_image.setImageURI(imageUri)
        }
    }

    fun addToDatabase(uri: Uri) {
        //Get widgets
        product_name = findViewById(R.id.admin_product_name)
        product_price = findViewById(R.id.admin_product_price)
        product_available = findViewById(R.id.admin_product_available)
        val name = product_name.text.toString()
        val price = product_price.text.toString()
        val available = product_available.isChecked

        //Handle missing fields
        if(name.isEmpty() || price.isEmpty() || available == false) {
            Toast.makeText(this@AdminAddItem, "Error: missing fields", Toast.LENGTH_SHORT).show()
            //Handle exception if price field is missing
            prodPrice(price)
        }
        else {
            //Turn number from string to Double
            var prodPrice:Double = price.toDouble()
            //Create the image url using uri
            product_image_url = uri.toString()
            //Create instance of Product
            var prodID: String? = referenceDB.push().key
            val product = Product(prodID, name, prodPrice, product_image_url, available)
            //Add product to database
            referenceDB.child(prodID!!).setValue(product)
            Toast.makeText(this@AdminAddItem, "Upload successful", Toast.LENGTH_SHORT).show()
                var send = Intent(this, AdminHomePage::class.java)
                startActivity(send)
        }
    }

    /**
     * This function is to handle the exception
     */
    fun prodPrice(price: String){
        var priceNumber:Double
        try {
            priceNumber = price.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this@AdminAddItem, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


}