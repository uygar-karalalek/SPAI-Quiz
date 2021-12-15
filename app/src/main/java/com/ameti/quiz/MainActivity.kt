package com.ameti.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ameti.quiz.db.QuizDatabaseManager
import com.ameti.quiz.db.DatabaseManager

class MainActivity : AppCompatActivity() {

    lateinit var dbManager: QuizDatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager = QuizDatabaseManager(this)
        dbManager.onAttach(this)
    }

}