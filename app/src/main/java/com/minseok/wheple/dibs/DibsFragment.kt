package com.minseok.wheple.dibs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minseok.wheple.R
import com.minseok.wheple.dibs.adapter.DibsAdpater
import com.minseok.wheple.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_dibs.*
import kotlinx.android.synthetic.main.fragment_dibs.view.*

class DibsFragment : androidx.fragment.app.Fragment(), DibsContract.View {

    class MyClass{
        companion object{
           var dibs_change :Boolean = false
        }

    }

    private lateinit var mPresenter: DibsContract.Presenter

    private val gridLayoutManager by lazy {
        androidx.recyclerview.widget.GridLayoutManager(
            context,2
        )
    }
    private lateinit var dibsAdapter: DibsAdpater


    override fun setPresenter(presenter: DibsContract.Presenter) {
        this.mPresenter=presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_dibs, container, false)

       DibsPresenter(this)


        view.button_dibs_login.setOnClickListener {
            activity?.let {
                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }

        return view

    }

    companion object {
        fun newInstance(): DibsFragment = DibsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mPresenter.check_preference()
        makeRecycler()

            text_dibs_cancelReady.setOnClickListener {
                text_dibs_cancelReady.visibility = View.GONE
                text_dibs_cancel.visibility = View.VISIBLE
                dibsAdapter.readyForDibsCancel()
            }

            text_dibs_cancel.setOnClickListener {
                text_dibs_cancelReady.visibility = View.VISIBLE
                text_dibs_cancel.visibility = View.GONE

                dibsAdapter.deleteDibs()

            }

    }

//      계속 안터지면 지우자....
//    override fun onStart() {
////        mPresenter.check_preference()
////        text_dibs_cancelReady.visibility = View.VISIBLE
////        text_dibs_cancel.visibility = View.GONE
//        super.onStart()
//        Log.d("dibsFrag","onStart")
//    }

    override fun onResume() {
        if(MyClass.dibs_change){ //찜하기가 바뀌었으면 다시 찜 목록을 서버에서 다시 불러온다.
            mPresenter.check_preference()
            destroyRecycler()
            makeRecycler()
            MyClass.dibs_change = false
        }
        super.onResume()
        Log.d("dibsFrag","onresume")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden){
            Log.d("dibsFrag","hidden")

            if(dibsAdapter!=null){

                dibsAdapter.rechangeDibs()
                if(dibsAdapter.itemInitial){
                    if(dibsAdapter.itemsList.size>0){
                        text_dibs_cancelReady.visibility = View.VISIBLE
                        text_dibs_cancel.visibility = View.GONE
                    }

                }
            }

        }
    }

    override fun makeRecycler() {
        recycler_dibs.layoutManager = gridLayoutManager
        dibsAdapter = DibsAdpater(mPresenter)
        mPresenter.getlist(dibsAdapter)

    }

    override fun connectAdapter(){
        recycler_dibs.adapter = dibsAdapter
    }

    override fun destroyRecycler(){
        recycler_dibs.layoutManager = null
    }






    override fun login_mode() {

        const_dibs_login.visibility = View.VISIBLE
        const_dibs_logout.visibility = View.GONE
    }

    override fun logout_mode() {
        const_dibs_login.visibility = View.GONE
        const_dibs_logout.visibility = View.VISIBLE
    }

    override fun setNumber(num:Int){
        text_dibs_num.text = num.toString()
        if(num==0){
            img_dibs_noplace.visibility = View.VISIBLE
            text_dibs_noplace1.visibility = View.VISIBLE
            text_dibs_noplace2.visibility = View.VISIBLE
            text_dibs_cancelReady.visibility = View.GONE
        }else{
            img_dibs_noplace.visibility = View.GONE
            text_dibs_noplace1.visibility = View.GONE
            text_dibs_noplace2.visibility = View.GONE
            text_dibs_cancelReady.visibility = View.VISIBLE
        }
    }


}