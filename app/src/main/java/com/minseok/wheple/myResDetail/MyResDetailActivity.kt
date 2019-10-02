package com.minseok.wheple.myResDetail

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_my_reservatioin_detail.*

class MyResDetailActivity : AppCompatActivity(),  MyResDetailContract.View {
    private lateinit var mPresenter: MyResDetailContract.Presenter


    override fun setPresenter(presenter: MyResDetailContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reservatioin_detail)
        mPresenter = MyResDetailPresenter(this)

        img_res_detail_place.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY)

        view_res_detail_back.setOnClickListener {
            onBackPressed()
        }

    }

}