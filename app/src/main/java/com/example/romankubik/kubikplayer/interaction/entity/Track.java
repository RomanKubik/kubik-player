package com.example.romankubik.kubikplayer.interaction.entity;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.example.romankubik.kubikplayer.general.Constants;

import java.util.UUID;

/**
 * Created by roman.kubik on 8/17/17.
 */

public class Track {

    private String id;
    private String path;
    private String artist;
    private String song;
    private String album;
    private Bitmap image;
    private int primaryColor;
    private int secondaryColor;
    private int bodyColor;

    public Track() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Track && ((Track) obj).getId().equals(id);
    }

    public String getId() {
        return id;
    }

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        Palette palette = Palette.from(this.image).generate();
        if (palette.getVibrantSwatch() != null) {
            primaryColor = palette.getVibrantSwatch().getRgb();
            secondaryColor = palette.getLightVibrantSwatch().getRgb();
            bodyColor = palette.getDarkVibrantSwatch().getRgb();
        } else {
            primaryColor = palette.getMutedSwatch().getRgb();
            secondaryColor = palette.getLightMutedSwatch().getRgb();
            bodyColor = palette.getDarkMutedSwatch().getRgb();
        }
    }

    public void setDefaultImage(Bitmap image) {
        this.image = image;
        primaryColor = Constants.Color.GRAY_LIGHT_DARK;
        secondaryColor = Constants.Color.GRAY;
        bodyColor = Constants.Color.GRAY_DARK;
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
