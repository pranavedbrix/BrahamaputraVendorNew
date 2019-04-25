package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.Circle;
import com.brahamaputra.mahindra.brahamaputra.Data.CircleList;
import com.brahamaputra.mahindra.brahamaputra.Data.Customer;
import com.brahamaputra.mahindra.brahamaputra.Data.CustomerList;
import com.brahamaputra.mahindra.brahamaputra.Data.EBlSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.Data.EbSiteConnectedData;
import com.brahamaputra.mahindra.brahamaputra.Data.ElectricBillProcessData;
import com.brahamaputra.mahindra.brahamaputra.Data.SSA;
import com.brahamaputra.mahindra.brahamaputra.Data.SSAList;
import com.brahamaputra.mahindra.brahamaputra.Data.Site;
import com.brahamaputra.mahindra.brahamaputra.Data.SiteList;
import com.brahamaputra.mahindra.brahamaputra.Data.State;
import com.brahamaputra.mahindra.brahamaputra.Data.StateList;
import com.brahamaputra.mahindra.brahamaputra.Data.UserDetailsParent;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.EnglishNumberToWords;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.commons.ToastMessage;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ElectricBillProcess extends BaseActivity {

    private static final String TAG = ElectricBillProcess.class.getSimpleName();
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private Customer customer;
    private Circle circle;
    private State state;
    private SSA ssa;
    private Site site;
    private EbSiteConnectedData ebSiteConnectedData;
    private SessionManager sessionManager;
    private ElectricBillProcessData electricBillProcessData;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    Calendar myCalendar3 = Calendar.getInstance();
    Calendar myCalendar4 = Calendar.getInstance();
    DecimalConversion decimalConversion;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private Uri imageFileUri = null;
    private String imageFileName = "";

    private ToastMessage toastMessage;
    private AlertDialogManager alertDialogManager;
    private TextView mEbProcessTextViewCustomer;
    private TextView mEbProcessTextViewCustomerVal;
    private TextView mEbProcessTextViewCircle;
    private TextView mEbProcessTextViewCircleVal;
    private TextView mEbProcessTextViewState;
    private TextView mEbProcessTextViewStateVal;
    private TextView mEbProcessTextViewSsa;
    private TextView mEbProcessTextViewSsaVal;
    private TextView mEbProcessTextViewSite;
    private TextView mEbProcessTextViewSiteVal;
    private TextView mEbProcessTextViewSiteID;
    private TextView mEbProcessTextViewSiteIDVal;
    private TextView mEbProcessTextViewSiteDetails;
    private TextView mEbProcessTextViewSiteDetailsVal;
    private TextView mEbProcessTextViewEbServiceProvider;
    private TextView mEbProcessTextViewEbServiceProviderVal;
    private TextView mEbProcessTextViewEbConsumerNumber;
    private TextView mEbProcessTextViewEbConsumerNumberVal;
    private TextView mEbProcessTextViewTypeModeOfPayement;
    private TextView mEbProcessTextViewTypeModeOfPayementVal;
    private TextView mEbProcessTextViewTypeOfElectricConnection;
    private TextView mEbProcessTextViewTypeOfElectricConnectionVal;
    private TextView mEbProcessTextViewTariff;
    private TextView mEbProcessTextViewTariffVal;
    private TextView mEbProcessTextViewUnitConsumed;
    private EditText mEbProcessEditTextUnitConsumed;
    private TextView mEbProcessTextViewBillingFrom;
    private EditText mEbProcessEditTextBillingFrom;
    private TextView mEbProcessTextViewBillingTo;
    private EditText mEbProcessEditTextBillingTo;
    private TextView mEbProcessTextViewBillNo;
    private EditText mEbProcessEditTextBillNo;
    private TextView mEbProcessTextViewBillingIssueDate;
    private EditText mEbProcessEditTextBillingIssueDate;
    private TextView mEbProcessTextViewBilliDueDate;
    private EditText mEbProcessEditTextBilliDueDate;
    private TextView mEbProcessTextViewGrossAmount;
    private EditText mEbProcessEditTextGrossAmount;
    private TextView mEbProcessTextViewNetPaybleBeforeDueDate;
    private EditText mEbProcessEditTextNetPaybleBeforeDueDate;
    private TextView mEbProcessTextViewEbBillScanCopy;
    private ImageView mEbProcessButtonEbBillScanCopy;
    private ImageView mEbProcessButtonEbBillScanCopyiew;
    private String base64StringBillUpload = "";
    private TextView mEbProcessTextViewNetPayableWords;
    private TextView mEbProcessTextViewNetPayableWordsVal;

    String str_customerName = "";
    String str_circleName = "";
    String str_stateName;
    String str_ssa = "";
    String str_siteName;

    int customerId = 0;
    int circleId = 0;
    int StateId = 0;
    int ssaID = 0;
    int siteID = 0;

    String date_BillFrom;
    String date_BillTo;
    String date_issue;
    String date_due;

    private List<String> ConsumerNoList = null;
    private List<String> PaymentTypeList = null;
    private List<String> ElectricConnectionTypeList = null;
    private List<String> ConnectionTariffList = null;

    public GPSTracker gpsTracker;
    private ArrayList<String> siteArray;


    final DatePickerDialog.OnDateSetListener dateBillfrom = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, monthOfYear);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelBillfrom();
        }

    };

    final DatePickerDialog.OnDateSetListener dateBillto = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelBillto();
        }

    };

    final DatePickerDialog.OnDateSetListener dateIssueBill = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar3.set(Calendar.YEAR, year);
            myCalendar3.set(Calendar.MONTH, monthOfYear);
            myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelIssueBill();
        }

    };

    final DatePickerDialog.OnDateSetListener dateDueDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar4.set(Calendar.YEAR, year);
            myCalendar4.set(Calendar.MONTH, monthOfYear);
            myCalendar4.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelDueDate();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_bill_process);
        this.setTitle("Bill Request");//Electric Bill Process
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gpsTracker = new GPSTracker(ElectricBillProcess.this);

        decimalConversion = new DecimalConversion();
        sessionManager = new SessionManager(ElectricBillProcess.this);
        alertDialogManager = new AlertDialogManager(ElectricBillProcess.this);
        toastMessage = new ToastMessage(ElectricBillProcess.this);
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(ElectricBillProcess.this, userId, "EB");
        assignViews();
        //initCombo();
        set_listener();
        prepareUserPersonalData();//arj


        // setInputDetails();
        mEbProcessEditTextUnitConsumed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbProcessEditTextGrossAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbProcessEditTextNetPaybleBeforeDueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mEbProcessEditTextNetPaybleBeforeDueDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mEbProcessTextViewNetPayableWordsVal.setText("");
                    String netPayable = mEbProcessEditTextNetPaybleBeforeDueDate.getText().toString().trim();
                    if (netPayable.toString().length() > 0 && !netPayable.equals(".")) {
                        float number = Float.valueOf(netPayable);
                        long aValue = (long) number;
                        //String str_rentLease = EnglishNumberToWords.convert(Long.valueOf(netPayable));
                        String str_rentLease = EnglishNumberToWords.convert(aValue);
                        mEbProcessTextViewNetPayableWordsVal.setText(str_rentLease);
                    }
                } catch (Exception e) {
                    Log.d(ElectricBillProcess.TAG, e.getMessage());
                }
            }
        });


    }

    private void assignViews() {
        mEbProcessTextViewCustomer = (TextView) findViewById(R.id.ebProcess_textView_customer);
        mEbProcessTextViewCustomerVal = (TextView) findViewById(R.id.ebProcess_textView_customerVal);
        mEbProcessTextViewCircle = (TextView) findViewById(R.id.ebProcess_textView_circle);
        mEbProcessTextViewCircleVal = (TextView) findViewById(R.id.ebProcess_textView_circleVal);
        mEbProcessTextViewState = (TextView) findViewById(R.id.ebProcess_textView_state);
        mEbProcessTextViewStateVal = (TextView) findViewById(R.id.ebProcess_textView_stateVal);
        mEbProcessTextViewSsa = (TextView) findViewById(R.id.ebProcess_textView_ssa);
        mEbProcessTextViewSsaVal = (TextView) findViewById(R.id.ebProcess_textView_ssaVal);
        mEbProcessTextViewSite = (TextView) findViewById(R.id.ebProcess_textView_site);
        mEbProcessTextViewSiteVal = (TextView) findViewById(R.id.ebProcess_textView_siteVal);
        mEbProcessTextViewSiteID = (TextView) findViewById(R.id.ebProcess_textView_siteID);
        mEbProcessTextViewSiteIDVal = (TextView) findViewById(R.id.ebProcess_textView_siteID_val);
        mEbProcessTextViewSiteDetails = (TextView) findViewById(R.id.ebProcess_textView_siteDetails);
        mEbProcessTextViewSiteDetailsVal = (TextView) findViewById(R.id.ebProcess_textView_siteDetails_val);
        mEbProcessTextViewEbServiceProvider = (TextView) findViewById(R.id.ebProcess_textView_ebServiceProvider);
        mEbProcessTextViewEbServiceProviderVal = (TextView) findViewById(R.id.ebProcess_textView_ebServiceProviderVal);
        mEbProcessTextViewEbConsumerNumber = (TextView) findViewById(R.id.ebProcess_textView_ebConsumerNumber);
        mEbProcessTextViewEbConsumerNumberVal = (TextView) findViewById(R.id.ebProcess_textView_ebConsumerNumberVal);
        mEbProcessTextViewTypeModeOfPayement = (TextView) findViewById(R.id.ebProcess_textView_typeModeOfPayement);
        mEbProcessTextViewTypeModeOfPayementVal = (TextView) findViewById(R.id.ebProcess_textView_typeModeOfPayementVal);
        mEbProcessTextViewTypeOfElectricConnection = (TextView) findViewById(R.id.ebProcess_textView_typeOfElectricConnection);
        mEbProcessTextViewTypeOfElectricConnectionVal = (TextView) findViewById(R.id.ebProcess_textView_typeOfElectricConnectionVal);
        mEbProcessTextViewTariff = (TextView) findViewById(R.id.ebProcess_textView_tariff);
        mEbProcessTextViewTariffVal = (TextView) findViewById(R.id.ebProcess_textView_tariffVal);
        mEbProcessTextViewUnitConsumed = (TextView) findViewById(R.id.ebProcess_textView_unitConsumed);
        mEbProcessEditTextUnitConsumed = (EditText) findViewById(R.id.ebProcess_editText_unitConsumed);
        mEbProcessTextViewBillingFrom = (TextView) findViewById(R.id.ebProcess_textView_billingFrom);
        mEbProcessEditTextBillingFrom = (EditText) findViewById(R.id.ebProcess_editText_billingFrom);
        mEbProcessTextViewBillingTo = (TextView) findViewById(R.id.ebProcess_textView_billingTo);
        mEbProcessEditTextBillingTo = (EditText) findViewById(R.id.ebProcess_editText_billingTo);
        mEbProcessTextViewBillNo = (TextView) findViewById(R.id.ebProcess_textView_billNo);
        mEbProcessEditTextBillNo = (EditText) findViewById(R.id.ebProcess_editText_billNo);
        mEbProcessTextViewBillingIssueDate = (TextView) findViewById(R.id.ebProcess_textView_billingIssueDate);
        mEbProcessEditTextBillingIssueDate = (EditText) findViewById(R.id.ebProcess_editText_billingIssueDate);
        mEbProcessTextViewBilliDueDate = (TextView) findViewById(R.id.ebProcess_textView_billiDueDate);
        mEbProcessEditTextBilliDueDate = (EditText) findViewById(R.id.ebProcess_editText_billiDueDate);
        mEbProcessTextViewGrossAmount = (TextView) findViewById(R.id.ebProcess_textView_grossAmount);
        mEbProcessEditTextGrossAmount = (EditText) findViewById(R.id.ebProcess_editText_grossAmount);
        mEbProcessTextViewNetPaybleBeforeDueDate = (TextView) findViewById(R.id.ebProcess_textView_netPaybleBeforeDueDate);
        mEbProcessEditTextNetPaybleBeforeDueDate = (EditText) findViewById(R.id.ebProcess_editText_netPaybleBeforeDueDate);
        mEbProcessTextViewEbBillScanCopy = (TextView) findViewById(R.id.ebProcess_textView_ebBillScanCopy);
        mEbProcessButtonEbBillScanCopy = (ImageView) findViewById(R.id.ebProcess_button_ebBillScanCopy);
        mEbProcessButtonEbBillScanCopyiew = (ImageView) findViewById(R.id.ebProcess_button_ebBillScanCopyiew);
        mEbProcessTextViewNetPayableWords = (TextView) findViewById(R.id.ebProcess_textView_netPayableWords);
        mEbProcessTextViewNetPayableWordsVal = (TextView) findViewById(R.id.ebProcess_textView_netPayableWordsVal);

        mEbProcessEditTextUnitConsumed.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mEbProcessEditTextGrossAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mEbProcessEditTextNetPaybleBeforeDueDate.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});

        mEbProcessTextViewSiteIDVal.setAllCaps(true);
        mEbProcessTextViewSiteDetailsVal.setAllCaps(true);
        mEbProcessTextViewEbServiceProviderVal.setAllCaps(true);
        mEbProcessTextViewNetPayableWordsVal.setAllCaps(true);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void DecimalFormatConversion() {
        mEbProcessEditTextUnitConsumed.setText(decimalConversion.convertDecimal(mEbProcessEditTextUnitConsumed.getText().toString()));
        mEbProcessEditTextGrossAmount.setText(decimalConversion.convertDecimal(mEbProcessEditTextGrossAmount.getText().toString()));
        mEbProcessEditTextNetPaybleBeforeDueDate.setText(decimalConversion.convertDecimal(mEbProcessEditTextNetPaybleBeforeDueDate.getText().toString()));

    }

    private void initCombo() {
    }

    public void set_listener() {
        mEbProcessTextViewCustomerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ////prepareCustomer();arj

            }
        });
        mEbProcessTextViewCircleVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEbProcessTextViewCustomerVal.getText().toString().trim().isEmpty()) {
                    ////prepareCircle();arj
                } else {
                    showToast("Please Select Customer");
                }

            }
        });

        mEbProcessTextViewStateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prepareStates();
                if (!mEbProcessTextViewCircleVal.getText().toString().trim().isEmpty()) {
                    ////prepareStates();arj
                } else {
                    showToast("Please Select Circle");
                }

            }
        });
        mEbProcessTextViewSsaVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prepareSSA();
                if (!mEbProcessTextViewStateVal.getText().toString().trim().isEmpty()) {
                    ////prepareSSA();arj
                } else {
                    showToast("Please Select State");
                }
            }
        });

        mEbProcessTextViewSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepareSite();
                //on 20022019 if (!mEbProcessTextViewSsaVal.getText().toString().trim().isEmpty()) {
                //prepareSite();
                if (siteArray != null) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                            siteArray,
                            "Select Site",
                            "Close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            ConsumerNoList = null;
                            PaymentTypeList = null;
                            ElectricConnectionTypeList = null;
                            ConnectionTariffList = null;
                            mEbProcessTextViewEbConsumerNumberVal.setText("");
                            mEbProcessTextViewTypeModeOfPayementVal.setText("");
                            mEbProcessTextViewTypeOfElectricConnectionVal.setText("");
                            mEbProcessTextViewTariffVal.setText("");
                            mEbProcessTextViewSiteIDVal.setText("");
                            mEbProcessTextViewSiteDetailsVal.setText("");
                            mEbProcessTextViewEbServiceProviderVal.setText("");

                            str_siteName = item.get(position);
                            mEbProcessTextViewSiteVal.setText(str_siteName);
                            siteID = Integer.valueOf(site.getSiteList().get(position).getId());
                            mEbProcessTextViewSiteIDVal.setText(site.getSiteList().get(position).getSiteId());
                            //String siteAddress = String.valueOf(site.getSiteList().get(position).getSiteAddress());

                            str_circleName = site.getSiteList().get(position).getCircleName() == null || site.getSiteList().get(position).getCircleName().isEmpty() == true ? "-" : site.getSiteList().get(position).getCircleName();
                            //str_circleName = site.getSiteList().get(position).getCircleName();
                            mEbProcessTextViewCircleVal.setText(str_circleName);
                            circleId = Integer.valueOf(site.getSiteList().get(position).getCircleId() == null || site.getSiteList().get(position).getCircleId().isEmpty() == true ? "" : site.getSiteList().get(position).getCircleId());
                            //circleId = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getCircleId());

                            str_stateName = site.getSiteList().get(position).getStateName() == null || site.getSiteList().get(position).getStateName().isEmpty() == true ? "-" : site.getSiteList().get(position).getStateName();
                            //str_stateName = response.getUserDetails().getUserAdditionalDetails().getStateName();
                            mEbProcessTextViewStateVal.setText(str_stateName);
                            StateId = Integer.valueOf(site.getSiteList().get(position).getStateId() == null || site.getSiteList().get(position).getStateId().isEmpty() == true ? "" : site.getSiteList().get(position).getStateId());
                            //StateId = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getStateId());

                            str_ssa = site.getSiteList().get(position).getSsaName() == null || site.getSiteList().get(position).getSsaName().isEmpty() == true ? "-" : site.getSiteList().get(position).getSsaName();
                            //str_ssa = response.getUserDetails().getUserAdditionalDetails().getSsaName();
                            mEbProcessTextViewSsaVal.setText(str_ssa);
                            ssaID = Integer.valueOf(site.getSiteList().get(position).getSsaId() == null || site.getSiteList().get(position).getSsaId().isEmpty() == true ? "" : site.getSiteList().get(position).getSsaId());
                            //ssaID = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getSsaId());


                            //if (!siteAddress.isEmpty()) {
                            mEbProcessTextViewSiteDetailsVal.setText(site.getSiteList().get(position).getSiteAddress() == null || site.getSiteList().get(position).getSiteAddress().isEmpty() == true ? "-" : site.getSiteList().get(position).getSiteAddress());
                            //}
                            mEbProcessTextViewEbServiceProviderVal.setText(site.getSiteList().get(position).getEbOfficeName() == null || site.getSiteList().get(position).getEbOfficeName().isEmpty() == true ? "-" : site.getSiteList().get(position).getEbOfficeName());
                            prepareEbSiteConnectedData();
                        }
                    });
                } else {
                    showToast("Sites are not found");
                }

                /* on 20022019 } else {
                    showToast("Please Select SSA");
                }*/

            }
        });
        mEbProcessTextViewEbConsumerNumberVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConsumerNoList != null) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                            new ArrayList<String>(ConsumerNoList),
                            "Select Consumer List",
                            "Close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            // str_customerName = item.get(position);
                            mEbProcessTextViewEbConsumerNumberVal.setText(item.get(position));


                        }
                    });
                } else {
                    mEbProcessTextViewEbConsumerNumberVal.setText("No Data");
                }
            }
        });

        mEbProcessTextViewTypeModeOfPayementVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PaymentTypeList != null) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                            new ArrayList<String>(PaymentTypeList),
                            "Select Payment Type",
                            "Close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            //str_customerName = item.get(position);
                            mEbProcessTextViewTypeModeOfPayementVal.setText(item.get(position));


                        }
                    });
                } else {
                    mEbProcessTextViewTypeModeOfPayementVal.setText("No Data");
                }
            }
        });

        mEbProcessTextViewTypeOfElectricConnectionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ElectricConnectionTypeList != null) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                            new ArrayList<String>(ElectricConnectionTypeList),
                            "Select Connection Type",
                            "Close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            //str_customerName = item.get(position);
                            mEbProcessTextViewTypeOfElectricConnectionVal.setText(item.get(position));


                        }
                    });
                } else {
                    mEbProcessTextViewTypeOfElectricConnectionVal.setText("No Data");
                }
            }
        });

        mEbProcessTextViewTariffVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionTariffList != null) {
                    SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                            new ArrayList<String>(ConnectionTariffList),
                            "Select Tariff",
                            "Close", "#000000");
                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                        @Override
                        public void onClick(ArrayList<String> item, int position) {

                            //str_customerName = item.get(position);
                            mEbProcessTextViewTariffVal.setText(item.get(position));


                        }
                    });
                } else {
                    mEbProcessTextViewTariffVal.setText("No Data");
                }
            }
        });


        mEbProcessEditTextBillingFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ElectricBillProcess.this, dateBillfrom, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEbProcessEditTextBillingTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ElectricBillProcess.this, dateBillto, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mEbProcessEditTextBillingIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ElectricBillProcess.this, dateIssueBill, myCalendar3
                        .get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH),
                        myCalendar3.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEbProcessEditTextBilliDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ElectricBillProcess.this, dateDueDate, myCalendar4
                        .get(Calendar.YEAR), myCalendar4.get(Calendar.MONTH),
                        myCalendar4.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mEbProcessButtonEbBillScanCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkCameraPermission()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        imageFileName = "IMG_" + sdf.format(new Date()) + "_site.jpg";

                        File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
                        imageFileUri = FileProvider.getUriForFile(ElectricBillProcess.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                        startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mEbProcessButtonEbBillScanCopyiew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(ElectricBillProcess.this, imageFileUri);
                } else {
                    Toast.makeText(ElectricBillProcess.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private boolean checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(ElectricBillProcess.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ElectricBillProcess.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    private void updateLabelBillfrom() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEbProcessEditTextBillingFrom.setText(sdf.format(myCalendar1.getTime()));

        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);

        date_BillFrom = sdf1.format(myCalendar1.getTime());
        BillFromToDateValidation();
    }

    private void updateLabelBillto() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEbProcessEditTextBillingTo.setText(sdf.format(myCalendar2.getTime()));

        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);

        date_BillTo = sdf1.format(myCalendar2.getTime());
        BillFromToDateValidation();
    }

    private void updateLabelIssueBill() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEbProcessEditTextBillingIssueDate.setText(sdf.format(myCalendar3.getTime()));

        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);

        date_issue = sdf1.format(myCalendar3.getTime());
        BillIsseuDueDateValidation();
    }

    private void updateLabelDueDate() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEbProcessEditTextBilliDueDate.setText(sdf.format(myCalendar4.getTime()));

        String myFormat1 = "dd/MM/yyyy"; //In
        // which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);

        date_due = sdf1.format(myCalendar4.getTime());
        BillIsseuDueDateValidation();
    }

    private void BillFromToDateValidation() {
        if (date_BillFrom != null && date_BillTo != null) {
            if (!date_BillFrom.isEmpty() && !date_BillTo.isEmpty()) {
                if (!dateFromToValid(date_BillFrom, date_BillTo)) {
                    showToast("Bill To Date should greater than Bill From Date ");
                    mEbProcessEditTextBillingFrom.setText("");
                    date_BillFrom = "";
                    mEbProcessEditTextBillingTo.setText("");
                    date_BillTo = "";
                }
            }
        }
    }
    //from = issue
    //to = due

    private void BillIsseuDueDateValidation() {
        if (date_issue != null && date_due != null) {
            if (!date_issue.isEmpty() && !date_due.isEmpty()) {
                if (!dateFromToValid(date_issue, date_due)) {
                    showToast("Bill due Date should greater than Bill issue Date ");
                    mEbProcessEditTextBillingIssueDate.setText("");
                    date_issue = "";
                    mEbProcessEditTextBilliDueDate.setText("");
                    date_due = "";
                }
            }
        }
    }
  /*  private String ddmmyyyy_ddmmmyyyy() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEbProcessEditTextBilliDueDate.setText(sdf.format(String value));
    }*/


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
                //  startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                DecimalFormatConversion();
                if (checkValiadtion()) {
                    //showSettingsAlert();
                    if (gpsTracker.canGetLocation()) {
                        if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                            showSettingsAlert();
                        } else {
                            //showToast("Could not detecting location. Please try again later.");
                            alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                @Override
                                public void onPositiveClick() {
                                    if (gpsTracker.canGetLocation()) {
                                        //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                        Log.e(MyEnergyListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                    }
                                }
                            }).show();
                        }
                    }
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValiadtion() {

        String CustomerId = mEbProcessTextViewCustomerVal.getText().toString().trim();
        String CircleId = mEbProcessTextViewCircleVal.getText().toString().trim();
        String stateId = mEbProcessTextViewStateVal.getText().toString().trim();
        String SSAId = mEbProcessTextViewSsaVal.getText().toString().trim();
        String SiteId = mEbProcessTextViewSiteVal.getText().toString().trim();
        String EBServiceProvider = mEbProcessTextViewEbServiceProviderVal.getText().toString().trim();
        String EBConsumerNumber = mEbProcessTextViewEbConsumerNumberVal.getText().toString().trim();
        String ModeOfPayment = mEbProcessTextViewTypeModeOfPayementVal.getText().toString().trim();
        String TypeofElectricConnection = mEbProcessTextViewTypeOfElectricConnectionVal.getText().toString().trim();
        String Tariff = mEbProcessTextViewTariffVal.getText().toString().trim();
        String UnitsConsumed = mEbProcessEditTextUnitConsumed.getText().toString().trim();
        String BillingPeriodFrom = mEbProcessEditTextBillingFrom.getText().toString().trim();
        String BillingPeriodTo = mEbProcessEditTextBillingTo.getText().toString().trim();
        /*String BillingPeriodFrom = date_BillFrom;
        String BillingPeriodTo = date_BillTo;*/
        String ReceiptNumber = mEbProcessEditTextBillNo.getText().toString().trim();
        String BillIssueDate = mEbProcessEditTextBillingIssueDate.getText().toString().trim();
        String BillDueDate = mEbProcessEditTextBilliDueDate.getText().toString().trim();
       /* String BillIssueDate = date_issue;
        String BillDueDate = date_due;*/
        String GrossAmount = mEbProcessEditTextGrossAmount.getText().toString().trim();
        String NetPayableOnOrBeforeDueDate = mEbProcessEditTextNetPaybleBeforeDueDate.getText().toString().trim();
        String VendorSAPId = "";
        String EbBillScanCopyImageName = base64StringBillUpload;

        Double netPayAmt = 0.0, grossPayAmt = 0.0;
        if (!NetPayableOnOrBeforeDueDate.isEmpty()) {
            netPayAmt = Double.parseDouble(NetPayableOnOrBeforeDueDate);
        }
        if (!GrossAmount.isEmpty()) {
            grossPayAmt = Double.parseDouble(GrossAmount);
        }


        if (CustomerId.isEmpty() || CustomerId == null || CustomerId.equals("-")) {
            showToast("Customer  not found");
            return false;
        } else if (SiteId.isEmpty() || SiteId == null || SiteId.equals("-")) {
            showToast("Select Site");
            return false;
        } else if (CircleId.isEmpty() || CircleId == null || CircleId.equals("-")) {
            showToast("Circle  not found");
            return false;
        } else if (stateId.isEmpty() || stateId == null || stateId.equals("-")) {
            showToast("State  not found");
            return false;
        } else if (SSAId.isEmpty() || SSAId == null || SSAId.equals("-")) {
            showToast("SSA not found");
            return false;
        } else if (EBServiceProvider.isEmpty() || EBServiceProvider == null || EBServiceProvider.equals("-")) {
            showToast("EB Service Provider not found");
            return false;
        } else if (EBConsumerNumber.isEmpty() || EBConsumerNumber == null) {
            showToast("Select EB Consumer Number ");
            return false;
        } else if (ModeOfPayment.isEmpty() || ModeOfPayment == null) {
            showToast("Select Mode of Payment ");
            return false;
        } else if (TypeofElectricConnection.isEmpty() || TypeofElectricConnection == null) {
            showToast("Select Type of Electric Connection ");
            return false;
        } else if (Tariff.isEmpty() || Tariff == null) {
            showToast("Select Tariff ");
            return false;
        } else if (UnitsConsumed.isEmpty() || UnitsConsumed == null) {
            showToast("Enter Units Consumed ");
            return false;
        } else if (BillingPeriodFrom.isEmpty() || BillingPeriodFrom == null) {
            showToast("Select Billing Period From ");
            return false;
        } else if (BillingPeriodTo.isEmpty() || BillingPeriodTo == null) {
            showToast("Select Billing Period To ");
            return false;
        } else if (ReceiptNumber.isEmpty() || ReceiptNumber == null) {
            showToast("Enter Bill Number ");
            return false;
        } else if (BillIssueDate.isEmpty() || BillIssueDate == null) {
            showToast("Select Bill Issue Date ");
            return false;
        } else if (BillDueDate.isEmpty() || BillDueDate == null) {
            showToast("Select Bill Due Date ");
            return false;
        } else if (GrossAmount.isEmpty() || GrossAmount == null) {
            showToast("Enter Gross Amount ");
            return false;
        } else if (NetPayableOnOrBeforeDueDate.isEmpty() || NetPayableOnOrBeforeDueDate == null) {
            showToast("Enter Net Payable On Or Before Due Date");
            return false;
        } else if (base64StringBillUpload.isEmpty() || base64StringBillUpload == null) {
            showToast("Upload EB Bill Scan Copy");
            return false;
        } else if (!dateFromToValid(date_BillFrom, date_BillTo)) {
            showToast("Bill To Date should greater than Bill From Date");
            return false;
        } else if (netPayAmt > grossPayAmt) {
            showToast("Net Payable Amt Should Be Less Than Gross Amt");
            return false;
        } else
            return true;


    }

    private boolean dateFromToValid(String startdate, String enddate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date_1 = new Date();
        Date date_2 = new Date();
        try {
            date_1 = dateFormat.parse(startdate);
            date_2 = dateFormat.parse(enddate);
            if (date_2.after(date_1)) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e) {

            e.printStackTrace();

        }
        return false;
    }


    private void setInputDetails() {
        try {
          /*  if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                Gson gson = new Gson();
                mDieselFillingTextViewSiteNameVal.setText(dieselFillingData.getSiteName());
                mDieselFillingTextViewSiteDetailsVal.setText(dieselFillingData.getSiteDetails());
                mDieselFillingTextViewSiteIDVal.setText(dieselFillingData.getSiteID());
                mDieselFillingTextViewSelectDgIdQrCodeVal.setText(dieselFillingData.getSelectDgIdQrCode());
                mDieselFillingEditTextPresentDgHmr.setText(dieselFillingData.getPresentDgHmr());
                base64StringHmrPhoto= dieselFillingData.getHmrPhotoUpload();
                mDieselFillingEditTextTankBalanceBeforeFilling.setText(dieselFillingData.getTankBalanceBeforeFilling());
                mDieselFillingEditTextFillingQty.setText(dieselFillingData.getFillingQty());
                mDieselFillingTextViewFinalDieselStockVal.setText(dieselFillingData.getFinalDieselStock());
                mDieselFillingEditTextPresentEbReading.setText(dieselFillingData.getPesentEbReading());
                base64StringEbReadingKwh =dieselFillingData.getEbReadingKwhPhoto();
            } else {
                Toast.makeText(DieselFilling.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Confirmation", "Do you want to submit this ticket?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {

                submitDetails();

            }

            @Override
            public void onNegativeClick() {

            }
        }).show();

    }


    private void submitDetails() {

        try {
            showBusyProgress();
            String userId = sessionManager.getSessionUserId();
            String accessToken = sessionManager.getSessionDeviceToken();

            String CustomerId = String.valueOf(customerId);
            String CircleId = String.valueOf(circleId);
            String stateId = String.valueOf(StateId);
            String SSAId = String.valueOf(ssaID);
            String SiteId = String.valueOf(siteID);
            String EBServiceProvider = mEbProcessTextViewEbServiceProviderVal.getText().toString().trim();
            String EBConsumerNumber = mEbProcessTextViewEbConsumerNumberVal.getText().toString().trim();
            String ModeOfPayment = mEbProcessTextViewTypeModeOfPayementVal.getText().toString().trim();
            String TypeofElectricConnection = mEbProcessTextViewTypeOfElectricConnectionVal.getText().toString().trim();
            String Tariff = mEbProcessTextViewTariffVal.getText().toString().trim();
            String UnitsConsumed = mEbProcessEditTextUnitConsumed.getText().toString().trim();
            String BillingPeriodFrom = mEbProcessEditTextBillingFrom.getText().toString().trim();
            String BillingPeriodTo = mEbProcessEditTextBillingTo.getText().toString().trim();
            String ReceiptNumber = mEbProcessEditTextBillNo.getText().toString().trim();
            String BillIssueDate = mEbProcessEditTextBillingIssueDate.getText().toString().trim();
            ;
            String BillDueDate = mEbProcessEditTextBilliDueDate.getText().toString().trim();
            String GrossAmount = mEbProcessEditTextGrossAmount.getText().toString().trim();
            String NetPayableOnOrBeforeDueDate = mEbProcessEditTextNetPaybleBeforeDueDate.getText().toString().trim();
            String VendorSAPId = "";
            String EbBillScanCopyImageName = base64StringBillUpload;


            electricBillProcessData = new ElectricBillProcessData(userId, accessToken, CustomerId, CircleId, stateId, SSAId, SiteId, EBServiceProvider, EBConsumerNumber, ModeOfPayment, TypeofElectricConnection, Tariff, UnitsConsumed, BillingPeriodFrom, BillingPeriodTo, ReceiptNumber, BillIssueDate, BillDueDate, GrossAmount, NetPayableOnOrBeforeDueDate, VendorSAPId, EbBillScanCopyImageName);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(electricBillProcessData);

            //offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

            GsonRequest<EBlSubmitResposeData> eBlSubmitResposeDataGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.SubmitElectricityBillTicketList, jsonString, EBlSubmitResposeData.class,
                    new Response.Listener<EBlSubmitResposeData>() {
                        @Override
                        public void onResponse(EBlSubmitResposeData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getError() != null) {
                                    showToast(response.getError().getErrorMessage());
                                } else {
                                    if (response.getSuccess() == 1) {

                                        setResult(RESULT_OK);
                                        showToast("Record submitted successfully.");
                                        finish();
                                    } else {

                                        showToast("Something went wrong");
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            eBlSubmitResposeDataGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            eBlSubmitResposeDataGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(eBlSubmitResposeDataGsonRequest, "eBlSubmitResposeDataGsonRequest");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void prepareCustomer() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());

            GsonRequest<Customer> getCustomer = new GsonRequest<>(Request.Method.POST, Constants.GetCustomer, jo.toString(), Customer.class,
                    new Response.Listener<Customer>() {
                        @Override
                        public void onResponse(Customer response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    customer = response;

                                    if (customer.getCustomerList().size() > 0) {

                                        final ArrayList<String> cust = new ArrayList<String>();
                                        for (CustomerList site : customer.getCustomerList()) {
                                            cust.add(site.getCustomerName());
                                        }

                                        SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                                                cust,
                                                "Select Customer",
                                                "Close", "#000000");
                                        searchableSpinnerDialog.showSearchableSpinnerDialog();

                                        searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                            @Override
                                            public void onClick(ArrayList<String> item, int position) {

                                                str_customerName = item.get(position);
                                                if (!mEbProcessTextViewCustomerVal.getText().equals(str_customerName)) {
                                                    mEbProcessTextViewCustomerVal.setText(str_customerName);
                                                    customerId = Integer.valueOf(customer.getCustomerList().get(position).getId());

                                                    mEbProcessTextViewCircleVal.setText("");
                                                    mEbProcessTextViewStateVal.setText("");
                                                    mEbProcessTextViewSsaVal.setText("");
                                                    mEbProcessTextViewSiteVal.setText("");
                                                    ConsumerNoList = null;
                                                    PaymentTypeList = null;
                                                    ElectricConnectionTypeList = null;
                                                    ConnectionTariffList = null;
                                                    mEbProcessTextViewEbConsumerNumberVal.setText("");
                                                    mEbProcessTextViewTypeModeOfPayementVal.setText("");
                                                    mEbProcessTextViewTypeOfElectricConnectionVal.setText("");
                                                    mEbProcessTextViewTariffVal.setText("");
                                                    mEbProcessTextViewSiteIDVal.setText("");
                                                    mEbProcessTextViewSiteDetailsVal.setText("");
                                                    mEbProcessTextViewEbServiceProviderVal.setText("");
                                                }

                                            }
                                        });


                                    } else {
                                        mEbProcessTextViewCustomerVal.setText("No Customer Found");
                                        //No sites found
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            getCustomer.setRetryPolicy(Application.getDefaultRetryPolice());
            getCustomer.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getCustomer, "getCustomer");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }


    private void prepareCircle() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("CustomerId", customerId);


            GsonRequest<Circle> getCircleRequest = new GsonRequest<>(Request.Method.POST, Constants.GetCircle, jo.toString(), Circle.class,
                    new Response.Listener<Circle>() {
                        @Override
                        public void onResponse(Circle response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    circle = response;

                                    if (circle.getCircleList().size() > 0) {

                                        final ArrayList<String> circ = new ArrayList<String>();
                                        for (CircleList circleList : circle.getCircleList()) {
                                            circ.add(circleList.getCircleName());
                                        }

                                        SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                                                circ,
                                                "Select Circle",
                                                "Close", "#000000");
                                        searchableSpinnerDialog.showSearchableSpinnerDialog();

                                        searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                            @Override
                                            public void onClick(ArrayList<String> item, int position) {

                                                str_circleName = item.get(position);
                                                if (!mEbProcessTextViewCircleVal.getText().equals(str_circleName)) {
                                                    mEbProcessTextViewCircleVal.setText(str_circleName);
                                                    circleId = Integer.valueOf(circle.getCircleList().get(position).getId());

                                                    mEbProcessTextViewStateVal.setText("");
                                                    mEbProcessTextViewSsaVal.setText("");
                                                    mEbProcessTextViewSiteVal.setText("");
                                                    ConsumerNoList = null;
                                                    PaymentTypeList = null;
                                                    ElectricConnectionTypeList = null;
                                                    ConnectionTariffList = null;
                                                    mEbProcessTextViewEbConsumerNumberVal.setText("");
                                                    mEbProcessTextViewTypeModeOfPayementVal.setText("");
                                                    mEbProcessTextViewTypeOfElectricConnectionVal.setText("");
                                                    mEbProcessTextViewTariffVal.setText("");
                                                    mEbProcessTextViewSiteIDVal.setText("");
                                                    mEbProcessTextViewSiteDetailsVal.setText("");
                                                    mEbProcessTextViewEbServiceProviderVal.setText("");
                                                }

                                            }
                                        });


                                    } else {
                                        mEbProcessTextViewCircleVal.setText("No Site Found");
                                        //No sites found
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            getCircleRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getCircleRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getCircleRequest, "getCircleRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    private void prepareStates() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("CircleId", circleId);


            GsonRequest<State> getStateRequest = new GsonRequest<>(Request.Method.POST, Constants.GetState, jo.toString(), State.class,
                    new Response.Listener<State>() {
                        @Override
                        public void onResponse(State response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    state = response;

                                    if (state.getStateList().size() > 0) {

                                        final ArrayList<String> stateArray = new ArrayList<String>();
                                        for (StateList stateList : state.getStateList()) {
                                            stateArray.add(stateList.getStateName());
                                        }

                                        SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                                                stateArray,
                                                "Select State",
                                                "Close", "#000000");
                                        searchableSpinnerDialog.showSearchableSpinnerDialog();

                                        searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                            @Override
                                            public void onClick(ArrayList<String> item, int position) {

                                                str_stateName = item.get(position);
                                                if (!mEbProcessTextViewStateVal.getText().equals(str_stateName)) {
                                                    mEbProcessTextViewStateVal.setText(str_stateName);
                                                    StateId = Integer.valueOf(state.getStateList().get(position).getId());

                                                    mEbProcessTextViewSsaVal.setText("");
                                                    mEbProcessTextViewSiteVal.setText("");
                                                    ConsumerNoList = null;
                                                    PaymentTypeList = null;
                                                    ElectricConnectionTypeList = null;
                                                    ConnectionTariffList = null;
                                                    mEbProcessTextViewEbConsumerNumberVal.setText("");
                                                    mEbProcessTextViewTypeModeOfPayementVal.setText("");
                                                    mEbProcessTextViewTypeOfElectricConnectionVal.setText("");
                                                    mEbProcessTextViewTariffVal.setText("");
                                                    mEbProcessTextViewSiteIDVal.setText("");
                                                    mEbProcessTextViewSiteDetailsVal.setText("");
                                                    mEbProcessTextViewEbServiceProviderVal.setText("");
                                                }

                                            }
                                        });


                                    } else {
                                        mEbProcessTextViewStateVal.setText("No State Found");
                                        //No sites found
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            getStateRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getStateRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getStateRequest, "getStateRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    private void prepareSSA() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("StateId", StateId);


            GsonRequest<SSA> getSsaRequest = new GsonRequest<>(Request.Method.POST, Constants.GetSSA, jo.toString(), SSA.class,
                    new Response.Listener<SSA>() {
                        @Override
                        public void onResponse(SSA response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    ssa = response;

                                    if (ssa.getSSAList().size() > 0) {

                                        final ArrayList<String> ssaArray = new ArrayList<String>();
                                        for (SSAList ssaList : ssa.getSSAList()) {
                                            ssaArray.add(ssaList.getSsaName());
                                        }

                                        SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                                                ssaArray,
                                                "Select SSA",
                                                "Close", "#000000");
                                        searchableSpinnerDialog.showSearchableSpinnerDialog();

                                        searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                            @Override
                                            public void onClick(ArrayList<String> item, int position) {
                                                str_ssa = item.get(position);
                                                if (!mEbProcessTextViewSsaVal.getText().equals(str_ssa)) {

                                                    mEbProcessTextViewSsaVal.setText(str_ssa);
                                                    ssaID = Integer.valueOf(ssa.getSSAList().get(position).getId());

                                                    mEbProcessTextViewSiteVal.setText("");
                                                    ConsumerNoList = null;
                                                    PaymentTypeList = null;
                                                    ElectricConnectionTypeList = null;
                                                    ConnectionTariffList = null;
                                                    mEbProcessTextViewEbConsumerNumberVal.setText("");
                                                    mEbProcessTextViewTypeModeOfPayementVal.setText("");
                                                    mEbProcessTextViewTypeOfElectricConnectionVal.setText("");
                                                    mEbProcessTextViewTariffVal.setText("");
                                                    mEbProcessTextViewSiteIDVal.setText("");
                                                    mEbProcessTextViewSiteDetailsVal.setText("");
                                                    mEbProcessTextViewEbServiceProviderVal.setText("");
                                                }

                                            }
                                        });


                                    } else {
                                        mEbProcessTextViewSsaVal.setText("No SSA Found");
                                        //No sites found
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            getSsaRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getSsaRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getSsaRequest, "getSsaRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    private void prepareSite() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("IsEbProcess", "1");
            //jo.put("SSAId", ssaID);

            //GetSite old web service
            GsonRequest<Site> getSiteRequest = new GsonRequest<>(Request.Method.POST, Constants.GetUserSites, jo.toString(), Site.class,
                    new Response.Listener<Site>() {
                        @Override
                        public void onResponse(Site response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    site = response;

                                    if (site.getSiteList().size() > 0) {
                                        siteArray = new ArrayList<String>();
                                        for (SiteList siteList : site.getSiteList()) {
                                            siteArray.add(siteList.getSiteName());
                                        }
                                        //siteArray.sort(String::compareToIgnoreCase);
                                        ////Collections.sort(siteArray, String.CASE_INSENSITIVE_ORDER); by 008
                                    /*SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ElectricBillProcess.this,
                                            siteArray,
                                            "Select Site",
                                            "Close", "#000000");
                                    searchableSpinnerDialog.showSearchableSpinnerDialog();

                                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                        @Override
                                        public void onClick(ArrayList<String> item, int position) {

                                            str_siteName = item.get(position);
                                            mEbProcessTextViewSiteVal.setText(str_siteName);
                                            siteID = Integer.valueOf(site.getSiteList().get(position).getId());
                                            mEbProcessTextViewSiteIDVal.setText(site.getSiteList().get(position).getSiteId());
                                            String siteAddress = String.valueOf(site.getSiteList().get(position).getSiteAddress());
                                            if (!siteAddress.isEmpty()) {
                                                mEbProcessTextViewSiteDetailsVal.setText(String.valueOf(site.getSiteList().get(position).getSiteAddress()));
                                            }
                                            mEbProcessTextViewEbServiceProviderVal.setText(String.valueOf(site.getSiteList().get(position).getEbOfficeName()));
                                            prepareEbSiteConnectedData();
                                        }
                                    });
*/

                                    } else {
                                        mEbProcessTextViewSiteIDVal.setText("No Site Found");
                                        //No sites found
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            getSiteRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getSiteRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getSiteRequest, "getSiteRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }


    private void prepareEbSiteConnectedData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("SiteId", siteID);


            GsonRequest<EbSiteConnectedData> getEbSiteConnectedData = new GsonRequest<>(Request.Method.POST, Constants.GetSiteSelectConnectiondata, jo.toString(), EbSiteConnectedData.class,
                    new Response.Listener<EbSiteConnectedData>() {
                        @Override
                        public void onResponse(EbSiteConnectedData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    ebSiteConnectedData = response;

                                    ConsumerNoList = ebSiteConnectedData.getConsumerNoList();
                                    //Collections.sort(ConsumerNoList, String.CASE_INSENSITIVE_ORDER); by 008

                                    PaymentTypeList = ebSiteConnectedData.getPaymentTypeList();
                                    //Collections.sort(PaymentTypeList, String.CASE_INSENSITIVE_ORDER);

                                    ElectricConnectionTypeList = ebSiteConnectedData.getElectricConnectionTypeList();
                                    //Collections.sort(ElectricConnectionTypeList, String.CASE_INSENSITIVE_ORDER);

                                    ConnectionTariffList = ebSiteConnectedData.getConnectionTariffList();
                                    //Collections.sort(ConnectionTariffList, String.CASE_INSENSITIVE_ORDER);

                                } else {
                                    showToast("No Data Found Of Selected Site");

                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            getEbSiteConnectedData.setRetryPolicy(Application.getDefaultRetryPolice());
            getEbSiteConnectedData.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getEbSiteConnectedData, "getEbSiteConnectedData");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (imageFileUri != null) {
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                        byte[] bitmapDataArray = stream.toByteArray();
                        base64StringBillUpload = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                        mEbProcessButtonEbBillScanCopyiew.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                imageFileName = "";
                imageFileUri = null;
                mEbProcessButtonEbBillScanCopyiew.setVisibility(View.GONE);
            }
        }
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void prepareUserPersonalData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());


            GsonRequest<UserDetailsParent> userProfileRequestGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.GetUserDetails, jo.toString(), UserDetailsParent.class,
                    new Response.Listener<UserDetailsParent>() {
                        @Override
                        public void onResponse(@NonNull UserDetailsParent response) {

                            if (response.getSuccess() == 1) {
                                /*sessionManager.updateSessionUsername(response.getUserDetails().getUsername());
                                sessionManager.updateSessionUserID(response.getUserDetails().getId());
                                sessionManager.updateSessionUserFirstName(response.getUserDetails().getFirstName());
                                sessionManager.updateSessionUserLastName(response.getUserDetails().getLastName());
                                sessionManager.updateSessionUserEmail(response.getUserDetails().getEmail());
                                sessionManager.updateSessionMobileNo(response.getUserDetails().getMobileNo());
                                sessionManager.updateSessionDesignation(response.getUserDetails().getDesignation());
                                sessionManager.updateSessionProfileImageUrl(response.getUserDetails().getProfileImageUrl());
                                sessionManager.updateSessionCircle(response.getUserDetails().getUserAdditionalDetails().getCircleName());
                                sessionManager.updateSessionState(response.getUserDetails().getUserAdditionalDetails().getStateName());
                                sessionManager.updateSessionSsa(response.getUserDetails().getUserAdditionalDetails().getSsaName());*/

                                ConsumerNoList = null;
                                PaymentTypeList = null;
                                ElectricConnectionTypeList = null;
                                ConnectionTariffList = null;
                                mEbProcessTextViewEbConsumerNumberVal.setText("");
                                mEbProcessTextViewTypeModeOfPayementVal.setText("");
                                mEbProcessTextViewTypeOfElectricConnectionVal.setText("");
                                mEbProcessTextViewTariffVal.setText("");

                                str_customerName = response.getUserDetails().getUserAdditionalDetails().getCustomerName();
                                mEbProcessTextViewCustomerVal.setText(str_customerName);
                                customerId = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getCustomerId());

                                /*str_circleName = response.getUserDetails().getUserAdditionalDetails().getCircleName();
                                mEbProcessTextViewCircleVal.setText(str_circleName);
                                circleId = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getCircleId());

                                str_stateName = response.getUserDetails().getUserAdditionalDetails().getStateName();
                                mEbProcessTextViewStateVal.setText(str_stateName);
                                StateId = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getStateId());

                                str_ssa = response.getUserDetails().getUserAdditionalDetails().getSsaName();
                                mEbProcessTextViewSsaVal.setText(str_ssa);
                                ssaID = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getSsaId());*/
                                //on 19022019


                                //str_siteName = response.getUserDetails().getUserAdditionalDetails().getSiteName();
                                //mEbProcessTextViewSiteVal.setText(str_siteName);
                                //siteID = Integer.valueOf(response.getUserDetails().getUserAdditionalDetails().getSiteId());
                                //mEbProcessTextViewSiteIDVal.setText(response.getUserDetails().getUserAdditionalDetails().getSiteId());
                                //String siteAddress = String.valueOf(response.getUserDetails().getUserAdditionalDetails().getSiteAddress());

                                //if (!siteAddress.isEmpty()) {
                                //    mEbProcessTextViewSiteDetailsVal.setText(siteAddress);
                                //}

                                //19022019 by 008 mEbProcessTextViewEbServiceProviderVal.setText(String.valueOf(response.getUserDetails().getUserAdditionalDetails().getEbOfficeName()));
                                prepareEbSiteConnectedData();
                                prepareSite();
                                hideBusyProgress();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        showToast("No Internet Connection.");
                    }
                    hideBusyProgress();

                }
            });
            userProfileRequestGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            userProfileRequestGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(userProfileRequestGsonRequest, "ebPaymentRequestGsonRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }
}