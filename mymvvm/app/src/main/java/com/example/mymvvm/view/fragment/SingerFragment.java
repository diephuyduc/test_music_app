package com.example.mymvvm.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymvvm.Interface.IAlbumClick;
import com.example.mymvvm.Interface.ISingerClick;
import com.example.mymvvm.R;
import com.example.mymvvm.adapter.AlbumAdapter;
import com.example.mymvvm.adapter.SingerAdapter;
import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Singer;
import com.example.mymvvm.viewmodel.AlbumViewModel;
import com.example.mymvvm.viewmodel.SingerViewModel;


public class SingerFragment extends Fragment implements ISingerClick {
    private SingerViewModel singerViewModel;
    private RecyclerView recyclerView;
    private SingerAdapter singerAdapter;

    public SingerFragment() {
        // Required empty public constructor

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.album_lv);
        singerViewModel = new ViewModelProvider(getActivity()).get(SingerViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        singerAdapter = new SingerAdapter(this);
        recyclerView.setAdapter(singerAdapter);
        singerViewModel.filterSinger();

        singerAdapter.setSinger( singerViewModel.getSingers());





        return view;
    }


    @Override
    public void onClick(Singer singer) {

    }
}