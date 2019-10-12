package com.minseok.wheple.myReservation.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.minseok.wheple.CheckReservationTime
import com.minseok.wheple.R
import com.minseok.wheple.myResDetail.MyResDetailActivity
import com.minseok.wheple.myReservation.MyreservationContract
import com.minseok.wheple.myReservation.MyreservationItem
import com.minseok.wheple.myReservation.MyreservationPresenter
import com.minseok.wheple.writiingReview.WritingReviewActivity

class MyreservationAdapter(private var mpresenter: MyreservationContract.Presenter) : RecyclerView.Adapter<MyreservationAdapter.MyreservationViewHolder>() {


    private lateinit var itemsList:ArrayList<MyreservationItem>

    var comparetime = CheckReservationTime()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyreservationViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_my_reservation, parent, false)
        return MyreservationViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MyreservationViewHolder, position: Int) {
        itemsList[position].let{
            holder?.bind(it)
        }

        holder.deletetext.setOnClickListener {

            val builder = AlertDialog.Builder(holder.itemView.context)

            builder.setMessage("삭제한 예약내역은 복구할 수 없습니다.\n\n정말로 삭제하시겠습니까?" +
                    "\n")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->


                mpresenter.delete(itemsList[position].no)

                itemsList.removeAt(position)
                notifyItemRemoved(position)
            }

            builder.setNegativeButton(android.R.string.no){dialog, which ->

            }


            builder.show()

        }


    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun addItems(myreservationItems: ArrayList<MyreservationItem>) {
        this.itemsList = myreservationItems
    }

    fun clearItem() {
        itemsList.clear()
    }

    inner class MyreservationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var myresName = itemView.findViewById<TextView>(R.id.text_item_myres_name)
        var myresDate = itemView.findViewById<TextView>(R.id.text_item_myres_date)
        var myresTime = itemView.findViewById<TextView>(R.id.text_item_myres_time)
        var reviewbutton = itemView.findViewById<Button>(R.id.button_item_myres_review)
        var state = itemView.findViewById<TextView>(R.id.text_item_myres_state)
        var deletetext = itemView.findViewById<TextView>(R.id.text_item_myres_delete)


        fun bind(myresItem: MyreservationItem) {

        if (myresItem.cancel == "t") {
            state.text = "예약취소"
            state.setBackgroundResource(R.color.red)
            reviewbutton.visibility = View.GONE
            deletetext.visibility = View.VISIBLE

        }else if(comparetime.comparetime(myresItem.date, myresItem.time)=="now"){
            reviewbutton.visibility = View.GONE
            state.text = "이용중"
            state.setBackgroundResource(R.color.bluegrey)
            deletetext.visibility = View.GONE

       }else if(comparetime.comparetime(myresItem.date, myresItem.time)=="after"){
            reviewbutton.visibility = View.VISIBLE
            state.text = "이용완료"
            state.setBackgroundResource(R.color.bluegrey)
            deletetext.visibility = View.VISIBLE

            if(myresItem.review == "t"){
                reviewbutton.visibility = View.GONE
            }

        }else{
            state.text = "이용전"
            state.setBackgroundResource(R.color.colorPrimary)
            reviewbutton.visibility = View.GONE
            deletetext.visibility = View.GONE
        }


//            if(state.text.equals("이용전")){
//                state.setBackgroundResource(R.color.colorPrimary)
//            }

            myresName.text = myresItem.name
            myresDate.text = myresItem.date
            myresTime.text = myresItem.time

            itemView.setOnClickListener{
                //                    Toast.makeText(itemView.context, myresItem.no, Toast.LENGTH_SHORT).show()


                val nextIntent = Intent(itemView.context, MyResDetailActivity::class.java)
                nextIntent.putExtra("no",  myresItem.no)
                itemView.context.startActivity(nextIntent)

            }

            reviewbutton.setOnClickListener {
                val nextIntent = Intent(itemView.context, WritingReviewActivity::class.java)
                nextIntent.putExtra("no",  myresItem.no)
                itemView.context.startActivity(nextIntent)

            }


        }

    }
}