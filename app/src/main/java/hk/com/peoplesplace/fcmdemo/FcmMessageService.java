package hk.com.peoplesplace.fcmdemo;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * create by
 * on 2020/4/27
 * explain${Fcm接收通知}
 */
public class FcmMessageService extends FirebaseMessagingService {
    public static String TAG = "tag_-----FcmMessagService:";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From Id為: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            if (true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                Log.d(TAG, "Message Send Title:"+remoteMessage.getNotification().getTitle());
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(getBaseContext(),remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

//        if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
//            sendNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//        } else {
//            sendNotification(getApplicationContext(), remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
//        }
    }

    /**
     * Schedule async work using WorkManager.
     */

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    private void sendNotification(Context iContext, String messageTitle, String messageBody) {
        NotificationManager notificationManager = (NotificationManager) iContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent intent = new Intent(this, MessageActivity.class); // 接收到通知后，点击通知，启动 MessageActivity

//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] pattern = {500, 500, 500, 500, 500};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "-1")
                .setTicker(messageTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("你有新信息")
                .setAutoCancel(true)
                .setContentText(messageBody)
                .setWhen(System.currentTimeMillis())
                .setVibrate(pattern)
                .setChannelId(iContext.getPackageName())
                .setLights(Color.BLUE, 1, 1);

        builder.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);

//        builder.setContentIntent(pendingIntent);
//        builder.setFullScreenIntent(pendingIntent, true);//将一个Notification变成悬挂式Notification

        if (notificationManager != null) {
            notificationManager.notify(0, builder.build());
        }
    }
}
