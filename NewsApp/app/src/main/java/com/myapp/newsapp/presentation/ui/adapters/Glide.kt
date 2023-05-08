package com.myapp.newsapp.presentation.ui.adapters

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader(): ImageDisplay {
    override fun display(url: String,imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
}