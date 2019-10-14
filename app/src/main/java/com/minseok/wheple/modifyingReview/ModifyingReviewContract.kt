package com.minseok.wheple.modifyingReview

import android.net.Uri
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import com.minseok.wheple.reviewEditorPhotoadapter.ReviewEditorPhotoAdapter

interface ModifyingReviewContract {

    interface View : BaseView<Presenter> {

        fun setinfo(name:String, datetime:String, rating:String, review: String)
        fun buttonOn()
        fun buttonOff()
        fun showToast(string: String)
        fun get_base_url() :String

        fun checkPermission():Boolean
        fun showPermissionDialog()
        fun chooseGallery()

        fun connectAdapter()
        fun getPath(uri: Uri): String
        fun finishmodifying()
        fun shortedittext()
    }

    interface Presenter : BasePresenter {
        fun getinfo(no:String,  modifyingReviewPhotoAdapter: ReviewEditorPhotoAdapter, baseurl:String)
        fun reviewCheck(rating:Float, review:String)
        fun sendReview(no:String, rating:Float, review:String, wr_itemsList:ArrayList<String>)

        fun ChooseGalleryClick()
        fun showPreview(mFilePath : String)

    }
}