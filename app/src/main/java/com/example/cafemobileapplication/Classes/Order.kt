package com.example.cafemobileapplication.Classes

import java.time.LocalDateTime

/**
 * This data class will contain the orders that the customers have placed
 * It passes in the constructor the orderID, the customerID, the order date time and the order status
 */
data class Order(
    var orderID: String?,
    var customerID: String?,
    var orderDateTime: LocalDateTime?,
    var orderStatus:String){

    //No argument constructor
    constructor() : this("", "",null, "")
}
