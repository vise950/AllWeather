package com.dev.nicola.allweather.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dev.nicola.allweather.DailyFragment;
import com.dev.nicola.allweather.ForecastFragment;
import com.dev.nicola.allweather.R;

/**
 * Created by Nicola on 12/05/2016.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 2;
    private Context context;
    private String argument;

    public FragmentAdapter(FragmentManager fm, Context context, String argument) {
        super(fm);
        this.context = context;
        this.argument = argument;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DailyFragment.newInstance(argument);

            case 1:
                return ForecastFragment.newInstance(argument);
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tab1_title);

            case 1:
                return context.getString(R.string.tab2_title);
        }
        return null;
    }

}
