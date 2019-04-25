package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.AirConditionParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.AirConditionersData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Air_Conditioners extends BaseActivity {

    private static final String TAG = Air_Conditioners.class.getSimpleName();

    private TextView mAirConditionersTextViewNoOfAirConditionersACprovided;
    private TextView mAirConditionersTextViewNoOfAirConditionersACprovidedVal;
    private TextView mAirConditionersTextViewNumberOfACInWorkingCondition;
    private TextView mAirConditionersTextViewNumberOfACInWorkingConditionVal;
    private TextView mAirConditionersTextViewQRCodeScan;
    private ImageView mAirConditionersButtonQRCodeScan;
    private ImageView mAirConditionersButtonQRCodeScanView;
    private TextView mAirConditionersTextViewAssetOwner;
    private TextView mAirConditionersTextViewAssetOwnerVal;
    private TextView mAirConditionersTextViewTypeOfAcSpliWindow;
    private TextView mAirConditionersTextViewTypeOfAcSpliWindowVal;
    private TextView mAirConditionersTextViewManufacturerMakeModel;
    //private EditText mAirConditionersEditTextManufacturerMakeModel;
    private TextView mAirConditionersTextViewManufacturerMakeModelVal;
    private TextView mAirConditionersTextViewAcSerialNumber;
    private EditText mAirConditionersEditTextAcSerialNumber;
    private TextView mAirConditionersTextViewCapacityTr;
    private EditText mAirConditionersEditTextCapacityTr;
    private TextView mAirConditionersTextViewDateOfInstallation;
    private EditText mAirConditionersEditTextDateOfInstallation;
    private EditText mAirConditionersEditTextBookValue;
    private TextView mAirConditionersTextViewAmcYesNo;
    private TextView mAirConditionersTextViewAmcYesNoVal;
    private TextView mAirConditionersTextViewValidityOfAmc;
    private EditText mAirConditionersEditTextDateOfvalidityOfAmc;
    private TextView mAirConditionersTextViewWorkingCondition;
    private TextView mAirConditionersTextViewWorkingConditionVal;
    private TextView mAirConditionersTextViewNatureOfProblem;
    private EditText mAirConditionersEditTextNatureOfProblem;
    private ImageView button_ClearQRCodeScanView;

    private Button airCondition_button_previousReading;
    private Button airCondition_button_nextReading;
    //private Button airCondition_button_save;
    private TextView airConditioners_textView_AcNumber;
    private LinearLayout linearLayout_container;
    private LinearLayout mAirConditionersLinearLayoutNumberOfACInWorkingCondition;
    private LinearLayout mAirConditionersLinearLayoutValidityOfAmc;

    private String str_noOfAirConditionersACprovided;
    private String str_numberOfACInWorkingCondition;
    private String str_sssetOwner;
    private String str_typeOfAcSpliWindow;
    private String str_manufacturerMakeModel;
    private String str_amcYesNo;
    private String str_workingCondition;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ArrayList<AirConditionersData> airConditionersData;
    //private AirConditionersData airConditionersDataTemp;
    private String base64StringQRCodeScan = "";
    private SessionManager sessionManager;
    private Uri imageFileUri;
    private String imageFileName;
    private DecimalConversion decimalConversion;
    final Calendar myCalendar = Calendar.getInstance();

    /////////////////////////
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public String date_flag = "no";

    private AlertDialogManager alertDialogManager;
    private AirConditionParentData dataList;

    private int totalAcCount = 0;
    private int currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_conditioners);

        this.setTitle("Air Conditioners");
        alertDialogManager = new AlertDialogManager(Air_Conditioners.this);

        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
        decimalConversion = new DecimalConversion();
        sessionManager = new SessionManager(Air_Conditioners.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Air_Conditioners.this, userId, ticketName);

        //dataList = new ArrayList<>();
        airConditionersData = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mAirConditionersButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Air_Conditioners.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Air_Conditioners.this, ALLOW_KEY)) {
                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Air_Conditioners.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Air_Conditioners.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Air_Conditioners.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCamera();
                    //openCameraIntent();This Commented by 008 On 15-12-2018 For QR Code Scan Purpose
                    onClicked(v);
                }

            }
        });

        mAirConditionersEditTextDateOfInstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_flag = "install";
                DatePickerDialog dialog = new DatePickerDialog(Air_Conditioners.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        mAirConditionersEditTextDateOfvalidityOfAmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_flag = "valid";
                new DatePickerDialog(Air_Conditioners.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*This Commented by 008 On 15-12-2018 For QR Code Scan Purpose
        mAirConditionersButtonQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(Air_Conditioners.this, imageFileUri);
                } else {
                    Toast.makeText(Air_Conditioners.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        mAirConditionersEditTextCapacityTr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        button_ClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringQRCodeScan = "";
                button_ClearQRCodeScanView.setVisibility(View.GONE);
                mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");

            }
        });

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

    private void assignViews() {
        mAirConditionersTextViewNoOfAirConditionersACprovided = (TextView) findViewById(R.id.airConditioners_textView_noOfAirConditionersACprovided);
        mAirConditionersTextViewNoOfAirConditionersACprovidedVal = (TextView) findViewById(R.id.airConditioners_textView_noOfAirConditionersACprovided_val);
        mAirConditionersTextViewNumberOfACInWorkingCondition = (TextView) findViewById(R.id.airConditioners_textView_numberOfACInWorkingCondition);
        mAirConditionersTextViewNumberOfACInWorkingConditionVal = (TextView) findViewById(R.id.airConditioners_textView_numberOfACInWorkingCondition_val);
        mAirConditionersTextViewQRCodeScan = (TextView) findViewById(R.id.airConditioners_textView_QRCodeScan);

        mAirConditionersButtonQRCodeScan = (ImageView) findViewById(R.id.airConditioners_button_QRCodeScan);
        mAirConditionersButtonQRCodeScanView = (ImageView) findViewById(R.id.airConditioners_button_QRCodeScanView);

        mAirConditionersTextViewAssetOwner = (TextView) findViewById(R.id.airConditioners_textView_assetOwner);
        mAirConditionersTextViewAssetOwnerVal = (TextView) findViewById(R.id.airConditioners_textView_assetOwner_val);
        mAirConditionersTextViewTypeOfAcSpliWindow = (TextView) findViewById(R.id.airConditioners_textView_typeOfAcSpliWindow);
        mAirConditionersTextViewTypeOfAcSpliWindowVal = (TextView) findViewById(R.id.airConditioners_textView_typeOfAcSpliWindow_val);
        mAirConditionersTextViewManufacturerMakeModel = (TextView) findViewById(R.id.airConditioners_textView_manufacturerMakeModel);
        //mAirConditionersEditTextManufacturerMakeModel = (EditText) findViewById(R.id.airConditioners_editText_manufacturerMakeModel);
        mAirConditionersTextViewManufacturerMakeModelVal = (TextView) findViewById(R.id.airConditioners_textView_manufacturerMakeModel_val);
        mAirConditionersTextViewAcSerialNumber = (TextView) findViewById(R.id.airConditioners_textView_acSerialNumber);
        mAirConditionersEditTextAcSerialNumber = (EditText) findViewById(R.id.airConditioners_editText_acSerialNumber);
        mAirConditionersTextViewCapacityTr = (TextView) findViewById(R.id.airConditioners_textView_capacityTr);
        mAirConditionersEditTextCapacityTr = (EditText) findViewById(R.id.airConditioners_editText_capacityTr);
        mAirConditionersTextViewDateOfInstallation = (TextView) findViewById(R.id.airConditioners_textView_dateOfInstallation);
        mAirConditionersEditTextDateOfInstallation = (EditText) findViewById(R.id.airConditioners_editText_dateOfInstallation);
        mAirConditionersEditTextBookValue = (EditText) findViewById(R.id.airConditioners_editText_bookValue);
        mAirConditionersTextViewAmcYesNo = (TextView) findViewById(R.id.airConditioners_textView_amcYesNo);
        mAirConditionersTextViewAmcYesNoVal = (TextView) findViewById(R.id.airConditioners_textView_amcYesNo_val);
        mAirConditionersTextViewValidityOfAmc = (TextView) findViewById(R.id.airConditioners_textView_validityOfAmc);
        mAirConditionersEditTextDateOfvalidityOfAmc = (EditText) findViewById(R.id.airConditioners_editText_dateOfvalidityOfAmc);
        mAirConditionersTextViewWorkingCondition = (TextView) findViewById(R.id.airConditioners_textView_workingCondition);
        mAirConditionersTextViewWorkingConditionVal = (TextView) findViewById(R.id.airConditioners_textView_workingCondition_val);
        mAirConditionersTextViewNatureOfProblem = (TextView) findViewById(R.id.airConditioners_textView_natureOfProblem);
        mAirConditionersEditTextNatureOfProblem = (EditText) findViewById(R.id.airConditioners_editText_natureOfProblem);
        button_ClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);

        airCondition_button_nextReading = (Button) findViewById(R.id.airCondition_button_nextReading);
        airCondition_button_previousReading = (Button) findViewById(R.id.airCondition_button_previousReading);
        //airCondition_button_save = (Button) findViewById(R.id.airCondition_button_save);
        airConditioners_textView_AcNumber = (TextView) findViewById(R.id.airConditioners_textView_AcNumber);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);

        mAirConditionersLinearLayoutNumberOfACInWorkingCondition = (LinearLayout) findViewById(R.id.airConditioners_linearLayout_numberOfACInWorkingCondition);
        mAirConditionersLinearLayoutValidityOfAmc = (LinearLayout) findViewById(R.id.airConditioners_linearLayout_validityOfAmc);
        mAirConditionersEditTextCapacityTr.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void DecimalFormatConversion() {
        mAirConditionersEditTextCapacityTr.setText(decimalConversion.convertDecimal(mAirConditionersEditTextCapacityTr.getText().toString()));
    }

    private void initCombo() {
        mAirConditionersTextViewNoOfAirConditionersACprovidedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_noOfAirConditionersACprovided))),
                        "No.of Air Conditioners (AC) provided",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfAirConditionersACprovided = item.get(position);
                        invalidateOptionsMenu();
                        mAirConditionersTextViewNoOfAirConditionersACprovidedVal.setText(str_noOfAirConditionersACprovided);
                        mAirConditionersTextViewNumberOfACInWorkingConditionVal.setText("");//008
                        //checkValidationOnNoOfAcSelection();//008

                        //clear AC collection empty by select / changing value of No of Ac provided
                        if (airConditionersData != null && airConditionersData.size() > 0) {
                            airConditionersData.clear();
                        }
                        currentPos = 0;
                        totalAcCount = 0;
                        clearFields(currentPos);

                        // Clear all field value and hide layout If Non AC or O //
                        if (str_noOfAirConditionersACprovided.equals("Non AC") || str_noOfAirConditionersACprovided.equals("0")) {
                            linearLayout_container.setVisibility(View.GONE);
                            mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                        } else {
                            totalAcCount = Integer.parseInt(str_noOfAirConditionersACprovided);
                            airConditioners_textView_AcNumber.setText("Reading: #1");
                            linearLayout_container.setVisibility(View.VISIBLE);
                            mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                            airCondition_button_previousReading.setVisibility(View.GONE);
                            airCondition_button_nextReading.setVisibility(View.VISIBLE);
                            if (totalAcCount > 0 && totalAcCount == 1) {
                                airCondition_button_nextReading.setText("Finish");
                            } else {
                                airCondition_button_nextReading.setText("Next Reading");
                            }
                        }

                    }
                });
            }
        });

        mAirConditionersTextViewNumberOfACInWorkingConditionVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_numberofACInWorkingCondition))),
                        "Number of AC in Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfACInWorkingCondition = item.get(position);
                        mAirConditionersTextViewNumberOfACInWorkingConditionVal.setText("");
                        if (checkValidationOnChangeNoOfAcSelection(mAirConditionersTextViewNoOfAirConditionersACprovidedVal.getText().toString().trim(), str_numberOfACInWorkingCondition, "onClick") == true) {
                            mAirConditionersTextViewNumberOfACInWorkingConditionVal.setText(str_numberOfACInWorkingCondition);
                        }
                    }
                });
            }
        });

        mAirConditionersTextViewAssetOwnerVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_assetOwner))),
                        "Asset Owner",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_sssetOwner = item.get(position);
                        mAirConditionersTextViewAssetOwnerVal.setText(str_sssetOwner);
                    }
                });
            }
        });

        mAirConditionersTextViewTypeOfAcSpliWindowVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_typeOfAcSpliWindow))),
                        "Type of AC (Split/Window)",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfAcSpliWindow = item.get(position);
                        mAirConditionersTextViewTypeOfAcSpliWindowVal.setText(str_typeOfAcSpliWindow);
                    }
                });
            }
        });

        mAirConditionersTextViewManufacturerMakeModelVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_manufacturerMakeModel))),
                        "Manufacturer/Make/Model",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_manufacturerMakeModel = item.get(position);
                        mAirConditionersTextViewManufacturerMakeModelVal.setText(str_manufacturerMakeModel);
                    }
                });
            }
        });

        mAirConditionersTextViewAmcYesNoVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_amc))),
                        "AMC (Yes / No)",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_amcYesNo = item.get(position);
                        mAirConditionersTextViewAmcYesNoVal.setText(str_amcYesNo);
                        visibilityOfValidityOfTheAMC(str_amcYesNo);
                    }
                });
            }
        });


        mAirConditionersTextViewWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Air_Conditioners.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_airConditioners_workingCondition))),
                        "Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_workingCondition = item.get(position);
                        mAirConditionersTextViewWorkingConditionVal.setText(str_workingCondition);
                    }
                });
            }
        });

        airCondition_button_previousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkValidationOfArrayFields() == true) {comment 04022019 by 008*/
                if (currentPos > 0) {
                    //Save current ac reading
                    saveACRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayACRecords(currentPos);
                }
                /*}*/
            }
        });
        airCondition_button_nextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalAcCount - 1)) {
                        //Save current ac reading
                        saveACRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayACRecords(currentPos);

                    } else if (currentPos == (totalAcCount - 1)) {

                        saveACRecords(currentPos);
                        //if (checkValidationOnNoOfAcSelection() == true) {
                        if (checkDuplicationQrCodeNew() == false) {//add 04022019 by 008
                            if (checkValidationOnChangeNoOfAcSelection(mAirConditionersTextViewNoOfAirConditionersACprovidedVal.getText().toString().trim(), mAirConditionersTextViewNumberOfACInWorkingConditionVal.getText().toString().trim(), "onSubmit") == true) {
                                //Save Final current reading and submit all AC data
                                //saveACRecords(currentPos);
                                submitDetails();
                                startActivity(new Intent(Air_Conditioners.this, Solar_Power_System.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

    }

    private void updateLabel() {
        try {
            String myFormat = "dd/MMM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            if (date_flag.equals("install")) {
                mAirConditionersEditTextDateOfInstallation.setText(sdf.format(myCalendar.getTime()));
            } else if (date_flag.equals("valid")) {
                mAirConditionersEditTextDateOfvalidityOfAmc.setText(sdf.format(myCalendar.getTime()));
            } else {

            }
        } catch (Exception ex) {

        }
    }

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                dataList = hotoTransactionData.getAirConditionParentData();//setAirConditionersData(hotoTransactionData.getAirConditionParentData().getAirConditionersData());
                airConditionersData.addAll(dataList.getAirConditionersData());

                mAirConditionersTextViewNoOfAirConditionersACprovidedVal.setText(dataList.getNoOfACprovided());

                mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                if (!dataList.getNumberOfACInWorkingCondition().isEmpty() && dataList.getNumberOfACInWorkingCondition() != null) {
                    mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                }
                mAirConditionersTextViewNumberOfACInWorkingConditionVal.setText(dataList.getNumberOfACInWorkingCondition());

                if (airConditionersData != null && airConditionersData.size() > 0) {
                    linearLayout_container.setVisibility(View.VISIBLE);
                    airConditioners_textView_AcNumber.setText("Reading: #1");
                    totalAcCount = Integer.parseInt(dataList.getNoOfACprovided());
                    /*commented by 008 on 21-11-2018
                    mAirConditionersTextViewNoOfAirConditionersACprovidedVal.setText(dataList.getNoOfACprovided());
                    mAirConditionersTextViewNumberOfACInWorkingConditionVal.setText(dataList.getNumberOfACInWorkingCondition());*/
                    //mAirConditionersButtonQRCodeScan;
                    base64StringQRCodeScan = airConditionersData.get(index).getqRCodeScan();
                    mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView.setVisibility(View.GONE);

                    if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                        mAirConditionersButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    mAirConditionersTextViewAssetOwnerVal.setText(airConditionersData.get(index).getAssetOwner());
                    mAirConditionersTextViewTypeOfAcSpliWindowVal.setText(airConditionersData.get(index).getTypeOfAcSplitWindow());
                    //mAirConditionersEditTextManufacturerMakeModel.setText(airConditionersData.get(index).getManufacturerMakeModel());
                    mAirConditionersTextViewManufacturerMakeModelVal.setText(airConditionersData.get(index).getManufacturerMakeModel());

                    mAirConditionersEditTextAcSerialNumber.setText(airConditionersData.get(index).getAcSerialNumber());
                    mAirConditionersEditTextCapacityTr.setText(airConditionersData.get(index).getCapacityTr());
                    mAirConditionersEditTextDateOfInstallation.setText(airConditionersData.get(index).getDateOfInstallation());
                    mAirConditionersEditTextBookValue.setText(airConditionersData.get(index).getBookValue());

                    mAirConditionersTextViewAmcYesNoVal.setText(airConditionersData.get(index).getAmcYesNo());
                    visibilityOfValidityOfTheAMC(airConditionersData.get(index).getAmcYesNo());//008
                    mAirConditionersEditTextDateOfvalidityOfAmc.setText(airConditionersData.get(index).getDateOfvalidityOfAmc());
                    mAirConditionersTextViewWorkingConditionVal.setText(airConditionersData.get(index).getWorkingCondition());
                    mAirConditionersEditTextNatureOfProblem.setText(airConditionersData.get(index).getNatureOfProblem());

                    airCondition_button_previousReading.setVisibility(View.GONE);
                    airCondition_button_nextReading.setVisibility(View.VISIBLE);

                    //if (airConditionersData.size() > 1) {
                    if (totalAcCount > 1) {
                        airCondition_button_nextReading.setText("Next Reading");
                    } else {
                        airCondition_button_nextReading.setText("Finish");
                    }

                }

            } else {
                showToast("No previous saved data available");
                linearLayout_container.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveACRecords(int pos) {

        String qRCodeScan = base64StringQRCodeScan;//mAirConditionersButtonQRCodeScan.getText().toString().trim();
        String assetOwner = mAirConditionersTextViewAssetOwnerVal.getText().toString().trim();
        String typeOfAcSplitWindow = mAirConditionersTextViewTypeOfAcSpliWindowVal.getText().toString().trim();
        //String manufacturerMakeModel = mAirConditionersEditTextManufacturerMakeModel.getText().toString().trim();
        String manufacturerMakeModel = mAirConditionersTextViewManufacturerMakeModelVal.getText().toString().trim();
        String acSerialNumber = mAirConditionersEditTextAcSerialNumber.getText().toString().trim();
        String capacityTr = mAirConditionersEditTextCapacityTr.getText().toString().trim();
        String bookValue = mAirConditionersEditTextBookValue.getText().toString().trim();
        String dateOfInstallation = mAirConditionersEditTextDateOfInstallation.getText().toString().trim();
        String amcYesNo = mAirConditionersTextViewAmcYesNoVal.getText().toString().trim();
        String dateOfvalidityOfAmc = mAirConditionersEditTextDateOfvalidityOfAmc.getText().toString().trim();
        String workingCondition = mAirConditionersTextViewWorkingConditionVal.getText().toString().trim();
        String natureOfProblem = mAirConditionersEditTextNatureOfProblem.getText().toString().trim();


        //airConditionersData = new ArrayList<>();
        AirConditionersData air_conditioners = new AirConditionersData(qRCodeScan, assetOwner, typeOfAcSplitWindow, manufacturerMakeModel, acSerialNumber, capacityTr, bookValue, dateOfInstallation, amcYesNo, dateOfvalidityOfAmc, workingCondition, natureOfProblem, imageFileName);
        //airConditionersData.add(air_conditioners);

        if (airConditionersData.size() > 0) {
            if (pos == airConditionersData.size()) {
                airConditionersData.add(air_conditioners);
            } else if (pos < airConditionersData.size()) {
                airConditionersData.set(pos, air_conditioners);
            }
        } else {
            airConditionersData.add(air_conditioners);
        }
    }

    private void displayACRecords(int pos) {

        if (airConditionersData.size() > 0 && pos < airConditionersData.size()) {

            airConditioners_textView_AcNumber.setText("Reading: #" + (pos + 1));

            base64StringQRCodeScan = airConditionersData.get(pos).getqRCodeScan();
            mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                mAirConditionersButtonQRCodeScanView.setVisibility(View.VISIBLE);
                button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            mAirConditionersTextViewAssetOwnerVal.setText(airConditionersData.get(pos).getAssetOwner());
            mAirConditionersTextViewTypeOfAcSpliWindowVal.setText(airConditionersData.get(pos).getTypeOfAcSplitWindow());
            //mAirConditionersEditTextManufacturerMakeModel.setText(airConditionersData.get(pos).getManufacturerMakeModel());
            mAirConditionersTextViewManufacturerMakeModelVal.setText(airConditionersData.get(pos).getManufacturerMakeModel());
            mAirConditionersEditTextAcSerialNumber.setText(airConditionersData.get(pos).getAcSerialNumber());
            mAirConditionersEditTextCapacityTr.setText(airConditionersData.get(pos).getCapacityTr());
            mAirConditionersEditTextBookValue.setText(airConditionersData.get(pos).getBookValue());
            mAirConditionersEditTextDateOfInstallation.setText(airConditionersData.get(pos).getDateOfInstallation());
            mAirConditionersTextViewAmcYesNoVal.setText(airConditionersData.get(pos).getAmcYesNo());
            mAirConditionersEditTextDateOfvalidityOfAmc.setText(airConditionersData.get(pos).getDateOfvalidityOfAmc());
            mAirConditionersTextViewWorkingConditionVal.setText(airConditionersData.get(pos).getWorkingCondition());
            mAirConditionersEditTextNatureOfProblem.setText(airConditionersData.get(pos).getNatureOfProblem());

            airCondition_button_previousReading.setVisibility(View.VISIBLE);
            airCondition_button_nextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalAcCount - 1)) {
            airCondition_button_previousReading.setVisibility(View.VISIBLE);
            airCondition_button_nextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalAcCount - 1)) {
            airCondition_button_previousReading.setVisibility(View.VISIBLE);
            airCondition_button_nextReading.setText("Finish");
        } else if (pos == 0) {
            airCondition_button_previousReading.setVisibility(View.GONE);
            if (pos == (totalAcCount - 1)) {
                airCondition_button_nextReading.setText("Finish");
            } else {
                airCondition_button_nextReading.setText("Next Reading");
            }
        }

    }

    private void submitDetails() {
        try {
            String noOfACprovided = mAirConditionersTextViewNoOfAirConditionersACprovidedVal.getText().toString().trim();
            String numberOfACInWorkingCondition = mAirConditionersTextViewNumberOfACInWorkingConditionVal.getText().toString().trim();
            dataList = new AirConditionParentData(noOfACprovided, numberOfACInWorkingCondition, airConditionersData);
            //airConditionersDataList.setAirConditionersDataList(dataList);

            hotoTransactionData.setAirConditionParentData(dataList);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        airConditioners_textView_AcNumber.setText("Reading: #" + (indexPos + 1));

        mAirConditionersTextViewAssetOwnerVal.setText("");
        mAirConditionersTextViewTypeOfAcSpliWindowVal.setText("");
        //mAirConditionersEditTextManufacturerMakeModel.setText("");
        mAirConditionersTextViewManufacturerMakeModelVal.setText("");
        mAirConditionersEditTextAcSerialNumber.setText("");
        mAirConditionersEditTextCapacityTr.setText("");
        mAirConditionersEditTextBookValue.setText("");
        mAirConditionersEditTextDateOfInstallation.setText("");
        mAirConditionersTextViewAmcYesNoVal.setText("");
        mAirConditionersEditTextDateOfvalidityOfAmc.setText("");
        mAirConditionersTextViewWorkingConditionVal.setText("");
        mAirConditionersEditTextNatureOfProblem.setText("");

        str_sssetOwner = "";
        str_typeOfAcSpliWindow = "";
        str_amcYesNo = "";
        str_workingCondition = "";
        base64StringQRCodeScan = "";

        mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
        button_ClearQRCodeScanView.setVisibility(View.GONE);
        /*if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
            mAirConditionersButtonQRCodeScanView.setVisibility(View.VISIBLE);
            button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
        } else {
            mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_noOfAirConditionersACprovided != null && !str_noOfAirConditionersACprovided.isEmpty()) {
            if (Integer.valueOf(str_noOfAirConditionersACprovided) > 0) {
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
                return true;
            case R.id.menuSubmit:

                str_noOfAirConditionersACprovided = mAirConditionersTextViewNoOfAirConditionersACprovidedVal.getText().toString();

                if (str_noOfAirConditionersACprovided == null || str_noOfAirConditionersACprovided.equals("")) {
                    showToast("Please select no of ac");
                } else {
                    // if (checkValidationOnNoOfAcSelection() == true) {
                    if (checkValidationOnChangeNoOfAcSelection(mAirConditionersTextViewNoOfAirConditionersACprovidedVal.getText().toString().trim(), mAirConditionersTextViewNumberOfACInWorkingConditionVal.getText().toString().trim(), "onSubmit") == true) {
                        submitDetails();
                        startActivity(new Intent(this, Solar_Power_System.class));
                        finish();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*008 28112018*/
    public boolean checkValidationOnChangeNoOfAcSelection(String noOfACprovided, String numberOfACInWorkingCondition, String methodFlag) {

        /*if (!noOfACprovided.isEmpty() && noOfACprovided != null) {
            if (noOfACprovided.matches("\\d+(?:\\.\\d+)?")) {
                if (Integer.valueOf(noOfACprovided) > 0) {
                    if (!numberOfACInWorkingCondition.isEmpty() && numberOfACInWorkingCondition != null) {
                        if (Integer.valueOf(numberOfACInWorkingCondition) <= Integer.valueOf(noOfACprovided)) {
                            return true;
                        } else {
                            showToast("Select AC in working condition is less than or equal to Air Conditioners(AC) provided");
                            return false;
                        }
                    } else {
                        showToast("Select Number of AC in working condition");
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select No of Air Conditioners(AC) provided");
            return false;
        }*/


        if (noOfACprovided.isEmpty() || noOfACprovided == null) {
            showToast("Select No of Air Conditioners(AC) provided");
            return false;
        } else if (Integer.valueOf(noOfACprovided) > 0) {
            if (numberOfACInWorkingCondition.isEmpty() || numberOfACInWorkingCondition == null) {
                showToast("Select Number of AC in working condition");
                return false;
            } else if (Integer.valueOf(numberOfACInWorkingCondition) > Integer.valueOf(noOfACprovided)) {
                showToast("Select AC in working condition is less than or equal to Air Conditioners(AC) provided");
                return false;
            } else if ((airConditionersData.size() != Integer.valueOf(noOfACprovided) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");//as a mentioned AC in no of AC provided
                return false;
            } else return true;
        } else return true;

    }

    /*008 21112018*/
    public boolean checkValidationOnNoOfAcSelection() {
        String noOfACprovided = mAirConditionersTextViewNoOfAirConditionersACprovidedVal.getText().toString().trim();
        String numberOfACInWorkingCondition = mAirConditionersTextViewNumberOfACInWorkingConditionVal.getText().toString().trim().equals("Non AC") ? "0" : mAirConditionersTextViewNumberOfACInWorkingConditionVal.getText().toString().trim();

        if (!noOfACprovided.isEmpty() && noOfACprovided != null) {
            if (noOfACprovided.matches("\\d+(?:\\.\\d+)?")) {
                if (Integer.valueOf(noOfACprovided) > 0) {
                    if (!numberOfACInWorkingCondition.isEmpty() && numberOfACInWorkingCondition != null) {
                        if (Integer.valueOf(numberOfACInWorkingCondition) <= Integer.valueOf(noOfACprovided)) {
                            return true;
                        } else {
                            showToast("Select AC in working condition is less than or equal to Air Conditioners(AC) provided");
                            return false;
                        }
                    } else {
                        showToast("Select Number of AC in working condition");
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select No of Air Conditioners(AC) provided");
            return false;
        }

    }

    /*008 21112018*/
    public boolean checkValidationOfArrayFields() {
        DecimalFormatConversion();
        String qRCodeScan = base64StringQRCodeScan;//mAirConditionersButtonQRCodeScan.getText().toString().trim();
        /*String assetOwner = mAirConditionersTextViewAssetOwnerVal.getText().toString().trim();
        String typeOfAcSplitWindow = mAirConditionersTextViewTypeOfAcSpliWindowVal.getText().toString().trim();
        String manufacturerMakeModel = mAirConditionersTextViewManufacturerMakeModelVal.getText().toString().trim();
        String acSerialNumber = mAirConditionersEditTextAcSerialNumber.getText().toString().trim();
        String capacityTr = mAirConditionersEditTextCapacityTr.getText().toString().trim();
        String dateOfInstallation = mAirConditionersEditTextDateOfInstallation.getText().toString().trim();
        String amcYesNo = mAirConditionersTextViewAmcYesNoVal.getText().toString().trim();
        String dateOfvalidityOfAmc = mAirConditionersEditTextDateOfvalidityOfAmc.getText().toString().trim();
        String workingCondition = mAirConditionersTextViewWorkingConditionVal.getText().toString().trim();
        String natureOfProblem = mAirConditionersEditTextNatureOfProblem.getText().toString().trim();*/


        if (qRCodeScan.isEmpty() || qRCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        }/* else if (assetOwner.isEmpty() || assetOwner == null) {
            showToast("Select Asset Owner");
            return false;
        } else if (typeOfAcSplitWindow.isEmpty() || typeOfAcSplitWindow == null) {
            showToast("Select Type of AC");
            return false;
        } else if (manufacturerMakeModel.isEmpty() || manufacturerMakeModel == null) {
            showToast("Select Manufacturer/Make/Model");
            return false;
        } else if (acSerialNumber.isEmpty() || acSerialNumber == null) {
            showToast("Enter AC Serial Number");
            return false;
        } else if (capacityTr.isEmpty() || capacityTr == null) {
            showToast("Enter Capacity(TR)");
            return false;
        } else if (dateOfInstallation.isEmpty() || dateOfInstallation == null) {
            showToast("Select Date of Installation");
            return false;
        } else if (amcYesNo.isEmpty() || amcYesNo == null) {
            showToast("Select AMC");
            return false;
        } else if ((dateOfvalidityOfAmc.isEmpty() || dateOfvalidityOfAmc == null) && amcYesNo.equals("Yes")) {
            showToast("Select Validity of the AMC");
            return false;
        } else if (workingCondition.isEmpty() || workingCondition == null) {
            showToast("Enter Working Condition");
            return false;
        } else if (natureOfProblem.isEmpty() || natureOfProblem == null) {
            showToast("Enter Nature of Problem");
            return false;
        }*//*04022019 else if (checkDuplicationQrCode(currentPos)) {
            return false;
        }*/ else return true;

    }

    //add 04022019 by 008 for new requirement
    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < airConditionersData.size(); i++) {
            for (int j = i + 1; j < airConditionersData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (airConditionersData.get(i).getqRCodeScan().toString().equals(airConditionersData.get(j).getqRCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    //comment 04022019 by 008 for new requirement
    private boolean checkDuplicationQrCodeOld(int curr_pos) {
        for (int i = 0; i < airConditionersData.size(); i++) {
            if (i != curr_pos) {
                if (base64StringQRCodeScan.equals(airConditionersData.get(i).getqRCodeScan().toString())) {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }
            }
        }
        return false;
    }

    /*008 21112018*/
    private void visibilityOfValidityOfTheAMC(String amcYesNo) {
        if (amcYesNo.equals("Yes")) {
            mAirConditionersLinearLayoutValidityOfAmc.setVisibility(View.VISIBLE);
        } else {
            mAirConditionersLinearLayoutValidityOfAmc.setVisibility(View.GONE);
            mAirConditionersEditTextDateOfvalidityOfAmc.setText("");

        }
    }

    /*008 21112018*/
    private boolean validityOfValidityOfTheAMC(String amcYesNo) {
        String dateOfvalidityOfAmc = mAirConditionersEditTextDateOfvalidityOfAmc.getText().toString().trim();
        if (amcYesNo.equals("Yes")) {
            if (!dateOfvalidityOfAmc.isEmpty() && dateOfvalidityOfAmc != null) {
                return true;
            }
            {
                showToast("Select Validity of the AMC");
                return false;
            }
        } else {
            return true;
        }
    }

    //////////////////////
    //Camera//

    public void openCameraIntent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            //imageFileUri = Uri.fromFile(file);

            imageFileUri = FileProvider.getUriForFile(Air_Conditioners.this, BuildConfig.APPLICATION_ID + ".provider", file);

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);

            if (result.getContents() == null) {
                base64StringQRCodeScan = "";
                showToast("Cancelled");
            } else {
                Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                //showToast("Val:\n"+isDuplicateQRcode[0]+"\n"+isDuplicateQRcode[1]);
                if (!flagIsDuplicateQRcode) {
                    base64StringQRCodeScan = result.getContents();
                    if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                        mAirConditionersButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                } else {
                    base64StringQRCodeScan = "";
                    showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                }

            }
        }
        /*This Commented by 008 On 15-12-2018 For QR Code Scan Purpose
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA &&
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
                    mAirConditionersButtonQRCodeScanView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imageFileName = "";
                imageFileUri = null;
                mAirConditionersButtonQRCodeScanView.setVisibility(View.GONE);
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

                final EditText taskEditText = new EditText(Air_Conditioners.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Air_Conditioners.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(Air_Conditioners.this);
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

                final EditText taskEditText = new EditText(Air_Conditioners.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Air_Conditioners.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(Air_Conditioners.this,
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
                            saveToPreferences(Air_Conditioners.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

}
