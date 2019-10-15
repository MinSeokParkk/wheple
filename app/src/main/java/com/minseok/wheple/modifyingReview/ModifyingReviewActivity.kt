package com.minseok.wheple.modifyingReview

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Toast
import com.minseok.wheple.Gallery
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.myReview.MyreviewActivity
import com.minseok.wheple.reviewEditorPhotoadapter.DragManageAdapter
import com.minseok.wheple.reviewEditorPhotoadapter.ReviewEditorPhotoAdapter
import kotlinx.android.synthetic.main.activity_review_writer.*

class ModifyingReviewActivity :AppCompatActivity(), ModifyingReviewContract.View {
    private lateinit var mPresenter:ModifyingReviewContract.Presenter

    private val linearLayoutManager by lazy { LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) }
    private lateinit var modifyingReviewPhotoAdapter: ReviewEditorPhotoAdapter

    override fun setPresenter(presenter: ModifyingReviewContract.Presenter) {
        this.mPresenter = presenter
    }

    private lateinit var gallery : Gallery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_writer)
        mPresenter = ModifyingReviewPresenter(this)
        recycler_rev_photos.layoutManager = linearLayoutManager
        modifyingReviewPhotoAdapter = ReviewEditorPhotoAdapter()

        gallery = Gallery()

        img_rev_writer_back.setOnClickListener {
            onBackPressed()
        }

        mPresenter.getinfo(intent.getStringExtra("no"), modifyingReviewPhotoAdapter, get_base_url())


        text_rev_writer_subject.text = "리뷰 수정하기"

        rating_rev_writer.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating < 0.5f) {
                rating_rev_writer.rating = 0.5f
            }

            inputcheck_m()
        }

        edit_rev_writer.afterTextChanged {
            inputcheck_m()
        }

        constraint_rev_addphoto.setOnClickListener {
            mPresenter.ChooseGalleryClick()
        }

        // 아이템 드래그앤드롭으로 순서 바꾸기 Setup ItemTouchHelper
        val callback = DragManageAdapter(
            modifyingReviewPhotoAdapter, this,
            ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT).or(ItemTouchHelper.LEFT).or(ItemTouchHelper.RIGHT), -1
        )
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recycler_rev_photos)

        button_rev_writer_sending.setOnClickListener {
            mPresenter.sendReview(
                intent.getStringExtra("no"),
                rating_rev_writer.rating,
                edit_rev_writer.text.toString(),
                modifyingReviewPhotoAdapter.wr_itemsList
            )
        }


    }

    fun inputcheck_m() {
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

    override fun setinfo(name: String, datetime: String, rating: String, review: String) {
        text_rev_wrtier_name.text = name
        text_rev_writer_daytime.text = datetime
        rating_rev_writer.rating = rating.toFloat()
        edit_rev_writer.setText(review.replace("|||", "\n"))
    }

    override fun get_base_url() :String{
        return getString(R.string.base_url)
    }

    override  fun connectAdapter(){
        recycler_rev_photos.adapter = modifyingReviewPhotoAdapter
    }

    override fun checkPermission(): Boolean {
//        val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        if (result == PackageManager.PERMISSION_DENIED) return false
//        return true
        return gallery.checkPermission(this)
    }

    override fun showPermissionDialog() {
        println("퍼미션 다이얼로그")
//        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        gallery.showPermissionDialog(this)
    }
    override fun chooseGallery() {
//        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        startActivityForResult(pickPhoto, 102)
        gallery.chooseGallery(this)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode==1000){
//            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                mPresenter.ChooseGalleryClick()
//            }
//        }
        gallery.requestpermissionresult(requestCode, grantResults, this)
    }

    override fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    override fun finishmodifying() {
        MyreviewActivity.MyClass.cancel = true
        finish()
    }

    override fun shortedittext() {
        edit_rev_writer.requestFocus()
    }




}