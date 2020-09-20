package com.tu.pmu.the100th.internal.utils

import java.util.regex.Pattern
import java.util.regex.Pattern.compile

class ValidationUtils {
    companion object {
        private val passwordRegex = compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")
        private val nameRegex = compile("^[a-zA-Z]{2,25}$")
        private val dateRegex = compile("^[0-1][0-9]/[0-1][0-9]/[1-2][0-9][0-9]{2}$")

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return testRegx(passwordRegex,password)
        }

        fun isValidName(name: String): Boolean {
            return testRegx(nameRegex,name)
        }
        fun isValidDate(date: String): Boolean {
            return testRegx(dateRegex,date)
        }

        private fun testRegx(regex: Pattern, testString:String): Boolean {
            return regex.matcher(testString).matches()
        }
    }
}
