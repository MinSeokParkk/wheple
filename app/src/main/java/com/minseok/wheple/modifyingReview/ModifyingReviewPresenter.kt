package com.minseok.wheple.modifyingReview

import com.minseok.wheple.modifyingReview.adapter.ModifyingReviewPhotoAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ModifyingReviewPresenter (private val view : ModifyingReviewContract.View) : ModifyingReviewContract.Presenter {
    lateinit var wAdapter : ModifyingReviewPhotoAdapter
    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    override fun start() {
    }

    init {
        this.view.setPresenter(this)
    }

    override fun reviewCheck(rating: Float, review: String) {
        if(rating==0f || review.trim().length<5){
            view.buttonOff()
        }else{
            view.buttonOn()
        }
    }

    override fun getinfo(no: String, modifyingReviewPhotoAdapter: ModifyingReviewPhotoAdapter) {
        var sending = "{ \"no\" : \""+ no + "\"}"
        wAdapter = modifyingReviewPhotoAdapter

        disposable =
            apiService.connect_server("review_modifier.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mod -> showResult(mod) }
                )
    }

    fun showResult(mod: Result.Connectresult ){
        println(mod.mod)
        view.setinfo(mod.mod.placename, mod.mod.usedtime, mod.mod.rating, mod.mod.review)
//
//        var eldlist: ArrayList<String>
//        eldlist = ArrayList()
//        wAdapter.addItems(eldlist)
//        wAdapter.notifyAdapter()
//        view.connectAdapter()
    }
}
