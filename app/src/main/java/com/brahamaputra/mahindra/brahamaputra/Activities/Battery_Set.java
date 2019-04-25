package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import android.Manifest;
import android.widget.TimePicker;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.BatterySetData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatterySetParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.R;
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

public class Battery_Set extends BaseActivity {

    private static final String TAG = Battery_Set.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private Uri imageFileUri = null;
    private String imageFileName = "";

    private AlertDialogManager alertDialogManager;

    final Calendar myCalendar = Calendar.getInstance();
    private String str_noofBatterySetProvided;
    private String str_numberofBatteryBankWorking;
    private String str_assetOwner;
    private String str_manufacturerMakeModel;
    private String str_capacityinAH;
    private String str_typeofBattery;
    private String str_positionofBatteryBank;
    private String str_batteryBankCableSizeinSQMM;
    private String str_batteryBankEarthingStatus;
    private String str_backupCondition;


    private TextView mBatterySetTextViewNoofBatterySetProvided;
    private TextView mBatterySetTextViewNoofBatterySetProvidedVal;
    private TextView mBatterySetTextViewNumberofBatteryBankWorking;
    private TextView mBatterySetTextViewNumberofBatteryBankWorkingVal;
    private TextView mBatterySetTextViewQRCodeScan;
    private ImageView mBatterySetButtonQRCodeScan;
    private ImageView mBatterySetButtonQRCodeScanView;
    private TextView mBatterySetTextViewAssetOwner;
    private TextView mBatterySetTextViewAssetOwnerVal;
    private TextView mPowerBackupsDgTextViewDividerDesign;
    private TextView mBatterySetTextViewManufacturerMakeModel;
    private TextView mBatterySetTextViewManufacturerMakeModelVal;
    private TextView mBatterySetTextViewCapacityinAH;
    private TextView mBatterySetTextViewCapacityinAHVal;
    private TextView mBatterySetTextViewTypeofBattery;
    private TextView mBatterySetTextViewTypeofBatteryVal;
    private TextView mBatterySetTextViewDateofInstallation;
    private EditText mBatterySetEditTextDateofInstallation;
    private EditText mBatterySetEditTextbookValue;
    private TextView mBatterySetTextViewBackupduration;
    private EditText mBatterySetEditTextBackupduration;
    private TextView mBatterySetTextViewPositionofBatteryBank;
    private TextView mBatterySetTextViewPositionofBatteryBankVal;
    private TextView mBatterySetTextViewBatteryBankCableSizeinSQMM;
    private TextView mBatterySetTextViewBatteryBankCableSizeinSQMMVal;
    private TextView mBatterySetTextViewBatteryBankEarthingStatus;
    private TextView mBatterySetTextViewBatteryBankEarthingStatusVal;
    private TextView mBatterySetTextViewBACKUPCondition;
    private TextView mBatterySetTextViewBACKUPConditionVal;
    private TextView mBatterySetTextViewNatureofProblem;
    private EditText mBatterySetEditTextNatureofProblem;
    private ImageView button_ClearQRCodeScanView;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ArrayList<BatterySetData> batterySetData;
    private String base64StringBatterySet = "";

    private BatterySetParentData batterySetParentData;

    private SessionManager sessionManager;

    private int totalCount = 0;
    private int currentPos = 0;
    private Button batterySet_button_previousReading;
    private Button batterySet_button_nextReading;
    private TextView batterySet_textView_Number;
    private LinearLayout linearLayout_container;
    private LinearLayout batterySetLinearLayoutNumberOfBatteryBankWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_set);
        this.setTitle("Battery Set");

        sessionManager = new SessionManager(Battery_Set.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Battery_Set.this, userId, ticketName);
        alertDialogManager = new AlertDialogManager(this);

        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        batterySetData = new ArrayList<>();
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

        mBatterySetEditTextDateofInstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(Battery_Set.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        mBatterySetButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Battery_Set.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Battery_Set.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Battery_Set.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Battery_Set.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Battery_Set.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCameraIntent();
                    onClicked(v);
                }

            }
        });

        mBatterySetEditTextBackupduration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Battery_Set.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHour1 = (selectedHour >= 10) ? Integer.toString(selectedHour) : String.format("0%s", Integer.toString(selectedHour));
                        String selectedMinute1 = (selectedMinute >= 10) ? Integer.toString(selectedMinute) : String.format("0%s", Integer.toString(selectedMinute));
                        mBatterySetEditTextBackupduration.setText(selectedHour1 + ":" + selectedMinute1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time For Scheduled Power Cut");
                mTimePicker.show();

            }
        });

    }

    private void assignViews() {
        mBatterySetTextViewNoofBatterySetProvided = (TextView) findViewById(R.id.batterySet_textView_NoofBatterySetProvided);
        mBatterySetTextViewNoofBatterySetProvidedVal = (TextView) findViewById(R.id.batterySet_textView_NoofBatterySetProvided_val);
        mBatterySetTextViewNumberofBatteryBankWorking = (TextView) findViewById(R.id.batterySet_textView_NumberofBatteryBankWorking);
        mBatterySetTextViewNumberofBatteryBankWorkingVal = (TextView) findViewById(R.id.batterySet_textView_NumberofBatteryBankWorking_val);
        mBatterySetTextViewQRCodeScan = (TextView) findViewById(R.id.batterySet_textView_QRCodeScan);
        mBatterySetButtonQRCodeScan = (ImageView) findViewById(R.id.batterySet_button_QRCodeScan);
        mBatterySetButtonQRCodeScanView = (ImageView) findViewById(R.id.batterySet_button_QRCodeScanView);
        mBatterySetTextViewAssetOwner = (TextView) findViewById(R.id.batterySet_textView_assetOwner);
        mBatterySetTextViewAssetOwnerVal = (TextView) findViewById(R.id.batterySet_textView_assetOwner_val);
        mPowerBackupsDgTextViewDividerDesign = (TextView) findViewById(R.id.powerBackupsDg_textView_dividerDesign);
        mBatterySetTextViewManufacturerMakeModel = (TextView) findViewById(R.id.batterySet_textView_ManufacturerMakeModel);
        mBatterySetTextViewManufacturerMakeModelVal = (TextView) findViewById(R.id.batterySet_textView_ManufacturerMakeModel_val);
        mBatterySetTextViewCapacityinAH = (TextView) findViewById(R.id.batterySet_textView_CapacityinAH);
        mBatterySetTextViewCapacityinAHVal = (TextView) findViewById(R.id.batterySet_textView_CapacityinAH_val);
        mBatterySetTextViewTypeofBattery = (TextView) findViewById(R.id.batterySet_textView_TypeofBattery);
        mBatterySetTextViewTypeofBatteryVal = (TextView) findViewById(R.id.batterySet_textView_TypeofBattery_val);
        mBatterySetTextViewDateofInstallation = (TextView) findViewById(R.id.batterySet_textView_DateofInstallation);
        mBatterySetEditTextDateofInstallation = (EditText) findViewById(R.id.batterySet_editText_DateofInstallation);

        mBatterySetEditTextbookValue = (EditText) findViewById(R.id.batterySet_editText_bookValue);
        mBatterySetTextViewBackupduration = (TextView) findViewById(R.id.batterySet_textView_Backupduration);
        mBatterySetEditTextBackupduration = (EditText) findViewById(R.id.batterySet_editText_Backupduration);
        mBatterySetTextViewPositionofBatteryBank = (TextView) findViewById(R.id.batterySet_textView_PositionofBatteryBank);
        mBatterySetTextViewPositionofBatteryBankVal = (TextView) findViewById(R.id.batterySet_textView_PositionofBatteryBank_val);
        mBatterySetTextViewBatteryBankCableSizeinSQMM = (TextView) findViewById(R.id.batterySet_textView_BatteryBankCableSizeinSQMM);
        mBatterySetTextViewBatteryBankCableSizeinSQMMVal = (TextView) findViewById(R.id.batterySet_textView_BatteryBankCableSizeinSQMM_val);
        mBatterySetTextViewBatteryBankEarthingStatus = (TextView) findViewById(R.id.batterySet_textView_BatteryBankEarthingStatus);
        mBatterySetTextViewBatteryBankEarthingStatusVal = (TextView) findViewById(R.id.batterySet_textView_BatteryBankEarthingStatus_val);
        mBatterySetTextViewBACKUPCondition = (TextView) findViewById(R.id.batterySet_textView_BACKUPCondition);
        mBatterySetTextViewBACKUPConditionVal = (TextView) findViewById(R.id.batterySet_textView_BACKUPCondition_val);
        mBatterySetTextViewNatureofProblem = (TextView) findViewById(R.id.batterySet_textView_NatureofProblem);
        mBatterySetEditTextNatureofProblem = (EditText) findViewById(R.id.batterySet_editText_NatureofProblem);
        button_ClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);

        batterySet_button_nextReading = (Button) findViewById(R.id.batterySet_button_nextReading);
        batterySet_button_previousReading = (Button) findViewById(R.id.batterySet_button_previousReading);
        batterySet_textView_Number = (TextView) findViewById(R.id.batterySet_textView_Number);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);
        batterySetLinearLayoutNumberOfBatteryBankWorking = (LinearLayout) findViewById(R.id.batterySet_linearLayout_NumberofBatteryBankWorking);

        mBatterySetEditTextBackupduration.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        //hotoTransactionData = new HotoTransactionData();
        //setInputDetails();
    }

    private void initCombo() {

        mBatterySetTextViewNoofBatterySetProvidedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_NoofBatterySetProvided))),
                        "No of Battery Set Provided",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        str_noofBatterySetProvided = item.get(position);
                        invalidateOptionsMenu();
                        mBatterySetTextViewNoofBatterySetProvidedVal.setText(str_noofBatterySetProvided);
                        mBatterySetTextViewNumberofBatteryBankWorkingVal.setText("");

                        //clear AC collection empty by select / changing value of No of Ac provided
                        if (batterySetData != null && batterySetData.size() > 0) {
                            batterySetData.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        totalCount = Integer.parseInt(str_noofBatterySetProvided);

                        // Clear all field value and hide layout If Non AC or O //
                        if (totalCount > 0) {

                            batterySet_textView_Number.setText("Reading: #1");
                            linearLayout_container.setVisibility(View.VISIBLE);
                            batterySetLinearLayoutNumberOfBatteryBankWorking.setVisibility(View.VISIBLE);
                            batterySet_button_previousReading.setVisibility(View.GONE);
                            batterySet_button_nextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                batterySet_button_nextReading.setText("Finish");
                            } else {
                                batterySet_button_nextReading.setText("Next Reading");
                            }
                        } else {
                            linearLayout_container.setVisibility(View.GONE);
                            batterySetLinearLayoutNumberOfBatteryBankWorking.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
        mBatterySetTextViewNumberofBatteryBankWorkingVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_NumberofBatteryBankWorking))),
                        "Number of Battery Bank Working",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberofBatteryBankWorking = item.get(position);
                        mBatterySetTextViewNumberofBatteryBankWorkingVal.setText("");
                        if (checkValidation(mBatterySetTextViewNoofBatterySetProvidedVal.getText().toString().trim(), str_numberofBatteryBankWorking, "onClick") == true) {
                            mBatterySetTextViewNumberofBatteryBankWorkingVal.setText(str_numberofBatteryBankWorking);
                        }
                    }
                });
            }
        });

        mBatterySetTextViewAssetOwnerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_AssetOwner))),
                        "Asset Owner",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_assetOwner = item.get(position);
                        mBatterySetTextViewAssetOwnerVal.setText(str_assetOwner);
                    }
                });
            }
        });
        mBatterySetTextViewManufacturerMakeModelVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_ManufacturerMakeModel))),
                        "Manufacturer Make Model",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_manufacturerMakeModel = item.get(position);
                        mBatterySetTextViewManufacturerMakeModelVal.setText(str_manufacturerMakeModel);
                    }
                });

            }
        });


        mBatterySetTextViewCapacityinAHVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_CapacityinAH))),
                        "Capacity in AH",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_capacityinAH = item.get(position);
                        mBatterySetTextViewCapacityinAHVal.setText(str_capacityinAH);
                    }
                });

            }
        });

        mBatterySetTextViewTypeofBatteryVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_TypeofBattery))),
                        "Type of Battery",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeofBattery = item.get(position);
                        mBatterySetTextViewTypeofBatteryVal.setText(str_typeofBattery);
                    }
                });

            }
        });

        mBatterySetTextViewPositionofBatteryBankVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_PositionofBatteryBank))),
                        "Position of Battery Bank",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_positionofBatteryBank = item.get(position);
                        mBatterySetTextViewPositionofBatteryBankVal.setText(str_positionofBatteryBank);
                    }
                });

            }
        });

        mBatterySetTextViewBatteryBankCableSizeinSQMMVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_BatteryBankCableSizeinSQMM))),
                        "Battery Bank Cable Size in SQMM",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_batteryBankCableSizeinSQMM = item.get(position);
                        mBatterySetTextViewBatteryBankCableSizeinSQMMVal.setText(str_batteryBankCableSizeinSQMM);
                    }
                });

            }
        });

        mBatterySetTextViewBatteryBankEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_BatteryBankEarthingStatus))),
                        "Battery Bank Earthing Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_batteryBankEarthingStatus = item.get(position);
                        mBatterySetTextViewBatteryBankEarthingStatusVal.setText(str_batteryBankEarthingStatus);
                    }
                });

            }
        });

        mBatterySetTextViewBACKUPConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Battery_Set.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_batterySet_BACKUPCondition))),
                        "Backup Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_backupCondition = item.get(position);
                        mBatterySetTextViewBACKUPConditionVal.setText(str_backupCondition);
                    }
                });

            }
        });
        /*mBatterySetButtonQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(Battery_Set.this, imageFileUri);
                } else {
                    Toast.makeText(Battery_Set.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        batterySet_button_nextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkValidtionForArrayFields()) { comment 04022019 by tiger*/
                if (currentPos < (totalCount - 1)) {
                    //Save current ac reading
                    saveRecords(currentPos);
                    currentPos = currentPos + 1;
                    //move to Next reading
                    displayRecords(currentPos);

                } else if (currentPos == (totalCount - 1)) {
                    saveRecords(currentPos);
                    //Save Final current reading and submit all AC data

                    if (checkDuplicationQrCodeNew() == false) {//add 04022019 by 008
                         if (checkTypeOfBatteryIsSame() == false) {//add 23032019 by tiger for new requirement
                            if (checkValidation(mBatterySetTextViewNoofBatterySetProvidedVal.getText().toString().trim(), mBatterySetTextViewNumberofBatteryBankWorkingVal.getText().toString().trim(), "onSubmit") == true) {
                                //saveRecords(currentPos);
                                submitDetails();
                                startActivity(new Intent(Battery_Set.this, ExternalTenantsPersonaldetails.class));
                                finish();
                            }
                        }
                    }
                }
                /*}*/
            }
        });
        batterySet_button_previousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPos > 0) {
                    //Save current ac reading
                    saveRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayRecords(currentPos);
                }
            }
        });

        button_ClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringBatterySet = "";
                button_ClearQRCodeScanView.setVisibility(View.GONE);
                mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });


    }

    //add 23032019 by tiger for new requirement
    private boolean checkTypeOfBatteryIsSame() {
        for (int i = 0; i < batterySetData.size(); i++) {
            for (int j = i + 1; j < batterySetData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (!batterySetData.get(i).getTypeOfBattery().toString().equals(batterySetData.get(j).getTypeOfBattery().toString())) {
                    int dup_pos = j + 1;
                    showToast("Type Of Battery value in reading no: " + dup_pos + " was not same in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkValidtionForArrayFields() {
        String batterySet_Qr = base64StringBatterySet;
        String assetOwner = mBatterySetTextViewAssetOwnerVal.getText().toString().trim();
        String manufactureMakeModel = mBatterySetTextViewManufacturerMakeModelVal.getText().toString().trim();
        String capacityInAH = mBatterySetTextViewCapacityinAHVal.getText().toString().trim();
        String typeOfBattery = mBatterySetTextViewTypeofBatteryVal.getText().toString().trim();
        String dateOfInstallation = mBatterySetEditTextDateofInstallation.getText().toString().trim();
        String backupDuaration = mBatterySetEditTextBackupduration.getText().toString().trim();
        String positionOfBatteryBank = mBatterySetTextViewPositionofBatteryBankVal.getText().toString().trim();
        String batteryBankCableSize = mBatterySetTextViewBatteryBankCableSizeinSQMMVal.getText().toString().trim();
        String batteryBankEarthingStatus = mBatterySetTextViewBatteryBankEarthingStatusVal.getText().toString().trim();
        String backupCondition = mBatterySetTextViewBACKUPConditionVal.getText().toString().trim();
        String natureOfProblem = mBatterySetEditTextNatureofProblem.getText().toString().trim();

        if (batterySet_Qr.isEmpty() || batterySet_Qr == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (assetOwner.isEmpty() || assetOwner == null) {
            showToast("Select Asset Owner ");
            return false;
        } else if (manufactureMakeModel.isEmpty() || manufactureMakeModel == null) {
            showToast("Select Manufacture/Make/Model");
            return false;
        } else if (capacityInAH.isEmpty() || capacityInAH == null) {
            showToast("Select Capacity in AH ");
            return false;
        } else if (typeOfBattery.isEmpty() || typeOfBattery == null) {
            showToast("Select Type of Battery ");
            return false;
        } else if (dateOfInstallation.isEmpty() || dateOfInstallation == null) {
            showToast("Select Date of Installation ");
            return false;
        } else if (backupDuaration.isEmpty() || backupDuaration == null) {
            showToast("Select Backup Duaration ");
            return false;
        } else if (positionOfBatteryBank.isEmpty() || positionOfBatteryBank == null) {
            showToast("Select Position of Battery Bank ");
            return false;
        } else if (batteryBankCableSize.isEmpty() || batteryBankCableSize == null) {
            showToast("Enter Battery Bank Cable Size");
            return false;
        } else if (batteryBankEarthingStatus.isEmpty() || batteryBankEarthingStatus == null) {
            showToast("Select Battery Bank Earthing Status ");
            return false;
        } else if (backupCondition.isEmpty() || backupCondition == null) {
            showToast("Select Backup Condition");
            return false;
        } else if (natureOfProblem.isEmpty() || natureOfProblem == null) {
            showToast("Select Nature of Problem ");
            return false;
        }/* else if (checkDuplicationQrCode(currentPos)) {
            return false;
        }*/ else return true;


    }

    //add 04022019 by tiger for new requirement
    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < batterySetData.size(); i++) {
            for (int j = i + 1; j < batterySetData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (batterySetData.get(i).getBatterySet_Qr().toString().equals(batterySetData.get(j).getBatterySet_Qr().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDuplicationQrCodeOld(int curr_pos) {
        for (int i = 0; i < batterySetData.size(); i++) {
            if (i != curr_pos) {
                if (base64StringBatterySet.equals(batterySetData.get(i).getBatterySet_Qr().toString())) {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuDone);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_noofBatterySetProvided != null && !str_noofBatterySetProvided.isEmpty()) {
            if (Integer.valueOf(str_noofBatterySetProvided) > 0) {
                shareItem.setVisible(false);
            }
        }

        return true;
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBatterySetEditTextDateofInstallation.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                // startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                ///if (checkValidation()) {
                if (checkValidation(mBatterySetTextViewNoofBatterySetProvidedVal.getText().toString().trim(), mBatterySetTextViewNumberofBatteryBankWorkingVal.getText().toString().trim(), "onSubmit") == true) {
                    submitDetails();
                    finish();
                    startActivity(new Intent(this, ExternalTenantsPersonaldetails.class));
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation(String noOfBatterySet, String noOfBatteryBankWorking, String methodFlag) {
        //String noOfBatterySet = mBatterySetTextViewNoofBatterySetProvidedVal.getText().toString().trim();
        //String noOfBatteryBankWorking = mBatterySetTextViewNumberofBatteryBankWorkingVal.getText().toString().trim();

        /*if (noOfBatterySet.isEmpty() || noOfBatterySet == null) {
            showToast("Select No of Battery Set Provided ");
            return false;
        } else if (noOfBatteryBankWorking.isEmpty() || noOfBatteryBankWorking == null) {
            showToast("Select No of Battery Bank Working ");
            return false;
        } else if (Integer.valueOf(noOfBatteryBankWorking) > Integer.valueOf(noOfBatterySet)) {
            showToast("Number of battery bank working  is not more than number of battery set provided ");
            return false;
        } else return true;*/


        if (noOfBatterySet.isEmpty() || noOfBatterySet == null) {
            showToast("Select No of Battery Set Provided ");
            return false;
        } else if (Integer.valueOf(noOfBatterySet) > 0) {
            if (noOfBatteryBankWorking.isEmpty() || noOfBatteryBankWorking == null) {
                showToast("Select No of Battery Bank Working ");
                return false;
            } else if (Integer.valueOf(noOfBatteryBankWorking) > Integer.valueOf(noOfBatterySet)) {
                showToast("Number of battery bank working  is not more than number of battery set provided ");
                return false;
            } else if ((batterySetData.size() != Integer.valueOf(noOfBatterySet) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;
    }

    /*private boolean checkValidation() {
        String noOfBatterySet = mBatterySetTextViewNoofBatterySetProvidedVal.getText().toString().trim();
        String noOfBatteryBankWorking = mBatterySetTextViewNumberofBatteryBankWorkingVal.getText().toString().trim();

        if (noOfBatterySet.isEmpty() || noOfBatterySet == null) {
            showToast("Select No of Battery Set Provided ");
            return false;
        } else if (noOfBatteryBankWorking.isEmpty() || noOfBatteryBankWorking == null) {
            showToast("Select No of Battery Bank Working ");
            return false;
        } else if (Integer.valueOf(noOfBatteryBankWorking) > Integer.valueOf(noOfBatterySet)) {
            showToast("Number of battery bank working  is not more than number of battery set provided ");
            return false;
        } else return true;
    }*/


    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(Battery_Set.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Battery_Set.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(Battery_Set.this);
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

                final EditText taskEditText = new EditText(Battery_Set.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Battery_Set.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(Battery_Set.this,
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


    public void openCameraIntent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            //  imageFileUri = Uri.fromFile(file);
            imageFileUri = FileProvider.getUriForFile(Battery_Set.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA && resultCode == RESULT_OK)
        {
            if (imageFileUri != null) {
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
//                            (Bitmap) data.getExtras().get("data");
//                mImageView.setImageBitmap(imageBitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] bitmapDataArray = stream.toByteArray();
                    base64StringBatterySet = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                    mBatterySetButtonQRCodeScanView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            imageFileName = "";
            imageFileUri = null;
            mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
        }*/

        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (result.getContents() == null) {
                base64StringBatterySet = "";
                showToast("Cancelled");
            } else {
                Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                if (!flagIsDuplicateQRcode) {
                    base64StringBatterySet = result.getContents();
                    if (!base64StringBatterySet.isEmpty() && base64StringBatterySet != null) {
                        mBatterySetButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                } else {
                    base64StringBatterySet = "";
                    showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
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
                            saveToPreferences(Battery_Set.this, ALLOW_KEY, true);
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                batterySetParentData = hotoTransactionData.getBatterySetParentData();
                batterySetData.addAll(batterySetParentData.getBatterySetData());

                totalCount = Integer.parseInt(batterySetParentData.getNoOfBatterySet());
                mBatterySetTextViewNoofBatterySetProvidedVal.setText(batterySetParentData.getNoOfBatterySet());

                batterySetLinearLayoutNumberOfBatteryBankWorking.setVisibility(View.GONE);
                if (!batterySetParentData.getNoOfBatteryBankWorking().isEmpty() && batterySetParentData.getNoOfBatteryBankWorking() != null) {
                    batterySetLinearLayoutNumberOfBatteryBankWorking.setVisibility(View.VISIBLE);
                }
                mBatterySetTextViewNumberofBatteryBankWorkingVal.setText(batterySetParentData.getNoOfBatteryBankWorking());

                if (batterySetData != null && batterySetData.size() > 0) {

                    linearLayout_container.setVisibility(View.VISIBLE);
                    batterySet_textView_Number.setText("Reading: #1");


                    base64StringBatterySet = (batterySetData.get(index).getBatterySet_Qr());
                    mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringBatterySet.isEmpty() && base64StringBatterySet != null) {
                        mBatterySetButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    mBatterySetTextViewAssetOwnerVal.setText(batterySetData.get(index).getAssetOwner());
                    mBatterySetTextViewManufacturerMakeModelVal.setText(batterySetData.get(index).getManufactureMakeModel());
                    mBatterySetTextViewCapacityinAHVal.setText(batterySetData.get(index).getCapacityInAH());
                    mBatterySetTextViewTypeofBatteryVal.setText(batterySetData.get(index).getTypeOfBattery());
                    mBatterySetEditTextDateofInstallation.setText(batterySetData.get(index).getDateOfInstallation());
                    mBatterySetEditTextbookValue.setText(batterySetData.get(index).getBookValue());
                    mBatterySetEditTextBackupduration.setText(batterySetData.get(index).getBackupDuaration());
                    mBatterySetTextViewPositionofBatteryBankVal.setText(batterySetData.get(index).getPositionOfBatteryBank());
                    mBatterySetTextViewBatteryBankCableSizeinSQMMVal.setText(batterySetData.get(index).getBatteryBankCableSize());
                    mBatterySetTextViewBatteryBankEarthingStatusVal.setText(batterySetData.get(index).getBatteryBankEarthingStatus());
                    mBatterySetTextViewBACKUPConditionVal.setText(batterySetData.get(index).getBackupCondition());
                    mBatterySetEditTextNatureofProblem.setText(batterySetData.get(index).getNatureOfProblem());

                    // New added for image #ImageSet
                    /*imageFileName = batterySetData.get(index).getQrCodeImageFileName();
                    mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
                    if (imageFileName != null && imageFileName.length() > 0) {
                        File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
                        imageFileUri = FileProvider.getUriForFile(Battery_Set.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        if (imageFileUri != null) {
                            mBatterySetButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        }
                    }*/

                    batterySet_button_previousReading.setVisibility(View.GONE);
                    batterySet_button_nextReading.setVisibility(View.VISIBLE);

                    //if (batterySetData.size() > 1) {
                    if (totalCount > 1) {
                        batterySet_button_nextReading.setText("Next Reading");
                    } else {
                        batterySet_button_nextReading.setText("Finish");
                    }
                }
            } else {
                //Toast.makeText(Battery_Set.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                showToast("No previous saved data available");
                linearLayout_container.setVisibility(View.GONE);
                batterySetLinearLayoutNumberOfBatteryBankWorking.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            String noOfBatterySet = mBatterySetTextViewNoofBatterySetProvidedVal.getText().toString().trim();
            String noOfBatteryBankWorking = mBatterySetTextViewNumberofBatteryBankWorkingVal.getText().toString().trim();

            batterySetParentData = new BatterySetParentData(noOfBatterySet, noOfBatteryBankWorking, batterySetData);
            hotoTransactionData.setBatterySetParentData(batterySetParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecords(int pos) {
        String batterySet_Qr = base64StringBatterySet;
        String assetOwner = mBatterySetTextViewAssetOwnerVal.getText().toString().trim();
        String manufactureMakeModel = mBatterySetTextViewManufacturerMakeModelVal.getText().toString().trim();
        String capacityInAH = mBatterySetTextViewCapacityinAHVal.getText().toString().trim();
        String typeOfBattery = mBatterySetTextViewTypeofBatteryVal.getText().toString().trim();
        String dateOfInstallation = mBatterySetEditTextDateofInstallation.getText().toString().trim();
        String bookValue = mBatterySetEditTextbookValue.getText().toString().trim();
        String backupDuaration = mBatterySetEditTextBackupduration.getText().toString().trim();
        String positionOfBatteryBank = mBatterySetTextViewPositionofBatteryBankVal.getText().toString().trim();
        String batteryBankCableSize = mBatterySetTextViewBatteryBankCableSizeinSQMMVal.getText().toString().trim();
        String batteryBankEarthingStatus = mBatterySetTextViewBatteryBankEarthingStatusVal.getText().toString().trim();
        String backupCondition = mBatterySetTextViewBACKUPConditionVal.getText().toString().trim();
        String natureOfProblem = mBatterySetEditTextNatureofProblem.getText().toString().trim();


        BatterySetData obj_batterySetData = new BatterySetData(batterySet_Qr, bookValue, assetOwner, manufactureMakeModel, capacityInAH, typeOfBattery, dateOfInstallation, backupDuaration, positionOfBatteryBank, batteryBankCableSize, batteryBankEarthingStatus, backupCondition, natureOfProblem, imageFileName);

        if (batterySetData.size() > 0) {
            if (pos == batterySetData.size()) {
                batterySetData.add(obj_batterySetData);
            } else if (pos < batterySetData.size()) {
                batterySetData.set(pos, obj_batterySetData);
            }
        } else {
            batterySetData.add(obj_batterySetData);
        }

    }

    private void displayRecords(int pos) {
        if (batterySetData.size() > 0 && pos < batterySetData.size()) {

            batterySet_textView_Number.setText("Reading: #" + (pos + 1));

            base64StringBatterySet = batterySetData.get(pos).getBatterySet_Qr();
            mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringBatterySet.isEmpty() && base64StringBatterySet != null) {
                mBatterySetButtonQRCodeScanView.setVisibility(View.VISIBLE);
                button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            mBatterySetTextViewAssetOwnerVal.setText(batterySetData.get(pos).getAssetOwner());
            mBatterySetTextViewManufacturerMakeModelVal.setText(batterySetData.get(pos).getManufactureMakeModel());
            mBatterySetTextViewCapacityinAHVal.setText(batterySetData.get(pos).getCapacityInAH());
            mBatterySetTextViewTypeofBatteryVal.setText(batterySetData.get(pos).getTypeOfBattery());
            mBatterySetEditTextDateofInstallation.setText(batterySetData.get(pos).getDateOfInstallation());
            mBatterySetEditTextbookValue.setText(batterySetData.get(pos).getBookValue());
            mBatterySetEditTextBackupduration.setText(batterySetData.get(pos).getBackupDuaration());
            mBatterySetTextViewPositionofBatteryBankVal.setText(batterySetData.get(pos).getPositionOfBatteryBank());
            mBatterySetTextViewBatteryBankCableSizeinSQMMVal.setText(batterySetData.get(pos).getBatteryBankCableSize());
            mBatterySetTextViewBatteryBankEarthingStatusVal.setText(batterySetData.get(pos).getBatteryBankEarthingStatus());
            mBatterySetTextViewBACKUPConditionVal.setText(batterySetData.get(pos).getBackupCondition());
            mBatterySetEditTextNatureofProblem.setText(batterySetData.get(pos).getNatureOfProblem());

            batterySet_button_previousReading.setVisibility(View.VISIBLE);
            batterySet_button_nextReading.setVisibility(View.VISIBLE);
        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            batterySet_button_previousReading.setVisibility(View.VISIBLE);
            batterySet_button_nextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            batterySet_button_previousReading.setVisibility(View.VISIBLE);
            batterySet_button_nextReading.setText("Finish");
        } else if (pos == 0) {
            batterySet_button_previousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                batterySet_button_nextReading.setText("Finish");
            } else {
                batterySet_button_nextReading.setText("Next Reading");
            }
        }
    }

    public void clearFields(int indexPos) {

        batterySet_textView_Number.setText("Reading: #" + (indexPos + 1));

        //mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
        //button_ClearQRCodeScanView.setVisibility(View.GONE);

        mBatterySetTextViewAssetOwnerVal.setText("");
        mBatterySetTextViewManufacturerMakeModelVal.setText("");
        mBatterySetTextViewCapacityinAHVal.setText("");
        mBatterySetTextViewTypeofBatteryVal.setText("");
        mBatterySetEditTextDateofInstallation.setText("");
        mBatterySetEditTextbookValue.setText("");
        mBatterySetEditTextBackupduration.setText("00:00");
        mBatterySetTextViewPositionofBatteryBankVal.setText("");
        mBatterySetTextViewBatteryBankCableSizeinSQMMVal.setText("");
        mBatterySetTextViewBatteryBankEarthingStatusVal.setText("");
        mBatterySetTextViewBACKUPConditionVal.setText("");
        mBatterySetEditTextNatureofProblem.setText("");

        str_numberofBatteryBankWorking = "";
        str_assetOwner = "";
        str_manufacturerMakeModel = "";
        str_capacityinAH = "";
        str_typeofBattery = "";
        str_positionofBatteryBank = "";
        str_batteryBankCableSizeinSQMM = "";
        str_batteryBankEarthingStatus = "";
        str_backupCondition = "";
        base64StringBatterySet = "";

        mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
        button_ClearQRCodeScanView.setVisibility(View.GONE);

        if (!base64StringBatterySet.isEmpty() && base64StringBatterySet != null) {
            mBatterySetButtonQRCodeScanView.setVisibility(View.VISIBLE);
            button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
        } /*else {
            mBatterySetButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
        }*/
    }

    public void onClicked(View v) {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

}
