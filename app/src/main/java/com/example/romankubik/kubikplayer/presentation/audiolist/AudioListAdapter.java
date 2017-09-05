package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private OnItemClickListener onTrackClickListener;

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
        holder.setOnItemClickListener(v -> {
            if (onTrackClickListener != null)
                onTrackClickListener.onItemClicked(holder, trackList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onTrackClickListener = onItemClickListener;
    }

    public class AudioListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_root)
        public LinearLayout llRoot;
        @BindView(R.id.iv_poster)
        public ImageView ivPoster;
        @BindView(R.id.tv_song)
        public TextView tvSong;
        @BindView(R.id.tv_artist)
        public TextView tvArtist;
        @BindView(R.id.cl_holder)
        public ConstraintLayout clBackground;
        @BindView(R.id.iv_starred)
        public ImageView ivStarred;

        private AudioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setItem(Track track) {
            tvSong.setText(track.getSong());
            if (track.getArtist() != null) tvArtist.setText(track.getArtist());
            else tvArtist.setText(R.string.unknown);
            if (track.getImage() != null) {
                clBackground.setBackgroundColor(track.getPrimaryColor());
                ivStarred.setColorFilter(track.getSecondaryColor(), PorterDuff.Mode.MULTIPLY);
                tvSong.setTextColor(track.getSecondaryColor());
                tvArtist.setTextColor(track.getSecondaryColor());
                ivPoster.setImageBitmap(track.getImage());
            } else {
                clBackground.setBackgroundColor(context.getResources().getColor(R.color.gray_light_dark));
                ivStarred.setColorFilter(context.getResources().getColor(R.color.gray), PorterDuff.Mode.MULTIPLY);
                tvSong.setTextColor(context.getResources().getColor(R.color.gray));
                tvArtist.setTextColor(context.getResources().getColor(R.color.gray));
                ivPoster.setImageDrawable(context.getDrawable(R.drawable.ic_album_gray_light_dark_24dp));
            }
        }

        private void setOnItemClickListener(View.OnClickListener onClickListener) {
            llRoot.setOnClickListener(onClickListener);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(AudioListHolder itemHolder, Track track);
    }
}
