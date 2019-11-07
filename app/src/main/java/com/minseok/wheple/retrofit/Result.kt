package com.minseok.wheple.retrofit

import com.minseok.wheple.PlaceReviewItem
import com.minseok.wheple.cancel.CancelItem
import com.minseok.wheple.home.PlaceItem
import com.minseok.wheple.modifyingReview.ModifyingReviewItem
import com.minseok.wheple.myResDetail.MyResDetailItem
import com.minseok.wheple.myReservation.MyreservationItem
import com.minseok.wheple.myReview.MyreviewItem
import com.minseok.wheple.myinfo.MyinfoItem
import com.minseok.wheple.mypage.MypageItem
import com.minseok.wheple.near.NearDetailItem
import com.minseok.wheple.near.NearItem
import com.minseok.wheple.place.PlaceDetailItem
import com.minseok.wheple.reservation.ReservationItem
import com.minseok.wheple.select_date_time.SelectTimeItem
import com.minseok.wheple.writiingReview.WritingReviewItem

object Result {
    data class Connectresult(val result: String,
                             val places: ArrayList<PlaceItem>,
                             val place : PlaceDetailItem,
                             val date : SelectTimeItem,
                             val res: ReservationItem,
                             val mypage: MypageItem,
                             val myres:ArrayList<MyreservationItem>,
                             val myres_de:MyResDetailItem,
                             val cancelSet:CancelItem,
                             val reW:WritingReviewItem,
                             val myrev:ArrayList<MyreviewItem>,
                             val mod : ModifyingReviewItem,
                             val myinfo:MyinfoItem,
                             val pr:ArrayList<PlaceReviewItem>,
                             val ni:ArrayList<NearItem>,
                             val ndi: NearDetailItem

                             )

}