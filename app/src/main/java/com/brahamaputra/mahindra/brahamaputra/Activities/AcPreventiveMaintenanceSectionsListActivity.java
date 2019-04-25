package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.brahamaputra.mahindra.brahamaputra.Adapters.AcPreventiveMaintenanceSectionListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintenanceAcSection;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AcPreventiveMaintenanceSectionsListActivity extends BaseActivity {

    public ListView acPreventiveMaintenanceSections_listView_sections;
    ArrayList<PreventiveMaintenanceAcSection> dataModels;
    String[] values;
    private static AcPreventiveMaintenanceSectionListAdapter adapter;
    public static final int RESULT_READING_COMPLETED = 650;
    private SessionManager sessionManager;
    private String designation;
    String flag;
    String returnValue;

    //008 start

    private String customerName;
    private String circleName;
    private String stateName;
    private String ssaName;
    private String siteDBId;
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

    public GPSTracker gpsTracker;
    private AlertDialogManager alertDialogManager;
    private String checkOutLat = "0.0";
    private String checkOutLong = "0.0";
    //008 end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_preventive_maintenance_sections_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("AC PM Sections List");

        alertDialogManager = new AlertDialogManager(AcPreventiveMaintenanceSectionsListActivity.this);

        sessionManager = new SessionManager(AcPreventiveMaintenanceSectionsListActivity.this);
        acPreventiveMaintenanceSections_listView_sections = (ListView) findViewById(R.id.acPreventiveMaintenanceSections_listView_sections);

        Intent intent = getIntent();
        //flag = intent.getStringExtra("status");

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
        siteDBId = intent.getStringExtra("siteDBId");
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

        designation = sessionManager.getSessionDesignation();
        gpsTracker = new GPSTracker(AcPreventiveMaintenanceSectionsListActivity.this);

        int flag = 0;
        if (accessType.equals("S") && ticketAccess.equals("1") && (acPmTickStatus.equals("Open") || acPmTickStatus.equals("Reassigned"))) {
            flag = 1;
            values = new String[]{"Ticket Submission from Field Engineer"};
        } else if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            flag = 1;
            values = new String[]{"AC PM Process"};
        } else if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            flag = 1;
            values = new String[]{"AC PM Process", "Ticket Submission from Field Engineer"};
        } else {
            flag = 0;
            showToast("Unauthorised ticket status.");
            //finish();
            intent = new Intent(AcPreventiveMaintenanceSectionsListActivity.this, AcPreventiveMaintenanceDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, RESULT_OK);
        }/*else if (accessType.equals("") && ticketAccess.equals("") && acPmTickStatus.equals("")) {
            values = new String[]{"", ""};
        }*/


        /*values = getResources().getStringArray(R.array.listView_acPreventiveMaintenanceSections_sections);
        dataModels = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            if (flag.equals("Submitted by Technician")) {
                if (i / 2 == 0) {
                    dataModels.add(new PreventiveMaintenanceAcSection("" + (i + 1), "" + values[i], 0));
                } else {
                    dataModels.add(new PreventiveMaintenanceAcSection("" + (i + 1), "" + values[i], 0));
                }
            } else {
                if (values[i].equals("AC PM Process") && designation.equals("AC Technician (Mobile)")) {
                    dataModels.add(new PreventiveMaintenanceAcSection("" + (i + 1), "" + values[i], 0));
                } else if (values[i].equals("Ticket Submission from Field Engineer") && designation.equals("Site Head")) {// || designation.equals("Senior Field Support Engineer (Mobile)")
                    dataModels.add(new PreventiveMaintenanceAcSection("" + (i + 1), "" + values[i], 0));
                }
            }
        }*/
        if (flag == 1) {
            dataModels = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                dataModels.add(new PreventiveMaintenanceAcSection("" + (i + 1), "" + values[i], 0));
            }

            adapter = new AcPreventiveMaintenanceSectionListAdapter(dataModels, getApplicationContext());

            acPreventiveMaintenanceSections_listView_sections.setAdapter(adapter);
            acPreventiveMaintenanceSections_listView_sections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch (position) {
                        case 0:

                            if (dataModels.get(position).getSecName().equals("Ticket Submission from Field Engineer")) {
                                Intent acPMProcessIntent = new Intent(AcPreventiveMaintenanceSectionsListActivity.this, PreventiveMaintanceAcFieldEngineerActivity.class);

                                //008 start
                                acPMProcessIntent.putExtra("TicketId", TicketId);
                                acPMProcessIntent.putExtra("TicketNO", TicketNO);
                                acPMProcessIntent.putExtra("TicketDate", TicketDate);

                                acPMProcessIntent.putExtra("acPmPlanDate", acPmPlanDate);
                                acPMProcessIntent.putExtra("submittedDate", submittedDate);
                                acPMProcessIntent.putExtra("acPmSheduledDate", acPmSheduledDate);

                                acPMProcessIntent.putExtra("customerName", customerName);
                                acPMProcessIntent.putExtra("circleName", circleName);
                                acPMProcessIntent.putExtra("stateName", stateName);
                                acPMProcessIntent.putExtra("ssaName", ssaName);
                                acPMProcessIntent.putExtra("siteDBId", siteDBId);
                                acPMProcessIntent.putExtra("siteId", siteId);
                                acPMProcessIntent.putExtra("siteName", siteName);
                                acPMProcessIntent.putExtra("siteType", siteType);

                                acPMProcessIntent.putExtra("numberOfAc", numberOfAc);
                                acPMProcessIntent.putExtra("modeOfOpration", modeOfOpration);
                                acPMProcessIntent.putExtra("vendorName", vendorName);
                                acPMProcessIntent.putExtra("acTechnicianName", acTechnicianName);
                                acPMProcessIntent.putExtra("acTechnicianMobileNo", acTechnicianMobileNo);
                                acPMProcessIntent.putExtra("accessType", accessType);
                                acPMProcessIntent.putExtra("ticketAccess", ticketAccess);
                                acPMProcessIntent.putExtra("acPmTickStatus", acPmTickStatus);//WIP
                                //008 end

                                startActivityForResult(acPMProcessIntent, RESULT_READING_COMPLETED);
                                break;

                            } else if (dataModels.get(position).getSecName().equals("AC PM Process")) {

                                Intent acPMProcessIntent = new Intent(AcPreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceAcTechnicianActivity.class);

                                //008 start
                                acPMProcessIntent.putExtra("TicketId", TicketId);
                                acPMProcessIntent.putExtra("TicketNO", TicketNO);
                                acPMProcessIntent.putExtra("TicketDate", TicketDate);

                                acPMProcessIntent.putExtra("acPmPlanDate", acPmPlanDate);
                                acPMProcessIntent.putExtra("submittedDate", submittedDate);
                                acPMProcessIntent.putExtra("acPmSheduledDate", acPmSheduledDate);

                                acPMProcessIntent.putExtra("customerName", customerName);
                                acPMProcessIntent.putExtra("circleName", circleName);
                                acPMProcessIntent.putExtra("stateName", stateName);
                                acPMProcessIntent.putExtra("ssaName", ssaName);
                                acPMProcessIntent.putExtra("siteDBId", siteDBId);
                                acPMProcessIntent.putExtra("siteId", siteId);
                                acPMProcessIntent.putExtra("siteName", siteName);
                                acPMProcessIntent.putExtra("siteType", siteType);

                                acPMProcessIntent.putExtra("numberOfAc", numberOfAc);
                                acPMProcessIntent.putExtra("modeOfOpration", modeOfOpration);
                                acPMProcessIntent.putExtra("vendorName", vendorName);
                                acPMProcessIntent.putExtra("acTechnicianName", acTechnicianName);
                                acPMProcessIntent.putExtra("acTechnicianMobileNo", acTechnicianMobileNo);
                                acPMProcessIntent.putExtra("accessType", accessType);
                                acPMProcessIntent.putExtra("ticketAccess", ticketAccess);
                                acPMProcessIntent.putExtra("acPmTickStatus", acPmTickStatus);//WIP
                                //008 end

                                LocationManager lm = (LocationManager) AcPreventiveMaintenanceSectionsListActivity.this.getSystemService(Context.LOCATION_SERVICE);
                                boolean gps_enabled = false;
                                boolean network_enabled = false;

                                try {
                                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                                } catch (Exception ex) {
                                }

                                if (!gps_enabled && !network_enabled) {
                                    // notify user
                                    alertDialogManager = new AlertDialogManager(AcPreventiveMaintenanceSectionsListActivity.this);
                                    alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                        @Override
                                        public void onPositiveClick() {
                                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            AcPreventiveMaintenanceSectionsListActivity.this.startActivity(myIntent);
                                        }
                                    }).show();
                                } else {
                                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                                        checkOutLat = String.valueOf(gpsTracker.getLatitude());
                                        checkOutLong = String.valueOf(gpsTracker.getLongitude());
                                        startActivityForResult(acPMProcessIntent, RESULT_READING_COMPLETED);

                                    } else {
                                        //showToast("Could not detecting location.");
                                        alertDialogManager = new AlertDialogManager(AcPreventiveMaintenanceSectionsListActivity.this);
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
                                break;
                            }

                        case 1:
                            if (dataModels.get(position).getSecName().equals("Ticket Submission from Field Engineer")) {
                                Intent acPMProcessIntent = new Intent(AcPreventiveMaintenanceSectionsListActivity.this, PreventiveMaintanceAcFieldEngineerActivity.class);
                                //acPMProcessIntent.putExtra("status", "");

                                //008 start
                                acPMProcessIntent.putExtra("TicketId", TicketId);
                                acPMProcessIntent.putExtra("TicketNO", TicketNO);
                                acPMProcessIntent.putExtra("TicketDate", TicketDate);

                                acPMProcessIntent.putExtra("acPmPlanDate", acPmPlanDate);
                                acPMProcessIntent.putExtra("submittedDate", submittedDate);
                                acPMProcessIntent.putExtra("acPmSheduledDate", acPmSheduledDate);

                                acPMProcessIntent.putExtra("customerName", customerName);
                                acPMProcessIntent.putExtra("circleName", circleName);
                                acPMProcessIntent.putExtra("stateName", stateName);
                                acPMProcessIntent.putExtra("ssaName", ssaName);
                                acPMProcessIntent.putExtra("siteDBId", siteDBId);
                                acPMProcessIntent.putExtra("siteId", siteId);
                                acPMProcessIntent.putExtra("siteName", siteName);
                                acPMProcessIntent.putExtra("siteType", siteType);

                                acPMProcessIntent.putExtra("numberOfAc", numberOfAc);
                                acPMProcessIntent.putExtra("modeOfOpration", modeOfOpration);
                                acPMProcessIntent.putExtra("vendorName", vendorName);
                                acPMProcessIntent.putExtra("acTechnicianName", acTechnicianName);
                                acPMProcessIntent.putExtra("acTechnicianMobileNo", acTechnicianMobileNo);
                                acPMProcessIntent.putExtra("accessType", accessType);
                                acPMProcessIntent.putExtra("ticketAccess", ticketAccess);
                                acPMProcessIntent.putExtra("acPmTickStatus", acPmTickStatus);//WIP
                                //008 end

                                startActivityForResult(acPMProcessIntent, RESULT_READING_COMPLETED);
                                break;
                            }


                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_READING_COMPLETED && resultCode == RESULT_OK) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuSubmit:
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

    @Override
    protected void onResume() {
        //refreshList();
        super.onResume();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
