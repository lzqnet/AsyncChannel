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

public class ChannelOriginator implements IChannel {
    private final static String TAG = CHANNEL_TAG;
    private HandlerThread mChannelOriginatorThread;
    private Handler mChannelOriginatorHandler;
    private AsyncChannel mChannelOriginatorChannel;
    private Context mContext;
    private IReceiveMsgCallback mCallback;

    public ChannelOriginator(Context mContext, IReceiveMsgCallback callback) {
        this.mContext = mContext;
        mCallback = callback;
        mChannelOriginatorThread = new HandlerThread("ChannelOriginator");
        mChannelOriginatorThread.start();
        mChannelOriginatorHandler = new ChannelOriginatorHandler(mChannelOriginatorThread.getLooper());
        mChannelOriginatorChannel = new AsyncChannel();
    }


    public Bundle buildBundle() {
        Log.d(TAG, "ChannelOriginator buildBundle: ");
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(BUNDLE_MESSENGER_KEY, new Messenger(mChannelOriginatorHandler));
            return bundle;
        } catch (Exception e) {
            Log.e(TAG, "buildBundle: ", e);
        }
        return null;

    }

    @Override
    public void sendMessage(Message message) {
        mChannelOriginatorChannel.sendMessage(message);

    }


    @Override
    public void disConnect() {

    }

    @Override
    public void destroy() {
        if (mChannelOriginatorThread != null) {
            mChannelOriginatorThread.quit();
        }

    }

    private class ChannelOriginatorHandler extends Handler {
        ChannelOriginatorHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AsyncChannel.CMD_CHANNEL_HALF_CONNECTED: {
                    Log.d(TAG, "ChannelOriginator handleMessage: CMD_CHANNEL_HALF_CONNECTED");
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_FULL_CONNECTION: {
                    Log.d(TAG, "ChannelOriginator handleMessage CMD_CHANNEL_FULL_CONNECTION");
                    mChannelOriginatorChannel.connect(mContext, mChannelOriginatorHandler, msg.replyTo);
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_DISCONNECT: {
                    Log.d(TAG, "ChannelOriginator handleMessage: CMD_CHANNEL_DISCONNECT");
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_DISCONNECTED: {
                    Log.d(TAG, "ChannelOriginator handleMessage: CMD_CHANNEL_DISCONNECTED");
                    break;
                }
                case AsyncChannel.CMD_CHANNEL_FULLY_CONNECTED: {
                    Log.d(TAG, "ChannelOriginator handleMessage: CMD_CHANNEL_FULLY_CONNECTED");
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
