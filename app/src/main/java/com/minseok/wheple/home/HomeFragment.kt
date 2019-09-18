package com.minseok.wheple.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var mPresenter: HomeContract.Presenter

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.mPresenter = presenter
    }

    private lateinit var homeAdapter: HomeAdapter

//    private val recyclerView by lazy {
//        findViewById(R.id.recycler_home) as RecyclerView
//    }
private val linearLayoutManager by lazy { LinearLayoutManager(context) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        recycler_home?.layoutManager = linearLayoutManager

        homeAdapter = HomeAdapter(activity!!.applicationContext)
        recycler_home?.adapter = homeAdapter

//        view.constraint1.setOnClickListener {
//            activity?.let{
//                val intent = Intent (it, SpaceActivity::class.java)
//                it.startActivity(intent)
//            }
//        }


        HomePresenter(this)

        // Return the fragment view/layout
        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter.loadItems(activity!!.applicationContext, false)
    }



    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}