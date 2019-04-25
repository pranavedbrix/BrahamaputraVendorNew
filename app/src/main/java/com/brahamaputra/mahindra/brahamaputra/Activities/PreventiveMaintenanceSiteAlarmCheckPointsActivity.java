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
import com.brahamaputra.mahindra.brahamaputra.Data.AlarmCheckPoints;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PreventiveMaintenanceSiteAlarmCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteAlarmCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDetailsOfWrms;
    private ImageView mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView;
    private ImageView mButtonClearDetailsOfWrmsQRCodeScanView;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarm;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOn;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailable;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTemp;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmoke;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailure;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNoc;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarks;
    private EditText mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarksVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal;

    private LinearLayout mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteAlarmCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView;

    private LinearLayout mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutTypeOfFault;
    private LinearLayout mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutRemarks;

    String str_pmSiteAcpDoorOpenAlarmVal = "";
    String str_pmSiteAcpDgOnVal = "";
    String str_pmSiteAcpDgOutputAvailableVal = "";
    String str_pmSiteAcpHighRoomTempVal = "";
    String str_pmSiteAcpFireAndSmokeVal = "";
    String str_pmSiteAcpPowerPlantFailureVal = "";
    String str_pmSiteAcpAlarmConfirmedByNocVal = "";
    String str_pmSiteAcpRemarks = "";
    String str_pmSiteAcpRegisterFaultVal = "";
    String str_pmSiteAcpTypeOfFaultVal = "";

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringDetailsOfWrmsQRCodeScan = "";

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;


    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private AlarmCheckPoints alarmCheckPoints;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

     /*String detailsOfWrmsQrCodeScan;
     String doorOpenAlarm;
     String dgOn;
     String dgOutputAvailable;
     String highRoomTemp;
     String fireSmoke;
     String powerPlantFailure;
     String alarmConfirmedByNoc;
     String remarks;
     String registerFault;
     String typeOfFault;
     private String base64StringUploadPhotoOfRegisterFault;*/

    ArrayList<BatteryType> batteryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_alarm_check_points);
        this.setTitle("Alarm Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        batteryType = new ArrayList<BatteryType>();
        Intent intent = getIntent();
        batteryType = (ArrayList<BatteryType>) intent.getSerializableExtra("batteryType");

        assignViews();
        checkCameraPermission();
        initCombo();
        setListner();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this, userId, ticketName);


        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }
        setInputDetails();
        setMultiSelectModel();

    }

    private void assignViews() {
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDetailsOfWrms = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_detailsOfWrmsQRCodeScan);
        mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_button_detailsOfWrmsQRCodeScan);
        mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_button_detailsOfWrmsQRCodeScanView);
        mButtonClearDetailsOfWrmsQRCodeScanView = (ImageView) findViewById(R.id.button_clearDetailsOfWrmsQRCodeScanView);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarm = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_doorOpenAlarm);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_doorOpenAlarmVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOn = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_dgOn);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_dgOnVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailable = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_dgOutputAvailable);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_dgOutputAvailableVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTemp = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_highRoomTemp);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_highRoomTempVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmoke = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_fireSmoke);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_fireSmokeVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailure = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_powerPlantFailure);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_powerPlantFailureVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNoc = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_alarmConfirmedByNoc);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_alarmConfirmedByNocVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarks = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_remarks);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarksVal = (EditText) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_remarksVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_linearLayout_typeOfFault);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_button_uploadPhotoOfRegisterFaultView);
        mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutRemarks = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteAlarmCheckPoints_linearLayout_remarks);

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
                        str_pmSiteAcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteAcpTypeOfFaultVal);

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
//                landDetailsData = gson.fromJson(jsonInString, LandDetailsData.class);

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (pmSiteTransactionDetails != null) {

                    alarmCheckPoints = pmSiteTransactionDetails.getAlarmCheckPoints();

                    if (alarmCheckPoints != null) {


                        base64StringDetailsOfWrmsQRCodeScan = alarmCheckPoints.getDetailsOfWrmsQrCodeScan();
                        mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView.setVisibility(View.GONE);
                        mButtonClearDetailsOfWrmsQRCodeScanView.setVisibility(View.GONE);
                        if (!base64StringDetailsOfWrmsQRCodeScan.isEmpty() && base64StringDetailsOfWrmsQRCodeScan != null) {
                            mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView.setVisibility(View.VISIBLE);
                            mButtonClearDetailsOfWrmsQRCodeScanView.setVisibility(View.VISIBLE);
                        }

                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal.setText(alarmCheckPoints.getDoorOpenAlarm());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal.setText(alarmCheckPoints.getDgOn());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal.setText(alarmCheckPoints.getDgOutputAvailable());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal.setText(alarmCheckPoints.getHighRoomTemp());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal.setText(alarmCheckPoints.getFireSmoke());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal.setText(alarmCheckPoints.getPowerPlantFailure());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal.setText(alarmCheckPoints.getAlarmConfirmedByNoc());
                        visibilityOfRemarks(alarmCheckPoints.getAlarmConfirmedByNoc());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarksVal.setText(alarmCheckPoints.getRemarks());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal.setText(alarmCheckPoints.getRegisterFault());
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.setText(alarmCheckPoints.getTypeOfFault());
                        this.base64StringUploadPhotoOfRegisterFault = alarmCheckPoints.getBase64StringUploadPhotoOfRegisterFault();

                        visibilityOfTypesOfFault(alarmCheckPoints.getRegisterFault());

                        mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                        if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                            mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                        }

                        if (alarmCheckPoints.getTypeOfFault() != null && alarmCheckPoints.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                            setArrayValuesOfTypeOfFault(alarmCheckPoints.getTypeOfFault());
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

            String detailsOfWrmsQrCodeScan = base64StringDetailsOfWrmsQRCodeScan;
            String doorOpenAlarm = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal.getText().toString().trim();
            String dgOn = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal.getText().toString().trim();
            String dgOutputAvailable = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal.getText().toString().trim();
            String highRoomTemp = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal.getText().toString().trim();
            String fireSmoke = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal.getText().toString().trim();
            String powerPlantFailure = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal.getText().toString().trim();
            String alarmConfirmedByNoc = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal.getText().toString().trim();
            String remarks = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarksVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;

            //int isSubmited = 1;

            alarmCheckPoints = new AlarmCheckPoints(detailsOfWrmsQrCodeScan, doorOpenAlarm, dgOn, dgOutputAvailable, highRoomTemp,
                    fireSmoke, powerPlantFailure, alarmConfirmedByNoc, remarks, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault);
            pmSiteTransactionDetails.setAlarmCheckPoints(alarmCheckPoints);

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

    private void visibilityOfTypesOfFault(String pmSiteAcpRegisterFaultVal) {

        mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (pmSiteAcpRegisterFaultVal.equals("Yes")) {
            mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    private void visibilityOfRemarks(String str_pmSiteAcpRemarks) {
        if (!str_pmSiteAcpRemarks.isEmpty() && str_pmSiteAcpRemarks != null) {
            mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutRemarks.setVisibility(View.VISIBLE);
            if (str_pmSiteAcpRemarks.equals("Yes")) {
                mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarksVal.setText("");
                mPreventiveMaintenanceSiteAlarmCheckPointsLinearLayoutRemarks.setVisibility(View.GONE);
            }
        }
    }

    private void initCombo() {
        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_doorOpenAlarm))),
                        "Door Open Alarm",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpDoorOpenAlarmVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal.setText(str_pmSiteAcpDoorOpenAlarmVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_dgOn))),
                        "DG ON",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpDgOnVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal.setText(str_pmSiteAcpDgOnVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_dgOutputAvailable))),
                        "DG Output Available",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpDgOutputAvailableVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal.setText(str_pmSiteAcpDgOutputAvailableVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_highRoomTemp))),
                        "High Room Temp",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpHighRoomTempVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal.setText(str_pmSiteAcpHighRoomTempVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_fireAndSmoke))),
                        "Fire & Smoke",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpFireAndSmokeVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal.setText(str_pmSiteAcpFireAndSmokeVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_powerPlantFailure))),
                        "Power Plant Failure",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpPowerPlantFailureVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal.setText(str_pmSiteAcpPowerPlantFailureVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_alarmConfirmedByNoc))),
                        "Alarm Confirmed by NOC",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpAlarmConfirmedByNocVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal.setText(str_pmSiteAcpAlarmConfirmedByNocVal);
                        visibilityOfRemarks(str_pmSiteAcpAlarmConfirmedByNocVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteAcpRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteAcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
                /*SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteAlarmCheckPoints_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteAcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteAcpTypeOfFaultVal);
                    }
                });*/
            }
        });
    }

    private void setListner() {

        mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DetailsOfWrmsQRCodeScan();
                }
            }
        });

        mButtonClearDetailsOfWrmsQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDetailsOfWrmsQRCodeScan = "";
                mButtonClearDetailsOfWrmsQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void DetailsOfWrmsQRCodeScan() {
        try {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("Scan QRcode");
            integrator.setOrientationLocked(true);
            integrator.setRequestCode(MY_PERMISSIONS_REQUEST_CAMERA);
            integrator.initiateScan();

            //        Use this for more customization
            //        IntentIntegrator integrator = new IntentIntegrator(this);
            //        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            //        integrator.setPrompt("Scan a barcode");
            //        integrator.setCameraId(0);  // Use a specific camera of the device
            //        integrator.setBeepEnabled(false);
            //        integrator.setBarcodeImageEnabled(true);
            //        integrator.initiateScan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_alarmCheckReg.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteAlarmCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfWrmsQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringDetailsOfWrmsQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringDetailsOfWrmsQRCodeScan = result.getContents();
                            if (!base64StringDetailsOfWrmsQRCodeScan.isEmpty() && base64StringDetailsOfWrmsQRCodeScan != null) {
                                mPreventiveMaintenanceSiteAlarmCheckPointsButtonDetailsOfWrmsQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearDetailsOfWrmsQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringDetailsOfWrmsQRCodeScan = "";
                            showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                        }
                    }
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriUploadPhotoOfRegisterFault != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriUploadPhotoOfRegisterFault);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringUploadPhotoOfRegisterFault = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteAlarmCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                }
                break;
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
                onBackPressed();
                return true;

            case R.id.menuSubmit:
                if (checkValidationOfArrayFields() == true) {
                    submitDetails();
                    if (batteryType != null) {
                        if (batteryType.size() > 0) {
                            Intent intent = new Intent(this, PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.class);
                            intent.putExtra("batteryType", batteryType);
                            startActivity(intent);
                        } else {
                            showToast("Battery Type not available for that site");
                        }
                    } else {
                        showToast("Battery Type not available for that site");
                    }
                    //startActivity(new Intent(this, PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.class));
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
        String qrCodeScan = base64StringDetailsOfWrmsQRCodeScan;
        String doorOpenAlarm = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDoorOpenAlarmVal.getText().toString().trim();
        String dgOn = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOnVal.getText().toString().trim();
        String dgOutputAvailable = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewDgOutputAvailableVal.getText().toString().trim();
        String highRoomTemp = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewHighRoomTempVal.getText().toString().trim();
        String fireAndSmoke = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewFireSmokeVal.getText().toString().trim();
        String powerPlantFailure = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewPowerPlantFailureVal.getText().toString().trim();
        String alarmConfirmedByNoc = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewAlarmConfirmedByNocVal.getText().toString().trim();
        // String remarks = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRemarksVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteAlarmCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

        if (qrCodeScan.isEmpty() || qrCodeScan == null) {
            showToast("Scan QR Code");
            return false;
        } else if (doorOpenAlarm.isEmpty() || doorOpenAlarm == null) {
            showToast("Select Door Open Alarm");
            return false;
        } else if (dgOn.isEmpty() || dgOn == null) {
            showToast("Select DG On");
            return false;
        } else if (dgOutputAvailable.isEmpty() || dgOutputAvailable == null) {
            showToast("Select DG Output Available");
            return false;
        } else if (highRoomTemp.isEmpty() || highRoomTemp == null) {
            showToast("Select High Room Temp");
            return false;
        } else if (fireAndSmoke.isEmpty() || fireAndSmoke == null) {
            showToast("Select Fire And Smoke");
            return false;
        } else if (powerPlantFailure.isEmpty() || powerPlantFailure == null) {
            showToast("Select Power Plant Failure");
            return false;
        } else if (alarmConfirmedByNoc.isEmpty() || alarmConfirmedByNoc == null) {
            showToast("Select Alarm Confirmed By Noc");
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
