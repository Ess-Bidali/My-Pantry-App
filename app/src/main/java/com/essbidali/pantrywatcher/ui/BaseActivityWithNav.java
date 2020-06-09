package com.essbidali.pantrywatcher.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.ui.authentication.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivityWithNav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Class currentActivity = this.getClass();
    static Boolean backPressedOnce = false;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DrawerLayout sideNavDrawerLayout;
    NavigationView sideNavNavigationView;
    androidx.appcompat.widget.Toolbar actionBarToolBar;
    FrameLayout activityContainer;
    TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_with_nav);

    }

    @Override
    public void setContentView(int layoutResID) {
        //Hooks
        sideNavDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base_with_nav, null);
        sideNavNavigationView = sideNavDrawerLayout.findViewById(R.id.sideNavNavigationView);
        actionBarToolBar = sideNavDrawerLayout.findViewById(R.id.actionBarToolBar);
        activityContainer = sideNavDrawerLayout.findViewById(R.id.contentFrameLayout);
        tabs = sideNavDrawerLayout.findViewById(R.id.tabs);

        //Set up custom action bar
        setSupportActionBar(actionBarToolBar);
        actionBarToolBar.setTitleTextColor(getResources().getColor(R.color.actionBarTextColor));

        //Set the activity content into this parent activity's frame layout
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        //Set up side navigation
        sideNavNavigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, sideNavDrawerLayout, actionBarToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        sideNavDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        sideNavNavigationView.setNavigationItemSelectedListener(this);
        sideNavNavigationView.setCheckedItem(R.id.sideNavHome);

        super.setContentView(sideNavDrawerLayout);
    }

    //Set current activity to keep track of it during menu navigation
    public void setCurrentActivity(Class activityClass, @IdRes int resId){
        this.currentActivity = activityClass;
        sideNavNavigationView.setCheckedItem(resId);
    }

    //Menu actions on the side nav bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        sideNavDrawerLayout.closeDrawer(GravityCompat.START);
        int itemId = item.getItemId();
        Class target = null;
        Class dummyClass = UserProfileActivity.class;
        switch (itemId){
            case R.id.sideNavHome:
                target = MainActivity.class;
                break;
            case R.id.sideNavLogin:
            case R.id.sideNavLogout:
                logout();
                break;
            case R.id.sideNavExit:
                endAppUnlessCancelled();
                break;
            case R.id.sideNavProfile:
            default:
                target = dummyClass;
                break;
        }
        if(currentActivity != target && target != null){
            startActivity(new Intent(BaseActivityWithNav.this, target));
        }

        return true;
    }

    //Logout button
    public void logout(){
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null){
                    Toast.makeText(BaseActivityWithNav.this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(BaseActivityWithNav.this, Login.class);
                    //Clear previous tasks and activities and start afresh with Login activity as base activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
        mAuth.signOut();
    }

    public void endAppUnlessCancelled(){
        final Handler myHandler = new Handler();
        final Runnable exitApp = new Runnable() {
            @Override
            public void run() {
                BaseActivityWithNav.super.onBackPressed();
            }
        };
        myHandler.postDelayed(exitApp, 3000);
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.exit_snackbar, BaseTransientBottomBar.LENGTH_LONG);
        mySnackbar.setAction(R.string.snackbar_exit_abort, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler.removeCallbacks(exitApp);
            }
        });
        mySnackbar.show();
    }

    public void onBackPressed() {
        //Require back button pressed twice to exit
        if(backPressedOnce){
            super.onBackPressed();
        }

        //Or If drawer is open close it on first back press
        else if(sideNavDrawerLayout.isDrawerOpen(GravityCompat.START)){
            sideNavDrawerLayout.closeDrawer(GravityCompat.START);
        }
        //Or If there's a previous activity, move back to it
        else if(!isTaskRoot()){
            super.onBackPressed();
        }
        else{
            backPressedOnce = true;
            //wait for 2 seconds until you reset backPressedOnce to false
            Toast.makeText(BaseActivityWithNav.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            resetBackPressedAfterSetTime(1900);
        }

    }

    //Wait for back to be pressed again in order to reset
    public void resetBackPressedAfterSetTime(int timeInMilliseconds){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, timeInMilliseconds);
    }
}
