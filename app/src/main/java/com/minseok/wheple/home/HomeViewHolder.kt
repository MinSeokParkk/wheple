package com.minseok.wheple.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_test2.view.*
import kotlinx.android.synthetic.main.item_home.view.*

class HomeViewHolder(context: Context, parent: ViewGroup?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)){

//     val imageView_home_photo by lazy{
//         itemView.findViewById(R.id.img_home_photo) as ImageView
//     }

     val textView_home_name by lazy{
        itemView.findViewById(R.id.text_home_name) as TextView
     }

    val textView_home_address by lazy{
        itemView.findViewById(R.id.text_home_address) as TextView
    }
    val textView_home_price by lazy{
        itemView.findViewById(R.id.text_home_price) as TextView
    }
    val textView_home_rating by lazy{
        itemView.findViewById(R.id.text_home_rating) as TextView
    }
    val textView_home_review by lazy{
        itemView.findViewById(R.id.text_home_review) as TextView
    }






    fun onBind(item: HomeItem, position: Int){

        //이미지도 처리해야함

        textView_home_name.text = item.name
        textView_home_address.text = item.address
        textView_home_price.text = item.price
        textView_home_rating.text = item.rating
        textView_home_review.text = item.review

//        itemView.setOnClickListener {
//            onItemClickListener?.onItemClick(position)
//        }
    }

}