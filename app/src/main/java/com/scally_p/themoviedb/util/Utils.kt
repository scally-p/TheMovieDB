package com.scally_p.themoviedb.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @SuppressLint("SimpleDateFormat")
    fun formatDate(strDate: String): String {
        var format = SimpleDateFormat("yyyy-MM-dd")
        try {
            val newDate = format.parse(strDate) ?: return strDate
            format = SimpleDateFormat("dd MMM yyyy")
            return format.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            return strDate
        }
    }
}