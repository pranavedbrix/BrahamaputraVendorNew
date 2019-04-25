package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.UserLoginResponseData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.Volley.JsonRequest;
import com.brahamaputra.mahindra.brahamaputra.Volley.SettingsMy;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;

public class PriventiveMaintenanceSiteTransactionActivity extends BaseActivity {

    private static final String TAG = PriventiveMaintenanceSiteTransactionActivity.class.getSimpleName();

    private EditText mPriventiveMaintenanceSiteTransEditTextCustomerName;
    private EditText mPriventiveMaintenanceSiteTransEditTextCircle;
    private EditText mPriventiveMaintenanceSiteTransEditTextState;
    private EditText mPriventiveMaintenanceSiteTransEditTextSsa;
    private EditText mPriventiveMaintenanceSiteTransEditTextNameOfSite;
    private EditText mPriventiveMaintenanceSiteTransEditTextSiteID;
    private EditText mPriventiveMaintenanceSiteTransEditTextSheduledDateOfPm;
    private EditText mPriventiveMaintenanceSiteTransEditTextActualPmExecutionDate;
    private Button mPriventiveMaintenanceSiteTransButtonSubmit;
    public static final int RESULT_PRIVENTIVE_MAINTENANCE_SITE_READING = 258;
    public GPSTracker gpsTracker;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketName = "";
    private String ticketId = "";
    private SessionManager sessionManager;

    private String checkInLat = "0.0";
    private String checkInLong = "0.0";
    private String checkInBatteryData = "0";

    private String checkOutLat = "0.0";
    private String checkOutLong = "0.0";
    private String checkOutBatteryData = "0";

    public double siteLongitude = 0;
    public double siteLatitude = 0;

    private AlertDialogManager alertDialogManager;
    private PreventiveMaintanceSiteTransactionDetails preventiveMaintanceSiteTransactionDetails;

    private String sitePMTicketStatus;
    ArrayList<BatteryType> batteryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priventive_maintenance_site_transaction);
        assignViews();
        this.setTitle("Site PM Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkInBatteryData = "" + GlobalMethods.getBattery_percentage(PriventiveMaintenanceSiteTransactionActivity.this);
        batteryType = new ArrayList<BatteryType>();
        Intent intent = getIntent();
        batteryType = (ArrayList<BatteryType>) intent.getSerializableExtra("batteryType");
        String id = intent.getStringExtra("ticketNO");
        sitePMTicketStatus = intent.getStringExtra("status");
        this.setTitle(id);

        //ticketId = intent.getStringExtra("ticketNO");
        //this.setTitle(ticketId);
        checkInLat = intent.getStringExtra("latitude");
        checkInLong = intent.getStringExtra("longitude");

        alertDialogManager = new AlertDialogManager(PriventiveMaintenanceSiteTransactionActivity.this);
        sessionManager = new SessionManager(PriventiveMaintenanceSiteTransactionActivity.this);
        userId = sessionManager.getSessionUserId();
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = sessionManager.getSessionUserTicketName();

        preventiveMaintanceSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PriventiveMaintenanceSiteTransactionActivity.this, userId, ticketName);
        getOfflineData();
        gpsTracker = new GPSTracker(PriventiveMaintenanceSiteTransactionActivity.this);

        mPriventiveMaintenanceSiteTransButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PriventiveMaintenanceSiteTransactionActivity.this, SitePreventiveMaintenanceSectionsListActivity.class);
                intent.putExtra("batteryType", batteryType);
                //intent.putExtra("ticketName", "");

                startActivityForResult(intent, RESULT_PRIVENTIVE_MAINTENANCE_SITE_READING);

            }
        });
        checkNetworkConnection();

    }

    private void assignViews() {
        mPriventiveMaintenanceSiteTransEditTextCustomerName = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_customerName);
        mPriventiveMaintenanceSiteTransEditTextCircle = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_circle);
        mPriventiveMaintenanceSiteTransEditTextState = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_state);
        mPriventiveMaintenanceSiteTransEditTextSsa = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_ssa);
        mPriventiveMaintenanceSiteTransEditTextNameOfSite = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_nameOfSite);
        mPriventiveMaintenanceSiteTransEditTextSiteID = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_siteID);
        mPriventiveMaintenanceSiteTransEditTextSheduledDateOfPm = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_sheduledDateOfPm);
        mPriventiveMaintenanceSiteTransEditTextActualPmExecutionDate = (EditText) findViewById(R.id.priventiveMaintenanceSiteTrans_editText_actualPmExecutionDate);
        mPriventiveMaintenanceSiteTransButtonSubmit = (Button) findViewById(R.id.priventiveMaintenanceSiteTrans_button_submit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userpm_site_transaction_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuSubmit);
        // show the button when some condition is true
        shareItem.setVisible(false);
        if (preventiveMaintanceSiteTransactionDetails.isAtLeastOneSitePmFormsSubmit() == 1 || preventiveMaintanceSiteTransactionDetails.isAtLeastOneSitePmFormsSubmit() == 2) {
            shareItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuSubmit:
                LocationManager lm = (LocationManager) PriventiveMaintenanceSiteTransactionActivity.this.getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;

                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (!gps_enabled && !network_enabled) {
                    // notify user
                    alertDialogManager = new AlertDialogManager(PriventiveMaintenanceSiteTransactionActivity.this);
                    alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            PriventiveMaintenanceSiteTransactionActivity.this.startActivity(myIntent);
                        }
                    }).show();
                } else {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        checkOutLat = String.valueOf(gpsTracker.getLatitude());
                        checkOutLong = String.valueOf(gpsTracker.getLongitude());

                        CheckSubmitFlagOfAllSitePmForms();

                    } else {
                        //showToast("Could not detecting location.");
                        alertDialogManager = new AlertDialogManager(PriventiveMaintenanceSiteTransactionActivity.this);
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By 008 on 10-11-2018
                                    Log.e(UserHotoTransactionActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CheckSubmitFlagOfAllSitePmForms() {
        try {
            if (preventiveMaintanceSiteTransactionDetails.isAtLeastOneSitePmFormsSubmit() == 1) {
                hideBusyProgress();
                alertDialogManager = new AlertDialogManager(PriventiveMaintenanceSiteTransactionActivity.this);
                alertDialogManager.Dialog("Confirmation", "Some sections not fully completed.Do you want to submit?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        showSettingsAlert();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }).show();
            } else if (preventiveMaintanceSiteTransactionDetails.isAtLeastOneSitePmFormsSubmit() == 2) {
                showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidationOnSubmitPmTransactionTicket() {
        String userId = sessionManager.getSessionUserId();
        String accessToken = sessionManager.getSessionDeviceToken();
        String latitude = String.valueOf(gpsTracker.getLatitude());
        String longitude = String.valueOf(gpsTracker.getLongitude());
        String siteID = mPriventiveMaintenanceSiteTransEditTextSiteID.getText().toString();
        String customer = mPriventiveMaintenanceSiteTransEditTextCustomerName.getText().toString();
        String state = mPriventiveMaintenanceSiteTransEditTextState.getText().toString();
        String ssa = mPriventiveMaintenanceSiteTransEditTextSsa.getText().toString();
        String nameOfSite = mPriventiveMaintenanceSiteTransEditTextNameOfSite.getText().toString();
        String sheduledDatePm = mPriventiveMaintenanceSiteTransEditTextSheduledDateOfPm.getText().toString();
        String actualPmExecutionDate = mPriventiveMaintenanceSiteTransEditTextActualPmExecutionDate.getText().toString();

        if (siteID.isEmpty() || siteID == null) {
            showToast("Select Site Name");
            return false;
        } else if (customer.isEmpty() || customer == null) {
            showToast("Select DG ID / QR Code");
            return false;
        } else if (state.isEmpty() || state == null) {
            showToast("Enter Present DG HMR");
            return false;
        } else if (ssa.isEmpty() || ssa == null) {
            showToast("Upload Photo of HMR");
            return false;
        } else if (nameOfSite.isEmpty() || nameOfSite == null) {
            showToast("Enter Tank Balance Before Filling");
            return false;
        } else if (sheduledDatePm.isEmpty() || sheduledDatePm == null) {
            showToast("Enter Filling Quantity");
            return false;
        } else if (actualPmExecutionDate.isEmpty() || actualPmExecutionDate == null) {
            showToast("Enter Diesel Price");
            return false;
        } else return true;
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Confirmation", "Do you want to submit this ticket?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                submitDetails();
            }

            @Override
            public void onNegativeClick() {

            }
        }).show();
    }

    private void submitDetails() {
        try {

            checkOutBatteryData = "" + GlobalMethods.getBattery_percentage(PriventiveMaintenanceSiteTransactionActivity.this);
            preventiveMaintanceSiteTransactionDetails.setUserId(sessionManager.getSessionUserId());
            preventiveMaintanceSiteTransactionDetails.setAccessToken(sessionManager.getSessionDeviceToken());
            preventiveMaintanceSiteTransactionDetails.setTicketId(ticketId);
            preventiveMaintanceSiteTransactionDetails.setCheckOutLatitude(checkOutLat);
            preventiveMaintanceSiteTransactionDetails.setCheckOutLongitude(checkOutLong);
            //preventiveMaintanceSiteTransactionDetails.setCheckOutBatteryData(checkOutBatteryData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(preventiveMaintanceSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt", jsonString);
            submitPMSiteTicket();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void submitPMSiteTicket() {

        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
                showBusyProgress();
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");
                Log.e("123", jsonInString);
                //remaining to add webservice for submit PM site Ticket
                GsonRequest<UserLoginResponseData>
                        submitSitePmTicketRequest = new GsonRequest<>(Request.Method.POST, Constants.submitSitePMTicket, jsonInString, UserLoginResponseData.class,
                        new Response.Listener<UserLoginResponseData>() {
                            @Override
                            public void onResponse(@NonNull UserLoginResponseData response) {
                                hideBusyProgress();
                                if (response.getError() != null) {
                                    showToast(response.getError().getErrorMessage());
                                } else {
                                    if (response.getSuccess() == 1) {
                                        showToast("Ticket submitted successfully.");
                                        sessionManager.updateSessionUserTicketId(null);
                                        sessionManager.updateSessionUserTicketName(null);
                                        setResult(RESULT_OK);
                                        removeOfflineCache();
                                        finish();
                                    }
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideBusyProgress();
                        showToast(SettingsMy.getErrorMessage(error));
                    }
                });

                submitSitePmTicketRequest.setRetryPolicy(Application.getDefaultRetryPolice());
                submitSitePmTicketRequest.setShouldCache(false);
                Application.getInstance().addToRequestQueue(submitSitePmTicketRequest, "submitSitePmTicketRequest");
            }
        } catch (Exception e) {
            hideBusyProgress();
            e.printStackTrace();
        }
    }

    private void removeOfflineCache() {
        if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
            offlineStorageWrapper.removedOffLineFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");
        }
    }

    public void checkNetworkConnection() {
        if (!isNetworkConnected()) {
            mPriventiveMaintenanceSiteTransEditTextCustomerName.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextCircle.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextState.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextSsa.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextNameOfSite.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextSiteID.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextSheduledDateOfPm.setHint("Offline");
            mPriventiveMaintenanceSiteTransEditTextActualPmExecutionDate.setHint("Offline");

        } else {
            Intent intent = getIntent();

            mPriventiveMaintenanceSiteTransEditTextCustomerName.setText(intent.getStringExtra("customerName"));
            mPriventiveMaintenanceSiteTransEditTextState.setText(intent.getStringExtra("stateName"));
            mPriventiveMaintenanceSiteTransEditTextCircle.setText(intent.getStringExtra("circleName"));
            mPriventiveMaintenanceSiteTransEditTextSsa.setText(intent.getStringExtra("ssaName"));
            mPriventiveMaintenanceSiteTransEditTextNameOfSite.setText(intent.getStringExtra("siteName"));
            mPriventiveMaintenanceSiteTransEditTextSiteID.setText(intent.getStringExtra("siteId"));
            mPriventiveMaintenanceSiteTransEditTextSheduledDateOfPm.setText(intent.getStringExtra("sitePmScheduledDate"));
            mPriventiveMaintenanceSiteTransEditTextActualPmExecutionDate.setText(intent.getStringExtra("sitePmScheduledDate"));

            /*if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                checkInLat = String.valueOf(gpsTracker.getLatitude());
                checkInLong = String.valueOf(gpsTracker.getLongitude());*/
            if (sitePMTicketStatus.equals("Open") || sitePMTicketStatus.equals("Reassigned")) {
                submitCheckIn(checkInLong, checkInLat, checkInBatteryData);
            }
            /*} else {
                showToast("Could not detecting location.");
            }*/
        }
    }

    private void submitCheckIn(String longitude, String latitude, String batteryLevel) {
        showBusyProgress();
        try {
            JSONObject jo = new JSONObject();
            try {
                jo.put("UserId", sessionManager.getSessionUserId());
                jo.put("AccessToken", sessionManager.getSessionDeviceToken());
                jo.put("Longitude", longitude);
                jo.put("Latitude", latitude);
                jo.put("SitePMTicketId", ticketId);
                /*jo.put("BatteryLevel", batteryLevel);*/
            } catch (JSONException e) {
                Log.e(LoginActivity.class.getName(), e.getMessage().toString());
                return;
            }

            JsonRequest hototticketstatusclockin = new JsonRequest(Request.Method.POST, Constants.updatedSitePMCheckIn, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(@NonNull JSONObject response) {
                            hideBusyProgress();
                            try {
                                if (response != null) {
                                    if (response.has("Success")) {
                                        int success = response.getInt("Success");
                                        if (success == 1) {
                                            showToast("Checked In");
                                            mPriventiveMaintenanceSiteTransEditTextActualPmExecutionDate.setText(response.getString("SitePMExecutionDate")/*.toString().substring(0, 10)*/);
                                        } else {
                                            showToast("Problem while check-in");
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                showToast("Exception :" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(SettingsMy.getErrorMessage(error));
                }
            });
            hototticketstatusclockin.setRetryPolicy(Application.getDefaultRetryPolice());
            hototticketstatusclockin.setShouldCache(false);
            Application.getInstance().addToRequestQueue(hototticketstatusclockin, "hototticketstatusclockin");

        } catch (Exception e) {
            hideBusyProgress();
            e.printStackTrace();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getOfflineData() {
        try {

            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
            /*if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");*/
                // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                preventiveMaintanceSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (preventiveMaintanceSiteTransactionDetails != null) {
                   /* mUserHotoTransEditTextSiteID.setText(hotoTransactionData.getSiteId());
                    mUserHotoTransEditTextSiteAddress.setText(hotoTransactionData.getSiteAddress());
                    mUserHotoTransSpinnerSourceOfPowerVal.setText(hotoTransactionData.getSourceOfPower());
                    hototicket_sourceOfPower = hotoTransactionData.getSourceOfPower() == null || hotoTransactionData.getSourceOfPower().isEmpty() ? "" : hotoTransactionData.getSourceOfPower();*/
                }

            } else {
//                Toast.makeText(UserHotoTransactionActivity.this, "No offline data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        invalidateOptionsMenu();
        if (requestCode == RESULT_PRIVENTIVE_MAINTENANCE_SITE_READING && resultCode == RESULT_OK) {
            getOfflineData();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
