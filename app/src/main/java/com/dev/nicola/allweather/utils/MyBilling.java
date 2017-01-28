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

    private static final String TAG = MyBilling.class.getSimpleName();

    private static String base64EncodedPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhnW+of4DcU6dwUlAw1ktvtkxKEo3ccbw4FAToMLu4RdePZ0Krwn3C/yKRfr8q8gQKTnHNKZ1py6S/loY/Ez5lJriB1cPJR+7eNDtHpmRXSVhrCRl3UxaAtgU9olaAXRhEseiVJS23RwLtWajji04BJWQk3cfXwZFhV2I1/L/qUnxtKIK1SuWEZWmbYnZujb/LRopD2MYrj9aSVxJNLSU7OKgun3wtRDjc0PAa16do+h0a6NKUsImxwW04LZct/A91jePSDqVvu4orVfdLUlAfAPal34uTmD8ITJKParrR5kWosoGeDiCyudnCTLzyB5X+5bwNgrvoEsxFBbM+o8C4wIDAQAB";
//    private String base64EncodedPublicKey = BuildConfig.LICENSE_API_KEY;
    private static final String SKU_PRO_VERSION = "com.nicola.dev.allweather_pro";
    private String payload = "AllWeatherPro";
    private Boolean isProVersion = false;

    private static final int RC_REQUEST = 10111;

    private Activity activity;

    private IabHelper mHelper;

    // Listener that's called when we finish querying the items and subscriptions we own
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (mHelper == null)
                return;

            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }


            // Do we have the premium upgrade?
            Purchase purchase = inventory.getPurchase(SKU_PRO_VERSION);
            if (purchase != null && verifyDeveloperPayload(purchase))
                setIsProVersion();
            else
                setIsNotProVersion();

        }
    };

    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
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
//                activity.recreate();
            }
        }
    };

    public MyBilling(Activity launcher) {
        this.activity = launcher;
    }

    public void onCreate() {

        mHelper = new IabHelper(activity, base64EncodedPublicKey);

        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                if (mHelper == null)
                    return;

                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper == null)
            return true;

        return mHelper.handleActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
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

        return p.getSku().equals(SKU_PRO_VERSION);
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
        PreferencesUtils.INSTANCE.setPreferences(activity.getApplicationContext(), "isProVersion", isProVersion);
    }

    private void setIsNotProVersion() {
        isProVersion = false;
        PreferencesUtils.INSTANCE.setPreferences(activity.getApplicationContext(), "isProVersion", isProVersion);
    }


    // message for error action
    private void complain(final String message) {
//        if (message.contains("7"))
//            consume(SKU_PRO_VERSION);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
