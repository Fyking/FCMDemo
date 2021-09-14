package hk.com.peoplesplace.fcmdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.fancy.fcm.FirePush;

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


//        FireModule.getToken(mContext);
        //测试通知信息
//        findViewById(R.id.sendMsg).setOnClickListener(v -> FireModule.sendNotification(mContext,"测试信息","你好"));
        findViewById(R.id.sendMsg).setOnClickListener(v -> FirePush.getToken(mContext));
    }
}