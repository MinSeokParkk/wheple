package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.minseok.wheple.home.PlaceAdapter

import com.minseok.wheple.home.PlaceItem
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    private lateinit var placeItemList : ArrayList<PlaceItem>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var sending : String
        sending = ""

        disposable =
            apiService.connect_server("placelist.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { places -> showResult(places)

                    }
                )

    }



    fun showResult(places:Result.Connectresult ){
        println("해보자 "+places.places)

        placeItemList = places.places
        recycler_home.layoutManager = linearLayoutManager
        placeAdapter = PlaceAdapter(placeItemList)
        recycler_home.adapter = placeAdapter

    }



    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}