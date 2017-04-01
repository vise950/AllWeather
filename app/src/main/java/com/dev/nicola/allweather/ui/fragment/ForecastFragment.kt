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
import com.dev.nicola.allweather.model.ForecastDay
import com.dev.nicola.allweather.utils.Utils
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootDarkSky
import io.realm.Realm
import kotlinx.android.synthetic.main.forecast_fragment.*

class ForecastFragment : Fragment() {

    private var realm: Realm? = null
    private var darkSky: RootDarkSky? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()
        darkSky = realm?.where(RootDarkSky::class.java)?.findFirst()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.forecast_fragment, container, false)
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
        val forecastDayList = ArrayList<ForecastDay>()
        darkSky?.daily?.data?.forEach {
            forecastDayList.add(ForecastDay(Utils.TimeHelper.getDay(resources, it.time!!.toInt()),
                    it.summary,
                    Utils.ConverterHelper.temperature((it.temperatureMax!! + it.temperatureMin!!) / 2, "celsius"),
                    Utils.ConverterHelper.weatherIcon(it.icon.toString())))
        }

        val forecastDayAdapter = ForecastDayAdapter(context,forecastDayList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        forecast_day_recycle_view.layoutManager = layoutManager
        forecast_day_recycle_view.itemAnimator = DefaultItemAnimator()
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        forecast_day_recycle_view.addItemDecoration(itemDecoration)
        forecast_day_recycle_view.adapter = forecastDayAdapter
    }
}
