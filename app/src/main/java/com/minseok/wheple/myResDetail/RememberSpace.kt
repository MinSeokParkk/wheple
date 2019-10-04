package com.minseok.wheple.myResDetail

class RememberSpace {

    var space = ""

    fun remember(no:String){
        space = no
    }
    fun putspace():String{
        return space
    }
}