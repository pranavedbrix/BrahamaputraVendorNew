package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.EbMeterBox;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmNoOfAcAvailableAtSite;

public class PreventiveMaintenanceSiteEbMeterBoxActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteEbMeterBoxActivity.class.getSimpleName();
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxCondition;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatus;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatus;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatus;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatus;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewAcLoadAmpPh;
    private EditText mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhRPhase;
    private EditText mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhYPhase;
    private EditText mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhBPhase;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterReadingKwh;
    private EditText mPreventiveMaintenanceSiteEbMeterBoxEditTextEbMeterReadingKwh;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireCondition;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal;
    private LinearLayout mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutTypeOfFault;

    private LinearLayout mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteEbMeterBoxTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;
    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    String str_pmSiteEbmbEBMeterBoxConditionVal = "";
    String str_pmSiteEbmbEBMeterWorkingStatusVal = "";
    String str_pmSiteEbmbKitkatOrClayFuseStatusVal = "";
    String str_pmSiteEbmbSfuOrMccbStatusVal = "";
    String str_pmSiteEbmbHRCFuseStatusVal = "";
    String str_pmSiteEbmbEBServiceWireConditionVal = "";
    String str_pmSiteEbmbRegisterFaultVal = "";
    String str_pmSiteEbmbTypeOfFaultVal = "";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private EbMeterBox ebMeterBox;
    //private EbMeterBox ebMeterBox;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    /*private String ebMeterBoxCondition;
    private String ebMeterWorkingStatus;
    private String kitkatClayFuseStatus;
    private String sfuMccbStatus;
    private String hrcFuseStatus;
    String acLoadAmpRPh,
    String acLoadAmpYPh,
    String acLoadAmpBPh,
    private String ebMeterReadingKwh;
    private String ebServiceWireCondition;
    private String registerFault;
    private String typeOfFault;
    private String base64StringUploadPhotoOfRegisterFault;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_eb_meter_box);
        setTitle("EB Meter Box");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        setListner();

        sessionManager = new SessionManager(PreventiveMaintenanceSiteEbMeterBoxActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteEbMeterBoxActivity.this, userId, ticketName);


        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }
        setInputDetails();
        setMultiSelectModel();
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxCondition = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebMeterBoxCondition);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebMeterBoxConditionVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebMeterWorkingStatus);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebMeterWorkingStatusVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_KitKatClayFuseStatus);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_KitKatClayFuseStatusVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_SfuMccbStatus);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_SfuMccbStatusVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_HrcFuseStatus);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_HrcFuseStatusVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewAcLoadAmpPh = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_acLoadAmpPh);
        mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhRPhase = (EditText) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_editText_acLoadAmpPh_rPhase);
        mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhYPhase = (EditText) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_editText_acLoadAmpPh_yPhase);
        mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhBPhase = (EditText) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_editText_acLoadAmpPh_bPhase);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterReadingKwh = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebMeterReadingKwh);
        mPreventiveMaintenanceSiteEbMeterBoxEditTextEbMeterReadingKwh = (EditText) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_editText_ebMeterReadingKwh);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireCondition = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebServiceWireCondition);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_ebServiceWireConditionVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_registerFault);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_registerFaultVal);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_typeOfFault);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_linearLayout_typeOfFault);
        mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteEbMeterBoxTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteEbMeterBox_button_uploadPhotoOfRegisterFaultView);
    }

    private void initCombo() {
        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_ebMeterBoxCondition))),
                        "EB Meter Box Condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbEBMeterBoxConditionVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal.setText(str_pmSiteEbmbEBMeterBoxConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_ebMeterBoxWorkingStatus))),
                        "EB Meter Working Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbEBMeterWorkingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal.setText(str_pmSiteEbmbEBMeterWorkingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_kitkatOrClayFuseStatus))),
                        "KITKAT/Clay Fuse Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbKitkatOrClayFuseStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal.setText(str_pmSiteEbmbKitkatOrClayFuseStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_sfuOrMccbStatus))),
                        "SFU/MCCB Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbSfuOrMccbStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal.setText(str_pmSiteEbmbSfuOrMccbStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_hrcFuseStatus))),
                        "HRC Fuse Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbHRCFuseStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal.setText(str_pmSiteEbmbHRCFuseStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_ebServiceWireCondition))),
                        "EB Service Wire Condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbEBServiceWireConditionVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal.setText(str_pmSiteEbmbEBServiceWireConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal.setText(str_pmSiteEbmbRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteEbmbRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
                /*SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteEbMeterBox_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteEbmbTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.setText(str_pmSiteEbmbTypeOfFaultVal);
                    }
                });*/
            }
        });
    }

    private void setListner() {

        mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteEbMeterBoxActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteEbMeterBoxActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void setMultiSelectModel() {
        //MultiSelectModel
        multiSelectDialog = new MultiSelectDialog()
                .title("Type of Fault") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .preSelectIDsList(alreadySelectedTypeOfFaultList)
                .setMinSelectionLimit(0)
                .setMaxSelectionLimit(typeOfFaultList.size())
                //List of ids that you need to be selected
                .multiSelectList(listOfFaultsTypes) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        str_pmSiteEbmbTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.setText(str_pmSiteEbmbTypeOfFaultVal);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");
                    }
                });
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (pmSiteTransactionDetails != null) {

                    ebMeterBox = pmSiteTransactionDetails.getEbMeterBox();

                    if (ebMeterBox != null) {

                        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal.setText(ebMeterBox.getEbMeterBoxCondition());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal.setText(ebMeterBox.getEbMeterWorkingStatus());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal.setText(ebMeterBox.getKitkatClayFuseStatus());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal.setText(ebMeterBox.getSfuMccbStatus());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal.setText(ebMeterBox.getHrcFuseStatus());
                        mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhRPhase.setText(ebMeterBox.getAcLoadAmpRPh());
                        mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhYPhase.setText(ebMeterBox.getAcLoadAmpYPh());
                        mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhBPhase.setText(ebMeterBox.getAcLoadAmpBPh());
                        mPreventiveMaintenanceSiteEbMeterBoxEditTextEbMeterReadingKwh.setText(ebMeterBox.getEbMeterReadingKwh());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal.setText(ebMeterBox.getEbServiceWireCondition());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal.setText(ebMeterBox.getRegisterFault());
                        mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.setText(ebMeterBox.getTypeOfFault());
                        this.base64StringUploadPhotoOfRegisterFault = ebMeterBox.getBase64StringUploadPhotoOfRegisterFault();

                        visibilityOfTypesOfFault(ebMeterBox.getRegisterFault());

                        mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                        if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                            mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                        }

                        if (ebMeterBox.getTypeOfFault() != null && ebMeterBox.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                            setArrayValuesOfTypeOfFault(ebMeterBox.getTypeOfFault());
                        }
                    }
                }
            } else {
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            String ebMeterBoxCondition = mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal.getText().toString().trim();
            String ebMeterWorkingStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal.getText().toString().trim();
            String kitkatClayFuseStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal.getText().toString().trim();
            String sfuMccbStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal.getText().toString().trim();
            String hrcFuseStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal.getText().toString().trim();
            String acLoadAmpRPh = mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhRPhase.getText().toString().trim();
            String acLoadAmpYPh = mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhYPhase.getText().toString().trim();
            String acLoadAmpBPh = mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhBPhase.getText().toString().trim();
            String ebMeterReadingKwh = mPreventiveMaintenanceSiteEbMeterBoxEditTextEbMeterReadingKwh.getText().toString().trim();
            String ebServiceWireCondition = mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;
            //int isSubmited = 1;

            ebMeterBox = new EbMeterBox(ebMeterBoxCondition, ebMeterWorkingStatus, kitkatClayFuseStatus, sfuMccbStatus, hrcFuseStatus,
                    acLoadAmpRPh, acLoadAmpYPh, acLoadAmpBPh, ebMeterReadingKwh, ebServiceWireCondition, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault);
            pmSiteTransactionDetails.setEbMeterBox(ebMeterBox);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);
            //Log.d(TAG, "jsonString: " + jsonString);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setArrayValuesOfTypeOfFault(String TypeOfFault) {

        if (!TypeOfFault.isEmpty() && TypeOfFault != null) {
            List<String> items = Arrays.asList(TypeOfFault.split("\\s*,\\s*"));
            for (String ss : items) {
                for (MultiSelectModel c : listOfFaultsTypes) {
                    if (ss.equals(c.getName())) {
                        alreadySelectedTypeOfFaultList.add(c.getId());
                        break;
                    }
                }
            }
        }
    }

    private void visibilityOfTypesOfFault(String pmSiteEbmbRegisterFaultVal) {

        mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (pmSiteEbmbRegisterFaultVal.equals("Yes")) {
            mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteEbMeterBoxLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }

    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_alarmCheckReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteEbMeterBoxActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteEbMeterBoxActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteEbMeterBoxActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriUploadPhotoOfRegisterFault != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriUploadPhotoOfRegisterFault);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringUploadPhotoOfRegisterFault = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteEbMeterBoxButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuSubmit);

        // show the button when some condition is true
        shareItem.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuSubmit:
                if (checkValidationOfArrayFields() == true) {
                    submitDetails();
                    if (hototicket_sourceOfPower.equals("Non DG") && sitePmNoOfAcAvailableAtSite.equals("0")) {
                        startActivity(new Intent(this, PreventiveMaintenanceSiteSmpsCheckPointsActivity.class));
                    } else if (hototicket_sourceOfPower.equals("Non DG")) {
                        startActivity(new Intent(this, PreventiveMaintenanceSiteAcCheckPointsActivity.class));
                    } else {
                        startActivity(new Intent(this, PreventiveMaintenanceSiteDgCheckPointsActivity.class));
                    }
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public boolean checkValidationOfArrayFields() {
        String ebMeterBoxCondition = mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterBoxConditionVal.getText().toString().trim();
        String ebMeterWorkingStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewEbMeterWorkingStatusVal.getText().toString().trim();
        String kitkatClayFuseStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewKitKatClayFuseStatusVal.getText().toString().trim();
        String sfuMccbStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewSfuMccbStatusVal.getText().toString().trim();
        String hrcFuseStatus = mPreventiveMaintenanceSiteEbMeterBoxTextViewHrcFuseStatusVal.getText().toString().trim();
        String acLoadAmpPhRPhase = mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhRPhase.getText().toString().trim();
        String acLoadAmpPhYPhase = mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhYPhase.getText().toString().trim();
        String acLoadAmpPhBPhase = mPreventiveMaintenanceSiteEbMeterBoxEditTextAcLoadAmpPhBPhase.getText().toString().trim();
        String ebMeterReadingKwh = mPreventiveMaintenanceSiteEbMeterBoxEditTextEbMeterReadingKwh.getText().toString().trim();
        String ebServiceWireCondition = mPreventiveMaintenanceSiteEbMeterBoxTextViewEbServiceWireConditionVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteEbMeterBoxTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteEbMeterBoxTextViewTypeOfFaultVal.getText().toString().trim();

        if (ebMeterBoxCondition.isEmpty() || ebMeterBoxCondition == null) {
            showToast("Select EB Meter Box Condition");
            return false;
        } else if (ebMeterWorkingStatus.isEmpty() || ebMeterWorkingStatus == null) {
            showToast("Select EB Meter Working Status");
            return false;
        } else if (kitkatClayFuseStatus.isEmpty() || kitkatClayFuseStatus == null) {
            showToast("Select KITKAT/Clay Fuse Status");
            return false;
        } else if (sfuMccbStatus.isEmpty() || sfuMccbStatus == null) {
            showToast("Select SFU/MCCB Status");
            return false;
        } else if (hrcFuseStatus.isEmpty() || hrcFuseStatus == null) {
            showToast("Select HRC Fuse Status");
            return false;
        } else if (acLoadAmpPhRPhase.isEmpty() || acLoadAmpPhRPhase == null) {
            showToast("Please Enter AC Load Amp/Ph R Phase");
            return false;
        } else if (acLoadAmpPhYPhase.isEmpty() || acLoadAmpPhYPhase == null) {
            showToast("Please Enter AC Load Amp/Ph Y Phase");
            return false;
        } else if (acLoadAmpPhBPhase.isEmpty() || acLoadAmpPhBPhase == null) {
            showToast("Please Enter AC Load Amp/Ph B Phase");
            return false;
        } else if (ebMeterReadingKwh.isEmpty() || ebMeterReadingKwh == null) {
            showToast("Please Enter EB Meter Reading KWH");
            return false;
        } else if (ebServiceWireCondition.isEmpty() || ebServiceWireCondition == null) {
            showToast("Select EB Service Wire Condition");
            return false;
        } else if (registerFault.isEmpty() || registerFault == null) {
            showToast("Select Register Fault");
            return false;
        } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
            showToast("Select Type Of Fault");
            return false;
        } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
            showToast("Upload Photo Of Register Fault");
            return false;
        } else return true;
    }
}
