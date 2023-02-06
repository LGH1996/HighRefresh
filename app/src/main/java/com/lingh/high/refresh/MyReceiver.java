package com.lingh.high.refresh;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

public class MyReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Settings.System.canWrite(context)) {
            Settings.System.putInt(context.getContentResolver(), "speed_mode", 1);
            int status = Settings.System.getInt(context.getContentResolver(), "speed_mode", 0);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            Intent intentMain = new Intent(context, MainActivity.class);
            Notification.Builder builder = new Notification.Builder(context)
                    .setContentIntent(PendingIntent.getActivity(context, 0x01, intentMain, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT))
                    .setSmallIcon(R.drawable.ic_app)
                    .setContentTitle("全局高刷" + (status == 1 ? "已开启" : "未开启"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(context.getPackageName());
                NotificationChannel channel = new NotificationChannel(context.getPackageName(), context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(context.getPackageName(), 0x01, builder.build());
        }
    }
}
