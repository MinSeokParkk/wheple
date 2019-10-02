package com.minseok.wheple.myResDetail

import com.minseok.wheple.retrofit.APIService
import io.reactivex.disposables.Disposable

class MyResDetailPresenter (private val view : MyResDetailContract.View): MyResDetailContract.Presenter {


    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

}
