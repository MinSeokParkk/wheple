package com.minseok.wheple.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R

class SearchFragment : Fragment(), SearchContract.View {

    private lateinit var mPresenter: SearchContract.Presenter

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.mPresenter=presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

       SearchPresenter(this)
        return view

    }

    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
    }
}