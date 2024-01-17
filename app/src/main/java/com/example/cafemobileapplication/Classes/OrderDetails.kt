package com.example.cafemobileapplication.Classes

/**
 * This class will represent the order detail of each order.
 * The class will pass orderDetailID, orderID, prodocutID
 */
data class OrderDetails(
    var orderDetailID: String?,
    //Reference to the Order class
    var orderID: Order?,
    //Reference to the Product class
    var prodID: Product?){

    //No argument constructor
    constructor() : this("", null,null)

}
