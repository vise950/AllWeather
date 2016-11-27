package com.dev.nicola.allweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.dev.nicola.allweather.utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicola on 21/10/2016.
 */

public class InAppPurchaseActivity extends AppCompatActivity {

    final static String TAG = InAppPurchaseActivity.class.getSimpleName();

    private MyBilling mBilling;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setTheme(getApplicationContext());

        setContentView(R.layout.activity_in_app_purchase);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        mBilling = new MyBilling(InAppPurchaseActivity.this);
        mBilling.onCreate();
    }

    @OnClick(R.id.buttonPurchase)
    public void purchaseOnClick() {
        Log.d(TAG, "onClick");

        mBilling.purchaseProVersion();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBilling.onActivityResult(requestCode, resultCode, data);
    }
}
