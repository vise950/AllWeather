package com.dev.nicola.allweather.util

import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.ewt.nicola.common.extension.log

class BillingUtil(private val activity: AppCompatActivity) {

    private var billingClient: BillingClient? = null
    private var skuMap = HashMap<String, List<String>>()
    private val flowParams by lazy { BillingFlowParams.newBuilder() }

    private fun unlockPro() {
        flowParams.setSku(ALL_WEATHER_PRO_SKU)
        flowParams.setType(BillingClient.SkuType.INAPP)
        billingClient?.launchBillingFlow(activity, flowParams.build())
    }


    fun initBilling() {
        billingClient = BillingClient.newBuilder(activity)
                .setListener { responseCode, purchases ->
                    when (responseCode) {
                        BillingClient.BillingResponse.OK -> {
                            "purchase update OK".log()
                        }
                        BillingClient.BillingResponse.USER_CANCELED -> {
                            "purchase update USER CANCELED".log()
                        }
                    }
                }.build()

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    "start connection OK".log()
                    skuMap[BillingClient.SkuType.INAPP] = listOf(ALL_WEATHER_PRO_SKU)
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                "disconnected billing client".log()
            }
        })
    }

    fun checkIsPro() {
        billingClient?.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP) { responseCode, purchasesList ->
            when (responseCode) {
                BillingClient.BillingResponse.OK -> {
                    purchasesList?.let {
                        if (it.isNotEmpty()) {
                            it.firstOrNull()?.let {
                                if (it.sku == ALL_WEATHER_PRO_SKU) {
                                    "purchase pro".log()
                                    //todo alert already pro
                                }
                            }
                        } else {
                            "no purchase".log()
                            unlockPro()
                        }
                    }
                }
            }
        }
    }

    fun destroy() {
        billingClient?.endConnection()
    }
}