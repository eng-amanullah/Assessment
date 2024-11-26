package com.amanullah.assessment.base.networkmanager

/**
 * Class that is responsible to return if device is connected to the internet or not.
 *
 * App should use the class across the application to check connectivity status.
 *
 * Responsible NetworkManager (Manage Connection Status) should update
 * latest status to [NetworkManager] by following the api [setInternetConnection].
 */
object NetworkManager {
    @Volatile
    var isInternetConnected: Boolean = false
        private set

    fun setInternetConnection(value: Boolean) {
        isInternetConnected = value
    }
}