package com.dev.nicola.allweather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIORequest;
import com.dev.nicola.allweather.Util.FragmentAdapter;
import com.dev.nicola.allweather.Util.GPSTracker;
import com.dev.nicola.allweather.Util.PlaceAutocomplete;
import com.dev.nicola.allweather.Util.Preferences;
import com.dev.nicola.allweather.Util.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final static String TAG = MainActivity.class.getSimpleName();

    final String PREFERENCES = MainActivity.class.getName();

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private SearchView mSearchView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Snackbar mSnackbar;

    private boolean firstRun;
    private Preferences mPreferences;
    private Utils mUtils;

    private SharedPreferences mSharedPreferences;

    private PlaceAutocomplete mPlaceAutocomplete;
    private List<SearchItem> suggestionsList;
    private SearchAdapter mSearchAdapter;
    private boolean showSuggestion = false;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private GPSTracker mGPSTracker;

    private Handler mHandler;
    private ForecastIORequest mRequest;
    private JSONObject mJSONObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String theme = mSharedPreferences.getString("themeUnit", "1");
        if (theme.equals("1"))
            setTheme(R.style.lightTheme);
        else
            setTheme(R.style.darkTheme);

        setContentView(R.layout.activity_main);


        mUtils = new Utils(getApplicationContext(), getResources());
        mPreferences = new Preferences(getApplicationContext());
        firstRun = mPreferences.getBoolenaPrefences(PREFERENCES, "firstRun");

        if (!firstRun) {
            Intent intent = new Intent(getApplicationContext(), MainIntro.class);
            startActivity(intent);
            mPreferences.setBooleanPrefences(PREFERENCES, "firstRun", true);
        }


        if (!mUtils.checkPermission())
            showSnackbar(1);
        else if (!mUtils.checkGpsEnable())
            showSnackbar(2);
        else if (!mUtils.checkInternetConnession())
            showSnackbar(3);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();

        if (firstRun && mUtils.checkPermission() && mUtils.checkGpsEnable() && mUtils.checkInternetConnession()) {
            buildGoogleApiClient();
            initialSetup();
            if (mGoogleApiClient != null)
                mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

        if (!mUtils.checkPermission())
            showSnackbar(1);
        else if (!mUtils.checkGpsEnable())
            showSnackbar(2);
        else if (!mUtils.checkInternetConnession())
            showSnackbar(3);

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();

        if (firstRun && mUtils.checkPermission() && mUtils.checkGpsEnable() && mUtils.checkInternetConnession()) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
            if (mGPSTracker.isConnected())
                mGPSTracker.stopUsingGPS();
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
//        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
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


    private void initialSetup() {
        mProgressDialog = ProgressDialog.show(MainActivity.this, "", "loading...", true);

        mPlaceAutocomplete = new PlaceAutocomplete();
        mRequest = new ForecastIORequest(getApplicationContext());
        mGPSTracker = new GPSTracker(getApplicationContext());

        setDrawer();
        setNavigationView();
        setSearchView();
    }


    private void setDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    private void setNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    int id = item.getItemId();

                    switch (id) {
                        case R.id.nav_settings:
                            Intent intent = new Intent(getApplicationContext(), AppPreferences.class);
                            startActivity(intent);
                    }
                    mDrawer.closeDrawer(GravityCompat.START);
                    return false;
                }
            });

        }
    }


    private void setViewPager(String argument) {
        Log.d(TAG, "setViewPager");
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), argument);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        if (mViewPager != null) {
            mViewPager.setAdapter(fragmentAdapter);
        }

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new task().execute();
            }
        });
    }


    private void setSearchView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        suggestionsList = new ArrayList<>();
        if (mSearchView != null) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
            mSearchView.setElevation(10);
//            mSearchView.setTheme();
            mSearchView.setHint("Non funziona per ora");
            mSearchView.setVoice(false);
            mSearchView.setShadow(false);

            mSearchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
                @Override
                public void onMenuClick() {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            });

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > 3) {
                        taskAutoComplete(newText);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchView.close(false);
                    return false;
                }
            });

//            suggestionsList.add(new SearchItem("search1"));
//            suggestionsList.add(new SearchItem("search2"));
//            suggestionsList.add(new SearchItem("search3"));


//            Log.d(TAG,"bool show sugg "+showSuggestion);
//            if (showSuggestion) {
//                suggestionsList = mPlaceAutocomplete.getSuggestionList();
//                Log.d(TAG, "suggestion list " + suggestionsList.size());
//
//                SearchAdapter searchAdapter = new SearchAdapter(this, suggestionsList);
//                Log.d(TAG, "searchAdapter " + searchAdapter.getItemCount());
//            Log.d(TAG,"searchAdapter "+searchAdapter.getItemCount());
//            searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    mSearchView.close(false);
//                    TextView textView=(TextView)view.findViewById(R.id.textView_item_text);
//                    String item=textView.getText().toString();
//                    Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
//                }
//            });
//                mSearchView.setAdapter(searchAdapter);
//            }
        }
    }


    private void showSnackbar(int code) {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        switch (code) {
            case 1:// ask LOCATION permission
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Non ho accesso alla posione\nVuoi dare il permesso?", Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askPermission();
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 2:// GPS disable
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Geolocalizzazione non attiva\nVuoi attivarla?", Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gpsSetting = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsSetting);
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 3:// No internet connession
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Nessuna connessione ad internet", Snackbar.LENGTH_LONG);
                break;

            case 4:// Impossibile recuperare la posizione
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Impossibile recuperare la posizione", Snackbar.LENGTH_LONG);
                break;

            case 5:// LOCATION permission denied
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Non sarà possibile recuperare i dati", Snackbar.LENGTH_LONG);
                break;

            case 6:// Impossibile contattare il server
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Impossibile contattare il server in questo momento", Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new task().execute();
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;
        }
        mSnackbar.show();
    }

    private void taskAutoComplete(final String query) {
        mHandler = new Handler();

        new Thread() {
            public void run() {

                mPlaceAutocomplete.autocomplete(query);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        suggestionsList = mPlaceAutocomplete.getSuggestionList();
                        Log.d(TAG, "suggestion list " + suggestionsList.size());
                        mSearchAdapter = new SearchAdapter(getApplicationContext(), suggestionsList);
                        Log.d(TAG, "searchAdapter " + mSearchAdapter.getItemCount());
                        mSearchView.setAdapter(mSearchAdapter);
                    }
                });
            }
        }.start();
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null)
            mLocation = mGPSTracker.getLocation();

        new task().execute();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    private void askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Log.d(TAG, "permission granted");
                } else {
                    // Permission Denied
                    showSnackbar(5);

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public class task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            mJSONObject = mRequest.getData(mRequest.setUrl(mLocation.getLatitude(), mLocation.getLongitude()));

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            if (mJSONObject != null) {
                setViewPager(mJSONObject.toString());
            } else {
                showSnackbar(6);
            }

            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();

            if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);

        }
    }

}
