package com.example.mymvvm.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymvvm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingerFragment extends Fragment {



    public SingerFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_singer, container, false);
    }
}