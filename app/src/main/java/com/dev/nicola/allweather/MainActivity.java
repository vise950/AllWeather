package com.dev.nicola.allweather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIORequest;
import com.dev.nicola.allweather.Util.FragmentAdapter;
import com.dev.nicola.allweather.Util.PlaceAutocomplete;
import com.dev.nicola.allweather.Util.Preferences;
import com.dev.nicola.allweather.Util.Utils;
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

    private boolean firstRun;
    private Preferences mPreferences;
    private String locationName;

    private Utils mUtils;
    private Bundle mBundle;
    private DailyFragment mDailyFragment;
    private PlaceAutocomplete mPlaceAutocomplete;

    private Handler mHandler;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;

    private ForecastIORequest mRequest;


    private JSONObject mJSONObject;

    private ProgressDialog mProgressDialog;

    private List<SearchItem> suggestionsList;

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

            mUtils = new Utils(getApplicationContext());
            mBundle = new Bundle();
            mDailyFragment = new DailyFragment();
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
            setViewPager();
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();

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


    private void setViewPager() {
        final FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new DailyFragment(), "NOW");
        fragmentAdapter.addFragment(new ForecastFragment(), "WEEK");

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        if (mViewPager != null) {
            mViewPager.setAdapter(fragmentAdapter);
        }

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }


    private void setSearchView() {
        mSearchView = (SearchView) findViewById(R.id.search_view);
        if (mSearchView != null) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
            mSearchView.setHint("Search location");
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
                    if (newText.length()>3) {
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

//            suggestionsList = new ArrayList<>();
//            suggestionsList.add(new SearchItem("search1"));
//            suggestionsList.add(new SearchItem("search2"));
//            suggestionsList.add(new SearchItem("search3"));

            SearchAdapter searchAdapter = new SearchAdapter(this, suggestionsList);
            searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mSearchView.close(false);
                    TextView textView=(TextView)view.findViewById(R.id.textView_item_text);
                    String item=textView.getText().toString();
                    Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                }
            });
            mSearchView.setAdapter(searchAdapter);
        }
    }

//    private void setUpLayout() {
//        Log.d(TAG, "setUpLayout");
//
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//
//        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        mTabLayout.addTab(mTabLayout.newTab().setText("NOW"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("WEEK"));
//
//        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
//        mViewPager.setAdapter(adapter);
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//
//    }
//
//
//    ///adapter tabLayout
//    public class PagerAdapter extends FragmentStatePagerAdapter {
//        int mNumOfTabs;
//        Bundle bundle=new Bundle();
//
//        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
//            super(fm);
//            this.mNumOfTabs = NumOfTabs;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
//            switch (position) {
//                case 0:
//                    DailyFragment dailyFragment = new DailyFragment();
//                    bundle.putString("JsonObject", mJSONObject.toString());
//                    dailyFragment.setArguments(bundle);
//                    return dailyFragment;
//                case 1:
//                    ForecastFragment forecastFragment = new ForecastFragment();
//                    bundle.putString("JsonObject",mJSONObject.toString());
//                    forecastFragment.setArguments(bundle);
//                    return forecastFragment;
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return mNumOfTabs;
//        }
//    }


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

//            Snackbar snackbar = Snackbar.make(findViewById(R.id.content_layout), "Impossibile recuperare la posizione", Snackbar.LENGTH_LONG);
//            snackbar.show();
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


    private void initialTask() {
        Log.d(TAG, "initialTask");
        mHandler = new Handler();
        new Thread() {
            public void run() {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        setUpLayout();
                    }
                });
            }
        }.start();
    }


    private void taskAutoComplete(final String query) {
        new Thread() {
            public void run() {
                suggestionsList=mPlaceAutocomplete.autocomplete(query);
            }
        }.start();

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
//            Log.d(TAG, "Json" + mJSONObject);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Log.d(TAG, "AsyncTask onPostExecute");

//            setUpLayout();

            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();

        }
    }

}
