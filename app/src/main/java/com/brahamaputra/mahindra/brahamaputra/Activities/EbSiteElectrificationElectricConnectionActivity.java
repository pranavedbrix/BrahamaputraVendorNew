package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.brahamaputra.mahindra.brahamaputra.Data.EbSiteElectrificationElectricConnectionData;
import com.brahamaputra.mahindra.brahamaputra.Data.EbSiteElectrificationTransactionData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Calendar;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_nameOfSupplyCompany;

public class EbSiteElectrificationElectricConnectionActivity extends BaseActivity {

    private static final String TAG = EbSiteElectrificationElectricConnectionActivity.class.getSimpleName();

    private TextView mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompany;
    private EditText mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal;
    private LinearLayout mEbSiteElectrificationElectricConnectionLinearLayoutConsumerNumber;
    private TextView mEbSiteElectrificationElectricConnectionTextViewConsumerNumber;
    private EditText mEbSiteElectrificationElectricConnectionEditTextConsumerNumber;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbMeterSerialNumber;
    private TextView mEbSiteElectrificationTransactionTextViewEbMeterSerialNumber;
    private EditText mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber;
    private TextView mEbSiteElectrificationTransactionTextViewTypeOfElectricConnection;
    private TextView mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal;
    private TextView mEbSiteElectrificationTransactionTextViewTariff;
    private TextView mEbSiteElectrificationTransactionTextViewTariffVal;
    private TextView mEbSiteElectrificationTransactionTextViewSanctionedLoadKVA;
    private EditText mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA;
    private TextView mEbSiteElectrificationTransactionTextViewExistingLoadAtSiteKVA;
    private EditText mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA;
    private EditText mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany;
    private TextView mEbSiteElectrificationTransactionTextViewCopyOfElectricBills;
    private TextView mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal;
    private TextView mEbSiteElectrificationTransactionTextViewNumberOfCompoundLights;
    private EditText mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbMeterReadingInKWh;
    private TextView mEbSiteElectrificationTransactionTextViewEbMeterReadingInKWh;
    private EditText mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbSupplier;
    private TextView mEbSiteElectrificationTransactionTextViewEbSupplier;
    private TextView mEbSiteElectrificationTransactionTextViewEbSupplierVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbCostPerUnitForSharedConnection;
    private TextView mEbSiteElectrificationTransactionTextViewEbCostPerUnitForSharedConnection;
    private EditText mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbStatus;
    private TextView mEbSiteElectrificationTransactionTextViewEbStatus;
    private TextView mEbSiteElectrificationTransactionTextViewEbStatusVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutTransformerWorkingCondition;
    private TextView mEbSiteElectrificationTransactionTextViewTransformerWorkingCondition;
    private TextView mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutTransformerCapacityInKva;
    private TextView mEbSiteElectrificationTransactionTextViewTransformerCapacityInKva;
    private EditText mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbMeterBoxStatus;
    private TextView mEbSiteElectrificationTransactionTextViewEbMeterBoxStatus;
    private TextView mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutSectionName;
    private TextView mEbSiteElectrificationTransactionTextViewSectionName;
    private EditText mEbSiteElectrificationTransactionEditTextSectionName;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutSectionNumber;
    private TextView mEbSiteElectrificationTransactionTextViewSectionNumber;
    private EditText mEbSiteElectrificationTransactionEditTextSectionNumber;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbMeterWorkingStatus;
    private TextView mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatus;
    private TextView mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutTypeOfPayment;
    private TextView mEbSiteElectrificationTransactionTextViewTypeOfPayment;
    private TextView mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbPaymentSchedule;
    private TextView mEbSiteElectrificationTransactionTextViewEbPaymentSchedule;
    private TextView mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutSafetyFuseUnit;
    private TextView mEbSiteElectrificationTransactionTextViewSafetyFuseUnit;
    private TextView mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutKitKatClayFuseStatus;
    private TextView mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatus;
    private TextView mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbNeutralEarthing;
    private TextView mEbSiteElectrificationTransactionTextViewEbNeutralEarthing;
    private TextView mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutAverageEbAvailabilityPerDay;
    private TextView mEbSiteElectrificationTransactionTextViewAverageEbAvailabilityPerDay;
    private EditText mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutScheduledPowerCutInHrs;
    private TextView mEbSiteElectrificationTransactionTextViewScheduledPowerCutInHrs;
    private EditText mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs;
    private LinearLayout mEbSiteElectrificationTransactionLinearLayoutEbBillDate;
    private TextView mEbSiteElectrificationTransactionTextViewEbBillDate;
    private TextView mEbSiteElectrificationTransactionTextViewEbBillDateVal;
    private TextView mEbSiteElectrificationTransactionTextViewSapVendorCode;
    private EditText mEbSiteElectrificationTransactionEditTextSapVendorCode;
    private TextView mEbSiteElectrificationTransactionTextViewTypeModeOfPayment;
    private TextView mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal;
    private TextView mEbSiteElectrificationTransactionTextViewBankIfscCode;
    private EditText mEbSiteElectrificationTransactionEditTextBankIfscCode;
    private TextView mEbSiteElectrificationTransactionTextViewBankAccountNo;
    private EditText mEbSiteElectrificationTransactionEditTextBankAccountNo;

    DecimalConversion decimalConversion;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private EbSiteElectrificationTransactionData ebSiteElectrificationTransactionData;
    private EbSiteElectrificationElectricConnectionData ebSiteElectrificationElectricConnectionData;
    private SessionManager sessionManager;

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
    String str_ebBillDate;
    String str_typeModeOfPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eb_site_electrification_electric_connection);
        this.setTitle("Electric Connection");//EB Site Electrification
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        decimalConversion = new DecimalConversion();
        assignViews();
        initCombo();

        ebSiteElectrificationTransactionData = new EbSiteElectrificationTransactionData();
        ebSiteElectrificationElectricConnectionData = new EbSiteElectrificationElectricConnectionData();

        setClassEbSiteElectrificationTransactionData();

        if (ebSiteElectrificationTransactionData != null) {
            setValues();
        }
        mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EbSiteElectrificationElectricConnectionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHour1 = (selectedHour >= 10) ? Integer.toString(selectedHour) : String.format("0%s", Integer.toString(selectedHour));
                        String selectedMinute1 = (selectedMinute >= 10) ? Integer.toString(selectedMinute) : String.format("0%s", Integer.toString(selectedMinute));
                        mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay.setText(selectedHour1 + ":" + selectedMinute1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time For Average Eb Availability");
                mTimePicker.show();

            }
        });

        mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EbSiteElectrificationElectricConnectionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHour1 = (selectedHour >= 10) ? Integer.toString(selectedHour) : String.format("0%s", Integer.toString(selectedHour));
                        String selectedMinute1 = (selectedMinute >= 10) ? Integer.toString(selectedMinute) : String.format("0%s", Integer.toString(selectedMinute));
                        mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs.setText(selectedHour1 + ":" + selectedMinute1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time For Scheduled Power Cut");
                mTimePicker.show();

            }
        });

        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
            mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal.setText(hototicket_nameOfSupplyCompany);
            mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal.setKeyListener(null);
        }


    }

    private void assignViews() {
        mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompany = (TextView) findViewById(R.id.ebSiteElectrificationElectricConnection_textView_nameOfSupplyCompany);
        mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal = (EditText) findViewById(R.id.ebSiteElectrificationElectricConnection_textView_nameOfSupplyCompany_val);
        mEbSiteElectrificationElectricConnectionLinearLayoutConsumerNumber = (LinearLayout) findViewById(R.id.ebSiteElectrificationElectricConnection_linearLayout_consumerNumber);
        mEbSiteElectrificationElectricConnectionTextViewConsumerNumber = (TextView) findViewById(R.id.ebSiteElectrificationElectricConnection_textView_consumerNumber);
        mEbSiteElectrificationElectricConnectionEditTextConsumerNumber = (EditText) findViewById(R.id.ebSiteElectrificationElectricConnection_editText_consumerNumber);
        mEbSiteElectrificationTransactionLinearLayoutEbMeterSerialNumber = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebMeterSerialNumber);
        mEbSiteElectrificationTransactionTextViewEbMeterSerialNumber = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterSerialNumber);
        mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_ebMeterSerialNumber);
        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnection = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeOfElectricConnection);
        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeOfElectricConnection_val);
        mEbSiteElectrificationTransactionTextViewTariff = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_tariff);
        mEbSiteElectrificationTransactionTextViewTariffVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_tariff_val);
        mEbSiteElectrificationTransactionTextViewSanctionedLoadKVA = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_sanctionedLoadKVA);
        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sanctionedLoadKVA);
        mEbSiteElectrificationTransactionTextViewExistingLoadAtSiteKVA = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_existingLoadAtSiteKVA);
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_existingLoadAtSiteKVA);
        mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_SecurityAmountPaidToTheCompany);
        mEbSiteElectrificationTransactionTextViewCopyOfElectricBills = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_copyOfElectricBills);
        mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_copyOfElectricBills_val);
        mEbSiteElectrificationTransactionTextViewNumberOfCompoundLights = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_numberOfCompoundLights);
        mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_numberOfCompoundLights);
        mEbSiteElectrificationTransactionLinearLayoutEbMeterReadingInKWh = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebMeterReadingInKWh);
        mEbSiteElectrificationTransactionTextViewEbMeterReadingInKWh = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterReadingInKWh);
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_ebMeterReadingInKWh);
        mEbSiteElectrificationTransactionLinearLayoutEbSupplier = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebSupplier);
        mEbSiteElectrificationTransactionTextViewEbSupplier = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebSupplier);
        mEbSiteElectrificationTransactionTextViewEbSupplierVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebSupplier_val);
        mEbSiteElectrificationTransactionLinearLayoutEbCostPerUnitForSharedConnection = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebCostPerUnitForSharedConnection);
        mEbSiteElectrificationTransactionTextViewEbCostPerUnitForSharedConnection = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebCostPerUnitForSharedConnection);
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_ebCostPerUnitForSharedConnection);
        mEbSiteElectrificationTransactionLinearLayoutEbStatus = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebStatus);
        mEbSiteElectrificationTransactionTextViewEbStatus = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebStatus);
        mEbSiteElectrificationTransactionTextViewEbStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebStatus_val);
        mEbSiteElectrificationTransactionLinearLayoutTransformerWorkingCondition = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_transformerWorkingCondition);
        mEbSiteElectrificationTransactionTextViewTransformerWorkingCondition = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_transformerWorkingCondition);
        mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_transformerWorkingCondition_val);
        mEbSiteElectrificationTransactionLinearLayoutTransformerCapacityInKva = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_transformerCapacityInKva);
        mEbSiteElectrificationTransactionTextViewTransformerCapacityInKva = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_transformerCapacityInKva);
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_transformerCapacityInKva);
        mEbSiteElectrificationTransactionLinearLayoutEbMeterBoxStatus = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebMeterBoxStatus);
        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatus = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterBoxStatus);
        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterBoxStatus_val);
        mEbSiteElectrificationTransactionLinearLayoutSectionName = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_sectionName);
        mEbSiteElectrificationTransactionTextViewSectionName = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_sectionName);
        mEbSiteElectrificationTransactionEditTextSectionName = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sectionName);
        mEbSiteElectrificationTransactionLinearLayoutSectionNumber = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_sectionNumber);
        mEbSiteElectrificationTransactionTextViewSectionNumber = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_sectionNumber);
        mEbSiteElectrificationTransactionEditTextSectionNumber = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sectionNumber);
        mEbSiteElectrificationTransactionLinearLayoutEbMeterWorkingStatus = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebMeterWorkingStatus);
        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatus = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterWorkingStatus);
        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterWorkingStatus_val);
        mEbSiteElectrificationTransactionLinearLayoutTypeOfPayment = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_typeOfPayment);
        mEbSiteElectrificationTransactionTextViewTypeOfPayment = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeOfPayment);
        mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeOfPayment_val);
        mEbSiteElectrificationTransactionLinearLayoutEbPaymentSchedule = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebPaymentSchedule);
        mEbSiteElectrificationTransactionTextViewEbPaymentSchedule = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebPaymentSchedule);
        mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebPaymentSchedule_val);
        mEbSiteElectrificationTransactionLinearLayoutSafetyFuseUnit = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_safetyFuseUnit);
        mEbSiteElectrificationTransactionTextViewSafetyFuseUnit = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_safetyFuseUnit);
        mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_safetyFuseUnit_val);
        mEbSiteElectrificationTransactionLinearLayoutKitKatClayFuseStatus = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_kitKatClayFuseStatus);
        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatus = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_kitKatClayFuseStatus);
        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_kitKatClayFuseStatus_val);
        mEbSiteElectrificationTransactionLinearLayoutEbNeutralEarthing = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebNeutralEarthing);
        mEbSiteElectrificationTransactionTextViewEbNeutralEarthing = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebNeutralEarthing);
        mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebNeutralEarthing_val);
        mEbSiteElectrificationTransactionLinearLayoutAverageEbAvailabilityPerDay = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_averageEbAvailabilityPerDay);
        mEbSiteElectrificationTransactionTextViewAverageEbAvailabilityPerDay = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_averageEbAvailabilityPerDay);
        mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_averageEbAvailabilityPerDay);
        mEbSiteElectrificationTransactionLinearLayoutScheduledPowerCutInHrs = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_scheduledPowerCutInHrs);
        mEbSiteElectrificationTransactionTextViewScheduledPowerCutInHrs = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_scheduledPowerCutInHrs);
        mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_scheduledPowerCutInHrs);
        mEbSiteElectrificationTransactionLinearLayoutEbBillDate = (LinearLayout) findViewById(R.id.ebSiteElectrificationTransaction_linearLayout_ebBillDate);
        mEbSiteElectrificationTransactionTextViewEbBillDate = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebBillDate);
        mEbSiteElectrificationTransactionTextViewEbBillDateVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebBillDate_Val);
        mEbSiteElectrificationTransactionTextViewSapVendorCode = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_sapVendorCode);
        mEbSiteElectrificationTransactionEditTextSapVendorCode = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sapVendorCode);
        mEbSiteElectrificationTransactionTextViewTypeModeOfPayment = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeModeOfPayment);
        mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeModeOfPayment_Val);
        mEbSiteElectrificationTransactionTextViewBankIfscCode = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_bankIfscCode);
        mEbSiteElectrificationTransactionEditTextBankIfscCode = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_bankIfscCode);
        mEbSiteElectrificationTransactionTextViewBankAccountNo = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_bankAccountNo);
        mEbSiteElectrificationTransactionEditTextBankAccountNo = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_bankAccountNo);


        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    public void DecimalFormatConversion() {
        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.setText(decimalConversion.convertDecimal(mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.getText().toString()));
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.setText(decimalConversion.convertDecimal(mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.getText().toString()));
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.setText(decimalConversion.convertDecimal(mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.getText().toString()));
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.setText(decimalConversion.convertDecimal(mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.getText().toString()));
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.setText(decimalConversion.convertDecimal(mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.getText().toString()));
    }

    private void initCombo() {

        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_typrOfElectricConnection))),
                        "Type of Electric Connection",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfElectricConnection = item.get(position);
                        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal.setText(str_typeOfElectricConnection);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewTariffVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_tariff))),
                        "Tariff",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_tariff = item.get(position);
                        mEbSiteElectrificationTransactionTextViewTariffVal.setText(str_tariff);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_copyOfElectricBills))),
                        "Copy of the electric bills",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_copyOfElectricBills = item.get(position);
                        mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal.setText(str_copyOfElectricBills);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbSupplierVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebSupplier))),
                        "EB Supplier",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebSupplier = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbSupplierVal.setText(str_ebSupplier);
                        visibilityOfEbCostPerUnitOnEbSupplierSelection(str_ebSupplier);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebStatus))),
                        "EB Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebStatus = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbStatusVal.setText(str_ebStatus);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_transformerWorkingCondition))),
                        "Transformer Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_transformerWorkingCondition = item.get(position);
                        mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal.setText(str_transformerWorkingCondition);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebMeterBoxStatus))),
                        "EB Meter Box Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebMeterBoxStatus = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal.setText(str_ebMeterBoxStatus);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebMeterWorkingStatus))),
                        "EB Meter Working Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebMeterWorkingStatus = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal.setText(str_ebMeterWorkingStatus);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_typeOfPayment))),
                        "Type Of Payment",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfPayment = item.get(position);
                        mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal.setText(str_typeOfPayment);
                        visibilityOfPaymentScheduleOnTypeOfPaymentSelection(str_typeOfPayment);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebPaymentSchedule))),
                        "EB Payment Schedule",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebPaymentSchedule = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.setText(str_ebPaymentSchedule);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_safetyFuseUnit))),
                        "Safety Fuse Unit (SFU)",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_safetyFuseUnit = item.get(position);
                        mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal.setText(str_safetyFuseUnit);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_kitKatClayFuseStatus))),
                        "KIT-KAT/Clay Fuse Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_kitKatClayFuseStatus = item.get(position);
                        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal.setText(str_kitKatClayFuseStatus);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebNeutralEarthing))),
                        "EB Neutral Earthing",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebNeutralEarthing = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal.setText(str_ebNeutralEarthing);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewEbBillDateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_ebBillDate))),
                        "EB Bill Date",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ebBillDate = item.get(position);
                        mEbSiteElectrificationTransactionTextViewEbBillDateVal.setText(str_ebBillDate);
                    }
                });
            }
        });

        mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(EbSiteElectrificationElectricConnectionActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_electricConnection_typeModeOfPayment))),
                        "Type|Mode Of Payment",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeModeOfPayment = item.get(position);
                        mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal.setText(str_typeModeOfPayment);
                    }
                });
            }
        });
    }

    private void visibilityOfPaymentScheduleOnTypeOfPaymentSelection(String str_typeOfPayment) {
        if (str_typeOfPayment.equals("Pre Paid")) {
            mEbSiteElectrificationTransactionLinearLayoutEbPaymentSchedule.setVisibility(View.GONE);
            mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.setText("");
        } else {
            mEbSiteElectrificationTransactionLinearLayoutEbPaymentSchedule.setVisibility(View.VISIBLE);
        }
    }

    private void visibilityOfEbCostPerUnitOnEbSupplierSelection(String ebSupplier) {
        if (ebSupplier.equals("Dedicated Connection")) {
            mEbSiteElectrificationTransactionLinearLayoutEbCostPerUnitForSharedConnection.setVisibility(View.GONE);
            mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.setText("");
        } else {
            mEbSiteElectrificationTransactionLinearLayoutEbCostPerUnitForSharedConnection.setVisibility(View.VISIBLE);
        }
    }

    private void setClassEbSiteElectrificationTransactionData() {
        /*try {*/

        Intent intent = getIntent();
        ebSiteElectrificationTransactionData = (EbSiteElectrificationTransactionData) intent.getSerializableExtra("ebSiteElectrificationTransactionData");
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void setValues() {

        //EbSiteElectrificationElectricConnectionData ebSiteElectrificationElectricConnectionData = new EbSiteElectrificationElectricConnectionData();
        ebSiteElectrificationElectricConnectionData = ebSiteElectrificationTransactionData.getObjEbSiteElectrificationElectricConnection();

        mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal.setText(ebSiteElectrificationElectricConnectionData.getNameOfTheSupplyCompany() == null || ebSiteElectrificationElectricConnectionData.getNameOfTheSupplyCompany().isEmpty() ? "-" : ebSiteElectrificationElectricConnectionData.getNameOfTheSupplyCompany());
        mEbSiteElectrificationElectricConnectionEditTextConsumerNumber.setText(ebSiteElectrificationElectricConnectionData.getConsumerNumber() == null || ebSiteElectrificationElectricConnectionData.getConsumerNumber().isEmpty() ? "-" : ebSiteElectrificationElectricConnectionData.getConsumerNumber());
        mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber.setText(ebSiteElectrificationElectricConnectionData.getEbMeterSerialNumber() == null || ebSiteElectrificationElectricConnectionData.getEbMeterSerialNumber().isEmpty() ? "-" : ebSiteElectrificationElectricConnectionData.getEbMeterSerialNumber());
        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal.setText(ebSiteElectrificationElectricConnectionData.getTypeOfElectricConnection() == null || ebSiteElectrificationElectricConnectionData.getTypeOfElectricConnection().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getTypeOfElectricConnection());
        mEbSiteElectrificationTransactionTextViewTariffVal.setText(ebSiteElectrificationElectricConnectionData.getTariff() == null || ebSiteElectrificationElectricConnectionData.getTariff().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getTariff());
        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.setText(ebSiteElectrificationElectricConnectionData.getSanctionedLoad() == null || ebSiteElectrificationElectricConnectionData.getSanctionedLoad().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getSanctionedLoad());
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.setText(ebSiteElectrificationElectricConnectionData.getExistingLoadAtSite() == null || ebSiteElectrificationElectricConnectionData.getExistingLoadAtSite().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getExistingLoadAtSite());
        mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany.setText(ebSiteElectrificationElectricConnectionData.getSecurityAmountPaidToTheCompany() == null || ebSiteElectrificationElectricConnectionData.getSecurityAmountPaidToTheCompany().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getSecurityAmountPaidToTheCompany());
        mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal.setText(ebSiteElectrificationElectricConnectionData.getCopyOfTheElectricBills() == null || ebSiteElectrificationElectricConnectionData.getCopyOfTheElectricBills().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getCopyOfTheElectricBills());
        mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights.setText(ebSiteElectrificationElectricConnectionData.getNumberOfCompoundLights() == null || ebSiteElectrificationElectricConnectionData.getNumberOfCompoundLights().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getNumberOfCompoundLights());
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.setText(ebSiteElectrificationElectricConnectionData.getEbMeterReadingInKWH() == null || ebSiteElectrificationElectricConnectionData.getEbMeterReadingInKWH().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbMeterReadingInKWH());
        mEbSiteElectrificationTransactionTextViewEbSupplierVal.setText(ebSiteElectrificationElectricConnectionData.getEbSupplier() == null || ebSiteElectrificationElectricConnectionData.getEbSupplier().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbSupplier());
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.setText(ebSiteElectrificationElectricConnectionData.getEbCostPerUnitForSharedConnection() == null || ebSiteElectrificationElectricConnectionData.getEbCostPerUnitForSharedConnection().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbCostPerUnitForSharedConnection());
        mEbSiteElectrificationTransactionTextViewEbStatusVal.setText(ebSiteElectrificationElectricConnectionData.getEbStatus() == null || ebSiteElectrificationElectricConnectionData.getEbStatus().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbStatus());
        mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal.setText(ebSiteElectrificationElectricConnectionData.getTransformerWorkingCondition() == null || ebSiteElectrificationElectricConnectionData.getTransformerWorkingCondition().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getTransformerWorkingCondition());
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.setText(ebSiteElectrificationElectricConnectionData.getTransformerCapacityInKVA() == null || ebSiteElectrificationElectricConnectionData.getTransformerCapacityInKVA().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getTransformerCapacityInKVA());
        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal.setText(ebSiteElectrificationElectricConnectionData.getEbMeterBoxStatus() == null || ebSiteElectrificationElectricConnectionData.getEbMeterBoxStatus().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbMeterBoxStatus());
        mEbSiteElectrificationTransactionEditTextSectionName.setText(ebSiteElectrificationElectricConnectionData.getSectionName() == null || ebSiteElectrificationElectricConnectionData.getSectionName().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getSectionName());
        mEbSiteElectrificationTransactionEditTextSectionNumber.setText(ebSiteElectrificationElectricConnectionData.getSectionNumber() == null || ebSiteElectrificationElectricConnectionData.getSectionNumber().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getSectionNumber());
        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal.setText(ebSiteElectrificationElectricConnectionData.getEbMeterWorkingStatus() == null || ebSiteElectrificationElectricConnectionData.getEbMeterWorkingStatus().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbMeterWorkingStatus());
        mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal.setText(ebSiteElectrificationElectricConnectionData.getTypeOfPayment() == null || ebSiteElectrificationElectricConnectionData.getTypeOfPayment().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getTypeOfPayment());
        mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.setText(ebSiteElectrificationElectricConnectionData.getEbPaymentSchedule() == null || ebSiteElectrificationElectricConnectionData.getEbPaymentSchedule().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbPaymentSchedule());
        mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal.setText(ebSiteElectrificationElectricConnectionData.getSafetyFuseUnit() == null || ebSiteElectrificationElectricConnectionData.getSafetyFuseUnit().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getSafetyFuseUnit());
        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal.setText(ebSiteElectrificationElectricConnectionData.getKitkatClayFuseStatus() == null || ebSiteElectrificationElectricConnectionData.getKitkatClayFuseStatus().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getKitkatClayFuseStatus());
        mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal.setText(ebSiteElectrificationElectricConnectionData.getEbNeutralEarthing() == null || ebSiteElectrificationElectricConnectionData.getEbNeutralEarthing().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbNeutralEarthing());
        mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay.setText(ebSiteElectrificationElectricConnectionData.getAverageEbAvailabilityPerDay() == null || ebSiteElectrificationElectricConnectionData.getAverageEbAvailabilityPerDay().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getAverageEbAvailabilityPerDay());
        mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs.setText(ebSiteElectrificationElectricConnectionData.getScheduledPowerCutInHrs() == null || ebSiteElectrificationElectricConnectionData.getScheduledPowerCutInHrs().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getScheduledPowerCutInHrs());
        mEbSiteElectrificationTransactionTextViewEbBillDateVal.setText(ebSiteElectrificationElectricConnectionData.getEbBillDate() == null || ebSiteElectrificationElectricConnectionData.getEbBillDate().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getEbBillDate());
        //mEbSiteElectrificationTransactionEditTextSapVendorCode.setText(ebSiteElectrificationElectricConnectionData.getNameOfTheSupplyCompany() == null || ebSiteElectrificationElectricConnectionData.getNameOfTheSupplyCompany().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getNameOfTheSupplyCompany());
        mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal.setText(ebSiteElectrificationElectricConnectionData.getTypeModeOfPayment() == null || ebSiteElectrificationElectricConnectionData.getTypeModeOfPayment().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getTypeModeOfPayment());
        mEbSiteElectrificationTransactionEditTextBankIfscCode.setText(ebSiteElectrificationElectricConnectionData.getBankIfscCode() == null || ebSiteElectrificationElectricConnectionData.getBankIfscCode().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getBankIfscCode());
        mEbSiteElectrificationTransactionEditTextBankAccountNo.setText(ebSiteElectrificationElectricConnectionData.getBankAccountNo() == null || ebSiteElectrificationElectricConnectionData.getBankAccountNo().isEmpty() ? "" : ebSiteElectrificationElectricConnectionData.getBankAccountNo());
    }

    private boolean validation() {
        /*mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal = (EditText) findViewById(R.id.ebSiteElectrificationElectricConnection_textView_nameOfSupplyCompany_val);
        mEbSiteElectrificationElectricConnectionEditTextConsumerNumber = (EditText) findViewById(R.id.ebSiteElectrificationElectricConnection_editText_consumerNumber);
        mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_ebMeterSerialNumber);
        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeOfElectricConnection_val);
        mEbSiteElectrificationTransactionTextViewTariffVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_tariff_val);
        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sanctionedLoadKVA);
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_existingLoadAtSiteKVA);
        mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_SecurityAmountPaidToTheCompany);
        mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_copyOfElectricBills_val);
        mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_numberOfCompoundLights);
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_ebMeterReadingInKWh);
        mEbSiteElectrificationTransactionTextViewEbSupplierVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebSupplier_val);
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_ebCostPerUnitForSharedConnection);
        mEbSiteElectrificationTransactionTextViewEbStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebStatus_val);
        mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_transformerWorkingCondition_val);
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_transformerCapacityInKva);
        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterBoxStatus_val);
        mEbSiteElectrificationTransactionEditTextSectionName = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sectionName);
        mEbSiteElectrificationTransactionEditTextSectionNumber = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sectionNumber);
        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebMeterWorkingStatus_val);
        mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeOfPayment_val);
        mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebPaymentSchedule_val);
        mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_safetyFuseUnit_val);
        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_kitKatClayFuseStatus_val);
        mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebNeutralEarthing_val);
        mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_averageEbAvailabilityPerDay);
        mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_scheduledPowerCutInHrs);
        mEbSiteElectrificationTransactionTextViewEbBillDateVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_ebBillDate_Val);
        mEbSiteElectrificationTransactionEditTextSapVendorCode = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_sapVendorCode);
        mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal = (TextView) findViewById(R.id.ebSiteElectrificationTransaction_textView_typeModeOfPayment_Val);
        mEbSiteElectrificationTransactionEditTextBankIfscCode = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_bankIfscCode);
        mEbSiteElectrificationTransactionEditTextBankAccountNo = (EditText) findViewById(R.id.ebSiteElectrificationTransaction_editText_bankAccountNo);*/


        /*mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal.getText().toString();
        mEbSiteElectrificationElectricConnectionEditTextConsumerNumber.getText().toString();
        mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber.getText().toString();
        mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewTariffVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.getText().toString();
        mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.getText().toString();
        mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany.getText().toString();
        mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights.getText().toString();
        mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbSupplierVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbStatusVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextSectionName.getText().toString();
        mEbSiteElectrificationTransactionEditTextSectionNumber.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay.getText().toString();
        mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs.getText().toString();
        mEbSiteElectrificationTransactionTextViewEbBillDateVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextSapVendorCode.getText().toString();
        mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal.getText().toString();
        mEbSiteElectrificationTransactionEditTextBankIfscCode.getText().toString();
        mEbSiteElectrificationTransactionEditTextBankAccountNo.getText().toString();*/

        /*
        String nameOfTheSupplyCompany =
        String consumerNumber =
        String ebMeterSerialNumber =
        String typeOfElectricConnection =
        String tariff =
        String sanctionedLoad =
        String existingLoadAtSite =
        String securityAmountPaidToTheCompany =
        String copyOfTheElectricBills =
        String numberOfCompoundLights =
        String ebMeterReadingInKWH =
        String ebSupplier =
        String ebCostPerUnitForSharedConnection =
        String ebStatus =
        String transformerWorkingCondition =
        String transformerCapacityInKVA =
        String ebMeterBoxStatus =
        String sectionName =
        String sectionNumber =
        String ebMeterWorkingStatus =
        String typeOfPayment =
        String ebPaymentSchedule =
        String safetyFuseUnit =
        String kitkatClayFuseStatus =
        String ebNeutralEarthing =
        String averageEbAvailabilityPerDay =
        String scheduledPowerCutInHrs =
        String ebBillDate =
        String typeModeOfPayment =
        String bankIfscCode =
        String bankAccountNo =
        */

        String nameOfTheSupplyCompany = mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal.getText().toString();
        String consumerNumber = mEbSiteElectrificationElectricConnectionEditTextConsumerNumber.getText().toString();
        String ebMeterSerialNumber = mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber.getText().toString();
        String typeOfElectricConnection = mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal.getText().toString();
        String tariff = mEbSiteElectrificationTransactionTextViewTariffVal.getText().toString();
        String sanctionedLoad = mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.getText().toString();
        String existingLoadAtSite = mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.getText().toString();
        String securityAmountPaidToTheCompany = mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany.getText().toString();
        String copyOfTheElectricBills = mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal.getText().toString();
        String numberOfCompoundLights = mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights.getText().toString();
        String ebMeterReadingInKWH = mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.getText().toString();
        String ebSupplier = mEbSiteElectrificationTransactionTextViewEbSupplierVal.getText().toString();
        String ebCostPerUnitForSharedConnection = mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.getText().toString();
        String ebStatus = mEbSiteElectrificationTransactionTextViewEbStatusVal.getText().toString();
        String transformerWorkingCondition = mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal.getText().toString();
        String transformerCapacityInKVA = mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.getText().toString();
        String ebMeterBoxStatus = mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal.getText().toString();
        String sectionName = mEbSiteElectrificationTransactionEditTextSectionName.getText().toString();
        String sectionNumber = mEbSiteElectrificationTransactionEditTextSectionNumber.getText().toString();
        String ebMeterWorkingStatus = mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal.getText().toString();
        String typeOfPayment = mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal.getText().toString();
        String ebPaymentSchedule = mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.getText().toString();
        String safetyFuseUnit = mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal.getText().toString();
        String kitkatClayFuseStatus = mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal.getText().toString();
        String ebNeutralEarthing = mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal.getText().toString();
        String averageEbAvailabilityPerDay = mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay.getText().toString();
        String scheduledPowerCutInHrs = mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs.getText().toString();
        String ebBillDate = mEbSiteElectrificationTransactionTextViewEbBillDateVal.getText().toString();
        //String SapVendorCode = mEbSiteElectrificationTransactionEditTextSapVendorCode.getText().toString();
        String typeModeOfPayment = mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal.getText().toString();
        String bankIfscCode = mEbSiteElectrificationTransactionEditTextBankIfscCode.getText().toString();
        String bankAccountNo = mEbSiteElectrificationTransactionEditTextBankAccountNo.getText().toString();


        if (nameOfTheSupplyCompany.isEmpty() || nameOfTheSupplyCompany == null || nameOfTheSupplyCompany.equals("-")) {
            showToast("Name of the Supply Company not found");
            return false;
        } else if (consumerNumber.isEmpty() || consumerNumber == null || consumerNumber.equals("-")) {
            showToast("Consumer Number not found");
            return false;
        } else if (ebMeterSerialNumber.isEmpty() || ebMeterSerialNumber == null || ebMeterSerialNumber.equals("-")) {
            showToast("EB Meter Serial Number not found");
            return false;
        } else if (typeOfElectricConnection.isEmpty() || typeOfElectricConnection == null) {
            showToast("Select Type of Electric Connection");
            return false;
        } else if (tariff.isEmpty() || tariff == null) {
            showToast("Select Tariff");
            return false;
        } else if (sanctionedLoad.isEmpty() || sanctionedLoad == null) {
            showToast("Enter Sanctioned Load(KVA)");
            return false;
        } else if (existingLoadAtSite.isEmpty() || existingLoadAtSite == null) {
            showToast("Enter Existing load at Site(KVA)");
            return false;
        } else if (securityAmountPaidToTheCompany.isEmpty() || securityAmountPaidToTheCompany == null) {
            showToast("Enter Security Amount paid to the Company");
            return false;
        } else if (copyOfTheElectricBills.isEmpty() || copyOfTheElectricBills == null) {
            showToast("Select Copy of the Electric Bills(Last Three Months)");
            return false;
        } else if (numberOfCompoundLights.isEmpty() || numberOfCompoundLights == null) {
            showToast("Enter Number of Compound Lights");
            return false;
        } else if (ebMeterReadingInKWH.isEmpty() || ebMeterReadingInKWH == null) {
            showToast("Enter EB Meter Reading(KWH)");
            return false;
        } else if (ebSupplier.isEmpty() || ebSupplier == null) {
            showToast("Select EB Supplier");
            return false;
        } else if ((ebCostPerUnitForSharedConnection.isEmpty() || ebCostPerUnitForSharedConnection == null) && (!ebSupplier.equals("Dedicated Connection"))) {
            showToast("Enter EB Cost Per Unit for Shared Connection");
            return false;
        } else if (ebStatus.isEmpty() || ebStatus == null) {
            showToast("Select EB Status");
            return false;
        } else if (transformerWorkingCondition.isEmpty() || transformerWorkingCondition == null) {
            showToast("Select Transformer Working Condition");
            return false;
        } else if (transformerCapacityInKVA.isEmpty() || transformerCapacityInKVA == null) {
            showToast("Enter Transformer Capacity(KVA)");
            return false;
        } else if (ebMeterBoxStatus.isEmpty() || ebMeterBoxStatus == null) {
            showToast("Select EB Meter Box Status");
            return false;
        } else if (sectionName.isEmpty() || sectionName == null) {
            showToast("Enter Section Name");
            return false;
        } else if (sectionNumber.isEmpty() || sectionNumber == null) {
            showToast("Enter Section Number");
            return false;
        } else if (ebMeterWorkingStatus.isEmpty() || ebMeterWorkingStatus == null) {
            showToast("Select EB Meter Working Status");
            return false;
        } else if (typeOfPayment.isEmpty() || typeOfPayment == null) {
            showToast("Select Type of Payment");
            return false;
        } else if ((ebPaymentSchedule.isEmpty() || ebPaymentSchedule == null) && (typeOfPayment.equals("Post Paid"))) {
            showToast("Select EB Payment Schedule");
            return false;
        } else if (safetyFuseUnit.isEmpty() || safetyFuseUnit == null) {
            showToast("Select Safety Fuse Unit");
            return false;
        } else if (kitkatClayFuseStatus.isEmpty() || kitkatClayFuseStatus == null) {
            showToast("Select KIT-KAT/Clay Fuse Status");
            return false;
        } else if (ebNeutralEarthing.isEmpty() || ebNeutralEarthing == null) {
            showToast("Select EB Neutral Earthing");
            return false;
        } else if (averageEbAvailabilityPerDay.equals("00:00")) {
            showToast("Select Average EB Availability Per Day");
            return false;
        } else if (scheduledPowerCutInHrs.equals("00:00")) {
            showToast("Select Scheduled Power Cut in Hrs");
            return false;
        } else if (ebBillDate.isEmpty() || ebBillDate == null) {
            showToast("Select EB Bill Date");
            return false;
        } else if (typeModeOfPayment.isEmpty() || typeModeOfPayment == null) {
            showToast("Select Type|Mode of Payment");
            return false;
        } else if (bankIfscCode.isEmpty() || bankIfscCode == null) {
            showToast("Enter Bank IFSC Code");
            return false;
        } else if (bankAccountNo.isEmpty() || bankAccountNo == null) {
            showToast("Enter Bank Account No");
            return false;
        }
        return true;
    }

    private void saveEbElectrificationElectricConnectionData() {
        String nameOfTheSupplyCompany = mEbSiteElectrificationElectricConnectionTextViewNameOfSupplyCompanyVal.getText().toString();
        String consumerNumber = mEbSiteElectrificationElectricConnectionEditTextConsumerNumber.getText().toString();
        String ebMeterSerialNumber = mEbSiteElectrificationTransactionEditTextEbMeterSerialNumber.getText().toString();
        String typeOfElectricConnection = mEbSiteElectrificationTransactionTextViewTypeOfElectricConnectionVal.getText().toString();
        String tariff = mEbSiteElectrificationTransactionTextViewTariffVal.getText().toString();
        String sanctionedLoad = mEbSiteElectrificationTransactionEditTextSanctionedLoadKVA.getText().toString();
        String existingLoadAtSite = mEbSiteElectrificationTransactionEditTextExistingLoadAtSiteKVA.getText().toString();
        String securityAmountPaidToTheCompany = mEbSiteElectrificationTransactionEditTextSecurityAmountPaidToTheCompany.getText().toString();
        String copyOfTheElectricBills = mEbSiteElectrificationTransactionTextViewCopyOfElectricBillsVal.getText().toString();
        String numberOfCompoundLights = mEbSiteElectrificationTransactionEditTextNumberOfCompoundLights.getText().toString();
        String ebMeterReadingInKWH = mEbSiteElectrificationTransactionEditTextEbMeterReadingInKWh.getText().toString();
        String ebSupplier = mEbSiteElectrificationTransactionTextViewEbSupplierVal.getText().toString();
        String ebCostPerUnitForSharedConnection = mEbSiteElectrificationTransactionEditTextEbCostPerUnitForSharedConnection.getText().toString();
        String ebStatus = mEbSiteElectrificationTransactionTextViewEbStatusVal.getText().toString();
        String transformerWorkingCondition = mEbSiteElectrificationTransactionTextViewTransformerWorkingConditionVal.getText().toString();
        String transformerCapacityInKVA = mEbSiteElectrificationTransactionEditTextTransformerCapacityInKva.getText().toString();
        String ebMeterBoxStatus = mEbSiteElectrificationTransactionTextViewEbMeterBoxStatusVal.getText().toString();
        String sectionName = mEbSiteElectrificationTransactionEditTextSectionName.getText().toString();
        String sectionNumber = mEbSiteElectrificationTransactionEditTextSectionNumber.getText().toString();
        String ebMeterWorkingStatus = mEbSiteElectrificationTransactionTextViewEbMeterWorkingStatusVal.getText().toString();
        String typeOfPayment = mEbSiteElectrificationTransactionTextViewTypeOfPaymentVal.getText().toString();
        String ebPaymentSchedule = mEbSiteElectrificationTransactionTextViewEbPaymentScheduleVal.getText().toString();
        String safetyFuseUnit = mEbSiteElectrificationTransactionTextViewSafetyFuseUnitVal.getText().toString();
        String kitkatClayFuseStatus = mEbSiteElectrificationTransactionTextViewKitKatClayFuseStatusVal.getText().toString();
        String ebNeutralEarthing = mEbSiteElectrificationTransactionTextViewEbNeutralEarthingVal.getText().toString();
        String averageEbAvailabilityPerDay = mEbSiteElectrificationTransactionEditTextAverageEbAvailabilityPerDay.getText().toString();
        String scheduledPowerCutInHrs = mEbSiteElectrificationTransactionEditTextScheduledPowerCutInHrs.getText().toString();
        String ebBillDate = mEbSiteElectrificationTransactionTextViewEbBillDateVal.getText().toString();
        //String SapVendorCode = mEbSiteElectrificationTransactionEditTextSapVendorCode.getText().toString();
        String typeModeOfPayment = mEbSiteElectrificationTransactionTextViewTypeModeOfPaymentVal.getText().toString();
        String bankIfscCode = mEbSiteElectrificationTransactionEditTextBankIfscCode.getText().toString();
        String bankAccountNo = mEbSiteElectrificationTransactionEditTextBankAccountNo.getText().toString();


        ebSiteElectrificationElectricConnectionData = new EbSiteElectrificationElectricConnectionData(nameOfTheSupplyCompany, consumerNumber, ebMeterSerialNumber,
                typeOfElectricConnection, tariff, sanctionedLoad, existingLoadAtSite, securityAmountPaidToTheCompany, copyOfTheElectricBills,
                numberOfCompoundLights, ebMeterReadingInKWH, ebSupplier, ebCostPerUnitForSharedConnection, ebStatus, transformerWorkingCondition,
                transformerCapacityInKVA, ebMeterBoxStatus, sectionName, sectionNumber, ebMeterWorkingStatus, typeOfPayment, ebPaymentSchedule, safetyFuseUnit
                , kitkatClayFuseStatus, ebNeutralEarthing, averageEbAvailabilityPerDay, scheduledPowerCutInHrs, ebBillDate, typeModeOfPayment, bankIfscCode, bankAccountNo);
        ebSiteElectrificationTransactionData.setObjEbSiteElectrificationElectricConnection(ebSiteElectrificationElectricConnectionData);

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
                //submitDetails();
                //startActivity(new Intent(this, EbSiteElectrificationTransactionActivity.class));
                if (validation() == true) {
                    saveEbElectrificationElectricConnectionData();
                    Intent intent = new Intent(this, EbSiteElectrificationTransactionActivity.class);
                    intent.putExtra("ebSiteElectrificationTransactionData", (Serializable) ebSiteElectrificationTransactionData);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
