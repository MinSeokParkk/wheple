package com.minseok.wheple.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.minseok.wheple.R


class PlaceItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
    }
}