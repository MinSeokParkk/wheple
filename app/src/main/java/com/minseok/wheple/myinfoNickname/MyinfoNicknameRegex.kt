package com.minseok.wheple.myinfoNickname

class MyinfoNicknameRegex {
    val regex_nickname = Regex(pattern = "[가-힣]{2,8}")
    fun checkNickname(nickname:String):String?{
        if(!regex_nickname.matches(input = nickname)){
            return "닉네임은 2~8자 한글만 가능합니다."
        }

       return null
    }
}