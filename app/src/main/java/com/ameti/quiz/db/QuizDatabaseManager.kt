package com.ameti.quiz.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ameti.quiz.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class QuizDatabaseManager(val context: Context) : DatabaseManager("quiz.db", 0) {

    data class Question(
        val category: Int,
        val wright: Int,
        val value: String,
        val answers: MutableList<String>,
        val additional_data: String?,
        val win_points: Int
    )

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

    fun updatePoints(username: String, points: Int): Boolean {
        return try {
            sqliteDatabase.execSQL(
                "UPDATE user SET points=points+? WHERE username=?",
                arrayOf(
                    points,
                    username
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getCategories(): ArrayList<Pair<Int, String>> {
        val cursor = sqliteDatabase.query(
            "category",
            arrayOf("id", "name"),
            null,
            null,
            null,
            null,
            null
        )

        val idAndName: ArrayList<Pair<Int, String>> = arrayListOf()
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                idAndName.add(id to name)
                cursor.moveToNext();
            }
        }
        return idAndName
    }

    fun getQuestions(category: String): MutableList<Question> {
        val cursor = sqliteDatabase.query(
            "question",
            arrayOf("category", "wright", "value", "response1", "response2", "response3", "response4", "additional_data", "win_points"),
            "category=?",
            arrayOf(category),
            null,
            null,
            null
        )

        val questions: MutableList<Question> = mutableListOf()
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val question = Question(
                    category = cursor.getInt(cursor.getColumnIndex("category")),
                    wright = cursor.getInt(cursor.getColumnIndex("wright")),
                    value = cursor.getString(cursor.getColumnIndex("value")),
                    answers = mutableListOf(
                        cursor.getString(cursor.getColumnIndex("response1")),
                        cursor.getString(cursor.getColumnIndex("response2")),
                        cursor.getString(cursor.getColumnIndex("response3")),
                        cursor.getString(cursor.getColumnIndex("response4"))
                    ),
                    additional_data = cursor.getString(cursor.getColumnIndex("additional_data")),
                    win_points = cursor.getInt(cursor.getColumnIndex("win_points"))
                )
                questions.add(question);
                cursor.moveToNext();
            }
        }

        return questions
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