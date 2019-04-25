package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerPlantDetailsData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerPlantDetailsModulesData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerPlantDetailsParentData;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PowerPlantDetailsActivity extends BaseActivity {

    private TextView mPowerPlantDetailsTextViewQRCodeScan;
    private ImageView mPowerPlantDetailsButtonQRCodeScan;
    private ImageView mPowerPlantDetailsButtonQRCodeScanView;

    private TextView mpowerPlantDetails_textView_PlantNumber;
    private TextView mPowerPlantDetailsTextViewAssetOwner;
    private TextView mPowerPlantDetailsTextViewAssetOwnerVal;
    private TextView mPowerPlantDetailsTextViewNumberOfPowerPlant;
    private TextView mPowerPlantDetailsTextViewNumberOfPowerPlantVal;
    private TextView mPowerPlantDetailsTextViewManufacturerMakeModel;
    private TextView mPowerPlantDetailsTextViewManufacturerMakeModelVal;
    private TextView mPowerPlantDetailsTextViewPowerPlantModel;
    private EditText mPowerPlantDetailsEditTextPowerPlantModel;
    private TextView mPowerPlantDetailsTextViewNumberModuleSlots;
    private TextView mPowerPlantDetailsTextViewNumberModuleSlotsVal;
    private TextView mPowerPlantDetailsTextViewPowerPlantEarthingStatus;
    private TextView mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal;
    private TextView mPowerPlantDetailsTextViewDcLoadInDisplayAmp;
    private EditText mPowerPlantDetailsEditTextDcLoadInDisplayAmp;
    private TextView mPowerPlantDetailsTextViewPowerPlantSerialNumber;
    private EditText mPowerPlantDetailsEditTextPowerPlantSerialNumber;
    private TextView mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmps;
    private TextView mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal;
    private TextView mPowerPlantDetailsTextViewCapacityInAmp;
    private EditText mPowerPlantDetailsEditTextCapacityInAmp;
    private TextView mPowerPlantDetailsTextViewNumberOfModules;
    private TextView mPowerPlantDetailsTextViewNumberOfModulesVal;
    private TextView mPowerPlantDetailsTextViewNoOfFaultyModulese;
    private EditText mPowerPlantDetailsEditTextBookValue;
    private TextView mPowerPlantDetailsTextViewNoOfFaultyModuleseVal;
    private TextView mPowerPlantDetailsTextViewSmpsExpandableUpToKW;
    private EditText mPowerPlantDetailsEditTextSmpsExpandableUpToKW;
    private TextView mPowerPlantDetailsTextViewSmpsUltimateCapacity;
    private EditText mPowerPlantDetailsEditTextSmpsUltimateCapacity;
    private TextView mPowerPlantDetailsTextViewSpdStatus;
    private TextView mPowerPlantDetailsTextViewSpdStatusVal;
    private TextView mPowerPlantDetailsTextViewWorkingCondition;
    private TextView mPowerPlantDetailsTextViewWorkingConditionVal;
    private TextView mPowerPlantDetailsTextViewNatureOfProblem;
    private EditText mPowerPlantDetailsEditTextNatureOfProblem;
    private Button btnPrevReadingPowerPlant;
    private Button btnNextReadingPowerPlant;
    private ImageView powerPlantDetails_imageview_modules;
    private LinearLayout lnrPlantDetails;
    private ImageView button_ClearQRCodeScanView;

    private LinearLayout mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking;
    private TextView mPowerPlantDetailsTextViewNumberOfPowerPlantWorking;
    private TextView mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal;

    private String str_assetOwner;
    private String str_numberOfPowerPlant;
    private String str_manufacturerMakeModel;
    private String str_numberModuleSlots;
    private String str_powerPlantEarthingStatus;
    private String str_typeOfPowerPlantCommercialSmps;
    private String str_numberOfModules;
    private String str_noOfFaultyModulese;
    private String str_spdStatus;
    private String str_workingCondition;
    private String str_numberOfPowerPlantWorking;

    private static final String TAG = PowerPlantDetailsActivity.class.getSimpleName();
    DecimalConversion decimalConversion;
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private PowerPlantDetailsParentData powerPlantDetailsParentData;
    private ArrayList<PowerPlantDetailsData> powerPlantDetailsDataList;
    private String base64StringQRCodeScan = "";
    private SessionManager sessionManager;
    private Uri imageFileUri;
    private String imageFileName = "";
    private int totalPlantCount = 0;
    private int currentPos = 0;
    //
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_FLAG_MODULE_RESULT = 200;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    //public String date_flag = "no";

    private AlertDialogManager alertDialogManager;

    private ArrayList<PowerPlantDetailsModulesData> powerPlantDetailsModulesData;
    private ArrayList<PowerPlantDetailsModulesData> powerPlantDetailsModulesDataForDuplicity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_plant_details);
        this.setTitle("Power Plant Details");
        alertDialogManager = new AlertDialogManager(PowerPlantDetailsActivity.this);
        decimalConversion = new DecimalConversion();
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
        powerPlantDetailsDataList = new ArrayList<>();
        powerPlantDetailsModulesData = new ArrayList<>();
        sessionManager = new SessionManager(PowerPlantDetailsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PowerPlantDetailsActivity.this, userId, ticketName);

        setInputDetails(0);

        mPowerPlantDetailsButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PowerPlantDetailsActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(PowerPlantDetailsActivity.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(PowerPlantDetailsActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PowerPlantDetailsActivity.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(PowerPlantDetailsActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCamera();
                    onClicked(v);
                }

            }
        });
        mPowerPlantDetailsEditTextDcLoadInDisplayAmp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerPlantDetailsEditTextCapacityInAmp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerPlantDetailsEditTextSmpsExpandableUpToKW.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerPlantDetailsEditTextSmpsUltimateCapacity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });


        /*This Commented By 008 on 15-11-2018 For QR Code Purpose
        mPowerPlantDetailsButtonQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(PowerPlantDetailsActivity.this, imageFileUri);
                } else {
                    Toast.makeText(PowerPlantDetailsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }


    private void assignViews() {
        mPowerPlantDetailsTextViewQRCodeScan = (TextView) findViewById(R.id.powerPlantDetails_textView_QRCodeScan);
        mPowerPlantDetailsButtonQRCodeScan = (ImageView) findViewById(R.id.powerPlantDetails_button_QRCodeScan);

        mPowerPlantDetailsButtonQRCodeScanView = (ImageView) findViewById(R.id.powerPlantDetails_button_QRCodeScanView);
        mpowerPlantDetails_textView_PlantNumber = (TextView) findViewById(R.id.powerPlantDetails_textView_PlantNumber);

        mPowerPlantDetailsTextViewAssetOwner = (TextView) findViewById(R.id.powerPlantDetails_textView_assetOwner);
        mPowerPlantDetailsTextViewAssetOwnerVal = (TextView) findViewById(R.id.powerPlantDetails_textView_assetOwner_val);
        mPowerPlantDetailsTextViewNumberOfPowerPlant = (TextView) findViewById(R.id.powerPlantDetails_textView_numberOfPowerPlant);
        mPowerPlantDetailsTextViewNumberOfPowerPlantVal = (TextView) findViewById(R.id.powerPlantDetails_textView_numberOfPowerPlant_val);
        mPowerPlantDetailsTextViewManufacturerMakeModel = (TextView) findViewById(R.id.powerPlantDetails_textView_manufacturerMakeModel);
        mPowerPlantDetailsTextViewManufacturerMakeModelVal = (TextView) findViewById(R.id.powerPlantDetails_textView_manufacturerMakeModel_val);
        mPowerPlantDetailsTextViewPowerPlantModel = (TextView) findViewById(R.id.powerPlantDetails_textView_powerPlantModel);
        mPowerPlantDetailsEditTextPowerPlantModel = (EditText) findViewById(R.id.powerPlantDetails_editText_powerPlantModel);
        mPowerPlantDetailsTextViewNumberModuleSlots = (TextView) findViewById(R.id.powerPlantDetails_textView_numberModuleSlots);
        mPowerPlantDetailsTextViewNumberModuleSlotsVal = (TextView) findViewById(R.id.powerPlantDetails_textView_numberModuleSlots_val);
        mPowerPlantDetailsTextViewPowerPlantEarthingStatus = (TextView) findViewById(R.id.powerPlantDetails_textView_powerPlantEarthingStatus);
        mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal = (TextView) findViewById(R.id.powerPlantDetails_textView_powerPlantEarthingStatus_val);
        mPowerPlantDetailsTextViewDcLoadInDisplayAmp = (TextView) findViewById(R.id.powerPlantDetails_textView_dcLoadInDisplayAmp);
        mPowerPlantDetailsEditTextDcLoadInDisplayAmp = (EditText) findViewById(R.id.powerPlantDetails_editText_dcLoadInDisplayAmp);
        mPowerPlantDetailsTextViewPowerPlantSerialNumber = (TextView) findViewById(R.id.powerPlantDetails_textView_powerPlantSerialNumber);
        mPowerPlantDetailsEditTextPowerPlantSerialNumber = (EditText) findViewById(R.id.powerPlantDetails_editText_powerPlantSerialNumber);
        mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmps = (TextView) findViewById(R.id.powerPlantDetails_textView_typeOfPowerPlantCommercialSmps);
        mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal = (TextView) findViewById(R.id.powerPlantDetails_textView_typeOfPowerPlantCommercialSmps_val);
        mPowerPlantDetailsTextViewCapacityInAmp = (TextView) findViewById(R.id.powerPlantDetails_textView_capacityInAmp);
        mPowerPlantDetailsEditTextCapacityInAmp = (EditText) findViewById(R.id.powerPlantDetails_editText_capacityInAmp);
        mPowerPlantDetailsTextViewNumberOfModules = (TextView) findViewById(R.id.powerPlantDetails_textView_numberOfModules);
        mPowerPlantDetailsTextViewNumberOfModulesVal = (TextView) findViewById(R.id.powerPlantDetails_textView_numberOfModules_val);
        mPowerPlantDetailsTextViewNoOfFaultyModulese = (TextView) findViewById(R.id.powerPlantDetails_textView_noOfFaultyModulese);

        mPowerPlantDetailsEditTextBookValue = (EditText) findViewById(R.id.powerPlantDetails_editText_bookValue);

        mPowerPlantDetailsTextViewNoOfFaultyModuleseVal = (TextView) findViewById(R.id.powerPlantDetails_textView_noOfFaultyModulese_val);
        mPowerPlantDetailsTextViewSmpsExpandableUpToKW = (TextView) findViewById(R.id.powerPlantDetails_textView_smpsExpandableUpToKW);
        mPowerPlantDetailsEditTextSmpsExpandableUpToKW = (EditText) findViewById(R.id.powerPlantDetails_editText_smpsExpandableUpToKW);
        mPowerPlantDetailsTextViewSmpsUltimateCapacity = (TextView) findViewById(R.id.powerPlantDetails_textView_smpsUltimateCapacity);
        mPowerPlantDetailsEditTextSmpsUltimateCapacity = (EditText) findViewById(R.id.powerPlantDetails_editText_smpsUltimateCapacity);
        mPowerPlantDetailsTextViewSpdStatus = (TextView) findViewById(R.id.powerPlantDetails_textView_spdStatus);
        mPowerPlantDetailsTextViewSpdStatusVal = (TextView) findViewById(R.id.powerPlantDetails_textView_spdStatus_val);
        mPowerPlantDetailsTextViewWorkingCondition = (TextView) findViewById(R.id.powerPlantDetails_textView_workingCondition);
        mPowerPlantDetailsTextViewWorkingConditionVal = (TextView) findViewById(R.id.powerPlantDetails_textView_workingCondition_val);
        mPowerPlantDetailsTextViewNatureOfProblem = (TextView) findViewById(R.id.powerPlantDetails_textView_natureOfProblem);
        mPowerPlantDetailsEditTextNatureOfProblem = (EditText) findViewById(R.id.powerPlantDetails_editText_natureOfProblem);
        btnPrevReadingPowerPlant = (Button) findViewById(R.id.btnPrevReadingPowerPlant);
        btnNextReadingPowerPlant = (Button) findViewById(R.id.btnNextReadingPowerPlant);
        powerPlantDetails_imageview_modules = (ImageView) findViewById(R.id.powerPlantDetails_imageview_modules);
        button_ClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);

        lnrPlantDetails = (LinearLayout) findViewById(R.id.lnrPlantDetails);
        lnrPlantDetails.setVisibility(View.GONE);

        mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking = (LinearLayout) findViewById(R.id.powerPlantDetails_linearLayout_numberOfPowerPlantWorking);
        mPowerPlantDetailsTextViewNumberOfPowerPlantWorking = (TextView) findViewById(R.id.powerPlantDetails_textView_numberOfPowerPlantWorking);
        mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal = (TextView) findViewById(R.id.powerPlantDetails_textView_numberOfPowerPlantWorking_val);
        mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.GONE);

        mPowerPlantDetailsEditTextDcLoadInDisplayAmp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerPlantDetailsEditTextCapacityInAmp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerPlantDetailsEditTextSmpsExpandableUpToKW.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerPlantDetailsEditTextSmpsUltimateCapacity.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void DecimalFormatConversion() {
        mPowerPlantDetailsEditTextDcLoadInDisplayAmp.setText(decimalConversion.convertDecimal(mPowerPlantDetailsEditTextDcLoadInDisplayAmp.getText().toString()));
        mPowerPlantDetailsEditTextCapacityInAmp.setText(decimalConversion.convertDecimal(mPowerPlantDetailsEditTextCapacityInAmp.getText().toString()));
        mPowerPlantDetailsEditTextSmpsExpandableUpToKW.setText(decimalConversion.convertDecimal(mPowerPlantDetailsEditTextSmpsExpandableUpToKW.getText().toString()));
        mPowerPlantDetailsEditTextSmpsUltimateCapacity.setText(decimalConversion.convertDecimal(mPowerPlantDetailsEditTextSmpsUltimateCapacity.getText().toString()));
    }

    private void initCombo() {
        mPowerPlantDetailsTextViewAssetOwnerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_assetOwner))),
                        "Asset Owner",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_assetOwner = item.get(position);
                        mPowerPlantDetailsTextViewAssetOwnerVal.setText(str_assetOwner);
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewNumberOfPowerPlantVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_numberOfPowerPlant))),
                        "Number of Power Plant",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfPowerPlant = item.get(position);
                        invalidateOptionsMenu();
                        mPowerPlantDetailsTextViewNumberOfPowerPlantVal.setText(str_numberOfPowerPlant);
                        mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.setText("");

                        currentPos = 0;
                        totalPlantCount = Integer.parseInt(str_numberOfPowerPlant);
                        clearFields(currentPos);
                        //clear TenantData collection empty by select / changing value of No of Tenant selected
                        if (powerPlantDetailsDataList != null && powerPlantDetailsDataList.size() > 0) {
                            powerPlantDetailsDataList.clear();
                        }
                        if (totalPlantCount > 0) {

                            mpowerPlantDetails_textView_PlantNumber.setText("Plant: #1");
                            lnrPlantDetails.setVisibility(View.VISIBLE);
                            mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.VISIBLE);
                            btnPrevReadingPowerPlant.setVisibility(View.GONE);
                            btnNextReadingPowerPlant.setVisibility(View.VISIBLE);
                            if (totalPlantCount > 0 && totalPlantCount == 1) {
                                btnNextReadingPowerPlant.setText("Finish");
                            } else {
                                btnNextReadingPowerPlant.setText("Next Reading");
                            }
                        } else {
                            lnrPlantDetails.setVisibility(View.GONE);
                            mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_numberOfWorkingPowerPlant))),
                        "Number Of Working Power Plant",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfPowerPlantWorking = item.get(position);
                        mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.setText("");
                        if (checkValidationOnChangeNoOfPowerPlant(mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim(), str_numberOfPowerPlantWorking, "onClick") == true) {
                            mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.setText(str_numberOfPowerPlantWorking);
                        }
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewManufacturerMakeModelVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_manufacturerMakeModel))),
                        "Manufacturer/Make",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_manufacturerMakeModel = item.get(position);
                        mPowerPlantDetailsTextViewManufacturerMakeModelVal.setText(str_manufacturerMakeModel);
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewNumberModuleSlotsVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_numberModuleSlots))),
                        "Number Module Slots",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberModuleSlots = item.get(position);
                        mPowerPlantDetailsTextViewNumberModuleSlotsVal.setText(str_numberModuleSlots);
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_powerPlantEarthingStatus))),
                        "Power Plant Earthing Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_powerPlantEarthingStatus = item.get(position);
                        mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.setText(str_powerPlantEarthingStatus);
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_typeOfPowerPlantCommercialSmps))),
                        "Type of the Power Plant [Commercial/SMPs]",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfPowerPlantCommercialSmps = item.get(position);
                        mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.setText(str_typeOfPowerPlantCommercialSmps);
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewNumberOfModulesVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_numberOfModules))),
                        "Number of Modules",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfModules = item.get(position);
                        if (checkValidationOnChangeNoOfModuleSlots(mPowerPlantDetailsTextViewNumberModuleSlotsVal.getText().toString().trim(), str_numberOfModules) == true) {
                            mPowerPlantDetailsTextViewNumberOfModulesVal.setText(str_numberOfModules);
                            int count = Integer.parseInt(str_numberOfModules);
                            if (count > 0) {
                                powerPlantDetails_imageview_modules.setVisibility(View.VISIBLE);
                            } else {
                                powerPlantDetails_imageview_modules.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        });

        powerPlantDetails_imageview_modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //007
                str_numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString();
                if (str_numberOfModules == null || str_numberOfModules.isEmpty()) {
                    showToast("Select number of Modules");
                } else {
                    int count = Integer.parseInt(str_numberOfModules);
                    if (count > 0) {
                        savePlantRecords(currentPos);
                        Intent i = new Intent(PowerPlantDetailsActivity.this, PowerPlantDetailsModulesReadingsActivity.class);
                        i.putExtra("numberOfModules", count);
                        i.putExtra("powerPlantDetailsModulesData", powerPlantDetailsModulesData);
                        i.putExtra("powerPlantDetailsDataList", powerPlantDetailsDataList);
                        //startActivity(i);
                        startActivityForResult(i, MY_FLAG_MODULE_RESULT);
                    } else {
                        showToast("Number of Modules is zero.");
                    }
                }
            }
        });

        mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_noOfFaultyModules))),
                        "No. of Faulty Modules",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfFaultyModulese = item.get(position);
                        if (checkValidationOnChangeNoOfFaultyModulese(mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim(), str_noOfFaultyModulese) == true) {
                            mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.setText(str_noOfFaultyModulese);
                        }
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewSpdStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_spdStatus))),
                        "SPD Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_spdStatus = item.get(position);
                        mPowerPlantDetailsTextViewSpdStatusVal.setText(str_spdStatus);
                    }
                });
            }
        });

        mPowerPlantDetailsTextViewWorkingConditionVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_workingCondition))),
                        "Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_workingCondition = item.get(position);
                        mPowerPlantDetailsTextViewWorkingConditionVal.setText(str_workingCondition);
                    }
                });
            }
        });

        btnPrevReadingPowerPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkValidationOfArrayFields() == true) {*/
                if (currentPos > 0) {
                    //Save current reading
                    savePlantRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayPlantRecords(currentPos);
                }
                /*}*/
            }
        });

        btnNextReadingPowerPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalPlantCount - 1)) {
                        //Save current  reading
                        savePlantRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayPlantRecords(currentPos);

                    } else if (currentPos == (totalPlantCount - 1)) {
                        savePlantRecords(currentPos);
                        //if (checkValidationOnNoOfPowerPlant() == true) {
                        if (checkDuplicationQrCode() == false) {
                            if (checkValidationOnChangeNoOfPowerPlant(mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim(), mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.getText().toString().trim(), "onSubmit") == true) {
                                //Save Final current reading and submit all  data
                                //savePlantRecords(currentPos);
                                submitDetails();
                                startActivity(new Intent(PowerPlantDetailsActivity.this, Power_Backups_DG.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

        button_ClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringQRCodeScan = "";
                button_ClearQRCodeScanView.setVisibility(View.GONE);
                mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

    }

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                powerPlantDetailsParentData = hotoTransactionData.getPowerPlantDetailsParentData();
                powerPlantDetailsDataList.addAll(powerPlantDetailsParentData.getPowerPlantDetailsData());

                mPowerPlantDetailsTextViewNumberOfPowerPlantVal.setText(powerPlantDetailsParentData.getNumberOfPowerPlant());

                mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.GONE);
                if (!powerPlantDetailsParentData.getNumberOfWorkingPowerPlant().isEmpty() && powerPlantDetailsParentData.getNumberOfWorkingPowerPlant() != null) {
                    mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.VISIBLE);
                }
                mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.setText(powerPlantDetailsParentData.getNumberOfWorkingPowerPlant());

                if (powerPlantDetailsDataList != null && powerPlantDetailsDataList.size() > 0) {
                    totalPlantCount = powerPlantDetailsDataList.size();
                    //mPowerPlantDetailsTextViewNumberOfPowerPlantVal.setText(powerPlantDetailsParentData.getNumberOfPowerPlant());
                    lnrPlantDetails.setVisibility(View.VISIBLE);
                    PowerPlantDetailsData powerPlantDetailsData = powerPlantDetailsDataList.get(index);

                    base64StringQRCodeScan = powerPlantDetailsData.getqRCodeScan();
                    mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                        mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    powerPlantDetailsModulesData.addAll(powerPlantDetailsData.getPowerPlantDetailsModulesData());
                    str_numberOfModules = "" + powerPlantDetailsModulesData.size();
                    int count = powerPlantDetailsModulesData.size();
                    if (count > 0) {
                        powerPlantDetails_imageview_modules.setVisibility(View.VISIBLE);
                    } else {
                        powerPlantDetails_imageview_modules.setVisibility(View.GONE);
                    }

                    mPowerPlantDetailsTextViewAssetOwnerVal.setText(powerPlantDetailsData.getAssetOwner());
                    mPowerPlantDetailsTextViewManufacturerMakeModelVal.setText(powerPlantDetailsData.getManufacturerMakeModel());
                    mPowerPlantDetailsEditTextPowerPlantModel.setText(powerPlantDetailsData.getPowerPlantModel());
                    mPowerPlantDetailsTextViewNumberModuleSlotsVal.setText(powerPlantDetailsData.getNumberModuleSlots());
                    mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.setText(powerPlantDetailsData.getEarthingStatus());
                    mPowerPlantDetailsEditTextDcLoadInDisplayAmp.setText(powerPlantDetailsData.getDcLoadInDisplay());
                    mPowerPlantDetailsEditTextPowerPlantSerialNumber.setText(powerPlantDetailsData.getSerialNumber());
                    mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.setText(powerPlantDetailsData.getTypeOfPowerPlantCommercialSmps());
                    mPowerPlantDetailsEditTextCapacityInAmp.setText(powerPlantDetailsData.getCapacityInAmp());
                    mPowerPlantDetailsTextViewNumberOfModulesVal.setText(powerPlantDetailsData.getNumberOfModules());
                    mPowerPlantDetailsEditTextBookValue.setText(powerPlantDetailsData.getBookValue());
                    mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.setText(powerPlantDetailsData.getNoOfFaultyModulese());
                    mPowerPlantDetailsEditTextSmpsExpandableUpToKW.setText(powerPlantDetailsData.getSmpsExpandable());
                    mPowerPlantDetailsEditTextSmpsUltimateCapacity.setText(powerPlantDetailsData.getSmpsUltimateCapacity());
                    mPowerPlantDetailsTextViewSpdStatusVal.setText(powerPlantDetailsData.getSpdStatus());
                    mPowerPlantDetailsTextViewWorkingConditionVal.setText(powerPlantDetailsData.getWorkingCondition());
                    mPowerPlantDetailsEditTextNatureOfProblem.setText(powerPlantDetailsData.getNatureOfProblem());

                    btnPrevReadingPowerPlant.setVisibility(View.GONE);
                    btnNextReadingPowerPlant.setVisibility(View.VISIBLE);

                    //if (powerPlantDetailsDataList.size() > 1) {
                    if (totalPlantCount > 1) {
                        btnNextReadingPowerPlant.setText("Next Reading");
                    } else {
                        btnNextReadingPowerPlant.setText("Finish");
                    }
                }

            } else {
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPlantRecords(int pos) {

        if (powerPlantDetailsDataList.size() > 0 && pos < powerPlantDetailsDataList.size()) {

            mpowerPlantDetails_textView_PlantNumber.setText("Plant: #" + (pos + 1));

            PowerPlantDetailsData powerPlantDetailsData = powerPlantDetailsDataList.get(pos);

            base64StringQRCodeScan = powerPlantDetailsData.getqRCodeScan();
            mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            powerPlantDetailsModulesData.clear();
            powerPlantDetailsModulesData.addAll(powerPlantDetailsData.getPowerPlantDetailsModulesData());

            str_numberOfModules = "" + powerPlantDetailsModulesData.size();
            int count = powerPlantDetailsModulesData.size();
            if (count > 0 || Integer.valueOf(powerPlantDetailsData.getNumberOfModules() == "" ? "0" : powerPlantDetailsData.getNumberOfModules()) > 0) {
                powerPlantDetails_imageview_modules.setVisibility(View.VISIBLE);
            } else {
                powerPlantDetails_imageview_modules.setVisibility(View.GONE);
            }

            mPowerPlantDetailsTextViewAssetOwnerVal.setText(powerPlantDetailsData.getAssetOwner());
            mPowerPlantDetailsTextViewManufacturerMakeModelVal.setText(powerPlantDetailsData.getManufacturerMakeModel());
            mPowerPlantDetailsEditTextPowerPlantModel.setText(powerPlantDetailsData.getPowerPlantModel());
            mPowerPlantDetailsTextViewNumberModuleSlotsVal.setText(powerPlantDetailsData.getNumberModuleSlots());
            mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.setText(powerPlantDetailsData.getEarthingStatus());
            mPowerPlantDetailsEditTextDcLoadInDisplayAmp.setText(powerPlantDetailsData.getDcLoadInDisplay());
            mPowerPlantDetailsEditTextPowerPlantSerialNumber.setText(powerPlantDetailsData.getSerialNumber());
            mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.setText(powerPlantDetailsData.getTypeOfPowerPlantCommercialSmps());
            mPowerPlantDetailsEditTextCapacityInAmp.setText(powerPlantDetailsData.getCapacityInAmp());
            mPowerPlantDetailsTextViewNumberOfModulesVal.setText(powerPlantDetailsData.getNumberOfModules());
            mPowerPlantDetailsEditTextBookValue.setText(powerPlantDetailsData.getBookValue());
            mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.setText(powerPlantDetailsData.getNoOfFaultyModulese());
            mPowerPlantDetailsEditTextSmpsExpandableUpToKW.setText(powerPlantDetailsData.getSmpsExpandable());
            mPowerPlantDetailsEditTextSmpsUltimateCapacity.setText(powerPlantDetailsData.getSmpsUltimateCapacity());
            mPowerPlantDetailsTextViewSpdStatusVal.setText(powerPlantDetailsData.getSpdStatus());
            mPowerPlantDetailsTextViewWorkingConditionVal.setText(powerPlantDetailsData.getWorkingCondition());
            mPowerPlantDetailsEditTextNatureOfProblem.setText(powerPlantDetailsData.getNatureOfProblem());

            btnPrevReadingPowerPlant.setVisibility(View.VISIBLE);
            btnNextReadingPowerPlant.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalPlantCount - 1)) {
            btnPrevReadingPowerPlant.setVisibility(View.VISIBLE);
            btnNextReadingPowerPlant.setText("Next Reading");
        } else if (pos > 0 && pos == (totalPlantCount - 1)) {
            btnPrevReadingPowerPlant.setVisibility(View.VISIBLE);
            btnNextReadingPowerPlant.setText("Finish");
        } else if (pos == 0) {
            btnPrevReadingPowerPlant.setVisibility(View.GONE);
            if (pos == (totalPlantCount - 1)) {
                btnNextReadingPowerPlant.setText("Finish");
            } else {
                btnNextReadingPowerPlant.setText("Next Reading");
            }
        }
    }

    private void savePlantRecords(int pos) {

        String qRCodeScan = base64StringQRCodeScan;
        String assetOwner = mPowerPlantDetailsTextViewAssetOwnerVal.getText().toString().trim();
        String numberOfPowerPlant = mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim();
        String manufacturerMakeModel = mPowerPlantDetailsTextViewManufacturerMakeModelVal.getText().toString().trim();
        String powerPlantModel = mPowerPlantDetailsEditTextPowerPlantModel.getText().toString().trim();
        String numberModuleSlots = mPowerPlantDetailsTextViewNumberModuleSlotsVal.getText().toString().trim();
        String earthingStatus = mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.getText().toString().trim();
        String dcLoadInDisplay = mPowerPlantDetailsEditTextDcLoadInDisplayAmp.getText().toString().trim();
        String serialNumber = mPowerPlantDetailsEditTextPowerPlantSerialNumber.getText().toString().trim();
        String typeOfPowerPlantCommercialSmps = mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.getText().toString().trim();
        String capacityInAmp = mPowerPlantDetailsEditTextCapacityInAmp.getText().toString().trim();
        String numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim();
        String bookedValue = mPowerPlantDetailsEditTextBookValue.getText().toString().trim();
        String noOfFaultyModulese = mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.getText().toString().trim();
        String smpsExpandable = mPowerPlantDetailsEditTextSmpsExpandableUpToKW.getText().toString().trim();
        String SmpsUltimateCapacity = mPowerPlantDetailsEditTextSmpsUltimateCapacity.getText().toString().trim();
        String spdStatus = mPowerPlantDetailsTextViewSpdStatusVal.getText().toString().trim();
        String workingCondition = mPowerPlantDetailsTextViewWorkingConditionVal.getText().toString().trim();
        String natureOfProblem = mPowerPlantDetailsEditTextNatureOfProblem.getText().toString().trim();

        ArrayList<PowerPlantDetailsModulesData> arr_powerPlantDetailsModulesData = new ArrayList<>();
        arr_powerPlantDetailsModulesData.addAll(powerPlantDetailsModulesData);

        //007
        PowerPlantDetailsData powerPlantDetailsData = new PowerPlantDetailsData(qRCodeScan, bookedValue, assetOwner, manufacturerMakeModel, powerPlantModel, numberModuleSlots, earthingStatus, dcLoadInDisplay, serialNumber, typeOfPowerPlantCommercialSmps, capacityInAmp, numberOfModules, noOfFaultyModulese, smpsExpandable, SmpsUltimateCapacity, spdStatus, workingCondition, natureOfProblem, imageFileName, arr_powerPlantDetailsModulesData);

        if (powerPlantDetailsDataList.size() > 0) {
            if (pos == powerPlantDetailsDataList.size()) {
                powerPlantDetailsDataList.add(powerPlantDetailsData);
            } else if (pos < powerPlantDetailsDataList.size()) {
                powerPlantDetailsDataList.set(pos, powerPlantDetailsData);
            }
        } else {
            powerPlantDetailsDataList.add(powerPlantDetailsData);
        }
    }

    private void submitDetails() {
        try {
            //hotoTransactionData.setTicketNo(ticketName);
            String totalNumberofPlants = mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim();
            String numberOfWorkingPowerPlant = mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.getText().toString().trim();
            powerPlantDetailsParentData = new PowerPlantDetailsParentData(totalNumberofPlants, numberOfWorkingPowerPlant, powerPlantDetailsDataList);
            hotoTransactionData.setPowerPlantDetailsParentData(powerPlantDetailsParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        mpowerPlantDetails_textView_PlantNumber.setText("Tenant: #" + (indexPos + 1));

        mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
        button_ClearQRCodeScanView.setVisibility(View.GONE);

        mPowerPlantDetailsTextViewAssetOwnerVal.setText("");
        mPowerPlantDetailsTextViewManufacturerMakeModelVal.setText("");
        mPowerPlantDetailsEditTextPowerPlantModel.setText("");
        mPowerPlantDetailsTextViewNumberModuleSlotsVal.setText("");
        mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.setText("");
        mPowerPlantDetailsEditTextDcLoadInDisplayAmp.setText("");
        mPowerPlantDetailsEditTextPowerPlantSerialNumber.setText("");
        mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.setText("");
        mPowerPlantDetailsEditTextCapacityInAmp.setText("");
        mPowerPlantDetailsTextViewNumberOfModulesVal.setText("");
        powerPlantDetails_imageview_modules.setVisibility(View.GONE);
        mPowerPlantDetailsEditTextBookValue.setText("");
        mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.setText("");
        mPowerPlantDetailsEditTextSmpsExpandableUpToKW.setText("");
        mPowerPlantDetailsEditTextSmpsUltimateCapacity.setText("");
        mPowerPlantDetailsTextViewSpdStatusVal.setText("");
        mPowerPlantDetailsTextViewWorkingConditionVal.setText("");
        mPowerPlantDetailsEditTextNatureOfProblem.setText("");
        base64StringQRCodeScan = "";
        powerPlantDetailsModulesData.clear();
    }

    /*008 21112018*/
    public boolean checkValidationOnChangeNoOfPowerPlant(String numberOfPowerPlant, String numberOfWorkingPowerPlant, String methodFlag) {

        /*if (!numberOfPowerPlant.isEmpty() && numberOfPowerPlant != null) {
            if (Integer.valueOf(numberOfPowerPlant) > 0) {
                if (!numberOfWorkingPowerPlant.isEmpty() && numberOfWorkingPowerPlant != null) {
                    if (Integer.valueOf(numberOfWorkingPowerPlant) <= Integer.valueOf(numberOfPowerPlant)) {
                        return true;
                    } else {
                        showToast("Select number of working Power Plant is less than or equal to number Of Power Plant");
                        return false;
                    }
                } else {
                    showToast("Select number of working Power Plant");
                    return false;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select number of Power Plant");
            return false;
        }*/
        if (numberOfPowerPlant.isEmpty() || numberOfPowerPlant == null) {
            showToast("Select number of Power Plant");
            return false;
        } else if (Integer.valueOf(numberOfPowerPlant) > 0) {
            if (numberOfWorkingPowerPlant.isEmpty() || numberOfWorkingPowerPlant == null) {
                showToast("Select number of working Power Plant");
                return false;
            } else if (Integer.valueOf(numberOfWorkingPowerPlant) > Integer.valueOf(numberOfPowerPlant)) {
                showToast("Select number of working Power Plant is less than or equal to number Of Power Plant");
                return false;
            } else if ((powerPlantDetailsDataList.size() != Integer.valueOf(numberOfPowerPlant) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;

        } else return true;

    }

    /*008 21112018*/
    public boolean checkValidationOnNoOfPowerPlant() {

        String numberOfPowerPlant = mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim();
        String numberOfWorkingPowerPlant = mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.getText().toString().trim();

        if (!numberOfPowerPlant.isEmpty() && numberOfPowerPlant != null) {
            if (Integer.valueOf(numberOfPowerPlant) > 0) {
                if (!numberOfWorkingPowerPlant.isEmpty() && numberOfWorkingPowerPlant != null) {
                    if (Integer.valueOf(numberOfWorkingPowerPlant) <= Integer.valueOf(numberOfPowerPlant)) {
                        return true;
                    } else {
                        showToast("Select number of working Power Plant is less than or equal to number Of Power Plant");
                        return false;
                    }
                } else {
                    showToast("Select number of working Power Plant");
                    return false;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select number of Power Plant");
            return false;
        }

    }

    /*008 21112018*/
    public boolean checkValidationOfArrayFields() {
        DecimalFormatConversion();
        String numberOfPowerPlant = mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim();
        String qRCodeScan = base64StringQRCodeScan;
        String numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim();
        /*String assetOwner = mPowerPlantDetailsTextViewAssetOwnerVal.getText().toString().trim();
        String manufacturerMakeModel = mPowerPlantDetailsTextViewManufacturerMakeModelVal.getText().toString().trim();
        String powerPlantModel = mPowerPlantDetailsEditTextPowerPlantModel.getText().toString().trim();
        String numberModuleSlots = mPowerPlantDetailsTextViewNumberModuleSlotsVal.getText().toString().trim();
        String earthingStatus = mPowerPlantDetailsTextViewPowerPlantEarthingStatusVal.getText().toString().trim();
        String dcLoadInDisplay = mPowerPlantDetailsEditTextDcLoadInDisplayAmp.getText().toString().trim();
        String serialNumber = mPowerPlantDetailsEditTextPowerPlantSerialNumber.getText().toString().trim();
        String typeOfPowerPlantCommercialSmps = mPowerPlantDetailsTextViewTypeOfPowerPlantCommercialSmpsVal.getText().toString().trim();
        String capacityInAmp = mPowerPlantDetailsEditTextCapacityInAmp.getText().toString().trim();
        String numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim();
        String noOfFaultyModulese = mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.getText().toString().trim();
        String smpsExpandable = mPowerPlantDetailsEditTextSmpsExpandableUpToKW.getText().toString().trim();
        String SmpsUltimateCapacity = mPowerPlantDetailsEditTextSmpsUltimateCapacity.getText().toString().trim();
        String spdStatus = mPowerPlantDetailsTextViewSpdStatusVal.getText().toString().trim();
        String workingCondition = mPowerPlantDetailsTextViewWorkingConditionVal.getText().toString().trim();
        String natureOfProblem = mPowerPlantDetailsEditTextNatureOfProblem.getText().toString().trim();*/


        if (numberOfPowerPlant.isEmpty() || numberOfPowerPlant == null) {
            showToast("Select Number Of Power Plant");
            return false;
        } else if (qRCodeScan.isEmpty() || qRCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (!numberOfModules.isEmpty()) {
            if (powerPlantDetailsModulesData.size() != Integer.valueOf(numberOfModules == "" ? "0" : numberOfModules)) {
                showToast("No of Rectifier Modules Readings should be equal to selected number of Rectifier Modules. ");
                return false;
            } else return true;
        }
        /*else if (assetOwner.isEmpty() || assetOwner == null) {
            showToast("Select Asset Owner");
            return false;
        } else if (manufacturerMakeModel.isEmpty() || manufacturerMakeModel == null) {
            showToast("Select Manufacturer/Make");
            return false;
        }*//* else if (powerPlantModel.isEmpty() || powerPlantModel == null) {
            showToast("Enter Power Plant Model"); comment by 008 no imp powerPlantModel
            return false;
        }*//* else if (numberModuleSlots.isEmpty() || numberModuleSlots == null) {
            showToast("Select Number of Module Slots");
            return false;
        } else if (earthingStatus.isEmpty() || earthingStatus == null) {
            showToast("Select Power Plant Earthing Status");
            return false;
        } else if (dcLoadInDisplay.isEmpty() || dcLoadInDisplay == null) {
            showToast("Enter Dc Load in Display(Amp)");
            return false;
        } else if (serialNumber.isEmpty() || serialNumber == null) {
            showToast("Enter Power Plant Serial Number");
            return false;
        } else if (typeOfPowerPlantCommercialSmps.isEmpty() || typeOfPowerPlantCommercialSmps == null) {
            showToast("Select Type of Power Plant[Commercial/SMPs]");
            return false;
        } else if (capacityInAmp.isEmpty() || capacityInAmp == null) {
            showToast("Enter Capacity in AMP");
            return false;
        } else if (numberOfModules.isEmpty() || numberOfModules == null) {
            showToast("Select Number of Modules");
            return false;
        } else if (noOfFaultyModulese.isEmpty() || noOfFaultyModulese == null) {
            showToast("Select No. of Faulty Modules");
            return false;
        } else if (smpsExpandable.isEmpty() || smpsExpandable == null) {
            showToast("Enter SMPS Expandable up to(KW)");
            return false;
        } else if (SmpsUltimateCapacity.isEmpty() || SmpsUltimateCapacity == null) {
            showToast("Enter SMPS Ultimate Capacity");
            return false;
        } else if (spdStatus.isEmpty() || spdStatus == null) {
            showToast("Select SPD Status");
            return false;
        } else if (workingCondition.isEmpty() || workingCondition == null) {
            showToast("Enter Working Condition");
            return false;
        } else if (natureOfProblem.isEmpty() || natureOfProblem == null) {
            showToast("Enter Nature of Problem");
            return false;
        }*/ /*else if (checkDuplicationQrCode(currentPos)) {
            return false;
        } 04022019 by 008 for new purpose*/
        else return true;/*if (checkValidationOnNoOfModuleSlots() == false) {
            return false;
        } else return checkValidationOnNoOfFaultyModulese();*/

    }


    //add 04022019 by 008 for new requirement
    private boolean checkDuplicationQrCode1() {

        //For Child Array Comparision
        if (powerPlantDetailsDataList != null) {
            for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
                for (int j = i + 1; j < powerPlantDetailsDataList.size(); j++) {
                    //compare list.get(i) and list.get(j)
                    if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsDataList.get(j).getqRCodeScan().toString())) {
                        int dup_pos = j + 1;
                        showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                        return true;
                    }
                }
            }

        }

        if (powerPlantDetailsModulesData != null) {
            for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
                for (int j = 0; j < powerPlantDetailsModulesData.size(); j++) {
                    //compare list.get(i) and list.get(j)
                    if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsModulesData.get(j).getModuleQrCodeScan().toString())) {
                        int dup_pos = j + 1;
                        showToast("QR Code scanned in reading no: " + (i + 1) + " was already scanned in Rectifier Modules reading no:" + dup_pos);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //add 04022019 by 008 for new requirement 2
    private boolean checkDuplicationQrCode() {

        //For Child Array Comparision
        if (powerPlantDetailsDataList != null) {
            for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
                for (int j = i + 1; j < powerPlantDetailsDataList.size(); j++) {
                    //compare list.get(i) and list.get(j)
                    if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsDataList.get(j).getqRCodeScan().toString())) {
                        int dup_pos = j + 1;
                        showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                        return true;
                    }
                }
            }

        }


        //if (powerPlantDetailsModulesData != null) {
        for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
            for (int k = 0; k <= i; k++) {
                PowerPlantDetailsData powerPlantDetailsData = powerPlantDetailsDataList.get(k);
                powerPlantDetailsModulesDataForDuplicity = new ArrayList<>();
                powerPlantDetailsModulesDataForDuplicity.addAll(powerPlantDetailsData.getPowerPlantDetailsModulesData());
                if (powerPlantDetailsModulesDataForDuplicity != null) {
                    for (int j = 0; j < powerPlantDetailsModulesDataForDuplicity.size(); j++) {
                        //compare list.get(i) and list.get(j)
                        if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsModulesDataForDuplicity.get(j).getModuleQrCodeScan().toString())) {
                            int dup_pos = j + 1;
                            showToast("QR Code scanned in reading no: " + (i + 1) + " was already scanned in Rectifier Modules reading no:" + dup_pos);
                            return true;
                        }
                    }
                }
            }
        }
        //}
        return false;
    }


    //add 04022019 by 008 for new requirement
    private boolean checkDuplicationQrCodeOld(int curr_pos) {
        for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
            //if (i != curr_pos) {
            if (base64StringQRCodeScan.equals(powerPlantDetailsDataList.get(i).getqRCodeScan().toString())) {
                if (i == curr_pos) {
                    return checkDuplicationQrCodeInChild();
                } else {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }
            } else if (powerPlantDetailsModulesData.size() > 0) {
                return checkDuplicationQrCodeInChild();
            }
            //}
        }
        return false;
    }

    private boolean checkDuplicationQrCodeInChild() {
        for (int j = 0; j < powerPlantDetailsModulesData.size(); j++) {
            if (base64StringQRCodeScan.equals(powerPlantDetailsModulesData.get(j).getModuleQrCodeScan().toString())) {
                int dup_pos = j + 1;
                showToast("This QR Code Already scanned in Modules Reading Number: " + dup_pos);
                return true;
            }
        }
        return false;
    }


    /*008 21112018*/
    public boolean checkValidationOnNoOfModuleSlots(String numberModuleSlots, String numberOfModules) {

        //String numberModuleSlots = mPowerPlantDetailsTextViewNumberModuleSlotsVal.getText().toString().trim();
        //String numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim();

        if (!numberModuleSlots.isEmpty() && numberModuleSlots != null) {
            if (numberModuleSlots.matches("\\d+(?:\\.\\d+)?")) {
                if (Integer.valueOf(numberModuleSlots) > 0) {
                    if (!numberOfModules.isEmpty() && numberOfModules != null) {
                        if (Integer.valueOf(numberOfModules) <= Integer.valueOf(numberModuleSlots)) {
                            return true;
                        } else {
                            showToast("Select Number of Modules is less than or equal to Module Slots");
                            return false;
                        }
                    } else {
                        showToast("Select Number of Modules");
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select Number of Module Slots");
            return false;
        }

    }

    /*008 21112018*/
    public boolean checkValidationOnNoOfFaultyModulese(String numberOfModules, String noOfFaultyModulese) {

        //String numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim();
        //String noOfFaultyModulese = mPowerPlantDetailsTextViewNoOfFaultyModuleseVal.getText().toString().trim();

        if (!numberOfModules.isEmpty() && numberOfModules != null) {
            if (numberOfModules.matches("\\d+(?:\\.\\d+)?")) {
                if (Integer.valueOf(numberOfModules) > 0) {
                    if (!noOfFaultyModulese.isEmpty() && noOfFaultyModulese != null) {
                        if (Integer.valueOf(noOfFaultyModulese) <= Integer.valueOf(numberOfModules)) {
                            return true;
                        } else {
                            showToast("Select No. of Faulty Modules is less than or equal to Number of Modules");
                            return false;
                        }
                    } else {
                        showToast("Select No. of Faulty Modules");
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select Number of Modules");
            return false;
        }

    }


    /*008 28112018*/
    public boolean checkValidationOnChangeNoOfModuleSlots(String numberModuleSlots, String numberOfModules) {
        //String numberModuleSlots = mPowerPlantDetailsTextViewNumberModuleSlotsVal.getText().toString().trim();

        if (numberModuleSlots.isEmpty() || numberModuleSlots == null) {
            showToast("Select Number of Module Slots");
            return false;
        } else return checkValidationOnNoOfModuleSlots(numberModuleSlots, numberOfModules);
    }

    /*008 28112018*/
    public boolean checkValidationOnChangeNoOfFaultyModulese(String numberOfModules, String noOfFaultyModulese) {

        //String numberOfModules = mPowerPlantDetailsTextViewNumberOfModulesVal.getText().toString().trim();

        if (numberOfModules.isEmpty() || numberOfModules == null) {
            showToast("Select Number of Modules");
            return false;
        } else return checkValidationOnNoOfFaultyModulese(numberOfModules, noOfFaultyModulese);
    }

//////////////////////
    //Camera//

    public void openCameraIntent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            //imageFileUri = Uri.fromFile(file);

            imageFileUri = FileProvider.getUriForFile(PowerPlantDetailsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClicked(View v) {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

//        Use this for more customization
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("Scan a barcode");
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setBeepEnabled(false);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_FLAG_MODULE_RESULT) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                //powerPlantDetailsModulesData = data.getBundleExtra("powerPlantDetailsModulesData");
                //powerPlantDetailsModulesData = (ArrayList<PowerPlantDetailsModulesData>)data.getExtras().getSerializable("powerPlantDetailsModulesData");
                powerPlantDetailsModulesData.clear();
                powerPlantDetailsModulesData.addAll((ArrayList<PowerPlantDetailsModulesData>) b.getSerializable("powerPlantDetailsModulesData"));
                Log.e("123", "123");
            }
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (result.getContents() == null) {
                base64StringQRCodeScan = "";
                showToast("Cancelled");
            } else {
                Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                if (!flagIsDuplicateQRcode) {
                    base64StringQRCodeScan = result.getContents();
                    if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                        mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                } else {
                    base64StringQRCodeScan = "";
                    showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                }
            }
        }


        /*if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA &&
                resultCode == RESULT_OK) {
            if (imageFileUri != null) {
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
//                            (Bitmap) data.getExtras().get("data");
//                mImageView.setImageBitmap(imageBitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] bitmapDataArray = stream.toByteArray();
                    base64StringQRCodeScan = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                    mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imageFileName = "";
                imageFileUri = null;
                mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
            }
        }*/
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(PowerPlantDetailsActivity.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(PowerPlantDetailsActivity.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(PowerPlantDetailsActivity.this);
                            }
                        })
                        .setNegativeButton("DONT ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        }).show();

    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void showAlert() {
        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(PowerPlantDetailsActivity.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(PowerPlantDetailsActivity.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(PowerPlantDetailsActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        })
                        .setNegativeButton("DONT ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .create();
                dialog.show();
            }
        }).show();


    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(PowerPlantDetailsActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }


        }
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_numberOfPowerPlant != null && !str_numberOfPowerPlant.isEmpty()) {
            if (Integer.valueOf(str_numberOfPowerPlant) > 0) {
                shareItem.setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuSubmit:
                //if (checkValidationOnNoOfPowerPlant() == true) {
                if (checkValidationOnChangeNoOfPowerPlant(mPowerPlantDetailsTextViewNumberOfPowerPlantVal.getText().toString().trim(), mPowerPlantDetailsTextViewNumberOfPowerPlantWorkingVal.getText().toString().trim(), "onSubmit") == true) {
                    submitDetails();
                    startActivity(new Intent(this, Power_Backups_DG.class));
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}