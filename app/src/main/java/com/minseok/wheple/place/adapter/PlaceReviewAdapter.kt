package com.minseok.wheple.place.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minseok.wheple.PlaceReviewItem
import com.minseok.wheple.R


class PlaceReviewAdapter : RecyclerView.Adapter<PlaceReviewAdapter.PlaceReviewViewHolder>(){
    lateinit var itemsList:ArrayList<PlaceReviewItem>
    var baseurl = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceReviewViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_place_review, parent, false)
        return PlaceReviewViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: PlaceReviewViewHolder, position: Int) {
        itemsList[position].let{
            holder?.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun addItems(placereviewItems: ArrayList<PlaceReviewItem>,base_url:String) {
        this.itemsList = placereviewItems
        this.baseurl = base_url
    }



    inner class PlaceReviewViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var prUserphoto = itemView.findViewById<ImageView>(R.id.img_pr_userphoto)
        var prUsername = itemView.findViewById<TextView>(R.id.text_pr_username)
        var prRating = itemView.findViewById<RatingBar>(R.id.rating_pr)
        var prReview = itemView.findViewById<TextView>(R.id.text_pr_review)
        var prPhoto1 = itemView.findViewById<ImageView>(R.id.img_pr_photo1)
        var prPhoto2 = itemView.findViewById<ImageView>(R.id.img_pr_photo2)
        var prPhoto3 = itemView.findViewById<ImageView>(R.id.img_pr_photo3)
        var prDatetime = itemView.findViewById<TextView>(R.id.text_pr_datetime)

        fun bind(prItem:PlaceReviewItem){
            Glide.with(itemView)
                .load(baseurl+prItem.userphoto)
                .into(prUserphoto)

            prUsername.text = prItem.username
            prRating.rating = prItem.rating.toFloat()
            prReview.text = prItem.review.replace("|||", "\n")
            prDatetime.text = prItem.datetime.replace("|", " ")


            if(prItem.photo1!=""){
                prPhoto1.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(baseurl+prItem.photo1)
                    .into(prPhoto1)
            } else{
                prPhoto1.visibility = View.GONE
                prPhoto2.visibility = View.GONE
                prPhoto3.visibility = View.GONE
            }
            if(prItem.photo2!=""){
                prPhoto2.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(baseurl+prItem.photo2)
                    .into(prPhoto2)
            } else{
                prPhoto2.visibility = View.GONE
                prPhoto3.visibility = View.GONE
            }

            if(prItem.photo3!=""){
                prPhoto3.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(baseurl+prItem.photo3)
                    .into(prPhoto3)
            } else {
                prPhoto3.visibility = View.GONE
            }
        }
    }
}