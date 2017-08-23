package com.androidatc.readsms;

import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.sql.Date;

public class ReadSMS extends ListActivity {

    final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static final Uri SMS = Uri.parse("content://sms");

    public class SmsColumns {
        public static final String ID = "_id";
        public static final String ADDRESS = "address";
        public static final String DATE = "date";
        public static final String BODY = "body";
    }

    private class SmsCursorAdapter extends CursorAdapter {
        public SmsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return View.inflate(context, R.layout.activity_read_sms, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ((TextView) view.findViewById(R.id.sms_origin)).setText(cursor.getString(cursor.getColumnIndexOrThrow(SmsColumns.ADDRESS)));
            ((TextView) view.findViewById(R.id.sms_body)).setText(cursor.getString(cursor.getColumnIndexOrThrow(SmsColumns.BODY)));
            ((TextView) view.findViewById(R.id.sms_date)).setText(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(SmsColumns.DATE))).toLocaleString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ReadSMS.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        Cursor cursor = getContentResolver().query(SMS, new String[]{SmsColumns.ID, SmsColumns.ADDRESS, SmsColumns.DATE, SmsColumns.BODY}, null, null, SmsColumns.DATE + " DESC");
        SmsCursorAdapter adapter = new SmsCursorAdapter(this, cursor, true);
        setListAdapter(adapter);
    }
}




















