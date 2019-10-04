package com.minseok.wheple.cancel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.minseok.wheple.R
import kotlinx.android.synthetic.main.activity_cancel.*
import kotlinx.android.synthetic.main.dialog_filter_sports.*

class CancelActivity : AppCompatActivity(), CancelContract.View{
    private lateinit var mPresenter :CancelContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel)
        CancelPresenter(this)

        img_cancel_back.setOnClickListener {
            onBackPressed()
        }

        mPresenter.setData(intent.getStringExtra("no"))

        check_cancel.setOnCheckedChangeListener { buttonView, isChecked ->
            mPresenter.checkchange(check_cancel.isChecked)
        }

        button_cancel.setOnClickListener {
            mPresenter.clickbutton(check_cancel.isChecked, intent.getStringExtra("no"), text_cancel_refund.text.toString(),
                text_cancel_point.text.toString())
        }
    }


    override fun setPresenter(presenter: CancelContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun setText(refund: String, repoint: String, price: String, usedpoint: String, payment: String) {
        text_cancel_refund.text = refund
        text_cancel_repoint.text = repoint
        text_cancel_price.text = price
        text_cancel_point.text = usedpoint
        text_cancel_payment.text = payment

    }

    override fun button_on(){
        button_cancel.setBackgroundResource(R.drawable.button_on)
    }

    override fun button_off(){
        button_cancel.setBackgroundResource(R.drawable.button_off)
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun back() {
        super.onBackPressed()
    }

}