package com.marcelo.marvelheroes.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.marcelo.marvelheroes.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_img_loading_error)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}
