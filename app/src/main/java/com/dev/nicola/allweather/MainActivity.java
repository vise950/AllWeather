package com.dev.nicola.allweather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dev.nicola.allweather.adapter.FragmentAdapter;
import com.dev.nicola.allweather.utils.LocationUtils;
import com.dev.nicola.allweather.utils.PermissionUtils;
import com.dev.nicola.allweather.utils.PlaceAutocomplete;
import com.dev.nicola.allweather.utils.PreferencesUtils;
import com.dev.nicola.allweather.utils.SnackbarUtils;
import com.dev.nicola.allweather.utils.Utils;
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootData;
import com.dev.nicola.allweather.weatherProvider.DarkSky.service.DarkSkyClient;
import com.dev.nicola.allweather.weatherProvider.DarkSky.service.DarkSkyService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final static String TAG = MainActivity.class.getSimpleName();

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    final private boolean DEBUG_MODE = BuildConfig.DEBUG;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.placeholder_no_connection)
    View placeholderNoConnection;
    @BindView(R.id.placeholder_no_data)
    View placeholderNoData;
    @BindView(R.id.progress_dialog_container)
    View mDialog;

    private Context mContext;
    private boolean firstRun = false;

    private String prefTheme;
    private String prefTemperature;
    private String prefSpeed;
    private String prefTime;
    private String prefProvider;
    private boolean prefUseGps;
    private boolean goToSetting = false;

    private PlaceAutocomplete mPlaceAutocomplete;
    private List<SearchItem> suggestionsList;
    private SearchAdapter mSearchAdapter;
    private SearchHistoryTable mHistoryDatabase;
    private String firstSuggestion = "";
    private boolean searchViewItemClick = false;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private com.dev.nicola.allweather.MyBilling mBilling;

//    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        Utils.setTheme(mContext);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
//        mRealm=Realm.getDefaultInstance();
        buildGoogleApiClient();

//        mBilling = new com.dev.nicola.allweather.MyBilling(MainActivity.this);
//        mBilling.onCreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBilling.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        mGoogleApiClient.connect();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        if (!Utils.checkInternetConnection(mContext)) {
            placeholderNoConnection.setVisibility(View.VISIBLE);
        } else if (!Utils.checkGpsEnable(mContext)) {
            SnackbarUtils.showSnackbar(MainActivity.this, mCoordinatorLayout, 2);
        } else if (!PermissionUtils.isPermissionGranted(mContext)) {
            SnackbarUtils.showSnackbar(MainActivity.this, mCoordinatorLayout, 1);
        } else if (!firstRun) {
            initialSetup();
            firstRun = true;
        }

        if (goToSetting) {
            checkPreferences();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

        if (mDialog != null && mDialog.getVisibility() == View.VISIBLE) {
            mDialog.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBilling != null) {
            mBilling.onDestroy();
        }

        if (mDialog != null && mDialog.getVisibility() == View.VISIBLE) {
            mDialog.setVisibility(View.GONE);
        }

//        mRealm.close();

        firstRun = false;
    }


    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mSearchView != null && mSearchView.isSearchOpen()) {
            mSearchView.close(true);
        } else {
            super.onBackPressed();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // Permission DENIED
                    SnackbarUtils.showSnackbar(MainActivity.this, mCoordinatorLayout, 1);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @OnClick(R.id.button_retry_no_connection)
    public void retryNoConnection() {
        recreate();
    }

    @OnClick(R.id.button_retry_no_data)
    public void retryNoData() {
    }


    /**
     * asynctask callback
     */
//    @Override
//    public void onTaskComplete() {
//        Log.d(TAG, "TASK COMPLETE");
//        if ((PreferencesUtils.INSTANCE.getPreferences(mContext, "lastJSONObject", null)) == null) {
//            placeholderNoData.setVisibility(View.VISIBLE);
//        } else {
//            setViewPager();
//            String place = PreferencesUtils.INSTANCE.getPreferences(mContext, "lastPlace", null);
//            if (place != null) {
//                mSearchView.setTextOnly(place);
//            }
//        }
//
//        if (mDialog != null && mDialog.getVisibility() == View.VISIBLE) {
//            mDialog.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
//            mViewPager.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
//            mDialog.setVisibility(View.GONE);
//            mViewPager.setVisibility(View.VISIBLE);
//        }
//    }

    private void checkPreferences() {
        if (!prefTheme.equals(PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme))) ||
                !prefProvider.equals(PreferencesUtils.INSTANCE.getPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider)))) {
            prefTheme = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme));
            prefProvider = PreferencesUtils.INSTANCE.getPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));
            recreate();
        }
//
//        // FIXME: 09/09/2016 se cambio tema e provider crasha
        else if (!prefTemperature.equals(PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_temperature), getResources().getString(R.string.default_pref_temperature))) ||
                !prefSpeed.equals(PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_speed), getResources().getString(R.string.default_pref_speed))) ||
                !prefTime.equals(PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_time), getResources().getString(R.string.default_pref_time))) ||
                !prefProvider.equals(PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider)))) {

            prefProvider = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));
            prefTemperature = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_temperature), getResources().getString(R.string.default_pref_temperature));
            prefSpeed = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_speed), getResources().getString(R.string.default_pref_speed));
            prefTime = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_time), getResources().getString(R.string.default_pref_time));

            if (mDialog != null && mDialog.getVisibility() == View.GONE) {
                mViewPager.setVisibility(View.GONE);
                mDialog.setVisibility(View.VISIBLE);
            }
//            new MyAsyncTask(mContext, MainActivity.this).execute(mLocation);
        } else if (prefUseGps != PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_use_gps), false)) {
            prefUseGps = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_use_gps), false);
            MainActivity.this.onResume();
        }

    }


    private void initialSetup() {
        Log.d(TAG, "initialSetup");

        if (mDialog != null) {
            mDialog.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
            mDialog.setVisibility(View.VISIBLE);
        }

        getPreferences();

//        if (prefUseGps) {
//            mLocationGPS = new LocationGPS(mContext);
//            mLocation = mLocationGPS.getLocation();
//        }


        if (DEBUG_MODE) { //se sono in debug l'app è pro
            PreferencesUtils.INSTANCE.setPreferences(mContext, getResources().getString(R.string.key_pro_version), true);
        }

        setDrawer();
        setNavigationView();
        setSearchView();
        getData();
    }


    private void getData() {
        DarkSkyService service = DarkSkyClient.INSTANCE.getService();
        Call<RootData> call = service.getDarkSkyData(37.421998333333335, -122.08400000000002);

        call.enqueue(new Callback<RootData>() {
            @Override
            public void onResponse(Call<RootData> call, Response<RootData> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RootData> call, Throwable t) {
                Log.e(TAG, "onFailure " + t.toString());
            }
        });
    }

    private void getPreferences() {
        if (prefTheme == null) {
            prefTheme = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme));
        }
        if (prefProvider == null) {
            prefProvider = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));
        }
        if (prefTemperature == null) {
            prefTemperature = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_temperature), getResources().getString(R.string.default_pref_temperature));
        }
        if (prefSpeed == null) {
            prefSpeed = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_speed), getResources().getString(R.string.default_pref_speed));
        }
        if (prefTime == null) {
            prefTime = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_time), getResources().getString(R.string.default_pref_time));
        }
        prefUseGps = PreferencesUtils.INSTANCE.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_use_gps), false);
    }


    private void setDrawer() {
        if (mDrawer != null) {
            mDrawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                    if (mSearchView != null && mSearchView.isSearchOpen()) {
                        mSearchView.close(true);
                    }
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu();
                }
            });
        }
    }


    private void setNavigationView() {
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    int id = item.getItemId();

                    switch (id) {
                        case R.id.drawer_item_settings:
                            Intent intentSetting = new Intent(mContext, com.dev.nicola.allweather.AppPreferences.class);
                            startActivity(intentSetting);
                            goToSetting = true;
                            break;

                        case R.id.drawer_item_pro_version:
                            if (PreferencesUtils.INSTANCE.getPreferences(mContext, getResources().getString(R.string.key_pro_version), false)) {
                                SnackbarUtils.showSnackbar(MainActivity.this, mCoordinatorLayout, 6);
                            } else {
                                Intent intentInApp = new Intent(mContext, com.dev.nicola.allweather.InAppPurchaseActivity.class);
                                startActivity(intentInApp);
                            }
                            break;
                    }
                    mDrawer.closeDrawer(GravityCompat.START);
                    return false;
                }
            });
        }
    }


    private void setViewPager() {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mContext);

        if (mViewPager != null) {
            mViewPager.setAdapter(fragmentAdapter);
        }

        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }


    private void setSearchView() {
        final Handler handler = new Handler();
        if (mSearchView != null) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
            mSearchView.setElevation(20);
            mSearchView.setHint(R.string.hint_search_view);
            mSearchView.setVoice(false);
            mSearchView.setShadow(false);
            mSearchView.setDivider(false);
            mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);

            mPlaceAutocomplete = new PlaceAutocomplete(mContext);
            mSearchAdapter = new SearchAdapter(mContext);
            suggestionsList = new ArrayList<>();

            if (prefTheme.equals(getResources().getString(R.string.default_pref_theme)))
                mSearchView.setTheme(SearchView.THEME_LIGHT, true);
            else
                mSearchView.setTheme(SearchView.THEME_DARK, true);

            mSearchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
                @Override
                public void onMenuClick() {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            });


            mSearchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }

                @Override
                public boolean onOpen() {
                    return false;
                }
            });

            // FIXME: 08/09/2016 click historyTable cause crash app
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(final String query) {
                    if (query.length() >= 2) {
                        if (!firstSuggestion.contains(query)) {    // FIXME: 13/09/2016 i suggerimenti sono lenti ad apparire
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "run thread");
                                    mPlaceAutocomplete.autocomplete(query);

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            suggestionsList = mPlaceAutocomplete.getSuggestionList();
                                            Log.d(TAG, "suggestionList " + suggestionsList.size());
                                            firstSuggestion = suggestionsList.get(0).get_text().toString();
                                            Log.d(TAG, "firstSuggetion " + firstSuggestion);
                                            mSearchAdapter.setSuggestionsList(suggestionsList);
                                            Log.d(TAG, "searchAdapter " + mSearchAdapter.getItemCount());
                                            mSearchView.setAdapter(mSearchAdapter); // FIXME: 16/09/2016  java.lang.IllegalArgumentException: Scrapped or attached views may not be recycled. isScrap:false isAttached:true
                                        }
                                    });
                                }
                            }).start();


//                        } else {
//                            Log.d(TAG, "searchView first suggestion eqauals");
//                            suggestionsList = mPlaceAutocomplete.getSuggestionList();
//                            Log.d(TAG, "searchView suggestionList " + suggestionsList.size());
//                            mSearchAdapter.setSuggestionsList(suggestionsList.subList(0,1));
//                            Log.d(TAG, "searchView searchAdapter " + mSearchAdapter.getItemCount());
//                            mSearchView.setAdapter(mSearchAdapter);
                        }
                    }
                    return true;
                }

                //
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchView.close(false);
                    return false;
                }
            });

            mSearchView.setAdapter(mSearchAdapter);
////
//
//
            mSearchAdapter.addOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mSearchView.close(false);
                    searchViewItemClick = true;
                    TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
                    if (!textView.getText().toString().equals(getResources().getString(R.string.no_result_suggestion))) { //if string not equals at no result
                        String item = (textView.getText().toString()).substring(0, (textView.getText().toString()).indexOf(',')); //troncare stringa da 0 al char ','
                        mLocation = LocationUtils.INSTANCE.getCoordinateByName(mContext, item);
                        if (mLocation != null) {
                            if (mDialog != null && mDialog.getVisibility() == View.GONE) {
                                mViewPager.setVisibility(View.GONE);
                                mDialog.setVisibility(View.VISIBLE);
                            }
//                            new MyAsyncTask(mContext, MainActivity.this).execute(mLocation);
                        }
                    }
                }
            });
        }

//        final Handler handler = new Handler();
//        if (mSearchView != null) {
//            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
//            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
//            mSearchView.setElevation(10);
////            mSearchView.setHint(R.string.hint_search_view);
//            mSearchView.setVoice(false);
//            mSearchView.setShadow(false);
//            mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
//
//            mPlaceAutocomplete = new PlaceAutocomplete(mContext);
//            mSearchAdapter = new SearchAdapter(mContext);
//            suggestionsList = new ArrayList<>();
//
//            if (prefTheme.equals(getResources().getString(R.string.default_pref_theme))) { // FIXME: 30/10/2016 se il tema è auto non cambia
//                mSearchView.setTheme(SearchView.THEME_LIGHT, true);
//            } else {
//                mSearchView.setTheme(SearchView.THEME_DARK, true);
//            }
//            mSearchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
//                @Override
//                public void onMenuClick() {
//                    mDrawer.openDrawer(GravityCompat.START);
//                }
//            });
//
//            mSearchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
//                @Override
//                public void onClose() {
//                    mSearchView.setText(PreferencesUtils.getPreferences(mContext, "lastPlace", null));
//                }
//
//                @Override
//                public void onOpen() {
//                    mSearchView.setText(null);
//                }
//            });
//
//            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(final String query) {
//                    if (query.length() > 1 && !query.equals(PreferencesUtils.getPreferences(mContext, "lastPlace", null))) {
//                        if (!firstSuggestion.toLowerCase().contains(query.toLowerCase())) {
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mPlaceAutocomplete.autocomplete(query);
//
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            suggestionsList = mPlaceAutocomplete.getSuggestionList();
//                                            Log.d(TAG, "suggestion list size " + suggestionsList.size());
//                                            firstSuggestion = suggestionsList.get(0).get_text().toString();
//                                            mSearchAdapter.setSuggestionsList(suggestionsList);
//                                            Log.d(TAG, "searchAdapter size " + mSearchAdapter.getItemCount());
//                                            mSearchView.setAdapter(mSearchAdapter);
//                                        }
//                                    });
//                                }
//                            }).start();
//                        } else
//                            mSearchView.setAdapter(mSearchAdapter);
//                    }
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    mSearchView.close(false);
//                    return false;
//                }
//            });
//
//            mSearchView.setAdapter(mSearchAdapter);
//
//
//            mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    mSearchView.close(false);
//                    TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
//                    if (!textView.getText().toString().equals(getResources().getString(R.string.no_result_suggestion))) { //if string not equals at no result
//                        String item = (textView.getText().toString()).substring(0, (textView.getText().toString()).indexOf(',')); //troncare stringa da 0 al char ','
//                        mLocation = LocationUtils.getCoordinateByName(mContext, item);
//                        if (mLocation != null) {
//                            if (mDialog != null && mDialog.getVisibility() == View.GONE) {
//                                mViewPager.setVisibility(View.GONE);
//                                mDialog.setVisibility(View.VISIBLE);
//                            }
//                            new MyAsyncTask(mContext, MainActivity.this).execute(mLocation);
//                        }
//                    }
//                }
//            });
//        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation!=null){
            Log.e(TAG,"location lat "+mLocation.getLatitude());
            Log.e(TAG,"location lng "+mLocation.getLongitude());
        }else {
            Log.e(TAG,"location null");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }
}
