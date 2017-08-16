package com.example.romankubik.kubikplayer.general.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Module
public class ApplicationModule {

    private Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    public Context getApplicationContext() {
        return applicationContext;
    }
}
