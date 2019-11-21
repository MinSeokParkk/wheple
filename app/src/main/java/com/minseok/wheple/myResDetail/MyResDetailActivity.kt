package com.minseok.wheple.myResDetail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ScrollView
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.cancel.CancelActivity
import com.minseok.wheple.place.PlaceActivity
import kotlinx.android.synthetic.main.activity_my_reservatioin_detail.*
import kotlinx.android.synthetic.main.activity_place.*

class MyResDetailActivity : AppCompatActivity(),  MyResDetailContract.View {
    private lateinit var mPresenter: MyResDetailContract.Presenter


    override fun setPresenter(presenter: MyResDetailContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reservatioin_detail)
        mPresenter = MyResDetailPresenter(this)

        img_res_detail_place.setColorFilter(Color.parseColor("#797979"), PorterDuff.Mode.MULTIPLY)

        img_res_detail_place.setOnClickListener {
            mPresenter.settingExtra()
        }

        view_res_detail_back.setOnClickListener {
            onBackPressed()
        }

        mPresenter.rememeberNo(intent.getStringExtra("no"))



        button_myres_de_cancel.setOnClickListener {
            val nextIntent = Intent(this, CancelActivity::class.java)
            nextIntent.putExtra("no",  intent.getStringExtra("no"))
            startActivity(nextIntent)
        }

    }

    override fun onStart() {

        scroll_myres_detail.fullScroll(ScrollView.FOCUS_UP)
        mPresenter.bringData()
        super.onStart()
    }

    override fun setData(place: String,photo: String, date: String, time: String, name: String, phone: String,
                         price: String, point: String, payment: String,refund_price:String, refund_point:String,
                         usedcoupon:String, refund_coupon:String) {
        text_myres_de_place.text = place

        Glide.with(this)
            .load(photo)
            .into(img_res_detail_place)

        text_myres_de_date.text =date
        text_myres_de_time.text = time
        text_myres_de_name.text = name
        text_myres_de_phone.text = phone
        text_myres_de_price.text = price
        text_myres_de_point.text = point
        text_myres_de_payment.text = payment
        text_myres_de_refund.text = refund_price
        text_myres_de_refund_point.text = refund_point
        text_myres_de_coupon.text = usedcoupon
        text_myres_de_refund_coupon.text = refund_coupon

    }


    override fun gotoPlace(no: String) {
        val nextIntent = Intent(this, PlaceActivity::class.java)
        nextIntent.putExtra("no",  no)
        startActivity(nextIntent)
    }

    override fun using_used(state:String){
        text_myres_de_state.text = state
        text_myres_de_state.setBackgroundResource(R.color.bluegrey)
        button_myres_de_cancel.visibility = View.GONE
    }

    override fun cancel(){
        text_myres_de_state.text = "예약취소"
        text_myres_de_state.setBackgroundResource(R.color.red)
        button_myres_de_cancel.visibility = View.GONE
        constraint_myres_de_cancel.visibility = View.VISIBLE
        text_myres_de_payment.setTextColor(getResources().getColor(R.color.red))
    }

}