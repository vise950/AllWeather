package com.dev.nicola.allweather;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.billing.utils.IabHelper;
import com.billing.utils.IabResult;
import com.billing.utils.Inventory;
import com.billing.utils.Purchase;
import com.dev.nicola.allweather.utils.PreferencesUtils;

/**
 * Created by Nicola on 27/09/2016.
 */

public class MyBilling {

    // Debug tag, for logging
    private static final String TAG = MyBilling.class.getSimpleName();

    private static final String SKU_PRO_VERSION = "com.nicola.dev.allweather_pro";

    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10111;

    private Activity activity;

    // The helper object
    private IabHelper mHelper;

    private String base64EncodedPublicKey = BuildConfig.APPLICATION_API_KEY;
    private Boolean isProVersion = false;
    private String payload = "AllWeatherPro";
    // Listener that's called when we finish querying the items and
    // subscriptions we own
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null)
                return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase purchase = inventory.getPurchase(SKU_PRO_VERSION);
//            Constants.isAdsDisabled = (removeAdsPurchase != null && verifyDeveloperPayload(removeAdsPurchase));
            if (purchase != null && verifyDeveloperPayload(purchase))
                setIsProVersion();
        }
    };
    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_PRO_VERSION)) {
                // bought the premium upgrade!
                setIsProVersion();
                activity.recreate();

//                Intent intent = activity.getIntent();
//                activity.finish();
//                activity.startActivity(intent);
            }
        }
    };

    public MyBilling(Activity launcher) {
        this.activity = launcher;
    }

    public void onCreate() {

        // Create the helper, passing it our context and the public key to
        // verify signatures with
        mHelper = new IabHelper(activity, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set
        // this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed off in the meantime? If so, quit.
                if (mHelper == null)
                    return;

                // IAB is fully set up. Now, let's get an inventory of stuff we
                // own.
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper == null)
            return true;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            return false;
        } else
            return true;
    }

    public void onDestroy() {
        // very important:
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    private boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

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

        if (p.getSku().equals(SKU_PRO_VERSION))
            return true;
        else
            return false;
    }

    // User clicked the "Remove Ads" button.
    public void purchaseProVersion() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHelper.launchPurchaseFlow(activity, SKU_PRO_VERSION, RC_REQUEST, mPurchaseFinishedListener, payload);
            }
        });
    }

    public void consume(final String skuName) {
        mHelper.queryInventoryAsync(true, new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                if (inventory.getSkuDetails(skuName) != null) {
                    mHelper.consumeAsync(inventory.getPurchase(skuName), null);
                }
            }
        });
    }


    private void setIsProVersion() {
        isProVersion = true;
        PreferencesUtils.setPreferences(activity.getApplicationContext(), "isProVersion", isProVersion);
    }


    // message for error action
    private void complain(final String message) {
//        if (message.contains("7"))
//            consume(SKU_PRO_VERSION);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // dialog called in main for upgrade to pro version
    // pass text for create dialog (title,message,text positive and negative button)
    public void purchaseDialog(final String title, final String message, final String positive, final String negative) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setCancelable(false);
                dialog.setTitle(title);
                dialog.setMessage(message);
                dialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        purchaseProVersion();
                    }
                });
                dialog.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.create().show();
            }
        });
    }

}
