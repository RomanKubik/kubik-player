package com.example.romankubik.kubikplayer.interaction.media.di;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.media.MediaFinder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/18/17.
 */

@Module
public class FinderModule {

    private MediaFinder mediaFinder;

    @Singleton
    @Provides
    public MediaFinder provideMediaFinder(Context context) {
        if (mediaFinder == null)
            mediaFinder = new MediaFinder(context);
        return mediaFinder;
    }

    @Singleton
    @Provides
    public Interactor.Finder providesFinder(Context context) {
        return provideMediaFinder(context);
    }

}
