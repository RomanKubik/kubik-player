package com.example.romankubik.kubikplayer.presentation.player;

import com.annimon.stream.Stream;
import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.PlayList;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayerService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class PlayerPresenter {

    private Track track;

    private Interactor.Player player;

    private CompositeDisposable compositeDisposable;

    public interface View {
        void onTrackReceived(Track track, boolean trackPlaying);

        void onProgressChanged(int progress);

        void onTrackChanged(Track track);

        void onPlayPause(boolean playing);

        void showError(String message);
    }

    private View view;
    private Interactor interactor;

    @Inject
    public PlayerPresenter(View view, Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.compositeDisposable = new CompositeDisposable();
    }

    private void subscribeObservers() {
        compositeDisposable.add(
                this.player.playProgress().subscribe(p -> view.onProgressChanged(p)));
        compositeDisposable.add(
                this.player.isPlaying().subscribe(p -> view.onPlayPause(p)));
        compositeDisposable.add(
                this.player.currentTrack().subscribe(t -> view.onTrackChanged(t)));
    }

    public void setTrack(String trackId) {
        Stream.of(PlayList.getActualPlayList())
                .filter(t -> t.getId().equals(trackId))
                .findFirst()
                .map(t -> track = t)
                .ifPresent(t -> view.onTrackReceived(track, player.isTrackPlaying(track)));
    }

    public void attach(MusicPlayerService musicService) {
        this.player = musicService;
        subscribeObservers();
    }

    public void detach() {
        this.player = null;
        compositeDisposable.clear();
    }

    public void play() {
        player.forcePlay(track);
    }

    public void pause() {
        player.playPause();
    }

    public void forward() {
        player.forward();
    }

    public void backward() {
        player.backward();
    }

    public void louder() {

    }

    public void quieter() {

    }

    public void setProgress(int progress) {
        player.setProgress(progress);
    }


}
