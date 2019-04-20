package com.zhi.qing.asyncchannel.activityToFragment;

import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhi.qing.asyncchannel.R;
import com.zhi.qing.channellib.ChannelOriginator;
import com.zhi.qing.channellib.IReceiveMsgCallback;

import static com.zhi.qing.channellib.ChannelConstants.CHANNEL_TAG;

public class MainActivity extends AppCompatActivity {
    private final static String TAG=CHANNEL_TAG;
    ChannelOriginator channelOriginator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        channelOriginator=new ChannelOriginator(this, new IReceiveMsgCallback() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "MainActivity handleMessage: "+msg.toString());
                Toast.makeText(getApplication(),"MainActivity handleMessage: "+msg.toString(),Toast.LENGTH_LONG).show();
            }
        });
        showFragment();
    }


    protected void showFragment() {
        // hideAllFragment();
        BlankFragment fragment = (BlankFragment) getSupportFragmentManager().findFragmentByTag(BlankFragment.class.toString());
        if (fragment == null) {
            fragment = new BlankFragment();
        }
        fragment.setArguments(channelOriginator.buildBundle());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root, fragment,BlankFragment.class.toString());
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    public void sendMsgToFragment(View view) {
        Message message=Message.obtain();
        message.what=1;
        channelOriginator.sendMessage(message);
    }
}
