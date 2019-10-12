package com.minseok.wheple.writiingReview

import android.net.Uri
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.writiingReview.adapter.WritingReviewPhotoAdapter

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

        fun connectAdapter()
        fun getPath(uri: Uri): String
    }

    interface Presenter : BasePresenter {
        fun getinfo(no:String, writingReviewPhotoAdapter: WritingReviewPhotoAdapter)
        fun reviewCheck(rating:Float, review:String)
        fun sendReview(no:String, rating:Float, review:String, wr_itemsList:ArrayList<String>)

        fun ChooseGalleryClick()
        fun showPreview(mFilePath : String)
        fun addphoto(mFilePath: String)
    }
}