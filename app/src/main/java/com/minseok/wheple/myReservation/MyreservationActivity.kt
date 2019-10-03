package com.minseok.wheple.myReservation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.minseok.wheple.R
import com.minseok.wheple.myReservation.adapter.MyreservationAdapter
import kotlinx.android.synthetic.main.activity_my_reservation.*


class MyreservationActivity: AppCompatActivity(), MyreservationContract.View {
    private lateinit var mPresenter: MyreservationContract.Presenter
    private val linearLayoutManager by lazy { LinearLayoutManager(this) }
    private lateinit var myreservationAdapter: MyreservationAdapter

    override fun setPresenter(presenter: MyreservationContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reservation)
        mPresenter = MyreservationPresenter(this)

        img_my_res_back.setOnClickListener {
            onBackPressed()
        }
        makeRecycler()

    }



    override  fun connectAdapter(){

        recycler_myreservation.adapter = myreservationAdapter

    }

    override fun showTextNothing(){
        text_myres_nothing.visibility= View.VISIBLE
    }

    override fun makeRecycler(){
        recycler_myreservation.layoutManager = linearLayoutManager
        myreservationAdapter = MyreservationAdapter()

        mPresenter.getlist(myreservationAdapter)
    }

    override fun destroyRecycler(){
        recycler_myreservation.layoutManager = null
    }
}