package com.javapapers.android.androidsmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToInbox(View view) {
        Intent intent = new Intent(MainActivity.this, ReceiveSmsActivity.class);
        startActivity(intent);
    }

    public void goToCompose(View view) {
        Intent intent = new Intent(MainActivity.this, SendSmsActivity.class);
        startActivity(intent);
    }

}

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity"
    android:background="#ffff8d7a">

    <TextView android:text="@string/androidSMS" android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView2"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="101dp"
        android:capitalize="characters"
        android:textSize="34dp" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Inbox"
        android:id="@+id/btnInbox"
        android:layout_centerVertical="true"
        android:layout_alignParentStart="true"
        android:onClick="goToInbox" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Compose"
        android:id="@+id/btnCompose"
        android:layout_alignTop="@+id/btnInbox"
        android:layout_alignParentEnd="true"
        android:onClick="goToCompose" />

</RelativeLayout>
Send SMS

Following method is the core part of sending SMS. You may download the complete example App to get the full code.

    protected void sendSMS() {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        String smsMessage = smsMessageET.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
Send-SMS

Receive SMS

Core code for parsing the message from the SMS inbox is as follows, please download the project for complete code or refer the earlier tutorial.

   public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        long timeMillis = smsInboxCursor.getColumnIndex("date");
        Date date = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        String dateText = format.format(date);

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = smsInboxCursor.getString(indexAddress) +" at "+
                    "\n" + smsInboxCursor.getString(indexBody) +dateText+ "\n";
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }