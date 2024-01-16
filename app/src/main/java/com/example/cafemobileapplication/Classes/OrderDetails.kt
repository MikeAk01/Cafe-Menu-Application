package com.example.cafemobileapplication.Classes

/**
 * This class will represent the order detail of each order.
 * The class will pass orderDetailID, orderID, prodocutID
 */
data class OrderDetails(
    var orderDetailID: String?,
    var orderID: String?,
    var prodID: String?){

    //No argument constructor
    constructor() : this("", "","")

}
