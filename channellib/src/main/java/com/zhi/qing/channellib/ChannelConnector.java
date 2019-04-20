package com.zhi.qing.channellib;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import static com.zhi.qing.channellib.ChannelConstants.BUNDLE_MESSENGER_KEY;
import static com.zhi.qing.channellib.ChannelConstants.CHANNEL_TAG;

/**
 * Created by linzhiqing on 2019/4/18.
 */

public class ChannelConnector implements IChannel {
    private final static String TAG = CHANNEL_TAG;
    private HandlerThread mChannelConnectorThread;
    private Handler mChannelConnectorHandler;
    private AsyncChannel mChannelConnectorChannel;
    private Context mContext;
    private IReceiveMsgCallback mCallback;

    public ChannelConnector(Context mContext, IReceiveMsgCallback callback) {
        this.mContext = mContext;
        mCallback = callback;
        mChannelConnectorThread = new HandlerThread("ChannelConnector");
        mChannelConnectorThread.start();
        mChannelConnectorHandler = new ChannelChannelConnectorHandler(mChannelConnectorThread.getLooper());
        mChannelConnectorChannel = new AsyncChannel();
    }


    public void connect(Bundle bundle) {
        Log.d(TAG, "ChannelConnector connect: ");
        if (bundle == null) {
            Log.e(TAG, "connect: bundle is null");
            return;
        }
        Messenger messenger = bundle.getParcelable(BUNDLE_MESSENGER_KEY);
        if (messenger == null) {
            Log.e(TAG, "connect: messenger is null");
            return;
        }
        try {
            mChannelConnectorChannel.connect(mContext, mChannelConnectorHandler, messenger);
        } catch (Exception e) {
            Log.e(TAG, "connect: ",e);
        }

    }

    @Override
    public void sendMessage(Message message) {
        mChannelConnectorChannel.sendMessage(message);
    }

    @Override
    public void disConnect() {

    }

    @Override
    public void destroy() {
        if (mChannelConnectorThread != null) {
            mChannelConnectorThread.quit();
        }

    }

    private class ChannelChannelConnectorHandler extends Handler {
        ChannelChannelConnectorHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AsyncChannel.CMD_CHANNEL_HALF_CONNECTED: {
                    Log.d(TAG, "ChannelConnector handleMessage: CMD_CHANNEL_HALF_CONNECTED");
                    mChannelConnectorChannel.sendMessage(AsyncChannel.CMD_CHANNEL_FULL_CONNECTION);
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_FULLY_CONNECTED: {
                    Log.d(TAG, "ChannelConnector handleMessage: CMD_CHANNEL_FULLY_CONNECTED");
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_DISCONNECT: {
                    Log.d(TAG, "ChannelConnector handleMessage: CMD_CHANNEL_DISCONNECT");
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_DISCONNECTED: {
                    Log.d(TAG, "ChannelConnector handleMessage: CMD_CHANNEL_DISCONNECTED");
                    break;
                }


                default: {
                    mCallback.handleMessage(msg);
                }
                break;
            }
        }
    }

}
