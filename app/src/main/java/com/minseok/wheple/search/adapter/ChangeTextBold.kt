package com.minseok.wheple.search.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.google.android.material.internal.ForegroundLinearLayout

class ChangeTextBold {
    fun change(text:String, word:String):SpannableString{
        val spannableString = SpannableString(text)

        val start = text.indexOf(word)
        val end = start + word.length

        spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#000000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return  spannableString
    }
}