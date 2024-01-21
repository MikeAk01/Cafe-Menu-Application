package com.example.cafemobileapplication.Classes

/**
 * This class will contain a list of the customer that has logged in.
 * The class will be called whenever a customer has successfully logged in.
 * When the customer logs out, the name of the customer will be removed.
 */
class LoggedInCustomerList {
    private val loggedInCustomers: MutableList<String> = mutableListOf()

    /**
     * Add the email of the customer that has logged in
     */
    fun addCustomer(email: String) {
        loggedInCustomers.add(email)
    }

    /**
     * Remove the email of the customer that has logged out
     */
    fun removeCustomer(email: String) {
        loggedInCustomers.remove(email)
    }

    /**
     * Get the logged in customers list
     */
    fun getLoggedInCustomers(): List<String> {
        return loggedInCustomers.toList()
    }

}