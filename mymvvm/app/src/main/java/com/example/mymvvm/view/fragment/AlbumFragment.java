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
import com.example.mymvvm.R;
import com.example.mymvvm.adapter.AlbumAdapter;
import com.example.mymvvm.adapter.SingerAdapter;
import com.example.mymvvm.model.Album;
import com.example.mymvvm.viewmodel.AlbumViewModel;


public class AlbumFragment extends Fragment implements IAlbumClick {
          private  AlbumViewModel albumViewModel;
          private RecyclerView recyclerView;
          private AlbumAdapter albumAdapter;

    public AlbumFragment() {
        // Required empty public constructor

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.album_lv);
        albumViewModel = new ViewModelProvider(getActivity()).get(AlbumViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        albumAdapter = new AlbumAdapter(this);
        recyclerView.setAdapter(albumAdapter);
        albumViewModel.filterAlbum();

        albumAdapter.setAlbums( albumViewModel.getAlbumName());





        return view;
    }

    @Override
    public void IClick(Album album) {

    }
}