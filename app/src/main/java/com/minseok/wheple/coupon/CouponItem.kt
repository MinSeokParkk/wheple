package com.minseok.wheple.coupon


data class CouponItem(var discount:Int, var min:Int, var name:String,
                      var start_date: String, var end_date:String, var no:String) {
}