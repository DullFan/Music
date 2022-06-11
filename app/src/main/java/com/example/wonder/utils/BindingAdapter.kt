package com.example.wonder.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("glideImage")
    fun setImage(imageView: ImageView, url: String) {
        if (url.isNotBlank()) {
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("glideBlueImage")
    fun setBlueImage(imageView: ImageView, url: String) {
        if (url.isNotBlank()) {
            Glide.with(imageView.context).load(url).apply(
                bitmapTransform(
                    BlurTransformation(100, 1)
                )
            ).into(imageView)
        }
    }

}