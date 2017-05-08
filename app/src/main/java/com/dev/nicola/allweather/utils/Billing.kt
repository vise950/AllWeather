package com.dev.nicola.allweather.utils

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.billing.utils.IabHelper
import com.billing.utils.Purchase
import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.R

class Billing(private val activity: Activity) {

    private val base64EncodedPublicKey = BuildConfig.PLAY_STORE_LICENSE_KEY
    private val SKU_PRO_VERSION = "com.nicola.dev.allweather_pro"
    private val RC_REQUEST = 10111
    private val payload = "AllWeatherPro"
    private var mHelper: IabHelper? = null

    fun onCreate() {
        mHelper = IabHelper(activity, base64EncodedPublicKey)
        mHelper?.enableDebugLogging(true)

        mHelper?.startSetup(IabHelper.OnIabSetupFinishedListener { result ->
            if (!result.isSuccess) {
                error("Problem setting up in-app billing: " + result)
                return@OnIabSetupFinishedListener
            }

            if (mHelper == null)
                return@OnIabSetupFinishedListener

            mHelper?.queryInventoryAsync(gotInventoryListener)
        })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Boolean {
        if (mHelper == null) {
            return true
        }
        return mHelper?.handleActivityResult(requestCode, resultCode, data) as Boolean
    }

    fun onDestroy() {
        mHelper?.dispose()
        mHelper = null
    }

    private val gotInventoryListener = IabHelper.QueryInventoryFinishedListener { result, inventory ->
        if (mHelper == null) {
            return@QueryInventoryFinishedListener
        }

        if (result.isFailure) {
            error("Failed to query inventory: " + result)
            return@QueryInventoryFinishedListener
        }

        val purchase = inventory.getPurchase(SKU_PRO_VERSION)
        if (purchase != null && verifyDeveloperPayload(purchase)) {
            PreferencesHelper.setPreferences(activity, PreferencesHelper.KEY_PREF_PRO_VERSION, true)
        } else {
            PreferencesHelper.setPreferences(activity, PreferencesHelper.KEY_PREF_PRO_VERSION, false)
        }
    }

    private val purchaseFinishedListener = IabHelper.OnIabPurchaseFinishedListener { result, purchase ->
        if (mHelper == null) {
            return@OnIabPurchaseFinishedListener
        }

        if (result.isFailure) {
            error("Error purchasing: " + result)
            return@OnIabPurchaseFinishedListener
        }
        if (!verifyDeveloperPayload(purchase)) {
            error("Error purchasing. Authenticity verification failed.")
            return@OnIabPurchaseFinishedListener
        }

        // bought the premium upgrade!
        if (purchase.sku == SKU_PRO_VERSION) {
            PreferencesHelper.setPreferences(activity, PreferencesHelper.KEY_PREF_PRO_VERSION, true)
            //activity.recreate();
        }
    }


    private fun verifyDeveloperPayload(p: Purchase): Boolean {
        val payload = p.developerPayload

        /*
         * TODO: verify that the developer payload of the purchase is correct.
         * It will be the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase
         * and verifying it here might seem like a good approach, but this will
         * fail in the case where the user purchases an item on one device and
         * then uses your app on a different device, because on the other device
         * you will not have access to the random string you originally
         * generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different
         * between them, so that one user's purchase can't be replayed to
         * another user.
         *
         * 2. The payload must be such that you can verify it even when the app
         * wasn't the one who initiated the purchase flow (so that items
         * purchased by the user on one device work on other devices owned by
         * the user).
         *
         * Using your own server to store and verify developer payloads across
         * app installations is recommended.
         */

        return p.sku == SKU_PRO_VERSION
    }

    private fun purchaseProVersion() {
        activity.runOnUiThread {
            mHelper?.launchPurchaseFlow(activity, SKU_PRO_VERSION, RC_REQUEST, purchaseFinishedListener, payload)
        }
    }

//    fun consume(skuName: String) {
//        mHelper?.queryInventoryAsync(true) { result, inventory ->
//            if (inventory.getSkuDetails(skuName) != null) {
//                mHelper?.consumeAsync(inventory.getPurchase(skuName), null)
//            }
//        }
//    }

    private fun error(message: String) {
//        activity.runOnUiThread {
//            Toast.makeText(activity.applicationContext, message, Toast.LENGTH_LONG).show()
//        }
        message.log("in app error")
    }


    fun purchaseDialog() {
        activity.runOnUiThread {
            val dialog = AlertDialog.Builder(activity)
            dialog.setCancelable(false)
            dialog.setTitle(activity.resources.getString(R.string.pro_version_title))
            dialog.setMessage(activity.resources.getString(R.string.pro_version_message))
            dialog.setPositiveButton(activity.resources.getString(R.string.action_OK), { dialog, i ->
                purchaseProVersion()
                dialog.dismiss()
            })
            dialog.setNegativeButton(activity.resources.getString(R.string.action_cancel), { dialog, i ->
                dialog.dismiss()
            })
            dialog.create().show()
        }
    }
}
