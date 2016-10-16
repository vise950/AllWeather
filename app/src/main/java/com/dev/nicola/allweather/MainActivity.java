package com.dev.nicola.allweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.nicola.allweather.adapter.FragmentAdapter;
import com.dev.nicola.allweather.utils.LocationGPS;
import com.dev.nicola.allweather.utils.LocationIP;
import com.dev.nicola.allweather.utils.LocationUtils;
import com.dev.nicola.allweather.utils.PermissionUtils;
import com.dev.nicola.allweather.utils.PlaceAutocomplete;
import com.dev.nicola.allweather.utils.PreferencesUtils;
import com.dev.nicola.allweather.utils.Utils;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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

    private Context mContext;
    private SharedPreferences mPreferences;
    private ProgressDialog mProgressDialog;
    private Snackbar mSnackbar;
    private long lastRefresh;
    private boolean searchViewItemClick = false;

    private String prefTheme;
    private String prefTemperature;
    private String prefSpeed;
    private String prefTime;
    private String prefProvider;
    private boolean prefUseGps;
    private String nullString = null;
    private boolean goToSetting = false;

    private PlaceAutocomplete mPlaceAutocomplete;
    private List<SearchItem> suggestionsList;
    private SearchAdapter mSearchAdapter;
    private SearchHistoryTable mHistoryDatabase;
    private String firstSuggestion = "";

    private Location mLocation;
    private LocationGPS mLocationGPS;
    private LocationIP mLocationIP;

    private JSONObject mJSONObject;
    private ProviderData mProviderData;

    private MyBilling mBilling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        mContext = getApplicationContext();

        prefTheme = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme));
        Utils.setTheme(this, prefTheme);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPreferences = getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE);

        mBilling = new MyBilling(MainActivity.this);
        mBilling.onCreate();

//        firstRun = mPreferences.getBoolean("firstRun", true);
//        if (firstRun) {
//            PreferenceManager.getDefaultSharedPreferences(mContext).edit().clear().apply();
//            mPreferences.edit().putBoolean("firstRun", false).apply();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBilling.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        if (!Utils.checkInternetConnession(mContext)) {
            showSnackbar(3);
        } else if (prefUseGps && !Utils.checkGpsEnable(mContext)) {
            showSnackbar(2);
        } else if (prefUseGps && !Utils.checkPermission(mContext)) {
            PermissionUtils.askPermission(MainActivity.this);
        } else if (mJSONObject == null) {
            initialSetup();
        }

        if (goToSetting) {
            checkPreferences();
        }
//        lastRefresh = mPreferences.getLong("lastRefresh", 0);
//        if (System.currentTimeMillis() - lastRefresh >= 1000 * 60 * 10)
//            new task().execute(nullString);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        if (mLocationGPS != null && mLocationGPS.isConnected()) {
            mLocationGPS.stopUsingGPS();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mViewPager != null) {
            mViewPager.removeAllViews();
        }

        if (mBilling != null) {
            mBilling.onDestroy();
        }
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

    private void checkPreferences() {
        if (!prefTheme.equals(PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme))) /*&&
                !prefProvider.equals(PreferencesUtils.getPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider)))*/) {
            prefTheme = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme));
//            prefProvider = PreferencesUtils.getPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));
            MainActivity.this.recreate();
        }

        // FIXME: 09/09/2016 se cambio tema e provider crasha
        else if (!prefTemperature.equals(PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_temperature), getResources().getString(R.string.default_pref_temperature))) ||
                !prefSpeed.equals(PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_speed), getResources().getString(R.string.default_pref_speed))) ||
                !prefTime.equals(PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_time), getResources().getString(R.string.default_pref_time))) ||
                !prefProvider.equals(PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider)))) {

            prefProvider = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));
            prefTemperature = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_temperature), getResources().getString(R.string.default_pref_temperature));
            prefSpeed = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_speed), getResources().getString(R.string.default_pref_speed));
            prefTime = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_time), getResources().getString(R.string.default_pref_time));

            if (mViewPager != null) {
                mViewPager.removeAllViews();
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
            new task().execute(nullString);
        } else if (prefUseGps != PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_use_gps), false)) {
            prefUseGps = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_use_gps), false);
            mJSONObject = null;
            MainActivity.this.onResume();
        }
    }


    private void initialSetup() {
        mProgressDialog = ProgressDialog.show(MainActivity.this, null, null, true);
        mProgressDialog.setContentView(R.layout.progress_bar);
        mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        prefProvider = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));
        prefTemperature = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_temperature), getResources().getString(R.string.default_pref_temperature));
        prefSpeed = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_speed), getResources().getString(R.string.default_pref_speed));
        prefTime = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_time), getResources().getString(R.string.default_pref_time));
        prefUseGps = PreferencesUtils.getDefaultPreferences(mContext, getResources().getString(R.string.key_pref_use_gps), false);

        if (DEBUG_MODE) { //se sono in debug l'app è pro
            PreferencesUtils.setPreferences(mContext, getResources().getString(R.string.key_pro_version), true);
        }

        if (prefUseGps) {
            mLocationGPS = new LocationGPS(mContext);
            mLocation = mLocationGPS.getLocation();
        }

        mLocationIP = new LocationIP();

        setDrawer();
        setNavigationView();
        setSearchView();

        mProviderData = new ProviderData(mContext, getResources());
        mPlaceAutocomplete = new PlaceAutocomplete(mContext);

        new task().execute(nullString);
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
                            Intent intent = new Intent(mContext, AppPreferences.class);
                            startActivity(intent);
                            goToSetting = true;
//                            mJSONObject = null;
                            break;

                        case R.id.drawer_item_pro_version:
                            if (!PreferencesUtils.getPreferences(mContext, getResources().getString(R.string.key_pro_version), false)) {
                                mBilling.purchaseDialog(getResources().getString(R.string.dialog_pro_version_title),
                                        getResources().getString(R.string.dialog_pro_version_message),
                                        getResources().getString(R.string.action_OK),
                                        getResources().getString(R.string.action_cancel));
                            } else {
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle(getResources().getString(R.string.dialog_already_pro_version));
                                dialog.setPositiveButton(getResources().getString(R.string.action_OK), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                            break;
                    }
                    mDrawer.closeDrawer(GravityCompat.START);
                    return false;
                }
            });
        }
    }


    private void setViewPager(String argument) {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mContext, argument);

        if (mViewPager != null) {
            mViewPager.setAdapter(fragmentAdapter);
        }

        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }


    private void setSearchView() {
//        final Handler handler = new Handler();
//        mSearchAdapter = new SearchAdapter(mContext);
////        mHistoryDatabase = new SearchHistoryTable(mContext);
////        mHistoryDatabase.setHistorySize(4);
//        suggestionsList = new ArrayList<>();
//        if (mSearchView != null) {
//            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
//            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
//            mSearchView.setElevation(10);
//            mSearchView.setHint(R.string.hint_search_view);
//            mSearchView.setVoice(false);
//            mSearchView.setShadow(false);
//            mSearchView.setDivider(false);
//            mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
//            mSearchView.setNavigationIconArrowHamburger();
//
//
//            if (prefTheme.equals(getResources().getString(R.string.default_pref_theme)))
//                mSearchView.setTheme(SearchView.THEME_LIGHT, true);
//            else
//                mSearchView.setTheme(SearchView.THEME_DARK, true);
//
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
//                }
//
//                @Override
//                public void onOpen() {
//                }
//            });
//
//            // FIXME: 08/09/2016 click historyTable cause crash app
//            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(final String query) {
//                    if (query.length() >= 2) {
////                        if (!firstSuggestion.contains(query)) {    // FIXME: 13/09/2016 i suggerimenti sono lenti ad apparire
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d(TAG, "run thread");
//                                mPlaceAutocomplete.autocomplete(query);
//
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        suggestionsList = mPlaceAutocomplete.getSuggestionList();
//                                        Log.d(TAG, "suggestionList " + suggestionsList.size());
////                                            firstSuggestion = suggestionsList.get(0).get_text().toString();
////                                            Log.d(TAG, "firstSuggetion " + firstSuggestion);
//                                        mSearchAdapter.setSuggestionsList(suggestionsList);
//                                        Log.d(TAG, "searchAdapter " + mSearchAdapter.getItemCount());
//                                        mSearchView.setAdapter(mSearchAdapter); // FIXME: 16/09/2016  java.lang.IllegalArgumentException: Scrapped or attached views may not be recycled. isScrap:false isAttached:true
//                                    }
//                                });
//                            }
//                        }).start();
//
//
////                        } else {
////                            Log.d(TAG, "searchView first suggestion eqauals");
////                            suggestionsList = mPlaceAutocomplete.getSuggestionList();
////                            Log.d(TAG, "searchView suggestionList " + suggestionsList.size());
////                            mSearchAdapter.setSuggestionsList(suggestionsList.subList(0,1));
////                            Log.d(TAG, "searchView searchAdapter " + mSearchAdapter.getItemCount());
////                            mSearchView.setAdapter(mSearchAdapter);
////                        }
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
//            // FIXME: 03/09/2016 agiungere un altro searchAdapter risolve il bug che non fa vedere i suggerimenti
//            SearchAdapter searchAdapter = new SearchAdapter(mContext, null);
//            mSearchView.setAdapter(searchAdapter);
////
//
//
//            mSearchAdapter.addOnItemClickListener(new SearchAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    mSearchView.close(false);
//                    searchViewItemClick = true;
//                    TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
//                    if (!textView.getText().toString().equals(getResources().getString(R.string.no_result_suggestion))) { //if string not equals at no result
//                        String item = (textView.getText().toString()).substring(0, (textView.getText().toString()).indexOf(',')); //troncare stringa da 0 al char ','
//                        Log.d(TAG, "item " + item);
////                    mHistoryDatabase.open();
////                    mHistoryDatabase.addItem(new SearchItem(item));
////                    mLocation = LocationUtils.getCoordinateByName(mContext, item);
////                    mHistoryDatabase.clearDatabase();
////                    mHistoryDatabase.close();
//                        new task().execute(item);
//                        if (!mProgressDialog.isShowing())
//                            mProgressDialog.show();
//                    }
//                }
//            });
//        }

        final Handler handler = new Handler();
        mSearchAdapter = new SearchAdapter(mContext);
        suggestionsList = new ArrayList<>();
        if (mSearchView != null) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
            mSearchView.setElevation(10);
            mSearchView.setHint(R.string.hint_search_view);
            mSearchView.setVoice(false);
            mSearchView.setShadow(false);
            mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);

            if (prefTheme.equals(getResources().getString(R.string.default_pref_theme))) {
                mSearchView.setTheme(SearchView.THEME_LIGHT, true);
            } else {
                mSearchView.setTheme(SearchView.THEME_DARK, true);
            }
            mSearchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
                @Override
                public void onMenuClick() {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            });

            mSearchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
                @Override
                public void onClose() {
                }

                @Override
                public void onOpen() {
                }
            });

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(final String query) {
                    if (query.length() > 1) {
                        if (!firstSuggestion.toLowerCase().contains(query.toLowerCase())) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mPlaceAutocomplete.autocomplete(query);

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            suggestionsList = mPlaceAutocomplete.getSuggestionList();
                                            Log.d(TAG, "suggestion list size " + suggestionsList.size());
                                            firstSuggestion = suggestionsList.get(0).get_text().toString();
                                            mSearchAdapter.setSuggestionsList(suggestionsList);
                                            Log.d(TAG, "searchAdapter size " + mSearchAdapter.getItemCount());
                                            mSearchView.setAdapter(mSearchAdapter);
                                        }
                                    });
                                }
                            }).start();
                        } else
                            mSearchView.setAdapter(mSearchAdapter);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchView.close(false);
                    return false;
                }
            });

            mSearchView.setAdapter(mSearchAdapter);


            mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mSearchView.close(false);
                    TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
                    if (!textView.getText().toString().equals(getResources().getString(R.string.no_result_suggestion))) { //if string not equals at no result
                        String item = (textView.getText().toString()).substring(0, (textView.getText().toString()).indexOf(',')); //troncare stringa da 0 al char ','
                        new task().execute(item);
                        if (!mProgressDialog.isShowing()) {
                            mProgressDialog.show();
                        }
                    }
                }
            });
        }
    }


    private void showSnackbar(int code) {
        switch (code) {
            case 1:// ask LOCATION permission
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_1, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.action_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PermissionUtils.askPermission(MainActivity.this);
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 2:// GPS disable
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_2, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.action_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 3:// No internet connession
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_3, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.action_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage(R.string.diaolog_connection_disable)
                                .setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                                    }
                                })
                                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 4:// Impossibile contattare il server
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_4, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.action_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new task().execute(nullString);
                        if (!mProgressDialog.isShowing())
                            mProgressDialog.show();
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;
        }
        mSnackbar.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // Permission DENIED
                    showSnackbar(1);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public class task extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
//            Log.d(TAG, "onPreExecute");

            if (searchViewItemClick && mViewPager != null) {
                mViewPager.removeAllViews();
            }

//            if (mSnackbar!=null && mSnackbar.isShown())
//                mSnackbar.dismiss();
        }

        @Override
        protected Void doInBackground(String... params) {
//            Log.d(TAG, "doInBackground");

            if (params[0] != null) {
                Log.d(TAG, "doInBackground string not null");
                mLocation = LocationUtils.getCoordinateByName(mContext, params[0]);
                mJSONObject = mProviderData.getProviderData(prefProvider, mLocation.getLatitude(), mLocation.getLongitude(),
                        LocationUtils.getLocationName(mContext, mLocation.getLatitude(), mLocation.getLongitude()));
            } else {

                if (prefUseGps && mLocation != null) {
                    Log.d(TAG, "doInBackground location not null");
                    mJSONObject = mProviderData.getProviderData(prefProvider, mLocation.getLatitude(), mLocation.getLongitude(),
                            LocationUtils.getLocationName(mContext, mLocation.getLatitude(), mLocation.getLongitude()));

                } else {
                    Log.d(TAG, "doInBackground location null, using ip");
                    final String ip = mLocationIP.getExternalIP();
                    mLocationIP.getLocation(ip);
                    mJSONObject = mProviderData.getProviderData(prefProvider, mLocationIP.getLatitude(), mLocationIP.getLongitude(),
                            LocationUtils.getLocationName(mContext, mLocationIP.getLatitude(), mLocationIP.getLongitude()));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
//            Log.d(TAG, "onPostExecute");

            RelativeLayout placeholderLayout = (RelativeLayout) findViewById(R.id.placeholder_layout);

            if (mJSONObject != null) {
                setViewPager(mJSONObject.toString());
                placeholderLayout.setVisibility(View.INVISIBLE); // FIXME: 18/09/2016 provare con setContentView al posto del placehoder
            } else {
                showSnackbar(4);
                if (mViewPager != null) {
                    mViewPager.removeAllViews();
                }
                placeholderLayout.setVisibility(View.VISIBLE);
            }

            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

            searchViewItemClick = false;
//            mPreferences.edit().putLong("lastRefresh", System.currentTimeMillis()).apply();

        }
    }

//    public class testLoader extends AsyncTaskLoader<String> {
//
//        public testLoader(Context context) {
//            super(context);
//        }
//
//        @Override
//        public String loadInBackground() {
//            return null;
//        }
//    }

}
