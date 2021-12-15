package com.ameti.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ameti.quiz.db.DatabaseQuizManager
import com.ameti.quiz.db.DbManager

class MainActivity : AppCompatActivity() {

    lateinit var dbManager: DbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager = DatabaseQuizManager(this)
        Log.d("FF", "FFFF")
        dbManager.onAttach(this)
        Log.d("UU", "RRR")
    }

}