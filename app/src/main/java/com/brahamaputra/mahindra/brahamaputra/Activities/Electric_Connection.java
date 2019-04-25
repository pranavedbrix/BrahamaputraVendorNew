package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.brahamaputra.mahindra.brahamaputra.Data.ElectricConnectionData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_nameOfSupplyCompany;


public class Electric_Connection extends BaseActivity {

    private TextView mElectricConnectionTextViewTypeOfElectricConnection;
    private TextView mElectricConnectionTextViewTypeOfElectricConnectionVal;
    private TextView mElectricConnectionTextViewTariff;
    private TextView mElectricConnectionTextViewTariffVal;
    private TextView mElectricConnectionTextViewSanctionedLoadKVA;
    private EditText mElectricConnectionEditTextSanctionedLoadKVA;
    private TextView mElectricConnectionTextViewExistingLoadAtSiteKVA;
    private EditText mElectricConnectionEditTextExistingLoadAtSiteKVA;
    private EditText mElectricConnectionEditTextSecurityAmountPaidToTheCompany;
    private TextView mElectricConnectionTextViewNameOfSupplyCompany;
    private EditText mElectricConnectionTextViewNameOfSupplyCompanyVal;
    private TextView mElectricConnectionTextViewCopyOfElectricBills;
    private TextView mElectricConnectionTextViewCopyOfElectricBillsVal;
    private TextView mElectricConnectionTextViewNumberOfCompoundLights;
    private EditText mElectricConnectionEditTextNumberOfCompoundLights;
    private TextView mElectricConnectionTextViewEbMeterReadingInKWh;
    private EditText mElectricConnectionEditTextEbMeterReadingInKWh;
    private TextView mElectricConnectionTextViewEbSupplier;
    private TextView mElectricConnectionTextViewEbSupplierVal;
    private TextView mElectricConnectionTextViewEbCostPerUnitForSharedConnection;
    private EditText mElectricConnectionEditTextEbCostPerUnitForSharedConnection;
    private TextView mElectricConnectionTextViewEbStatus;
    private TextView mElectricConnectionTextViewEbStatusVal;
    private TextView mElectricConnectionTextViewTransformerWorkingCondition;
    private TextView mElectricConnectionTextViewTransformerWorkingConditionVal;
    private TextView mElectricConnectionTextViewTransformerCapacityInKva;
    private EditText mElectricConnectionEditTextTransformerCapacityInKva;
    private TextView mElectricConnectionTextViewEbMeterBoxStatus;
    private TextView mElectricConnectionTextViewEbMeterBoxStatusVal;
    private TextView mElectricConnectionTextViewSectionName;
    private EditText mElectricConnectionEditTextSectionName;
    private TextView mElectricConnectionTextViewSectionNumber;
    private EditText mElectricConnectionEditTextSectionNumber;
    private TextView mElectricConnectionTextViewConsumerNumber;
    private EditText mElectricConnectionEditTextConsumerNumber;
    private TextView mElectricConnectionTextViewEbMeterWorkingStatus;
    private TextView mElectricConnectionTextViewEbMeterWorkingStatusVal;
    private TextView mElectricConnectionTextViewEbMeterSerialNumber;
    private EditText mElectricConnectionEditTextEbMeterSerialNumber;
    private TextView mElectricConnectionTextViewTypeOfPayment;
    private TextView mElectricConnectionTextViewTypeOfPaymentVal;
    private TextView mElectricConnectionTextViewEbPaymentSchedule;
    private TextView mElectricConnectionTextViewEbPaymentScheduleVal;
    private TextView mElectricConnectionTextViewSafetyFuseUnit;
    private TextView mElectricConnectionTextViewSafetyFuseUnitVal;
    private TextView mElectricConnectionTextViewKitKatClayFuseStatus;
    private TextView mElectricConnectionTextViewKitKatClayFuseStatusVal;
    private TextView mElectricConnectionTextViewEbNeutralEarthing;
    private TextView mElectricConnectionTextViewEbNeutralEarthingVal;
    private TextView mElectricConnectionTextViewAverageEbAvailabilityPerDay;
    private EditText mElectricConnectionEditTextAverageEbAvailabilityPerDay;
    private TextView mElectricConnectionTextViewScheduledPowerCutInHrs;
    private EditText mElectricConnectionEditTextScheduledPowerCutInHrs;
    private TextView mElectricConnectionTextViewEbBillDate;
    //private EditText mElectricConnectionEditTextEbBillDate;
    private TextView mElectricConnectionTextViewEbBillDateVal;


    //private TextView mElectricConnectionTextViewSapVendorCode;
    //private EditText mElectricConnectionEditTextSapVendorCode;

    private TextView mElectricConnectionTextViewTypeModeOfPayment;
    private TextView mElectricConnectionTextViewTypeModeOfPayment_Val;
    private TextView mElectricConnectionTextViewBankIfscCode;
    private EditText mElectricConnectionEditTextBankIfscCode;
    private TextView mElectricConnectionTextViewBankAccountNo;
    private EditText mElectricConnectionEditTextBankAccountNo;

    LinearLayout mElectricConnectionLinearLayoutEbMeterReadingInKWh;
    LinearLayout mElectricConnectionLinearLayoutEbSupplier;
    LinearLayout mElectricConnectionLinearLayoutEbCostPerUnitForSharedConnection;
    LinearLayout mElectricConnectionLinearLayoutEbStatus;
    LinearLayout mElectricConnectionLinearLayoutTransformerWorkingCondition;
    LinearLayout mElectricConnectionLinearLayoutTransformerCapacityInKva;
    LinearLayout mElectricConnectionLinearLayoutEbMeterBoxStatus;
    LinearLayout mElectricConnectionLinearLayoutSectionName;
    LinearLayout mElectricConnectionLinearLayoutSectionNumber;
    LinearLayout mElectricConnectionLinearLayoutConsumerNumber;
    LinearLayout mElectricConnectionLinearLayoutEbMeterWorkingStatus;
    LinearLayout mElectricConnectionLinearLayoutEbMeterSerialNumber;
    LinearLayout mElectricConnectionLinearLayoutTypeOfPayment;
    LinearLayout mElectricConnectionLinearLayoutEbPaymentSchedule;
    LinearLayout mElectricConnectionLinearLayoutSafetyFuseUnit;
    LinearLayout mElectricConnectionLinearLayoutKitKatClayFuseStatus;
    LinearLayout mElectricConnectionLinearLayoutEbNeutralEarthing;
    LinearLayout mElectricConnectionLinearLayoutAverageEbAvailabilityPerDay;
    LinearLayout mElectricConnectionLinearLayoutScheduledPowerCutInHrs;
    LinearLayout mElectricConnectionLinearLayoutEbBillDate;

    DecimalConversion decimalConversion;
    private static final String TAG = Electric_Connection.class.getSimpleName();

    String str_typeOfElectricConnection;
    String str_tariff;
    String str_copyOfElectricBills;
    String str_ebSupplier;
    String str_ebStatus;
    String str_transformerWorkingCondition;
    String str_ebMeterBoxStatus;
    String str_ebMeterWorkingStatus;
    String str_typeOfPayment;
    String str_ebPaymentSchedule;
    String str_safetyFuseUnit;
    String str_kitKatClayFuseStatus;
    String str_ebNeutralEarthing;
    String str_typeModeOfPayment;
    String str_ebBillDate;


    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ElectricConnectionData electricConnectionData;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_connection);
        this.setTitle("Electric Connection");
        decimalConversion = new DecimalConversion();
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();

        sessionManager = new SessionManager(Electric_Connection.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Electric_Connection.this, userId, ticketName);
        setInputDetails();
        checkValidation();


        mElectricConnectionEditTextAverageEbAvailabilityPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Electric_Connection.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHour1 = (selectedHour >= 10) ? Integer.toString(selectedHour) : String.format("0%s", Integer.toString(selectedHour));
                        String selectedMinute1 = (selectedMinute >= 10) ? Integer.toString(selectedMinute) : String.format("0%s", Integer.toString(selectedMinute));
                        mElectricConnectionEditTextAverageEbAvailabilityPerDay.setText(selectedHour1 + ":" + selectedMinute1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time For Average Eb Availability");
                mTimePicker.show();

            }
        });

        mElectricConnectionEditTextScheduledPowerCutInHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Electric_Connection.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHour1 = (selectedHour >= 10) ? Integer.toString(selectedHour) : String.format("0%s", Integer.toString(selectedHour));
                        String selectedMinute1 = (selectedMinute >= 10) ? Integer.toString(selectedMinute) : String.format("0%s", Integer.toString(selectedMinute));
                        mElectricConnectionEditTextScheduledPowerCutInHrs.setText(selectedHour1 + ":" + selectedMinute1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time For Scheduled Power Cut");
                mTimePicker.show();

            }
        });

        mElectricConnectionEditTextSanctionedLoadKVA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mElectricConnectionEditTextExistingLoadAtSiteKVA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mElectricConnectionEditTextEbMeterReadingInKWh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mElectricConnectionEditTextTransformerCapacityInKva.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

        if (hototicket_nameOfSupplyCompany.isEmpty() || hototicket_nameOfSupplyCompany.length() < 0) {
            //
        } else {
            mElectricConnectionTextViewNameOfSupplyCompanyVal.setText(hototicket_nameOfSupplyCompany);
            mElectricConnectionTextViewNameOfSupplyCompanyVal.setKeyListener(null);
        }

    }

    private void assignViews() {
        mElectricConnectionTextViewTypeOfElectricConnection = (TextView) findViewById(R.id.electricConnection_textView_typeOfElectricConnection);
        mElectricConnectionTextViewTypeOfElectricConnectionVal = (TextView) findViewById(R.id.electricConnection_textView_typeOfElectricConnection_val);
        mElectricConnectionTextViewTariff = (TextView) findViewById(R.id.electricConnection_textView_tariff);
        mElectricConnectionTextViewTariffVal = (TextView) findViewById(R.id.electricConnection_textView_tariff_val);
        mElectricConnectionTextViewSanctionedLoadKVA = (TextView) findViewById(R.id.electricConnection_textView_sanctionedLoadKVA);
        mElectricConnectionEditTextSanctionedLoadKVA = (EditText) findViewById(R.id.electricConnection_editText_sanctionedLoadKVA);
        mElectricConnectionTextViewExistingLoadAtSiteKVA = (TextView) findViewById(R.id.electricConnection_textView_existingLoadAtSiteKVA);
        mElectricConnectionEditTextExistingLoadAtSiteKVA = (EditText) findViewById(R.id.electricConnection_editText_existingLoadAtSiteKVA);

        mElectricConnectionEditTextSecurityAmountPaidToTheCompany = (EditText) findViewById(R.id.electricConnection_editText_SecurityAmountPaidToTheCompany);
        mElectricConnectionTextViewNameOfSupplyCompany = (TextView) findViewById(R.id.electricConnection_textView_nameOfSupplyCompany);

        mElectricConnectionTextViewNameOfSupplyCompanyVal = (EditText) findViewById(R.id.electricConnection_textView_nameOfSupplyCompany_val);
        mElectricConnectionTextViewCopyOfElectricBills = (TextView) findViewById(R.id.electricConnection_textView_copyOfElectricBills);
        mElectricConnectionTextViewCopyOfElectricBillsVal = (TextView) findViewById(R.id.electricConnection_textView_copyOfElectricBills_val);
        mElectricConnectionTextViewNumberOfCompoundLights = (TextView) findViewById(R.id.electricConnection_textView_numberOfCompoundLights);
        mElectricConnectionEditTextNumberOfCompoundLights = (EditText) findViewById(R.id.electricConnection_editText_numberOfCompoundLights);
        mElectricConnectionTextViewEbMeterReadingInKWh = (TextView) findViewById(R.id.electricConnection_textView_ebMeterReadingInKWh);
        mElectricConnectionEditTextEbMeterReadingInKWh = (EditText) findViewById(R.id.electricConnection_editText_ebMeterReadingInKWh);
        mElectricConnectionTextViewEbSupplier = (TextView) findViewById(R.id.electricConnection_textView_ebSupplier);
        mElectricConnectionTextViewEbSupplierVal = (TextView) findViewById(R.id.electricConnection_textView_ebSupplier_val);
        mElectricConnectionTextViewEbCostPerUnitForSharedConnection = (TextView) findViewById(R.id.electricConnection_textView_ebCostPerUnitForSharedConnection);
        mElectricConnectionEditTextEbCostPerUnitForSharedConnection = (EditText) findViewById(R.id.electricConnection_editText_ebCostPerUnitForSharedConnection);
        mElectricConnectionTextViewEbStatus = (TextView) findViewById(R.id.electricConnection_textView_ebStatus);
        mElectricConnectionTextViewEbStatusVal = (TextView) findViewById(R.id.electricConnection_textView_ebStatus_val);
        mElectricConnectionTextViewTransformerWorkingCondition = (TextView) findViewById(R.id.electricConnection_textView_transformerWorkingCondition);
        mElectricConnectionTextViewTransformerWorkingConditionVal = (TextView) findViewById(R.id.electricConnection_textView_transformerWorkingCondition_val);
        mElectricConnectionTextViewTransformerCapacityInKva = (TextView) findViewById(R.id.electricConnection_textView_transformerCapacityInKva);
        mElectricConnectionEditTextTransformerCapacityInKva = (EditText) findViewById(R.id.electricConnection_editText_transformerCapacityInKva);
        mElectricConnectionTextViewEbMeterBoxStatus = (TextView) findViewById(R.id.electricConnection_textView_ebMeterBoxStatus);
        mElectricConnectionTextViewEbMeterBoxStatusVal = (TextView) findViewById(R.id.electricConnection_textView_ebMeterBoxStatus_val);
        mElectricConnectionTextViewSectionName = (TextView) findViewById(R.id.electricConnection_textView_sectionName);
        mElectricConnectionEditTextSectionName = (EditText) findViewById(R.id.electricConnection_editText_sectionName);
        mElectricConnectionTextViewSectionNumber = (TextView) findViewById(R.id.electricConnection_textView_sectionNumber);
        mElectricConnectionEditTextSectionNumber = (EditText) findViewById(R.id.electricConnection_editText_sectionNumber);
        mElectricConnectionTextViewConsumerNumber = (TextView) findViewById(R.id.electricConnection_textView_consumerNumber);
        mElectricConnectionEditTextConsumerNumber = (EditText) findViewById(R.id.electricConnection_editText_consumerNumber);
        mElectricConnectionTextViewEbMeterWorkingStatus = (TextView) findViewById(R.id.electricConnection_textView_ebMeterWorkingStatus);
        mElectricConnectionTextViewEbMeterWorkingStatusVal = (TextView) findViewById(R.id.electricConnection_textView_ebMeterWorkingStatus_val);
        mElectricConnectionTextViewEbMeterSerialNumber = (TextView) findViewById(R.id.electricConnection_textView_ebMeterSerialNumber);
        mElectricConnectionEditTextEbMeterSerialNumber = (EditText) findViewById(R.id.electricConnection_editText_ebMeterSerialNumber);
        mElectricConnectionTextViewTypeOfPayment = (TextView) findViewById(R.id.electricConnection_textView_typeOfPayment);
        mElectricConnectionTextViewTypeOfPaymentVal = (TextView) findViewById(R.id.electricConnection_textView_typeOfPayment_val);
        mElectricConnectionTextViewEbPaymentSchedule = (TextView) findViewById(R.id.electricConnection_textView_ebPaymentSchedule);
        mElectricConnectionTextViewEbPaymentScheduleVal = (TextView) findViewById(R.id.electricConnection_textView_ebPaymentSchedule_val);
        mElectricConnectionTextViewSafetyFuseUnit = (TextView) findViewById(R.id.electricConnection_textView_safetyFuseUnit);
        mElectricConnectionTextViewSafetyFuseUnitVal = (TextView) findViewById(R.id.electricConnection_textView_safetyFuseUnit_val);
        mElectricConnectionTextViewKitKatClayFuseStatus = (TextView) findViewById(R.id.electricConnection_textView_kitKatClayFuseStatus);
        mElectricConnectionTextViewKitKatClayFuseStatusVal = (TextView) findViewById(R.id.electricConnection_textView_kitKatClayFuseStatus_val);
        mElectricConnectionTextViewEbNeutralEarthing = (TextView) findViewById(R.id.electricConnection_textView_ebNeutralEarthing);
        mElectricConnectionTextViewEbNeutralEarthingVal = (TextView) findViewById(R.id.electricConnection_textView_ebNeutralEarthing_val);
        mElectricConnectionTextViewAverageEbAvailabilityPerDay = (TextView) findViewById(R.id.electricConnection_textView_averageEbAvailabilityPerDay);
        mElectricConnectionEditTextAverageEbAvailabilityPerDay = (EditText) findViewById(R.id.electricConnection_editText_averageEbAvailabilityPerDay);
        mElectricConnectionTextViewScheduledPowerCutInHrs = (TextView) findViewById(R.id.electricConnection_textView_scheduledPowerCutInHrs);
        mElectricConnectionEditTextScheduledPowerCutInHrs = (EditText) findViewById(R.id.electricConnection_editText_scheduledPowerCutInHrs);
        mElectricConnectionTextViewEbBillDate = (TextView) findViewById(R.id.electricConnection_textView_ebBillDate);
        //mElectricConnectionEditTextEbBillDate = (EditText) findViewById(R.id.electricConnection_editText_ebBillDate);
        mElectricConnectionTextViewEbBillDateVal = (TextView) findViewById(R.id.electricConnection_textView_ebBillDate_Val);
        //mElectricConnectionTextViewSapVendorCode = (TextView) findViewById(R.id.electricConnection_textView_sapVendorCode);
        //mElectricConnectionEditTextSapVendorCode = (EditText) findViewById(R.id.electricConnection_editText_sapVendorCode);

        mElectricConnectionTextViewTypeModeOfPayment = (TextView) findViewById(R.id.electricConnection_textView_typeModeOfPayment);
        mElectricConnectionTextViewTypeModeOfPayment_Val = (TextView) findViewById(R.id.electricConnection_textView_typeModeOfPayment_Val);
        mElectricConnectionTextViewBankIfscCode = (TextView) findViewById(R.id.electricConnection_textView_bankIfscCode);
        mElectricConnectionEditTextBankIfscCode = (EditText) findViewById(R.id.electricConnection_editText_bankIfscCode);
        mElectricConnectionTextViewBankAccountNo = (TextView) findViewById(R.id.electricConnection_textView_bankAccountNo);
        mElectricConnectionEditTextBankAccountNo = (EditText) findViewById(R.id.electricConnection_editText_bankAccountNo);

        mElectricConnectionLinearLayoutEbMeterReadingInKWh = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebMeterReadingInKWh);
        mElectricConnectionLinearLayoutEbSupplier = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebSupplier);
        mElectricConnectionLinearLayoutEbCostPerUnitForSharedConnection = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebCostPerUnitForSharedConnection);
        mElectricConnectionLinearLayoutEbStatus = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebStatus);
        mElectricConnectionLinearLayoutTransformerWorkingCondition = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_transformerWorkingCondition);
        mElectricConnectionLinearLayoutTransformerCapacityInKva = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_transformerCapacityInKva);
        mElectricConnectionLinearLayoutEbMeterBoxStatus = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebMeterBoxStatus);
        mElectricConnectionLinearLayoutSectionName = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_sectionName);
        mElectricConnectionLinearLayoutSectionNumber = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_sectionNumber);
        mElectricConnectionLinearLayoutConsumerNumber = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_consumerNumber);
        mElectricConnectionLinearLayoutEbMeterWorkingStatus = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebMeterWorkingStatus);
        mElectricConnectionLinearLayoutEbMeterSerialNumber = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebMeterSerialNumber);
        mElectricConnectionLinearLayoutTypeOfPayment = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_typeOfPayment);
        mElectricConnectionLinearLayoutEbPaymentSchedule = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebPaymentSchedule);
        mElectricConnectionLinearLayoutSafetyFuseUnit = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_safetyFuseUnit);
        mElectricConnectionLinearLayoutKitKatClayFuseStatus = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_kitKatClayFuseStatus);
        mElectricConnectionLinearLayoutEbNeutralEarthing = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebNeutralEarthing);
        mElectricConnectionLinearLayoutAverageEbAvailabilityPerDay = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_averageEbAvailabilityPerDay);
        mElectricConnectionLinearLayoutScheduledPowerCutInHrs = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_scheduledPowerCutInHrs);
        mElectricConnectionLinearLayoutEbBillDate = (LinearLayout) findViewById(R.id.electricConnection_linearLayout_ebBillDate);

        mElectricConnectionEditTextSanctionedLoadKVA.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mElectricConnectionEditTextExistingLoadAtSiteKVA.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mElectricConnectionEditTextEbMeterReadingInKWh.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mElectricConnectionEditTextTransformerCapacityInKva.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


    }

    public void DecimalFormatConversion() {
        mElectricConnectionEditTextSanctionedLoadKVA.setText(decimalConversion.convertDecimal(mElectricConnectionEditTextSanctionedLoadKVA.getText().toString()));
        mElectricConnectionEditTextExistingLoadAtSiteKVA.setText(decimalConversion.convertDecimal(mElectricConnectionEditTextExistingLoadAtSiteKVA.getText().toString()));
        mElectricConnectionEditTextEbMeterReadingInKWh.setText(decimalConversion.convertDecimal(mElectricConnectionEditTextEbMeterReadingInKWh.getText().toString()));
        mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setText(decimalConversion.convertDecimal(mElectricConnectionEditTextEbCostPerUnitForSharedConnection.getText().toString()));
        mElectricConnectionEditTextTransformerCapacityInKva.setText(decimalConversion.convertDecimal(mElectricConnectionEditTextTransformerCapacityInKva.getText().toString()));
    }

    private void initCombo() {
        mElectricConnectionTextViewTypeOfElectricConnectionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_typrOfElectricConnection))),
                        "Type of Electric Connection",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfElectricConnection = item.get(position);
                        mElectricConnectionTextViewTypeOfElectricConnectionVal.setText(str_typeOfElectricConnection);
                    }
                });
            }
        });

        mElectricConnectionTextViewTariffVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_tariff))),
                        "Tariff",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_tariff = item.get(position);
                        mElectricConnectionTextViewTariffVal.setText(str_tariff);
                    }
                });
            }
        });




        /*mElectricConnectionTextViewNameOfSupplyCompanyVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_nameOfSupplyCompany))),
                        "Name of the supply company",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_nameOfSupplyCompany = item.get(position);
                        mElectricConnectionTextViewNameOfSupplyCompanyVal.setText(str_nameOfSupplyCompany);
                    }
                });
            }
        });*/


        mElectricConnectionTextViewCopyOfElectricBillsVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_copyOfElectricBills))),
                        "Copy of the electric bills",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_copyOfElectricBills = item.get(position);
                        mElectricConnectionTextViewCopyOfElectricBillsVal.setText(str_copyOfElectricBills);
                    }
                });
            }
        });

        mElectricConnectionTextViewEbSupplierVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebSupplier))),
                        "EB Supplier",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebSupplier = item.get(position);
                        mElectricConnectionTextViewEbSupplierVal.setText(str_ebSupplier);
                        visibilityOfEbCostPerUnitOnEbSupplierSelection(str_ebSupplier);
                    }
                });
            }
        });

        mElectricConnectionTextViewEbStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebStatus))),
                        "EB Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebStatus = item.get(position);
                        mElectricConnectionTextViewEbStatusVal.setText(str_ebStatus);
                    }
                });
            }
        });

        mElectricConnectionTextViewTransformerWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_transformerWorkingCondition))),
                        "Transformer Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_transformerWorkingCondition = item.get(position);
                        mElectricConnectionTextViewTransformerWorkingConditionVal.setText(str_transformerWorkingCondition);
                    }
                });
            }
        });

        mElectricConnectionTextViewEbMeterBoxStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebMeterBoxStatus))),
                        "EB Meter Box Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebMeterBoxStatus = item.get(position);
                        mElectricConnectionTextViewEbMeterBoxStatusVal.setText(str_ebMeterBoxStatus);
                    }
                });
            }
        });

        mElectricConnectionTextViewEbMeterWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebMeterWorkingStatus))),
                        "EB Meter Working Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebMeterWorkingStatus = item.get(position);
                        mElectricConnectionTextViewEbMeterWorkingStatusVal.setText(str_ebMeterWorkingStatus);
                    }
                });
            }
        });

        mElectricConnectionTextViewTypeOfPaymentVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_typeOfPayment))),
                        "Type Of Payment",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfPayment = item.get(position);
                        mElectricConnectionTextViewTypeOfPaymentVal.setText(str_typeOfPayment);
                        visibilityOfPaymentScheduleOnTypeOfPaymentSelection(str_typeOfPayment);
                    }
                });
            }
        });

        mElectricConnectionTextViewEbPaymentScheduleVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebPaymentSchedule))),
                        "EB Payment Schedule",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebPaymentSchedule = item.get(position);
                        mElectricConnectionTextViewEbPaymentScheduleVal.setText(str_ebPaymentSchedule);
                    }
                });
            }
        });

        mElectricConnectionTextViewSafetyFuseUnitVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_safetyFuseUnit))),
                        "Safety Fuse Unit (SFU)",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_safetyFuseUnit = item.get(position);
                        mElectricConnectionTextViewSafetyFuseUnitVal.setText(str_safetyFuseUnit);
                    }
                });
            }
        });

        mElectricConnectionTextViewKitKatClayFuseStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_kitKatClayFuseStatus))),
                        "KIT-KAT/Clay Fuse Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_kitKatClayFuseStatus = item.get(position);
                        mElectricConnectionTextViewKitKatClayFuseStatusVal.setText(str_kitKatClayFuseStatus);
                    }
                });
            }
        });


        mElectricConnectionTextViewEbNeutralEarthingVal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebNeutralEarthing))),
                        "EB Neutral Earthing",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebNeutralEarthing = item.get(position);
                        mElectricConnectionTextViewEbNeutralEarthingVal.setText(str_ebNeutralEarthing);
                    }
                });
            }
        });

        mElectricConnectionTextViewTypeModeOfPayment_Val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_typeModeOfPayment))),
                        "Type|Mode Of Payment",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeModeOfPayment = item.get(position);
                        mElectricConnectionTextViewTypeModeOfPayment_Val.setText(str_typeModeOfPayment);
                    }
                });
            }
        });

        mElectricConnectionTextViewEbBillDateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Electric_Connection.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebBillDate))),
                        "EB Bill Date",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebBillDate = item.get(position);
                        mElectricConnectionTextViewEbBillDateVal.setText(str_ebBillDate);
                    }
                });
            }
        });

    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                electricConnectionData = hotoTransactionData.getElectricConnectionData();

                mElectricConnectionTextViewTypeOfElectricConnectionVal.setText(electricConnectionData.getElectricConnectionType());
                mElectricConnectionTextViewTariffVal.setText(electricConnectionData.getConnectionTariff());
                mElectricConnectionEditTextSanctionedLoadKVA.setText(electricConnectionData.getSanctionLoad());
                mElectricConnectionEditTextSecurityAmountPaidToTheCompany.setText(electricConnectionData.getSecurityAmountPaid());
                mElectricConnectionEditTextExistingLoadAtSiteKVA.setText(electricConnectionData.getExistingLoadAtSite());
                mElectricConnectionTextViewNameOfSupplyCompanyVal.setText(electricConnectionData.getNameSupplyCompany());
                mElectricConnectionTextViewCopyOfElectricBillsVal.setText(electricConnectionData.getElectricBillCopyStatus());
                mElectricConnectionEditTextNumberOfCompoundLights.setText(electricConnectionData.getNoOfCompoundLights());
                mElectricConnectionEditTextEbMeterReadingInKWh.setText(electricConnectionData.getMeterReadingsEB());
                mElectricConnectionTextViewEbSupplierVal.setText(electricConnectionData.getSupplierEB());
                mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setText(electricConnectionData.getCostPerUnitForSharedConnectionEB());
                mElectricConnectionTextViewEbStatusVal.setText(electricConnectionData.getStatusEB());
                mElectricConnectionTextViewTransformerWorkingConditionVal.setText(electricConnectionData.getTransformerWorkingCondition());
                mElectricConnectionEditTextTransformerCapacityInKva.setText(electricConnectionData.getTransformerCapacity());
                mElectricConnectionTextViewEbMeterBoxStatusVal.setText(electricConnectionData.getMeterBoxStatusEB());
                mElectricConnectionEditTextSectionName.setText(electricConnectionData.getSectionName());
                mElectricConnectionEditTextSectionNumber.setText(electricConnectionData.getSectionNo());
                mElectricConnectionEditTextConsumerNumber.setText(electricConnectionData.getConsumerNo());
                mElectricConnectionTextViewEbMeterWorkingStatusVal.setText(electricConnectionData.getMeterWorkingStatusEB());
                mElectricConnectionEditTextEbMeterSerialNumber.setText(electricConnectionData.getMeterSerialNumberEB());
                mElectricConnectionTextViewTypeOfPaymentVal.setText(electricConnectionData.getPaymentType());
                mElectricConnectionTextViewEbPaymentScheduleVal.setText(electricConnectionData.getPaymentScheduleEB());
                mElectricConnectionTextViewSafetyFuseUnitVal.setText(electricConnectionData.getSafetyFuseUnit());
                mElectricConnectionTextViewKitKatClayFuseStatusVal.setText(electricConnectionData.getKitKatFuseStatus());
                mElectricConnectionTextViewEbNeutralEarthingVal.setText(electricConnectionData.getEbNeutralEarthing());
                mElectricConnectionEditTextAverageEbAvailabilityPerDay.setText(electricConnectionData.getAverageEbAvailability().isEmpty() ? "00:00" : electricConnectionData.getAverageEbAvailability());
                mElectricConnectionEditTextScheduledPowerCutInHrs.setText(electricConnectionData.getScheduledPowerCut().isEmpty() ? "00:00" : electricConnectionData.getScheduledPowerCut());
                //mElectricConnectionEditTextEbBillDate.setText(electricConnectionData.getEbBillDate());
                mElectricConnectionTextViewEbBillDateVal.setText(electricConnectionData.getEbBillDate());
                //mElectricConnectionEditTextSapVendorCode.setText(electricConnectionData.getSapVendorCode());

                mElectricConnectionTextViewTypeModeOfPayment_Val.setText(electricConnectionData.getTypeModeOfPayment_Val());
                mElectricConnectionEditTextBankIfscCode.setText(electricConnectionData.getBankIfscCode());
                mElectricConnectionEditTextBankAccountNo.setText(electricConnectionData.getBankAccountNo());

            } else {
                Toast.makeText(Electric_Connection.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {

            String electricConnectionType = mElectricConnectionTextViewTypeOfElectricConnectionVal.getText().toString().trim();
            String connectionTariff = mElectricConnectionTextViewTariffVal.getText().toString().trim();
            String sanctionLoad = mElectricConnectionEditTextSanctionedLoadKVA.getText().toString().trim();
            String securityAmountPaid = mElectricConnectionEditTextSecurityAmountPaidToTheCompany.getText().toString().trim();
            String existingLoadAtSite = mElectricConnectionEditTextExistingLoadAtSiteKVA.getText().toString().trim();
            String nameSupplyCompany = mElectricConnectionTextViewNameOfSupplyCompanyVal.getText().toString().trim();
            String electricBillCopyStatus = mElectricConnectionTextViewCopyOfElectricBillsVal.getText().toString().trim();
            String noOfCompoundLights = mElectricConnectionEditTextNumberOfCompoundLights.getText().toString().trim();
            String meterReadingsEB = mElectricConnectionEditTextEbMeterReadingInKWh.getText().toString().trim();
            String supplierEB = mElectricConnectionTextViewEbSupplierVal.getText().toString().trim();
            String costPerUnitForSharedConnectionEB = mElectricConnectionEditTextEbCostPerUnitForSharedConnection.getText().toString().trim();
            String statusEB = mElectricConnectionTextViewEbStatusVal.getText().toString().trim();
            String transformerWorkingCondition = mElectricConnectionTextViewTransformerWorkingConditionVal.getText().toString().trim();
            String transformerCapacity = mElectricConnectionEditTextTransformerCapacityInKva.getText().toString().trim();
            String meterBoxStatusEB = mElectricConnectionTextViewEbMeterBoxStatusVal.getText().toString().trim();
            String sectionName = mElectricConnectionEditTextSectionName.getText().toString().trim();
            String sectionNo = mElectricConnectionEditTextSectionNumber.getText().toString().trim();
            String consumerNo = mElectricConnectionEditTextConsumerNumber.getText().toString().trim();
            String meterWorkingStatusEB = mElectricConnectionTextViewEbMeterWorkingStatusVal.getText().toString().trim();
            String meterSerialNumberEB = mElectricConnectionEditTextEbMeterSerialNumber.getText().toString().trim();
            String paymentType = mElectricConnectionTextViewTypeOfPaymentVal.getText().toString().trim();
            String paymentScheduleEB = mElectricConnectionTextViewEbPaymentScheduleVal.getText().toString().trim();
            String safetyFuseUnit = mElectricConnectionTextViewSafetyFuseUnitVal.getText().toString().trim();
            String kitKatFuseStatus = mElectricConnectionTextViewKitKatClayFuseStatusVal.getText().toString().trim();
            String ebNeutralEarthing = mElectricConnectionTextViewEbNeutralEarthingVal.getText().toString().trim();
            String averageEbAvailability = mElectricConnectionEditTextAverageEbAvailabilityPerDay.getText().toString().trim();
            String scheduledPowerCut = mElectricConnectionEditTextScheduledPowerCutInHrs.getText().toString().trim();
            //String ebBillDate = mElectricConnectionEditTextEbBillDate.getText().toString().trim();
            String ebBillDate = mElectricConnectionTextViewEbBillDateVal.getText().toString().trim();
            String sapVendorCode = "";//mElectricConnectionEditTextSapVendorCode.getText().toString().trim();

            String typeModeOfPayment_Val = mElectricConnectionTextViewTypeModeOfPayment_Val.getText().toString().trim();
            String bankIfscCode = mElectricConnectionEditTextBankIfscCode.getText().toString().trim();
            String bankAccountNo = mElectricConnectionEditTextBankAccountNo.getText().toString().trim();


            electricConnectionData = new ElectricConnectionData(electricConnectionType, connectionTariff, sanctionLoad, securityAmountPaid, existingLoadAtSite, nameSupplyCompany, electricBillCopyStatus, noOfCompoundLights, meterReadingsEB, supplierEB, costPerUnitForSharedConnectionEB, statusEB, transformerWorkingCondition, transformerCapacity, meterBoxStatusEB, sectionName, sectionNo, consumerNo, meterWorkingStatusEB, meterSerialNumberEB, paymentType, paymentScheduleEB, safetyFuseUnit, kitKatFuseStatus, ebNeutralEarthing, averageEbAvailability, scheduledPowerCut, ebBillDate, sapVendorCode, typeModeOfPayment_Val, bankIfscCode, bankAccountNo);
            hotoTransactionData.setElectricConnectionData(electricConnectionData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*14-11-2018*/
    private void checkValidation() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(GlobalMethods.replaceAllSpecialCharAtUnderscore(ticketName) + ".txt");

                Gson gson = new Gson();
                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);

                if (hotoTransactionData != null) {
                    if (hotoTransactionData.getSourceOfPower().toString().equals("Non EB")) {

                        mElectricConnectionLinearLayoutEbMeterReadingInKWh.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbSupplier.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbCostPerUnitForSharedConnection.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbStatus.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutTransformerWorkingCondition.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutTransformerCapacityInKva.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbMeterBoxStatus.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutSectionName.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutSectionNumber.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutConsumerNumber.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbMeterWorkingStatus.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbMeterSerialNumber.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutTypeOfPayment.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbPaymentSchedule.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutSafetyFuseUnit.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutKitKatClayFuseStatus.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbNeutralEarthing.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutAverageEbAvailabilityPerDay.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutScheduledPowerCutInHrs.setVisibility(View.GONE);
                        mElectricConnectionLinearLayoutEbBillDate.setVisibility(View.GONE);

                        mElectricConnectionEditTextEbMeterReadingInKWh.setText("");
                        mElectricConnectionTextViewEbSupplierVal.setText("");
                        mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setText("");
                        mElectricConnectionTextViewEbStatusVal.setText("");
                        mElectricConnectionTextViewTransformerWorkingConditionVal.setText("");
                        mElectricConnectionEditTextTransformerCapacityInKva.setText("");
                        mElectricConnectionTextViewEbMeterBoxStatusVal.setText("");
                        mElectricConnectionEditTextSectionName.setText("");
                        mElectricConnectionEditTextSectionNumber.setText("");
                        mElectricConnectionEditTextConsumerNumber.setText("");
                        mElectricConnectionTextViewEbMeterWorkingStatusVal.setText("");
                        mElectricConnectionEditTextEbMeterSerialNumber.setText("");
                        mElectricConnectionTextViewTypeOfPaymentVal.setText("");
                        mElectricConnectionTextViewEbPaymentScheduleVal.setText("");
                        mElectricConnectionTextViewSafetyFuseUnitVal.setText("");
                        mElectricConnectionTextViewKitKatClayFuseStatusVal.setText("");
                        mElectricConnectionTextViewEbNeutralEarthingVal.setText("");
                        mElectricConnectionEditTextAverageEbAvailabilityPerDay.setText("00:00");
                        mElectricConnectionEditTextScheduledPowerCutInHrs.setText("");
                        mElectricConnectionTextViewEbBillDateVal.setText("");

                    }
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void visibilityOfEbCostPerUnitOnEbSupplierSelection(String ebSupplier) {
        if (ebSupplier.equals("Dedicated Connection")) {
            mElectricConnectionLinearLayoutEbCostPerUnitForSharedConnection.setVisibility(View.GONE);
            //mElectricConnectionTextViewEbCostPerUnitForSharedConnection.setText("");
            mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setText("");
            //mElectricConnectionTextViewEbCostPerUnitForSharedConnection.setVisibility(View.GONE);
            //mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setVisibility(View.GONE);
        } else {
            mElectricConnectionLinearLayoutEbCostPerUnitForSharedConnection.setVisibility(View.VISIBLE);
            //mElectricConnectionTextViewEbCostPerUnitForSharedConnection.setVisibility(View.VISIBLE);
            //mElectricConnectionEditTextEbCostPerUnitForSharedConnection.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfPaymentScheduleOnTypeOfPaymentSelection(String typeOfPayment) {
        if (typeOfPayment.equals("Pre Paid")) {

            mElectricConnectionLinearLayoutEbPaymentSchedule.setVisibility(View.GONE);
            mElectricConnectionTextViewEbPaymentScheduleVal.setText("");
            //mElectricConnectionTextViewTypeOfPaymentVal.setVisibility(View.GONE);
            //mElectricConnectionTextViewEbPaymentScheduleVal.setVisibility(View.GONE);
        } else {
            mElectricConnectionLinearLayoutEbPaymentSchedule.setVisibility(View.VISIBLE);
            //mElectricConnectionTextViewTypeOfPaymentVal.setVisibility(View.GONE);
            //mElectricConnectionTextViewEbPaymentScheduleVal.setVisibility(View.GONE);
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
                finish();
                return true;

            case R.id.menuSubmit:
                DecimalFormatConversion();
                submitDetails();
                startActivity(new Intent(this, Air_Conditioners.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
