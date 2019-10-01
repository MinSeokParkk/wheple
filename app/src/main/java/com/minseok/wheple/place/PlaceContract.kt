package com.minseok.wheple.place

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface PlaceContract {

    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun setPlace(
            name: String, address: String, price: String, rating: String, review: String, photo: String,
            parking: String, shower: String, heating: String, sports: String, introduction :String, guide:String
        )
        fun noParking()
        fun noShower()
        fun noHeating()
        fun movetoSelect(string: String)
         }


    interface Presenter : BasePresenter{
        fun showDetail()
        fun calling()
        fun sendPlaceNo()
    }
}