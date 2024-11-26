package com.amanullah.assessment.base.extensions

import androidx.compose.foundation.lazy.LazyListState
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

fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}