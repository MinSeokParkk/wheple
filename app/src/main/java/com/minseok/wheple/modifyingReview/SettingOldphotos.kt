package com.minseok.wheple.modifyingReview

class SettingOldphotos {
    fun setting( photo1:String, photo2:String, photo3:String, baseurl: String): ArrayList<String>{
        var eldlist: ArrayList<String>
        eldlist = ArrayList()

        if(photo1!=""){
            eldlist.add(baseurl+photo1)
        }
        if(photo2!=""){
            eldlist.add(baseurl+photo2)
        }
        if(photo3!=""){
            eldlist.add(baseurl+photo3)
        }
        return  eldlist
    }


}