package com.example.cafemobileapplication.Classes

/**
 * This data class represents the Product.
 * It contains the product ID, name, price, image and availability
 */
data class Product(
    var prodID: String?, var prodName:String, var prodPrice: Double, var prodImageURL:String, var prodAvailable:Boolean){

    //No argument constructor
    constructor() : this("", "",0.00,"", false)
}