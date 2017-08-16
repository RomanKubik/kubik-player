package com.example.romankubik.kubikplayer.interaction;

import javax.inject.Inject;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class Interactor {

    public interface Player {

    }


    private Player player;

    @Inject
    public Interactor(Player player) {
        this.player = player;
    }
}
