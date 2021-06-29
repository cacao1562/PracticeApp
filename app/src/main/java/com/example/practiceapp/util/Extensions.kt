package com.example.practiceapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.practiceapp.R

fun ImageView.loadImageOrDefault(imgUrl: String) {
    if (imgUrl.isNotEmpty())
        Glide.with(context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(android.R.drawable.stat_notify_error))
            .into(this)
    else
        this.setImageResource(android.R.drawable.star_off)
}