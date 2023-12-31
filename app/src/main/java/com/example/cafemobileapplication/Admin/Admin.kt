package com.example.cafemobileapplication.Admin
/**
 * This class creates a new Admin.
 * An admin is composed of: first and last name, email, phone number, username, password and if is active or not
 */
data class Admin(
    var adminID: String?, var firstName:String, var lastName:String, var email:String, var phoneN:Int,
    var userName:String, var password:String, var isActive:Boolean){

    //No argument constructor
    constructor() : this("","","","",0,"","",false)

}
