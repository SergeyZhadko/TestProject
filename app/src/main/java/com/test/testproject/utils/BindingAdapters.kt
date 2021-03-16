package com.test.testproject.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:image")
fun loadImage(imageView: ImageView, imageSuffix: String){
    Glide.with(imageView)
        .load(IMAGE_URL_500 + imageSuffix)
        .centerCrop()
        .into(imageView)
}