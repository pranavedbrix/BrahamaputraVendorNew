package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowHotoNotifiacationDetailsActivity extends BaseActivity {

    private TextView title;
    private TextView message;
    private TextView timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hoto_notifiacation_details);
        setTitle("Notification Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.txtHotoTitle);
        message = (TextView) findViewById(R.id.txtHotoMessage);
        timeStamp = (TextView) findViewById(R.id.txtHotoTimeStamp);

        Intent fromNotificationList = getIntent();
        String str_Title = fromNotificationList.getStringExtra("title");
        String str_Message = fromNotificationList.getStringExtra("message");
        String str_TimeStamp = fromNotificationList.getStringExtra("timestamp");

        title.setText(str_Title);
        message.setText(str_Message);
        timeStamp.setText(parseTime(str_TimeStamp));

    }

    public String parseTime(String date2) {
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = dateFormat.parse(date2);
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            String dateStr = formatter.format(date);

            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_no_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
