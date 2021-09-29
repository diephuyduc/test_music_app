package com.example.mymvvm.model;

import android.net.Uri;

public class Song {
    private long id;
    private String title;

    private String singer;
    private String thumbnail;
    private String songLink;
    private String album;

    public Song(long id, String title, String singer, String thumbnail, String songLink, String album) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.thumbnail = thumbnail;
        this.songLink = songLink;
        this.album = album;
    }
    public Song(){}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", singer='" + singer + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", songLink='" + songLink + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}
