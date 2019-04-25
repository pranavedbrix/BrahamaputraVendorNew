package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.brahamaputra.mahindra.brahamaputra.Data.GeneralSafetyMeasuresData;
import com.brahamaputra.mahindra.brahamaputra.Data.GeneralSafetyMeasuresParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;

import com.brahamaputra.mahindra.brahamaputra.Data.PowerBackupsDGParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.TowerDetailsData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;

import com.brahamaputra.mahindra.brahamaputra.Utils.InputFilterMinMax;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_Selected_SiteType;

public class GeneralAndSafetyMeasures extends BaseActivity {


    final Calendar myCalendar = Calendar.getInstance();

    String str_SiteBoundaryStatus;
    String str_SiteHygieneVegitationStatus;
    String str_GateLock;
    String str_DGRoomLock;
    String str_FireExtuinguisher;
    String str_FireExtuinguisherType;
    String str_FireBucket;
    String str_SecurityStatus24x7;
    String str_NoofSecurityPerson;
    String str_CaretakerStatusUpOnEmergency;
    String str_IsSecurityCaretakeristheOwnerofSite;
    String str_CaretakerSecuritySalaryPaidBy;
    String str_CaretakerSecurityStayinginSite;
    String str_NumberofEarthPit;
    String str_LightningArresterStatus;
    String str_FencingCompoundWallCondition;
    String str_NumberoffreeODPaltformAvailable;
    String str_AlarmMultipluxerStatus;
    String str_DoorOpenSensor;
    String str_FuelSensor;
    String str_FireSmokeSensor;



    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;

    private ArrayList<GeneralSafetyMeasuresData> generalSafetyMeasuresData;

    private TowerDetailsData towerDetailsData;
    private PowerBackupsDGParentData powerBackupsDGParentData;


    private SessionManager sessionManager;

    private TextView mGeneralAndSafetyMeasuresTextViewPrevailingSLA;
    private EditText mGeneralAndSafetyMeasuresEditTextPrevailingSLA;
    private TextView mGeneralAndSafetyMeasureTextViewSiteBoundaryStatus;
    private TextView mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal;
    private TextView mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatus;
    private TextView mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatusVal;
    private TextView mGeneralAndSafetyMeasureTextViewGateLock;
    private TextView mGeneralAndSafetyMeasureTextViewGateLockVal;
    private TextView mGeneralAndSafetyMeasureTextViewDGRoomLock;
    private TextView mGeneralAndSafetyMeasureTextViewDGRoomLockVal;
    private TextView mGeneralAndSafetyMeasureTextViewFireExtuinguisher;
    private TextView mGeneralAndSafetyMeasureTextViewFireExtuinguisherVal;
    private TextView mGeneralAndSafetyMeasureTextViewFireExtuinguisherType;
    private TextView mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal;
    private TextView mGeneralAndSafetyMeasureTextViewFireExtuinguisherExpiryDate;
    private EditText mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate;
    private TextView mGeneralAndSafetyMeasureTextViewFireBucket;
    private TextView mGeneralAndSafetyMeasureTextViewFireBucketVal;
    private TextView mGeneralAndSafetyMeasureTextViewSecurityStatus24x7;
    private TextView mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val;
    private TextView mGeneralAndSafetyMeasureTextViewNoofSecurityPerson;
    private TextView mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal;
    private TextView mGeneralAndSafetyMeasureTextViewMobileNumberofSecurity;
    private EditText mGeneralAndSafetyMeasureEditTextMobileNumberofSecurity;
    private TextView mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergency;
    private TextView mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal;
    private TextView mGeneralAndSafetyMeasureTextViewMobileNumberofCaretaker;
    private EditText mGeneralAndSafetyMeasureEditTextMobileNumberofCaretaker;
    private TextView mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSite;
    private TextView mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal;
    private TextView mGeneralAndSafetyMeasureTextViewSalaryofSecurityCaretaker;
    private EditText mGeneralAndSafetyMeasureEditTextSalaryofSecurityCaretaker;
    private TextView mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidBy;
    private TextView mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal;
    private TextView mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSite;
    private TextView mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal;
    private TextView mGeneralAndSafetyMeasureTextViewNumberofEarthPit;
    private TextView mGeneralAndSafetyMeasureTextViewNumberofEarthPitVal;
    private TextView mGeneralAndSafetyMeasureTextViewLightningArresterStatusVal;
    private TextView mGeneralAndSafetyMeasureTextViewLightningArresterStatus;
    private TextView mGeneralAndSafetyMeasureTextViewFencingCompoundWallCondition;
    private TextView mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal;
    private TextView mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailable;
    private TextView mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailableVal;
    private TextView mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatus;
    private TextView mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatusVal;
    private TextView mGeneralAndSafetyMeasureTextViewDoorOpenSensor;
    private TextView mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal;
    private TextView mGeneralAndSafetyMeasureTextViewFuelSensor;
    private TextView mGeneralAndSafetyMeasureTextViewFuelSensorVal;
    private TextView mGeneralAndSafetyMeasureTextViewFireSmokeSensor;
    private TextView mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal;

    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutSiteBoundaryStatus;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutDGRoomLock;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherType;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherExpiryDate;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutNoofSecurityPerson;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutMobileNumberofSecurity;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutMobileNumberofCaretaker;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutIsSecurityCaretakeristheOwnerofSite;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutSalaryofSecurityCaretaker;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutCaretakerSecuritySalaryPaidBy;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutCaretakerSecurityStayinginSite;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutFencingCompoundWallCondition;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutDoorOpenSensor;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutFuelSensor;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutFireSmokeSensor;
    private LinearLayout mGeneralAndSafetyMeasureLinearLayoutAgencyNameSalaryPaid;
    private EditText mGeneralAndSafetyMeasureEditTextAgencyNameSalaryPaid;


    TextView mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisher;
    TextView mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal;

    TextView mGeneralAndSafetyMeasureTextViewSiteAccessStatus;
    TextView mGeneralAndSafetyMeasureTextViewSiteAccessStatusVal;

    String str_NoOfFireExtuinguisher;
    String str_SiteAccessStatus;

    private GeneralSafetyMeasuresParentData generalSafetyMeasuresParentData;
    private int totalCount = 0;
    private int currentPos = 0;
    Button mGeneralAndSafetyMeasureButtonPreviousReading;
    Button mGeneralAndSafetyMeasureButtonNextReading;
    TextView mGeneralAndSafetyMeasureTextViewNumber;
    private LinearLayout linearLayout_container;
    DecimalConversion decimalConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_and_safety_measures);
        this.setTitle("GENERAL & SAFETY MEASURES");
        decimalConversion = new DecimalConversion();
        sessionManager = new SessionManager(GeneralAndSafetyMeasures.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(GeneralAndSafetyMeasures.this, userId, ticketName);
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
        //setInputDetails();
        checkValidation();
        //onValidateFireExtuinguisher();
        onValidateSecurityStatus();
        onValidateCaretakerStatus();
        onValidateSecurityCaretakeStatus();
        onValidateSalaryPaidBy();

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


        mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GeneralAndSafetyMeasures.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        generalSafetyMeasuresData = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);
        mGeneralAndSafetyMeasuresEditTextPrevailingSLA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
    }

    private void assignViews() {
        mGeneralAndSafetyMeasuresTextViewPrevailingSLA = (TextView) findViewById(R.id.generalAndSafetyMeasures_textView_PrevailingSLA);
        mGeneralAndSafetyMeasuresEditTextPrevailingSLA = (EditText) findViewById(R.id.generalAndSafetyMeasures_editText_PrevailingSLA);
        mGeneralAndSafetyMeasureTextViewSiteBoundaryStatus = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SiteBoundaryStatus);
        mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SiteBoundaryStatus_val);
        mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatus = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SiteHygieneVegitationStatus);
        mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatusVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SiteHygieneVegitationStatus_val);
        mGeneralAndSafetyMeasureTextViewGateLock = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_GateLock);
        mGeneralAndSafetyMeasureTextViewGateLockVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_GateLock_val);
        mGeneralAndSafetyMeasureTextViewDGRoomLock = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_DGRoomLock);
        mGeneralAndSafetyMeasureTextViewDGRoomLockVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_DGRoomLock_val);
        mGeneralAndSafetyMeasureTextViewFireExtuinguisher = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireExtuinguisher);
        mGeneralAndSafetyMeasureTextViewFireExtuinguisherVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireExtuinguisher_val);
        mGeneralAndSafetyMeasureTextViewFireExtuinguisherType = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireExtuinguisherType);
        mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireExtuinguisherType_val);
        mGeneralAndSafetyMeasureTextViewFireExtuinguisherExpiryDate = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireExtuinguisherExpiryDate);
        mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate = (EditText) findViewById(R.id.generalAndSafetyMeasure_editText_FireExtuinguisherExpiryDate);
        mGeneralAndSafetyMeasureTextViewFireBucket = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireBucket);
        mGeneralAndSafetyMeasureTextViewFireBucketVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireBucket_val);
        mGeneralAndSafetyMeasureTextViewSecurityStatus24x7 = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SecurityStatus24x7);
        mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SecurityStatus24x7_val);
        mGeneralAndSafetyMeasureTextViewNoofSecurityPerson = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_NoofSecurityPerson);
        mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_NoofSecurityPerson_val);
        mGeneralAndSafetyMeasureTextViewMobileNumberofSecurity = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_MobileNumberofSecurity);
        mGeneralAndSafetyMeasureEditTextMobileNumberofSecurity = (EditText) findViewById(R.id.generalAndSafetyMeasure_editText_MobileNumberofSecurity);
        mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergency = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_CaretakerStatusUpOnEmergency);
        mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_CaretakerStatusUpOnEmergency_val);
        mGeneralAndSafetyMeasureTextViewMobileNumberofCaretaker = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_MobileNumberofCaretaker);
        mGeneralAndSafetyMeasureEditTextMobileNumberofCaretaker = (EditText) findViewById(R.id.generalAndSafetyMeasure_editText_MobileNumberofCaretaker);
        mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSite = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_IsSecurityCaretakeristheOwnerofSite);
        mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_IsSecurityCaretakeristheOwnerofSite_val);
        mGeneralAndSafetyMeasureTextViewSalaryofSecurityCaretaker = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_SalaryofSecurityCaretaker);
        mGeneralAndSafetyMeasureEditTextSalaryofSecurityCaretaker = (EditText) findViewById(R.id.generalAndSafetyMeasure_editText_SalaryofSecurityCaretaker);
        mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidBy = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_CaretakerSecuritySalaryPaidBy);
        mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_CaretakerSecuritySalaryPaidBy_val);
        mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSite = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_CaretakerSecurityStayinginSite);
        mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_CaretakerSecurityStayinginSite_val);
        mGeneralAndSafetyMeasureTextViewNumberofEarthPit = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_NumberofEarthPit);
        mGeneralAndSafetyMeasureTextViewNumberofEarthPitVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_NumberofEarthPit_val);
        mGeneralAndSafetyMeasureTextViewLightningArresterStatus = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_LightningArresterStatus);
        mGeneralAndSafetyMeasureTextViewLightningArresterStatusVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_LightningArresterStatus_val);
        mGeneralAndSafetyMeasureTextViewFencingCompoundWallCondition = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FencingCompoundWallCondition);
        mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FencingCompoundWallCondition_val);
        mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailable = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_NumberoffreeODPaltformAvailable);
        mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailableVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_NumberoffreeODPaltformAvailable_val);
        mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatus = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_AlarmMultipluxerStatus);
        mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatusVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_AlarmMultipluxerStatus_val);
        mGeneralAndSafetyMeasureTextViewDoorOpenSensor = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_DoorOpenSensor);
        mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_DoorOpenSensor_val);
        mGeneralAndSafetyMeasureTextViewFuelSensor = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FuelSensor);
        mGeneralAndSafetyMeasureTextViewFuelSensorVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FuelSensor_val);
        mGeneralAndSafetyMeasureTextViewFireSmokeSensor = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireSmokeSensor);
        mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_FireSmokeSensor_val);

        mGeneralAndSafetyMeasureLinearLayoutSiteBoundaryStatus = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_SiteBoundaryStatus);
        mGeneralAndSafetyMeasureLinearLayoutDGRoomLock = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_DGRoomLock);
        mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherType = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_FireExtuinguisherType);
        mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherExpiryDate = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_FireExtuinguisherExpiryDate);
        mGeneralAndSafetyMeasureLinearLayoutNoofSecurityPerson = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_NoofSecurityPerson);
        mGeneralAndSafetyMeasureLinearLayoutMobileNumberofSecurity = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_MobileNumberofSecurity);
        mGeneralAndSafetyMeasureLinearLayoutMobileNumberofCaretaker = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_MobileNumberofCaretaker);
        mGeneralAndSafetyMeasureLinearLayoutIsSecurityCaretakeristheOwnerofSite = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_IsSecurityCaretakeristheOwnerofSite);
        mGeneralAndSafetyMeasureLinearLayoutSalaryofSecurityCaretaker = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_SalaryofSecurityCaretaker);
        mGeneralAndSafetyMeasureLinearLayoutCaretakerSecuritySalaryPaidBy = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_CaretakerSecuritySalaryPaidBy);
        mGeneralAndSafetyMeasureLinearLayoutCaretakerSecurityStayinginSite = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_CaretakerSecurityStayinginSite);
        mGeneralAndSafetyMeasureLinearLayoutFencingCompoundWallCondition = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_FencingCompoundWallCondition);
        mGeneralAndSafetyMeasureLinearLayoutDoorOpenSensor = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_DoorOpenSensor);
        mGeneralAndSafetyMeasureLinearLayoutFuelSensor = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_FuelSensor);
        mGeneralAndSafetyMeasureLinearLayoutFireSmokeSensor = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_FireSmokeSensor);
        mGeneralAndSafetyMeasureLinearLayoutAgencyNameSalaryPaid = (LinearLayout) findViewById(R.id.generalAndSafetyMeasure_linearLayout_agencyNameSalaryPaid);
        mGeneralAndSafetyMeasureEditTextAgencyNameSalaryPaid = (EditText) findViewById(R.id.generalAndSafetyMeasure_editText_agencyNameSalaryPaid);

        mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisher = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_noOfFireExtuinguisher);
        mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_noOfFireExtuinguisher_val);

        mGeneralAndSafetyMeasureTextViewSiteAccessStatus = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_siteAccessStatus);
        mGeneralAndSafetyMeasureTextViewSiteAccessStatusVal = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_siteAccessStatus_val);


        mGeneralAndSafetyMeasureTextViewNumber = (TextView) findViewById(R.id.generalAndSafetyMeasure_textView_Number);
        mGeneralAndSafetyMeasureButtonPreviousReading = (Button) findViewById(R.id.generalAndSafetyMeasure_button_previousReading);
        mGeneralAndSafetyMeasureButtonNextReading = (Button) findViewById(R.id.generalAndSafetyMeasure_button_nextReading);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);

        //mGeneralAndSafetyMeasuresEditTextPrevailingSLA.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mGeneralAndSafetyMeasuresEditTextPrevailingSLA.setFilters(new InputFilter[]{new InputFilterMinMax(0.00, 100.00, 6, 2)});
        mGeneralAndSafetyMeasureLinearLayoutAgencyNameSalaryPaid.setVisibility(View.GONE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void DecimalFormatConversion() {
        mGeneralAndSafetyMeasuresEditTextPrevailingSLA.setText(decimalConversion.convertDecimal(mGeneralAndSafetyMeasuresEditTextPrevailingSLA.getText().toString()));

    }


    private void initCombo() {


        mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_SiteBoundaryStatus))),
                        "Site Boundary Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_SiteBoundaryStatus = item.get(position);
                        mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal.setText(str_SiteBoundaryStatus);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_SiteHygieneVegitationStatus))),
                        "Site Hygiene Vegitation Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_SiteHygieneVegitationStatus = item.get(position);
                        mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatusVal.setText(str_SiteHygieneVegitationStatus);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewGateLockVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_GateLock))),
                        "Gate Lock",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_GateLock = item.get(position);
                        mGeneralAndSafetyMeasureTextViewGateLockVal.setText(str_GateLock);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewDGRoomLockVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_DGRoomLock))),
                        "DGRoom Lock",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_DGRoomLock = item.get(position);
                        mGeneralAndSafetyMeasureTextViewDGRoomLockVal.setText(str_DGRoomLock);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewFireExtuinguisherVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_FireExtuinguisher))),
                        "Fire Extuinguisher",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FireExtuinguisher = item.get(position);
                        mGeneralAndSafetyMeasureTextViewFireExtuinguisherVal.setText(str_FireExtuinguisher);
                        onValidateFireExtuinguisher();
                    }
                });

            }
        });

        mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_NoOfFireExtuinguisher))),
                        "No of Fire Extuinguisher",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_NoOfFireExtuinguisher = item.get(position);
                        mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal.setText(str_NoOfFireExtuinguisher);


                        //clear NoOfFireExtuinguisher collection empty by select / changing value of No of NoOfFireExtuinguisher
                        if (generalSafetyMeasuresData != null && generalSafetyMeasuresData.size() > 0) {
                            generalSafetyMeasuresData.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        totalCount = Integer.parseInt(str_NoOfFireExtuinguisher);

                        // Clear all field value and hide layout If O //
                        if (totalCount > 0) {

                            mGeneralAndSafetyMeasureTextViewNumber.setText("Reading: #1");
                            linearLayout_container.setVisibility(View.VISIBLE);
                            mGeneralAndSafetyMeasureButtonPreviousReading.setVisibility(View.GONE);
                            mGeneralAndSafetyMeasureButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                mGeneralAndSafetyMeasureButtonNextReading.setText("Finish");
                            } else {
                                mGeneralAndSafetyMeasureButtonNextReading.setText("Next Reading");
                            }
                        } else {
                            linearLayout_container.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_FireExtuinguisherType))),
                        "Fire Extuinguisher Type",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FireExtuinguisherType = item.get(position);
                        mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.setText(str_FireExtuinguisherType);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewFireBucketVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_FireBucket))),
                        "Fire Bucket",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FireBucket = item.get(position);
                        mGeneralAndSafetyMeasureTextViewFireBucketVal.setText(str_FireBucket);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_SecurityStatus24x7))),
                        "Security Status 24x7",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_SecurityStatus24x7 = item.get(position);
                        mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val.setText(str_SecurityStatus24x7);
                        onValidateSecurityStatus();
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_NoofSecurityPerson))),
                        "No of Security Person",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_NoofSecurityPerson = item.get(position);
                        mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal.setText(str_NoofSecurityPerson);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_CaretakerStatusUpOnEmergency))),
                        "Caretaker Status Up On Emergency",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_CaretakerStatusUpOnEmergency = item.get(position);
                        mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal.setText(str_CaretakerStatusUpOnEmergency);
                        onValidateCaretakerStatus();
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_IsSecurityCaretakeristheOwnerofSite))),
                        "Is Security Caretaker is the Owner of Site",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_IsSecurityCaretakeristheOwnerofSite = item.get(position);
                        mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal.setText(str_IsSecurityCaretakeristheOwnerofSite);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_CaretakerSecuritySalaryPaidBy))),
                        "Caretaker Security Salary Paid By",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_CaretakerSecuritySalaryPaidBy = item.get(position);
                        mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal.setText(str_CaretakerSecuritySalaryPaidBy);
                        onValidateSalaryPaidBy();

                    }
                });


            }
        });
        mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_CaretakerSecurityStayinginSite))),
                        "Caretaker Security Staying in Site",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_CaretakerSecurityStayinginSite = item.get(position);
                        mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal.setText(str_CaretakerSecurityStayinginSite);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewNumberofEarthPitVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_NumberofEarthPit))),
                        "Number of EarthPit",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_NumberofEarthPit = item.get(position);
                        mGeneralAndSafetyMeasureTextViewNumberofEarthPitVal.setText(str_NumberofEarthPit);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewLightningArresterStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_LightningArresterStatus))),
                        "Lightning Arrester Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_LightningArresterStatus = item.get(position);
                        mGeneralAndSafetyMeasureTextViewLightningArresterStatusVal.setText(str_LightningArresterStatus);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_FencingCompoundWallCondition))),
                        "Fencing Compound Wall Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FencingCompoundWallCondition = item.get(position);
                        mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal.setText(str_FencingCompoundWallCondition);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailableVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_NumberoffreeODPaltformAvailable))),
                        "Number of free OD Paltform Available",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_NumberoffreeODPaltformAvailable = item.get(position);
                        mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailableVal.setText(str_NumberoffreeODPaltformAvailable);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_AlarmMultipluxerStatus))),
                        "Alarm Multipluxer Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_AlarmMultipluxerStatus = item.get(position);
                        mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatusVal.setText(str_AlarmMultipluxerStatus);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_DoorOpenSensor))),
                        "Door Open Sensors",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_DoorOpenSensor = item.get(position);
                        mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal.setText(str_DoorOpenSensor);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewFuelSensorVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_FuelSensor))),
                        "Fuel Sensor",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FuelSensor = item.get(position);
                        mGeneralAndSafetyMeasureTextViewFuelSensorVal.setText(str_FuelSensor);
                    }
                });

            }
        });
        mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_FireSmokeSensor))),
                        "Fire Smoke Sensor",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FireSmokeSensor = item.get(position);
                        mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal.setText(str_FireSmokeSensor);

                    }
                });

            }
        });

        mGeneralAndSafetyMeasureTextViewSiteAccessStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(GeneralAndSafetyMeasures.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_generalAndSafetyMeasure_SiteAccessStatus))),
                        "Site Access status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_SiteAccessStatus = item.get(position);
                        mGeneralAndSafetyMeasureTextViewSiteAccessStatusVal.setText(str_SiteAccessStatus);

                    }
                });

            }
        });

        mGeneralAndSafetyMeasureButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
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

        mGeneralAndSafetyMeasureButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidtionForArrayFields()) {
                    if (currentPos < (totalCount - 1)) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    } else if (currentPos == (totalCount - 1)) {
                        //Save Final current reading and submit all AC data
                        saveRecords(currentPos);
                        if (checkValidationonSubmit("onSubmit") == true) {
                            submitDetails();
                            startActivity(new Intent(GeneralAndSafetyMeasures.this, ACDB_DCDB.class));
                            finish();
                        }
                    }
                }
            }
        });


    }


    private void onValidateFireExtuinguisher() {
        String value = mGeneralAndSafetyMeasureTextViewFireExtuinguisherVal.getText().toString();
        mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherType.setVisibility(View.VISIBLE);
        mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherExpiryDate.setVisibility(View.VISIBLE);

        if (value.equals("Not Available")) {
            mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherType.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.setText("");

            mGeneralAndSafetyMeasureLinearLayoutFireExtuinguisherExpiryDate.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.setText("");
        }

    }

    private void onValidateSecurityStatus() {
        String value = mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val.getText().toString();
        mGeneralAndSafetyMeasureLinearLayoutNoofSecurityPerson.setVisibility(View.VISIBLE);
        mGeneralAndSafetyMeasureLinearLayoutMobileNumberofSecurity.setVisibility(View.VISIBLE);

        if (value.equals("Not Available")) {
            mGeneralAndSafetyMeasureLinearLayoutNoofSecurityPerson.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal.setText("");

            mGeneralAndSafetyMeasureLinearLayoutMobileNumberofSecurity.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureEditTextMobileNumberofSecurity.setText("");
        }

        onValidateSecurityCaretakeStatus();
    }

    private void onValidateCaretakerStatus() {
        String value = mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal.getText().toString();
        mGeneralAndSafetyMeasureLinearLayoutMobileNumberofCaretaker.setVisibility(View.VISIBLE);
        mGeneralAndSafetyMeasureLinearLayoutIsSecurityCaretakeristheOwnerofSite.setVisibility(View.VISIBLE);

        if (value.equals("Not Available")) {
            mGeneralAndSafetyMeasureLinearLayoutMobileNumberofCaretaker.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureEditTextMobileNumberofCaretaker.setText("");

            mGeneralAndSafetyMeasureLinearLayoutIsSecurityCaretakeristheOwnerofSite.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal.setText("");
        }

        onValidateSecurityCaretakeStatus();
    }

    private void onValidateSecurityCaretakeStatus() {
        String securityStatus = mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val.getText().toString();
        String caretakerStatus = mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal.getText().toString();
        ;
        mGeneralAndSafetyMeasureLinearLayoutSalaryofSecurityCaretaker.setVisibility(View.VISIBLE);
        mGeneralAndSafetyMeasureLinearLayoutCaretakerSecuritySalaryPaidBy.setVisibility(View.VISIBLE);
        mGeneralAndSafetyMeasureLinearLayoutCaretakerSecurityStayinginSite.setVisibility(View.VISIBLE);

        if ((securityStatus.equals("Not Available")) & caretakerStatus.equals("Not Available")) {
            mGeneralAndSafetyMeasureLinearLayoutSalaryofSecurityCaretaker.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureEditTextSalaryofSecurityCaretaker.setText("");

            mGeneralAndSafetyMeasureLinearLayoutCaretakerSecuritySalaryPaidBy.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal.setText("");

            mGeneralAndSafetyMeasureLinearLayoutCaretakerSecurityStayinginSite.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal.setText("");

            mGeneralAndSafetyMeasureLinearLayoutAgencyNameSalaryPaid.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureEditTextAgencyNameSalaryPaid.setText("");
        }
    }

    private void onValidateSalaryPaidBy() {
        String value = mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal.getText().toString();
        mGeneralAndSafetyMeasureLinearLayoutAgencyNameSalaryPaid.setVisibility(View.VISIBLE);
        if (!value.equals("External Agency")) {
            mGeneralAndSafetyMeasureLinearLayoutAgencyNameSalaryPaid.setVisibility(View.GONE);
            mGeneralAndSafetyMeasureEditTextAgencyNameSalaryPaid.setText("");

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                // startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                if (checkValidationonSubmit("onSubmit") == true) {
                    submitDetails();
                    finish();
                    startActivity(new Intent(this, ACDB_DCDB.class));
                    return true;
                }


        }
        return super.

                onOptionsItemSelected(item);

    }


    private void checkValidation() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                towerDetailsData = hotoTransactionData.getTowerDetailsData();
                if (towerDetailsData.getTowerName().equals("RTT") || towerDetailsData.getTowerName().equals("Mast")) {

                    mGeneralAndSafetyMeasureLinearLayoutSiteBoundaryStatus.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal.setText("");

                    mGeneralAndSafetyMeasureLinearLayoutFencingCompoundWallCondition.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal.setText("");
                }
                powerBackupsDGParentData = hotoTransactionData.getPowerBackupsDGParentData();
                if (powerBackupsDGParentData.getNoOfEngineAlternator().equals("0")) {
                    mGeneralAndSafetyMeasureLinearLayoutDGRoomLock.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewDGRoomLockVal.setText("");

                    mGeneralAndSafetyMeasureLinearLayoutFuelSensor.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewFuelSensorVal.setText("");
                }

                if (hototicket_Selected_SiteType.equals("Outdoor")) {
                    mGeneralAndSafetyMeasureLinearLayoutDoorOpenSensor.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal.setText("");

                    mGeneralAndSafetyMeasureLinearLayoutFireSmokeSensor.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal.setText("");
                }

                if (hotoTransactionData.getSourceOfPower().toString().equals("Non DG")) {
                    mGeneralAndSafetyMeasureLinearLayoutFuelSensor.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureTextViewFuelSensorVal.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidationonSubmit(String methodFlag) {
        String noOfFireExtuinguisher = mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal.getText().toString().trim();
        if (!noOfFireExtuinguisher.isEmpty() && noOfFireExtuinguisher != null) {
            if (Integer.valueOf(noOfFireExtuinguisher) > 0) {
                if ((generalSafetyMeasuresData.size() != Integer.valueOf(noOfFireExtuinguisher) && methodFlag.equals("onSubmit"))) {
                    showToast("Complete the all readings.");
                    return false;
                } else return true;
            } else return true;
        } else return true;
    }

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                generalSafetyMeasuresParentData = hotoTransactionData.getGeneralSafetyMeasuresParentData();
                generalSafetyMeasuresData.addAll(generalSafetyMeasuresParentData.getGeneralSafetyMeasuresData());
                totalCount = Integer.parseInt(generalSafetyMeasuresParentData.getNoOfFireExtuinguisher().isEmpty() || generalSafetyMeasuresParentData.getNoOfFireExtuinguisher() == null ? "0" : generalSafetyMeasuresParentData.getNoOfFireExtuinguisher());
                mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal.setText(generalSafetyMeasuresParentData.getNoOfFireExtuinguisher());

                mGeneralAndSafetyMeasuresEditTextPrevailingSLA.setText(generalSafetyMeasuresParentData.getPrevailingSLA());
                mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal.setText(generalSafetyMeasuresParentData.getSiteBoundaryStatus());
                mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatusVal.setText(generalSafetyMeasuresParentData.getSiteHygieneVegitationStatus());
                mGeneralAndSafetyMeasureTextViewGateLockVal.setText(generalSafetyMeasuresParentData.getGateLock());
                mGeneralAndSafetyMeasureTextViewDGRoomLockVal.setText(generalSafetyMeasuresParentData.getDgRoomLock());
                mGeneralAndSafetyMeasureTextViewFireBucketVal.setText(generalSafetyMeasuresParentData.getFireBucket());
                mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val.setText(generalSafetyMeasuresParentData.getSecurityStatus());
                mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal.setText(generalSafetyMeasuresParentData.getNoofSecurityPerson());
                mGeneralAndSafetyMeasureEditTextMobileNumberofSecurity.setText(generalSafetyMeasuresParentData.getMobileNumberofSecurity());
                mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal.setText(generalSafetyMeasuresParentData.getCaretakerStatusUpOnEmergency());
                mGeneralAndSafetyMeasureEditTextMobileNumberofCaretaker.setText(generalSafetyMeasuresParentData.getMobileNumberofCaretaker());
                mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal.setText(generalSafetyMeasuresParentData.getIsSecurityCaretakeristheOwnerofSite());
                mGeneralAndSafetyMeasureEditTextSalaryofSecurityCaretaker.setText(generalSafetyMeasuresParentData.getSalaryofSecurityCaretaker());
                mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal.setText(generalSafetyMeasuresParentData.getCaretakerSecuritySalaryPaidBy());
                mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal.setText(generalSafetyMeasuresParentData.getCaretakerSecurityStayinginSite());
                mGeneralAndSafetyMeasureTextViewNumberofEarthPitVal.setText(generalSafetyMeasuresParentData.getNumberofEarthPit());
                mGeneralAndSafetyMeasureTextViewLightningArresterStatusVal.setText(generalSafetyMeasuresParentData.getLightningArresterStatus());
                mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal.setText(generalSafetyMeasuresParentData.getFencingCompoundWallCondition());
                mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailableVal.setText(generalSafetyMeasuresParentData.getNumberoffreeODPaltformAvailable());
                mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatusVal.setText(generalSafetyMeasuresParentData.getAlarmMultipluxerStatus());
                mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal.setText(generalSafetyMeasuresParentData.getDoorOpenSensor());
                mGeneralAndSafetyMeasureTextViewFuelSensorVal.setText(generalSafetyMeasuresParentData.getFuelSensor());
                mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal.setText(generalSafetyMeasuresParentData.getFireSmokeSensor());
                mGeneralAndSafetyMeasureEditTextAgencyNameSalaryPaid.setText(generalSafetyMeasuresParentData.getAgencyName());
                mGeneralAndSafetyMeasureTextViewSiteAccessStatusVal.setText(generalSafetyMeasuresParentData.getSiteAccessStatus());
                if (generalSafetyMeasuresData != null && generalSafetyMeasuresData.size() > 0) {
                    linearLayout_container.setVisibility(View.VISIBLE);
                    mGeneralAndSafetyMeasureTextViewNumber.setText("Reading: #1");

                    mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.setText(generalSafetyMeasuresData.get(index).getFireExtuinguisherType());
                    mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.setText(generalSafetyMeasuresData.get(index).getFireExtuinguisherExpiryDate());

                    mGeneralAndSafetyMeasureButtonPreviousReading.setVisibility(View.GONE);
                    mGeneralAndSafetyMeasureButtonNextReading.setVisibility(View.VISIBLE);

                    //if (generalSafetyMeasuresData.size() > 1) {
                    if (totalCount > 1) {
                        mGeneralAndSafetyMeasureButtonNextReading.setText("Next Reading");
                    } else {
                        mGeneralAndSafetyMeasureButtonNextReading.setText("Finish");
                    }

                }

            } else {
                Toast.makeText(GeneralAndSafetyMeasures.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            // hotoTransactionData.setTicketNo(ticketId);

            String prevailingSLA = mGeneralAndSafetyMeasuresEditTextPrevailingSLA.getText().toString().trim();
            String siteBoundaryStatus = mGeneralAndSafetyMeasureTextViewSiteBoundaryStatusVal.getText().toString().trim();
            String siteHygieneVegitationStatus = mGeneralAndSafetyMeasureTextViewSiteHygieneVegitationStatusVal.getText().toString().trim();
            String gateLock = mGeneralAndSafetyMeasureTextViewGateLockVal.getText().toString().trim();
            String dgRoomLock = mGeneralAndSafetyMeasureTextViewDGRoomLockVal.getText().toString().trim();
            String fireExtuinguisher = "";//mGeneralAndSafetyMeasureTextViewFireExtuinguisherVal.getText().toString().trim();
            String noOfFireExtuinguisher = mGeneralAndSafetyMeasureTextViewNoOfFireExtuinguisherVal.getText().toString().trim();
            /*String fireExtuinguisherType = mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.getText().toString().trim();
            String fireExtuinguisherExpiryDate = mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.getText().toString().trim();*/
            String fireBucket = mGeneralAndSafetyMeasureTextViewFireBucketVal.getText().toString().trim();
            String securityStatus = mGeneralAndSafetyMeasureTextViewSecurityStatus24x7Val.getText().toString().trim();
            String noofSecurityPerson = mGeneralAndSafetyMeasureTextViewNoofSecurityPersonVal.getText().toString().trim();
            String mobileNumberofSecurity = mGeneralAndSafetyMeasureEditTextMobileNumberofSecurity.getText().toString().trim();
            String caretakerStatusUpOnEmergency = mGeneralAndSafetyMeasureTextViewCaretakerStatusUpOnEmergencyVal.getText().toString().trim();
            String mobileNumberofCaretaker = mGeneralAndSafetyMeasureEditTextMobileNumberofCaretaker.getText().toString().trim();
            String isSecurityCaretakeristheOwnerofSite = mGeneralAndSafetyMeasureTextViewIsSecurityCaretakeristheOwnerofSiteVal.getText().toString().trim();
            String salaryofSecurityCaretaker = mGeneralAndSafetyMeasureEditTextSalaryofSecurityCaretaker.getText().toString().trim();
            String caretakerSecuritySalaryPaidBy = mGeneralAndSafetyMeasureTextViewCaretakerSecuritySalaryPaidByVal.getText().toString().trim();
            String caretakerSecurityStayinginSite = mGeneralAndSafetyMeasureTextViewCaretakerSecurityStayinginSiteVal.getText().toString().trim();
            String numberofEarthPit = mGeneralAndSafetyMeasureTextViewNumberofEarthPitVal.getText().toString().trim();
            String lightningArresterStatus = mGeneralAndSafetyMeasureTextViewLightningArresterStatusVal.getText().toString().trim();
            String fencingCompoundWallCondition = mGeneralAndSafetyMeasureTextViewFencingCompoundWallConditionVal.getText().toString().trim();
            String numberoffreeODPaltformAvailable = mGeneralAndSafetyMeasureTextViewNumberoffreeODPaltformAvailableVal.getText().toString().trim();
            String alarmMultipluxerStatus = mGeneralAndSafetyMeasureTextViewAlarmMultipluxerStatusVal.getText().toString().trim();
            String doorOpenSensor = mGeneralAndSafetyMeasureTextViewDoorOpenSensorVal.getText().toString().trim();
            String fuelSensor = mGeneralAndSafetyMeasureTextViewFuelSensorVal.getText().toString().trim();
            String fireSmokeSensor = mGeneralAndSafetyMeasureTextViewFireSmokeSensorVal.getText().toString().trim();
            String str_AgencyName = mGeneralAndSafetyMeasureEditTextAgencyNameSalaryPaid.getText().toString();
            String siteAccessStatus = mGeneralAndSafetyMeasureTextViewSiteAccessStatusVal.getText().toString();

            generalSafetyMeasuresParentData = new GeneralSafetyMeasuresParentData(prevailingSLA, siteBoundaryStatus, siteHygieneVegitationStatus, gateLock, dgRoomLock, fireExtuinguisher, noOfFireExtuinguisher, fireBucket, securityStatus, noofSecurityPerson, mobileNumberofSecurity, caretakerStatusUpOnEmergency, mobileNumberofCaretaker, isSecurityCaretakeristheOwnerofSite, salaryofSecurityCaretaker, caretakerSecuritySalaryPaidBy, caretakerSecurityStayinginSite, numberofEarthPit, lightningArresterStatus, fencingCompoundWallCondition, numberoffreeODPaltformAvailable, alarmMultipluxerStatus, doorOpenSensor, fuelSensor, fireSmokeSensor, str_AgencyName, siteAccessStatus, generalSafetyMeasuresData);
            hotoTransactionData.setGeneralSafetyMeasuresParentData(generalSafetyMeasuresParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkValidtionForArrayFields() {
       /* String fireExtuinguisherType = mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.getText().toString().trim();
        String fireExtuinguisherExpiryDate = mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.getText().toString().trim();

        if (fireExtuinguisherType.isEmpty() || fireExtuinguisherType == null) {
            showToast("Select Fire Extuinguisher Type");
            return false;
        } else if (fireExtuinguisherExpiryDate.isEmpty() || fireExtuinguisherExpiryDate == null) {
            showToast("Select Fire Extuinguisher Expiry Date");
            return false;
        } else*/
        return true;
    }

    private void saveRecords(int pos) {
        String fireExtuinguisherType = mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.getText().toString().trim();
        String fireExtuinguisherExpiryDate = mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.getText().toString().trim();

        GeneralSafetyMeasuresData obj_generalSafetyMeasuresData = new GeneralSafetyMeasuresData(fireExtuinguisherType, fireExtuinguisherExpiryDate);
        if (generalSafetyMeasuresData.size() > 0) {
            if (pos == generalSafetyMeasuresData.size()) {
                generalSafetyMeasuresData.add(obj_generalSafetyMeasuresData);
            } else if (pos < generalSafetyMeasuresData.size()) {
                generalSafetyMeasuresData.set(pos, obj_generalSafetyMeasuresData);
            }
        } else {
            generalSafetyMeasuresData.add(obj_generalSafetyMeasuresData);
        }
    }

    private void displayRecords(int pos) {
        if (generalSafetyMeasuresData.size() > 0 && pos < generalSafetyMeasuresData.size()) {

            mGeneralAndSafetyMeasureTextViewNumber.setText("Reading: #" + (pos + 1));

            mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.setText(generalSafetyMeasuresData.get(pos).getFireExtuinguisherType());
            mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.setText(generalSafetyMeasuresData.get(pos).getFireExtuinguisherExpiryDate());

            mGeneralAndSafetyMeasureButtonPreviousReading.setVisibility(View.VISIBLE);
            mGeneralAndSafetyMeasureButtonNextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            mGeneralAndSafetyMeasureButtonPreviousReading.setVisibility(View.VISIBLE);
            mGeneralAndSafetyMeasureButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            mGeneralAndSafetyMeasureButtonPreviousReading.setVisibility(View.VISIBLE);
            mGeneralAndSafetyMeasureButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mGeneralAndSafetyMeasureButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                mGeneralAndSafetyMeasureButtonNextReading.setText("Finish");
            } else {
                mGeneralAndSafetyMeasureButtonNextReading.setText("Next Reading");
            }
        }
    }

    public void clearFields(int indexPos) {
        mGeneralAndSafetyMeasureTextViewNumber.setText("Reading: #" + (indexPos + 1));

        mGeneralAndSafetyMeasureTextViewFireExtuinguisherTypeVal.setText("");
        mGeneralAndSafetyMeasureEditTextFireExtuinguisherExpiryDate.setText("");

        str_FireExtuinguisherType = "";
    }
}
