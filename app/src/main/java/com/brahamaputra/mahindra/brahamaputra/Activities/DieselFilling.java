package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.view.inputmethod.InputMethodManager;
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
import com.brahamaputra.mahindra.brahamaputra.Data.DgIdQrCode;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselFillingData;
import com.brahamaputra.mahindra.brahamaputra.Data.DgIdQrCodeList;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselRequestTicketNoList;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.Data.DiselRequestTicketList;
import com.brahamaputra.mahindra.brahamaputra.Data.UserSitesList;
import com.brahamaputra.mahindra.brahamaputra.Data.UserSites;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.R;
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
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class DieselFilling extends BaseActivity {

    private static final String TAG = DieselFilling.class.getSimpleName();
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketName = "";
    private String ticketId = "";
    //private HotoTransactionData hotoTransactionData;
    private SessionManager sessionManager;
    private DieselFillingData dieselFillingData;

    private DieselRequestTicketNoList dieselRequestTicketNoList;
    private UserSitesList userSitesList;
    private DgIdQrCodeList dgIdQrCodeList;

    private Uri HmrPhoto_imageFileUri = null;
    private Uri EbReadingKwh_imageFileUri = null;

    private String HmrPhoto_imageFileName = "";
    private String EbReadingKwh_imageFileName = "";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_HmrPhoto = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_EbReadingKwh = 102;

    private String base64StringHmrPhoto = "";
    private String base64StringEbReadingKwh = "";
    private String base64StringQRCodeScan = "";


    private TextView mDieselFillingTextViewSiteName;
    private TextView mDieselFillingTextViewSiteNameVal;
    private TextView mDieselFillingTextViewSiteDetails;
    private TextView mDieselFillingTextViewSiteDetailsVal;

    private TextView mDieselFillingTextViewDgMake;
    private TextView mDieselFillingTextViewDgMakeVal;
    private TextView mDieselFillingTextViewDgCapacityInKva;
    private TextView mDieselFillingTextViewDgCapacityInKvaVal;

    private TextView mDieselFillingTextViewSiteID;
    private TextView mDieselFillingTextViewSiteIDVal;
    private TextView mDieselFillingTextViewSelectDgIdQrCode;
    private TextView mDieselFillingTextViewSelectDgIdQrCodeVal;
    private TextView mDieselFillingTextViewPresentDgHmr;
    private EditText mDieselFillingEditTextPresentDgHmr;
    private TextView mDieselFillingTextViewHmrPhotoUpload;
    private ImageView mDieselFillingButtonHmrPhotoUpload;
    private ImageView mDieselFillingButtonHmrPhotoUploadView;
    private TextView mDieselFillingTextViewTankBalanceBeforeFilling;
    private EditText mDieselFillingEditTextTankBalanceBeforeFilling;
    private TextView mDieselFillingTextViewFillingQty;
    private EditText mDieselFillingEditTextFillingQty;
    private TextView mDieselFillingTextViewFinalDieselStock;
    private TextView mDieselFillingTextViewFinalDieselStockVal;
    private TextView mDieselFillingTextViewDieselPrice;
    private EditText mDieselFillingEditTextDieselPrice;
    private TextView mDieselFillingTextViewPresentEbReading;
    private EditText mDieselFillingEditTextPresentEbReading;
    private TextView mDieselFillingTextViewPresentEbReadingKwhPhoto;
    private ImageView mDieselFillingButtonPresentEbReadingKwhPhoto;
    private ImageView mDieselFillingButtonPresentEbReadingKwhPhotoView;
    private TextView mDieselFillingTextViewPresentFillingDate;
    private TextView mDieselFillingTextViewPresentFillingDateVal;
    private ImageView dieselFilling_button_qrCode;

    private TextView mDieselFillingTextViewDieselRequestTicketNo;
    private TextView mDieselFillingTextViewDieselRequestTicketNoVal;
    private TextView mDieselFillingTextViewChildPetroCardNo;
    private TextView mDieselFillingTextViewChildPetroCardNoVal;
    private TextView mDieselFillingTextViewApprovedQty;
    private TextView mDieselFillingTextViewApprovedQtyVal;
    private TextView mDieselFillingTextViewRemainingQty;
    private TextView mDieselFillingTextViewRemainingQtyVal;
    private TextView mDieselFillingTextViewFillingDate;
    private TextView mDieselFillingEditTextFillingDateVal;

    public GPSTracker gpsTracker;

    //private ToastMessage toastMessage;
    private AlertDialogManager alertDialogManager;

    String str_siteName = "";
    DecimalConversion decimalConversion;

    private ArrayList<String> DgIdList;
    public static final int MY_FLAG_QR_RESULT = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    public int siteDbId = 0;
    public int requestTicketDbId = 0;
    public double siteLongitude = 0;
    public double siteLatitude = 0;

    private String str_dieselRequestTicketNo;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel_filling);
        this.setTitle("Diesel Filling");

        sessionManager = new SessionManager(DieselFilling.this);
        alertDialogManager = new AlertDialogManager(DieselFilling.this);
        userSitesList = new UserSitesList();
        dgIdQrCodeList = new DgIdQrCodeList();
        //toastMessage = new ToastMessage(DieselFilling.this);
        //ticketId = sessionManager.getSessionUserTicketId();
        //ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(DieselFilling.this, userId, ticketName);
        gpsTracker = new GPSTracker(DieselFilling.this);
        decimalConversion = new DecimalConversion();
        assignViews();
        initCombo();
        set_listener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        mDieselFillingEditTextFillingDateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new DatePickerDialog(DieselFillingFundRequest.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/

                DatePickerDialog dialog = new DatePickerDialog(DieselFilling.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();

            }
        });

        setInputDetails();
        prepareUserSites(true);
        prepareDieselRequestTicketNo(true);
    }

    private void assignViews() {
        mDieselFillingTextViewSiteName = (TextView) findViewById(R.id.dieselFilling_textView_siteName);
        mDieselFillingTextViewSiteNameVal = (TextView) findViewById(R.id.dieselFilling_textView_siteNameVal);
        mDieselFillingTextViewSiteDetails = (TextView) findViewById(R.id.dieselFilling_textView_siteDetails);
        mDieselFillingTextViewSiteDetailsVal = (TextView) findViewById(R.id.dieselFilling_textView_siteDetails_val);
        mDieselFillingTextViewSiteID = (TextView) findViewById(R.id.dieselFilling_textView_siteID);
        mDieselFillingTextViewSiteIDVal = (TextView) findViewById(R.id.dieselFilling_textView_siteID_val);

        mDieselFillingTextViewDgMake = (TextView) findViewById(R.id.dieselFilling_textView_dgMake);
        mDieselFillingTextViewDgMakeVal = (TextView) findViewById(R.id.dieselFilling_textView_dgMake_val);
        mDieselFillingTextViewDgCapacityInKva = (TextView) findViewById(R.id.dieselFilling_textView_dgCapacityInKva);
        mDieselFillingTextViewDgCapacityInKvaVal = (TextView) findViewById(R.id.dieselFilling_textView_dgCapacityInKva_val);

        mDieselFillingTextViewSelectDgIdQrCode = (TextView) findViewById(R.id.dieselFilling_textView_selectDgIdQrCode);
        mDieselFillingTextViewSelectDgIdQrCodeVal = (TextView) findViewById(R.id.dieselFilling_textView_selectDgIdQrCodeVal);
        mDieselFillingTextViewPresentDgHmr = (TextView) findViewById(R.id.dieselFilling_textView_presentDgHmr);
        mDieselFillingEditTextPresentDgHmr = (EditText) findViewById(R.id.dieselFilling_editText_presentDgHmr);
        mDieselFillingTextViewHmrPhotoUpload = (TextView) findViewById(R.id.dieselFilling_textView_hmrPhotoUpload);
        mDieselFillingButtonHmrPhotoUpload = (ImageView) findViewById(R.id.dieselFilling_button_hmrPhotoUpload);
        mDieselFillingButtonHmrPhotoUploadView = (ImageView) findViewById(R.id.dieselFilling_button_hmrPhotoUploadView);
        mDieselFillingTextViewTankBalanceBeforeFilling = (TextView) findViewById(R.id.dieselFilling_textView_tankBalanceBeforeFilling);
        mDieselFillingEditTextTankBalanceBeforeFilling = (EditText) findViewById(R.id.dieselFilling_editText_tankBalanceBeforeFilling);
        mDieselFillingTextViewFillingQty = (TextView) findViewById(R.id.dieselFilling_textView_fillingQty);
        mDieselFillingEditTextFillingQty = (EditText) findViewById(R.id.dieselFilling_editText_fillingQty);
        mDieselFillingTextViewFinalDieselStock = (TextView) findViewById(R.id.dieselFilling_textView_finalDieselStock);
        mDieselFillingTextViewFinalDieselStockVal = (TextView) findViewById(R.id.dieselFilling_textView_finalDieselStockVal);
        mDieselFillingTextViewDieselPrice = (TextView) findViewById(R.id.dieselFilling_textView_dieselPrice);
        mDieselFillingEditTextDieselPrice = (EditText) findViewById(R.id.dieselFilling_editText_dieselPrice);
        mDieselFillingTextViewPresentEbReading = (TextView) findViewById(R.id.dieselFilling_textView_presentEbReading);
        mDieselFillingEditTextPresentEbReading = (EditText) findViewById(R.id.dieselFilling_editText_presentEbReading);
        mDieselFillingTextViewPresentEbReadingKwhPhoto = (TextView) findViewById(R.id.dieselFilling_textView_presentEbReadingKwhPhoto);
        mDieselFillingButtonPresentEbReadingKwhPhoto = (ImageView) findViewById(R.id.dieselFilling_button_presentEbReadingKwhPhoto);
        mDieselFillingButtonPresentEbReadingKwhPhotoView = (ImageView) findViewById(R.id.dieselFilling_button_presentEbReadingKwhPhotoView);
        dieselFilling_button_qrCode = (ImageView) findViewById(R.id.dieselFilling_button_qrCode);

        mDieselFillingTextViewDieselRequestTicketNo = (TextView) findViewById(R.id.dieselFilling_textView_dieselRequestTicketNo);
        mDieselFillingTextViewDieselRequestTicketNoVal = (TextView) findViewById(R.id.dieselFilling_textView_dieselRequestTicketNoVal);
        mDieselFillingTextViewChildPetroCardNo = (TextView) findViewById(R.id.dieselFilling_textView_childPetroCardNo);
        mDieselFillingTextViewChildPetroCardNoVal = (TextView) findViewById(R.id.dieselFilling_textView_childPetroCardNo_val);
        mDieselFillingTextViewApprovedQty = (TextView) findViewById(R.id.dieselFilling_textView_approvedQty);
        mDieselFillingTextViewApprovedQtyVal = (TextView) findViewById(R.id.dieselFilling_textView_approvedQty_val);
        mDieselFillingTextViewFillingDate = (TextView) findViewById(R.id.dieselFillingFundRequest_textView_fillingDate);
        mDieselFillingEditTextFillingDateVal = (EditText) findViewById(R.id.dieselFillingFundRequest_editText_fillingDateTime);
        mDieselFillingTextViewRemainingQty = (TextView) findViewById(R.id.dieselFilling_textView_remainingQty);
        mDieselFillingTextViewRemainingQtyVal = (TextView) findViewById(R.id.dieselFilling_textView_remainingQty_val);


      /*  mDieselFillingTextViewPresentFillingDate = (TextView) findViewById(R.id.dieselFilling_textView_presentFillingDate);
        mDieselFillingTextViewPresentFillingDateVal = (TextView) findViewById(R.id.dieselFilling_textView_presentFillingDateVal);*/
        mDieselFillingEditTextTankBalanceBeforeFilling.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        mDieselFillingEditTextFillingQty.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
        mDieselFillingEditTextDieselPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});

        mDieselFillingTextViewSiteIDVal.setAllCaps(true);
        mDieselFillingTextViewSiteDetailsVal.setAllCaps(true);
        mDieselFillingTextViewFinalDieselStockVal.setAllCaps(true);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDieselFillingEditTextFillingDateVal.setText(sdf.format(myCalendar.getTime()));
    }

    public void onClicked(View v) {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(DieselFilling.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(DieselFilling.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(DieselFilling.this, new String[]{Manifest.permission.CAMERA}, MY_FLAG_QR_RESULT);
                            }
                        })
                        .setNegativeButton("DONT ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .create();
                dialog.show();
            }
        }).show();


    }

    public void set_listener() {
        dieselFilling_button_qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siteDbId < 1) {// && DgIdList.size() > 0
                    showToast("Please Select Site ID First..");
                } else if (DgIdList == null) {
                    showToast("No Data Found");
                    mDieselFillingTextViewSelectDgIdQrCodeVal.setText("No Data Found");
                } else if (DgIdList.size() > 0) {
                    if (ContextCompat.checkSelfPermission(DieselFilling.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (getFromPref(DieselFilling.this, ALLOW_KEY)) {
                            showSettingsAlert();
                        } else if (ContextCompat.checkSelfPermission(DieselFilling.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(DieselFilling.this, Manifest.permission.CAMERA)) {
                                showAlert();
                            } else {
                                // No explanation needed, we can request the permission.
                                ActivityCompat.requestPermissions(DieselFilling.this, new String[]{Manifest.permission.CAMERA}, MY_FLAG_QR_RESULT);
                            }
                        }
                    } else {
                        //openCamera();
                        onClicked(v);
                    }
                } else {
                    //
                }
            }
        });
        mDieselFillingButtonHmrPhotoUploadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HmrPhoto_imageFileUri != null) {
                    GlobalMethods.showImageDialog(DieselFilling.this, HmrPhoto_imageFileUri);
                } else {
                    Toast.makeText(DieselFilling.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mDieselFillingButtonPresentEbReadingKwhPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EbReadingKwh_imageFileUri != null) {
                    GlobalMethods.showImageDialog(DieselFilling.this, EbReadingKwh_imageFileUri);
                } else {
                    Toast.makeText(DieselFilling.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mDieselFillingButtonHmrPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        HmrPhoto_imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_site.jpg";

                        File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), HmrPhoto_imageFileName);
                        HmrPhoto_imageFileUri = FileProvider.getUriForFile(DieselFilling.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, HmrPhoto_imageFileUri);
                        startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_HmrPhoto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mDieselFillingButtonPresentEbReadingKwhPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        EbReadingKwh_imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_site.jpg";

                        File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), EbReadingKwh_imageFileName);
                        EbReadingKwh_imageFileUri = FileProvider.getUriForFile(DieselFilling.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, EbReadingKwh_imageFileUri);
                        startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_EbReadingKwh);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        mDieselFillingEditTextTankBalanceBeforeFilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateDieselStock();
            }
        });
        mDieselFillingEditTextFillingQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if(mDieselFillingEditTextFillingQty.getText().toString().equals(".") && mDieselFillingEditTextFillingQty.getText().toString().equals("."))
                {
                    mDieselFillingEditTextFillingQty.setText("0.");
                }
                NumberFormat formatter = new DecimalFormat("#0.00");
                showToast(formatter.format(Float.valueOf(mDieselFillingEditTextFillingQty.getText().toString()))) ;*/
                calculateDieselStock();
            }
        });
        mDieselFillingTextViewSiteNameVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userSitesList.getSiteList() == null) {
                    if (Conditions.isNetworkConnected(DieselFilling.this)) {
                        prepareUserSites(false);
                    } else {
                        showToast("No Internet Found..");
                    }
                }
            }
        });

        mDieselFillingTextViewDieselRequestTicketNoVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dieselRequestTicketNoList.getDiselRequestTicketList() == null) {
                    if (Conditions.isNetworkConnected(DieselFilling.this)) {
                        prepareDieselRequestTicketNo(false);
                    } else {
                        showToast("No Internet Found..");
                    }
                }
            }
        });

        mDieselFillingTextViewSelectDgIdQrCodeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siteDbId < 1) {// && DgIdList.size() > 0
                    showToast("Please Select Site ID First..");
                } else if (DgIdList == null) {
                    mDieselFillingTextViewSelectDgIdQrCodeVal.setText("No Data Found");
                } else {
                    if (Conditions.isNetworkConnected(DieselFilling.this)) {
                        //prepareDgId_from_Sites();

                        SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFilling.this,
                                DgIdList,
                                "Select DG Capacity",
                                "Close", "#000000");
                        searchableSpinnerDialog.showSearchableSpinnerDialog();

                        searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                            @Override
                            public void onClick(ArrayList<String> item, int position) {

                                //"Select Dg ID / QR Code",
                                str_siteName = item.get(position);
                                mDieselFillingTextViewSelectDgIdQrCodeVal.setText(str_siteName);
                            }
                        });

                    } else {
                        showToast("No Internet Found..");
                    }
                } /*else {
                    showToast("Please Select Site ID First..");
                }*/
            }
        });

        mDieselFillingEditTextFillingQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mDieselFillingEditTextDieselPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });
        mDieselFillingEditTextTankBalanceBeforeFilling.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

    }

    public void DecimalFormatConversion() {
        mDieselFillingEditTextFillingQty.setText(decimalConversion.convertDecimal(mDieselFillingEditTextFillingQty.getText().toString()));
        mDieselFillingEditTextTankBalanceBeforeFilling.setText(decimalConversion.convertDecimal(mDieselFillingEditTextTankBalanceBeforeFilling.getText().toString()));
        mDieselFillingTextViewFinalDieselStockVal.setText(decimalConversion.convertDecimal(mDieselFillingTextViewFinalDieselStockVal.getText().toString()));
        mDieselFillingEditTextDieselPrice.setText(decimalConversion.convertDecimal(mDieselFillingEditTextDieselPrice.getText().toString()));
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
                //  startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                /*if (siteDbId > 0) {*/
                DecimalFormatConversion();
                if (checkValidationOnSubmitDiselTicket() == true) {
                    showSettingsAlert();
                    return true;

                    //commented on 05-02-2019 by tiger /// for mahindra have new requirement that doen't check location when submit ticket//////////////////////////////////
                    /*if (gpsTracker.canGetLocation()) {

                        // Code For Next Validation by 008 on 22112018
                        if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {

                            if (gpsTracker.distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), siteLatitude, siteLongitude) < 0.310686) {///// ( 0.310686 MILE == 500 Meter )
                                Log.i(TAG, "" + "in Area \n" + gpsTracker.distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), siteLatitude, siteLongitude));

                                showSettingsAlert();
                            } else {
                                Log.i(TAG, "" + "not in Area\n" + gpsTracker.distance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), siteLatitude, siteLongitude));
                                alertDialogManager.Dialog("Information", "User not in area of site", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                    @Override
                                    public void onPositiveClick() {

                                    }
                                }).show();
                            }


                        } else {
                            //showToast("Could not detecting location. Please try again later.");
                            alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                                @Override
                                public void onPositiveClick() {
                                    if (gpsTracker.canGetLocation()) {
                                        //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By 008 on 10-11-2018
                                        Log.e(MyEnergyListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                    }
                                }
                            }).show();
                        }
                    }*/
                    //////////////////////////////////////////////////////////////
                    //submitDetails();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setInputDetails() {
        /*try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();


                mDieselFillingTextViewSiteNameVal.setText(dieselFillingData.getSiteName());
                mDieselFillingTextViewSiteDetailsVal.setText(dieselFillingData.getSiteDetails());
                mDieselFillingTextViewDieselRequestTicketNoVal.setText(dieselFillingData.getDgReqTicketNo());
                mDieselFillingTextViewSiteIDVal.setText(dieselFillingData.getSiteID());
                mDieselFillingTextViewChildPetroCardNoVal.setText(dieselFillingData.getDgChildPetroCardNo());
                mDieselFillingTextViewSelectDgIdQrCodeVal.setText(dieselFillingData.getSelectDgIdQrCode());
                mDieselFillingEditTextPresentDgHmr.setText(dieselFillingData.getPresentDgHmr());
                base64StringHmrPhoto= dieselFillingData.getHmrPhotoUpload();
                mDieselFillingTextViewApprovedQtyVal.setText(dieselFillingData.getDgApprovedQty());
                mDieselFillingEditTextTankBalanceBeforeFilling.setText(dieselFillingData.getTankBalanceBeforeFilling());
                mDieselFillingEditTextFillingQty.setText(dieselFillingData.getFillingQty());
                mDieselFillingTextViewFinalDieselStockVal.setText(dieselFillingData.getFinalDieselStock());
                mDieselFillingEditTextPresentEbReading.setText(dieselFillingData.getPesentEbReading());
                mDieselFillingEditTextFillingDateVal.setText(dieselFillingData.getDgFillingDate());
                base64StringEbReadingKwh =dieselFillingData.getEbReadingKwhPhoto();



            } else {
                Toast.makeText(DieselFilling.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void showSettingsAlert() {
        alertDialogManager = new AlertDialogManager(DieselFilling.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to submit this filling request?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
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
          /*  String ticketId = "";
            String ticketNo = "";*/
            String latitude = String.valueOf(gpsTracker.getLatitude());
            String longitude = String.valueOf(gpsTracker.getLongitude());

            /*String siteName = mDieselFillingTextViewSiteNameVal.getText().toString().trim();
            String siteDetails = mDieselFillingTextViewSiteDetailsVal.getText().toString().trim();*/
            String siteID = String.valueOf(siteDbId);
            String requestTicketDbID = String.valueOf(requestTicketDbId);
            String siteName = mDieselFillingTextViewSiteNameVal.getText().toString().trim();
            String selectDgIdQrCode = mDieselFillingTextViewSelectDgIdQrCodeVal.getText().toString().trim();
            String presentDgHmr = mDieselFillingEditTextPresentDgHmr.getText().toString().trim();
            String hmrPhotoUpload = base64StringHmrPhoto;
            String tankBalanceBeforeFilling = mDieselFillingEditTextTankBalanceBeforeFilling.getText().toString().trim();
            String fillingQty = mDieselFillingEditTextFillingQty.getText().toString().trim();
            String finalDieselStock = mDieselFillingTextViewFinalDieselStockVal.getText().toString().trim();
            String presentEbReading = mDieselFillingEditTextPresentEbReading.getText().toString().trim();
            String ebReadingKwhPhoto = base64StringEbReadingKwh;
            String dieselPrice = mDieselFillingEditTextDieselPrice.getText().toString().trim();
            String dieselRequestTicketNo = mDieselFillingTextViewDieselRequestTicketNoVal.getText().toString().trim();
            String childPetroCardNo = mDieselFillingTextViewChildPetroCardNoVal.getText().toString().trim();
            String approvedQty = mDieselFillingTextViewApprovedQtyVal.getText().toString().trim();
            String fillingDate = mDieselFillingEditTextFillingDateVal.getText().toString().trim();
            String remainingQty = mDieselFillingTextViewRemainingQtyVal.getText().toString().trim();
/*
            dieselFillingData = new DieselFillingData(dieselRequestTicketNo,siteID,childPetroCardNo, selectDgIdQrCode, presentDgHmr, hmrPhotoUpload, tankBalanceBeforeFilling,
                    approvedQty,fillingQty, finalDieselStock, presentEbReading, fillingDate,ebReadingKwhPhoto,
                    userId, accessToken, dieselPrice, latitude, longitude,siteName);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(dieselFillingData);*/

                        /*{

    "UserId":"12",
    "AccessToken":"MjUyLTg1REEyUzMtQURTUzVELUVJNUI0QTIyMTEy",
  "dieselPrice": "55",
  "ebReadingKwhPhoto": "aa",
  "fillingQty": "5300",
  "finalDieselStock": "10300.0",
  "hmrPhotoUpload": "aa",
  "latitude": "16.683356",
  "longitude": "74.2310151",
  "pesentEbReading": "3540",
  "presentDgHmr": "500",
  "selectDgIdQrCode": "",
  "siteID": "1",
  "tankBalanceBeforeFilling": "5000",
  "diselfillingrequestsId": "101",
  "dateOfFilling": "30/01/2018"
}*/


            JSONObject jsonString = new JSONObject();
            jsonString.put("UserId", sessionManager.getSessionUserId());
            jsonString.put("AccessToken", sessionManager.getSessionDeviceToken());
            jsonString.put("dieselPrice", dieselPrice);
            jsonString.put("ebReadingKwhPhoto", ebReadingKwhPhoto);
            jsonString.put("fillingQty", fillingQty);
            jsonString.put("finalDieselStock", finalDieselStock);
            jsonString.put("hmrPhotoUpload", hmrPhotoUpload);
            jsonString.put("latitude", latitude);
            jsonString.put("longitude", longitude);
            jsonString.put("pesentEbReading", presentEbReading);
            jsonString.put("presentDgHmr", presentDgHmr);
            jsonString.put("selectDgIdQrCode", selectDgIdQrCode);//new field DG Capacity
            jsonString.put("siteID", siteID);
            jsonString.put("tankBalanceBeforeFilling", tankBalanceBeforeFilling);
            jsonString.put("diselfillingrequestsId", requestTicketDbID);
            jsonString.put("dateOfFilling", fillingDate);

            //offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

            GsonRequest<DieselSubmitResposeData> dieselSubmitResposeData = new GsonRequest<>(Request.Method.POST, Constants.Submitdieselfillingtransaction, jsonString.toString(), DieselSubmitResposeData.class,
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

        } catch (Exception e)

        {
            e.printStackTrace();
        }

    }

    public void calculateDieselStock() {
        float f_beforeFilling = 0;
        float f_filling = 0;
        String beforeFilling = mDieselFillingEditTextTankBalanceBeforeFilling.getText().toString();
        String Filled = mDieselFillingEditTextFillingQty.getText().toString();
        if ((beforeFilling.equals("")) || (beforeFilling == null) || (beforeFilling.equals("."))) {
            beforeFilling = "0";
        }
        if ((Filled.equals("")) || (Filled == null) || (Filled.equals("."))) {
            Filled = "0";
        }
        f_beforeFilling = Float.valueOf(beforeFilling);
        f_filling = Float.valueOf(Filled);
        mDieselFillingTextViewFinalDieselStockVal.setText(String.valueOf(f_beforeFilling + f_filling));

    }

    private boolean checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(DieselFilling.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DieselFilling.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.GONE);
            if (result.getContents() == null) {
                base64StringQRCodeScan = "";
                showToast("Cancelled");
            } else {
                base64StringQRCodeScan = result.getContents();
                if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                    //mPowerPlantDetailsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                    //dgIdQrCodeList.
                    /*if (dgIdQrCodeList.getPowerBackupsDGMRQRList().size() > 0) {

                        for (int i = 0; i < dgIdQrCodeList.getPowerBackupsDGMRQRList().size(); i++) {
                            if (base64StringQRCodeScan.equals(dgIdQrCodeList.getPowerBackupsDGMRQRList().get(i).getqRCodeScan().toString())) {
                                str_siteName = base64StringQRCodeScan;
                                mDieselFillingTextViewSelectDgIdQrCodeVal.setText(str_siteName);
                            }
                        }
                    } else {
                        mDieselFillingTextViewSelectDgIdQrCodeVal.setText("No Data Found");
                        //No sites found
                    }*/
                }
            }
        }

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_HmrPhoto:
                if (resultCode == RESULT_OK) {
                    if (HmrPhoto_imageFileUri != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), HmrPhoto_imageFileUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringHmrPhoto = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mDieselFillingButtonHmrPhotoUploadView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    HmrPhoto_imageFileName = "";
                    HmrPhoto_imageFileUri = null;
                    mDieselFillingButtonHmrPhotoUploadView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_EbReadingKwh:
                if (resultCode == RESULT_OK) {
                    if (EbReadingKwh_imageFileUri != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), EbReadingKwh_imageFileUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringEbReadingKwh = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mDieselFillingButtonPresentEbReadingKwhPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    EbReadingKwh_imageFileName = "";
                    EbReadingKwh_imageFileUri = null;
                    mDieselFillingButtonPresentEbReadingKwhPhotoView.setVisibility(View.GONE);
                }
                break;
        }

        //super.onActivityResult(requestCode, resultCode, data);
    }

    private void prepareUserSites(final boolean listbind_only) {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());


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
                                            //Sitelist.add(site.getSiteId() + ":" + site.getSiteName());
                                            Sitelist.add(site.getSiteName());
                                        }
                                        //Collections.sort(Sitelist);
                                        //Collections.sort(Sitelist, String.CASE_INSENSITIVE_ORDER);
                                        mDieselFillingTextViewSiteNameVal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFilling.this,
                                                        Sitelist,
                                                        "Select Site",
                                                        "Close", "#000000");
                                                searchableSpinnerDialog.showSearchableSpinnerDialog();

                                                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                    @Override
                                                    public void onClick(ArrayList<String> item, int position) {

                                                        /*str_siteName = item.get(position);*/
                                                    /*String tttt = item.get(position);
                                                    String kept = tttt.substring(0, tttt.indexOf(":"));
                                                    UserSites userSites = new UserSites();
                                                    userSites.setSiteId(kept);
                                                    int getCategoryPos = userSitesList.getSiteList().indexOf(userSites.getSiteId());*/


                                                        mDieselFillingTextViewSiteNameVal.setText(userSitesList.getSiteList().get(position).getSiteName());
                                                        mDieselFillingTextViewSiteDetailsVal.setText(userSitesList.getSiteList().get(position).getSiteAddress());
                                                        mDieselFillingTextViewSiteIDVal.setText(userSitesList.getSiteList().get(position).getSiteId());

                                                        /*if (userSitesList.getSiteList().get(position).getLatitude() != null && userSitesList.getSiteList().get(position).getLongitude() != null) {
                                                            siteLatitude = Double.parseDouble(userSitesList.getSiteList().get(position).getLatitude());
                                                            siteLongitude = Double.parseDouble(userSitesList.getSiteList().get(position).getLongitude());
                                                            //showToast(""+siteLatitude+","+siteLongitude);
                                                        } else {
                                                            siteLatitude = 0;
                                                            siteLongitude = 0;
                                                            //showToast(""+siteLatitude+","+siteLongitude);
                                                        }*/


                                                        /*siteDbId = Integer.valueOf(userSitesList.getSiteList().get(position).getId());
                                                        mDieselFillingTextViewSelectDgIdQrCodeVal.setText("");

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
                                            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFilling.this,
                                                    Sitelist,
                                                    "Select Site",
                                                    "Close", "#000000");
                                            searchableSpinnerDialog.showSearchableSpinnerDialog();

                                            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                @Override
                                                public void onClick(ArrayList<String> item, int position) {

                                                    //str_siteName = item.get(position);
                                                    mDieselFillingTextViewSiteNameVal.setText(userSitesList.getSiteList().get(position).getSiteName());
                                                    mDieselFillingTextViewSiteDetailsVal.setText(userSitesList.getSiteList().get(position).getSiteAddress());
                                                    mDieselFillingTextViewSiteIDVal.setText(userSitesList.getSiteList().get(position).getSiteId());
                                                    siteDbId = Integer.valueOf(userSitesList.getSiteList().get(position).getId());
                                                    mDieselFillingTextViewSelectDgIdQrCodeVal.setText("");
                                                }
                                            });
                                        }


                                    } else {
                                        mDieselFillingTextViewSiteNameVal.setText("No Site Found");
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
            getuserSitesNameRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getuserSitesNameRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getuserSitesNameRequest, "userSitesNameRequest");
        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

    private void prepareDieselRequestTicketNo(final boolean listbind_only) {

        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("PageNo", 0);

            GsonRequest<DieselRequestTicketNoList> getuserRequestTicketNoRequest = new GsonRequest<>(Request.Method.POST, Constants.getUserApprovedDieselRequestTicketList, jo.toString(), DieselRequestTicketNoList.class,
                    new Response.Listener<DieselRequestTicketNoList>() {
                        @Override
                        public void onResponse(DieselRequestTicketNoList response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dieselRequestTicketNoList = response;
                                    if (dieselRequestTicketNoList.getDiselRequestTicketList().size() > 0) {
                                        final ArrayList<String> requestTicketList = new ArrayList<String>();
                                        for (DiselRequestTicketList diselRequestTicketList : dieselRequestTicketNoList.getDiselRequestTicketList()) {
                                            requestTicketList.add(diselRequestTicketList.getSiteName()+" - "+diselRequestTicketList.getDiselRequesttTicketNo());
                                        }

                                        mDieselFillingTextViewDieselRequestTicketNoVal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFilling.this,
                                                        requestTicketList,
                                                        "Select Diesel Request Ticket",
                                                        "Close", "#000000");
                                                searchableSpinnerDialog.showSearchableSpinnerDialog();

                                                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                    @Override
                                                    public void onClick(ArrayList<String> item, int position) {

                                                        str_dieselRequestTicketNo = item.get(position);
                                                        requestTicketDbId = Integer.parseInt(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getId());
                                                        siteDbId = Integer.valueOf(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteDBId());
                                                        mDieselFillingTextViewDieselRequestTicketNoVal.setText(str_dieselRequestTicketNo);
                                                        mDieselFillingTextViewSiteNameVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteName());
                                                        mDieselFillingTextViewSiteIDVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteId());
                                                        mDieselFillingTextViewSiteDetailsVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteAddress());
                                                        mDieselFillingTextViewDgMakeVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getDGMake());
                                                        mDieselFillingTextViewDgCapacityInKvaVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getDGCapacity());
                                                        mDieselFillingTextViewChildPetroCardNoVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getChildCardNumber());
                                                        mDieselFillingTextViewApprovedQtyVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getApprovedQuantity());
                                                        mDieselFillingTextViewRemainingQtyVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getRemainingQty());


                                                        /* if (!(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getLatitude().equals("")) && !(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getLongitude().equals(""))) {
                                                            siteLatitude = Double.parseDouble(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getLatitude());
                                                            siteLongitude = Double.parseDouble(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getLongitude());
                                                        } else {
                                                            siteLatitude = 0;
                                                            siteLatitude = 0;
                                                            siteLongitude = 0;
                                                        }*/

                                                        //siteDbId = Integer.valueOf(userSitesList.getSiteList().get(position).getId());

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
                                            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DieselFilling.this,
                                                    requestTicketList,
                                                    "Select Diesel Request Ticket",
                                                    "Close", "#000000");
                                            searchableSpinnerDialog.showSearchableSpinnerDialog();

                                            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                                                @Override
                                                public void onClick(ArrayList<String> item, int position) {

                                                    str_dieselRequestTicketNo = item.get(position);
                                                    requestTicketDbId = Integer.parseInt(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getId());
                                                    siteDbId = Integer.valueOf(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteDBId());
                                                    mDieselFillingTextViewDieselRequestTicketNoVal.setText(str_dieselRequestTicketNo);
                                                    mDieselFillingTextViewSiteNameVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteName());
                                                    mDieselFillingTextViewSiteIDVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteId());
                                                    mDieselFillingTextViewSiteDetailsVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getSiteAddress());
                                                    mDieselFillingTextViewDieselRequestTicketNoVal.setText(str_dieselRequestTicketNo);
                                                    mDieselFillingTextViewChildPetroCardNoVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getChildCardNumber());
                                                    mDieselFillingTextViewApprovedQtyVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getApprovedQuantity());
                                                    mDieselFillingTextViewRemainingQtyVal.setText(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getRemainingQty());
                                                   /* siteLatitude = Double.parseDouble(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getLatitude());
                                                    siteLongitude = Double.parseDouble(dieselRequestTicketNoList.getDiselRequestTicketList().get(position).getLongitude());*/
                                                }
                                            });
                                        }
                                    } else {
                                        mDieselFillingTextViewSiteNameVal.setText("No Site Found");
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
            getuserRequestTicketNoRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getuserRequestTicketNoRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getuserRequestTicketNoRequest, "userRequestTicketNoRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }


    private void prepareDgId_from_Sites() {
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
                                    DgIdList = new ArrayList<String>();
                                    if (dgIdQrCodeList.getPowerBackupsDGDataList().size() > 0) {
                                        //DgIdList.addAll(dgIdQrCodeList.getPowerBackupsDGDataList()); by 008 for new changes on 07022019
                                    } else {
                                        mDieselFillingTextViewSelectDgIdQrCodeVal.setText("No Data Found");
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
            getDgIdQrCodeRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getDgIdQrCodeRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(getDgIdQrCodeRequest, "getDgIdQrCodeRequest");


        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }


    private void prepareDgId_from_Sitesold() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("SiteId", siteDbId);


            GsonRequest<DgIdQrCodeList> getDgIdQrCodeRequest = new GsonRequest<>(Request.Method.POST, Constants.GetDgId_from_Sites, jo.toString(), DgIdQrCodeList.class,
                    new Response.Listener<DgIdQrCodeList>() {
                        @Override
                        public void onResponse(DgIdQrCodeList response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dgIdQrCodeList = response;
                                    DgIdList = new ArrayList<String>();
                                    /*if (dgIdQrCodeList.getPowerBackupsDGMRQRList().size() > 0) {
                                        for (DgIdQrCode ids : dgIdQrCodeList.getPowerBackupsDGMRQRList()) {
                                            DgIdList.add(ids.getqRCodeScan());
                                        }
                                    } else {
                                        mDieselFillingTextViewSelectDgIdQrCodeVal.setText("No Data Found");
                                        //No sites found
                                    }*/
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

    private void submitDieselFilling(String jsonInString) {
        try {
            if (!jsonInString.toString().equals("")) {
                showBusyProgress();
                Log.e("123", jsonInString);


            }
        } catch (Exception e) {
            hideBusyProgress();
            e.printStackTrace();
        }
    }

    /*008 22112018*/
    public boolean checkValidationOnSubmitDiselTicket() {
        String userId = sessionManager.getSessionUserId();
        String accessToken = sessionManager.getSessionDeviceToken();
        String latitude = String.valueOf(gpsTracker.getLatitude());
        String longitude = String.valueOf(gpsTracker.getLongitude());
        String dieselRequestTicketNo = mDieselFillingTextViewDieselRequestTicketNoVal.getText().toString();
        String siteID = mDieselFillingTextViewSiteNameVal.getText().toString();
        String childPetroCardNo = mDieselFillingTextViewChildPetroCardNoVal.getText().toString();
        String selectDgIdQrCode = mDieselFillingTextViewSelectDgIdQrCodeVal.getText().toString().trim();
        String approvedQty = mDieselFillingTextViewApprovedQtyVal.getText().toString();
        String remainingQty = mDieselFillingTextViewRemainingQtyVal.getText().toString();
        String fillingDate = mDieselFillingEditTextFillingDateVal.getText().toString();

        String presentDgHmr = mDieselFillingEditTextPresentDgHmr.getText().toString().trim();
        String hmrPhotoUpload = base64StringHmrPhoto;
        String tankBalanceBeforeFilling = mDieselFillingEditTextTankBalanceBeforeFilling.getText().toString().trim();
        String fillingQty = mDieselFillingEditTextFillingQty.getText().toString().trim();
        String finalDieselStock = mDieselFillingTextViewFinalDieselStockVal.getText().toString().trim();
        String presentEbReading = mDieselFillingEditTextPresentEbReading.getText().toString().trim();
        String ebReadingKwhPhoto = base64StringEbReadingKwh;
        String dieselPrice = mDieselFillingEditTextDieselPrice.getText().toString().trim();

        if (dieselRequestTicketNo.isEmpty() || dieselRequestTicketNo == null) {
            showToast("Select Diesel Request Ticket No");
            return false;
        } else if (siteID.isEmpty() || siteID == null) {
            showToast("Select Site Name");
            return false;
        } else if (childPetroCardNo.isEmpty() || childPetroCardNo == null) {
            showToast("Select Child Petro Card No");
            return false;
        } /*else if (selectDgIdQrCode.isEmpty() || selectDgIdQrCode == null) {
            showToast("Select DG Capacity");
            return false;
        }*//*else if (selectDgIdQrCode.isEmpty() || selectDgIdQrCode == null) {
            showToast("Select DG ID / QR Code");
            return false;
        }*/ else if (presentDgHmr.isEmpty() || presentDgHmr == null) {
            showToast("Enter Present DG HMR");
            return false;
        } /*else if (hmrPhotoUpload.isEmpty() || hmrPhotoUpload == null) {
            showToast("Upload Photo of HMR");
            return false;
        } */ else if (tankBalanceBeforeFilling.isEmpty() || tankBalanceBeforeFilling == null) {
            showToast("Enter Tank Balance Before Filling");
            return false;
        } else if (fillingQty.isEmpty() || fillingQty == null) {
            showToast("Enter Filling Quantity");
            return false;
        } else if (checkFillingQy(approvedQty, fillingQty,remainingQty) == false) {
            showToast("Filling Quantity not be greater than Remaining Qty");
            mDieselFillingEditTextFillingQty.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mDieselFillingEditTextFillingQty, InputMethodManager.SHOW_IMPLICIT);
            return false;
        } else if (finalDieselStock.isEmpty() || finalDieselStock == null) {
            showToast("Wrong Final Diesel Stock");
            return false;
        } else if (dieselPrice.isEmpty() || dieselPrice == null) {
            showToast("Enter Diesel Price");
            return false;
        } else if (presentEbReading.isEmpty() || presentEbReading == null) {
            showToast("Enter Present EB Reading");
            return false;
        } /*else if (ebReadingKwhPhoto.isEmpty() || ebReadingKwhPhoto == null) {
            showToast("Upload Photo of EB Reading KWH");
            return false;
        }*/ /*else if (approvedQty.isEmpty() || approvedQty == null) {
            showToast("Select Approved Qty");
            return false;
        }*/ else if (fillingDate.isEmpty() || fillingDate == null) {
            showToast("Select Filling Date");
            return false;
        } else return true;

    }

    public boolean checkFillingQy(String approvedQty, String fillingQty, String remainingQty) {
        if (approvedQty == null || fillingQty == null || remainingQty == null) {
            return false;
        } else if (approvedQty.isEmpty() && (fillingQty.isEmpty()) && (remainingQty.isEmpty())) {
            return false;
        } else if (((Double.parseDouble(fillingQty) <= Double.parseDouble(approvedQty)) && (Double.parseDouble(remainingQty) == Double.parseDouble(approvedQty))) ||
                ((Double.parseDouble(fillingQty) <=  Double.parseDouble(approvedQty)) && (Double.parseDouble(fillingQty) <=  Double.parseDouble(remainingQty)) && (Double.parseDouble(remainingQty) != Double.parseDouble(approvedQty)))) {
            return true;
        }
        return false;
    }

}
