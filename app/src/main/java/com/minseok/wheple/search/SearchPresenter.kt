package com.minseok.wheple.search

import android.util.Log
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.search.adapter.SearchResultAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter  (private val view: SearchContract.View): SearchContract.Presenter{

    lateinit var myadapter : SearchResultAdapter

    val apiService by lazy {
        APIService.create()
    }

    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start(){
    }

    override fun sendSearching(input:String, sAdapter: SearchResultAdapter){

        myadapter = sAdapter

        if(input.trim().length>0){

            view.deleteOn()

            var sending = "{ \"input\" : \""+ input +"\"}"

            disposable =
                apiService.connect_server("searching.php", sending)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { srchR -> showResult(srchR, input)

                        }
                    )
        }else{
            view.deleteOff()

            myadapter.addItems(ArrayList<SearchItem>(), input)
            myadapter.notifyAdapter()
            view.connectAdapter()
        }
    }

    fun showResult(srchR: Result.Connectresult, input: String){
        Log.d("searchT6",srchR.srchR.toString())

        myadapter.addItems(srchR.srchR, input)
        myadapter.notifyAdapter()
        view.connectAdapter()

    }

}