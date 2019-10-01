package com.minseok.wheple.signup_phone

class SignupPhoneRegex {

    fun emptycheck(phone: String) :Boolean{
        var result : Boolean
        result = phone.trim().length>0

        return result
    }

    fun phonecheck(phone :String) : Boolean{
        var result : Boolean
        result = phone.trim().length>9 && phone.trim().length<12 && phone.startsWith("01")

        return  result

    }
}