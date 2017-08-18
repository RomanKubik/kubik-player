package com.example.romankubik.kubikplayer.interaction.entity;

/**
 * Created by roman.kubik on 8/17/17.
 */

public class Track {

    private String artist;
    private String song;
    private String album;
    private byte[] image;

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
        this.image = image;
    }
}
