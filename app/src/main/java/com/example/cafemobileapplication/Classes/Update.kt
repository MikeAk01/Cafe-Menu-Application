package com.example.cafemobileapplication.Classes

import com.example.cafemobileapplication.Customer.Customer

/**
 * This class will represent the update message that the admin will send to the customer.
 * The class has the following parameters: customerID, customer email, admin email and the message that should get sent
 */
data class Update (
    var updateID: String?,
    var customerEmail: String?,
    var adminEmail: String?,
    var message: String?){

    //No argument constructor
    constructor() : this(null, null, null,"")

}
