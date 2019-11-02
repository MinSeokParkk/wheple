package com.minseok.wheple.img_slider.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.minseok.wheple.R

class ImageSliderAdapter (var imageList: ArrayList<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return  imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val layoutInflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.item_imageslider, null)
        val photoView = view.findViewById<PhotoView>(R.id.img_photoview)!!

        Glide.with(container.context)
            .load(imageList[position])
            .thumbnail(0.2f)
            .into(photoView)
        photoView.maximumScale = 5.0f
        photoView.mediumScale = 3.0f
        photoView.minimumScale = 1.0f



        val vto = photoView.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                photoView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                photoView.setScale(2f)
            }
        })


        val vp = container  as ViewPager

        vp.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}