package com.example.romankubik.kubikplayer.interaction.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman.kubik on 9/15/17.
 */

public class PlayList {

    private static List<Track> actualPlayList = new ArrayList<>();

    public static void addToActualList(Track track) {
        actualPlayList.add(track);
    }

    public static List<Track> setActualPlayList(List<Track> playList) {
        actualPlayList.clear();
        actualPlayList.addAll(playList);
        return actualPlayList;
    }

    public static List<Track> getActualPlayList() {
        return actualPlayList;
    }


}
