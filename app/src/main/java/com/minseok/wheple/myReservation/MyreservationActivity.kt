package com.minseok.wheple.myReservation

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.minseok.wheple.R
import com.minseok.wheple.myReservation.adapter.MyreservationAdapter
import kotlinx.android.synthetic.main.activity_my_reservation.*


class MyreservationActivity: AppCompatActivity(), MyreservationContract.View {
    private lateinit var mPresenter: MyreservationContract.Presenter
    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var myreservationAdapter: MyreservationAdapter


    class MyClass{
        companion object{
            var activity: Activity? = null
            var cancel : Boolean = false
        }

    }

    override fun setPresenter(presenter: MyreservationContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reservation)
        mPresenter = MyreservationPresenter(this)

        MyClass.activity = this@MyreservationActivity


        img_my_res_back.setOnClickListener {
            onBackPressed()
        }
        makeRecycler()

    }

    override fun onStart() {


        if(MyClass.cancel){
             destroyRecycler()
             makeRecycler()
            MyClass.cancel=false
        }

        super.onStart()
    }




    override  fun connectAdapter(){

        recycler_myreservation.adapter = myreservationAdapter


    }

    override fun showTextNothing(){
        text_myres_nothing.visibility = View.VISIBLE
    }

    fun makeRecycler(){
        recycler_myreservation.layoutManager = linearLayoutManager
        myreservationAdapter = MyreservationAdapter(mPresenter)

        mPresenter.getlist(myreservationAdapter)
    }

    fun destroyRecycler(){
        recycler_myreservation.layoutManager = null
    }

}