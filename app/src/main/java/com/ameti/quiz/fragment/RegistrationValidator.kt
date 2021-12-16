package com.ameti.quiz.fragment

class RegistrationValidator(val username: String, val password: String, val confirmPassword: String) {

    fun usernameNotValid(): Boolean {
        return username.length < 3
    }

    fun passwordNotValid(): Boolean {
        return password.length < 6
    }

    fun confirmPasswordNotValid(): Boolean {
        return password != confirmPassword
    }
}