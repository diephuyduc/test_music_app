package com.example.mymvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.mymvvm.model.Singer;
import com.example.mymvvm.repo.Helper;

import java.util.ArrayList;
import java.util.List;

public class SingerViewModel  extends ViewModel {
    List<Singer> singers= new ArrayList<>();

    public List<Singer> getSingers() {
        return singers;
    }
    public void filterSinger(){
        singers.addAll(Helper.singerName());
    }
}
