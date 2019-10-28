package com.minseok.wheple.home


import android.widget.CheckBox
import com.minseok.wheple.Filter_data
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomePresenter  (private val view : HomeContract.View): HomeContract.Presenter{

    lateinit var placeAdapter1 :PlaceAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    val fildataClass = Filter_data()


    init {

        this.view.setPresenter(this)

    }

    override fun start() {
    }



    override fun getlist(placeAdapter: PlaceAdapter){
        placeAdapter1 =  placeAdapter

        val loc = fildataClass.make_loc(Filter_data.loc1, Filter_data.loc2)
        var sending : String
        sending = "{ \"soccer\" : \""+ Filter_data.soccer + "\", \r\n" +
                "\"futsal\" : \""+Filter_data.futsal+"\", \r\n" +
                "\"baseball\" : \""+Filter_data.baseball+"\", \r\n" +
                "\"basketball\" : \""+Filter_data.basketball+"\", \r\n" +
                "\"badminton\" : \""+Filter_data.badminton+"\", \r\n" +
                "\"tennis\" : \""+Filter_data.tennis+"\", \r\n" +
                "\"pingpong\" : \""+Filter_data.pingpong+"\", \r\n" +
                "\"volleyball\" : \""+Filter_data.volleyball+"\", \r\n" +
                "\"parking\" : \""+Filter_data.parking+"\", \r\n" +
                "\"shower\" : \""+Filter_data.shower+"\", \r\n" +
                "\"cooling\" : \""+Filter_data.cooling+"\", \r\n" +
                "\"heating\" : \""+Filter_data.heating+"\", \r\n" +
                "\"loc\" : \""+loc+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("placelist.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { places -> showResult(places)

                    }
                )

    }

    fun showResult(places: Result.Connectresult ){

                placeAdapter1.addItems(places.places)
                placeAdapter1.notifyAdapter()

               view.connectAdapter()
               view.setPlaceNumber(placeAdapter1.itemsList.size.toString())
                view.showNothing(placeAdapter1.itemsList.size)


    }

    override fun clear(){ //테스트용
        placeAdapter1.clearItem()
        placeAdapter1.notifyAdapter()
    }

    override fun setfilter_sports(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox, c5:CheckBox, c6:CheckBox, c7:CheckBox, c8:CheckBox){
        view.set_sports(c1, c2, c3, c4, c5, c6, c7, c8,
            Filter_data.soccer, Filter_data.futsal, Filter_data.baseball, Filter_data.basketball, Filter_data.badminton, Filter_data.tennis, Filter_data.pingpong, Filter_data.volleyball)
    }

    override fun filter_sports(soccer:Boolean, futsal:Boolean, baseball:Boolean, basketball:Boolean,
                                badminton:Boolean, tennis:Boolean, pingpong:Boolean, volley:Boolean){
        Filter_data.soccer = soccer
        Filter_data.futsal = futsal
        Filter_data.baseball = baseball
        Filter_data.basketball = basketball
        Filter_data.badminton = badminton
        Filter_data.tennis = tennis
        Filter_data.pingpong = pingpong
        Filter_data.volleyball = volley

    }

    override fun setfilter_facility(c1: CheckBox, c2: CheckBox, c3: CheckBox, c4: CheckBox){
        view.set_facility(c1, c2, c3, c4,
            Filter_data.parking, Filter_data.shower, Filter_data.cooling, Filter_data.heating)
    }

    override fun filter_facility(parking: Boolean, shower: Boolean, cooling: Boolean, heating: Boolean) {
        Filter_data.parking = parking
        Filter_data.shower = shower
        Filter_data.cooling = cooling
        Filter_data.heating = heating
    }

    override fun get_loc1():String{
        return Filter_data.loc1
    }

    override fun get_loc2():String{
        return Filter_data.loc2
    }

    override fun save_loc(loc1: String, loc2: String) {
        Filter_data.loc1 = loc1
        Filter_data.loc2 = loc2
    }

    override fun checkChange(){
        if(Filter_data.filter_change){
            view.destroyRecycler()
            view.makeRecycler()
            Filter_data.filter_change = false
        }
    }

    override fun set_sportsBack(){
        view.filter_sportsBack(fildataClass.checkChange_sports())
    }

    override fun set_facilityBack(){
        view.filter_facilityBack(fildataClass.checkChange_facility())
    }

    override fun set_locationBack() {
        view.filter_locationBack(fildataClass.checkChange_location(Filter_data.loc1))
    }

    override fun set_homefilterBack() {
        val change = fildataClass.checkChange_sports() || fildataClass.checkChange_facility() ||
                fildataClass.checkChange_location(Filter_data.loc1)
        view.homefilter_Back(change)
    }




}