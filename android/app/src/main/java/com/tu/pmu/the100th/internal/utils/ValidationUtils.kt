package com.tu.pmu.the100th.internal.utils

import java.util.regex.Pattern.compile

class ValidationUtils {
    companion object {
        private val passwordRegex = compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            passwordRegex.matcher(password).matches()
            return true
        }

    }
}
