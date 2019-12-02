package com.minseok.wheple.home


import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import com.minseok.wheple.Filter_data
import com.minseok.wheple.Sort_data
import com.minseok.wheple.home.adapter.PlaceAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class HomePresenter  (private val view : HomeContract.View): HomeContract.Presenter{

    lateinit var placeAdapter1 :PlaceAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    val fildataClass = Filter_data()
    val sortClass = Sort_data()
    val pg = HomePaging()


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
                "\"loc\" : \""+loc+"\", \r\n" +
                "\"start\" : \""+pg.start+"\", \r\n" +
                "\"sort\" : \""+sortClass.sort+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("placelist.php", sending)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                    { places -> showResult(places)
                    }
                    ,
                    {e ->
                        println("홈! 오류 테스트 중입니다."+ e.message)
//                        placeAdapter1.removeLoadingFooter()
//                        pg.isloading = false
                        view.showToast("네트워크 연결 오류")
                    }
                )

    }

    fun showResult(places: Result.Connectresult ){
            Log.d("homeplaces",places.places.toString())


            if (pg.start == 0) { //아이템이 새로 불러와질때

                placeAdapter1.addItems(places.places)
                placeAdapter1.notifyAdapter()

                view.connectAdapter()
                pg.size = 0 //아이템 사이즈는 0으로 초기화
                if (places.places.size > 0) { //아이템이 아예 없으면 places.places 가 아예 없다.. 그래서 확인차
                    pg.size = places.places[0].num.toInt()
                }
                view.setPlaceNumber(pg.size)

                view.showNothing(pg.size)
            } else { // 페이징을 통해서 아이템이 불러와질때
                placeAdapter1.removeLoadingFooter()

                placeAdapter1.newItems(places.places)

            }

            pg.isloading = false

    }

    override fun paging(placeAdapter: PlaceAdapter){
        if(!pg.isloading) {
            pg.isloading = true
            pg.start = pg.start + 2 // 2는  한번에 보여주는 개수
            if (pg.size > pg.start) { // size(아이템의 전체 사이즈)가 start(새로 시작하는 아이템의 position)보다 작으면 더이상 페이징 되지 않는다.

                placeAdapter.addLoadingFooter()

                getlist(placeAdapter)

            } else {
                println("더이상 가져올게 없다...")
            }
        }

    }

    override fun page0(){ //필터를 사용하거나, 정렬을 사용하면 아이템은 다시 0부터 시작된다.
        pg.start =0
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

    override fun checkChange(){  // 필터 액티비티에서 필터 변화가 있고 홈플래그먼트로 돌아올 때 변화감지
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



    override fun basicSort(rating: TextView, review: TextView, cheap: TextView, expensive: TextView){
        val num = sortClass.changedsort(sortClass.sort)
        if(num == 1){
            view.sortTextChange(rating)
        }else if(num ==2){
            view.sortTextChange(review)
        }else if(num == 3){
            view.sortTextChange(cheap)
        }else if(num == 4){
            view.sortTextChange(expensive)
        }
    }

    override fun sortSelected(sort:String){
        sortClass.sort = sort

       setSort()
    }

    override fun setSort(){
        view.setSortText(sortClass.sort)
    }


}