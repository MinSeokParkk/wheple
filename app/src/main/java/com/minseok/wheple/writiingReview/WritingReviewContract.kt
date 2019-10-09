package com.minseok.wheple.writiingReview

import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView

interface WritingReviewContract {
    interface View : BaseView<Presenter> {

        fun setinfo(name:String, datetime:String)
        fun buttonOn()
        fun buttonOff()
        fun showToast(string: String)

        fun checkPermission():Boolean
        fun showPermissionDialog()
        fun chooseGallery()
        fun displayImagePreview(mFilePath : String)
    }

    interface Presenter : BasePresenter {
        fun getinfo(no:String)
        fun reviewCheck(rating:Float, review:String)
        fun sendReview(no:String, rating:Float, review:String)

        fun ChooseGalleryClick()
        fun showPreview(mFilePath : String)
    }
}