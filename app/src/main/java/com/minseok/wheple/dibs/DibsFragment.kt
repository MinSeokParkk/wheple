package com.minseok.wheple.dibs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R

class DibsFragment : androidx.fragment.app.Fragment(), DibsContract.View {

    private lateinit var mPresenter: DibsContract.Presenter

    override fun setPresenter(presenter: DibsContract.Presenter) {
        this.mPresenter=presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

       DibsPresenter(this)




        return view

    }

    companion object {
        fun newInstance(): DibsFragment = DibsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }
}