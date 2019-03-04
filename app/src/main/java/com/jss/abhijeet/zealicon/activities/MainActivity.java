package com.jss.abhijeet.zealicon.activities;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.jss.abhijeet.zealicon.R;
import com.jss.abhijeet.zealicon.fragments.HomeFragment;
import com.jss.abhijeet.zealicon.fragments.InfoFragment;
import com.jss.abhijeet.zealicon.fragments.RegisterFragment;
import com.jss.abhijeet.zealicon.fragments.ScheduleFragment;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity {

    private String TAG = "Main Activity";

    private int POS_CURRENT = -1;
    ConstraintLayout activityMain;
    private BottomNavigationView mBottomNav;
    private Fragment selectedFragment = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = getClass().getSimpleName();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        activityMain = findViewById(R.id.activity_main);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        initBottomNavigation();

    }

    private void showFragment(Fragment fragment, int position) {
        //Fragment current= getFragmentManager().findFragmentById(R.id.frame_layout);
        if (POS_CURRENT != position) {
            POS_CURRENT = position;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        } else
            Log.v(TAG, "same fragment selected");
    }


    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    /**
     * Bottom Navigation Implementation
     */
    public void initBottomNavigation() {
        mBottomNav = findViewById(R.id.bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment selectedFragment=null;
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.home:
                        position = 0;
                        selectedFragment = HomeFragment.newInstance();
                        //activityMain.setBackgroundResource(R.drawable.background);
                        Log.v(TAG, "Home Fragment Selected");
                        break;
                    case R.id.info:
                        position = 3;
                        selectedFragment = InfoFragment.newInstance();
                        //activityMain.setBackgroundResource(R.drawable.background);
                        Log.v(TAG, "Info Fragment Selected");
                        break;
                    case R.id.schedule:
                        position = 1;
                        //activityMain.setBackgroundResource(R.drawable.background);
                        selectedFragment = ScheduleFragment.newInstance();
                        Log.v(TAG, "Schedule Fragment Selected");
                        break;
                    case R.id.register:
                        position = 2;
                        selectedFragment = RegisterFragment.newInstance();
                        //activityMain.setBackgroundResource(R.drawable.background);
                        Log.v(TAG, "Register Fragment Selected");
                        break;
                }
                showFragment(selectedFragment, position);
                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                */
                return true;
            }
        });
        //Manually displaying the first fragment - one time only
        /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment
                .newInstance());
        transaction.commit();*/
        showFragment(HomeFragment.newInstance(), 0);
        disableShiftingModeOfBottomNavigationView(mBottomNav);
    }

    /**
     * This method disables Shifting of bottom nav items in the bottom
     * nav bar.
     */

    @SuppressLint("RestrictedApi")
    private void disableShiftingModeOfBottomNavigationView(BottomNavigationView btmNavigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) btmNavigationView.getChildAt(0);

        try {

            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShifting(false);
                item.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

                //To update view, set the checked value again
                item.setChecked(item.getItemData().isChecked());
            }


        } catch (NoSuchFieldException e) {
            e.printStackTrace();

            Log.e(TAG, "Unable to get shift mode field");


        } catch (IllegalAccessException e) {
            e.printStackTrace();

            Log.e(TAG, "Unable to change value of shift mode");

        } catch (SecurityException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}

