package com.minseok.wheple.near

import android.widget.CheckBox
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface NearContract {

    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun loc_setting(x:Double, y:Double,no:String)
        fun changeSelectedMarker(marker: Marker)
        fun setDetail(photo:String, name:String, rating:String, review:String, price:String, marker: Marker, distance:String)

        fun set_checkbox(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox,
                       c5:CheckBox, c6:CheckBox, c7:CheckBox, c8:CheckBox,
                       c9: CheckBox, c10: CheckBox, c11: CheckBox, c12: CheckBox,
                       soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                       badminton:Boolean, tennis:Boolean, pingpong:Boolean, volley:Boolean,
                       parking:Boolean, shower:Boolean, cooling:Boolean, heating:Boolean)

        fun change_filterlayout(change:Int)

        fun set_placeNumber(num:Int)

        fun checkPermission(): Boolean
        fun showPermissionDialog()

        fun  startLocationUpdates()
        fun showNoPermissionView()
        fun showPermissionView()
    }

    interface Presenter : BasePresenter {
        fun getPlace(lat_ne:Float, lng_ne:Float, lat_sw:Float, lng_sw:Float)
        fun getDetail(no:String, marker: Marker, currentposition:LatLng)
        fun setfilter(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox, c5: CheckBox, c6: CheckBox,
                      c7: CheckBox, c8: CheckBox, c9:CheckBox, c10:CheckBox, c11:CheckBox, c12:CheckBox)

        fun filter_change(soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                          badminton:Boolean, tennis:Boolean, pingpong:Boolean, volleyball:Boolean,
                          parking:Boolean, shower:Boolean, cooling:Boolean, heating:Boolean)
        fun change_filter()
        fun check_viewhasPer()
    }
}