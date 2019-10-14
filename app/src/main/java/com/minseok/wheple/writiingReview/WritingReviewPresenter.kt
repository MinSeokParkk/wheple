package com.minseok.wheple.writiingReview

import android.net.Uri
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.shared.App
import com.minseok.wheple.reviewEditorPhotoadapter.ReviewEditorPhotoAdapter
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

class WritingReviewPresenter (private val view : WritingReviewContract.View): WritingReviewContract.Presenter{

    lateinit var wAdapter : ReviewEditorPhotoAdapter

    val apiService by lazy {
        APIService.create()
    }
    var disposable: Disposable? = null

    override fun start() {
    }

    init {
        this.view.setPresenter(this)
    }

    override fun getinfo(no: String, writingReviewPhotoAdapter: ReviewEditorPhotoAdapter) {
        var sending = "{ \"no\" : \""+ no + "\"}"
        wAdapter = writingReviewPhotoAdapter

        disposable =
            apiService.connect_server("review_writer.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { reW -> showResult(reW) }
                )
    }

    fun showResult(reW: Result.Connectresult ){
        println(reW.reW)
        view.setinfo(reW.reW.name, reW.reW.datetime)

        var eldlist: ArrayList<String>
        eldlist = ArrayList()
        wAdapter.addItems(eldlist)
        wAdapter.notifyAdapter()
        view.connectAdapter()
    }

    override fun reviewCheck(rating: Float, review: String) {
        if(rating==0f || review.trim().length<5){
            view.buttonOff()
        }else{
            view.buttonOn()
        }
    }

    override fun sendReview(resevationNo: String, rating: Float, review: String, wr_itemsList:ArrayList<String>,datetime:String) {
        if(rating==0f){
            view.showToast("별점을 선택해주세요.")

        }else if(review.trim().length<5){
            view.showToast("리뷰는 5자 이상 작성해주세요.")
            view.shortedittext()
        }else{

            var images = ArrayList<MultipartBody.Part>()

            for (index in 0..wr_itemsList.size - 1) {
                val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())

                val file = File(view.getPath(Uri.parse(wr_itemsList[index])))
                val rnds = Random().nextDouble().toString().replace(".","")

                var fileName = App.prefs.autologin.replace("@", "").replace(".", "")+
                               "-"+rnds +"-" +timeStamp
                fileName = fileName + ".png"
                var requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                images.add(MultipartBody.Part.createFormData("uploaded_file[]", fileName, requestBody))
            }

            val time = SimpleDateFormat("yyyy-MM-dd|HH:mm:ss").format(Date())

            var sending : String
            var reviewC = review.replace("\n","|||")
            sending = "{ \"writer\" : \""+ App.prefs.autologin + "\", \r\n" +
                    "\"time\" : \""+ time + "\", \r\n" +
                    "\"rating\" : \""+ rating + "\", \r\n" +
                    "\"review\" : \""+ reviewC + "\", \r\n" +
                    "\"datetime\" : \""+ datetime + "\", \r\n" +
                    "\"no\" : \""+ resevationNo +"\"}"
            println("sending은 ? : " +sending)

            disposable =
                apiService.post_Porfile_Request("review_upload.php", sending, images)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult1(result) }
                    )

        }
    }

    fun  showResult1(result: Result.Connectresult){

        view.showToast("리뷰가 등록되었습니다.")
        view.reviewsuccess()
        println("통신내용 : " +result.toString())
    }

    override fun ChooseGalleryClick() {
        if(wAdapter.wr_itemsList.size<3){
            if (!view.checkPermission()) {
                view.showPermissionDialog()
                return
            }


            view.chooseGallery()
        }else{
            view.showToast("사진을 모두 선택했습니다.")
        }
    }

    override fun showPreview(mFilePath: String) {
        var oldlist: ArrayList<String>
        oldlist = wAdapter.wr_itemsList
        oldlist.add(mFilePath)
        wAdapter.addItems(oldlist)

        wAdapter.notifyAdapter()
    }






}