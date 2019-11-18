package com.minseok.wheple.coupon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.coupon.adapter.CouponAdapter
import kotlinx.android.synthetic.main.activity_coupon_reservation.*

class CouponActivity : AppCompatActivity(), CouponContract.View {


    private lateinit var mPresenter:  CouponContract.Presenter

    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var cAdapter: CouponAdapter

    override fun setPresenter(presenter:  CouponContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_reservation)
        mPresenter =  CouponPresenter(this)

        img_coupon_back.setOnClickListener {
            onBackPressed()
        }

        Log.d("couponA1", "intent 확인 : "+intent.getStringExtra("price"))
        makeRecycler_search()




    }

    override fun connectAdapter(){
        recycler_coupon.adapter = cAdapter
    }

    fun makeRecycler_search(){
        recycler_coupon.layoutManager = linearLayoutManager
        cAdapter = CouponAdapter()

        mPresenter.getCoupon(intent.getStringExtra("price"), cAdapter)
    }

    fun destroyRecycler_search(){
        recycler_coupon.layoutManager = null
    }
}