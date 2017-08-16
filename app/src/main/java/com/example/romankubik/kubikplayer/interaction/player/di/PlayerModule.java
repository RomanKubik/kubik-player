package com.example.romankubik.kubikplayer.interaction.player.di;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Module
public class PlayerModule {

    private MusicPlayer musicPlayer;

    @Singleton
    @Provides
    MusicPlayer provideMusicPlayer(Context context) {
        if (musicPlayer == null)
            musicPlayer = new MusicPlayer(context);
        return musicPlayer;
    }

    @Singleton
    @Provides
    Interactor.Player providePlayer(Context context) {
        return provideMusicPlayer(context);
    }
}
