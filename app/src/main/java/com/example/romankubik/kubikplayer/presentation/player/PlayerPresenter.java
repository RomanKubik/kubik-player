package com.example.romankubik.kubikplayer.presentation.player;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.interaction.media.MediaMapper;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayerService;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class PlayerPresenter {

    private Track track;

    private Interactor.Player player;

    public interface View {
        void onTrackReceived(Track track);

        void showError(String message);
    }

    private View view;
    private Interactor interactor;

    @Inject
    public PlayerPresenter(View view, Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void setTrack(String trackPath) {
        File file = new File(trackPath);
        track = MediaMapper.mapFileToTrack(file);
        view.onTrackReceived(track);
    }

    public void attach(MusicPlayerService musicService) {
        this.player = musicService;
    }

    public void detach() {
        this.player = null;
    }

    public void play() {
        player.forcePlay(track);
    }

    public void pause() {
        player.playPause();
    }

    public void forward() {

    }

    public void backward() {

    }

    public void louder() {

    }

    public void quieter() {

    }


}
