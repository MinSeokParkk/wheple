package com.minseok.wheple.myinfoPw

class MyinfoPwRegex {
    val regex_password =Regex(pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,16}")
    fun checkPw(pw:String):String?{
        if(!regex_password.matches(input = pw)){
            return "8~16자리 영문, 숫자, 특수문자 조합으로 설정 해주세요."
        }
        return null
    }
}