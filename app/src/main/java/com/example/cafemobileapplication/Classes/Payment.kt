package com.example.cafemobileapplication.Classes

import java.time.LocalDateTime

/**
 * This class will represent the payment object whenever the customer pays for the product
 * It will pass paymentID, orderID, paymentType, amount, paymentDate
 */
data class Payment(
    var paymentID: String?,
    var orderID: String?,
    var paymentType: String,
    var amount: Double,
    var paymentDate: LocalDateTime?){

    //No argument constructor
    constructor() : this("", "","", 0.00, null)

}