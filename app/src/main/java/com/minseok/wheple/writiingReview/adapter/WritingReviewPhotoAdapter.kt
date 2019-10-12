package com.minseok.wheple.writiingReview.adapter

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.minseok.wheple.R

class WritingReviewPhotoAdapter : RecyclerView.Adapter<WritingReviewPhotoAdapter.WritingReviewPhotoViewHolder>(){


 lateinit var wr_itemsList:ArrayList<String>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WritingReviewPhotoViewHolder {
        val rootview =  LayoutInflater.from(parent.context).inflate(R.layout.item_review_writer_photo, parent, false)
        return WritingReviewPhotoViewHolder(rootview)

    }

    override fun onBindViewHolder(holder: WritingReviewPhotoViewHolder, position: Int) {
        wr_itemsList[position].let{
          holder?.bind(it)
      }

       holder.deleteButton.setOnClickListener {


           holder.deleteButton.visibility = View.GONE
           println("포지션은 ???? : " + position.toString())

           wr_itemsList.removeAt(position)
           notifyItemRemoved(position)

           addItems(wr_itemsList)
           notifyAdapter()


           Handler().postDelayed({
               holder.deleteButton.visibility = View.VISIBLE
         }, 200)
       }
    }

    override fun getItemCount(): Int {
        return wr_itemsList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun addItems(photoitems:ArrayList<String>){
        this.wr_itemsList = photoitems
    }


    fun clearItem() {
        wr_itemsList.clear()
    }

    fun change(){
        addItems(wr_itemsList)
        notifyAdapter()
    }



    fun swapItems(fromPosition:Int, toPosition:Int){
        println("frompositon : "+fromPosition.toString()+"     toposition :"+toPosition.toString())
        if(fromPosition>toPosition){
            println("from이 커요!!!!!!!!!!!!!!!!!!!")

            for (i in fromPosition..toPosition + 1) {
                wr_itemsList.set(i, wr_itemsList.set(i-1, wr_itemsList.get(i)))
            }
        }else{
            println("to가 커요!!!!!!!!!!!!!!!!!!!")

            for (i in fromPosition..toPosition - 1) {
                wr_itemsList.set(i, wr_itemsList.set(i+1, wr_itemsList.get(i)))
            }
        }

        notifyItemMoved(fromPosition, toPosition)


    }


    inner class WritingReviewPhotoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var reviewImage = itemView.findViewById<ImageView>(R.id.img_rew)
        var deleteButton = itemView.findViewById<ImageView>(R.id.img_rev_item_clear)

        fun bind(mFilePath : String){
            Glide.with(itemView.context)
                .load(mFilePath)
                .apply(RequestOptions().centerCrop())
                .into(reviewImage)
        }


    }
}

