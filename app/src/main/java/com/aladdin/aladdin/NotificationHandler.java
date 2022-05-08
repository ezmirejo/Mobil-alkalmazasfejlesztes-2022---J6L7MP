package com.aladdin.aladdin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {

    private NotificationManager notificationManager;
    private Context mContext;

    private static final String CHANNEL_ID = "shop_notification_channel";
    private final int NOTIFICATION_ID = 0;

    public NotificationHandler(Context context) {
        this.mContext = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();

    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Aladdin Notification", NotificationManager.IMPORTANCE_LOW);

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setDescription("Notification from Aladdin");
        this.notificationManager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentTitle("Aladdin")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_cart);

        this.notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


}
