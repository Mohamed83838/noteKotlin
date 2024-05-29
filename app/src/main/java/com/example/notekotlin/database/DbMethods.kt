package com.example.notekotlin.database

import android.content.ContentValues
import android.content.Context

class NoteManagerRepo(context: Context) {
     val dbHelper = FeedReaderDbHelper(context)

    // Method to insert data
    fun insert(title: String, content: String,image:String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("content", content)
            put("image",image)
        }
        return db.insert("notes", null, values)
    }

    // Method to query all data
    fun getAllEntries(): List<Note> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf("id", "title", "content", "image")
        val cursor = db.query(
            "notes",   // The table to query
            projection,    // The array of columns to return (pass null to get all)
            null,          // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,          // Don't group the rows
            null,          // Don't filter by row groups
            "title DESC" // The sort order
        )

        val entries = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val title = getString(getColumnIndexOrThrow("title"))
                val content = getString(getColumnIndexOrThrow("content"))
                val image = getString(getColumnIndexOrThrow("image"))
                entries.add(Note(id, title, content, image))
            }
        }
        cursor.close()
        return entries
    }

    // Method to update data
    fun update(id: Long, title: String, content: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("content", content)
        }
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update("notes", values, selection, selectionArgs)
    }

    // Method to delete data
    fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.delete("notes", selection, selectionArgs)
    }
}

data class Note(val id: Long, val title: String, val content: String, val image: String)
