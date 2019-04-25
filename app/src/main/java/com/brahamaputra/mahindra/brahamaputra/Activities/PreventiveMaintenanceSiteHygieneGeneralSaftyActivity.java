package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
import com.brahamaputra.mahindra.brahamaputra.Data.SiteHygenieneGenralSeftyParameter;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePm_siteBoundaryStatus;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_Selected_SiteType;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;

public class PreventiveMaintenanceSiteHygieneGeneralSaftyActivity extends BaseActivity {


    private static final String TAG = PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.class.getSimpleName();


    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutSitePremisesCleaning;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaning;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUploadPhotoOfSitePremises;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUploadPhotoOfSitePremises;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremises;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutEquipmentCleaning;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaning;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutAnyHivesInTower;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTower;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCompoundWallFencingStatus;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatus;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutGateLockAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutShelterLockAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDgLockAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireExtingisherAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutNoOfFireExtingisherAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireExtingisherExpiryDate;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherExpiryDate;
    private EditText mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireBucketAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablity;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCautionSignBoardPhoto;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCautionSignBoardPhoto;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhoto;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutWarningSignBoardPhoto;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewWarningSignBoardPhoto;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhoto;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDangerSignBoardPhoto;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDangerSignBoardPhoto;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhoto;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutSafetyChartsAndCalendar;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendar;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUnusedMaterialInSite;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSite;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutRegisterFault;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal;
    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutTypesOfFault;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFault;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal;

    private LinearLayout mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView;


    String str_pmSitePremisesCleaningVal = "";
    String str_pmSiteanyEagleOrCrowOrHoneyHivesInTowerVal = "";
    String str_pmSiteCompoundWallOrFencingStatusVal = "";
    String str_pmSiteGateLockAvailablityVal = "";
    String str_pmSiteShelterLockAvailablityVal = "";
    String str_pmSiteDgLockAvailablityVal = "";
    String str_pmSiteFireExtinguisherAvilabilityVal = "";
    String str_pmSiteNoOfFireExtinguisherVal = "";
    String str_pmSiteFireBucketVal = "";
    String str_pmSiteSafetyChartsAndCalendarVal = "";
    String str_pmSiteUnusedMaterialInSiteVal = "";
    String str_pmSiteRegisterFaultVal = "";
    String str_pmSiteTypeOfFaultVal = "";

    final Calendar myCalendar = Calendar.getInstance();

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfSitePremises = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_CautionSignBoard = 102;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_WarningSignBoard = 103;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_DangerSignBoard = 104;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringUploadPhotoOfSitePremises = "";
    private String base64StringCautionSignBoard = "";
    private String base64StringWarningSignBoard = "";
    private String base64StringDangerSignBoard = "";
    private String base64StringUploadPhotoOfRegisterFault = "";

    private String imageFileUploadPhotoOfSitePremises;
    private String imageFileCautionSignBoard;
    private String imageFileWarningSignBoard;
    private String imageFileDangerSignBoard;
    private String imageFileUploadPhotoOfRegisterFault;

    private Uri imageFileUriUploadPhotoOfSitePremises = null;
    private Uri imageFileUriCautionSignBoard = null;
    private Uri imageFileUriWarningSignBoard = null;
    private Uri imageFileUriDangerSignBoard = null;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private SiteHygenieneGenralSeftyParameter siteHygenSaftyDetailsData;
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    /*private String sitePremisesCleaning;
    private String base64StringUploadPhotoOfSitePremises;
    private String equipmentCleaning;
    private String anyEagleCrowHoneyHivesInTower;
    private String compoundWallFencingStatus;
    private String gateLockAvailablity;
    private String shelterLockAvailablity;
    private String dgLockAvailablity;
    private String fireExtinguisherAvilability;
    private String noOfFireExtinguisher;
    private String fireExtinguisherExpiryDate;
    private String fireBucket;
    private String base64StringCautionSignBoardPhoto;
    private String base64StringWarningSignBoardPhoto;
    private String base64StringDangerSignBoardPhoto;
    private String safetyChartsCalendar;
    private String unusedMaterialInSite;
    private String registerFault;
    private String typeOfFault;
    private String base64StringUploadPhotoOfRegisterFault;
    private int isSubmited;*/

    ArrayList<BatteryType> batteryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_hygiene_general_safty);
        setTitle("Site Hygiene-General Safety Parameters");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        batteryType = new ArrayList<BatteryType>();
        Intent intent = getIntent();
        batteryType = (ArrayList<BatteryType>) intent.getSerializableExtra("batteryType");

        assignViews();
        initCombo();
        checkCameraPermission();
        setListner();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, userId, ticketName);

        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();

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

        mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCompoundWallFencingStatus.setVisibility(View.VISIBLE);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutShelterLockAvailablity.setVisibility(View.VISIBLE);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDgLockAvailablity.setVisibility(View.VISIBLE);

        visibilityOfCompoundWallFencingStatus(sitePm_siteBoundaryStatus);
        visibilityOfShelterLockAvailablity(hototicket_Selected_SiteType);
        visibilityOfDgLockAvailablity(hototicket_sourceOfPower);

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }
        setInputDetails();
        setMultiSelectModel();
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutSitePremisesCleaning = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_sitePremisesCleaning);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_sitePremisesCleaning);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_sitePremisesCleaningVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUploadPhotoOfSitePremises = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_uploadPhotoOfSitePremises);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUploadPhotoOfSitePremises = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_uploadPhotoOfSitePremises);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremises = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_uploadPhotoOfSitePremises);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_uploadPhotoOfSitePremisesView);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutEquipmentCleaning = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_equipmentCleaning);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_equipmentCleaning);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_equipmentCleaningVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutAnyHivesInTower = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_anyHivesInTower);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTower = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_anyHivesInTower);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_anyHivesInTowerVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCompoundWallFencingStatus = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_compoundWallFencingStatus);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_compoundWallFencingStatus);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_compoundWallFencingStatusVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutGateLockAvailablity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_gateLockAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablity = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_gateLockAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_gateLockAvailablityVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutShelterLockAvailablity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_shelterLockAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablity = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_shelterLockAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_shelterLockAvailablityVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDgLockAvailablity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_dgLockAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablity = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_dgLockAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_dgLockAvailablityVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireExtingisherAvailablity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_fireExtingisherAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablity = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_fireExtingisherAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_fireExtingisherAvailablityVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutNoOfFireExtingisherAvailablity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_noOfFireExtingisherAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablity = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_noOfFireExtingisherAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_noOfFireExtingisherAvailablityVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireExtingisherExpiryDate = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_fireExtingisherExpiryDate);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherExpiryDate = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_fireExtingisherExpiryDate);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate = (EditText) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_editText_fireExtingisherExpiryDate);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireBucketAvailablity = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_fireBucketAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablity = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_fireBucketAvailablity);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_fireBucketAvailablityVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCautionSignBoardPhoto = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_cautionSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCautionSignBoardPhoto = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_cautionSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhoto = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_cautionSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_cautionSignBoardPhotoView);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutWarningSignBoardPhoto = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_warningSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewWarningSignBoardPhoto = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_warningSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhoto = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_warningSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_warningSignBoardPhotoView);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDangerSignBoardPhoto = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_dangerSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDangerSignBoardPhoto = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_dangerSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhoto = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_dangerSignBoardPhoto);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_dangerSignBoardPhotoView);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutSafetyChartsAndCalendar = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_safetyChartsAndCalendar);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendar = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_safetyChartsAndCalendar);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_safetyChartsAndCalendarVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUnusedMaterialInSite = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_unusedMaterialInSite);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_unusedMaterialInSite);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_unusedMaterialInSiteVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_registerFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_registerFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_registerFaultVal);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutTypesOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_typesOfFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_typesOfFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_typesOfFaultVal);

        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteHygieneGeneralSafty_button_uploadPhotoOfRegisterFaultView);
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
                        str_pmSiteTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal.setText(str_pmSiteTypeOfFaultVal);

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");

                    }
                });
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                Gson gson = new Gson();

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (pmSiteTransactionDetails != null) {

                    siteHygenSaftyDetailsData = pmSiteTransactionDetails.getSiteHygenieneGenralSeftyParameter();

                    if (siteHygenSaftyDetailsData != null) {

                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal.setText(siteHygenSaftyDetailsData.getSitePremisesCleaning());
                        this.base64StringUploadPhotoOfSitePremises = siteHygenSaftyDetailsData.getBase64StringUploadPhotoOfSitePremises();
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal.setText(siteHygenSaftyDetailsData.getEquipmentCleaning());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal.setText(siteHygenSaftyDetailsData.getAnyEagleCrowHoneyHivesInTower());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.setText(siteHygenSaftyDetailsData.getCompoundWallFencingStatus());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal.setText(siteHygenSaftyDetailsData.getGateLockAvailablity());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal.setText(siteHygenSaftyDetailsData.getShelterLockAvailablity());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal.setText(siteHygenSaftyDetailsData.getDgLockAvailablity());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal.setText(siteHygenSaftyDetailsData.getFireExtinguisherAvilability());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal.setText(siteHygenSaftyDetailsData.getNoOfFireExtinguisher());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate.setText(siteHygenSaftyDetailsData.getFireExtinguisherExpiryDate());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal.setText(siteHygenSaftyDetailsData.getFireBucket());
                        this.base64StringCautionSignBoard = siteHygenSaftyDetailsData.getBase64StringCautionSignBoardPhoto();
                        this.base64StringWarningSignBoard = siteHygenSaftyDetailsData.getBase64StringWarningSignBoardPhoto();
                        this.base64StringDangerSignBoard = siteHygenSaftyDetailsData.getBase64StringDangerSignBoardPhoto();
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal.setText(siteHygenSaftyDetailsData.getSafetyChartsCalendar());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal.setText(siteHygenSaftyDetailsData.getUnusedMaterialInSite());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal.setText(siteHygenSaftyDetailsData.getRegisterFault());
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal.setText(siteHygenSaftyDetailsData.getTypeOfFault());
                        this.base64StringUploadPhotoOfRegisterFault = siteHygenSaftyDetailsData.getBase64StringUploadPhotoOfRegisterFault();

                        visibilityOfFireExtingisherAvailablity(mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal.getText().toString());
                        visibilityOfTypesOfFault(mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal.getText().toString());

                        // New added for image #ImageSet

                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setVisibility(View.GONE);
                        if (!this.base64StringUploadPhotoOfSitePremises.isEmpty() && this.base64StringUploadPhotoOfSitePremises != null) {
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfSitePremises);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriUploadPhotoOfSitePremises = Uri.parse(path);
                        }

                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setVisibility(View.GONE);
                        if (!this.base64StringCautionSignBoard.isEmpty() && this.base64StringCautionSignBoard != null) {
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringCautionSignBoard);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriCautionSignBoard = Uri.parse(path);
                        }

                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setVisibility(View.GONE);
                        if (!this.base64StringWarningSignBoard.isEmpty() && this.base64StringWarningSignBoard != null) {
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringWarningSignBoard);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriWarningSignBoard = Uri.parse(path);
                        }

                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setVisibility(View.GONE);
                        if (!this.base64StringDangerSignBoard.isEmpty() && this.base64StringDangerSignBoard != null) {
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringDangerSignBoard);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriDangerSignBoard = Uri.parse(path);
                        }


                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                        if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                        }


                        /*imageFileUploadPhotoOfSitePremises = siteHygenSaftyDetailsData.getImageFileUploadPhotoOfSitePremises();
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setVisibility(View.GONE);
                        if (imageFileUploadPhotoOfSitePremises != null && imageFileUploadPhotoOfSitePremises.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfSitePremises);
                            imageFileUriUploadPhotoOfSitePremises = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileUriUploadPhotoOfSitePremises != null) {
                                mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileCautionSignBoard = siteHygenSaftyDetailsData.getImageFileCautionSignBoard();
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setVisibility(View.GONE);
                        if (imageFileCautionSignBoard != null && imageFileCautionSignBoard.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileCautionSignBoard);
                            imageFileUriCautionSignBoard = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileUriCautionSignBoard != null) {
                                mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileWarningSignBoard = siteHygenSaftyDetailsData.getImageFileWarningSignBoard();
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setVisibility(View.GONE);
                        if (imageFileWarningSignBoard != null && imageFileWarningSignBoard.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileWarningSignBoard);
                            imageFileUriWarningSignBoard = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileUriWarningSignBoard != null) {
                                mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileDangerSignBoard = siteHygenSaftyDetailsData.getImageFileDangerSignBoard();
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setVisibility(View.GONE);
                        if (imageFileDangerSignBoard != null && imageFileDangerSignBoard.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileDangerSignBoard);
                            imageFileUriDangerSignBoard = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileUriDangerSignBoard != null) {
                                mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setVisibility(View.VISIBLE);
                            }
                        }*/

                        if (siteHygenSaftyDetailsData.getTypeOfFault() != null && siteHygenSaftyDetailsData.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                            setArrayValuesOfTypeOfFault(siteHygenSaftyDetailsData.getTypeOfFault());
                        }
                    }
                }
            } else {
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {

            String sitePremisesCleaning = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal.getText().toString().trim();
            String base64StringUploadPhotoOfSitePremises = this.base64StringUploadPhotoOfSitePremises;
            String equipmentCleaning = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal.getText().toString().trim();
            String anyEagleCrowHoneyHivesInTower = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal.getText().toString().trim();

            String compoundWallFencingStatus = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.getText().toString().trim();
            String gateLockAvailablity = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal.getText().toString().trim();
            String shelterLockAvailablity = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal.getText().toString().trim();
            String dgLockAvailablity = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal.getText().toString().trim();
            String fireExtinguisherAvilability = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal.getText().toString().trim();
            String noOfFireExtinguisher = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal.getText().toString().trim();
            String fireExtinguisherExpiryDate = mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate.getText().toString().trim();
            String fireBucket = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal.getText().toString().trim();
            String base64StringCautionSignBoardPhoto = this.base64StringCautionSignBoard;
            String base64StringWarningSignBoardPhoto = this.base64StringWarningSignBoard;
            String base64StringDangerSignBoardPhoto = this.base64StringDangerSignBoard;
            String safetyChartsCalendar = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal.getText().toString().trim();
            String unusedMaterialInSite = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;
            //int isSubmited = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal.getText().toString().trim();


            siteHygenSaftyDetailsData = new SiteHygenieneGenralSeftyParameter(sitePremisesCleaning, base64StringUploadPhotoOfSitePremises, equipmentCleaning,
                    anyEagleCrowHoneyHivesInTower, compoundWallFencingStatus, gateLockAvailablity, shelterLockAvailablity, dgLockAvailablity,
                    fireExtinguisherAvilability, noOfFireExtinguisher, fireExtinguisherExpiryDate, fireBucket, base64StringCautionSignBoardPhoto,
                    base64StringWarningSignBoardPhoto, base64StringDangerSignBoardPhoto, safetyChartsCalendar,
                    unusedMaterialInSite, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault/*,
                    imageFileUploadPhotoOfSitePremises, imageFileCautionSignBoard,
                    imageFileWarningSignBoard, imageFileDangerSignBoard*/);

            pmSiteTransactionDetails.setSiteHygenieneGenralSeftyParameter(siteHygenSaftyDetailsData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);
            //Log.d(TAG, "jsonString: " + jsonString);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void initCombo() {
        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_sitePremisesCleaning))),
                        "Site Premises Cleaning",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSitePremisesCleaningVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal.setText(str_pmSitePremisesCleaningVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_equipmentCleaning))),
                        "Equipment Cleaning",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteanyEagleOrCrowOrHoneyHivesInTowerVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal.setText(str_pmSiteanyEagleOrCrowOrHoneyHivesInTowerVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_anyEagleOrCrowOrHoneyHivesInTower))),
                        "Any Eagle/Crow/Honey Hives in Tower",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteanyEagleOrCrowOrHoneyHivesInTowerVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal.setText(str_pmSiteanyEagleOrCrowOrHoneyHivesInTowerVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_compoundWallOrFencingStatus))),
                        "Compound Wall/Fencing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteCompoundWallOrFencingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.setText(str_pmSiteCompoundWallOrFencingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_gateLockAvailablity))),
                        "Gate Lock Availablity",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteGateLockAvailablityVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal.setText(str_pmSiteGateLockAvailablityVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_shelterLockAvailablity))),
                        "Shelter Lock Availablity",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteShelterLockAvailablityVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal.setText(str_pmSiteShelterLockAvailablityVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_dgLockAvailablity))),
                        "DG Lock Availablity",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgLockAvailablityVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal.setText(str_pmSiteDgLockAvailablityVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_fireExtinguisherAvailablity))),
                        "Fire Extingisher Availablity",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteFireExtinguisherAvilabilityVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal.setText(str_pmSiteFireExtinguisherAvilabilityVal);
                        visibilityOfFireExtingisherAvailablity(str_pmSiteFireExtinguisherAvilabilityVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_noOfFireExtinguisher))),
                        "No of Fire Extingisher",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteNoOfFireExtinguisherVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal.setText(str_pmSiteNoOfFireExtinguisherVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_fireBucket))),
                        "Fire Bucket",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteFireBucketVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal.setText(str_pmSiteFireBucketVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_safetyChartsAndCalender))),
                        "Safety Charts & Calender",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteSafetyChartsAndCalendarVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal.setText(str_pmSiteSafetyChartsAndCalendarVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_unusedMaterialOnSite))),
                        "Un-Used Material in Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteUnusedMaterialInSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal.setText(str_pmSiteUnusedMaterialInSiteVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteHygieneGeneralSafetyParameters_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal.setText(str_pmSiteRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void setListner() {
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfSitePremises();
                }
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    CautionSignBoardPhoto();
                }
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    WarningSignBoardPhoto();
                }
            }
        });
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DangerSignBoardPhoto();
                }
            }
        });
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        //////////////////////////

        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfSitePremises != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, imageFileUriUploadPhotoOfSitePremises);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriCautionSignBoard != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, imageFileUriCautionSignBoard);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriWarningSignBoard != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, imageFileUriWarningSignBoard);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriDangerSignBoard != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, imageFileUriDangerSignBoard);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void UploadPhotoOfSitePremises() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfSitePremises = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfSitePremises);
            imageFileUriUploadPhotoOfSitePremises = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfSitePremises);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfSitePremises);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CautionSignBoardPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileCautionSignBoard = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_cautionBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileCautionSignBoard);
            imageFileUriCautionSignBoard = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriCautionSignBoard);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_CautionSignBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void WarningSignBoardPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileWarningSignBoard = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_warningBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileWarningSignBoard);
            imageFileUriWarningSignBoard = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriWarningSignBoard);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_WarningSignBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DangerSignBoardPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileDangerSignBoard = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dangerBoard.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileDangerSignBoard);
            imageFileUriDangerSignBoard = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriDangerSignBoard);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_DangerSignBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremisesReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    private void visibilityOfCompoundWallFencingStatus(String SiteBoundaryStatus) {
        //mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.setText("");

        if (SiteBoundaryStatus.equals("Not Applicable") || SiteBoundaryStatus.equals("Open Site")) {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.setText("");
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCompoundWallFencingStatus.setVisibility(View.GONE);
        } else {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutCompoundWallFencingStatus.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfShelterLockAvailablity(String Selected_SiteType) {
        //mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.setText("");
        if (Selected_SiteType.equals("Outdoor")) {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal.setText("");
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutShelterLockAvailablity.setVisibility(View.GONE);
        } else {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutShelterLockAvailablity.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfDgLockAvailablity(String SourceOfPower) {
        if (SourceOfPower.equals("Non DG")) {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal.setText("");
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDgLockAvailablity.setVisibility(View.GONE);
        } else {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutDgLockAvailablity.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfFireExtingisherAvailablity(String FireExtingisherAvailablity) {

        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireExtingisherExpiryDate.setVisibility(View.VISIBLE);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutNoOfFireExtingisherAvailablity.setVisibility(View.VISIBLE);
        if (FireExtingisherAvailablity.equals("Not Available")) {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate.setText("");
            mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal.setText("");
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutFireExtingisherExpiryDate.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutNoOfFireExtingisherAvailablity.setVisibility(View.GONE);
        }
    }

    private void visibilityOfTypesOfFault(String RegisterFault) {

        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutTypesOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutTypesOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteHygieneGeneralSaftyLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal.setText("");
            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfSitePremises:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriUploadPhotoOfSitePremises != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriUploadPhotoOfSitePremises);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringUploadPhotoOfSitePremises = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfSitePremises = "";
                    imageFileUriUploadPhotoOfSitePremises = null;
                    mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfSitePremisesView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_CautionSignBoard:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriCautionSignBoard != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriCautionSignBoard);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringCautionSignBoard = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileCautionSignBoard = "";
                    imageFileUriCautionSignBoard = null;
                    mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonCautionSignBoardPhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_WarningSignBoard:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriWarningSignBoard != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriWarningSignBoard);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringWarningSignBoard = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileWarningSignBoard = "";
                    imageFileUriWarningSignBoard = null;
                    mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonWarningSignBoardPhotoView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_DangerSignBoard:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriDangerSignBoard != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriDangerSignBoard);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringDangerSignBoard = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileDangerSignBoard = "";
                    imageFileUriDangerSignBoard = null;
                    mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonDangerSignBoardPhotoView.setVisibility(View.GONE);
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
                            mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteHygieneGeneralSaftyButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menuSubmit:
                if (checkValidationOfArrayFields() == true) {
                    submitDetails();
                    Intent intent = new Intent(this, PreventiveMaintenanceSiteAlarmCheckPointsActivity.class);
                    intent.putExtra("batteryType", batteryType);
                    startActivity(intent);
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

    public boolean checkValidationOfArrayFields() {
        String sitePremisesCleaning = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSitePremisesCleaningVal.getText().toString().trim();
        String uploadPhotoOfSitePremises = base64StringUploadPhotoOfSitePremises;
        String equipmentCleaning = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewEquipmentCleaningVal.getText().toString().trim();
        String anyEagleCrowHoneyHivesInTower = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewAnyHivesInTowerVal.getText().toString().trim();
        String compoundWallFencingStatus = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewCompoundWallFencingStatusVal.getText().toString().trim();
        String gateLockAvailability = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewGateLockAvailablityVal.getText().toString().trim();
        String shelterLockAvailability = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewShelterLockAvailablityVal.getText().toString().trim();
        String dgLockAvailability = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewDgLockAvailablityVal.getText().toString().trim();
        String fireExtinguisherAvaliability = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireExtingisherAvailablityVal.getText().toString().trim();
        String noOfFireExtingusher = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewNoOfFireExtingisherAvailablityVal.getText().toString().trim();
        String fireExtinguisherExpiryDate = mPreventiveMaintenanceSiteHygieneGeneralSaftyEditTextFireExtingisherExpiryDate.getText().toString().trim();
        String fireBucket = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewFireBucketAvailablityVal.getText().toString().trim();
        String cautionSignBoardPhoto = base64StringCautionSignBoard;
        String warningSignBoardPhoto = base64StringWarningSignBoard;
        String dangerSignBoardPhoto = base64StringDangerSignBoard;
        String safetyChartsAndCalender = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewSafetyChartsAndCalendarVal.getText().toString().trim();
        String unusedMaterialInSite = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewUnusedMaterialInSiteVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteHygieneGeneralSaftyTextViewTypesOfFaultVal.getText().toString().trim();

        if (sitePremisesCleaning.isEmpty() || sitePremisesCleaning == null) {
            showToast("Select Site Premises Cleaning");
            return false;
        } else if (uploadPhotoOfSitePremises.isEmpty() || uploadPhotoOfSitePremises == null) {
            showToast("Upload Photo Of Site Premises");
            return false;
        } else if (equipmentCleaning.isEmpty() || equipmentCleaning == null) {
            showToast("Select Equipment Cleaning");
            return false;
        } else if (anyEagleCrowHoneyHivesInTower.isEmpty() || anyEagleCrowHoneyHivesInTower == null) {
            showToast("Select Any Eagle/Crow/Honey Hives In Tower");
            return false;
        } else if ((compoundWallFencingStatus.isEmpty() || compoundWallFencingStatus == null) && (!sitePm_siteBoundaryStatus.equals("Not Applicable") && !sitePm_siteBoundaryStatus.equals("Open Site"))) {
            showToast("Select Compound Wall/Fencing Status");
            return false;
        } else if (gateLockAvailability.isEmpty() || gateLockAvailability == null) {
            showToast("Select Gate Lock Availability");
            return false;
        } else if ((shelterLockAvailability.isEmpty() || shelterLockAvailability == null) && (!hototicket_Selected_SiteType.equals("Outdoor"))) {
            showToast("Select Shelter Lock Availability");
            return false;
        } else if ((dgLockAvailability.isEmpty() || dgLockAvailability == null) && (!hototicket_sourceOfPower.equals("Non DG"))) {
            showToast("Select DG Lock Availability");
            return false;
        } else if (fireExtinguisherAvaliability.isEmpty() || fireExtinguisherAvaliability == null) {
            showToast("Select Fire Extinguisher Avaliability");
            return false;
        } else if ((noOfFireExtingusher.isEmpty() || noOfFireExtingusher == null) && fireExtinguisherAvaliability.equals("Available")) {
            showToast("Select No Of Fire Extingusher");
            return false;
        } else if ((fireExtinguisherExpiryDate.isEmpty() || fireExtinguisherExpiryDate == null) && fireExtinguisherAvaliability.equals("Available")) {
            showToast("Select Fire Extinguisher Expiry Date");
            return false;
        } else if (fireBucket.isEmpty() || fireBucket == null) {
            showToast("Select Fire Bucket");
            return false;
        } else if (cautionSignBoardPhoto.isEmpty() || cautionSignBoardPhoto == null) {
            showToast("Select Caution Sign Board Photo");
            return false;
        } else if (warningSignBoardPhoto.isEmpty() || warningSignBoardPhoto == null) {
            showToast("Select Warning Sign Board Photo");
            return false;
        } else if (dangerSignBoardPhoto.isEmpty() || dangerSignBoardPhoto == null) {
            showToast("Select Danger Sign Board Photo");
            return false;
        } else if (safetyChartsAndCalender.isEmpty() || safetyChartsAndCalender == null) {
            showToast("Select Safety Charts And Calender");
            return false;
        } else if (unusedMaterialInSite.isEmpty() || unusedMaterialInSite == null) {
            showToast("Select Unused Material In Site");
            return false;
        } else if (registerFault.isEmpty() || registerFault == null) {
            showToast("Select Register Fault");
            return false;
        } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
            showToast("Select Type Of Fault");
            return false;
        } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
            showToast("Upload Photo Of Register Fault");
            return false;
        } else return true;
    }

}
