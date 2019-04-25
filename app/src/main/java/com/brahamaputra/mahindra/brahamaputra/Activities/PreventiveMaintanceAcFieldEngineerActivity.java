package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.AcPreventiveMaintanceProcessParentDatum;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.Data.TicktetSubmissionFromFieldEngineerDatum;
import com.brahamaputra.mahindra.brahamaputra.Data.UpdateAcPreventiveMaintanceStatusResposeData;
import com.brahamaputra.mahindra.brahamaputra.Data.UserLoginResponseData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.Volley.SettingsMy;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PreventiveMaintanceAcFieldEngineerActivity extends BaseActivity {

    private String TAG = this.getClass().getName();

    private TextView mPreventiveMaintanceAcFieldEngineerTextViewCustomer;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewCustomerVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewCircle;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewCircleVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewState;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewStateVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewSsa;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewSsaVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewSiteName;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewSiteNameVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewSiteID;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewSiteIDVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewPmSheduledDateOfAc;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewPmSheduledDateOfAcVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewModeOfOpration;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewModeOfOprationVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewTicketNo;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewTicketNoVal;
    private LinearLayout mPreventiveMaintanceAcFieldEngineerLinearLayoutVendorName;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewVendorName;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewVendorNameVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianName;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianNameVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianMobNo;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianMobNoVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewTicketStatusToWip;
    private CheckBox mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewStatusSubmittedByTechnician;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewStatusSubmittedByTechnicianVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewDateSubmittedByTechnician;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewDateSubmittedByTechnicianVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewFeedBack;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal;
    private TextView mPreventiveMaintanceAcFieldEngineerTextViewRemark;
    private EditText mPreventiveMaintanceAcFieldEngineerEditTextRemark;
    private LinearLayout mLinearLayoutParent;

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    String flag = "";

    String str_feedBackVal;
    TicktetSubmissionFromFieldEngineerDatum ticktetSubmissionFromFieldEngineerDatum;

    public GPSTracker gpsTracker;

    private String customerName;
    private String circleName;
    private String stateName;
    private String ssaName;
    private String siteId;
    private String siteName;
    private String siteType;
    private String TicketId;
    private String TicketNO;
    private String TicketDate;
    private String acPmPlanDate;
    private String submittedDate;
    private String acPmSheduledDate;
    private String numberOfAc;
    private String modeOfOpration;
    private String vendorName;
    private String acTechnicianName;
    private String acTechnicianMobileNo;
    private String accessType;
    private String ticketAccess;
    private String acPmTickStatus;

    private String fieldEngineerLat = "0.0";
    private String fieldEngineerLong = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintance_ac_field_engineer);
        setTitle("Ticket Submission from Field Engineer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        accessType = intent.getStringExtra("accessType");
        ticketAccess = intent.getStringExtra("ticketAccess");
        acPmTickStatus = intent.getStringExtra("acPmTickStatus");
        modeOfOpration = intent.getStringExtra("modeOfOpration");

        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            invalidateOptionsMenu();
        }
        //flag = intent.getStringExtra("status");

        sessionManager = new SessionManager(PreventiveMaintanceAcFieldEngineerActivity.this);
        gpsTracker = new GPSTracker(PreventiveMaintanceAcFieldEngineerActivity.this);
        userId = sessionManager.getSessionUserId();
        //offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintanceAcFieldEngineerActivity.this, userId, ticketName);
        alertDialogManager = new AlertDialogManager(PreventiveMaintanceAcFieldEngineerActivity.this);
        ticktetSubmissionFromFieldEngineerDatum = new TicktetSubmissionFromFieldEngineerDatum();
        assignViews();
        initCombo();
        setDataToFields(intent);

        //setInputDetails();
    }

    private void assignViews() {
        mPreventiveMaintanceAcFieldEngineerTextViewCustomer = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_customer);
        mPreventiveMaintanceAcFieldEngineerTextViewCustomerVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_customerVal);
        mPreventiveMaintanceAcFieldEngineerTextViewCircle = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_circle);
        mPreventiveMaintanceAcFieldEngineerTextViewCircleVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_circleVal);
        mPreventiveMaintanceAcFieldEngineerTextViewState = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_state);
        mPreventiveMaintanceAcFieldEngineerTextViewStateVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_stateVal);
        mPreventiveMaintanceAcFieldEngineerTextViewSsa = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_ssa);
        mPreventiveMaintanceAcFieldEngineerTextViewSsaVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_ssaVal);
        mPreventiveMaintanceAcFieldEngineerTextViewSiteName = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_siteName);
        mPreventiveMaintanceAcFieldEngineerTextViewSiteNameVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_siteNameVal);
        mPreventiveMaintanceAcFieldEngineerTextViewSiteID = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_siteID);
        mPreventiveMaintanceAcFieldEngineerTextViewSiteIDVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_siteIDVal);
        mPreventiveMaintanceAcFieldEngineerTextViewPmSheduledDateOfAc = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_pmSheduledDateOfAc);
        mPreventiveMaintanceAcFieldEngineerTextViewPmSheduledDateOfAcVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_pmSheduledDateOfAcVal);
        mPreventiveMaintanceAcFieldEngineerTextViewModeOfOpration = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_modeOfOpration);
        mPreventiveMaintanceAcFieldEngineerTextViewModeOfOprationVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_modeOfOprationVal);
        mPreventiveMaintanceAcFieldEngineerTextViewTicketNo = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_ticketNo);
        mPreventiveMaintanceAcFieldEngineerTextViewTicketNoVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_ticketNoVal);
        mPreventiveMaintanceAcFieldEngineerLinearLayoutVendorName = (LinearLayout) findViewById(R.id.preventiveMaintanceAcFieldEngineer_linearLayout_vendorName);
        mPreventiveMaintanceAcFieldEngineerTextViewVendorName = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_vendorName);
        mPreventiveMaintanceAcFieldEngineerTextViewVendorNameVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_vendorNameVal);
        mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianName = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_acTechnicianName);
        mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianNameVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_acTechnicianNameVal);
        mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianMobNo = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_acTechnicianMobNo);
        mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianMobNoVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_acTechnicianMobNoVal);
        mPreventiveMaintanceAcFieldEngineerTextViewTicketStatusToWip = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_ticketStatusToWip);
        mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal = (CheckBox) findViewById(R.id.preventiveMaintanceAcFieldEngineer_checkBox_ticketStatusToWipVal);
        mPreventiveMaintanceAcFieldEngineerTextViewStatusSubmittedByTechnician = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_statusSubmittedByTechnician);
        mPreventiveMaintanceAcFieldEngineerTextViewStatusSubmittedByTechnicianVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_statusSubmittedByTechnicianVal);
        mPreventiveMaintanceAcFieldEngineerTextViewDateSubmittedByTechnician = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_dateSubmittedByTechnician);
        mPreventiveMaintanceAcFieldEngineerTextViewDateSubmittedByTechnicianVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_dateSubmittedByTechnicianVal);
        mPreventiveMaintanceAcFieldEngineerTextViewFeedBack = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_feedBack);
        mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_feedBackVal);
        mPreventiveMaintanceAcFieldEngineerTextViewRemark = (TextView) findViewById(R.id.preventiveMaintanceAcFieldEngineer_textView_remark);
        mPreventiveMaintanceAcFieldEngineerEditTextRemark = (EditText) findViewById(R.id.preventiveMaintanceAcFieldEngineer_editText_remark);
        mLinearLayoutParent = (LinearLayout) findViewById(R.id.LinearLayoutParent);

        /*if (flag.equals("Submitted by Technician")) {
            mLinearLayoutParent.setVisibility(View.VISIBLE);
        }*/


        if (modeOfOpration.equals("Out-source")) {
            mPreventiveMaintanceAcFieldEngineerLinearLayoutVendorName.setVisibility(View.VISIBLE);
        }

        mLinearLayoutParent.setVisibility(View.GONE);
        mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setEnabled(true);
        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            mLinearLayoutParent.setVisibility(View.VISIBLE);
            mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setEnabled(false);
            mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setChecked(true);

        }

    }

    private void initCombo() {
        mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintanceAcFieldEngineerActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcFieldEngineer_feedBack))),
                        "Feed Back",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_feedBackVal = item.get(position);
                        mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal.setText(str_feedBackVal);
                    }
                });
            }
        });

        mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.isChecked() == true) {
                    alertDialogManager.Dialog("Conformation", "Do you want to proceed?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            LocationManager lm = (LocationManager) PreventiveMaintanceAcFieldEngineerActivity.this.getSystemService(Context.LOCATION_SERVICE);
                            boolean gps_enabled = false;
                            boolean network_enabled = false;

                            try {
                                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                            } catch (Exception ex) {
                            }

                            if (!gps_enabled && !network_enabled) {
                                // notify user
                                alertDialogManager = new AlertDialogManager(PreventiveMaintanceAcFieldEngineerActivity.this);
                                alertDialogManager.Dialog("Conformation", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                    @Override
                                    public void onPositiveClick() {
                                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        PreventiveMaintanceAcFieldEngineerActivity.this.startActivity(myIntent);
                                    }
                                }).show();
                            } else {
                                if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                                    fieldEngineerLat = String.valueOf(gpsTracker.getLongitude());
                                    fieldEngineerLong = String.valueOf(gpsTracker.getLongitude());
                                    if (Conditions.isNetworkConnected(PreventiveMaintanceAcFieldEngineerActivity.this)) {
                                        updateStatusOfTicket(String.valueOf(gpsTracker.getLongitude()), String.valueOf(gpsTracker.getLatitude()));
                                    } else {
                                        //No Internet Connection
                                        showToast("Device has no internet connection.");
                                        mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setChecked(false);
                                    }
                                } else {
                                    alertDialogManager = new AlertDialogManager(PreventiveMaintanceAcFieldEngineerActivity.this);
                                    alertDialogManager.Dialog("Conformation", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                        @Override
                                        public void onPositiveClick() {
                                            if (gpsTracker.canGetLocation()) {
                                                Log.e(PreventiveMaintanceAcFieldEngineerActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                            }
                                        }
                                    }).show();
                                }
                            }
                        }

                        @Override
                        public void onNegativeClick() {
                            mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setChecked(false);
                        }
                    }).show();
                }

                //onBackPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        shareItem.setVisible(false);

        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
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
                alertDialogManager = new AlertDialogManager(PreventiveMaintanceAcFieldEngineerActivity.this);
                alertDialogManager.Dialog("Conformation", "Do you want to submit this ticket?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                            if (checkValidationOfArrayFields() == true) {
                                if (Conditions.isNetworkConnected(PreventiveMaintanceAcFieldEngineerActivity.this)) {
                                    submitDetails();
                                    //finish();
                                } else {
                                    //No Internet Connection
                                    showToast("Device has no internet connection.");
                                }

                            }
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("returnValue", "WIP");
        setResult(RESULT_OK, i);
        finish();
    }

    public void setDataToFields(Intent intent) {

        TicketId = intent.getStringExtra("TicketId");
        TicketNO = intent.getStringExtra("TicketNO");
        TicketDate = intent.getStringExtra("TicketDate");

        acPmPlanDate = intent.getStringExtra("acPmPlanDate");
        submittedDate = intent.getStringExtra("submittedDate");
        acPmSheduledDate = intent.getStringExtra("acPmSheduledDate");

        customerName = intent.getStringExtra("customerName");
        circleName = intent.getStringExtra("circleName");
        stateName = intent.getStringExtra("stateName");
        ssaName = intent.getStringExtra("ssaName");
        siteId = intent.getStringExtra("siteId");
        siteName = intent.getStringExtra("siteName");
        siteType = intent.getStringExtra("siteType");

        numberOfAc = intent.getStringExtra("numberOfAc");
        modeOfOpration = intent.getStringExtra("modeOfOpration");
        vendorName = intent.getStringExtra("vendorName");
        acTechnicianName = intent.getStringExtra("acTechnicianName");
        acTechnicianMobileNo = intent.getStringExtra("acTechnicianMobileNo");
        accessType = intent.getStringExtra("accessType");
        ticketAccess = intent.getStringExtra("ticketAccess");
        acPmTickStatus = intent.getStringExtra("acPmTickStatus");

        mPreventiveMaintanceAcFieldEngineerTextViewCustomerVal.setText(customerName);
        mPreventiveMaintanceAcFieldEngineerTextViewCircleVal.setText(circleName);
        mPreventiveMaintanceAcFieldEngineerTextViewStateVal.setText(stateName);
        mPreventiveMaintanceAcFieldEngineerTextViewSsaVal.setText(ssaName);
        mPreventiveMaintanceAcFieldEngineerTextViewSiteNameVal.setText(siteName);
        mPreventiveMaintanceAcFieldEngineerTextViewSiteIDVal.setText(siteId);
        mPreventiveMaintanceAcFieldEngineerTextViewPmSheduledDateOfAcVal.setText(acPmSheduledDate);
        mPreventiveMaintanceAcFieldEngineerTextViewModeOfOprationVal.setText(modeOfOpration);
        mPreventiveMaintanceAcFieldEngineerTextViewTicketNoVal.setText(TicketNO);
        if (modeOfOpration.equals("Out-source")) {
            mPreventiveMaintanceAcFieldEngineerTextViewVendorNameVal.setText(vendorName);
        }
        mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianNameVal.setText(acTechnicianName);
        mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianMobNoVal.setText(acTechnicianMobileNo);

        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            mPreventiveMaintanceAcFieldEngineerTextViewStatusSubmittedByTechnicianVal.setText(acPmTickStatus);
            mPreventiveMaintanceAcFieldEngineerTextViewDateSubmittedByTechnicianVal.setText(submittedDate);
        }
    }

    public boolean checkValidationOfArrayFields() {

        String feedBack = mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal.getText().toString().trim();
        String remark = mPreventiveMaintanceAcFieldEngineerEditTextRemark.getText().toString().trim();

        if (feedBack.isEmpty() || feedBack == null) {
            showToast("Select Feedback");
            return false;
        } else if (remark.isEmpty() || remark == null) {
            showToast("Enter remark");
            return false;
        } else return true;
    }

    //for update the ticket status when field engineer select the status WIP is yes
    private void updateStatusOfTicket(final String Longitude, final String Latitude) {

        showBusyProgress();
        String userId = sessionManager.getSessionUserId();
        String accessToken = sessionManager.getSessionDeviceToken();
        //String siteDbId = "";
        //String status = "WIP";

        try {
            JSONObject jsonString = new JSONObject();
            jsonString.put("UserId", userId);
            jsonString.put("AccessToken", accessToken);
            jsonString.put("SitePMAcTicketId", TicketId);
            jsonString.put("Longitude", Longitude);
            jsonString.put("Latitude", Latitude);

            //jsonString.put("Status", status);
            //jsonString.put("SiteId", siteDbId);//siteCodeId
            // jsonString.put("Id", siteDbId); //ticketDbId
            // jsonString.put("SitePMAcTicketNo", siteDbId); //ticketCode

            Log.e(PreventiveMaintanceAcFieldEngineerActivity.class.getName(), "WIP JSON : " + jsonString.toString());

            GsonRequest<UpdateAcPreventiveMaintanceStatusResposeData> updateAcPreventiveMaintanceStatusResposeDataGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.acPmCheckTicketByFieldEngineer, jsonString.toString(), UpdateAcPreventiveMaintanceStatusResposeData.class,
                    new Response.Listener<UpdateAcPreventiveMaintanceStatusResposeData>() {
                        @Override
                        public void onResponse(UpdateAcPreventiveMaintanceStatusResposeData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    hideBusyProgress();
                                    // setResult(RESULT_OK);
                                    showToast("Status updated successfully.");
                                    //onBackPressed();
                                    //finish();

                                    Intent intent = new Intent(PreventiveMaintanceAcFieldEngineerActivity.this, AcPreventiveMaintenanceDashboardActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivityForResult(intent, RESULT_OK);

                                } else {
                                    hideBusyProgress();
                                    showToast("Something went wrong");
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideBusyProgress();
                            Log.e(TAG, error.toString());
                        }
                    });
            updateAcPreventiveMaintanceStatusResposeDataGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            updateAcPreventiveMaintanceStatusResposeDataGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(updateAcPreventiveMaintanceStatusResposeDataGsonRequest, "updateAcPreventiveMaintanceStatusResposeData");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void submitDetails() {

        try {

            LocationManager lm = (LocationManager) PreventiveMaintanceAcFieldEngineerActivity.this.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                fieldEngineerLat = "0.0";
                fieldEngineerLong = "0.0";
            } else {
                if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                    fieldEngineerLat = String.valueOf(gpsTracker.getLongitude());
                    fieldEngineerLong = String.valueOf(gpsTracker.getLongitude());
                }
            }

            String feedback = mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal.getText().toString().trim();
            String remark = mPreventiveMaintanceAcFieldEngineerEditTextRemark.getText().toString().trim();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId", sessionManager.getSessionUserId());
            jsonObject.put("AccessToken", sessionManager.getSessionDeviceToken());
            jsonObject.put("TicketId", TicketId);
            //jsonObject.put("TicketNo", TicketNO);
            jsonObject.put("feedBack", feedback);
            jsonObject.put("remark", remark);
            jsonObject.put("acPmSiteEngineerLat", fieldEngineerLat);
            jsonObject.put("acPmSiteEngineerLong", fieldEngineerLong);

            GsonRequest<TicktetSubmissionFromFieldEngineerDatum> ticktetSubmissionFromFieldEngineerDatum = new GsonRequest<>(Request.Method.POST, Constants.submitAcPmTicket, jsonObject.toString(), TicktetSubmissionFromFieldEngineerDatum.class,
                    new Response.Listener<TicktetSubmissionFromFieldEngineerDatum>() {
                        @Override
                        public void onResponse(TicktetSubmissionFromFieldEngineerDatum response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    hideBusyProgress();
                                    //setResult(RESULT_OK);
                                    showToast("Record submitted successfully.");
                                    //finish();
                                    Intent intent = new Intent(PreventiveMaintanceAcFieldEngineerActivity.this, AcPreventiveMaintenanceDashboardActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivityForResult(intent, RESULT_OK);

                                } else {
                                    hideBusyProgress();
                                    showToast("Something went wrong");
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            ticktetSubmissionFromFieldEngineerDatum.setRetryPolicy(Application.getDefaultRetryPolice());
            ticktetSubmissionFromFieldEngineerDatum.setShouldCache(false);
            Application.getInstance().addToRequestQueue(ticktetSubmissionFromFieldEngineerDatum, "ticktetSubmissionFromFieldEngineerDatum");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //No important
    private void setInputDetails() {

        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                ticktetSubmissionFromFieldEngineerDatum = gson.fromJson(jsonInString, TicktetSubmissionFromFieldEngineerDatum.class);

                mPreventiveMaintanceAcFieldEngineerTextViewCustomerVal.setText(ticktetSubmissionFromFieldEngineerDatum.getCustomer());
                mPreventiveMaintanceAcFieldEngineerTextViewCircleVal.setText(ticktetSubmissionFromFieldEngineerDatum.getCircle());
                mPreventiveMaintanceAcFieldEngineerTextViewStateVal.setText(ticktetSubmissionFromFieldEngineerDatum.getState());
                mPreventiveMaintanceAcFieldEngineerTextViewSsaVal.setText(ticktetSubmissionFromFieldEngineerDatum.getSsa());
                mPreventiveMaintanceAcFieldEngineerTextViewSiteIDVal.setText(ticktetSubmissionFromFieldEngineerDatum.getSiteId());
                mPreventiveMaintanceAcFieldEngineerTextViewSiteNameVal.setText(ticktetSubmissionFromFieldEngineerDatum.getSiteName());
                mPreventiveMaintanceAcFieldEngineerTextViewPmSheduledDateOfAcVal.setText(ticktetSubmissionFromFieldEngineerDatum.getSheduledDateOfAcPm());
                mPreventiveMaintanceAcFieldEngineerTextViewModeOfOprationVal.setText(ticktetSubmissionFromFieldEngineerDatum.getModeOfOpration());
                mPreventiveMaintanceAcFieldEngineerTextViewTicketNoVal.setText(ticktetSubmissionFromFieldEngineerDatum.getTicketNo());
                mPreventiveMaintanceAcFieldEngineerTextViewVendorNameVal.setText(ticktetSubmissionFromFieldEngineerDatum.getVendorName());
                mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianNameVal.setText(ticktetSubmissionFromFieldEngineerDatum.getAcTechnicianName());
                mPreventiveMaintanceAcFieldEngineerTextViewAcTechnicianMobNoVal.setText(ticktetSubmissionFromFieldEngineerDatum.getAcTechnicianMobileNo());

                if (ticktetSubmissionFromFieldEngineerDatum.getTicketStatusToWip().equals("true")) {
                    mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setChecked(true);
                } else {
                    mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setChecked(false);
                }
                mPreventiveMaintanceAcFieldEngineerTextViewStatusSubmittedByTechnicianVal.setText(ticktetSubmissionFromFieldEngineerDatum.getStatus());
                mPreventiveMaintanceAcFieldEngineerTextViewDateSubmittedByTechnicianVal.setText(ticktetSubmissionFromFieldEngineerDatum.getSubmittedDate());
                mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal.setText(ticktetSubmissionFromFieldEngineerDatum.getFeedBack());
                mPreventiveMaintanceAcFieldEngineerEditTextRemark.setText(ticktetSubmissionFromFieldEngineerDatum.getRemark());


            }
        } catch (Exception e) {

        }

    }

    private void clearFields() {
        mPreventiveMaintanceAcFieldEngineerTextViewFeedBackVal.setText("");
        mPreventiveMaintanceAcFieldEngineerEditTextRemark.setText("");
        mPreventiveMaintanceAcFieldEngineerCheckBoxTicketStatusToWipVal.setChecked(false);
    }

    public void submitFieldEngineerAcPmSiteTicket() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {

                showBusyProgress();
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");
                Log.e("123", jsonInString);

                GsonRequest<TicktetSubmissionFromFieldEngineerDatum> submitSitePmAcTicketRequest = new GsonRequest<>(Request.Method.POST, Constants.submitSitePMTicket, jsonInString, TicktetSubmissionFromFieldEngineerDatum.class,
                        new Response.Listener<TicktetSubmissionFromFieldEngineerDatum>() {
                            @Override
                            public void onResponse(@NonNull TicktetSubmissionFromFieldEngineerDatum response) {
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

                submitSitePmAcTicketRequest.setRetryPolicy(Application.getDefaultRetryPolice());
                submitSitePmAcTicketRequest.setShouldCache(false);
                Application.getInstance().addToRequestQueue(submitSitePmAcTicketRequest, "submitSitePmTicketRequest");
            }
        } catch (Exception e) {

        }
    }

    private void removeOfflineCache() {
        if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
            offlineStorageWrapper.removedOffLineFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");
        }
    }

}
