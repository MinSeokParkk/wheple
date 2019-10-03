package com.minseok.wheple.place


import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlacePresenter (private val view: PlaceContract.View, private val no:String): PlaceContract.Presenter{

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
}