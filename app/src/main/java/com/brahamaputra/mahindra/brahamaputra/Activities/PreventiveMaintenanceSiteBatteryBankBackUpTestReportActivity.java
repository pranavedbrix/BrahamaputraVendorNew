package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsChildData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsViLionBatteryData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmBatteryBankType;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmCustomerName;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmCircleName;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmStateName;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmSiteName;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmSiteId;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmSsaName;

public class PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity extends BaseActivity {

    //https://developer.android.com/training/scheduling/alarms

    private static final String TAG = PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.class.getSimpleName();

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutDetailsOfQRCodeScan;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewDetailsOfQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView;
    private ImageView mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView;

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSelectBatteryBank;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBank;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal;

    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNo;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomer;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircle;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewState;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteName;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteId;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsa;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignation;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryType;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal;
    private LinearLayout mLinearLayoutBatteryTypeLiIonReadings;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber1;
    private EditText mBdTestVoltageModuleReading1EditText1;
    private EditText mBdTestCurrentModuleReading1EditText1;
    private EditText mBdTestSocModuleReadingEditText1;
    private EditText mBdTestSohModuleReadingEditText1;
    private EditText mBdTestVoltageModuleReading1EditText2;
    private EditText mBdTestCurrentModuleReading1EditText2;
    private EditText mBdTestSocModuleReadingEditText2;
    private EditText mBdTestSohModuleReadingEditText2;
    private EditText mBdTestVoltageModuleReading1EditText3;
    private EditText mBdTestCurrentModuleReading1EditText3;
    private EditText mBdTestSocModuleReadingEditText3;
    private EditText mBdTestSohModuleReadingEditText3;
    private LinearLayout mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber;
    private EditText mBdTestCellReadingEditText1;
    private EditText mBdTestCellReadingEditText2;
    private EditText mBdTestCellReadingEditText3;
    private EditText mBdTestCellReadingEditText4;
    private EditText mBdTestCellReadingEditText5;
    private EditText mBdTestCellReadingEditText6;
    private EditText mBdTestCellReadingEditText7;
    private EditText mBdTestCellReadingEditText8;
    private EditText mBdTestCellReadingEditText9;
    private EditText mBdTestCellReadingEditText10;
    private EditText mBdTestCellReadingEditText11;
    private EditText mBdTestCellReadingEditText12;
    private EditText mBdTestCellReadingEditText13;
    private EditText mBdTestCellReadingEditText14;
    private EditText mBdTestCellReadingEditText15;
    private EditText mBdTestCellReadingEditText16;
    private EditText mBdTestCellReadingEditText17;
    private EditText mBdTestCellReadingEditText18;
    private EditText mBdTestCellReadingEditText19;
    private EditText mBdTestCellReadingEditText20;
    private EditText mBdTestCellReadingEditText21;
    private EditText mBdTestCellReadingEditText22;
    private EditText mBdTestCellReadingEditText23;
    private EditText mBdTestCellReadingEditText24;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewFloatVoltageInSmpsBusBarAfter30Min;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTotalLoadCurrentInAmps;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps;

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankMake;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMake;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankCapacity;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacity;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfWorkingRectifireModule;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModule;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteLoadOnBatteryInAmps;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewFloatVoltageBeforeBbTest;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest;

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSingleModuleRating;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSingleModuleRating;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating;

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfBatteryModule;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModule;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal;

    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAt;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal;

    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewLastReadingTaketAt;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal;

    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewPhotoOfBatteryBank;
    private ImageView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBank;
    private ImageView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView;
    private TextView mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewRemarks;
    private EditText mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks;

    private Button mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading;


    private String str_readingTaketAtVal = "";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_PhotoOfBatteryBank = 101;

    private String base64StringDetailsOfBatteryBankQRCodeScan = "";

    private String base64StringPhotoOfBatteryBank = "";
    private String imageFilePhotoOfBatteryBank;
    private Uri imageFileUriPhotoOfBatteryBank = null;


    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private BatteryBankCheckPointsParentData batteryBankCheckPointsParentData;
    private ArrayList<BatteryBankCheckPointsChildData> batteryBankCheckPointschildData;

    private ArrayList<BatteryBankCheckPointsViLionBatteryData> batteryBankCheckPointsViLionBatteryData;//13042019

    //BatteryBankCheckPointsData batteryBankCheckPointsData;
    private BatteryBankCheckPointsData batteryBankCheckPointsData;
    private ArrayList<BatteryBankCheckPointsData> BatteryBankCheckPointsDataList;
    private String str_pmSiteBbcpTestDoneAs;
    private int arrayIndex;

    private int totalAcCount = 7;//6
    private int currentPos = 0;

    DecimalConversion decimalConversion;

    ArrayList<BatteryType> batteryType;
    ArrayList<String> batteryQRCodeList;
    String str_pmSiteBbcpSelectBatteryBank = "";
    public static int submitMenu = 0;
    int setBtn = 0;
    ArrayList<String> StringListReadingTakenAt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_battery_bank_back_up_test_report);
        this.setTitle("Battery Bank Discharge Test");//Battery Bank Back Up Test Report
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        decimalConversion = new DecimalConversion();
        assignViews();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, userId, ticketName);
        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();

        batteryType = new ArrayList<BatteryType>();

        StringListReadingTakenAt = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankBackUpTestReport_readingTakenAt)));

        initCombo();
        setListner();
        if (!mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.getText().toString().trim().contains("Set")) {
            submitMenu = 0;
            invalidateOptionsMenu();
        }

        batteryBankCheckPointsParentData = new BatteryBankCheckPointsParentData();
        //BatteryBankCheckPointsDataList = new ArrayList<>();

        batteryBankCheckPointschildData = new ArrayList<>();
        batteryBankCheckPointsViLionBatteryData = new ArrayList<>();//13042019

        batteryBankCheckPointsData = new BatteryBankCheckPointsData();

        setClassBatteryBankCheckPointsData();

        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
            getInputDetails(batteryBankCheckPointsParentData);
        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            getInputDetailsForArray(batteryBankCheckPointsData);
        }
        visibilityofFieldOnBattery();
        //getInputDetails(batteryBankCheckPointsData);
        currentPos = 0;
        //setInputDetails();

        decimalEditTextListener();
        setfocus();
    }

    private void visibilityofFieldOnBattery() {
        if (sitePmBatteryBankType.equals("Li-Ion"))
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSingleModuleRating.setVisibility(View.VISIBLE);
    }

    private void bindQrListToField() {
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this,
                        batteryQRCodeList,
                        "Select QR Code",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        str_pmSiteBbcpSelectBatteryBank = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText(str_pmSiteBbcpSelectBatteryBank);
                        base64StringDetailsOfBatteryBankQRCodeScan = str_pmSiteBbcpSelectBatteryBank;
                        setValueToDepedentFieldsOnQr(position);
                    }
                });
            }
        });
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutDetailsOfQRCodeScan = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_detailsOfQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewDetailsOfQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_detailsOfQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_button_detailsOfQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_button_detailsOfQRCodeScanView);
        mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView = (ImageView) findViewById(R.id.button_ClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSelectBatteryBank = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_selectBatteryBank);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBank = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_selectBatteryBank);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_selectBatteryBankVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNo = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_ticketNo);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_ticketNoVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomer = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_customer);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_customerVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircle = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_circle);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_circleVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewState = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_state);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_stateVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteName = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_siteName);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_siteNameVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteId = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_siteId);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_siteIdVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsa = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_ssa);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_ssaVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignation = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_userFseNameDesignation);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_userFseNameDesignationVal);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryType = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_batteryType);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_batteryTypeVal);
        mLinearLayoutBatteryTypeLiIonReadings = (LinearLayout) findViewById(R.id.LinearLayoutBatteryTypeLiIonReadings);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber1 = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_battreyBankCellNumber_1);
        mBdTestVoltageModuleReading1EditText1 = (EditText) findViewById(R.id.bdTestVoltageModuleReading1EditText1);
        mBdTestCurrentModuleReading1EditText1 = (EditText) findViewById(R.id.bdTestCurrentModuleReading1EditText1);
        mBdTestSocModuleReadingEditText1 = (EditText) findViewById(R.id.bdTestSocModuleReadingEditText1);
        mBdTestSohModuleReadingEditText1 = (EditText) findViewById(R.id.bdTestSohModuleReadingEditText1);
        mBdTestVoltageModuleReading1EditText2 = (EditText) findViewById(R.id.bdTestVoltageModuleReading1EditText2);
        mBdTestCurrentModuleReading1EditText2 = (EditText) findViewById(R.id.bdTestCurrentModuleReading1EditText2);
        mBdTestSocModuleReadingEditText2 = (EditText) findViewById(R.id.bdTestSocModuleReadingEditText2);
        mBdTestSohModuleReadingEditText2 = (EditText) findViewById(R.id.bdTestSohModuleReadingEditText2);
        mBdTestVoltageModuleReading1EditText3 = (EditText) findViewById(R.id.bdTestVoltageModuleReading1EditText3);
        mBdTestCurrentModuleReading1EditText3 = (EditText) findViewById(R.id.bdTestCurrentModuleReading1EditText3);
        mBdTestSocModuleReadingEditText3 = (EditText) findViewById(R.id.bdTestSocModuleReadingEditText3);
        mBdTestSohModuleReadingEditText3 = (EditText) findViewById(R.id.bdTestSohModuleReadingEditText3);
        mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings = (LinearLayout) findViewById(R.id.LinearLayoutBatteryTypeVRLAAndVRLAPlusReadings);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_battreyBankCellNumber);
        mBdTestCellReadingEditText1 = (EditText) findViewById(R.id.bdTestCellReadingEditText1);
        mBdTestCellReadingEditText2 = (EditText) findViewById(R.id.bdTestCellReadingEditText2);
        mBdTestCellReadingEditText3 = (EditText) findViewById(R.id.bdTestCellReadingEditText3);
        mBdTestCellReadingEditText4 = (EditText) findViewById(R.id.bdTestCellReadingEditText4);
        mBdTestCellReadingEditText5 = (EditText) findViewById(R.id.bdTestCellReadingEditText5);
        mBdTestCellReadingEditText6 = (EditText) findViewById(R.id.bdTestCellReadingEditText6);
        mBdTestCellReadingEditText7 = (EditText) findViewById(R.id.bdTestCellReadingEditText7);
        mBdTestCellReadingEditText8 = (EditText) findViewById(R.id.bdTestCellReadingEditText8);
        mBdTestCellReadingEditText9 = (EditText) findViewById(R.id.bdTestCellReadingEditText9);
        mBdTestCellReadingEditText10 = (EditText) findViewById(R.id.bdTestCellReadingEditText10);
        mBdTestCellReadingEditText11 = (EditText) findViewById(R.id.bdTestCellReadingEditText11);
        mBdTestCellReadingEditText12 = (EditText) findViewById(R.id.bdTestCellReadingEditText12);
        mBdTestCellReadingEditText13 = (EditText) findViewById(R.id.bdTestCellReadingEditText13);
        mBdTestCellReadingEditText14 = (EditText) findViewById(R.id.bdTestCellReadingEditText14);
        mBdTestCellReadingEditText15 = (EditText) findViewById(R.id.bdTestCellReadingEditText15);
        mBdTestCellReadingEditText16 = (EditText) findViewById(R.id.bdTestCellReadingEditText16);
        mBdTestCellReadingEditText17 = (EditText) findViewById(R.id.bdTestCellReadingEditText17);
        mBdTestCellReadingEditText18 = (EditText) findViewById(R.id.bdTestCellReadingEditText18);
        mBdTestCellReadingEditText19 = (EditText) findViewById(R.id.bdTestCellReadingEditText19);
        mBdTestCellReadingEditText20 = (EditText) findViewById(R.id.bdTestCellReadingEditText20);
        mBdTestCellReadingEditText21 = (EditText) findViewById(R.id.bdTestCellReadingEditText21);
        mBdTestCellReadingEditText22 = (EditText) findViewById(R.id.bdTestCellReadingEditText22);
        mBdTestCellReadingEditText23 = (EditText) findViewById(R.id.bdTestCellReadingEditText23);
        mBdTestCellReadingEditText24 = (EditText) findViewById(R.id.bdTestCellReadingEditText24);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewFloatVoltageInSmpsBusBarAfter30Min = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_floatVoltageInSmpsBusBarAfter30Min);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_floatVoltageInSmpsBusBarAfter30Min);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTotalLoadCurrentInAmps = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_totalLoadCurrentInAmps);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_totalLoadCurrentInAmps);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_button_previousReading);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_button_nextReading);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankMake = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_batteryBankMake);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMake = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_batteryBankMake);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_batteryBankMakeVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankCapacity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_batteryBankCapacity);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacity = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_batteryBankCapacity);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_batteryBankCapacityVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfWorkingRectifireModule = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_noOfWorkingRectifireModule);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModule = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_noOfWorkingRectifireModule);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_noOfWorkingRectifireModuleVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteLoadOnBatteryInAmps = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_siteLoadOnBatteryInAmps);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_siteLoadOnBatteryInAmps);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewFloatVoltageBeforeBbTest = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_floatVoltageBeforeBbTest);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_floatVoltageBeforeBbTest);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSingleModuleRating = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_singleModuleRating);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSingleModuleRating = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_singleModuleRating);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_singleModuleRating);


        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfBatteryModule = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_linearLayout_noOfBatteryModule);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModule = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_noOfBatteryModule);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_noOfBatteryModuleVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAt = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_readingTaketAt);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_readingTaketAtVal);

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewLastReadingTaketAt = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_lastReadingTaketAt);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_lastReadingTaketAtVal);


        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewPhotoOfBatteryBank = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_photoOfBatteryBank);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBank = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_button_photoOfBatteryBank);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_button_photoOfBatteryBankView);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewRemarks = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_textView_remarks);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks = (EditText) findViewById(R.id.preventiveMaintenanceSiteBatteryBankBackUpTestReport_editText_remarks);

        ////////////
        mBdTestCellReadingEditText1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText3.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText4.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText5.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText6.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText7.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText8.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText9.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText10.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText11.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText12.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText13.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText14.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText15.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText16.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText17.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText18.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText19.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText20.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText21.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText22.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText23.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCellReadingEditText24.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        /////////////
        mBdTestVoltageModuleReading1EditText1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCurrentModuleReading1EditText1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestSocModuleReadingEditText1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestSohModuleReadingEditText1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestVoltageModuleReading1EditText2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCurrentModuleReading1EditText2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestSocModuleReadingEditText2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestSohModuleReadingEditText2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestVoltageModuleReading1EditText3.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestCurrentModuleReading1EditText3.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestSocModuleReadingEditText3.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mBdTestSohModuleReadingEditText3.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        //////////////
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        /////////////

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void decimalEditTextListener() {

        mBdTestCellReadingEditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText9.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText12.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText13.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText14.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText15.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText16.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText17.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText18.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText19.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText20.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText21.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText22.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText23.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCellReadingEditText24.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        /////////////
        mBdTestVoltageModuleReading1EditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCurrentModuleReading1EditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestSocModuleReadingEditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestSohModuleReadingEditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestVoltageModuleReading1EditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCurrentModuleReading1EditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestSocModuleReadingEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestSohModuleReadingEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestVoltageModuleReading1EditText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestCurrentModuleReading1EditText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestSocModuleReadingEditText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mBdTestSohModuleReadingEditText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        //////////////
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        /////////////
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

    }

    public void DecimalFormatConversion() {

        mBdTestCellReadingEditText1.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText1.getText().toString()));
        mBdTestCellReadingEditText2.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText2.getText().toString()));
        mBdTestCellReadingEditText3.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText3.getText().toString()));
        mBdTestCellReadingEditText4.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText4.getText().toString()));
        mBdTestCellReadingEditText5.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText5.getText().toString()));
        mBdTestCellReadingEditText6.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText6.getText().toString()));
        mBdTestCellReadingEditText7.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText7.getText().toString()));
        mBdTestCellReadingEditText8.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText8.getText().toString()));
        mBdTestCellReadingEditText9.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText9.getText().toString()));
        mBdTestCellReadingEditText10.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText10.getText().toString()));
        mBdTestCellReadingEditText11.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText11.getText().toString()));
        mBdTestCellReadingEditText12.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText12.getText().toString()));
        mBdTestCellReadingEditText13.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText13.getText().toString()));
        mBdTestCellReadingEditText14.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText14.getText().toString()));
        mBdTestCellReadingEditText15.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText15.getText().toString()));
        mBdTestCellReadingEditText16.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText16.getText().toString()));
        mBdTestCellReadingEditText17.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText17.getText().toString()));
        mBdTestCellReadingEditText18.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText18.getText().toString()));
        mBdTestCellReadingEditText19.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText19.getText().toString()));
        mBdTestCellReadingEditText20.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText20.getText().toString()));
        mBdTestCellReadingEditText21.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText21.getText().toString()));
        mBdTestCellReadingEditText22.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText22.getText().toString()));
        mBdTestCellReadingEditText23.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText23.getText().toString()));
        mBdTestCellReadingEditText24.setText(decimalConversion.convertDecimal(mBdTestCellReadingEditText24.getText().toString()));
        /////////////
        mBdTestVoltageModuleReading1EditText1.setText(decimalConversion.convertDecimal(mBdTestVoltageModuleReading1EditText1.getText().toString()));
        mBdTestCurrentModuleReading1EditText1.setText(decimalConversion.convertDecimal(mBdTestCurrentModuleReading1EditText1.getText().toString()));
        mBdTestSocModuleReadingEditText1.setText(decimalConversion.convertDecimal(mBdTestSocModuleReadingEditText1.getText().toString()));
        mBdTestSohModuleReadingEditText1.setText(decimalConversion.convertDecimal(mBdTestSohModuleReadingEditText1.getText().toString()));
        mBdTestVoltageModuleReading1EditText2.setText(decimalConversion.convertDecimal(mBdTestVoltageModuleReading1EditText2.getText().toString()));
        mBdTestCurrentModuleReading1EditText2.setText(decimalConversion.convertDecimal(mBdTestCurrentModuleReading1EditText2.getText().toString()));
        mBdTestSocModuleReadingEditText2.setText(decimalConversion.convertDecimal(mBdTestSocModuleReadingEditText2.getText().toString()));
        mBdTestSohModuleReadingEditText2.setText(decimalConversion.convertDecimal(mBdTestSohModuleReadingEditText2.getText().toString()));
        mBdTestVoltageModuleReading1EditText3.setText(decimalConversion.convertDecimal(mBdTestVoltageModuleReading1EditText3.getText().toString()));
        mBdTestCurrentModuleReading1EditText3.setText(decimalConversion.convertDecimal(mBdTestCurrentModuleReading1EditText3.getText().toString()));
        mBdTestSocModuleReadingEditText3.setText(decimalConversion.convertDecimal(mBdTestSocModuleReadingEditText3.getText().toString()));
        mBdTestSohModuleReadingEditText3.setText(decimalConversion.convertDecimal(mBdTestSohModuleReadingEditText3.getText().toString()));
        //////////////
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(decimalConversion.convertDecimal(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.getText().toString()));
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(decimalConversion.convertDecimal(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.getText().toString()));
        /////////////
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.setText(decimalConversion.convertDecimal(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.getText().toString()));
    }


    private void setInputDetails() {
        try {
            batteryBankCheckPointsParentData.setBdTestBatteryBankBackUpTicketNo(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestCustomer(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestCircle(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestState(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestSiteName(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestSiteId(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestSsa(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestUserFseNameDesignation(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal.getText().toString());
            batteryBankCheckPointsParentData.setTypeOfBattery(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString());

            batteryBankCheckPointsParentData.setBdTestBatteryBankMake(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestBatteryBankCapacity(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestNumberOfRectifireModuleWorking(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestSiteLoadOnBatteryInAmps(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.getText().toString());
            batteryBankCheckPointsParentData.setBdTestFloatVoltageBeforeBBTest(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest.getText().toString());
            batteryBankCheckPointsParentData.setBdTestSingleModuleRating(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating.getText().toString());
            batteryBankCheckPointsParentData.setBdTestNumberOfBatteryModule(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.getText().toString());
            //batteryBankCheckPointsParentData.setBdTestReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestLastReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.getText().toString());
            batteryBankCheckPointsParentData.setBdTestRemarks(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks.getText().toString());

            batteryBankCheckPointsParentData.setBdTestDetailsBatteryBankBackUpOfQRCodeScan(base64StringDetailsOfBatteryBankQRCodeScan);
            batteryBankCheckPointsParentData.setBdTestBase64StringPhotoOfBatteryBank(base64StringPhotoOfBatteryBank);

            if (batteryBankCheckPointschildData != null) {
                batteryBankCheckPointsParentData.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
            }

            //start 13042019
            if (batteryBankCheckPointsViLionBatteryData != null) {
                batteryBankCheckPointsParentData.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);
            }
            //end 13042019
            /*--New Code--*/
            if (batteryBankCheckPointsParentData.getBatteryBankCheckPointsData() == null) {

                int i = Integer.valueOf(batteryBankCheckPointsParentData.getNoOfBatteryBankAvailableAtSite());
                ArrayList<BatteryBankCheckPointsData> list = new ArrayList<BatteryBankCheckPointsData>();
                for (int j = 0; j > i; j++) {
                    list.add(new BatteryBankCheckPointsData());
                }
                batteryBankCheckPointsParentData.setBatteryBankCheckPointsData(list);
            } else if (batteryBankCheckPointsParentData.getBatteryBankCheckPointsData().size() < 1) {
                int i = Integer.valueOf(batteryBankCheckPointsParentData.getNoOfBatteryBankAvailableAtSite());

                ArrayList<BatteryBankCheckPointsData> list = new ArrayList<BatteryBankCheckPointsData>();
                for (int j = 0; j > i; j++) {
                    list.add(new BatteryBankCheckPointsData());
                }
                batteryBankCheckPointsParentData.setBatteryBankCheckPointsData(list);
            }
            /*--New Code--*/

            /*if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

            }

            pmSiteTransactionDetails.setBatteryBankCheckPointsParentData(batteryBankCheckPointsParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);*/
            submitDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInputDetailsForArray() {
        try {
            if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
                batteryBankCheckPointsParentData.setBdTestBatteryBankBackUpTicketNo(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestCustomer(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestCircle(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestState(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestSiteName(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestSiteId(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestSsa(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestUserFseNameDesignation(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal.getText().toString());
                batteryBankCheckPointsParentData.setTypeOfBattery(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString());

                batteryBankCheckPointsParentData.setBdTestBatteryBankMake(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestBatteryBankCapacity(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestNumberOfRectifireModuleWorking(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestSiteLoadOnBatteryInAmps(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.getText().toString());
                batteryBankCheckPointsParentData.setBdTestFloatVoltageBeforeBBTest(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest.getText().toString());
                batteryBankCheckPointsParentData.setBdTestSingleModuleRating(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating.getText().toString());
                batteryBankCheckPointsParentData.setBdTestNumberOfBatteryModule(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.getText().toString());
                //batteryBankCheckPointsParentData.setBdTestReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestLastReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.getText().toString());
                batteryBankCheckPointsParentData.setBdTestRemarks(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks.getText().toString());

                batteryBankCheckPointsParentData.setBdTestDetailsBatteryBankBackUpOfQRCodeScan(base64StringDetailsOfBatteryBankQRCodeScan);
                batteryBankCheckPointsParentData.setBdTestBase64StringPhotoOfBatteryBank(base64StringPhotoOfBatteryBank);

                if (batteryBankCheckPointschildData != null) {
                    batteryBankCheckPointsParentData.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);//VRLA
                }

                //start 13042019
                if (batteryBankCheckPointsViLionBatteryData != null) {
                    batteryBankCheckPointsParentData.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);//Li-Ion
                }
                //end 13042019

                if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                    String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                    Gson gson = new Gson();
                    pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                }

                pmSiteTransactionDetails.setBatteryBankCheckPointsParentData(batteryBankCheckPointsParentData);

                Gson gson2 = new GsonBuilder().create();
                String jsonString = gson2.toJson(pmSiteTransactionDetails);
                offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
            } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {

                batteryBankCheckPointsData.setDetailsOfBatteryBankQrCodeScan(batteryBankCheckPointsData.getDetailsOfBatteryBankQrCodeScan());
                batteryBankCheckPointsData.setStripBoltTightnessAsPerTorque(batteryBankCheckPointsData.getStripBoltTightnessAsPerTorque());
                batteryBankCheckPointsData.setPetroleumJellyApplied(batteryBankCheckPointsData.getPetroleumJellyApplied());
                batteryBankCheckPointsData.setBatteryVentPlugStatus(batteryBankCheckPointsData.getBatteryVentPlugStatus());
                batteryBankCheckPointsData.setBbEarthingStatus(batteryBankCheckPointsData.getBbEarthingStatus());
                batteryBankCheckPointsData.setBatteryBankDischargeTest(batteryBankCheckPointsData.getBatteryBankDischargeTest());
                batteryBankCheckPointsData.setBatteryBankQrCodeSelection(batteryBankCheckPointsData.getBatteryBankQrCodeSelection());

                batteryBankCheckPointsData.setBdTestBatteryBankBackUpTicketNo(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal.getText().toString());
                batteryBankCheckPointsData.setBdTestCustomer(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal.getText().toString());
                batteryBankCheckPointsData.setBdTestCircle(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal.getText().toString());
                batteryBankCheckPointsData.setBdTestState(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal.getText().toString());
                batteryBankCheckPointsData.setBdTestSiteName(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal.getText().toString());
                batteryBankCheckPointsData.setBdTestSiteId(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal.getText().toString());
                batteryBankCheckPointsData.setBdTestSsa(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal.getText().toString());
                batteryBankCheckPointsData.setBdTestUserFseNameDesignation(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal.getText().toString());
                batteryBankCheckPointsData.setTypeOfBattery(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString());

                batteryBankCheckPointsData.setBdTestBatteryBankMake(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.getText().toString());
                batteryBankCheckPointsData.setBdTestBatteryBankCapacity(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.getText().toString());
                batteryBankCheckPointsData.setBdTestNumberOfRectifireModuleWorking(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.getText().toString());
                batteryBankCheckPointsData.setBdTestSiteLoadOnBatteryInAmps(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.getText().toString());
                batteryBankCheckPointsData.setBdTestFloatVoltageBeforeBBTest(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest.getText().toString());
                batteryBankCheckPointsData.setBdTestSingleModuleRating(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating.getText().toString());
                batteryBankCheckPointsData.setBdTestNumberOfBatteryModule(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.getText().toString());

                //batteryBankCheckPointsData.setBdTestReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.getText().toString());

                batteryBankCheckPointsData.setBdTestLastReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.getText().toString());
                batteryBankCheckPointsData.setBdTestRemarks(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks.getText().toString());

                batteryBankCheckPointsData.setBdTestDetailsBatteryBankBackUpOfQRCodeScan(base64StringDetailsOfBatteryBankQRCodeScan);
                batteryBankCheckPointsData.setBdTestBase64StringPhotoOfBatteryBank(base64StringPhotoOfBatteryBank);

                if (batteryBankCheckPointschildData != null) {
                    batteryBankCheckPointsData.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
                }

                //start 13042019
                if (batteryBankCheckPointsViLionBatteryData != null) {
                    batteryBankCheckPointsData.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);
                }
                //end 13042019


                BatteryBankCheckPointsDataList.set(arrayIndex, batteryBankCheckPointsData);

                /*if (BatteryBankCheckPointsDataList.size() > 0) {
                    if (arrayIndex == BatteryBankCheckPointsDataList.size()) {
                        BatteryBankCheckPointsDataList.add(batteryBankCheckPointsListData);
                    } else if (arrayIndex < BatteryBankCheckPointsDataList.size()) {
                        BatteryBankCheckPointsDataList.set(arrayIndex, batteryBankCheckPointsListData);
                    }
                } else {
                    BatteryBankCheckPointsDataList.add(batteryBankCheckPointsListData);
                }

                batteryBankCheckPointsParentData.setBatteryBankCheckPointsData(BatteryBankCheckPointsDataList);

                if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                    String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                    Gson gson = new Gson();
                    pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                }

                pmSiteTransactionDetails.setBatteryBankCheckPointsParentData(batteryBankCheckPointsParentData);

                Gson gson2 = new GsonBuilder().create();
                String jsonString = gson2.toJson(pmSiteTransactionDetails);
                offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);*/
            }
            submitDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getInputDetails(BatteryBankCheckPointsParentData batteryBankCheckPointsParentData) {
        //try {
        if (batteryBankCheckPointsParentData != null) {

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal.setText(batteryBankCheckPointsParentData.getBdTestBatteryBankBackUpTicketNo() == null ? "" : batteryBankCheckPointsParentData.getBdTestBatteryBankBackUpTicketNo());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal.setText(batteryBankCheckPointsParentData.getBdTestCustomer() == null || batteryBankCheckPointsParentData.getBdTestCustomer().isEmpty() ? sitePmCustomerName : batteryBankCheckPointsParentData.getBdTestCustomer());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal.setText(batteryBankCheckPointsParentData.getBdTestCircle() == null || batteryBankCheckPointsParentData.getBdTestCircle().isEmpty() ? sitePmCircleName : batteryBankCheckPointsParentData.getBdTestCircle());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal.setText(batteryBankCheckPointsParentData.getBdTestState() == null || batteryBankCheckPointsParentData.getBdTestState().isEmpty() ? sitePmStateName : batteryBankCheckPointsParentData.getBdTestState());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal.setText(batteryBankCheckPointsParentData.getBdTestSiteName() == null || batteryBankCheckPointsParentData.getBdTestSiteName().isEmpty() ? sitePmSiteName : batteryBankCheckPointsParentData.getBdTestSiteName());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal.setText(batteryBankCheckPointsParentData.getBdTestSiteId() == null || batteryBankCheckPointsParentData.getBdTestSiteId().isEmpty() ? sitePmSiteId : batteryBankCheckPointsParentData.getBdTestSiteId());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal.setText(batteryBankCheckPointsParentData.getBdTestSsa() == null || batteryBankCheckPointsParentData.getBdTestSsa().isEmpty() ? sitePmSsaName : batteryBankCheckPointsParentData.getBdTestSsa());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal.setText(batteryBankCheckPointsParentData.getBdTestUserFseNameDesignation() == null || batteryBankCheckPointsParentData.getBdTestUserFseNameDesignation().isEmpty() ? sessionManager.getSessionUserFirstName() + " " + sessionManager.getSessionUserFirstLast() + " (" + sessionManager.getSessionDesignation() + ")" : batteryBankCheckPointsParentData.getBdTestUserFseNameDesignation());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.setText(batteryBankCheckPointsParentData.getTypeOfBattery() == null || batteryBankCheckPointsParentData.getTypeOfBattery().isEmpty() ? sitePmBatteryBankType : batteryBankCheckPointsParentData.getTypeOfBattery());

            mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings.setVisibility(View.GONE);
            mLinearLayoutBatteryTypeLiIonReadings.setVisibility(View.GONE);

            str_readingTaketAtVal = StringListReadingTakenAt.get(currentPos);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(str_readingTaketAtVal);

            if (batteryBankCheckPointsParentData.getTypeOfBattery().contains("VRLA")) {
                mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings.setVisibility(View.VISIBLE);

                if (batteryBankCheckPointsParentData.getBatteryBankCheckPointsChildData() != null) {
                    batteryBankCheckPointschildData.addAll(batteryBankCheckPointsParentData.getBatteryBankCheckPointsChildData() == null ? null : batteryBankCheckPointsParentData.getBatteryBankCheckPointsChildData());
                    if (batteryBankCheckPointschildData != null && batteryBankCheckPointschildData.size() > 0) {

                        batteryBankCheckPointschildData.clear();
                        batteryBankCheckPointschildData.addAll(batteryBankCheckPointsParentData.getBatteryBankCheckPointsChildData());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestReadingTakenAt() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestReadingTakenAt());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber.setText("Reading: #1");

                        mBdTestCellReadingEditText1.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading1() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading1());
                        mBdTestCellReadingEditText2.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading2() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading2());
                        mBdTestCellReadingEditText3.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading3() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading3());
                        mBdTestCellReadingEditText4.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading4() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading4());
                        mBdTestCellReadingEditText5.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading5() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading5());
                        mBdTestCellReadingEditText6.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading6() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading6());
                        mBdTestCellReadingEditText7.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading7() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading7());
                        mBdTestCellReadingEditText8.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading8() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading8());
                        mBdTestCellReadingEditText9.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading9() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading9());
                        mBdTestCellReadingEditText10.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading10() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading10());
                        mBdTestCellReadingEditText11.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading11() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading11());
                        mBdTestCellReadingEditText12.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading12() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading12());
                        mBdTestCellReadingEditText13.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading13() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading13());
                        mBdTestCellReadingEditText14.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading14() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading14());
                        mBdTestCellReadingEditText15.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading15() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading15());
                        mBdTestCellReadingEditText16.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading16() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading16());
                        mBdTestCellReadingEditText17.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading17() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading17());
                        mBdTestCellReadingEditText18.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading18() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading18());
                        mBdTestCellReadingEditText19.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading19() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading19());
                        mBdTestCellReadingEditText20.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading20() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading20());
                        mBdTestCellReadingEditText21.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading21() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading21());
                        mBdTestCellReadingEditText22.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading22() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading22());
                        mBdTestCellReadingEditText23.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading23() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading23());
                        mBdTestCellReadingEditText24.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading24() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestCellReading24());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min());
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointschildData.get(currentPos).getBdTestTotalLoadCurrentInAmps() == null ? "" : batteryBankCheckPointschildData.get(currentPos).getBdTestTotalLoadCurrentInAmps());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.GONE);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.VISIBLE);

                        if (totalAcCount > 1) {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Next Reading");
                        } else {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Set");//Finish
                        }
                    }
                }
            } else if (batteryBankCheckPointsParentData.getTypeOfBattery().equals("Li-Ion")) {
                mLinearLayoutBatteryTypeLiIonReadings.setVisibility(View.VISIBLE);

                //13042019
                if (batteryBankCheckPointsParentData.getBatteryBankCheckPointsViLionBatteryData() != null) {
                    batteryBankCheckPointsViLionBatteryData.addAll(batteryBankCheckPointsParentData.getBatteryBankCheckPointsViLionBatteryData() == null ? null : batteryBankCheckPointsParentData.getBatteryBankCheckPointsViLionBatteryData());
                    if (batteryBankCheckPointsViLionBatteryData != null && batteryBankCheckPointsViLionBatteryData.size() > 0) {

                        batteryBankCheckPointsViLionBatteryData.clear();
                        batteryBankCheckPointsViLionBatteryData.addAll(batteryBankCheckPointsParentData.getBatteryBankCheckPointsViLionBatteryData());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestReadingTakenAt() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestReadingTakenAt());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber1.setText("Reading: #1");

                        mBdTestVoltageModuleReading1EditText1.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestVoltageModuleReading1() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestVoltageModuleReading1());
                        mBdTestCurrentModuleReading1EditText1.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestCurrentModuleReading1() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestCurrentModuleReading1());
                        mBdTestSocModuleReadingEditText1.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSocModuleReading1() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSocModuleReading1());
                        mBdTestSohModuleReadingEditText1.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSohModuleReading1() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSohModuleReading1());
                        mBdTestVoltageModuleReading1EditText2.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestVoltageModuleReading2() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestVoltageModuleReading2());
                        mBdTestCurrentModuleReading1EditText2.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestCurrentModuleReading2() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestCurrentModuleReading2());
                        mBdTestSocModuleReadingEditText2.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSocModuleReading2() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSocModuleReading2());
                        mBdTestSohModuleReadingEditText2.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSohModuleReading2() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSohModuleReading2());
                        mBdTestVoltageModuleReading1EditText3.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestVoltageModuleReading3() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestVoltageModuleReading3());
                        mBdTestCurrentModuleReading1EditText3.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestCurrentModuleReading3() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestCurrentModuleReading3());
                        mBdTestSocModuleReadingEditText3.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSocModuleReading3() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSocModuleReading3());
                        mBdTestSohModuleReadingEditText3.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSohModuleReading3() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestSohModuleReading3());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min());
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestTotalLoadCurrentInAmps() == null ? "" : batteryBankCheckPointsViLionBatteryData.get(currentPos).getBdTestTotalLoadCurrentInAmps());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.GONE);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.VISIBLE);

                        if (totalAcCount > 1) {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Next Reading");
                        } else {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Set");//Finish
                        }
                    }
                }

            } else {
                mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings.setVisibility(View.GONE);
                mLinearLayoutBatteryTypeLiIonReadings.setVisibility(View.GONE);
            }


            //mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointsParentData.getBdTestFloatVoltageInSmpsBusBarAfter30Min() == null ? "" : batteryBankCheckPointsParentData.getBdTestFloatVoltageInSmpsBusBarAfter30Min());
            //mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointsParentData.getBdTestTotalLoadCurrentInAmps() == null ? "" : batteryBankCheckPointsParentData.getBdTestTotalLoadCurrentInAmps());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.setText(batteryBankCheckPointsParentData.getBdTestBatteryBankMake() == null ? "" : batteryBankCheckPointsParentData.getBdTestBatteryBankMake());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.setText(batteryBankCheckPointsParentData.getBdTestBatteryBankCapacity() == null ? "" : batteryBankCheckPointsParentData.getBdTestBatteryBankCapacity());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.setText(batteryBankCheckPointsParentData.getBdTestNumberOfRectifireModuleWorking() == null ? "" : batteryBankCheckPointsParentData.getBdTestNumberOfRectifireModuleWorking());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.setText(batteryBankCheckPointsParentData.getBdTestSiteLoadOnBatteryInAmps() == null ? "" : batteryBankCheckPointsParentData.getBdTestSiteLoadOnBatteryInAmps());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest.setText(batteryBankCheckPointsParentData.getBdTestFloatVoltageBeforeBBTest() == null ? "" : batteryBankCheckPointsParentData.getBdTestFloatVoltageBeforeBBTest());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating.setText(batteryBankCheckPointsParentData.getBdTestSingleModuleRating() == null ? "" : batteryBankCheckPointsParentData.getBdTestSingleModuleRating());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.setText(batteryBankCheckPointsParentData.getBdTestNumberOfBatteryModule() == null ? "" : batteryBankCheckPointsParentData.getBdTestNumberOfBatteryModule());

            //mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(batteryBankCheckPointsParentData.getBdTestReadingTakenAt() == null ? "" : batteryBankCheckPointsParentData.getBdTestReadingTakenAt());

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.setText(batteryBankCheckPointsParentData.getBdTestLastReadingTakenAt() == null ? "" : batteryBankCheckPointsParentData.getBdTestLastReadingTakenAt());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks.setText(batteryBankCheckPointsParentData.getBdTestRemarks() == null ? "" : batteryBankCheckPointsParentData.getBdTestRemarks());

            base64StringDetailsOfBatteryBankQRCodeScan = batteryBankCheckPointsParentData.getBdTestDetailsBatteryBankBackUpOfQRCodeScan() == null ? "" : batteryBankCheckPointsParentData.getBdTestDetailsBatteryBankBackUpOfQRCodeScan();
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.GONE);
            mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);

            }

            base64StringPhotoOfBatteryBank = batteryBankCheckPointsParentData.getBdTestBase64StringPhotoOfBatteryBank() == null ? "" : batteryBankCheckPointsParentData.getBdTestBase64StringPhotoOfBatteryBank();
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setVisibility(View.GONE);
            if (!base64StringPhotoOfBatteryBank.isEmpty() && base64StringPhotoOfBatteryBank != null) {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringPhotoOfBatteryBank);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriPhotoOfBatteryBank = Uri.parse(path);
            }

        }
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void getInputDetailsForArray(BatteryBankCheckPointsData batteryBankCheckPointsData) {
        if (batteryBankCheckPointsData != null) {

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewTicketNoVal.setText(batteryBankCheckPointsData.getBdTestBatteryBankBackUpTicketNo() == null ? "" : batteryBankCheckPointsData.getBdTestBatteryBankBackUpTicketNo());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal.setText(batteryBankCheckPointsData.getBdTestCustomer() == null || batteryBankCheckPointsData.getBdTestCustomer().isEmpty() ? sitePmCustomerName : batteryBankCheckPointsData.getBdTestCustomer());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal.setText(batteryBankCheckPointsData.getBdTestCircle() == null || batteryBankCheckPointsData.getBdTestCircle().isEmpty() ? sitePmCircleName : batteryBankCheckPointsData.getBdTestCircle());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal.setText(batteryBankCheckPointsData.getBdTestState() == null || batteryBankCheckPointsData.getBdTestState().isEmpty() ? sitePmStateName : batteryBankCheckPointsData.getBdTestState());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal.setText(batteryBankCheckPointsData.getBdTestSiteName() == null || batteryBankCheckPointsData.getBdTestSiteName().isEmpty() ? sitePmSiteName : batteryBankCheckPointsData.getBdTestSiteName());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal.setText(batteryBankCheckPointsData.getBdTestSiteId() == null || batteryBankCheckPointsData.getBdTestSiteId().isEmpty() ? sitePmSiteId : batteryBankCheckPointsData.getBdTestSiteId());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal.setText(batteryBankCheckPointsData.getBdTestSsa() == null || batteryBankCheckPointsData.getBdTestSsa().isEmpty() ? sitePmSsaName : batteryBankCheckPointsData.getBdTestSsa());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal.setText(batteryBankCheckPointsData.getBdTestUserFseNameDesignation() == null || batteryBankCheckPointsData.getBdTestUserFseNameDesignation().isEmpty() ? sessionManager.getSessionUserFirstName() + " " + sessionManager.getSessionUserFirstLast() + " (" + sessionManager.getSessionDesignation() + ")" : batteryBankCheckPointsData.getBdTestUserFseNameDesignation());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.setText(sitePmBatteryBankType);

            mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings.setVisibility(View.GONE);
            mLinearLayoutBatteryTypeLiIonReadings.setVisibility(View.GONE);

            str_readingTaketAtVal = StringListReadingTakenAt.get(currentPos);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(str_readingTaketAtVal);

            if (sitePmBatteryBankType.contains("VRLA")) {
                mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings.setVisibility(View.VISIBLE);

                if (batteryBankCheckPointsData.getBatteryBankCheckPointsChildData() != null) {
                    if (batteryBankCheckPointsData.getBatteryBankCheckPointsChildData() != null && batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().size() > 0) {

                        batteryBankCheckPointschildData.clear();
                        batteryBankCheckPointschildData.addAll(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestReadingTakenAt() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestReadingTakenAt());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber.setText("Reading: #1");

                        mBdTestCellReadingEditText1.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading1() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading1());
                        mBdTestCellReadingEditText2.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading2() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading2());
                        mBdTestCellReadingEditText3.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading3() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading3());
                        mBdTestCellReadingEditText4.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading4() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading4());
                        mBdTestCellReadingEditText5.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading5() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading5());
                        mBdTestCellReadingEditText6.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading6() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading6());
                        mBdTestCellReadingEditText7.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading7() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading7());
                        mBdTestCellReadingEditText8.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading8() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading8());
                        mBdTestCellReadingEditText9.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading9() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading9());
                        mBdTestCellReadingEditText10.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading10() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading10());
                        mBdTestCellReadingEditText11.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading11() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading11());
                        mBdTestCellReadingEditText12.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading12() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading12());
                        mBdTestCellReadingEditText13.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading13() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading13());
                        mBdTestCellReadingEditText14.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading14() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading14());
                        mBdTestCellReadingEditText15.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading15() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading15());
                        mBdTestCellReadingEditText16.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading16() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading16());
                        mBdTestCellReadingEditText17.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading17() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading17());
                        mBdTestCellReadingEditText18.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading18() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading18());
                        mBdTestCellReadingEditText19.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading19() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading19());
                        mBdTestCellReadingEditText20.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading20() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading20());
                        mBdTestCellReadingEditText21.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading21() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading21());
                        mBdTestCellReadingEditText22.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading22() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading22());
                        mBdTestCellReadingEditText23.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading23() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading23());
                        mBdTestCellReadingEditText24.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading24() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestCellReading24());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min());
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestTotalLoadCurrentInAmps() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsChildData().get(currentPos).getBdTestTotalLoadCurrentInAmps());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.GONE);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.VISIBLE);

                        if (totalAcCount > 1) {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Next Reading");
                        } else {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Set");//Finish
                        }
                    }
                }
            } else if (sitePmBatteryBankType.equals("Li-Ion")) {
                mLinearLayoutBatteryTypeLiIonReadings.setVisibility(View.VISIBLE);

                if (batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData() != null) {
                    if (batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData() != null && batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().size() > 0) {

                        batteryBankCheckPointsViLionBatteryData.clear();
                        batteryBankCheckPointsViLionBatteryData.addAll(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestReadingTakenAt() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestReadingTakenAt());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber.setText("Reading: #1");

                        mBdTestVoltageModuleReading1EditText1.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestVoltageModuleReading1() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestVoltageModuleReading1());
                        mBdTestCurrentModuleReading1EditText1.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestCurrentModuleReading1() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestCurrentModuleReading1());
                        mBdTestSocModuleReadingEditText1.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSocModuleReading1() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSocModuleReading1());
                        mBdTestSohModuleReadingEditText1.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSohModuleReading1() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSohModuleReading1());
                        mBdTestVoltageModuleReading1EditText2.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestVoltageModuleReading2() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestVoltageModuleReading2());
                        mBdTestCurrentModuleReading1EditText2.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestCurrentModuleReading2() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestCurrentModuleReading2());
                        mBdTestSocModuleReadingEditText2.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSocModuleReading2() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSocModuleReading2());
                        mBdTestSohModuleReadingEditText2.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSohModuleReading2() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSohModuleReading2());
                        mBdTestVoltageModuleReading1EditText3.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestVoltageModuleReading3() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestVoltageModuleReading3());
                        mBdTestCurrentModuleReading1EditText3.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestCurrentModuleReading3() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestCurrentModuleReading3());
                        mBdTestSocModuleReadingEditText3.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSocModuleReading3() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSocModuleReading3());
                        mBdTestSohModuleReadingEditText3.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSohModuleReading3() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestSohModuleReading3());
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestFloatVoltageInSmpsBusBarAfter30Min());
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestTotalLoadCurrentInAmps() == null ? "" : batteryBankCheckPointsData.getBatteryBankCheckPointsViLionBatteryData().get(currentPos).getBdTestTotalLoadCurrentInAmps());

                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.GONE);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.VISIBLE);

                        if (totalAcCount > 1) {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Next Reading");
                        } else {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Set");//Finish
                        }
                    }
                }


            } else {
                mLinearLayoutBatteryTypeVRLAAndVRLAPlusReadings.setVisibility(View.GONE);
                mLinearLayoutBatteryTypeLiIonReadings.setVisibility(View.GONE);
            }

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.setText(batteryBankCheckPointsData.getBdTestBatteryBankMake() == null ? "" : batteryBankCheckPointsData.getBdTestBatteryBankMake());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.setText(batteryBankCheckPointsData.getBdTestBatteryBankCapacity() == null ? "" : batteryBankCheckPointsData.getBdTestBatteryBankCapacity());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.setText(batteryBankCheckPointsData.getBdTestNumberOfRectifireModuleWorking() == null ? "" : batteryBankCheckPointsData.getBdTestNumberOfRectifireModuleWorking());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.setText(batteryBankCheckPointsData.getBdTestSiteLoadOnBatteryInAmps() == null ? "" : batteryBankCheckPointsData.getBdTestSiteLoadOnBatteryInAmps());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest.setText(batteryBankCheckPointsData.getBdTestFloatVoltageBeforeBBTest() == null ? "" : batteryBankCheckPointsData.getBdTestFloatVoltageBeforeBBTest());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating.setText(batteryBankCheckPointsData.getBdTestSingleModuleRating() == null ? "" : batteryBankCheckPointsData.getBdTestSingleModuleRating());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.setText(batteryBankCheckPointsData.getBdTestNumberOfBatteryModule() == null ? "" : batteryBankCheckPointsData.getBdTestNumberOfBatteryModule());

            //mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(batteryBankCheckPointsData.getBdTestReadingTakenAt() == null ? "" : batteryBankCheckPointsData.getBdTestReadingTakenAt());

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.setText(batteryBankCheckPointsData.getBdTestLastReadingTakenAt() == null ? "" : batteryBankCheckPointsData.getBdTestLastReadingTakenAt());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks.setText(batteryBankCheckPointsData.getBdTestRemarks() == null ? "" : batteryBankCheckPointsData.getBdTestRemarks());

            base64StringDetailsOfBatteryBankQRCodeScan = batteryBankCheckPointsData.getBdTestDetailsBatteryBankBackUpOfQRCodeScan() == null ? "" : batteryBankCheckPointsData.getBdTestDetailsBatteryBankBackUpOfQRCodeScan();
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.GONE);
            mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.GONE);
            checkQrInListArray(-1);

            base64StringPhotoOfBatteryBank = batteryBankCheckPointsData.getBdTestBase64StringPhotoOfBatteryBank() == null ? "" : batteryBankCheckPointsData.getBdTestBase64StringPhotoOfBatteryBank();
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setVisibility(View.GONE);
            if (!base64StringPhotoOfBatteryBank.isEmpty() && base64StringPhotoOfBatteryBank != null) {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringPhotoOfBatteryBank);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriPhotoOfBatteryBank = Uri.parse(path);
            }

        }

    }


    private void initCombo() {
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormatConversion();
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankBackUpTestReport_readingTakenAt))),
                        "Reading Taken At",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_readingTaketAtVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(str_readingTaketAtVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkValidationOfArrayFields() == true) {*/
                if (currentPos > 0) {
                    //Save current ac reading
                    setBtn = 0;
                    saveDGCheckRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayDGCheckRecords(currentPos);

                }
                /*}*/
                if (!mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.getText().toString().trim().contains("Set")) {
                    submitMenu = 0;
                    invalidateOptionsMenu();
                }
            }
        });
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalAcCount - 1)) {
                        //Save current ac reading
                        setBtn = 1;
                        saveDGCheckRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayDGCheckRecords(currentPos);

                    } else if (currentPos == (totalAcCount - 1)) {
                        saveDGCheckRecords(currentPos);
                        //setInputDetails();

                        //if (checkValidationOnChangeNoOfDgBatteryAvailable(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                        //submitDetails();
                        //startActivity(new Intent(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, PreventiveMaintenanceSiteEarthingCheckPointsActivity.class));
                        //finish();
                        //}

                    }
                }
                if (!mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.getText().toString().trim().contains("Set")) {
                    submitMenu = 0;
                    invalidateOptionsMenu();
                }
            }
        });

    }

    private boolean checkLastReadingTakenAt(String lastReadingTakenVal) {
        if (!lastReadingTakenVal.isEmpty()) {
            if (Integer.valueOf(lastReadingTakenVal) <= 180) {
                return true;
            } else {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.setText("");
                showToast("Last Reading Taken At is must be less than and equal to Reding Taken At value");
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal, InputMethodManager.SHOW_IMPLICIT);
                return false;
            }
        }
        return true;
    }

    public boolean checkValidationOfArrayFields() {

        String TypeOfBattery = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString().trim();

        String bdTestCellReading1 = mBdTestCellReadingEditText1.getText().toString();
        String bdTestCellReading2 = mBdTestCellReadingEditText2.getText().toString();
        String bdTestCellReading3 = mBdTestCellReadingEditText3.getText().toString();
        String bdTestCellReading4 = mBdTestCellReadingEditText4.getText().toString();
        String bdTestCellReading5 = mBdTestCellReadingEditText5.getText().toString();
        String bdTestCellReading6 = mBdTestCellReadingEditText6.getText().toString();
        String bdTestCellReading7 = mBdTestCellReadingEditText7.getText().toString();
        String bdTestCellReading8 = mBdTestCellReadingEditText8.getText().toString();
        String bdTestCellReading9 = mBdTestCellReadingEditText9.getText().toString();
        String bdTestCellReading10 = mBdTestCellReadingEditText10.getText().toString();
        String bdTestCellReading11 = mBdTestCellReadingEditText11.getText().toString();
        String bdTestCellReading12 = mBdTestCellReadingEditText12.getText().toString();
        String bdTestCellReading13 = mBdTestCellReadingEditText13.getText().toString();
        String bdTestCellReading14 = mBdTestCellReadingEditText14.getText().toString();
        String bdTestCellReading15 = mBdTestCellReadingEditText15.getText().toString();
        String bdTestCellReading16 = mBdTestCellReadingEditText16.getText().toString();
        String bdTestCellReading17 = mBdTestCellReadingEditText17.getText().toString();
        String bdTestCellReading18 = mBdTestCellReadingEditText18.getText().toString();
        String bdTestCellReading19 = mBdTestCellReadingEditText19.getText().toString();
        String bdTestCellReading20 = mBdTestCellReadingEditText20.getText().toString();
        String bdTestCellReading21 = mBdTestCellReadingEditText21.getText().toString();
        String bdTestCellReading22 = mBdTestCellReadingEditText22.getText().toString();
        String bdTestCellReading23 = mBdTestCellReadingEditText23.getText().toString();
        String bdTestCellReading24 = mBdTestCellReadingEditText24.getText().toString();

        ///////////////////////

        String VoltageModuleReading1 = mBdTestVoltageModuleReading1EditText1.getText().toString().trim();
        String CurrentModuleReading1 = mBdTestCurrentModuleReading1EditText1.getText().toString().trim();
        String SocModuleReading1 = mBdTestSocModuleReadingEditText1.getText().toString().trim();
        String SohModuleReading1 = mBdTestSohModuleReadingEditText1.getText().toString().trim();
        String VoltageModuleReading2 = mBdTestVoltageModuleReading1EditText2.getText().toString().trim();
        String CurrentModuleReading2 = mBdTestCurrentModuleReading1EditText2.getText().toString().trim();
        String SocModuleReading2 = mBdTestSocModuleReadingEditText2.getText().toString().trim();
        String SohModuleReading2 = mBdTestSohModuleReadingEditText2.getText().toString().trim();
        String VoltageModuleReading3 = mBdTestVoltageModuleReading1EditText3.getText().toString().trim();
        String CurrentModuleReading3 = mBdTestCurrentModuleReading1EditText3.getText().toString().trim();
        String SocModuleReading3 = mBdTestSocModuleReadingEditText3.getText().toString().trim();
        String SohModuleReading3 = mBdTestSohModuleReadingEditText3.getText().toString().trim();
        String FloatVoltageInSmpsBusBarAfter30Min = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.getText().toString().trim();
        String TotalLoadCurrentInAmps = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.getText().toString().trim();


        /*if ((bdTestCellReading1.isEmpty() || bdTestCellReading1 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 1");
            return false;
        } else if ((bdTestCellReading2.isEmpty() || bdTestCellReading2 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 2");
            return false;
        } else if ((bdTestCellReading3.isEmpty() || bdTestCellReading3 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 3");
            return false;
        } else if ((bdTestCellReading4.isEmpty() || bdTestCellReading4 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 4");
            return false;
        } else if ((bdTestCellReading5.isEmpty() || bdTestCellReading5 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 5");
            return false;
        } else if ((bdTestCellReading6.isEmpty() || bdTestCellReading6 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 6");
            return false;
        } else if ((bdTestCellReading7.isEmpty() || bdTestCellReading7 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 7");
            return false;
        } else if ((bdTestCellReading8.isEmpty() || bdTestCellReading8 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 8");
            return false;
        } else if ((bdTestCellReading9.isEmpty() || bdTestCellReading9 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 9");
            return false;
        } else if ((bdTestCellReading10.isEmpty() || bdTestCellReading10 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 10");
            return false;
        } else if ((bdTestCellReading11.isEmpty() || bdTestCellReading11 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 11");
            return false;
        } else if ((bdTestCellReading12.isEmpty() || bdTestCellReading12 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 12");
            return false;
        } else if ((bdTestCellReading13.isEmpty() || bdTestCellReading13 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 13");
            return false;
        } else if ((bdTestCellReading14.isEmpty() || bdTestCellReading14 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 14");
            return false;
        } else if ((bdTestCellReading15.isEmpty() || bdTestCellReading15 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 15");
            return false;
        } else if ((bdTestCellReading16.isEmpty() || bdTestCellReading16 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 16");
            return false;
        } else if ((bdTestCellReading17.isEmpty() || bdTestCellReading17 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 17");
            return false;
        } else if ((bdTestCellReading18.isEmpty() || bdTestCellReading18 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 18");
            return false;
        } else if ((bdTestCellReading19.isEmpty() || bdTestCellReading19 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 19");
            return false;
        } else if ((bdTestCellReading20.isEmpty() || bdTestCellReading20 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 20");
            return false;
        } else if ((bdTestCellReading21.isEmpty() || bdTestCellReading21 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 21");
            return false;
        } else if ((bdTestCellReading22.isEmpty() || bdTestCellReading22 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 22");
            return false;
        } else if ((bdTestCellReading23.isEmpty() || bdTestCellReading23 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 23");
            return false;
        } else if ((bdTestCellReading24.isEmpty() || bdTestCellReading24 == null) && TypeOfBattery.contains("VRLA")) {
            showToast("Enter Cell Reading 24");
            return false;
        } else if ((VoltageModuleReading1.isEmpty() || VoltageModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Voltage Module Reading 1.");
            return false;
        } else if ((CurrentModuleReading1.isEmpty() || CurrentModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Current Module Reading 1.");
            return false;
        } else if ((SocModuleReading1.isEmpty() || SocModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOC Module Reading 1.");
            return false;
        } else if ((SohModuleReading1.isEmpty() || SohModuleReading1 == null) && TypeOfBattery.equals("Li-Ion") && mBdTestSohModuleReadingEditText1.isEnabled()) {
            showToast("Enter SOH Module Reading 1.");
            return false;
        } else if ((VoltageModuleReading2.isEmpty() || VoltageModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Voltage Module Reading 2.");
            return false;
        } else if ((CurrentModuleReading2.isEmpty() || CurrentModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Current Module Reading 2.");
            return false;
        } else if ((SocModuleReading2.isEmpty() || SocModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOC Module Reading 2.");
            return false;
        } else if ((SohModuleReading2.isEmpty() || SohModuleReading2 == null) && TypeOfBattery.equals("Li-Ion") && mBdTestSohModuleReadingEditText2.isEnabled()) {
            showToast("Enter SOH Module Reading 2.");
            return false;
        } else if ((VoltageModuleReading3.isEmpty() || VoltageModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Voltage Module Reading 3.");
            return false;
        } else if ((CurrentModuleReading3.isEmpty() || CurrentModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Current Module Reading 3.");
            return false;
        } else if ((SocModuleReading3.isEmpty() || SocModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOC Module Reading 3.");
            return false;
        } else if ((SohModuleReading3.isEmpty() || SohModuleReading3 == null) && TypeOfBattery.equals("Li-Ion") && mBdTestSohModuleReadingEditText3.isEnabled()) {
            showToast("Enter SOH Module Reading 3.");
            return false;
        } else if (FloatVoltageInSmpsBusBarAfter30Min.isEmpty() || FloatVoltageInSmpsBusBarAfter30Min == null) {
            showToast("Enter Float Voltage in SMPS Bus Bar After 30 min.");
            return false;
        } else if (TotalLoadCurrentInAmps.isEmpty() || TotalLoadCurrentInAmps == null) {
            showToast("Enter Total Load Current(in Amps).");
            return false;
        }*/
        return true;
    }

    private void saveDGCheckRecords(int pos) {

        String ReadingTaketAtVal = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.getText().toString().trim();
        String TypeOfBattery = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString().trim();
        String FloatVoltageInSmpsBusBarAfter30Min = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.getText().toString().trim();
        String TotalLoadCurrentInAmps = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.getText().toString().trim();

        if (TypeOfBattery.equals("Li-Ion")) {

            String VoltageModuleReading1 = mBdTestVoltageModuleReading1EditText1.getText().toString().trim();
            String CurrentModuleReading1 = mBdTestCurrentModuleReading1EditText1.getText().toString().trim();
            String SocModuleReading1 = mBdTestSocModuleReadingEditText1.getText().toString().trim();
            String SohModuleReading1 = mBdTestSohModuleReadingEditText1.getText().toString().trim();

            String VoltageModuleReading2 = mBdTestVoltageModuleReading1EditText2.getText().toString().trim();
            String CurrentModuleReading2 = mBdTestCurrentModuleReading1EditText2.getText().toString().trim();
            String SocModuleReading2 = mBdTestSocModuleReadingEditText2.getText().toString().trim();
            String SohModuleReading2 = mBdTestSohModuleReadingEditText2.getText().toString().trim();

            String VoltageModuleReading3 = mBdTestVoltageModuleReading1EditText3.getText().toString().trim();
            String CurrentModuleReading3 = mBdTestCurrentModuleReading1EditText3.getText().toString().trim();
            String SocModuleReading3 = mBdTestSocModuleReadingEditText3.getText().toString().trim();
            String SohModuleReading3 = mBdTestSohModuleReadingEditText3.getText().toString().trim();

            BatteryBankCheckPointsViLionBatteryData batteryBankCheckPointsViLionBatteryDatacls = new BatteryBankCheckPointsViLionBatteryData(
                    ReadingTaketAtVal,
                    VoltageModuleReading1, VoltageModuleReading2, VoltageModuleReading3,
                    CurrentModuleReading1, CurrentModuleReading2, CurrentModuleReading3,
                    SocModuleReading1, SocModuleReading2, SocModuleReading3,
                    SohModuleReading1, SohModuleReading2, SohModuleReading3,
                    FloatVoltageInSmpsBusBarAfter30Min, TotalLoadCurrentInAmps
            );

            if (batteryBankCheckPointsViLionBatteryData.size() > 0) {
                if (pos == batteryBankCheckPointsViLionBatteryData.size()) {
                    batteryBankCheckPointsViLionBatteryData.add(batteryBankCheckPointsViLionBatteryDatacls);
                } else if (pos < batteryBankCheckPointsViLionBatteryData.size()) {
                    batteryBankCheckPointsViLionBatteryData.set(pos, batteryBankCheckPointsViLionBatteryDatacls);
                }
            } else {
                batteryBankCheckPointsViLionBatteryData.add(batteryBankCheckPointsViLionBatteryDatacls);
            }

        } else if (TypeOfBattery.contains("VRLA")) {
            String bdTestCellReading1 = mBdTestCellReadingEditText1.getText().toString();
            String bdTestCellReading2 = mBdTestCellReadingEditText2.getText().toString();
            String bdTestCellReading3 = mBdTestCellReadingEditText3.getText().toString();
            String bdTestCellReading4 = mBdTestCellReadingEditText4.getText().toString();
            String bdTestCellReading5 = mBdTestCellReadingEditText5.getText().toString();
            String bdTestCellReading6 = mBdTestCellReadingEditText6.getText().toString();
            String bdTestCellReading7 = mBdTestCellReadingEditText7.getText().toString();
            String bdTestCellReading8 = mBdTestCellReadingEditText8.getText().toString();
            String bdTestCellReading9 = mBdTestCellReadingEditText9.getText().toString();
            String bdTestCellReading10 = mBdTestCellReadingEditText10.getText().toString();
            String bdTestCellReading11 = mBdTestCellReadingEditText11.getText().toString();
            String bdTestCellReading12 = mBdTestCellReadingEditText12.getText().toString();
            String bdTestCellReading13 = mBdTestCellReadingEditText13.getText().toString();
            String bdTestCellReading14 = mBdTestCellReadingEditText14.getText().toString();
            String bdTestCellReading15 = mBdTestCellReadingEditText15.getText().toString();
            String bdTestCellReading16 = mBdTestCellReadingEditText16.getText().toString();
            String bdTestCellReading17 = mBdTestCellReadingEditText17.getText().toString();
            String bdTestCellReading18 = mBdTestCellReadingEditText18.getText().toString();
            String bdTestCellReading19 = mBdTestCellReadingEditText19.getText().toString();
            String bdTestCellReading20 = mBdTestCellReadingEditText20.getText().toString();
            String bdTestCellReading21 = mBdTestCellReadingEditText21.getText().toString();
            String bdTestCellReading22 = mBdTestCellReadingEditText22.getText().toString();
            String bdTestCellReading23 = mBdTestCellReadingEditText23.getText().toString();
            String bdTestCellReading24 = mBdTestCellReadingEditText24.getText().toString();

            BatteryBankCheckPointsChildData batteryBankCheckPointsChildDataCls = new BatteryBankCheckPointsChildData(
                    ReadingTaketAtVal,
                    bdTestCellReading1, bdTestCellReading2, bdTestCellReading3,
                    bdTestCellReading4, bdTestCellReading5, bdTestCellReading6,
                    bdTestCellReading7, bdTestCellReading8, bdTestCellReading9,
                    bdTestCellReading10, bdTestCellReading11, bdTestCellReading12,
                    bdTestCellReading13, bdTestCellReading14, bdTestCellReading15,
                    bdTestCellReading16, bdTestCellReading17, bdTestCellReading18,
                    bdTestCellReading19, bdTestCellReading20, bdTestCellReading21,
                    bdTestCellReading22, bdTestCellReading23, bdTestCellReading24,
                    FloatVoltageInSmpsBusBarAfter30Min, TotalLoadCurrentInAmps
            );

            if (batteryBankCheckPointschildData.size() > 0) {
                if (pos == batteryBankCheckPointschildData.size()) {
                    batteryBankCheckPointschildData.add(batteryBankCheckPointsChildDataCls);
                } else if (pos < batteryBankCheckPointschildData.size()) {
                    batteryBankCheckPointschildData.set(pos, batteryBankCheckPointsChildDataCls);
                }
            } else {
                batteryBankCheckPointschildData.add(batteryBankCheckPointsChildDataCls);
            }
        }


        if (mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.getText().toString().trim().contains("Set") && setBtn == 1) {
            showToast("All readings are set successfully.");
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.VISIBLE);
            submitMenu = 1;
            invalidateOptionsMenu();
        }

    }

    private void displayDGCheckRecords(int pos) {

        String TypeOfBattery = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString().trim();
        str_readingTaketAtVal = StringListReadingTakenAt.get(pos);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.setText(str_readingTaketAtVal);

        if (TypeOfBattery.contains("VRLA")) {
            if (batteryBankCheckPointschildData.size() > 0 && pos < batteryBankCheckPointschildData.size()) {

                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber.setText("Reading: #" + (pos + 1));


                mBdTestCellReadingEditText1.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading1());
                mBdTestCellReadingEditText2.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading2());
                mBdTestCellReadingEditText3.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading3());
                mBdTestCellReadingEditText4.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading4());
                mBdTestCellReadingEditText5.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading5());
                mBdTestCellReadingEditText6.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading6());
                mBdTestCellReadingEditText7.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading7());
                mBdTestCellReadingEditText8.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading8());
                mBdTestCellReadingEditText9.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading9());
                mBdTestCellReadingEditText10.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading10());
                mBdTestCellReadingEditText11.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading11());
                mBdTestCellReadingEditText12.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading12());
                mBdTestCellReadingEditText13.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading13());
                mBdTestCellReadingEditText14.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading14());
                mBdTestCellReadingEditText15.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading15());
                mBdTestCellReadingEditText16.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading16());
                mBdTestCellReadingEditText17.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading17());
                mBdTestCellReadingEditText18.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading18());
                mBdTestCellReadingEditText19.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading19());
                mBdTestCellReadingEditText20.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading20());
                mBdTestCellReadingEditText21.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading21());
                mBdTestCellReadingEditText22.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading22());
                mBdTestCellReadingEditText23.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading23());
                mBdTestCellReadingEditText24.setText(batteryBankCheckPointschildData.get(pos).getBdTestCellReading24());
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointschildData.get(pos).getBdTestFloatVoltageInSmpsBusBarAfter30Min());
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointschildData.get(pos).getBdTestTotalLoadCurrentInAmps());

                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.VISIBLE);
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.VISIBLE);

            } else {
                clearFields(pos);
            }
        } else if (TypeOfBattery.equals("Li-Ion")) {
            visibilityOfSOH(pos);
            if (batteryBankCheckPointsViLionBatteryData.size() > 0 && pos < batteryBankCheckPointsViLionBatteryData.size()) {

                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber1.setText("Reading: #" + (pos + 1));

                mBdTestVoltageModuleReading1EditText1.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestVoltageModuleReading1());
                mBdTestCurrentModuleReading1EditText1.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestCurrentModuleReading1());
                mBdTestSocModuleReadingEditText1.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestSocModuleReading1());
                mBdTestSohModuleReadingEditText1.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestSohModuleReading1());

                mBdTestVoltageModuleReading1EditText2.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestVoltageModuleReading2());
                mBdTestCurrentModuleReading1EditText2.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestCurrentModuleReading2());
                mBdTestSocModuleReadingEditText2.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestSocModuleReading2());
                mBdTestSohModuleReadingEditText2.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestSohModuleReading2());

                mBdTestVoltageModuleReading1EditText3.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestVoltageModuleReading3());
                mBdTestCurrentModuleReading1EditText3.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestCurrentModuleReading3());
                mBdTestSocModuleReadingEditText3.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestSocModuleReading3());
                mBdTestSohModuleReadingEditText3.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestSohModuleReading3());

                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestFloatVoltageInSmpsBusBarAfter30Min());
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText(batteryBankCheckPointsViLionBatteryData.get(pos).getBdTestTotalLoadCurrentInAmps());

                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.VISIBLE);
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setVisibility(View.VISIBLE);

            } else {
                clearFields(pos);
            }
        }


        if (pos > 0 && pos < (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Set");//Finish
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalAcCount - 1)) {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Set");//Finish
            } else {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonNextReading.setText("Next Reading");
            }
        }
    }

    public void visibilityOfSOH(int indexPos) {

        mBdTestSohModuleReadingEditText1.setText("");
        mBdTestSohModuleReadingEditText2.setText("");
        mBdTestSohModuleReadingEditText3.setText("");
        mBdTestSohModuleReadingEditText1.setEnabled(false);
        mBdTestSohModuleReadingEditText2.setEnabled(false);
        mBdTestSohModuleReadingEditText3.setEnabled(false);
        if (indexPos < 1) {
            mBdTestSohModuleReadingEditText1.setEnabled(true);
            mBdTestSohModuleReadingEditText2.setEnabled(true);
            mBdTestSohModuleReadingEditText3.setEnabled(true);
        }
    }

    public void clearFields(int indexPos) {

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber.setText("Reading: #" + (indexPos + 1));
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBattreyBankCellNumber1.setText("Reading: #" + (indexPos + 1));

        mBdTestCellReadingEditText1.setText("");
        mBdTestCellReadingEditText2.setText("");
        mBdTestCellReadingEditText3.setText("");
        mBdTestCellReadingEditText4.setText("");
        mBdTestCellReadingEditText5.setText("");
        mBdTestCellReadingEditText6.setText("");
        mBdTestCellReadingEditText7.setText("");
        mBdTestCellReadingEditText8.setText("");
        mBdTestCellReadingEditText9.setText("");
        mBdTestCellReadingEditText10.setText("");
        mBdTestCellReadingEditText11.setText("");
        mBdTestCellReadingEditText12.setText("");
        mBdTestCellReadingEditText13.setText("");
        mBdTestCellReadingEditText14.setText("");
        mBdTestCellReadingEditText15.setText("");
        mBdTestCellReadingEditText16.setText("");
        mBdTestCellReadingEditText17.setText("");
        mBdTestCellReadingEditText18.setText("");
        mBdTestCellReadingEditText19.setText("");
        mBdTestCellReadingEditText20.setText("");
        mBdTestCellReadingEditText21.setText("");
        mBdTestCellReadingEditText22.setText("");
        mBdTestCellReadingEditText23.setText("");
        mBdTestCellReadingEditText24.setText("");

        mBdTestVoltageModuleReading1EditText1.setText("");
        mBdTestCurrentModuleReading1EditText1.setText("");
        mBdTestSocModuleReadingEditText1.setText("");
        mBdTestSohModuleReadingEditText1.setText("");
        mBdTestVoltageModuleReading1EditText2.setText("");
        mBdTestCurrentModuleReading1EditText2.setText("");
        mBdTestSocModuleReadingEditText2.setText("");
        mBdTestSohModuleReadingEditText2.setText("");
        mBdTestVoltageModuleReading1EditText3.setText("");
        mBdTestCurrentModuleReading1EditText3.setText("");
        mBdTestSocModuleReadingEditText3.setText("");
        mBdTestSohModuleReadingEditText3.setText("");
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.setText("");
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.setText("");

    }

    public void setfocus() {

        mBdTestVoltageModuleReading1EditText1.focusSearch(View.FOCUS_RIGHT);
        mBdTestCurrentModuleReading1EditText1.requestFocus();

        mBdTestCurrentModuleReading1EditText1.focusSearch(View.FOCUS_RIGHT);
        mBdTestSocModuleReadingEditText1.requestFocus();

        mBdTestSocModuleReadingEditText1.focusSearch(View.FOCUS_RIGHT);
        mBdTestSohModuleReadingEditText1.requestFocus();

        mBdTestSohModuleReadingEditText1.focusSearch(View.FOCUS_RIGHT);
        mBdTestVoltageModuleReading1EditText2.requestFocus();

        mBdTestVoltageModuleReading1EditText2.focusSearch(View.FOCUS_RIGHT);
        mBdTestCurrentModuleReading1EditText2.requestFocus();

        mBdTestCurrentModuleReading1EditText2.focusSearch(View.FOCUS_RIGHT);
        mBdTestSocModuleReadingEditText2.requestFocus();

        mBdTestSocModuleReadingEditText2.focusSearch(View.FOCUS_RIGHT);
        mBdTestSohModuleReadingEditText2.requestFocus();

        mBdTestSohModuleReadingEditText2.focusSearch(View.FOCUS_RIGHT);
        mBdTestVoltageModuleReading1EditText3.requestFocus();

        mBdTestVoltageModuleReading1EditText3.focusSearch(View.FOCUS_RIGHT);
        mBdTestCurrentModuleReading1EditText3.requestFocus();

        mBdTestCurrentModuleReading1EditText3.focusSearch(View.FOCUS_RIGHT);
        mBdTestSocModuleReadingEditText3.requestFocus();

        mBdTestSocModuleReadingEditText3.focusSearch(View.FOCUS_RIGHT);
        mBdTestSohModuleReadingEditText3.requestFocus();

        mBdTestSohModuleReadingEditText3.focusSearch(View.FOCUS_RIGHT);
        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.requestFocus();

    }

    private void setClassBatteryBankCheckPointsData() {
        /*try {*/

        Intent intent = getIntent();
        str_pmSiteBbcpTestDoneAs = intent.getStringExtra("str_pmSiteBbcpTestDoneAs");
        arrayIndex = intent.getIntExtra("arrayIndex", 0);
        batteryType = (ArrayList<BatteryType>) intent.getSerializableExtra("batteryType");
        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
            batteryBankCheckPointsParentData = (BatteryBankCheckPointsParentData) intent.getSerializableExtra("batteryBankCheckPointsParentData");
        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            //batteryBankCheckPointsData = (BatteryBankCheckPointsParentData) intent.getSerializableExtra("batteryBankCheckPointsParentData");
            BatteryBankCheckPointsDataList = (ArrayList<BatteryBankCheckPointsData>) intent.getSerializableExtra("batteryBankCheckPointsDataList");
            if (BatteryBankCheckPointsDataList != null) {
                batteryBankCheckPointsData = BatteryBankCheckPointsDataList.get(arrayIndex);
            }
        }

        visibilityOfDependentFieldsOnTestDoneAs(str_pmSiteBbcpTestDoneAs);
        //batteryBankCheckPointsData = (BatteryBankCheckPointsParentData) intent.getSerializableExtra("batteryBankCheckPointsDataList");

        /*i.putExtra("str_pmSiteBbcpTestDoneAs", str_pmSiteBbcpTestDoneAs);
        i.putExtra("arrayIndex", currentPos);
        i.putExtra("batteryBankCheckPointsDataList", batteryBankCheckPointsData);*/
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
        invalidateOptionsMenu();
    }

    private void visibilityOfDependentFieldsOnTestDoneAs(String str_pmSiteBbcpTestDoneAs) {
        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
            base64StringDetailsOfBatteryBankQRCodeScan = "";
            mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.GONE);

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.setText("");

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutDetailsOfQRCodeScan.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSelectBatteryBank.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankMake.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankCapacity.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfWorkingRectifireModule.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfBatteryModule.setVisibility(View.GONE);
        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {

            if (batteryType != null) {
                batteryQRCodeList = new ArrayList<String>();
                getBatteryQRCodeList(batteryType);
                bindQrListToField();
            }

            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutDetailsOfQRCodeScan.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutSelectBatteryBank.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankMake.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutBatteryBankCapacity.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfWorkingRectifireModule.setVisibility(View.VISIBLE);
            if (sitePmBatteryBankType.equals("Li-Ion")) {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportLinearLayoutNoOfBatteryModule.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setListner() {

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DetailsOfBatteryBankBackUpQRCodeScan();
                }
            }
        });

        mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDetailsOfBatteryBankQRCodeScan = "";
                mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText("");
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    PhotoOfBatteryBank();
                }
            }
        });

        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriPhotoOfBatteryBank != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, imageFileUriPhotoOfBatteryBank);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void PhotoOfBatteryBank() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFilePhotoOfBatteryBank = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFilePhotoOfBatteryBank);
            imageFileUriPhotoOfBatteryBank = FileProvider.getUriForFile(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriPhotoOfBatteryBank);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_PhotoOfBatteryBank);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DetailsOfBatteryBankBackUpQRCodeScan() {
        try {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("Scan QR Code");
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

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_CAMERA_PhotoOfBatteryBank:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriPhotoOfBatteryBank != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriPhotoOfBatteryBank);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringPhotoOfBatteryBank = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFilePhotoOfBatteryBank = "";
                    imageFileUriPhotoOfBatteryBank = null;
                    mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonPhotoOfBatteryBankView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringDetailsOfBatteryBankQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        /*Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {*/
                        base64StringDetailsOfBatteryBankQRCodeScan = result.getContents();


                        ////
                        checkQrInListArray(0);
                        /*if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                            if (batteryQRCodeList.size() > 0) {
                                for (int i = 0; i < batteryQRCodeList.size(); i++) {
                                    if (base64StringDetailsOfBatteryBankQRCodeScan.equals(batteryQRCodeList.get(i).toString())) {
                                        str_pmSiteBbcpSelectBatteryBank = base64StringDetailsOfBatteryBankQRCodeScan;
                                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText(str_pmSiteBbcpSelectBatteryBank);
                                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                                        mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                                        setValueToDepedentFieldsOnQr(i);
                                    }
                                }
                            } else {
                                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText("No Data Found");
                                //No sites found
                            }
                        }*/
                        ////



                        /*if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                            mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                        }*/
                        /*} else {
                            base64StringDetailsOfBatteryBankQRCodeScan = "";
                            showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                        }*/
                    }
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
        /*shareItem.setVisible(true);
        if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            shareItem.setVisible(false);
        }*/
        shareItem.setVisible(false);

        if (!str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            shareItem.setVisible(true);
        }

        if (submitMenu == 1) {
            shareItem.setVisible(true);
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
                alertDialogManager = new AlertDialogManager(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this);
                alertDialogManager.Dialog("Confirmation", "Do you want to save this record?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        if (validation() == true) {
                            if (checkLastReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.getText().toString().trim())) {
                                if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
                                    setInputDetails();
                                } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
                                    setInputDetailsForArray();
                                }
                                //setInputDetails();
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }).show();

                /*if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
                    setInputDetails();
                } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
                    setInputDetailsForArray();
                }
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to save this record?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                if (validation() == true) {
                    if (checkLastReadingTakenAt(mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.getText().toString().trim())) {
                        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
                            setInputDetails();
                        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
                            setInputDetailsForArray();
                        }
                        //setInputDetails();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }

            @Override
            public void onNegativeClick() {

            }
        }).show();

    }

    private void submitDetails() {

        /*Intent data = new Intent(PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.this, PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.class);
        data.putExtra("str_pmSiteBbcpTestDoneAs", str_pmSiteBbcpTestDoneAs);
        data.putExtra("arrayIndex", arrayIndex);
        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
            data.putExtra("batteryBankCheckPointsParentData", batteryBankCheckPointsParentData);
        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            data.putExtra("batteryBankCheckPointsDataList", BatteryBankCheckPointsDataList);
        }
        data.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(data, Activity.RESULT_OK);*/

        Intent data = new Intent();
        //data.putExtra("batteryBankCheckPointsParentData", batteryBankCheckPointsData);

        data.putExtra("str_pmSiteBbcpTestDoneAs", str_pmSiteBbcpTestDoneAs);
        data.putExtra("arrayIndex", arrayIndex);
        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
            data.putExtra("batteryBankCheckPointsParentData", batteryBankCheckPointsParentData);
        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            data.putExtra("batteryBankCheckPointsDataList", BatteryBankCheckPointsDataList);
        }

        setResult(Activity.RESULT_OK, data);
        finish();
        //finishActivity(Activity.RESULT_OK);
    }

    public void getBatteryQRCodeList(ArrayList<BatteryType> batteryTypeList) {
        for (BatteryType batteryType : batteryTypeList) {
            batteryQRCodeList.add(batteryType.getQRCodeScan());
        }
    }

    public void setValueToDepedentFieldsOnQr(int valueDepedentOnQr) {
        if (batteryType.size() > 0) {
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.setText(batteryType.get(valueDepedentOnQr).getManufactureMakeModel().toString().trim());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.setText(batteryType.get(valueDepedentOnQr).getCapacityInAH().toString().trim());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.setText(batteryType.get(valueDepedentOnQr).getNoOfRectifireModuleWorking().toString().trim());
            mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.setText(batteryType.get(valueDepedentOnQr).getNoOfBatteryModule().toString().trim());
        }
    }

    public void checkQrInListArray(int j) {
        ////
        if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
            if (batteryQRCodeList.size() > 0) {
                for (int i = 0; i < batteryQRCodeList.size(); i++) {
                    if (base64StringDetailsOfBatteryBankQRCodeScan.equals(batteryQRCodeList.get(i).toString())) {
                        str_pmSiteBbcpSelectBatteryBank = base64StringDetailsOfBatteryBankQRCodeScan;
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText(str_pmSiteBbcpSelectBatteryBank);
                        mPreventiveMaintenanceSiteBatteryBankBackUpTestReportButtonDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearBatteryBankBackUpTestReportDetailsOfQRCodeScanView.setVisibility(View.VISIBLE);
                        if (j == 0) {
                            setValueToDepedentFieldsOnQr(i);
                            break;
                        }
                    }
                }
            } else {
                mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.setText("No Data Found");
                //No sites found
            }
        }
        ////
    }

    private boolean validation() {
        String Customer = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCustomerVal.getText().toString().trim();
        String Circle = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewCircleVal.getText().toString().trim();
        String State = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewStateVal.getText().toString().trim();
        String SiteName = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteNameVal.getText().toString().trim();
        String SiteId = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSiteIdVal.getText().toString().trim();
        String Ssa = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSsaVal.getText().toString().trim();
        String UserFseNameDesignation = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewUserFseNameDesignationVal.getText().toString().trim();
        String TypeOfBattery = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString().trim();

        //if (mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryTypeVal.getText().toString().equals("Li-Ion")) {
        String VoltageModuleReading1 = mBdTestVoltageModuleReading1EditText1.getText().toString().trim();
        String CurrentModuleReading1 = mBdTestCurrentModuleReading1EditText1.getText().toString().trim();
        String SocModuleReading1 = mBdTestSocModuleReadingEditText1.getText().toString().trim();
        String SohModuleReading1 = mBdTestSohModuleReadingEditText1.getText().toString().trim();
        String VoltageModuleReading2 = mBdTestVoltageModuleReading1EditText2.getText().toString().trim();
        String CurrentModuleReading2 = mBdTestCurrentModuleReading1EditText2.getText().toString().trim();
        String SocModuleReading2 = mBdTestSocModuleReadingEditText2.getText().toString().trim();
        String SohModuleReading2 = mBdTestSohModuleReadingEditText2.getText().toString().trim();
        String VoltageModuleReading3 = mBdTestVoltageModuleReading1EditText3.getText().toString().trim();
        String CurrentModuleReading3 = mBdTestCurrentModuleReading1EditText3.getText().toString().trim();
        String SocModuleReading3 = mBdTestSocModuleReadingEditText3.getText().toString().trim();
        String SohModuleReading3 = mBdTestSohModuleReadingEditText3.getText().toString().trim();
        //}

        String FloatVoltageInSmpsBusBarAfter30Min = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageInSmpsBusBarAfter30Min.getText().toString().trim();
        String TotalLoadCurrentInAmps = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextTotalLoadCurrentInAmps.getText().toString().trim();


        String BatteryBankMake = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankMakeVal.getText().toString().trim();
        String BatteryBankCapacity = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewBatteryBankCapacityVal.getText().toString().trim();
        //String NumberOfRectifireModuleWorking = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfWorkingRectifireModuleVal.getText().toString().trim();
        String SiteLoadOnBatteryInAmps = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSiteLoadOnBatteryInAmps.getText().toString().trim();
        String FloatVoltageBeforeBBTest = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextFloatVoltageBeforeBbTest.getText().toString().trim();
        String SingleModuleRating = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextSingleModuleRating.getText().toString().trim();//for Li-Ion
        //String NumberOfBatteryModule = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewNoOfBatteryModuleVal.getText().toString().trim();//for Li-Ion
        //String ReadingTakenAt = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewReadingTaketAtVal.getText().toString().trim();
        String LastReadingTakenAt = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextLastReadingTaketAtVal.getText().toString().trim();
        String Remarks = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportEditTextRemarks.getText().toString().trim();

        String DetailsBatteryBankBackUpOfQRCodeScan = mPreventiveMaintenanceSiteBatteryBankBackUpTestReportTextViewSelectBatteryBankVal.getText().toString().trim();//individual
        String Base64StringPhotoOfBatteryBank = base64StringPhotoOfBatteryBank;



        /*if (batteryBankCheckPointschildData != null) {
            batteryBankCheckPointsParentData.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
        }*/

        if ((DetailsBatteryBankBackUpOfQRCodeScan.isEmpty() || DetailsBatteryBankBackUpOfQRCodeScan == null) && str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            showToast("Scan QR Code.");
            return false;
        } else if (Customer.isEmpty() || Customer == null) {
            showToast("Customer must be required.");
            return false;
        } else if (Circle.isEmpty() || Circle == null) {
            showToast("Circle must be required.");
            return false;
        } else if (State.isEmpty() || State == null) {
            showToast("State must be required.");
            return false;
        } else if (SiteName.isEmpty() || SiteName == null) {
            showToast("Site Name must be required.");
            return false;
        } else if (SiteId.isEmpty() || SiteId == null) {
            showToast("Site Id must be required.");
            return false;
        } else if (Ssa.isEmpty() || Ssa == null) {
            showToast("SSA must be required.");
            return false;
        } else if (UserFseNameDesignation.isEmpty() || UserFseNameDesignation == null) {
            showToast("User/FSE Name & Designation must be required.");
            return false;
        } else if (TypeOfBattery.isEmpty() || TypeOfBattery == null) {
            showToast("Type of Battery must be required.");
            return false;
        }
        //////////
        /*else if ((VoltageModuleReading1.isEmpty() || VoltageModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Voltage Module Reading 1.");
            return false;
        } else if ((CurrentModuleReading1.isEmpty() || CurrentModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Current Module Reading 1.");
            return false;
        } else if ((SocModuleReading1.isEmpty() || SocModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOC Module Reading 1.");
            return false;
        } else if ((SohModuleReading1.isEmpty() || SohModuleReading1 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOH Module Reading 1.");
            return false;
        } else if ((VoltageModuleReading2.isEmpty() || VoltageModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Voltage Module Reading 2.");
            return false;
        } else if ((CurrentModuleReading2.isEmpty() || CurrentModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Current Module Reading 2.");
            return false;
        } else if ((SocModuleReading2.isEmpty() || SocModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOC Module Reading 2.");
            return false;
        } else if ((SohModuleReading2.isEmpty() || SohModuleReading2 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOH Module Reading 2.");
            return false;
        } else if ((VoltageModuleReading3.isEmpty() || VoltageModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Voltage Module Reading 3.");
            return false;
        } else if ((CurrentModuleReading3.isEmpty() || CurrentModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Current Module Reading 3.");
            return false;
        } else if ((SocModuleReading3.isEmpty() || SocModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOC Module Reading 3.");
            return false;
        } else if ((SohModuleReading3.isEmpty() || SohModuleReading3 == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter SOH Module Reading 3.");
            return false;
        }
        //////////

        else if (FloatVoltageInSmpsBusBarAfter30Min.isEmpty() || FloatVoltageInSmpsBusBarAfter30Min == null) {
            showToast("Enter Float Voltage in SMPS Bus Bar After 30 min.");
            return false;
        } else if (TotalLoadCurrentInAmps.isEmpty() || TotalLoadCurrentInAmps == null) {
            showToast("Enter Total Load Current(in Amps).");
            return false;
        }*/
        else if ((BatteryBankMake.isEmpty() || BatteryBankMake == null) && str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            showToast("Battery Bank Make must be required.");
            return false;
        } else if ((BatteryBankCapacity.isEmpty() || BatteryBankCapacity == null) && str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            showToast("Battery Bank Capacity must be required.");
            return false;
        } /*else if ((NumberOfRectifireModuleWorking.isEmpty() || NumberOfRectifireModuleWorking == null) && str_pmSiteBbcpTestDoneAs.equals("Individual")) {
            showToast("Number of Rectifire Module Working must be required.");
            return false;
        }*/
        ////

        else if (SiteLoadOnBatteryInAmps.isEmpty() || SiteLoadOnBatteryInAmps == null) {
            showToast("Enter Site Load on Battery(in Amps).");
            return false;
        } else if (FloatVoltageBeforeBBTest.isEmpty() || FloatVoltageBeforeBBTest == null) {
            showToast("Enter Float Voltage before BB Test.");
            return false;
        } else if ((SingleModuleRating.isEmpty() || SingleModuleRating == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Single Module Rating.");
            return false;
        } /*else if ((NumberOfBatteryModule.isEmpty() || NumberOfBatteryModule == null) && TypeOfBattery.equals("Li-Ion")) {
            showToast("Enter Number of Battery Module.");
            return false;
        }*/ /*else if (ReadingTakenAt.isEmpty() || ReadingTakenAt == null) {
            showToast("Select Reading Taken At.");
            return false;
        }*/ else if (LastReadingTakenAt.isEmpty() || LastReadingTakenAt == null) {
            showToast("Enter Last Reading Taken At.");
            return false;
        } /*else if (Base64StringPhotoOfBatteryBank.isEmpty() || Base64StringPhotoOfBatteryBank == null) {
            showToast("Photo of Battery Bank must be required.");
            return false;
        }*/ else if (Remarks.isEmpty() || Remarks == null) {
            showToast("Enter Remarks");
            return false;
        }
        return true;
    }
}
