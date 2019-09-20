package com.minseok.wheple.retrofit

import com.minseok.wheple.home.PlaceItem

object Result {
    data class Connectresult(val result:String, val places: ArrayList<PlaceItem>)

}