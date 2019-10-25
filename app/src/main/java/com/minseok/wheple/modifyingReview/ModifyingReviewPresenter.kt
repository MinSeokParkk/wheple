package com.minseok.wheple.modifyingReview


import android.net.Uri
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.reviewEditorPhotoadapter.ReviewEditorPhotoAdapter
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

class ModifyingReviewPresenter (private val view : ModifyingReviewContract.View) : ModifyingReviewContract.Presenter {
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

    override fun reviewCheck(rating: Float, review: String) {
        if(rating==0f || review.trim().length<5){
            view.buttonOff()
        }else{
            view.buttonOn()
        }
    }

    override fun getinfo(no: String, modifyingReviewPhotoAdapter: ReviewEditorPhotoAdapter,baseurl:String) {
        var sending = "{ \"no\" : \""+ no + "\"}"
        wAdapter = modifyingReviewPhotoAdapter

        disposable =
            apiService.connect_server("review_modifier.php", sending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mod -> showResult(mod, baseurl) }
                )
    }

    fun showResult(mod: Result.Connectresult, baseurl: String ){
        println(mod.mod)
        view.setinfo(mod.mod.placename, mod.mod.usedtime, mod.mod.rating, mod.mod.review)

        val list = SettingOldphotos().setting(mod.mod.photo1, mod.mod.photo2, mod.mod.photo3, baseurl)
        wAdapter.addItems(list)
        wAdapter.notifyAdapter()
        view.connectAdapter()
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

    override fun sendReview(no: String, rating: Float, review: String, wr_itemsList: ArrayList<String>) {
        if(rating==0f){
            view.showToast("별점을 선택해주세요.")

        }else if(review.trim().length<5){
            view.showToast("리뷰는 5자 이상 작성해주세요.")
            view.shortedittext()

        }else{
            var images = ArrayList<MultipartBody.Part>()
            var photo = arrayOf("", "", "")


            for (index in 0..wr_itemsList.size - 1) {
                if(wr_itemsList[index].startsWith("http://")){
                    println("새로운게 없어여ㅛ`~~~~")
                    val strs = wr_itemsList[index].split("/").toTypedArray()

                    photo[index] = strs[3]+"/"+strs[4]+"/"+strs[5]

                }else{
                    println("새로운게 있어여ㅛ`~~~~")
                    val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                    val file = File(view.getPath(Uri.parse(wr_itemsList[index])))
                    val rnds = Random().nextDouble().toString().replace(".","")

                    var fileName = App.prefs.autologin.replace("@", "").replace(".", "")+
                            "-"+rnds +"-" +timeStamp
                    fileName = fileName + ".png"
                    var requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    images.add(MultipartBody.Part.createFormData("uploaded_file[]", fileName, requestBody))
                }
            }

            var sending : String
            var reviewC = review.replace("\n","|||")
            sending = "{ \"rating\" : \""+ rating + "\", \r\n" +
                    "\"review\" : \""+ reviewC + "\", \r\n" +
                    "\"photo1\" : \""+ photo[0] + "\", \r\n" +
                    "\"photo2\" : \""+ photo[1] + "\", \r\n" +
                    "\"photo3\" : \""+ photo[2] + "\", \r\n" +
                    "\"no\" : \""+ no +"\"}"
            println("sending은 ? : " +sending)

            disposable =
                apiService.post_Porfile_Request("review_modify.php", sending, images)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result -> showResult1(result) }
                    )

        }
    }

    fun  showResult1(result: Result.Connectresult){

        view.showToast("리뷰가 수정되었습니다.")
        println("통신내용 : " +result.toString())
        view.finishmodifying()
    }


}
