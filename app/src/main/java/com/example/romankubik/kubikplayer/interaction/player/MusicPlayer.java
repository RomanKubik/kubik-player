package com.example.romankubik.kubikplayer.interaction.player;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.Interactor;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class MusicPlayer implements Interactor.Player {

    private Context context;

    public MusicPlayer(Context context) {
        this.context = context;
    }
}
