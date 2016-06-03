package com.dev.nicola.allweather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIORequest;
import com.dev.nicola.allweather.Util.FragmentAdapter;
import com.dev.nicola.allweather.Util.LocationGPS;
import com.dev.nicola.allweather.Util.LocationIP;
import com.dev.nicola.allweather.Util.PlaceAutocomplete;
import com.dev.nicola.allweather.Util.Preferences;
import com.dev.nicola.allweather.Util.Utils;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

    private String theme;
    private String systemUnit;

    private PlaceAutocomplete mPlaceAutocomplete;
    private List<SearchItem> suggestionsList;
    private SearchAdapter mSearchAdapter;
    private boolean useSuggestion = false;

    private Location mLocation;
    private LocationGPS mLocationGPS;
    private LocationIP mLocationIP;
    private Double latitude;
    private Double longitude;

    private Handler mHandler;
    private ForecastIORequest mRequest;
    private JSONObject mJSONObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mUtils = new Utils(getApplicationContext(), getResources());

        theme = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("themeUnit", "1");
        mUtils.setTheme(this, theme);

        setContentView(R.layout.activity_main);

        mPreferences = new Preferences(getApplicationContext());
        firstRun = mPreferences.getBoolenaPrefences(PREFERENCES, "firstRun");

        if (!firstRun) {
            Intent intent = new Intent(getApplicationContext(), MainIntro.class);
            startActivity(intent);
            mPreferences.setBooleanPrefences(PREFERENCES, "firstRun", true);
        }


        if (!mUtils.checkPermission())
            showSnackbar(1);
//        else if (!mUtils.checkGpsEnable())
//            showSnackbar(2);
        else if (!mUtils.checkInternetConnession())
            showSnackbar(3);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

        if (!mUtils.checkPermission())
            showSnackbar(1);
//        else if (!mUtils.checkGpsEnable())
//            showSnackbar(2);
        else if (!mUtils.checkInternetConnession())
            showSnackbar(3);
        else if (mJSONObject == null) {
            initialSetup();
            getLocation();
        }

        if (!theme.equals(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("themeUnit", "1")))
            MainActivity.this.recreate();

        if (!systemUnit.equals(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("systemUnit", "1"))) {
            systemUnit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("systemUnit", "1");
            if (!mProgressDialog.isShowing())
                mProgressDialog.show();
            new task().execute();
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();

        if (firstRun && mUtils.checkPermission() && mUtils.checkGpsEnable() && mUtils.checkInternetConnession()) {
            if (mLocationGPS.isConnected())
                mLocationGPS.stopUsingGPS();

            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();

            if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
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


    private void initialSetup() {
        Log.d(TAG, "initialSetup");
        mProgressDialog = ProgressDialog.show(MainActivity.this, "", "loading...", true);

        mPlaceAutocomplete = new PlaceAutocomplete();
        mRequest = new ForecastIORequest(getApplicationContext());
        mLocationGPS = new LocationGPS(getApplicationContext());
        mLocationIP = new LocationIP();

        setDrawer();
        setNavigationView();
        setSearchView();

        systemUnit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("systemUnit", "1");
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
                        case R.id.drawer_item_settings:
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
            mSearchView.setHint(R.string.hint_search_view);
            mSearchView.setVoice(false);
            mSearchView.setShadow(false);

            if (theme.equals("1"))
                mSearchView.setTheme(SearchView.THEME_LIGHT, true);
            else
                mSearchView.setTheme(SearchView.THEME_DARK, true);

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
//                        taskAutoComplete(newText);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchView.close(false);
                    return false;
                }
            });

            suggestionsList.add(new SearchItem("Milano"));
            suggestionsList.add(new SearchItem("Madrid"));
            suggestionsList.add(new SearchItem("Mosca"));


            SearchAdapter searchAdapter = new SearchAdapter(this, suggestionsList);
            searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mSearchView.close(false);
                    TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
                    String item = textView.getText().toString();
                    mLocation = mUtils.getCoordinateByName(item);
                    new task().execute();
                    if (!mProgressDialog.isShowing())
                        mProgressDialog.show();
                }
            });
            mSearchView.setAdapter(searchAdapter);
        }
    }


    private void showSnackbar(int code) {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        switch (code) {
            case 1:// ask LOCATION permission
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_1, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.snackbar_action_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askPermission();
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 2:// GPS disable
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_2, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.snackbar_action_OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gpsSetting = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsSetting);
                    }
                });
                mSnackbar.setActionTextColor(Color.YELLOW);
                break;

            case 3:// No internet connession
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_3, Snackbar.LENGTH_LONG);
                break;

            case 4:// Impossibile recuperare la posizione
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_4, Snackbar.LENGTH_LONG);
                break;

            case 5:// LOCATION permission denied
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_5, Snackbar.LENGTH_LONG);
                break;

            case 6:// Impossibile contattare il server
                mSnackbar = Snackbar.make(mCoordinatorLayout, R.string.snackbar_6, Snackbar.LENGTH_INDEFINITE);
                mSnackbar.setAction(R.string.snackbar_action_retry, new View.OnClickListener() {
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


    private void taskLocationByIP() {
        mHandler = new Handler();
        new Thread() {
            public void run() {
                final String ip = mLocationIP.getExternalIP();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLocationIP.getLocation(ip);
                    }
                });
            }
        }.start();
    }

    public void getLocation() {
        mLocation = mLocationGPS.getLocation();
//        if (mLocation != null)
//            new task().execute();
//        else
//            showSnackbar(4);

//        taskLocationByIP();
        new task().execute();


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
            if (mLocation != null)
                mJSONObject = mRequest.getData(mRequest.setUrl(mLocation.getLatitude(), mLocation.getLongitude()));
            else {
                final String ip = mLocationIP.getExternalIP();
                mLocationIP.getLocation(ip);
                mJSONObject = mRequest.getData(mRequest.setUrl(mLocationIP.getLatitude(), mLocationIP.getLongitude()));
            }
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
