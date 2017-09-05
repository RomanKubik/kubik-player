package com.example.romankubik.kubikplayer.presentation.player;

import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import javax.inject.Inject;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class PlayerPresenter {

    private Track track;

    public interface View {
        void showError(String message);
    }

    private View view;
    private Interactor interactor;

    @Inject
    public PlayerPresenter(View view, Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
