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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.DgCheckPointsData;
import com.brahamaputra.mahindra.brahamaputra.Data.DgCheckPointsParentData;
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

public class PreventiveMaintenanceSiteDgCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteDgCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal;

    private LinearLayout mLinearLayoutContainer;

    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewDgNumber;

    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView;
    private ImageView mButtonClearQRCodeScanView;

    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewDgHmrReading;
    private EditText mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading;

    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewPhotoOfDgHmr;
    private ImageView mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmr;
    private ImageView mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView;

    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingCondition;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevel;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTension;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevel;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatus;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatus;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal;

    private LinearLayout mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteDgCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView;

    private Button mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading;
    private LinearLayout mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutTypeOfFault;

    String str_pmSiteDgcpNoOfDgAvailableAtSiteVal = "";
    String str_pmSiteDgcpDGWorkingConditionVal = "";
    String str_pmSiteDgcpCoolentLevelVal = "";
    String str_pmSiteDgcpBeltTensionVal = "";
    String str_pmSiteDgcpEngineLubeOilLevelVal = "";
    String str_pmSiteDgcpSafetyWorkingStatusVal = "";
    String str_pmSiteDgcpPowerCableConnectionStatusVal = "";
    String str_pmSiteDgcpRegisterFaultVal = "";
    String str_pmSiteDgcpTypeOfFaultVal = "";

    private AlertDialogManager alertDialogManager;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_PhotoOfDgHmr = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;
    //public static final int MY_PERMISSIONS_REQUEST_CAMERA_CautionSignBoard = 102;

    private String base64StringDgCheckPointsQRCodeScan = "";
    private String base64StringPhotoOfDgHmr = "";

    //private String imageFileDgCheckPointsQRCodeScan;
    private String imageFilePhotoOfDgHmr;

    //private Uri imageFileUriDgCheckPointsQRCodeScan = null;
    private Uri imageFileUriPhotoOfDgHmr = null;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private String base64StringTakePhotoOfDgHmr = "";
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private ArrayList<DgCheckPointsData> dgCheckPointsData;// replce airConditionersData

    private DgCheckPointsParentData dataList;

    private int totalAcCount = 0;
    private int currentPos = 0;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    int isQrCodeNew = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_dg_check_points);
        this.setTitle("DG Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();

        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceSiteDgCheckPointsActivity.this);
        sessionManager = new SessionManager(PreventiveMaintenanceSiteDgCheckPointsActivity.this);

        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteDgCheckPointsActivity.this, userId, ticketName);
        setListner();

        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();

        dgCheckPointsData = new ArrayList<>();
        currentPos = 0;

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }

        setInputDetails(currentPos);
        invalidateOptionsMenu();
        setMultiSelectModel();
    }

    private void assignViews() {

        mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_noOfDgAvailableAtSite);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_noOfDgAvailableAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_dgNumber);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_QRCodeScan);
        mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_QRCodeScan);
        mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_QRCodeScanView);
        mButtonClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgHmrReading = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_dgHmrReading);
        mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading = (EditText) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_editText_dgHmrReading);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewPhotoOfDgHmr = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_photoOfDgHmr);
        mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmr = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_photoOfDgHmr);
        mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_photoOfDgHmrView);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingCondition = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_dgWorkingCondition);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_dgWorkingConditionVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevel = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_coolentLevel);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_coolentLevelVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTension = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_beltTension);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_beltTensionVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevel = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_engineLubeOilLevel);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_engineLubeOilLevelVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_safetyWorkingStatus);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_safetyWorkingStatusVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_powerCableConnectionStatus);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_powerCableConnectionStatusVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_typeOfFaultVal);

        mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteDgCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_uploadPhotoOfRegisterFaultView);

        mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_button_nextReading);
        mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteDgCheckPoints_linearLayout_typeOfFault);
    }


    /////////////
    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                dataList = pmSiteTransactionDetails.getDgCheckPointsParentData();
                dgCheckPointsData.addAll(dataList.getDgCheckPointsData());


                mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.setText(dataList.getNoOfDgAvailableAtSite());

                mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());
                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();
                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                    setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                }


                str_pmSiteDgcpNoOfDgAvailableAtSiteVal = dataList.getNoOfDgAvailableAtSite();
                invalidateOptionsMenu();


                if (dgCheckPointsData != null && dgCheckPointsData.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewDgNumber.setText("Reading: #1");
                    totalAcCount = Integer.parseInt(dataList.getNoOfDgAvailableAtSite());

                    base64StringDgCheckPointsQRCodeScan = dgCheckPointsData.get(index).getDetailsOfDgQrCodeScan();
                    mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);

                    if (!base64StringDgCheckPointsQRCodeScan.isEmpty() && base64StringDgCheckPointsQRCodeScan != null) {
                        mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }


                    base64StringPhotoOfDgHmr = dgCheckPointsData.get(index).getBase64StringTakePhotoOfDgHmr();
                    mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.GONE);
                    if (!base64StringPhotoOfDgHmr.isEmpty() && base64StringPhotoOfDgHmr != null) {
                        mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.VISIBLE);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringPhotoOfDgHmr);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriPhotoOfDgHmr = Uri.parse(path);
                    }

                    mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading.setText(dgCheckPointsData.get(index).getDgHmrReading());
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.setText(dgCheckPointsData.get(index).getDgWorkingCondition());
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.setText(dgCheckPointsData.get(index).getCoolentLevel());
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.setText(dgCheckPointsData.get(index).getBeltTension());
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.setText(dgCheckPointsData.get(index).getEngineLubeOilLevel());
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.setText(dgCheckPointsData.get(index).getSafetyWorkingStatus());
                    mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.setText(dgCheckPointsData.get(index).getPowerCableConnectionStatus());


                    mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

                    //if (airConditionersData.size() > 1) {
                    if (totalAcCount > 1) {
                        mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Finish");
                    }
                }

            } else {
                showToast("No previous saved data available");
                mLinearLayoutContainer.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
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
                        str_pmSiteDgcpTypeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteDgcpTypeOfFaultVal);

                        /*str_pmSiteDgcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteDgcpTypeOfFaultVal);*/
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");
                    }
                });
    }

    private void resetMultiSelectModel() {
        alreadySelectedTypeOfFaultList = new ArrayList<>();
        setMultiSelectModel();
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

    public boolean checkValidationOfArrayFields() {

        String detailsOfDgQrCodeScan = base64StringDgCheckPointsQRCodeScan;
        String dgHmrReading = mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading.getText().toString().trim();
        String base64StringTakePhotoOfDgHmr = base64StringPhotoOfDgHmr;
        String dgWorkingCondition = mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.getText().toString().trim();
        String coolentLevel = mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.getText().toString().trim();
        String beltTension = mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.getText().toString().trim();
        String engineLubeOilLevel = mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.getText().toString().trim();
        String safetyWorkingStatus = mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.getText().toString().trim();
        String powerCableConnectionStatus = mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.getText().toString().trim();


        if (detailsOfDgQrCodeScan.isEmpty() || detailsOfDgQrCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (dgHmrReading.isEmpty() || dgHmrReading == null) {
            showToast("Enter DG HMR Reading");
            return false;
        } else if (base64StringTakePhotoOfDgHmr.isEmpty() || base64StringTakePhotoOfDgHmr == null) {
            showToast("Please Take A Photo Of DG HMR ");
            return false;
        } else if (dgWorkingCondition.isEmpty() || dgWorkingCondition == null) {
            showToast("Select DG Working Condition");
            return false;
        } else if (coolentLevel.isEmpty() || coolentLevel == null) {
            showToast("Select Coolent Level");
            return false;
        } else if (beltTension.isEmpty() || beltTension == null) {
            showToast("Select Belt Tension");
            return false;
        } else if (engineLubeOilLevel.isEmpty() || engineLubeOilLevel == null) {
            showToast("Select Engine Lube Oil Level");
            return false;
        } else if (safetyWorkingStatus.isEmpty() || safetyWorkingStatus == null) {
            showToast("Select Safety Working Status");
            return false;
        } else if (powerCableConnectionStatus.isEmpty() || powerCableConnectionStatus == null) {
            showToast("Select Power Cable Connection Status");
            return false;
        } else return true;

    }

    private void saveDGCheckRecords(int pos) {

        String detailsOfDgQrCodeScan = base64StringDgCheckPointsQRCodeScan;
        String dgHmrReading = mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading.getText().toString().trim();
        String base64StringTakePhotoOfDgHmr = base64StringPhotoOfDgHmr;
        //String imageFileUploadPhotoOfSitePremises= "";
        String dgWorkingCondition = mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.getText().toString().trim();
        String coolentLevel = mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.getText().toString().trim();
        String beltTension = mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.getText().toString().trim();
        String engineLubeOilLevel = mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.getText().toString().trim();
        String safetyWorkingStatus = mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.getText().toString().trim();
        String powerCableConnectionStatus = mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.getText().toString().trim();
        /*String registerFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();*/


        //String detailsOfDgQrCodeScan, String dgHmrReading, String base64StringTakePhotoOfDgHmr, String imageFileUploadPhotoOfSitePremises,
        // String dgWorkingCondition, String coolentLevel, String beltTension, String engineLubeOilLevel, String safetyWorkingStatus,
        // String powerCableConnectionStatus, String registerFault, String typeOfFault
        DgCheckPointsData dgCheckPointsChild = new DgCheckPointsData(detailsOfDgQrCodeScan, dgHmrReading,
                base64StringTakePhotoOfDgHmr, /*imageFilePhotoOfDgHmr,*/ dgWorkingCondition, coolentLevel, beltTension, engineLubeOilLevel, safetyWorkingStatus,
                powerCableConnectionStatus,/*, registerFault, typeOfFault*/isQrCodeNew);

        if (dgCheckPointsData.size() > 0) {
            if (pos == dgCheckPointsData.size()) {
                dgCheckPointsData.add(dgCheckPointsChild);
            } else if (pos < dgCheckPointsData.size()) {
                dgCheckPointsData.set(pos, dgCheckPointsChild);
            }
        } else {
            dgCheckPointsData.add(dgCheckPointsChild);
        }
    }

    private void displayDGCheckRecords(int pos) {

        if (dgCheckPointsData.size() > 0 && pos < dgCheckPointsData.size()) {

            mPreventiveMaintenanceSiteDgCheckPointsTextViewDgNumber.setText("Reading: #" + (pos + 1));

            base64StringDgCheckPointsQRCodeScan = dgCheckPointsData.get(pos).getDetailsOfDgQrCodeScan();
            mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
            mButtonClearQRCodeScanView.setVisibility(View.GONE);

            if (!base64StringDgCheckPointsQRCodeScan.isEmpty() && base64StringDgCheckPointsQRCodeScan != null) {
                mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
            }

            base64StringPhotoOfDgHmr = dgCheckPointsData.get(pos).getBase64StringTakePhotoOfDgHmr();
            mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.GONE);
            if (!base64StringPhotoOfDgHmr.isEmpty() && base64StringPhotoOfDgHmr != null) {
                mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringPhotoOfDgHmr);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriPhotoOfDgHmr = Uri.parse(path);
            }

            /*imageFilePhotoOfDgHmr = dgCheckPointsData.get(pos).getImageFileTakePhotoOfDgHmr();
            mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.GONE);
            if (imageFilePhotoOfDgHmr != null && imageFilePhotoOfDgHmr.length() > 0) {
                File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFilePhotoOfDgHmr);
                imageFileUriPhotoOfDgHmr = FileProvider.getUriForFile(PreventiveMaintenanceSiteDgCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                if (imageFileUriPhotoOfDgHmr != null) {
                    mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.VISIBLE);
                }
            }*/

            mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading.setText(dgCheckPointsData.get(pos).getDgHmrReading());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.setText(dgCheckPointsData.get(pos).getDgWorkingCondition());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.setText(dgCheckPointsData.get(pos).getCoolentLevel());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.setText(dgCheckPointsData.get(pos).getBeltTension());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.setText(dgCheckPointsData.get(pos).getEngineLubeOilLevel());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.setText(dgCheckPointsData.get(pos).getSafetyWorkingStatus());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.setText(dgCheckPointsData.get(pos).getPowerCableConnectionStatus());
            /*mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.setText(dgCheckPointsData.get(pos).getRegisterFault());
            mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText(dgCheckPointsData.get(pos).getTypeOfFault());

            if (dgCheckPointsData.get(pos).getTypeOfFault() != null && dgCheckPointsData.get(pos).getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                alreadySelectedTypeOfFaultList = new ArrayList<>();
                setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                setMultiSelectModel();
            }*/

            mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalAcCount - 1)) {
            mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalAcCount - 1)) {
                mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Next Reading");
            }
        }

    }

    private void submitDetails() {
        try {

            String noOfDgAvailableAtSite = mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;
            //String numberOfEarthPitVisible = mPreventiveMaintenanceSiteEarthingCheckPointsTextViewNumberOfEarthPitVisibleVal.getText().toString().trim();

            dataList = new DgCheckPointsParentData(noOfDgAvailableAtSite, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault, dgCheckPointsData);

            pmSiteTransactionDetails.setDgCheckPointsParentData(dataList);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(int indexPos) {

        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgNumber.setText("Reading: #" + (indexPos + 1));

        mPreventiveMaintenanceSiteDgCheckPointsEditTextDgHmrReading.setText("");
        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.setText("");
        mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.setText("");
        mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.setText("");
        mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.setText("");
        mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.setText("");
        mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.setText("");
        //mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.setText("");
        //mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText("");

        str_pmSiteDgcpDGWorkingConditionVal = "";
        str_pmSiteDgcpCoolentLevelVal = "";
        str_pmSiteDgcpBeltTensionVal = "";
        str_pmSiteDgcpEngineLubeOilLevelVal = "";
        str_pmSiteDgcpSafetyWorkingStatusVal = "";
        str_pmSiteDgcpPowerCableConnectionStatusVal = "";
        //str_pmSiteDgcpRegisterFaultVal = "";
        //str_pmSiteDgcpTypeOfFaultVal = "";

        base64StringDgCheckPointsQRCodeScan = "";
        base64StringPhotoOfDgHmr = "";
        imageFilePhotoOfDgHmr = "";
        imageFileUriPhotoOfDgHmr = null;

        mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
        mButtonClearQRCodeScanView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.GONE);

    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < dgCheckPointsData.size(); i++) {
            for (int j = i + 1; j < dgCheckPointsData.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (dgCheckPointsData.get(i).getDetailsOfDgQrCodeScan().toString().equals(dgCheckPointsData.get(j).getDetailsOfDgQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkValidationOnChangeNoOfDgAvailable(String NoOfDgAvailable, String methodFlag) {

        String registerFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

        if (NoOfDgAvailable.isEmpty() || NoOfDgAvailable == null) {
            showToast("Select No of DG available at site");
            return false;
        } else if (registerFault.isEmpty() || registerFault == null) {
            showToast("Select Register Fault");
            return false;
        } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
            showToast("Select Type of Fault");
            return false;
        } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
            showToast("Upload Photo Of Register Fault");
            return false;
        } else if ((dgCheckPointsData.size() != Integer.valueOf(NoOfDgAvailable) && methodFlag.equals("onSubmit"))) {
            showToast("Complete the all readings.");//as a mentioned AC in no of AC provided
            return false;
        } else return true;

    }

    //////////////////

    private void visibilityOfTypesOfFault(String RegisterFault) {

        mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteDgCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            base64StringUploadPhotoOfRegisterFault = "";
            imageFileUploadPhotoOfRegisterFault = "";
        }

    }

    private void initCombo() {
        mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_noOfDgAvailableAtSite))),
                        "No of DG available at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {


                        str_pmSiteDgcpNoOfDgAvailableAtSiteVal = item.get(position);
                        invalidateOptionsMenu();
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.setText(str_pmSiteDgcpNoOfDgAvailableAtSiteVal);

                        if (dgCheckPointsData != null && dgCheckPointsData.size() > 0) {
                            dgCheckPointsData.clear();
                        }
                        currentPos = 0;
                        totalAcCount = 0;
                        clearFields(currentPos);

                        // Clear all field value and hide layout If Non AC or O //
                        if (str_pmSiteDgcpNoOfDgAvailableAtSiteVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                        } else {
                            totalAcCount = Integer.parseInt(str_pmSiteDgcpNoOfDgAvailableAtSiteVal);
                            mPreventiveMaintenanceSiteDgCheckPointsTextViewDgNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalAcCount > 0 && totalAcCount == 1) {
                                mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Finish");
                            } else {
                                mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setText("Next Reading");
                            }
                        }


                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_dgWorkingCondition))),
                        "DG Working Condition",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpDGWorkingConditionVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewDgWorkingConditionVal.setText(str_pmSiteDgcpDGWorkingConditionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_coolentLevel))),
                        "Coolent Level",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpCoolentLevelVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewCoolentLevelVal.setText(str_pmSiteDgcpCoolentLevelVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_beltTension))),
                        "Belt Tension",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpBeltTensionVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewBeltTensionVal.setText(str_pmSiteDgcpBeltTensionVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_engineLubeOilLevel))),
                        "Engine Lube Oil Level",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpEngineLubeOilLevelVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewEngineLubeOilLevelVal.setText(str_pmSiteDgcpEngineLubeOilLevelVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_safetyWorkingStatus))),
                        "Safety Working Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpSafetyWorkingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewSafetyWorkingStatusVal.setText(str_pmSiteDgcpSafetyWorkingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_powerCableConnecionStatus))),
                        "Power Cable Connection Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpPowerCableConnectionStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewPowerCableConnectionStatusVal.setText(str_pmSiteDgcpPowerCableConnectionStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteDgcpRegisterFaultVal);
                        visibilityOfTypesOfFault(str_pmSiteDgcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
                /*SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteDgCheckPoints_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteDgcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteDgcpTypeOfFaultVal);
                    }
                });*/
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetMultiSelectModel(); by008
                /*  if (checkValidationOfArrayFields() == true) {*/
                if (currentPos > 0) {
                    //Save current ac reading
                    saveDGCheckRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayDGCheckRecords(currentPos);
                    //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim());
                }
                /* }*/
            }
        });
        mPreventiveMaintenanceSiteDgCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetMultiSelectModel(); by008
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalAcCount - 1)) {
                        //Save current ac reading
                        saveDGCheckRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayDGCheckRecords(currentPos);
                        //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim());

                    } else if (currentPos == (totalAcCount - 1)) {
                        saveDGCheckRecords(currentPos);
                        if (checkDuplicationQrCodeNew() == false) {
                            //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim());
                            if (checkValidationOnChangeNoOfDgAvailable(mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                                submitDetails();
                                startActivity(new Intent(PreventiveMaintenanceSiteDgCheckPointsActivity.this, PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

    }

    private void setListner() {

        mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    PhotoOfDgHmr();
                }
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriPhotoOfDgHmr != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this, imageFileUriPhotoOfDgHmr);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteDgCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DgCheckPointsQRCodeScan();
                }
            }
        });

        mButtonClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDgCheckPointsQRCodeScan = "";
                mButtonClearQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteDgCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteDgCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void PhotoOfDgHmr() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFilePhotoOfDgHmr = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFilePhotoOfDgHmr);
            imageFileUriPhotoOfDgHmr = FileProvider.getUriForFile(PreventiveMaintenanceSiteDgCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriPhotoOfDgHmr);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_PhotoOfDgHmr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DgCheckPointsQRCodeScan() {
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
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteDgCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteDgCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteDgCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_PhotoOfDgHmr:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriPhotoOfDgHmr != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriPhotoOfDgHmr);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringPhotoOfDgHmr = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFilePhotoOfDgHmr = "";
                    imageFileUriPhotoOfDgHmr = null;
                    mPreventiveMaintenanceSiteDgCheckPointsButtonPhotoOfDgHmrView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA:
                IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                if (result != null) {
                    mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringDgCheckPointsQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringDgCheckPointsQRCodeScan = result.getContents();
                            if (!base64StringDgCheckPointsQRCodeScan.isEmpty() && base64StringDgCheckPointsQRCodeScan != null) {
                                mPreventiveMaintenanceSiteDgCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringDgCheckPointsQRCodeScan = "";
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
                            mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteDgCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
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
        if (str_pmSiteDgcpNoOfDgAvailableAtSiteVal != null && !str_pmSiteDgcpNoOfDgAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_pmSiteDgcpNoOfDgAvailableAtSiteVal) > 0) {
                shareItem.setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuSubmit:

                str_pmSiteDgcpNoOfDgAvailableAtSiteVal = mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.getText().toString();
                String registerFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
                String typeOfFault = mPreventiveMaintenanceSiteDgCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

                if (str_pmSiteDgcpNoOfDgAvailableAtSiteVal == null || str_pmSiteDgcpNoOfDgAvailableAtSiteVal.equals("")) {
                    showToast("Select No of DG available at site");
                } else if (registerFault.isEmpty() || registerFault == null) {
                    showToast("Select Register Fault");
                } else if ((typeOfFault.isEmpty() || typeOfFault == null) && registerFault.equals("Yes")) {
                    showToast("Select Type of Fault");
                } else if ((base64StringUploadPhotoOfRegisterFault.isEmpty() || base64StringUploadPhotoOfRegisterFault == null) && registerFault.equals("Yes")) {
                    showToast("Upload Photo Of Register Fault");
                } else {
                    if (checkValidationOnChangeNoOfDgAvailable(mPreventiveMaintenanceSiteDgCheckPointsTextViewNoOfDgAvailableAtSiteVal.getText().toString().trim(), "onSubmit") == true) {
                        submitDetails();
                        startActivity(new Intent(this, Solar_Power_System.class));
                        finish();
                    }
                }

                /*startActivity(new Intent(this, PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.class));
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
