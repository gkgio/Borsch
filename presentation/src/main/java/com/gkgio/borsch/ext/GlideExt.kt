package com.gkgio.borsch.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target

fun RequestBuilder<*>.withCenterCropRoundedCorners(
    context: Context,
    radiusDp: Int = 8
) = apply(
    RequestOptions().transform(
        CenterCrop(),
        RoundedCorners(context.dpToPx(radiusDp))
    )
)

fun RequestBuilder<Drawable>.withFade() = apply(
    transition(DrawableTransitionOptions.withCrossFade())
)

fun RequestBuilder<Drawable>.withCenterCropOval() = apply(
    RequestOptions().circleCrop()
)

fun RequestBuilder<Drawable>.placeholderByDrawable(
    @DrawableRes drawableRes: Int,
    successListener: (() -> Unit)? = null
) =
    apply(
        placeholder(
            drawableRes
        ).doOnSuccess { imageView ->
            imageView?.setImageDrawable(null)
            successListener?.invoke()
        }
    )

fun RequestBuilder<Drawable>.doOnSuccess(action: (ImageView?) -> Unit) = apply(
    listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            action((target as? DrawableImageViewTarget)?.view)
            return false
        }
    })
)