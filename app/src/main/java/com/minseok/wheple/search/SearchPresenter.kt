package com.minseok.wheple.search

import android.util.Log
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter  (private val view: SearchContract.View): SearchContract.Presenter{

//    lateinit var myadapter : ReviewAdapter

    val apiService by lazy {
        APIService.create()
    }

    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start(){
    }

    override fun sendSearching(input:String){
        if(input!=""){
            var sending = "{ \"input\" : \""+ input +"\"}"

            disposable =
                apiService.connect_server("searching.php", sending)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { srchR -> showResult(srchR)

                        }
                    )
        }

    }

    fun showResult(srchR: Result.Connectresult){
        Log.d("searchT6",srchR.srchR.toString())

    }

}