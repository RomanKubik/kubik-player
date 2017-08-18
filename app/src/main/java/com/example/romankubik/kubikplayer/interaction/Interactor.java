package com.example.romankubik.kubikplayer.interaction;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class Interactor {

    public interface Player {

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
