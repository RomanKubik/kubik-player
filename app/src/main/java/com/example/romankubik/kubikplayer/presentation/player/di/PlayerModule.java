package com.example.romankubik.kubikplayer.presentation.player.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.player.PlayerActivity;
import com.example.romankubik.kubikplayer.presentation.player.PlayerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 9/4/17.
 */

@Module
public class PlayerModule {
    private PlayerActivity activity;

    public PlayerModule(PlayerActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public PlayerPresenter.View getView() {
        return activity;
    }

    @Provides
    @ActivityScope
    public PlayerActivity getActivity() {
        return activity;
    }
}
