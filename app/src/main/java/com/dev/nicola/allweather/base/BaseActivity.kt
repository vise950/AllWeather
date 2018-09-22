package com.dev.nicola.allweather.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import co.eggon.eggoid.extension.error
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.ui.activity.SettingsActivity
import com.dev.nicola.allweather.util.ALL_WEATHER_PRO_SKU
import com.dev.nicola.allweather.util.goto
import com.dev.nicola.allweather.viewmodel.FavoritePlaceViewModel
import com.dev.nicola.allweather.viewmodel.WeatherViewModel
import com.dev.nicola.allweather.viewmodel.viewModel
import io.realm.Realm

abstract class BaseActivity(@LayoutRes private val layoutRes: Int? = null,
                            @MenuRes private val menuRes: Int? = null,
                            private val showBackArrow: Boolean = false) : AppCompatActivity() {

    val realm: Realm by lazy { Init.getRealmInstance() }

    val placeViewModel by lazy { this.viewModel { FavoritePlaceViewModel(application) } }
    val weatherViewModel by lazy { this.viewModel { WeatherViewModel(application) } }

    lateinit var billingClient: BillingClient
    var skuMap = HashMap<String, List<String>>()
    private val flowParams by lazy { BillingFlowParams.newBuilder() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }

        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        menuRes?.let {
            menuInflater.inflate(it, menu)
        } ?: run {
            super.onCreateOptionsMenu(menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_settings -> goto<SettingsActivity>()
            R.id.action_unlock_pro -> checkIsPro()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        billingClient.endConnection()
    }

    fun initBilling() {
        billingClient = BillingClient.newBuilder(this)
                .setListener { responseCode, purchases ->
                    when (responseCode) {
                        BillingClient.BillingResponse.OK -> {
                            "purchase update OK".error()
                        }
                        BillingClient.BillingResponse.USER_CANCELED -> {
                            "purchase update USER CANCELED".error()
                        }
                    }
                }.build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    "start connection OK".error()
                    skuMap[BillingClient.SkuType.INAPP] = listOf(ALL_WEATHER_PRO_SKU)
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                "disconnected billing client".error()
            }
        })
    }

    private fun checkIsPro() {
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP) { responseCode, purchasesList ->
            when (responseCode) {
                BillingClient.BillingResponse.OK -> {
                    purchasesList?.let {
                        if (it.isNotEmpty()) {
                            it.firstOrNull()?.let {
                                if (it.sku == ALL_WEATHER_PRO_SKU) {
                                    "purchase pro".error()
                                    //todo alert already pro
                                }
                            }
                        } else {
                            "no purchase".error()
                            unlockPro()
                        }
                    }
                }
            }
        }
    }

    private fun unlockPro() {
        flowParams.setSku(ALL_WEATHER_PRO_SKU)
        flowParams.setType(BillingClient.SkuType.INAPP)
        billingClient.launchBillingFlow(this, flowParams.build())
    }
}