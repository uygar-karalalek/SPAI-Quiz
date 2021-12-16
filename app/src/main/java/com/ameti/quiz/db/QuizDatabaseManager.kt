package com.ameti.quiz.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ameti.quiz.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class QuizDatabaseManager(val context: Context) : DatabaseManager("quiz.db", 0) {

    override fun dbVersionChanged(
        sqliteDatabase: SQLiteDatabase?,
        dbName: String,
        oldVersion: Int,
        version: Int
    ) {
        sqliteDatabase.apply {
            val dropDbQuery = context.resources.openRawResource(R.raw.delete)
            dropDbQuery.executeQuery()
            initializeDb(sqliteDatabase, setOf(), dbName, version)
        }
    }

    override fun initializeDb(
        sqliteDatabase: SQLiteDatabase?, cols: Set<String>,
        dbName: String, version: Int
    ) {
        for (col in cols) sqliteDatabase!!.execSQL("DROP TABLE $col;")

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

    fun insertUser(username: String, password: String): Boolean {
        return try {
            sqliteDatabase.execSQL(
                "INSERT INTO user VALUES(null, ?, ?, 0)",
                arrayOf(
                    username,
                    password
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    fun doUsnAndPasswordExist(username: String, password: String): Boolean {
        return sqliteDatabase.query(
            "user",
            arrayOf("username", "password"),
            "username=? AND password=?",
            arrayOf(username, password),
            null,
            null,
            null
        ).count == 1
    }

    fun getUserClassification(): ArrayList<Pair<String, Int>> {
        val cursor = sqliteDatabase.query(
            "user",
            arrayOf("username", "points"),
            null,
            null,
            null,
            null,
            "points DESC"
        )

        val usernameAndPoints: ArrayList<Pair<String, Int>> = arrayListOf()
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val username = cursor.getString(cursor.getColumnIndex("username"))
                val points = cursor.getInt(cursor.getColumnIndex("points"))
                usernameAndPoints.add(username to points)
                cursor.moveToNext();
            }
        }

        return usernameAndPoints
    }

}