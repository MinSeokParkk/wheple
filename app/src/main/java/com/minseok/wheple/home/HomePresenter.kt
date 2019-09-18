package com.minseok.wheple.home

import android.content.Context

class HomePresenter(private val view : HomeContract.View): HomeContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    lateinit override var homeData: HomeData

    lateinit override var adapterModel: HomeAdapterContract.Model
    lateinit override var adapterView: HomeAdapterContract.View


    override fun start() {
    }

    override fun loadItems(context: Context, isClear: Boolean) {
        if(isClear){
            adapterModel.clearItem()
        }

        var list = ArrayList<HomeItem>()
        val homeItem = HomeItem("a","b","c","d","e")
        list.add(homeItem)


        adapterModel.addItems(list)
        adapterView.notifyAdapter()
    }
}