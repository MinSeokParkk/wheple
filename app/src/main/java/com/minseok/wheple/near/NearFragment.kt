package com.minseok.wheple.near

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_chatlist.*
import kotlinx.android.synthetic.main.fragment_chatlist.view.*

class NearFragment : androidx.fragment.app.Fragment(), NearContract.View {

   private lateinit var mPresenter : NearContract.Presenter

    override fun setPresenter(presenter: NearContract.Presenter) {
        this.mPresenter = presenter
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_near, container, false)






        NearPresenter(this)

        // Return the fragment view/layout
        return view

    }



    companion object {
        fun newInstance(): NearFragment = NearFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }
}