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
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.SmpsCheckPoint;
import com.brahamaputra.mahindra.brahamaputra.Data.SmpsCheckPointParentData;
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

public class PreventiveMaintenanceSiteSmpsCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteSmpsCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsNumber;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView;
    private ImageView mButtonClearQRCodeScanView;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsCondition;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatus;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatus;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewDcLoadCurrent;
    private EditText mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewPhotoDcLoadCurrent;
    private ImageView mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrent;
    private ImageView mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewDcLoadAmpPh;
    private EditText mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal;
    private Button mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading;
    private LinearLayout mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutTypeOfFault;

    private LinearLayout mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteSmpsCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView;

    String str_noOfSmpsAvailableAtSiteVal;
    String str_smpsConditionVal;
    String str_smpsControlerStatusVal;
    String str_smpsEarthingStatusVal;
    String str_segisterFaultVal;
    String str_sypeOfFaultVal;

    private AlertDialogManager alertDialogManager;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_PhotoDcLoadCurrent = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;
    //public static final int MY_PERMISSIONS_REQUEST_CAMERA_CautionSignBoard = 102;


    private String base64StringSmpsCheckPointsQRCodeScan = "";
    private String base64StringPhotoDcLoadCurrent = "";

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    //private String imageFileSmpsCheckPointsQRCodeScan;
    private String imageFilePhotoDcLoadCurrent;

    //private Uri imageFileUriSmpsCheckPointsQRCodeScan = null;
    private Uri imageFileUriPhotoDcLoadCurrent = null;


    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    /*private HotoTransactionData hotoTransactionData;
    private LandDetailsData landDetailsData;*/
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private SmpsCheckPointParentData dataList;
    private ArrayList<SmpsCheckPoint> smpsCheckPointsData;// replce airConditionersData
    private int totalCount = 0;
    private int currentPos = 0;

    private ArrayList<SmpsCheckPoint> arr_smpsCheckPoints;// replce airConditionersData
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelected;
    ArrayList<String> typeOfFaultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_smps_check_points);
        this.setTitle("SMPS Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this);
        sessionManager = new SessionManager(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, userId, ticketName);

        smpsCheckPointsData = new ArrayList<>();

        //dataList = new ArrayList<>();
        arr_smpsCheckPoints = new ArrayList<>();
        currentPos = 0;

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteSmpsCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }

        assignViews();
        checkCameraPermission();
        initCombo();
        setListner();

        setInputDetails(currentPos);
        invalidateOptionsMenu();
        setMultiSelectModel();
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_noOfSmpsAvailableAtSite);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_noOfSmpsAvailableAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsNumber);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_QRCodeScan);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_QRCodeScan);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_QRCodeScanView);
        mButtonClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsCondition = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsCondition);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsConditionVal);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsControlerStatus);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsControlerStatusVal);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsEarthingStatus);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_smpsEarthingStatusVal);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewDcLoadCurrent = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_dcLoadCurrent);
        mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent = (EditText) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_editText_dcLoadCurrent);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewPhotoDcLoadCurrent = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_photoDcLoadCurrent);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrent = (ImageView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_photoDcLoadCurrent);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_photoDcLoadCurrentView);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewDcLoadAmpPh = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_dcLoadAmpPh);
        mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh = (EditText) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_editText_dcLoadAmpPh);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_nextReading);
        mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_linearLayout_typeOfFault);

        mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteSmpsCheckPoints_button_uploadPhotoOfRegisterFaultView);
    }

    private void initCombo() {

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteSmpsCheckPoints_noOfSMPSAvailableAtSite))),
                        "No of SMPS available at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfSmpsAvailableAtSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal.setText(str_noOfSmpsAvailableAtSiteVal);

                        invalidateOptionsMenu();
                        if (smpsCheckPointsData != null && smpsCheckPointsData.size() > 0) {
                            smpsCheckPointsData.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        if (str_noOfSmpsAvailableAtSiteVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                        } else {
                            totalCount = Integer.parseInt(str_noOfSmpsAvailableAtSiteVal);
                            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Finish");
                            } else {
                                mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Next Reading");
                            }
                        }
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteSmpsCheckPoints_smpsCondition))),
                        "SMPS Condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_smpsConditionVal = item.get(position);
                        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.setText(str_smpsConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteSmpsCheckPoints_smpsControlerStatus))),
                        "SMPS Controler Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_smpsControlerStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.setText(str_smpsControlerStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteSmpsCheckPoints_smpsEarthingStatus))),
                        "SMPS Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_smpsEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.setText(str_smpsEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteSmpsCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_segisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.setText(str_segisterFaultVal);
                        visibilityOfTypesOfFault(str_segisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkValidationOfArrayFields() == true) {*/
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
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
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
                                startActivity(new Intent(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });


    }

    private void setListner() {

        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    PhotoDcLoadCurrent();
                }
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriPhotoDcLoadCurrent != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, imageFileUriPhotoDcLoadCurrent);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DgCheckPointsQRCodeScan();
                }
            }
        });

        mButtonClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringSmpsCheckPointsQRCodeScan = "";
                mButtonClearQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_alarmCheckReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PhotoDcLoadCurrent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFilePhotoDcLoadCurrent = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFilePhotoDcLoadCurrent);
            imageFileUriPhotoDcLoadCurrent = FileProvider.getUriForFile(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriPhotoDcLoadCurrent);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_PhotoDcLoadCurrent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DgCheckPointsQRCodeScan() {
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

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteSmpsCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_PhotoDcLoadCurrent:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriPhotoDcLoadCurrent != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriPhotoDcLoadCurrent);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringPhotoDcLoadCurrent = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFilePhotoDcLoadCurrent = "";
                    imageFileUriPhotoDcLoadCurrent = null;
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringSmpsCheckPointsQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringSmpsCheckPointsQRCodeScan = result.getContents();
                            if (!base64StringSmpsCheckPointsQRCodeScan.isEmpty() && base64StringSmpsCheckPointsQRCodeScan != null) {
                                mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringSmpsCheckPointsQRCodeScan = "";
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
                            mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
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
        if (str_noOfSmpsAvailableAtSiteVal != null && !str_noOfSmpsAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_noOfSmpsAvailableAtSiteVal) > 0) {
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
                if (checkValidationonSubmit("onSubmit") == true) {
                    //submitDetails();
                    startActivity(new Intent(this, PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.class));
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                dataList = pmSiteTransactionDetails.getSmpsCheckPointParentData();
                smpsCheckPointsData.addAll(dataList.getSmpsCheckPointsData());

                mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal.setText(dataList.getNoOfSmpsAvailableAtSite());
                mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());

                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();
                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                    setArrayValuesOfTypeOfFault(dataList.getTypeOfFault());
                }

                str_noOfSmpsAvailableAtSiteVal = dataList.getNoOfSmpsAvailableAtSite();
                invalidateOptionsMenu();

                if (smpsCheckPointsData != null && smpsCheckPointsData.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsNumber.setText("Reading: #1");
                    totalCount = Integer.parseInt(dataList.getNoOfSmpsAvailableAtSite());

                    mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.setText(smpsCheckPointsData.get(index).getSmpsCondition());
                    mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.setText(smpsCheckPointsData.get(index).getSmpsControlerStatus());
                    mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.setText(smpsCheckPointsData.get(index).getSmpsEarthingStatus());
                    mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent.setText(smpsCheckPointsData.get(index).getDcLoadCurrentInFloat());
                    mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh.setText(smpsCheckPointsData.get(index).getDcLoadAmpPh());

                    base64StringSmpsCheckPointsQRCodeScan = smpsCheckPointsData.get(index).getDetailsOfSmpsQrCodeScan();
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringSmpsCheckPointsQRCodeScan.isEmpty() && base64StringSmpsCheckPointsQRCodeScan != null) {
                        mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    base64StringPhotoDcLoadCurrent = smpsCheckPointsData.get(index).getBase64DcLoadCurrentPhoto();
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.GONE);
                    if (!base64StringPhotoDcLoadCurrent.isEmpty() && base64StringPhotoDcLoadCurrent != null) {
                        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.VISIBLE);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringPhotoDcLoadCurrent);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriPhotoDcLoadCurrent = Uri.parse(path);
                    }

                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                    if (totalCount > 1) {
                        mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Finish");
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

    private void visibilityOfTypesOfFault(String RegisterFault) {
        mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteSmpsCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    private void saveRecords(int pos) {

        String smpsCondition = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.getText().toString().trim();
        String smpsControlerStatus = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.getText().toString().trim();
        String smpsEarthingStatus = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.getText().toString().trim();
        String dcLoadCurrentInFloat = mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent.getText().toString().trim();
        String dcLoadAmpPh = mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh.getText().toString().trim();
        String detailsOfSmpsQrCodeScan = base64StringSmpsCheckPointsQRCodeScan;
        String base64DcLoadCurrentPhoto = base64StringPhotoDcLoadCurrent;

        SmpsCheckPoint smpsCheckPointchild = new SmpsCheckPoint(detailsOfSmpsQrCodeScan, smpsCondition, smpsControlerStatus,
                smpsEarthingStatus, dcLoadCurrentInFloat, base64DcLoadCurrentPhoto,
                dcLoadAmpPh);
        if (smpsCheckPointsData.size() > 0) {
            if (pos == smpsCheckPointsData.size()) {
                smpsCheckPointsData.add(smpsCheckPointchild);
            } else if (pos < smpsCheckPointsData.size()) {
                smpsCheckPointsData.set(pos, smpsCheckPointchild);
            }
        } else {
            smpsCheckPointsData.add(smpsCheckPointchild);
        }

    }

    private void displayRecords(int pos) {
        if (smpsCheckPointsData.size() > 0 && pos < smpsCheckPointsData.size()) {

            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsNumber.setText("Reading: #" + (pos + 1));

            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.setText(smpsCheckPointsData.get(pos).getSmpsCondition());
            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.setText(smpsCheckPointsData.get(pos).getSmpsControlerStatus());
            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.setText(smpsCheckPointsData.get(pos).getSmpsEarthingStatus());
           /* mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.setText(smpsCheckPointsData.get(pos).getRegisterFault());
            mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.setText(smpsCheckPointsData.get(pos).getTypeOfFault());*/
            mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent.setText(smpsCheckPointsData.get(pos).getDcLoadCurrentInFloat());
            mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh.setText(smpsCheckPointsData.get(pos).getDcLoadAmpPh());

            base64StringSmpsCheckPointsQRCodeScan = smpsCheckPointsData.get(pos).getDetailsOfSmpsQrCodeScan();
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
            mButtonClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringSmpsCheckPointsQRCodeScan.isEmpty() && base64StringSmpsCheckPointsQRCodeScan != null) {
                mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            base64StringPhotoDcLoadCurrent = smpsCheckPointsData.get(pos).getBase64DcLoadCurrentPhoto();
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.GONE);
            if (!base64StringPhotoDcLoadCurrent.isEmpty() && base64StringPhotoDcLoadCurrent != null) {
                mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.VISIBLE);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringPhotoDcLoadCurrent);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriPhotoDcLoadCurrent = Uri.parse(path);
            }

            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteSmpsCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteSmpsCheckPointsButtonNextReading.setText("Next Reading");
            }
        }
    }

    private void clearFields(int pos) {

        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.setText("");
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.setText("");
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.setText("");
        /*mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.setText("");
        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.setText("");*/
        mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent.setText("");
        mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh.setText("");

        base64StringSmpsCheckPointsQRCodeScan = "";
        base64StringPhotoDcLoadCurrent = "";

        mButtonClearQRCodeScanView.setVisibility(View.GONE);
        //mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScan.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteSmpsCheckPointsButtonPhotoDcLoadCurrentView.setVisibility(View.GONE);
        alreadySelectedTypeOfFaultList = new ArrayList<>();
    }

    private void submitDetails() {
        try {
            String noOfsmpsAtSite = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;

            dataList = new SmpsCheckPointParentData(noOfsmpsAtSite, smpsCheckPointsData, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault);
            pmSiteTransactionDetails.setSmpsCheckPointParentData(dataList);
            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidationOfArrayFields() {
        String smpsCondition = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsConditionVal.getText().toString().trim();
        String smpsControlerStatus = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsControlerStatusVal.getText().toString().trim();
        String smpsEarthingStatus = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewSmpsEarthingStatusVal.getText().toString().trim();
        String dcLoadCurrentInFloat = mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadCurrent.getText().toString().trim();
        String dcLoadAmpPh = mPreventiveMaintenanceSiteSmpsCheckPointsEditTextDcLoadAmpPh.getText().toString().trim();
        String detailsOfSmpsQrCodeScan = base64StringSmpsCheckPointsQRCodeScan;
        String base64DcLoadCurrentPhoto = base64StringPhotoDcLoadCurrent;

        if (detailsOfSmpsQrCodeScan.isEmpty() || detailsOfSmpsQrCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (smpsCondition.isEmpty() || smpsCondition == null) {
            showToast("Select SMPS Condition");
            return false;
        } else if (smpsControlerStatus.isEmpty() || smpsControlerStatus == null) {
            showToast("Select SMPS Controler Status");
            return false;
        } else if (smpsEarthingStatus.isEmpty() || smpsEarthingStatus == null) {
            showToast("Select SMPS Earthing Status");
            return false;
        } else if (dcLoadCurrentInFloat.isEmpty() || dcLoadCurrentInFloat == null) {
            showToast("Select DC Load Current in Float");
            return false;
        } else if (base64DcLoadCurrentPhoto.isEmpty() || base64DcLoadCurrentPhoto == null) {
            showToast("Take Photo Of DC Load Current");
            return false;
        } else if (dcLoadAmpPh.isEmpty() || dcLoadAmpPh == null) {
            showToast("Select DC Load Amp/Ph");
            return false;
        } else return true;
    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < smpsCheckPointsData.size(); i++) {
            for (int j = i + 1; j < smpsCheckPointsData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (smpsCheckPointsData.get(i).getDetailsOfSmpsQrCodeScan().toString().equals(smpsCheckPointsData.get(j).getDetailsOfSmpsQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkValidationonSubmit(String methodFlag) {

        String totalNumberval = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewNoOfSmpsAvailableAtSiteVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
        if (totalNumberval.isEmpty() || totalNumberval == null) {
            showToast("Select No of SMPS available at site");
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
        } else if (Integer.valueOf(totalNumberval) > 0) {
            if ((smpsCheckPointsData.size() != Integer.valueOf(totalNumberval) && methodFlag.equals("onSubmit"))) {
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

                        /*str_pmSiteAcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteAcCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteAcpTypeOfFaultVal);*/
                        str_sypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteSmpsCheckPointsTextViewTypeOfFaultVal.setText(str_sypeOfFaultVal);
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

