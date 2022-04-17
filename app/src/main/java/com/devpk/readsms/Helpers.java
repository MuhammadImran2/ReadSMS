package com.devpk.readsms;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;

public class Helpers extends ContextWrapper {

    private String folderName;
    private String id;
    private String address;
    private String messageBody;
    private String readState;
    private String getDate;
    private Activity activity = null;

    public Helpers(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    public void readSms() {
        Uri message = Uri.parse("content://sms/");
        ContentResolver contentResolver = this.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        Cursor cursor = contentResolver.query(message, null, null, null, null);
        int totalSMS = cursor.getCount();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                messageBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                readState = cursor.getString(cursor.getColumnIndexOrThrow("read"));
                getDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
                    folderName = "inbox";
                } else {
                    folderName = "sent";
                }
                stringBuilder.append("id :").append(id).append("\n");
                stringBuilder.append("address :").append(address).append("\n");
                stringBuilder.append("body :").append(messageBody).append("\n");
                stringBuilder.append("readstate :").append(readState).append("\n");
                stringBuilder.append("date :").append(getDate).append("\n");
                stringBuilder.append("foldername :").append(folderName).append("\n\n");
                TextView textView = activity.findViewById(R.id.textView);
                textView.setText(stringBuilder);
                cursor.moveToNext();
            }
        } else {
            throw new RuntimeException("You have no SMS");
        }

        cursor.close();
    }
}
