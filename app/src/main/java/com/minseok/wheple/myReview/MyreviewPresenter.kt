package com.minseok.wheple.myReview

import com.minseok.wheple.myReview.adapter.MyreviewAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyreviewPresenter(private val view : MyreviewContract.View): MyreviewContract.Presenter{

    lateinit var myadapter : MyreviewAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getlist(myreviewAdapter: MyreviewAdapter) {
        myadapter = myreviewAdapter

        var sending = "{ \"email\" : \""+ App.prefs.autologin + "\"}"
        disposable =
            apiService.connect_server("myreview.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { myrev -> showResult(myrev)

                    }
                )

    }

    fun showResult(myrev: Result.Connectresult ){
        println(myrev.myrev)
        if(myrev.myrev.toString().equals("[]")){
            view.showTextNothing()
        }
        myadapter.addItems(myrev.myrev, view.get_base_url())
        myadapter.notifyAdapter()

        view.connectAdapter()

    }

    override fun delete(no: String) {
        println("실험중입니다. : "+no)

        var sending = "{ \"no\" : \""+ no + "\"}"
        disposable =
            apiService.connect_server("deleteReview.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->

                    }
                )
    }


}