package com.otb.vipboilerplate.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Created by Mohit Rajput on 6/5/19.
 * Glide image loading utility methods
 */
object GlideUtils {

    fun loadImage(
        activity: Activity?, url: String,
        imageView: ImageView, progressBar: ProgressBar
    ) {
        if (activity != null && !activity.isFinishing) {
            Glide.with(activity).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(
                        e: Exception,
                        model: String,
                        target: Target<GlideDrawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: GlideDrawable,
                        model: String,
                        target: Target<GlideDrawable>,
                        isFromMemoryCache: Boolean,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(imageView)
        }
    }

    fun loadImageAsBitmap(
        activity: Activity?, url: String,
        imageView: ImageView, placeholder: Int
    ) {
        if (activity != null && !activity.isFinishing) {
            Glide.with(activity).load(url).asBitmap().placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

    fun loadImage(
        context: Context, url: String,
        imageView: ImageView, placeholder: Int
    ) {
        Glide.with(context).load(url).placeholder(placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}
