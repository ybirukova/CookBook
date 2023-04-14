package com.example.cookbook.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.security.auth.Subject

class TypeConverter @Inject constructor(){

    private val gson = Gson()

    fun subToJson(list: List<String>): String {
        return if (list.isEmpty()) {
            ""
        } else {
            gson.toJson(list) ?: throw IllegalArgumentException()
        }
    }

    fun jsonToSubject(json: String?): List<String> {
        return if (json.isNullOrEmpty()) {
            listOf()
        } else {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        }
    }
}