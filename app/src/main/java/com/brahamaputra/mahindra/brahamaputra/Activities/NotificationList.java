package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.Adapters.NotificationListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Data.Notification;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.DatabaseHelper;

import java.util.ArrayList;

public class NotificationList extends BaseActivity {

    //implements NotificationReceiver

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketName = "";
    private String ticketId = "";
    private Notification notification;
    private SessionManager sessionManager;

    private ListView mNotificationListView;
    private TextView mTxtNoNotificationFound;
    private AlertDialogManager alertDialogManager;
    private NotificationListAdapter notificationListAdapter;
    private DatabaseHelper databaseHelper;

    private BroadcastReceiver broadcastReceiver;
    private final String SERVICE_RESULT = "com.service.result";
    private final String SERVICE_MESSAGE = "com.service.message";
    ArrayList<Notification> dd;

    private void assignViews() {
        mNotificationListView = (ListView) findViewById(R.id.listViewNotification);
        mTxtNoNotificationFound = (TextView) findViewById(R.id.txtNoNotificationFound);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        this.setTitle("Notification List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertDialogManager = new AlertDialogManager(NotificationList.this);
        sessionManager = new SessionManager(NotificationList.this);
        userId = sessionManager.getSessionUserId();
        notification = new Notification();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        assignViews();
        prepareListData();

        // ListView on item selected listener.
        mNotificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification dataModel = dd.get(position);
                //int b4 = dataModel.getIsRead();
                databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.updateNotification(dataModel);
                prepareListData();
                Intent intent = new Intent(getApplicationContext(), ShowHotoNotifiacationDetailsActivity.class);
                intent.putExtra("title", dd.get(position).getTitle());
                intent.putExtra("timestamp", dd.get(position).getTimestamp());
                intent.putExtra("message", dd.get(position).getMessage());
                startActivity(intent);
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // do something here.
                String receiveFlag = intent.getStringExtra(SERVICE_MESSAGE);
                if (receiveFlag.equals("receive")) {
                    prepareListData();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver),
                new IntentFilter(SERVICE_RESULT));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    private void prepareListData() {
        if (databaseHelper.getAllNotification() != null && databaseHelper.getAllNotification().size() > 0) {
            dd = new ArrayList<Notification>(databaseHelper.getAllNotification().size());
            dd.addAll(databaseHelper.getAllNotification());
            notificationListAdapter = new NotificationListAdapter(dd, NotificationList.this);

            mNotificationListView.setAdapter(notificationListAdapter);
            mNotificationListView.setVisibility(View.VISIBLE);
            mTxtNoNotificationFound.setVisibility(View.GONE);

        } else {
            mNotificationListView.setVisibility(View.GONE);
            mTxtNoNotificationFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notification_icon_menu, menu);

        MenuItem deleteAllNotificationItem = menu.findItem(R.id.menuDeleteAll);
        deleteAllNotificationItem.setVisible(false);
        int notificationCount = databaseHelper.getAllNotification().size();
        if(notificationCount!=0){
            deleteAllNotificationItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menuRefresh:
                prepareListData();
                return true;

            case R.id.menuDeleteAll:
                deleteNotificationConfirmBox();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void deleteNotificationConfirmBox() {
        alertDialogManager = new AlertDialogManager(NotificationList.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to delete all notification?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                databaseHelper.deleteAllNotification();
                prepareListData();
            }

            @Override
            public void onNegativeClick() {

            }
        }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            prepareListData();
        }
    }

}
