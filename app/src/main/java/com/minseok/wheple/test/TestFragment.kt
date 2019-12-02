package com.minseok.wheple.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.fragment_test.view.*

class TestFragment  : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val view: View = inflater.inflate(R.layout.fragment_test, container, false)


        view.text_testf.setOnClickListener {
            showToast("플래그먼트의 이동")
        }

        return view
    }

    fun showToast(string: String) {
        Toast.makeText(this.context, string, Toast.LENGTH_SHORT).show()
    }
}