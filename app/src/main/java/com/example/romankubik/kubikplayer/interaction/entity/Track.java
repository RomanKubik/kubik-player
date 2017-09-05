package com.example.romankubik.kubikplayer.interaction.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;

import org.parceler.Parcel;

/**
 * Created by roman.kubik on 8/17/17.
 */

@Parcel
public class Track {

    private String path;
    private String artist;
    private String song;
    private String album;
    private byte[] image;
    private int primaryColor;
    private int secondaryColor;
    private int bodyColor;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        if (image != null) {
            this.image = image;
            Palette.Swatch swatch = Palette.from(getBitmapImage()).generate().getDominantSwatch();
            if (swatch != null) {
                primaryColor = swatch.getRgb();
                secondaryColor = swatch.getTitleTextColor();
                bodyColor = swatch.getBodyTextColor();
            }
        }
    }

    public Bitmap getBitmapImage() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public int getBodyColor() {
        return bodyColor;
    }
}
