package com.ameti.quiz

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ameti.quiz.db.QuizDatabaseManager
import com.ameti.quiz.db.DatabaseManager

class MainActivity : AppCompatActivity() {

    lateinit var dbManager: QuizDatabaseManager
    lateinit var sharedPreferences: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        dbManager = QuizDatabaseManager(this)
        dbManager.onAttach(this)
    }

}