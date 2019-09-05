package com.minseok.wheple

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        view.constraint1.setOnClickListener {
            activity?.let{
                val intent = Intent (it, SpaceActivity::class.java)
                it.startActivity(intent)
            }
        }

//        view.filterSports_layout.setOnClickListener {
//            activity?.let{
//                val intent = Intent (it, FilterSportsDialog::class.java)
//                it.startActivity(intent)
//            }
//        }

        // Return the fragment view/layout
        return view
    }



    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}