package com.minseok.wheple.review

import com.minseok.wheple.retrofit.APIService
import io.reactivex.disposables.Disposable

class ReviewPresenter (private val view: ReviewContract.View, private val no:String): ReviewContract.Presenter{

//    lateinit var myadapter : ReviewAdapter

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