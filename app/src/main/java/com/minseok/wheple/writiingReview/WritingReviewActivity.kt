package com.minseok.wheple.writiingReview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_review_writer.*

class WritingReviewActivity  : AppCompatActivity(), WritingReviewContract.View {
    private lateinit var mPresenter : WritingReviewContract.Presenter

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
            if(rating<0.5f){
                rating_rev_writer.rating = 0.5f
            }

            inputcheck()
        }

        edit_rev_writer.afterTextChanged {
            inputcheck()
        }

        button_rev_writer_sending.setOnClickListener {
            mPresenter.sendReview(intent.getStringExtra("no"),rating_rev_writer.rating, edit_rev_writer.text.toString())
        }


    }

    override fun setinfo(name: String, datetime: String) {
        text_rev_wrtier_name.text = name
        text_rev_writer_daytime.text = datetime
    }

    fun inputcheck(){
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

}