package com.dev.nicola.allweather;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.dev.nicola.allweather.callback.AsyncTaskCompleteListener;
import com.dev.nicola.allweather.utils.LocationIP;
import com.dev.nicola.allweather.utils.LocationUtils;
import com.dev.nicola.allweather.utils.PreferencesUtils;

import org.json.JSONObject;

/**
 * Created by Nicola on 20/10/2016.
 */

public class MyAsyncTask extends AsyncTask<Location, Void, Void> {

    private final static String TAG = MyAsyncTask.class.getSimpleName();

    private Context mContext;
    private String prefProvider;
    private LocationIP mLocationIP;
    private String place;
    private double latitude;
    private double longitude;
    private JSONObject mJSONObject;

    private AsyncTaskCompleteListener mListener;

    public MyAsyncTask(Context context, AsyncTaskCompleteListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        prefProvider = PreferencesUtils.getDefaultPreferences(mContext, mContext.getString(R.string.key_pref_provider), mContext.getString(R.string.default_pref_provider));
        latitude = PreferencesUtils.getPreferences(mContext, "lastLatitude", 0F);
        longitude = PreferencesUtils.getPreferences(mContext, "lastLongitude", 0F);
        mLocationIP = new LocationIP();
    }


    @Override
    protected Void doInBackground(Location... locations) {

        //if first run fai queste cose altrimenti prendi da shared le info (place,latitude,longitude)

        if (locations[0] != null) {
            Log.d(TAG, "location not null");
            latitude = locations[0].getLatitude();
            longitude = locations[0].getLongitude();
            place = LocationUtils.getLocationName(mContext, latitude, longitude);
            mJSONObject = ProviderData.getProviderData(prefProvider, latitude, longitude, place);
//        }if (latitude!=0 && longitude!=0){
//            place= LocationUtils.getLocationName(mContext,latitude,longitude);
//            mJSONObject = ProviderData.getProviderData(prefProvider, latitude,longitude,place);
        } else {
            Log.d(TAG, "location null, use ip");
            String ip = mLocationIP.getExternalIP();
            mLocationIP.getLocation(ip);
            latitude = mLocationIP.getLatitude();
            longitude = mLocationIP.getLongitude();
            place = LocationUtils.getLocationName(mContext, latitude, longitude);
            Log.d(TAG, "place " + place);
            mJSONObject = ProviderData.getProviderData(prefProvider, latitude, longitude, place);
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void v) {
        if (mJSONObject != null) {
            PreferencesUtils.setPreferences(mContext, "lastJSONObject", mJSONObject.toString());
            PreferencesUtils.setPreferences(mContext, "lastPlace", place);
            PreferencesUtils.setPreferences(mContext, "lastLatitude", ((float) latitude));
            PreferencesUtils.setPreferences(mContext, "lastLongitude", ((float) longitude));
            PreferencesUtils.setPreferences(mContext, "lastRefreshData", System.currentTimeMillis());
            mListener.onTaskComplete();
        }

    }
}