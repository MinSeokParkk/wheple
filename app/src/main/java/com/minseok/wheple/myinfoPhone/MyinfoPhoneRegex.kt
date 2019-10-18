package com.minseok.wheple.myinfoPhone

class MyinfoPhoneRegex {
    fun checkPhone(phone:String):String?{
        if(phone.length<10){
            return "전화번호를 다 입력하지 않았습니다."
        }

        if(!phone.startsWith("01")){
            return "전화번호 형식이 올바르지 않습니다."
        }

        return null
    }
}