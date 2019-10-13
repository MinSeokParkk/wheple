package com.minseok.wheple.modifyingReview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.modifyingReview.adapter.ModifyingReviewPhotoAdapter
import kotlinx.android.synthetic.main.activity_review_writer.*

class ModifyingReviewActivity :AppCompatActivity(), ModifyingReviewContract.View {
    private lateinit var mPresenter:ModifyingReviewContract.Presenter

    private val linearLayoutManager by lazy { LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) }
    private lateinit var modifyingReviewPhotoAdapter: ModifyingReviewPhotoAdapter

    override fun setPresenter(presenter: ModifyingReviewContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_writer)
        mPresenter = ModifyingReviewPresenter(this)
        recycler_rev_photos.layoutManager = linearLayoutManager
        modifyingReviewPhotoAdapter = ModifyingReviewPhotoAdapter()

        img_rev_writer_back.setOnClickListener {
            onBackPressed()
        }

        mPresenter.getinfo(intent.getStringExtra("no"), modifyingReviewPhotoAdapter)


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


}