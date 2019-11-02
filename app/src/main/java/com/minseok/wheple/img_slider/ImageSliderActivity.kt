package com.minseok.wheple.img_slider

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.minseok.wheple.R
import com.minseok.wheple.img_slider.adapter.ImageSliderAdapter
import kotlinx.android.synthetic.main.activity_imageslider.*


class ImageSliderActivity: AppCompatActivity(), ImageSliderContract.View {
    private lateinit var mPresenter:  ImageSliderContract.Presenter

    override fun setPresenter(presenter:  ImageSliderContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageslider)
        mPresenter = ImageSliderPresenter(this)

        img_imgslider_back.setOnClickListener {
            onBackPressed()
        }

        println("엑스트라 ~~"+intent.getStringArrayExtra("images"))
        println("포지션 ~~"+intent.getStringExtra("position"))


        mPresenter.setting(intent.getStringArrayExtra("images"))



    }

    override fun setAdapter(imageList: ArrayList<String>){
        val viewPagerAdapter = ImageSliderAdapter(imageList)
        viewPager_imgslider.adapter = viewPagerAdapter

        viewPager_imgslider.setCurrentItem(intent.getStringExtra("position").toInt())

        makingDots(viewPagerAdapter, intent.getStringExtra("position").toInt())
    }

    fun makingDots(viewPagerAdapter : ImageSliderAdapter, start:Int) {

        val dotscount = viewPagerAdapter.count
        val dots = arrayOfNulls<ImageView>(dotscount)

        for (i in 0..dotscount - 1) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.non_active_dot
                )
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(3, 0, 3, 0)

            SliderDots_imgslider.addView(dots[i], params)
        }

        dots[start]!!.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.active_dot
            )
        )


        viewPager_imgslider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                println("바뀌나요? + " + dotscount.toString())
                for (i in 0..dotscount - 1) {

                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            getApplicationContext(),
                            R.drawable.non_active_dot
                        )
                    )
                }
                dots[position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active_dot
                    )
                )

            }

        })

    }

}

