package com.example.romankubik.kubikplayer.interaction.finder.di;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.finder.MediaFinder;

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
    public MediaFinder provideMediaFinder() {
        if (mediaFinder == null)
            mediaFinder = new MediaFinder();
        return mediaFinder;
    }

    @Singleton
    @Provides
    public Interactor.Finder providesFinder() {
        return provideMediaFinder();
    }

}
