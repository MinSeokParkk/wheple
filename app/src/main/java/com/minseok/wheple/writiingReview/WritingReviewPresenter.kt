package com.minseok.wheple.writiingReview

import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.writiingReview.adapter.WritingReviewPhotoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WritingReviewPresenter (private val view : WritingReviewContract.View): WritingReviewContract.Presenter{

    lateinit var wAdapter : WritingReviewPhotoAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    override fun start() {
    }

    init {
        this.view.setPresenter(this)
    }

    override fun getinfo(no: String, writingReviewPhotoAdapter: WritingReviewPhotoAdapter) {
        var sending = "{ \"no\" : \""+ no + "\"}"
        wAdapter = writingReviewPhotoAdapter

        disposable =
            apiService.connect_server("review_writer.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { reW -> showResult(reW) }
                )
    }

    fun showResult(reW: Result.Connectresult ){
        println(reW.reW)
        view.setinfo(reW.reW.name, reW.reW.datetime)

        var eldlist: ArrayList<String>
        eldlist = ArrayList()
        wAdapter.addItems(eldlist)
        wAdapter.notifyAdapter()
        view.connectAdapter()
    }

    override fun reviewCheck(rating: Float, review: String) {
        if(rating==0f || review.trim().length<5){
            view.buttonOff()
        }else{
            view.buttonOn()
        }
    }

    override fun sendReview(no: String, rating: Float, review: String) {
        if(rating==0f){
            view.showToast("별점을 선택해주세요.")

        }else if(review.trim().length<5){
            view.showToast("리뷰는 5자 이상 작성해주세요.")

        }else{
            println("통과$$$$$$$$$$$$$$$$$$$$$")

        }
    }

    override fun ChooseGalleryClick() {
        if(wAdapter.wr_itemsList.size<3){
            if (!view.checkPermission()) {
                view.showPermissionDialog()
                return
            }


            view.chooseGallery()
        }else{
            view.showToast("사진을 모두 선택했습니다.")
        }
    }

    override fun showPreview(mFilePath: String) {
        view.displayImagePreview(mFilePath)
    }

    override fun addphoto(mFilePath: String){
        var oldlist: ArrayList<String>
           oldlist = wAdapter.wr_itemsList
            oldlist.add(mFilePath)
            wAdapter.addItems(oldlist)


        wAdapter.notifyAdapter()

    }


}