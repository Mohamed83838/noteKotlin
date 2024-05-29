package com.example.notekotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
      private val SQL_CREATE_ENTRIES =
          """
    CREATE TABLE notes (
        id INTEGER PRIMARY KEY,
        title TEXT,
        content TEXT,
        image TEXT
    )
    """
     private  val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS notes"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

     override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
         // This database is only a cache for online data, so its upgrade policy is
         // to simply discard the data and start over
         db.execSQL(SQL_DELETE_ENTRIES)
         onCreate(db)
     }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "notes.db"
    }
}