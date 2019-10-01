package com.minseok.wheple.home.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.place.PlaceActivity
import com.minseok.wheple.home.PlaceItem


class PlaceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var placeName = itemView.findViewById<TextView>(R.id.text_home_name)
    var placeAddress = itemView.findViewById<TextView>(R.id.text_home_address)
    var placePrice = itemView.findViewById<TextView>(R.id.text_home_price)
    var placeRating = itemView.findViewById<TextView>(R.id.text_home_rating)
    var placeReview = itemView.findViewById<TextView>(R.id.text_home_review)
    var placePhoto = itemView.findViewById<ImageView>(R.id.img_home_photo)

    fun bind(placeItemList: PlaceItem){
        placeName.text = placeItemList.name
        placeAddress.text = placeItemList.address
        placePrice.text = placeItemList.price
        placeRating.text = placeItemList.rating
        placeReview.text = placeItemList.review

        Glide.with(itemView)
            .load(placeItemList.photo)
            .into(placePhoto)

        itemView.setOnClickListener{
//            Toast.makeText(itemView.context, position.toString(), Toast.LENGTH_SHORT).show()


            val nextIntent = Intent(itemView.context, PlaceActivity::class.java)
           nextIntent.putExtra("no", placeItemList.no)
            itemView.context.startActivity(nextIntent)
        }

    }
}