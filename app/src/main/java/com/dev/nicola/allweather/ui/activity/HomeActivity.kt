package com.dev.nicola.allweather.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.adapter.FavoritePlaceAdapter
import com.dev.nicola.allweather.base.BaseActivity
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.util.getName
import com.dev.nicola.allweather.util.layoutAnimation
import com.dev.nicola.allweather.util.uuid
import com.ewt.nicola.common.extension.log
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_place.*


class HomeActivity : BaseActivity(R.layout.activity_home, R.menu.main_menu) {

    companion object {
        const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 563
        const val PLACE_ID = "placeId"
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private val placeAutocompleteIntent by lazy {
        PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                .setFilter(placeAutocompleteFilter)
                .build(this)
    }

    private val placeAutocompleteFilter by lazy {
        AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build()
    }

    private var favoritePlace: List<FavoritePlace>? = null
    private lateinit var placeAdapter: FavoritePlaceAdapter
    private var actionMode: ActionMode? = null
    private val actionModeCallback = ActionModeCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo first card is position

        getLastKnowPosition()
        initBilling()
        initUI()
        observeData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = PlaceAutocomplete.getPlace(this, data)
                    placeViewModel.addPlace(FavoritePlace().apply {
                        id = place.id
                        name = place.name.toString()
                        latitude = place.latLng.latitude
                        longitude = place.latLng.longitude
                    })
                }
                PlaceAutocomplete.RESULT_ERROR -> {
                    val status = PlaceAutocomplete.getStatus(this, data)
                    //todo
                    status.log("place autocomplete error")
                    Snackbar.make(root_view, status.statusMessage.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initUI() {
        add_place_fab.setOnClickListener {
            searchPlace()
            actionMode?.finish()
        }
        initRecycler()
    }

    private fun initRecycler() {
        placeAdapter = FavoritePlaceAdapter()
        favorite_places_rv.layoutManager = LinearLayoutManager(this)
        favorite_places_rv.adapter = placeAdapter
        favorite_places_rv.layoutAnimation()

        placeAdapter.onItemClicked = { gotoPlace(it) }

        placeAdapter.onItemLongClicked = {
            if (placeAdapter.selectedItem.size > 0) {
                placeAdapter.isActionModeActive = true
                if (actionMode == null) actionMode = startSupportActionMode(actionModeCallback)
                actionMode?.title = placeAdapter.selectedItem.size.toString()
            } else {
                actionMode?.finish()
            }
        }
    }

    private fun searchPlace() {
        try {
            startActivityForResult(placeAutocompleteIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace().log("error")
            Snackbar.make(root_view, "Errore", Snackbar.LENGTH_LONG).show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace().log("error")
            Snackbar.make(root_view, "Errore", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeData() {
        placeViewModel.getPlaces().observe(this, Observer {
            it?.let {
                favoritePlace = it
                placeAdapter.updateData(it)
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnowPosition() {
        //todo persmission
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    //todo add location on first position of list
                    FavoritePlace().apply {
                        id = uuid()
                        name = location?.getName(this@HomeActivity)
                        latitude = location?.latitude
                        longitude = location?.longitude
                    }.also {
                        placeViewModel.addPlace(it)
                        "add last know location".log()
                    }
                }
    }

    //todo snackbar for undo deleted places
//    private fun handleRemovePlace() {
//        favoritePlace?.let { placeAdapter.fakeUpdateData(it) }
//        Snackbar.make(root_view, "${placeAdapter.selectedItem.size} deleted", Snackbar.LENGTH_LONG)
//                .setAction("UNDO", {
//                    placeAdapter.notifyDataSetChanged()
//                })
//                .addCallback(object : Snackbar.Callback() {
//                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
//                        super.onDismissed(transientBottomBar, event)
//                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
//                            "snackbar dismissed".error()
//                            removePlace()
//                        }
//                    }
//                }).show()
//    }

    private fun gotoPlace(placeId: String) {
        val intent = Intent(this, WeatherPlaceActivity::class.java).putExtra(PLACE_ID, placeId)
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, place_weather_bg, place_weather_bg.transitionName).let {
            startActivity(intent, it.toBundle())
        }
    }

    private fun removePlace() {
        placeAdapter.selectedItem.log("selected item")
        placeViewModel.removePlace(placeAdapter.selectedItem)
    }

    private fun setStatusBarColor(isActionMode: Boolean) {
        window.statusBarColor = ContextCompat.getColor(this, if (isActionMode) R.color.action_mode else R.color.primary_dark)
    }

    inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.favorite_place_action, menu)
            setStatusBarColor(true)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            setStatusBarColor(false)
            placeAdapter.clearSelection()
            actionMode = null
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_delete -> {
                    removePlace()
//                    handleRemovePlace()
                    mode?.finish()
                    true
                }
                else -> false
            }
        }
    }
}