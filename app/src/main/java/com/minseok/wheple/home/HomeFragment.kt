package com.minseok.wheple


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.home.HomeContract
import com.minseok.wheple.home.HomePresenter
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.home.PlaceItem
import kotlinx.android.synthetic.main.fragment_home.*



class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var mPresenter : HomeContract.Presenter

    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        HomePresenter(this)



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        makeRecycler()


    }


    override  fun connectAdapter(){

        recycler_home.adapter = placeAdapter

    }

    override fun setPresenter(presenter: HomeContract.Presenter) {

        this.mPresenter = presenter

    }

    override fun makeRecycler() {
        recycler_home.layoutManager = linearLayoutManager

        placeAdapter = PlaceAdapter()

        mPresenter.getlist(placeAdapter)
    }

    override fun destroyRecycler() {
        recycler_home.layoutManager = null
    }


    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
}