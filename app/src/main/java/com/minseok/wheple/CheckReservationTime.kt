package com.minseok.wheple

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class CheckReservationTime {

    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
    val now = current.format(formatter).toLong()

    fun comparetime(date:String, timerange:String) : String{

        var result = ""

        var dateString =  date.replace("-", "")

        val strs = timerange.split(" ").toTypedArray()

        var timeString_start = strs[0].replace(":","")

        var timeString_finish = strs[2].replace(":","")

        val starttime = dateString+timeString_start
        val finishtime = dateString+timeString_finish

        if(now>=starttime.toLong()&&now<finishtime.toLong()){
            result = "now"
        }else if(now>=finishtime.toLong()){
            result = "after"
        }

        return result
//       return now.toLong()>=reservetime.toLong()

    }

}