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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String TAG = "FirePush";
    final int NOTIFY_ID = 2233;
    Context mContext;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mHandler = new Handler(Looper.myLooper());

        FireModule fire=new FireModule();
        fire.getToken(mContext);

        //测试通知信息
        findViewById(R.id.sendMsg).setOnClickListener(v -> fire.sendNotification(mContext,"测试信息","你好"));
    }
}