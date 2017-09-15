package com.example.romankubik.kubikplayer.interaction;

import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class Interactor {

    public interface Player {
        public void play(Track track);

        public void pause();

        public void forward();

        public void backward();

        public void louder();

        public void quieter();
    }

    public interface Finder {
        Observable<File> findAllMusicFiles();
    }


    private Player player;
    private Finder finder;

    @Inject
    public Interactor(Player player, Finder finder) {
        this.player = player;
        this.finder = finder;
    }

    public Player player() {
        return player;
    }

    public Finder finder() {
        return finder;
    }
}
