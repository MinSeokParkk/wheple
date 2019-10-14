package com.minseok.wheple.myReview

import android.app.Activity
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

    class MyClass{
        companion object{
            var activity: Activity? = null
            var cancel : Boolean = false
        }

    }

    override fun setPresenter(presenter:  MyreviewContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)
        mPresenter = MyreviewPresenter(this)

        MyClass.activity = this@MyreviewActivity

        img_my_review_back.setOnClickListener {
            onBackPressed()
        }
        makeRecycler()

    }

    override fun onStart() {

        if(MyClass.cancel){
            destroyRecycler()
             makeRecycler()
//            리싸이클러뷰 스크롤을  수정된 리뷰에 맞춰 주고 싶은데 작동 안함.
//            linearLayoutManager.scrollToPositionWithOffset(1, 0)
            MyClass.cancel=false
        }

        super.onStart()
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