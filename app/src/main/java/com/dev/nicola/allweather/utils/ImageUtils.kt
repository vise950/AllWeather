package com.dev.nicola.allweather.utils

import android.content.res.Resources

import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 18/08/2016.
 */
object ImageUtils {

    /**
     * @param resources
     * *
     * @return String with contains URL for take image
     */
    fun getImage(resources: Resources): String {
        val imageUrl: String
        val headerWallpaper = resources.getStringArray(R.array.header_wallpaper)

        val random = (Math.random() * headerWallpaper.size).toInt()
        imageUrl = headerWallpaper[random]

        return imageUrl
    }
}
