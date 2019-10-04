package com.minseok.wheple.retrofit

import com.minseok.wheple.cancel.CancelItem
import com.minseok.wheple.home.PlaceItem
import com.minseok.wheple.myResDetail.MyResDetailItem
import com.minseok.wheple.myReservation.MyreservationItem
import com.minseok.wheple.mypage.MypageItem
import com.minseok.wheple.place.PlaceDetailItem
import com.minseok.wheple.reservation.ReservationItem
import com.minseok.wheple.select_date_time.SelectTimeItem

object Result {
    data class Connectresult(val result: String,
                             val places: ArrayList<PlaceItem>,
                             val place : PlaceDetailItem,
                             val date : SelectTimeItem,
                             val res: ReservationItem,
                             val mypage: MypageItem,
                             val myres:ArrayList<MyreservationItem>,
                             val myres_de:MyResDetailItem,
                             val cancelSet:CancelItem
                             )

}