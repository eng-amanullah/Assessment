package com.amanullah.assessment.base.extensions

import com.google.gson.Gson
import java.net.InetAddress

val gson by lazy { Gson() }

fun Any.isHostReachable(host: String = "google.com"): Boolean {
    return try {
        return !InetAddress.getByName(host).equals("")
    } catch (e: java.lang.Exception) {
        false
    }
}