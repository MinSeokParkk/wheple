package com.minseok.wheple.review.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minseok.wheple.PlaceReviewItem
import com.minseok.wheple.R
import com.minseok.wheple.R.id.text_pr_username

class ReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var itemsList: ArrayList<PlaceReviewItem>
    var baseurl = ""

    private val ITEM = 0
    private val LOADING = 1

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==ITEM){
            val rootView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_place_review, parent, false)
            return ReviewViewHolder(rootView)
        }else{
            val rootiew = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            return LoadingViewHolder(rootiew)
        }
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)==ITEM){
            var rholder : ReviewViewHolder = holder as ReviewViewHolder

            itemsList[position].let{
                rholder?.bind(it)
            }
            if(position==0){
                rholder.rView.visibility = View.GONE
            }

        }else if (getItemViewType(position)==LOADING){
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

    fun newItems(placeItems: ArrayList<PlaceReviewItem>) {
        this.itemsList.addAll(placeItems)
        notifyItemInserted(itemsList.size-1)
    }

    fun addLoadingFooter(){
        isLoadingAdded = true
        add(PlaceReviewItem("","","","","","","","", ""))
    }

    fun add(pt:PlaceReviewItem){
        itemsList.add(pt)
        notifyItemInserted(itemsList.size - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = itemsList.size -1
        val item = getItem(position)

        if(item != null){
            itemsList.remove(item)
            notifyItemRemoved(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == itemsList.size-1 && isLoadingAdded) {
            println("프로그래스바 생깁니다~~~~~~~")
            return  LOADING

        } else return ITEM
    }

    fun getItem(position: Int) : PlaceReviewItem{
        return itemsList.get(position)
    }


    inner class ReviewViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var rUserphoto = itemView.findViewById<ImageView>(R.id.img_pr_userphoto)
        var rUsername = itemView.findViewById<TextView>(text_pr_username)
        var rRating = itemView.findViewById<RatingBar>(R.id.rating_pr)
        var rReview = itemView.findViewById<TextView>(R.id.text_pr_review)
        var rPhoto1 = itemView.findViewById<ImageView>(R.id.img_pr_photo1)
        var rPhoto2 = itemView.findViewById<ImageView>(R.id.img_pr_photo2)
        var rPhoto3 = itemView.findViewById<ImageView>(R.id.img_pr_photo3)
        var rDatetime = itemView.findViewById<TextView>(R.id.text_pr_datetime)
        var rConst = itemView.findViewById<ConstraintLayout>(R.id.const_pr)
        var rView = itemView.findViewById<View>(R.id.view_pr)

        fun bind(prItem:PlaceReviewItem){
            rConst.setBackgroundResource(R.color.white)


            Glide.with(itemView)
                .load(baseurl+prItem.userphoto)
                .into(rUserphoto)

            rUsername.text = prItem.username
            rRating.rating = prItem.rating.toFloat()
            rReview.text = prItem.review.replace("|||", "\n")
            rDatetime.text = prItem.datetime.replace("|", " ")


            if(prItem.photo1!=""){
                rPhoto1.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(baseurl+prItem.photo1)
                    .into(rPhoto1)
            } else{
                rPhoto1.visibility = View.GONE
                rPhoto2.visibility = View.GONE
                rPhoto3.visibility = View.GONE
            }
            if(prItem.photo2!=""){
                rPhoto2.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(baseurl+prItem.photo2)
                    .into(rPhoto2)
            } else{
                rPhoto2.visibility = View.GONE
                rPhoto3.visibility = View.GONE
            }

            if(prItem.photo3!=""){
                rPhoto3.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(baseurl+prItem.photo3)
                    .into(rPhoto3)
            } else {
                rPhoto3.visibility = View.GONE
            }
        }
    }

    inner class LoadingViewHolder (itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    }

}