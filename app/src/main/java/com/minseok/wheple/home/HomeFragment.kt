package com.minseok.wheple


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.minseok.wheple.home.HomeContract
import com.minseok.wheple.home.HomePresenter
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.home.PlaceItem
import com.minseok.wheple.retrofit.Result
import kotlinx.android.synthetic.main.fragment_home.*



class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var mPresenter : HomeContract.Presenter

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.mPresenter = presenter
    }

    private lateinit var placeItemList : ArrayList<PlaceItem>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var placeAdapter: PlaceAdapter




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        HomePresenter(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mPresenter.getlist()
    }

    override  fun makeadapter(places: Result.Connectresult){
        placeItemList = places.places
        recycler_home.layoutManager = linearLayoutManager
        placeAdapter = PlaceAdapter(placeItemList)
        recycler_home.adapter = placeAdapter
    }



    fun showToast(string: String) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show()
    }



    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
}