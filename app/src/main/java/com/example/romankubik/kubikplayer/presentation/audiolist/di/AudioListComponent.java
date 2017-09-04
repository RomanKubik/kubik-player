package com.example.romankubik.kubikplayer.presentation.audiolist.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.audiolist.AudioListActivity;

import dagger.Subcomponent;

/**
 * Created by roman.kubik on 8/17/17.
 */
@ActivityScope
@Subcomponent(modules = AudioListModule.class)
public interface AudioListComponent {
    void inject(AudioListActivity activity);
}
