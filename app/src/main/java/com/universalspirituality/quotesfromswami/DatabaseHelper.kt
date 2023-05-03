package com.universalspirituality.quotesfromswami

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quotes_database.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "quoteswithdate_table"
        private const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_QUOTE = "Quotes"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_QUOTE TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertQuote(quote: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_QUOTE, quote)
            put(COLUMN_DATE, getCurrentDate())
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    fun getRandomQuote(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY RANDOM() LIMIT 1", null)
        var quote = ""
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_QUOTE)
            if (columnIndex >= 0) {
                quote = cursor.getString(columnIndex)
            }
        }
        cursor.close()
        db.close()
        return quote
    }
    fun getAllQuotes(): List<String> {
        val quotes = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_QUOTE)
            if (columnIndex >= 0) {
                quotes.add(cursor.getString(columnIndex))
            }
        }
        cursor.close()
        db.close()
        return quotes
    }
    fun deleteAndAddQuotes() {
        val db = this.writableDatabase
        // delete all existing quotes
        val deletedRows = db.delete(TABLE_NAME, null, null)
        println("Deleted $deletedRows rows from the table")

        // add new quotes
        QuotesAddorDelete.quotesList.forEach { quote ->
            insertQuote(quote)
        }

        db.close()
    }
    fun deleteQuote(quote: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_QUOTE = ?", arrayOf(quote))
        db.close()
    }


}