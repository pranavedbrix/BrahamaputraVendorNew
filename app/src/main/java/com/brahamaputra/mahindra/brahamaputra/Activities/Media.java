package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.MediaData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Media extends BaseActivity {


    final Calendar myCalendar = Calendar.getInstance();
    String str_typeOfMedia;

    private TextView mMediaTextViewTypeofmedia;
    private TextView mMediaTextViewTypeofmediaVal;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private MediaData mediaData;
    private SessionManager sessionManager;

    private void assignViews() {
        mMediaTextViewTypeofmedia = (TextView) findViewById(R.id.media_textView_Typeofmedia);
        mMediaTextViewTypeofmediaVal = (TextView) findViewById(R.id.media_textView_Typeofmedia_val);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    private void initCombo() {
        mMediaTextViewTypeofmediaVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Media.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_media_Typeofmedia))),
                        "Media",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfMedia = item.get(position);
                        mMediaTextViewTypeofmediaVal.setText(str_typeOfMedia);
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        this.setTitle("Media");
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(Media.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Media.this, userId, ticketName);
        hotoTransactionData = new HotoTransactionData();

        setInputDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                if (checkValiadtion()) {
                    submitDetails();
                    finish();
                    startActivity(new Intent(this, Battery_Set.class));
                }


                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValiadtion() {
        String mediaType = mMediaTextViewTypeofmediaVal.getText().toString().trim();
        if (mediaType.isEmpty() || mediaType == null) {
            showToast("Select Media Type ");
            return false;
        }else
            return true;
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                mediaData = hotoTransactionData.getMediaData();


                mMediaTextViewTypeofmediaVal.setText(mediaData.getTypeOfMedia());

            } else {
                //Toast.makeText(Media.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            // hotoTransactionData.setTicketNo(ticketId);
            String mediaType = mMediaTextViewTypeofmediaVal.getText().toString().trim();

            mediaData = new MediaData(mediaType);

            hotoTransactionData.setMediaData(mediaData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
