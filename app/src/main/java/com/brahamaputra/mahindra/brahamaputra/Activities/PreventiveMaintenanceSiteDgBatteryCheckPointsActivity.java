package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.DgBatteryCheckPointsData;
import com.brahamaputra.mahindra.brahamaputra.Data.DgBatteryCheckPointsParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmNoOfAcAvailableAtSite;

public class PreventiveMaintenanceSiteDgBatteryCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal;

    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewBatteryNumber;

    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDetailsOfDgBatteryQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView;
    private ImageView mButtonClearDetailsOfDgBatteryQRCodeScanView;

    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryCondition;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailable;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminal;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryCharger;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal;

    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal;

    private Button mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading;
    private LinearLayout mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutTypeOfFault;

    private LinearLayout mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView;

    String str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal = "";
    String str_pmSiteDgbcpDGBatteryConditionVal = "";
    String str_pmSiteDgbcpDGBatteryWaterAvailableVal = "";
    String str_pmSiteDgbcpPetroleumJellyToDGBatteryTerminalVal = "";
    String str_pmSiteDgbcpDGBatteryChargerVal = "";
    String str_pmSiteDgbcpRegisterFaultVal = "";
    String str_pmSiteDgbcpTypeOfFaultVal = "";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private String base64StringDetailsOfDgBatteryQRCodeScan = "";
    //private String imageFileDetailsOfDgBatteryQRCodeScan;
    //private Uri imageFileUriDetailsOfDgBatteryQRCodeScan = null;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private ArrayList<DgBatteryCheckPointsData> dgBatteryCheckPointsData;// replce airConditionersData

    private DgBatteryCheckPointsParentData dataList;

    private int totalAcCount = 0;
    private int currentPos = 0;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    int isQrCodeNew = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_dg_battery_check_points);
        this.setTitle("DG Battery Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this);
        sessionManager = new SessionManager(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this);

        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, userId, ticketName);
        setListner();

        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();

        dgBatteryCheckPointsData = new ArrayList<>();
        currentPos = 0;

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }

        setInputDetails(currentPos);
        invalidateOptionsMenu();
        setMultiSelectModel();


    }

    private void initCombo() {
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_noOfDgBatteryAvailableAtSite))),
                        "No of DG Battery available at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal = item.get(position);
                        invalidateOptionsMenu();
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.setText(str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal);
                        //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText("");//008

                        if (dgBatteryCheckPointsData != null && dgBatteryCheckPointsData.size() > 0) {
                            dgBatteryCheckPointsData.clear();
                        }
                        currentPos = 0;
                        totalAcCount = 0;
                        clearFields(currentPos);

                        // Clear all field value and hide layout If Non AC or O //
                        if (str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                        } else {
                            str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal = item.get(position);
                            totalAcCount = Integer.parseInt(str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal);
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewBatteryNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalAcCount > 0 && totalAcCount == 1) {
                                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Finish");
                            } else {
                                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Next Reading");
                            }
                        }
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_dgBatteryCondition))),
                        "DG Battery Condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpDGBatteryConditionVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.setText(str_pmSiteDgbcpDGBatteryConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_dgBatteryWaterAvailable))),
                        "DG Battery Water Available",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpDGBatteryWaterAvailableVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.setText(str_pmSiteDgbcpDGBatteryWaterAvailableVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_petroleumJellyToDgBatteryTerminal))),
                        "Petroleum Jelly to DG Battery Terminal",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpPetroleumJellyToDGBatteryTerminalVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.setText(str_pmSiteDgbcpPetroleumJellyToDGBatteryTerminalVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_dgBatteryCharger))),
                        "DG Battery Charger",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpDGBatteryChargerVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.setText(str_pmSiteDgbcpDGBatteryChargerVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteDgbcpRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteDgbcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

                /*SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgBatteryCheckPoints_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgbcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteDgbcpTypeOfFaultVal);
                    }
                });*/
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetMultiSelectModel(); by 008
                /* if (checkValidationOfArrayFields() == true) {*/
                if (currentPos > 0) {
                    //Save current ac reading
                    saveDGCheckRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayDGCheckRecords(currentPos);
                    //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim());
                }
                /* }*/
            }
        });
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetMultiSelectModel(); by 008
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalAcCount - 1)) {
                        //Save current ac reading
                        saveDGCheckRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayDGCheckRecords(currentPos);
                        //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim());

                    } else if (currentPos == (totalAcCount - 1)) {
                        saveDGCheckRecords(currentPos);
                        //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim());
                        if (checkDuplicationQrCodeNew() == false) {
                            if (checkValidationOnChangeNoOfDgBatteryAvailable(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                                submitDetails();
                                if (sitePmNoOfAcAvailableAtSite.equals("0")) {
                                    startActivity(new Intent(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, PreventiveMaintenanceSiteSmpsCheckPointsActivity.class));
                                } else {
                                    startActivity(new Intent(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, PreventiveMaintenanceSiteAcCheckPointsActivity.class));
                                }
                                //startActivity(new Intent(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, PreventiveMaintenanceSiteAcCheckPointsActivity.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

    }

    private void assignViews() {

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_noOfDgBatteryAvailableAtSite);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_noOfDgBatteryAvailableAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewBatteryNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_batteryNumber);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDetailsOfDgBatteryQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_detailsOfDgBatteryQRCodeScan);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_button_detailsOfDgBatteryQRCodeScan);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_button_detailsOfDgBatteryQRCodeScanView);
        mButtonClearDetailsOfDgBatteryQRCodeScanView = (ImageView) findViewById(R.id.button_ClearDetailsOfDgBatteryQRCodeScanView);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryCondition = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_dgBatteryCondition);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_dgBatteryConditionVal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailable = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_dgBatteryWaterAvailable);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_dgBatteryWaterAvailableVal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_petroleumJellyToDGBatteryTerminal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_petroleumJellyToDGBatteryTerminalVal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryCharger = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_dgBatteryCharger);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_dgBatteryChargerVal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_button_nextReading);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_linearLayout_typeOfFault);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgBatteryCheckPoints_button_uploadPhotoOfRegisterFaultView);

    }


    /////////////
    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                dataList = pmSiteTransactionDetails.getDgBatteryCheckPointsParentData();
                dgBatteryCheckPointsData.addAll(dataList.getDgBatteryCheckPointsData());


                mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.setText(dataList.getNoOfDgBatteryavailableAtSite());
                //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal .setText(dataList.getNumberOfEarthPitVisible());

                mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());
                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();

                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }


                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                    setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                }

                str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal = dataList.getNoOfDgBatteryavailableAtSite();
                invalidateOptionsMenu();

                /*mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                if (!dataList.getNumberOfEarthPitVisible().isEmpty() && dataList.getNumberOfEarthPitVisible() != null) {
                    mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                }
                mAirConditionersTextViewNumberOfACInWorkingConditionVal.setText(dataList.getNumberOfEarthPitVisible());*/


                if (dgBatteryCheckPointsData != null && dgBatteryCheckPointsData.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewBatteryNumber.setText("Reading: #1");
                    totalAcCount = Integer.parseInt(dataList.getNoOfDgBatteryavailableAtSite());

                    base64StringDetailsOfDgBatteryQRCodeScan = dgBatteryCheckPointsData.get(index).getDetailsOfDgBatteryQrCodeScan();
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);

                    if (!base64StringDetailsOfDgBatteryQRCodeScan.isEmpty() && base64StringDetailsOfDgBatteryQRCodeScan != null) {
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.setText(dgBatteryCheckPointsData.get(index).getDgBatteryCondition());
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.setText(dgBatteryCheckPointsData.get(index).getDgBatteryWaterAvailable());
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.setText(dgBatteryCheckPointsData.get(index).getPetroleumJellyToDgBatteryTerminal());
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.setText(dgBatteryCheckPointsData.get(index).getDgBatteryCharger());

                    mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                    //if (airConditionersData.size() > 1) {
                    if (totalAcCount > 1) {
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Finish");
                    }
                }

            } else {
                showToast("No previous saved data available");
                mLinearLayoutContainer.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void setMultiSelectModel() {
        //MultiSelectModel
        multiSelectDialog = new MultiSelectDialog()
                .title("Type of Fault") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .preSelectIDsList(alreadySelectedTypeOfFaultList)
                .setMinSelectionLimit(0)
                .setMaxSelectionLimit(typeOfFaultList.size())
                //List of ids that you need to be selected
                .multiSelectList(listOfFaultsTypes) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        str_pmSiteDgbcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteDgbcpTypeOfFaultVal);

                        /*str_pmSiteDgbcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteDgbcpTypeOfFaultVal);*/
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");
                    }
                });
    }

    private void resetMultiSelectModel() {
        alreadySelectedTypeOfFaultList = new ArrayList<>();
        setMultiSelectModel();
    }

    private void setArrayValuesOfTypeOfFault(String TypeOfFault) {

        if (!TypeOfFault.isEmpty() && TypeOfFault != null) {
            List<String> items = Arrays.asList(TypeOfFault.split("\\s*,\\s*"));
            for (String ss : items) {
                for (MultiSelectModel c : listOfFaultsTypes) {
                    if (ss.equals(c.getName())) {
                        alreadySelectedTypeOfFaultList.add(c.getId());
                        break;
                    }
                }
            }
        }
    }

    public boolean checkValidationOfArrayFields() {

        String detailsOfDgBatteryQrCodeScan = base64StringDetailsOfDgBatteryQRCodeScan;
        String dgBatteryCondition = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.getText().toString().trim();
        String dgBatteryWaterAvailable = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.getText().toString().trim();
        String petroleumJellyToDgBatteryTerminal = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.getText().toString().trim();
        String dgBatteryCharger = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.getText().toString().trim();


        if (detailsOfDgBatteryQrCodeScan.isEmpty() || detailsOfDgBatteryQrCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (dgBatteryCondition.isEmpty() || dgBatteryCondition == null) {
            showToast("Select DG Battery Condition");
            return false;
        } else if (dgBatteryWaterAvailable.isEmpty() || dgBatteryWaterAvailable == null) {
            showToast("Select DG Battery Water Available");
            return false;
        } else if (petroleumJellyToDgBatteryTerminal.isEmpty() || petroleumJellyToDgBatteryTerminal == null) {
            showToast("Select Petroleum Jelly To DG Battery Terminal");
            return false;
        } else if (dgBatteryCharger.isEmpty() || dgBatteryCharger == null) {
            showToast("Select DG Battery Charger");
            return false;
        } else return true;

    }

    private void saveDGCheckRecords(int pos) {

        String detailsOfDgBatteryQrCodeScan = base64StringDetailsOfDgBatteryQRCodeScan;
        String dgBatteryCondition = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.getText().toString().trim();
        String dgBatteryWaterAvailable = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.getText().toString().trim();
        String petroleumJellyToDgBatteryTerminal = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.getText().toString().trim();
        String dgBatteryCharger = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.getText().toString().trim();
        /*String registerFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();*/

        //detailsOfDgBatteryQrCodeScan;
        //                    String dgBatteryCondition;
        //                    String dgBatteryWaterAvailable;
        //                    String petroleumJellyToDgBatteryTerminal;
        //                    String dgBatteryCharger;
        //                    String registerFault;
        //                    String typeOfFault


        DgBatteryCheckPointsData dgBatteryCheckPointsDataChild = new DgBatteryCheckPointsData(detailsOfDgBatteryQrCodeScan,
                dgBatteryCondition,
                dgBatteryWaterAvailable,
                petroleumJellyToDgBatteryTerminal,
                dgBatteryCharger,isQrCodeNew);

        if (dgBatteryCheckPointsData.size() > 0) {
            if (pos == dgBatteryCheckPointsData.size()) {
                dgBatteryCheckPointsData.add(dgBatteryCheckPointsDataChild);
            } else if (pos < dgBatteryCheckPointsData.size()) {
                dgBatteryCheckPointsData.set(pos, dgBatteryCheckPointsDataChild);
            }
        } else {
            dgBatteryCheckPointsData.add(dgBatteryCheckPointsDataChild);
        }
    }

    private void displayDGCheckRecords(int pos) {

        if (dgBatteryCheckPointsData.size() > 0 && pos < dgBatteryCheckPointsData.size()) {

            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewBatteryNumber.setText("Reading: #" + (pos + 1));

            base64StringDetailsOfDgBatteryQRCodeScan = dgBatteryCheckPointsData.get(pos).getDetailsOfDgBatteryQrCodeScan();
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
            mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);

            if (!base64StringDetailsOfDgBatteryQRCodeScan.isEmpty() && base64StringDetailsOfDgBatteryQRCodeScan != null) {
                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.VISIBLE);
            }

            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.setText(dgBatteryCheckPointsData.get(pos).getDgBatteryCondition());
            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.setText(dgBatteryCheckPointsData.get(pos).getDgBatteryWaterAvailable());
            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.setText(dgBatteryCheckPointsData.get(pos).getPetroleumJellyToDgBatteryTerminal());
            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.setText(dgBatteryCheckPointsData.get(pos).getDgBatteryCharger());

            /*mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.setText(dgBatteryCheckPointsData.get(pos).getRegisterFault());
            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText(dgBatteryCheckPointsData.get(pos).getTypeOfFault());

            if (dgBatteryCheckPointsData.get(pos).getTypeOfFault() != null && dgBatteryCheckPointsData.get(pos).getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                alreadySelectedTypeOfFaultList = new ArrayList<>();
                setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                setMultiSelectModel();
            }*/

            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalAcCount - 1)) {
                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonNextReading.setText("Next Reading");
            }
        }

    }

    private void submitDetails() {
        try {
            String noOfDgBatteryAvailableAtSite = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.getText().toString().trim();
            //String numberOfEarthPitVisible = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;

            dataList = new DgBatteryCheckPointsParentData(noOfDgBatteryAvailableAtSite, registerFault,
                    typeOfFault, base64StringUploadPhotoOfRegisterFault, dgBatteryCheckPointsData);

            pmSiteTransactionDetails.setDgBatteryCheckPointsParentData(dataList);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewBatteryNumber.setText("Reading: #" + (indexPos + 1));

        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal.setText("");
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal.setText("");
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal.setText("");
        mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal.setText("");
        //mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.setText("");
        //mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText("");

        str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal = "";
        str_pmSiteDgbcpDGBatteryConditionVal = "";
        str_pmSiteDgbcpDGBatteryWaterAvailableVal = "";
        str_pmSiteDgbcpPetroleumJellyToDGBatteryTerminalVal = "";
        str_pmSiteDgbcpDGBatteryChargerVal = "";
        //str_pmSiteDgbcpRegisterFaultVal = "";

        base64StringDetailsOfDgBatteryQRCodeScan = "";

        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
        mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);

    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < dgBatteryCheckPointsData.size(); i++) {
            for (int j = i + 1; j < dgBatteryCheckPointsData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (dgBatteryCheckPointsData.get(i).getDetailsOfDgBatteryQrCodeScan().toString().equals(dgBatteryCheckPointsData.get(j).getDetailsOfDgBatteryQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkValidationOnChangeNoOfDgBatteryAvailable(String NoOfDgBatteryAvailable, String methodFlag) {

        /*String detailsOfDgBatteryQrCodeScan;
                    String dgBatteryCondition;
                    String dgBatteryWaterAvailable;
                    String petroleumJellyToDgBatteryTerminal;
                    String dgBatteryCharger;
                    String registerFault;
                    String typeOfFault;*/

                   /* mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryConditionVal
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryWaterAvailableVal
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewPetroleumJellyToDGBatteryTerminalVal
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewDgBatteryChargerVal
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal*/
        String registerFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();


        if (NoOfDgBatteryAvailable.isEmpty() || NoOfDgBatteryAvailable == null) {
            showToast("Select No of DG Battery Available at site");
            return false;
        } else if (registerFault.isEmpty() || registerFault == null) {
            showToast("Select Register Fault");
            return false;
        } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
            showToast("Select Type of Fault");
            return false;
        } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
            showToast("Upload Photo Of Register Fault");
            return false;
        } else if ((dgBatteryCheckPointsData.size() != Integer.valueOf(NoOfDgBatteryAvailable) && methodFlag.equals("onSubmit"))) {
            showToast("Complete the all readings.");//as a mentioned AC in no of AC provided
            return false;
        } else return true;


        /*String noOfEarthPitValue, String noOfEarthPitValueVisible,
        if (noOfEarthPitValue.isEmpty() || noOfEarthPitValue == null) {
            showToast("Select No of Earth Pit");
            return false;
        } else if (Integer.valueOf(noOfEarthPitValueVisible) > 0) {
            if (noOfEarthPitValueVisible.isEmpty() || noOfEarthPitValueVisible == null) {
                showToast("Select Number of Earth Pit Visible");
                return false;
            } else if (Integer.valueOf(noOfEarthPitValueVisible) > Integer.valueOf(noOfEarthPitValue)) {
                showToast("Select Earth Pit Visible is less than or equal to Earth Pit");
                return false;
            } else if ((dgBatteryCheckPointsData.size() != Integer.valueOf(noOfEarthPitValue) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");//as a mentioned AC in no of AC provided
                return false;
            } else return true;
        } else return true;*/

    }

    //////////////////
    private void visibilityOfTypesOfFault(String RegisterFault) {

        mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgBatteryCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }


    }

    private void setListner() {

        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DetailsOfDgBatteryQRCodeScan();
                }
            }
        });

        mButtonClearDetailsOfDgBatteryQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDetailsOfDgBatteryQRCodeScan = "";
                mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void DetailsOfDgBatteryQRCodeScan() {
        try {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("Scan QRcode");
            integrator.setOrientationLocked(true);
            integrator.setRequestCode(MY_PERMISSIONS_REQUEST_CAMERA);
            integrator.initiateScan();

            //        Use this for more customization
            //        IntentIntegrator integrator = new IntentIntegrator(this);
            //        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            //        integrator.setPrompt("Scan a barcode");
            //        integrator.setCameraId(0);  // Use a specific camera of the device
            //        integrator.setBeepEnabled(false);
            //        integrator.setBarcodeImageEnabled(true);
            //        integrator.initiateScan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_DGBCReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringDetailsOfDgBatteryQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringDetailsOfDgBatteryQRCodeScan = result.getContents();
                            if (!base64StringDetailsOfDgBatteryQRCodeScan.isEmpty() && base64StringDetailsOfDgBatteryQRCodeScan != null) {
                                mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonDetailsOfDgBatteryQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearDetailsOfDgBatteryQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringDetailsOfDgBatteryQRCodeScan = "";
                            showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                        }
                    }
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriUploadPhotoOfRegisterFault != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriUploadPhotoOfRegisterFault);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringUploadPhotoOfRegisterFault = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteDgBatteryCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal != null && !str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal) > 0) {
                shareItem.setVisible(false);
            }
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

                str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.getText().toString();
                String registerFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
                String typeOfFault = mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

                if (str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal == null || str_pmSiteDgbcpNoOfDGBatteryAvailableAtSiteVal.equals("")) {
                    showToast("Select No of DG Battery Available at site");
                } else if (registerFault.isEmpty() || registerFault == null) {
                    showToast("Select Register Fault");
                } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
                    showToast("Select Type of Fault");
                } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
                    showToast("Upload Photo Of Register Fault");
                } else {
                    if (checkValidationOnChangeNoOfDgBatteryAvailable(mPreventiveMaintenanceSiteDgBatteryCheckPointsTextViewNoOfDgBatteryAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                        submitDetails();
                        if (sitePmNoOfAcAvailableAtSite.equals("0")) {
                            startActivity(new Intent(this, PreventiveMaintenanceSiteSmpsCheckPointsActivity.class));
                        } else {
                            startActivity(new Intent(this, PreventiveMaintenanceSiteAcCheckPointsActivity.class));
                        }
                        finish();
                    }
                }

                /*startActivity(new Intent(this, PreventiveMaintenanceSiteAcCheckPointsActivity.class));
                finish();*/
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
