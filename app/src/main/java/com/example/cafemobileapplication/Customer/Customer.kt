package com.example.cafemobileapplication.Customer

/**
 * this class creates a new customer
 * customer consists of first and last name, email, phone number, username, password and
 */
data class Customer(
    var customerID: String?, var firstName:String, var lastName:String, var email:String, var phoneN:Int,
    var userName:String, var password:String, var isActive:Boolean){

    //No argument constructor
    constructor() : this("","","","",0,"","",false)
}