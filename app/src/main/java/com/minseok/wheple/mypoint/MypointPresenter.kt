package com.minseok.wheple.mypoint

import android.util.Log
import com.minseok.wheple.mypoint.adapter.MypointAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MypointPresenter (private val view :  MypointContract.View): MypointContract.Presenter{

    lateinit var myAdapter: MypointAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getpoints(mAdapter: MypointAdapter) {
        myAdapter = mAdapter
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\"}"
        disposable =
            apiService.connect_server("getmypoint.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { myp -> showResult(myp) }
                )
    }

    fun showResult(myp: Result.Connectresult){
        Log.d("mypointB1",myp.myp.toString())
        myAdapter.addItems(myp.myp)
        myAdapter.notifyAdapter()
        view.connectAdapter()

    }
}