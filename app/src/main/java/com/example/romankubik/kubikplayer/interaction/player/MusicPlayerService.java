package com.example.romankubik.kubikplayer.interaction.player;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.Interactor;
import com.example.romankubik.kubikplayer.interaction.entity.PlayList;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class MusicPlayerService extends Service implements Interactor.Player, ExoPlayer.EventListener {

    @Inject
    Interactor interactor;

    private ExoPlayer exoPlayer;
    private BandwidthMeter bandwidthMeter;
    private Notification playerNotification;

    private BehaviorSubject<Track> currentTrack = BehaviorSubject.create();
    private int trackPosition;

    private final IBinder playerBinder = new PlayerBinder();

    public class PlayerBinder extends Binder {
        public MusicPlayerService getMusicService() {
            return MusicPlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playerBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component.inject(this);
        initPlayer();
        prepareNotification();
    }

    // region Interactor.Player

    @Override
    public void forcePlay(Track track) {
        startForeground(Constants.Service.SERVICE_ID, playerNotification);
        trackPosition = PlayList.getActualPlayList().indexOf(track);
        playTrack(track);
    }

    @Override
    public void playPause() {
        if (exoPlayer.getPlayWhenReady()) {
            stopForeground(false);
            exoPlayer.setPlayWhenReady(false);
        } else {
            startForeground(Constants.Service.SERVICE_ID, playerNotification);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void forward() {
        if (trackPosition < PlayList.getActualPlayList().size() - 1) {
            trackPosition++;
        } else {
            trackPosition = 0;
        }
        playTrack(PlayList.getActualPlayList().get(trackPosition));
    }

    @Override
    public void backward() {
        if (trackPosition != 0) {
            trackPosition--;
        } else {
            trackPosition = PlayList.getActualPlayList().size() - 1;
        }
        playTrack(PlayList.getActualPlayList().get(trackPosition));
    }

    @Override
    public void louder() {

    }

    @Override
    public void quieter() {

    }

    @Override
    public Observable<Track> currentTrack() {
        return currentTrack;
    }

    // endregion

    // region ExoPlayer Events Listeners

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        //ignored
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        //ignored
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        //ignored
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED)
            forward();
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        //ignored
    }

    @Override
    public void onPositionDiscontinuity() {
        //ignored
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        //ignored
    }

    // endregion

    private void initPlayer() {
        bandwidthMeter = new DefaultBandwidthMeter();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        exoPlayer.addListener(this);
    }

    private void prepareNotification() {
        playerNotification = new Notification.Builder(this).build();
    }

    private MediaSource prepareMediaSource(Track track) {
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "KubikPlayerApp"),
                (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        return new ExtractorMediaSource(Uri.parse(track.getPath()), dataSourceFactory,
                extractorsFactory, null, null);
    }

    private void playTrack(Track track) {
        currentTrack.onNext(track);
        exoPlayer.prepare(prepareMediaSource(track));
        exoPlayer.setPlayWhenReady(true);
    }
}
