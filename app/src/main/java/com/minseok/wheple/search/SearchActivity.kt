package com.minseok.wheple.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.search.adapter.SearchRecentAdapter
import com.minseok.wheple.search.adapter.SearchResultAdapter
import com.minseok.wheple.search.model.RecentOpenHelper
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchContract.View {

    private lateinit var mPresenter: SearchContract.Presenter
    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private val linearLayoutManager_r by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var resultAdapter: SearchResultAdapter
    private lateinit var recentAdapter: SearchRecentAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val rohelper = RecentOpenHelper(this)

        SearchPresenter(this, rohelper)

        edit_search.requestFocus()

        makeRecycler_recent()

        img_search_back.setOnClickListener {
            onBackPressed()
        }

        edit_search.afterTextChanged {
            destroyRecycler_search()
            makeRecycler_search()
        }

        text_search_delete.setOnClickListener { //검색 edittext 글 지우기
            edit_search.text.clear()
            deleteOff()
        }

        text_search_allDelete.setOnClickListener {  //최근 검색어 전체 삭제 눌렀을 때
            rohelper.deleteAll()
            mPresenter.refreshRecentR()
            noRecent(true)
        }

    }

    override fun onStop() {
        super.onStop()
        mPresenter.refreshRecentR() //최근 검색어 최신화시키기
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun connectAdapter(){
        recycler_search_result.adapter = resultAdapter
    }

    override fun connectAdapter_recent(){
        recycler_search_recent.adapter = recentAdapter
    }

    fun makeRecycler_search(){
        recycler_search_result.layoutManager = linearLayoutManager
        resultAdapter = SearchResultAdapter(mPresenter)

        mPresenter.sendSearching(edit_search.text.toString(), resultAdapter)
    }

    fun destroyRecycler_search(){
        recycler_search_result.layoutManager = null
    }

    override fun makeRecycler_recent(){
        recycler_search_recent.layoutManager = linearLayoutManager_r
        recentAdapter = SearchRecentAdapter(mPresenter)

        mPresenter.getRecent(recentAdapter)
    }

    override fun destroyRecycler_recent(){
        recycler_search_recent.layoutManager = null
    }

    override fun deleteOn(){
        text_search_delete.visibility = View.VISIBLE
        const_search_result.visibility = View.VISIBLE
        const_search_recent.visibility = View.GONE
    }

    override fun deleteOff(){
        text_search_delete.visibility = View.GONE
        const_search_result.visibility = View.GONE
        const_search_recent.visibility = View.VISIBLE
    }

    override fun noRecent(zero:Boolean){
        if(zero){
            text_search_norecent.visibility = View.VISIBLE
            text_search_allDelete.visibility = View.GONE
        }else{
            text_search_norecent.visibility = View.GONE
            text_search_allDelete.visibility = View.VISIBLE
        }

    }

}