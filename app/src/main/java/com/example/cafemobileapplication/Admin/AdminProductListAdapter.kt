package com.example.cafemobileapplication.Admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.R
import com.google.android.material.imageview.ShapeableImageView

class AdminProductListAdapter : RecyclerView.Adapter<AdminProductListAdapter.MyViewHolder> {
    //Fields
    val context: Context
    val list: ArrayList<Product>

    //Secondary constructor
    constructor(context: Context, list: ArrayList<Product>) : super() {
        this.context = context
        this.list = list
    }

    //Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v: View = LayoutInflater.from(context).inflate(R.layout.product_list_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product: Product = list.get(position)
        holder.product_name.setText(product.prodName)
        holder.product_price.setText(product.prodPrice.toString())
        holder.product_available.setText(product.prodAvailable.toString())
        // Load image using Glide
        Glide.with(holder.itemView.context).load(product.prodImageURL).into(holder.product_image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //View holder class
    class MyViewHolder: RecyclerView.ViewHolder {
        //Fields
        val product_name: TextView
        val product_price: TextView
        val product_available: TextView
        val product_image: ShapeableImageView
        //Constructor
        constructor(itemView: View) : super(itemView){
            product_name = itemView.findViewById(R.id.admin_list_name)
            product_price = itemView.findViewById(R.id.admin_list_price)
            product_available = itemView.findViewById(R.id.admin_list_availability)
            product_image = itemView.findViewById(R.id.admin_list_image)
        }

    }
}