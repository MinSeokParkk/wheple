package com.minseok.wheple.home

import android.support.v7.widget.LinearLayoutManager
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

class HomePresenter  (private val view : HomeContract.View): HomeContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }



    override fun getlist(){
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
//        println("해보자 "+places.places)
       view.makeadapter(places)
    }

}