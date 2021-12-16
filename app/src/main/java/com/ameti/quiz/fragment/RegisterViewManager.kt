package com.ameti.quiz.fragment

import android.view.View
import android.view.View.*
import android.widget.EditText
import android.widget.TextView
import com.ameti.quiz.R

class RegisterViewManager(val registerRootView: View) {

    fun getRegisterToLoginLink(): TextView {
        return registerRootView.findViewById(R.id.register_link_to_login)
    }

    fun userExistsInvisibleWarn(): TextView {
        return registerRootView.findViewById(R.id.user_exists_warn)
    }

    fun registrationCompleteInvisibleView(): TextView {
        return registerRootView.findViewById(R.id.registration_complete)
    }

    fun confirmPasswordInvisibleWarn(): TextView {
        return registerRootView.findViewById(R.id.register_confirm_warn)
    }

    fun passwordInvisibleWarn(): TextView {
        return registerRootView.findViewById(R.id.register_pwd_warn)
    }

    fun usernameInvisibleWarn(): TextView {
        return registerRootView.findViewById(R.id.register_usn_warn)
    }

    fun registrationConfirmPassword(): String {
        val registrationConfirmPassword = R.id.registration_confirm_password
        return registerRootView.findViewById<EditText>(registrationConfirmPassword).text.toString()
    }

    fun registrationPassword(): String {
        val loginPassword = R.id.login_password
        return registerRootView.findViewById<EditText>(loginPassword).text.toString()
    }

    fun registrationUsername(): String {
        val loginUsername = R.id.login_username
        return registerRootView.findViewById<EditText>(loginUsername).text.toString()
    }

    fun visibilitySetup() {
        userExistsInvisibleWarn().visibility = GONE
        registrationCompleteInvisibleView().visibility = GONE
        confirmPasswordInvisibleWarn().visibility = GONE
        passwordInvisibleWarn().visibility = GONE
        usernameInvisibleWarn().visibility = GONE
    }

}