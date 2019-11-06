package com.minseok.wheple.map

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface MapContract {

    interface View : BaseView<Presenter> {

        var  currentPosition : LatLng?
        fun loc_setting(x:Double, y:Double)
        fun showToast(string: String)
        fun showCurrentP()
        fun moveTocurrentP()
        fun checkPermission(): Boolean
        fun showPermissionDialog()
        fun startLocationUpdates()
        fun showProgress()
    }

    interface Presenter : BasePresenter {
        fun setAddr(addr:String, context: Context)
        fun currentPosition()

    }
}