package com.dev.nicola.allweather.Util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dev.nicola.allweather.DailyFragment;
import com.dev.nicola.allweather.ForecastFragment;

/**
 * Created by Nicola on 12/05/2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = {"NOW", "WEEK"};
    private String argument;

    public FragmentAdapter(FragmentManager fm, String argument) {
        super(fm);
        this.argument = argument;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return DailyFragment.newInstance(argument);
        else
            return ForecastFragment.newInstance(argument);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
