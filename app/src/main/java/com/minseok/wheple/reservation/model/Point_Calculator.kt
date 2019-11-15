package com.minseok.wheple.reservation.model

import java.text.NumberFormat
import java.util.*

class Point_Calculator {

    var finalPrice : Int = 0
    var result : String = ""
    var result_check : String = ""

    fun point_check(point: String, mypoint:String):String{
        var array = mypoint.split("P").toTypedArray()
        var mypointChange = array[0]
        if (point != null && point !="") {


            if(point.toInt()>mypointChange.toInt()){
                println("큰")
                result_check = mypointChange
            }
            else{
                println("작은")
                result_check = "stop"
                var newpoint = point
                if(point.startsWith("0") && !point.equals("0")){
                    newpoint = newpoint.toInt().toString()
                    result_check = newpoint
                }
            }
        }
        else{
            result_check = "stop"
        }
        return result_check
    }

    fun point_cal(price:String, point: String, mypoint:String):String{

        var pricechange = price.replace(",", "")
        pricechange = pricechange.replace("원", "")

        var array = mypoint.split("P").toTypedArray()
        var mypointChange = array[0]


        if (point != null && point !="") {
            if(point.toInt()>mypointChange.toInt()){
                finalPrice = pricechange.toInt() - mypointChange.toInt()
            }else {

                finalPrice = pricechange.toInt() - point.toInt()
            }

            result = NumberFormat.getNumberInstance(Locale.US).format(finalPrice) + "원"
        } else {
            result  = price
        }

        return result
    }

}