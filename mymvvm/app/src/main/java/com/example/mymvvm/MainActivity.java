package com.example.mymvvm;

import static com.example.mymvvm.Constants.MY_ACTION_KEY;
import static com.example.mymvvm.Constants.MY_SONG_KEY;
import static com.example.mymvvm.Constants.SONG_KEY;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.TextView;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.adapter.SongAdapter;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.InitSong;
import com.example.mymvvm.service.MusicService;
import com.example.mymvvm.ultis.MAction;
import com.example.mymvvm.view.fragment.AlbumFragment;
import com.example.mymvvm.view.fragment.SingerFragment;
import com.example.mymvvm.view.fragment.SongFragment;
import com.example.mymvvm.viewmodel.SongViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<Song> songData = new ArrayList<>();

    private FrameLayout frameLayout;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private Song songNow;
    private TextView name, singer;
    private ImageView playOrPause;
    private Boolean isPLaying;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                if(intent.getExtras()!=null){
                    Bundle bundle = intent.getExtras();
                    songNow = (Song) bundle.get(MY_SONG_KEY);
                    MAction mAction = (MAction) bundle.get(MY_ACTION_KEY);
                     isPLaying = (Boolean) bundle.get("status");
                    handleAction(mAction);
                    if(songNow!=null){
                        name.setText(songNow.getTitle()+"");
                      singer.setText(songNow.getSinger()+"");
                    }
                }
        }
    };

    private void handleAction(MAction mAction) {
        switch (mAction){
            case PLAY:
                playOrPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                break;
            case PAUSE:
                playOrPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            case STOP:
        }
    }

    private void sendActionService(MAction mAction){
        Intent intent = new Intent(MainActivity.this, MusicService.class );
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, mAction);
        intent.putExtra(MY_ACTION_KEY,bundle);
        startService(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        grantPermission();
        loadFragment(new SongFragment());


    }

    public void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            }

        }

    }

    void init() {
        frameLayout = findViewById(R.id.nav_fragment);
        bottomNavigationView = findViewById(R.id.bottom_navigatin);
        View miniBar = findViewById(R.id.minibar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new SongFragment();
                switch (item.getItemId()) {
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

        name = miniBar.findViewById(R.id.name);
        singer = miniBar.findViewById(R.id.singer);

        playOrPause = miniBar.findViewById(R.id.mini_play_pause);
        playOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playOrPauseOnclick();
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("send_action"));





    }

    private void playOrPauseOnclick() {
        if(isPLaying){
            sendActionService(MAction.PAUSE);
   //         playOrPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        else{
            sendActionService(MAction.PLAY);
 //           playOrPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        }
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}