package com.example.romankubik.kubikplayer.interaction.player;

import android.content.Context;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class MusicPlayer implements Interactor.Player {

    private Context context;

    public MusicPlayer(Context context) {
        this.context = context;
    }

    @Override
    public void play(Track track) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void forward() {

    }

    @Override
    public void backward() {

    }

    @Override
    public void louder() {

    }

    @Override
    public void quieter() {

    }
}
