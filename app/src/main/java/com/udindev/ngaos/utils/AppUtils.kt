package com.udindev.ngaos.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.udindev.ngaos.R

object AppUtils {
    fun loadImageFromUrl(imageView: ImageView?, url: String?) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_no_pic)
                .error(R.drawable.ic_no_pic)
                .centerCrop()
                .fit()
                .into(imageView)
    }
}