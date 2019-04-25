package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.brahamaputra.mahindra.brahamaputra.Adapters.UsersOfflineHotoListAdapter;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;

import java.io.File;
import java.util.ArrayList;

public class UsersOfflineHotoListActivity extends BaseActivity {

    Context context;
    //public String[] listItem;
    public ArrayList<String> listItem;
    public ArrayAdapter<ArrayList> adapter;

    public ListView listView;
    private SessionManager sessionManager;
    private AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_offline_hoto_list);
        listView = (ListView)findViewById(R.id.offlineTicketList);
        this.setTitle("HOTO List (Offline)");
        sessionManager = new SessionManager(UsersOfflineHotoListActivity.this);
        alertDialogManager = new AlertDialogManager(UsersOfflineHotoListActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        listItem = new ArrayList<String>();
        getOfflineTicketList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                //String value= String.valueOf(adapter.getItem(position));
                String value = listItem.get(position);
                //Toast.makeText(UsersOfflineHotoListActivity.this,value,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UsersOfflineHotoListActivity.this, UserHotoTransactionActivity.class);
                intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(UsersOfflineHotoListActivity.this));
                intent.putExtra("ticketNO", value);
                sessionManager.updateSessionUserTicketName(value);

                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.eb_add_item_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuAdd:
                newTicket();
                 return true;
            case R.id.menuRefresh:
                getOfflineTicketList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newTicket(){
        alertDialogManager.Dialog("Information", "Device has no internet connection. Do you want to use offline mode?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(UsersOfflineHotoListActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(UsersOfflineHotoListActivity.this)
                        .setTitle("Information")
                        .setMessage("Enter Ticket ID")
                        .setView(taskEditText)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(UsersOfflineHotoListActivity.this, UserHotoTransactionActivity.class);
                                intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(UsersOfflineHotoListActivity.this));
                                intent.putExtra("ticketNO", String.valueOf(taskEditText.getText()));
                                sessionManager.updateSessionUserTicketName(String.valueOf(taskEditText.getText()));

                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("cancel", null)
                        .create();
                dialog.show();
            }
        }).show();
    }

    public void getOfflineTicketList(){
        listItem.clear();
        File file=new File(Environment.getExternalStorageDirectory(), "/" + context.getString(R.string.app_name) + "/offlinedata/");

        //Read UserID FolderNames*
        File[] userFolder = file.listFiles();
        if (userFolder != null) {
            for (int p = 0; p < userFolder.length; p++) {
                //Read TicketID FolderNames*
                File[] TicketFolder = userFolder[p].listFiles();
                if (TicketFolder != null) {
                    for (int i = 0; i < TicketFolder.length; i++) {
                        //Read TicketFile Names*
                        File[] TicketFile = TicketFolder[i].listFiles();
                        if (TicketFile != null) {
                            for (int j = 0; j < TicketFile.length; j++) {
                                String name = TicketFile[j].getName();
                                if (name.endsWith(".txt")) {
                                    if (name.indexOf(".") > 0) {
                                        String fileName = name.substring(0, name.lastIndexOf("."));
                                        listItem.add(fileName);
                                    }
                                    //showToast(name+" is Present");
                                }
                            }
                        } else {
                            showToast("Ticket File Not Found");
                        }
                    }
                } else {
                    showToast("Ticket Folder Not Found");
                }
            }
        } else {
            showToast("Root Folder Not Found");
        }
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UsersOfflineHotoListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        UsersOfflineHotoListAdapter usersOfflineHotoListAdapter = new UsersOfflineHotoListAdapter(UsersOfflineHotoListActivity.this,listItem);
        listView.setAdapter(usersOfflineHotoListAdapter);
    }
}
