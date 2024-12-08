package com.example.kr.data

import com.example.kr.model.MediaItem
import android.content.ContentValues
import android.content.Context
import android.util.Log

class Repository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getAllItems(): List<MediaItem> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null)
        val items = mutableListOf<MediaItem>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION))
                val genre = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENRE))
                val releaseYear = getInt(getColumnIndexOrThrow(DatabaseHelper.COLUMN_RELEASE_YEAR))
                val rating = getFloat(getColumnIndexOrThrow(DatabaseHelper.COLUMN_RATING))
                val authorOrDirector = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_OR_DIRECTOR))
                val imageUrl = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_URL))
                items.add(
                    MediaItem(
                        id = id,
                        title = title,
                        description = description,
                        genre = genre,
                        releaseYear = releaseYear,
                        rating = rating,
                        authorOrDirector = authorOrDirector,
                        imageUrl = imageUrl
                    )
                )
            }
            close()
        }

        Log.d("Repository", "Fetched items: ${items.size} items found.")

        return items
    }

    fun addItem(item: MediaItem) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TITLE, item.title)
            put(DatabaseHelper.COLUMN_DESCRIPTION, item.description)
            put(DatabaseHelper.COLUMN_GENRE, item.genre)
            put(DatabaseHelper.COLUMN_RELEASE_YEAR, item.releaseYear)
            put(DatabaseHelper.COLUMN_RATING, item.rating)
            put(DatabaseHelper.COLUMN_AUTHOR_OR_DIRECTOR, item.authorOrDirector)
            put(DatabaseHelper.COLUMN_IMAGE_URL, item.imageUrl)
        }
        val newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            Log.e("Repository", "Failed to insert row")
        } else {
            Log.d("Repository", "Item inserted successfully with ID: $newRowId")
        }
    }

    fun updateItem(item: MediaItem) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TITLE, item.title)
            put(DatabaseHelper.COLUMN_DESCRIPTION, item.description)
            put(DatabaseHelper.COLUMN_GENRE, item.genre)
            put(DatabaseHelper.COLUMN_RELEASE_YEAR, item.releaseYear)
            put(DatabaseHelper.COLUMN_RATING, item.rating)
            put(DatabaseHelper.COLUMN_AUTHOR_OR_DIRECTOR, item.authorOrDirector)
            put(DatabaseHelper.COLUMN_IMAGE_URL, item.imageUrl)
        }
        db.update(
            DatabaseHelper.TABLE_NAME,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    fun deleteItem(itemId: Int) {
        val db = dbHelper.writableDatabase
        db.delete(
            DatabaseHelper.TABLE_NAME,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(itemId.toString())
        )
    }
}
