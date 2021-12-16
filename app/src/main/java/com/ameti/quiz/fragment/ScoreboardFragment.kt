package com.ameti.quiz.fragment

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.navigation.findNavController
import com.ameti.quiz.MainActivity
import com.ameti.quiz.R
import com.ameti.quiz.db.QuizDatabaseManager
import java.util.ArrayList

class ScoreboardFragment : Fragment() {

    lateinit var dbManager: QuizDatabaseManager
    lateinit var rootView: View
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbManager = (this.context as MainActivity).dbManager
        sharedPreferences = (this.context as MainActivity).sharedPreferences
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_score_board, container, false)
        rootView = inflate.rootView
        var loggedUser = sharedPreferences.getString("user", null)

        val table: TableLayout = rootView.findViewById(R.id.scoreboard_table)
        val tableHeader: TableRow = TableRow(context)
        tableHeader.setBackgroundColor(Color.MAGENTA)

        val rankHeader: TextView = TextView(context, null, 0, R.style.table_row)
        rankHeader.text = "Posto"
        rankHeader.setTextColor(Color.WHITE)


        val userHeader: TextView = TextView(context, null, 0, R.style.table_row)
        userHeader.text = "Utente"
        userHeader.setTextColor(Color.WHITE)

        val puntiHeader: TextView = TextView(context, null, 0, R.style.table_row)
        puntiHeader.text = "Posto"
        puntiHeader.setTextColor(Color.WHITE)

        tableHeader.addView(rankHeader)
        tableHeader.addView(userHeader)
        tableHeader.addView(puntiHeader)

        table.addView(tableHeader)

        val test: ArrayList<Pair<String, Int>> = dbManager.getUserClassification();
        test.forEachIndexed { index, score ->
            val row: TableRow = TableRow(context)
            val rank = TextView(context, null, 0, R.style.table_row)
            rank.text = (index + 1).toString()
            val user = TextView(context, null, 0, R.style.table_row)
            user.text = score.first
            val points = TextView(context, null, 0, R.style.table_row)
            points.text = score.second.toString()
            if(score.first.equals(loggedUser)) {
                row.setBackgroundColor(Color.CYAN)
            }
            row.addView(rank)
            row.addView(user)
            row.addView(points)
            table.addView(row)
        }
        return inflate
    }

}