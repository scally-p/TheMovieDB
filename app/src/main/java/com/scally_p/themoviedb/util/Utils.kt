package com.scally_p.themoviedb.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.scally_p.themoviedb.R
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback
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

    fun launchChromeCustomTab(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setShowTitle(true)
            .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            .setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)
            .build()
        CustomTabsHelper.addKeepAliveExtra(context, customTabsIntent.intent)

        CustomTabsHelper.openCustomTab(
            context, customTabsIntent,
            Uri.parse(if (url.startsWith("www")) "http://$url" else url),
            WebViewFallback()
        )
    }
}