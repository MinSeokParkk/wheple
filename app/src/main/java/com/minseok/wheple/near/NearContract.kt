package com.minseok.wheple.near

import com.google.android.gms.maps.model.Marker
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface NearContract {

    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun loc_setting(x:Double, y:Double,no:String)
        fun changeSelectedMarker(marker: Marker)
        fun setDetail(photo:String, name:String, rating:String, review:String, price:String, marker: Marker)
    }

    interface Presenter : BasePresenter {
        fun getPlace(lat_ne:Float, lng_ne:Float, lat_sw:Float, lng_sw:Float)
        fun getDetail(no:String, marker: Marker)
    }
}