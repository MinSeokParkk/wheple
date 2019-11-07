package com.minseok.wheple.near

import android.util.Log
import com.google.android.gms.maps.model.Marker
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NearPresenter (private val view : NearContract.View): NearContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getPlace(lat_ne:Float, lng_ne:Float, lat_sw:Float, lng_sw:Float){
        val sending = "{ \"lat_ne\" : \""+ lat_ne + "\", \r\n" +
                             "\"lng_ne\" : \""+ lng_ne + "\", \r\n" +
                             "\"lat_sw\" : \""+ lat_sw + "\", \r\n" +
                             "\"lng_sw\" : \""+lng_sw+"\"}"

        disposable =
            apiService.connect_server("near_getplace.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { ni -> showResult(ni) }
                )
    }

    fun showResult(ni: Result.Connectresult) {
        Log.d("near12", ni.ni.toString())
        val size = ni.ni.size
        for(i in 0..size-1)
        view.loc_setting(ni.ni[i].lat, ni.ni[i].lng, ni.ni[i].no)
    }

    override fun getDetail(no:String, marker: Marker){
        val sending = "{ \"no\" : \""+no+"\"}"

        Log.d("near12",sending)

        disposable =
            apiService.connect_server("near_detail.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { ndi -> showResult_D(ndi, marker) }
                )
    }

    fun showResult_D(ndi: Result.Connectresult, marker: Marker){
        Log.d("near12", ndi.ndi.toString())
        view.setDetail(ndi.ndi.photo, ndi.ndi.name, ndi.ndi.rating, ndi.ndi.review, ndi.ndi.price, marker)

    }

}