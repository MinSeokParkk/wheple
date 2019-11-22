package com.minseok.wheple.mycoupon

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.addcoupon.AddcouponActivity
import com.minseok.wheple.mycoupon.adapter.MycouponAdapter
import kotlinx.android.synthetic.main.activity_mycoupon.*

class MycouponActivity: AppCompatActivity(), MycouponContract.View {

    companion object{
        var change = false
    }

    private lateinit var mPresenter: MycouponContract.Presenter

    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var cAdapter: MycouponAdapter

    override fun setPresenter(presenter: MycouponContract.Presenter) {
        this.mPresenter = presenter
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycoupon)
        mPresenter = MycouponPresenter(this)

        img_mycou_back.setOnClickListener {
            onBackPressed()
        }

        text_mycou_add.setOnClickListener {
            val nextIntent = Intent(this, AddcouponActivity::class.java)
            startActivity(nextIntent)
        }

        makeRecycler()
    }

    override fun onStart() {
        if(change){
            destroyRecycler()
            makeRecycler()
            change = false
        }
        super.onStart()
    }

    override fun connectAdapter(){
        recycler_mycou.adapter = cAdapter
    }

    fun makeRecycler(){
        recycler_mycou.layoutManager = linearLayoutManager
        cAdapter = MycouponAdapter()

        text_mycou_nocoupon.visibility = View.GONE
        mPresenter.getCoupon(cAdapter)
    }

    fun destroyRecycler(){
        recycler_mycou.layoutManager = null
    }

    override fun setNum(num:String){
        text_mycou_couponNum.text = num
    }

    override fun nocoupon(){
        text_mycou_nocoupon.visibility = View.VISIBLE
    }

}