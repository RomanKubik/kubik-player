package com.example.romankubik.kubikplayer.interaction;

import com.example.romankubik.kubikplayer.interaction.entity.Track;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class Interactor {

    public interface Player {
        void forcePlay(Track track);

        void playPause();

        void forward();

        void backward();

        void louder();

        void quieter();

        void setProgress(int position);

        boolean isTrackPlaying(Track track);

        Observable<Boolean> isPlaying();

        Observable<Track> currentTrack();

        Observable<Integer> playProgress();
    }

    public interface Finder {
        Observable<Track> findAllMusicFiles();
    }


    private Player player;
    private Finder finder;

    @Inject
    public Interactor(Finder finder) {
        this.finder = finder;
    }

    public Player player() {
        return player;
    }

    public Finder finder() {
        return finder;
    }
}
