package com.example.romankubik.kubikplayer.presentation.audiolist;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by roman.kubik on 8/17/17.
 */

@ActivityScope
public class AudioListPresenter {

    public interface View {
        void onTrackListReceived(List<Track> trackList);
    }

    private View view;

    @Inject
    public AudioListPresenter(View view) {
        this.view = view;
    }

    public void onCreated() {
        view.onTrackListReceived(getTrackList());
    }

    private List<Track> getTrackList() {
        List<Track> trackList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Track track = new Track();
            track.setSong("Song: " + i);
            track.setArtist("Artist: " + i);
            track.setAlbum("Album: " + i);
            trackList.add(track);
        }
        return trackList;
    }

}
