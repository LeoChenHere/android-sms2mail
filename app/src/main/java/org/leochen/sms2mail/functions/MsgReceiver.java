package org.leochen.sms2mail.functions;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.Date;
import java.util.Properties;

public class MsgReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Properties msg = query(context);

        String to = "leochen.here@gmail.com";
        StringBuffer subject = new StringBuffer();
        subject.append("來自: ");
        subject.append(msg.getProperty("address"));
        subject.append(" 的短信");

        StringBuffer msgBody = new StringBuffer();
        msgBody.append("发送时间: ");
        msgBody.append(msg.getProperty("dateSent"));
        msgBody.append("\n");
        msgBody.append("來自: ");
        msgBody.append(msg.getProperty("address"));
        msgBody.append("\n");
        msgBody.append("最新短信：");
        msgBody.append(msg.getProperty("smsBody"));

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Your code goes here
                    EmailSender.send(to,
                            subject.toString(),
                            msgBody.toString()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private Properties query(Context context) {
        Properties result = new Properties();
        //读取所有短信
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String smsBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                // 获取短信发送时间
                Long date = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
                Date dateSent = new Date(date);

                Log.d("SMS", "最新短信： " + smsBody + ", 发件人: " + address + ", 发送时间: " + dateSent);
                result.put("address", address);
                result.put("smsBody", smsBody);
                result.put("dateSent", dateSent.toString());
            }

        }

        return result;
    }



}