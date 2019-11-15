package com.minseok.wheple.search.adapter

import android.content.Intent
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
import com.minseok.wheple.search.model.SearchItem

class SearchResultAdapter (private var mpresenter: SearchContract.Presenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var itemsList:ArrayList<SearchItem>
    lateinit var dbhelper :RecentOpenHelper
    var input =""

    val changeB = ChangeTextBold()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder : SearchResultViewHolder = holder as SearchResultViewHolder

        itemsList[position].let{
            itemHolder?.bind(it)
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun notifyAdapter(){
        notifyDataSetChanged()
    }

    fun addItems(searchItems:ArrayList<SearchItem>, input_:String, rohelper:RecentOpenHelper){
        Log.d("searchT6","add items")
        this.itemsList = searchItems
        input = input_
        dbhelper = rohelper
    }




    inner class SearchResultViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var nameS = itemView.findViewById<TextView>(R.id.text_searchItem_name)
        var addressS = itemView.findViewById<TextView>(R.id.text_searchItem_address)

        fun bind(si: SearchItem){
            nameS.text = si.name

            if(input  in nameS.text.toString()){
                nameS.text = changeB.change(nameS.text.toString(), input)
            }

            addressS.text = si.address

            itemView.setOnClickListener {

                //클릭한 장소를 sqlite에 저장 시키자. (이름, no)
                dbhelper.addSearching(si.name, si.no)

                val nextIntent = Intent(itemView.context, PlaceActivity::class.java)
                nextIntent.putExtra("no", si.no)
                itemView.context.startActivity(nextIntent)
            }

        }
    }
}