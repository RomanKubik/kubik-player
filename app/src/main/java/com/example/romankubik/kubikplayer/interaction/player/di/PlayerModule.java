package com.example.romankubik.kubikplayer.interaction.player.di;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 9/15/17.
 */

@Module
public class PlayerModule {

    private MusicPlayer musicPlayer;

    @Singleton
    @Provides
    public MusicPlayer provideMusicPlayer(Context context) {
        if (musicPlayer == null)
            musicPlayer = new MusicPlayer(context);
        return musicPlayer;
    }

    @Singleton
    @Provides
    public Interactor.Player providePlayer(Context context) {
        return provideMusicPlayer(context);
    }

}
