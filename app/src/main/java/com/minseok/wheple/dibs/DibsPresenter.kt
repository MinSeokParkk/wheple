package com.minseok.wheple.dibs

import com.minseok.wheple.dibs.adapter.DibsAdpater
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DibsPresenter (private val view : DibsContract.View): DibsContract.Presenter{

    lateinit var myAdapter : DibsAdpater


    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun check_preference(){
        if(App.prefs.autologin!=""){
            view.login_mode()
        }else{
            view.logout_mode()

        }
    }

    override fun getlist(dibsAdpater: DibsAdpater) {
        myAdapter = dibsAdpater

        var sending : String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("get_mydibs.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { di -> showResult(di)

                    }
                )


    }

    fun showResult(di: Result.Connectresult){
        myAdapter.addItems(di.di)
        myAdapter.notifyAdapter()
        view.setNumber(di.di.size)

        view.connectAdapter()
    }


    override fun delete(nos: String) {

        var sending = "{ \"nos\" : \""+ nos + "\", \r\n" +
                             "\"email\" : \""+App.prefs.autologin+"\"}"
        disposable =
            apiService.connect_server("deleteDibs.php", sending)



                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->resultdelete()

                    }
                )
    }
    fun resultdelete(){
            view.setNumber(myAdapter.itemsList.size)
     }


}