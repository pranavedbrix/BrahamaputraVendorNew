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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.Data.EarthResistanceEquipmentData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
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
import java.util.Date;
import java.util.Locale;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;

public class Earth_Resistance_Equipment extends BaseActivity {

    private static final String TAG = Earth_Resistance_Equipment.class.getSimpleName();
    private TextView mEarthResistanceEquipmentTextViewTypeOfEarth;
    private TextView mEarthResistanceEquipmentTextViewTypeOfEarthVal;
    private TextView mEarthResistanceEquipmentTextViewEarthResistance;
    private EditText mEarthResistanceEquipmentEditTextEarthResistance;
    private TextView mEarthResistanceEquipmentTextViewDateOfearthResistanceMeasured;
    private EditText mEarthResistanceEquipmentEditTextDateOfearthResistanceMeasured;

    DecimalConversion decimalConversion;
    String str_typeOfEarth;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private EarthResistanceEquipmentData earthResistanceEquipmentData;
    private SessionManager sessionManager;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_resistance_equipment);
        decimalConversion = new DecimalConversion();
        assignViews();
        this.setTitle("Earth Resistance (Equipment)");
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();

        sessionManager = new SessionManager(Earth_Resistance_Equipment.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Earth_Resistance_Equipment.this, userId, ticketName);

        setInputDetails();

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

        mEarthResistanceEquipmentEditTextDateOfearthResistanceMeasured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormatConversion();
                DatePickerDialog dialog = new DatePickerDialog(Earth_Resistance_Equipment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        mEarthResistanceEquipmentEditTextEarthResistance.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
    }

    private void assignViews() {
        mEarthResistanceEquipmentTextViewTypeOfEarth = (TextView) findViewById(R.id.earthResistanceEquipment_textView_typeOfEarth);
        mEarthResistanceEquipmentTextViewTypeOfEarthVal = (TextView) findViewById(R.id.earthResistanceEquipment_textView_typeOfEarth_val);
        mEarthResistanceEquipmentTextViewEarthResistance = (TextView) findViewById(R.id.earthResistanceEquipment_textView_earthResistance);
        mEarthResistanceEquipmentEditTextEarthResistance = (EditText) findViewById(R.id.earthResistanceEquipment_editText_earthResistance);
        mEarthResistanceEquipmentTextViewDateOfearthResistanceMeasured = (TextView) findViewById(R.id.earthResistanceEquipment_textView_dateOfearthResistanceMeasured);
        mEarthResistanceEquipmentEditTextDateOfearthResistanceMeasured = (EditText) findViewById(R.id.earthResistanceEquipment_editText_dateOfearthResistanceMeasured);

        mEarthResistanceEquipmentEditTextEarthResistance.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void DecimalFormatConversion() {
        mEarthResistanceEquipmentEditTextEarthResistance.setText(decimalConversion.convertDecimal(mEarthResistanceEquipmentEditTextEarthResistance.getText().toString()));

    }

    private void initCombo() {
        mEarthResistanceEquipmentTextViewTypeOfEarthVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormatConversion();
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Earth_Resistance_Equipment.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_earthResistanceEquipment_typeOfEarth))),
                        "Type of Earth",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfEarth = item.get(position);
                        mEarthResistanceEquipmentTextViewTypeOfEarthVal.setText(str_typeOfEarth);
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEarthResistanceEquipmentEditTextDateOfearthResistanceMeasured.setText(sdf.format(myCalendar.getTime()));
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                earthResistanceEquipmentData = hotoTransactionData.getEarthResistanceEquipmentData();

                mEarthResistanceEquipmentTextViewTypeOfEarthVal.setText(earthResistanceEquipmentData.getEarthType());
                mEarthResistanceEquipmentEditTextEarthResistance.setText(earthResistanceEquipmentData.getEarthResistanceInOhms());
                mEarthResistanceEquipmentEditTextDateOfearthResistanceMeasured.setText(earthResistanceEquipmentData.getEarthResistanceMeasuredDate());


            } else {
                Toast.makeText(Earth_Resistance_Equipment.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {

            String earthType = mEarthResistanceEquipmentTextViewTypeOfEarthVal.getText().toString().trim();
            String earthResistanceInOhms = mEarthResistanceEquipmentEditTextEarthResistance.getText().toString().trim();
            String earthResistanceMeasuredDate = mEarthResistanceEquipmentEditTextDateOfearthResistanceMeasured.getText().toString().trim();

            earthResistanceEquipmentData = new EarthResistanceEquipmentData(earthType, earthResistanceInOhms, earthResistanceMeasuredDate);

            hotoTransactionData.setEarthResistanceEquipmentData(earthResistanceEquipmentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
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
                if(!(hototicket_sourceOfPower.equals("Non EB")))
                {
                    startActivity(new Intent(this, Electric_Connection.class));
                }else{
                    startActivity(new Intent(this, Air_Conditioners.class));
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
