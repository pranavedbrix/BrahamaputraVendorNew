package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.Data.OtherElectricalCheckPoints;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
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

public class PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity extends BaseActivity {
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatus;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLamp;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelter;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHead;
    private TextView mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal;

    String str_dcEnergyMeterStatusVal;
    String str_aviationLampVal;
    String str_lightsInsideTheShelterVal;
    String str_lightsInSitePremisesOrBulkHeadVal;

    private OfflineStorageWrapper offlineStorageWrapper;

    private PreventiveMaintanceSiteTransactionDetails preventiveMaintanceSiteTransactionDetails;
    private OtherElectricalCheckPoints otherElectricalCheckPoints;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_other_electrical_check_points);
        this.setTitle("Other Electrical Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();

        sessionManager = new SessionManager(PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.this, userId, ticketName);

        preventiveMaintanceSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        setInputDetails();

    }

    private void assignViews() {
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_dcEnergyMeterStatus);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_dcEnergyMeterStatusVal);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLamp = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_aviationLamp);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_aviationLampVal);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelter = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_lightsInsideTheShelter);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_lightsInsideTheShelterVal);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHead = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_lightsInSitePremisesOrBulkHead);
        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteOtherElectricalCheckPoints_textView_lightsInSitePremisesOrBulkHeadVal);
    }

    private void initCombo() {

        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteOtherElectricalCheckPoints_dcEnergyMeterStatus))),
                        "DC Energy Meter status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dcEnergyMeterStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal.setText(str_dcEnergyMeterStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteOtherElectricalCheckPoints_aviationLamp))),
                        "Aviation Lamp",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_aviationLampVal = item.get(position);
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal.setText(str_aviationLampVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteOtherElectricalCheckPoints_lightsInsideTheShelter))),
                        "Lights Inside the Shelter",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_lightsInsideTheShelterVal = item.get(position);
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal.setText(str_lightsInsideTheShelterVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteOtherElectricalCheckPoints_lightsInSitePremisesOrBulkHead))),
                        "Lights in Site Premises/Bulk head",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_lightsInSitePremisesOrBulkHeadVal = item.get(position);
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal.setText(str_lightsInSitePremisesOrBulkHeadVal);
                    }
                });
            }
        });
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
                if (checkValidationOfArrayFields() == true) {
                    submitDetails();
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                Gson gson = new Gson();
                preventiveMaintanceSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (preventiveMaintanceSiteTransactionDetails != null) {
                    otherElectricalCheckPoints = preventiveMaintanceSiteTransactionDetails.getOtherElectricalCheckPoints();
                    if (otherElectricalCheckPoints != null) {
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal.setText(otherElectricalCheckPoints.getDcEnergyMeterstatus());
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal.setText(otherElectricalCheckPoints.getAviationLamp());
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal.setText(otherElectricalCheckPoints.getLightsInsideTheShelter());
                        mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal.setText(otherElectricalCheckPoints.getLightsInSitePremisesBulkhead());
                    }
                }

            } else {
                showToast("No previous saved data available");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            String dcEnergyMeterStatus = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal.getText().toString().trim();
            String aviationLamp = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal.getText().toString().trim();
            String lightInsideTheShelter = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal.getText().toString().trim();
            String lightsInSitePremisesBulkHead = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal.getText().toString().trim();

            otherElectricalCheckPoints = new OtherElectricalCheckPoints(dcEnergyMeterStatus, aviationLamp, lightInsideTheShelter, lightsInSitePremisesBulkHead);
            preventiveMaintanceSiteTransactionDetails.setOtherElectricalCheckPoints(otherElectricalCheckPoints);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(preventiveMaintanceSiteTransactionDetails);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public boolean checkValidationOfArrayFields() {
        String dcEnergyMeterStatus = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewDcEnergyMeterStatusVal.getText().toString().trim();
        String aviationLamp = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewAviationLampVal.getText().toString().trim();
        String lightsInsideTheShelter = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInsideTheShelterVal.getText().toString().trim();
        String lightsInSitePremisesBulkHead = mPreventiveMaintenanceSiteOtherElectricalCheckPointsTextViewLightsInSitePremisesOrBulkHeadVal.getText().toString().trim();

        if (dcEnergyMeterStatus.isEmpty() || dcEnergyMeterStatus == null) {
            showToast("Select DC Energy Meter Status");
            return false;
        } else if (aviationLamp.isEmpty() || aviationLamp == null) {
            showToast("Select Aviation Lamp");
            return false;
        } else if (lightsInsideTheShelter.isEmpty() || lightsInsideTheShelter == null) {
            showToast("Select Lights Inside The Shelter");
            return false;
        } else if (lightsInSitePremisesBulkHead.isEmpty() || lightsInSitePremisesBulkHead == null) {
            showToast("Select Lights In Site Premises Bulk Head");
            return false;
        } else return true;
    }
}
