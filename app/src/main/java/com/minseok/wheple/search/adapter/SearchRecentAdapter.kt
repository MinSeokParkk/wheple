package com.minseok.wheple.search.adapter

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minseok.wheple.R
import com.minseok.wheple.place.PlaceActivity
import com.minseok.wheple.search.SearchContract
import com.minseok.wheple.search.model.RecentOpenHelper
import com.minseok.wheple.search.model.RecentSearch

class SearchRecentAdapter  (private var mpresenter: SearchContract.Presenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var itemsList:ArrayList<RecentSearch>
    lateinit var dbhelper :RecentOpenHelper


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_recent, parent, false)
        return SearchRecentViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder : SearchRecentViewHolder = holder as SearchRecentViewHolder

        itemsList[position].let{
           itemHolder?.bind(it)

        }

        holder.deleteS.setOnClickListener {
            dbhelper.deleteSearching(itemsList[position].name!!, itemsList[position].no!!)

            itemsList.removeAt(position)
            notifyItemRemoved(position)

        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter(){
        notifyDataSetChanged()
    }

    fun addItems(searchItems:ArrayList<RecentSearch>, rohelper:RecentOpenHelper){
        dbhelper = rohelper
        Log.d("searchT6","add items on 최근 검색 어댑터")
        Log.d("searchT6",searchItems.toString())
        this.itemsList = searchItems
    }




    inner class SearchRecentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var nameS = itemView.findViewById<TextView>(R.id.text_searchRecent_name)
        var deleteS = itemView.findViewById<TextView>(R.id.text_searchRecent_delete)

        fun bind(si: RecentSearch){
            nameS.text = si.name

            itemView.setOnClickListener {


                val nextIntent = Intent(itemView.context, PlaceActivity::class.java)
                nextIntent.putExtra("no", si.no)
                itemView.context.startActivity(nextIntent)

                //클릭한 장소를 sqlite에 저장 시키자. (이름, no)
                dbhelper.addSearching(si.name!!, si.no!!)

            }



        }
    }
}