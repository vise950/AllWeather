package com.dev.nicola.allweather.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.MotionEvent
import android.view.View
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.adapter.FragmentAdapter
import com.dev.nicola.allweather.preferences.AppPreferences
import com.dev.nicola.allweather.service.MapsGoogleApiClient
import com.dev.nicola.allweather.service.WeatherClient
import com.dev.nicola.allweather.utils.*
import com.dev.nicola.allweather.weatherProvider.Apixu.model.RootApixu
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootDarkSky
import com.dev.nicola.allweather.weatherProvider.Yahoo.model.RootYahoo
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.placeholder_something_was_wrong.*
import java.util.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private var prefTheme: String? = null
    private var prefWeatherProvider: String? = null
    private var goToSetting = false
    private var firstRun = true
    private var offlineMode = false

    private val searchResult: ArrayList<PredictionResult> = ArrayList()

    private var googleApiClient: GoogleApiClient? = null
    private var location: Location? = null
    private var locationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    private var realm: Realm? = null

    private var billing: Billing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.changeTheme(this)
        setContentView(R.layout.activity_main)
        progress_dialog_container.visibility = View.VISIBLE

        realm = Realm.getDefaultInstance()

        buildGoogleClient()

        billing = Billing(this)
        billing!!.onCreate()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        billing!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()

        goToSetting.log("setting")

        if (!goToSetting) {
            Handler().postDelayed({
                initialCheckUp()
            }, 1500)
        } else {
            //fixme change theme
            if (PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.DEFAULT_PREF_THEME, prefTheme!!) as Boolean) {
                recreate()
            }

            if (PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_WEATHER_PROVIDER, PreferencesHelper.DEFAULT_PREF_WEATHER_PROVIDER, prefWeatherProvider!!) as Boolean) {
                getData(location?.latitude, location?.longitude)
            }
            goToSetting = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (googleApiClient!!.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (googleApiClient!!.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
            googleApiClient?.disconnect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (billing != null) {
            billing!!.onDestroy()
        }

        realm?.close()
        realm = null
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (search_view.isSearchBarFocused) {
                search_view.clearSearchFocus()
            } else {
                super.onBackPressed()
            }
        }
    }


    private fun initialCheckUp() {
        if (PreferencesHelper.isFirstLaunch(this) as Boolean && (!Utils.isLocationEnable(this) || !Utils.isConnectedToInternet(this))) {

            getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().clear().apply()
            getDefaultSharedPreferences(this).edit().clear().apply()

            progress_dialog_container.visibility = View.GONE
            placeholder_something_wrong.visibility = View.VISIBLE
            retry_button.setOnClickListener {
                placeholder_something_wrong.visibility = View.GONE
                progress_dialog_container.visibility = View.VISIBLE
                onResume()
            }
        } else {
            PreferencesHelper.setPreferences(this, PreferencesHelper.KEY_FIRST_LAUNCH, false)
            if (!Utils.isLocationEnable(this) || !Utils.isConnectedToInternet(this)) {
                offlineMode = true
                initialSetup()
            } else {
                offlineMode = false
                googleApiClient?.reconnect()
                initialSetup()
            }
        }
    }

    private fun initialSetup() {
        firstRun = true
        getPreferences()
        setDrawer()
        setNavigationView()
        setSearchView()
        setSwipeToRefresh()
        if (offlineMode) {
            setViewPager()
            progress_dialog_container.visibility = View.GONE
            //todo show alert
        }
    }


    private fun getPreferences() {
        prefTheme = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.DEFAULT_PREF_THEME) as String
        prefWeatherProvider = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_WEATHER_PROVIDER, PreferencesHelper.DEFAULT_PREF_WEATHER_PROVIDER) as String
    }


    private fun getData(latitude: Double?, longitude: Double?) {
        var call: Observable<RootDarkSky>? = null
        var call1: Observable<RootApixu>? = null
        var call2: Observable<RootYahoo>? = null
//        var id: String? = null

        when (PreferencesHelper.getWeatherProvider(this)) {
            WeatherProvider.DARK_SKY -> {
//                call = WeatherClient(this).service.getDarkSkyData(latitude!!, longitude!!)
                call1 = WeatherClient(this).service.getApixuData("latitude!!, longitude!!")
//                call2 = WeatherClient(this).service.getYahooData("select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text="sossano")")
//                id = WeatherProvider.DARK_SKY.value
            }
        }

        call1!!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    realm?.executeTransactionAsync({
                        //                        data.id = id
                        it.copyToRealmOrUpdate(data)
                    }, {
                        progress_dialog_container?.visibility = View.GONE
                        refresh_layout?.isRefreshing = false
                        setViewPager()
                        PreferencesHelper.setPreferences(this, PreferencesHelper.KEY_LAST_UPDATE, System.currentTimeMillis())
                    }, {
                        it.log("realm Error")
                    })
                }, { error ->
                    error.log("Error call")
                    progress_dialog_container?.visibility = View.GONE
                    SnackBarHelper.serverError(this, {
                        progress_dialog_container?.visibility = View.VISIBLE
                        getData(location?.latitude, location?.longitude)
                    })
                    setViewPager()
                })
    }


    /**
     * Drawer
     */
    private fun setDrawer() {
        drawer_layout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                supportInvalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                supportInvalidateOptionsMenu()
            }
        })
    }


    /**
     * Navigation View
     */
    private fun setNavigationView() {
        navigation_view.setNavigationItemSelectedListener {
            Handler().postDelayed({
                when (it.itemId) {
                    R.id.drawer_item_settings -> {
                        startActivity(Intent(this, AppPreferences::class.java))
                        goToSetting = true
                    }

                    R.id.drawer_item_pro_version -> {
                        if (PreferencesHelper.isProVersion(this) as Boolean) {
                            SnackBarHelper.alreadyPro(this)
                        } else {
                            billing?.purchaseDialog()
                        }
                    }
                }
            }, 250)
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }


    /**
     * Search View
     */
    private fun setSearchView() {
        var clickedItem = false

        search_view.attachNavigationDrawerToMenuButton(drawer_layout)

        //todo setTheme

        search_view.setOnFocusChangeListener { v, focus ->
            if (!focus && !search_view.query.isNullOrEmpty()) {
                search_view.clearQuery()
                clickedItem = false
            }
        }

        search_view.setOnQueryChangeListener { oldQuery, newQuery ->
            if (!clickedItem && oldQuery != newQuery && newQuery.trim() != "" && newQuery.length > 2) {
                searchSuggestion(newQuery)
                search_view.showProgress()
            } else {
                search_view.clearSuggestions()
            }
        }

        search_view.setOnBindSuggestionCallback { suggestionView, leftIcon, textView, item, itemPosition ->
            if (item.body == resources.getString(R.string.no_result_suggestion) || item.body == resources.getString(R.string.no_result_suggestion)) {
                leftIcon.setImageResource(R.drawable.ic_error_outline)
                suggestionView.setOnClickListener {
                    search_view.clearSuggestions()
                    search_view.clearQuery()
                    search_view.clearSearchFocus()
                }
            } else {
                leftIcon.setImageResource(R.drawable.ic_location_city)
                suggestionView.setOnClickListener {
                    Utils.LocationHelper.getCoordinates(item.body, {
                        getData(it.latitude, it.longitude)
                        location = it
                    })
                    clickedItem = true
                    search_view.setSearchText(textView.text)
                    search_view.clearSearchFocus()
                    search_view.clearSuggestions()
                }
            }
        }
    }

    private fun searchSuggestion(query: String) {
        if (searchResult.isNotEmpty()) {
            searchResult.clear()
        }

        MapsGoogleApiClient.service.getPrediction(query).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    if (data.predictions!!.isNotEmpty()) {
                        data.predictions.forEachIndexed { index, data ->
                            if (index in 1..4) {
                                searchResult.add(PredictionResult(Utils.trimString(data.description.toString())))
                            }
                        }
                    } else {
                        searchResult.add(PredictionResult(resources.getString(R.string.no_result_suggestion)))
                    }
                    search_view.swapSuggestions(searchResult)
                    search_view.hideProgress()
                }, { error ->
                    error.log("Error call")
                    searchResult.add(PredictionResult(resources.getString(R.string.error_load_suggestion)))
                    search_view.swapSuggestions(searchResult)
                    search_view.hideProgress()
                })
    }


    /**
     *  View Pager
     */
    private fun setViewPager() {
        val fragmentAdapter = FragmentAdapter(supportFragmentManager, this)
        view_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(view_pager)

        view_pager.setOnTouchListener { view, motionEvent ->
            refresh_layout.isEnabled = false
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> refresh_layout.isEnabled = true
            }
            return@setOnTouchListener false
        }
    }


    /**
     *  Swipe to refresh
     */
    private fun setSwipeToRefresh() {
        refresh_layout.setOnRefreshListener {
            if ((PreferencesHelper.getPreferences(this, PreferencesHelper.KEY_LAST_UPDATE, 0L) as Long) < Utils.localTimeMillis - 600000L) {
                getData(location?.latitude, location?.longitude)
            } else {
                SnackBarHelper.dataRefresh(this)
                refresh_layout.isRefreshing = false
            }
        }
    }


    @Synchronized private fun buildGoogleClient() {
        googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        createLocationRequest()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
            if (location != null) {
                if (!offlineMode) {
                    getData(location?.latitude, location?.longitude)
                }

                Utils.LocationHelper.getLocationName(location!!.latitude, location!!.longitude, {
                    search_view.setSearchText(it)
                }, {
                    search_view.setSearchText(resources.getString(R.string.no_result_suggestion))
                })
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 123)
        }
    }

    override fun onConnected(bundle: Bundle?) {
        getDeviceLocation()
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onLocationChanged(location: Location?) {
        this.location = location
    }
}