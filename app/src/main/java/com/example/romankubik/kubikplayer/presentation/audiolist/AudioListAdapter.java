package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roman.kubik on 8/17/17.
 */

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioListHolder> {

    private Context context;

    private List<Track> trackList = new ArrayList<>();

    @Inject
    public AudioListAdapter() {
    }

    public void addData(List<Track> trackList) {
        Optional.ofNullable(trackList)
                .ifPresent(t -> {
                    this.trackList.addAll(t);
                    notifyDataSetChanged();
                });
    }

    public void clearData() {
        trackList.clear();
        notifyDataSetChanged();
    }

    @Override
    public AudioListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AudioListHolder(inflater.inflate(R.layout.item_audio_list, parent, false));
    }

    @Override
    public void onBindViewHolder(AudioListHolder holder, int position) {
        holder.setItem(trackList.get(position));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class AudioListHolder extends RecyclerView.ViewHolder {

        static final int ANIMATION_DURATION = 1500;
        static final float FACTOR = 7.0f;
        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.tv_song)
        TextView tvSong;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.cl_holder)
        ConstraintLayout clBackground;
        @BindView(R.id.iv_starred)
        ImageView ivStarred;

        public AudioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setItem(Track track) {
            tvSong.setText(track.getSong());
            if (track.getArtist() != null) tvArtist.setText(track.getArtist());
            else tvArtist.setText(R.string.unknown);
            if (track.getImage() != null) {
                prepareAnimation(
                        context.getResources().getColor(R.color.gray_dark),
                        track.getPrimaryColor(),
                        a -> clBackground.setBackgroundColor((int) a.getAnimatedValue()))
                        .start();

                prepareAnimation(
                        context.getResources().getColor(R.color.gray),
                        track.getTitleColor(),
                        a -> {
                            ivStarred.setColorFilter((int) a.getAnimatedValue(), PorterDuff.Mode.MULTIPLY);
                            tvSong.setTextColor((int) a.getAnimatedValue());
                            tvArtist.setTextColor((int) a.getAnimatedValue());
                        })
                        .start();
                ivPoster.setImageBitmap(track.getImage());
            } else ivPoster.setImageDrawable(context.getDrawable(R.drawable.ic_album_black_24dp));
        }

        private ValueAnimator prepareAnimation(int startColor, int endColor,
                                               ValueAnimator.AnimatorUpdateListener updateListener) {
            ValueAnimator valueAnimator
                    = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
            valueAnimator.setInterpolator(new DecelerateInterpolator(FACTOR));
            valueAnimator.setStartDelay(ANIMATION_DURATION);
            valueAnimator.setDuration(ANIMATION_DURATION);
            valueAnimator.addUpdateListener(updateListener);
            return valueAnimator;
        }
    }
}
