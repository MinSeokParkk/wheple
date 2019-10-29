package com.minseok.wheple

class Sort_data {
    var sort = "별점순"

    fun changedsort(text:String):Int?{
        if(text=="별점순"){
            return 1
        }else if(text == "리뷰 많은순"){
            return 2
        }else if(text == "가격 낮은순"){
            return 3
        }else if(text == "가격 높은순"){
            return 4
        }
        return null
    }
}