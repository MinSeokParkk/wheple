package com.minseok.wheple.cancel

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class Cancelfee {

    fun calculateFee(resdate:String, price:String, payment:String, usedpoint:String):String{

        val days = dateCalcu(resdate)

        var intrefund = 0
        var intrepoint = 0
        var fee = 0

        val intprice =  price.replace(",", "").toInt()
        val intpayment =  payment.replace(",", "").toInt()
        val intusedpoint = usedpoint.replace(",", "").toInt()

        if(days>=7){
            intrefund = intpayment
            intrepoint = intusedpoint

        }else if(days<7 && days>=3){
            fee = intprice /2
            if(fee <= intpayment){
                intrefund = intpayment - fee
                intrepoint = intusedpoint
            }else{
                intrefund = 0
                intrepoint = intusedpoint - fee + intpayment
                if(intrepoint<0){
                    intrepoint = 0
                }
            }


        }else if(days<3 && days>=1){

            fee = intprice *4/5
            if(fee <= intpayment){
                intrefund = intpayment - fee
                intrepoint = intusedpoint
            }else{
                intrefund = 0
                intrepoint = intusedpoint - fee + intpayment
                if(intrepoint<0){
                    intrepoint = 0
                }
            }

        }else if(days<1){

        }

        return NumberFormat.getNumberInstance(Locale.US).format(intrefund) + "|"+ NumberFormat.getNumberInstance(Locale.US).format(intrepoint)


    }

    @SuppressLint("NewApi")
    fun dateCalcu(resdate:String):Int{
        val nowdate: LocalDate = LocalDate.now()
        val cdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
        val resDate = cdf.parse(resdate)

        val nowDate = cdf.parse(nowdate.toString())

        val diff = resDate.time - nowDate.time

        val diffDays = diff / (24 * 60 * 60 * 1000)

        return diffDays.toInt()

    }
}