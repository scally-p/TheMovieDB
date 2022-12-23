package com.scally_p.themoviedb.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtils {

    fun setGlideImage(view: View, imageView: ImageView, url: String) {
        Glide.with(view)
            .load(url)
            .into(imageView)
    }
}