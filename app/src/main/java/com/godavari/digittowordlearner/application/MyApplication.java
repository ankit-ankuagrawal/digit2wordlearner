package com.godavari.digittowordlearner.application;

import android.app.Application;
import android.content.Context;

import com.godavari.digittowordlearner.utility.LocaleLanguageHelper;

public class MyApplication extends Application {
    private static Context context;

    public MyApplication() {
        super.onCreate();
        this.context = this;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleLanguageHelper.onAttach(base, "en"));
    }

    public static Context getContext() {
        return context;
    }
}
