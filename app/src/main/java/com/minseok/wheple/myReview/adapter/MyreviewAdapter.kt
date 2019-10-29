package com.minseok.wheple.myReview.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.minseok.wheple.R
import com.minseok.wheple.modifyingReview.ModifyingReviewActivity
import com.minseok.wheple.myReview.MyreviewContract
import com.minseok.wheple.myReview.MyreviewItem
import kotlinx.android.synthetic.main.dialog_my_review_more.view.*

class MyreviewAdapter (private var mpresenter: MyreviewContract.Presenter)
   : androidx.recyclerview.widget.RecyclerView.Adapter<MyreviewAdapter.MyreviewViewHolder>() {

    lateinit var itemsList: ArrayList<MyreviewItem>
    var baseurl = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyreviewViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_my_review, parent, false)
        return MyreviewViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MyreviewViewHolder, position: Int) {
        itemsList[position].let {
            holder?.bind(it)
        }

        holder.morebutton.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_my_review_more, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(mDialogView)

            val  mAlertDialog = mBuilder.show()
            mAlertDialog.window.setLayout(620, 375)

            mDialogView.constraint_myr_more_modify.setOnClickListener{
                mAlertDialog.dismiss()
//                Toast.makeText(holder.itemView.context, itemsList[position].no, Toast.LENGTH_SHORT).show()

                val nextIntent = Intent(holder.itemView.context, ModifyingReviewActivity::class.java)
                nextIntent.putExtra("no", itemsList[position].no)
                holder.itemView.context.startActivity(nextIntent)

            }
            mDialogView.constraint_myr_more_delete.setOnClickListener{
                mAlertDialog.dismiss()
                deletedialog(holder.itemView.context, position)
            }
            mDialogView.textview_myr_more_close.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }
    }

        override fun getItemCount(): Int {
            return itemsList.size
        }

        fun notifyAdapter() {
            notifyDataSetChanged()
        }

        fun addItems(myreviewItems: ArrayList<MyreviewItem>,base_url:String) {
            this.itemsList = myreviewItems
            this.baseurl = base_url
        }

        fun clearItem() {
            itemsList.clear()
        }

    fun deletedialog(context: Context, position: Int){
        val builder = AlertDialog.Builder(context)

        builder.setMessage("삭제한 리뷰는 복구할 수 없습니다.\n\n정말로 삭제하시겠습니까?" +
                "\n")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(context, "리뷰가 삭제되었습니다.", Toast.LENGTH_SHORT).show()

            mpresenter.delete(itemsList[position].no)

            itemsList.removeAt(position)
            notifyItemRemoved(position)
        }

        builder.setNegativeButton(android.R.string.no){dialog, which ->

        }


        builder.show()
    }

   inner class MyreviewViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

       var myrevPlacephoto = itemView.findViewById<ImageView>(R.id.img_myr_place_photo)
       var myrevPlacename = itemView.findViewById<TextView>(R.id.text_myr_place_name)
       var myrevRating = itemView.findViewById<RatingBar>(R.id.rating_myr_place)
       var myrevReview = itemView.findViewById<TextView>(R.id.text_myr_review)
       var myrevPhoto1 = itemView.findViewById<ImageView>(R.id.img_myr_photo1)
       var myrevPhoto2 = itemView.findViewById<ImageView>(R.id.img_myr_photo2)
       var myrevPhoto3 = itemView.findViewById<ImageView>(R.id.img_myr_photo3)
       var myrevDatetime = itemView.findViewById<TextView>(R.id.text_myr_datetime)
       var morebutton = itemView.findViewById<TextView>(R.id.text_myr_more)

       fun bind(myrevItem:MyreviewItem){
           Glide.with(itemView)
               .load(myrevItem.placephoto)
               .into(myrevPlacephoto)

           myrevPlacename.text = myrevItem.placename
           myrevRating.rating = myrevItem.rating.toFloat()
           myrevReview.text = myrevItem.review.replace("|||", "\n")
           myrevDatetime.text = myrevItem.datetime.replace("|", " ")

           if(myrevItem.photo1!=""){
               myrevPhoto1.visibility = View.VISIBLE
               Glide.with(itemView)
                   .load(baseurl+myrevItem.photo1)
                   .into(myrevPhoto1)
           } else{
               myrevPhoto1.visibility = View.GONE
               myrevPhoto2.visibility = View.GONE
               myrevPhoto3.visibility = View.GONE
           }
           if(myrevItem.photo2!=""){
               myrevPhoto2.visibility = View.VISIBLE
               Glide.with(itemView)
                   .load(baseurl+myrevItem.photo2)
                   .into(myrevPhoto2)
           } else{
               myrevPhoto2.visibility = View.GONE
               myrevPhoto3.visibility = View.GONE
           }

           if(myrevItem.photo3!=""){
               myrevPhoto3.visibility = View.VISIBLE
               Glide.with(itemView)
                   .load(baseurl+myrevItem.photo3)
                   .into(myrevPhoto3)
           } else {
               myrevPhoto3.visibility = View.GONE
           }



       }

   }
}