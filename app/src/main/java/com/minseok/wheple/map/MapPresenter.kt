package com.minseok.wheple.map

import android.content.Context
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.retrofit.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MapPresenter (private val view : MapContract.View): MapContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }

    val point = Point()

    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun setAddr(addr:String, context: Context){
       val result = point.getPointFromGeoCoder(point, addr, context)

        view.loc_setting(result.x, result.y)
    }

    override fun currentPosition() {
        if(!view.checkPermission()){
            view.showPermissionDialog()
            return
        }
        if(view.currentPosition !=null){
            view.showCurrentP()
            view.moveTocurrentP()
        }else{
            view.showProgress()
            view.startLocationUpdates()
        }

    }

}