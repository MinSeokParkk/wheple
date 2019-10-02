package com.minseok.wheple.myReservation

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class CheckFinishTime {

    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
    val now = current.format(formatter)

    fun comparetime(date:String, timerange:String) : Boolean{
        var dateString =  date.replace("-", "")

        val strs = timerange.split(" ").toTypedArray()

        var timeString = strs[2].replace(":","")

        val reservetime = dateString+timeString

       return now.toLong()>=reservetime.toLong()

    }

}