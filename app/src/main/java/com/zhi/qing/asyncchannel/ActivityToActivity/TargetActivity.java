package com.zhi.qing.asyncchannel.ActivityToActivity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhi.qing.asyncchannel.R;
import com.zhi.qing.channellib.ChannelConnector;
import com.zhi.qing.channellib.IReceiveMsgCallback;

public class TargetActivity extends AppCompatActivity {
    private final static String TAG="TargetActivity";
    ChannelConnector channelConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        channelConnector=new ChannelConnector(getApplicationContext(), new IReceiveMsgCallback() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "TargetActivity handleMessage: "+msg.toString());
                Toast.makeText(getApplication(),"TargetActivity handleMessage: "+msg.toString(),Toast.LENGTH_LONG).show();
            }
        });
        Bundle bundle=getIntent().getBundleExtra("bundle");
        if(bundle!=null){
            channelConnector.connect(bundle);

        }
    }

    public void button(View view) {
        Message message=Message.obtain();
        message.what=2;
        channelConnector.sendMessage(message);
    }
}
