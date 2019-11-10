package com.minseok.wheple.dibs

class DibsPresenter (private val view : DibsContract.View): DibsContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }
}