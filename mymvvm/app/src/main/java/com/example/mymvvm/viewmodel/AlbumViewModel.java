package com.example.mymvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumViewModel extends ViewModel {
    List<Album> albumName= new ArrayList<>();

    public List<Album> getAlbumName() {
        return albumName;
    }
    public void filterAlbum(){
        albumName.addAll(Helper.albumName());
    }
}
