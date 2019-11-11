package com.minseok.wheple.place


import android.util.Log
import android.view.View
import com.minseok.wheple.dibs.DibsFragment
import com.minseok.wheple.login.LoginActivity
import com.minseok.wheple.place.adapter.PlaceReviewAdapter
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
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

        var email = ""
      if(App.prefs.autologin!=""){
          email = App.prefs.autologin
      }

        var sending = "{ \"no\" : \""+ no + "\", \r\n" +
                    "\"email\" : \""+ email +"\"}"
        Log.d("placeA", "sending :   " + sending.toString())
        disposable =
            apiService.connect_server("place.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { place -> showResult(place) }
                )
    }

    fun showResult(place: Result.Connectresult){
        Log.d("placeA", "showresult :   " + place.place.toString())

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

        val photoarray = here.photo.split("|").toTypedArray()

        view.setPlace(here.name, here.address, here.price, here.rating, here.review, photoarray,
                      parking, shower, heating, sports, here.introduction, here.guide, here.dibs)



        phone = here.phone

    }
    override fun calling(){
        view.ask_phone(phone!!)
    }

    override fun messaging() {
        view.ask_message(phone!!)
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

    override fun review_more(rating: String, review: String){
        val reviewN = review.replace("(","").replace(")","")

        view.gotoReview(no, rating, reviewN)
    }

    override fun show_map() {
        view.gotoMap(no)
    }

    override fun checkDibs(mydibs: Boolean) {
        if(App.prefs.autologin!=""){

            if(!PlaceActivity.MyClass.login_back) {
                if (!mydibs) {
                    view.showToast("찜 목록에 추가되었습니다.")
                } else {
                    view.showToast("찜하기가 해제되었습니다.")
                }
            }

            var sending = "{ \"mydibs\" : \""+ mydibs + "\", \r\n" +
                    "\"no\" : \""+ no + "\", \r\n" +
                    "\"email\" : \""+ App.prefs.autologin +"\"}"
            Log.d("placeA", "checkDibs sending :   " + sending.toString())
            disposable =
                apiService.connect_server("dibs_onoff.php", sending)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult_Dibs(result) }
                    )



        }else{
            // 나중에 다이얼로그로 바꿀 예정.
           view.gotoLogin(no)
        }
    }

    fun showResult_Dibs(result: Result.Connectresult){
            Log.d("placeA", "딥 결과는 : " + result.result)
        if(result.result=="already"){
            view.showToast("로그인 성공!\n이미 찜한 장소입니다.")
            view.changeHeart(true)
        }else {
            if(PlaceActivity.MyClass.login_back){
                view.showToast("로그인 성공!\n찜 목록에 추가되었습니다.")
            }
            val result_di = result.result.toBoolean()
            view.changeHeart(result_di)
        }
        PlaceActivity.MyClass.login_back = false // 로그인 했다 돌아왔다는 표시 없애줌.

        DibsFragment.MyClass.dibs_change = true
    }

    override fun checkDibs_before() {
        var sending = "{ \"no\" : \""+ no + "\", \r\n" +
                "\"email\" : \""+ App.prefs.autologin +"\"}"

        Log.d("placeA", "checkDibs_before sending :   " + sending)
        disposable =
            apiService.connect_server("mydibscheck.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult_Dibs_before(result) }
                )
    }

    fun showResult_Dibs_before(result: Result.Connectresult){
        Log.d("placeA", "저기 결과는 :   " + result.result)
        val result_di = result.result.toBoolean()
        view.changeHeart(result_di)

        PlaceActivity.MyClass.res_login_back = false // 날짜시간선택 액티비티에서 로그인 했다 돌아왔다는 표시 없애줌.
    }
}