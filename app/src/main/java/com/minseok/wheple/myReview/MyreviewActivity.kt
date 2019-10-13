package com.minseok.wheple.myReview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.minseok.wheple.R
import com.minseok.wheple.myReview.adapter.MyreviewAdapter
import kotlinx.android.synthetic.main.activity_my_review.*

class MyreviewActivity : AppCompatActivity(), MyreviewContract.View {
    private lateinit var mPresenter: MyreviewContract.Presenter
    private val linearLayoutManager by lazy { LinearLayoutManager(this) }
    private lateinit var myreviewAdapter: MyreviewAdapter

    override fun setPresenter(presenter:  MyreviewContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)
        mPresenter = MyreviewPresenter(this)

        img_my_review_back.setOnClickListener {
            onBackPressed()
        }
        makeRecycler()

    }

    override  fun connectAdapter(){

        recycler_myreview.adapter = myreviewAdapter

    }

    override fun showTextNothing(){
        text_myreview_nothing.visibility = View.VISIBLE
    }

    fun makeRecycler(){
        recycler_myreview.layoutManager = linearLayoutManager
        myreviewAdapter = MyreviewAdapter(mPresenter)

        mPresenter.getlist(myreviewAdapter)
    }

    fun destroyRecycler(){
        recycler_myreview.layoutManager = null
    }

    override fun get_base_url() :String{
        return getString(R.string.base_url)
    }

}