package com.example.romankubik.kubikplayer.interaction.media;

import android.media.MediaMetadataRetriever;

import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.io.File;

/**
 * Map audio metadata to {@link Track}
 * Created by roman.kubik on 8/18/17.
 */

public class MediaMapper {

    public static Track mapFileToTrack(File file) {
        Track track = new Track();
        track.setPath(file.getPath());
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(file.getPath());
        if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) != null)
            track.setSong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        else track.setSong(file.getName());
        track.setAlbum(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        track.setArtist(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        track.setImage(metadataRetriever.getEmbeddedPicture());
        return track;
    }
}
