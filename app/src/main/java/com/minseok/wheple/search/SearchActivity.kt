package com.minseok.wheple.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import com.minseok.wheple.search.adapter.SearchResultAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchContract.View {

    private lateinit var mPresenter: SearchContract.Presenter
    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var sAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        SearchPresenter(this)

        edit_search.requestFocus()

        img_search_back.setOnClickListener {
            onBackPressed()
        }

        edit_search.afterTextChanged {
            destroyRecycler()
            makeRecycler()
        }

        text_search_delete.setOnClickListener {
            edit_search.text.clear()
            deleteOff()
        }

    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun connectAdapter(){
        recycler_search_result.adapter = sAdapter
    }

    fun makeRecycler(){
        recycler_search_result.layoutManager = linearLayoutManager
        sAdapter = SearchResultAdapter()

        mPresenter.sendSearching(edit_search.text.toString(), sAdapter)
    }

    fun destroyRecycler(){
        recycler_search_result.layoutManager = null
    }

    override fun deleteOn(){
        text_search_delete.visibility = View.VISIBLE
    }

    override fun deleteOff(){
        text_search_delete.visibility = View.GONE
    }
}