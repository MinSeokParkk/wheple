package com.minseok.wheple.review


import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.minseok.wheple.R
import com.minseok.wheple.review.adapter.ReviewAdapter
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.dialog_review_sort.*


class ReviewActivity : AppCompatActivity(), ReviewContract.View{

    private lateinit var mPresenter: ReviewContract.Presenter
    private val linearLayoutManager by lazy { androidx.recyclerview.widget.LinearLayoutManager(this) }
    private lateinit var rAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        ReviewPresenter(this, intent.getStringExtra("no"))
        setting(intent.getStringExtra("rating"), intent.getStringExtra("review"))

        makeRecycler()

        img_rv_back.setOnClickListener {
            onBackPressed()
        }

        //정렬 다이얼로그
        layout_rv_sort.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_review_sort, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            mAlertDialog.window.setLayout(520, 510)

            //기존 선택되어있는 정렬 text 색 다르게 나오게 세팅
            mPresenter.basicSort(mAlertDialog.text_rw_fresh, mAlertDialog.text_rw_good,
                mAlertDialog.text_rw_bad)

            mAlertDialog.const_rw_fresh.setOnClickListener {
                mPresenter.sortSelected("최근 작성순")

                destroyRecycler()
                makeRecycler()

                mAlertDialog.dismiss()
            }

            mAlertDialog.const_rw_good.setOnClickListener {
                mPresenter.sortSelected("별점 높은순")

                destroyRecycler()
                makeRecycler()

                mAlertDialog.dismiss()
            }

            mAlertDialog.const_rw_bad.setOnClickListener {
                mPresenter.sortSelected("별점 낮은순")

                destroyRecycler()
                makeRecycler()

                mAlertDialog.dismiss()
            }

            mAlertDialog.const_rw_cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

        //사진리뷰만 보기 체크 리스너
        switch_rv_onlyphoto.setOnCheckedChangeListener { buttonView, isChecked ->
            mPresenter.photoSwitchChange(switch_rv_onlyphoto.isChecked) //스위치의 체크를 반영함
            destroyRecycler() //리싸이클러뷰를 없애고
            makeRecycler() //다시 만듬.
        }

        //리싸이클러뷰 아래로 드래그 감지
        recycler_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recycler_rv.canScrollVertically(1)) {
                    // 최하단
                    println("아래로 드래그!!!!!!!!!!!!!!!!!!!!!")
                    mPresenter.paging(rAdapter)
                }
            }

        })

    }


    override fun setPresenter(presenter: ReviewContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun sortTextChange(textview: TextView){
        textview.setTextColor(resources.getColor(R.color.colorPrimary))
        textview.setTypeface(null, Typeface.BOLD)
    }

    override fun setSortText(text:String){
        text_rv_sort.text = text
    }

   fun setting(rating:String, review:String){
        ratingbar_rv.rating = rating.toFloat()
        text_rv_rating.text = rating
        text_rv_top.text = "전체리뷰 ("+review+"개)"
    }

    override fun get_base_url() :String{
        return getString(R.string.base_url)
    }

    override fun connectAdapter(){
        recycler_rv.adapter = rAdapter
    }

    fun makeRecycler(){
        recycler_rv.layoutManager = linearLayoutManager
        rAdapter = ReviewAdapter()

        mPresenter.getlist(rAdapter)
    }

    fun destroyRecycler() {
        mPresenter.page0()
        recycler_rv.layoutManager = null
    }

    override fun showNothing(nophoto:Boolean){
        if(nophoto){
            img_rv_nophoto.visibility = View.VISIBLE
            text_rv_nophoto.visibility = View.VISIBLE
        }else{
            img_rv_nophoto.visibility = View.GONE
            text_rv_nophoto.visibility = View.GONE
        }
    }

}