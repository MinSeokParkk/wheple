package com.minseok.wheple.mypoint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.mypoint.adapter.MypointAdapter
import kotlinx.android.synthetic.main.activity_mypoint.*

class MypointActivity  : AppCompatActivity(),  MypointContract.View {
    private lateinit var mPresenter:   MypointContract.Presenter
    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var mAdapter: MypointAdapter

    override fun setPresenter(presenter:  MypointContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypoint)
        mPresenter = MypointPresenter(this)

        recycler_mypoint.setNestedScrollingEnabled(false)

        text_mypoint_point.text = intent.getStringExtra("myPoint")

        img_mypoint_back.setOnClickListener {
            onBackPressed()
        }
        makeRecycler()

    }

    override fun connectAdapter(){
        recycler_mypoint.adapter = mAdapter
    }

    fun makeRecycler(){
        recycler_mypoint.layoutManager = linearLayoutManager
        mAdapter = MypointAdapter()
        mPresenter.getpoints(mAdapter)
    }


}