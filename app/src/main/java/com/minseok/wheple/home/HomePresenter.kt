package com.minseok.wheple.home


import android.util.Log
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomePresenter  (private val view : HomeContract.View): HomeContract.Presenter{

    lateinit var placeAdapter1 :PlaceAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null


    init {

        this.view.setPresenter(this)

    }

    override fun start() {
    }



    override fun getlist(placeAdapter: PlaceAdapter){
        placeAdapter1 =  placeAdapter
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

    fun showResult(places: Result.Connectresult ){

                placeAdapter1.addItems(places.places)
                placeAdapter1.notifyAdapter()

               view.connectAdapter()
    }

    override fun clear(){ //테스트용
        placeAdapter1.clearItem()
        placeAdapter1.notifyAdapter()
    }



}