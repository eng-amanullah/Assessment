package com.amanullah.assessment.base.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json: String?): List<String>? {
        return Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }
}