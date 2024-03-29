package com.example.cafemobileapplication.Classes

import com.example.cafemobileapplication.Customer.Customer
import java.time.LocalDateTime

/**
 * This data class will contain the orders that the customers have placed
 * It passes in the constructor the orderID, the customerID, customer email, order price, the order date time and the order status
 */
data class Order(
    var orderID: String?,
    //Reference to the Customer class
    var customerID: String?,
    var customerEmail: String?,
    //Reference to Payment class
    var orderAmount: String?,
    var orderDateTime: LocalDateTime?,
    var orderStatus:String){

    //No argument constructor
    constructor() : this("", null, "", null,null, "Pending")
}
