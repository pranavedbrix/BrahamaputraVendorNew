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

import com.brahamaputra.mahindra.brahamaputra.Data.ActiveequipmentDetailsData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.R;
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

public class ActiveequipmentDetails extends BaseActivity {

    final Calendar myCalendar = Calendar.getInstance();

    private String str_typeofBTS;
    private String str_importanceOfSite;
    private String str_numberOfDependantSites;
    DecimalConversion decimalConversion;
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ActiveequipmentDetailsData activeequipmentDetailsData;
    private SessionManager sessionManager;

    private TextView mActiveEquipmentDetailsTextViewTypeofBTS;
    private TextView mActiveEquipmentDetailsTextViewTypeofBTSVal;
    private TextView mActiveEquipmentDetailsTextViewImportanceOfSite;
    private TextView mActiveEquipmentDetailsTextViewImportanceOfSiteVal;
    private TextView mActiveEquipmentDetailsTextViewNumberOfDependantSites;
    private TextView mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal;
    private TextView mActiveEquipmentDetailsTextViewMake;
    private EditText mActiveEquipmentDetailsEditTextMake;
    private TextView mActiveEquipmentDetailsTextViewDCLoadofBTSequipment;
    private EditText mActiveEquipmentDetailsEditTextDCLoadofBTSequipment;
    private TextView mActiveEquipmentDetailsTextViewYearofInstallationatsite;
    private EditText mActiveEquipmentDetailsEditTextYearofInstallationatsite;
    private TextView mActiveEquipmentDetailsTextViewPositionoftheantennaatTowerinMtrs;
    private EditText mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_equipment_details);
        this.setTitle("Active equipment Details");

        sessionManager = new SessionManager(ActiveequipmentDetails.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(ActiveequipmentDetails.this, userId, ticketName);
        decimalConversion = new DecimalConversion();
        assignViews();
        initCombo();
        set_listener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
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

        mActiveEquipmentDetailsEditTextYearofInstallationatsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new DatePickerDialog(ActiveequipmentDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                DatePickerDialog dialog = new DatePickerDialog(ActiveequipmentDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();

            }
        });
    }

    private void assignViews() {
        mActiveEquipmentDetailsTextViewTypeofBTS = (TextView) findViewById(R.id.activeEquipmentDetails_textView_TypeofBTS);
        mActiveEquipmentDetailsTextViewTypeofBTSVal = (TextView) findViewById(R.id.activeEquipmentDetails_textView_TypeofBTS_val);
        mActiveEquipmentDetailsTextViewImportanceOfSite = (TextView) findViewById(R.id.activeEquipmentDetails_textView_importance_of_Site);
        mActiveEquipmentDetailsTextViewImportanceOfSiteVal = (TextView) findViewById(R.id.activeEquipmentDetails_textView_importance_of_Site_val);
        mActiveEquipmentDetailsTextViewNumberOfDependantSites = (TextView) findViewById(R.id.activeEquipmentDetails_textView_numberOfDependantSites);
        mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal = (TextView) findViewById(R.id.activeEquipmentDetails_textView_numberOfDependantSites_val);
        mActiveEquipmentDetailsTextViewMake = (TextView) findViewById(R.id.activeEquipmentDetails_textView_Make);
        mActiveEquipmentDetailsEditTextMake = (EditText) findViewById(R.id.activeEquipmentDetails_editText_Make);
        mActiveEquipmentDetailsTextViewDCLoadofBTSequipment = (TextView) findViewById(R.id.activeEquipmentDetails_textView_DCLoadofBTSequipment);
        mActiveEquipmentDetailsEditTextDCLoadofBTSequipment = (EditText) findViewById(R.id.activeEquipmentDetails_editText_DCLoadofBTSequipment);
        mActiveEquipmentDetailsTextViewYearofInstallationatsite = (TextView) findViewById(R.id.activeEquipmentDetails_textView_YearofInstallationatsite);
        mActiveEquipmentDetailsEditTextYearofInstallationatsite = (EditText) findViewById(R.id.activeEquipmentDetails_editText_YearofInstallationatsite);
        mActiveEquipmentDetailsTextViewPositionoftheantennaatTowerinMtrs = (TextView) findViewById(R.id.activeEquipmentDetails_textView_PositionoftheantennaatTowerinMtrs);
        mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs = (EditText) findViewById(R.id.activeEquipmentDetails_editText_PositionoftheantennaatTowerinMtrs);

        mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mActiveEquipmentDetailsEditTextDCLoadofBTSequipment.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    public void DecimalFormatConversion() {
        mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs.setText(decimalConversion.convertDecimal(mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs.getText().toString()));
        mActiveEquipmentDetailsEditTextDCLoadofBTSequipment.setText(decimalConversion.convertDecimal(mActiveEquipmentDetailsEditTextDCLoadofBTSequipment.getText().toString()));

    }

    public void set_listener() {
        mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mActiveEquipmentDetailsEditTextDCLoadofBTSequipment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
    }

    private void initCombo() {

        mActiveEquipmentDetailsTextViewTypeofBTSVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ActiveequipmentDetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_activeEquipmentDetails_TypeofBTS))),
                        "Type of BTS",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeofBTS = item.get(position);
                        mActiveEquipmentDetailsTextViewTypeofBTSVal.setText(str_typeofBTS);
                    }
                });

            }
        });

        mActiveEquipmentDetailsTextViewImportanceOfSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ActiveequipmentDetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_activeEquipmentDetails_importance_of_Site))),
                        "Importance of Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_importanceOfSite = item.get(position);
                        mActiveEquipmentDetailsTextViewImportanceOfSiteVal.setText(str_importanceOfSite);
                    }
                });

            }
        });

        mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ActiveequipmentDetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_activeEquipmentDetails_numberOfDependantSites))),
                        "Number of Dependant Sites",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfDependantSites = item.get(position);
                        mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal.setText(str_numberOfDependantSites);
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuDone:
                DecimalFormatConversion();
                submitDetails();
                finish();
                startActivity(new Intent(this, PowerManagementSystem.class));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mActiveEquipmentDetailsEditTextYearofInstallationatsite.setText(sdf.format(myCalendar.getTime()));
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
//                landDetailsData = gson.fromJson(jsonInString, LandDetailsData.class);

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                activeequipmentDetailsData = hotoTransactionData.getActiveequipmentDetailsData();

                mActiveEquipmentDetailsTextViewTypeofBTSVal.setText(activeequipmentDetailsData.getTypeofBTS());
                mActiveEquipmentDetailsTextViewImportanceOfSiteVal.setText(activeequipmentDetailsData.getImportanceOfSite());
                mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal.setText(activeequipmentDetailsData.getNumberOfDependantSites());
                mActiveEquipmentDetailsEditTextMake.setText(activeequipmentDetailsData.getMake());
                mActiveEquipmentDetailsEditTextDCLoadofBTSequipment.setText(activeequipmentDetailsData.getDCLoadofBTSequipment());
                mActiveEquipmentDetailsEditTextYearofInstallationatsite.setText(activeequipmentDetailsData.getYearofInstallationatsite());
                mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs.setText(activeequipmentDetailsData.getPositionofAntennaTower());

            } else {
                //Toast.makeText(ActiveequipmentDetails.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            //hotoTransactionData.setTicketNo(ticketId);

            String typeofBTS = mActiveEquipmentDetailsTextViewTypeofBTSVal.getText().toString().trim();
            String importanceOfSite = mActiveEquipmentDetailsTextViewImportanceOfSiteVal.getText().toString().trim();
            String numberOfDependantSites = mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal.getText().toString().trim();
            String make = mActiveEquipmentDetailsEditTextMake.getText().toString().trim();
            String DCLoadofBTSequipment = mActiveEquipmentDetailsEditTextDCLoadofBTSequipment.getText().toString().trim();
            String yearofInstallationatsite = mActiveEquipmentDetailsEditTextYearofInstallationatsite.getText().toString().trim();
            String positionofAntennaTower = mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs.getText().toString().trim();

            activeequipmentDetailsData = new ActiveequipmentDetailsData(typeofBTS, importanceOfSite, numberOfDependantSites, make, DCLoadofBTSequipment, yearofInstallationatsite, positionofAntennaTower);
            hotoTransactionData.setActiveequipmentDetailsData(activeequipmentDetailsData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
