package com.example.mymvvm.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.InitSong;

import java.util.List;

public class SongViewModel extends AndroidViewModel {
    MutableLiveData<List<Song>> songListMutableLiveData = new MutableLiveData<>();
    Context context;

    public SongViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<Song>> getSongListMutableLiveData() {
//        if(songListMutableLiveData==null){
//           songListMutableLiveData= new MutableLiveData<>();
//            scanAllSong();
//        }
        return songListMutableLiveData;
    }
    public void scanAllSong(){
       songListMutableLiveData.setValue(InitSong.getAllSongs(context));
    }
}
