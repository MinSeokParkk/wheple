package com.minseok.wheple

class Filter_data {

    companion object{
        var soccer :Boolean = false
        var futsal :Boolean = false
        var baseball :Boolean = false
        var basketball :Boolean = false
        var badminton :Boolean = false
        var tennis :Boolean = false
        var pingpong :Boolean = false
        var volleyball:Boolean = false

        var parking:Boolean = false
        var shower:Boolean = false
        var cooling:Boolean =false
        var heating:Boolean = false

        var loc1:String = "전체"
        var loc2:String = " "

        var filter_change :Boolean = false
    }

    fun make_loc(loca1:String, loca2:String):String{
        if(loca1=="전체"){
            return ""
        }else{
            if(loca2.contains("전체")){
                return loca1
            }
        }
        return  loca1 + " "+loca2
    }

    fun checkChange_sports() :Boolean{
        val change =  Filter_data.soccer || Filter_data.futsal || Filter_data.baseball || Filter_data.basketball ||
                Filter_data.badminton || Filter_data.tennis|| Filter_data.pingpong ||  Filter_data.volleyball

        return change
    }

    fun checkChange_facility():Boolean{
        val change = Filter_data.parking || Filter_data.shower || Filter_data.cooling|| Filter_data.heating
        return  change
    }

    fun checkChange_location(loca1: String):Boolean{
        if(loca1!="전체"){
            return true
        }
        return false
    }


}