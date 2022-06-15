package com.example.wonder.utils

import android.view.View
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

    @JvmStatic
    @BindingAdapter("isVisibility")
    fun isVisibility(view: View, flag: Boolean) {
        if (flag) {
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
        }
    }

}