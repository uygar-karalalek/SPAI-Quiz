package com.ameti.quiz.fragment

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

class RegisterFragment : Fragment() {

    lateinit var dbManager: QuizDatabaseManager
    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbManager = (this.context as MainActivity).dbManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_register, container, false)
        rootView = inflate.rootView

        rootView.findViewById<Button>(R.id.do_login_button).setOnClickListener {

            val registrationPassword = getRegistrationPassword()
            val registrationUsername = getRegistrationUsername()
            val confirmPassword = registrationConfirmPassword()

            val usnWarning = getUsernameWarning().also { it.visibility = GONE }
            val pwdWarning = getPasswordWarning().also { it.visibility = GONE }
            val confirmWarning = getConfirmPasswordWarning().also { it.visibility = GONE }
            val userExistsWarning = getUserExistsWarning().also { it.visibility = GONE }
            val registrationComplete = getRegistrationCompleteView().also { it.visibility = GONE }

            val usnMinLen = registrationUsername.length >= 3
            val pswMinLen = registrationPassword.length >= 6
            val pwdAndConfirmPwdMatch = confirmPassword == registrationPassword

            when {
                usnMinLen.not() -> usnWarning.visibility = VISIBLE
                pswMinLen.not() -> pwdWarning.visibility = VISIBLE
                pwdAndConfirmPwdMatch.not() -> confirmWarning.visibility = VISIBLE

                else -> {
                    if (dbManager.insertUser(
                            registrationUsername,
                            registrationPassword
                        )
                    ) {
                        registrationComplete.visibility = VISIBLE
                        getRegisterToLoginLink().visibility = VISIBLE
                    } else {
                        userExistsWarning.visibility = VISIBLE
                    }
                }
            }
        }

        rootView.findViewById<TextView>(R.id.register_link_to_login).setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return inflate
    }

    private fun getRegisterToLoginLink() =
        rootView.findViewById<TextView>(R.id.register_link_to_login)

    private fun getUserExistsWarning() = rootView.findViewById<TextView>(R.id.user_exists_warn)

    private fun getRegistrationCompleteView() =
        rootView.findViewById<TextView>(R.id.registration_complete)

    private fun getConfirmPasswordWarning() =
        rootView.findViewById<TextView>(R.id.register_confirm_warn)

    private fun getPasswordWarning() = rootView.findViewById<TextView>(R.id.register_pwd_warn)

    private fun getUsernameWarning() = rootView.findViewById<TextView>(R.id.register_usn_warn)

    private fun registrationConfirmPassword() =
        rootView.findViewById<EditText>(R.id.registration_confirm_password).text.toString()

    private fun getRegistrationPassword() =
        rootView.findViewById<EditText>(R.id.login_password).text.toString()

    private fun getRegistrationUsername() =
        rootView.findViewById<EditText>(R.id.login_username).text.toString()

}