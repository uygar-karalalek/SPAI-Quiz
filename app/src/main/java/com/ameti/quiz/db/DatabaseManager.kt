package com.ameti.quiz.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

abstract class DatabaseManager(val dbName: String, val version: Int = 1) {

    lateinit var sqliteDatabase: SQLiteDatabase

    fun onAttach(context: Context) {
        if (!::sqliteDatabase.isInitialized) {
            val dbFile = context.getDatabasePath(dbName)
            sqliteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null)

            val c: Cursor =
                sqliteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)

            val arrTblNames = ArrayList<String>()

            val exists = dbFile.exists()
            if (exists) {
                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {
                        arrTblNames.add(c.getString(c.getColumnIndex("name")));
                        c.moveToNext();
                    }
                }
            }

            val existingDb = exists && arrTblNames.containsAll(arrayListOf("user", "question"))

            if (!existingDb) initializeDb(sqliteDatabase, dbName, version)
            else {
                val oldVersion = sqliteDatabase.version
                if (version != oldVersion)
                    dbVersionChanged(sqliteDatabase, dbName, oldVersion, version)
            }
        }
    }

    open fun dbVersionChanged(
        sqliteDatabase: SQLiteDatabase?,
        dbName: String,
        oldVersion: Int,
        version: Int
    ) {
    }

    abstract fun initializeDb(sqliteDatabase: SQLiteDatabase?, dbName: String, version: Int)

}