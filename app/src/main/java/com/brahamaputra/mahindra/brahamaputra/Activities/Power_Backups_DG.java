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

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerBackupsDGData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerBackupsDGParentData;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;

public class Power_Backups_DG extends BaseActivity {

    private TextView mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovided;
    private TextView mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal;
    private TextView mPowerBackupsDgTextViewNumberOfWorkingDg;
    private TextView mPowerBackupsDgTextViewNumberOfWorkingDgVal;
    private TextView mPowerBackupsDgTextViewQRCodeScan;
    private ImageView mPowerBackupsDgButtonQRCodeScan;

    private ImageView mPowerBackupsDgButtonQRCodeScanView;

    private TextView mPowerBackupsDgTextViewAssetOwner;
    private TextView mPowerBackupsDgTextViewAssetOwnerVal;
    private TextView mPowerBackupsDgTextViewDividerDesign;
    private TextView mPowerBackupsDgTextViewManufacturerMakeModel;
    private TextView mPowerBackupsDgTextViewManufacturerMakeModelVal;
    private TextView mPowerBackupsDgTextViewCapacityInKva;
    private TextView mPowerBackupsDgTextViewCapacityInKvaVal;
    private TextView mPowerBackupsDgTextViewAutoManual;
    private TextView mPowerBackupsDgTextViewAutoManualVal;
    private TextView mPowerBackupsDgTextViewDieselTankCapacity;
    private EditText mPowerBackupsDgEditTextDieselTankCapacity;
    private TextView mPowerBackupsDgTextViewDateOfInstallation;
    private EditText mPowerBackupsDgEditTextDateOfInstallation;
    private TextView mPowerBackupsDgTextViewAverageDieselConsumption;
    private EditText mPowerBackupsDgEditTextAverageDieselConsumption;
    private EditText mPowerBackupsDgEditTextBookValue;
    private TextView mPowerBackupsDgTextViewAmc;
    private TextView mPowerBackupsDgTextViewAmcVal;
    private TextView mPowerBackupsDgTextViewValidityOfAmc;
    private EditText mPowerBackupsDgEditTextDateOfvalidityOfAmc;
    private TextView mPowerBackupsDgTextViewDgWorkingType;
    private TextView mPowerBackupsDgTextViewDgWorkingTypeVal;
    private TextView mPowerBackupsDgTextViewDgHmrReading;
    private EditText mPowerBackupsDgEditTextDgHmrReading;
    private TextView mPowerBackupsDgTextViewDgEngineSerialNumber;
    private EditText mPowerBackupsDgEditTextDgEngineSerialNumber;
    private TextView mPowerBackupsDgTextViewDgMainAlternatorType;
    private TextView mPowerBackupsDgTextViewDgMainAlternatorTypeVal;
    private TextView mPowerBackupsDgTextViewDgMainAlternatorMake;
    private TextView mPowerBackupsDgTextViewDgMainAlternatorMakeVal;
    private TextView mPowerBackupsDgTextViewDgMainAlternatorSerialNumber;
    private EditText mPowerBackupsDgEditTextDgMainAlternatorSerialNumber;
    private TextView mPowerBackupsDgTextViewDgCanopyStatus;
    private TextView mPowerBackupsDgTextViewDgCanopyStatusVal;
    private TextView mPowerBackupsDgTextViewDgStartingBatteryStatus;
    private TextView mPowerBackupsDgTextViewDgStartingBatteryStatusVal;
    private TextView mPowerBackupsDgTextViewChargingAlternator;
    private TextView mPowerBackupsDgTextViewChargingAlternatorVal;
    private TextView mPowerBackupsDgTextViewBatteryCharger;
    private TextView mPowerBackupsDgTextViewBatteryChargerVal;
    private TextView mPowerBackupsDgTextViewPresentDieselStock;
    private EditText mPowerBackupsDgEditTextPresentDieselStock;
    private TextView mPowerBackupsDgTextViewGcuRunHrs;
    private EditText mPowerBackupsDgEditTextGcuRunHrs;
    private TextView mPowerBackupsDgTextViewGcuKwh;
    private EditText mPowerBackupsDgEditTextGcuKwh;
    private TextView mPowerBackupsDgTextViewDgAvrWorkingStatus;
    private TextView mPowerBackupsDgTextViewDgAvrWorkingStatusVal;
    private TextView mPowerBackupsDgTextViewFuelTankPosition;
    private TextView mPowerBackupsDgTextViewFuelTankPositionVal;
    private TextView mPowerBackupsDgTextViewWorkingCondition;
    private TextView mPowerBackupsDgTextViewWorkingConditionVal;
    private TextView mPowerBackupsDgTextViewNatureOfProblem;
    private EditText mPowerBackupsDgEditTextNatureOfProblem;

    private LinearLayout mPowerBackupsDgLinearLayoutDgBatteryStatusQRCodeScan;
    private TextView mPowerBackupsDgTextViewDgBatteryStatusQRCodeScan;
    private ImageView mPowerBackupsDgButtonDgBatteryStatusQRCodeScan;
    private ImageView mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView;

    private LinearLayout mPowerBackupsDgLinearLayoutNumberOfWorkingDg;

    private String str_noOfEngineAlternatorSetsprovided;
    private String str_numberOfWorkingDg;
    private String str_assetOwner;
    private String str_manufacturerMakeModel;
    private String str_capacityInKva;
    private String str_autoManual;
    private String str_amc;
    private String str_dgWorkingType;
    private String str_dgMainAlternatorType;
    private String str_dgMainAlternatorMake;
    private String str_DgCanopyStatus;
    private String str_dgStartingBatteryStatus;
    private String str_chargingAlternator;
    private String str_batteryCharger;
    private String str_dgAvrWorkingStatus;
    private String str_fuelTankPosition;
    private String str_workingCondition;

    private static final String TAG = Power_Backups_DG.class.getSimpleName();
    DecimalConversion decimalConversion;
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ArrayList<PowerBackupsDGData> powerBackupsDGData;
    private byte qrScanFlag = 0;
    private String base64StringQRCodeScan = "";
    private String base64StringDgBatteryStatusQRCodeScan = "";

    private SessionManager sessionManager;
    private Uri imageFileUri;
    private String imageFileName;

    final Calendar myCalendar = Calendar.getInstance();

    /////////////////////////
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public String date_flag = "no";

    private AlertDialogManager alertDialogManager;

    private PowerBackupsDGParentData powerBackupsDGParentData;
    private int totalCount = 0;
    private int currentPos = 0;
    private Button powerBackupsDg_button_previousReading;
    private Button powerBackupsDg_button_nextReading;
    private TextView powerBackupsDg_textView_Number;
    private LinearLayout linearLayout_container;
    private LinearLayout mPowerBackupsDgLinearLayoutValidityOfAmc;

    private ImageView button_ClearQRCodeScanView1;
    private ImageView button_ClearQRCodeScanView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_backups_dg);
        this.setTitle("Power Backups (DG)");
        alertDialogManager = new AlertDialogManager(Power_Backups_DG.this);
        decimalConversion = new DecimalConversion();
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();

        sessionManager = new SessionManager(Power_Backups_DG.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Power_Backups_DG.this, userId, ticketName);
        //setInputDetails();

        powerBackupsDGData = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mPowerBackupsDgButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Power_Backups_DG.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Power_Backups_DG.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Power_Backups_DG.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Power_Backups_DG.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Power_Backups_DG.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCamera();
                    //openCameraIntent();
                    qrScanFlag = 1;
                    onClicked(v);
                }

            }
        });


        mPowerBackupsDgEditTextDateOfInstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_flag = "install";
                DatePickerDialog dialog = new DatePickerDialog(Power_Backups_DG.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        mPowerBackupsDgEditTextDateOfvalidityOfAmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_flag = "valid";
                new DatePickerDialog(Power_Backups_DG.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mPowerBackupsDgEditTextDieselTankCapacity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerBackupsDgEditTextAverageDieselConsumption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerBackupsDgEditTextDgHmrReading.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerBackupsDgEditTextPresentDieselStock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerBackupsDgEditTextGcuRunHrs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPowerBackupsDgEditTextGcuKwh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

        mPowerBackupsDgButtonDgBatteryStatusQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Power_Backups_DG.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Power_Backups_DG.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Power_Backups_DG.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Power_Backups_DG.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Power_Backups_DG.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    qrScanFlag = 2;
                    onClicked(v);
                }

            }
        });

        /*mPowerBackupsDgButtonQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(Power_Backups_DG.this, imageFileUri);
                } else {
                    Toast.makeText(Power_Backups_DG.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        button_ClearQRCodeScanView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringQRCodeScan = "";
                button_ClearQRCodeScanView1.setVisibility(View.GONE);
                mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");

            }
        });

        button_ClearQRCodeScanView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDgBatteryStatusQRCodeScan = "";
                button_ClearQRCodeScanView2.setVisibility(View.GONE);
                mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");

            }
        });

    }

    public void onClicked(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(true);
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
        mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovided = (TextView) findViewById(R.id.powerBackupsDg_textView_noOfEngineAlternatorSetsprovided);
        mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal = (TextView) findViewById(R.id.powerBackupsDg_textView_noOfEngineAlternatorSetsprovided_val);
        mPowerBackupsDgTextViewNumberOfWorkingDg = (TextView) findViewById(R.id.powerBackupsDg_textView_numberOfWorkingDg);
        mPowerBackupsDgTextViewNumberOfWorkingDgVal = (TextView) findViewById(R.id.powerBackupsDg_textView_numberOfWorkingDg_val);
        mPowerBackupsDgTextViewQRCodeScan = (TextView) findViewById(R.id.powerBackupsDg_textView_QRCodeScan);
        mPowerBackupsDgButtonQRCodeScan = (ImageView) findViewById(R.id.powerBackupsDg_button_QRCodeScan);

        mPowerBackupsDgButtonQRCodeScanView = (ImageView) findViewById(R.id.powerBackupsDg_button_QRCodeScanView);

        mPowerBackupsDgTextViewAssetOwner = (TextView) findViewById(R.id.powerBackupsDg_textView_assetOwner);
        mPowerBackupsDgTextViewAssetOwnerVal = (TextView) findViewById(R.id.powerBackupsDg_textView_assetOwner_val);
        mPowerBackupsDgTextViewDividerDesign = (TextView) findViewById(R.id.powerBackupsDg_textView_dividerDesign);
        mPowerBackupsDgTextViewManufacturerMakeModel = (TextView) findViewById(R.id.powerBackupsDg_textView_manufacturerMakeModel);
        mPowerBackupsDgTextViewManufacturerMakeModelVal = (TextView) findViewById(R.id.powerBackupsDg_textView_manufacturerMakeModel_val);
        mPowerBackupsDgTextViewCapacityInKva = (TextView) findViewById(R.id.powerBackupsDg_textView_capacityInKva);
        mPowerBackupsDgTextViewCapacityInKvaVal = (TextView) findViewById(R.id.powerBackupsDg_textView_capacityInKva_val);
        mPowerBackupsDgTextViewAutoManual = (TextView) findViewById(R.id.powerBackupsDg_textView_autoManual);
        mPowerBackupsDgTextViewAutoManualVal = (TextView) findViewById(R.id.powerBackupsDg_textView_autoManual_val);
        mPowerBackupsDgTextViewDieselTankCapacity = (TextView) findViewById(R.id.powerBackupsDg_textView_dieselTankCapacity);
        mPowerBackupsDgEditTextDieselTankCapacity = (EditText) findViewById(R.id.powerBackupsDg_editText_dieselTankCapacity);
        mPowerBackupsDgTextViewDateOfInstallation = (TextView) findViewById(R.id.powerBackupsDg_textView_dateOfInstallation);
        mPowerBackupsDgEditTextDateOfInstallation = (EditText) findViewById(R.id.powerBackupsDg_editText_dateOfInstallation);
        mPowerBackupsDgTextViewAverageDieselConsumption = (TextView) findViewById(R.id.powerBackupsDg_textView_averageDieselConsumption);
        mPowerBackupsDgEditTextAverageDieselConsumption = (EditText) findViewById(R.id.powerBackupsDg_editText_averageDieselConsumption);

        mPowerBackupsDgEditTextBookValue = (EditText) findViewById(R.id.powerBackupsDg_editText_bookValue);

        mPowerBackupsDgTextViewAmc = (TextView) findViewById(R.id.powerBackupsDg_textView_amc);
        mPowerBackupsDgTextViewAmcVal = (TextView) findViewById(R.id.powerBackupsDg_textView_amc_val);
        mPowerBackupsDgTextViewValidityOfAmc = (TextView) findViewById(R.id.powerBackupsDg_textView_validityOfAmc);
        mPowerBackupsDgEditTextDateOfvalidityOfAmc = (EditText) findViewById(R.id.powerBackupsDg_editText_dateOfvalidityOfAmc);
        mPowerBackupsDgTextViewDgWorkingType = (TextView) findViewById(R.id.powerBackupsDg_textView_dgWorkingType);
        mPowerBackupsDgTextViewDgWorkingTypeVal = (TextView) findViewById(R.id.powerBackupsDg_textView_dgWorkingType_val);
        mPowerBackupsDgTextViewDgHmrReading = (TextView) findViewById(R.id.powerBackupsDg_textView_dgHmrReading);
        mPowerBackupsDgEditTextDgHmrReading = (EditText) findViewById(R.id.powerBackupsDg_editText_dgHmrReading);
        mPowerBackupsDgTextViewDgEngineSerialNumber = (TextView) findViewById(R.id.powerBackupsDg_textView_dgEngineSerialNumber);
        mPowerBackupsDgEditTextDgEngineSerialNumber = (EditText) findViewById(R.id.powerBackupsDg_editText_dgEngineSerialNumber);
        mPowerBackupsDgTextViewDgMainAlternatorType = (TextView) findViewById(R.id.powerBackupsDg_textView_dgMainAlternatorType);
        mPowerBackupsDgTextViewDgMainAlternatorTypeVal = (TextView) findViewById(R.id.powerBackupsDg_textView_dgMainAlternatorType_val);
        mPowerBackupsDgTextViewDgMainAlternatorMake = (TextView) findViewById(R.id.powerBackupsDg_textView_dgMainAlternatorMake);
        mPowerBackupsDgTextViewDgMainAlternatorMakeVal = (TextView) findViewById(R.id.powerBackupsDg_textView_dgMainAlternatorMake_val);
        mPowerBackupsDgTextViewDgMainAlternatorSerialNumber = (TextView) findViewById(R.id.powerBackupsDg_textView_dgMainAlternatorSerialNumber);
        mPowerBackupsDgEditTextDgMainAlternatorSerialNumber = (EditText) findViewById(R.id.powerBackupsDg_editText_dgMainAlternatorSerialNumber);
        mPowerBackupsDgTextViewDgCanopyStatus = (TextView) findViewById(R.id.powerBackupsDg_textView_dgCanopyStatus);
        mPowerBackupsDgTextViewDgCanopyStatusVal = (TextView) findViewById(R.id.powerBackupsDg_textView_dgCanopyStatus_val);
        mPowerBackupsDgTextViewDgStartingBatteryStatus = (TextView) findViewById(R.id.powerBackupsDg_textView_dgStartingBatteryStatus);
        mPowerBackupsDgTextViewDgStartingBatteryStatusVal = (TextView) findViewById(R.id.powerBackupsDg_textView_dgStartingBatteryStatus_val);
        mPowerBackupsDgTextViewChargingAlternator = (TextView) findViewById(R.id.powerBackupsDg_textView_chargingAlternator);
        mPowerBackupsDgTextViewChargingAlternatorVal = (TextView) findViewById(R.id.powerBackupsDg_textView_chargingAlternator_val);
        mPowerBackupsDgTextViewBatteryCharger = (TextView) findViewById(R.id.powerBackupsDg_textView_batteryCharger);
        mPowerBackupsDgTextViewBatteryChargerVal = (TextView) findViewById(R.id.powerBackupsDg_textView_batteryCharger_val);
        mPowerBackupsDgTextViewPresentDieselStock = (TextView) findViewById(R.id.powerBackupsDg_textView_presentDieselStock);
        mPowerBackupsDgEditTextPresentDieselStock = (EditText) findViewById(R.id.powerBackupsDg_editText_presentDieselStock);
        mPowerBackupsDgTextViewGcuRunHrs = (TextView) findViewById(R.id.powerBackupsDg_textView_gcuRunHrs);
        mPowerBackupsDgEditTextGcuRunHrs = (EditText) findViewById(R.id.powerBackupsDg_editText_gcuRunHrs);
        mPowerBackupsDgTextViewGcuKwh = (TextView) findViewById(R.id.powerBackupsDg_textView_gcuKwh);
        mPowerBackupsDgEditTextGcuKwh = (EditText) findViewById(R.id.powerBackupsDg_editText_gcuKwh);
        mPowerBackupsDgTextViewDgAvrWorkingStatus = (TextView) findViewById(R.id.powerBackupsDg_textView_dgAvrWorkingStatus);
        mPowerBackupsDgTextViewDgAvrWorkingStatusVal = (TextView) findViewById(R.id.powerBackupsDg_textView_dgAvrWorkingStatus_val);
        mPowerBackupsDgTextViewFuelTankPosition = (TextView) findViewById(R.id.powerBackupsDg_textView_fuelTankPosition);
        mPowerBackupsDgTextViewFuelTankPositionVal = (TextView) findViewById(R.id.powerBackupsDg_textView_fuelTankPosition_val);
        mPowerBackupsDgTextViewWorkingCondition = (TextView) findViewById(R.id.powerBackupsDg_textView_workingCondition);
        mPowerBackupsDgTextViewWorkingConditionVal = (TextView) findViewById(R.id.powerBackupsDg_textView_workingCondition_val);
        mPowerBackupsDgTextViewNatureOfProblem = (TextView) findViewById(R.id.powerBackupsDg_textView_natureOfProblem);
        mPowerBackupsDgEditTextNatureOfProblem = (EditText) findViewById(R.id.powerBackupsDg_editText_natureOfProblem);

        powerBackupsDg_button_nextReading = (Button) findViewById(R.id.powerBackupsDg_button_nextReading);
        powerBackupsDg_button_previousReading = (Button) findViewById(R.id.powerBackupsDg_button_previousReading);
        powerBackupsDg_textView_Number = (TextView) findViewById(R.id.powerBackupsDg_textView_Number);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);

        mPowerBackupsDgLinearLayoutDgBatteryStatusQRCodeScan = (LinearLayout) findViewById(R.id.powerBackupsDg_linearLayout_dgBatteryStatusQRCodeScan);
        mPowerBackupsDgTextViewDgBatteryStatusQRCodeScan = (TextView) findViewById(R.id.powerBackupsDg_textView_dgBatteryStatusQRCodeScan);
        mPowerBackupsDgButtonDgBatteryStatusQRCodeScan = (ImageView) findViewById(R.id.powerBackupsDg_button_dgBatteryStatusQRCodeScan);
        mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView = (ImageView) findViewById(R.id.powerBackupsDg_button_dgBatteryStatusQRCodeScanView);

        button_ClearQRCodeScanView1 = (ImageView) findViewById(R.id.button_ClearQRCodeScanView1);
        button_ClearQRCodeScanView2 = (ImageView) findViewById(R.id.button_ClearQRCodeScanView2);

        mPowerBackupsDgLinearLayoutNumberOfWorkingDg = (LinearLayout) findViewById(R.id.powerBackupsDg_linearLayout_numberOfWorkingDg);
        mPowerBackupsDgLinearLayoutValidityOfAmc = (LinearLayout) findViewById(R.id.powerBackupsDg_linearLayout_validityOfAmc);

        mPowerBackupsDgEditTextDieselTankCapacity.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerBackupsDgEditTextAverageDieselConsumption.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerBackupsDgEditTextDgHmrReading.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerBackupsDgEditTextPresentDieselStock.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerBackupsDgEditTextGcuRunHrs.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mPowerBackupsDgEditTextGcuKwh.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


    }

    public void DecimalFormatConversion() {
        mPowerBackupsDgEditTextDieselTankCapacity.setText(decimalConversion.convertDecimal(mPowerBackupsDgEditTextDieselTankCapacity.getText().toString()));
        mPowerBackupsDgEditTextAverageDieselConsumption.setText(decimalConversion.convertDecimal(mPowerBackupsDgEditTextAverageDieselConsumption.getText().toString()));
        mPowerBackupsDgEditTextDgHmrReading.setText(decimalConversion.convertDecimal(mPowerBackupsDgEditTextDgHmrReading.getText().toString()));
        mPowerBackupsDgEditTextPresentDieselStock.setText(decimalConversion.convertDecimal(mPowerBackupsDgEditTextPresentDieselStock.getText().toString()));
        mPowerBackupsDgEditTextGcuRunHrs.setText(decimalConversion.convertDecimal(mPowerBackupsDgEditTextGcuRunHrs.getText().toString()));
        mPowerBackupsDgEditTextGcuKwh.setText(decimalConversion.convertDecimal(mPowerBackupsDgEditTextGcuKwh.getText().toString()));
    }

    private void initCombo() {
        if (hototicket_sourceOfPower.equals("Non DG")) {
            mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setText("0");
            mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setEnabled(false);
        } else {
            mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setEnabled(true);
            mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                            new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_noOfEngineAlternatorSetsprovided))),
                            "No.of Engine Alternator Sets provided",
                            "Close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            str_noOfEngineAlternatorSetsprovided = item.get(position);
                            invalidateOptionsMenu();
                            mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setText(str_noOfEngineAlternatorSetsprovided);
                            mPowerBackupsDgTextViewNumberOfWorkingDgVal.setText("");

                            //clear AC collection empty by select / changing value of No of Ac provided
                            if (powerBackupsDGData != null && powerBackupsDGData.size() > 0) {
                                powerBackupsDGData.clear();
                            }
                            currentPos = 0;
                            totalCount = 0;
                            clearFields(currentPos);
                            totalCount = Integer.parseInt(str_noOfEngineAlternatorSetsprovided);

                            // Clear all field value and hide layout If Non AC or O //
                            if (totalCount > 0) {

                                powerBackupsDg_textView_Number.setText("Reading: #1");
                                linearLayout_container.setVisibility(View.VISIBLE);
                                mPowerBackupsDgLinearLayoutNumberOfWorkingDg.setVisibility(View.VISIBLE);//008
                                powerBackupsDg_button_previousReading.setVisibility(View.GONE);
                                powerBackupsDg_button_nextReading.setVisibility(View.VISIBLE);
                                if (totalCount > 0 && totalCount == 1) {
                                    powerBackupsDg_button_nextReading.setText("Finish");
                                } else {
                                    powerBackupsDg_button_nextReading.setText("Next Reading");
                                }
                            } else {
                                linearLayout_container.setVisibility(View.GONE);
                                mPowerBackupsDgLinearLayoutNumberOfWorkingDg.setVisibility(View.GONE);//008

                            }
                        }
                    });
                }
            });
        }


        mPowerBackupsDgTextViewNumberOfWorkingDgVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_numberOfWorkingDg))),
                        "Number of Working DG",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfWorkingDg = item.get(position);
                        mPowerBackupsDgTextViewNumberOfWorkingDgVal.setText("");
                        if (checkValidationOnChangeNoOfEngineAlternatorSelection(mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.getText().toString().trim(), str_numberOfWorkingDg, "onClick") == true) {
                            mPowerBackupsDgTextViewNumberOfWorkingDgVal.setText(str_numberOfWorkingDg);
                        }
                    }
                });
            }
        });

        mPowerBackupsDgTextViewAssetOwnerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_assetOwner))),
                        "Asset Owner",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_assetOwner = item.get(position);
                        mPowerBackupsDgTextViewAssetOwnerVal.setText(str_assetOwner);
                    }
                });
            }
        });


        mPowerBackupsDgTextViewManufacturerMakeModelVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_manufacturerMakeModel))),
                        "Manufacturer/Make/Model",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_manufacturerMakeModel = item.get(position);
                        mPowerBackupsDgTextViewManufacturerMakeModelVal.setText(str_manufacturerMakeModel);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewCapacityInKvaVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_capacityInKva))),
                        "Capacity in KVA",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_capacityInKva = item.get(position);
                        mPowerBackupsDgTextViewCapacityInKvaVal.setText(str_capacityInKva);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewAutoManualVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_autoManual))),
                        "Auto/Manual",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_autoManual = item.get(position);
                        mPowerBackupsDgTextViewAutoManualVal.setText(str_autoManual);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewAmcVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_amc))),
                        "AMC(Yes/No)",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_amc = item.get(position);
                        mPowerBackupsDgTextViewAmcVal.setText(str_amc);
                        visibilityOfValidityOfTheAMC(str_amc);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewDgWorkingTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_dgWorkingType))),
                        "DG Working Type",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dgWorkingType = item.get(position);
                        mPowerBackupsDgTextViewDgWorkingTypeVal.setText(str_dgWorkingType);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewDgMainAlternatorTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_dgMainAlternatorType))),
                        "DG Main Alternator Type",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dgMainAlternatorType = item.get(position);
                        mPowerBackupsDgTextViewDgMainAlternatorTypeVal.setText(str_dgMainAlternatorType);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewDgMainAlternatorMakeVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_dgMainAlternatorMake))),
                        "DG Main Alternator Make",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dgMainAlternatorMake = item.get(position);
                        mPowerBackupsDgTextViewDgMainAlternatorMakeVal.setText(str_dgMainAlternatorMake);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewDgCanopyStatusVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_dgCanopyStatus))),
                        "DG Canopy Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_DgCanopyStatus = item.get(position);
                        mPowerBackupsDgTextViewDgCanopyStatusVal.setText(str_DgCanopyStatus);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewDgStartingBatteryStatusVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_dgStartingBatteryStatus))),
                        "DG Starting Battery Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dgStartingBatteryStatus = item.get(position);
                        mPowerBackupsDgTextViewDgStartingBatteryStatusVal.setText(str_dgStartingBatteryStatus);
                        visibilityOfDgBatteryStatusQRCodeScan(str_dgStartingBatteryStatus);

                    }
                });
            }
        });

        mPowerBackupsDgTextViewChargingAlternatorVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_chargingAlternator))),
                        "Charging Alternator",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_chargingAlternator = item.get(position);
                        mPowerBackupsDgTextViewChargingAlternatorVal.setText(str_chargingAlternator);
                    }
                });
            }
        });


        mPowerBackupsDgTextViewBatteryChargerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_batteryCharger))),
                        "Battery Charger",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_batteryCharger = item.get(position);
                        mPowerBackupsDgTextViewBatteryChargerVal.setText(str_batteryCharger);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewDgAvrWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_dgAvrWorkingStatus))),
                        "DG AVR working Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dgAvrWorkingStatus = item.get(position);
                        mPowerBackupsDgTextViewDgAvrWorkingStatusVal.setText(str_dgAvrWorkingStatus);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewFuelTankPositionVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_fuelTankPosition))),
                        "Fuel Tank Position",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_fuelTankPosition = item.get(position);
                        mPowerBackupsDgTextViewFuelTankPositionVal.setText(str_fuelTankPosition);
                    }
                });
            }
        });

        mPowerBackupsDgTextViewWorkingConditionVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Power_Backups_DG.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerBackupsDg_workingCondition))),
                        "Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_workingCondition = item.get(position);
                        mPowerBackupsDgTextViewWorkingConditionVal.setText(str_workingCondition);
                    }
                });
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        powerBackupsDg_button_nextReading.setOnClickListener(new View.OnClickListener() {
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
                        //if (checkValidationOnNoOfEngineAlternatorSelection() == true) {
                        if (checkDuplicationQrCodeNew() == false) {//add 04022019 by 008
                            if (checkValidationOnChangeNoOfEngineAlternatorSelection(mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.getText().toString().trim(), mPowerBackupsDgTextViewNumberOfWorkingDgVal.getText().toString().trim(), "onSubmit") == true) {
                                //Save Final current reading and submit all AC data
                                //saveRecords(currentPos);
                                submitDetails();
                                startActivity(new Intent(Power_Backups_DG.this, Shelter.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

        powerBackupsDg_button_previousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkValidationOfArrayFields() == true) {//add 04022019 by 008 for new requirement*/
                if (currentPos > 0) {
                    //Save current ac reading
                    saveRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayRecords(currentPos);
                }
                /* }*/
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (date_flag.equals("install")) {
            mPowerBackupsDgEditTextDateOfInstallation.setText(sdf.format(myCalendar.getTime()));
        } else if (date_flag.equals("valid")) {
            mPowerBackupsDgEditTextDateOfvalidityOfAmc.setText(sdf.format(myCalendar.getTime()));
        } else {

        }
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

                final EditText taskEditText = new EditText(Power_Backups_DG.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Power_Backups_DG.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(Power_Backups_DG.this);
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

                final EditText taskEditText = new EditText(Power_Backups_DG.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Power_Backups_DG.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(Power_Backups_DG.this,
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

    //////////////////////
    //Camera//

    public void openCameraIntent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
//            imageFileUri = Uri.fromFile(file);

            imageFileUri = FileProvider.getUriForFile(Power_Backups_DG.this, BuildConfig.APPLICATION_ID + ".provider", file);

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imageFileName = "";
                imageFileUri = null;
                mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
            }
        }*/

        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (qrScanFlag == 1) {
                mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
                button_ClearQRCodeScanView1.setVisibility(View.GONE);
                if (result.getContents() == null) {
                    base64StringQRCodeScan = "";
                    showToast("Cancelled");
                } else {
                    Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                    boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                    if (!flagIsDuplicateQRcode) {
                        base64StringQRCodeScan = result.getContents();
                        if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                            mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.VISIBLE);
                            button_ClearQRCodeScanView1.setVisibility(View.VISIBLE);
                        }
                    } else {
                        base64StringQRCodeScan = "";
                        showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                    }
                }
            } else if (qrScanFlag == 2) {
                mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
                button_ClearQRCodeScanView2.setVisibility(View.GONE);
                if (result.getContents() == null) {
                    base64StringDgBatteryStatusQRCodeScan = "";
                    showToast("Cancelled");
                } else {
                    Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                    boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                    if (!flagIsDuplicateQRcode) {
                        base64StringDgBatteryStatusQRCodeScan = result.getContents();
                        if (!base64StringDgBatteryStatusQRCodeScan.isEmpty() && base64StringDgBatteryStatusQRCodeScan != null) {
                            mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.VISIBLE);
                            button_ClearQRCodeScanView2.setVisibility(View.VISIBLE);
                        }
                    } else {
                        base64StringDgBatteryStatusQRCodeScan = "";
                        showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                    }
                }
            }
        }
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
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
                            saveToPreferences(Power_Backups_DG.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
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
        if (str_noOfEngineAlternatorSetsprovided != null && !str_noOfEngineAlternatorSetsprovided.isEmpty()) {
            if (Integer.valueOf(str_noOfEngineAlternatorSetsprovided) > 0) {
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
                //if (checkValidationOnNoOfEngineAlternatorSelection() == true) {
                if (checkValidationOnChangeNoOfEngineAlternatorSelection(mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.getText().toString().trim(), mPowerBackupsDgTextViewNumberOfWorkingDgVal.getText().toString().trim(), "onSubmit") == true) {
                    submitDetails();
                    startActivity(new Intent(this, Shelter.class));
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                powerBackupsDGParentData = hotoTransactionData.getPowerBackupsDGParentData();
                powerBackupsDGData.addAll(powerBackupsDGParentData.getPowerBackupsDGData());

                mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setText(hototicket_sourceOfPower.equals("Non DG") ? "0" : powerBackupsDGParentData.getNoOfEngineAlternator());

                mPowerBackupsDgTextViewNumberOfWorkingDgVal.setText(powerBackupsDGParentData.getNumberOfWorkingDg());

                linearLayout_container.setVisibility(View.GONE);
                mPowerBackupsDgLinearLayoutNumberOfWorkingDg.setVisibility(View.GONE);//008
                if (powerBackupsDGData != null && powerBackupsDGData.size() > 0) {

                    linearLayout_container.setVisibility(View.VISIBLE);
                    mPowerBackupsDgLinearLayoutNumberOfWorkingDg.setVisibility(View.VISIBLE);//008
                    powerBackupsDg_textView_Number.setText("Reading: #1");
                    totalCount = Integer.parseInt(powerBackupsDGParentData.getNoOfEngineAlternator());

                    /*Comment By 008
                    mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.setText(powerBackupsDGParentData.getNoOfEngineAlternator());
                    mPowerBackupsDgTextViewNumberOfWorkingDgVal.setText(powerBackupsDGParentData.getNumberOfWorkingDg());*/

                    base64StringQRCodeScan = powerBackupsDGData.get(index).getqRCodeScan();
                    mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView1.setVisibility(View.GONE);
                    if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                        mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView1.setVisibility(View.VISIBLE);
                    }

                    visibilityOfDgBatteryStatusQRCodeScan(powerBackupsDGData.get(index).getDgStartingBatteryStatus());

                    base64StringDgBatteryStatusQRCodeScan = powerBackupsDGData.get(index).getDgBatteryStatusQRCodeScan();
                    mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView2.setVisibility(View.GONE);
                    if (!base64StringDgBatteryStatusQRCodeScan.isEmpty() && base64StringDgBatteryStatusQRCodeScan != null) {
                        mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView2.setVisibility(View.VISIBLE);
                    }
                    // New added for image #ImageSet
                    /*imageFileName = powerBackupsDGData.get(index).getQrCodeImageFileName();
                    mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
                    if (imageFileName != null && imageFileName.length() > 0) {
                        File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
//                             imageFileUri = Uri.fromFile(file);
                        imageFileUri = FileProvider.getUriForFile(Power_Backups_DG.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        if (imageFileUri != null) {
                            mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        }
                    }*/

                    mPowerBackupsDgTextViewAssetOwnerVal.setText(powerBackupsDGData.get(index).getAssetOwner());
                    mPowerBackupsDgTextViewManufacturerMakeModelVal.setText(powerBackupsDGData.get(index).getManufacturerMakeModel());
                    mPowerBackupsDgTextViewCapacityInKvaVal.setText(powerBackupsDGData.get(index).getCapacityInKva());
                    mPowerBackupsDgTextViewAutoManualVal.setText(powerBackupsDGData.get(index).getAutoManual());
                    mPowerBackupsDgEditTextDieselTankCapacity.setText(powerBackupsDGData.get(index).getDieselTankCapacity());
                    mPowerBackupsDgEditTextDateOfInstallation.setText(powerBackupsDGData.get(index).getDateOfInstallation());
                    mPowerBackupsDgEditTextAverageDieselConsumption.setText(powerBackupsDGData.get(index).getAverageDieselConsumption());

                    mPowerBackupsDgEditTextBookValue.setText(powerBackupsDGData.get(index).getBookValue());

                    mPowerBackupsDgTextViewAmcVal.setText(powerBackupsDGData.get(index).getAmc());
                    mPowerBackupsDgEditTextDateOfvalidityOfAmc.setText(powerBackupsDGData.get(index).getDateOfvalidityOfAmc());
                    mPowerBackupsDgTextViewDgWorkingTypeVal.setText(powerBackupsDGData.get(index).getDgWorkingType());
                    mPowerBackupsDgEditTextDgHmrReading.setText(powerBackupsDGData.get(index).getDgHmrReading());
                    mPowerBackupsDgEditTextDgEngineSerialNumber.setText(powerBackupsDGData.get(index).getDgEngineSerialNo());
                    mPowerBackupsDgTextViewDgMainAlternatorTypeVal.setText(powerBackupsDGData.get(index).getDgMainAltType());
                    mPowerBackupsDgTextViewDgMainAlternatorMakeVal.setText(powerBackupsDGData.get(index).getDgMainAltMake());
                    mPowerBackupsDgEditTextDgMainAlternatorSerialNumber.setText(powerBackupsDGData.get(index).getDgMainAltSerialNo());
                    mPowerBackupsDgTextViewDgCanopyStatusVal.setText(powerBackupsDGData.get(index).getDgCanopyStatus());
                    mPowerBackupsDgTextViewDgStartingBatteryStatusVal.setText(powerBackupsDGData.get(index).getDgStartingBatteryStatus());
                    mPowerBackupsDgTextViewChargingAlternatorVal.setText(powerBackupsDGData.get(index).getChargingAlternator());
                    mPowerBackupsDgTextViewBatteryChargerVal.setText(powerBackupsDGData.get(index).getBatteryCharger());
                    mPowerBackupsDgEditTextPresentDieselStock.setText(powerBackupsDGData.get(index).getPresentDieselStock());
                    mPowerBackupsDgEditTextGcuRunHrs.setText(powerBackupsDGData.get(index).getGcuRunHrs());
                    mPowerBackupsDgEditTextGcuKwh.setText(powerBackupsDGData.get(index).getGcuKwh());
                    mPowerBackupsDgTextViewDgAvrWorkingStatusVal.setText(powerBackupsDGData.get(index).getDgAvrWorkingStatus());
                    mPowerBackupsDgTextViewFuelTankPositionVal.setText(powerBackupsDGData.get(index).getFuelTankPosition());
                    mPowerBackupsDgTextViewWorkingConditionVal.setText(powerBackupsDGData.get(index).getWorkingCondition());
                    mPowerBackupsDgEditTextNatureOfProblem.setText(powerBackupsDGData.get(index).getNatureOfProblem());

                    powerBackupsDg_button_previousReading.setVisibility(View.GONE);
                    powerBackupsDg_button_nextReading.setVisibility(View.VISIBLE);

                    //if (powerBackupsDGData.size() > 1) {
                    if (totalCount > 1) {
                        powerBackupsDg_button_nextReading.setText("Next Reading");
                    } else {
                        powerBackupsDg_button_nextReading.setText("Finish");
                    }
                }

            } else {
                showToast("No previous saved data available");
                linearLayout_container.setVisibility(View.GONE);
                mPowerBackupsDgLinearLayoutNumberOfWorkingDg.setVisibility(View.GONE);//008
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            String noOfEngineAlternator = mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.getText().toString().trim();
            String numberOfWorkingDg = mPowerBackupsDgTextViewNumberOfWorkingDgVal.getText().toString().trim();

            powerBackupsDGParentData = new PowerBackupsDGParentData(noOfEngineAlternator, numberOfWorkingDg, powerBackupsDGData);
            hotoTransactionData.setPowerBackupsDGParentData(powerBackupsDGParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecords(int pos) {
        String qRCodeScan = base64StringQRCodeScan;
        //private ImageView mPowerBackupsDgButtonQRCodeScan.getText().toString().trim();
        String assetOwner = mPowerBackupsDgTextViewAssetOwnerVal.getText().toString().trim();
        String manufacturerMakeModel = mPowerBackupsDgTextViewManufacturerMakeModelVal.getText().toString().trim();
        String capacityInKva = mPowerBackupsDgTextViewCapacityInKvaVal.getText().toString().trim();
        String autoManual = mPowerBackupsDgTextViewAutoManualVal.getText().toString().trim();
        String dieselTankCapacity = mPowerBackupsDgEditTextDieselTankCapacity.getText().toString().trim();
        String dateOfInstallation = mPowerBackupsDgEditTextDateOfInstallation.getText().toString().trim();
        String averageDieselConsumption = mPowerBackupsDgEditTextAverageDieselConsumption.getText().toString().trim();
        String bookValue = mPowerBackupsDgEditTextBookValue.getText().toString().trim();
        String amc = mPowerBackupsDgTextViewAmcVal.getText().toString().trim();
        String dateOfvalidityOfAmc = mPowerBackupsDgEditTextDateOfvalidityOfAmc.getText().toString().trim();
        String dgWorkingType = mPowerBackupsDgTextViewDgWorkingTypeVal.getText().toString().trim();
        String dgHmrReading = mPowerBackupsDgEditTextDgHmrReading.getText().toString().trim();
        String dgEngineSerialNo = mPowerBackupsDgEditTextDgEngineSerialNumber.getText().toString().trim();
        String dgMainAltType = mPowerBackupsDgTextViewDgMainAlternatorTypeVal.getText().toString().trim();
        String dgMainAltMake = mPowerBackupsDgTextViewDgMainAlternatorMakeVal.getText().toString().trim();
        String dgMainAltSerialNo = mPowerBackupsDgEditTextDgMainAlternatorSerialNumber.getText().toString().trim();
        String dgCanopyStatus = mPowerBackupsDgTextViewDgCanopyStatusVal.getText().toString().trim();
        String dgStartingBatteryStatus = mPowerBackupsDgTextViewDgStartingBatteryStatusVal.getText().toString().trim();
        String dgBatteryQRCodeScan = base64StringDgBatteryStatusQRCodeScan;
        String chargingAlternator = mPowerBackupsDgTextViewChargingAlternatorVal.getText().toString().trim();
        String batteryCharger = mPowerBackupsDgTextViewBatteryChargerVal.getText().toString().trim();
        String presentDieselStock = mPowerBackupsDgEditTextPresentDieselStock.getText().toString().trim();
        String gcuRunHrs = mPowerBackupsDgEditTextGcuRunHrs.getText().toString().trim();
        String gcuKwh = mPowerBackupsDgEditTextGcuKwh.getText().toString().trim();
        String dgAvrWorkingStatus = mPowerBackupsDgTextViewDgAvrWorkingStatusVal.getText().toString().trim();
        String fuelTankPosition = mPowerBackupsDgTextViewFuelTankPositionVal.getText().toString().trim();
        String workingCondition = mPowerBackupsDgTextViewWorkingConditionVal.getText().toString().trim();
        String natureOfProblem = mPowerBackupsDgEditTextNatureOfProblem.getText().toString().trim();

        String imageFileName = "";


        PowerBackupsDGData obj_powerBackupsDGData = new PowerBackupsDGData(qRCodeScan, bookValue, assetOwner, manufacturerMakeModel, capacityInKva, autoManual, dieselTankCapacity, dateOfInstallation, averageDieselConsumption, amc, dateOfvalidityOfAmc, dgWorkingType, dgHmrReading, dgEngineSerialNo, dgMainAltType, dgMainAltMake, dgMainAltSerialNo, dgCanopyStatus, dgStartingBatteryStatus, dgBatteryQRCodeScan, chargingAlternator, batteryCharger, presentDieselStock, gcuRunHrs, gcuKwh, dgAvrWorkingStatus, fuelTankPosition, workingCondition, natureOfProblem, imageFileName);
        if (powerBackupsDGData.size() > 0) {
            if (pos == powerBackupsDGData.size()) {
                powerBackupsDGData.add(obj_powerBackupsDGData);
            } else if (pos < powerBackupsDGData.size()) {
                powerBackupsDGData.set(pos, obj_powerBackupsDGData);
            }
        } else {
            powerBackupsDGData.add(obj_powerBackupsDGData);
        }
    }

    private void displayRecords(int pos) {
        if (powerBackupsDGData.size() > 0 && pos < powerBackupsDGData.size()) {

            powerBackupsDg_textView_Number.setText("Reading: #" + (pos + 1));

            base64StringQRCodeScan = powerBackupsDGData.get(pos).getqRCodeScan();
            mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView1.setVisibility(View.GONE);
            if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.VISIBLE);
                button_ClearQRCodeScanView1.setVisibility(View.VISIBLE);
            }

            visibilityOfDgBatteryStatusQRCodeScan(powerBackupsDGData.get(pos).getDgStartingBatteryStatus());

            base64StringDgBatteryStatusQRCodeScan = powerBackupsDGData.get(pos).getDgBatteryStatusQRCodeScan();
            mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView2.setVisibility(View.GONE);
            if (!base64StringDgBatteryStatusQRCodeScan.isEmpty() && base64StringDgBatteryStatusQRCodeScan != null) {
                mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.VISIBLE);
                button_ClearQRCodeScanView2.setVisibility(View.VISIBLE);
            }

            mPowerBackupsDgTextViewAssetOwnerVal.setText(powerBackupsDGData.get(pos).getAssetOwner());
            mPowerBackupsDgTextViewManufacturerMakeModelVal.setText(powerBackupsDGData.get(pos).getManufacturerMakeModel());
            mPowerBackupsDgTextViewCapacityInKvaVal.setText(powerBackupsDGData.get(pos).getCapacityInKva());
            mPowerBackupsDgTextViewAutoManualVal.setText(powerBackupsDGData.get(pos).getAutoManual());
            mPowerBackupsDgEditTextDieselTankCapacity.setText(powerBackupsDGData.get(pos).getDieselTankCapacity());
            mPowerBackupsDgEditTextDateOfInstallation.setText(powerBackupsDGData.get(pos).getDateOfInstallation());
            mPowerBackupsDgEditTextAverageDieselConsumption.setText(powerBackupsDGData.get(pos).getAverageDieselConsumption());
            mPowerBackupsDgEditTextBookValue.setText(powerBackupsDGData.get(pos).getBookValue());
            mPowerBackupsDgTextViewAmcVal.setText(powerBackupsDGData.get(pos).getAmc());
            mPowerBackupsDgEditTextDateOfvalidityOfAmc.setText(powerBackupsDGData.get(pos).getDateOfvalidityOfAmc());
            mPowerBackupsDgTextViewDgWorkingTypeVal.setText(powerBackupsDGData.get(pos).getDgWorkingType());
            mPowerBackupsDgEditTextDgHmrReading.setText(powerBackupsDGData.get(pos).getDgHmrReading());
            mPowerBackupsDgEditTextDgEngineSerialNumber.setText(powerBackupsDGData.get(pos).getDgEngineSerialNo());
            mPowerBackupsDgTextViewDgMainAlternatorTypeVal.setText(powerBackupsDGData.get(pos).getDgMainAltType());
            mPowerBackupsDgTextViewDgMainAlternatorMakeVal.setText(powerBackupsDGData.get(pos).getDgMainAltMake());
            mPowerBackupsDgEditTextDgMainAlternatorSerialNumber.setText(powerBackupsDGData.get(pos).getDgMainAltSerialNo());
            mPowerBackupsDgTextViewDgCanopyStatusVal.setText(powerBackupsDGData.get(pos).getDgCanopyStatus());
            mPowerBackupsDgTextViewDgStartingBatteryStatusVal.setText(powerBackupsDGData.get(pos).getDgStartingBatteryStatus());
            mPowerBackupsDgTextViewChargingAlternatorVal.setText(powerBackupsDGData.get(pos).getChargingAlternator());
            mPowerBackupsDgTextViewBatteryChargerVal.setText(powerBackupsDGData.get(pos).getBatteryCharger());
            mPowerBackupsDgEditTextPresentDieselStock.setText(powerBackupsDGData.get(pos).getPresentDieselStock());
            mPowerBackupsDgEditTextGcuRunHrs.setText(powerBackupsDGData.get(pos).getGcuRunHrs());
            mPowerBackupsDgEditTextGcuKwh.setText(powerBackupsDGData.get(pos).getGcuKwh());
            mPowerBackupsDgTextViewDgAvrWorkingStatusVal.setText(powerBackupsDGData.get(pos).getDgAvrWorkingStatus());
            mPowerBackupsDgTextViewFuelTankPositionVal.setText(powerBackupsDGData.get(pos).getFuelTankPosition());
            mPowerBackupsDgTextViewWorkingConditionVal.setText(powerBackupsDGData.get(pos).getWorkingCondition());
            mPowerBackupsDgEditTextNatureOfProblem.setText(powerBackupsDGData.get(pos).getNatureOfProblem());

            powerBackupsDg_button_previousReading.setVisibility(View.VISIBLE);
            powerBackupsDg_button_nextReading.setVisibility(View.VISIBLE);
        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            powerBackupsDg_button_previousReading.setVisibility(View.VISIBLE);
            powerBackupsDg_button_nextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            powerBackupsDg_button_previousReading.setVisibility(View.VISIBLE);
            powerBackupsDg_button_nextReading.setText("Finish");
        } else if (pos == 0) {
            powerBackupsDg_button_previousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                powerBackupsDg_button_nextReading.setText("Finish");
            } else {
                powerBackupsDg_button_nextReading.setText("Next Reading");
            }
        }

    }

    public void clearFields(int indexPos) {

        powerBackupsDg_textView_Number.setText("Reading: #" + (indexPos + 1));
        //mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
        //button_ClearQRCodeScanView1.setVisibility(View.GONE);

        mPowerBackupsDgTextViewAssetOwnerVal.setText("");
        mPowerBackupsDgTextViewManufacturerMakeModelVal.setText("");
        mPowerBackupsDgTextViewCapacityInKvaVal.setText("");
        mPowerBackupsDgTextViewAutoManualVal.setText("");
        mPowerBackupsDgEditTextDieselTankCapacity.setText("");
        mPowerBackupsDgEditTextDateOfInstallation.setText("");
        mPowerBackupsDgEditTextAverageDieselConsumption.setText("");
        mPowerBackupsDgEditTextBookValue.setText("");
        mPowerBackupsDgTextViewAmcVal.setText("");
        mPowerBackupsDgEditTextDateOfvalidityOfAmc.setText("");
        mPowerBackupsDgTextViewDgWorkingTypeVal.setText("");
        mPowerBackupsDgEditTextDgHmrReading.setText("");
        mPowerBackupsDgEditTextDgEngineSerialNumber.setText("");
        mPowerBackupsDgTextViewDgMainAlternatorTypeVal.setText("");
        mPowerBackupsDgTextViewDgMainAlternatorMakeVal.setText("");
        mPowerBackupsDgEditTextDgMainAlternatorSerialNumber.setText("");
        mPowerBackupsDgTextViewDgCanopyStatusVal.setText("");
        mPowerBackupsDgTextViewDgStartingBatteryStatusVal.setText("");
        mPowerBackupsDgTextViewChargingAlternatorVal.setText("");
        mPowerBackupsDgTextViewBatteryChargerVal.setText("");
        mPowerBackupsDgEditTextPresentDieselStock.setText("");
        mPowerBackupsDgEditTextGcuRunHrs.setText("");
        mPowerBackupsDgEditTextGcuKwh.setText("");
        mPowerBackupsDgTextViewDgAvrWorkingStatusVal.setText("");
        mPowerBackupsDgTextViewFuelTankPositionVal.setText("");
        mPowerBackupsDgTextViewWorkingConditionVal.setText("");
        mPowerBackupsDgEditTextNatureOfProblem.setText("");


        str_assetOwner = "";
        str_manufacturerMakeModel = "";
        str_capacityInKva = "";
        str_autoManual = "";
        str_amc = "";
        str_dgWorkingType = "";
        str_dgMainAlternatorType = "";
        str_dgMainAlternatorMake = "";
        str_DgCanopyStatus = "";
        str_dgStartingBatteryStatus = "";
        str_chargingAlternator = "";
        str_batteryCharger = "";
        str_dgAvrWorkingStatus = "";
        str_fuelTankPosition = "";
        str_workingCondition = "";
        base64StringQRCodeScan = "";
        base64StringDgBatteryStatusQRCodeScan = "";

        mPowerBackupsDgLinearLayoutDgBatteryStatusQRCodeScan.setVisibility(View.GONE);
        //mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
        //button_ClearQRCodeScanView2.setVisibility(View.GONE);

        mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
        button_ClearQRCodeScanView1.setVisibility(View.GONE);
        if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
            mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.VISIBLE);
            button_ClearQRCodeScanView1.setVisibility(View.VISIBLE);
        } /*else {
            mPowerBackupsDgButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView1.setVisibility(View.GONE);
        }*/

        mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
        button_ClearQRCodeScanView2.setVisibility(View.GONE);
        if (!base64StringDgBatteryStatusQRCodeScan.isEmpty() && base64StringDgBatteryStatusQRCodeScan != null) {
            mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.VISIBLE);
            button_ClearQRCodeScanView2.setVisibility(View.VISIBLE);
        } /*else {
            mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView2.setVisibility(View.GONE);
        }*/
    }

    /*008 21112018*/
    public boolean checkValidationOnChangeNoOfEngineAlternatorSelection(String noOfEngineAlternator, String numberOfWorkingDg, String methodFlag) {
        //String noOfEngineAlternator = mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.getText().toString().trim();
        //String numberOfWorkingDg = mPowerBackupsDgTextViewNumberOfWorkingDgVal.getText().toString().trim();
        /*if (!noOfEngineAlternator.isEmpty() && noOfEngineAlternator != null) {
            if (Integer.valueOf(noOfEngineAlternator) > 0) {
                if (!numberOfWorkingDg.isEmpty() && numberOfWorkingDg != null) {
                    if (Integer.valueOf(numberOfWorkingDg) <= Integer.valueOf(noOfEngineAlternator)) {
                        return true;
                    } else {
                        showToast("Select Number of working DG is less than or equal to Number of Engine Alternator sets provided");
                        return false;
                    }
                } else {
                    showToast("Select Number of working DG");
                    return false;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select Number of Engine Alternator sets provided");
            return false;
        }*/


        if (noOfEngineAlternator.isEmpty() || noOfEngineAlternator == null) {
            showToast("Select Number of Engine Alternator sets provided");
            return false;
        } else if (Integer.valueOf(noOfEngineAlternator) > 0) {
            if (numberOfWorkingDg.isEmpty() || numberOfWorkingDg == null) {
                showToast("Select Number of working DG");
                return false;
            } else if (Integer.valueOf(numberOfWorkingDg) > Integer.valueOf(noOfEngineAlternator)) {
                showToast("Select Number of working DG is less than or equal to Number of Engine Alternator sets provided");
                return false;
            } else if ((powerBackupsDGData.size() != Integer.valueOf(noOfEngineAlternator) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;


    }

    /*008 21112018*/
    public boolean checkValidationOnNoOfEngineAlternatorSelection() {
        String noOfEngineAlternator = mPowerBackupsDgTextViewNoOfEngineAlternatorSetsprovidedVal.getText().toString().trim();
        String numberOfWorkingDg = mPowerBackupsDgTextViewNumberOfWorkingDgVal.getText().toString().trim();

        if (!noOfEngineAlternator.isEmpty() && noOfEngineAlternator != null) {
            if (Integer.valueOf(noOfEngineAlternator) > 0) {
                if (!numberOfWorkingDg.isEmpty() && numberOfWorkingDg != null) {
                    if (Integer.valueOf(numberOfWorkingDg) <= Integer.valueOf(noOfEngineAlternator)) {
                        return true;
                    } else {
                        showToast("Select Number of working DG is less than or equal to Number of Engine Alternator sets provided");
                        return false;
                    }
                } else {
                    showToast("Select Number of working DG");
                    return false;
                }
            } else {
                return true;
            }
        } else {
            showToast("Select Number of Engine Alternator sets provided");
            return false;
        }

    }

    /*008 21112018*/
    public boolean checkValidationOfArrayFields() {
        DecimalFormatConversion();
        String qRCodeScan = base64StringQRCodeScan;
        String dgqRCodeScan = base64StringDgBatteryStatusQRCodeScan;
        String dgStartingBatteryStatus = mPowerBackupsDgTextViewDgStartingBatteryStatusVal.getText().toString().trim();
        /*String assetOwner = mPowerBackupsDgTextViewAssetOwnerVal.getText().toString().trim();
        String manufacturerMakeModel = mPowerBackupsDgTextViewManufacturerMakeModelVal.getText().toString().trim();
        String capacityInKva = mPowerBackupsDgTextViewCapacityInKvaVal.getText().toString().trim();
        String autoManual = mPowerBackupsDgTextViewAutoManualVal.getText().toString().trim();
        String dieselTankCapacity = mPowerBackupsDgEditTextDieselTankCapacity.getText().toString().trim();
        String dateOfInstallation = mPowerBackupsDgEditTextDateOfInstallation.getText().toString().trim();
        String averageDieselConsumption = mPowerBackupsDgEditTextAverageDieselConsumption.getText().toString().trim();
        String amcYesNo = mPowerBackupsDgTextViewAmcVal.getText().toString().trim();
        String dateOfvalidityOfAmc = mPowerBackupsDgEditTextDateOfvalidityOfAmc.getText().toString().trim();
        String dgWorkingType = mPowerBackupsDgTextViewDgWorkingTypeVal.getText().toString().trim();
        String dgHmrReading = mPowerBackupsDgEditTextDgHmrReading.getText().toString().trim();
        String dgEngineSerialNo = mPowerBackupsDgEditTextDgEngineSerialNumber.getText().toString().trim();
        String dgMainAltType = mPowerBackupsDgTextViewDgMainAlternatorTypeVal.getText().toString().trim();
        String dgMainAltMake = mPowerBackupsDgTextViewDgMainAlternatorMakeVal.getText().toString().trim();
        String dgMainAltSerialNo = mPowerBackupsDgEditTextDgMainAlternatorSerialNumber.getText().toString().trim();
        String dgCanopyStatus = mPowerBackupsDgTextViewDgCanopyStatusVal.getText().toString().trim();
        String dgStartingBatteryStatus = mPowerBackupsDgTextViewDgStartingBatteryStatusVal.getText().toString().trim();
        String chargingAlternator = mPowerBackupsDgTextViewChargingAlternatorVal.getText().toString().trim();
        String batteryCharger = mPowerBackupsDgTextViewBatteryChargerVal.getText().toString().trim();
        String presentDieselStock = mPowerBackupsDgEditTextPresentDieselStock.getText().toString().trim();
        String gcuRunHrs = mPowerBackupsDgEditTextGcuRunHrs.getText().toString().trim();
        String gcuKwh = mPowerBackupsDgEditTextGcuKwh.getText().toString().trim();
        String dgAvrWorkingStatus = mPowerBackupsDgTextViewDgAvrWorkingStatusVal.getText().toString().trim();
        String fuelTankPosition = mPowerBackupsDgTextViewFuelTankPositionVal.getText().toString().trim();
        String workingCondition = mPowerBackupsDgTextViewWorkingConditionVal.getText().toString().trim();
        String natureOfProblem = mPowerBackupsDgEditTextNatureOfProblem.getText().toString().trim();*/


        if (qRCodeScan.isEmpty() || qRCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } /*else if (assetOwner.isEmpty() || assetOwner == null) {
            showToast("Select Asset Owner");
            return false;
        } else if (manufacturerMakeModel.isEmpty() || manufacturerMakeModel == null) {
            showToast("Select Manufacturer/Make/Model");
            return false;
        } else if (capacityInKva.isEmpty() || capacityInKva == null) {
            showToast("Select Capacity in KVA");
            return false;
        } else if (autoManual.isEmpty() || autoManual == null) {
            showToast("Select Auto/Manual");
            return false;
        } else if (dieselTankCapacity.isEmpty() || dieselTankCapacity == null) {
            showToast("Enter Diesel Tank Capacity");
            return false;
        } else if (dateOfInstallation.isEmpty() || dateOfInstallation == null) {
            showToast("Select Date of Installation");
            return false;
        } else if (averageDieselConsumption.isEmpty() || averageDieselConsumption == null) {
            showToast("Enter Average Diesel Consumption per hr.");
            return false;
        } else if (amcYesNo.isEmpty() || amcYesNo == null) {
            showToast("Select AMC");
            return false;
        } else if ((dateOfvalidityOfAmc.isEmpty() || dateOfvalidityOfAmc == null) && amcYesNo.equals("Yes")) {
            showToast("Select Validity of the AMC");
            return false;
        } else if (dgWorkingType.isEmpty() || dgWorkingType == null) {
            showToast("Select DG Working Type");
            return false;
        } else if (dgHmrReading.isEmpty() || dgHmrReading == null) {
            showToast("Enter DG HMR Reading");
            return false;
        } else if (dgEngineSerialNo.isEmpty() || dgEngineSerialNo == null) {
            showToast("Enter DG Engine Serial Number");
            return false;
        } else if ((dgMainAltType.isEmpty() || dgMainAltType == null)) {
            showToast("Select DG Main Alternator Type");
            return false;
        } else if ((dgMainAltMake.isEmpty() || dgMainAltMake == null)) {
            showToast("Select DG Main Alternator Make");
            return false;
        } else if ((dgMainAltSerialNo.isEmpty() || dgMainAltSerialNo == null)) {
            showToast("Enter DG Main Alternator Serial Number");
            return false;
        } else if ((dgCanopyStatus.isEmpty() || dgCanopyStatus == null)) {
            showToast("Select DG Canopy Status");
            return false;
        } else if ((dgStartingBatteryStatus.isEmpty() || dgStartingBatteryStatus == null)) {
            showToast("Select DG Starting Battery Status");
            return false;
        } else if ((chargingAlternator.isEmpty() || dateOfvalidityOfAmc == null)) {
            showToast("Select Charging Alternator");
            return false;
        } else if ((batteryCharger.isEmpty() || dateOfvalidityOfAmc == null)) {
            showToast("Select Battery Charger");
            return false;
        } else if ((presentDieselStock.isEmpty() || presentDieselStock == null)) {
            showToast("Enter Present Diesel Stock");
            return false;
        } else if ((gcuRunHrs.isEmpty() || gcuRunHrs == null)) {
            showToast("Enter GCU Run Hrs");
            return false;
        } else if ((gcuKwh.isEmpty() || gcuKwh == null)) {
            showToast("Enter GCU KWH");
            return false;
        } else if ((dgAvrWorkingStatus.isEmpty() || dgAvrWorkingStatus == null)) {
            showToast("Select DG AVR Working Status");
            return false;
        } else if ((fuelTankPosition.isEmpty() || fuelTankPosition == null)) {
            showToast("Select Fuel Tank Position");
            return false;
        } else if (workingCondition.isEmpty() || workingCondition == null) {
            showToast("Select Working Condition");
            return false;
        }*/ else if ((dgqRCodeScan.isEmpty() || dgqRCodeScan == null) && dgStartingBatteryStatus.equals("Yes")) {
            showToast("Please Scan DG Starting Battery QR Code ");
            return false;
        } /*else if (checkDuplicationQrCode(currentPos)) {
            return false;
        }comment 04022019 by 008 for new requirement*/ else return true;

    }

    //add 04022019 by 008 for new requirement
    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < powerBackupsDGData.size(); i++) {
            if (powerBackupsDGData.get(i).getqRCodeScan().toString().equals(powerBackupsDGData.get(i).getDgBatteryStatusQRCodeScan().toString())) {
                showToast("QR Code Scanned in field DG Starting Battery was already scanned in DG QR Code in Reading No: " + (i + 1));
                return true;
            }
            for (int j = i + 1; j < powerBackupsDGData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (powerBackupsDGData.get(i).getqRCodeScan().toString().equals(powerBackupsDGData.get(j).getqRCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                } else if (powerBackupsDGData.get(i).getqRCodeScan().toString().equals(powerBackupsDGData.get(j).getDgBatteryStatusQRCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    //add 04022019 by 008 for new requirement
    private boolean checkDuplicationQrCodeOld(int curr_pos) {
        for (int i = 0; i < powerBackupsDGData.size(); i++) {
            if (i != curr_pos) {
                if (base64StringQRCodeScan.equals(powerBackupsDGData.get(i).getqRCodeScan().toString())) {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }

                if (base64StringQRCodeScan.equals(powerBackupsDGData.get(i).getDgBatteryStatusQRCodeScan().toString())) {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }

                if (base64StringDgBatteryStatusQRCodeScan.equals(powerBackupsDGData.get(i).getqRCodeScan().toString())) {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }
                if (!base64StringDgBatteryStatusQRCodeScan.isEmpty()) {
                    if (base64StringDgBatteryStatusQRCodeScan.equals(powerBackupsDGData.get(i).getDgBatteryStatusQRCodeScan().toString())) {
                        int dup_pos = i + 1;
                        showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                        return true;
                    }
                }
            }
            if (i == curr_pos) {
                if (base64StringQRCodeScan.equals(base64StringDgBatteryStatusQRCodeScan)) {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned in that Reading " + dup_pos);
                    return true;
                }
            }
        }
        if (base64StringQRCodeScan.equals(base64StringDgBatteryStatusQRCodeScan)) {
            showToast("This QR Code Already scanned in that Reading ");
            return true;
        }
        return false;
    }

    /*008 21112018*/
    private void visibilityOfValidityOfTheAMC(String amcYesNo) {
        if (amcYesNo.equals("Yes")) {
            mPowerBackupsDgLinearLayoutValidityOfAmc.setVisibility(View.VISIBLE);
        } else {
            mPowerBackupsDgLinearLayoutValidityOfAmc.setVisibility(View.GONE);
            mPowerBackupsDgEditTextDateOfvalidityOfAmc.setText("");

        }
    }

    private void visibilityOfDgBatteryStatusQRCodeScan(String dgBatteryStatus) {
        base64StringDgBatteryStatusQRCodeScan = "";
        mPowerBackupsDgButtonDgBatteryStatusQRCodeScanView.setVisibility(View.GONE);
        mPowerBackupsDgLinearLayoutDgBatteryStatusQRCodeScan.setVisibility(View.GONE);
        if (dgBatteryStatus.equals("Yes")) {
            mPowerBackupsDgLinearLayoutDgBatteryStatusQRCodeScan.setVisibility(View.VISIBLE);
        }
    }
}
