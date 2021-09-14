package com.fancy.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirePush {
    final static String TAG = "FirePush";
    final static int NOTIFY_ID = 2233;

    public static void getToken(Context iContext){
        //获取推送Token
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);
                        Toast.makeText(iContext,"获取Token：" + token, Toast.LENGTH_SHORT).show();
                    });
        }catch (Exception ex){
            Log.e(TAG, "Fetching FCM registration token failed", ex);
        }
    }

    public static void sendNotification(Context iContext,String messageTitle, String messageBody) {
        //        Intent intent = new Intent(this, MessageActivity.class); // 接收到通知后，点击通知，启动 MessageActivity
        //        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        NotificationManager manager = (NotificationManager)iContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String id = "channelId";
            String name = "channelName";
            NotificationChannel channel = new NotificationChannel(id,name,NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
            notification = new Notification.Builder(iContext,iContext.getPackageName())
                    .setChannelId(id)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(iContext.getResources(),R.mipmap.ic_launcher))
                    .build();
        }else{
            notification = new NotificationCompat.Builder(iContext,iContext.getPackageName())
                    .setContentTitle(messageTitle)
                    .setContentText(messageTitle)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(iContext.getResources(), R.mipmap.ic_launcher))
                    .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                    .setLights(Color.BLUE, 1, 1)
                    .build();
        }
        //        builder.setContentIntent(pendingIntent);
        //        builder.setFullScreenIntent(pendingIntent, true);//将一个Notification变成悬挂式Notification
        manager.notify(NOTIFY_ID,notification);
    }
}
