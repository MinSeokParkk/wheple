package com.minseok.wheple.writiingReview

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_review_writer.*

class WritingReviewActivity  : AppCompatActivity(), WritingReviewContract.View {
    private lateinit var mPresenter: WritingReviewContract.Presenter

    override fun setPresenter(presenter: WritingReviewContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_writer)
        mPresenter = WritingReviewPresenter(this)

        img_rev_writer_back.setOnClickListener {
            onBackPressed()
        }

        mPresenter.getinfo(intent.getStringExtra("no"))

        rating_rev_writer.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating < 0.5f) {
                rating_rev_writer.rating = 0.5f
            }

            inputcheck()
        }

        edit_rev_writer.afterTextChanged {
            inputcheck()
        }

        button_rev_writer_sending.setOnClickListener {
            mPresenter.sendReview(
                intent.getStringExtra("no"),
                rating_rev_writer.rating,
                edit_rev_writer.text.toString()
            )
        }

        img_rev_writer_1.setOnClickListener {
            mPresenter.ChooseGalleryClick()
        }


    }

    override fun setinfo(name: String, datetime: String) {
        text_rev_wrtier_name.text = name
        text_rev_writer_daytime.text = datetime
    }

    fun inputcheck() {
        mPresenter.reviewCheck(rating_rev_writer.rating, edit_rev_writer.text.toString())
    }

    override fun buttonOff() {
        button_rev_writer_sending.setBackgroundResource(R.drawable.button_off)
    }

    override fun buttonOn() {
        button_rev_writer_sending.setBackgroundResource(R.drawable.button_on)
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun checkPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_DENIED) return false
        return true
    }

    override fun showPermissionDialog() {
        println("퍼미션 다이얼로그")
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)


    }

    override fun chooseGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, 102)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 102) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                if (data.getData() != null) {
                    var mPhotoPath: Uri = data.getData()
                    mPresenter.showPreview(mPhotoPath.toString())

                    println("포토 패쓰는 : " + mPhotoPath)
                }
            }
        }

    }

    override fun displayImagePreview(mFilePath: String) {
        Glide.with(this)
            .load(mFilePath)
            .apply(RequestOptions().centerCrop())
            .into(img_rev_writer_1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1000){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mPresenter.ChooseGalleryClick()
            }
        }
    }
}