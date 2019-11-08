package com.minseok.wheple

 fun meterDistanceBetweenPoints(
    lat_a: Float,
    lng_a: Float,
    lat_b: Float,
    lng_b: Float
): String {
    val pk = (180f / Math.PI).toFloat()

    val a1 = lat_a / pk
    val a2 = lng_a / pk
    val b1 = lat_b / pk
    val b2 = lng_b / pk

    val t1 =
        Math.cos(a1.toDouble()) * Math.cos(a2.toDouble()) * Math.cos(b1.toDouble()) * Math.cos(b2.toDouble())
    val t2 =
        Math.cos(a1.toDouble()) * Math.sin(a2.toDouble()) * Math.cos(b1.toDouble()) * Math.sin(b2.toDouble())
    val t3 = Math.sin(a1.toDouble()) * Math.sin(b1.toDouble())
    val tt = Math.acos(t1 + t2 + t3)

     val distanceD = 6366000 * tt

     var result = ""

     if(distanceD<1000){
         result = Math.round(distanceD).toString() + "m"
     }else if(distanceD<10000){
         result = String.format("%.1f", distanceD/1000) + "km"
     }else{
         result = Math.round(distanceD/1000).toString() + "km"
     }


    return result
}