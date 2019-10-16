package com.minseok.wheple.myinfo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.minseok.wheple.BuildConfig
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_myinformation.*
import kotlinx.android.synthetic.main.dialog_way_to_get_photo.view.*
import java.io.File
import java.io.IOException
import java.util.*

class MyinfoActivity : AppCompatActivity(), MyinfoContract.View {
    private lateinit var mPresenter: MyinfoContract.Presenter

    override fun setPresenter(presenter: MyinfoContract.Presenter) {
        this.mPresenter = presenter
    }

    private lateinit var photoURI:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myinformation)
        mPresenter = MyinfoPresenter(this)

        mPresenter.bringData()

        img_myinfo_back.setOnClickListener {
            onBackPressed()
        }

        img_myinfo_photo.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_way_to_get_photo, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()
// 크기조정           mAlertDialog.window.setLayout(620, 375)

            mDialogView.button_camera.setOnClickListener {
                mAlertDialog.dismiss()
                mPresenter.ChooseCameraClick()
            }

            mDialogView.button_album.setOnClickListener {
                mAlertDialog.dismiss()
                mPresenter.ChooseGalleryClick()

            }
            mDialogView.button_basicphoto.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }


    }

    override fun setmyinfo( photo: String, nickname: String, email: String,
                            password: String, username: String, phone: String ) {
        Glide.with(this)
            .load(get_base_url() + photo)
            .into(img_myinfo_photo)

        text_myinfo_nickname.text = nickname
        text_myinfo_email.text = email
        text_myinfo_pw.text = password
        text_myinfo_name.text = username
        text_myinfo_phone.text = phone

    }

    fun get_base_url(): String {
        return getString(R.string.base_url)
    }


    override fun checkPermission(int: Int): Boolean {
        if (int == 1) {
            val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (result == PackageManager.PERMISSION_DENIED) return false
        } else if (int == 2) {
            val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (result == PackageManager.PERMISSION_DENIED) return false
        }
        return true
    }

    override fun showPermissionDialog(int: Int) {
        println("퍼미션 다이얼로그")
        if (int == 1) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1000)
        } else if (int == 2) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2000)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {

            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mPresenter.ChooseCameraClick()
            }
        } else if (requestCode == 2000) {

            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.ChooseGalleryClick()
            }
        }

    }

    override fun chooseGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, 102)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                if (data.getData() != null) {
                    var mPhotoPath: Uri = data.getData()

                    println("포토 패쓰는 : " + getPath(mPhotoPath))
                    mPresenter.savephoto(getPath(mPhotoPath))
                }
            }
        }else if(requestCode==101){
            if (resultCode == Activity.RESULT_OK ) {

                    println("phtoURI는???   :  " + photoURI.toString())

                    mPresenter.savephoto(photoURI.toString())

            }
        }
    }


    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun availableDisk(): Int {
        val mFilePath = getFilePath()
        val freeSpace: Long = mFilePath.freeSpace
        return Math.round(freeSpace.toFloat() / 1048576)
    }

    fun getFilePath(): File {
        return Environment.getExternalStorageDirectory()
    }

    override fun newFile(): File? {
        val cal = Calendar.getInstance()
        val timeInMillis :Long = cal.timeInMillis
        val mFilename = "JPEG_"+timeInMillis.toString()+".jpeg"
        val mFilePath:File = getFilePath()

        try {
            val newFile:File = File(mFilePath.getAbsolutePath()+"/Pictures", mFilename)
            newFile.createNewFile()
            return newFile
        }catch (e : IOException) {
            e.printStackTrace()
        }
        return  null
    }

    override fun startCamera(file: File) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePictureIntent.resolveActivity(packageManager)!=null){
            photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider", file)
            println("phtoURI는??!   :  "+photoURI.toString())
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, 101)
            photoURI= Uri.parse(file.toString())

        }
    }

    override fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    override fun  displayImagePreview(photo:String){
        Glide.with(this)
            .load(photo)
            .into(img_myinfo_photo)

    }

}