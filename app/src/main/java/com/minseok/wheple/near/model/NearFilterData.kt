package com.minseok.wheple.near.model

class NearFilterData {

        var soccerN :Boolean = false
        var futsalN :Boolean = false
        var baseballN :Boolean = false
        var basketballN :Boolean = false
        var badmintonN :Boolean = false
        var tennisN :Boolean = false
        var pingpongN :Boolean = false
        var volleyballN:Boolean = false

        var parkingN:Boolean = false
        var showerN:Boolean = false
        var coolingN:Boolean =false
        var heatingN:Boolean = false




     fun checkChange() : Int{
          var count = 0
             val filters = arrayOf(this.soccerN, this.futsalN, this.baseballN, this.basketballN,
                     this.badmintonN, this.tennisN, this.pingpongN,  this.volleyballN, this.parkingN, this.showerN,
                     this.coolingN, this.heatingN)
             for(i in 0..filters.size-1){
                     if(filters[i]){
                             count++
                     }
             }
         return count

      }



}