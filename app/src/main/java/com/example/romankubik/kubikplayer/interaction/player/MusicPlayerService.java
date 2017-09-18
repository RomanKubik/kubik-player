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
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 8/16/17.
 */

public class MusicPlayerService extends Service implements Interactor.Player {

    @Inject
    Interactor interactor;

    private ExoPlayer exoPlayer;
    private BandwidthMeter bandwidthMeter;
    private Notification playerNotification;

    private BehaviorSubject<Track> currentTrack = BehaviorSubject.create();
    private int trackPossition;

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
        trackPossition = PlayList.getActualPlayList().indexOf(track);
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
        if (trackPossition < PlayList.getActualPlayList().size() - 1) {
            trackPossition++;
        } else {
            trackPossition = 0;
        }
        playTrack(PlayList.getActualPlayList().get(trackPossition));
    }

    @Override
    public void backward() {
        if (trackPossition != 0) {
            trackPossition--;
        } else {
            trackPossition = PlayList.getActualPlayList().size() - 1;
        }
        playTrack(PlayList.getActualPlayList().get(trackPossition));
    }

    @Override
    public void louder() {

    }

    @Override
    public void quieter() {

    }

    // endregion

    private void initPlayer() {
        bandwidthMeter = new DefaultBandwidthMeter();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
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
