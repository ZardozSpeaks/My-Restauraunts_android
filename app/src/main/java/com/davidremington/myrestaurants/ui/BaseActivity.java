package com.davidremington.myrestaurants.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.davidremington.myrestaurants.BuildConfig;

import timber.log.Timber;

abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Timber.forest().size() == 0 || BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
