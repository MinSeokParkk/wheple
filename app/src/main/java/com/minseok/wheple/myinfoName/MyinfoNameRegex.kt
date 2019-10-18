package com.minseok.wheple.myinfoName

class MyinfoNameRegex {
    val regex_name = Regex(pattern = "[가-힣]{2,}")
    fun checkName(name:String):String?{
        if(!regex_name.matches(input = name)){
            return "이름 형식이 올바르지 않습니다."
        }
        if(name.length<2){
            return "이름을 2자 이상 입력해주세요."
        }

        return null
    }
}