package com.minseok.wheple.myinfoNickname

import com.minseok.wheple.myinfo.MyinfoActivity
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyinfoNicknamePresenter(private val view : MyinfoNicknameContract.View): MyinfoNicknameContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var regex = MyinfoNicknameRegex()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun changeNickname(nickname: String, oldNickname:String) {
        if(nickname.trim().length>0){
            view.deleteOn()


            if(nickname.contains(" ")){
                view.setNickname(nickname.replace(" ",""))
            }
            val nickresult = regex.checkNickname(nickname.trim())
            if(nickresult!=null){
                  view.alarmOn(nickresult)
            }else{
                if(nickname.trim()!=oldNickname){
                    view.buttonOn()
                }else{
                    view.alarmOn("현재 닉네임과 동일합니다.")
                }

            }


        }else{
            view.deleteOff()
            if(nickname.contains(" ")){
                view.setNickname(nickname.replace(" ",""))
            }
            view.alarmOn("닉네임을 입력해주세요.")
        }
    }

    override fun saveNickname(nickname: String) {
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"nickname\" : \""+nickname+"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("myinfo_save_nickname.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult(result) }
                )
    }

    fun showResult(result: Result.Connectresult){
        println("return ======"+result.result )
        if(result.result.equals("1")){
            view.alarmOn("이미 사용하고 있는 닉네임입니다.")
            view.buttonOff()
            view.wrongInput()

        } else {
            view.showToast("닉네임이 변경되었습니다.")

            MyinfoActivity.MyClass.change = true
            view.back()

        }
    }
}