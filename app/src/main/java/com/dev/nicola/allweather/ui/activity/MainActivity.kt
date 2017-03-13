package com.dev.nicola.allweather.ui.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.anadeainc.rxbus.Bus
import com.anadeainc.rxbus.BusProvider
import com.arlib.floatingsearchview.FloatingSearchView
import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.model.Prediction
import com.dev.nicola.allweather.preferences.SettingActivity
import com.dev.nicola.allweather.service.PredictionClient
import com.dev.nicola.allweather.ui.fragment.FavouriteFragment
import com.dev.nicola.allweather.ui.fragment.LocationFragment
import com.dev.nicola.allweather.ui.fragment.MapFragment
import com.dev.nicola.allweather.utils.*
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootData
import com.dev.nicola.allweather.weatherProvider.DarkSky.service.DarkSkyClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    companion object {
        internal val TAG = MainActivity::class.java.simpleName
    }

    private val DEBUG_MODE = BuildConfig.DEBUG
    private val DEFAULT_ITEM = 1

    private var prefTheme: String? = null
    private var prefTemperature: String? = null
    private var prefSpeed: String? = null
    private var prefTime: String? = null
    private var prefProvider: String? = null
    private var goToSetting = false

    private val searchResult: ArrayList<PredictionResult> = ArrayList()

    private var googleApiClient: GoogleApiClient? = null
    private var location: Location? = null
    private var locationName: String? = null
    private var locationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    private var locationPermissionGranted: Boolean = false

    private var realm: Realm? = null
    private var bus: Bus? = null

    private var fragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    private var transaction: FragmentTransaction? = null

    private var billing: MyBilling? = null

    private var search_view: FloatingSearchView by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.changeTheme(this)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        bus=BusProvider.getInstance()

        buildGoogleClient()
        googleApiClient?.connect()

        billing = MyBilling(this)
        billing!!.onCreate()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        billing!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        if (googleApiClient!!.isConnected) {
            getDeviceLocation()
        }

        initialSetup()
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
            googleApiClient?.disconnect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (billing != null) {
            billing!!.onDestroy()
        }

        realm?.close()
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


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            SnackbarUtils.showSnackbar(this, coordinator_layout, 1)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    private fun initialSetup() {
        Log.d(TAG, "initialSetup")

        progress.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))
        progress.visibility = View.VISIBLE

        //
        //        if (DEBUG_MODE) { //se sono in debug l'app è pro
        //            PreferencesUtils.INSTANCE.setPreferences(mContext, getResources().getString(R.string.key_pro_version), true);
        //        }

        setDrawer()
        setNavigationView()
        setSearchView()
        setBottomNavigationView()
//        getData()
        loadUi()
    }


    private fun getData() {
        val service = DarkSkyClient.service
        val call = service.getDarkSkyData(location!!.latitude, location!!.longitude, "us", Locale.getDefault().language)

        call.enqueue(object : Callback<RootData> {
            override fun onResponse(call: Call<RootData>, response: Response<RootData>) {
                call.request().url().log()
                if (response.isSuccessful) {
                    progress.visibility = View.GONE
                    loadUi()
                }
            }

            override fun onFailure(call: Call<RootData>, t: Throwable) {
                Log.e(TAG, "onFailure " + t.toString())
                progress.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, android.R.anim.fade_out))
                progress.visibility = View.GONE
            }
        })
    }


    /**
     * - NEVER commit() transactions after onPause() on pre-Honeycomb, and onStop() on post-Honeycomb
     * - Be careful when committing transactions inside Activity lifecycle methods. Use onCreate(), onResumeFragments() and onPostResume()
     * - Avoid performing transactions inside asynchronous callback methods
     * - Use commitAllowingStateLoss() only as a last resort
     */
    @SuppressLint("CommitTransaction")
    private fun loadUi() {
        fragmentManager = supportFragmentManager
        fragment = LocationFragment()
        transaction = fragmentManager!!.beginTransaction()
        transaction!!.add(R.id.main_container, fragment).commitAllowingStateLoss()
        //todo crash with dark theme (i don't know why XD)
    }


    private fun getPreferences() {
        prefTheme = PreferencesUtils.getDefaultPreferences(this, resources.getString(R.string.key_pref_theme), resources.getString(R.string.default_pref_theme)) as String
        prefProvider = PreferencesUtils.getDefaultPreferences(this, resources.getString(R.string.key_pref_provider), resources.getString(R.string.default_pref_provider)) as String
        prefTemperature = PreferencesUtils.getDefaultPreferences(this, resources.getString(R.string.key_pref_temperature), resources.getString(R.string.default_pref_temperature)) as String
        prefSpeed = PreferencesUtils.getDefaultPreferences(this, resources.getString(R.string.key_pref_speed), resources.getString(R.string.default_pref_speed)) as String
        prefTime = PreferencesUtils.getDefaultPreferences(this, resources.getString(R.string.key_pref_time), resources.getString(R.string.default_pref_time)) as String
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
            //todo metodo provvisorio per il lag in chiusura del drawer
            Handler().postDelayed({
                when (it.itemId) {
                    R.id.drawer_item_settings -> {
                        startActivity(Intent(this, SettingActivity::class.java))
                        goToSetting = true
                    }

                    R.id.drawer_item_pro_version -> {
//                    if (!Utils.isProVersion(this)) {
//                        SnackbarUtils.showSnackbar(this, bottom_navigation, 6)
//                    } else {
                        //dialog
//                    }
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

        search_view = findViewById(R.id.search_view_light) as FloatingSearchView
//        search_view=findViewById(R.id.search_view_dark)as FloatingSearchView

        search_view.attachNavigationDrawerToMenuButton(drawer_layout)

        //todo setTheme

        search_view.setOnMenuItemClickListener {
            val id = it.itemId

            when (id) {
                R.id.action_favorite -> Toast.makeText(this@MainActivity, "Save to favorite place", Toast.LENGTH_SHORT).show()
            }
        }

        //fixme focus not work
        search_view.setOnFocusChangeListener { v, focus ->
            Log.e(TAG, "focus " + focus)
            Log.e(TAG, "search query " + search_view.query)
        }

        search_view.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != newQuery && newQuery.trim() != "" && newQuery.length > 2) {
                searchSuggestion(newQuery)
                search_view.showProgress()
            } else {
                search_view.clearSuggestions()
            }
        }

        search_view.setOnBindSuggestionCallback { suggestionView, leftIcon, textView, item, itemPosition ->
            if (item.body == "No suggestion" || item.body == "Fail to load suggestion") {
                leftIcon.setImageResource(R.drawable.ic_error_outline)
                suggestionView.setOnClickListener {
                    search_view.clearSuggestions()
                    search_view.clearQuery()
                    search_view.clearSearchFocus()
                }
            } else {
                leftIcon.setImageResource(R.drawable.ic_location_grey)
                suggestionView.setOnClickListener {
                    textView.text.log("searchView")
                    search_view.setSearchText(textView.text)
//                    search_view.clearSuggestions()
//                    search_view.clearQuery()
                    search_view.clearSearchFocus()
//                    if(RxBus().hasObservers()){
//                        RxBus().send(textView.text)
//                        "send event".log()
//                    }
                    bus!!.post(textView.text.toString())
                }
            }
        }
    }

    private fun searchSuggestion(query: String) {
        if (searchResult.isNotEmpty()) {
            searchResult.clear()
        }

        PredictionClient.service.getPrediction(query).enqueue(object : Callback<Prediction> {
            override fun onResponse(call: Call<Prediction>, response: Response<Prediction>) {
                if (response.isSuccessful && response.body().status.equals("OK")) {
                    (0..response.body().predictions!!.size - 1).mapTo(searchResult) {
                        PredictionResult(Utils.trimString(response.body().predictions?.get(it)?.description!!))
                    }
                }

                if (searchResult.isEmpty()) {
                    searchResult.add(PredictionResult("No suggestion"))
                }
                search_view.swapSuggestions(searchResult)
                search_view.hideProgress()
            }


            override fun onFailure(call: Call<Prediction>, t: Throwable) {
                searchResult.add(PredictionResult("Fail to load suggestion"))
                search_view.swapSuggestions(searchResult)
                search_view.hideProgress()
            }
        })
    }


    /**
     *  Bottom Navigation
     */
    @SuppressLint("NewApi", "CommitTransaction")
    private fun setBottomNavigationView() {

        bottom_navigation.menu.getItem(DEFAULT_ITEM).isChecked = true

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId

            when (id) {
                R.id.action_favorites -> {
//                    circularReveal('l', R.color.test1)
                    fragment = FavouriteFragment()
                }

                R.id.action_location -> {
//                    circularReveal('c', R.color.test2)
                    fragment = LocationFragment()
                }

                R.id.action_map -> {
//                    circularReveal('r', R.color.test3)
                    fragment = MapFragment()
                }
            }
            transaction = fragmentManager!!.beginTransaction()
            transaction!!.replace(R.id.main_container, fragment).commit()
            true
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    internal fun circularReveal(position: Char, toColor: Int) {
        var x = 0
        var y = 0

        when (position) {
            'l' -> {
                x = bottom_navigation.measuredWidth / 6
                y = bottom_navigation.measuredHeight / 2
            }
            'c' -> {
                x = bottom_navigation.measuredWidth / 2
                y = bottom_navigation.measuredHeight / 2
            }
            'r' -> {
                x = bottom_navigation.measuredWidth - bottom_navigation.measuredWidth / 6
                y = bottom_navigation.measuredHeight / 2
            }
        }

        val finalRadius = Math.max(bottom_navigation.width, bottom_navigation.height)
        val anim = ViewAnimationUtils.createCircularReveal(bottom_navigation, x, y, 0f, finalRadius.toFloat())
        bottom_navigation.itemBackgroundResource = toColor
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.duration = 800
        anim.start()
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
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 123)
        }
        if (locationPermissionGranted) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
            if (location != null) {
                locationName = LocationUtils.getLocationName(this, location!!.latitude, location!!.longitude)
                Log.e(TAG, "location name " + locationName)
                if (locationName != null || locationName != "Not Found") {
                    search_view.setSearchText(locationName)
                }
            }
        }
    }

    override fun onConnected(bundle: Bundle?) {
        getDeviceLocation()
//        getData()
    }

    override fun onConnectionSuspended(i: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

    override fun onLocationChanged(location: Location?) {
        this.location = location
    }
}
