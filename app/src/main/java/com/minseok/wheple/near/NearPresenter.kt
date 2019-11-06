package com.minseok.wheple.near

import com.minseok.wheple.shared.App

class NearPresenter (private val view : NearContract.View): NearContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }


}