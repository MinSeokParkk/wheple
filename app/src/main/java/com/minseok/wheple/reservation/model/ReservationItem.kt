package com.minseok.wheple.reservation.model

data class ReservationItem(var name : String, var price : String, var hour : String,
                           var phone : String, var point : String, var username:String, var coupon:Int) {
}