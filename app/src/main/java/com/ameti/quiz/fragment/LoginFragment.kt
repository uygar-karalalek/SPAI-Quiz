package com.ameti.quiz.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.ameti.quiz.MainActivity
import com.ameti.quiz.R
import com.ameti.quiz.db.QuizDatabaseManager

class LoginFragment : Fragment() {

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
        val inflate = inflater.inflate(R.layout.fragment_login, container, false)
        rootView = inflate.rootView

        rootView.findViewById<Button>(R.id.do_login_button).setOnClickListener {
            val username = rootView.findViewById<EditText>(R.id.login_username).text.toString()
            val password = rootView.findViewById<EditText>(R.id.login_password).text.toString()
            if (dbManager.doUsnAndPasswordExist(username , password)) {
                sharedPreferences.edit().putString("user", username).apply()
                it.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                rootView.findViewById<TextView>(R.id.login_failed_warn).visibility = VISIBLE
            }
        }

        return inflate
    }

}