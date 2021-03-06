package com.minseok.wheple.place

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.place.adapter.PlaceReviewAdapter

interface PlaceContract {

    interface View : BaseView<Presenter> {
        fun showToast(string: String)
        fun setPlace(
            name: String, address: String, price: String, rating: String, review: String, photos: Array<String>,
            parking: String, shower: String, heating: String, sports: String, introduction :String, guide:String, dibs:Boolean)
        fun noParking()
        fun noShower()
        fun noHeating()
        fun movetoSelect(string: String)
        fun toptextVisibility(state:Int)
        fun get_base_url() :String
        fun connectAdapter()
        fun no_review()

        fun gotoReview(placeNO:String, rating: String, review: String)

        fun ask_message(phone:String)
        fun ask_phone(phone: String)

        fun gotoMap(no:String)
        fun changeHeart(result:Boolean)
        fun gotoLogin(no_here: String)
    }


    interface Presenter : BasePresenter{
        fun showDetail()
        fun calling()
        fun messaging()
        fun sendPlaceNo()
        fun topplacename(scroll:Int)
        fun getReview(prAdapter:PlaceReviewAdapter, placeNo:String)
        fun review_more(rating: String, review: String)
        fun show_map()
        fun checkDibs(mydibs:Boolean)
         fun checkDibs_before()

    }
}