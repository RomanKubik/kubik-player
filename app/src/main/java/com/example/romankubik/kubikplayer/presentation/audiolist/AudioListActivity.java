package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.widgets.AnimatedGridRecyclerView;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.audiolist.di.AudioListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 8/17/17.
 */

public class AudioListActivity extends AppCompatActivity implements AudioListPresenter.View {

    @BindView(R.id.rv_audio_list)
    AnimatedGridRecyclerView rvAudioList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    AudioListPresenter presenter;

    private AudioListAdapter audioListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        component.audioListComponent(new AudioListModule(this)).inject(this);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
        presenter.onCreated();
    }

    @Override
    public void onTrackListReceived(List<Track> trackList) {
        audioListAdapter.addData(trackList);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_my_library);
        }
    }

    private void initRecyclerView() {
        audioListAdapter = new AudioListAdapter();
        rvAudioList.setAdapter(audioListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAudioList.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_tiny);
        rvAudioList.addItemDecoration(new AudioColumnDecoration(spacingInPixels));
        rvAudioList.scheduleLayoutAnimation();
    }

}
