package com.example.romankubik.kubikplayer.presentation.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.player.di.PlayerModule;

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

    @Inject
    PlayerPresenter playerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        component.playerComponent(new PlayerModule(this)).inject(this);
        ButterKnife.bind(this);
        getExtras();
        initViews();
    }

    @Override
    public void onTrackReceived(Track track) {
        if (track.getArtist() != null) tvDetails.setText(track.getArtist() + " / "+ track.getSong());
        else tvDetails.setText(track.getSong());
        if (track.getImage() != null) {
            clNavigation.setBackgroundColor(track.getPrimaryColor());
//            ivStarred.setColorFilter(track.getTitleColor(), PorterDuff.Mode.MULTIPLY);
            tvDetails.setTextColor(track.getTitleColor());
            ivLogo.setImageBitmap(track.getImage());
        }
    }

    @Override
    public void showError(String message) {

    }

    private void getExtras() {
        String trackPath = getIntent().getStringExtra(Constants.Intent.TRACK_EXTRA);
        playerPresenter.setTrack(trackPath);
    }

    private void initViews() {

    }

}
