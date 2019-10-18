package com.minseok.wheple.myinfoName

import com.minseok.wheple.myinfo.MyinfoActivity
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyinfoNamePresenter (private val view : MyinfoNameContract.View): MyinfoNameContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    var regex = MyinfoNameRegex()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun changeName(name: String, oldName: String) {
        if(name.trim().length>0){
            view.deleteOn()
            if(name.contains(" ")){
                view.setName(name.replace(" ",""))
            }
            val nameresult = regex.checkName(name.trim())
            if(nameresult!=null){
                view.alarmOn(nameresult)
            }else{
                if(name.trim()!=oldName){
                    view.buttonOn()
                }else{
                    view.alarmOn("현재 이름과 동일합니다.")
                }
            }

        }else{
            view.deleteOff()
            if(name.contains(" ")){
                view.setName(name.replace(" ",""))
            }
            view.alarmOn("이름을 입력해주세요.")
        }

    }

    override fun saveName(name: String) {
        var sending:String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\", \r\n" +
                "\"name\" : \""+name+"\"}"
        println("sending ===   " + sending)

        disposable =
            apiService.connect_server("myinfo_save_name.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult() }
                )

    }

    fun showResult(){
        view.showToast("이름이 변경되었습니다.")

        MyinfoActivity.MyClass.change = true
        view.back()
    }
}