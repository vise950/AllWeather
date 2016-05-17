package com.dev.nicola.allweather;
//Prova uno

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.dev.nicola.allweather.Util.PlaceAutocomplete;
import com.dev.nicola.allweather.Util.Preferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    final static String TAG = MainActivity.class.getSimpleName();

    final String PREFERENCES = MainActivity.class.getName();

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

    private PlaceAutocomplete mPlaceAutocomplete;
    private List<SearchItem> suggestionsList;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;

    private Handler mHandler;
    private ForecastIORequest mRequest;
    private JSONObject mJSONObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = new Preferences(getApplicationContext());

        firstRun = mPreferences.getBoolenaPrefences(PREFERENCES, "firstRun");

        if (!firstRun) {
            Intent intent = new Intent(getApplicationContext(), MainIntro.class);
            startActivity(intent);
            mPreferences.setBooleanPrefences(PREFERENCES, "firstRun", true);
        } else {
            mProgressDialog = ProgressDialog.show(MainActivity.this, "", "loading...", true);

            mPlaceAutocomplete = new PlaceAutocomplete();
            mRequest = new ForecastIORequest(getApplicationContext());

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            setDrawer();
            setNavigationView();
            setSearchView();
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();

        if (firstRun)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
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
        if (mSearchView != null) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
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
//                    if (newText.length() > 3) {
//                       taskAutoComplete(newText);
//                    }
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchView.close(false);
                    return false;
                }
            });

//            suggestionsList = new ArrayList<>();
//            suggestionsList.add(new SearchItem("search1"));
//            suggestionsList.add(new SearchItem("search2"));
//            suggestionsList.add(new SearchItem("search3"));
//
//            SearchAdapter searchAdapter = new SearchAdapter(this, suggestionsList);
//            searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    mSearchView.close(false);
//                    TextView textView=(TextView)view.findViewById(R.id.textView_item_text);
//                    String item=textView.getText().toString();
//                    Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
//                }
//            });
//            mSearchView.setAdapter(searchAdapter);
        }
    }


    private void taskAutoComplete(final String query) {
        mHandler = new Handler();

        new Thread() {
            public void run() {

                suggestionsList = mPlaceAutocomplete.autocomplete(query);
                Log.d(TAG, "autocomplete object" + mJSONObject);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < suggestionsList.size(); i++) {
                            Log.d(TAG, "suggestion list " + suggestionsList.get(i).get_text().toString());
                        }
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), suggestionsList);
                        mSearchView.setAdapter(searchAdapter);
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
        if (mLocation != null) {
            Log.d(TAG, "latitude:" + mLocation.getLatitude() + " longitude:" + mLocation.getLongitude());
            new task().execute();
        } else {
            mSnackbar = Snackbar.make(mCoordinatorLayout, "Impossibile recuperare la posizione", Snackbar.LENGTH_LONG);
        }
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

    @Override
    public void onLocationChanged(Location location) {
    }


    public class task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "AsyncTask doInBackground");
            mJSONObject = mRequest.getData(mRequest.setUrl(mLocation.getLatitude(), mLocation.getLongitude()));

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Log.d(TAG, "AsyncTask onPostExecute");

            if (mJSONObject != null)
                setViewPager(mJSONObject.toString());
            else
                mSnackbar = Snackbar.make(mCoordinatorLayout, "Impossibile contattare il server in questo momento", Snackbar.LENGTH_LONG);

            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();

            if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);

        }
    }

}
