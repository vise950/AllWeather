package com.dev.nicola.allweather.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.dev.nicola.allweather.ui.fragment.DailyFragment
import com.dev.nicola.allweather.ui.fragment.ForecastFragment
import com.dev.nicola.allweather.R

class FragmentAdapter(fm: FragmentManager, private val context: Context) : FragmentStatePagerAdapter(fm) {

    private val PAGE_COUNT = 2

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return DailyFragment()
            1 -> return ForecastFragment()
        }
        return null
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.tab1_title)
            1 -> return context.getString(R.string.tab2_title)
        }
        return null
    }
}
