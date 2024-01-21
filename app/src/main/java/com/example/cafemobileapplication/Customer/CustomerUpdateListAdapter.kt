package com.example.cafemobileapplication.Customer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemobileapplication.Classes.Update
import com.example.cafemobileapplication.R

class CustomerUpdateListAdapter : RecyclerView.Adapter<CustomerUpdateListAdapter.MyViewHolder> {


    //Fields
    val context: Context
    val list: ArrayList<Update>

    //Secondary constructor
    constructor(context: Context, list: ArrayList<Update>) : super() {
        this.context = context
        this.list = list
    }

    //Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v: View = LayoutInflater.from(context).inflate(R.layout.update_list_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val update: Update = list.get(position)
        holder.admin_email.setText(update.adminEmail)
        holder.message.setText(update.message)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    //View holder class
    class MyViewHolder: RecyclerView.ViewHolder {
        //Fields
        val admin_email: TextView
        val message: TextView
        val cardView: CardView
        //Constructor
        constructor(itemView: View) : super(itemView){
            admin_email = itemView.findViewById(R.id.customer_updateAdminEmail)
            message = itemView.findViewById(R.id.customer_updateMessage)
            cardView = itemView.findViewById(R.id.customer_updateList_cardView)
        }

    }
}