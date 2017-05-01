package com.dev.nicola.allweather.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.model.Apixu.RootApixu
import com.dev.nicola.allweather.model.DarkSky.RootDarkSky
import com.dev.nicola.allweather.model.Yahoo.RootYahoo
import com.dev.nicola.allweather.utils.PreferencesHelper
import io.realm.Realm
import kotlin.properties.Delegates


class DailyFragment : Fragment() {

    private var realm: Realm? = null
    private var darkSkyData: RootDarkSky? = null
    private var apixuData: RootApixu? = null
    private var yahooData: RootYahoo? = null

    private var prefTemp: String by Delegates.notNull()
    private var prefTime: String by Delegates.notNull()
    private var prefSpeed: String by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
        prefTemp = PreferencesHelper.getDefaultPreferences(activity, PreferencesHelper.KEY_PREF_TEMPERATURE, PreferencesHelper.DEFAULT_PREF_TEMPERATURE) as String
        prefTime = PreferencesHelper.getDefaultPreferences(activity, PreferencesHelper.KEY_PREF_TIME, PreferencesHelper.DEFAULT_PREF_TIME) as String
        prefSpeed = PreferencesHelper.getDefaultPreferences(activity, PreferencesHelper.KEY_PREF_SPEED, PreferencesHelper.DEFAULT_PREF_SPEED) as String
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.daily_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setUpRecyclerView()
//        setUpLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realm?.close()
    }

//    private fun setUpRecyclerView() {
//        val forecastHourAdapter = ForecastHourAdapter(context, getRecyclerData())
//        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        forecast_hour_recycle_view.layoutManager = layoutManager
//        forecast_hour_recycle_view.itemAnimator = DefaultItemAnimator()
//        forecast_hour_recycle_view.adapter = forecastHourAdapter
//    }
//
//    private fun getRecyclerData(): ArrayList<ForecastHour> {
//        val forecastHourList = ArrayList<ForecastHour>()
//        when (PreferencesHelper.getWeatherProvider(activity)) {
//            WeatherProvider.DARK_SKY -> {
//                forecast_hour_recycle_view.visibility = View.VISIBLE
//                darkSkyData = realm?.where(RootDarkSky::class.java)?.findFirst()
//                darkSkyData?.let {
//                    darkSkyData?.hourly?.data?.forEachIndexed { index, data ->
//                        if (index in 1..25) {
//                            forecastHourList.add(ForecastHour(Utils.TimeHelper.getHour(prefTime, index),
//                                    Utils.ConverterHelper.weatherIcon(data.icon ?: getString(R.string.error_no_text)),
//                                    Utils.ConverterHelper.temperature(data.temperature ?: 0.0, prefTemp)))
//                        }
//                    }
//                }
//            }
//
//            WeatherProvider.APIXU -> {
//                forecast_hour_recycle_view.visibility = View.VISIBLE
//                var indexHour = Utils.TimeHelper.localTimeHour
//                var item = 1
//                apixuData = realm?.where(RootApixu::class.java)?.findFirst()
//                apixuData?.let {
//                    apixuData?.forecast?.forecastday?.forEach {
//                        it.hour?.forEachIndexed { index, data ->
//                            if (index >= indexHour) {
//                                if (item <= 25) {
//                                    forecastHourList.add(ForecastHour(Utils.TimeHelper.getHour(prefTime, item),
//                                            Utils.ConverterHelper.weatherIcon(data.condition?.code?.toString() ?: getString(R.string.error_no_text)),
//                                            Utils.ConverterHelper.temperature(data.tempC ?: 0.0, prefTemp, "celsius")))
//                                    item++
//                                } else {
//                                    return@forEach
//                                }
//                                if (index >= 23) {
//                                    indexHour = 0
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            WeatherProvider.YAHOO -> {
//                forecast_hour_recycle_view.visibility = View.GONE
//                yahooData = realm?.where(RootYahoo::class.java)?.findFirst()
//            }
//        }
//        return forecastHourList
//    }
//
//    private fun setUpLayout() {
//        when (PreferencesHelper.getWeatherProvider(activity)) {
//            WeatherProvider.DARK_SKY -> {
//
//                //todo come faccio l'immagine header??
//                Glide.with(activity).load(Utils.getHeaderImage(resources)).placeholder(R.drawable.header_drawer).into(image_daily_fragment)
//
//                darkSkyData?.let {
//                    condition_daily_fragment.text = darkSkyData?.currently?.summary ?: getString(R.string.error_no_text)
//                    temperature_daily_fragment.text = Utils.ConverterHelper.temperature(darkSkyData?.currently?.temperature ?: 0.0, prefTemp)
//                    wind_daily_fragment.text = String.format(resources.getString(R.string.wind),
//                            Utils.ConverterHelper.windDirection(darkSkyData?.currently?.windBearing ?: -1),
//                            Utils.ConverterHelper.speed(darkSkyData?.currently?.windSpeed ?: 0.0, prefSpeed))
//                    humidity_daily_fragment.text = String.format(resources.getString(R.string.humidity),
//                            darkSkyData?.currently?.humidity ?: getString(R.string.error_no_text))
//                    pressure_daily_fragment.text = String.format(resources.getString(R.string.pressure),
//                            darkSkyData?.currently?.pressure ?: getString(R.string.error_no_text))
//                    sunrise_daily_fragment.text = Utils.TimeHelper.formatTime(darkSkyData?.daily?.data?.get(0)?.sunriseTime ?: 0L, null, prefTime)
//                    sunset_daily_fragment.text = Utils.TimeHelper.formatTime(darkSkyData?.daily?.data?.get(0)?.sunsetTime ?: 0L, null, prefTime)
//                }
//                //fixme theme
////                when (PreferencesHelper.getDefaultPreferences(activity, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.DEFAULT_PREF_THEME)) {
////                    "light" -> Glide.with(activity).load(R.drawable.ic_darksky_dark).into(provider_logo)
////                    "dark" -> Glide.with(activity).load(R.drawable.ic_darksky_light).into(provider_logo)
////                }
//
//                Glide.with(activity).load(R.drawable.ic_darksky_dark).into(provider_logo)
//                provider_logo.setOnClickListener {
//                    openBrowser("https://darksky.net/poweredby/")
//                }
//            }
//
//            WeatherProvider.APIXU -> {
//
//                Glide.with(this).load(Utils.getHeaderImage(resources)).placeholder(R.drawable.header_drawer).into(image_daily_fragment)
//
//                apixuData?.let {
//                    condition_daily_fragment.text = apixuData?.current?.currentConditionApixu?.text ?: getString(R.string.error_no_text)
//                    temperature_daily_fragment.text = Utils.ConverterHelper.temperature(apixuData?.current?.tempC ?: 0.0, prefTemp, "celsius")
//                    wind_daily_fragment.text = String.format(resources.getString(R.string.wind),
//                            Utils.ConverterHelper.windDirection(apixuData?.current?.windDegree ?: -1),
//                            Utils.ConverterHelper.speed(apixuData?.current?.windMph ?: 0.0, prefSpeed))
//                    humidity_daily_fragment.text = String.format(resources.getString(R.string.humidity),
//                            apixuData?.current?.humidity ?: getString(R.string.error_no_text))
//                    pressure_daily_fragment.text = String.format(resources.getString(R.string.pressure),
//                            apixuData?.current?.pressureMb ?: getString(R.string.error_no_text))
//                    sunrise_daily_fragment.text = Utils.TimeHelper.formatTime(0, apixuData?.forecast?.forecastday?.get(0)?.astroApixu?.sunrise, prefTime)
//                    sunset_daily_fragment.text = Utils.TimeHelper.formatTime(0, apixuData?.forecast?.forecastday?.get(0)?.astroApixu?.sunset, prefTime)
//                }
//                //todo add logo
//                provider_logo.visibility = View.GONE
//            }
//
//            WeatherProvider.YAHOO -> {
//
//                Glide.with(this).load(Utils.getHeaderImage(resources)).placeholder(R.drawable.header_drawer).into(image_daily_fragment)
//
//                yahooData?.let {
//                    condition_daily_fragment.text = yahooData?.query?.results?.channel?.item?.condition?.text ?: getString(R.string.error_no_text)
//                    temperature_daily_fragment.text = Utils.ConverterHelper.temperature(yahooData?.query?.results?.channel?.item?.condition?.temp?.toDouble() ?: 0.0, prefTemp)
//                    wind_daily_fragment.text = String.format(resources.getString(R.string.wind),
//                            Utils.ConverterHelper.windDirection(yahooData?.query?.results?.channel?.wind?.direction?.toInt() ?: -1),
//                            Utils.ConverterHelper.speed(yahooData?.query?.results?.channel?.wind?.speed?.toDouble() ?: 0.0, prefSpeed))
//                    humidity_daily_fragment.text = String.format(resources.getString(R.string.humidity),
//                            yahooData?.query?.results?.channel?.atmosphereYahoo?.humidity ?: getString(R.string.error_no_text))
//                    pressure_daily_fragment.text = String.format(resources.getString(R.string.pressure),
//                            yahooData?.query?.results?.channel?.atmosphereYahoo?.humidity ?: getString(R.string.error_no_text))
//                    sunrise_daily_fragment.text = Utils.TimeHelper.formatTime(0, yahooData?.query?.results?.channel?.astronomyYahoo?.sunrise, prefTime)
//                    sunset_daily_fragment.text = Utils.TimeHelper.formatTime(0, yahooData?.query?.results?.channel?.astronomyYahoo?.sunset, prefTime)
//                }
//
//                Glide.with(activity).load(R.drawable.ic_yahoo).into(provider_logo)
//                provider_logo.setOnClickListener {
//                    openBrowser("https://www.yahoo.com/news/weather/")
//                }
//            }
//        }
//    }
//
//    private fun openBrowser(url: String) {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(intent)
//    }
}