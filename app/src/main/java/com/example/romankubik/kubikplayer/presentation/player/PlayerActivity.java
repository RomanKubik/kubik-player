package com.example.romankubik.kubikplayer.presentation.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import butterknife.BindDrawable;
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

    @BindDrawable(R.drawable.ic_play_arrow_white_24dp)
    Drawable icPlayArrow;
    @BindDrawable(R.drawable.ic_pause_white_24dp)
    Drawable icPause;

    @Inject
    PlayerPresenter playerPresenter;

    private boolean revealFlag;
    private float fabSize;
    private boolean playingTrackFlag;

    private Track track;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        component.playerComponent(new PlayerModule(this)).inject(this);
        bindService();
        ButterKnife.bind(this);
        addTransitionListener();
        addSeekBarChangeListeners();
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
        playingTrackFlag = trackPlaying;
        tvDetails.setText(track.getSong());
        tvDetails.setBackgroundColor(Constants.Color.WHITE);
        tvSong.setText(track.getAlbum());
        tvArtist.setText(track.getArtist());
        getWindow().setStatusBarColor(track.getPrimaryColor());
        clDetails.setBackgroundColor(track.getSecondaryColor());
        clNavigation.setBackgroundColor(track.getPrimaryColor());
        icPlayArrow.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        fabPlay.setImageDrawable(icPlayArrow);
        fabPlay.setBackgroundTintList(ColorStateList.valueOf(track.getPrimaryColor()));
        ivPlayBack.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivPlayForward.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivPlayPause.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivVolumeDown.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivVolumeUp.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivLogo.setImageBitmap(track.getImage());
    }

    @Override
    public void onProgressChanged(int progress) {
        sbMusic.setProgress(progress);
    }

    @Override
    public void onVolumeChanged(int level) {
        sbVolume.setProgress(level);
    }

    @Override
    public void onTrackChanged(Track track) {
        tvDetails.setText(track.getSong());
        tvDetails.setTextColor(track.getBodyColor());
        getWindow().setStatusBarColor(track.getPrimaryColor());
        ivPlayBack.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivPlayForward.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivPlayPause.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivVolumeDown.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        ivVolumeUp.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
        imageViewAnimatedChange(this, ivLogo, track.getImage());
        animateBackgroundColor(tvDetails, track.getSecondaryColor());
        animateBackgroundColor(clNavigation, track.getPrimaryColor());
    }

    private void animateBackgroundColor(View view, int colorTo) {
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        int colorFrom = colorDrawable.getColor();
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(SHORTER_ANIMATION_DURATION);
        colorAnimation.addUpdateListener(animator -> view.setBackgroundColor((int) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    @Override
    public void onPlayPause(boolean playing) {
        if (playing)
            ivPlayPause.setImageDrawable(icPause);
        else
            ivPlayPause.setImageDrawable(icPlayArrow);
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

    private void addSeekBarChangeListeners() {
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

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    playerPresenter.setVolume(progress);
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
            public void onTransitionStart(Transition transition) {/*ignored*/}

            @Override
            public void onTransitionEnd(Transition transition) {
                if (playingTrackFlag) {
                    clDetails.animate()
                            .alpha(0f)
                            .setDuration(ANIMATION_DURATION * 2)
                            .setInterpolator(new DecelerateInterpolator(MATERIAL_INTERPOLATOR_FACTOR));
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
                } else {
                    showFab();
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {/*ignored*/}

            @Override
            public void onTransitionPause(Transition transition) {/*ignored*/}

            @Override
            public void onTransitionResume(Transition transition) {/*ignored*/}
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

        if (revealFlag)
            fabPlay.setTranslationY(newLoc.mY - (fabSize / 2));
        else
            fabPlay.setTranslationY(newLoc.mY);
    }

    @OnClick(R.id.fab_play)
    public void onFabPressed() {
        playerPresenter.play();
        fabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
        AnimatorPath path = new AnimatorPath();
        float sX = fabPlay.getY();
        float startX = fabPlay.getTranslationX();
        float startY = fabPlay.getTranslationY() + (fabSize / 2);
        float endX = -ivPlayPause.getX() + (fabSize / 2);
        float endY = ivPlayPause.getY() + startY + (fabSize / 2);
        path.moveTo(startX, startY);
        path.curveTo(startX, startY, startX, endY, endX, endY);

        final ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc",
                new PathEvaluator(), path.getPoints().toArray());

        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();

        anim.addUpdateListener(animation -> {
            if (Math.abs(sX - fabPlay.getY()) > fabSize) ivLogo.bringToFront();
            if (!revealFlag) {
                animateViewsOnFab();
                revealFlag = true;
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

    public static void imageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_in.setDuration(SHORTER_ANIMATION_DURATION);
        anim_out.setDuration(SHORTER_ANIMATION_DURATION);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {/*ignored*/}

            @Override
            public void onAnimationRepeat(Animation animation) {/*ignored*/}

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

}
