package com.minseok.wheple.map

import android.content.Context
import android.graphics.Point
import android.location.Address
import android.location.Geocoder

class Point() {

    var x : Double = 0.0
    var y : Double = 0.0


    fun getPointFromGeoCoder(point: com.minseok.wheple.map.Point, addr:String, context: Context):com.minseok.wheple.map.Point {

        val geocoder:Geocoder = Geocoder(context)
        val listAddress :List<Address>


          listAddress = geocoder.getFromLocationName(addr, 1)

        point.y = listAddress.get(0).longitude
        point.x = listAddress.get(0).latitude

        return point
    }
}