package com.example.romankubik.kubikplayer.general.android;

import android.app.Application;

import com.example.romankubik.kubikplayer.general.di.ApplicationComponent;
import com.example.romankubik.kubikplayer.general.di.ApplicationModule;
import com.example.romankubik.kubikplayer.general.di.DaggerApplicationComponent;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class PlayerApplication extends Application {

    public static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }
}
