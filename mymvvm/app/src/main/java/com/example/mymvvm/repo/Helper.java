package com.example.mymvvm.repo;

import com.example.mymvvm.MainActivity;
import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Singer;
import com.example.mymvvm.model.Song;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Helper {
    //return position a song in a list
    public static int findSong(Song song) {
        for (int i = 0; i < MainActivity.songData.size(); i++) {
            if (MainActivity.songData.get(i).getId() == song.getId()) {
                return i;
            }

        }
        return -1;
    }

    public static  int existAlbum(String name, List<Album> albums) {
        if(albums.size()==0){
            return -1;
        }
        for(int i=0; i<albums.size(); i++){
            if(name.trim().equals(albums.get(i).getName().trim())){
                return i;
            }
        }
        return -1;
    }

    public static  int existASinger(String name, List<Singer> singer) {
        if(singer.size()==0){
            return -1;
        }
        for(int i=0; i<singer.size(); i++){
            if(name.trim().equals(singer.get(i).getName().trim())){
                return i;
            }
        }
        return -1;
    }

    public static List<Album> albumName() {
        List<Album> album = new ArrayList<>();
        for (Song song : MainActivity.songData
        ) {
            int index = existAlbum(song.getAlbum(),album);
            if (index==-1) {
                album.add(new Album(song.getAlbum(), 1));
            } else {

                album.get(index).setAmount(album.get(index).getAmount() + 1);
            }
        }
        return album;
    }
    public static List<Singer> singerName() {
        List<Singer> singer = new ArrayList<>();
        for (Song song : MainActivity.songData
        ) {
            int index = existASinger(song.getSinger(),singer);
            if (index==-1) {
                singer.add(new Singer(song.getSinger(), 1));
            } else {

                singer.get(index).setAmount(singer.get(index).getAmount() + 1);
            }
        }
        return singer;
    }



    public static List<Song> songFromAlbum(String albumName) {
        List<Song> songs = new ArrayList<>();
        for (Song s : MainActivity.songData
        ) {
            if (s.getAlbum().equals(albumName)) {
                songs.add(s);
            }
        }
        return songs;
    }

    public static List<Song> songFromSinger(String singerName) {
        List<Song> songs = new ArrayList<>();
        for (Song s : MainActivity.songData
        ) {
            if (s.getSinger().equals(singerName)) {
                songs.add(s);
            }
        }
        return songs;
    }
}
