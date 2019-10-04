package com.minseok.wheple.myResDetail

class PhoneChange{
    var newphone =""
     fun check(phone:String) : String {


         val array = phone.toCharArray()

         if(array.size==11){
             for(i in array.indices){
                 newphone = newphone+array[i].toString()
                 if(i == 2 || i == 6){
                     newphone = newphone+"-"
                 }
             }
         }else if(array.size==10){
             for(i in array.indices){
                 newphone = newphone+array[i].toString()
                 if(i == 2 || i == 5){
                     newphone = newphone+"-"
                 }
             }
         }
         return newphone
     }

}