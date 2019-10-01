package com.minseok.wheple.signup_others

class SignupOthersRegex {

    val regex_password = Regex(pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,16}")
    val regex_nickname = Regex(pattern = "[가-힣]{2,8}")

    fun lengthCheck(string: String) : Boolean {
        var result : Boolean
        result = string.trim().length!=0

        return result
    }

    fun checkRegex(email: String, password: String, repassword: String, nickname: String) : Int{
        var result : Int

        result = -1

        if(!isEmailValid(email)) {
           result = 1
        }else if(!regex_password.matches(input = password)) {
          result = 2
        }else if(!repassword.equals(password)){
            result = 3
        }else if(!regex_nickname.matches(input = nickname)) {
            result = 4
        }


        return result
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}