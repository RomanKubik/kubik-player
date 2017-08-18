package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.widgets.AnimatedGridRecyclerView;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.audiolist.di.AudioListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 8/17/17.
 */

@RuntimePermissions
public class AudioListActivity extends AppCompatActivity implements AudioListPresenter.View {

    @BindView(R.id.rv_audio_list)
    AnimatedGridRecyclerView rvAudioList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    AudioListPresenter presenter;

    @Inject
    AudioListAdapter audioListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        component.audioListComponent(new AudioListModule(this)).inject(this);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
        AudioListActivityPermissionsDispatcher.requestMediaWithCheck(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showProgress(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AudioListActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onTrackListReceived(List<Track> trackList) {
        rvAudioList.scheduleLayoutAnimation();
        audioListAdapter.addData(trackList);
    }

    @Override
    public void showProgress(boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_my_library);
        }
    }

    private void initRecyclerView() {
        rvAudioList.setAdapter(audioListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAudioList.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_tiny);
        rvAudioList.addItemDecoration(new AudioColumnDecoration(spacingInPixels));
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestMedia() {
        presenter.readMediaFromDevice();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.msg_request_storage_permission)
                .setPositiveButton(R.string.btn_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.btn_deny, (dialog, button) -> request.cancel())
                .show();
    }

}