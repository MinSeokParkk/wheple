package com.minseok.wheple

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult

class Gallery {

    fun checkPermission(context: Context): Boolean {
        val result = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_DENIED) return false
        return true
    }


    fun showPermissionDialog(activity:Activity) {
        println("퍼미션 다이얼로그")
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)

    }

    fun chooseGallery(activity:Activity) {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(activity, pickPhoto, 102, null)

    }

    fun requestpermissionresult(requestCode: Int, grantResults: IntArray,activity: Activity ){
        if(requestCode==1000){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               chooseGallery(activity)
            }
        }
    }


}