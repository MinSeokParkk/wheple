package com.minseok.wheple.place.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.img_slider.ImageSliderActivity

class PlacePhotoViewPagerAdapter(photos: Array<String>) : PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    private var images = photos

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, object1: Any): Boolean {
        return view == object1
    }


    override fun instantiateItem(container: ViewGroup, position:Int) : Any {
        layoutInflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.item_placephoto, null)
        val imageView = view.findViewById<ImageView>(R.id.placephoto_imgView)
        Glide.with(container.context)
            .load(images[position])
            .into(imageView)

        imageView.setOnClickListener {
            println("포지션은~~~~~ " + position)

            val nextIntent = Intent(container.context, ImageSliderActivity::class.java)
            nextIntent.putExtra("images", images)
            nextIntent.putExtra("position", position.toString())
            nextIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            container.context.startActivity(nextIntent)
        }

        val vp = container as ViewPager
        vp.addView(view, 0)




        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}