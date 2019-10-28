package com.minseok.wheple.home

import android.widget.CheckBox
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.home.adapter.PlaceAdapter

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun connectAdapter()
        fun makeRecycler()
        fun destroyRecycler()
        fun setPlaceNumber(string: String)
        fun set_sports(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox,
                      c5:CheckBox, c6:CheckBox, c7:CheckBox, c8:CheckBox,
                      soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                      badminton:Boolean, tennis:Boolean, pingpong:Boolean, volley:Boolean)
        fun set_facility(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox,
                         parking:Boolean, shower:Boolean, cooling:Boolean, heating:Boolean)
        fun filter_sportsBack(change:Boolean)
        fun filter_facilityBack(change:Boolean)
        fun filter_locationBack(change:Boolean)
        fun homefilter_Back(change:Boolean)
        fun showNothing(itemsize:Int)
    }

    interface Presenter : BasePresenter {

        fun getlist(placeAdapter: PlaceAdapter)
        fun clear()
        fun setfilter_sports(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox, c5:CheckBox, c6:CheckBox, c7:CheckBox, c8:CheckBox)
        fun setfilter_facility(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox)
        fun filter_sports(soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                          badminton:Boolean, tennis:Boolean, pingpong:Boolean, volley:Boolean)
        fun filter_facility(parking: Boolean, shower: Boolean, cooling: Boolean, heating: Boolean)
        fun get_loc1():String
        fun get_loc2():String
        fun save_loc(loc1:String, loc2:String)
        fun checkChange()
        fun set_sportsBack()
        fun set_facilityBack()
        fun set_locationBack()
        fun set_homefilterBack()

     }
}