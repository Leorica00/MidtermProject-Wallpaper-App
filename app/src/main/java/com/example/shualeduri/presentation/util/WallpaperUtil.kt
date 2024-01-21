package com.example.shualeduri.presentation.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shualeduri.data.common.AppError
import javax.inject.Inject

class WallpaperUtil @Inject constructor(val context: Context) {

    fun setWallpaper(
        bitmap: Bitmap,
        setHomeScreen: Boolean = true,
        setLockScreen: Boolean = false
    ) {
        try {
            val flags = when {
                setHomeScreen && setLockScreen -> WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                setHomeScreen -> WallpaperManager.FLAG_SYSTEM
                setLockScreen -> WallpaperManager.FLAG_LOCK
                else -> 0
            }
            WallpaperManager.getInstance(context).setBitmap(bitmap, null, true, flags)
        } catch (t: Throwable) {
            val errorMessage = AppError.fromException(t).message
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun downloadAndSetWallpaper(
        imageUrl: String,
        setHomeScreen: Boolean = true,
        setLockScreen: Boolean = false
    ) {
        Glide.with(context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                setWallpaper(resource, setHomeScreen, setLockScreen)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }
}