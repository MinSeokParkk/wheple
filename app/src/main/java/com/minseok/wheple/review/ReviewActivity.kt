package com.minseok.wheple.review


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_review.*


class ReviewActivity : AppCompatActivity(), ReviewContract.View{

    private lateinit var mPresenter: ReviewContract.Presenter
//    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
//    private lateinit var prAdapter:ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        ReviewPresenter(this, intent.getStringExtra("no"))

        img_rv_back.setOnClickListener {
            onBackPressed()
        }
    }


    override fun setPresenter(presenter: ReviewContract.Presenter) {
        this.mPresenter = presenter

    }
}