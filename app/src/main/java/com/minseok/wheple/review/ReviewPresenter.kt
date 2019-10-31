package com.minseok.wheple.review

import android.widget.TextView
import com.minseok.wheple.home.HomePaging
import com.minseok.wheple.retrofit.APIService
import com.minseok.wheple.retrofit.Result
import com.minseok.wheple.review.adapter.ReviewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ReviewPresenter (private val view: ReviewContract.View, private val no:String): ReviewContract.Presenter{

    lateinit var myadapter : ReviewAdapter

    val apiService by lazy {
        APIService.create()
    }

    var disposable: Disposable? = null

    var sf = Review_Sort_Filter()
    val rp = HomePaging()

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }


    override fun basicSort(fresh: TextView, good: TextView, bad: TextView) {
        val num = sf.changedsort(sf.sort)

        if(num == 1){
            view.sortTextChange(fresh)
        }else if(num ==2){
            view.sortTextChange(good)
        }else if(num == 3){
            view.sortTextChange(bad)
        }
    }


    override fun sortSelected(sort:String){
        sf.sort = sort
        view.setSortText(sf.sort)
    }

    override fun photoSwitchChange(photo: Boolean) {
        sf.photo = photo
    }

    override fun getlist(rAdapter: ReviewAdapter){
        view.showNothing(false)
        myadapter = rAdapter

        var sending = "{ \"place\" : \""+ no +"\", \r\n" +
                "\"start\" : \""+rp.start+"\", \r\n" +
                "\"onlyphoto\" : \""+sf.photo+"\", \r\n" +
                "\"sort\" : \""+sf.sort+"\"}"

        disposable =
        apiService.connect_server("reviews.php", sending)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { pr -> showResult(pr)

                }
            )
    }

    fun showResult(pr: Result.Connectresult){
        println(pr.pr)

        if(rp.start==0){
            myadapter.addItems(pr.pr, view.get_base_url())
            myadapter.notifyAdapter()
            view.connectAdapter()

            rp.size = 0 //아이템 사이즈는 0으로 초기화

            if(pr.pr.size>0){ //아이템이 아예 없으면 places.places 가 아예 없다.. 그래서 확인차
                rp.size = pr.pr[0].num.toInt()
            }

            if(rp.size==0){
                view.showNothing(true)
            }
        }else{ // 페이징을 통해서 아이템이 불러와질때
            myadapter.removeLoadingFooter()
            myadapter.newItems(pr.pr)
        }

        rp.isloading = false


    }


    override fun page0(){ //필터를 사용하거나, 정렬을 사용하면 아이템은 다시 0부터 시작된다.
        rp.start =0
    }

    override fun paging(rAdapter: ReviewAdapter) {
        if(!rp.isloading) {
            rp.isloading = true
            rp.start = rp.start + 4 // 4는  한번에 보여주는 개수
            if (rp.size > rp.start) { // size(아이템의 전체 사이즈)가 start(새로 시작하는 아이템의 position)보다 작으면 더이상 페이징 되지 않는다.

                myadapter.addLoadingFooter()

                getlist(myadapter)

            } else {
                println("더이상 가져올게 없다...")
            }
        }
    }
}