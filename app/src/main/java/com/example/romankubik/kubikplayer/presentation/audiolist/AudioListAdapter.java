package com.example.romankubik.kubikplayer.presentation.audiolist;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.tv_song)
        TextView tvSong;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.cl_holder)
        ConstraintLayout clBackground;

        public AudioListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setItem(Track track) {
            tvSong.setText(track.getSong());
            if (track.getArtist() != null) tvArtist.setText(track.getArtist());
            else tvArtist.setText(R.string.unknown);
            if (track.getImage() != null) {
                ivPoster.setImageBitmap(track.getImage());
                tvSong.setTextColor(track.getTitleColor());
                tvArtist.setTextColor(track.getTitleColor());
                clBackground.setBackgroundColor(track.getPrimaryColor());
            }
            else ivPoster.setImageDrawable(context.getDrawable(R.drawable.ic_album_black_24dp));
        }
    }
}
