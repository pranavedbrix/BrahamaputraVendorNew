package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Adapters.PmAcExpListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.AcCheckPoint;
import com.brahamaputra.mahindra.brahamaputra.Data.AcPmAcData;
import com.brahamaputra.mahindra.brahamaputra.Data.AcPmTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.AcPreventiveMaintanceProcessDatum;
import com.brahamaputra.mahindra.brahamaputra.Data.AcPreventiveMaintanceProcessParentDatum;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.SitePmAcTicketList;
import com.brahamaputra.mahindra.brahamaputra.Data.UserLoginResponseData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.Volley.SettingsMy;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PreventiveMaintenanceAcTechnicianActivity extends BaseActivity {

    //design not fix for that no written code for make list color green when submitted all field for this section
    // and also web service are not create for that submit data on database is also remaining

    private static final String TAG = PreventiveMaintenanceAcTechnicianActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceAcTechnicianTextViewCircle;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCircleVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewState;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStateVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSsa;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSsaVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSiteId;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSiteIdVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSiteName;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSiteNameVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewTicketNo;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewTicketNoVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewTicketDate;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewTicketDateVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewPmPlanDate;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewPmPlanDateVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSubmittedDate;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSubmittedDateVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSite;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcNumber;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewQRCodeScan;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonQRCodeScan;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView;
    private ImageView mButtonClearQRCodeScanView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcModel;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcModelVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcType;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcMake;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcCapacity;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcSerialNo;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStablizerStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStabilizerMake;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacity;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewInputAcVoltage;
    private EditText mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFilterCleaned;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedBeforePhoto;

    private ImageView mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhoto;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedAfterPhoto;

    private ImageView mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhoto;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleaned;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedBeforePhoto;

    private ImageView mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhoto;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedAfterPhoto;

    private ImageView mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhoto;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleaned;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedBeforePhoto;

    private ImageView mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhoto;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedAfterPhoto;

    private ImageView mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhoto;
    private ImageView mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcStartingLoadcurrent;
    private EditText mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcRunningLoadCurrent;
    private EditText mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotor;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFanLeafCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotor;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCompressorCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewControllerCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcSensorCondition;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewRoomTemperature;
    private EditText mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewSetTemperature;
    private EditText mPreventiveMaintenanceAcTechnicianEditTextSetTemperature;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAc;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewWaterLeakage;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatus;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal;
    private TextView mPreventiveMaintenanceAcTechnicianTextViewRemarks;
    private EditText mPreventiveMaintenanceAcTechnicianEditTextRemarks;
    private Button mPreventiveMaintenanceAcTechnicianButtonPreviousReading;
    private Button mPreventiveMaintenanceAcTechnicianButtonNextReading;

    String str_noOfAcAtSiteVal;
    String str_mainMcbStatusVal;
    String str_subMcbStatusVal;
    String str_metalCladPlugStatusVal;
    String str_metalCladSocketStatusVal;
    String str_stablizerStatusVal;
    String str_stabilizerMakeVal;
    String str_stabilizerCapacityVal;
    String str_stablizerWorkingStatusVal;
    String str_acEarthingStatusVal;
    String str_filterCleanedVal;
    String str_condenserCoilCleanedVal;
    String str_coolingCoilCleanedVal;
    String str_acCoolingStatusVal;
    String str_indoorFilterCleanedStatusVal;
    String str_indoorFanMotorConditionVal;
    String str_blowerWheelConditionVal;
    String str_noiseIndoorMotorVal;
    String str_outdoorFanMotorConditionVal;
    String str_fanLeafConditionVal;
    String str_noiseOutdoorMotorVal;
    String str_compressorConditionVal;
    String str_compCapacitorConditionVal;
    String str_controllerConditionVal;
    String str_acAlarmStatusVal;
    String str_acSensorConditionVal;
    String str_vibrationOfAcVal;
    String str_freeCoolingUnitStatusVal;
    String str_freeCoolingAvailableWorkingStatusVal;
    String str_waterLeakageVal;
    String str_acCabinateStatusVal;
    String str_shelterDoorStatusVal;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_FilterCleanedBeforePhoto = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_FilterCleanedAfterPhoto = 102;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_CondenserCoilCleanedBeforePhoto = 103;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_CondenserCoilCleanedAfterPhoto = 104;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_CoolingCoilCleanedBeforePhoto = 105;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_CoolingCoilCleanedAfterPhoto = 106;


    private String base64StringAcTechnicianQRCodeScan = "";
    //private String imageFileAcTechnicianQRCodeScan;
    //private Uri imageFileUriAcTechnicianQRCodeScan = null;

    private String base64StringFilterCleanedBeforePhoto = "";
    private String base64StringFilterCleanedAfterPhoto = "";
    private String base64StringCondenserCoilCleanedBeforePhoto = "";
    private String base64StringCondenserCoilCleanedAfterPhoto = "";
    private String base64StringCoolingCoilCleanedBeforePhoto = "";
    private String base64StringCoolingCoilCleanedAfterPhoto = "";

    private String imageFileFilterCleanedBeforePhoto;
    private String imageFileFilterCleanedAfterPhoto;
    private String imageFileCondenserCoilCleanedBeforePhoto;
    private String imageFileCondenserCoilCleanedAfterPhoto;
    private String imageFileCoolingCoilCleanedBeforePhoto;
    private String imageFileCoolingCoilCleanedAfterPhoto;

    private Uri imageFileUriFilterCleanedBeforePhoto = null;
    private Uri imageFileUriFilterCleanedAfterPhoto = null;
    private Uri imageFileUriCondenserCoilCleanedBeforePhoto = null;
    private Uri imageFileUriCondenserCoilCleanedAfterPhoto = null;
    private Uri imageFileUriCoolingCoilCleanedBeforePhoto = null;
    private Uri imageFileUriCoolingCoilCleanedAfterPhoto = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private int currentPos = 0;
    private int totalCount = 0;
    private ArrayList<AcPreventiveMaintanceProcessDatum> acPreventiveMaintanceProcessData;
    private AcPreventiveMaintanceProcessParentDatum acPreventiveMaintanceProcessParentDatum;
    MenuItem shareItem;

    private String customerName;
    private String circleName;
    private String stateName;
    private String ssaName;
    private String siteDBId;
    private String siteId;
    private String siteName;
    private String siteType;
    private String TicketId;
    private String TicketNO;
    private String TicketDate;
    private String acPmPlanDate;
    private String submittedDate;
    private String acPmSheduledDate;
    private String numberOfAc;
    private String modeOfOpration;
    private String vendorName;
    private String acTechnicianName;
    private String acTechnicianMobileNo;
    private String accessType;
    private String ticketAccess;
    private String acPmTickStatus;

    private AlertDialogManager alertDialogManager;
    private AcPmTransactionDetails acPmTransactionDetails;

    private boolean flagReadDataByFSE = false;
    public GPSTracker gpsTracker;
    private String technicianLat = "0.0";
    private String technicianLong = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_ac_technician);
        this.setTitle("AC PM Process");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        setListner();
        checkCameraPermission();

        Intent intent = getIntent();
        //statusFlag = intent.getStringExtra("status");
        str_noOfAcAtSiteVal = intent.getStringExtra("numberOfAc");


        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceAcTechnicianActivity.this);
        sessionManager = new SessionManager(PreventiveMaintenanceAcTechnicianActivity.this);
        gpsTracker = new GPSTracker(PreventiveMaintenanceAcTechnicianActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceAcTechnicianActivity.this, userId, ticketName);

        acPmTransactionDetails = new AcPmTransactionDetails();
        acPreventiveMaintanceProcessData = new ArrayList<>();
        currentPos = 0;

        displayDataOnForm(intent);
        invalidateOptionsMenu();
        setNoAcList(str_noOfAcAtSiteVal);

        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            readDataByFSE();
        } else if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            setInputDetails(currentPos);
        }
        invalidateOptionsMenu();
        /*if (!statusFlag.equals("")) {
            disableAllFields();
        }*/

    }

    private void assignViews() {
        mPreventiveMaintenanceAcTechnicianTextViewCircle = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_circle);
        mPreventiveMaintenanceAcTechnicianTextViewCircleVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_circleVal);
        mPreventiveMaintenanceAcTechnicianTextViewState = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_state);
        mPreventiveMaintenanceAcTechnicianTextViewStateVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stateVal);
        mPreventiveMaintenanceAcTechnicianTextViewSsa = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_ssa);
        mPreventiveMaintenanceAcTechnicianTextViewSsaVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_ssaVal);
        mPreventiveMaintenanceAcTechnicianTextViewSiteId = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_siteId);
        mPreventiveMaintenanceAcTechnicianTextViewSiteIdVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_siteIdVal);
        mPreventiveMaintenanceAcTechnicianTextViewSiteName = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_siteName);
        mPreventiveMaintenanceAcTechnicianTextViewSiteNameVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_siteNameVal);
        mPreventiveMaintenanceAcTechnicianTextViewTicketNo = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_ticketNo);
        mPreventiveMaintenanceAcTechnicianTextViewTicketNoVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_ticketNoVal);
        mPreventiveMaintenanceAcTechnicianTextViewTicketDate = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_ticketDate);
        mPreventiveMaintenanceAcTechnicianTextViewTicketDateVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_ticketDateVal);
        mPreventiveMaintenanceAcTechnicianTextViewPmPlanDate = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_pmPlanDate);
        mPreventiveMaintenanceAcTechnicianTextViewPmPlanDateVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_pmPlanDateVal);
        mPreventiveMaintenanceAcTechnicianTextViewSubmittedDate = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_submittedDate);
        mPreventiveMaintenanceAcTechnicianTextViewSubmittedDateVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_submittedDateVal);
        mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSite = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_noOfAcAtSite);
        mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_noOfAcAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceAcTechnicianTextViewAcNumber = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_AcNumber);
        mPreventiveMaintenanceAcTechnicianTextViewQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_QRCodeScan);
        mPreventiveMaintenanceAcTechnicianButtonQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_QRCodeScan);
        mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_QRCodeScanView);
        mButtonClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
        mPreventiveMaintenanceAcTechnicianTextViewAcModel = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acModel);
        mPreventiveMaintenanceAcTechnicianTextViewAcModelVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acModelVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcType = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acType);
        mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acTypeVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcMake = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acMake);
        mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acMakeVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcCapacity = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acCapacity);
        mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acCapacityVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcSerialNo = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acSerialNo);
        mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acSerialNoVal);
        mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_mainMcbStatus);
        mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_mainMcbStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_subMcbStatus);
        mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_subMcbStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_metalCladPlugStatus);
        mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_metalCladPlugStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_metalCladSocketStatus);
        mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_metalCladSocketStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewStablizerStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stablizerStatus);
        mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stablizerStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewStabilizerMake = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stabilizerMake);
        mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stabilizerMakeVal);
        mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacity = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stabilizerCapacity);
        mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stabilizerCapacityVal);
        mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stablizerWorkingStatus);
        mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_stablizerWorkingStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewInputAcVoltage = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_inputAcVoltage);
        mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage = (EditText) findViewById(R.id.preventiveMaintenanceAcTechnician_editText_inputAcVoltage);
        mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acEarthingStatus);
        mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acEarthingStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewFilterCleaned = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_filterCleaned);
        mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_filterCleanedVal);
        mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedBeforePhoto = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_filterCleanedBeforePhoto);
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhoto = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_filterCleanedBeforePhoto);
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_filterCleanedBeforePhotoView);
        mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedAfterPhoto = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_filterCleanedAfterPhoto);
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhoto = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_filterCleanedAfterPhoto);
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_filterCleanedAfterPhotoView);
        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleaned = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_condenserCoilCleaned);
        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_condenserCoilCleanedVal);
        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedBeforePhoto = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_condenserCoilCleanedBeforePhoto);
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhoto = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_condenserCoilCleanedBeforePhoto);
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_condenserCoilCleanedBeforePhotoView);
        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedAfterPhoto = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_condenserCoilCleanedAfterPhoto);
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhoto = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_condenserCoilCleanedAfterPhoto);
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_condenserCoilCleanedAfterPhotoView);
        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleaned = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_coolingCoilCleaned);
        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_coolingCoilCleanedVal);
        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedBeforePhoto = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_coolingCoilCleanedBeforePhoto);
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhoto = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_coolingCoilCleanedBeforePhoto);
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_coolingCoilCleanedBeforePhotoView);
        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedAfterPhoto = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_coolingCoilCleanedAfterPhoto);
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhoto = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_coolingCoilCleanedAfterPhoto);
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceAcTechnician_button_coolingCoilCleanedAfterPhotoView);
        mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acCoolingStatus);
        mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acCoolingStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcStartingLoadcurrent = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acStartingLoadcurrent);
        mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent = (EditText) findViewById(R.id.preventiveMaintenanceAcTechnician_editText_acStartingLoadcurrent);
        mPreventiveMaintenanceAcTechnicianTextViewAcRunningLoadCurrent = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acRunningLoadCurrent);
        mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent = (EditText) findViewById(R.id.preventiveMaintenanceAcTechnician_editText_acRunningLoadCurrent);
        mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_indoorFilterCleanedStatus);
        mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_indoorFilterCleanedStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_indoorFanMotorCondition);
        mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_indoorFanMotorConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_blowerWheelCondition);
        mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_blowerWheelConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotor = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_noiseIndoorMotor);
        mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_noiseIndoorMotorVal);
        mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_outdoorFanMotorCondition);
        mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_outdoorFanMotorConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewFanLeafCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_fanLeafCondition);
        mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_fanLeafConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotor = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_noiseOutdoorMotor);
        mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_noiseOutdoorMotorVal);
        mPreventiveMaintenanceAcTechnicianTextViewCompressorCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_compressorCondition);
        mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_compressorConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_compCapacitorCondition);
        mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_compCapacitorConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewControllerCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_controllerCondition);
        mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_controllerConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acAlarmStatus);
        mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acAlarmStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcSensorCondition = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acSensorCondition);
        mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acSensorConditionVal);
        mPreventiveMaintenanceAcTechnicianTextViewRoomTemperature = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_roomTemperature);
        mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature = (EditText) findViewById(R.id.preventiveMaintenanceAcTechnician_editText_roomTemperature);
        mPreventiveMaintenanceAcTechnicianTextViewSetTemperature = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_setTemperature);
        mPreventiveMaintenanceAcTechnicianEditTextSetTemperature = (EditText) findViewById(R.id.preventiveMaintenanceAcTechnician_editText_setTemperature);
        mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAc = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_vibrationOfAc);
        mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_vibrationOfAcVal);
        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_freeCoolingUnitStatus);
        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_freeCoolingUnitStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_freeCoolingAvailableWorkingStatus);
        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_freeCoolingAvailableWorkingStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewWaterLeakage = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_waterLeakage);
        mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_waterLeakageVal);
        mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acCabinateStatus);
        mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_acCabinateStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatus = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_shelterDoorStatus);
        mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_shelterDoorStatusVal);
        mPreventiveMaintenanceAcTechnicianTextViewRemarks = (TextView) findViewById(R.id.preventiveMaintenanceAcTechnician_textView_remarks);
        mPreventiveMaintenanceAcTechnicianEditTextRemarks = (EditText) findViewById(R.id.preventiveMaintenanceAcTechnician_EditText_remarks);
        mPreventiveMaintenanceAcTechnicianButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceAcTechnician_button_previousReading);
        mPreventiveMaintenanceAcTechnicianButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceAcTechnician_button_nextReading);
    }

    private void initCombo() {
        mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_noOfAcAtSite))),
                        "No of AC at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfAcAtSiteVal = item.get(position);
                        setNoAcList(str_noOfAcAtSiteVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_mainMcbStatus))),
                        "Main MCB Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_mainMcbStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.setText(str_mainMcbStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_subMcbStatus))),
                        "Sub MCB Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_subMcbStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.setText(str_subMcbStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_metalCladPlugStatus))),
                        "Metal Clad Plug Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_metalCladPlugStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.setText(str_metalCladPlugStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_metalCladSocketStatus))),
                        "Metal Clad Socket Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_metalCladSocketStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.setText(str_metalCladSocketStatusVal);
                    }
                });
            }
        });


        mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_stablizerStatus))),
                        "Stablizer Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_stablizerStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.setText(str_stablizerStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_StabilizerMake))),
                        "Stabilizer Make",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_stabilizerMakeVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.setText(str_stabilizerMakeVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_stabilizerCapacity))),
                        "Stabilizer Capacity",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_stabilizerCapacityVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.setText(str_stabilizerCapacityVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_stablizerWorkingStatus))),
                        "Stablizer Working Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_stablizerWorkingStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.setText(str_stablizerWorkingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_acEarthingStatus))),
                        "AC Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_acEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.setText(str_acEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_filterCleaned))),
                        "Filter Cleaned",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_filterCleanedVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.setText(str_filterCleanedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_condenserCoilCleaned))),
                        "Condenser Coil Cleaned",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_condenserCoilCleanedVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.setText(str_condenserCoilCleanedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_coolingCoilCleaned))),
                        "Cooling Coil Cleaned",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_coolingCoilCleanedVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.setText(str_coolingCoilCleanedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_acCoolingStatus))),
                        "AC Cooling Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_acCoolingStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.setText(str_acCoolingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_indoorFilterCleanedStatus))),
                        "Indoor filter Cleaned Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_indoorFilterCleanedStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.setText(str_indoorFilterCleanedStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_indoorFanMotorCondition))),
                        "Indoor Fan motor condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_indoorFanMotorConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.setText(str_indoorFanMotorConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_blowerWheelCondition))),
                        "Blower Wheel condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_blowerWheelConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.setText(str_blowerWheelConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_noiseIndoorMotor))),
                        "If any noise Indoor Motor",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noiseIndoorMotorVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.setText(str_noiseIndoorMotorVal);
                    }
                });
            }
        });


        mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_outdoorFanMotorCondition))),
                        "Outdoor Fan motor condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_outdoorFanMotorConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.setText(str_outdoorFanMotorConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_fanLeafCondition))),
                        "Fan Leaf Condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_fanLeafConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.setText(str_fanLeafConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_noiseOutdoorMotor))),
                        "If any noise Outdoor Motor",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noiseOutdoorMotorVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.setText(str_noiseOutdoorMotorVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_compressorCondition))),
                        "Compressor condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_compressorConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.setText(str_compressorConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_compCapacitorCondition))),
                        "Comp - Capacitor condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_compCapacitorConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.setText(str_compCapacitorConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_controllerCondition))),
                        "Controller condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_controllerConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.setText(str_controllerConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_acAlarmStatus))),
                        "AC Alarm Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_acAlarmStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.setText(str_acAlarmStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_acSensorCondition))),
                        "AC Sensor condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_acSensorConditionVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.setText(str_acSensorConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_vibrationOfAc))),
                        "If any Vibration of AC",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_vibrationOfAcVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.setText(str_vibrationOfAcVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_freeCoolingUnitStatus))),
                        "Free Cooling Unit Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_freeCoolingUnitStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.setText(str_freeCoolingUnitStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_freeCoolingAvailableWorkingStatus))),
                        "If Free Cooling Available, Working Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_freeCoolingAvailableWorkingStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.setText(str_freeCoolingAvailableWorkingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_waterLeakage))),
                        "Water Leakage if Any",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_waterLeakageVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.setText(str_waterLeakageVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_acCabinateStatus))),
                        "AC Cabinate Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_acCabinateStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.setText(str_acCabinateStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceAcTechnicianActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmAcTechnician_shelterDoorStatus))),
                        "Shelter Door Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterDoorStatusVal = item.get(position);
                        mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.setText(str_shelterDoorStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                    /*if (checkValidationOfArrayFields() == true) {*/
                    if (currentPos > 0) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos - 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    }
                    /*}*/
                } else {
                    if (currentPos > 0) {
                        currentPos = currentPos - 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    }
                }
            }
        });

        mPreventiveMaintenanceAcTechnicianButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                    if (checkValidationOfArrayFields() == true) {
                        if (currentPos < (totalCount - 1)) {
                            //Save current ac reading
                            saveRecords(currentPos);
                            currentPos = currentPos + 1;
                            //move to Next reading
                            displayRecords(currentPos);

                        } else if (currentPos == (totalCount - 1)) {
                            saveRecords(currentPos);

                            /*if (checkDuplicationQrCodeNew() == false) {*/
                            if (checkValidationonSubmit("onSubmit") == true) {
                                //submitDetails();
                                if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                                    showToast("You are in readable mode");
                                } else {
                                    if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {

                                        LocationManager lm = (LocationManager) PreventiveMaintenanceAcTechnicianActivity.this.getSystemService(Context.LOCATION_SERVICE);
                                        boolean gps_enabled = false;
                                        boolean network_enabled = false;

                                        try {
                                            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                                        } catch (Exception ex) {
                                        }

                                        if (!gps_enabled && !network_enabled) {
                                            // notify user
                                            alertDialogManager = new AlertDialogManager(PreventiveMaintenanceAcTechnicianActivity.this);
                                            alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                                @Override
                                                public void onPositiveClick() {
                                                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                    PreventiveMaintenanceAcTechnicianActivity.this.startActivity(myIntent);
                                                }
                                            }).show();
                                        } else {
                                            if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                                                technicianLat = String.valueOf(gpsTracker.getLatitude());
                                                technicianLong = String.valueOf(gpsTracker.getLongitude());
                                                showSettingsAlert();

                                            } else {
                                                //showToast("Could not detecting location.");
                                                alertDialogManager = new AlertDialogManager(PreventiveMaintenanceAcTechnicianActivity.this);
                                                alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                                    @Override
                                                    public void onPositiveClick() {
                                                        if (gpsTracker.canGetLocation()) {
                                                            //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By 008 on 10-11-2018
                                                            Log.e(UserHotoTransactionActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                                        }
                                                    }
                                                }).show();
                                            }
                                        }
                                        //showSettingsAlert();
                                    } else {
                                        showToast("Unauthorised ticket");
                                    }
                                }
                                //finish();
                            }
                        }

                        /*}*/
                    }
                } else {
                    if (currentPos < (totalCount - 1)) {
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    } else if (currentPos == (totalCount - 1)) {
                        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                            showToast("You are in readable mode");
                        }
                    }

                }
            }
        });

    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceAcTechnicianActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceAcTechnicianActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    private void setListner() {

        mPreventiveMaintenanceAcTechnicianButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    AcTechnicianQRCodeScan();
                }
            }
        });

        mButtonClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringAcTechnicianQRCodeScan = "";
                mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText("");
                mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText("");
                mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText("");
                mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText("");
                mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText("");
                mButtonClearQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        //////////////

        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    FilterCleanedBeforePhoto();
                }
            }
        });

        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    FilterCleanedAfterPhoto();
                }
            }
        });

        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    CondenserCoilCleanedBeforePhoto();
                }
            }
        });
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    CondenserCoilCleanedAfterPhoto();
                }
            }
        });

        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    CoolingCoilCleanedBeforePhoto();
                }
            }
        });
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    CoolingCoilCleanedAfterPhoto();
                }
            }
        });

        //////////////////////////

        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriFilterCleanedBeforePhoto != null) {
                    /*if (flagReadDataByFSE == true) {
                        GlobalMethods.showImageDialogFromUrl(PreventiveMaintenanceAcTechnicianActivity.this, "");
                    } else {*/
                    GlobalMethods.showImageDialog(PreventiveMaintenanceAcTechnicianActivity.this, imageFileUriFilterCleanedBeforePhoto);
                    /*}*/
                } else {
                    Toast.makeText(PreventiveMaintenanceAcTechnicianActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriFilterCleanedAfterPhoto != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceAcTechnicianActivity.this, imageFileUriFilterCleanedAfterPhoto);
                } else {
                    Toast.makeText(PreventiveMaintenanceAcTechnicianActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriCondenserCoilCleanedBeforePhoto != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceAcTechnicianActivity.this, imageFileUriCondenserCoilCleanedBeforePhoto);
                } else {
                    Toast.makeText(PreventiveMaintenanceAcTechnicianActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriCondenserCoilCleanedAfterPhoto != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceAcTechnicianActivity.this, imageFileUriCondenserCoilCleanedAfterPhoto);
                } else {
                    Toast.makeText(PreventiveMaintenanceAcTechnicianActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriCoolingCoilCleanedBeforePhoto != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceAcTechnicianActivity.this, imageFileUriCoolingCoilCleanedBeforePhoto);
                } else {
                    Toast.makeText(PreventiveMaintenanceAcTechnicianActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriCoolingCoilCleanedAfterPhoto != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceAcTechnicianActivity.this, imageFileUriCoolingCoilCleanedAfterPhoto);
                } else {
                    Toast.makeText(PreventiveMaintenanceAcTechnicianActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void AcTechnicianQRCodeScan() {
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

    private void FilterCleanedBeforePhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileFilterCleanedBeforePhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileFilterCleanedBeforePhoto);
            imageFileUriFilterCleanedBeforePhoto = FileProvider.getUriForFile(PreventiveMaintenanceAcTechnicianActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriFilterCleanedBeforePhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_FilterCleanedBeforePhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void FilterCleanedAfterPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileFilterCleanedAfterPhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_cautionBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileFilterCleanedAfterPhoto);
            imageFileUriFilterCleanedAfterPhoto = FileProvider.getUriForFile(PreventiveMaintenanceAcTechnicianActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriFilterCleanedAfterPhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_FilterCleanedAfterPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CondenserCoilCleanedBeforePhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileCondenserCoilCleanedBeforePhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_warningBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileCondenserCoilCleanedBeforePhoto);
            imageFileUriCondenserCoilCleanedBeforePhoto = FileProvider.getUriForFile(PreventiveMaintenanceAcTechnicianActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriCondenserCoilCleanedBeforePhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_CondenserCoilCleanedBeforePhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CondenserCoilCleanedAfterPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileCondenserCoilCleanedAfterPhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dangerBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileCondenserCoilCleanedAfterPhoto);
            imageFileUriCondenserCoilCleanedAfterPhoto = FileProvider.getUriForFile(PreventiveMaintenanceAcTechnicianActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriCondenserCoilCleanedAfterPhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_CondenserCoilCleanedAfterPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CoolingCoilCleanedBeforePhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileCoolingCoilCleanedBeforePhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_warningBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileCoolingCoilCleanedBeforePhoto);
            imageFileUriCoolingCoilCleanedBeforePhoto = FileProvider.getUriForFile(PreventiveMaintenanceAcTechnicianActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriCoolingCoilCleanedBeforePhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_CoolingCoilCleanedBeforePhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CoolingCoilCleanedAfterPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileCoolingCoilCleanedAfterPhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dangerBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileCoolingCoilCleanedAfterPhoto);
            imageFileUriCoolingCoilCleanedAfterPhoto = FileProvider.getUriForFile(PreventiveMaintenanceAcTechnicianActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriCoolingCoilCleanedAfterPhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_CoolingCoilCleanedAfterPhoto);
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
                    mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringAcTechnicianQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        if (accessType.equals("A") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
                            if (checkDuplicationQrCodeNew(result.getContents()) == false) {
                                base64StringAcTechnicianQRCodeScan = result.getContents();
                                if (!base64StringAcTechnicianQRCodeScan.isEmpty() && base64StringAcTechnicianQRCodeScan != null) {
                                    mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.VISIBLE);
                                    mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                                    GetScannedAcQrData();
                                }
                            }
                        }
                        /*Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];

                        /*} else {
                            base64StringAcTechnicianQRCodeScan = "";
                            showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                        }*/
                    }
                }
                break;


            case MY_PERMISSIONS_REQUEST_CAMERA_FilterCleanedBeforePhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriFilterCleanedBeforePhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriFilterCleanedBeforePhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringFilterCleanedBeforePhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileFilterCleanedBeforePhoto = "";
                    imageFileUriFilterCleanedBeforePhoto = null;
                    mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_FilterCleanedAfterPhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriFilterCleanedAfterPhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriFilterCleanedAfterPhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringFilterCleanedAfterPhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileFilterCleanedAfterPhoto = "";
                    imageFileUriFilterCleanedAfterPhoto = null;
                    mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_CondenserCoilCleanedBeforePhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriCondenserCoilCleanedBeforePhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriCondenserCoilCleanedBeforePhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringCondenserCoilCleanedBeforePhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileCondenserCoilCleanedBeforePhoto = "";
                    imageFileUriCondenserCoilCleanedBeforePhoto = null;
                    mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_CondenserCoilCleanedAfterPhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriCondenserCoilCleanedAfterPhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriCondenserCoilCleanedAfterPhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringCondenserCoilCleanedAfterPhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileCondenserCoilCleanedAfterPhoto = "";
                    imageFileUriCondenserCoilCleanedAfterPhoto = null;
                    mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_CoolingCoilCleanedBeforePhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriCoolingCoilCleanedBeforePhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriCoolingCoilCleanedBeforePhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringCoolingCoilCleanedBeforePhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileCoolingCoilCleanedBeforePhoto = "";
                    imageFileUriCoolingCoilCleanedBeforePhoto = null;
                    mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_CoolingCoilCleanedAfterPhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriCoolingCoilCleanedAfterPhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriCoolingCoilCleanedAfterPhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringCoolingCoilCleanedAfterPhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileCoolingCoilCleanedAfterPhoto = "";
                    imageFileUriCoolingCoilCleanedAfterPhoto = null;
                    mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        shareItem = menu.findItem(R.id.menuSubmit);
        shareItem.setVisible(true);
        if (str_noOfAcAtSiteVal != null && !str_noOfAcAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_noOfAcAtSiteVal) > 0) {
                shareItem.setVisible(false);
            }
        }

        if (accessType.equals("S") && ticketAccess.equals("1") && acPmTickStatus.equals("WIP")) {
            shareItem.setVisible(false);
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
                    LocationManager lm = (LocationManager) PreventiveMaintenanceAcTechnicianActivity.this.getSystemService(Context.LOCATION_SERVICE);
                    boolean gps_enabled = false;
                    boolean network_enabled = false;

                    try {
                        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    } catch (Exception ex) {
                    }

                    if (!gps_enabled && !network_enabled) {
                        // notify user
                        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceAcTechnicianActivity.this);
                        alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                PreventiveMaintenanceAcTechnicianActivity.this.startActivity(myIntent);
                            }
                        }).show();
                    } else {
                        if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                            technicianLat = String.valueOf(gpsTracker.getLatitude());
                            technicianLong = String.valueOf(gpsTracker.getLongitude());
                            showSettingsAlert();

                        } else {
                            //showToast("Could not detecting location.");
                            alertDialogManager = new AlertDialogManager(PreventiveMaintenanceAcTechnicianActivity.this);
                            alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                @Override
                                public void onPositiveClick() {
                                    if (gpsTracker.canGetLocation()) {
                                        //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By 008 on 10-11-2018
                                        Log.e(UserHotoTransactionActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                    }
                                }
                            }).show();
                        }
                    }

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
    ///////////////////////////////////////////////////////////////////////////////

    //use this for readable Web Service
    private void setInputDetails(int index) {

        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                acPmTransactionDetails = gson.fromJson(jsonInString, AcPmTransactionDetails.class);
                if (acPmTransactionDetails != null) {
                    setAcPmData(index);
                } else {
                    showToast("No previous saved data available");
                    mLinearLayoutContainer.setVisibility(View.GONE);
                    setNoAcList(str_noOfAcAtSiteVal);//008
                }

            } else {
                showToast("No previous saved data available");
                mLinearLayoutContainer.setVisibility(View.GONE);
                setNoAcList(str_noOfAcAtSiteVal);//008
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setAcPmData(int index) {
        acPreventiveMaintanceProcessParentDatum = acPmTransactionDetails.getAcPreventiveMaintanceProcessParentDatum();

        //acPreventiveMaintanceProcessParentDatum = gson.fromJson(jsonInString, AcPreventiveMaintanceProcessParentDatum.class);
        acPreventiveMaintanceProcessData.addAll(acPreventiveMaintanceProcessParentDatum.getAcPreventiveMaintanceProcessData());

                /*mPreventiveMaintenanceAcTechnicianTextViewCircleVal.setText(acPreventiveMaintanceProcessParentDatum.getCircle());
                mPreventiveMaintenanceAcTechnicianTextViewStateVal.setText(acPreventiveMaintanceProcessParentDatum.getState());
                mPreventiveMaintenanceAcTechnicianTextViewSsaVal.setText(acPreventiveMaintanceProcessParentDatum.getSsa());
                mPreventiveMaintenanceAcTechnicianTextViewSiteIdVal.setText(acPreventiveMaintanceProcessParentDatum.getSiteId());
                mPreventiveMaintenanceAcTechnicianTextViewSiteNameVal.setText(acPreventiveMaintanceProcessParentDatum.getSiteName());
                mPreventiveMaintenanceAcTechnicianTextViewTicketNoVal.setText(acPreventiveMaintanceProcessParentDatum.getTicketNo());
                mPreventiveMaintenanceAcTechnicianTextViewTicketDateVal.setText(acPreventiveMaintanceProcessParentDatum.getTicketDate());
                mPreventiveMaintenanceAcTechnicianTextViewPmPlanDateVal.setText(acPreventiveMaintanceProcessParentDatum.getPmPlanDate());
                mPreventiveMaintenanceAcTechnicianTextViewSubmittedDateVal.setText(acPreventiveMaintanceProcessParentDatum.getSubmittedDate());*/

        mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal.setText(acPreventiveMaintanceProcessParentDatum.getNoOfAcAtSite());

        invalidateOptionsMenu();
        if (acPreventiveMaintanceProcessData != null && acPreventiveMaintanceProcessData.size() > 0) {
            mLinearLayoutContainer.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceAcTechnicianTextViewAcNumber.setText("Reading: #1");
            totalCount = Integer.parseInt(acPreventiveMaintanceProcessParentDatum.getNoOfAcAtSite() == null ? "0" : acPreventiveMaintanceProcessParentDatum.getNoOfAcAtSite());

            base64StringAcTechnicianQRCodeScan = acPreventiveMaintanceProcessData.get(index).getAcPreventiveMaintanceProcessQrCodeScan();
            mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
            mButtonClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringAcTechnicianQRCodeScan.isEmpty() && base64StringAcTechnicianQRCodeScan != null) {
                mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText(acPreventiveMaintanceProcessData.get(index).getAcModel());
            mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText(acPreventiveMaintanceProcessData.get(index).getAcType());
            mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText(acPreventiveMaintanceProcessData.get(index).getAcMake());
            mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText(acPreventiveMaintanceProcessData.get(index).getAcCapacity());
            mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText(acPreventiveMaintanceProcessData.get(index).getAcSerialNo());
            mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getMainMcbStatus());
            mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getSubMcbStatus());
            mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getMetalCladPlugStatus());
            mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getMetalCladSocketStatus());
            mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.setText(acPreventiveMaintanceProcessData.get(index).getStabilizerMake());
            mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getStabilizerStatus());
            mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.setText(acPreventiveMaintanceProcessData.get(index).getStabilizerCapacity());
            mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getStabilizerWorkingStatus());
            mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage.setText(acPreventiveMaintanceProcessData.get(index).getInputAcVoltage());
            mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getAcEarthingStatus());
            mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.setText(acPreventiveMaintanceProcessData.get(index).getFilterCleaned());
/*if (flagReadDataByFSE == true) {

                } else {*//*}*/

            base64StringFilterCleanedBeforePhoto = acPreventiveMaintanceProcessData.get(index).getBase64StringAcFilterPhotoBeforeCleaned();
            if (base64StringFilterCleanedBeforePhoto.contains("data:image/jpg;base64,")) {
                base64StringFilterCleanedBeforePhoto = base64StringFilterCleanedBeforePhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.GONE);
            if (!base64StringFilterCleanedBeforePhoto.isEmpty() && base64StringFilterCleanedBeforePhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.VISIBLE);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringFilterCleanedBeforePhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriFilterCleanedBeforePhoto = Uri.parse(path);

            }

            base64StringFilterCleanedAfterPhoto = acPreventiveMaintanceProcessData.get(index).getBase64StringAcFilterPhotoAfterCleaned();
            if (base64StringFilterCleanedAfterPhoto.contains("data:image/jpg;base64,")) {
                base64StringFilterCleanedAfterPhoto = base64StringFilterCleanedAfterPhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.GONE);
            if (!base64StringFilterCleanedAfterPhoto.isEmpty() && base64StringFilterCleanedAfterPhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringFilterCleanedAfterPhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriFilterCleanedAfterPhoto = Uri.parse(path);
            }

            mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.setText(acPreventiveMaintanceProcessData.get(index).getCondenserCoilCleaned());

            base64StringCondenserCoilCleanedBeforePhoto = acPreventiveMaintanceProcessData.get(index).getBase64StringCondenserCoilPhotoBeforeCleaned();
            if (base64StringCondenserCoilCleanedBeforePhoto.contains("data:image/jpg;base64,")) {
                base64StringCondenserCoilCleanedBeforePhoto = base64StringCondenserCoilCleanedBeforePhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.GONE);
            if (!base64StringCondenserCoilCleanedBeforePhoto.isEmpty() && base64StringCondenserCoilCleanedBeforePhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCondenserCoilCleanedBeforePhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCondenserCoilCleanedBeforePhoto = Uri.parse(path);
            }

            base64StringCondenserCoilCleanedAfterPhoto = acPreventiveMaintanceProcessData.get(index).getBase64StringCondenserCoilPhotoAfterCleaned();
            if (base64StringCondenserCoilCleanedAfterPhoto.contains("data:image/jpg;base64,")) {
                base64StringCondenserCoilCleanedAfterPhoto = base64StringCondenserCoilCleanedAfterPhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.GONE);
            if (!base64StringCondenserCoilCleanedAfterPhoto.isEmpty() && base64StringCondenserCoilCleanedAfterPhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCondenserCoilCleanedAfterPhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCondenserCoilCleanedAfterPhoto = Uri.parse(path);
            }

            mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.setText(acPreventiveMaintanceProcessData.get(index).getMainMcbStatus());

            base64StringCoolingCoilCleanedBeforePhoto = acPreventiveMaintanceProcessData.get(index).getBase64StringCoolingCoilPhotoBeforeCleaned();
            if (base64StringCoolingCoilCleanedBeforePhoto.contains("data:image/jpg;base64,")) {
                base64StringCoolingCoilCleanedBeforePhoto = base64StringCoolingCoilCleanedBeforePhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.GONE);
            if (!base64StringCoolingCoilCleanedBeforePhoto.isEmpty() && base64StringCoolingCoilCleanedBeforePhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCoolingCoilCleanedBeforePhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCoolingCoilCleanedBeforePhoto = Uri.parse(path);
            }

            base64StringCoolingCoilCleanedAfterPhoto = acPreventiveMaintanceProcessData.get(index).getBase64StringCoolingCoilPhotoAfterCleaned();
            if (base64StringCoolingCoilCleanedAfterPhoto.contains("data:image/jpg;base64,")) {
                base64StringCoolingCoilCleanedAfterPhoto = base64StringCoolingCoilCleanedAfterPhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.GONE);
            if (!base64StringCoolingCoilCleanedAfterPhoto.isEmpty() && base64StringCoolingCoilCleanedAfterPhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCoolingCoilCleanedAfterPhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCoolingCoilCleanedAfterPhoto = Uri.parse(path);
            }

            mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getAcCoolingStatus());
            mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent.setText(acPreventiveMaintanceProcessData.get(index).getAcStartingLoadCurrent());
            mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent.setText(acPreventiveMaintanceProcessData.get(index).getAcRunningLoadCurrent());
            mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getIndoorFilterCleanedStatus());
            mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getIndoorFanMotorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getBlowerWheelCondition());
            mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.setText(acPreventiveMaintanceProcessData.get(index).getIfAnyNoiseIndoorMotor());
            mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getOutdoorFanMotorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getFanLeafCondition());
            mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.setText(acPreventiveMaintanceProcessData.get(index).getIfAnyNoiseOutdoorMotor());
            mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getCompressorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getCompCapacitorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getControllerCondition());
            mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getAcAlarmStatus());
            mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.setText(acPreventiveMaintanceProcessData.get(index).getAcSensorCondition());
            mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature.setText(acPreventiveMaintanceProcessData.get(index).getRoomTemperature());
            mPreventiveMaintenanceAcTechnicianEditTextSetTemperature.setText(acPreventiveMaintanceProcessData.get(index).getSetTemperature());
            mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.setText(acPreventiveMaintanceProcessData.get(index).getIfAnyVibrationOfAc());
            mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getFreeCoolingUnitStatus());
            mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getIfFreeCoolingAvaibleWorkingStatus());
            mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.setText(acPreventiveMaintanceProcessData.get(index).getWaterLeakageIfAny());
            mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getAcCabinateStatus());
            mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.setText(acPreventiveMaintanceProcessData.get(index).getShelterDoorStatus());
            mPreventiveMaintenanceAcTechnicianEditTextRemarks.setText(acPreventiveMaintanceProcessData.get(index).getRemark());

            mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setVisibility(View.GONE);
            mPreventiveMaintenanceAcTechnicianButtonNextReading.setVisibility(View.VISIBLE);

            if (totalCount > 1) {
                mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Next Reading");
            } else {
                mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Finish");
            }
        }
    }

    private void displayRecords(int pos) {

        if (acPreventiveMaintanceProcessData.size() > 0 && pos < acPreventiveMaintanceProcessData.size()) {

            mPreventiveMaintenanceAcTechnicianTextViewAcNumber.setText("Reading: #" + (pos + 1));
            base64StringAcTechnicianQRCodeScan = acPreventiveMaintanceProcessData.get(pos).getAcPreventiveMaintanceProcessQrCodeScan();
            mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
            mButtonClearQRCodeScanView.setVisibility(View.GONE);

            if (!base64StringAcTechnicianQRCodeScan.isEmpty() && base64StringAcTechnicianQRCodeScan != null) {
                mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcModel());
            mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcType());
            mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcMake());
            mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcCapacity());
            mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcSerialNo());
            mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getMainMcbStatus());
            mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getSubMcbStatus());
            mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getMetalCladPlugStatus());
            mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getMetalCladSocketStatus());
            mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.setText(acPreventiveMaintanceProcessData.get(pos).getStabilizerMake());
            mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getStabilizerStatus());
            mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.setText(acPreventiveMaintanceProcessData.get(pos).getStabilizerCapacity());
            mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getStabilizerWorkingStatus());
            mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage.setText(acPreventiveMaintanceProcessData.get(pos).getInputAcVoltage());
            mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcEarthingStatus());
            mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.setText(acPreventiveMaintanceProcessData.get(pos).getFilterCleaned());

            base64StringFilterCleanedBeforePhoto = acPreventiveMaintanceProcessData.get(pos).getBase64StringAcFilterPhotoBeforeCleaned();
            if (base64StringFilterCleanedBeforePhoto.contains("data:image/jpg;base64,")) {
                base64StringFilterCleanedBeforePhoto = base64StringFilterCleanedBeforePhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.GONE);
            if (!base64StringFilterCleanedBeforePhoto.isEmpty() && base64StringFilterCleanedBeforePhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringFilterCleanedBeforePhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriFilterCleanedBeforePhoto = Uri.parse(path);
            }

            base64StringFilterCleanedAfterPhoto = acPreventiveMaintanceProcessData.get(pos).getBase64StringAcFilterPhotoAfterCleaned();
            if (base64StringFilterCleanedAfterPhoto.contains("data:image/jpg;base64,")) {
                base64StringFilterCleanedAfterPhoto = base64StringFilterCleanedAfterPhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.GONE);
            if (!base64StringFilterCleanedAfterPhoto.isEmpty() && base64StringFilterCleanedAfterPhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringFilterCleanedAfterPhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriFilterCleanedAfterPhoto = Uri.parse(path);
            }

            mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.setText(acPreventiveMaintanceProcessData.get(pos).getCondenserCoilCleaned());
            base64StringCondenserCoilCleanedBeforePhoto = acPreventiveMaintanceProcessData.get(pos).getBase64StringCondenserCoilPhotoBeforeCleaned();
            if (base64StringCondenserCoilCleanedBeforePhoto.contains("data:image/jpg;base64,")) {
                base64StringCondenserCoilCleanedBeforePhoto = base64StringCondenserCoilCleanedBeforePhoto.replace("data:image/jpg;base64,", "");
            }

            mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.GONE);
            if (!base64StringCondenserCoilCleanedBeforePhoto.isEmpty() && base64StringCondenserCoilCleanedBeforePhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCondenserCoilCleanedBeforePhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCondenserCoilCleanedBeforePhoto = Uri.parse(path);
            }

            base64StringCondenserCoilCleanedAfterPhoto = acPreventiveMaintanceProcessData.get(pos).getBase64StringCondenserCoilPhotoAfterCleaned();
            if (base64StringCondenserCoilCleanedAfterPhoto.contains("data:image/jpg;base64,")) {
                base64StringCondenserCoilCleanedAfterPhoto = base64StringCondenserCoilCleanedAfterPhoto.replace("data:image/jpg;base64,", "");
            }

            mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.GONE);
            if (!base64StringCondenserCoilCleanedAfterPhoto.isEmpty() && base64StringCondenserCoilCleanedAfterPhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCondenserCoilCleanedAfterPhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCondenserCoilCleanedAfterPhoto = Uri.parse(path);
            }

            mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.setText(acPreventiveMaintanceProcessData.get(pos).getMainMcbStatus());

            base64StringCoolingCoilCleanedBeforePhoto = acPreventiveMaintanceProcessData.get(pos).getBase64StringCoolingCoilPhotoBeforeCleaned();
            if (base64StringCoolingCoilCleanedBeforePhoto.contains("data:image/jpg;base64,")) {
                base64StringCoolingCoilCleanedBeforePhoto = base64StringCoolingCoilCleanedBeforePhoto.replace("data:image/jpg;base64,", "");
            }

            mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.GONE);
            if (!base64StringCoolingCoilCleanedBeforePhoto.isEmpty() && base64StringCoolingCoilCleanedBeforePhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCoolingCoilCleanedBeforePhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCoolingCoilCleanedBeforePhoto = Uri.parse(path);
            }

            base64StringCoolingCoilCleanedAfterPhoto = acPreventiveMaintanceProcessData.get(pos).getBase64StringCoolingCoilPhotoAfterCleaned();
            if (base64StringCoolingCoilCleanedAfterPhoto.contains("data:image/jpg;base64,")) {
                base64StringCoolingCoilCleanedAfterPhoto = base64StringCoolingCoilCleanedAfterPhoto.replace("data:image/jpg;base64,", "");
            }
            mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.GONE);
            if (!base64StringCoolingCoilCleanedAfterPhoto.isEmpty() && base64StringCoolingCoilCleanedAfterPhoto != null) {
                mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringCoolingCoilCleanedAfterPhoto);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriCoolingCoilCleanedAfterPhoto = Uri.parse(path);
            }

            mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcCoolingStatus());
            mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent.setText(acPreventiveMaintanceProcessData.get(pos).getAcStartingLoadCurrent());
            mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent.setText(acPreventiveMaintanceProcessData.get(pos).getAcRunningLoadCurrent());
            mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getIndoorFilterCleanedStatus());
            mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getIndoorFanMotorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getBlowerWheelCondition());
            mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.setText(acPreventiveMaintanceProcessData.get(pos).getIfAnyNoiseIndoorMotor());
            mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getOutdoorFanMotorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getFanLeafCondition());
            mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.setText(acPreventiveMaintanceProcessData.get(pos).getIfAnyNoiseOutdoorMotor());
            mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getCompressorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getCompCapacitorCondition());
            mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getControllerCondition());
            mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcAlarmStatus());
            mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcSensorCondition());
            mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature.setText(acPreventiveMaintanceProcessData.get(pos).getRoomTemperature());
            mPreventiveMaintenanceAcTechnicianEditTextSetTemperature.setText(acPreventiveMaintanceProcessData.get(pos).getSetTemperature());
            mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.setText(acPreventiveMaintanceProcessData.get(pos).getIfAnyVibrationOfAc());
            mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getFreeCoolingUnitStatus());
            mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getIfFreeCoolingAvaibleWorkingStatus());
            mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.setText(acPreventiveMaintanceProcessData.get(pos).getWaterLeakageIfAny());
            mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getAcCabinateStatus());
            mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.setText(acPreventiveMaintanceProcessData.get(pos).getShelterDoorStatus());
            mPreventiveMaintenanceAcTechnicianEditTextRemarks.setText(acPreventiveMaintanceProcessData.get(pos).getRemark());

            mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceAcTechnicianButtonNextReading.setVisibility(View.VISIBLE);
        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Next Reading");
            }
        }
    }

    private void saveRecords(int pos) {
        String qrCodeScan = base64StringAcTechnicianQRCodeScan;
        String acModel = mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.getText().toString().trim();
        String acType = mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.getText().toString().trim();
        String acMake = mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.getText().toString().trim();
        String acCapacity = mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.getText().toString().trim();
        String acSerialNo = mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.getText().toString().trim();
        String mainMcbStatus = mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.getText().toString().trim();
        String subMcbStatus = mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.getText().toString().trim();
        String metalCladPlugStatus = mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.getText().toString().trim();
        String metalCladSocketStatus = mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.getText().toString().trim();
        String stabilizerMake = mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.getText().toString().trim();
        String stabilizerStatus = mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.getText().toString().trim();
        String stabilizerCapacity = mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.getText().toString().trim();
        String stabilizerWorkingStatus = mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.getText().toString().trim();
        String inputAcVoltage = mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage.getText().toString().trim();
        String acEarthingStatus = mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.getText().toString().trim();
        String filterCleaned = mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.getText().toString().trim();
        String filterPhotoBeforeCleaned = base64StringFilterCleanedBeforePhoto;
        String filterPhotoAfterCleaned = base64StringFilterCleanedAfterPhoto;
        String condenserCoilCleaned = mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.getText().toString().trim();
        String condenserCoilPhotoBeforeCleaned = base64StringCondenserCoilCleanedBeforePhoto;
        String condenserCoilPhotoAfterCleaned = base64StringCondenserCoilCleanedAfterPhoto;
        String coolingCoilCleaned = mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.getText().toString().trim();
        String coolingCoilPhotoBeforeCleaned = base64StringCoolingCoilCleanedBeforePhoto;
        String coolingCoilPhotoAfterCleaned = base64StringCoolingCoilCleanedAfterPhoto;
        String acCoolingStatus = mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.getText().toString().trim();
        String acStartingLoadCurrent = mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent.getText().toString().trim();
        String acRunningLoadCurrent = mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent.getText().toString().trim();
        String indoorFilterCleanedStatus = mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.getText().toString().trim();
        String indoorFanMotorCondition = mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.getText().toString().trim();
        String blowerWheelCondition = mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.getText().toString().trim();
        String ifAnyNoiseIndoorMotor = mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.getText().toString().trim();
        String outdoorFanMotorCondition = mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.getText().toString().trim();
        String fanLeafCondition = mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.getText().toString().trim();
        String ifAnyNoiseOutdoorMotor = mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.getText().toString().trim();
        String compressorCondition = mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.getText().toString().trim();
        String compCapacitorCondition = mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.getText().toString().trim();
        String controllerCondition = mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.getText().toString().trim();
        String acAlarmStatus = mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.getText().toString().trim();
        String acSensorCondition = mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.getText().toString().trim();
        String roomTemperature = mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature.getText().toString().trim();
        String setTemperature = mPreventiveMaintenanceAcTechnicianEditTextSetTemperature.getText().toString().trim();
        String ifAnyVibrationOfAc = mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.getText().toString().trim();
        String freeCoolingUnitStatus = mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.getText().toString().trim();
        String ifFreeCoolingAvailableWorkingStatus = mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.getText().toString().trim();
        String waterLeakageIfAny = mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.getText().toString().trim();
        String acCabinateStatus = mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.getText().toString().trim();
        String shelterDoorStatus = mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.getText().toString().trim();
        String remark = mPreventiveMaintenanceAcTechnicianEditTextRemarks.getText().toString().trim();

        AcPreventiveMaintanceProcessDatum acPreventiveMaintanceProcessDatum = new AcPreventiveMaintanceProcessDatum(qrCodeScan, acModel, acType, acMake, acCapacity,
                acSerialNo, mainMcbStatus, subMcbStatus, metalCladPlugStatus, metalCladSocketStatus, stabilizerStatus, stabilizerMake, stabilizerCapacity,
                stabilizerWorkingStatus, inputAcVoltage, acEarthingStatus, filterCleaned, filterPhotoBeforeCleaned, filterPhotoAfterCleaned, condenserCoilCleaned,
                condenserCoilPhotoBeforeCleaned, condenserCoilPhotoAfterCleaned, coolingCoilCleaned, coolingCoilPhotoBeforeCleaned, coolingCoilPhotoAfterCleaned,
                acCoolingStatus, acStartingLoadCurrent, acRunningLoadCurrent, indoorFilterCleanedStatus, indoorFanMotorCondition, blowerWheelCondition, ifAnyNoiseIndoorMotor,
                outdoorFanMotorCondition, fanLeafCondition, ifAnyNoiseOutdoorMotor, compressorCondition, compCapacitorCondition, controllerCondition, acAlarmStatus,
                acSensorCondition, roomTemperature, setTemperature, ifAnyVibrationOfAc, freeCoolingUnitStatus, ifFreeCoolingAvailableWorkingStatus, waterLeakageIfAny,
                acCabinateStatus, shelterDoorStatus, remark);

        if (acPreventiveMaintanceProcessData.size() > 0) {
            if (pos == acPreventiveMaintanceProcessData.size()) {
                acPreventiveMaintanceProcessData.add(acPreventiveMaintanceProcessDatum);
            } else if (pos < acPreventiveMaintanceProcessData.size()) {
                acPreventiveMaintanceProcessData.set(pos, acPreventiveMaintanceProcessDatum);
            }
        } else {
            acPreventiveMaintanceProcessData.add(acPreventiveMaintanceProcessDatum);
        }

    }

    private void clearFields(int pos) {

        mPreventiveMaintenanceAcTechnicianTextViewAcNumber.setText("Reading: #" + (pos + 1));
        base64StringAcTechnicianQRCodeScan = "";
        mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
        mButtonClearQRCodeScanView.setVisibility(View.GONE);

        mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.setText("");

        base64StringFilterCleanedBeforePhoto = "";
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedBeforePhotoView.setVisibility(View.GONE);

        base64StringFilterCleanedAfterPhoto = "";
        mPreventiveMaintenanceAcTechnicianButtonFilterCleanedAfterPhotoView.setVisibility(View.GONE);

        mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.setText("");

        base64StringCondenserCoilCleanedBeforePhoto = "";
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedBeforePhotoView.setVisibility(View.GONE);

        base64StringCondenserCoilCleanedAfterPhoto = "";
        mPreventiveMaintenanceAcTechnicianButtonCondenserCoilCleanedAfterPhotoView.setVisibility(View.GONE);

        mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.setText("");

        base64StringCoolingCoilCleanedBeforePhoto = "";
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedBeforePhotoView.setVisibility(View.GONE);

        base64StringCoolingCoilCleanedAfterPhoto = "";
        mPreventiveMaintenanceAcTechnicianButtonCoolingCoilCleanedAfterPhotoView.setVisibility(View.GONE);

        mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent.setText("");
        mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.setText("");
        mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature.setText("");
        mPreventiveMaintenanceAcTechnicianEditTextSetTemperature.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.setText("");
        mPreventiveMaintenanceAcTechnicianEditTextRemarks.setText("");

    }

    private void submitDetails(int i) {

        try {
            String circle = mPreventiveMaintenanceAcTechnicianTextViewCircleVal.getText().toString().trim();
            String state = mPreventiveMaintenanceAcTechnicianTextViewStateVal.getText().toString().trim();
            String ssa = mPreventiveMaintenanceAcTechnicianTextViewSsaVal.getText().toString().trim();
            String siteId = mPreventiveMaintenanceAcTechnicianTextViewSiteIdVal.getText().toString().trim();
            String siteName = mPreventiveMaintenanceAcTechnicianTextViewSiteNameVal.getText().toString().trim();
            String ticketNo = mPreventiveMaintenanceAcTechnicianTextViewTicketNoVal.getText().toString().trim();
            String ticketDate = mPreventiveMaintenanceAcTechnicianTextViewTicketDateVal.getText().toString().trim();
            String pmPlanDate = mPreventiveMaintenanceAcTechnicianTextViewPmPlanDateVal.getText().toString().trim();
            String submittedDate = mPreventiveMaintenanceAcTechnicianTextViewSubmittedDateVal.getText().toString().trim();
            String noOfAcAtSite = mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal.getText().toString().trim();

            acPreventiveMaintanceProcessParentDatum = new AcPreventiveMaintanceProcessParentDatum(circle, state, ssa, siteId, siteName, ticketNo, ticketDate,
                    pmPlanDate, submittedDate, noOfAcAtSite, acPreventiveMaintanceProcessData);
            acPreventiveMaintanceProcessParentDatum.setAcPmTechnicianLat(technicianLat);
            acPreventiveMaintanceProcessParentDatum.setAcPmTechnicianLong(technicianLong);
            /*Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(acPreventiveMaintanceProcessParentDatum);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);*/

            acPmTransactionDetails.setAcPreventiveMaintanceProcessParentDatum(acPreventiveMaintanceProcessParentDatum);
            acPmTransactionDetails.setUserId(sessionManager.getSessionUserId());
            acPmTransactionDetails.setAccessToken(sessionManager.getSessionDeviceToken());
            acPmTransactionDetails.setTicketId(sessionManager.getSessionUserTicketId());


            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(acPmTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
            if (i == 1) {
                if (Conditions.isNetworkConnected(PreventiveMaintenanceAcTechnicianActivity.this)) {
                    submitAcPmTicket();
                } else {
                    //No Internet Connection
                    showToast("Device has no internet connection.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidationOfArrayFields() {

        String qrCodeScan = base64StringAcTechnicianQRCodeScan;
        String mainMcbStatus = mPreventiveMaintenanceAcTechnicianTextViewMainMcbStatusVal.getText().toString().trim();
        String subMcbStatus = mPreventiveMaintenanceAcTechnicianTextViewSubMcbStatusVal.getText().toString().trim();
        String metalCladPlugStatus = mPreventiveMaintenanceAcTechnicianTextViewMetalCladPlugStatusVal.getText().toString().trim();
        String metalCladSocketStatus = mPreventiveMaintenanceAcTechnicianTextViewMetalCladSocketStatusVal.getText().toString().trim();
        String stabilizerMake = mPreventiveMaintenanceAcTechnicianTextViewStabilizerMakeVal.getText().toString().trim();
        String stabilizerStatus = mPreventiveMaintenanceAcTechnicianTextViewStablizerStatusVal.getText().toString().trim();
        String stabilizerCapacity = mPreventiveMaintenanceAcTechnicianTextViewStabilizerCapacityVal.getText().toString().trim();
        String stabilizerWorkingStatus = mPreventiveMaintenanceAcTechnicianTextViewStablizerWorkingStatusVal.getText().toString().trim();
        String inputAcVoltage = mPreventiveMaintenanceAcTechnicianEditTextInputAcVoltage.getText().toString().trim();
        String acEarthingStatus = mPreventiveMaintenanceAcTechnicianTextViewAcEarthingStatusVal.getText().toString().trim();
        String filterCleaned = mPreventiveMaintenanceAcTechnicianTextViewFilterCleanedVal.getText().toString().trim();
        String filterPhotoBeforeCleaned = base64StringFilterCleanedBeforePhoto;
        String filterPhotoAfterCleaned = base64StringFilterCleanedAfterPhoto;
        String condenserCoilCleaned = mPreventiveMaintenanceAcTechnicianTextViewCondenserCoilCleanedVal.getText().toString().trim();
        String condenserCoilPhotoBeforeCleaned = base64StringCondenserCoilCleanedBeforePhoto;
        String condenserCoilPhotoAfterCleaned = base64StringCondenserCoilCleanedAfterPhoto;
        String coolingCoilCleaned = mPreventiveMaintenanceAcTechnicianTextViewCoolingCoilCleanedVal.getText().toString().trim();
        String coolingCoilPhotoBeforeCleaned = base64StringCoolingCoilCleanedBeforePhoto;
        String coolingCoilPhotoAfterCleaned = base64StringCoolingCoilCleanedAfterPhoto;
        String acCoolingStatus = mPreventiveMaintenanceAcTechnicianTextViewAcCoolingStatusVal.getText().toString().trim();
        String acStartingLoadCurrent = mPreventiveMaintenanceAcTechnicianEditTextAcStartingLoadcurrent.getText().toString().trim();
        String acRunningLoadCurrent = mPreventiveMaintenanceAcTechnicianEditTextAcRunningLoadCurrent.getText().toString().trim();
        String indoorFilterCleanedStatus = mPreventiveMaintenanceAcTechnicianTextViewIndoorFilterCleanedStatusVal.getText().toString().trim();
        String indoorFanMotorCondition = mPreventiveMaintenanceAcTechnicianTextViewIndoorFanMotorConditionVal.getText().toString().trim();
        String blowerWheelCondition = mPreventiveMaintenanceAcTechnicianTextViewBlowerWheelConditionVal.getText().toString().trim();
        String ifAnyNoiseIndoorMotor = mPreventiveMaintenanceAcTechnicianTextViewNoiseIndoorMotorVal.getText().toString().trim();
        String outdoorFanMotorCondition = mPreventiveMaintenanceAcTechnicianTextViewOutdoorFanMotorConditionVal.getText().toString().trim();
        String fanLeafCondition = mPreventiveMaintenanceAcTechnicianTextViewFanLeafConditionVal.getText().toString().trim();
        String ifAnyNoiseOutdoorMotor = mPreventiveMaintenanceAcTechnicianTextViewNoiseOutdoorMotorVal.getText().toString().trim();
        String compressorCondition = mPreventiveMaintenanceAcTechnicianTextViewCompressorConditionVal.getText().toString().trim();
        String compCapacitorCondition = mPreventiveMaintenanceAcTechnicianTextViewCompCapacitorConditionVal.getText().toString().trim();
        String controllerCondition = mPreventiveMaintenanceAcTechnicianTextViewControllerConditionVal.getText().toString().trim();
        String acAlarmStatus = mPreventiveMaintenanceAcTechnicianTextViewAcAlarmStatusVal.getText().toString().trim();
        String acSensorCondition = mPreventiveMaintenanceAcTechnicianTextViewAcSensorConditionVal.getText().toString().trim();
        String roomTemperature = mPreventiveMaintenanceAcTechnicianEditTextRoomTemperature.getText().toString().trim();
        String setTemperature = mPreventiveMaintenanceAcTechnicianEditTextSetTemperature.getText().toString().trim();
        String ifAnyVibrationOfAc = mPreventiveMaintenanceAcTechnicianTextViewVibrationOfAcVal.getText().toString().trim();
        String freeCoolingUnitStatus = mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingUnitStatusVal.getText().toString().trim();
        String ifFreeCoolingAvailableWorkingStatus = mPreventiveMaintenanceAcTechnicianTextViewFreeCoolingAvailableWorkingStatusVal.getText().toString().trim();
        String waterLeakageIfAny = mPreventiveMaintenanceAcTechnicianTextViewWaterLeakageVal.getText().toString().trim();
        String acCabinateStatus = mPreventiveMaintenanceAcTechnicianTextViewAcCabinateStatusVal.getText().toString().trim();
        String shelterDoorStatus = mPreventiveMaintenanceAcTechnicianTextViewShelterDoorStatusVal.getText().toString().trim();
        String remark = mPreventiveMaintenanceAcTechnicianEditTextRemarks.getText().toString().trim();

        if (qrCodeScan.isEmpty() || qrCodeScan == null) {
            showToast("Please scan QR code");
            return false;
        } else if (mainMcbStatus.isEmpty() || mainMcbStatus == null) {
            showToast("Select Main MCB Status");
            return false;
        } else if (subMcbStatus.isEmpty() || subMcbStatus == null) {
            showToast("Select Sub MCB Status");
            return false;
        } else if (metalCladPlugStatus.isEmpty() || metalCladPlugStatus == null) {
            showToast("Select Metal Clad Plug Status");
            return false;
        } else if (metalCladSocketStatus.isEmpty() || metalCladSocketStatus == null) {
            showToast("Select Metal Clad Socket Status");
            return false;
        } else if (stabilizerStatus.isEmpty() || stabilizerStatus == null) {
            showToast("Select Stabilizer Status");
            return false;
        } else if (stabilizerMake.isEmpty() || stabilizerMake == null) {
            showToast("Select Stabilizer Make");
            return false;
        } else if (stabilizerCapacity.isEmpty() || stabilizerCapacity == null) {
            showToast("Select Stabilizer Capacity");
            return false;
        } else if (stabilizerWorkingStatus.isEmpty() || stabilizerWorkingStatus == null) {
            showToast("Select Stabilizer Working Status");
            return false;
        } else if (inputAcVoltage.isEmpty() || inputAcVoltage == null) {
            showToast("Select Input Ac Voltage");
            return false;
        } else if (acEarthingStatus.isEmpty() || acEarthingStatus == null) {
            showToast("Select AC Earthing Status");
            return false;
        } else if (filterCleaned.isEmpty() || filterCleaned == null) {
            showToast("Select Filter Cleaned");
            return false;
        } else if (filterPhotoBeforeCleaned.isEmpty() || filterPhotoBeforeCleaned == null) {
            showToast("Please Take Photo Filter Before Cleaned");
            return false;
        } else if (filterPhotoAfterCleaned.isEmpty() || filterPhotoAfterCleaned == null) {
            showToast("Please Take Photo Filter After Cleaned");
            return false;
        } else if (condenserCoilCleaned.isEmpty() || condenserCoilCleaned == null) {
            showToast("Select Condenser Coil Cleaned");
            return false;
        } else if (condenserCoilPhotoBeforeCleaned.isEmpty() || condenserCoilPhotoBeforeCleaned == null) {
            showToast("Please Take Photo Condenser Coil Before Cleaned");
            return false;
        } else if (condenserCoilPhotoAfterCleaned.isEmpty() || condenserCoilPhotoAfterCleaned == null) {
            showToast("Please Take Photo Condenser Coil After Cleaned");
            return false;
        } else if (coolingCoilCleaned.isEmpty() || coolingCoilCleaned == null) {
            showToast("Select Cooling Coil Cleaned");
            return false;
        } else if (coolingCoilPhotoBeforeCleaned.isEmpty() || coolingCoilPhotoBeforeCleaned == null) {
            showToast("Please Take Photo Cooling Coil Before Cleaned");
            return false;
        } else if (coolingCoilPhotoAfterCleaned.isEmpty() || coolingCoilPhotoAfterCleaned == null) {
            showToast("Please Take Photo Cooling Coil After Cleaned");
            return false;
        } else if (acCoolingStatus.isEmpty() || acCoolingStatus == null) {
            showToast("Select AC Cooling Status");
            return false;
        } else if (acStartingLoadCurrent.isEmpty() || acStartingLoadCurrent == null) {
            showToast("Enter AC Starting Load Current");
            return false;
        } else if (acRunningLoadCurrent.isEmpty() || acRunningLoadCurrent == null) {
            showToast("Enter AC Running Load Current");
            return false;
        } else if (indoorFilterCleanedStatus.isEmpty() || indoorFilterCleanedStatus == null) {
            showToast("Select Indoor Filter Cleaned Status");
            return false;
        } else if (indoorFanMotorCondition.isEmpty() || indoorFanMotorCondition == null) {
            showToast("Select Indoor Fan Motor Condition");
            return false;
        } else if (blowerWheelCondition.isEmpty() || blowerWheelCondition == null) {
            showToast("Select Blower Wheel Condition");
            return false;
        } else if (ifAnyNoiseIndoorMotor.isEmpty() || ifAnyNoiseIndoorMotor == null) {
            showToast("Select If any Noise Indoor Motor");
            return false;
        } else if (outdoorFanMotorCondition.isEmpty() || outdoorFanMotorCondition == null) {
            showToast("Select Outdoor Fan Motor Condition");
            return false;
        } else if (fanLeafCondition.isEmpty() || fanLeafCondition == null) {
            showToast("Select Fan Leaf Condition");
            return false;
        } else if (ifAnyNoiseOutdoorMotor.isEmpty() || ifAnyNoiseOutdoorMotor == null) {
            showToast("Select If any Noise Outdoor Motor");
            return false;
        } else if (compressorCondition.isEmpty() || compressorCondition == null) {
            showToast("Select Compressor Condition");
            return false;
        } else if (compCapacitorCondition.isEmpty() || compCapacitorCondition == null) {
            showToast("Select Comp Capacitor Condition");
            return false;
        } else if (controllerCondition.isEmpty() || controllerCondition == null) {
            showToast("Select Controller Condition");
            return false;
        } else if (acAlarmStatus.isEmpty() || acAlarmStatus == null) {
            showToast("Select AC Alarm Status");
            return false;
        } else if (acSensorCondition.isEmpty() || acSensorCondition == null) {
            showToast("Select AC Sensor Condition");
            return false;
        } else if (roomTemperature.isEmpty() || roomTemperature == null) {
            showToast("Enter Room Temperature");
            return false;
        } else if (setTemperature.isEmpty() || setTemperature == null) {
            showToast("Enter Set Temperature");
            return false;
        } else if (ifAnyVibrationOfAc.isEmpty() || ifAnyVibrationOfAc == null) {
            showToast("Select If any Vibration Of AC");
            return false;
        } else if (freeCoolingUnitStatus.isEmpty() || freeCoolingUnitStatus == null) {
            showToast("Select Free Cooling Unit Status");
            return false;
        } else if (ifFreeCoolingAvailableWorkingStatus.isEmpty() || ifFreeCoolingAvailableWorkingStatus == null) {
            showToast("Select If Free Cooling Available Working Status");
            return false;
        } else if (waterLeakageIfAny.isEmpty() || waterLeakageIfAny == null) {
            showToast("Select Water Leakage If Any");
            return false;
        } else if (acCabinateStatus.isEmpty() || acCabinateStatus == null) {
            showToast("Select AC Cabinate Status");
            return false;
        } else if (shelterDoorStatus.isEmpty() || shelterDoorStatus == null) {
            showToast("Select Shelter Door Status");
            return false;
        } else if (remark.isEmpty() || remark == null) {
            showToast("Enter Remark");
            return false;
        } else return true;
    }

    private boolean checkValidationonSubmit(String methodFlag) {

        String noOfAcAtSite = mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal.getText().toString().trim();

        if (noOfAcAtSite.isEmpty() || noOfAcAtSite == null) {
            showToast("Select No Of AC At Site");
            return false;
        } else if (Integer.valueOf(noOfAcAtSite) > 0) {
            if ((acPreventiveMaintanceProcessData.size() != Integer.valueOf(noOfAcAtSite) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;
    }

    private boolean checkDuplicationQrCodeNew(String qrCode) {
        for (int i = 0; i < acPreventiveMaintanceProcessData.size(); i++) {
            for (int j = i; j < acPreventiveMaintanceProcessData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (acPreventiveMaintanceProcessData.get(i).getAcPreventiveMaintanceProcessQrCodeScan().toString().equals(qrCode)) {//acPreventiveMaintanceProcessData.get(j).getAcPreventiveMaintanceProcessQrCodeScan().toString()
                    int dup_pos = j + 1;
                    showToast("Scanned QR Code in Reading No: " + ++dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private void displayDataOnForm(Intent intent) {

        TicketId = intent.getStringExtra("TicketId");
        TicketNO = intent.getStringExtra("TicketNO");
        TicketDate = intent.getStringExtra("TicketDate");

        acPmPlanDate = intent.getStringExtra("acPmPlanDate");
        submittedDate = intent.getStringExtra("submittedDate");
        acPmSheduledDate = intent.getStringExtra("acPmSheduledDate");

        customerName = intent.getStringExtra("customerName");
        circleName = intent.getStringExtra("circleName");
        stateName = intent.getStringExtra("stateName");
        ssaName = intent.getStringExtra("ssaName");
        siteDBId = intent.getStringExtra("siteDBId");
        siteId = intent.getStringExtra("siteId");
        siteName = intent.getStringExtra("siteName");
        siteType = intent.getStringExtra("siteType");

        numberOfAc = intent.getStringExtra("numberOfAc");
        modeOfOpration = intent.getStringExtra("modeOfOpration");
        vendorName = intent.getStringExtra("vendorName");
        acTechnicianName = intent.getStringExtra("acTechnicianName");
        acTechnicianMobileNo = intent.getStringExtra("acTechnicianMobileNo");
        accessType = intent.getStringExtra("accessType");
        ticketAccess = intent.getStringExtra("ticketAccess");
        acPmTickStatus = intent.getStringExtra("acPmTickStatus");


        mPreventiveMaintenanceAcTechnicianTextViewCircleVal.setText(circleName);
        mPreventiveMaintenanceAcTechnicianTextViewStateVal.setText(stateName);
        mPreventiveMaintenanceAcTechnicianTextViewSsaVal.setText(ssaName);
        mPreventiveMaintenanceAcTechnicianTextViewSiteIdVal.setText(siteId);
        mPreventiveMaintenanceAcTechnicianTextViewSiteNameVal.setText(siteName);
        mPreventiveMaintenanceAcTechnicianTextViewTicketNoVal.setText(TicketNO);
        mPreventiveMaintenanceAcTechnicianTextViewTicketDateVal.setText(TicketDate);
        mPreventiveMaintenanceAcTechnicianTextViewPmPlanDateVal.setText(acPmPlanDate);
        mPreventiveMaintenanceAcTechnicianTextViewSubmittedDateVal.setText(submittedDate);


    }

    private void setNoAcList(String NoOfAc) {
        if (!NoOfAc.isEmpty()) {
            str_noOfAcAtSiteVal = NoOfAc;
            mPreventiveMaintenanceAcTechnicianTextViewNoOfAcAtSiteVal.setText(str_noOfAcAtSiteVal);
            invalidateOptionsMenu();

            if (acPreventiveMaintanceProcessData != null && acPreventiveMaintanceProcessData.size() > 0) {
                acPreventiveMaintanceProcessData.clear();
            }
            currentPos = 0;
            totalCount = 0;
            clearFields(currentPos);

            if (str_noOfAcAtSiteVal.equals("0")) {
                mLinearLayoutContainer.setVisibility(View.GONE);
            } else {
                totalCount = Integer.parseInt(str_noOfAcAtSiteVal);
                mPreventiveMaintenanceAcTechnicianTextViewAcNumber.setText("Reading: #1");
                mLinearLayoutContainer.setVisibility(View.VISIBLE);
                mPreventiveMaintenanceAcTechnicianButtonPreviousReading.setVisibility(View.GONE);
                mPreventiveMaintenanceAcTechnicianButtonNextReading.setVisibility(View.VISIBLE);
                if (totalCount > 0 && totalCount == 1) {
                    mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Finish");
                } else {
                    mPreventiveMaintenanceAcTechnicianButtonNextReading.setText("Next Reading");
                }
            }
        }
    }

    private void GetScannedAcQrData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("SiteId", siteDBId);
            jo.put("QrCode", base64StringAcTechnicianQRCodeScan);

            /*jo.put("UserId", "120");
            jo.put("AccessToken", "MjUyLTg1REEyUzMtQURTUzVELUVJNUI0QTIyMTEyMA==");
            jo.put("SiteId", "9");
            jo.put("QrCode", "http://ialac1");*/

            Log.i(PreventiveMaintenanceAcTechnicianActivity.class.getName(), Constants.acPmAcDataOnQrCodeScan + "\n\n" + jo.toString());

            GsonRequest<AcPmAcData> getScannedQrCodeAcData = new GsonRequest<>(Request.Method.POST, Constants.acPmAcDataOnQrCodeScan, jo.toString(), AcPmAcData.class,
                    new Response.Listener<AcPmAcData>() {
                        @Override
                        public void onResponse(@NonNull AcPmAcData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    AcPmAcData acPmAcData = new AcPmAcData();
                                    acPmAcData = response;

                                    if (acPmAcData.getAcPmAcDetails() != null) {

                                        mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText(acPmAcData.getAcPmAcDetails().getModel() == null ? "" : acPmAcData.getAcPmAcDetails().getModel().toString().trim());
                                        mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText(acPmAcData.getAcPmAcDetails().getType() == null ? "" : acPmAcData.getAcPmAcDetails().getType().toString().trim());
                                        mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText(acPmAcData.getAcPmAcDetails().getMake() == null ? "" : acPmAcData.getAcPmAcDetails().getMake().toString().trim());
                                        mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText(acPmAcData.getAcPmAcDetails().getCapacity() == null ? "" : acPmAcData.getAcPmAcDetails().getCapacity().toString().trim());
                                        mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText(acPmAcData.getAcPmAcDetails().getSerialNo() == null ? "" : acPmAcData.getAcPmAcDetails().getSerialNo().toString().trim());

                                        if (acPmAcData.getAcPmAcDetails().getModel() == null && acPmAcData.getAcPmAcDetails().getType() == null &&
                                                acPmAcData.getAcPmAcDetails().getMake() == null && acPmAcData.getAcPmAcDetails().getCapacity() == null && acPmAcData.getAcPmAcDetails().getSerialNo() == null) {
                                            showToast("No data found for scanned QR Code.");
                                            mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText("");
                                            mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText("");
                                            mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText("");
                                            mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText("");
                                            mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText("");
                                            mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
                                            mButtonClearQRCodeScanView.setVisibility(View.GONE);
                                            base64StringAcTechnicianQRCodeScan = "";
                                        }
                                    } else {
                                        showToast("No data found for scanned QR Code.");
                                        mPreventiveMaintenanceAcTechnicianTextViewAcModelVal.setText("");
                                        mPreventiveMaintenanceAcTechnicianTextViewAcTypeVal.setText("");
                                        mPreventiveMaintenanceAcTechnicianTextViewAcMakeVal.setText("");
                                        mPreventiveMaintenanceAcTechnicianTextViewAcCapacityVal.setText("");
                                        mPreventiveMaintenanceAcTechnicianTextViewAcSerialNoVal.setText("");
                                        mPreventiveMaintenanceAcTechnicianButtonQRCodeScanView.setVisibility(View.GONE);
                                        mButtonClearQRCodeScanView.setVisibility(View.GONE);
                                        base64StringAcTechnicianQRCodeScan = "";
                                    }
                                }
                            }
                        }
                    }, new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();

                }
            });
            getScannedQrCodeAcData.setRetryPolicy(Application.getDefaultRetryPolice());
            getScannedQrCodeAcData.setShouldCache(false);
            Application.getInstance().

                    addToRequestQueue(getScannedQrCodeAcData, "ScannedQrCodeAcData");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }

    /////////////////////////////////////////////////////

    private void showSettingsAlert() {
        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceAcTechnicianActivity.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to submit this ticket?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                submitDetails(1);
            }

            @Override
            public void onNegativeClick() {
                submitDetails(0);
                finish();
            }
        }).show();
    }

    public void submitAcPmTicket() {

        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
                showBusyProgress();
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");
                Log.e("AcPm Tech: ", jsonInString);
                //remaining to add webservice for submit PM site Ticket
                GsonRequest<UserLoginResponseData>
                        submitSitePmTicketRequest = new GsonRequest<>(Request.Method.POST, Constants.processedAcPmTicket, jsonInString, UserLoginResponseData.class,
                        new Response.Listener<UserLoginResponseData>() {
                            @Override
                            public void onResponse(@NonNull UserLoginResponseData response) {
                                hideBusyProgress();
                                if (response.getError() != null) {
                                    showToast(response.getError().getErrorMessage());
                                } else {
                                    if (response.getSuccess() == 1) {
                                        showToast("Ticket submitted successfully.");
                                        sessionManager.updateSessionUserTicketId(null);
                                        sessionManager.updateSessionUserTicketName(null);

                                        removeOfflineCache();
                                        Intent intent = new Intent(PreventiveMaintenanceAcTechnicianActivity.this, AcPreventiveMaintenanceDashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivityForResult(intent, RESULT_OK);
                                    }
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideBusyProgress();
                        showToast(SettingsMy.getErrorMessage(error));
                    }
                });

                submitSitePmTicketRequest.setRetryPolicy(Application.getDefaultRetryPolice());
                submitSitePmTicketRequest.setShouldCache(false);
                Application.getInstance().addToRequestQueue(submitSitePmTicketRequest, "submitSitePmTicketRequest");
            }
        } catch (Exception e) {
            hideBusyProgress();
            e.printStackTrace();
        }
    }

    private void removeOfflineCache() {
        if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
            offlineStorageWrapper.removedOffLineFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");
        }
    }

    private void readDataByFSE() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("acPMTicketId", sessionManager.getSessionUserTicketId());

            Log.i(PreventiveMaintenanceAcTechnicianActivity.class.getName(), Constants.readAcPmDataByFSE + "\n\n" + jo.toString());

            GsonRequest<AcPmTransactionDetails> getReadAcPmDataProcessedByTech = new GsonRequest<>(Request.Method.POST, Constants.readAcPmDataByFSE, jo.toString(), AcPmTransactionDetails.class,
                    new Response.Listener<AcPmTransactionDetails>() {
                        @Override
                        public void onResponse(@NonNull AcPmTransactionDetails response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else if (response.getAcPreventiveMaintanceProcessParentDatum() != null) {
                                //if (response != null) {
                                acPmTransactionDetails = response;
                                flagReadDataByFSE = true;
                                setAcPmData(0);
                            } else {
                                showToast("No data found");
                            }
                        }
                    }, new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();

                }
            });
            getReadAcPmDataProcessedByTech.setRetryPolicy(Application.getDefaultRetryPolice());
            getReadAcPmDataProcessedByTech.setShouldCache(false);
            Application.getInstance().

                    addToRequestQueue(getReadAcPmDataProcessedByTech, "getReadAcPmDataProcessedByTech");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }


}
