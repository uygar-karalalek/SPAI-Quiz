package com.ameti.quiz.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.ameti.quiz.MainActivity
import com.ameti.quiz.R
import com.ameti.quiz.db.QuizDatabaseManager

class GameFinishFragment : Fragment() {
    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_game_finish, container, false)
        rootView = inflate.rootView

        if(arguments != null) {
            if(requireArguments().get("puntiFatti") != null) {
                val puntiFatti = requireArguments().get("puntiFatti")
                rootView.findViewById<TextView>(R.id.finish).text = "Hai finito il quiz con $puntiFatti punti"
            }
        }

        rootView.findViewById<Button>(R.id.new_game).setOnClickListener {
            it.findNavController().navigate(R.id.action_gameFinishFragment_to_homeFragment)
        }
        return inflate
    }

}