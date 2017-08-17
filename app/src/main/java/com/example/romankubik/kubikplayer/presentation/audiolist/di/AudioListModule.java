package com.example.romankubik.kubikplayer.presentation.audiolist.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.audiolist.AudioListActivity;
import com.example.romankubik.kubikplayer.presentation.audiolist.AudioListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/17/17.
 */

@Module
public class AudioListModule {

    private AudioListActivity activity;

    public AudioListModule(AudioListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public AudioListPresenter.View getView() {
        return activity;
    }

    @Provides
    @ActivityScope
    public AudioListActivity getActivity() {
        return activity;
    }
}
