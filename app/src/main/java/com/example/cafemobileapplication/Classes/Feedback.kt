package com.example.cafemobileapplication.Classes

import com.example.cafemobileapplication.Customer.Customer

/**
 * This class will represent the feedback and rating that the Customer leaves
 * The class will pass as argument feedbackID, the customerID, a feedback message and the rating
 */
data class Feedback(
    var feedbackID: String?,
    //Reference to the Customer class
    var customerID: Customer?,
    var message: String?,
    var rating: Int){

    //No argument constructor
    constructor() : this("", null, "",0)
}

