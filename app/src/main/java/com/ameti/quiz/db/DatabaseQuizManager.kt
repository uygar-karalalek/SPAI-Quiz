package com.ameti.quiz.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ameti.quiz.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class DatabaseQuizManager(val context: Context): DbManager("quiz.db", 1) {

    override fun dbVersionChanged(
        sqliteDatabase: SQLiteDatabase?,
        dbName: String,
        oldVersion: Int,
        version: Int
    ) {
        sqliteDatabase.apply {
            val dropDbQuery = context.resources.openRawResource(R.raw.delete)
            dropDbQuery.executeQuery()
            initializeDb(sqliteDatabase, dbName, version)
        }
    }

    override fun initializeDb(sqliteDatabase: SQLiteDatabase?, dbName: String, version: Int) {
        val createDbQuery = context.resources.openRawResource(R.raw.init)
        createDbQuery.executeQuery()
    }

    private fun InputStream.executeQuery() {
        val insertReader = BufferedReader(InputStreamReader(this))

        while (insertReader.ready()) {
            val insertStmt = insertReader.readLine()
            sqliteDatabase.execSQL(insertStmt)
        }
        insertReader.close()
    }

}