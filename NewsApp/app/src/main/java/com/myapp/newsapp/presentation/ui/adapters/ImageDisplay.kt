package com.myapp.newsapp.presentation.ui.adapters

import android.view.View
import android.widget.ImageView

interface ImageDisplay {
    fun display(url: String, imageView: ImageView)
}