package org.leochen.sms2mail.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneStateReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive()");
        String action = intent.getAction();

        if ("android.intent.action.PHONE_STATE".equals(action)) {// 来电
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String inNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);// 来电号码
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {// 电话正在响铃
            } else if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {// 挂断
            } else if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {// 摘机，通话状态
            }
        }
    }
}

