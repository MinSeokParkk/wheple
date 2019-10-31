package com.minseok.wheple.review

class Review_Sort_Filter {

    var photo:Boolean = false

    var sort = "최근 작성순"

    fun changedsort(text:String):Int?{
        if(text=="최근 작성순"){
            return 1
        }else if(text == "별점 높은순"){
            return 2
        }else if(text == "별점 낮은순"){
            return 3
        }

        return null
    }
}