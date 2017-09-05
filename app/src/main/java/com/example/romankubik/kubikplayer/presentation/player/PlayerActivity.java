package com.example.romankubik.kubikplayer.presentation.player;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.player.di.PlayerModule;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class PlayerActivity extends AppCompatActivity implements PlayerPresenter.View {

    @BindView(R.id.sb_volume)
    SeekBar sbVolume;
    @BindView(R.id.pb_music)
    ProgressBar pbMusic;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.cl_navigation)
    ConstraintLayout clNavigation;
    @BindView(R.id.iv_play_back)
    ImageView ivPlayBack;
    @BindView(R.id.iv_play_forward)
    ImageView ivPlayForward;
    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;
    @BindView(R.id.iv_volume_down)
    ImageView ivVolumeDown;
    @BindView(R.id.iv_volume_up)
    ImageView ivVolumeUp;
    @BindView(R.id.fab_play)
    FloatingActionButton fabPlay;

    @Inject
    PlayerPresenter playerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        component.playerComponent(new PlayerModule(this)).inject(this);
        ButterKnife.bind(this);
        getExtras();
    }

    @Override
    public void showError(String message) {

    }

    private void getExtras() {
        Track track = Parcels.unwrap(getIntent().getParcelableExtra(Constants.Intent.TRACK_EXTRA));
        playerPresenter.setTrack(track);
        onTrackReceived(track);
    }

    private void onTrackReceived(Track track) {
        if (track.getArtist() != null) tvDetails.setText(track.getArtist() + " / "+ track.getSong());
        else tvDetails.setText(track.getSong());
        if (track.getImage() != null) {
            clNavigation.setBackgroundColor(track.getPrimaryColor());
            fabPlay.setBackgroundTintList(ColorStateList.valueOf(track.getPrimaryColor()));
            tvDetails.setTextColor(track.getBodyColor());
            ivLogo.setImageBitmap(track.getBitmapImage());
            tvDetails.setBackgroundColor(track.getSecondaryColor());
            ivPlayBack.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivPlayForward.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivPlayPause.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivVolumeDown.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivVolumeUp.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        }
    }

    private void initViews() {

    }

}
