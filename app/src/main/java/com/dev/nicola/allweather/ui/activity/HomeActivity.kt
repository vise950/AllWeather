package com.dev.nicola.allweather.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import com.dev.nicola.allweather.util.*
import com.ewt.nicola.common.extension.log
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_place.*


class HomeActivity : BaseActivity() {

    private val locationUtil by lazy { LocationUtil(this) }
    private val billingUtil by lazy { BillingUtil(this) }
    private val placeAutocompleteUtil by lazy { PlaceAutocompleteUtil(this) }

    private var favoritePlace: List<FavoritePlace>? = null
    private lateinit var placeAdapter: FavoritePlaceAdapter
    private var actionMode: ActionMode? = null

    private val actionModeCallback = ActionModeCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        billingUtil.destroy()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                locationUtil.getLastKnowPosition()
            } else {
                LOCATION_PERMISSION.requestPermission(this, LOCATION_PERMISSION_CODE)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> goto<SettingsActivity>()
            R.id.action_unlock_pro -> billingUtil.checkIsPro()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        locationUtil.getLastKnowPosition()
        billingUtil.initBilling()
        initUI()
        observeData()
    }

    private fun initUI() {
        add_place_fab.setOnClickListener {
            placeAutocompleteUtil.gotoSearchPlace()
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

    private fun observeData() {
        placeViewModel.getPlaces().observe(this, Observer {
            it?.let {
                favoritePlace = it
                placeAdapter.updateData(it)
            }
        })
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