package com.minseok.wheple.mypage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MypagePresenter (private val view: MypageContract.View): MypageContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun check_preference() {
        if(App.prefs.autologin!=""){
            view.login_mode()

            var sending = "{ \"email\" : \""+ App.prefs.autologin + "\"}"

            disposable =
                apiService.connect_server("mypage.php", sending)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { mypage -> showResult(mypage) },
                        {e ->
                            println("마이페이지! 오류 테스트 중입니다."+ e.message)
                        }
                    )

        }else{
            view.guest_mode()
        }
    }

    fun showResult(mypage: Result.Connectresult){
        Log.d("MypageFragM","Return to mypage : "+mypage.mypage)
            view.set_myinfo(mypage.mypage.nickname, mypage.mypage.point, mypage.mypage.photo, mypage.mypage.coupon)

    }

    override fun logout() {

        App.prefs.logout()
    }


}