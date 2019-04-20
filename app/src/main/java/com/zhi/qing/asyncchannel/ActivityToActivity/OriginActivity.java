package com.zhi.qing.asyncchannel.ActivityToActivity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhi.qing.asyncchannel.R;
import com.zhi.qing.asyncchannel.activityToFragment.BlankFragment;
import com.zhi.qing.channellib.ChannelOriginator;
import com.zhi.qing.channellib.IReceiveMsgCallback;

public class OriginActivity extends AppCompatActivity {
    private final static String TAG="OriginActivity";
    ChannelOriginator channelOriginator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        channelOriginator=new ChannelOriginator(this, new IReceiveMsgCallback() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "OriginActivity handleMessage: "+msg.toString());
                Toast.makeText(getApplication(),"OriginActivity handleMessage: "+msg.toString(),Toast.LENGTH_LONG).show();
            }
        });
        showActivity();
    }
    protected void showActivity() {
        Intent intent=new Intent(this,TargetActivity.class);
        Bundle bundle=channelOriginator.buildBundle();
        intent.putExtra("bundle",bundle);
        startActivity(intent);

    }

}
