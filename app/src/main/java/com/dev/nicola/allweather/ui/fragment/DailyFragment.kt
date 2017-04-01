package com.dev.nicola.allweather.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.adapter.ForecastHourAdapter
import com.dev.nicola.allweather.model.ForecastHour
import com.dev.nicola.allweather.utils.Utils
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootDarkSky
import io.realm.Realm
import kotlinx.android.synthetic.main.daily_fragment.*
import java.util.*

class DailyFragment : Fragment() {

    private var realm: Realm? = null
    private var darkSky: RootDarkSky? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()
        darkSky = realm?.where(RootDarkSky::class.java)?.findFirst()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.daily_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realm?.close()
    }

    private fun setUpRecyclerView() {
        val forecastHourList = ArrayList<ForecastHour>()
        darkSky?.hourly?.data?.forEachIndexed { index, hourlyData ->
            if (index in 1..25) {
                forecastHourList.add(ForecastHour(Utils.TimeHelper.getHourFormat(hourlyData.time!!, null, "24"),
                        Utils.ConverterHelper.weatherIcon(hourlyData.icon.toString()),
                        Utils.ConverterHelper.temperature(hourlyData.temperature!!, "celsius")))
            }
        }
        val forecastHourAdapter = ForecastHourAdapter(context, forecastHourList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        forecast_hour_recycle_view.layoutManager = layoutManager
        forecast_hour_recycle_view.itemAnimator = DefaultItemAnimator()
        forecast_hour_recycle_view.adapter = forecastHourAdapter
    }

    private fun setUpLayout() {
        Glide.with(this).load(Utils.getHeaderImage(resources)).placeholder(R.drawable.header_drawer).into(image_daily_fragment)
        condition_daily_fragment.text = darkSky?.currently?.summary
        temperature_daily_fragment.text = Utils.ConverterHelper.temperature(darkSky?.currently?.temperature!!, "celsius")
        wind_daily_fragment.text = String.format(resources.getString(R.string.wind),
                Utils.ConverterHelper.windDirection(darkSky?.currently?.windBearing!!),
                Utils.ConverterHelper.speed(darkSky?.currently?.windSpeed!!, "ms"))
        humidity_daily_fragment.text = String.format(resources.getString(R.string.humidity),
                darkSky?.currently?.humidity.toString())
        pressure_daily_fragment.text = String.format(resources.getString(R.string.pressure),
                darkSky?.currently?.pressure.toString())
        sunrise_daily_fragment.text = Utils.TimeHelper.getHourFormat(darkSky?.daily?.data?.get(0)?.sunriseTime!!, null, "24")
        sunset_daily_fragment.text = Utils.TimeHelper.getHourFormat(darkSky?.daily?.data?.get(0)?.sunsetTime!!, null, "24")
    }
}
