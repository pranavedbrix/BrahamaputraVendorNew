package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.EbPaymentDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.EBlSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.Data.EbPaymentDetailsParent;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalConversion;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UploadEBPaymentDetailsActivity extends BaseActivity {

    private static final String TAG = UploadEBPaymentDetailsActivity.class.getSimpleName();

    private TextView mUploadEbPaymentEditTextTicketNumber;
    private TextView mUploadEbPaymentEditTextSiteId;
    private TextView mUploadEbPaymentEditTextSiteName;
    private TextView mUploadEbPaymentTextViewPaymentTypeVal;

    private TextView mUploadEbPaymentTextViewDdChequeTransactionDetails;//uploadEbPayment_textView_DdChequeTransactionDetails
    /*private TextView mUploadEbPaymentTextViewDdChequeNumber;
    //private EditText mUploadEbPaymentEditTextDdChequeDate;
    private TextView mUploadEbPaymentTextViewDdChequeDate;
    private TextView mUploadEbPaymentTextViewDdChequeAmount;*/

    /*private EditText mUploadEbPaymentEditTextDdChequeTransactionDetails;*/
    private EditText mUploadEbPaymentEditTextDdChequeNumber;
    //private EditText mUploadEbPaymentEditTextDdChequeDate;
    private EditText mUploadEbPaymentEditTextDdChequeDate;
    private EditText mUploadEbPaymentEditTextDdChequeAmount;


    private EditText mUploadEbPaymentEditTextDdChequeRemark;

    private ImageView mUploadEbPaymentButtonDdChequePhoto;
    private ImageView mUploadEbPaymentButtonDdChequePhotoView;

    private EbPaymentDetails eBBillUploadPaymentDetails;

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private String imageFileName;
    private Uri imageFileNameUri = null;
    private String imageUri = "";
    private String base64String = "";
    String request_id;
    String ticket_no;
    String site_id;
    String site_name;
    String modeOfPayment;
    String StatusId;
    String ebPaymentAmt;
    private AlertDialogManager alertDialogManager;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    DecimalConversion decimalConversion;
    Calendar myCalendar1 = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener dateBillfrom = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, monthOfYear);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelPaymentDate();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_eb_payment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Upload DD / Cheque");//Upload Payment Details

        Intent intent = getIntent();
        request_id = intent.getStringExtra("request_id");
        ticket_no = intent.getStringExtra("ticket_no");
        site_id = intent.getStringExtra("site_id");
        site_name = intent.getStringExtra("site_name");
        modeOfPayment = intent.getStringExtra("ModeOfPayment");
        StatusId = intent.getStringExtra("StatusId");
        ebPaymentAmt = intent.getStringExtra("EbPaymentAmt");
        decimalConversion = new DecimalConversion();
        assignViews();
        setListners();
        alertDialogManager = new AlertDialogManager(UploadEBPaymentDetailsActivity.this);
        sessionManager = new SessionManager(UploadEBPaymentDetailsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(UploadEBPaymentDetailsActivity.this, userId, ticketName);

        mUploadEbPaymentEditTextTicketNumber.setText(ticket_no);
        mUploadEbPaymentEditTextSiteId.setText(site_id);
        mUploadEbPaymentEditTextSiteName.setText(site_name);
        mUploadEbPaymentTextViewPaymentTypeVal.setText(modeOfPayment);
        mUploadEbPaymentEditTextDdChequeAmount.setText(ebPaymentAmt);

        getEbPaymentDetailsInDDCheque();
        /*mUploadEbPaymentTextViewPaymentTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(UploadEBPaymentDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_payment_type))),
                        "Type of Payment",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        visibilityOfLayout();
                        mUploadEbPaymentTextViewPaymentTypeVal.setText(item.get(position));
                    }
                });
            }
        });*/

       /* mUploadEbPaymentTextViewDdChequeAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });*/

        mUploadEbPaymentEditTextDdChequeAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    DecimalFormatConversion();
                }
            }
        });

        /*mUploadEbPaymentTextViewDdChequeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UploadEBPaymentDetailsActivity.this, dateBillfrom, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });*/

        mUploadEbPaymentEditTextDdChequeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UploadEBPaymentDetailsActivity.this, dateBillfrom, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        mUploadEbPaymentButtonDdChequePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameUri != null) {
                    GlobalMethods.showImageDialog(UploadEBPaymentDetailsActivity.this, imageFileNameUri);
                } else if (imageUri != null && imageUri.length() > 0) {
                    mUploadEbPaymentButtonDdChequePhoto.setVisibility(View.GONE);
                    GlobalMethods.showImageDialogFromUrl(UploadEBPaymentDetailsActivity.this, imageUri);
                } else {
                    Toast.makeText(UploadEBPaymentDetailsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        EditTextEnable(true);
        if (StatusId.equals("3")) {
            invalidateOptionsMenu();
            EditTextEnable(false);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        // show the button when some condition is true
        shareItem.setVisible(true);
        //if (imageUri.length() > 0) {
        if (StatusId.equals("3")) {
            shareItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menuSubmit:
                if (checkValidation()) {
                    showSettingsAlert();
                    //finish();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void assignViews() {
        mUploadEbPaymentEditTextTicketNumber = (TextView) findViewById(R.id.uploadEbPayment_textView_ticketNumber);
        mUploadEbPaymentEditTextSiteId = (TextView) findViewById(R.id.uploadEbPayment_textView_siteId);
        mUploadEbPaymentEditTextSiteName = (TextView) findViewById(R.id.uploadEbPayment_textView_siteName);
        mUploadEbPaymentTextViewPaymentTypeVal = (TextView) findViewById(R.id.uploadEbPayment_textView_paymentType_val);

        mUploadEbPaymentButtonDdChequePhoto = (ImageView) findViewById(R.id.uploadEbPayment_button_ddChequePhoto);
        mUploadEbPaymentButtonDdChequePhotoView = (ImageView) findViewById(R.id.uploadEbPayment_button_ddChequePhotoView);

        mUploadEbPaymentTextViewDdChequeTransactionDetails = (TextView) findViewById(R.id.uploadEbPayment_textView_DdChequeTransactionDetails);
        /*mUploadEbPaymentTextViewDdChequeNumber = (TextView) findViewById(R.id.uploadEbPayment_textView_DdChequeNumber);
        mUploadEbPaymentTextViewDdChequeDate = (TextView) findViewById(R.id.uploadEbPayment_textView_ddChequeDate);
        mUploadEbPaymentTextViewDdChequeAmount = (TextView) findViewById(R.id.uploadEbPayment_textView_DdChequeAmount);*/

        /*mUploadEbPaymentEditTextDdChequeTransactionDetails = (EditText) findViewById(R.id.uploadEbPayment_editText_DdChequeTransactionDetails);*/
        mUploadEbPaymentEditTextDdChequeNumber = (EditText) findViewById(R.id.uploadEbPayment_editText_DdChequeNumber);
        mUploadEbPaymentEditTextDdChequeDate = (EditText) findViewById(R.id.uploadEbPayment_editText_ddChequeDate);
        mUploadEbPaymentEditTextDdChequeAmount = (EditText) findViewById(R.id.uploadEbPayment_editText_DdChequeAmount);

        mUploadEbPaymentEditTextDdChequeRemark = (EditText) findViewById(R.id.uploadEbPayment_editText_DdChequeRemark);
        mUploadEbPaymentEditTextDdChequeAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(9, 2)});
    }

    private void updateLabelPaymentDate() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mUploadEbPaymentEditTextDdChequeDate.setText(sdf.format(myCalendar1.getTime()));
        //mUploadEbPaymentTextViewDdChequeDate.setText(sdf.format(myCalendar1.getTime()));


    }

    private void setListners() {
        /*mUploadEbPaymentEditTextDdChequeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(UploadEBPaymentDetailsActivity.this, dateBillfrom, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/
        mUploadEbPaymentButtonDdChequePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    public void DecimalFormatConversion() {
        //mUploadEbPaymentTextViewDdChequeAmount.setText(decimalConversion.convertDecimal(mUploadEbPaymentTextViewDdChequeAmount.getText().toString()));
        mUploadEbPaymentEditTextDdChequeAmount.setText(decimalConversion.convertDecimal(mUploadEbPaymentEditTextDdChequeAmount.getText().toString()));

    }

    private void getEbPaymentDetailsInDDCheque() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("EbpaymentrequestId", request_id);


            GsonRequest<EbPaymentDetailsParent> ebPaymentDetailsParentGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.GetEbPaymentDetails, jo.toString(), EbPaymentDetailsParent.class,
                    new Response.Listener<EbPaymentDetailsParent>() {
                        @Override
                        public void onResponse(@NonNull EbPaymentDetailsParent response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    //Set The Values to Control Fields
                                    //mUploadEbPaymentTextViewPaymentTypeVal.setText(response.getEbPaymentDetails().getEbPaymentRemark() == null ? "" : response.getEbPaymentDetails().getEbPaymentRemark());

                                    mUploadEbPaymentTextViewDdChequeTransactionDetails.setText(response.getEbPaymentDetails().getEbPaymentDdChequeTransactionDetails() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequeTransactionDetails());
                                /*mUploadEbPaymentTextViewDdChequeNumber.setText(response.getEbPaymentDetails().getEbPaymentDdChequeNumber() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequeNumber());
                                mUploadEbPaymentTextViewDdChequeDate.setText(response.getEbPaymentDetails().getEbPaymentDdChequetDate() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequetDate());
                                mUploadEbPaymentTextViewDdChequeAmount.setText(response.getEbPaymentDetails().getEbPaymentDdChequeAmount() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequeAmount());*/

                                    /*mUploadEbPaymentEditTextDdChequeTransactionDetails.setText(response.getEbPaymentDetails().getEbPaymentDdChequeTransactionDetails() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequeTransactionDetails());*/
                                    mUploadEbPaymentEditTextDdChequeNumber.setText(response.getEbPaymentDetails().getEbPaymentDdChequeNumber() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequeNumber());
                                    mUploadEbPaymentEditTextDdChequeDate.setText(response.getEbPaymentDetails().getEbPaymentDdChequetDate() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequetDate());
                                    mUploadEbPaymentEditTextDdChequeAmount.setText(response.getEbPaymentDetails().getEbPaymentDdChequeAmount() == null ? "" : response.getEbPaymentDetails().getEbPaymentDdChequeAmount());

                                    //mUploadEbPaymentEditTextDdChequeRemark.setText(response.getEbPaymentDetails().getEbPaymentDDUploadRemark() == null ? "" : response.getEbPaymentDetails().getEbPaymentDDUploadRemark());
                                    mUploadEbPaymentEditTextDdChequeRemark.setText(response.getEbPaymentDetails().getEbPaymentRemark() == null ? "" : response.getEbPaymentDetails().getEbPaymentRemark());
                                    if (StatusId.equals("3")) {
                                        mUploadEbPaymentButtonDdChequePhoto.setVisibility(View.GONE);
                                        mUploadEbPaymentButtonDdChequePhotoView.setVisibility(View.GONE);
                                        //invalidateOptionsMenu();
                                    }
                                    if (response.getEbPaymentDetails().getEbPaymentDdChequeScanCopyImageUrl() != null && !response.getEbPaymentDetails().getEbPaymentDdChequeScanCopyImageUrl().isEmpty() && response.getEbPaymentDetails().getEbPaymentDdChequeScanCopyImageUrl().length() > 0) {
                                        imageUri = response.getEbPaymentDetails().getEbPaymentDdChequeScanCopyImageUrl();
                                        imageFileName = "";
                                        imageFileNameUri = null;
                                        mUploadEbPaymentButtonDdChequePhoto.setVisibility(View.GONE);
                                        mUploadEbPaymentButtonDdChequePhotoView.setVisibility(View.VISIBLE);
                                        //invalidateOptionsMenu();
                                    }
                                    invalidateOptionsMenu();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    /*if (error.getMessage().contains("java.net.UnknownHostException")) {
                        showToast("No Internet Connection.");
                    }*/
                    error.printStackTrace();
                    Log.d("Upload EB", "error:" + error);

                    int errorCode = 0;
                    if (error instanceof TimeoutError) {
                        errorCode = -7;
                        showToast("Network Timeout.");
                    } else if (error instanceof NoConnectionError) {
                        errorCode = -1;
                        showToast("No Internet Connection.");
                    } else if (error instanceof AuthFailureError) {
                        errorCode = -6;
                        showToast("Unauthorised User.");
                    } else if (error instanceof ServerError) {
                        errorCode = 0;
                        showToast("Server Error.");
                    } else if (error instanceof NetworkError) {
                        errorCode = -1;
                        showToast("Network Error.");
                    } else if (error instanceof ParseError) {
                        errorCode = -8;
                        showToast("Parse Error.");
                    }
                    hideBusyProgress();

                }
            });
            ebPaymentDetailsParentGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            ebPaymentDetailsParentGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(ebPaymentDetailsParentGsonRequest, "ebPaymentDetailsParentGsonRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }

    private void EditTextEnable(boolean flag) {
        mUploadEbPaymentEditTextDdChequeNumber.setEnabled(flag);
        mUploadEbPaymentEditTextDdChequeDate.setEnabled(flag);
        mUploadEbPaymentEditTextDdChequeAmount.setEnabled(flag);
        mUploadEbPaymentEditTextDdChequeRemark.setEnabled(flag);
        //mUploadEbPaymentEditTextDdChequeRemark.setTextIsSelectable(false);
        //mUploadEbPaymentEditTextDdChequeRemark.setFocusable(false);
        //mUploadEbPaymentEditTextDdChequeRemark.setFocusableInTouchMode(false);
    }

    private boolean checkValidation() {
        String payment_type = mUploadEbPaymentTextViewPaymentTypeVal.getText().toString();
        String DdChequeRemark = mUploadEbPaymentEditTextDdChequeRemark.getText().toString();
        /*String ebPaymentReceiptNumber = mUploadEbPaymentEditTextPaymentReceiptNumber.getText().toString();
        String ebPaymentDate = mUploadEbPaymentEditTextReceiptPaymentDate.getText().toString();
        String ebPaymentAmount = mUploadEbPaymentEditTextPaymentAmount.getText().toString();*/

        String ddChequeTransactionDetails = mUploadEbPaymentTextViewDdChequeTransactionDetails.getText().toString();
        /*String DdChequeNumber = mUploadEbPaymentTextViewDdChequeNumber.getText().toString();
        String DdChequeDate = mUploadEbPaymentTextViewDdChequeDate.getText().toString();
        String DdChequeAmount = mUploadEbPaymentTextViewDdChequeAmount.getText().toString();*/


        /*String ddChequeTransactionDetails = mUploadEbPaymentEditTextDdChequeTransactionDetails.getText().toString();*/
        String DdChequeNumber = mUploadEbPaymentEditTextDdChequeNumber.getText().toString();
        String DdChequeDate = mUploadEbPaymentEditTextDdChequeDate.getText().toString();
        String DdChequeAmount = mUploadEbPaymentEditTextDdChequeAmount.getText().toString();

        if (request_id.isEmpty() || request_id == null) {
            showToast("Invalid Request ID ");
            return false;
        } else if (ticket_no.isEmpty() || ticket_no == null) {
            showToast("Invalid Ticket ");
            return false;
        } else if (site_id.isEmpty() || site_id == null) {
            showToast("Invalid Site ID ");
            return false;
        } else if (site_name.isEmpty() || site_name == null) {
            showToast("Invalid Site Name ");
            return false;
        } else if (payment_type.isEmpty() || payment_type == null) {
            showToast("Invalid Payment Type ");
            return false;
        } else if (ddChequeTransactionDetails.isEmpty() || ddChequeTransactionDetails == null) {
            showToast("Enter Transaction Details ");
            return false;
        } else if (DdChequeNumber.isEmpty() || DdChequeNumber == null) {
            showToast("Enter DD/Cheque Number ");
            return false;
        } else if (DdChequeDate.isEmpty() || DdChequeDate == null) {
            showToast("Select Date ");
            return false;
        } else if (DdChequeAmount.isEmpty() || DdChequeAmount == null) {
            showToast("Enter Amount ");
            return false;
        } else if (DdChequeRemark.isEmpty() || DdChequeRemark == null) {
            showToast("Enter Payment Remark ");
            return false;
        } else if (base64String.isEmpty() || base64String == null) {
            showToast("Upload DD/Cheque Photo ");
            return false;
        } else return true;
    }

    private void showSettingsAlert() {

        //alertDialogManager = new AlertDialogManager(uploadEbPaymentActivity.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to Upload EB Payment Details?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
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
            String ModeOfPayment = mUploadEbPaymentTextViewPaymentTypeVal.getText().toString();

            String ddChequeTransactionDetails = mUploadEbPaymentTextViewDdChequeTransactionDetails.getText().toString();
            /*String DdChequeNumber = mUploadEbPaymentTextViewDdChequeNumber.getText().toString();
            String DdChequeDate = mUploadEbPaymentTextViewDdChequeDate.getText().toString();
            String DdChequeAmount = mUploadEbPaymentTextViewDdChequeAmount.getText().toString();
            String ebPaymentDDUploadRemark = mUploadEbPaymentEditTextDdChequeRemark.getText().toString();
            String ebPaymentRemark = mUploadEbPaymentEditTextDdChequeRemark.getText().toString();*/

            /*String ddChequeTransactionDetails = mUploadEbPaymentEditTextDdChequeTransactionDetails.getText().toString();*/
            String DdChequeNumber = mUploadEbPaymentEditTextDdChequeNumber.getText().toString();
            String DdChequeDate = mUploadEbPaymentEditTextDdChequeDate.getText().toString();
            String DdChequeAmount = mUploadEbPaymentEditTextDdChequeAmount.getText().toString();
            String ebPaymentDDUploadRemark = mUploadEbPaymentEditTextDdChequeRemark.getText().toString();
            String ebPaymentRemark = mUploadEbPaymentEditTextDdChequeRemark.getText().toString();

            /*String paymentMode = "DD/Cheque";
            String ddChequeTransactionDetails = "ICICI01hikTrans";
            String DdChequeNumber = "0012582";
            String DdChequeDate = "10/Dec/2018";
            String DdChequeAmount = "1250";*/

            eBBillUploadPaymentDetails = new EbPaymentDetails(userId, accessToken, request_id, ModeOfPayment,
                    DdChequeNumber, DdChequeDate, DdChequeAmount,
                    ddChequeTransactionDetails, base64String, ebPaymentRemark, ebPaymentDDUploadRemark);

            Gson gson2 = new GsonBuilder().create();

            String jsonString = gson2.toJson(eBBillUploadPaymentDetails);

            //offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

            GsonRequest<EBlSubmitResposeData> eBlSubmitEbPaymentDetailsDataGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.SubmitEbPaymentDetails, jsonString, EBlSubmitResposeData.class,
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
                                        showToast("EB Payment Uploaded successfully.");
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
            eBlSubmitEbPaymentDetailsDataGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            eBlSubmitEbPaymentDetailsDataGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(eBlSubmitEbPaymentDetailsDataGsonRequest, "eBlSubmitEbPaymentDetailsDataGsonRequest");


        } catch (Exception e)

        {
            e.printStackTrace();
        }

    }

    private void takePhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_site.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            imageFileNameUri = FileProvider.getUriForFile(UploadEBPaymentDetailsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void visibilityOfLayout() {
        /*mUploadEbPaymentLinearLayout_paymentReceiptNumber.setVisibility(View.VISIBLE);
        mUploadEbPaymentLinearLayout_receiptPaymentDate.setVisibility(View.VISIBLE);
        mUploadEbPaymentLinearLayout_paymentAmount.setVisibility(View.VISIBLE);
        mUploadEbPaymentLinearLayout_uploadPhoto.setVisibility(View.VISIBLE);

        mUploadEbPaymentEditTextPaymentReceiptNumber.setText("");
        mUploadEbPaymentEditTextReceiptPaymentDate.setText("");
        mUploadEbPaymentEditTextPaymentAmount.setText("");*/
        imageFileName = "";
        imageFileNameUri = null;
        mUploadEbPaymentButtonDdChequePhotoView.setVisibility(View.GONE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameUri != null) {
                        try {
                            imageUri = "";
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64String = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mUploadEbPaymentButtonDdChequePhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileName = "";
                    imageFileNameUri = null;
                    mUploadEbPaymentButtonDdChequePhotoView.setVisibility(View.GONE);
                }
                break;
        }
    }

}
