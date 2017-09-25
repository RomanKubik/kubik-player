package com.example.romankubik.kubikplayer.presentation.audiolist;

import com.example.romankubik.kubikplayer.general.di.ActivityScope;
import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.PlayList;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> view.showProgress(false))
                .toList()
                .map(PlayList::setActualPlayList)
                .subscribe(l -> view.onTrackListReceived(l),
                        e -> {
                            view.showError("Can't load music from music directory");
                            e.printStackTrace();
                        });
    }

}
