package com.minseok.wheple.myReservation

import com.minseok.wheple.myReservation.adapter.MyreservationAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyreservationPresenter (private val view : MyreservationContract.View): MyreservationContract.Presenter{

    lateinit var myadapter :MyreservationAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getlist(myreservationAdapter: MyreservationAdapter) {
        myadapter = myreservationAdapter

        var sending = "{ \"email\" : \""+ App.prefs.autologin + "\"}"
        disposable =
            apiService.connect_server("myreservation.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { myres -> showResult(myres)

                    }
                )

    }

    fun showResult(myres: Result.Connectresult ){
println(myres.myres)
        if(myres.myres.toString().equals("[]")){
            view.showTextNothing()
        }
        myadapter.addItems(myres.myres)
        myadapter.notifyAdapter()

        view.connectAdapter()
    }

}