package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
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
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsChildData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryBankCheckPointsViLionBatteryData;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
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

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmBatteryBankType;

public class PreventiveMaintenanceSiteBatteryBankCheckPointsActivity extends BaseActivity {

    //region Form Depedency Fields:
    //#Battery Type:
    //1.Tubular
    //2.Li-Ion
    //3.VRLA
    //4.VRLA+
    //#Test Done As
    //1.Combined
    //2.Individual
    //#Battery Discharge Test:
    //1.Abort
    //2.Execute BD Test
    //endregion


    private static final String TAG = PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal;

    private LinearLayout mLinearLayoutContainer;
    private LinearLayout mLinearLayoutMainContainer;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber;

    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewDetailsOfBatteryBankQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView;
    private ImageView mButtonClearDetailsOfBatteryBankQRCodeScanView;

    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTest;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest;

    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorque;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyApplied;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatus;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatus;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal;

    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAs;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBank;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal;


    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTypeOfFaultVal;


    private Button mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTypeOfFault;

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView;

    String str_noOfBatteryBankAvailableAtSiteVal = "";
    String str_pmSiteBbcpBatteryBankDischargeTestVal = "";
    String str_pmSiteBbcpStripBoltTightnessAsPerTorque = "";
    String str_pmSiteBbcpPetroleumJellyAppliedVal = "";
    String str_pmSiteBbcpBatteryVentPlugStatusVal = "";
    String str_pmSiteBbcpBBEarthingStatusVal = "";
    String str_pmSiteBbcpRegisterFaultVal = "";
    String str_pmSiteBbcpTypeOfFaultVal = "";
    String str_pmSiteBbcpTestDoneAs = "";
    String str_pmSiteBbcpSelectBatteryBank = "";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private String base64StringDetailsOfBatteryBankQRCodeScan = "";
    //private String imageFileDetailsOfBatteryBankQRCodeScan;
    //private Uri imageFileUriDetailsOfBatteryBankQRCodeScan = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private ArrayList<BatteryBankCheckPointsData> batteryBankCheckPointsData;

    private BatteryBankCheckPointsParentData dataList;

    private int totalAcCount = 2;
    private int currentPos = 0;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    public static final int RESULT_PRIVENTIVE_MAINTENANCE_SITE_BATTERY_BANK_CHECK = 258;
    public static final int MY_FLAG_MODULE_RESULT = 200;

    private BatteryBankCheckPointsData batteryBankCheckPointsDataChild;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    private ArrayList<BatteryBankCheckPointsChildData> batteryBankCheckPointschildData;

    private ArrayList<BatteryBankCheckPointsViLionBatteryData> batteryBankCheckPointsViLionBatteryData;//13042019

    ArrayList<BatteryType> batteryType;
    ArrayList<String> batteryQRCodeList;

    /////////////

    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTestDoneAs;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank;
    private LinearLayout mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1;

    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTest1;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1;
    String str_pmSiteBbcpBatteryBankDischargeTestVal1 = "";

    private TextView mBatterySetTextViewTypeofBattery;
    private TextView mBatterySetTextViewTypeofBatteryVal;
    private String str_typeofBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_battery_bank_check_points);
        this.setTitle("Battery Bank Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        batteryType = new ArrayList<BatteryType>();
        Intent intent = getIntent();
        batteryType = (ArrayList<BatteryType>) intent.getSerializableExtra("batteryType");

        sessionManager = new SessionManager(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, userId, ticketName);

        checkCameraPermission();
        assignViews();
        sitePmBatteryBankType = batteryType.get(0).getTypeOfBattery().toString().trim();//"VRLA";// VRLA Li-Ion
        //sitePmBatteryBankType = "VRLA";// VRLA Li-Ion
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText("");
        if (sitePmBatteryBankType.equals("Li-Ion")) {
            str_pmSiteBbcpTestDoneAs = "Combined";
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText(str_pmSiteBbcpTestDoneAs);
        }

        if (batteryType != null) {
            batteryQRCodeList = new ArrayList<String>();
            getBatteryQRCodeList(batteryType);
        }
        visibilityOfFieldsBasedOnBatteryType(sitePmBatteryBankType);
        initCombo();
        setListner();

        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        batteryBankCheckPointsData = new ArrayList<>();
        dataList = new BatteryBankCheckPointsParentData();

        batteryBankCheckPointsDataChild = new BatteryBankCheckPointsData();
        batteryBankCheckPointschildData = new ArrayList<>();
        batteryBankCheckPointsViLionBatteryData = new ArrayList<>();//13042019

        currentPos = 0;

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }

        setInputDetails(currentPos);
        invalidateOptionsMenu();
        setMultiSelectModel();

    }

    private void assignViews() {

        mBatterySetTextViewTypeofBattery = (TextView) findViewById(R.id.batterySet_textView_TypeofBattery);
        mBatterySetTextViewTypeofBatteryVal = (TextView) findViewById(R.id.batterySet_textView_TypeofBattery_val);

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_noOfBatteryBankAvailableAtSite);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_noOfBatteryBankAvailableAtSiteVal);
        mLinearLayoutMainContainer = (LinearLayout) findViewById(R.id.linearLayout_mainContainer);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_battreyBankNumber);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewDetailsOfBatteryBankQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_detailsOfBatteryBankQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_detailsOfBatteryBankQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_detailsOfBatteryBankQRCodeScanView);
        mButtonClearDetailsOfBatteryBankQRCodeScanView = (ImageView) findViewById(R.id.button_ClearDetailsOfBatteryBankQRCodeScanView);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTest = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryBankDischargeTest);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryBankDischargeTestVal);

        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_imageview_batteryBankDischargeTest);

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorque = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_stripBoltTightnessAsPerTorque);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_stripBoltTightnessAsPerTorqueVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyApplied = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_petroleumJellyApplied);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_petroleumJellyAppliedVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryVentPlugStatus);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryVentPlugStatusVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_bbEarthingStatus);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_bbEarthingStatusVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTypeOfFaultVal = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_typeOfFault);

        //only initialization other related code is remaining for below field ---
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAs = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_testDoneAs);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_testDoneAsVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBank = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_selectBatteryBank);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_selectBatteryBankVal);
        //-----------------------------------

        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_nextReading);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_typeOfFault);

        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_uploadPhotoOfRegisterFaultView);

        /////

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTest1 = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryBankDischargeTest1);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1 = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryBankDischargeTestVal1);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1 = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_imageview_batteryBankDischargeTest1);

        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTestDoneAs = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_testDoneAs);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_batteryBankDischargeTest);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_selectBatteryBank);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_linearLayout_batteryBankDischargeTest1);
    }

    private void initCombo() {

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_noOfBatteryBank))),
                        "No of Battery Bank available at Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfBatteryBankAvailableAtSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.setText(str_noOfBatteryBankAvailableAtSiteVal);


                        str_noOfBatteryBankAvailableAtSiteVal = item.get(position);
                        invalidateOptionsMenu();
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.setText(str_noOfBatteryBankAvailableAtSiteVal);
                        //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText("");//008

                        if (batteryBankCheckPointsData != null && batteryBankCheckPointsData.size() > 0) {
                            batteryBankCheckPointsData.clear();
                        }
                        currentPos = 0;
                        totalAcCount = Integer.parseInt(str_noOfBatteryBankAvailableAtSiteVal);
                        //totalAcCount = 2;
                        clearFields(currentPos);

                        // Clear all field value and hide layout If Non AC or O //
                        if (str_noOfBatteryBankAvailableAtSiteVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                            mLinearLayoutMainContainer.setVisibility(View.GONE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                        } else {
                            str_noOfBatteryBankAvailableAtSiteVal = item.get(position);
                            //totalAcCount = Integer.parseInt(str_noOfBatteryBankAvailableAtSiteVal);
                            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            mLinearLayoutMainContainer.setVisibility(View.VISIBLE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.GONE);

                            /*if (sitePmBatteryBankType.contains("Li-Ion")) {
                                str_pmSiteBbcpTestDoneAs = "Combined";
                                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText(str_pmSiteBbcpTestDoneAs);
                                mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
                            }*/
                            if (totalAcCount > 0 && totalAcCount == 1) {

                                if (sitePmBatteryBankType.contains("VRLA")) {
                                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText("Individual");
                                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setEnabled(false);
                                    visibilityOfBatteryBankAndDischargeTestFields("Individual");
                                }

                                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Finish");
                            } else {

                                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText("");
                                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setEnabled(true);
                                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Next Reading");

                            }

                            if (sitePmBatteryBankType.contains("Li-Ion")) {
                                str_pmSiteBbcpTestDoneAs = "Combined";
                                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText(str_pmSiteBbcpTestDoneAs);
                                mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
                            }

                        }


                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_testDoneAs))),
                        "Test Done As",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpTestDoneAs = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText(str_pmSiteBbcpTestDoneAs);
                        visibilityOfBatteryBankAndDischargeTestFields(str_pmSiteBbcpTestDoneAs);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_batteryBankDischargeTest))),
                        "Battery Bank Discharge Test",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBatteryBankDischargeTestVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText(str_pmSiteBbcpBatteryBankDischargeTestVal);
                        visibilityOfImageViewBatteryBankDischargeTest(str_pmSiteBbcpBatteryBankDischargeTestVal);
                    }
                });
            }
        });


        mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        batteryQRCodeList,
                        "Select Battery Bank",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        str_pmSiteBbcpSelectBatteryBank = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText(str_pmSiteBbcpSelectBatteryBank);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteBbcpRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteBbcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });


        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_pmSiteBbcpBatteryBankDischargeTestVal = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.getText().toString();
                if (str_pmSiteBbcpBatteryBankDischargeTestVal.equals("Execute BD Test")) {

                    if (sitePmBatteryBankType.equals("Li-Ion")) {
                        str_pmSiteBbcpTestDoneAs = "Combined";
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText(str_pmSiteBbcpTestDoneAs);
                    }

                    str_pmSiteBbcpTestDoneAs = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString();
                    submitDetails();
                    Intent i = new Intent(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.class);
                    i.putExtra("batteryType", batteryType);
                    i.putExtra("str_pmSiteBbcpTestDoneAs", str_pmSiteBbcpTestDoneAs);
                    i.putExtra("arrayIndex", 0);
                    i.putExtra("batteryBankCheckPointsParentData", dataList);
                    //startActivity(i);
                    startActivityForResult(i, MY_FLAG_MODULE_RESULT);
                } else {
                    showToast("Select Battery Bank Discharge Test");
                }
            }
        });


        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_stripTightnessAsPerTorque))),
                        "Strip Bolt tightness as per torquet",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpStripBoltTightnessAsPerTorque = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setText(str_pmSiteBbcpStripBoltTightnessAsPerTorque);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_petroleumJellyApplied))),
                        "Petroleum Jelly Applied",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpPetroleumJellyAppliedVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setText(str_pmSiteBbcpPetroleumJellyAppliedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_batteryVentPlugStatus))),
                        "Battery Vent plug Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBatteryVentPlugStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setText(str_pmSiteBbcpBatteryVentPlugStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_bbEarthingStatus))),
                        "BB Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBBEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setText(str_pmSiteBbcpBBEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_batteryBankDischargeTest))),
                        "Battery Bank Discharge Test",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBatteryBankDischargeTestVal1 = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText(str_pmSiteBbcpBatteryBankDischargeTestVal1);
                        visibilityOfImageViewBatteryBankDischargeTest1(str_pmSiteBbcpBatteryBankDischargeTestVal1);
                    }
                });
            }
        });
        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //007
                str_pmSiteBbcpBatteryBankDischargeTestVal1 = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.getText().toString();

                if (str_pmSiteBbcpBatteryBankDischargeTestVal1.equals("Execute BD Test")) {
                    str_noOfBatteryBankAvailableAtSiteVal = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString();
                    str_pmSiteBbcpTestDoneAs = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString();
                    if (str_noOfBatteryBankAvailableAtSiteVal == null || str_noOfBatteryBankAvailableAtSiteVal.isEmpty()) {
                        showToast("Select No Of Battery Bank available at Site");
                    } else {
                        int count = Integer.parseInt(str_noOfBatteryBankAvailableAtSiteVal);
                        if (count > 0) {
                            saveDGCheckRecords(currentPos);
                            Intent i = new Intent(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, PreventiveMaintenanceSiteBatteryBankBackUpTestReportActivity.class);
                            i.putExtra("batteryType", batteryType);
                            i.putExtra("str_pmSiteBbcpTestDoneAs", str_pmSiteBbcpTestDoneAs);
                            i.putExtra("arrayIndex", currentPos);//currentPos - 1 < 0 ? 0 : currentPos - 1
                            i.putExtra("batteryBankCheckPointsDataList", batteryBankCheckPointsData);
                            startActivityForResult(i, MY_FLAG_MODULE_RESULT);
                        } else {
                            showToast("Number of Modules is zero.");
                        }
                    }
                } else {
                    showToast("Select Battery Bank Discharge Test");
                }

            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetMultiSelectModel(); by 008
                /*if (checkValidationOfArrayFields() == true) {*/
                if (currentPos > 0) {
                    //Save current ac reading
                    saveDGCheckRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayDGCheckRecords(currentPos);
                }
                /*}*/
            }
        });
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
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

                    } else if (currentPos == (totalAcCount - 1)) {
                        saveDGCheckRecords(currentPos);
                        if (checkDuplicationQrCodeNew() == false) {
                            if (checkValidationOnChangeNoOfDgBatteryAvailable(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                                submitDetails();
                                startActivity(new Intent(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, PreventiveMaintenanceSiteEarthingCheckPointsActivity.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

    }

    /////////////
    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                dataList = pmSiteTransactionDetails.getBatteryBankCheckPointsParentData();
                batteryBankCheckPointsData.clear();
                batteryBankCheckPointsData.addAll(dataList.getBatteryBankCheckPointsData());
                batteryBankCheckPointschildData.clear();
                batteryBankCheckPointschildData.addAll(dataList.getBatteryBankCheckPointsChildData());

                batteryBankCheckPointsViLionBatteryData.clear();//13042019
                batteryBankCheckPointsViLionBatteryData.addAll(dataList.getBatteryBankCheckPointsViLionBatteryData());//13042019

                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.setText(dataList.getNoOfBatteryBankAvailableAtSite());

                if (dataList.getNoOfBatteryBankAvailableAtSite().equals("1")) {
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setEnabled(false);
                }

                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.setText(dataList.getTestDoneAs());
                visibilityOfBatteryBankAndDischargeTestFields(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString());//dataList.getTestDoneAs());

                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText(dataList.getBatteryBankDischargeTest());
                visibilityOfImageViewBatteryBankDischargeTest(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.getText().toString());//dataList.getBatteryBankDischargeTest());


                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());
                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();
                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                    alreadySelectedTypeOfFaultList = new ArrayList<>();
                    setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                    setMultiSelectModel();
                }

                str_noOfBatteryBankAvailableAtSiteVal = dataList.getNoOfBatteryBankAvailableAtSite();
                invalidateOptionsMenu();

                mLinearLayoutMainContainer.setVisibility(View.VISIBLE);
                if (batteryBankCheckPointsData != null && batteryBankCheckPointsData.size() > 0) {
                    batteryBankCheckPointsDataChild = batteryBankCheckPointsData.get(index);

                    //mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber.setText("Reading: #1");
                    totalAcCount = Integer.parseInt(dataList.getNoOfBatteryBankAvailableAtSite());

                    base64StringDetailsOfBatteryBankQRCodeScan = batteryBankCheckPointsData.get(index).getDetailsOfBatteryBankQrCodeScan();
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);

                    if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText(batteryBankCheckPointsData.get(index).getBatteryBankQrCodeSelection());

                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setText(batteryBankCheckPointsData.get(index).getStripBoltTightnessAsPerTorque());
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setText(batteryBankCheckPointsData.get(index).getPetroleumJellyApplied());
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setText(batteryBankCheckPointsData.get(index).getBatteryVentPlugStatus());
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setText(batteryBankCheckPointsData.get(index).getBbEarthingStatus());

                    mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText(batteryBankCheckPointsData.get(index).getBatteryBankDischargeTest());
                    visibilityOfImageViewBatteryBankDischargeTest1(batteryBankCheckPointsData.get(index).getBatteryBankDischargeTest());
                    visibilityOfBatteryBankAndDischargeTestFields(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString());

                    mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                    if (totalAcCount > 1) {
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Finish");
                    }
                } else if (!str_noOfBatteryBankAvailableAtSiteVal.isEmpty()) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    //int AcCount = Integer.parseInt(str_noOfBatteryBankAvailableAtSiteVal);
                    totalAcCount = Integer.parseInt(str_noOfBatteryBankAvailableAtSiteVal);
                    if (totalAcCount > 1) {
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Finish");
                    }
                }

            } else {
                showToast("No previous saved data available");
                //mLinearLayoutContainer.setVisibility(View.GONE);
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
                        str_pmSiteBbcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteBbcpTypeOfFaultVal);

                        /*str_pmSiteBbcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteBbcpTypeOfFaultVal);*/
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

    private void saveDGCheckRecords(int pos) {

        String detailsOfBatteryBankQRCodeScan = base64StringDetailsOfBatteryBankQRCodeScan;
        str_pmSiteBbcpBatteryBankDischargeTestVal1 = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.getText().toString().trim();
        String stripBoltTightnessAsPerTorque = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.getText().toString().trim();
        String petroleumJellyApplied = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.getText().toString().trim();
        String batteryVentPlugStatus = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.getText().toString().trim();
        String bbEarthingStatus = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.getText().toString().trim();
        String batteryBankQrCodeSelection = mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.getText().toString().trim();

        if (batteryBankCheckPointsData.size() > 0 && batteryBankCheckPointsData.size() != pos) {
            //if (batteryBankCheckPointsData.get(pos).getTypeOfBattery() != null) {
            batteryBankCheckPointsData.get(pos).setDetailsOfBatteryBankQrCodeScan(batteryBankQrCodeSelection);//detailsOfBatteryBankQRCodeScan
            batteryBankCheckPointsData.get(pos).setBatteryBankQrCodeSelection(batteryBankQrCodeSelection);
            batteryBankCheckPointsData.get(pos).setStripBoltTightnessAsPerTorque(stripBoltTightnessAsPerTorque);
            batteryBankCheckPointsData.get(pos).setPetroleumJellyApplied(petroleumJellyApplied);
            batteryBankCheckPointsData.get(pos).setBatteryVentPlugStatus(batteryVentPlugStatus);
            batteryBankCheckPointsData.get(pos).setBbEarthingStatus(bbEarthingStatus);
            batteryBankCheckPointsData.get(pos).setBatteryBankDischargeTest(str_pmSiteBbcpBatteryBankDischargeTestVal1);
            // }
        } else {
            //detailsOfBatteryBankQRCodeScan
            BatteryBankCheckPointsData child = new BatteryBankCheckPointsData(batteryBankQrCodeSelection, stripBoltTightnessAsPerTorque,
                    petroleumJellyApplied, batteryBankQrCodeSelection, str_pmSiteBbcpBatteryBankDischargeTestVal1, batteryVentPlugStatus, bbEarthingStatus);

            if (batteryBankCheckPointsData.size() > 0) {
                if (pos == batteryBankCheckPointsData.size()) {
                    batteryBankCheckPointsData.add(child);
                } else if (pos < batteryBankCheckPointsData.size()) {
                    batteryBankCheckPointsData.set(pos, child);
                }
            } else {
                batteryBankCheckPointsData.add(child);
            }
        }



        /*String detailsOfBatteryBankQRCodeScan = base64StringDetailsOfBatteryBankQRCodeScan;
        String stripBoltTightnessAsPerTorque = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.getText().toString().trim();
        String petroleumJellyApplied = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.getText().toString().trim();
        String batteryVentPlugStatus = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.getText().toString().trim();
        String bbEarthingStatus = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.getText().toString().trim();

        BatteryBankCheckPointsData child = new BatteryBankCheckPointsData(detailsOfBatteryBankQRCodeScan, stripBoltTightnessAsPerTorque,
                petroleumJellyApplied, batteryVentPlugStatus, bbEarthingStatus);

        if (batteryBankCheckPointsData.size() > 0) {
            if (pos == batteryBankCheckPointsData.size()) {
                batteryBankCheckPointsData.add(child);
            } else if (pos < batteryBankCheckPointsData.size()) {
                batteryBankCheckPointsData.set(pos, child);
            }
        } else {
            batteryBankCheckPointsData.add(child);
        }*/
    }

    private void displayDGCheckRecords(int pos) {
        //visibilityOfBatteryBankAndDischargeTestFields(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString());
        str_noOfBatteryBankAvailableAtSiteVal = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString();
        int count = Integer.parseInt(str_noOfBatteryBankAvailableAtSiteVal);
        if (batteryBankCheckPointsData.size() > 0 && pos < batteryBankCheckPointsData.size()) {
            //&& batteryBankCheckPointsData.size() < count

            batteryBankCheckPointsDataChild = batteryBankCheckPointsData.get(pos);

            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber.setText("Reading: #" + (pos + 1));

            base64StringDetailsOfBatteryBankQRCodeScan = batteryBankCheckPointsData.get(pos).getDetailsOfBatteryBankQrCodeScan();
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
            mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);

            if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
            }


            /*mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText(batteryBankCheckPointsData.get(pos).getBatteryBankDischargeTest());
            visibilityOfImageViewBatteryBankDischargeTest(batteryBankCheckPointsData.get(pos).getBatteryBankDischargeTest());
            */
            mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText(batteryBankCheckPointsData.get(pos).getBatteryBankQrCodeSelection());
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setText(batteryBankCheckPointsData.get(pos).getStripBoltTightnessAsPerTorque());
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setText(batteryBankCheckPointsData.get(pos).getPetroleumJellyApplied());
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setText(batteryBankCheckPointsData.get(pos).getBatteryVentPlugStatus());
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setText(batteryBankCheckPointsData.get(pos).getBbEarthingStatus());

            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText(batteryBankCheckPointsData.get(pos).getBatteryBankDischargeTest());
            visibilityOfImageViewBatteryBankDischargeTest1(batteryBankCheckPointsData.get(pos).getBatteryBankDischargeTest());
            visibilityOfBatteryBankAndDischargeTestFields(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString());

            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalAcCount - 1)) {
                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading.setText("Next Reading");
            }
        }

    }

    private void submitDetails() {
        try {

            String noOfBatteryBankAvailableAtSiteVal = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;
            String batteryBankDischargeTest = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.getText().toString().trim();
            String testDoneAs = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString().trim();


            dataList.setNoOfBatteryBankAvailableAtSite(noOfBatteryBankAvailableAtSiteVal);
            dataList.setRegisterFault(registerFault);
            dataList.setTypeOfFault(typeOfFault);
            dataList.setBase64StringUploadPhotoOfRegisterFault(base64StringUploadPhotoOfRegisterFault);
            dataList.setBatteryBankDischargeTest(batteryBankDischargeTest);
            dataList.setTestDoneAs(testDoneAs);

            //dataList.setBatteryBankQrCodeSelection(batteryBankQrCodeSelection);

            /*if (sitePmBatteryBankType.contains("VRLA") && testDoneAs.equals("Individual")) {



            } else {*/

            /*}*/

            if (!noOfBatteryBankAvailableAtSiteVal.isEmpty() && !registerFault.isEmpty()) {//&& !batteryBankDischargeTest.isEmpty()
                dataList.setSubmited(2);
            } else {
                dataList.setSubmited(1);
            }

            if (sitePmBatteryBankType.contains("VRLA") || sitePmBatteryBankType.contains("Li-Ion")) {
                if ((sitePmBatteryBankType.contains("VRLA") && testDoneAs.equals("Individual"))) {
                    //dataList.setTypeOfBattery("");// 008
                    batteryBankCheckPointschildData = new ArrayList<>();
                    dataList.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
                    batteryBankCheckPointsViLionBatteryData = new ArrayList<>();//13042019
                    dataList.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);//13042019
                    dataList.setBatteryBankDischargeTest("");//
                    dataList.setBdTestBatteryBankBackUpTicketNo("");
                    dataList.setBdTestCustomer("");
                    dataList.setBdTestCircle("");
                    dataList.setBdTestState("");
                    dataList.setBdTestSiteName("");
                    dataList.setBdTestSiteId("");
                    dataList.setBdTestSsa("");
                    dataList.setBdTestUserFseNameDesignation("");
                    //dataList.setTypeOfBattery(""); 008

                    /*dataList.setBdTestVoltageModuleReading1("");
                    dataList.setBdTestCurrentModuleReading1("");
                    dataList.setBdTestSocModuleReading1("");
                    dataList.setBdTestSohModuleReading1("");
                    dataList.setBdTestVoltageModuleReading2("");
                    dataList.setBdTestCurrentModuleReading2("");
                    dataList.setBdTestSocModuleReading2("");
                    dataList.setBdTestSohModuleReading2("");
                    dataList.setBdTestVoltageModuleReading3("");
                    dataList.setBdTestCurrentModuleReading3("");
                    dataList.setBdTestSocModuleReading3("");
                    dataList.setBdTestSohModuleReading3("");

                    dataList.setBdTestFloatVoltageInSmpsBusBarAfter30Min("");
                    dataList.setBdTestTotalLoadCurrentInAmps("");*/

                    //Set Vi Lion List new with size 0

                    dataList.setBdTestBatteryBankMake("");
                    dataList.setBdTestBatteryBankCapacity("");
                    dataList.setBdTestNumberOfRectifireModuleWorking("");
                    dataList.setBdTestSiteLoadOnBatteryInAmps("");
                    dataList.setBdTestFloatVoltageBeforeBBTest("");
                    dataList.setBdTestSingleModuleRating("");
                    dataList.setBdTestNumberOfBatteryModule("");
                    //dataList.setBdTestReadingTakenAt("");
                    dataList.setBdTestLastReadingTakenAt("");
                    dataList.setBdTestRemarks("");

                    dataList.setBdTestDetailsBatteryBankBackUpOfQRCodeScan("");
                    dataList.setBdTestBase64StringPhotoOfBatteryBank("");
                    dataList.setBatteryBankCheckPointsData(batteryBankCheckPointsData);

                } else {

                    dataList.setTypeOfBattery(sitePmBatteryBankType);//Li Ion
                    if (batteryBankCheckPointsData.size() > 0) {
                        int i = 0;
                        for (BatteryBankCheckPointsData dd : batteryBankCheckPointsData) {
                            batteryBankCheckPointschildData = new ArrayList<>();
                            dd.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
                            batteryBankCheckPointsViLionBatteryData = new ArrayList<>();//13042019
                            dd.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);
                            dd.setBatteryBankDischargeTest("");//
                            dd.setBdTestBatteryBankBackUpTicketNo("");
                            dd.setBdTestCustomer("");
                            dd.setBdTestCircle("");
                            dd.setBdTestState("");
                            dd.setBdTestSiteName("");
                            dd.setBdTestSiteId("");
                            dd.setBdTestSsa("");
                            dd.setBdTestUserFseNameDesignation("");
                            dd.setTypeOfBattery("");

                            /*dd.setBdTestVoltageModuleReading1("");
                            dd.setBdTestCurrentModuleReading1("");
                            dd.setBdTestSocModuleReading1("");
                            dd.setBdTestSohModuleReading1("");
                            dd.setBdTestVoltageModuleReading2("");
                            dd.setBdTestCurrentModuleReading2("");
                            dd.setBdTestSocModuleReading2("");
                            dd.setBdTestSohModuleReading2("");
                            dd.setBdTestVoltageModuleReading3("");
                            dd.setBdTestCurrentModuleReading3("");
                            dd.setBdTestSocModuleReading3("");
                            dd.setBdTestSohModuleReading3("");

                            dd.setBdTestFloatVoltageInSmpsBusBarAfter30Min("");
                            dd.setBdTestTotalLoadCurrentInAmps("");*/
                            dd.setBdTestBatteryBankMake("");
                            dd.setBdTestBatteryBankCapacity("");
                            dd.setBdTestNumberOfRectifireModuleWorking("");
                            dd.setBdTestSiteLoadOnBatteryInAmps("");
                            dd.setBdTestFloatVoltageBeforeBBTest("");
                            dd.setBdTestSingleModuleRating("");
                            dd.setBdTestNumberOfBatteryModule("");
                            //dd.setBdTestReadingTakenAt("");
                            dd.setBdTestLastReadingTakenAt("");
                            dd.setBdTestRemarks("");

                            dd.setBdTestDetailsBatteryBankBackUpOfQRCodeScan("");
                            dd.setBdTestBase64StringPhotoOfBatteryBank("");
                            batteryBankCheckPointsData.set(i, dd);
                            i++;
                        }
                    }

                    dataList.setBatteryBankCheckPointsData(batteryBankCheckPointsData);

                    if (batteryBankDischargeTest.equals("Execute BD Test")) {
                        if (sitePmBatteryBankType.contains("VRLA")) {
                            if (batteryBankCheckPointschildData.size() > 0) {
                                batteryBankCheckPointsViLionBatteryData = new ArrayList<>();
                                dataList.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
                                dataList.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);
                            }
                        } else if (sitePmBatteryBankType.contains("Li-Ion")) {
                            if (batteryBankCheckPointsViLionBatteryData.size() > 0) {
                                batteryBankCheckPointschildData = new ArrayList<>();
                                dataList.setBatteryBankCheckPointsChildData(batteryBankCheckPointschildData);
                                dataList.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);
                            }
                        }
                    } else {
                        batteryBankCheckPointschildData = new ArrayList<>();
                        batteryBankCheckPointsViLionBatteryData = new ArrayList<>();//13042019
                        dataList.setBatteryBankCheckPointsViLionBatteryData(batteryBankCheckPointsViLionBatteryData);
                        dataList.setBdTestBatteryBankBackUpTicketNo("");
                        dataList.setBdTestCustomer("");
                        dataList.setBdTestCircle("");
                        dataList.setBdTestState("");
                        dataList.setBdTestSiteName("");
                        dataList.setBdTestSiteId("");
                        dataList.setBdTestSsa("");
                        dataList.setBdTestUserFseNameDesignation("");
                        //dataList.setTypeOfBattery(""); 008

                        /*dataList.setBdTestVoltageModuleReading1("");
                        dataList.setBdTestCurrentModuleReading1("");
                        dataList.setBdTestSocModuleReading1("");
                        dataList.setBdTestSohModuleReading1("");
                        dataList.setBdTestVoltageModuleReading2("");
                        dataList.setBdTestCurrentModuleReading2("");
                        dataList.setBdTestSocModuleReading2("");
                        dataList.setBdTestSohModuleReading2("");
                        dataList.setBdTestVoltageModuleReading3("");
                        dataList.setBdTestCurrentModuleReading3("");
                        dataList.setBdTestSocModuleReading3("");
                        dataList.setBdTestSohModuleReading3("");

                        dataList.setBdTestFloatVoltageInSmpsBusBarAfter30Min("");
                        dataList.setBdTestTotalLoadCurrentInAmps("");*/
                        dataList.setBdTestBatteryBankMake("");
                        dataList.setBdTestBatteryBankCapacity("");
                        dataList.setBdTestNumberOfRectifireModuleWorking("");
                        dataList.setBdTestSiteLoadOnBatteryInAmps("");
                        dataList.setBdTestFloatVoltageBeforeBBTest("");
                        dataList.setBdTestSingleModuleRating("");
                        dataList.setBdTestNumberOfBatteryModule("");
                        //dataList.setBdTestReadingTakenAt("");
                        dataList.setBdTestLastReadingTakenAt("");
                        dataList.setBdTestRemarks("");

                        dataList.setBdTestDetailsBatteryBankBackUpOfQRCodeScan("");
                        dataList.setBdTestBase64StringPhotoOfBatteryBank("");

                    }
                }
            } else if (sitePmBatteryBankType.equals("Tubular")) {
                dataList.setBatteryBankCheckPointsData(batteryBankCheckPointsData);
            }
            dataList.setTypeOfBattery(sitePmBatteryBankType);//008 new

            pmSiteTransactionDetails.setBatteryBankCheckPointsParentData(dataList);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber.setText("Reading: #" + (indexPos + 1));


        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText("");
        mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText("");
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setText("");
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setText("");
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setText("");
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setText("");
        //mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.setText("");
        //mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setText("");


        str_noOfBatteryBankAvailableAtSiteVal = "";

        str_pmSiteBbcpBatteryBankDischargeTestVal1 = "";
        str_pmSiteBbcpStripBoltTightnessAsPerTorque = "";
        str_pmSiteBbcpPetroleumJellyAppliedVal = "";
        str_pmSiteBbcpBatteryVentPlugStatusVal = "";
        str_pmSiteBbcpBBEarthingStatusVal = "";
        //str_pmSiteBbcpRegisterFaultVal = "";
        //str_pmSiteBbcpTypeOfFaultVal = "";

        base64StringDetailsOfBatteryBankQRCodeScan = "";
        //base64StringUploadPhotoOfRegisterFault = "";

        //mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
        mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1.setVisibility(View.GONE);
        batteryBankCheckPointsDataChild = new BatteryBankCheckPointsData();
    }

    public boolean checkValidationOnChangeNoOfDgBatteryAvailable(String NoOfBatteryBankAvailableAtSiteVal, String methodFlag) {

        String registerFault = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
        String batteryBankDischargeTest = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.getText().toString().trim();
        String testDoneAs = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString().trim();
        String selectBatteryBank = mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.getText().toString().trim();

        if (NoOfBatteryBankAvailableAtSiteVal.isEmpty() || NoOfBatteryBankAvailableAtSiteVal == null) {
            showToast("Select No of Battery Bank Available");
            return false;
        } else if ((batteryBankDischargeTest.isEmpty() || batteryBankDischargeTest == null) && (sitePmBatteryBankType.contains("Li-Ion") || sitePmBatteryBankType.contains("VRLA")) && !testDoneAs.equals("Individual")) {
            showToast("Select Battery Bank Discharge Test");
            return false;
        } else if ((testDoneAs.isEmpty() || testDoneAs == null) && !sitePmBatteryBankType.contains("Li-Ion")) {
            showToast("Select Test Done As");
            return false;
        } /*else if (selectBatteryBank.isEmpty() || selectBatteryBank == null) {
            showToast("Select Battery Bank");
            return false;
        }*/ else if (registerFault.isEmpty() || registerFault == null) {
            showToast("Select Register Fault");
            return false;
        } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
            showToast("Select Types Of Fault");
            return false;
        } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
            showToast("Select Types Of Fault");
            return false;
        } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
            showToast("Upload Photo Of Register Fault");
            return false;
        } else if ((batteryBankCheckPointsData.size() != Integer.valueOf(NoOfBatteryBankAvailableAtSiteVal) && methodFlag.equals("onSubmit"))) {
            showToast("Complete the all readings.");//as a mentioned AC in no of AC provided
            return false;
        } else return true;

    }

    //////////////////
    public boolean checkValidationOfArrayFields() {

        //String qrCodeScan = base64StringDetailsOfBatteryBankQRCodeScan;
        String testDoneAs = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTestDoneAsVal.getText().toString().trim();
        String batteryBankDischargeTest = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.getText().toString().trim();
        String qrCodeScan = mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.getText().toString().trim();
        String stripBoltTightnessAsPerTorque = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.getText().toString().trim();
        String petroleumJellyApplied = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.getText().toString().trim();
        String batteryVentPlugStatus = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.getText().toString().trim();
        String bbEarthingStatus = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.getText().toString().trim();
        String batteryBankDischargeTest1 = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.getText().toString().trim();

        if ((testDoneAs.isEmpty() || testDoneAs == null) && sitePmBatteryBankType.contains("VRLA")) {
            showToast("Select Test Done as");
            return false;
        } else if ((batteryBankDischargeTest.isEmpty() || batteryBankDischargeTest == null) && (sitePmBatteryBankType.contains("Li-Ion") || sitePmBatteryBankType.contains("VRLA")) && !testDoneAs.equals("Individual")) {
            showToast("Select Battery Bank Discharge Test");
            return false;
        } else if (qrCodeScan.isEmpty() || qrCodeScan == null) {
            showToast("Scan QR Code");
            return false;
        } else if (qrCodeScan.isEmpty() || qrCodeScan == null) {
            showToast("Scan QR Code");
            return false;
        } else if (stripBoltTightnessAsPerTorque.isEmpty() || stripBoltTightnessAsPerTorque == null) {
            showToast("Select Strip Bolt Tightness As Per Torque");
            return false;
        } else if (petroleumJellyApplied.isEmpty() || petroleumJellyApplied == null) {
            showToast("Select Petroleum Jelly Applied");
            return false;
        } else if (batteryVentPlugStatus.isEmpty() || batteryVentPlugStatus == null) {
            showToast("Select Battery Vent Plug Status");
            return false;
        } else if (bbEarthingStatus.isEmpty() || bbEarthingStatus == null) {
            showToast("Select BB Earthing Status");
            return false;
        } else if ((batteryBankDischargeTest1.isEmpty() || batteryBankDischargeTest1 == null) && (sitePmBatteryBankType.contains("VRLA") && testDoneAs.equals("Individual"))) {
            showToast("Select Battery Bank Discharge Test");
            return false;
        } else return true;
    }

    private void visibilityOfFieldsBasedOnBatteryType(String sitePmBatteryBankType) {

        //Tubular
        //Li-Ion
        //VRLA
        //VRLA+

        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTestDoneAs.setVisibility(View.VISIBLE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.GONE);
        //mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank.setVisibility(View.VISIBLE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.GONE);

        if (sitePmBatteryBankType.equals("Li-Ion")) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTestDoneAs.setVisibility(View.GONE);
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
        } else if (sitePmBatteryBankType.contains("VRLA")) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTestDoneAs.setVisibility(View.VISIBLE);
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
        }

    }

    private void visibilityOfBatteryBankAndDischargeTestFields(String str_pmSiteBbcpTestDoneAs) {
        //mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.GONE);
        //mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.GONE);

        if (str_pmSiteBbcpTestDoneAs.equals("Individual") && !sitePmBatteryBankType.equals("Tubular")) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.GONE);
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.VISIBLE);
        } else if (str_pmSiteBbcpTestDoneAs.equals("Combined") && !sitePmBatteryBankType.equals("Tubular")) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutSelectBatteryBank.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.GONE);
        }
    }

    private void visibilityOfImageViewBatteryBankDischargeTest(String str_pmSiteBbcpBatteryBankDischargeTestVal) {
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest.setVisibility(View.GONE);

        if (str_pmSiteBbcpBatteryBankDischargeTestVal.equals("Execute BD Test")) {
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest.setVisibility(View.VISIBLE);
        } else if (str_pmSiteBbcpBatteryBankDischargeTestVal.equals("Abort")) {
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal1.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfImageViewBatteryBankDischargeTest1(String str_pmSiteBbcpBatteryBankDischargeTestVal1) {
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1.setVisibility(View.GONE);

        if (str_pmSiteBbcpBatteryBankDischargeTestVal1.equals("Execute BD Test")) {
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsImageViewBatteryBankDischargeTest1.setVisibility(View.VISIBLE);
        } else if (str_pmSiteBbcpBatteryBankDischargeTestVal1.equals("Abort")) {
            //mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutBatteryBankDischargeTest1.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfTypesOfFault(String RegisterFault) {
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteBatteryBankCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            base64StringUploadPhotoOfRegisterFault = "";
            mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    private void setListner() {

        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DetailsOfBatteryBankQRCodeScan();
                }
            }
        });

        mButtonClearDetailsOfBatteryBankQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDetailsOfBatteryBankQRCodeScan = "";
                mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText("");
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DetailsOfBatteryBankQRCodeScan() {
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

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_noOfBatteryBankAvailableAtSiteVal != null && !str_noOfBatteryBankAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_noOfBatteryBankAvailableAtSiteVal) > 0) {
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
                //submitDetails();

                str_noOfBatteryBankAvailableAtSiteVal = mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString();

                if (str_noOfBatteryBankAvailableAtSiteVal == null || str_noOfBatteryBankAvailableAtSiteVal.equals("")) {
                    showToast("Select No of Battery Bank available at Site");
                } else {
                    if (checkValidationOnChangeNoOfDgBatteryAvailable(mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                        submitDetails();
                        startActivity(new Intent(this, PreventiveMaintenanceSiteEarthingCheckPointsActivity.class));
                        finish();
                    }
                }

                //startActivity(new Intent(this, PreventiveMaintenanceSiteEarthingCheckPointsActivity.class));
                //finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_FLAG_MODULE_RESULT:
                /*if (requestCode == MY_FLAG_MODULE_RESULT) {*/
                if (resultCode == RESULT_OK) {
                    /*Bundle b = data.getExtras();
                    //dataList = new BatteryBankCheckPointsParentData();
                    //dataList = (BatteryBankCheckPointsParentData) b.getSerializable("batteryBankCheckPointsParentData");
                    String ss = (String) b.getSerializable("flag");
                    if (ss.equals("1")) {
                        //showToast("Called Return");
                        setInputDetails(0);
                    }*/

                    if (data != null) {

                        Bundle b = data.getExtras();

                        str_pmSiteBbcpTestDoneAs = (String) b.getSerializable("str_pmSiteBbcpTestDoneAs");
                        currentPos = b.getInt("arrayIndex", 0);
                        if (str_pmSiteBbcpTestDoneAs.equals("Combined")) {
                            dataList = (BatteryBankCheckPointsParentData) b.getSerializable("batteryBankCheckPointsParentData");
                        } else if (str_pmSiteBbcpTestDoneAs.equals("Individual")) {
                            //dataList = (BatteryBankCheckPointsParentData) b.getSerializable("batteryBankCheckPointsParentData");
                            batteryBankCheckPointsData = (ArrayList<BatteryBankCheckPointsData>) b.getSerializable("batteryBankCheckPointsDataList");
                        }

                        Log.e("123", "123");
                    } else {
                        showToast("Null Data Pass");
                    }
                }
                /*}*/
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringDetailsOfBatteryBankQRCodeScan = "";
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText("");
                        showToast("Cancelled");
                    } else {

                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringDetailsOfBatteryBankQRCodeScan = result.getContents();

                            if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                                if (batteryQRCodeList.size() > 0) {
                                    boolean tempflag = false;
                                    for (int i = 0; i < batteryQRCodeList.size(); i++) {
                                        if (base64StringDetailsOfBatteryBankQRCodeScan.equals(batteryQRCodeList.get(i).toString())) {
                                            str_pmSiteBbcpSelectBatteryBank = base64StringDetailsOfBatteryBankQRCodeScan;
                                            mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText(str_pmSiteBbcpSelectBatteryBank);
                                            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                                            mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                                            tempflag = true;
                                            break;
                                        } else {
                                            mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText("");
                                            //showToast("QR Code not matched.");
                                        }
                                    }
                                    if (tempflag == false) {
                                        showToast("QR Code not matched.");
                                    }
                                } else {
                                    mPreventiveMaintenanceSiteBatteryBankCheckPointsSelectBatteryBankVal.setText("No Data Found");
                                    //No sites found
                                }
                            }

                        /*if (!base64StringDetailsOfBatteryBankQRCodeScan.isEmpty() && base64StringDetailsOfBatteryBankQRCodeScan != null) {
                            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                            mButtonClearDetailsOfBatteryBankQRCodeScanView.setVisibility(View.VISIBLE);
                        }*/

                        } else {
                            base64StringDetailsOfBatteryBankQRCodeScan = "";
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
                            mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public void getBatteryQRCodeList(ArrayList<BatteryType> batteryTypeList) {
        for (BatteryType batteryType : batteryTypeList) {
            batteryQRCodeList.add(batteryType.getQRCodeScan());
        }
    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < batteryBankCheckPointsData.size(); i++) {
            for (int j = i + 1; j < batteryBankCheckPointsData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (batteryBankCheckPointsData.get(i).getDetailsOfBatteryBankQrCodeScan().toString().equals(batteryBankCheckPointsData.get(j).getDetailsOfBatteryBankQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

}
