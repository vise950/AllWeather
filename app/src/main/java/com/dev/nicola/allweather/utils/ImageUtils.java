package com.dev.nicola.allweather.utils;

import android.content.res.Resources;

import com.dev.nicola.allweather.R;

/**
 * Created by Nicola on 18/08/2016.
 */
public class ImageUtils {

    /**
     * @param resources
     * @return String with contains URL for take image
     */
    public static String getImage(Resources resources) {
        String imageUrl;
        String headerWallpaper[] = resources.getStringArray(R.array.header_wallpaper);

        int random = (int) (Math.random() * headerWallpaper.length);
        imageUrl = headerWallpaper[random];

        return imageUrl;
    }
}
