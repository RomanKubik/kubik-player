package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.media.MediaMetadataRetriever;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by roman.kubik on 8/17/17.
 */

@ActivityScope
public class AudioListPresenter {

    public interface View {
        void onTrackListReceived(List<Track> trackList);

        void showProgress(boolean show);

        void showError(String message);
    }

    private View view;
    private Interactor interactor;

    @Inject
    public AudioListPresenter(View view, Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void readMediaFromDevice() {
        view.showProgress(true);
        interactor.finder()
                .findAllMusicFiles()
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::fileToTrackMapper)
                .doOnComplete(() -> view.showProgress(false))
                .toList()
                .subscribe(l -> view.onTrackListReceived(l),
                        e -> view.showError("Can't load music from music directory"));
    }

    private Track fileToTrackMapper(File file) {
        Track track = new Track();
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(file.getPath());
        track.setSong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        track.setAlbum(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        track.setArtist(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        return track;
    }

}
