package com.minseok.wheple.modifyingReview

import android.net.Uri
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.modifyingReview.adapter.ModifyingReviewPhotoAdapter

interface ModifyingReviewContract {

    interface View : BaseView<Presenter> {

        fun setinfo(name:String, datetime:String, rating:String, review: String)
        fun buttonOn()
        fun buttonOff()
        fun showToast(string: String)

//        fun checkPermission():Boolean
//        fun showPermissionDialog()
//        fun chooseGallery()
//        fun displayImagePreview(mFilePath : String)
//
//        fun connectAdapter()
//        fun getPath(uri: Uri): String
//        fun reviewsuccess()
    }

    interface Presenter : BasePresenter {
        fun getinfo(no:String,  modifyingReviewPhotoAdapter: ModifyingReviewPhotoAdapter)
        fun reviewCheck(rating:Float, review:String)
//        fun sendReview(no:String, rating:Float, review:String, wr_itemsList:ArrayList<String>,datetime: String)
//
//        fun ChooseGalleryClick()
//        fun showPreview(mFilePath : String)
//        fun addphoto(mFilePath: String)
    }
}