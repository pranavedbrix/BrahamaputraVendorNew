package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerManagementSystemData;
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

import android.Manifest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PowerManagementSystem extends BaseActivity {

    private static final String TAG = PowerManagementSystem.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private Uri imageFileUri = null;
    private String imageFileName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private PowerManagementSystemData powerManagementSystemData;
    private String base64StringPowerManagementSystem = "";
    private SessionManager sessionManager;

    private String str_newFieldAvailibity;
    private String str_assetOwner;
    private String str_powerManagementSystemType;
    private String str_powerManagementSystemMake;
    private String str_powerManagementSystemPosition;
    private String str_powerManagementSystemStaus;
    private String str_workingCondition;

    private AlertDialogManager alertDialogManager;

    private TextView mPowerManagementSystemTextViewAvailability;
    private TextView mPowerManagementSystemTextViewAvailabilityVal;
    private TextView mPowerManagementSystemTextViewQRCodeScan;
    private ImageView mPowerManagementSystemButtonQRCodeScan;
    private ImageView mPowerManagementSystemButtonQRCodeScanView;
    private TextView mPowerManagementSystemTextViewAssetOwner;
    private TextView mPowerManagementSystemTextViewAssetOwnerVal;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemType;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemTypeVal;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemMake;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemMakeVal;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemPosition;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemPositionVal;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemStaus;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemStausVal;
    private TextView mPowerManagementSystemTextViewPowerManagementSystemSerialNumber;
    private EditText mPowerManagementSystemEditTextPowerManagementSystemSerialNumber;
    private TextView mPowerManagementSystemTextViewWorkingCondition;
    private TextView mPowerManagementSystemTextViewWorkingConditionVal;
    private TextView mPowerManagementSystemTextViewNatureofProblem;
    private EditText mPowerManagementSystemEditTextNatureofProblem;
    private ImageView button_ClearQRCodeScanView;
    private LinearLayout linearLayoutPowerManagementDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_management_system);
        this.setTitle("Power Management System");
        sessionManager = new SessionManager(PowerManagementSystem.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PowerManagementSystem.this, userId, ticketName);
        assignViews();
        initCombo();
        alertDialogManager = new AlertDialogManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hotoTransactionData = new HotoTransactionData();
        setInputDetails();

        mPowerManagementSystemButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PowerManagementSystem.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(PowerManagementSystem.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(PowerManagementSystem.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PowerManagementSystem.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(PowerManagementSystem.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCameraIntent();This Commented By 008 on 15-11-2018 For QR Code Purpose
                    onClicked(v);
                }

            }
        });

    }

    private void assignViews() {
        linearLayoutPowerManagementDetails = (LinearLayout) findViewById(R.id.linearLayout_powerManagementDetails);
        mPowerManagementSystemTextViewAvailability = (TextView) findViewById(R.id.powerManagementSystem_textView_availability);
        mPowerManagementSystemTextViewAvailabilityVal = (TextView) findViewById(R.id.powerManagementSystem_textView_availability_val);
        mPowerManagementSystemTextViewQRCodeScan = (TextView) findViewById(R.id.powerManagementSystem_textView_QRCodeScan);
        mPowerManagementSystemButtonQRCodeScan = (ImageView) findViewById(R.id.powerManagementSystem_button_QRCodeScan);
        mPowerManagementSystemButtonQRCodeScanView = (ImageView) findViewById(R.id.powerManagementSystem_button_QRCodeScanView);
        mPowerManagementSystemTextViewAssetOwner = (TextView) findViewById(R.id.powerManagementSystem_textView_AssetOwner);
        mPowerManagementSystemTextViewAssetOwnerVal = (TextView) findViewById(R.id.powerManagementSystem_textView_AssetOwner_val);
        mPowerManagementSystemTextViewPowerManagementSystemType = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemType);
        mPowerManagementSystemTextViewPowerManagementSystemTypeVal = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemType_val);
        mPowerManagementSystemTextViewPowerManagementSystemMake = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemMake);
        mPowerManagementSystemTextViewPowerManagementSystemMakeVal = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemMake_val);
        mPowerManagementSystemTextViewPowerManagementSystemPosition = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemPosition);
        mPowerManagementSystemTextViewPowerManagementSystemPositionVal = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemPosition_val);
        mPowerManagementSystemTextViewPowerManagementSystemStaus = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemStaus);
        mPowerManagementSystemTextViewPowerManagementSystemStausVal = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemStaus_val);
        mPowerManagementSystemTextViewPowerManagementSystemSerialNumber = (TextView) findViewById(R.id.powerManagementSystem_textView_PowerManagementSystemSerialNumber);
        mPowerManagementSystemEditTextPowerManagementSystemSerialNumber = (EditText) findViewById(R.id.powerManagementSystem_editText_PowerManagementSystemSerialNumber);
        mPowerManagementSystemTextViewWorkingCondition = (TextView) findViewById(R.id.powerManagementSystem_textView_WorkingCondition);
        mPowerManagementSystemTextViewWorkingConditionVal = (TextView) findViewById(R.id.powerManagementSystem_textView_WorkingCondition_val);
        mPowerManagementSystemTextViewNatureofProblem = (TextView) findViewById(R.id.powerManagementSystem_textView_NatureofProblem);
        mPowerManagementSystemEditTextNatureofProblem = (EditText) findViewById(R.id.powerManagementSystem_editText_NatureofProblem);
        button_ClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    private void initCombo() {

        mPowerManagementSystemTextViewAvailabilityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_NewFieldAvailability))),
                        "Availability",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_newFieldAvailibity = item.get(position);
                        mPowerManagementSystemTextViewAvailabilityVal.setText(str_newFieldAvailibity);
                        // Added Code By Pranav For Handling Field Visibility In Power Management System
                        if (str_newFieldAvailibity.equals("No")) {
                            base64StringPowerManagementSystem = "";
                            button_ClearQRCodeScanView.setVisibility(View.GONE);
                            mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.GONE);
                            mPowerManagementSystemTextViewAssetOwnerVal.setText("");
                            mPowerManagementSystemTextViewPowerManagementSystemTypeVal.setText("");
                            mPowerManagementSystemTextViewPowerManagementSystemMakeVal.setText("");
                            mPowerManagementSystemTextViewPowerManagementSystemPositionVal.setText("");
                            mPowerManagementSystemTextViewPowerManagementSystemStausVal.setText("");
                            mPowerManagementSystemEditTextPowerManagementSystemSerialNumber.setText("");
                            mPowerManagementSystemTextViewWorkingConditionVal.setText("");
                            mPowerManagementSystemEditTextNatureofProblem.setText("");
                            linearLayoutPowerManagementDetails.setVisibility(View.GONE);
                        } else {
                            linearLayoutPowerManagementDetails.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        mPowerManagementSystemTextViewAssetOwnerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_AssetOwner))),
                        "Asset Owner",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_assetOwner = item.get(position);
                        mPowerManagementSystemTextViewAssetOwnerVal.setText(str_assetOwner);
                    }
                });

            }
        });
        mPowerManagementSystemTextViewPowerManagementSystemTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_PowerManagementSystemType))),
                        "Power Management System Type",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_powerManagementSystemType = item.get(position);
                        mPowerManagementSystemTextViewPowerManagementSystemTypeVal.setText(str_powerManagementSystemType);
                    }
                });

            }
        });
        mPowerManagementSystemTextViewPowerManagementSystemMakeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_PowerManagementSystemMake))),
                        "Power Management System Make",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_powerManagementSystemMake = item.get(position);
                        mPowerManagementSystemTextViewPowerManagementSystemMakeVal.setText(str_powerManagementSystemMake);
                    }
                });

            }
        });

        mPowerManagementSystemTextViewPowerManagementSystemPositionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_PowerManagementSystemPosition))),
                        "Power Management System Position",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_powerManagementSystemPosition = item.get(position);
                        mPowerManagementSystemTextViewPowerManagementSystemPositionVal.setText(str_powerManagementSystemPosition);
                    }
                });

            }
        });
        mPowerManagementSystemTextViewPowerManagementSystemStausVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_PowerManagementSystemStaus))),
                        "Power Management System Staus",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_powerManagementSystemStaus = item.get(position);
                        mPowerManagementSystemTextViewPowerManagementSystemStausVal.setText(str_powerManagementSystemStaus);
                    }
                });

            }
        });
        mPowerManagementSystemTextViewWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerManagementSystem.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerManagementSystem_WorkingCondition))),
                        "Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_workingCondition = item.get(position);
                        mPowerManagementSystemTextViewWorkingConditionVal.setText(str_workingCondition);
                    }
                });

            }
        });
        button_ClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringPowerManagementSystem = "";
                button_ClearQRCodeScanView.setVisibility(View.GONE);
                mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        /*This Commented By 008 on 15-11-2018 For QR Code Purpose
        mPowerManagementSystemButtonQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(PowerManagementSystem.this, imageFileUri);
                } else {
                    Toast.makeText(PowerManagementSystem.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                // startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                // Added Code By Pranav For Handling Field Visibility In Power Management System
                if (mPowerManagementSystemTextViewAvailabilityVal.getText().toString().equals("No")) {
                    submitDetails();
                    finish();
                    startActivity(new Intent(this, GeneralAndSafetyMeasures.class));
                    return true;
                } else {
                    if (checkValidation() == true) {
                        submitDetails();
                        finish();
                        startActivity(new Intent(this, GeneralAndSafetyMeasures.class));
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                powerManagementSystemData = hotoTransactionData.getPowerManagementSystemData();

                mPowerManagementSystemTextViewAvailabilityVal.setText(powerManagementSystemData.getNewFieldAvailability());
                // Added Code By Pranav For Handling Field Visibility In Power Management System
                if (mPowerManagementSystemTextViewAvailabilityVal.getText().toString().equals("No")) {
                    // mPowerManagementSystemTextViewAvailabilityVal.setText(powerManagementSystemData.getNewFieldAvailability());
                    linearLayoutPowerManagementDetails.setVisibility(View.GONE);
                } else {
                    // mPowerManagementSystemTextViewAvailabilityVal.setText(powerManagementSystemData.getNewFieldAvailability());
                    base64StringPowerManagementSystem = powerManagementSystemData.getPowerManagementSystemQR();
                    mPowerManagementSystemTextViewAssetOwnerVal.setText(powerManagementSystemData.getAssetOwner());
                    mPowerManagementSystemTextViewPowerManagementSystemTypeVal.setText(powerManagementSystemData.getPowerManagementSystemType());
                    mPowerManagementSystemTextViewPowerManagementSystemMakeVal.setText(powerManagementSystemData.getPowerManagementSystemMake());
                    mPowerManagementSystemTextViewPowerManagementSystemPositionVal.setText(powerManagementSystemData.getPowerManagementSystemPosition());
                    mPowerManagementSystemTextViewPowerManagementSystemStausVal.setText(powerManagementSystemData.getPowerManagementSystemStaus());
                    mPowerManagementSystemEditTextPowerManagementSystemSerialNumber.setText(powerManagementSystemData.getSerialNumber());
                    mPowerManagementSystemTextViewWorkingConditionVal.setText(powerManagementSystemData.getWorkingCondition());
                    mPowerManagementSystemEditTextNatureofProblem.setText(powerManagementSystemData.getNatureofProblem());

                    mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringPowerManagementSystem.isEmpty() && base64StringPowerManagementSystem != null) {
                        mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                }

                // New added for image #ImageSet
                /*This Commented By 008 on 15-11-2018 For QR Code Purpose
                imageFileName = powerManagementSystemData.getQrCodeImageFileName();
                mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.GONE);
                if (imageFileName != null && imageFileName.length() > 0) {
                    File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
//                             imageFileUri = Uri.fromFile(file);
                    imageFileUri = FileProvider.getUriForFile(PowerManagementSystem.this, BuildConfig.APPLICATION_ID + ".provider", file);
                    if (imageFileUri != null) {
                        mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                }*/

            } else {
                //Toast.makeText(PowerManagementSystem.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            //hotoTransactionData.setTicketNo(ticketId);

            String newFieldAvailability = mPowerManagementSystemTextViewAvailabilityVal.getText().toString().trim();
            String powerManagementSystemQR = base64StringPowerManagementSystem;
            String assetOwner = mPowerManagementSystemTextViewAssetOwnerVal.getText().toString().trim();
            String powerManagementSystemType = mPowerManagementSystemTextViewPowerManagementSystemTypeVal.getText().toString().trim();
            String powerManagementSystemMake = mPowerManagementSystemTextViewPowerManagementSystemMakeVal.getText().toString().trim();
            String powerManagementSystemPosition = mPowerManagementSystemTextViewPowerManagementSystemPositionVal.getText().toString().trim();
            String powerManagementSystemStaus = mPowerManagementSystemTextViewPowerManagementSystemStausVal.getText().toString().trim();
            String serialNumber = mPowerManagementSystemEditTextPowerManagementSystemSerialNumber.getText().toString().trim();
            String workingCondition = mPowerManagementSystemTextViewWorkingConditionVal.getText().toString().trim();
            String natureofProblem = mPowerManagementSystemEditTextNatureofProblem.getText().toString().trim();

            powerManagementSystemData = new PowerManagementSystemData(newFieldAvailability, powerManagementSystemQR, assetOwner, powerManagementSystemType, powerManagementSystemMake, powerManagementSystemPosition, powerManagementSystemStaus, serialNumber, workingCondition, natureofProblem, imageFileName);

            hotoTransactionData.setPowerManagementSystemData(powerManagementSystemData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*008 29112018*/
    public boolean checkValidation() {
        String newFieldAvailability = mPowerManagementSystemTextViewAvailabilityVal.getText().toString().trim();
        String qRCodeScan = base64StringPowerManagementSystem;
        if (newFieldAvailability.isEmpty() || newFieldAvailability == null) {
            showToast("Select Availability");
            return false;
        } else if ((qRCodeScan.isEmpty() || qRCodeScan == null) && (newFieldAvailability.equals("Yes"))) {
            showToast("Please Scan QR Code");
            return false;
        } else return true;

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

                final EditText taskEditText = new EditText(PowerManagementSystem.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(PowerManagementSystem.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(PowerManagementSystem.this);
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

                final EditText taskEditText = new EditText(PowerManagementSystem.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(PowerManagementSystem.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(PowerManagementSystem.this,
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
            //imageFileUri = Uri.fromFile(file);
            imageFileUri = FileProvider.getUriForFile(PowerManagementSystem.this, BuildConfig.APPLICATION_ID + ".provider", file);

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
            mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (result.getContents() == null) {
                base64StringPowerManagementSystem = "";
                showToast("Cancelled");
            } else {
                Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                if (!flagIsDuplicateQRcode) {
                    base64StringPowerManagementSystem = result.getContents();
                    if (!base64StringPowerManagementSystem.isEmpty() && base64StringPowerManagementSystem != null) {
                        mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                } else {
                    base64StringPowerManagementSystem = "";
                    showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                }


            }
        }

        /*This Commented By 008 on 15-11-2018 For QR Code Purpose
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
                    base64StringPowerManagementSystem = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                    mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            imageFileName = "";
            imageFileUri = null;
            mPowerManagementSystemButtonQRCodeScanView.setVisibility(View.GONE);
        }*/

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
                            saveToPreferences(PowerManagementSystem.this, ALLOW_KEY, true);
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
}
