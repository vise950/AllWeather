package com.dev.nicola.allweather.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.adapter.ForecastDayAdapter
import com.dev.nicola.allweather.model.Apixu.RootApixu
import com.dev.nicola.allweather.model.DarkSky.RootDarkSky
import com.dev.nicola.allweather.model.ForecastDay
import com.dev.nicola.allweather.model.Yahoo.RootYahoo
import com.dev.nicola.allweather.utils.PreferencesHelper
import com.dev.nicola.allweather.utils.Utils
import com.dev.nicola.allweather.utils.WeatherProvider
import io.realm.Realm
import kotlinx.android.synthetic.main.forecast_fragment.*
import kotlin.properties.Delegates

class ForecastFragment : Fragment() {

    private var realm: Realm? = null
    private var darkSkyData: RootDarkSky? = null
    private var apixuData: RootApixu? = null
    private var yahooData: RootYahoo? = null

    private var prefTemp: String by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
        prefTemp = PreferencesHelper.getDefaultPreferences(activity, PreferencesHelper.KEY_PREF_TEMPERATURE, PreferencesHelper.DEFAULT_PREF_TEMPERATURE) as String
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realm?.close()
    }

    private fun setUpRecyclerView() {
        val forecastDayAdapter = ForecastDayAdapter(context, getRecyclerData())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        forecast_day_recycle_view.layoutManager = layoutManager
        forecast_day_recycle_view.itemAnimator = DefaultItemAnimator()
        forecast_day_recycle_view.addItemDecoration(DividerItemDecoration(activity,LinearLayoutManager.VERTICAL))
        forecast_day_recycle_view.adapter = forecastDayAdapter
    }

    private fun getRecyclerData(): ArrayList<ForecastDay> {
        val forecastDayList = ArrayList<ForecastDay>()
        when (PreferencesHelper.getWeatherProvider(activity)) {
            WeatherProvider.DARK_SKY -> {
                darkSkyData = realm?.where(RootDarkSky::class.java)?.findFirst()
                darkSkyData?.let {
                    darkSkyData?.daily?.data?.forEachIndexed { index, data ->
                        forecastDayList.add(ForecastDay(Utils.TimeHelper.getDate(activity, index),
                                data.summary ?: getString(R.string.error_no_text),
                                Utils.ConverterHelper.temperature(((data.temperatureMax ?: 0.0).plus(data.temperatureMin ?: 0.0)) / 2, prefTemp),
                                Utils.ConverterHelper.weatherIcon(data.icon ?: getString(R.string.error_no_text))))
                    }
                }
            }

            WeatherProvider.APIXU -> {
                apixuData = realm?.where(RootApixu::class.java)?.findFirst()
                apixuData?.let {
                    apixuData?.forecast?.forecastday?.forEachIndexed { index, data ->
                        forecastDayList.add(ForecastDay(Utils.TimeHelper.getDate(activity, index),
                                data?.day?.condition?.text ?: getString(R.string.error_no_text),
                                Utils.ConverterHelper.temperature(data?.day?.avgtempC ?: 0.0, prefTemp, "celsius"),
                                Utils.ConverterHelper.weatherIcon(data.day?.condition?.code?.toString() ?: getString(R.string.error_no_text))))
                    }
                }
            }

            WeatherProvider.YAHOO -> {
                yahooData = realm?.where(RootYahoo::class.java)?.findFirst()
                yahooData?.let {
                    yahooData?.query?.results?.channel?.item?.forecast?.forEachIndexed { index, data ->
                        forecastDayList.add(ForecastDay(Utils.TimeHelper.getDate(activity, index),
                                data?.text ?: getString(R.string.error_no_text),
                                Utils.ConverterHelper.temperature(((data?.low?.toDouble() ?: 0.0).plus(data.high?.toDouble() ?: 0.0)) / 2, prefTemp),
                                Utils.ConverterHelper.weatherIcon(data.code ?: getString(R.string.error_no_text))))
                    }
                }
            }
        }
        return forecastDayList
    }
}
