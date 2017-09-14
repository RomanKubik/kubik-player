package com.example.romankubik.kubikplayer.interaction.player.di;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman.kubik on 8/16/17.
 */

@Module
public class PlayerModule {

    private MusicPlayerService musicPlayer;

    @Singleton
    @Provides
    MusicPlayerService provideMusicPlayer(Context context) {
        if (musicPlayer == null) {
            Intent intent = new Intent(context, MusicPlayerService.class);
            context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        return musicPlayer;
    }

    @Singleton
    @Provides
    Interactor.Player providePlayer(Context context) {
        return provideMusicPlayer(context);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to PlayerBinder, cast the IBinder and get PlayerBinder instance
            MusicPlayerService.PlayerBinder binder = (MusicPlayerService.PlayerBinder) service;
            musicPlayer = binder.getMusicService();
//            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
//            mBound = false;
        }
    };
}
