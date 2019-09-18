package com.minseok.wheple.search

class SearchPresenter (private val view : SearchContract.View): SearchContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }
}