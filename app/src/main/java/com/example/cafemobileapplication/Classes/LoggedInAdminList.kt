package com.example.cafemobileapplication.Classes
/**
 * This class will contain a list of the admin that has logged in.
 * The class will be called whenever an admin has successfully logged in.
 * When the admin logs out, the name of the admin will be removed.
 */
class LoggedInAdminList {
    private val LoggedInAdmin: MutableList<String> = mutableListOf()

    /**
     * Add the email of the admin that has logged in
     */
    fun addAdmin(email: String) {
        LoggedInAdmin.add(email)
    }


    /**
     * Get the logged in admin list
     */
    fun getLoggedInAdmin(): List<String> {
        return LoggedInAdmin.toList()
    }

}