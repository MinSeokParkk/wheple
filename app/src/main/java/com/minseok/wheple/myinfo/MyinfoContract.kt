package com.minseok.wheple.myinfo

import android.net.Uri
import com.minseok.wheple.base.BasePresenter
import com.minseok.wheple.base.BaseView
import java.io.File

interface MyinfoContract {

    interface View : BaseView<Presenter> {

        fun setmyinfo(photo:String, nickname:String, email:String, password:String,
                      username:String, phone:String)


        fun checkPermission(int: Int):Boolean
        fun showPermissionDialog(int: Int)
        fun chooseGallery()
        fun showToast(string: String)
        fun availableDisk():Int
        fun newFile():File?
        fun startCamera(file: File)
        fun getPath(uri: Uri): String
        fun displayImagePreview(photo:String)
    }

    interface Presenter : BasePresenter {
        fun bringData()
        fun ChooseGalleryClick()
        fun ChooseCameraClick()
        fun savephoto(photo: String)
    }
}