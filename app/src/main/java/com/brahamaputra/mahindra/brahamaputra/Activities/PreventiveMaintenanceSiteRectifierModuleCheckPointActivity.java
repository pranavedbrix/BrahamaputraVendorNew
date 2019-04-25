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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.RectifierModuleCheckPointData;
import com.brahamaputra.mahindra.brahamaputra.Data.RectifierModuleCheckPointParentData;
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

public class PreventiveMaintenanceSiteRectifierModuleCheckPointActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorking;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSite;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierModuleNumber;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewDetailsOfRectifierModuleQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView;
    private ImageView mButtonClearDetailsOfRectifierModuleQRCodeScanView;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaning;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierPhotoBeforeCleaning;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaning;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierPhotoAfterCleaning;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaning;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal;
    private Button mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading;
    private LinearLayout mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutTypeOfFault;

    private LinearLayout mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView;


    String str_noOfRectifierModuleAvailableAtSiteVal;
    String str_noOfModulesWorkingVal;
    //String str_noOfFaultyModulesInSiteVal;
    String str_rectifierCleaningVal;
    String str_registerFaultVal;
    String str_typeOfFaultVal;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_RectifierPhotoBeforeCleaning = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_RectifierPhotoAfterCleaning = 102;

    private String base64StringDetailsOfRectifierModuleQRCodeScan = "";
    //private String imageFileDetailsOfRectifierModuleQRCodeScan;
    //private Uri imageFileUriDetailsOfRectifierModuleQRCodeScan = null;

    private String base64StringRectifierPhotoBeforeCleaning = "";
    private String base64StringRectifierPhotoAfterCleaning = "";

    private String imageFileRectifierPhotoBeforeCleaning;
    private String imageFileRectifierPhotoAfterCleaning;

    private Uri imageFileUriRectifierPhotoBeforeCleaning = null;
    private Uri imageFileUriRectifierPhotoAfterCleaning = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    /*private HotoTransactionData hotoTransactionData;
    private LandDetailsData landDetailsData;*/
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    private PreventiveMaintanceSiteTransactionDetails pmSiteTransactionDetails;
    private RectifierModuleCheckPointParentData dataList;
    private RectifierModuleCheckPointData rectifierModuleCheckPointData;
    private ArrayList<RectifierModuleCheckPointData> rectifierModuleCheckPointDataList;
    private int totalCount = 0;
    private int currentPos = 0;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    int isQrCodeNew = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_rectifier_module_check_point);
        this.setTitle("Rectifier Module Check Point");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        checkCameraPermission();
        setListner();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, userId, ticketName);

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteRectifierModuleCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }

        pmSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        rectifierModuleCheckPointDataList = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);
        setMultiSelectModel();

    }

    private void setInputDetails(int currentPos) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                pmSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                dataList = pmSiteTransactionDetails.getRectifierModuleCheckPoint();
                rectifierModuleCheckPointDataList.addAll(dataList.getRectifierModuleCheckPointData());
                totalCount = Integer.parseInt(dataList.getNoOfRectifierModuleAvailableAtSite());

                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal.setText("" + totalCount);
                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.setText(dataList.getNoOfModulesWorking());
                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.setText(dataList.getNoOfFaultyModulesInSite());

                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.setText(dataList.getRegisterFault());
                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.setText(dataList.getTypeOfFault());
                this.base64StringUploadPhotoOfRegisterFault = dataList.getBase64StringUploadPhotoOfRegisterFault();

                visibilityOfTypesOfFault(dataList.getRegisterFault());

                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                if (dataList.getTypeOfFault() != null && dataList.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                    setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.getText().toString().trim());
                }
                invalidateOptionsMenu();

                if (rectifierModuleCheckPointDataList != null && rectifierModuleCheckPointDataList.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);

                    mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierModuleNumber.setText("Reading: #1");

                    base64StringDetailsOfRectifierModuleQRCodeScan = rectifierModuleCheckPointDataList.get(currentPos).getDetailsOfRectifierModuleQrCodeScan();
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringDetailsOfRectifierModuleQRCodeScan.isEmpty() && base64StringDetailsOfRectifierModuleQRCodeScan != null) {
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.setText(rectifierModuleCheckPointDataList.get(currentPos).getRectifierCleaning());

                    base64StringRectifierPhotoBeforeCleaning = rectifierModuleCheckPointDataList.get(currentPos).getBase64RectifierPhotoBeforeCleaning();//////002
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.GONE);
                    if (!base64StringRectifierPhotoBeforeCleaning.isEmpty() && base64StringRectifierPhotoBeforeCleaning != null) {
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.VISIBLE);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringRectifierPhotoBeforeCleaning);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriRectifierPhotoBeforeCleaning = Uri.parse(path);
                    }

                    base64StringRectifierPhotoAfterCleaning = rectifierModuleCheckPointDataList.get(currentPos).getBase64RectifierPhotoAfterCleaning();//////002
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.GONE);
                    if (!base64StringRectifierPhotoAfterCleaning.isEmpty() && base64StringRectifierPhotoAfterCleaning != null) {
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.VISIBLE);

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        Bitmap inImage = decodeFromBase64ToBitmap(base64StringRectifierPhotoAfterCleaning);
                        inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                        imageFileUriRectifierPhotoAfterCleaning = Uri.parse(path);
                    }

                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setVisibility(View.VISIBLE);
                    //visibleTypeOfFaultField();
                    if (totalCount > 1) {
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Finish");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No previous saved data available", Toast.LENGTH_SHORT).show();
                    mLinearLayoutContainer.setVisibility(View.GONE);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMultiSelectModel() {
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
                        str_typeOfFaultVal = dataString;
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.setText(str_typeOfFaultVal);

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");
                    }
                });
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

    private void initCombo() {

        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteRectifierModuleCheckPoints_noOfRectifierModuleAvailableAtSite))),
                        "No of Rectifier Module available at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfRectifierModuleAvailableAtSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal.setText(str_noOfRectifierModuleAvailableAtSiteVal);

                        invalidateOptionsMenu();
                        if (rectifierModuleCheckPointDataList != null && rectifierModuleCheckPointDataList.size() > 0) {
                            rectifierModuleCheckPointDataList.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        if (str_noOfRectifierModuleAvailableAtSiteVal.equals("0")) {
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.setText("0");
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.setText("0");
                            mLinearLayoutContainer.setVisibility(View.GONE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.GONE);
                        } else {
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.setText("");
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.setText("");

                            totalCount = Integer.parseInt(str_noOfRectifierModuleAvailableAtSiteVal);
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierModuleNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            //mAirConditionersLinearLayoutNumberOfACInWorkingCondition.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Finish");

                            } else {
                                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Next Reading");
                            }
                        }
                    }
                });
            }
        });


        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteRectifierModuleCheckPoints_noOfModulesWorking))),
                        "No of Modules Working",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();


                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        if (mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Must be select no of rectifier module available at site", Toast.LENGTH_LONG).show();
                        } else {
                            str_noOfModulesWorkingVal = item.get(position);
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.setText(str_noOfModulesWorkingVal);
                            int noOfWorkingModules = Integer.parseInt(str_noOfModulesWorkingVal);
                            int noOfModuleAvailableInSite = Integer.parseInt(str_noOfRectifierModuleAvailableAtSiteVal);
                            int remainingFaultyModules = 0;
                            if (noOfWorkingModules > noOfModuleAvailableInSite) {
                                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.setText("");
                                Toast.makeText(getApplicationContext(), "Wrong input must be less then or equal to no of available module in site.", Toast.LENGTH_LONG).show();
                            } else {
                                remainingFaultyModules = noOfModuleAvailableInSite - noOfWorkingModules;
                                mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.setText("" + remainingFaultyModules);
                            }
                        }
                    }
                });
            }
        });

        /*mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteRectifierModuleCheckPoints_noOfFaultyModulesInSite))),
                        "No of Faulty Modules in Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfFaultyModulesInSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.setText(str_noOfFaultyModulesInSiteVal);
                    }
                });
            }
        });*/

        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteRectifierModuleCheckPoints_rectifierCleaning))),
                        "Rectifier Cleaning",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_rectifierCleaningVal = item.get(position);
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.setText(str_rectifierCleaningVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteRectifierModuleCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_registerFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.setText(str_registerFaultVal);
                        visibilityOfTypesOfFault(str_registerFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (checkValidationOfArrayFields() == true) {*/
                    if (currentPos > 0) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos - 1;
                        //move to Next reading
                        displayRecords(currentPos);
                        //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.getText().toString().trim());
                    }
               /* }*/
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidationOfArrayFields() == true) {
                    if (currentPos < (totalCount - 1)) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);
                        //visibilityOfTypesOfFault(mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.getText().toString().trim());
                    } else if (currentPos == (totalCount - 1)) {
                        saveRecords(currentPos);
                        // visibilityOfTypesOfFault(mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.getText().toString().trim());
                        if (checkDuplicationQrCodeNew() == false) {
                            if (checkValidationonSubmit("onSubmit") == true) {
                                submitDetails();
                                startActivity(new Intent(getApplicationContext(), PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.class));
                                finish();
                            }
                        }
                    }
                }
            }
        });

    }

    private void submitDetails() {
        try {
            String noOfRectifierModuleAvailableAtSite = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal.getText().toString().trim();
            String noOfModulesWorking = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.getText().toString().trim();
            String noOfFaultyModulesAtSite = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.getText().toString().trim();
            String registerFault = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;
            dataList = new RectifierModuleCheckPointParentData(noOfRectifierModuleAvailableAtSite, noOfModulesWorking,
                    noOfFaultyModulesAtSite, rectifierModuleCheckPointDataList, registerFault, typeOfFault, base64StringUploadPhotoOfRegisterFault);
            pmSiteTransactionDetails.setRectifierModuleCheckPoint(dataList);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(pmSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_noOfRectifierModuleAvailableAtSite);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_noOfRectifierModuleAvailableAtSiteVal);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorking = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_noOfModulesWorking);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_noOfModulesWorkingVal);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_noOfFaultyModulesInSite);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_noOfFaultyModulesInSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierModuleNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_rectifierModuleNumber);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewDetailsOfRectifierModuleQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_detailsOfRectifierModuleQRCodeScan);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_detailsOfRectifierModuleQRCodeScan);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_detailsOfRectifierModuleQRCodeScanView);
        mButtonClearDetailsOfRectifierModuleQRCodeScanView = (ImageView) findViewById(R.id.button_ClearDetailsOfRectifierModuleQRCodeScanView);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_rectifierCleaning);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_rectifierCleaningVal);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierPhotoBeforeCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_rectifierPhotoBeforeCleaning);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaning = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_rectifierPhotoBeforeCleaning);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_rectifierPhotoBeforeCleaningView);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierPhotoAfterCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_rectifierPhotoAfterCleaning);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaning = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_rectifierPhotoAfterCleaning);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_rectifierPhotoAfterCleaningView);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_registerFault);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_registerFaultVal);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_typeOfFault);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_previousReading);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_nextReading);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_linearLayout_typeOfFault);

        mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteRectifierModuleCheckPoint_button_uploadPhotoOfRegisterFaultView);
    }

    private void saveRecords(int currentPos) {
        try {
            String base64RectifierModuleDetailsQrCodeScan = base64StringDetailsOfRectifierModuleQRCodeScan;
            String base64RectifierPhotoBeforeCleaning = base64StringRectifierPhotoBeforeCleaning;
            String base64RectifierPhotoAfterCleaning = base64StringRectifierPhotoAfterCleaning;
            String rectifierCleaning = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.getText().toString().trim();

            rectifierModuleCheckPointData = new RectifierModuleCheckPointData(base64RectifierModuleDetailsQrCodeScan, base64RectifierPhotoBeforeCleaning, base64RectifierPhotoAfterCleaning
                    , rectifierCleaning/*,isQrCodeNew*/);
            if (rectifierModuleCheckPointDataList.size() > 0) {
                if (currentPos == rectifierModuleCheckPointDataList.size()) {
                    rectifierModuleCheckPointDataList.add(rectifierModuleCheckPointData);
                } else if (currentPos < rectifierModuleCheckPointDataList.size()) {
                    rectifierModuleCheckPointDataList.set(currentPos, rectifierModuleCheckPointData);
                }
            } else {
                rectifierModuleCheckPointDataList.add(rectifierModuleCheckPointData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayRecords(int currentPos) {
        if (rectifierModuleCheckPointDataList.size() > 0 && currentPos < rectifierModuleCheckPointDataList.size()) {
            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierModuleNumber.setText("Reading: #" + (currentPos + 1));

            base64StringDetailsOfRectifierModuleQRCodeScan = rectifierModuleCheckPointDataList.get(currentPos).getDetailsOfRectifierModuleQrCodeScan();//////001
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
            mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringDetailsOfRectifierModuleQRCodeScan.isEmpty() && base64StringDetailsOfRectifierModuleQRCodeScan != null) {
                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.VISIBLE);
            }

            base64StringRectifierPhotoBeforeCleaning = rectifierModuleCheckPointDataList.get(currentPos).getBase64RectifierPhotoBeforeCleaning();//////002
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.GONE);
            if (!base64StringRectifierPhotoBeforeCleaning.isEmpty() && base64StringRectifierPhotoBeforeCleaning != null) {
                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.VISIBLE);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringRectifierPhotoBeforeCleaning);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriRectifierPhotoBeforeCleaning = Uri.parse(path);
            }

            base64StringRectifierPhotoAfterCleaning = rectifierModuleCheckPointDataList.get(currentPos).getBase64RectifierPhotoAfterCleaning();//////002
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.GONE);
            if (!base64StringRectifierPhotoAfterCleaning.isEmpty() && base64StringRectifierPhotoAfterCleaning != null) {
                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.VISIBLE);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Bitmap inImage = decodeFromBase64ToBitmap(base64StringRectifierPhotoAfterCleaning);
                inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                imageFileUriRectifierPhotoAfterCleaning = Uri.parse(path);
            }

            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.setText(rectifierModuleCheckPointDataList.get(currentPos).getRectifierCleaning());
           /* mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.setText(rectifierModuleCheckPointDataList.get(currentPos).getRegisterFault());
            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.setText(rectifierModuleCheckPointDataList.get(currentPos).getTypeOfFault());*/

            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setVisibility(View.VISIBLE);
        } else {
            clearFields(currentPos);
        }

        if (currentPos > 0 && currentPos < (totalCount - 1)) {
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Next Reading");
        } else if (currentPos > 0 && currentPos == (totalCount - 1)) {
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Finish");
        } else if (currentPos == 0) {
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonPreviousReading.setVisibility(View.GONE);
            if (currentPos == (totalCount - 1)) {
                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonNextReading.setText("Next Reading");
            }
        }
    }

    private void visibilityOfTypesOfFault(String RegisterFault) {
        mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteRectifierModuleCheckPointLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            base64StringUploadPhotoOfRegisterFault = "";
            mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    public boolean checkValidationOfArrayFields() {
        String qrCodeScan = base64StringDetailsOfRectifierModuleQRCodeScan;
        String rectifierCleaning = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.getText().toString().trim();
        String rectifierPhotoBeforeCleaning = base64StringRectifierPhotoBeforeCleaning;
        String rectifierPhotoAfterCleaning = base64StringRectifierPhotoAfterCleaning;

        if (qrCodeScan.isEmpty() || qrCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (rectifierCleaning.isEmpty() || rectifierCleaning == null) {
            showToast("Select Rectifier Cleaning");
            return false;
        } else if (rectifierPhotoBeforeCleaning.isEmpty() || rectifierPhotoBeforeCleaning == null) {
            showToast("Please Take Rectifier Photo Before Cleaning");
            return false;
        } else if (rectifierPhotoAfterCleaning.isEmpty() || rectifierPhotoAfterCleaning == null) {
            showToast("Please Take Rectifier Photo After Cleaning");
            return false;
        } else return true;
    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < rectifierModuleCheckPointDataList.size(); i++) {
            for (int j = i + 1; j < rectifierModuleCheckPointDataList.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (rectifierModuleCheckPointDataList.get(i).getDetailsOfRectifierModuleQrCodeScan().toString().equals(rectifierModuleCheckPointDataList.get(j).getDetailsOfRectifierModuleQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkValidationonSubmit(String methodFlag) {

        String totalNoOFRectifierModule = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfRectifierModuleAvailableAtSiteVal.getText().toString().trim();
        String noOfModulesWorking = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfModulesWorkingVal.getText().toString().trim();
        String noOfFaultyModulesInSite = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewNoOfFaultyModulesInSiteVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.getText().toString().trim();

        if (totalNoOFRectifierModule.isEmpty() || totalNoOFRectifierModule == null) {
            showToast("Select No OF Rectifier Module Available At Site");
            return false;
        } else if (noOfModulesWorking.isEmpty() || noOfModulesWorking == null) {
            showToast("Select No Of Modules Working");
            return false;
        } else if (noOfFaultyModulesInSite.isEmpty() || noOfFaultyModulesInSite == null) {
            showToast("Select No Of Faulty Modules In Site");
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
        } else if (Integer.valueOf(totalNoOFRectifierModule) > 0) {
            if ((rectifierModuleCheckPointDataList.size() != Integer.valueOf(totalNoOFRectifierModule) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;
    }

    private void clearFields(int currentPos) {
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
        mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.GONE);

        mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRectifierCleaningVal.setText("");
        //mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewRegisterFaultVal.setText("");
        //mPreventiveMaintenanceSiteRectifierModuleCheckPointTextViewTypeOfFaultVal.setText("");
    }

    private void setListner() {

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DgCheckPointsQRCodeScan();
                }
            }
        });

        mButtonClearDetailsOfRectifierModuleQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringDetailsOfRectifierModuleQRCodeScan = "";
                mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    rectifierPhotoBeforeCleaning();
                }
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    rectifierPhotoAfterCleaning();
                }
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriRectifierPhotoBeforeCleaning != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, imageFileUriRectifierPhotoBeforeCleaning);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriRectifierPhotoAfterCleaning != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, imageFileUriRectifierPhotoAfterCleaning);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
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

    private void rectifierPhotoBeforeCleaning() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileRectifierPhotoBeforeCleaning = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileRectifierPhotoBeforeCleaning);
            imageFileUriRectifierPhotoBeforeCleaning = FileProvider.getUriForFile(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriRectifierPhotoBeforeCleaning);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_RectifierPhotoBeforeCleaning);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rectifierPhotoAfterCleaning() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileRectifierPhotoAfterCleaning = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";
            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileRectifierPhotoAfterCleaning);
            imageFileUriRectifierPhotoAfterCleaning = FileProvider.getUriForFile(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriRectifierPhotoAfterCleaning);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_RectifierPhotoAfterCleaning);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
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
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringDetailsOfRectifierModuleQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());

                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringDetailsOfRectifierModuleQRCodeScan = result.getContents();
                            if (!base64StringDetailsOfRectifierModuleQRCodeScan.isEmpty() && base64StringDetailsOfRectifierModuleQRCodeScan != null) {
                                mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearDetailsOfRectifierModuleQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringDetailsOfRectifierModuleQRCodeScan = "";
                            showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                        }
                    }
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_RectifierPhotoBeforeCleaning:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriRectifierPhotoBeforeCleaning != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriRectifierPhotoBeforeCleaning);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringRectifierPhotoBeforeCleaning = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileRectifierPhotoBeforeCleaning = "";
                    imageFileUriRectifierPhotoBeforeCleaning = null;
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoBeforeCleaningView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_RectifierPhotoAfterCleaning:
                if (resultCode == RESULT_OK) {
                    if (imageFileUriRectifierPhotoAfterCleaning != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUriRectifierPhotoAfterCleaning);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringRectifierPhotoAfterCleaning = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileRectifierPhotoAfterCleaning = "";
                    imageFileUriRectifierPhotoAfterCleaning = null;
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonRectifierPhotoAfterCleaningView.setVisibility(View.GONE);
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
                            mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteRectifierModuleCheckPointButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
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
        if (str_noOfRectifierModuleAvailableAtSiteVal != null && !str_noOfRectifierModuleAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_noOfRectifierModuleAvailableAtSiteVal) > 0) {
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
                if (checkValidationonSubmit("onSubmit") == true) {
                    submitDetails();
                    startActivity(new Intent(this, PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.class));
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
}
