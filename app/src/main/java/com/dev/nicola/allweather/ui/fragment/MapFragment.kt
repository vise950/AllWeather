package com.dev.nicola.allweather.ui.fragment

import android.animation.Animator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anadeainc.rxbus.BusProvider
import com.anadeainc.rxbus.Subscribe
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.utils.log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fab_menu.*
import java.net.MalformedURLException
import java.net.URL


class MapFragment : Fragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private val TEMPERATURE_LAYER_URL = "http://maps.owm.io:8099/58c41e2b73e9ac0001bbf883/%d/%d/%d?hash=47393ce8ee71a22f88e377edfd57e950"
    private val CLOUDS_LAYER_URL = "http://maps.owm.io:8099/58c4351573e9ac0001bbf885/%d/%d/%d?hash=47393ce8ee71a22f88e377edfd57e950"
    private val PRECIPITATION_LAYER_URL = "http://maps.owm.io:8099/58c439e373e9ac0001bbf887/%d/%d/%d?hash=47393ce8ee71a22f88e377edfd57e950"
    private var tileOverlay: TileOverlay? = null
//    private val tileProvider = object : UrlTileProvider(256, 256) {
//        override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
//            val s = String.format(OWM_TILE_URL, tileType, zoom, x, y)
//            try {
//                return URL(s)
//            } catch (e: MalformedURLException) {
//                e.printStackTrace().log("Tile exception")
//                return null
//            }
//        }
//    }

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BusProvider.getInstance().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMap()
        setUi()
    }

    override fun onStop() {
        super.onStop()
        BusProvider.getInstance().unregister(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        "onMapReady".log()
        this.map = map
        setMapUi(map!!)
        setMapOverlay(map, TEMPERATURE_LAYER_URL)
    }

    @Subscribe
    fun onEvent(event: Any) {
        event.log("event")
    }

    private fun initializeMap() {
        if (map == null) {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    private fun setMapUi(map: GoogleMap) {
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.uiSettings.isRotateGesturesEnabled = false

//        map.mapType = GoogleMap.MAP_TYPE_NORMAL
//        map.mapType = GoogleMap.MAP_TYPE_HYBRID
//        map.mapType = GoogleMap.MAP_TYPE_SATELLITE
//        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
//        map.mapType = GoogleMap.MAP_TYPE_NONE
//        if (PreferencesUtils.getDefaultPreferences(context, resources.getString(R.string.key_pref_theme), resources.getString(R.string.default_pref_theme)) as String =="dark"){
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context,R.raw.maps_dark_style))
//        }

    }

    private fun setMapOverlay(map: GoogleMap, layerMapType: String) {
        tileOverlay = map.addTileOverlay(TileOverlayOptions().tileProvider(getMapLayer(layerMapType)).transparency(0.5f))
    }

    private fun updateMapOverlay(){
        if (tileOverlay?.isVisible!!){
            tileOverlay?.remove()
            tileOverlay?.clearTileCache()
        }
    }

    private fun setUi() {
        fab_master.setOnClickListener {
            if (!isFabOpen) {
                showFabMenu()
            } else {
                closeFabMenu()
            }
        }

        fab_layout_1.setOnClickListener {
            updateMapOverlay()
            setMapOverlay(map!!, TEMPERATURE_LAYER_URL)
            closeFabMenu()
        }
        fab_layout_2.setOnClickListener {
            updateMapOverlay()
            setMapOverlay(map!!, CLOUDS_LAYER_URL)
            closeFabMenu()
        }
        fab_layout_3.setOnClickListener {
            updateMapOverlay()
            setMapOverlay(map!!, PRECIPITATION_LAYER_URL)
            closeFabMenu()
        }
    }


    private fun showFabMenu() {
        isFabOpen = true
        fab_layout_1.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fab_layout_2.animate().translationY(-resources.getDimension(R.dimen.standard_100))
        fab_layout_3.animate().translationY(-resources.getDimension(R.dimen.standard_145))
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(p0: Animator?) {
                        fab_title_1.visibility = View.VISIBLE
                        fab_title_2.visibility = View.VISIBLE
                        fab_title_3.visibility = View.VISIBLE
                    }

                    override fun onAnimationRepeat(p0: Animator?) {}

                    override fun onAnimationCancel(p0: Animator?) {}

                    override fun onAnimationStart(anim: Animator?) {
                        fab_layout_1.visibility = View.VISIBLE
                        fab_layout_2.visibility = View.VISIBLE
                        fab_layout_3.visibility = View.VISIBLE
                    }

                })
    }

    private fun closeFabMenu() {
        isFabOpen = false
        fab_layout_1.animate().translationY(0f)
        fab_layout_2.animate().translationY(0f)
        fab_layout_3.animate().translationY(0f).setListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                fab_layout_1.visibility = View.GONE
                fab_layout_2.visibility = View.GONE
                fab_layout_3.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {
                fab_title_1.visibility = View.GONE
                fab_title_2.visibility = View.GONE
                fab_title_3.visibility = View.GONE
            }
        })

    }

    private fun getMapLayer(layerUrl: String): TileProvider {
        val tileProvider = object : UrlTileProvider(256, 256) {
            override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
                val s = String.format(layerUrl, zoom, x, y)
//                if (!checkTileExists(x, y, zoom)) {
//                    return null
//                }

                try {
                    URL(s).log("url")
                    return URL(s)
                } catch (e: MalformedURLException) {
                    e.printStackTrace().log("Tile exception")
                    return null
                }
            }

//            private fun  checkTileExists(x: Int, y: Int, zoom: Int): Boolean {
//                val  minZoom = 12
//                val maxZoom = 16
//
//                if ((zoom < minZoom || zoom > maxZoom)) {
//                    return false
//                }
//                return true
//            }
        }
        return tileProvider
    }

}
