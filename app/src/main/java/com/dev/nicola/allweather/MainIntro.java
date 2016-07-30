package com.dev.nicola.allweather;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Nicola on 01/04/2016.
 */
public class MainIntro extends AppIntro2 {


    @Override
    public void init(Bundle savedInstanceState) {


        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.intro_title_1), getResources().getString(R.string.intro_description_1), R.drawable.clear_day, Color.parseColor("#2196F3")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.intro_title_2), getResources().getString(R.string.intro_description_permission_2), R.drawable.snow, Color.parseColor("#00BCD4")));
            askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.intro_title_2), getResources().getString(R.string.intro_description_2), R.drawable.snow, Color.parseColor("#00BCD4")));
        }

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.intro_title_3), getResources().getString(R.string.intro_description_3), R.drawable.cloud, Color.parseColor("#4CAF50")));


    }

    @Override
    public void onDonePressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }

}
