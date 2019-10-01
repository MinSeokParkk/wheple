package com.minseok.wheple.login

class LoginRegex {
    fun lengthCheck(string:String) : Boolean{
        var result = string.trim().length!=0

        return result
    }

    fun emailcheck(email: String) : Boolean{
        var result = isEmailValid(email)
        return result
    }


    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}