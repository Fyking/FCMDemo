package hk.com.peoplesplace.fcmdemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Push";
    final int NOTIFY_ID = 666;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        //获取推送Token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast
                    @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });

        //测试通知信息
        findViewById(R.id.sendMsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(mContext,"测试信息","你好");
            }
        });

    }

    private void sendNotification(Context iContext, String messageTitle, String messageBody) {
        NotificationManager notificationManager = (NotificationManager) iContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent intent = new Intent(this, MessageActivity.class); // 接收到通知后，点击通知，启动 MessageActivity

//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] pattern = {500, 500, 500, 500, 500};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "-1")
                .setTicker(messageTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("你有新的信息")
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
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
    }
}