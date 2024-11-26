package com.amanullah.assessment.data.remote.models

import androidx.annotation.Keep

@Keep
class BaseRequestModel {
    var limit: Int = 10
    val skip: Int = 0
    var id: Int = 0
}