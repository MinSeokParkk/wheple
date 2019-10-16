package com.minseok.wheple.myinfo

import com.minseok.wheple.PhoneChange
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyinfoPresenter (private val view : MyinfoContract.View): MyinfoContract.Presenter{

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun bringData() {
        var sending = "{ \"email\" : \""+ App.prefs.autologin + "\"}"

        disposable =
            apiService.connect_server("bring_myinfo.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { myinfo -> showResult(myinfo) }
                )
    }

    fun showResult(myinfo: Result.Connectresult) {
        println("return ======" + myinfo.myinfo)

        if(myinfo.myinfo.username==null){
            myinfo.myinfo.username="미등록"
        }

        var password=""
        for(i in 1..myinfo.myinfo.passwordL){
            password = password+"●"
        }

        val phone =  PhoneChange().check(myinfo.myinfo.phone)

        view.setmyinfo(myinfo.myinfo.photo, myinfo.myinfo.nickname, App.prefs.autologin, password, myinfo.myinfo.username,
            phone)

    }



    override fun ChooseCameraClick() {
        if(!view.checkPermission(1)){
            view.showPermissionDialog(1)
            return
        }
        if (view.availableDisk() <= 5) {
           view.showToast("저장 공간이 부족합니다.")
            return
        }
        val file = view.newFile()

        if(file==null){
            view.showToast("에러가 발생했습니다.")
            return
        }
        println("file은!!!  : " +file.toString())
        view.startCamera(file)

    }

    override fun ChooseGalleryClick() {
        if (!view.checkPermission(2)) {
            view.showPermissionDialog(2)
            return
        }

        view.chooseGallery()
    }

    override fun savephoto(photo: String) {

        var images = ArrayList<MultipartBody.Part>()

           val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val file = File(photo)
        val rnds = Random().nextDouble().toString().replace(".","")
        var fileName = App.prefs.autologin.replace("@", "").replace(".", "")+
                "-"+rnds +"-" +timeStamp
        fileName = fileName + ".png"
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        images.add(MultipartBody.Part.createFormData("uploaded_file[]", fileName, requestBody))

        var sending : String
        sending = "{ \"email\" : \""+ App.prefs.autologin  +"\"}"
        println("sending은 ? : " +sending)

        disposable =
            apiService.post_Porfile_Request("myinfo_save_myphoto.php", sending, images)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> showResult1(result, photo) }
                )

    }

    fun  showResult1(result: Result.Connectresult, photo:String){

        view.displayImagePreview(photo)
        println("통신내용 : " +result.toString())
    }



}