package com.example.romankubik.kubikplayer.presentation.player.di;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.presentation.player.PlayerActivity;

import dagger.Subcomponent;

/**
 * Created by roman.kubik on 9/4/17.
 */

@ActivityScope
@Subcomponent(modules = PlayerModule.class)
public interface PlayerComponent {
    void inject(PlayerActivity activity);
}
