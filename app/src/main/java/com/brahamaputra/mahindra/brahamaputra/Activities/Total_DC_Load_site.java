package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.TotalDCLoadofSiteData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

public class Total_DC_Load_site extends BaseActivity {

    private TextView mTotalDCLoadsiteTextViewTotalDcLoadOfSite;
    private EditText mTotalDCLoadsiteEditTextTotalDcLoadOfSite;
    DecimalConversion decimalConversion;
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private TotalDCLoadofSiteData totalDCLoadofSiteData;
    private SessionManager sessionManager;

    private void assignViews() {
        mTotalDCLoadsiteTextViewTotalDcLoadOfSite = (TextView) findViewById(R.id.totalDCLoadsite_textView_totalDcLoadOfSite);
        mTotalDCLoadsiteEditTextTotalDcLoadOfSite = (EditText) findViewById(R.id.totalDCLoadsite_editText_totalDcLoadOfSite);

        mTotalDCLoadsiteEditTextTotalDcLoadOfSite.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void DecimalFormatConversion() {
        mTotalDCLoadsiteEditTextTotalDcLoadOfSite.setText(decimalConversion.convertDecimal(mTotalDCLoadsiteEditTextTotalDcLoadOfSite.getText().toString()));
    }

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_dc_load_of_the_site);
        this.setTitle("Total DC load of the site");
        decimalConversion = new DecimalConversion();
        sessionManager = new SessionManager(Total_DC_Load_site.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Total_DC_Load_site.this, userId, ticketName);
        assignViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
        setInputDetails();
        mTotalDCLoadsiteEditTextTotalDcLoadOfSite.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });


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
                return true;
            case R.id.menuDone:
                DecimalFormatConversion();
                if (checkValiadtion()) {
                    submitDetails();
                    startActivity(new Intent(this, ActiveequipmentDetails.class));
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValiadtion() {
        String totalDcLoadOfSite = mTotalDCLoadsiteEditTextTotalDcLoadOfSite.getText().toString().trim();
        if (totalDcLoadOfSite.isEmpty() || totalDcLoadOfSite == null) {
            showToast("Enter Total DC Load of the Site ");
            return false;
        } else return true;
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
//                landDetailsData = gson.fromJson(jsonInString, LandDetailsData.class);

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                totalDCLoadofSiteData = hotoTransactionData.getTotalDCLoadofSiteData();

                mTotalDCLoadsiteEditTextTotalDcLoadOfSite.setText(totalDCLoadofSiteData.getTotalDcLoadOfSite());

            } else {
                //Toast.makeText(Total_DC_Load_site.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            //hotoTransactionData.setTicketNo(ticketId);

            String totalDcLoadOfSite = mTotalDCLoadsiteEditTextTotalDcLoadOfSite.getText().toString().trim();
            totalDCLoadofSiteData = new TotalDCLoadofSiteData(totalDcLoadOfSite);
            hotoTransactionData.setTotalDCLoadofSiteData(totalDCLoadofSiteData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
