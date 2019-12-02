package com.minseok.wheple.near


import android.util.Log
import android.widget.CheckBox
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.minseok.wheple.meterDistanceBetweenPoints
import com.minseok.wheple.near.model.NearFilterData
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NearPresenter (private val view : NearContract.View): NearContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var nfd = NearFilterData()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun getPlace(lat_ne:Float, lng_ne:Float, lat_sw:Float, lng_sw:Float){
        val sending = "{ \"lat_ne\" : \""+ lat_ne + "\", \r\n" +
                             "\"lng_ne\" : \""+ lng_ne + "\", \r\n" +
                             "\"lat_sw\" : \""+ lat_sw + "\", \r\n" +
                             "\"lng_sw\" : \""+lng_sw+"\", \r\n" +
                             "\"soccer\" : \""+ nfd.soccerN + "\", \r\n" +
                             "\"futsal\" : \""+nfd.futsalN+"\", \r\n" +
                             "\"baseball\" : \""+nfd.baseballN+"\", \r\n" +
                             "\"basketball\" : \""+nfd.basketballN+"\", \r\n" +
                             "\"badminton\" : \""+nfd.badmintonN+"\", \r\n" +
                             "\"tennis\" : \""+nfd.tennisN+"\", \r\n" +
                             "\"pingpong\" : \""+nfd.pingpongN+"\", \r\n" +
                             "\"volleyball\" : \""+nfd.volleyballN+"\", \r\n" +
                             "\"parking\" : \""+nfd.parkingN+"\", \r\n" +
                             "\"shower\" : \""+nfd.showerN+"\", \r\n" +
                             "\"cooling\" : \""+nfd.coolingN+"\", \r\n" +
                             "\"heating\" : \""+nfd.heatingN+"\"}"
                      // 필터 추가 해야함!!!!!!!

        disposable =
            apiService.connect_server("near_getplace.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { ni -> showResult(ni) },
                    {e ->
                        println("내주변! 오류 테스트 중입니다."+ e.message)
                    }
                )
    }

    fun showResult(ni: Result.Connectresult) {
        Log.d("near12", ni.ni.toString())
        val size = ni.ni.size

        for(i in 0..size-1)
        view.loc_setting(ni.ni[i].lat, ni.ni[i].lng, ni.ni[i].no)

        var placenum = 0
        if(size>0){
            placenum = ni.ni[0].num.toInt()
        }

        view.set_placeNumber(placenum)
    }

    override fun getDetail(no:String, marker: Marker, currentposition:LatLng){
        val sending = "{ \"no\" : \""+no+"\"}"

        Log.d("near12",sending)

        disposable =
            apiService.connect_server("near_detail.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { ndi -> showResult_D(ndi, marker, currentposition) }
                )
    }

    fun showResult_D(ndi: Result.Connectresult, marker: Marker, currentposition:LatLng){
        Log.d("near12", ndi.ndi.toString())
       val distance = meterDistanceBetweenPoints(marker.position.latitude.toFloat(), marker.position.longitude.toFloat(),
                                   currentposition.latitude.toFloat(), currentposition.longitude.toFloat())

        Log.d("near12", "거리는 : "+distance)
        view.setDetail(ndi.ndi.photo, ndi.ndi.name, ndi.ndi.rating, ndi.ndi.review, ndi.ndi.price, marker, distance)

    }


    override fun filter_change(soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                               badminton:Boolean, tennis:Boolean, pingpong:Boolean, volleyball:Boolean,
                               parking:Boolean, shower:Boolean, cooling:Boolean, heating:Boolean){
        nfd.soccerN = soccer
        nfd.futsalN = futsal
        nfd.baseballN = baseball
        nfd.basketballN = basketball
        nfd.badmintonN = badminton
        nfd.tennisN = tennis
        nfd.pingpongN= pingpong
        nfd.volleyballN = volleyball

        nfd.parkingN = parking
        nfd.showerN = shower
        nfd.coolingN = cooling
        nfd.heatingN = heating
    }

    override fun setfilter(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox, c5: CheckBox, c6: CheckBox,
                               c7: CheckBox, c8: CheckBox, c9: CheckBox, c10: CheckBox, c11: CheckBox, c12: CheckBox){
        view.set_checkbox(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12,
                          nfd.soccerN, nfd.futsalN, nfd.baseballN, nfd.basketballN, nfd.badmintonN,
                         nfd.tennisN, nfd.pingpongN, nfd.volleyballN,  nfd.parkingN, nfd.showerN ,
                         nfd.coolingN,  nfd.heatingN  )
    }

    override fun change_filter(){
        view.change_filterlayout(nfd.checkChange())
    }

    override fun check_viewhasPer() {
        if(!view.checkPermission()){
           view.showNoPermissionView()
            return
        }

        view.showPermissionView()

    }



}