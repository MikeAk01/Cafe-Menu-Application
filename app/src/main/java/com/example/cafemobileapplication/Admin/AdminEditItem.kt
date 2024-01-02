package com.example.cafemobileapplication.Admin

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AdminEditItem : AppCompatActivity() {
    //Declare variable
    lateinit var edit_image: ImageView
    lateinit var edit_name: EditText
    lateinit var edit_price: EditText
    lateinit var edit_available: CheckBox
    lateinit var redirect: TextView
    lateinit var edit_button: Button
    lateinit var delete_button: Button
    lateinit var image_url: String
    lateinit var old_image_url: String
    lateinit var prodID: String
    lateinit var referenceDB: DatabaseReference
    lateinit var storageDB: StorageReference
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_item)

        //Get the widgets
        edit_button = findViewById(R.id.admin_edit_prod_button)
        delete_button = findViewById(R.id.admin_delete_prod_button)
        edit_image = findViewById(R.id.admin_edit_prod_image)
        edit_name = findViewById(R.id.admin_edit_prod_name)
        edit_price = findViewById(R.id.admin_edit_prod_price)
        edit_available = findViewById(R.id.admin_edit_prod_available)

        //Listener for the textview to go back
        redirect = findViewById(R.id.admin_edit_goBack)
        redirect.setOnClickListener{
            var send = Intent(this, AdminManageMenu::class.java)
            startActivity(send)
        }

        //Get reference of firebase  database
        referenceDB = FirebaseDatabase.getInstance().getReference("Products")
        //Get reference of firebase storage
        storageDB = FirebaseStorage.getInstance().getReference()

        //Edit image event listener
        edit_image.setOnClickListener{
            val galleryIntent = Intent()
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT)
            galleryIntent.setType("image/*")
            startActivityForResult(galleryIntent, 2)
        }

        //Get the extra data from the intent
        val bundle: Bundle? = intent.extras
        if(bundle != null){
            Glide.with(this@AdminEditItem).load(bundle.getString("productImageURL")).into(edit_image)
            edit_name.setText(bundle.getString("productName"))
            edit_price.setText(bundle.getString("productPrice"))
            edit_available.isChecked
            prodID = bundle.getString("prodID").toString()
            old_image_url = bundle.getString("productImageURL").toString()

        }

        //Edit button event listener
        edit_button.setOnClickListener{
            if(imageUri != null){
                updateFirebase(imageUri!!)
            }
            else{
                Toast.makeText(this@AdminEditItem, "Error: upload image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.data!!
            edit_image.setImageURI(imageUri)
        }
    }

    /**
     * This function uploads the input data to firebase storage
     */
    private fun updateFirebase(uri: Uri) {
        val builder = AlertDialog.Builder(this@AdminEditItem)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_bar_layout)
        val dialog = builder.create()
        var fileReference: StorageReference = storageDB.child(System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
            // Upon successful upload
            fileReference.downloadUrl.addOnSuccessListener { uri ->
                dialog.dismiss()
                updateDatabase(uri)
            }
        }.addOnProgressListener { e ->
            dialog.show()
        }.addOnFailureListener { e ->
            // Upon upload failure
            dialog.dismiss()
            Toast.makeText(this@AdminEditItem, "Error: upload unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(mUri: Uri): String? {
        var cr: ContentResolver = contentResolver
        var mime: MimeTypeMap = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri))
    }

    fun updateDatabase(uri: Uri) {
        val name = edit_name.text.toString()
        val price = edit_price.text.toString()
        val available = edit_available.isChecked

        //Handle missing fields
        if(name.isEmpty() || price.isEmpty() || available == false) {
            Toast.makeText(this@AdminEditItem, "Error: missing fields", Toast.LENGTH_SHORT).show()
            //Handle exception if price field is missing
            prodPrice(price)
        }
        else {
            //Turn number from string to Double
            var prodPrice:Double = price.toDouble()
            //Create the image url using uri
            image_url = uri.toString()
            //Create instance of Product
            val product = Product(prodID, name, prodPrice, image_url, available)
            //Add product to database
            referenceDB.child(prodID!!).setValue(product)
            Toast.makeText(this@AdminEditItem, "Upload successful", Toast.LENGTH_SHORT).show()
            //Delete old url in firebase storage
            val storageDB2 = FirebaseStorage.getInstance().getReferenceFromUrl(old_image_url)
            storageDB2.delete()
            //Start new activity
            var send = Intent(this, AdminManageMenu::class.java)
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
            Toast.makeText(this@AdminEditItem, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


}