package com.godavari.digittowordlearner.listeners;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.godavari.digittowordlearner.R;
import com.godavari.digittowordlearner.utility.RateMeUtility;
import com.godavari.digittowordlearner.utility.ShareUtility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyCustomNavigationItemSelected implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Context context;

    public MyCustomNavigationItemSelected(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miExitTheApp:
                ((Activity) context).finish();
                return true;
            case R.id.miRateTheApp:
                rateMe();
                return true;
            case R.id.miShareTheApp:
                shareTheApp();
                return true;
        }
        return false;
    }

    private void shareTheApp() {
        ShareUtility.shareTheApp(context);
    }

    public void rateMe() {
        RateMeUtility.rateMe(context);
    }
}
