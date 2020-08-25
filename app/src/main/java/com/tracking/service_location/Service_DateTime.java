package com.tracking.service_location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.text.SimpleDateFormat;

import androidx.annotation.Nullable;

public class Service_DateTime extends Service {

    long date = System.currentTimeMillis();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
    //tiempo.setText(simpleDateFormat.format(date));

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
