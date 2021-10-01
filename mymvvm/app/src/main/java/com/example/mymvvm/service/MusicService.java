package com.example.mymvvm.service;


import static com.example.mymvvm.Constants.MY_ACTION_KEY;
import static com.example.mymvvm.Constants.MY_SONG_KEY;
import static com.example.mymvvm.Constants.NOTIFICATION_ID;
import static com.example.mymvvm.Constants.SONG_KEY;
import static com.example.mymvvm.repo.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mymvvm.R;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.ultis.MAction;

import java.util.Date;

public class MusicService extends Service {
    MediaPlayer mediaPlayer = new MediaPlayer();
    Song song;
    boolean isPlaying = true;
    MAction mAction = MAction.PLAY;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBundleExtra(SONG_KEY) != null) {
            song = (Song) intent.getBundleExtra(SONG_KEY).get(SONG_KEY);
            if (song != null) {
              //  mAction = MAction.PLAY;
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                playSong(song);
                isPlaying=true;
            }
            else{
                stopSelf();
            }




        }

//        sendNotification(song);
//        playSong(song);
        if (intent.getBundleExtra(MY_ACTION_KEY) != null) {
            mAction = (MAction) intent.getBundleExtra(MY_ACTION_KEY).get(MY_ACTION_KEY);
            Log.d("br", "onStartCommand: " + mAction.toString());
        } else {
            mAction = MAction.PLAY;
        }

        handleAction(mAction);
        return START_REDELIVER_INTENT;
    }
    private void playOnComplete(){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                isPlaying=false;
                sendNext();
            }
        });
    }

    private void handleAction(MAction mAction) {
        switch (mAction) {
            case PLAY:
                resumeSong();
                break;
            case PREV:
                changeSongPre();
            case NEXT:
                changeSongNext();
                break;
            case PAUSE:
                pauseASong();
                break;
            case STOP:
                clear();
                break;

        }
    }

    private void pauseASong() {
        isPlaying = false;
        mediaPlayer.pause();
        sendNotification(song);
        sendActionToMain(MAction.PAUSE);
    }

    private void changeSongNext() {

        sendActionToMain(MAction.NEXT);
        sendNotification(song);
    }

    private void changeSongPre() {
        mediaPlayer.pause();
        sendActionToMain(MAction.PREV);
        sendNotification(song);
    }



    private void clear() {
        sendActionToMain(MAction.STOP);
        stopSelf();
    }

    public void playSong(Song song) {
        if (song != null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(song.getSongLink()));
            if(mediaPlayer!=null){
                playOnComplete();
                mediaPlayer.start();
                isPlaying = true;
                sendNotification(song);
                sendActionToMain(MAction.PLAY);
            }

        }

    }
    public void  resumeSong(){
        if(song!=null){
            if(mediaPlayer!=null){
                mediaPlayer.start();
                isPlaying = true;
                sendNotification(song);
                sendActionToMain(MAction.PLAY);
            }

        }
    }

    public void sendNotification(Song song) {
        if(song==null){return;}
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.music_notification);
        remoteViews.setTextViewText(R.id.song_name, song.getTitle());
        remoteViews.setTextViewText(R.id.song_singer, song.getSinger());


        Notification notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .build();

        remoteViews.setOnClickPendingIntent(R.id.pre, getPendingIntentForPrevOrNext(MAction.PREV));
        remoteViews.setOnClickPendingIntent(R.id.next, getPendingIntentForPrevOrNext(MAction.NEXT));
        if (isPlaying) {
            remoteViews.setImageViewResource(R.id.pause_play, R.drawable.ic_baseline_pause_circle_filled_24);
            remoteViews.setOnClickPendingIntent(R.id.pause_play, getPendingIntent(MAction.PAUSE));
        } else {
            remoteViews.setImageViewResource(R.id.pause_play, R.drawable.ic_baseline_play_circle_filled_24);
            remoteViews.setOnClickPendingIntent(R.id.pause_play, getPendingIntent(MAction.PLAY));
        }
        remoteViews.setOnClickPendingIntent(R.id.clear, getPendingIntent(MAction.STOP));
        startForeground(NOTIFICATION_ID, notificationBuilder);

    }
    public void sendNext(){
        Intent intent = new Intent(getApplicationContext(), ChangeSong.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, MAction.NEXT);
        bundle.putSerializable(MY_SONG_KEY, song);
        intent.putExtra(MY_SONG_KEY, bundle);
        getApplicationContext().sendBroadcast(intent);
    }
    public PendingIntent getPendingIntent(MAction mAction) {

        Intent intent = new Intent(getApplicationContext(), SongBroadcastReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, mAction);
        intent.putExtra(MY_ACTION_KEY, bundle);
        return PendingIntent.getBroadcast(getApplicationContext(), (int) new Date().getTime(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent getPendingIntentForPrevOrNext(MAction mAction) {
        Intent intent = new Intent(getApplicationContext(), ChangeSong.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, mAction);
        bundle.putSerializable(MY_SONG_KEY, song);
        intent.putExtra(MY_SONG_KEY, bundle);
        Log.d("br", "getPendingIntentForPrevOrNext: " + song.toString());
        return PendingIntent.getBroadcast(getApplicationContext(), (int) new Date().getTime(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendActionToMain(MAction mAction){
        Intent intent = new Intent("send_action");
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, mAction);
        bundle.putSerializable(MY_SONG_KEY, song);
        bundle.putBoolean("status", isPlaying);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}