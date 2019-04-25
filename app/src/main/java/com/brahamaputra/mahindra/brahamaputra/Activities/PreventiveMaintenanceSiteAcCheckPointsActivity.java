package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.AcCheckPoint;
import com.brahamaputra.mahindra.brahamaputra.Data.AcCheckPointParentData;
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

public class PreventiveMaintenanceSiteAcCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteAcCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcNumber;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView;
    private ImageView mButtonClearQRCodeScanView;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingCondition;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcController;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatus;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatus;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewPhotoOfAcFiltersBeforeCleaning;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaning;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewPhotoOfAcFiltersAfterCleaning;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaning;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCooling;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotor;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterTemperature;
    private EditText mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewPhotoOfTemperature;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperature;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatus;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal;
    private Button mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading;

    private LinearLayout mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutTypeOfFault;

    private LinearLayout mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteAcCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView;

    String str_pmSiteAcpNoOfAcAvailableAtSiteVal = "";
    String str_pmSiteAcpWorkingConditionOfAcVal = "";
    String str_pmSiteAcpAutomationOfAcControllerVal = "";
    String str_pmSiteAcpAcEarthingStatusVal = "";
    String str_pmSiteAcpAcFilterStatusVal = "";
    String str_pmSiteAcpCleaningOfCoolingOrCondensorCoilsVal = "";
    String str_pmSiteAcpAnyAbnormalSoundFromMotorVal = "";
    String str_pmSiteAcpShelterDoorStatusVal = "";
    String str_pmSiteAcpRegisterFaultVal = "";
    String str_pmSiteAcpTypeOfFaultVal = "";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfAcFiltersBeforeCleaning = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfAcFiltersAfterCleaning = 102;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfTemperature = 103;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringAcCheckPointsQRCodeScan = "";
    //private String imageFileAcCheckPointsQRCodeScan;
    //private Uri imageFileUriAcCheckPointsQRCodeScan = null;

    private String base64StringTakePhotoOfAcFiltersBeforeCleaning = "";
    private String base64StringTakePhotoOfAcFiltersAfterCleaning = "";
    private String base64StringTakePhotoOfTemperature = "";

    private String imageFileAcFiltersBeforeCleaning;
    private String imageFileAcFiltersAfterCleaning;
    private String imageFileTemperature;

    private Uri imageFileUriAcFiltersBeforeCleaning = null;
    private Uri imageFileUriAcFiltersAfterCleaning = null;
    private Uri imageFileUriTemperature = null;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    /*private HotoTransactionData hotoTransactionData;
    private LandDetailsData landDetailsData;*/
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private AcCheckPointParentData dataList;
    private ArrayList<AcCheckPoint> acCheckPointsData;// replce airConditionersData
    private int totalCount = 0;
    private int currentPos = 0;

    private ArrayList<AcCheckPoint> arr_acCheckPoints;// replce airConditionersData
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelected;
    ArrayList<String> typeOfFaultList;

    int isQrCodeNew = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_ac_check_points);
        this.setTitle("AC Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        checkCameraPermission();
        setListner();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteAcCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteAcCheckPointsActivity.this, userId, ticketName);


        acCheckPointsData = new ArrayList<>();
        //dataList = new ArrayList<>();
        arr_acCheckPoints = new ArrayList<>();
        currentPos = 0;

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_typeOfFault)));
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
        mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_noOfAcAvailableAtSite))),
                        "No of AC available at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpNoOfAcAvailableAtSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal.setText(str_pmSiteAcpNoOfAcAvailableAtSiteVal);

                        invalidateOptionsMenu();
                        if (acCheckPointsData != null && acCheckPointsData.size() > 0) {
                            acCheckPointsData.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        if (str_pmSiteAcpNoOfAcAvailableAtSiteVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                        } else {
                            totalCount = Integer.parseInt(str_pmSiteAcpNoOfAcAvailableAtSiteVal);
                            mPreventiveMaintenanceSiteAcCheckPointsTextViewAcNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Finish");
                            } else {
                                mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Next Reading");
                            }
                        }
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_workingConditionOfAc))),
                        "Working Condition of AC",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpWorkingConditionOfAcVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.setText(str_pmSiteAcpWorkingConditionOfAcVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_automationOfAcController))),
                        "Automation of AC Controller",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpAutomationOfAcControllerVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.setText(str_pmSiteAcpAutomationOfAcControllerVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_acEarthingStatus))),
                        "AC Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpAcEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.setText(str_pmSiteAcpAcEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_acFilterStatus))),
                        "AC Filter Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpAcFilterStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.setText(str_pmSiteAcpAcFilterStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_cleaningOfCoolingOrCondensorCoils))),
                        "Cleaning of Cooling/Condensor Coils",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpCleaningOfCoolingOrCondensorCoilsVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.setText(str_pmSiteAcpCleaningOfCoolingOrCondensorCoilsVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_anyAbnormalSoundFromMotor))),
                        "Any abnormal Sound from Motor",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpAnyAbnormalSoundFromMotorVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.setText(str_pmSiteAcpAnyAbnormalSoundFromMotorVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_shelterDoorStatus))),
                        "Shelter Door Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpShelterDoorStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.setText(str_pmSiteAcpShelterDoorStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAcCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteAcpRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteAcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* if (checkValidationOfArrayFields() == true) {*/
                if (currentPos > 0) {
                    //Save current ac reading
                    saveRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayRecords(currentPos);

                }
                /*}*/
            }
        });
        mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalCount - 1)) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    } else if (currentPos == (totalCount - 1)) {
                        saveRecords(currentPos);
                        if (checkDuplicationQrCodeNew() == false) {
                            if (checkValidationonSubmit("onSubmit") == true) {
                                submitDetails();
                                startActivity(new Intent(PreventiveMaintenanceSiteAcCheckPointsActivity.this, PreventiveMaintenanceSiteSmpsCheckPointsActivity.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }

    private void visibilityOfTypesOfFault(String RegisterFault) {
        mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_noOfAcAvailableAtSite);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_noOfAcAvailableAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acNumber);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_QRCodeScan);
        mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_QRCodeScan);
        mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_QRCodeScanView);
        mButtonClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingCondition = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acWorkingCondition);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acWorkingConditionVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcController = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_automationOfAcController);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_automationOfAcControllerVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acEarthingStatus);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acEarthingStatusVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acFilterStatus);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_acFilterStatusVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewPhotoOfAcFiltersBeforeCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_photoOfAcFiltersBeforeCleaning);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaning = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_photoOfAcFiltersBeforeCleaning);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_photoOfAcFiltersBeforeCleaningView);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewPhotoOfAcFiltersAfterCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_photoOfAcFiltersAfterCleaning);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaning = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_photoOfAcFiltersAfterCleaning);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_photoOfAcFiltersAfterCleaningView);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCooling = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_cleaningOfCooling);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_cleaningOfCoolingVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotor = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_abnormalSoundOfMotor);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_abnormalSoundOfMotorVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterTemperature = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_shelterTemperature);
        mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature = (EditText) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_editText_shelterTemperature);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewPhotoOfTemperature = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_photoOfTemperature);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperature = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_photoOfTemperature);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_photoOfTemperatureView);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_shelterDoorStatus);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_shelterDoorStatusVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_nextReading);
        mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_linearLayout_typeOfFault);

        mPreventiveMaintenanceSiteAcCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteAcCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAcCheckPoints_button_uploadPhotoOfRegisterFaultView);
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteAcCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteAcCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    private void setListner() {

        mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    AcCheckPointsQRCodeScan();
                }
            }
        });

        mButtonClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringAcCheckPointsQRCodeScan = "";
                mButtonClearQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        ///////////////

        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoOfAcFiltersBeforeCleaning();
                }
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoOfAcFiltersAfterCleaning();
                }
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoOfTemperature();
                }
            }
        });
        //////////////////////////

        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriAcFiltersBeforeCleaning != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this, imageFileUriAcFiltersBeforeCleaning);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteAcCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriAcFiltersAfterCleaning != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this, imageFileUriAcFiltersAfterCleaning);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteAcCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriTemperature != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this, imageFileUriTemperature);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteAcCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //////////////////////////
        mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteAcCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteAcCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_alarmCheckReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteAcCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AcCheckPointsQRCodeScan() {
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

    private void takePhotoOfAcFiltersBeforeCleaning() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileAcFiltersBeforeCleaning = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileAcFiltersBeforeCleaning);
            imageFileUriAcFiltersBeforeCleaning = FileProvider.getUriForFile(PreventiveMaintenanceSiteAcCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriAcFiltersBeforeCleaning);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfAcFiltersBeforeCleaning);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void takePhotoOfAcFiltersAfterCleaning() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileAcFiltersAfterCleaning = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileAcFiltersAfterCleaning);
            imageFileUriAcFiltersAfterCleaning = FileProvider.getUriForFile(PreventiveMaintenanceSiteAcCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriAcFiltersAfterCleaning);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfAcFiltersAfterCleaning);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePhotoOfTemperature() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileTemperature = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileTemperature);
            imageFileUriTemperature = FileProvider.getUriForFile(PreventiveMaintenanceSiteAcCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriTemperature);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfTemperature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringAcCheckPointsQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringAcCheckPointsQRCodeScan = result.getContents();
                            if (!base64StringAcCheckPointsQRCodeScan.isEmpty() && base64StringAcCheckPointsQRCodeScan != null) {
                                mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringAcCheckPointsQRCodeScan = "";
                            showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                        }
                    }
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfAcFiltersBeforeCleaning:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriAcFiltersBeforeCleaning != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriAcFiltersBeforeCleaning);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringTakePhotoOfAcFiltersBeforeCleaning = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileAcFiltersBeforeCleaning = "";
                    imageFileUriAcFiltersBeforeCleaning = null;
                    mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfAcFiltersAfterCleaning:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriAcFiltersAfterCleaning != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriAcFiltersAfterCleaning);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringTakePhotoOfAcFiltersAfterCleaning = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileAcFiltersAfterCleaning = "";
                    imageFileUriAcFiltersAfterCleaning = null;
                    mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_TakePhotoOfTemperature:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriTemperature != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriTemperature);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringTakePhotoOfTemperature = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileTemperature = "";
                    imageFileUriTemperature = null;
                    mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.GONE);
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
                            mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
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
        if (str_pmSiteAcpNoOfAcAvailableAtSiteVal != null && !str_pmSiteAcpNoOfAcAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_pmSiteAcpNoOfAcAvailableAtSiteVal) > 0) {
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
                String noOfAcAvalibleAtSite = mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal.getText().toString().trim();
                String registerFault = mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
                String typeOfFault = mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
                if (noOfAcAvalibleAtSite.isEmpty() || noOfAcAvalibleAtSite == null) {
                    showToast("Select No of AC Avalible At Site");
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
                } else {
                    submitDetails();
                    startActivity(new Intent(this, PreventiveMaintenanceSiteSmpsCheckPointsActivity.class));
                    finish();
                }
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                dataList = pmSiteTransactionDetails.getAcCheckPointParentData();
                acCheckPointsData.addAll(dataList.getAcCheckPoints());

                mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal.setText(dataList.getNoOfAcAvailableAtsite());
                mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());

                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();
                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteAcCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                    setArrayValuesOfTypeOfFault(dataList.getTypeOfFault());
                }

                str_pmSiteAcpNoOfAcAvailableAtSiteVal = dataList.getNoOfAcAvailableAtsite();
                invalidateOptionsMenu();
                if (acCheckPointsData != null && acCheckPointsData.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewAcNumber.setText("Reading: #1");
                    totalCount = Integer.parseInt(dataList.getNoOfAcAvailableAtsite());

                    mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.setText(acCheckPointsData.get(index).getWorkingConditionOfAc());
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.setText(acCheckPointsData.get(index).getAutomationOfAcController());
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.setText(acCheckPointsData.get(index).getAcEarthingStatus());
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.setText(acCheckPointsData.get(index).getAcFilterStatus());
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.setText(acCheckPointsData.get(index).getCleaningOfCoolingCondensorCoils());
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.setText(acCheckPointsData.get(index).getAnyAbnormalSoundFromMotor());
                    mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.setText(acCheckPointsData.get(index).getShelterDoorStatus());

                    mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature.setText(acCheckPointsData.get(index).getShelterTemperature());

                    base64StringAcCheckPointsQRCodeScan = acCheckPointsData.get(index).getDetailsOfAcQrCodeScan();//////001
                    mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringAcCheckPointsQRCodeScan.isEmpty() && base64StringAcCheckPointsQRCodeScan != null) {
                        mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    base64StringTakePhotoOfAcFiltersBeforeCleaning = acCheckPointsData.get(index).getBase64TakePhotoOfAcFiltersBeforeCleaning();//////002
                    mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.GONE);
                    if (!base64StringTakePhotoOfAcFiltersBeforeCleaning.isEmpty() && base64StringTakePhotoOfAcFiltersBeforeCleaning != null) {
                        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.VISIBLE);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringTakePhotoOfAcFiltersBeforeCleaning);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriAcFiltersBeforeCleaning = Uri.parse(path);
                    }

                    base64StringTakePhotoOfAcFiltersAfterCleaning = acCheckPointsData.get(index).getBase64TakePhotoOfAcFiltersAfterCleaning();//////003
                    mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.GONE);
                    if (!base64StringTakePhotoOfAcFiltersAfterCleaning.isEmpty() && base64StringTakePhotoOfAcFiltersAfterCleaning != null) {
                        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.VISIBLE);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringTakePhotoOfAcFiltersAfterCleaning);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriAcFiltersAfterCleaning = Uri.parse(path);
                    }

                    base64StringTakePhotoOfTemperature = acCheckPointsData.get(index).getBase64TakePhotoOfTemperature();//////004
                    mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.GONE);
                    if (!base64StringTakePhotoOfTemperature.isEmpty() && base64StringTakePhotoOfTemperature != null) {
                        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.VISIBLE);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringTakePhotoOfTemperature);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriTemperature = Uri.parse(path);
                    }

                    mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                    if (totalCount > 1) {
                        mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Finish");
                    }
                }
            } else {
                showToast("No previous saved data available");
                //Toast.makeText(Air_Conditioners.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                mLinearLayoutContainer.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //showToast(e.getMessage().toString());
        }
    }

    private void saveRecords(int pos) {

        String workingConditionOfAc = mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.getText().toString().trim();
        String automationOfAcController = mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.getText().toString().trim();
        String acEarthingStatus = mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.getText().toString().trim();
        String acFilterStatus = mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.getText().toString().trim();
        String cleaningOfCoolingCondensorCoils = mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.getText().toString().trim();
        String anyAbnormalSoundFromMotor = mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.getText().toString().trim();
        String shelterDoorStatus = mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.getText().toString().trim();
        String shelterTemperature = mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature.getText().toString().trim();
        String detailsOfAcQrCodeScan = base64StringAcCheckPointsQRCodeScan;
        String base64TakePhotoOfAcFiltersBeforeCleaning = base64StringTakePhotoOfAcFiltersBeforeCleaning;
        String base64TakePhotoOfAcFiltersAfterCleaning = base64StringTakePhotoOfAcFiltersAfterCleaning;
        String base64TakePhotoOfTemperature = base64StringTakePhotoOfTemperature;

        AcCheckPoint acCheckPointChild = new AcCheckPoint(detailsOfAcQrCodeScan, workingConditionOfAc, automationOfAcController,
                acEarthingStatus, acFilterStatus, base64TakePhotoOfAcFiltersBeforeCleaning,
                base64TakePhotoOfAcFiltersAfterCleaning, cleaningOfCoolingCondensorCoils,
                anyAbnormalSoundFromMotor, shelterTemperature, base64TakePhotoOfTemperature,
                shelterDoorStatus,isQrCodeNew);
        if (acCheckPointsData.size() > 0) {
            if (pos == acCheckPointsData.size()) {
                acCheckPointsData.add(acCheckPointChild);
            } else if (pos < acCheckPointsData.size()) {
                acCheckPointsData.set(pos, acCheckPointChild);
            }
        } else {
            acCheckPointsData.add(acCheckPointChild);
        }
    }

    private void displayRecords(int pos) {

        if (acCheckPointsData.size() > 0 && pos < acCheckPointsData.size()) {

            mPreventiveMaintenanceSiteAcCheckPointsTextViewAcNumber.setText("Reading: #" + (pos + 1));

            mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.setText(acCheckPointsData.get(pos).getWorkingConditionOfAc());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.setText(acCheckPointsData.get(pos).getAutomationOfAcController());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.setText(acCheckPointsData.get(pos).getAcEarthingStatus());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.setText(acCheckPointsData.get(pos).getAcFilterStatus());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.setText(acCheckPointsData.get(pos).getCleaningOfCoolingCondensorCoils());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.setText(acCheckPointsData.get(pos).getAnyAbnormalSoundFromMotor());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.setText(acCheckPointsData.get(pos).getShelterDoorStatus());
           /* mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.setText(acCheckPointsData.get(pos).getRegisterFault());
            mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setText(acCheckPointsData.get(pos).getTypeOfFault());*/
            mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature.setText(acCheckPointsData.get(pos).getShelterTemperature());

            base64StringAcCheckPointsQRCodeScan = acCheckPointsData.get(pos).getDetailsOfAcQrCodeScan();//////001
            mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
            mButtonClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringAcCheckPointsQRCodeScan.isEmpty() && base64StringAcCheckPointsQRCodeScan != null) {
                mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            base64StringTakePhotoOfAcFiltersBeforeCleaning = acCheckPointsData.get(pos).getBase64TakePhotoOfAcFiltersBeforeCleaning();//////002
            mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.GONE);
            if (!base64StringTakePhotoOfAcFiltersBeforeCleaning.isEmpty() && base64StringTakePhotoOfAcFiltersBeforeCleaning != null) {
                mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.VISIBLE);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringTakePhotoOfAcFiltersBeforeCleaning);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriAcFiltersBeforeCleaning = Uri.parse(path);
            }

            base64StringTakePhotoOfAcFiltersAfterCleaning = acCheckPointsData.get(pos).getBase64TakePhotoOfAcFiltersAfterCleaning();//////003
            mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.GONE);
            if (!base64StringTakePhotoOfAcFiltersAfterCleaning.isEmpty() && base64StringTakePhotoOfAcFiltersAfterCleaning != null) {
                mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringTakePhotoOfAcFiltersAfterCleaning);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriAcFiltersAfterCleaning = Uri.parse(path);
            }

            base64StringTakePhotoOfTemperature = acCheckPointsData.get(pos).getBase64TakePhotoOfTemperature();//////004
            mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.GONE);
            if (!base64StringTakePhotoOfTemperature.isEmpty() && base64StringTakePhotoOfTemperature != null) {
                mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringTakePhotoOfTemperature);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriTemperature = Uri.parse(path);
            }

            mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setVisibility(View.VISIBLE);


        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteAcCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteAcCheckPointsButtonNextReading.setText("Next Reading");
            }
        }
    }

    private void clearFields(int pos) {
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.setText("");
        /*mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.setText("");
        mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setText("");*/
        mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature.setText("");

        base64StringAcCheckPointsQRCodeScan = "";
        base64StringTakePhotoOfAcFiltersBeforeCleaning = "";
        base64StringTakePhotoOfAcFiltersAfterCleaning = "";
        base64StringTakePhotoOfTemperature = "";

        mButtonClearQRCodeScanView.setVisibility(View.GONE);
        mButtonClearQRCodeScanView.setVisibility(View.GONE);

        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersAfterCleaningView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfAcFiltersBeforeCleaningView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteAcCheckPointsButtonPhotoOfTemperatureView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteAcCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
        alreadySelectedTypeOfFaultList = new ArrayList<>();

    }

    private void submitDetails() {
        try {
            String noOfAcAvailableAtSite = mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;

            dataList = new AcCheckPointParentData(noOfAcAvailableAtSite, acCheckPointsData, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault);
            pmSiteTransactionDetails.setAcCheckPointParentData(dataList);
            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidationOfArrayFields() {

        String workingConditionOfAc = mPreventiveMaintenanceSiteAcCheckPointsTextViewAcWorkingConditionVal.getText().toString().trim();
        String automationOfAcController = mPreventiveMaintenanceSiteAcCheckPointsTextViewAutomationOfAcControllerVal.getText().toString().trim();
        String acEarthingStatus = mPreventiveMaintenanceSiteAcCheckPointsTextViewAcEarthingStatusVal.getText().toString().trim();
        String acFilterStatus = mPreventiveMaintenanceSiteAcCheckPointsTextViewAcFilterStatusVal.getText().toString().trim();
        String cleaningOfCoolingCondensorCoils = mPreventiveMaintenanceSiteAcCheckPointsTextViewCleaningOfCoolingVal.getText().toString().trim();
        String anyAbnormalSoundFromMotor = mPreventiveMaintenanceSiteAcCheckPointsTextViewAbnormalSoundOfMotorVal.getText().toString().trim();
        String shelterDoorStatus = mPreventiveMaintenanceSiteAcCheckPointsTextViewShelterDoorStatusVal.getText().toString().trim();
        String shelterTemperature = mPreventiveMaintenanceSiteAcCheckPointsEditTextShelterTemperature.getText().toString().trim();
        String detailsOfAcQrCodeScan = base64StringAcCheckPointsQRCodeScan;
        String base64TakePhotoOfAcFiltersBeforeCleaning = base64StringTakePhotoOfAcFiltersBeforeCleaning;
        String base64TakePhotoOfAcFiltersAfterCleaning = base64StringTakePhotoOfAcFiltersAfterCleaning;
        String base64TakePhotoOfTemperature = base64StringTakePhotoOfTemperature;

        if (detailsOfAcQrCodeScan.isEmpty() || detailsOfAcQrCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (workingConditionOfAc.isEmpty() || workingConditionOfAc == null) {
            showToast("Select Working Condition Of AC");
            return false;
        } else if (automationOfAcController.isEmpty() || automationOfAcController == null) {
            showToast("Select Automation Of AC Controller");
            return false;
        } else if (acEarthingStatus.isEmpty() || acEarthingStatus == null) {
            showToast("Select AC Earthing Status");
            return false;
        } else if (acFilterStatus.isEmpty() || acFilterStatus == null) {
            showToast("Select AC Filter Status");
            return false;
        } else if (base64TakePhotoOfAcFiltersBeforeCleaning.isEmpty() || base64TakePhotoOfAcFiltersBeforeCleaning == null) {
            showToast("Take Photo Of AC Filters Before Cleaning");
            return false;
        } else if (base64TakePhotoOfAcFiltersAfterCleaning.isEmpty() || base64TakePhotoOfAcFiltersAfterCleaning == null) {
            showToast("Take Photo Of AC Filters After Cleaning");
            return false;
        } else if (cleaningOfCoolingCondensorCoils.isEmpty() || cleaningOfCoolingCondensorCoils == null) {
            showToast("Select Cleaning Of Cooling Condensor Coils");
            return false;
        } else if (anyAbnormalSoundFromMotor.isEmpty() || anyAbnormalSoundFromMotor == null) {
            showToast("Select Any Abnormal Sound From Motor");
            return false;
        } else if (shelterTemperature.isEmpty() || shelterTemperature == null) {
            showToast("Select Shelter Temperature");
            return false;
        } else if (base64TakePhotoOfTemperature.isEmpty() || base64TakePhotoOfTemperature == null) {
            showToast("Take Photo Of Temperature");
            return false;
        } else if (shelterDoorStatus.isEmpty() || shelterDoorStatus == null) {
            showToast("Select Shelter Door Status");
            return false;
        } else return true;

    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < acCheckPointsData.size(); i++) {
            for (int j = i + 1; j < acCheckPointsData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (acCheckPointsData.get(i).getDetailsOfAcQrCodeScan().toString().equals(acCheckPointsData.get(j).getDetailsOfAcQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkValidationonSubmit(String methodFlag) {

        String noOfAcAvalibleAtSite = mPreventiveMaintenanceSiteAcCheckPointsTextViewNoOfAcAvailableAtSiteVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteAcCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
        if (noOfAcAvalibleAtSite.isEmpty() || noOfAcAvalibleAtSite == null) {
            showToast("Select No of AC Avalible At Site");
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
        } else if (Integer.valueOf(noOfAcAvalibleAtSite) > 0) {
            if ((acCheckPointsData.size() != Integer.valueOf(noOfAcAvalibleAtSite) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;
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
                        //str_pmSiteEcpTypeOfFaultVal = dataString;
                        //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteEcpTypeOfFaultVal);

                        str_pmSiteAcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteAcpTypeOfFaultVal);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");
                    }
                });
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
}
