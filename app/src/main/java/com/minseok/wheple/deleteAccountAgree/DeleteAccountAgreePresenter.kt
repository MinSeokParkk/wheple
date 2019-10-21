package com.minseok.wheple.deleteAccountAgree

import android.widget.TextView
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DeleteAccountAgreePresenter(private val view :  DeleteAccountAgreeContract.View):  DeleteAccountAgreeContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }

    val changeC = ChangeTextRed()

    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }
    override fun start() {
    }

    override fun textRed(text:String, word1:String,word2:String, textView: TextView){
        view.setText(textView, changeC.changeText(text, word1, word2))
    }

    override fun getpoint() {
        var sending: String
        sending = "{ \"email\" : \""+ App.prefs.autologin +"\"}"
        println("sending ===   " + sending)
        disposable =
            apiService.connect_server("get_mypoint.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult_point(result) }
                )
    }

    fun showResult_point(result: Result.Connectresult){
        println("return ======"+result.result )
       view.setpoint(result.result+" P")
    }

    override fun checkchange(check: Boolean) {
        view.buttonChange(check)
    }

    override fun delete(check: Boolean) {
        if(!check){
            view.showToast("주의사항 동의는 필수입니다.")
        }else{
            var sending: String
            sending = "{ \"email\" : \""+ App.prefs.autologin +"\"}"
            println("sending ===   " + sending)
            disposable =
                apiService.connect_server("delete_account.php", sending)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult_delete() }
                    )
        }
    }

    fun showResult_delete(){
        view.showToast("회원탈퇴가 완료되었습니다.")
        App.prefs.logout()
        view.deleteSuccess()
    }

}