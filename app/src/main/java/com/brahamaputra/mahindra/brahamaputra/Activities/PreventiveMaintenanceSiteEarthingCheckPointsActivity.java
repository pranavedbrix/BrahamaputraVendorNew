package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
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
import com.brahamaputra.mahindra.brahamaputra.Data.EarthingCheckPointsData;
import com.brahamaputra.mahindra.brahamaputra.Data.EarthingCheckPointsParentData;
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
import java.util.Date;
import java.util.List;

public class PreventiveMaintenanceSiteEarthingCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteEarthingCheckPointsActivity.class.getSimpleName();

    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutAllNutOrBoltsAreIntact;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntact;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutIgbOrOgbStatus;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatus;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutLightningArresterStatus;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatus;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutNumberOfEarthPit;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPit;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutNumberOfEarthPitVisible;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisible;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal;

    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutExecuteEarthPitTest;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTest;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal;

    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutExecuteEarthPitTestFields;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo1;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo1;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo2;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo2;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo3;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo3;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo4;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo4;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo5;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo5;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo6;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo6;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo7;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo7;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo8;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo8;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutEarthNuetralVoltage;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthNuetralVoltage;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutObservationConclusion;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewObservationConclusion;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion;

    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutTypeOfFault;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal;
    private LinearLayout mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthingPitNumber;
    private TextView mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthPitValue;
    private EditText mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue;
    private Button mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    String str_pmSiteEcpAllNutOrBoltsAreIntactVal = "";
    String str_pmSiteEcpIgbOrOgbStatusVal = "";
    String str_pmSiteEcpLightningArresterStatusVal = "";

    String str_pmSiteEcpNumberOfEarthPitVal = "";
    String str_pmSiteEcpNumberOfEarthPitVisibleVal = "";
    String str_pmSiteEcpExecuteEarthPitTestVal = "";
    String str_pmSiteEcpRegisterFaultVal = "";
    String str_pmSiteEcpTypeOfFaultVal = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private ArrayList<EarthingCheckPointsData> earthingCheckPointsData;// replce airConditionersData

    private AlertDialogManager alertDialogManager;
    private EarthingCheckPointsParentData dataList;

    private int totalAcCount = 0;
    private int currentPos = 0;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_earthing_check_points);
        this.setTitle("Earthing Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        //////////////////////////////

        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, userId, ticketName);
        setListner();

        //dataList = new ArrayList<>();
        earthingCheckPointsData = new ArrayList<>();
        currentPos = 0;

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_typeOfFault)));
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
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutAllNutOrBoltsAreIntact = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_allNutOrBoltsAreIntact);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntact = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_allNutOrBoltsAreIntact);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_allNutOrBoltsAreIntactVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutIgbOrOgbStatus = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_igbOrOgbStatus);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_igbOrOgbStatus);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_igbOrOgbStatusVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutLightningArresterStatus = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_lightningArresterStatus);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_lightningArresterStatus);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_lightningArresterStatusVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutNumberOfEarthPit = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_numberOfEarthPit);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPit = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_numberOfEarthPit);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_numberOfEarthPitVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutNumberOfEarthPitVisible = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_numberOfEarthPitVisible);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisible = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_numberOfEarthPitVisible);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_numberOfEarthPitVisibleVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutExecuteEarthPitTest = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_executeEarthPitTest);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTest = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_executeEarthPitTest);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_executeEarthPitTestVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutExecuteEarthPitTestFields = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_executeEarthPitTestFields);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo1 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo1);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo1 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo1);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo1);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo2 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo2);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo2 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo2);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo2);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo3 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo3);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo3 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo3);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo3);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo4 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo4);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo4 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo4);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo4);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo5 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo5);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo5 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo5);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo5);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo6 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo6);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo6 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo6);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo6);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo7 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo7);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo7 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo7);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo7);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo8 = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_valuePITNo8);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewValuePITNo8 = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_valuePITNo8);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8 = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_valuePITNo8);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutEarthNuetralVoltage = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_earthNuetralVoltage);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthNuetralVoltage = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_earthNuetralVoltage);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_earthNuetralVoltage);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutObservationConclusion = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_observationConclusion);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewObservationConclusion = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_observationConclusion);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_observationConclusion);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_typeOfFault);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_button_uploadPhotoOfRegisterFaultView);

        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthingPitNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_earthingPitNumber);
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthPitValue = (TextView) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_textView_earthPitValue);
        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue = (EditText) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_editText_earthPitValue);
        mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteEarthingCheckPoints_button_nextReading);
    }

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                dataList = pmSiteTransactionDetails.getEarthingCheckPointsParentData();
                //earthingCheckPointsData.addAll(dataList.getEarthingCheckPointsData());

                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal.setText(dataList.getAllNutBoltsAreIntact());
                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal.setText(dataList.getIgbOgbStatus());
                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal.setText(dataList.getLightningArresterStatus());
                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.setText(dataList.getNumberOfEarthPit());
                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText(dataList.getNumberOfEarthPitVisible());

                str_pmSiteEcpNumberOfEarthPitVal = dataList.getNumberOfEarthPit();
                invalidateOptionsMenu();

                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal.setText(dataList.getExecuteEarthPitTest());
                visibilityOfExecuteEarthPitTest(dataList.getExecuteEarthPitTest());

                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1.setText(dataList.getValuePITNo1());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2.setText(dataList.getValuePITNo2());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3.setText(dataList.getValuePITNo3());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4.setText(dataList.getValuePITNo4());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5.setText(dataList.getValuePITNo5());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6.setText(dataList.getValuePITNo6());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7.setText(dataList.getValuePITNo7());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8.setText(dataList.getValuePITNo8());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage.setText(dataList.getEarthNuetralVoltage());
                mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion.setText(dataList.getObservationConclusion());


                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());
                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();

                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                    setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                }


                /*Multi Form Set Value Purpose
                /*if (earthingCheckPointsData != null && earthingCheckPointsData.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthingPitNumber.setText("Reading: #1");
                    totalAcCount = Integer.parseInt(dataList.getNumberOfEarthPit());


                    mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue.setText(earthingCheckPointsData.get(index).getEarthPitValue());

                    mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                    //if (airConditionersData.size() > 1) {
                    if (totalAcCount > 1) {
                        mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Finish");
                    }
                }*/

            } else {
                showToast("No previous saved data available");
                mLinearLayoutContainer.setVisibility(View.GONE);
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
                        str_pmSiteEcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteEcpTypeOfFaultVal);
                        //str_pmSiteEbmbTypeOfFaultVal = dataString;
                        //mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.setText(str_pmSiteEbmbTypeOfFaultVal);
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

    private void initCombo() {
        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_allNutOrBoltsAreIntact))),
                        "All Nut/Bolts are Intact",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpAllNutOrBoltsAreIntactVal = item.get(position);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal.setText(str_pmSiteEcpAllNutOrBoltsAreIntactVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_igbOrOgbStatus))),
                        "IGB/OGB Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpIgbOrOgbStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal.setText(str_pmSiteEcpIgbOrOgbStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_lightningArresterStatus))),
                        "Lightning Arrester Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpLightningArresterStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal.setText(str_pmSiteEcpLightningArresterStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_noOfEarthPit))),
                        "Number of Earth PIT",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpNumberOfEarthPitVal = item.get(position);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.setText(str_pmSiteEcpNumberOfEarthPitVal);
                        /////////////////
                        /*Multi Form Dropdown Value Purpose
                        str_pmSiteEcpNumberOfEarthPitVal = item.get(position);
                        invalidateOptionsMenu();
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.setText(str_pmSiteEcpNumberOfEarthPitVal);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText("");//008

                        if (earthingCheckPointsData != null && earthingCheckPointsData.size() > 0) {
                            earthingCheckPointsData.clear();
                        }
                        currentPos = 0;
                        totalAcCount = 0;
                        clearFields(currentPos);

                        // Clear all field value and hide layout If Non AC or O //
                        if (str_pmSiteEcpNumberOfEarthPitVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                        } else {
                            totalAcCount = Integer.parseInt(str_pmSiteEcpNumberOfEarthPitVal);
                            mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthingPitNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalAcCount > 0 && totalAcCount == 1) {
                                mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Finish");
                            } else {
                                mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Next Reading");
                            }
                        }*/


                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_noOfEarthPitVisible))),
                        "Number of Earth PIT Visible",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpNumberOfEarthPitVisibleVal = item.get(position);
                        //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText(str_pmSiteEcpNumberOfEarthPitVisibleVal);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText("");
                        if (checkValidationOnChangeNoOfEarthPitValue(mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.getText().toString().trim(), str_pmSiteEcpNumberOfEarthPitVisibleVal, "onClick") == true) {
                            mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.setText(str_pmSiteEcpNumberOfEarthPitVisibleVal);
                        }

                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_executeEarthPitTest))),
                        "Execute Earth Pit Test",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpExecuteEarthPitTestVal = item.get(position);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal.setText(str_pmSiteEcpExecuteEarthPitTestVal);
                        visibilityOfExecuteEarthPitTest(str_pmSiteEcpExecuteEarthPitTestVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEarthingCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteEcpRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteEcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });


        ////////////////////////////////

        /*Multi Form Button Purpose
        mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMultiSelectModel();
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos > 0) {
                        //Save current ac reading
                        saveEartPitRecords(currentPos);
                        currentPos = currentPos - 1;
                        //move to Next reading
                        displayEartPitRecords(currentPos);
                    }
                }
            }
        });
        mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMultiSelectModel();
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalAcCount - 1)) {
                        //Save current ac reading
                        saveEartPitRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayEartPitRecords(currentPos);
                    } else if (currentPos == (totalAcCount - 1)) {
                        saveEartPitRecords(currentPos);
                        if (checkValidationOnChangeNoOfEarthPitValue(mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.getText().toString().trim(), mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.getText().toString().trim(), "onSubmit") == true) {
                            submitDetails();
                            startActivity(new Intent(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, PreventiveMaintenanceSiteEbMeterBoxActivity.class));
                            finish();
                        }
                    }
                }
            }
        });*/

    }

    public boolean checkValidationOfArrayFields() {

        String earthPitValue = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue.getText().toString().trim();

        if (earthPitValue.isEmpty() || earthPitValue == null) {
            showToast("Please Enter Earth Pit Value");
            return false;
        } else return true;

    }

    private void saveEartPitRecords(int pos) {

        /*Multi Form Purpose
        String earthPitValue = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue.getText().toString().trim();

        EarthingCheckPointsData earthingCheckPointsChild = new EarthingCheckPointsData(earthPitValue);

        if (earthingCheckPointsData.size() > 0) {
            if (pos == earthingCheckPointsData.size()) {
                earthingCheckPointsData.add(earthingCheckPointsChild);
            } else if (pos < earthingCheckPointsData.size()) {
                earthingCheckPointsData.set(pos, earthingCheckPointsChild);
            }
        } else {
            earthingCheckPointsData.add(earthingCheckPointsChild);
        }*/
    }

    private void displayEartPitRecords(int pos) {

        /*Multi Form Purpose
        if (earthingCheckPointsData.size() > 0 && pos < earthingCheckPointsData.size()) {

            mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthingPitNumber.setText("Reading: #" + (pos + 1));

            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue.setText(earthingCheckPointsData.get(pos).getEarthPitValue());
            *//*mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.setText(earthingCheckPointsData.get(pos).getRegisterFault());
            mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setText(earthingCheckPointsData.get(pos).getTypeOfFault());

            visibilityOfTypesOfFault(earthingCheckPointsData.get(pos).getRegisterFault());

            if (earthingCheckPointsData.get(pos).getTypeOfFault() != null && earthingCheckPointsData.get(pos).getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                alreadySelectedTypeOfFaultList = new ArrayList<>();
                setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                setMultiSelectModel();
            }*//*

            mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalAcCount - 1)) {
                mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteEarthingCheckPointsButtonNextReading.setText("Next Reading");
            }
        }*/

    }

    private void submitDetails() {
        try {

            String allNutBoltsAreIntact = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal.getText().toString().trim();
            String igbOgbStatus = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal.getText().toString().trim();
            String lightningArresterStatus = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal.getText().toString().trim();
            String numberOfEarthPit = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.getText().toString().trim();
            String numberOfEarthPitVisible = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.getText().toString().trim();

            String executeEarthPitTest = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal.getText().toString().trim();
            String valuePITNo1 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1.getText().toString().trim();
            String valuePITNo2 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2.getText().toString().trim();
            String valuePITNo3 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3.getText().toString().trim();
            String valuePITNo4 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4.getText().toString().trim();
            String valuePITNo5 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5.getText().toString().trim();
            String valuePITNo6 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6.getText().toString().trim();
            String valuePITNo7 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7.getText().toString().trim();
            String valuePITNo8 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8.getText().toString().trim();
            String earthNuetralVoltage = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage.getText().toString().trim();
            String observationConclusion = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion.getText().toString().trim();

            String registerFault = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;

            dataList = new EarthingCheckPointsParentData(allNutBoltsAreIntact, igbOgbStatus,
                    lightningArresterStatus, numberOfEarthPit, numberOfEarthPitVisible, executeEarthPitTest, valuePITNo1,
                    valuePITNo2, valuePITNo3, valuePITNo4, valuePITNo5, valuePITNo6, valuePITNo7,
                    valuePITNo8, earthNuetralVoltage, observationConclusion, registerFault,
                    typeOfFault, base64StringUploadPhotoOfRegisterFault);
            //, earthingCheckPointsData);

            pmSiteTransactionDetails.setEarthingCheckPointsParentData(dataList);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        mPreventiveMaintenanceSiteEarthingCheckPointsTextViewEarthingPitNumber.setText("Reading: #" + (indexPos + 1));

        mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthPitValue.setText("");
        //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.setText("");
        //mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setText("");
        alreadySelectedTypeOfFaultList = new ArrayList<>();

    }

    public boolean checkValidationOnChangeNoOfEarthPitValue(String noOfEarthPitValue, String noOfEarthPitValueVisible, String methodFlag) {

        String allNutBoltsAreIntact = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewAllNutOrBoltsAreIntactVal.getText().toString().trim();
        String igbOgbStatus = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewIgbOrOgbStatusVal.getText().toString().trim();
        String lightningArresterStatus = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewLightningArresterStatusVal.getText().toString().trim();

        String executeEarthPitTest = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewExecuteEarthPitTestVal.getText().toString().trim();
        /*For Future validation*/
        String valuePITNo1 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1.getText().toString().trim();
        String valuePITNo2 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2.getText().toString().trim();
        String valuePITNo3 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3.getText().toString().trim();
        String valuePITNo4 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4.getText().toString().trim();
        String valuePITNo5 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5.getText().toString().trim();
        String valuePITNo6 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6.getText().toString().trim();
        String valuePITNo7 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7.getText().toString().trim();
        String valuePITNo8 = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8.getText().toString().trim();
        String earthNuetralVoltage = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage.getText().toString().trim();
        String observationConclusion = mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion.getText().toString().trim();

        String registerFault = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

        if ((allNutBoltsAreIntact.isEmpty() || allNutBoltsAreIntact == null) && methodFlag.equals("onSubmit")) {
            showToast("Select All Nut/Bolts are Intact");
            return false;
        } else if ((igbOgbStatus.isEmpty() || igbOgbStatus == null) && methodFlag.equals("onSubmit")) {
            showToast("Select IGB/OGB Status");
            return false;
        } else if ((lightningArresterStatus.isEmpty() || lightningArresterStatus == null) && methodFlag.equals("onSubmit")) {
            showToast("Select Lighting Arrester Status");
            return false;
        } else if (noOfEarthPitValue.isEmpty() || noOfEarthPitValue == null) {
            showToast("Select No of Earth Pit");
            return false;
        } else if (noOfEarthPitValueVisible.isEmpty() || noOfEarthPitValueVisible == null) {
            showToast("Select Number of Earth Pit Visible");
            return false;
        } else if (Integer.valueOf(noOfEarthPitValueVisible) > 0) {
            if (noOfEarthPitValueVisible.isEmpty() || noOfEarthPitValueVisible == null) {
                showToast("Select Number of Earth Pit Visible");
                return false;
            } else if (Integer.valueOf(noOfEarthPitValueVisible) > Integer.valueOf(noOfEarthPitValue)) {
                showToast("Select Earth Pit Visible is less than or equal to Earth Pit");
                return false;
            }  /*else if ((earthingCheckPointsData.size() != Integer.valueOf(noOfEarthPitValue) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");//as a mentioned AC in no of AC provided
                return false;
            }*/ else return true;
        } else if ((executeEarthPitTest.isEmpty() || executeEarthPitTest == null) && methodFlag.equals("onSubmit")) {
            showToast("Select Execute Earth Pit Test");
            return false;
        } else if ((valuePITNo1.isEmpty() || valuePITNo1 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 01");
            return false;
        } else if ((valuePITNo2.isEmpty() || valuePITNo2 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 02");
            return false;
        } else if ((valuePITNo3.isEmpty() || valuePITNo3 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 03");
            return false;
        } else if ((valuePITNo4.isEmpty() || valuePITNo4 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 04");
            return false;
        } else if ((valuePITNo5.isEmpty() || valuePITNo5 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 05");
            return false;
        } else if ((valuePITNo6.isEmpty() || valuePITNo6 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 06");
            return false;
        } else if ((valuePITNo7.isEmpty() || valuePITNo7 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 07");
            return false;
        } else if ((valuePITNo8.isEmpty() || valuePITNo8 == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Value in PIT No 08");
            return false;
        } else if ((earthNuetralVoltage.isEmpty() || earthNuetralVoltage == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Earth to Neutral Voltage");
            return false;
        } else if ((observationConclusion.isEmpty() || observationConclusion == null) && executeEarthPitTest.equals("Yes")) {
            showToast("Enter Observation & Conclusion");
            return false;
        } else if ((registerFault.isEmpty() || registerFault == null) && methodFlag.equals("onSubmit")) {
            showToast("Select Register Fault");
            return false;
        } else if ((((typeOfFault.isEmpty() || typeOfFault == null) && (registerFault.equals("Yes"))) && methodFlag.equals("onSubmit"))) {
            showToast("Select Type of Fault");
            return false;
        } else if ((((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && (registerFault.equals("Yes"))) && methodFlag.equals("onSubmit"))) {
            showToast("Upload Photo Of Type Of Fault");
            return false;
        } else return true;

    }

    private void setListner() {

        mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void visibilityOfTypesOfFault(String pmSiteEcpRegisterFaultVal) {

        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (pmSiteEcpRegisterFaultVal.equals("Yes")) {
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteEarthingCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    private void visibilityOfExecuteEarthPitTest(String pmSiteEcpExecuteEarthPitTestVal) {
        if (pmSiteEcpExecuteEarthPitTestVal.equals("Yes")) {
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutExecuteEarthPitTestFields.setVisibility(View.VISIBLE);
            /*For Fuure Purpose
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo1.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo2.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo3.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo4.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo5.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo6.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo7.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo8.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutEarthNuetralVoltage.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutObservationConclusion.setVisibility(View.VISIBLE);*/
        } else {

            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutExecuteEarthPitTestFields.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion.setText("");
            /*For Fuure Purpose
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo1.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo1.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo2.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo2.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo3.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo3.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo4.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo4.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo5.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo5.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo6.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo6.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo7.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo7.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutValuePITNo8.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextValuePITNo8.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutEarthNuetralVoltage.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextEarthNuetralVoltage.setText("");
            mPreventiveMaintenanceSiteEarthingCheckPointsLinearLayoutObservationConclusion.setVisibility(View.GONE);
            mPreventiveMaintenanceSiteEarthingCheckPointsEditTextObservationConclusion.setText("");*/
        }
    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_DGBCReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteEarthingCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriUploadPhotoOfRegisterFault != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriUploadPhotoOfRegisterFault);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringUploadPhotoOfRegisterFault = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteEarthingCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
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
        /*if (str_pmSiteEcpNumberOfEarthPitVal != null && !str_pmSiteEcpNumberOfEarthPitVal.isEmpty()) {
            if (Integer.valueOf(str_pmSiteEcpNumberOfEarthPitVal) > 0) {
                shareItem.setVisible(false);
            }
        }*/

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
                str_pmSiteEcpNumberOfEarthPitVal = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.getText().toString();

                if (str_pmSiteEcpNumberOfEarthPitVal == null || str_pmSiteEcpNumberOfEarthPitVal.equals("")) {
                    showToast("Select Number of Earth PIT");
                } else {
                    if (checkValidationOnChangeNoOfEarthPitValue(mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVal.getText().toString().trim(), mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.getText().toString().trim(), "onSubmit") == true) {
                        submitDetails();
                        startActivity(new Intent(this, PreventiveMaintenanceSiteEbMeterBoxActivity.class));
                        finish();
                    }
                }

                //startActivity(new Intent(this, PreventiveMaintenanceSiteEbMeterBoxActivity.class));
                //finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
