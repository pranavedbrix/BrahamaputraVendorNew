package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.EBBillUploadReceipt;
import com.brahamaputra.mahindra.brahamaputra.Data.EBlSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;

import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UploadEBReceiptActivity extends BaseActivity {

    private static final String TAG = UploadEBReceiptActivity.class.getSimpleName();

    private TextView mUploadEbReceiptTextViewTicketNumber;
    private TextView mUploadEbReceiptTextViewSiteId;
    private TextView mUploadEbReceiptTextViewSiteName;
    private TextView mUploadEbReceiptTextViewPaymentTypeVal;

    private EditText mUploadEbReceiptEditTextEbPaymentReceiptNumber;
    private TextView mUploadEbReceiptTextViewDdPaymentDate;
    private EditText mUploadEbReceiptEditTextEbPaymentDate;

    private EditText mUploadEbReceiptEditTextEbPaymentAmount;
    private EditText mUploadEbReceiptEditTextEbReceiptRemark;
    private ImageView mUploadEbReceiptButtonEbReceiptPhoto;
    private ImageView mUploadEbReceiptButtonEbReceiptPhotoView;
    private EBBillUploadReceipt ebBillUploadReceipt;

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private String imageFileName;
    private Uri imageFileNameUri = null;
    private String base64String = "";
    String request_id;
    String ticket_no;
    String site_id;
    String site_name;
    String modeOfPayment;
    String ebPaymentAmt;
    private AlertDialogManager alertDialogManager;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;

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
        setContentView(R.layout.activity_upload_ebreceipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Upload EB Receipt");//Upload Receipt

        Intent intent = getIntent();
        request_id = intent.getStringExtra("request_id");
        ticket_no = intent.getStringExtra("ticket_no");
        site_id = intent.getStringExtra("site_id");
        site_name = intent.getStringExtra("site_name");
        modeOfPayment = intent.getStringExtra("ModeOfPayment");
        ebPaymentAmt =  intent.getStringExtra("EbPaymentAmt");
        assignViews();
        setListners();
        alertDialogManager = new AlertDialogManager(UploadEBReceiptActivity.this);
        sessionManager = new SessionManager(UploadEBReceiptActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(UploadEBReceiptActivity.this, userId, ticketName);

        mUploadEbReceiptTextViewTicketNumber.setText(ticket_no);//mUploadEbReceiptEditTextTicketNumber
        mUploadEbReceiptTextViewSiteId.setText(site_id);//mUploadEbReceiptEditTextSiteId
        mUploadEbReceiptTextViewSiteName.setText(site_name);//mUploadEbReceiptEditTextSiteName
        mUploadEbReceiptTextViewPaymentTypeVal.setText(modeOfPayment);
        mUploadEbReceiptEditTextEbPaymentAmount.setText(ebPaymentAmt);

        /*mUploadEbReceiptTextViewPaymentTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(UploadEBReceiptActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_payment_type))),
                        "Type of Payment",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        visibilityOfLayout();
                        mUploadEbReceiptTextViewPaymentTypeVal.setText(item.get(position));
                    }
                });
            }
        });*/

        mUploadEbReceiptButtonEbReceiptPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameUri != null) {
                    GlobalMethods.showImageDialog(UploadEBReceiptActivity.this, imageFileNameUri);
                } else {
                    Toast.makeText(UploadEBReceiptActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });


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
                if (checkValidation()) {
                    showSettingsAlert();
                    //finish();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation() {
        String payment_type = mUploadEbReceiptTextViewPaymentTypeVal.getText().toString();

        String ebPaymentReceiptNumber = mUploadEbReceiptEditTextEbPaymentReceiptNumber.getText().toString();
        String ebPaymentDate = mUploadEbReceiptEditTextEbPaymentDate.getText().toString();
        String ebPaymentAmount = mUploadEbReceiptEditTextEbPaymentAmount.getText().toString();
        String ebReceiptRemark = mUploadEbReceiptEditTextEbReceiptRemark.getText().toString();

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
            showToast("Invalid Mode of Payment ");
            return false;
        } else if (ebPaymentReceiptNumber.isEmpty() || ebPaymentReceiptNumber == null) {
            showToast("Enter Payment Receipt Number ");
            return false;
        } else if (ebPaymentDate.isEmpty() || ebPaymentDate == null) {
            showToast("Select Payment Date ");
            return false;
        } else if (ebPaymentAmount.isEmpty() || ebPaymentAmount == null) {
            showToast("Enter Payment Amount ");
            return false;
        } else if (ebReceiptRemark.isEmpty() || ebReceiptRemark == null) {
            showToast("Enter EB Receipt Remark ");
            return false;
        } else if (base64String.isEmpty() || base64String == null) {
            showToast("Upload Receipt ");
            return false;
        } else return true;
    }

    private void showSettingsAlert() {

        //alertDialogManager = new AlertDialogManager(UploadEBReceiptActivity.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to Upload Payment?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
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
            /*String userId = sessionManager.getSessionUserId();
            String accessToken = sessionManager.getSessionDeviceToken();
            String paymentMode = mUploadEbReceiptTextViewPaymentTypeVal.getText().toString();*/

            String ebPaymentReceiptNumber = mUploadEbReceiptEditTextEbPaymentReceiptNumber.getText().toString();
            String ebPaymentDate = mUploadEbReceiptEditTextEbPaymentDate.getText().toString();
            String ebPaymentAmount = mUploadEbReceiptEditTextEbPaymentAmount.getText().toString();
            String ebReceiptRemark = mUploadEbReceiptEditTextEbReceiptRemark.getText().toString();


            /*ebBillUploadReceipt = new EBBillUploadReceipt(userId, accessToken, request_id, ebPaymentReceiptNumber, ebPaymentDate, ebPaymentAmount, ebReceiptRemark, base64String);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(ebBillUploadReceipt);*/

            //offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("EbpaymentrequestId", request_id);
            jo.put("BillPaymentReceiptNumber", ebPaymentReceiptNumber);
            jo.put("BillPaymentAmount", ebPaymentAmount);
            jo.put("BillPaymentDate", ebPaymentDate);
            jo.put("EbPaymentRemark", ebReceiptRemark);
            jo.put("EbReceiptScanCopyImageName", base64String);

            GsonRequest<EBlSubmitResposeData> eBlSubmitResposeDataGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.SubmitEbfillingPaymentEeceipt, jo.toString(), EBlSubmitResposeData.class,
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
                                        showToast("Receipt Uploaded successfully.");
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

    private void assignViews() {
        mUploadEbReceiptTextViewTicketNumber = (TextView) findViewById(R.id.uploadEbReceipt_textView_ticketNumber);
        mUploadEbReceiptTextViewSiteId = (TextView) findViewById(R.id.uploadEbReceipt_textView_siteId);
        mUploadEbReceiptTextViewSiteName = (TextView) findViewById(R.id.uploadEbReceipt_textView_siteName);
        mUploadEbReceiptTextViewPaymentTypeVal = (TextView) findViewById(R.id.uploadEbReceipt_textView_paymentType_val);
        mUploadEbReceiptEditTextEbPaymentReceiptNumber = (EditText) findViewById(R.id.uploadEbReceipt_editText_ebPaymentReceiptNumber);
        mUploadEbReceiptTextViewDdPaymentDate = (TextView) findViewById(R.id.uploadEbReceipt_textView_ddPaymentDate);
        mUploadEbReceiptEditTextEbPaymentDate = (EditText) findViewById(R.id.uploadEbReceipt_editText_ebPaymentDate);
        mUploadEbReceiptEditTextEbPaymentAmount = (EditText) findViewById(R.id.uploadEbReceipt_editText_ebPaymentAmount);
        mUploadEbReceiptEditTextEbReceiptRemark = (EditText) findViewById(R.id.uploadEbReceipt_editText_EbReceiptRemark);
        mUploadEbReceiptButtonEbReceiptPhoto = (ImageView) findViewById(R.id.uploadEbReceipt_button_ebReceiptPhoto);
        mUploadEbReceiptButtonEbReceiptPhotoView = (ImageView) findViewById(R.id.uploadEbReceipt_button_ebReceiptPhotoView);
    }

    private void updateLabelPaymentDate() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mUploadEbReceiptEditTextEbPaymentDate.setText(sdf.format(myCalendar1.getTime()));


    }

    private void setListners() {
        mUploadEbReceiptEditTextEbPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(UploadEBReceiptActivity.this, dateBillfrom, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mUploadEbReceiptButtonEbReceiptPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_site.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            imageFileNameUri = FileProvider.getUriForFile(UploadEBReceiptActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void visibilityOfLayout() {
        /*mUploadEbReceiptLinearLayout_paymentReceiptNumber.setVisibility(View.VISIBLE);
        mUploadEbReceiptLinearLayout_receiptPaymentDate.setVisibility(View.VISIBLE);
        mUploadEbReceiptLinearLayout_paymentAmount.setVisibility(View.VISIBLE);
        mUploadEbReceiptLinearLayout_uploadPhoto.setVisibility(View.VISIBLE);

        mUploadEbReceiptEditTextPaymentReceiptNumber.setText("");
        mUploadEbReceiptEditTextReceiptPaymentDate.setText("");
        mUploadEbReceiptEditTextPaymentAmount.setText("");*/
        imageFileName = "";
        imageFileNameUri = null;
        mUploadEbReceiptButtonEbReceiptPhotoView.setVisibility(View.GONE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameUri != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64String = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mUploadEbReceiptButtonEbReceiptPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileName = "";
                    imageFileNameUri = null;
                    mUploadEbReceiptButtonEbReceiptPhotoView.setVisibility(View.GONE);
                }
                break;
        }
    }

}
