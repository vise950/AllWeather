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
import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.adapter.FragmentAdapter
import com.dev.nicola.allweather.preferences.AppPreferences
import com.dev.nicola.allweather.retrofit.MapsGoogleApiClient
import com.dev.nicola.allweather.retrofit.WeatherRequest
import com.dev.nicola.allweather.utils.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private var prefTheme: String? = null
    private var prefWeatherProvider: String? = null
    private var prefTemp: String? = null
    private var prefSpeed: String? = null
    private var prefTime: String? = null
    private var goToSetting = false
    private var offlineMode = false

    private val searchResult: ArrayList<PredictionResult> = ArrayList()

    private var fragmentAdapter: FragmentAdapter? = null

    private var googleApiClient: GoogleApiClient? = null
    private var location: Location? = null
    private var locationName: String? = null
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
        billing?.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        billing?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()

        if (!goToSetting) {
            Handler().postDelayed({
                initCheckUp()
            }, 1500)
        } else {
            if (PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.DEFAULT_PREF_THEME, prefTheme.toString()) ?: false) {
                recreate()
            }
            if (PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_WEATHER_PROVIDER,
                    PreferencesHelper.DEFAULT_PREF_WEATHER_PROVIDER, prefWeatherProvider.toString()) ?: false) {
                getData(location?.latitude, location?.longitude)
            }
            if (PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_TEMPERATURE, PreferencesHelper.DEFAULT_PREF_TEMPERATURE, prefTemp.toString()) ?: false ||
                    PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_SPEED, PreferencesHelper.DEFAULT_PREF_SPEED, prefSpeed.toString()) ?: false ||
                    PreferencesHelper.isPreferenceChange(this, PreferencesHelper.KEY_PREF_TIME, PreferencesHelper.DEFAULT_PREF_TIME, prefTime.toString()) ?: false) {
                fragmentAdapter?.notifyDataSetChanged()
            }
            goToSetting = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (googleApiClient?.isConnected ?: false) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (googleApiClient?.isConnected ?: false) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
            googleApiClient?.disconnect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        billing?.onDestroy()
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

    private fun initCheckUp() {
        if (PreferencesHelper.isFirstLaunch(this) ?: false && (!Utils.isLocationEnable(this) || !Utils.isConnectedToInternet(this))) {

            getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().clear().apply()
            getDefaultSharedPreferences(this).edit().clear().apply()

            progress_dialog_container.visibility = View.GONE
            SnackBarHelper.noInternetOrLocationActive(this,
                    {
                        progress_dialog_container.visibility = View.VISIBLE
                        onResume()
                    })
        } else {
            PreferencesHelper.setPreferences(this, PreferencesHelper.KEY_FIRST_LAUNCH, false)
            if (!Utils.isLocationEnable(this) || !Utils.isConnectedToInternet(this)) {
                offlineMode = true
                initSetup()
            } else {
                offlineMode = false
                googleApiClient?.reconnect()
                initSetup()
            }
        }
    }

    //todo ogni avvio (tranne la prima volta) carico i dari offline e se è possibile faccio la richiesta in background e carioo i nuovi (si perdono max 4,5 sec)
    //todo aggingere animazioni qundo viene caricato il layout
    private fun initSetup() {
        if (BuildConfig.DEBUG) {
            PreferencesHelper.setPreferences(this, PreferencesHelper.KEY_PREF_PRO_VERSION, true)
        }

        setDrawer()
        setNavigationView()
        setSearchView()
        setSwipeToRefresh()
        if (offlineMode) {
            setViewPager()
            progress_dialog_container.visibility = View.GONE
            SnackBarHelper.offline(this)
        }
        getPreferences()
    }


    private fun getPreferences() {
        prefTheme = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.DEFAULT_PREF_THEME) as String
        prefWeatherProvider = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_WEATHER_PROVIDER, PreferencesHelper.DEFAULT_PREF_WEATHER_PROVIDER) as String
        prefTemp = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_TEMPERATURE, PreferencesHelper.DEFAULT_PREF_TEMPERATURE) as String
        prefSpeed = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_SPEED, PreferencesHelper.DEFAULT_PREF_SPEED) as String
        prefTime = PreferencesHelper.getDefaultPreferences(this, PreferencesHelper.KEY_PREF_TIME, PreferencesHelper.DEFAULT_PREF_TIME) as String
    }


    private fun getData(latitude: Double?, longitude: Double?) {
        realm?.let {
            WeatherRequest.getWeatherData(this, it, latitude, longitude,
                    onSuccess = {
                        progress_dialog_container?.visibility = View.GONE
                        refresh_layout?.isRefreshing = false
                        setViewPager()
                        PreferencesHelper.setPreferences(this, PreferencesHelper.KEY_LAST_UPDATE, System.currentTimeMillis())
                    },
                    onRealmError = {
                        "realm error".log()
                    },
                    onError = {
                        "call error".log()
                        progress_dialog_container?.visibility = View.GONE
                        SnackBarHelper.serverError(this, {
                            progress_dialog_container?.visibility = View.VISIBLE
                            getData(location?.latitude, location?.longitude)
                        })
                        setViewPager()
                    })
        }
    }

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

    private fun searchSuggestion(query: String?) {
        if (searchResult.isNotEmpty()) {
            searchResult.clear()
        }
        MapsGoogleApiClient.service.getPrediction(query.toString()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    if (data?.predictions?.isNotEmpty() ?: false) {
                        data?.predictions?.forEachIndexed { index, data ->
                            if (index in 0..3) {
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

    private fun setViewPager() {
        fragmentAdapter = FragmentAdapter(supportFragmentManager, this)
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

    private fun setSwipeToRefresh() {
        val time = 60000L * 10 // 60000 = 1 minute
        refresh_layout.setOnRefreshListener {
            if ((PreferencesHelper.getPreferences(this, PreferencesHelper.KEY_LAST_UPDATE, 0L) as Long) < Utils.TimeHelper.localTimeMillis - time) {
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
        locationRequest?.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest?.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
            location?.let {
                if (!offlineMode) {
                    getData(location?.latitude, location?.longitude)
                }
                Utils.LocationHelper.getLocationName(location?.latitude ?: 100.0, location?.longitude ?: 100.0, {
                    locationName = it
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

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onLocationChanged(location: Location?) {
        this.location = location
    }
}