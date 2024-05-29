package com.example.notekotlin
import android.content.Context
import android.widget.Toast



class VisualTools {
    companion object {
        fun showToast(context: Context, text:String) {
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context, text, duration).show()
        }
    }

}