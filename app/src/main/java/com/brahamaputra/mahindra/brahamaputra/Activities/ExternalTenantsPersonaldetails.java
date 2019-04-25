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

import com.brahamaputra.mahindra.brahamaputra.Data.ExternalTenantsPersonalDetailsData;
import com.brahamaputra.mahindra.brahamaputra.Data.ExternalTenantsPersonalDetailsParentData;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_Selected_CustomerName;

public class ExternalTenantsPersonaldetails extends BaseActivity {

    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    private String str_totalNumberofTanents;
    private String str_nameoftheTenant;
    private String str_typeofTenant;
    DecimalConversion decimalConversion;
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ExternalTenantsPersonalDetailsParentData externalTenantsPersonalDetailsParentData;
    private ArrayList<ExternalTenantsPersonalDetailsData> externalTenantsPersonalDetailsDataList;
    private SessionManager sessionManager;
    private int totalTenantCount = 0;
    private int currentPos = 0;

    private TextView mExternalTenantsPersonaldetailsTextViewTotalNumberofTanents;
    private TextView mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal;
    private TextView mExternalTenantsPersonaldetailsTextViewNameoftheTenant;
    private TextView mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal;
    private TextView mExternalTenantsPersonaldetailsTextViewTypeofTenant;
    private TextView mExternalTenantsPersonaldetailsTextViewTypeofTenantVal;
    private TextView mExternalTenantsPersonaldetailsTextViewPositionattheTower;
    private EditText mExternalTenantsPersonaldetailsEditTextPositionattheTower;
    private TextView mExternalTenantsPersonaldetailsTextViewDateofthestartofTenancy;
    private EditText mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy;
    private TextView mExternalTenantsPersonaldetailsTextViewDateofthestartofRadiation;
    private EditText mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation;
    private TextView mExternalTenantsPersonaldetailsTextViewNameoftheContactPerson;
    private EditText mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson;
    private EditText mExternalTenantsPersonaldetailsEditTextrentalValue;
    private TextView mExternalTenantsPersonaldetailsTextViewAddressoftheContactPerson;
    private EditText mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson;
    private TextView mExternalTenantsPersonaldetailsTextViewTelephoneNoofContactPersonMobile;
    private EditText mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile;
    private TextView mExternalTenantsPersonaldetailsTextViewTelephoneNoofContactPersonLandline;
    private EditText mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline;
    private Button btnPrevReadingExtTenants;
    private Button btnNextReadingExtTenants;
    private LinearLayout lnrTentantDetails;

    private TextView mexternalTenantsPersonaldetails_textView_TentantCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_tenants_personal_details);
        this.setTitle("External Tenants Personal details");
        decimalConversion = new DecimalConversion();
        sessionManager = new SessionManager(ExternalTenantsPersonaldetails.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(ExternalTenantsPersonaldetails.this, userId, ticketName);
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
        externalTenantsPersonalDetailsDataList = new ArrayList<>();
        setInputDetails(0);


        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel_DateofthestartofTenancy();
            }

        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel_DateofthestartofRadiation();
            }

        };

        mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ExternalTenantsPersonaldetails.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new DatePickerDialog(ExternalTenantsPersonaldetails.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                DatePickerDialog dialog = new DatePickerDialog(ExternalTenantsPersonaldetails.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        btnPrevReadingExtTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPos > 0) {
                    //Save current reading
                    saveTenantRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayTenantRecords(currentPos);
                }
            }
        });

        btnNextReadingExtTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidtionForArrayFields()) {
                    if (currentPos < (totalTenantCount - 1)) {
                        //Save current  reading
                        saveTenantRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayTenantRecords(currentPos);

                    } else if (currentPos == (totalTenantCount - 1)) {
                        //Save Final current reading and submit all  data
                        saveTenantRecords(currentPos);
                        if (checkValidationonSubmit("onSubmit")) {
                            submitDetails();
                            startActivity(new Intent(ExternalTenantsPersonaldetails.this, Total_DC_Load_site.class));
                            finish();
                        }
                    }
                }
            }
        });

        mExternalTenantsPersonaldetailsEditTextPositionattheTower.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

    }

    private void assignViews() {
        mExternalTenantsPersonaldetailsTextViewTotalNumberofTanents = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TotalNumberofTanents);
        mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TotalNumberofTanents_val);
        mExternalTenantsPersonaldetailsTextViewNameoftheTenant = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_NameoftheTenant);
        mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_NameoftheTenant_val);
        mExternalTenantsPersonaldetailsTextViewTypeofTenant = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TypeofTenant);
        mExternalTenantsPersonaldetailsTextViewTypeofTenantVal = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TypeofTenant_val);
        mExternalTenantsPersonaldetailsTextViewPositionattheTower = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_PositionattheTower);
        mExternalTenantsPersonaldetailsEditTextPositionattheTower = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_PositionattheTower);
        mExternalTenantsPersonaldetailsTextViewDateofthestartofTenancy = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_DateofthestartofTenancy);
        mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_DateofthestartofTenancy);
        mExternalTenantsPersonaldetailsTextViewDateofthestartofRadiation = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_DateofthestartofRadiation);
        mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_DateofthestartofRadiation);
        mExternalTenantsPersonaldetailsTextViewNameoftheContactPerson = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_NameoftheContactPerson);
        mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_NameoftheContactPerson);

        mExternalTenantsPersonaldetailsEditTextrentalValue = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_rentalValue);

        mExternalTenantsPersonaldetailsTextViewAddressoftheContactPerson = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_AddressoftheContactPerson);
        mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_AddressoftheContactPerson);
        mExternalTenantsPersonaldetailsTextViewTelephoneNoofContactPersonMobile = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TelephoneNoofContactPersonMobile);
        mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile = (EditText) findViewById(R.id.externalTenantsPersonaldetails_editText_TelephoneNoofContactPersonMobile);
        mExternalTenantsPersonaldetailsTextViewTelephoneNoofContactPersonLandline = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TelephoneNoofContactPersonLandline);
        mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline = (EditText) findViewById(R.id.externalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline);
        mexternalTenantsPersonaldetails_textView_TentantCount = (TextView) findViewById(R.id.externalTenantsPersonaldetails_textView_TentantCount);
        lnrTentantDetails = (LinearLayout) findViewById(R.id.lnrTentantDetails);
        lnrTentantDetails.setVisibility(View.GONE);
        btnPrevReadingExtTenants = (Button) findViewById(R.id.btnPrevReadingExtTenants);
        btnNextReadingExtTenants = (Button) findViewById(R.id.btnNextReadingExtTenants);

        mExternalTenantsPersonaldetailsEditTextPositionattheTower.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        getNamesOfTenatsExludeSiteTenant();
    }

    public void DecimalFormatConversion() {
        mExternalTenantsPersonaldetailsEditTextPositionattheTower.setText(decimalConversion.convertDecimal(mExternalTenantsPersonaldetailsEditTextPositionattheTower.getText().toString()));

    }

    private void initCombo() {
        mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ExternalTenantsPersonaldetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_externalTenantsPersonaldetails_TotalNumberofTanents))),
                        "Total Number of Tenants",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_totalNumberofTanents = item.get(position);
                        invalidateOptionsMenu();
                        mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal.setText(str_totalNumberofTanents);

                        currentPos = 0;
                        totalTenantCount = Integer.parseInt(str_totalNumberofTanents);
                        clearFields(currentPos);
                        //clear TenantData collection empty by select / changing value of No of Tenant selected
                        if (externalTenantsPersonalDetailsDataList != null && externalTenantsPersonalDetailsDataList.size() > 0) {
                            externalTenantsPersonalDetailsDataList.clear();
                        }
                        if (totalTenantCount > 0) {
                            mexternalTenantsPersonaldetails_textView_TentantCount.setText("Tenant: #1");
                            lnrTentantDetails.setVisibility(View.VISIBLE);
                            btnPrevReadingExtTenants.setVisibility(View.GONE);
                            btnNextReadingExtTenants.setVisibility(View.VISIBLE);
                            if (totalTenantCount > 0 && totalTenantCount == 1) {
                                btnNextReadingExtTenants.setText("Finish");
                            } else {
                                btnNextReadingExtTenants.setText("Next Reading");
                            }
                        } else {
                            lnrTentantDetails.setVisibility(View.GONE);
                        }


                    }
                });

            }
        });
        mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ExternalTenantsPersonaldetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_externalTenantsPersonaldetails_NameoftheTenant))),
                        "Name of the Tenant",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_nameoftheTenant = item.get(position);
                        mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.setText(str_nameoftheTenant);
                    }
                });*/
                ArrayList<String> customeNames = getNamesOfTenatsExludeSiteTenant();
                if (customeNames != null) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ExternalTenantsPersonaldetails.this, customeNames,
                            "Name of the Tenant",
                            "close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            str_nameoftheTenant = item.get(position);
                            mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.setText(str_nameoftheTenant);
                        }
                    });
                }

            }
        });
        mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ExternalTenantsPersonaldetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_externalTenantsPersonaldetails_TypeofTenant))),
                        "Type of Tenant",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeofTenant = item.get(position);
                        mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.setText(str_typeofTenant);
                    }
                });

            }
        });
    }

    private boolean checkValidtionForArrayFields() {
        DecimalFormatConversion();
        String nameofTenant = mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.getText().toString().trim();
        String typeofTenant = mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.getText().toString().trim();
        String positionattheTower = mExternalTenantsPersonaldetailsEditTextPositionattheTower.getText().toString().trim();
        String dateofstartofTenancy = mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.getText().toString().trim();
        String dateofstartofRadiation = mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.getText().toString().trim();
        String nameofContactPerson = mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson.getText().toString().trim();
        //String rentalValue = mExternalTenantsPersonaldetailsEditTextrentalValue.getText().toString().trim();
        String addressofContactPerson = mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson.getText().toString().trim();
        String contactPersonMobile = mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile.getText().toString().trim();
        String contactPersonLandline = mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline.getText().toString().trim();


        if (nameofTenant.isEmpty() && nameofTenant == null) {
            showToast("Select Name of the Tenant");
            return false;
        } else if (typeofTenant.isEmpty() && typeofTenant == null) {
            showToast("Select Type of Tenant");
            return false;
        } else if (positionattheTower.isEmpty() && positionattheTower == null) {
            showToast("Enter Position at the Tower");
            return false;
        } else if (dateofstartofTenancy.isEmpty() && dateofstartofTenancy == null) {
            showToast("Enter Date of the start of Tenancy");
            return false;
        } else if (dateofstartofTenancy.isEmpty() && dateofstartofTenancy == null) {
            showToast("Enter Date of the start of Tenancy");
            return false;
        } else if (dateofstartofRadiation.isEmpty() && dateofstartofRadiation == null) {
            showToast("Enter Date of Start of Radiation");
            return false;
        } else if (nameofContactPerson.isEmpty() && nameofContactPerson == null) {
            showToast("Enter Name of the Contact Person");
            return false;
        } else if (addressofContactPerson.isEmpty() && addressofContactPerson == null) {
            showToast("Enter Address of the ContactPerson");
            return false;
        } else if (contactPersonMobile.isEmpty() && contactPersonMobile == null) {
            showToast("Enter Mobile Number of Contact Person ");
            return false;
        } else if (contactPersonLandline.isEmpty() && contactPersonLandline == null) {
            showToast("Enter Landline Number of Contact Person ");
            return false;
        } else if (date_compare(dateofstartofTenancy, dateofstartofRadiation) == false) {
            showToast("Select Date of the start of Tenancy is less than or equal to Date of Start of Radiation");
            return false;
        } else return true;

        /*if (!nameofTenant.isEmpty() && nameofTenant != null) {
            if (!typeofTenant.isEmpty() && typeofTenant != null) {
                if (!positionattheTower.isEmpty() && positionattheTower != null) {
                    if (!dateofstartofTenancy.isEmpty() && dateofstartofTenancy != null) {
                        if (!dateofstartofTenancy.isEmpty() && dateofstartofTenancy != null) {
                            if (!dateofstartofRadiation.isEmpty() && dateofstartofRadiation != null) {
                                if (!nameofContactPerson.isEmpty() && nameofContactPerson != null) {
                                    if (!addressofContactPerson.isEmpty() && addressofContactPerson != null) {
                                        if (!contactPersonMobile.isEmpty() && contactPersonMobile != null) {
                                            if (!contactPersonLandline.isEmpty() && contactPersonLandline != null) {
                                                return true;
                                            } else {
                                                showToast("Enter Landline Number of Contact Person ");
                                                return false;
                                            }
                                        } else {
                                            showToast("Enter Mobile Number of Contact Person ");
                                            return false;
                                        }
                                    } else {
                                        showToast("Enter Address of the ContactPerson");
                                        return false;
                                    }
                                } else {
                                    showToast("Enter Name of the Contact Person");
                                    return false;
                                }
                            } else {
                                showToast("Enter Date of Start of Radiation");
                                return false;
                            }
                        } else {
                            showToast("Enter Date of the start of Tenancy");
                            return false;
                        }
                    } else {
                        showToast("Enter Date of the start of Tenancy");
                        return false;
                    }
                } else {
                    showToast("Enter Position at the Tower");
                    return false;
                }
            } else {
                showToast("Select Type of Tenant");
                return false;
            }
        } else {
            showToast("Select Name of the Tenant");
            return false;
        }*/
    }

    public boolean date_compare(String dateStartTenancy, String dateStartRadiation) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");

            Date date1 = formatter.parse(dateStartTenancy);

            Date date2 = formatter.parse(dateStartRadiation);
            if (date1 != null && date2 != null) {
                if (date2.compareTo(date1) < 0) {
                    showToast("Select Date of the start of Tenancy is less than or equal to Date of Start of Radiation");
                    return false;
                }
            }
            /*if (date1.compareTo(date2) < 0) {
                System.out.println("date2 is Greater than my date1");
            }*/
            return true;
        } catch (ParseException e1) {
            e1.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuDone);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_totalNumberofTanents != null && !str_totalNumberofTanents.isEmpty()) {
            if (Integer.valueOf(str_totalNumberofTanents) > 0) {
                shareItem.setVisible(false);
            }
        }

        return true;
    }

    private void updateLabel_DateofthestartofTenancy() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.setText(sdf.format(myCalendar1.getTime()));

    }

    private void updateLabel_DateofthestartofRadiation() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        //mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setText(sdf.format(myCalendar2.getTime()));
        mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setText("");
        if (date_compare(mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.getText().toString().trim(), sdf.format(myCalendar2.getTime())) == true) {
            mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setText(sdf.format(myCalendar2.getTime()));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                DecimalFormatConversion();
                if (checkValidationonSubmit("onSubmit")) {
                    submitDetails();
                    finish();
                    startActivity(new Intent(this, Total_DC_Load_site.class));
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                externalTenantsPersonalDetailsParentData = hotoTransactionData.getExternalTenantsPersonalDetailsParentData();
                externalTenantsPersonalDetailsDataList.addAll(externalTenantsPersonalDetailsParentData.getExternalTenantsPersonalDetailsData());

                //totalTenantCount = externalTenantsPersonalDetailsDataList.size();
                totalTenantCount = Integer.parseInt(externalTenantsPersonalDetailsParentData.getTotalNumberofTanents());
                mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal.setText(externalTenantsPersonalDetailsParentData.getTotalNumberofTanents());

                if (externalTenantsPersonalDetailsDataList != null && externalTenantsPersonalDetailsDataList.size() > 0) {

                    lnrTentantDetails.setVisibility(View.VISIBLE);
                    ExternalTenantsPersonalDetailsData externalTenantsPersonalDetailsData = externalTenantsPersonalDetailsDataList.get(index);
                    mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.setText(externalTenantsPersonalDetailsData.getNameofTenant());
                    mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.setText(externalTenantsPersonalDetailsData.getTypeofTenant());
                    mExternalTenantsPersonaldetailsEditTextPositionattheTower.setText(externalTenantsPersonalDetailsData.getPositionattheTower());
                    mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.setText(externalTenantsPersonalDetailsData.getDateofstartofTenancy());
                    mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setText(externalTenantsPersonalDetailsData.getDateofstartofRadiation());
                    mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson.setText(externalTenantsPersonalDetailsData.getNameofContactPerson());
                    mExternalTenantsPersonaldetailsEditTextrentalValue.setText(externalTenantsPersonalDetailsData.getRentalValue());
                    mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson.setText(externalTenantsPersonalDetailsData.getAddressofContactPerson());
                    mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile.setText(externalTenantsPersonalDetailsData.getContactPersonMobile());
                    mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline.setText(externalTenantsPersonalDetailsData.getContactPersonLandline());

                    btnPrevReadingExtTenants.setVisibility(View.GONE);
                    btnNextReadingExtTenants.setVisibility(View.VISIBLE);

                    //if (batterySetData.size() > 1) {
                    if (totalTenantCount > 1) {
                        btnNextReadingExtTenants.setText("Next Reading");
                    } else {
                        btnNextReadingExtTenants.setText("Finish");
                    }
                }


            } else {
                //Toast.makeText(ExternalTenantsPersonaldetails.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTenantRecords(int pos) {

        if (externalTenantsPersonalDetailsDataList.size() > 0 && pos < externalTenantsPersonalDetailsDataList.size()) {

            mexternalTenantsPersonaldetails_textView_TentantCount.setText("Tentant: #" + (pos + 1));

            ExternalTenantsPersonalDetailsData externalTenantsPersonalDetailsData = externalTenantsPersonalDetailsDataList.get(pos);
            mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.setText(externalTenantsPersonalDetailsData.getNameofTenant());
            mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.setText(externalTenantsPersonalDetailsData.getTypeofTenant());
            mExternalTenantsPersonaldetailsEditTextPositionattheTower.setText(externalTenantsPersonalDetailsData.getPositionattheTower());
            mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.setText(externalTenantsPersonalDetailsData.getDateofstartofTenancy());
            mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setText(externalTenantsPersonalDetailsData.getDateofstartofRadiation());
            mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson.setText(externalTenantsPersonalDetailsData.getNameofContactPerson());
            mExternalTenantsPersonaldetailsEditTextrentalValue.setText(externalTenantsPersonalDetailsData.getRentalValue());
            mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson.setText(externalTenantsPersonalDetailsData.getAddressofContactPerson());
            mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile.setText(externalTenantsPersonalDetailsData.getContactPersonMobile());
            mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline.setText(externalTenantsPersonalDetailsData.getContactPersonLandline());

            btnPrevReadingExtTenants.setVisibility(View.VISIBLE);
            btnNextReadingExtTenants.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalTenantCount - 1)) {
            btnPrevReadingExtTenants.setVisibility(View.VISIBLE);
            btnNextReadingExtTenants.setText("Next Reading");
        } else if (pos > 0 && pos == (totalTenantCount - 1)) {
            btnPrevReadingExtTenants.setVisibility(View.VISIBLE);
            btnNextReadingExtTenants.setText("Finish");
        } else if (pos == 0) {
            btnPrevReadingExtTenants.setVisibility(View.GONE);
            if (pos == (totalTenantCount - 1)) {
                btnNextReadingExtTenants.setText("Finish");
            } else {
                btnNextReadingExtTenants.setText("Next Reading");
            }
        }
    }

    private void saveTenantRecords(int pos) {
        String nameofTenant = mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.getText().toString().trim();
        String typeofTenant = mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.getText().toString().trim();
        String positionattheTower = mExternalTenantsPersonaldetailsEditTextPositionattheTower.getText().toString().trim();
        String dateofstartofTenancy = mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.getText().toString().trim();
        String dateofstartofRadiation = mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.getText().toString().trim();
        String nameofContactPerson = mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson.getText().toString().trim();
        String rentalValue = mExternalTenantsPersonaldetailsEditTextrentalValue.getText().toString().trim();
        String addressofContactPerson = mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson.getText().toString().trim();
        String contactPersonMobile = mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile.getText().toString().trim();
        String contactPersonLandline = mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline.getText().toString().trim();
        ExternalTenantsPersonalDetailsData externalTenantsPersonalDetailsData = new ExternalTenantsPersonalDetailsData(nameofTenant, rentalValue,typeofTenant, positionattheTower, dateofstartofTenancy, dateofstartofRadiation, nameofContactPerson, addressofContactPerson, contactPersonMobile, contactPersonLandline);


        if (externalTenantsPersonalDetailsDataList.size() > 0) {
            if (pos == externalTenantsPersonalDetailsDataList.size()) {
                externalTenantsPersonalDetailsDataList.add(externalTenantsPersonalDetailsData);
            } else if (pos < externalTenantsPersonalDetailsDataList.size()) {
                externalTenantsPersonalDetailsDataList.set(pos, externalTenantsPersonalDetailsData);
            }
        } else {
            externalTenantsPersonalDetailsDataList.add(externalTenantsPersonalDetailsData);
        }
    }

    private boolean checkValidationonSubmit(String methodFlag) {
        /*String totalNumberofTanents = mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal.getText().toString().trim();
        if (!totalNumberofTanents.isEmpty() && totalNumberofTanents != null) {
            return true;
        } else {
            showToast("Select Number Of Tenant ");
            return false;
        }*/

        String totalNumberofTanents = mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal.getText().toString().trim();
        if (totalNumberofTanents.isEmpty() || totalNumberofTanents == null) {
            showToast("Select Number Of Tenant ");
            return false;
        } else if (Integer.valueOf(totalNumberofTanents) > 0) {
            if ((externalTenantsPersonalDetailsDataList.size() != Integer.valueOf(totalNumberofTanents) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;
    }

    private void submitDetails() {
        try {
            // hotoTransactionData.setTicketNo(ticketId);
            String totalNumberofTanents = mExternalTenantsPersonaldetailsTextViewTotalNumberofTanentsVal.getText().toString().trim();

            //externalTenantsPersonalDetailsParentData.setSubmited(true);
            //externalTenantsPersonalDetailsParentData.setTotalNumberofTanents(totalNumberofTanents);
            //externalTenantsPersonalDetailsParentData.setExternalTenantsPersonalDetailsData(externalTenantsPersonalDetailsDataList);
            externalTenantsPersonalDetailsParentData = new ExternalTenantsPersonalDetailsParentData(totalNumberofTanents, externalTenantsPersonalDetailsDataList);
            hotoTransactionData.setExternalTenantsPersonalDetailsParentData(externalTenantsPersonalDetailsParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        mexternalTenantsPersonaldetails_textView_TentantCount.setText("Tentant: #" + (indexPos + 1));

        mExternalTenantsPersonaldetailsTextViewNameoftheTenantVal.setText("");
        mExternalTenantsPersonaldetailsTextViewTypeofTenantVal.setText("");
        mExternalTenantsPersonaldetailsEditTextPositionattheTower.setText("");
        mExternalTenantsPersonaldetailsEditTextDateofthestartofTenancy.setText("");
        mExternalTenantsPersonaldetailsEditTextDateofthestartofRadiation.setText("");
        mExternalTenantsPersonaldetailsEditTextNameoftheContactPerson.setText("");
        mExternalTenantsPersonaldetailsEditTextrentalValue.setText("");
        mExternalTenantsPersonaldetailsEditTextAddressoftheContactPerson.setText("");
        mExternalTenantsPersonaldetailsEditTextTelephoneNoofContactPersonMobile.setText("");
        mExternalTenantsPersonaldetailseditTextTelephoneNoofContactPersonLandline.setText("");
    }

    public ArrayList<String> getNamesOfTenatsExludeSiteTenant() {
        String CurrentCustomer = hototicket_Selected_CustomerName;
        ArrayList<String> ss = null;
        if (!CurrentCustomer.isEmpty() && CurrentCustomer != null) {
            List<String> spinnerArray = new ArrayList<String>();
            spinnerArray = (Arrays.asList(getResources().getStringArray(R.array.array_externalTenantsPersonaldetails_NameoftheTenant)));
            ss = new ArrayList<>(spinnerArray);
            ss.remove(CurrentCustomer);
        } else {
            List<String> spinnerArray = new ArrayList<String>();
            spinnerArray = (Arrays.asList(getResources().getStringArray(R.array.array_externalTenantsPersonaldetails_NameoftheTenant)));
            ss = new ArrayList<>(spinnerArray);
        }
        return ss;
    }


}
