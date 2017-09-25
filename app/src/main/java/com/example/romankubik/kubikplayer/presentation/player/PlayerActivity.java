package com.example.romankubik.kubikplayer.presentation.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;
import com.example.romankubik.kubikplayer.interaction.player.MusicPlayerService;
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

public class PlayerActivity extends AppCompatActivity implements PlayerPresenter.View, ServiceConnection {

    public static final float MATERIAL_INTERPOLATOR_FACTOR = 7f;
    public final static float SCALE_FACTOR = 6f;
    public final static int ANIMATION_DURATION = 600;
    public final static int SHORTER_ANIMATION_DURATION = ANIMATION_DURATION / 4;
    public static final float NORMAL_SCALE = 1f;
    public static final float ZERO_SCALE = 0f;

    @BindView(R.id.sb_volume)
    SeekBar sbVolume;
    @BindView(R.id.sb_music)
    SeekBar sbMusic;
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

    private boolean mRevealFlag;
    private float mFabSize;

    private Track track;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        component.playerComponent(new PlayerModule(this)).inject(this);
        bindService();
        ButterKnife.bind(this);
        addTransitionListener();
        addProgressChangeListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
        playerPresenter.detach();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        playerPresenter.attach(((MusicPlayerService.PlayerBinder) service).getMusicService());
        getExtras();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        playerPresenter.detach();
    }

    @Override
    public void onTrackReceived(Track track, boolean trackPlaying) {
        this.track = track;
//        if (trackPlaying) {
//            tvDetails.setText(track.getSong());
//            fabPlay.setVisibility(View.INVISIBLE);
//            fabPlay.setScaleX(ZERO_SCALE);
//            fabPlay.setScaleY(ZERO_SCALE);
//            clDetails.setVisibility(View.INVISIBLE);
//            clNavigation.setBackgroundColor(track.getPrimaryColor());
//
//            clNavigation.setScaleX(NORMAL_SCALE);
//            clNavigation.setScaleY(NORMAL_SCALE);
//
//            if (track.getImage() != null) {
//                tvDetails.setBackgroundColor(track.getSecondaryColor());
//                tvDetails.setTextColor(track.getBodyColor());
//                ivPlayBack.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
//                ivPlayForward.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
//                ivPlayPause.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
//                ivVolumeDown.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
//                ivVolumeUp.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
//                ivLogo.setImageBitmap(track.getImage());
//            }
//            for (int i = 0; i < clNavigation.getChildCount(); i++) {
//                View v = clNavigation.getChildAt(i);
//                ViewPropertyAnimator animator = v.animate()
//                        .setInterpolator(new DecelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR))
//                        .scaleX(NORMAL_SCALE)
//                        .scaleY(NORMAL_SCALE)
//                        .setDuration(SHORTER_ANIMATION_DURATION);
//
//                animator.setStartDelay(i * 25);
//                animator.start();
//            }
//        } else {
            tvDetails.setText(track.getSong());
            tvSong.setText(track.getAlbum());
            tvArtist.setText(track.getArtist());
            if (track.getImage() != null) {
                getWindow().setStatusBarColor(track.getPrimaryColor());
                clDetails.setBackgroundColor(track.getSecondaryColor());
                clNavigation.setBackgroundColor(track.getPrimaryColor());
                Drawable fabImage = getDrawable(R.drawable.ic_play_arrow_white_24dp);
                fabImage.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                fabPlay.setImageDrawable(fabImage);
                fabPlay.setBackgroundTintList(ColorStateList.valueOf(track.getPrimaryColor()));
                ivPlayBack.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                ivPlayForward.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                ivPlayPause.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                ivVolumeDown.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                ivVolumeUp.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                ivLogo.setImageBitmap(track.getImage());
            }
            if (trackPlaying) {
                fabPlay.setVisibility(View.INVISIBLE);
                fabPlay.setScaleX(ZERO_SCALE);
                fabPlay.setScaleY(ZERO_SCALE);
                clDetails.setVisibility(View.INVISIBLE);
                clNavigation.setBackgroundColor(track.getPrimaryColor());

                clNavigation.setScaleX(NORMAL_SCALE);
                clNavigation.setScaleY(NORMAL_SCALE);

                tvDetails.setBackgroundColor(track.getSecondaryColor());
                tvDetails.setTextColor(track.getBodyColor());

                for (int i = 0; i < clNavigation.getChildCount(); i++) {
                    View v = clNavigation.getChildAt(i);
                    ViewPropertyAnimator animator = v.animate()
                            .setInterpolator(new DecelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR))
                            .scaleX(NORMAL_SCALE)
                            .scaleY(NORMAL_SCALE)
                            .setDuration(SHORTER_ANIMATION_DURATION);

                    animator.setStartDelay(i * 25);
                    animator.start();
                }
            }
//        }

    }

    @Override
    public void onProgressChanged(int progress) {
        sbMusic.setProgress(progress);
    }

    @Override
    public void onTrackChanged(Track track) {
        Log.d("MyTag", "onTrackChanged: " + track.getSong());
    }

    @Override
    public void onPlayPause(boolean playing) {
        Log.d("MyTag", "onPlayPause: " + playing);
    }

    @Override
    public void showError(String message) {

    }

    private void getExtras() {
        String trackId = getIntent().getStringExtra(Constants.Intent.TRACK_EXTRA);
        playerPresenter.setTrack(trackId);
    }

    private void bindService() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        startService(intent);
    }

    private void addProgressChangeListener() {
        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    playerPresenter.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // ignored
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // ignored
            }
        });
    }

    // region animations

    private void addTransitionListener() {
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                showFab();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void showFab() {
        fabPlay.animate()
                .scaleXBy(NORMAL_SCALE)
                .scaleYBy(NORMAL_SCALE)
                .setDuration(ANIMATION_DURATION / 2)
                .start();
    }

    private void animateViewsOnFab() {
        fabPlay.animate()
                .scaleXBy(SCALE_FACTOR)
                .scaleYBy(SCALE_FACTOR)
                .setInterpolator(new AccelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR))
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

        int colorFrom = Color.WHITE;
        int colorTo = track.getSecondaryColor();
        ValueAnimator backgroundAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        backgroundAnimator.setDuration(ANIMATION_DURATION);
        backgroundAnimator.setInterpolator(new AccelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR));
        backgroundAnimator.addUpdateListener(animator
                -> tvDetails.setBackgroundColor((int) animator.getAnimatedValue()));

        colorFrom = tvDetails.getCurrentTextColor();
        colorTo = track.getBodyColor();
        ValueAnimator textAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        textAnimator.setDuration(ANIMATION_DURATION);
        textAnimator.setInterpolator(new AccelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR));
        textAnimator.addUpdateListener(animator
                -> tvDetails.setBackgroundColor((int) animator.getAnimatedValue()));

        textAnimator.start();
        backgroundAnimator.start();
    }

    private AnimatorListenerAdapter mEndRevealListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            fabPlay.setVisibility(View.INVISIBLE);
            fabPlay.setScaleX(ZERO_SCALE);
            fabPlay.setScaleY(ZERO_SCALE);
            clDetails.setVisibility(View.INVISIBLE);
            clNavigation.setBackgroundColor(track.getPrimaryColor());

            clNavigation.setScaleX(NORMAL_SCALE);
            clNavigation.setScaleY(NORMAL_SCALE);

            tvDetails.setBackgroundColor(track.getSecondaryColor());
            tvDetails.setTextColor(track.getBodyColor());

            for (int i = 0; i < clNavigation.getChildCount(); i++) {
                View v = clNavigation.getChildAt(i);
                ViewPropertyAnimator animator = v.animate()
                        .setInterpolator(new DecelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR))
                        .scaleX(NORMAL_SCALE)
                        .scaleY(NORMAL_SCALE)
                        .setDuration(SHORTER_ANIMATION_DURATION);

                animator.setStartDelay(i * 25);
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

    @OnClick(R.id.fab_play)
    public void onFabPressed() {
        playerPresenter.play();
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
                animateViewsOnFab();
                mRevealFlag = true;
            }
        });
    }

    // endregion

    @OnClick(R.id.iv_play_pause)
    void onPlayPauseClicked() {
        playerPresenter.pause();
    }

    @OnClick(R.id.iv_play_back)
    void onPlayBackClicked() {
        playerPresenter.backward();
    }

    @OnClick(R.id.iv_play_forward)
    void onPlayForwardClicked() {
        playerPresenter.forward();
    }

    @OnClick(R.id.iv_volume_up)
    void onVoulumeUpClicked() {

    }

    @OnClick(R.id.iv_volume_down)
    void onVolumeDownClicked() {

    }

}
