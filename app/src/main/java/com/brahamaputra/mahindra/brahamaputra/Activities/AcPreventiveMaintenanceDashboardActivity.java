package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ParseException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.progresviews.ProgressWheel;
import com.brahamaputra.mahindra.brahamaputra.Adapters.PmAcExpListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
import com.brahamaputra.mahindra.brahamaputra.Data.SitePmAcTicketList;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AcPreventiveMaintenanceDashboardActivity extends BaseActivity {

    private ProgressWheel wheelprogress;
    private TextView acPreventiveMaintenanceSection_textView_openTickets;
    private TextView acPreventiveMaintenanceSection_textView_allTickets;
    private PmAcExpListAdapter pmAcExpListAdapter;
    public ExpandableListView pmSiteList_listView_siteList;
    private AlertDialogManager alertDialogManager;
    private SessionManager sessionManager;
    public GPSTracker gpsTracker;
    private SitePmAcTicketList acPmTicketList;
    /////
    public static final int RESULT_AC_PM_SUBMIT = 257;
    private TextView txtNoTicketFound;

    /////////////No Imp Below Part Testing Purpose

    /*private LinearLayout mLinearLayoutStatus;
    private ProgressWheel mWheelprogress;
    private LinearLayout mLinearLayoutContainer1;
    private TextView mAcPreventiveMaintenanceSectionTextViewName;
    private TextView mAcPreventiveMaintenanceSectionTextViewNo;
    private LinearLayout mLinearLayoutContainer2;
    private TextView mAcPreventiveMaintenanceSectionTextViewName2;
    private TextView mAcPreventiveMaintenanceSectionTextViewNo2;
    private LinearLayout mPmSiteDashboardLinearLayoutTicket1;
    private TextView mTextViewHotoName;
    private TextView mTextViewSiteID;
    private TextView mTextViewSiteName;
    private TextView mTextViewSiteSSA;
    private TextView mTextViewSiteAddress;
    private LinearLayout mPmSiteDashboardLinearLayoutTicket2;
    private TextView mTextViewHotoName1;
    private TextView mTextViewSiteID1;
    private TextView mTextViewSiteName1;
    private TextView mTextViewSiteSSA1;
    private TextView mTextViewSiteAddress1;
    private LinearLayout mPriventiveMaintenanceSiteLinearLayoutTicket1;
    private LinearLayout mPriventiveMaintenanceSiteLinearLayoutTicket2;
    public ExpandableListView pmSiteList_listView_siteacList;
    public ExpandableListView acSiteList_listView_siteList;
    private SitePmAcTicketList sitePmAcTicketList;
    String returnValueFromFieldEngineerForm;
    */

    //public GPSTracker gpsTracker;
    //private AlertDialogManager alertDialogManager;
    //private SessionManager sessionManager;
    //public static final int RESULT_AC_PM_SUBMIT = 257;
    //private TextView txtNoTicketFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_preventive_maintenance_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("AC PM");

        wheelprogress = (ProgressWheel) findViewById(R.id.wheelprogress);
        acPreventiveMaintenanceSection_textView_openTickets = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_openTickets);
        acPreventiveMaintenanceSection_textView_allTickets = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_allTickets);
        pmSiteList_listView_siteList = (ExpandableListView) findViewById(R.id.acPmSiteList_listView_siteAcList);
        txtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
        txtNoTicketFound.setVisibility(View.GONE);

        acPmTicketList = new SitePmAcTicketList();
        alertDialogManager = new AlertDialogManager(AcPreventiveMaintenanceDashboardActivity.this);
        sessionManager = new SessionManager(AcPreventiveMaintenanceDashboardActivity.this);
        gpsTracker = new GPSTracker(AcPreventiveMaintenanceDashboardActivity.this);

        //assignViews();
        //setListner();

        prepareListData();

        pmSiteList_listView_siteList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                pmSiteList_listView_siteList.expandGroup(groupPosition);
                return true;
            }
        });

        pmSiteList_listView_siteList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, final int childPosition, long id) {
                // notify user

                LocationManager lm = (LocationManager) AcPreventiveMaintenanceDashboardActivity.this.getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;

                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (!gps_enabled && !network_enabled) {
                    // notify user
                    alertDialogManager.Dialog("Conformation", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            AcPreventiveMaintenanceDashboardActivity.this.startActivity(myIntent);
                        }
                    }).show();
                } else {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        if (acPmTicketList != null) {
                            //final String sitePMAcTicketId = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getId().toString();
                            String myFormat = "dd/MMM/yyyy"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            String currentDateTimeString = sdf.format(new Date());

                            /*String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());*/
                            final String sitePMAcTicketId = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getId() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getId().toString();
                            final String sitePMAcTicketNo = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSitePMAcTicketNo() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSitePMAcTicketNo().toString();
                            final String sitePMAcTicketDate = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSitePMAcTicketDate() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSitePMAcTicketDate().toString();
                            final String pmPlanDate = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getPmPlanDate() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getPmPlanDate().toString();
                            final String submittedDate = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSubmittedDate() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSubmittedDate().toString();
                            final String sheduledDateOfAcPm = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSheduledDateOfAcPm() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSheduledDateOfAcPm().toString();
                            final String modeOfOpration = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getModeOfOpration() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getModeOfOpration().toString();
                            final String vendorName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getVendorName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getVendorName().toString();
                            final String acTechnicianName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getAcTechnicianName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getAcTechnicianName().toString();
                            final String acTechnicianMobileNo = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getAcTechnicianMobileNo() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getAcTechnicianMobileNo().toString();
                            final String siteDBId = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteDBId() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteDBId().toString();
                            final String siteId = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteId() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteId().toString();
                            final String siteName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteName().toString();

                            final String numberOfAc = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getNumberOfAc() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getNumberOfAc().toString();
                            final String stateName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteName().toString();
                            final String customerName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getCustomerName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getCustomerName().toString();
                            final String circleName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getCircleName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getCircleName().toString();
                            final String ssaName = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSSAName() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSSAName().toString();

                            final String siteType = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteType() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getSiteType().toString();
                            final String accessType = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getAccessType() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getAccessType().toString();
                            final String ticketAccess = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getTicketAccess() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getTicketAccess().toString();

                            final String acPmTickStatus = acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getStatus() == null ? "" : acPmTicketList.getSitePMTicketsDates().get(groupPosition).getSitePMAcTickets().get(childPosition).getStatus().toString();


                            if (getDaysRemainingForSheduledDate(currentDateTimeString, sheduledDateOfAcPm)) {
                                // comment for not added checkSystemLocation || acPmTickStatus.equals("Processed")
                                if (acPmTickStatus.equals("Open") || acPmTickStatus.equals("WIP") || acPmTickStatus.equals("Reassigned")) {
                                    if (acPmTickStatus.equals("Open")) {

                                        int flag = 0;
                                        if (accessType.equals("S") && ticketAccess.equals("1") && (acPmTickStatus.equals("Open") || acPmTickStatus.equals("Reassigned"))) {
                                            flag = 1;
                                        } else if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                                            flag = 1;
                                        } else if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                                            flag = 1;
                                        } else {
                                            flag = 0;
                                            showToast("Access denied in ticket status " + acPmTickStatus + " mode");
                                        }
                                        if (flag == 1) {
                                            checkSystemLocation(customerName, circleName, stateName, ssaName, siteDBId, siteId, siteName, siteType,
                                                    sitePMAcTicketId, sitePMAcTicketNo, sitePMAcTicketDate, pmPlanDate,
                                                    submittedDate, sheduledDateOfAcPm, numberOfAc, modeOfOpration,
                                                    vendorName, acTechnicianName, acTechnicianMobileNo, accessType, ticketAccess, acPmTickStatus);
                                        }

                                    } else {
                                        int flag = 0;
                                        if (accessType.equals("S") && ticketAccess.equals("1") && (acPmTickStatus.equals("Open") || acPmTickStatus.equals("Reassigned"))) {
                                            flag = 1;
                                        } else if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                                            flag = 1;
                                        } else if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                                            flag = 1;
                                        } else {
                                            flag = 0;
                                            showToast("Access denied in ticket status " + acPmTickStatus + " mode");
                                        }
                                        if (flag == 1) {
                                            checkSystemLocation(customerName, circleName, stateName, ssaName, siteDBId, siteId, siteName, siteType,
                                                    sitePMAcTicketId, sitePMAcTicketNo, sitePMAcTicketDate, pmPlanDate,
                                                    submittedDate, sheduledDateOfAcPm, numberOfAc, modeOfOpration,
                                                    vendorName, acTechnicianName, acTechnicianMobileNo, accessType, ticketAccess, acPmTickStatus);
                                        }
                                    }

                                }
                            }

                        }

                    } else {
                        alertDialogManager.Dialog("Conformation", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    Log.e(PriventiveMaintenanceSiteTransactionActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                }
                return false;
            }
        });
    }

    // added by tiger on 17092019 for date validation
    public boolean getDaysRemainingForSheduledDate(String currentDateTimeString, String sitePmScheduledDate) {

        long requiredDaysForStartWork = 3;
        long lastDayForStartWork = 0;
        String newCurrentDate, newSheduledDate;
        Date newFormatedCurrentDate;
        Date newFormatedSheduledDate;
        /* String date="May 1, 2019 6:30:00 PM";*/

        SimpleDateFormat simpleDateFormatForCurrentDate = new SimpleDateFormat("dd/MMM/yyyy");
        SimpleDateFormat simpleDateFormatForSheduleDate = new SimpleDateFormat("dd/MMM/yyyy");

        SimpleDateFormat newSimpleDateFormatForDaysCalculate = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        try {

            Date currentDate = simpleDateFormatForCurrentDate.parse(currentDateTimeString);
            Date sheduledDate = simpleDateFormatForSheduleDate.parse(sitePmScheduledDate);

            newCurrentDate = newSimpleDateFormatForDaysCalculate.format(currentDate);
            newSheduledDate = newSimpleDateFormatForDaysCalculate.format(sheduledDate);

            newFormatedCurrentDate = newSimpleDateFormatForDaysCalculate.parse(newCurrentDate);
            newFormatedSheduledDate = newSimpleDateFormatForDaysCalculate.parse(newSheduledDate);

            long different = newFormatedSheduledDate.getTime() - newFormatedCurrentDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            if (elapsedDays <= requiredDaysForStartWork) {//&& elapsedDays >= lastDayForStartWork
                return true;
            } else if (elapsedDays > requiredDaysForStartWork) {
                showToast("You can open this ticket only 3 days before Scheduled Date:" + sitePmScheduledDate);
            } /*else if (elapsedDays < lastDayForStartWork) {
                showToast("You don't have access to this ticket after " + newSheduledDate + " this date");
            }*/

           /* long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;*/

        } catch (ParseException e) {
            Log.d("ParseException", e.getMessage());
        } catch (java.text.ParseException e) {
            Log.d("ParseException", e.getMessage());
        }

        return false;
    }

    private void prepareListData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            /*{
                "UserId":"120",
                    "AccessToken":"MjUyLTg1REEyUzMtQURTUzVELUVJNUI0QTIyMTEyMA=="}*/

            /*{
                "UserId":"145",
                    "AccessToken":"MjUyLTg1REEyUzMtQURTUzVELUVJNUI0QTIyMTE0NQ=="
            }*/
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());

            Log.i(PreventiveMaintenanceDashboard.class.getName(), Constants.hototTicketList + "\n\n" + jo.toString());

            GsonRequest<SitePmAcTicketList> getAssignAvailabilityLearnersListRequest = new GsonRequest<>(Request.Method.POST, Constants.acPmTicketList, jo.toString(), SitePmAcTicketList.class,
                    new Response.Listener<SitePmAcTicketList>() {
                        @Override
                        public void onResponse(@NonNull SitePmAcTicketList response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    acPmTicketList = response;
                                    if (acPmTicketList.getSitePMTicketSummary() != null) {
                                        acPreventiveMaintenanceSection_textView_openTickets.setText(acPmTicketList.getSitePMTicketSummary().getOpenTickets() == null || acPmTicketList.getSitePMTicketSummary().getOpenTickets().isEmpty() ? "0" : acPmTicketList.getSitePMTicketSummary().getOpenTickets().toString());
                                        acPreventiveMaintenanceSection_textView_allTickets.setText(acPmTicketList.getSitePMTicketSummary().getTotalTickets() == null || acPmTicketList.getSitePMTicketSummary().getTotalTickets().isEmpty() ? "0" : acPmTicketList.getSitePMTicketSummary().getTotalTickets().toString());

                                        double per = 0.0;
                                        double circlePer = 0.0;
                                        int roundPer = 0;
                                        per = acPmTicketList.getSitePMTicketSummary().getPercentage();
                                        circlePer = (3.6) * Double.valueOf(per);
                                        roundPer = (int) Math.round(circlePer);

                                        DecimalFormat df = new DecimalFormat("###.##");
                                        df.setRoundingMode(RoundingMode.FLOOR);
                                        per = new Double(df.format(per));


                                        wheelprogress.setPercentage(roundPer);
                                        wheelprogress.setStepCountText(String.valueOf(per));


                                    }
                                    if (acPmTicketList.getSitePMTicketsDates() != null && acPmTicketList.getSitePMTicketsDates().size() > 0) {
                                        txtNoTicketFound.setVisibility(View.GONE);
                                        pmSiteList_listView_siteList.setVisibility(View.VISIBLE);
                                        pmAcExpListAdapter = new PmAcExpListAdapter(AcPreventiveMaintenanceDashboardActivity.this, acPmTicketList);
                                        pmSiteList_listView_siteList.setAdapter(pmAcExpListAdapter);
                                        for (int i = 0; i < acPmTicketList.getSitePMTicketsDates().size(); i++) {
                                            pmSiteList_listView_siteList.expandGroup(i);
                                        }
                                    } else {
                                        pmSiteList_listView_siteList.setVisibility(View.GONE);
                                        txtNoTicketFound.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }, new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();

                }
            });
            getAssignAvailabilityLearnersListRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getAssignAvailabilityLearnersListRequest.setShouldCache(false);
            Application.getInstance().

                    addToRequestQueue(getAssignAvailabilityLearnersListRequest, "acPmTicketList");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }


    public void checkSystemLocation(final String customerName, final String circleName, final String stateName, final String ssaName,
                                    final String siteDBId, final String siteId, final String siteName, final String siteType, final String acPmTicketId,
                                    final String acPmTicketNo, final String acPmTicketDate, final String acPmPlanDate,
                                    final String submittedDate, final String acPmSheduledDate, final String numberOfAc,
                                    final String modeOfOpration, final String vendorName, final String acTechnicianName,
                                    final String acTechnicianMobileNo, final String accessType, final String ticketAccess, final String acPmTickStatus) {

        LocationManager lm = (LocationManager) AcPreventiveMaintenanceDashboardActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            alertDialogManager.Dialog("Conformation", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                @Override
                public void onPositiveClick() {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    AcPreventiveMaintenanceDashboardActivity.this.startActivity(myIntent);
                }
            }).show();
        } else {
            if (Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this)) {

                Intent intent = new Intent(AcPreventiveMaintenanceDashboardActivity.this, AcPreventiveMaintenanceSectionsListActivity.class);
                intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this));

                intent.putExtra("TicketId", acPmTicketId);
                intent.putExtra("TicketNO", acPmTicketNo);
                intent.putExtra("TicketDate", acPmTicketDate);

                intent.putExtra("acPmPlanDate", acPmPlanDate);
                intent.putExtra("submittedDate", submittedDate);
                intent.putExtra("acPmSheduledDate", acPmSheduledDate);

                intent.putExtra("customerName", customerName);
                intent.putExtra("circleName", circleName);
                intent.putExtra("stateName", stateName);
                intent.putExtra("ssaName", ssaName);
                intent.putExtra("siteDBId", siteDBId);
                intent.putExtra("siteId", siteId);
                intent.putExtra("siteName", siteName);
                intent.putExtra("siteType", siteType);
                //intent.putExtra("siteAddress", siteAddress);

                intent.putExtra("numberOfAc", numberOfAc);
                intent.putExtra("modeOfOpration", modeOfOpration);
                intent.putExtra("vendorName", vendorName);
                intent.putExtra("acTechnicianName", acTechnicianName);
                intent.putExtra("acTechnicianMobileNo", acTechnicianMobileNo);
                intent.putExtra("accessType", accessType);
                intent.putExtra("ticketAccess", ticketAccess);
                intent.putExtra("acPmTickStatus", acPmTickStatus);

                intent.putExtra("latitude", String.valueOf(gpsTracker.getLatitude()));
                intent.putExtra("longitude", String.valueOf(gpsTracker.getLongitude()));

                sessionManager.updateSessionUserTicketId(acPmTicketId);
                sessionManager.updateSessionUserTicketName(acPmTicketNo);
                startActivityForResult(intent, RESULT_AC_PM_SUBMIT);


            } else {
                //No Internet Connection
                showToast("Device has no internet connection.");
                /*alertDialogManager.Dialog("Conformation", "Device has no internet connection. Do you want to use offline mode?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent(AcPreventiveMaintenanceDashboardActivity.this, AcPreventiveMaintenanceSectionsListActivity.class);
                        intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this));
                        intent.putExtra("TicketNO", acPmTicketNo);
                        sessionManager.updateSessionUserTicketId(acPmTicketId);
                        sessionManager.updateSessionUserTicketName(acPmTicketNo);
                        startActivityForResult(intent, RESULT_AC_PM_SUBMIT);
                    }
                }).show();*/
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.refresh_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuRefresh:
                prepareListData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_AC_PM_SUBMIT && resultCode == RESULT_OK) {
            prepareListData();
        }
    }

    //////////////////////////////////////////////

    private void assignViews() {
        /*mLinearLayoutStatus = (LinearLayout) findViewById(R.id.linearLayout_Status);
        mWheelprogress = (ProgressWheel) findViewById(R.id.wheelprogress);
        mLinearLayoutContainer1 = (LinearLayout) findViewById(R.id.linearLayout_container1);
        mAcPreventiveMaintenanceSectionTextViewName = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_name);
        mAcPreventiveMaintenanceSectionTextViewNo = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_no);
        mPriventiveMaintenanceSiteLinearLayoutTicket1 = (LinearLayout) findViewById(R.id.pmSiteDashboard_linearLayout_ticket1);
        mPriventiveMaintenanceSiteLinearLayoutTicket2 = (LinearLayout) findViewById(R.id.pmSiteDashboard_linearLayout_ticket2);
        mLinearLayoutContainer2 = (LinearLayout) findViewById(R.id.linearLayout_container2);
        //mAcPreventiveMaintenanceSectionTextViewName2 = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_name2);
        mAcPreventiveMaintenanceSectionTextViewNo2 = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_no2);
        mPmSiteDashboardLinearLayoutTicket1 = (LinearLayout) findViewById(R.id.pmSiteDashboard_linearLayout_ticket1);
        mTextViewHotoName = (TextView) findViewById(R.id.textView_HotoName);
        mTextViewSiteID = (TextView) findViewById(R.id.textView_SiteID);
        mTextViewSiteName = (TextView) findViewById(R.id.textView_SiteName);
        mTextViewSiteSSA = (TextView) findViewById(R.id.textView_SiteSSA);
        mTextViewSiteAddress = (TextView) findViewById(R.id.textView_SiteAddress);
        mPmSiteDashboardLinearLayoutTicket2 = (LinearLayout) findViewById(R.id.pmSiteDashboard_linearLayout_ticket2);
        mTextViewHotoName1 = (TextView) findViewById(R.id.textView_HotoName1);
        mTextViewSiteID1 = (TextView) findViewById(R.id.textView_SiteID1);
        mTextViewSiteName1 = (TextView) findViewById(R.id.textView_SiteName1);
        mTextViewSiteSSA1 = (TextView) findViewById(R.id.textView_SiteSSA1);
        mTextViewSiteAddress1 = (TextView) findViewById(R.id.textView_SiteAddress1);
        pmSiteList_listView_siteacList = (ExpandableListView) findViewById(R.id.acPmSiteList_listView_siteAcList);

        txtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
        txtNoTicketFound.setVisibility(View.GONE);*/
    }

    private void setListner() {

        /*mPriventiveMaintenanceSiteLinearLayoutTicket1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogManager.Dialog("Conformation", "Do you want to proceed doing AC PM?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        openTicket1();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }).show();

            }
        });

        mPriventiveMaintenanceSiteLinearLayoutTicket2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialogManager.Dialog("Conformation", "Do you want to proceed doing AC PM?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        openTicket2();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }).show();

            }
        });*/
    }

    private void openTicket2() {
        if (Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this)) {
            if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                Intent intent = new Intent(AcPreventiveMaintenanceDashboardActivity.this, AcPreventiveMaintenanceSectionsListActivity.class);
                /*intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this));
                intent.putExtra("ticketNO", "SIKL19019999");
                intent.putExtra("status", "Submitted");
                sessionManager.updateSessionUserTicketId("SIKL19019999");
                sessionManager.updateSessionUserTicketName("SIKL19019999");*/
                //008 start

                intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this));

                intent.putExtra("TicketId", "02");
                intent.putExtra("TicketNO", "PMAC00165");
                intent.putExtra("TicketDate", "16/Apr/2019");

                intent.putExtra("acPmPlanDate", "16/Apr/2019");
                intent.putExtra("submittedDate", "18/Apr/2019");
                intent.putExtra("acPmSheduledDate", "15/Apr/2019");

                intent.putExtra("customerName", "Mahindra");
                intent.putExtra("circleName", "BSNL");
                intent.putExtra("stateName", "PNB State");
                intent.putExtra("ssaName", "SSA 1");
                intent.putExtra("siteId", "Site 001");
                intent.putExtra("siteName", "Sangola");
                intent.putExtra("siteType", "Indoor");
                //intent.putExtra("siteAddress", siteAddress);

                intent.putExtra("numberOfAc", "3");
                intent.putExtra("modeOfOpration", "In-house");// Out-source
                intent.putExtra("vendorName", "252");
                intent.putExtra("acTechnicianName", "Ankil Parimal");
                intent.putExtra("acTechnicianMobileNo", "9876543210");
                intent.putExtra("accessType", "A");
                intent.putExtra("ticketAccess", "1");
                intent.putExtra("acPmTickStatus", "WIP");//

                intent.putExtra("latitude", String.valueOf(gpsTracker.getLatitude()));
                intent.putExtra("longitude", String.valueOf(gpsTracker.getLongitude()));

                sessionManager.updateSessionUserTicketId("02");
                sessionManager.updateSessionUserTicketName("PMAC00165");
                //008 end

                startActivityForResult(intent, RESULT_AC_PM_SUBMIT);
            } else {
                //showToast("Could not detecting location. Please try again later.");
                alertDialogManager.Dialog("Conformation", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        if (gpsTracker.canGetLocation()) {
                            //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                            Log.e(MyPreventiveListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                        }
                    }
                }).show();
            }
        } else {
            alertDialogManager.Dialog("Conformation", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                @Override
                public void onPositiveClick() {
                    finish();
                }
            }).show();
        }
    }

    private void openTicket1() {
        if (Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this)) {
            if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                Intent intent = new Intent(AcPreventiveMaintenanceDashboardActivity.this, AcPreventiveMaintenanceSectionsListActivity.class);
                /*intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this));
                intent.putExtra("ticketNO", "SIKL19012222");
                intent.putExtra("status", "Submitted by Technician");
                sessionManager.updateSessionUserTicketId("SIKL19012222");
                sessionManager.updateSessionUserTicketName("SIKL19012222");*/

                //008 start
                intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(AcPreventiveMaintenanceDashboardActivity.this));

                intent.putExtra("TicketId", "02");
                intent.putExtra("TicketNO", "PMAC00165");
                intent.putExtra("TicketDate", "16/Apr/2019");

                intent.putExtra("acPmPlanDate", "16/Apr/2019");
                intent.putExtra("submittedDate", "18/Apr/2019");
                intent.putExtra("acPmSheduledDate", "15/Apr/2019");

                intent.putExtra("customerName", "Mahindra");
                intent.putExtra("circleName", "BSNL");
                intent.putExtra("stateName", "PNB State");
                intent.putExtra("ssaName", "SSA 1");
                intent.putExtra("siteId", "Site 001");
                intent.putExtra("siteName", "Sangola");
                intent.putExtra("siteType", "Indoor");
                //intent.putExtra("siteAddress", siteAddress);

                intent.putExtra("numberOfAc", "3");
                intent.putExtra("modeOfOpration", "In-house");
                intent.putExtra("vendorName", "");
                intent.putExtra("acTechnicianName", "Ankil Parimal");
                intent.putExtra("acTechnicianMobileNo", "9876543210");
                intent.putExtra("accessType", "S");
                intent.putExtra("ticketAccess", "1");
                intent.putExtra("acPmTickStatus", "Open");

                intent.putExtra("latitude", String.valueOf(gpsTracker.getLatitude()));
                intent.putExtra("longitude", String.valueOf(gpsTracker.getLongitude()));

                sessionManager.updateSessionUserTicketId("02");
                sessionManager.updateSessionUserTicketName("PMAC00165");
                //008 end

                startActivityForResult(intent, RESULT_AC_PM_SUBMIT);
            } else {
                alertDialogManager.Dialog("Conformation", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        if (gpsTracker.canGetLocation()) {
                            Log.e(MyPreventiveListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                        }
                    }
                }).show();
            }
        } else {
            alertDialogManager.Dialog("Conformation", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                @Override
                public void onPositiveClick() {
                    finish();
                }
            }).show();
        }
    }


}
