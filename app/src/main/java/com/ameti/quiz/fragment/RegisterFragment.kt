package com.ameti.quiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.ameti.quiz.MainActivity
import com.ameti.quiz.R
import com.ameti.quiz.db.QuizDatabaseManager

class RegisterFragment : Fragment() {

    lateinit var dbManager: QuizDatabaseManager
    lateinit var rootView: View
    lateinit var viewManager: RegisterViewManager

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
        viewManager = RegisterViewManager(rootView)

        rootView.findViewById<Button>(R.id.do_login_button).setOnClickListener { validate() }

        rootView.findViewById<TextView>(R.id.register_link_to_login).setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return inflate
    }

    private fun validate() {
        val validator = RegistrationValidator(
            username = viewManager.registrationUsername(),
            password = viewManager.registrationPassword(),
            confirmPassword = viewManager.registrationConfirmPassword()
        )

        validatorCases(validator, viewManager)
    }

    private fun validatorCases(validator: RegistrationValidator, viewManager: RegisterViewManager) {
        viewManager.visibilitySetup()

        when {
            validator.usernameNotValid() -> viewManager.usernameInvisibleWarn().visibility = VISIBLE
            validator.passwordNotValid() -> viewManager.passwordInvisibleWarn().visibility = VISIBLE
            validator.confirmPasswordNotValid() -> viewManager.confirmPasswordInvisibleWarn().visibility = VISIBLE

            else -> {
                if (tryInsertUserToDb(validator)) registrationComplete(viewManager)
                else viewManager.userExistsInvisibleWarn().visibility = VISIBLE
            }
        }
    }

    private fun registrationComplete(viewManager: RegisterViewManager) {
        viewManager.registrationCompleteInvisibleView().visibility = VISIBLE
        viewManager.getRegisterToLoginLink().visibility = VISIBLE
    }

    private fun tryInsertUserToDb(validator: RegistrationValidator): Boolean {
        return dbManager.insertUser(
            validator.username,
            validator.password
        )
    }

}