package com.unaluzdev.enswiclopedia.ui.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.unaluzdev.enswiclopedia.R

fun renderImageWith(
    context: Context,
    imageView: ImageView,
    url: String,
    options: (RequestBuilder<Drawable>.() -> RequestBuilder<Drawable>)? = null
) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_image)
        .also {
            if (options != null) {
                it.options()
            }
        }
        .into(imageView)
}