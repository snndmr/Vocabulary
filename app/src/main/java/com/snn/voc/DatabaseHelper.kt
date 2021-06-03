package com.snn.voc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DatabaseHelper.databaseName, null, DatabaseHelper.databaseVersion) {

    private val tableName = "words"
    private val columnID = "id"
    private val columnName = "name"
    private val columnType = "type"
    private val columnMean = "mean"
    private val columnSynonym = "synonym"
    private val columnAntonym = "antonym"
    private val columnSentence = "sentence"
    private val columnIsUserAdd = "isUserAdd"

    companion object {
        private const val databaseName = "SQLITE_DATABASE"
        private const val databaseVersion = 1
    }

    override fun onCreate(database: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $tableName " +
                    "($columnID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $columnName VARCHAR(256)," +
                    " $columnType VARCHAR(256)," +
                    " $columnMean VARCHAR(256)," +
                    " $columnSynonym VARCHAR(256)," +
                    " $columnAntonym VARCHAR(256)," +
                    " $columnSentence VARCHAR(256)," +
                    " $columnIsUserAdd INTEGER)"
        database?.execSQL(createTable)
    }

    fun insertData(word: Word) {
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(columnName, word.name)
        contentValues.put(columnType, word.type)
        contentValues.put(columnMean, word.mean)
        contentValues.put(columnSynonym, word.synonym)
        contentValues.put(columnAntonym, word.antonym)
        contentValues.put(columnSentence, word.sentence)
        contentValues.put(columnIsUserAdd, word.isUserAdd)

        sqliteDB.insert(tableName, null, contentValues)
    }

    fun readData(): MutableList<Word> {
        val wordList = mutableListOf<Word>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $tableName"
        val result = sqliteDB.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                wordList.add(
                    Word(
                        result.getString(result.getColumnIndex(columnID)).toInt(),
                        result.getString(result.getColumnIndex(columnName)),
                        result.getString(result.getColumnIndex(columnType)),
                        result.getString(result.getColumnIndex(columnMean)),
                        result.getString(result.getColumnIndex(columnSynonym)),
                        result.getString(result.getColumnIndex(columnAntonym)),
                        result.getString(result.getColumnIndex(columnSentence)),
                        result.getString(result.getColumnIndex(columnIsUserAdd)).toInt(),
                    )
                )
            } while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return wordList
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}