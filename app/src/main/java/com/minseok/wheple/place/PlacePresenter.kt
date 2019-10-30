package com.minseok.wheple.place


import android.view.View
import com.minseok.wheple.place.adapter.PlaceReviewAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlacePresenter (private val view: PlaceContract.View, private val no:String): PlaceContract.Presenter{

    lateinit var myadapter : PlaceReviewAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null
    var phone:String ?= null

    init {
         this.view.setPresenter(this)
           }
    override fun start() {
    }

    override fun showDetail() {
        var sending = "{ \"no\" : \""+ no + "\"}"

        disposable =
            apiService.connect_server("place.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { place -> showResult(place) }
                )
    }

    fun showResult(place: Result.Connectresult){
        println("return ======"+place.place )

        var here : PlaceDetailItem
        here = place.place

        var parking : String = ""
        var shower : String = ""
        var heating : String = ""

        if(here.parking==0){
            parking = "불가"
            view.noParking()
        }else{
            parking = here.parking.toString() + "석"
        }

        if(here.shower==0){
            shower = "불가"
            view.noShower()
        }else{
            shower = here.shower.toString() + "석"
        }

        if(here.heating==0){
            heating = "냉/난방 불가"
            view.noHeating()
        }else if(here.heating==1){
            heating = "냉방만 가능"
        } else if(here.heating==2){
            heating = "난방만 가능"
        } else if(here.heating==3){
            heating = "냉/난방 가능"
        }

        val strs = here.sports.split("|").toTypedArray()

        var sports = ""
       for(i in strs.indices){
           sports = sports +"# "+strs[i]+"   "
       }


        if(here.review=="0"){  //리뷰가 없으면 리뷰 뷰들 안보이게함.
            view.no_review()
            here.review="첫번째 리뷰를 남겨주세요."
        }else{
            here.review = "("+here.review+")"
        }

        view.setPlace(here.name, here.address, here.price, here.rating, here.review, here.photo,
                      parking, shower, heating, sports, here.introduction, here.guide)



        phone = here.phone

    }
    override fun calling(){
        //나중에 수정 예정
        view.showToast(phone!!)
    }

    override fun sendPlaceNo() {
        view.movetoSelect(no)
    }

    override fun topplacename(scroll:Int){
        if(scroll>60){ //스크롤뷰의 y축 높이가 60보다 크면 상단 장소이름 textview 보이게, 그 이하면 안보이게
            view.toptextVisibility(View.VISIBLE)
        }else{
            view.toptextVisibility(View.GONE)
        }
    }

    override fun getReview(prAdapter: PlaceReviewAdapter, placeNo: String) {
        myadapter = prAdapter
        var sending = "{ \"place\" : \""+ placeNo + "\"}"

        disposable =
            apiService.connect_server("place_reviews.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { pr -> showResult_review(pr)

                    }
                )
    }

    fun showResult_review(pr: Result.Connectresult){
        println(pr.pr)

        myadapter.addItems(pr.pr, view.get_base_url())
        myadapter.notifyAdapter()

        view.connectAdapter()

    }

    override fun review_more(){
        view.gotoReview(no)
    }
}