package com.example.kr.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_GENRE TEXT NOT NULL,
                $COLUMN_RELEASE_YEAR INTEGER NOT NULL,
                $COLUMN_RATING REAL NOT NULL,
                $COLUMN_AUTHOR_OR_DIRECTOR TEXT NOT NULL,
                $COLUMN_IMAGE_URL TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "media.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "media_items"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_GENRE = "genre"
        const val COLUMN_RELEASE_YEAR = "release_year"
        const val COLUMN_RATING = "rating"
        const val COLUMN_AUTHOR_OR_DIRECTOR = "author_or_director"
        const val COLUMN_IMAGE_URL = "image_url"
    }
}
