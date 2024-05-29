package com.example.notekotlin.sharedpref

import android.content.Context
import android.content.SharedPreferences

class sharedpref {

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("logged", Context.MODE_PRIVATE)
    }

    // Function to save data to Shared Preferences
    fun saveData(context: Context, key: String, value: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply() // or editor.commit() if you want immediate apply
    }

    // Function to read data from Shared Preferences
    fun readData(context: Context, key: String): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(key, null)
    }

}