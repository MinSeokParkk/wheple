package com.minseok.wheple.reservation

class ReservationRegex {

    fun lengthCheck(string: String) : Boolean {
        var result : Boolean
        result = string.trim().length!=0

        return result
    }

    fun phonecheck(phone :String) : Boolean{
        var result : Boolean
        result = phone.trim().length>9 && phone.trim().length<12 && phone.startsWith("01")

        return  result

    }
}