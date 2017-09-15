package com.example.romankubik.kubikplayer.interaction.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman.kubik on 9/15/17.
 */

public class PlayList {

    private static List<Track> actualPlayList = new ArrayList<>();

    public static void setActualPlayList(List<Track> playList) {
        actualPlayList.clear();
        actualPlayList.addAll(playList);
    }

    public static List<Track> getActualPlayList() {
        return actualPlayList;
    }


}
