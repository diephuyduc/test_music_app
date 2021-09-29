package com.example.mymvvm.service;

import static com.example.mymvvm.Constants.CHANNEL_ID;
import static com.example.mymvvm.Constants.SONG_KEY;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.mymvvm.R;
import com.example.mymvvm.model.Song;

public class MusicService extends Service {
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Song song = (Song) intent.getBundleExtra(SONG_KEY).get(SONG_KEY);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.music_notification);
        remoteViews.setTextViewText(R.id.song_name, song.getTitle());
        remoteViews.setTextViewText(R.id.song_singer, song.getSinger());

        Notification notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .build();
        startForeground();
        return START_REDELIVER_INTENT;
    }
}