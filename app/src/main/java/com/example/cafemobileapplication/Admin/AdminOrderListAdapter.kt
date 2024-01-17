package com.example.cafemobileapplication.Admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cafemobileapplication.Classes.Order
import com.example.cafemobileapplication.Classes.Product
import com.example.cafemobileapplication.R

class AdminOrderListAdapter : RecyclerView.Adapter<AdminOrderListAdapter.MyViewHolder> {
    //Fields
    val context: Context
    val orderList: ArrayList<Order>

    //Secondary constructor
    constructor(context: Context, orderList: ArrayList<Order>) : super() {
        this.context = context
        this.orderList = orderList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v: View = LayoutInflater.from(context).inflate(R.layout.order_list_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order: Order = orderList.get(position)

        holder.customer_email.setText(order.customerEmail)
        holder.order_Amount.setText(order.orderAmount)
        holder.order_Status.setText(order.orderStatus)
        holder.order_DateTime.setText(order.orderDateTime!!.toString())
        //Send extra data in intent fro when the cardview is clicked
        holder.cardView.setOnClickListener {
            val send= Intent(holder.itemView.context, AdminManageOrders::class.java)
            send.putExtra("customerEmail", orderList.get(holder.adapterPosition).customerEmail)
            send.putExtra("orderPrice", orderList.get(holder.adapterPosition).orderAmount!!)
            send.putExtra("orderStatus", orderList.get(holder.adapterPosition).orderStatus)
            send.putExtra("orderDateTime", orderList.get(holder.adapterPosition).orderDateTime)
            holder.itemView.context.startActivity(send)
        }
    }

    //View holder class
    class MyViewHolder: RecyclerView.ViewHolder {
        //Fields
        val customer_email: TextView
        val order_Amount: TextView
        val order_Status: TextView
        val order_DateTime: TextView
        val cardView: CardView

        //Constructor
        constructor(itemView: View) : super(itemView){
            customer_email = itemView.findViewById(R.id.admin_orderOrderAmount)
            order_Amount = itemView.findViewById(R.id.admin_orderOrderAmount)
            order_Status = itemView.findViewById(R.id.admin_orderOrderStatus)
            order_DateTime = itemView.findViewById(R.id.admin_orderOrderDateTime)
            cardView = itemView.findViewById(R.id.admin_orderList_cardView)
        }


    }
}