package com.example.mymvvm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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

        return START_REDELIVER_INTENT;
    }
}