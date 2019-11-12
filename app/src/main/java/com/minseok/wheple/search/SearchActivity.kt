package com.minseok.wheple.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minseok.wheple.R
import com.minseok.wheple.afterTextChanged
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchContract.View {

    private lateinit var mPresenter: SearchContract.Presenter
//    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
//    private lateinit var rAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        SearchPresenter(this)

        img_search_back.setOnClickListener {
            onBackPressed()
        }

        edit_search.afterTextChanged {
            mPresenter.sendSearching(edit_search.text.toString())
        }

    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.mPresenter = presenter
    }
}