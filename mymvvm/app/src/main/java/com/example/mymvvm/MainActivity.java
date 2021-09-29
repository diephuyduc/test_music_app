package com.example.mymvvm;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.adapter.SongAdapter;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.InitSong;
import com.example.mymvvm.view.fragment.AlbumFragment;
import com.example.mymvvm.view.fragment.SingerFragment;
import com.example.mymvvm.view.fragment.SongFragment;
import com.example.mymvvm.viewmodel.SongViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private FrameLayout frameLayout;
   private FragmentManager fragmentManager =getSupportFragmentManager();
   private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   getSongList();
        init();
       grantPermission();
        loadFragment(new SongFragment());




    }
    public void grantPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M ){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            }
            else{
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            }

        }

    }
    void init(){
        frameLayout = findViewById(R.id.nav_fragment);
        bottomNavigationView = findViewById(R.id.bottom_navigatin);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new SongFragment();
                switch (item.getItemId()){
                    case R.id.songFragment:
                        fragment = new SongFragment();

                        break;
                    case R.id.albumFragment:
                        fragment = new AlbumFragment();
                        break;
                    case R.id.singerFragment:
                        fragment = new SingerFragment();
                        break;

                }
                loadFragment(fragment);
                return true;
            }
        });

    }

    void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragment, fragment);
        fragmentTransaction.commit();

    }



//    public void getSongList() {
//        //retrieve item_song info
//        ContentResolver musicResolver = getContentResolver();
//        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
//        if (musicCursor != null && musicCursor.moveToFirst()) {
//            //get columns
//            int titleColumn = musicCursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media.TITLE);
//            int idColumn = musicCursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media._ID);
//            int albumID = musicCursor.getColumnIndex
//                    (MediaStore.Audio.Media.ALBUM_ID);
//            int artistColumn = musicCursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media.ARTIST);
//            int songLink = musicCursor.getColumnIndex
//                    (MediaStore.Audio.Media.DATA);
//            //add songs to list
//            do {
//                long thisId = musicCursor.getLong(idColumn);
//                String thisTitle = musicCursor.getString(titleColumn);
//                Log.d("TAG", "getSongList: " + thisTitle);
//                String thisArtist = musicCursor.getString(artistColumn);
//                Uri thisSongLink = Uri.parse(musicCursor.getString(songLink));
//                long some = musicCursor.getLong(albumID);
//                Uri uri = ContentUris.withAppendedId(sArtworkUri, some);
//                mSongList.add(new Song(thisId, thisTitle, thisArtist, uri.toString(),
//                        thisSongLink.toString()));
//            }
//            while (musicCursor.moveToNext());
//        }
//        assert musicCursor != null;
//        musicCursor.close();
//        Toast.makeText(this, mSongList.size() + " Songs Found!!!", Toast.LENGTH_SHORT).show();
//    }
//



}