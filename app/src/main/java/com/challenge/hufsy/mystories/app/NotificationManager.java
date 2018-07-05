package com.challenge.hufsy.mystories.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicReference;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/5/18.
 * <p>
 */
public class NotificationManager {

    private static final NotificationManager INSTANCE = new NotificationManager();

    private static final int NOTIF_ID = 1;

    private AtomicReference<Context> context;

    private android.app.NotificationManager notificationManagerO;
    private NotificationManagerCompat notificationManager;


    public static NotificationManager getInstance() {
        return INSTANCE;
    }

    private NotificationManager() {
    }

    public void init(Context context) {
        this.context = new AtomicReference<>(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.notificationManagerO = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        } else {
            this.notificationManager = NotificationManagerCompat.from(context);
        }

    }

    private void checkInit() {
        if (null == context.get()) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must be initialized at " + App.class.getSimpleName());
        }
    }

    public void notifyUpload(@NonNull @StringRes Integer contentTitle, @NonNull @StringRes Integer contentText,
                             @NonNull @DrawableRes Integer smallIcon) {

        checkInit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifyChannel(contentTitle, contentText, smallIcon);
        } else {
            notifyCompat(contentTitle, contentText, smallIcon);
        }

    }

    public void dismiss() {

        checkInit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dismissChannel();
        } else {
            dismissCompat();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notifyChannel(@NonNull @StringRes Integer contentTitle, @NonNull @StringRes Integer contentText,
                               @NonNull @DrawableRes Integer smallIcon) {

        final NotificationChannel notificationChannel = new NotificationChannel(MyStoriesConstants.UPLOAD_CHANNEL_ID,
                MyStoriesConstants.UPLOAD_CHANNEL_NAME,
                android.app.NotificationManager.IMPORTANCE_LOW);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManagerO.createNotificationChannel(notificationChannel);

        final Notification notification = new Notification.Builder(context.get(), MyStoriesConstants.UPLOAD_CHANNEL_ID)
                .setContentTitle(ResExtractor.getInstance().getString(contentTitle))
                .setContentText(ResExtractor.getInstance().getString(contentText))
                .setSmallIcon(smallIcon)
                .setProgress(100, 0, true)
                .build();

        notificationManagerO.notify(NOTIF_ID, notification);

    }

    private void notifyCompat(@NonNull @StringRes Integer contentTitle, @NonNull @StringRes Integer contentText,
                              @NonNull @DrawableRes Integer smallIcon) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context.get(), MyStoriesConstants.UPLOAD_CHANNEL_ID);
        builder.setContentTitle(ResExtractor.getInstance().getString(contentTitle))
                .setContentText(ResExtractor.getInstance().getString(contentText))
                .setSmallIcon(smallIcon)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        builder.setProgress(100, 0, true);

        notificationManager.notify(NOTIF_ID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void dismissChannel() {
        notificationManagerO.deleteNotificationChannel(MyStoriesConstants.UPLOAD_CHANNEL_ID);
    }

    private void dismissCompat() {
        notificationManager.cancel(NOTIF_ID);
    }

}
