package com.example.romankubik.kubikplayer.presentation.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.SeekBar;

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

    private void getExtras() {
        Track track = Parcels.unwrap(getIntent().getParcelableExtra(Constants.Intent.TRACK_EXTRA));
        playerPresenter.setTrack(track);
    }

    private void initViews() {

    }
}
