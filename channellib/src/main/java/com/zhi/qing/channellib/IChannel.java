package com.zhi.qing.channellib;

import android.os.Message;

/**
 * Created by linzhiqing on 2019/4/18.
 */

public interface IChannel {
    void sendMessage(Message message);

    void disConnect();

    void destroy();
}
