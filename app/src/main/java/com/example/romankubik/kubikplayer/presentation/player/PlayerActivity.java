package com.example.romankubik.kubikplayer.presentation.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.presentation.player.animation.AnimatorPath;
import com.example.romankubik.kubikplayer.presentation.player.animation.PathEvaluator;
import com.example.romankubik.kubikplayer.presentation.player.animation.PathPoint;
import com.example.romankubik.kubikplayer.presentation.player.di.PlayerModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.romankubik.kubikplayer.general.android.PlayerApplication.component;

/**
 * Created by roman.kubik on 9/4/17.
 */

public class PlayerActivity extends AppCompatActivity implements PlayerPresenter.View {

    @BindView(R.id.sb_volume)
    SeekBar sbVolume;
    @BindView(R.id.sb_music)
    SeekBar pbMusic;
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
    @BindView(R.id.cl_details)
    ConstraintLayout clDetails;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.fab_holder)
    FrameLayout flFabContainer;

    @Inject
    PlayerPresenter playerPresenter;

    public final static float SCALE_FACTOR = 6f;
    public final static int ANIMATION_DURATION = 600;

    private boolean mRevealFlag;
    private float mFabSize;

    private Track track;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        component.playerComponent(new PlayerModule(this)).inject(this);
        ButterKnife.bind(this);
        getExtras();
    }

    @Override
    public void onTrackReceived(Track track) {
        this.track = track;
        tvDetails.setText(track.getSong());
        tvSong.setText(track.getAlbum());
        tvArtist.setText(track.getArtist());
        if (track.getImage() != null) {
            getWindow().setStatusBarColor(track.getPrimaryColor());
            clDetails.setBackgroundColor(track.getSecondaryColor());
            clNavigation.setBackgroundColor(track.getPrimaryColor());
            fabPlay.setBackgroundTintList(ColorStateList.valueOf(track.getPrimaryColor()));
            ivPlayBack.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivPlayForward.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivPlayPause.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivVolumeDown.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
            ivVolumeUp.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
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

    @OnClick(R.id.fab_play)
    public void onFabPressed() {
        mFabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
        AnimatorPath path = new AnimatorPath();
        float sX = fabPlay.getY();
        float startX = fabPlay.getTranslationX();
        float startY = fabPlay.getTranslationY() + (mFabSize / 2);
        float endX = -ivPlayPause.getX() + (mFabSize / 2);
        float endY = ivPlayPause.getY() + startY + (mFabSize / 2);
        path.moveTo(startX, startY);
        path.curveTo(startX, startY, startX, endY, endX, endY);

        final ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc",
                new PathEvaluator(), path.getPoints().toArray());

        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();

        anim.addUpdateListener(animation -> {
            if (Math.abs(sX - fabPlay.getY()) > mFabSize) ivLogo.bringToFront();
            if (!mRevealFlag) {
                fabPlay.animate()
                        .scaleXBy(SCALE_FACTOR)
                        .scaleYBy(SCALE_FACTOR)
                        .setInterpolator(new AccelerateInterpolator(5f))
                        .setListener(mEndRevealListener)
                        .setDuration(ANIMATION_DURATION);
                tvArtist.animate()
                        .alpha(0)
                        .setDuration(ANIMATION_DURATION)
                        .start();
                tvSong.animate()
                        .alpha(0)
                        .setDuration(ANIMATION_DURATION)
                        .start();
                mRevealFlag = true;
            }
        });
    }

    private AnimatorListenerAdapter mEndRevealListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            fabPlay.setVisibility(View.INVISIBLE);
            fabPlay.setScaleX(0);
            fabPlay.setScaleY(0);
            clDetails.setVisibility(View.INVISIBLE);
            clNavigation.setBackgroundColor(track.getPrimaryColor());

            clNavigation.setScaleX(1);
            clNavigation.setScaleY(1);

            for (int i = 0; i < clNavigation.getChildCount(); i++) {
                View v = clNavigation.getChildAt(i);
                ViewPropertyAnimator animator = v.animate()
                        .scaleX(1)
                        .scaleY(1)
                        .setDuration(ANIMATION_DURATION);

                animator.setStartDelay(i * 50);
                animator.start();
            }
        }
    };

    /**
     * We need this setter to translate between the information the animator
     * produces (a new "PathPoint" describing the current animated location)
     * and the information that the button requires (an xy location). The
     * setter will be called by the ObjectAnimator given the 'fabLoc'
     * property string.
     */
    public void setFabLoc(PathPoint newLoc) {
        fabPlay.setTranslationX(newLoc.mX);

        if (mRevealFlag)
            fabPlay.setTranslationY(newLoc.mY - (mFabSize / 2));
        else
            fabPlay.setTranslationY(newLoc.mY);
    }

}
