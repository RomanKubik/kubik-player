package com.example.romankubik.kubikplayer.interaction.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.support.v4.content.ContextCompat;

import com.example.romankubik.kubikplayer.R;
import com.example.romankubik.kubikplayer.general.Constants;
import com.example.romankubik.kubikplayer.interaction.entity.Track;

import java.io.File;

/**
 * Map audio metadata to {@link Track}
 * Created by roman.kubik on 8/18/17.
 */

public class MediaMapper {

    public static Track mapFileToTrack(File file, Context context) {
        Track track = new Track();
        track.setPath(file.getPath());
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(file.getPath());
        if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) != null)
            track.setSong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        else track.setSong(file.getName());
        track.setAlbum(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        String artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        track.setArtist(artist != null ? artist : context.getString(R.string.unknown));
        byte[] img = metadataRetriever.getEmbeddedPicture();
        if (img != null) {
            track.setImage(BitmapFactory.decodeByteArray(img, 0, img.length));
        } else {
            track.setDefaultImage(getBitmapFromVectorDrawable(context,
                    R.drawable.ic_album_gray_light_dark_24dp));
        }
        return track;
    }

    private static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(Constants.Dimens.LOGO_DIMENS,
                Constants.Dimens.LOGO_DIMENS, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
