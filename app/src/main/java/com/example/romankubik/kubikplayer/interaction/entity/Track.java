package com.example.romankubik.kubikplayer.interaction.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;

/**
 * Created by roman.kubik on 8/17/17.
 */

public class Track {

    private String artist;
    private String song;
    private String album;
    private Bitmap image;
    private Palette.Swatch swatch;

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        if (image != null) {
            this.image = BitmapFactory.decodeByteArray(image, 0, image.length);
            swatch = Palette.from(this.image).generate().getDominantSwatch();
        }
    }

    public Palette.Swatch getSwatch() {
        return swatch;
    }

}
