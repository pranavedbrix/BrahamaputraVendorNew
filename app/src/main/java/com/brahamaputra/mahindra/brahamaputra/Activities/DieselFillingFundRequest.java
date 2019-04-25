package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.DgIdQrCodeList;
import com.brahamaputra.mahindra.brahamaputra.Data.DgLastReadingsForDieselRequest;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselFillingFundRequestData;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerBackupsDGDataList;
import com.brahamaputra.mahindra.brahamaputra.Data.Site;
import com.brahamaputra.mahindra.brahamaputra.Data.UserSites;
import com.brahamaputra.mahindra.brahamaputra.Data.UserSitesList;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DieselFillingFundRequest extends BaseActivity {

    private static final String TAG = DieselFillingFundRequest.class.getSimpleName();

    private TextView mDieselFillingFundRequestTextViewCustomer;
    private TextView mDieselFillingFundRequestTextViewCustomerVal;
    private TextView mDieselFillingFundRequestTextViewCircle;
    private TextView mDieselFillingFundRequestTextViewCircleVal;
    private TextView mDieselFillingFundRequestTextViewState;
    private TextView mDieselFillingFundRequestTextViewStateVal;
    private TextView mDieselFillingFundRequestTextViewSsa;
    private TextView mDieselFillingFundRequestTextViewSsaVal;
    private TextView mDieselFillingFundRequestTextViewSiteName;
    private TextView mDieselFillingFundRequestTextViewSiteNameVal;
    private TextView mDieselFillingFundRequestTextViewSiteId;
    private TextView mDieselFillingFundRequestTextViewSiteIdVal;
    private TextView mDieselFillingFundRequestTextViewSourceOfPower;
    private TextView mDieselFillingFundRequestTextViewSourceOfPowerVal;
    private TextView mDieselFillingFundRequestTextViewCardSupplier;
    private TextView mDieselFillingFundRequestTextViewCardSupplierVal;
    private TextView mDieselFillingFundRequestTextViewChildCardNumber;
    private TextView mDieselFillingFundRequestTextViewChildCardNumberVal;

    private TextView mDieselFillingFundRequestTextViewDgQrCode;
    private TextView mDieselFillingFundRequestTextViewDgQrCodeVal;
    private TextView mDieselFillingFundRequestTextViewDgMake;
    private TextView mDieselFillingFundRequestTextViewDgMakeVal;
    private TextView mDieselFillingFundRequestTextViewDgCapacityInKva;
    private TextView mDieselFillingFundRequestTextViewDgCapacityInKvaVal;

    private TextView mDieselFillingFundRequestTextViewLastDieselFillingDate;
    private TextView mDieselFillingFundRequestTextViewLastDieselFillingDateVal;
    private TextView mDieselFillingFundRequestTextViewLastDieselStock;
    private TextView mDieselFillingFundRequestTextViewLastDieselStockVal;
    private TextView mDieselFillingFundRequestTextViewLastDgHmr;
    private TextView mDieselFillingFundRequestTextViewLastDgHmrVal;
    private TextView mDieselFillingFundRequestTextViewLastEbReading;
    private TextView mDieselFillingFundRequestTextViewLastEbReadingVal;
    private TextView mDieselFillingFundRequestTextViewPresentDgHmr;
    private EditText mDieselFillingFundRequestEditTextPresentDgHmr;
    private TextView mDieselFillingFundRequestTextViewHmrPhotoUpload;
    private ImageView mDieselFillingFundRequestButtonHmrPhotoUpload;
    private ImageView mDieselFillingFundRequestButtonHmrPhotoUploadView;
    private TextView mDieselFillingFundRequestTextViewPresentDieselStock;
    private EditText mDieselFillingFundRequestEditTextPresentDieselStock;
    private TextView mDieselFillingFundRequestTextViewPresentEbReading;
    private EditText mDieselFillingFundRequestEditTextPresentEbReading;
    private TextView mDieselFillingFundRequestTextViewPresentEbReadingKwhPhoto;
    private ImageView mDieselFillingFundRequestButtonPresentEbReadingKwhPhoto;
    private ImageView mDieselFillingFundRequestButtonPresentEbReadingKwhPhotoView;
    private TextView mDieselFillingFundRequestTextViewPresentDateTime;
    private EditText mDieselFillingFundRequestEditTextPresentDateTime;
    private ImageView mDieselFillingFundRequestButtonPresentDateTime;
    private TextView mDieselFillingFundRequestTextViewDieselQuantityRequired;
    private EditText mDieselFillingFundRequestEditTextDieselQuantityRequired;


    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketName = "";
    private String ticketId = "";
    private SessionManager sessionManager;
    private DieselFillingFundRequestData dieselFillingFundRequestData;
    final Calendar myCalendar = Calendar.getInstance();

    private UserSitesList userSitesList;
    private DgIdQrCodeList dgIdQrCodeList;

    private Uri HmrPhoto_imageFileUri = null;
    private Uri EbReadingKwh_imageFileUri = null;

    private String HmrPhoto_imageFileName = "";
    private String EbReadingKwh_imageFileName = "";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_HmrPhotoUpload = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_PresentEbReadingKwhPhoto = 102;

    private String base64StringHmrPhotoUpload = "";
    private String base64StringPresentEbReadingKwhPhoto = "";

    private String imageFileHmrPhotoUpload;
    private String imageFilePresentEbReadingKwhPhoto;

    private Uri imageFileUriHmrPhotoUpload = null;
    private Uri imageFileUriPresentEbReadingKwhPhoto = null;

    //////////

    public GPSTracker gpsTracker;
    private AlertDialogManager alertDialogManager;

    DecimalConversion decimalConversion;

    private ArrayList<String> siteArray;
    private Site site;
    public int siteDbId = 0;
    public double siteLongitude = 0;
    public double siteLatitude = 0;

    private String Str_Date = "", Str_Time = "";
    private ArrayList<String> dgMakeList;
    private ArrayList<String> dgCapacityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel_filling_fund_request);
        this.setTitle("Diesel Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gpsTracker = new GPSTracker(DieselFillingFundRequest.this);

        decimalConversion = new DecimalConversion();
        sessionManager = new SessionManager(DieselFillingFundRequest.this);
        alertDialogManager = new AlertDialogManager(DieselFillingFundRequest.this);
        userSitesList = new UserSitesList();
        dgIdQrCodeList = new DgIdQrCodeList();
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(DieselFillingFundRequest.this, userId, "DieselFundRequest");

        assignViews();
        initCombo();
        set_listener();
        setSessionValuesToFields();
        prepareUserSites(true);

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

        mDieselFillingFundRequestEditTextPresentDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(DieselFillingFundRequest.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();

            }
        });

        mDieselFillingFundRequestEditTextPresentDieselStock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

        mDieselFillingFundRequestEditTextDieselQuantityRequired.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

    }

    private void assignViews() {
        mDieselFillingFundRequestTextViewCustomer = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_customer);
        mDieselFillingFundRequestTextViewCustomerVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_customerVal);
        mDieselFillingFundRequestTextViewCircle = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_circle);
        mDieselFillingFundRequestTextViewCircleVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_circleVal);
        mDieselFillingFundRequestTextViewState = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_state);
        mDieselFillingFundRequestTextViewStateVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_stateVal);
        mDieselFillingFundRequestTextViewSsa = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_ssa);
        mDieselFillingFundRequestTextViewSsaVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_ssaVal);
        mDieselFillingFundRequestTextViewSiteName = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_siteName);
        mDieselFillingFundRequestTextViewSiteNameVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_siteNameVal);
        mDieselFillingFundRequestTextViewSiteId = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_siteId);
        mDieselFillingFundRequestTextViewSiteIdVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_siteIdVal);
        mDieselFillingFundRequestTextViewSourceOfPower = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_sourceOfPower);
        mDieselFillingFundRequestTextViewSourceOfPowerVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_sourceOfPowerVal);
        mDieselFillingFundRequestTextViewCardSupplier = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_cardSupplier);
        mDieselFillingFundRequestTextViewCardSupplierVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_cardSupplierVal);
        mDieselFillingFundRequestTextViewChildCardNumber = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_childCardNumber);
        mDieselFillingFundRequestTextViewChildCardNumberVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_childCardNumberVal);

        /*mDieselFillingFundRequestTextViewDgMake;
        mDieselFillingFundRequestTextViewDgMakeVal;
        mDieselFillingFundRequestTextViewDgCapacityInKva;
        mDieselFillingFundRequestTextViewDgCapacityInKvaVal;*/

        mDieselFillingFundRequestTextViewDgMake = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_dgMake);
        mDieselFillingFundRequestTextViewDgMakeVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_dgMakeVal);
        mDieselFillingFundRequestTextViewDgCapacityInKva = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_dgCapacityInKva);
        mDieselFillingFundRequestTextViewDgCapacityInKvaVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_dgCapacityInKvaVal);


        mDieselFillingFundRequestTextViewLastDieselFillingDate = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastDieselFillingDate);
        mDieselFillingFundRequestTextViewLastDieselFillingDateVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastDieselFillingDateVal);
        mDieselFillingFundRequestTextViewLastDieselStock = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastDieselStock);
        mDieselFillingFundRequestTextViewLastDieselStockVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastDieselStockVal);
        mDieselFillingFundRequestTextViewLastDgHmr = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastDgHmr);
        mDieselFillingFundRequestTextViewLastDgHmrVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastDgHmrVal);
        mDieselFillingFundRequestTextViewLastEbReading = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastEbReading);
        mDieselFillingFundRequestTextViewLastEbReadingVal = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_lastEbReadingVal);
        mDieselFillingFundRequestTextViewPresentDgHmr = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_presentDgHmr);
        mDieselFillingFundRequestEditTextPresentDgHmr = (EditText) findViewById(R.id.dieselFillingFundRequest_editText_presentDgHmr);
        mDieselFillingFundRequestTextViewHmrPhotoUpload = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_hmrPhotoUpload);
        mDieselFillingFundRequestButtonHmrPhotoUpload = (ImageView) findViewById(R.id.dieselFillingFundRequest_button_hmrPhotoUpload);
        mDieselFillingFundRequestButtonHmrPhotoUploadView = (ImageView) findViewById(R.id.dieselFillingFundRequest_button_hmrPhotoUploadView);
        mDieselFillingFundRequestTextViewPresentDieselStock = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_presentDieselStock);
        mDieselFillingFundRequestEditTextPresentDieselStock = (EditText) findViewById(R.id.dieselFillingFundRequest_editText_presentDieselStock);
        mDieselFillingFundRequestTextViewPresentEbReading = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_presentEbReading);
        mDieselFillingFundRequestEditTextPresentEbReading = (EditText) findViewById(R.id.dieselFillingFundRequest_editText_presentEbReading);
        mDieselFillingFundRequestTextViewPresentEbReadingKwhPhoto = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_presentEbReadingKwhPhoto);
        mDieselFillingFundRequestButtonPresentEbReadingKwhPhoto = (ImageView) findViewById(R.id.dieselFillingFundRequest_button_presentEbReadingKwhPhoto);
        mDieselFillingFundRequestButtonPresentEbReadingKwhPhotoView = (ImageView) findViewById(R.id.dieselFillingFundRequest_button_presentEbReadingKwhPhotoView);
        mDieselFillingFundRequestTextViewPresentDateTime = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_presentDateTime);
        mDieselFillingFundRequestEditTextPresentDateTime = (EditText) findViewById(R.id.dieselFillingFundRequest_editText_presentDateTime);
        mDieselFillingFundRequestButtonPresentDateTime = (ImageView) findViewById(R.id.dieselFillingFundRequest_button_presentDateTime);


        mDieselFillingFundRequestTextViewDieselQuantityRequired = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_dieselQuantityRequired);
        mDieselFillingFundRequestEditTextDieselQuantityRequired = (EditText) findViewById(R.id.dieselFillingFundRequest_editText_dieselQuantityRequired);

        /*mDieselFillingEditTextTankBalanceBeforeFilling.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mDieselFillingEditTextFillingQty.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mDieselFillingEditTextDieselPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});

        mDieselFillingTextViewSiteIDVal.setAllCaps(true);
        mDieselFillingTextViewSiteDetailsVal.setAllCaps(true);
        mDieselFillingTextViewFinalDieselStockVal.setAllCaps(true);*/


        mDieselFillingFundRequestEditTextPresentDieselStock.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mDieselFillingFundRequestEditTextDieselQuantityRequired.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {
    }

    public void DecimalFormatConversion() {
        mDieselFillingFundRequestEditTextPresentDieselStock.setText(decimalConversion.convertDecimal(mDieselFillingFundRequestEditTextPresentDieselStock.getText().toString()));
        mDieselFillingFundRequestEditTextDieselQuantityRequired.setText(decimalConversion.convertDecimal(mDieselFillingFundRequestEditTextDieselQuantityRequired.getText().toString()));
        /*mDieselFillingEditTextFillingQty.setText(decimalConversion.convertDecimal(mDieselFillingEditTextFillingQty.getText().toString()));
        mDieselFillingEditTextTankBalanceBeforeFilling.setText(decimalConversion.convertDecimal(mDieselFillingEditTextTankBalanceBeforeFilling.getText().toString()));
        mDieselFillingTextViewFinalDieselStockVal.setText(decimalConversion.convertDecimal(mDieselFillingTextViewFinalDieselStockVal.getText().toString()));
        mDieselFillingEditTextDieselPrice.setText(decimalConversion.convertDecimal(mDieselFillingEditTextDieselPrice.getText().toString()));*/
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //mDieselFillingFundRequestEditTextPresentDateTime.setText(sdf.format(myCalendar.getTime()));

        Str_Date = "";
        Str_Time = "";

        if (mDieselFillingFundRequestEditTextPresentDateTime.getText().length() > 1) {
            Str_Time = mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().substring(mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().indexOf(" ") + 1, mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().length());
            Str_Date = sdf.format(myCalendar.getTime()) + " ";
        } else {
            Str_Date = sdf.format(myCalendar.getTime()) + " ";
        }
        mDieselFillingFundRequestEditTextPresentDateTime.setText(Str_Date + Str_Time);

    }

    public void set_listener() {

        mDieselFillingFundRequestTextViewSiteNameVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSitesList.getSiteList() == null) {
                    if (Conditions.isNetworkConnected(DieselFillingFundRequest.this)) {
                        prepareUserSites(false);
                    } else {
                        showToast("No Internet Found..");
                    }
                }
            }
        });

        /*mDieselFillingFundRequestTextViewDgMakeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSitesList.getSiteList() == null) {
                    if (Conditions.isNetworkConnected(DieselFillingFundRequest.this)) {
                        prepareUserSites(false);
                    } else {
                        showToast("No Internet Found..");
                    }
                }
            }
        });

        mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSitesList.getSiteList() == null) {
                    if (Conditions.isNetworkConnected(DieselFillingFundRequest.this)) {
                        prepareUserSites(false);
                    } else {
                        showToast("No Internet Found..");
                    }
                }
            }
        });*/

        /////////////
        mDieselFillingFundRequestButtonHmrPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    HmrPhotoUpload();
                }
            }
        });

        mDieselFillingFundRequestButtonPresentEbReadingKwhPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    PresentEbReadingKwhPhotoPhoto();
                }
            }
        });
        mDieselFillingFundRequestButtonHmrPhotoUploadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriHmrPhotoUpload != null) {
                    GlobalMethods.showImageDialog(DieselFillingFundRequest.this, imageFileUriHmrPhotoUpload);
                } else {
                    Toast.makeText(DieselFillingFundRequest.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mDieselFillingFundRequestButtonPresentEbReadingKwhPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriPresentEbReadingKwhPhoto != null) {
                    GlobalMethods.showImageDialog(DieselFillingFundRequest.this, imageFileUriPresentEbReadingKwhPhoto);
                } else {
                    Toast.makeText(DieselFillingFundRequest.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mDieselFillingFundRequestButtonPresentDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DieselFillingFundRequest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedHour1 = (selectedHour >= 10) ? Integer.toString(selectedHour) : String.format("0%s", Integer.toString(selectedHour));
                        String selectedMinute1 = (selectedMinute >= 10) ? Integer.toString(selectedMinute) : String.format("0%s", Integer.toString(selectedMinute));
                        //mDieselFillingFundRequestEditTextPresentDateTime.setText(selectedHour1 + ":" + selectedMinute1);
                        Str_Date = "";
                        Str_Time = "";
                        if (mDieselFillingFundRequestEditTextPresentDateTime.getText().length() > 1) {
                            Str_Date = mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().substring(0, mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().indexOf(" "));
                            Str_Time = mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().substring(mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().indexOf(" "), mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().length());
                            Str_Time = " " + selectedHour1 + ":" + selectedMinute1;
                        } else {
                            Str_Time = " " + selectedHour1 + ":" + selectedMinute1;
                        }
                        mDieselFillingFundRequestEditTextPresentDateTime.setText(Str_Date + Str_Time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time For Average Eb Availability");
                mTimePicker.show();
            }
        });
    }

    private void prepareUserSites(final boolean listbind_only) {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("IsDieselRequest", "1");

            GsonRequest<UserSitesList> getuserSitesNameRequest = new GsonRequest<>(Request.Method.POST, Constants.GetUserSites, jo.toString(), UserSitesList.class,
                    new Response.Listener<UserSitesList>() {
                        @Override
                        public void onResponse(UserSitesList response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    userSitesList = response;

                                    if (userSitesList.getSiteList().size() > 0) {

                                        final ArrayList<String> Sitelist = new ArrayList<String>();
                                        for (UserSites site : userSitesList.getSiteList()) {
                                            Sitelist.add(site.getSiteName());
                                        }

                                        mDieselFillingFundRequestTextViewSiteNameVal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFillingFundRequest.this,
                                                        Sitelist,
                                                        "Select Site",
                                                        "Close", "#000000");
                                                searchableSpinnerDialog.showSearchableSpinnerDialog();

                                                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                    @Override
                                                    public void onClick(ArrayList<String> item, int position) {

                                                        dgMakeList = new ArrayList<String>();
                                                        dgCapacityList = new ArrayList<String>();

                                                        mDieselFillingFundRequestTextViewCircleVal.setText("");
                                                        mDieselFillingFundRequestTextViewStateVal.setText("");
                                                        mDieselFillingFundRequestTextViewSsaVal.setText("");

                                                        mDieselFillingFundRequestTextViewDgMakeVal.setText("");
                                                        mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setText("");

                                                        mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText("");
                                                        mDieselFillingFundRequestTextViewLastDieselStockVal.setText("");
                                                        mDieselFillingFundRequestTextViewLastDgHmrVal.setText("");
                                                        mDieselFillingFundRequestTextViewLastEbReadingVal.setText("");

                                                        mDieselFillingFundRequestTextViewCircleVal.setText(userSitesList.getSiteList().get(position).getCircleName() == null || userSitesList.getSiteList().get(position).getCircleName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getCircleName());
                                                        mDieselFillingFundRequestTextViewStateVal.setText(userSitesList.getSiteList().get(position).getStateName() == null || userSitesList.getSiteList().get(position).getStateName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getStateName());
                                                        mDieselFillingFundRequestTextViewSsaVal.setText(userSitesList.getSiteList().get(position).getSsaName() == null || userSitesList.getSiteList().get(position).getSsaName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getSsaName());

                                                        mDieselFillingFundRequestTextViewSiteNameVal.setText(userSitesList.getSiteList().get(position).getSiteName());
                                                        mDieselFillingFundRequestTextViewSiteIdVal.setText(userSitesList.getSiteList().get(position).getSiteId());

                                                        mDieselFillingFundRequestTextViewSourceOfPowerVal.setText(userSitesList.getSiteList().get(position).getSourceOfPower() == null || userSitesList.getSiteList().get(position).getSourceOfPower().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getSourceOfPower());
                                                        mDieselFillingFundRequestTextViewCardSupplierVal.setText(userSitesList.getSiteList().get(position).getPetroCompanyName() == null || userSitesList.getSiteList().get(position).getPetroCompanyName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getPetroCompanyName());
                                                        mDieselFillingFundRequestTextViewChildCardNumberVal.setText(userSitesList.getSiteList().get(position).getChildCardNumber() == null || userSitesList.getSiteList().get(position).getChildCardNumber().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getChildCardNumber());

                                                        /*mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText(userSitesList.getSiteList().get(position).getLastDieselFillingDate() == null || userSitesList.getSiteList().get(position).getLastDieselFillingDate().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastDieselFillingDate().toString().substring(0, userSitesList.getSiteList().get(position).getLastDieselFillingDate().toString().indexOf(" ")));
                                                        mDieselFillingFundRequestTextViewLastDieselStockVal.setText(userSitesList.getSiteList().get(position).getLastDieselStock() == null || userSitesList.getSiteList().get(position).getLastDieselStock().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastDieselStock());
                                                        mDieselFillingFundRequestTextViewLastDgHmrVal.setText(userSitesList.getSiteList().get(position).getLastDGHMR() == null || userSitesList.getSiteList().get(position).getLastDGHMR().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastDGHMR());
                                                        mDieselFillingFundRequestTextViewLastEbReadingVal.setText(userSitesList.getSiteList().get(position).getLastEBReadingl() == null || userSitesList.getSiteList().get(position).getLastEBReadingl().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastEBReadingl());*/

                                                        if (userSitesList.getSiteList().get(position).getLatitude() != null && userSitesList.getSiteList().get(position).getLongitude() != null &&
                                                                !userSitesList.getSiteList().get(position).getLatitude().isEmpty() && !userSitesList.getSiteList().get(position).getLongitude().isEmpty()) {
                                                            siteLatitude = Double.parseDouble(userSitesList.getSiteList().get(position).getLatitude());
                                                            siteLongitude = Double.parseDouble(userSitesList.getSiteList().get(position).getLongitude());
                                                        } else {
                                                            siteLatitude = 0;
                                                            siteLongitude = 0;
                                                        }

                                                        siteDbId = Integer.valueOf(userSitesList.getSiteList().get(position).getId());

                                                        if (siteDbId > 0) {
                                                            if (Conditions.isNetworkConnected(DieselFillingFundRequest.this)) {
                                                                prepareDgMake_from_Sites();
                                                            } else {
                                                                showToast("No Internet Found..");
                                                            }
                                                        } else {
                                                            showToast("Please Select Site ID First..");
                                                        }

                                                        /*mDieselFillingTextViewSelectDgIdQrCodeVal.setText("");

                                                        if (siteDbId > 0) {
                                                            if (Conditions.isNetworkConnected(DieselFilling.this)) {
                                                                prepareDgId_from_Sites();
                                                            } else {
                                                                showToast("No Internet Found..");
                                                            }
                                                        } else {
                                                            showToast("Please Select Site ID First..");
                                                        }*/
                                                    }
                                                });

                                            }
                                        });
                                        if (!listbind_only) {
                                            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFillingFundRequest.this,
                                                    Sitelist,
                                                    "Select Site",
                                                    "Close", "#000000");
                                            searchableSpinnerDialog.showSearchableSpinnerDialog();

                                            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                @Override
                                                public void onClick(ArrayList<String> item, int position) {

                                                    dgMakeList = new ArrayList<String>();
                                                    dgCapacityList = new ArrayList<String>();
                                                    mDieselFillingFundRequestTextViewDgMakeVal.setText("");
                                                    mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setText("");

                                                    mDieselFillingFundRequestTextViewSiteNameVal.setText(userSitesList.getSiteList().get(position).getSiteName());
                                                    mDieselFillingFundRequestTextViewSiteIdVal.setText(userSitesList.getSiteList().get(position).getSiteId());

                                                    mDieselFillingFundRequestTextViewCircleVal.setText(userSitesList.getSiteList().get(position).getCircleName() == null || userSitesList.getSiteList().get(position).getCircleName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getCircleName());
                                                    mDieselFillingFundRequestTextViewStateVal.setText(userSitesList.getSiteList().get(position).getStateName() == null || userSitesList.getSiteList().get(position).getStateName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getStateName());
                                                    mDieselFillingFundRequestTextViewSsaVal.setText(userSitesList.getSiteList().get(position).getSsaName() == null || userSitesList.getSiteList().get(position).getSsaName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getSsaName());

                                                    mDieselFillingFundRequestTextViewSourceOfPowerVal.setText(userSitesList.getSiteList().get(position).getSourceOfPower() == null || userSitesList.getSiteList().get(position).getSourceOfPower().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getSourceOfPower());
                                                    mDieselFillingFundRequestTextViewCardSupplierVal.setText(userSitesList.getSiteList().get(position).getPetroCompanyName() == null || userSitesList.getSiteList().get(position).getPetroCompanyName().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getPetroCompanyName());
                                                    mDieselFillingFundRequestTextViewChildCardNumberVal.setText(userSitesList.getSiteList().get(position).getChildCardNumber() == null || userSitesList.getSiteList().get(position).getChildCardNumber().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getChildCardNumber());

                                                    /*mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText(userSitesList.getSiteList().get(position).getLastDieselFillingDate() == null || userSitesList.getSiteList().get(position).getLastDieselFillingDate().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastDieselFillingDate().toString().substring(0, userSitesList.getSiteList().get(position).getLastDieselFillingDate().toString().indexOf(" ")));
                                                    mDieselFillingFundRequestTextViewLastDieselStockVal.setText(userSitesList.getSiteList().get(position).getLastDieselStock() == null || userSitesList.getSiteList().get(position).getLastDieselStock().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastDieselStock());
                                                    mDieselFillingFundRequestTextViewLastDgHmrVal.setText(userSitesList.getSiteList().get(position).getLastDGHMR() == null || userSitesList.getSiteList().get(position).getLastDGHMR().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastDGHMR());
                                                    mDieselFillingFundRequestTextViewLastEbReadingVal.setText(userSitesList.getSiteList().get(position).getLastEBReadingl() == null || userSitesList.getSiteList().get(position).getLastEBReadingl().isEmpty() == true ? "-" : userSitesList.getSiteList().get(position).getLastEBReadingl());*/

                                                    siteDbId = Integer.valueOf(userSitesList.getSiteList().get(position).getId());

                                                }
                                            });
                                        }

                                    } else {
                                        mDieselFillingFundRequestTextViewSiteNameVal.setText("No Site Found");

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
            getuserSitesNameRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getuserSitesNameRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getuserSitesNameRequest, "userSitesNameRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    private void setSessionValuesToFields() {
        mDieselFillingFundRequestTextViewCustomerVal.setText(sessionManager.getSessionCustomer().toString());
        //mDieselFillingFundRequestTextViewCircleVal.setText(sessionManager.getSessionCircle().toString());
        //mDieselFillingFundRequestTextViewStateVal.setText(sessionManager.getUser_State().toString());
        //mDieselFillingFundRequestTextViewSsaVal.setText(sessionManager.getUser_Ssa().toString());

    }

    private void HmrPhotoUpload() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileHmrPhotoUpload = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dieselFundReqHMR.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileHmrPhotoUpload);
            imageFileUriHmrPhotoUpload = FileProvider.getUriForFile(DieselFillingFundRequest.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriHmrPhotoUpload);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_HmrPhotoUpload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PresentEbReadingKwhPhotoPhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFilePresentEbReadingKwhPhoto = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dieselFundReqEB.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFilePresentEbReadingKwhPhoto);
            imageFileUriPresentEbReadingKwhPhoto = FileProvider.getUriForFile(DieselFillingFundRequest.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriPresentEbReadingKwhPhoto);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_PresentEbReadingKwhPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(DieselFillingFundRequest.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DieselFillingFundRequest.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Confirmation", "Do you want to submit this request?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
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
            String latitude = String.valueOf(gpsTracker.getLatitude());
            String longitude = String.valueOf(gpsTracker.getLongitude());

            //String siteName = mDieselFillingTextViewSiteNameVal.getText().toString().trim();

            String customer = mDieselFillingFundRequestTextViewCustomerVal.getText().toString().trim();
            String circle = mDieselFillingFundRequestTextViewCircleVal.getText().toString().trim();
            String state = mDieselFillingFundRequestTextViewStateVal.getText().toString().trim();
            String ssa = mDieselFillingFundRequestTextViewSsaVal.getText().toString().trim();
            String siteName = mDieselFillingFundRequestTextViewSiteNameVal.getText().toString().trim();
            //String siteCodeId = mDieselFillingFundRequestTextViewSiteIdVal.getText().toString().trim();
            String sourceOfPower = mDieselFillingFundRequestTextViewSourceOfPowerVal.getText().toString().trim();
            String cardSupplier = mDieselFillingFundRequestTextViewCardSupplierVal.getText().toString().trim();
            String childCardNumber = mDieselFillingFundRequestTextViewChildCardNumberVal.getText().toString().trim();
            String lastDieselFillingDate = mDieselFillingFundRequestTextViewLastDieselFillingDateVal.getText().toString().trim();
            String lastDieselStock = mDieselFillingFundRequestTextViewLastDieselStockVal.getText().toString().trim();
            String lastDgHmr = mDieselFillingFundRequestTextViewLastDgHmrVal.getText().toString().trim();
            String lastEbReading = mDieselFillingFundRequestTextViewLastEbReadingVal.getText().toString().trim();
            String presentDgHmr = mDieselFillingFundRequestEditTextPresentDgHmr.getText().toString().trim();
            String hmrPhotoUpload = base64StringHmrPhotoUpload;//mDieselFillingFundRequestTextViewHmrPhotoUpload.getText().toString().trim();
            String presentDieselStock = mDieselFillingFundRequestEditTextPresentDieselStock.getText().toString().trim();
            String presentEbReading = mDieselFillingFundRequestEditTextPresentEbReading.getText().toString().trim();
            String presentEbMeterReadingKwhPhoto = base64StringPresentEbReadingKwhPhoto;//mDieselFillingFundRequestTextView.getText().toString().trim();
            //String presentDateTime = mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().trim();
            String dieselQuantityRequiredInLtrs = mDieselFillingFundRequestEditTextDieselQuantityRequired.getText().toString().trim();
            String DGCapacity = mDieselFillingFundRequestTextViewDgCapacityInKvaVal.getText().toString().trim();
            String DGMake = mDieselFillingFundRequestTextViewDgMakeVal.getText().toString().trim();
            /*dieselFillingFundRequestData = new DieselFillingFundRequestData(customer, circle, state, ssa, siteName, siteCodeId,
                    sourceOfPower, cardSupplier, childCardNumber, lastDieselFillingDate,
                    lastDieselStock, lastDgHmr, lastEbReading, presentDgHmr,
                    hmrPhotoUpload, presentDieselStock, presentEbReading,
                    presentEbMeterReadingKwhPhoto, presentDateTime, dieselQuantityRequiredInLtrs);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(dieselFillingFundRequestData);*/

            /*"UserId":"12",
                    "AccessToken":"MjUyLTg1REEyUzMtQURTUzVELUVJNUI0QTIyMTEy" ,
                    "SiteId":"12",
                    "PresentDgHmr":"12",
                    "HMRPhotoUpload":"12",
                    "PresentDieselStock":"12",
                    "PresentEbReading":"12",
                    "PresentEBMeterReadingKWHPhoto":"12",
                    "DieselQuantityRequiredinLtrs":"12"*/

            JSONObject jsonString = new JSONObject();
            jsonString.put("UserId", sessionManager.getSessionUserId());
            jsonString.put("AccessToken", sessionManager.getSessionDeviceToken());
            jsonString.put("SiteId", siteDbId);//siteCodeId
            jsonString.put("PresentDgHmr", presentDgHmr);
            jsonString.put("HMRPhotoUpload", hmrPhotoUpload);
            jsonString.put("PresentDieselStock", presentDieselStock);
            jsonString.put("PresentEbReading", presentEbReading);
            jsonString.put("PresentEBMeterReadingKWHPhoto", presentEbMeterReadingKwhPhoto);
            //jsonString.put("DateOfRequest", presentDateTime);
            jsonString.put("DieselQuantityRequiredinLtrs", dieselQuantityRequiredInLtrs);
            jsonString.put("DGMake", DGMake);
            jsonString.put("DGCapacity", DGCapacity);

            //DGCapacity
            //DGMake

            GsonRequest<DieselSubmitResposeData> dieselSubmitResposeData = new GsonRequest<>(Request.Method.POST, Constants.Submitdieselfillingfundrequesttransaction, jsonString.toString(), DieselSubmitResposeData.class,
                    new Response.Listener<DieselSubmitResposeData>() {
                        @Override
                        public void onResponse(DieselSubmitResposeData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    hideBusyProgress();
                                    setResult(RESULT_OK);
                                    showToast("Record submitted successfully.");
                                    finish();
                                } else {
                                    hideBusyProgress();
                                    showToast("Something went wrong");
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
            dieselSubmitResposeData.setRetryPolicy(Application.getDefaultRetryPolice());
            dieselSubmitResposeData.setShouldCache(false);
            Application.getInstance().addToRequestQueue(dieselSubmitResposeData, "dieselSubmitResposeData");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkValidationOnSubmitDiselTicket() {
        String customer = mDieselFillingFundRequestTextViewCustomerVal.getText().toString().trim();
        String circle = mDieselFillingFundRequestTextViewCircleVal.getText().toString().trim();
        String state = mDieselFillingFundRequestTextViewStateVal.getText().toString().trim();
        String ssa = mDieselFillingFundRequestTextViewSsaVal.getText().toString().trim();
        String siteName = mDieselFillingFundRequestTextViewSiteNameVal.getText().toString().trim();
        String siteId = mDieselFillingFundRequestTextViewSiteIdVal.getText().toString().trim();
        String sourceOfPower = mDieselFillingFundRequestTextViewSourceOfPowerVal.getText().toString().trim();
        String cardSupplier = mDieselFillingFundRequestTextViewCardSupplierVal.getText().toString().trim();
        String childCardNumber = mDieselFillingFundRequestTextViewChildCardNumberVal.getText().toString().trim();
        String lastDieselFillingDate = mDieselFillingFundRequestTextViewLastDieselFillingDateVal.getText().toString().trim();
        String lastDieselStock = mDieselFillingFundRequestTextViewLastDieselStockVal.getText().toString().trim();
        String lastDgHmr = mDieselFillingFundRequestTextViewLastDgHmrVal.getText().toString().trim();
        String lastEbReading = mDieselFillingFundRequestTextViewLastEbReadingVal.getText().toString().trim();
        String presentDgHmr = mDieselFillingFundRequestEditTextPresentDgHmr.getText().toString().trim();
        String hmrPhotoUpload = base64StringHmrPhotoUpload;//mDieselFillingFundRequestTextViewHmrPhotoUpload.getText().toString().trim();
        String presentDieselStock = mDieselFillingFundRequestEditTextPresentDieselStock.getText().toString().trim();
        String presentEbReading = mDieselFillingFundRequestEditTextPresentEbReading.getText().toString().trim();
        String presentEbMeterReadingKwhPhoto = base64StringPresentEbReadingKwhPhoto;//mDieselFillingFundRequestTextView.getText().toString().trim();
        //String presentDateTime = mDieselFillingFundRequestEditTextPresentDateTime.getText().toString().trim();
        String dieselQuantityRequiredInLtrs = mDieselFillingFundRequestEditTextDieselQuantityRequired.getText().toString().trim();
        String DGCapacity = mDieselFillingFundRequestTextViewDgCapacityInKvaVal.getText().toString().trim();//.setText("No Data Found");
        String DGMake = mDieselFillingFundRequestTextViewDgMakeVal.getText().toString().trim();//.setText("No Data Found");


        if (siteId.isEmpty() || siteId == null) {
            showToast("Select Site Name");
            return false;
        } else if (DGMake.isEmpty() || DGMake == null || DGMake.equals("No Data Found")) {
            showToast("Select DG Make");
            return false;
        } else if (DGCapacity.isEmpty() || DGCapacity == null || DGCapacity.equals("No Data Found")) {
            showToast("Select DG Capacity");
            return false;
        } else if (presentDgHmr.isEmpty() || presentDgHmr == null) {
            showToast("Enter Present DG HMR");
            return false;
        } /*else if (hmrPhotoUpload.isEmpty() || hmrPhotoUpload == null) {
            showToast("Upload Photo of HMR");
            return false;
        }*/ else if (presentDieselStock.isEmpty() || presentDieselStock == null) {
            showToast("Enter Present Diesel Stock");
            return false;
        } else if (presentEbReading.isEmpty() || presentEbReading == null) {
            showToast("Enter Present EB Reading");
            return false;
        } /*else if (presentEbMeterReadingKwhPhoto.isEmpty() || presentEbMeterReadingKwhPhoto == null) {
            showToast("Upload Photo of EB Reading KWH");
            return false;
        }else if (presentDateTime.isEmpty() || presentDateTime == null) {
            showToast("Select Present Date & Time");
            return false;
        }*/ else if (dieselQuantityRequiredInLtrs.isEmpty() || dieselQuantityRequiredInLtrs == null) {
            showToast("Enter Diesel Quantity Required (in Ltrs)");
            return false;
        } else return true;

/*
        if (siteID.isEmpty() || siteID == null) {
            showToast("Select Site Name");
            return false;
        } else if (selectDgIdQrCode.isEmpty() || selectDgIdQrCode == null) {
            showToast("Select DG ID / QR Code");
            return false;
        } else if (presentDgHmr.isEmpty() || presentDgHmr == null) {
            showToast("Enter Present DG HMR");
            return false;
        } else if (hmrPhotoUpload.isEmpty() || hmrPhotoUpload == null) {
            showToast("Upload Photo of HMR");
            return false;
        } else if (tankBalanceBeforeFilling.isEmpty() || tankBalanceBeforeFilling == null) {
            showToast("Enter Tank Balance Before Filling");
            return false;
        } else if (fillingQty.isEmpty() || fillingQty == null) {
            showToast("Enter Filling Quantity");
            return false;
        } else if (dieselPrice.isEmpty() || dieselPrice == null) {
            showToast("Enter Diesel Price");
            return false;
        } else if (presentEbReading.isEmpty() || presentEbReading == null) {
            showToast("Enter Present EB Reading");
            return false;
        } else if (ebReadingKwhPhoto.isEmpty() || ebReadingKwhPhoto == null) {
            showToast("Upload Photo of EB Reading KWH");
            return false;
        } else return true;*/

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
                if (checkValidationOnSubmitDiselTicket() == true) {
                    showSettingsAlert();
                    return true;

                    //commented on 05-02-2019 by tiger /// for mahindra have new requirement that doen't check location when submit ticket//////////////////////////////////
                    /*if (gpsTracker.canGetLocation()) {
                        if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                            if (gpsTracker.distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), siteLatitude, siteLongitude) < 0.310686) {///// ( 0.310686 MILE == 500 Meter )
                                Log.i(DieselFillingFundRequest.class.getName(), "" + "in Area \n" + gpsTracker.distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), siteLatitude, siteLongitude));
                                showSettingsAlert();
                            } else {
                                Log.i(DieselFillingFundRequest.class.getName(), "" + "not in Area\n" + gpsTracker.distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), siteLatitude, siteLongitude));
                                alertDialogManager.Dialog("Information", "User not in area of site", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                    @Override
                                    public void onPositiveClick() {

                                    }
                                }).show();
                            }

                        } else {
                            alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                @Override
                                public void onPositiveClick() {
                                    if (gpsTracker.canGetLocation()) {
                                        Log.e(MyEnergyListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                    }
                                }
                            }).show();
                        }
                    }*/
                    //////////////////////////////////
                }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_HmrPhotoUpload:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriHmrPhotoUpload != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriHmrPhotoUpload);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringHmrPhotoUpload = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mDieselFillingFundRequestButtonHmrPhotoUploadView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileHmrPhotoUpload = "";
                    imageFileUriHmrPhotoUpload = null;
                    mDieselFillingFundRequestButtonHmrPhotoUploadView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_PresentEbReadingKwhPhoto:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriPresentEbReadingKwhPhoto != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriPresentEbReadingKwhPhoto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringPresentEbReadingKwhPhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mDieselFillingFundRequestButtonPresentEbReadingKwhPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFilePresentEbReadingKwhPhoto = "";
                    imageFileUriPresentEbReadingKwhPhoto = null;
                    mDieselFillingFundRequestButtonPresentEbReadingKwhPhotoView.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void prepareDgMake_from_Sites() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("SiteId", siteDbId);


            GsonRequest<DgIdQrCodeList> getDgIdQrCodeRequest = new GsonRequest<>(Request.Method.POST, Constants.GetSitePowerBackupDgData, jo.toString(), DgIdQrCodeList.class,
                    new Response.Listener<DgIdQrCodeList>() {
                        @Override
                        public void onResponse(DgIdQrCodeList response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dgIdQrCodeList = response;

                                    if (dgIdQrCodeList.getPowerBackupsDGDataList().size() > 0) {

                                        dgMakeList = new ArrayList<String>();
                                        for (PowerBackupsDGDataList circleList : dgIdQrCodeList.getPowerBackupsDGDataList()) {
                                            dgMakeList.add(circleList.getDGMakeName());
                                        }
                                        //////////////////


                                        mDieselFillingFundRequestTextViewDgMakeVal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFillingFundRequest.this,
                                                        dgMakeList,
                                                        "Select DG Make",
                                                        "Close", "#000000");
                                                searchableSpinnerDialog.showSearchableSpinnerDialog();

                                                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                    @Override
                                                    public void onClick(ArrayList<String> item, int position) {

                                                        dgCapacityList = new ArrayList<String>();
                                                        mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setText("");

                                                        mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText("");
                                                        mDieselFillingFundRequestTextViewLastDieselStockVal.setText("");
                                                        mDieselFillingFundRequestTextViewLastDgHmrVal.setText("");
                                                        mDieselFillingFundRequestTextViewLastEbReadingVal.setText("");

                                                        mDieselFillingFundRequestTextViewDgMakeVal.setText(dgIdQrCodeList.getPowerBackupsDGDataList().get(position).getDGMakeName());
                                                        if (dgIdQrCodeList.getPowerBackupsDGDataList().get(position).getCapacity() != null) {
                                                            dgCapacityList.addAll(dgIdQrCodeList.getPowerBackupsDGDataList().get(position).getCapacity());
                                                            bindToDgCapacity(dgCapacityList);
                                                        } else {
                                                            mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setText("No Data Found");
                                                        }
                                                    }
                                                });

                                            }
                                        });


                                        ////////////////////
                                    } else {
                                        mDieselFillingFundRequestTextViewDgMakeVal.setText("No Data Found");
                                        mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setText("No Data Found");
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
            getDgIdQrCodeRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getDgIdQrCodeRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getDgIdQrCodeRequest, "getDgIdQrCodeRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    private void bindToDgCapacity(final ArrayList<String> dgCapacityList) {

        mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFillingFundRequest.this,
                        dgCapacityList,
                        "Select DG Capacity",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText("");
                        mDieselFillingFundRequestTextViewLastDieselStockVal.setText("");
                        mDieselFillingFundRequestTextViewLastDgHmrVal.setText("");
                        mDieselFillingFundRequestTextViewLastEbReadingVal.setText("");

                        mDieselFillingFundRequestTextViewDgCapacityInKvaVal.setText(dgCapacityList.get(position).toString());

                        /*final ArrayList<String> dgCapacityList = new ArrayList<String>();
                        if (dgIdQrCodeList.getPowerBackupsDGDataList().get(position).getCapacity() != null) {
                            dgCapacityList.addAll(dgIdQrCodeList.getPowerBackupsDGDataList().get(position).getCapacity());
                            bindToDgCapacity(dgCapacityList);
                        }*/

                        if (!mDieselFillingFundRequestTextViewDgMakeVal.getText().toString().isEmpty() &&
                                !mDieselFillingFundRequestTextViewDgMakeVal.getText().toString().equals("No Data Found") &&
                                !mDieselFillingFundRequestTextViewDgCapacityInKvaVal.getText().toString().isEmpty() &&
                                !mDieselFillingFundRequestTextViewDgCapacityInKvaVal.getText().toString().equals("No Data Found")) {
                            setLastReadingValues();
                        }

                    }
                });

            }
        });

    }

    private void setLastReadingValues() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("SiteId", siteDbId);
            jo.put("DGMake", mDieselFillingFundRequestTextViewDgMakeVal.getText().toString().trim());
            jo.put("DGCapacity", mDieselFillingFundRequestTextViewDgCapacityInKvaVal.getText().toString().trim());


            GsonRequest<DgLastReadingsForDieselRequest> getDgLastReadings = new GsonRequest<>(Request.Method.POST, Constants.GetSiteDieselTransactionByDG, jo.toString(), DgLastReadingsForDieselRequest.class,
                    new Response.Listener<DgLastReadingsForDieselRequest>() {
                        @Override
                        public void onResponse(DgLastReadingsForDieselRequest response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    if (response.getSiteDieselTransactions() != null) {
                                        //SiteDieselTransactions siteDieselTransactions = new SiteDieselTransactions();
                                        //siteDieselTransactions = response.getSiteDieselTransactions();


                                        mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText(response.getSiteDieselTransactions().getLastDieselFillingDate() == null || response.getSiteDieselTransactions().getLastDieselFillingDate().isEmpty() == true ? "-" : response.getSiteDieselTransactions().getLastDieselFillingDate().toString().substring(0, response.getSiteDieselTransactions().getLastDieselFillingDate().toString().indexOf(" ")));
                                        mDieselFillingFundRequestTextViewLastDieselStockVal.setText(response.getSiteDieselTransactions().getLastDieselStock() == null || response.getSiteDieselTransactions().getLastDieselStock().isEmpty() == true ? "-" : response.getSiteDieselTransactions().getLastDieselStock());
                                        mDieselFillingFundRequestTextViewLastDgHmrVal.setText(response.getSiteDieselTransactions().getLastDGHMR() == null || response.getSiteDieselTransactions().getLastDGHMR().isEmpty() == true ? "-" : response.getSiteDieselTransactions().getLastDGHMR());
                                        mDieselFillingFundRequestTextViewLastEbReadingVal.setText(response.getSiteDieselTransactions().getLastEBReadingl() == null || response.getSiteDieselTransactions().getLastEBReadingl().isEmpty() == true ? "-" : response.getSiteDieselTransactions().getLastEBReadingl());

                                    } else {
                                        mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText("-");
                                        mDieselFillingFundRequestTextViewLastDieselStockVal.setText("-");
                                        mDieselFillingFundRequestTextViewLastDgHmrVal.setText("-");
                                        mDieselFillingFundRequestTextViewLastEbReadingVal.setText("-");
                                    }

                                } else {
                                    mDieselFillingFundRequestTextViewLastDieselFillingDateVal.setText("-");
                                    mDieselFillingFundRequestTextViewLastDieselStockVal.setText("-");
                                    mDieselFillingFundRequestTextViewLastDgHmrVal.setText("-");
                                    mDieselFillingFundRequestTextViewLastEbReadingVal.setText("-");
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
            getDgLastReadings.setRetryPolicy(Application.getDefaultRetryPolice());
            getDgLastReadings.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getDgLastReadings, "getDgLastReadings");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }
    }


}


