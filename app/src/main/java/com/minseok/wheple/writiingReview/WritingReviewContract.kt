package com.minseok.wheple.writiingReview

import android.net.Uri
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.reviewEditorPhotoadapter.ReviewEditorPhotoAdapter

interface WritingReviewContract {
    interface View : BaseView<Presenter> {

        fun setinfo(name:String, datetime:String)
        fun buttonOn()
        fun buttonOff()
        fun showToast(string: String)

        fun checkPermission():Boolean
        fun showPermissionDialog()
        fun chooseGallery()

        fun connectAdapter()
        fun getPath(uri: Uri): String
        fun reviewsuccess()
        fun shortedittext()
    }

    interface Presenter : BasePresenter {
        fun getinfo(no:String, writingReviewPhotoAdapter: ReviewEditorPhotoAdapter)
        fun reviewCheck(rating:Float, review:String)
        fun sendReview(resevationNo:String, rating:Float, review:String, wr_itemsList:ArrayList<String>,datetime: String)

        fun ChooseGalleryClick()
        fun showPreview(mFilePath : String)

    }
}