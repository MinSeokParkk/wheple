package com.minseok.wheple.img_slider

class ImageSliderPresenter(private val view : ImageSliderContract.View): ImageSliderContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    override fun start() {
    }

    override fun setting(images: Array<String>) {
        val imageList = ArrayList<String>()
        imageList.addAll(images)

        view.setAdapter(imageList)
    }
}