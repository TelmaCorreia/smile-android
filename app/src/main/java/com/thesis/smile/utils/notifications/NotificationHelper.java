package com.thesis.smile.utils.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.presentation.main.MainActivity;

public class NotificationHelper {

    private static NotificationManager notificationManager;
    private static final String NOTIFICATION_CHANNEL = "request_channel";

    public static void responseNotification(Context context, int image, String title, int id) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
        }

        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        long[] vibrateLength = {500, 1000};

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                id, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(image)
                .setColor(ContextCompat.getColor(context, R.color.colorBackground))
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setVibrate(vibrateLength)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(id, notification);
    }

    public static void requestNotification(Context context, int image, String title, int id) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
        }

        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                id, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(image)
                .setColor(ContextCompat.getColor(context, R.color.colorBackground))
                .setContentTitle(title)
                .setProgress(0, 0, true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build();

        notificationManager.notify(id, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createNotificationChannel(Context context) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL,
                context.getResources().getString(R.string.app_name),
                NotificationManager.IMPORTANCE_MIN);
        notificationManager.createNotificationChannel(channel);
    }

}
