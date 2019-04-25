package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.brahamaputra.mahindra.brahamaputra.Data.PmsAmfPanelCheckPointsParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.PmsAmfPanelCheckPointsData;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
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

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_Selected_SiteType;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmServoStabilizerWorkingStatus;

public class PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity extends BaseActivity {

    private static final String TAG = PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.class.getSimpleName();

    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSite;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmsAmfPiuNumber;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewQRCodeScan;
    private ImageView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScan;
    private ImageView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView;
    private ImageView mButtonClearQRCodeScanView;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManual;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypass;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatus;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal;
    private Button mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading;

    private LinearLayout mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutTypeOfFault;
    private LinearLayout mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView;


    String str_noOfPmsAmfPiuAvailableAtSiteVal;
    String str_siteInAutoManualVal;
    String str_looseConnectionBypassVal;
    String str_pmfAmfPiuEarthingStatusVal;
    String str_registerFaultVal;
    String str_typeOfFaultVal;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;

    private String base64StringPmsAmfPanelCheckPointsQRCodeScan = "";
    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    //private String imageFileUploadPhotoOfSitePremises;
    //private Uri imageFileUriUploadPhotoOfSitePremises = null;

    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private ArrayList<PmsAmfPanelCheckPointsData> pmsAmfPanelCheckPointsArrayList;

    private int totalCount = 0;
    private int currentPos = 0;

    private PmsAmfPanelCheckPointsParentData pmsAmfPanelCheckPoints;
    private PreventiveMaintanceSiteTransactionDetails preventiveMaintanceSiteTransactionDetails;

    /*private HotoTransactionData hotoTransactionData;
    private LandDetailsData landDetailsData;*/
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;

    MultiSelectDialog multiSelectDialog;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<String> typeOfFaultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_pms_amf_panel_check_points);
        this.setTitle("PMS/AMF Panel Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, userId, ticketName);
        assignViews();
        initCombo();
        checkCameraPermission();
        initCombo();
        setListner();

        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();

        //Code For MultiSelect Type Of Fault
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSitePmsOrAmfOrPiuAvailableAtSite_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }

        preventiveMaintanceSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        pmsAmfPanelCheckPointsArrayList = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);
        setMultiSelectModel();
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
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setText(str_typeOfFaultVal);

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


    private void assignViews() {
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_noOfPmsAmfPiuAvailableAtSite);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_noOfPmsAmfPiuAvailableAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmsAmfPiuNumber = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_pmsAmfPiuNumber);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_QRCodeScan);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_button_QRCodeScan);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_button_QRCodeScanView);
        mButtonClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManual = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_siteInAutoManual);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_siteInAutoManualVal);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypass = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_looseConnectionBypass);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_looseConnectionBypassVal);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_pmfAmfPiuEarthingStatus);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_pmfAmfPiuEarthingStatusVal);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_button_previousReading);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_button_nextReading);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutTypeOfFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_linearLayout_typeOfFault);

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSitePmsAmfPanelCheckPoints_button_uploadPhotoOfRegisterFaultView);
    }

    private void initCombo() {

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSitePmsOrAmfOrPiuAvailableAtSite_noOfPmsOrAmfOrPiuAvailableAtSite))),
                        "No of PMS/AMF/PIU available at site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfPmsAmfPiuAvailableAtSiteVal = item.get(position);
                        invalidateOptionsMenu();
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal.setText(str_noOfPmsAmfPiuAvailableAtSiteVal);

                        if (pmsAmfPanelCheckPointsArrayList != null && pmsAmfPanelCheckPointsArrayList.size() > 0) {
                            pmsAmfPanelCheckPointsArrayList.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        if (str_noOfPmsAmfPiuAvailableAtSiteVal.equals("0")) {
                            mLinearLayoutContainer.setVisibility(View.GONE);
                        } else {
                            totalCount = Integer.parseInt(str_noOfPmsAmfPiuAvailableAtSiteVal);
                            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmsAmfPiuNumber.setText("Reading: #1");
                            mLinearLayoutContainer.setVisibility(View.VISIBLE);
                            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Finish");
                            } else {
                                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Next Reading");
                            }
                        }
                    }
                });
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSitePmsOrAmfOrPiuAvailableAtSite_siteInAutoOrManual))),
                        "Site in Auto/Manual",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_siteInAutoManualVal = item.get(position);
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.setText(str_siteInAutoManualVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSitePmsOrAmfOrPiuAvailableAtSite_anyLooseConnectionOrBypass))),
                        "Any loose connection/Bypass",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_looseConnectionBypassVal = item.get(position);
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.setText(str_looseConnectionBypassVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSitePmsOrAmfOrPiuAvailableAtSite_pmsOrAmfOrPiuEarthingStatus))),
                        "PMS/AMF/PIU Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmfAmfPiuEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.setText(str_pmfAmfPiuEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSitePmsOrAmfOrPiuAvailableAtSite_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_registerFaultVal = item.get(position);
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.setText(str_registerFaultVal);
                        visibilityOfTypesOfFault(str_registerFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alreadySelectedTypeOfFaultList = new ArrayList<>();
                //setMultiSelectModel();
                if (currentPos > 0) {
                    //Save current ac reading
                    saveRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayRecords(currentPos);
                    //visibilityOfTypesOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                }
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alreadySelectedTypeOfFaultList = new ArrayList<>();
                //setMultiSelectModel();
                if (checkValidationOfArrayFields()) {
                    if (currentPos < (totalCount - 1)) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);
                        //visibilityOfTypesOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());

                    } else if (currentPos == (totalCount - 1)) {
                        //Save Final current reading and submit all AC data
                        saveRecords(currentPos);
                        // visibilityOfTypesOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                        if (checkDuplicationQrCodeNew() == false) {
                            if (checkValidtionForNoOfPmsAmfPuiAvailabelAtSite("onSubmit") == true) {
                                submitDetails();
                                if (sitePmServoStabilizerWorkingStatus.equals("Not Available") && hototicket_Selected_SiteType.equals("Outdoor")) {
                                    startActivity(new Intent(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.class));
                                    finish();
                                } else if (sitePmServoStabilizerWorkingStatus.equals("Not Available")) {
                                    startActivity(new Intent(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, PreventiveMaintenanceSiteShelterCheckPointsActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, PreventiveMaintenanceSiteServoCheckPointsActivity.class));
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void submitDetails() {
        try {
            String noOfPmsAmfPuiAvailableAtSite = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;
            String registerFault = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            pmsAmfPanelCheckPoints = new PmsAmfPanelCheckPointsParentData(noOfPmsAmfPuiAvailableAtSite, pmsAmfPanelCheckPointsArrayList, base64StringUploadPhotoOfRegisterFault, registerFault, typeOfFault);
            preventiveMaintanceSiteTransactionDetails.setPmsAmfPanelCheckPoints(pmsAmfPanelCheckPoints);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(preventiveMaintanceSiteTransactionDetails);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setListner() {

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    DetailsOfWrmsQRCodeScan();
                }
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////
        mButtonClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringPmsAmfPanelCheckPointsQRCodeScan = "";
                mButtonClearQRCodeScanView.setVisibility(View.GONE);
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");
            }
        });

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
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
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (result.getContents() == null) {
                        base64StringPmsAmfPanelCheckPointsQRCodeScan = "";
                        showToast("Cancelled");
                    } else {
                        Object[] isDuplicateQRcode = isDuplicateQRcodeForSitePM(result.getContents());
                        boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                        if (!flagIsDuplicateQRcode) {
                            base64StringPmsAmfPanelCheckPointsQRCodeScan = result.getContents();
                            if (!base64StringPmsAmfPanelCheckPointsQRCodeScan.isEmpty() && base64StringPmsAmfPanelCheckPointsQRCodeScan != null) {
                                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            base64StringPmsAmfPanelCheckPointsQRCodeScan = "";
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
                            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
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
        if (str_noOfPmsAmfPiuAvailableAtSiteVal != null && !str_noOfPmsAmfPiuAvailableAtSiteVal.isEmpty()) {
            if (Integer.valueOf(str_noOfPmsAmfPiuAvailableAtSiteVal) > 0) {
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
                if (checkValidtionForNoOfPmsAmfPuiAvailabelAtSite("onSubmit") == true) {
                                    /*submitDetails();
                if(sitePmServoStabilizerWorkingStatus.equals("Not Available") && hototicket_Selected_SiteType.equals("Outdoor"))
                {
                    startActivity(new Intent(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.class));
                    finish();
                }else if(sitePmServoStabilizerWorkingStatus.equals("Not Available")){
                    startActivity(new Intent(PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.this, PreventiveMaintenanceSiteShelterCheckPointsActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(PreventiveMaintenanc eSitePmsAmfPanelCheckPointsActivity.this, PreventiveMaintenanceSiteServoCheckPointsActivity.class));
                    finish();
                }*/
                    return true;
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setInputDetails(int currentPos) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                Gson gson = new Gson();

                preventiveMaintanceSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);
                pmsAmfPanelCheckPoints = preventiveMaintanceSiteTransactionDetails.getPmsAmfPanelCheckPoints();
                pmsAmfPanelCheckPointsArrayList.addAll(pmsAmfPanelCheckPoints.getPmsAmfPanelCheckPointsData());
                totalCount = Integer.parseInt(pmsAmfPanelCheckPoints.getNoOfPmsAmfPiuAvailableAtSite());
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal.setText(pmsAmfPanelCheckPoints.getNoOfPmsAmfPiuAvailableAtSite());
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.setText(pmsAmfPanelCheckPoints.getRegisterFault());
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setText(pmsAmfPanelCheckPoints.getTypeOfFault());

                if (pmsAmfPanelCheckPoints.getTypeOfFault() != null && pmsAmfPanelCheckPoints.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                    //setArrayValuesOfTypeOfFault(earthingCheckPointsData.get(index).getTypeOfFault());
                    setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                }
                this.base64StringUploadPhotoOfRegisterFault = pmsAmfPanelCheckPoints.getBase64StringUploadPhotoOfRegisterFault();

                visibilityOfTypesOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.getText().toString());

                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                    inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                    imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                }

                invalidateOptionsMenu();

                if (pmsAmfPanelCheckPointsArrayList != null && pmsAmfPanelCheckPointsArrayList.size() > 0) {
                    mLinearLayoutContainer.setVisibility(View.VISIBLE);

                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmsAmfPiuNumber.setText("Reading: #1");

                    base64StringPmsAmfPanelCheckPointsQRCodeScan = pmsAmfPanelCheckPointsArrayList.get(currentPos).getDetailsOfPmsAmfPiuQrCodeScan();
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
                    mButtonClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringPmsAmfPanelCheckPointsQRCodeScan.isEmpty() && base64StringPmsAmfPanelCheckPointsQRCodeScan != null) {
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                        mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }

                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.setText(pmsAmfPanelCheckPointsArrayList.get(currentPos).getSiteInAutoManual());
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.setText(pmsAmfPanelCheckPointsArrayList.get(currentPos).getAnyLooseConnectionBypass());
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.setText(pmsAmfPanelCheckPointsArrayList.get(currentPos).getPmsAmfPiuEarthingStatus());
                   /* mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.setText(pmsAmfPanelCheckPointsArrayList.get(currentPos).getRegisterFault());
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setText(pmsAmfPanelCheckPointsArrayList.get(currentPos).getTypeOfFault());*/




                   /* if (pmsAmfPanelCheckPointsArrayList.get(currentPos).getTypeOfFault() != null && pmsAmfPanelCheckPointsArrayList.get(currentPos).getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {

                        //setArrayValuesOfTypeOfFault(earthingCheckPointsData.get(index).getTypeOfFault());
                        setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                    }*/

                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setVisibility(View.GONE);
                    mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
                    ;
                    if (totalCount > 1) {
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Next Reading");
                    } else {
                        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Finish");
                    }
                } else {
                    showToast("No previous saved data available");
                    mLinearLayoutContainer.setVisibility(View.GONE);
                }
            } else {
                showToast("No previous saved data available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecords(int pos) {

        String detailsOfPmsAmfQrCodeScan = base64StringPmsAmfPanelCheckPointsQRCodeScan;
        String siteInAutoManual = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.getText().toString().trim();
        String anyLooseConnectionBypass = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.getText().toString().trim();
        String pmsAmfPuiEarthingStatus = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.getText().toString().trim();


        PmsAmfPanelCheckPointsData pmsAmfPanelCheckPointsData = new PmsAmfPanelCheckPointsData(detailsOfPmsAmfQrCodeScan, siteInAutoManual, anyLooseConnectionBypass,
                pmsAmfPuiEarthingStatus);
        if (pmsAmfPanelCheckPointsArrayList.size() > 0) {
            if (pos == pmsAmfPanelCheckPointsArrayList.size()) {
                pmsAmfPanelCheckPointsArrayList.add(pmsAmfPanelCheckPointsData);
            } else if (pos < pmsAmfPanelCheckPointsArrayList.size()) {
                pmsAmfPanelCheckPointsArrayList.set(pos, pmsAmfPanelCheckPointsData);
            }
        } else {
            pmsAmfPanelCheckPointsArrayList.add(pmsAmfPanelCheckPointsData);
        }
    }

    private void displayRecords(int pos) {
        if (pmsAmfPanelCheckPointsArrayList.size() > 0 && pos < pmsAmfPanelCheckPointsArrayList.size()) {
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmsAmfPiuNumber.setText("Reading: #" + (pos + 1));

            base64StringPmsAmfPanelCheckPointsQRCodeScan = pmsAmfPanelCheckPointsArrayList.get(pos).getDetailsOfPmsAmfPiuQrCodeScan();
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
            mButtonClearQRCodeScanView.setVisibility(View.GONE);
            if (!base64StringPmsAmfPanelCheckPointsQRCodeScan.isEmpty() && base64StringPmsAmfPanelCheckPointsQRCodeScan != null) {
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.VISIBLE);
                mButtonClearQRCodeScanView.setVisibility(View.VISIBLE);
            }
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.setText(pmsAmfPanelCheckPointsArrayList.get(pos).getSiteInAutoManual());
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.setText(pmsAmfPanelCheckPointsArrayList.get(pos).getAnyLooseConnectionBypass());
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.setText(pmsAmfPanelCheckPointsArrayList.get(pos).getPmsAmfPiuEarthingStatus());
            /*mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.setText(pmsAmfPanelCheckPointsArrayList.get(pos).getRegisterFault());
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setText(pmsAmfPanelCheckPointsArrayList.get(pos).getTypeOfFault());

            if (pmsAmfPanelCheckPointsArrayList.get(pos).getTypeOfFault() != null && pmsAmfPanelCheckPointsArrayList.get(pos).getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                alreadySelectedTypeOfFaultList = new ArrayList<>();
                //setArrayValuesOfTypeOfFault(earthingCheckPointsData.get(pos).getTypeOfFault());
                setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
                setMultiSelectModel();
            }*/

            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setVisibility(View.VISIBLE);
        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Finish");
        } else if (pos == 0) {
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonPreviousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Finish");
            } else {
                mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonNextReading.setText("Next Reading");
            }
        }
    }

    private void visibilityOfTypesOfFault(String RegisterFault) {
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutTypeOfFault.setVisibility(View.GONE);
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutTypeOfFault.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            base64StringUploadPhotoOfRegisterFault = "";
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setText("");
            mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public void clearFields(int indexPos) {
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmsAmfPiuNumber.setText("Reading: #" + (indexPos + 1));

        base64StringPmsAmfPanelCheckPointsQRCodeScan = "";
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsButtonQRCodeScanView.setVisibility(View.GONE);
        mButtonClearQRCodeScanView.setVisibility(View.GONE);

        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.setText("");
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.setText("");
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.setText("");
       /* mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.setText("");
        mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.setText("");*/
    }

    public boolean checkValidationOfArrayFields() {
        String qrCodeScan = base64StringPmsAmfPanelCheckPointsQRCodeScan;
        String siteInAutoManual = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewSiteInAutoManualVal.getText().toString().trim();
        String anyLooseConnectionBypass = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewLooseConnectionBypassVal.getText().toString().trim();
        String pmsAmfPiuEarthingStatus = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewPmfAmfPiuEarthingStatusVal.getText().toString().trim();


        if (qrCodeScan.isEmpty() || qrCodeScan == null) {
            showToast("Please Scan QR Code");
            return false;
        } else if (siteInAutoManual.isEmpty() || siteInAutoManual == null) {
            showToast("Select Site In Auto/Manual");
            return false;
        } else if (anyLooseConnectionBypass.isEmpty() || anyLooseConnectionBypass == null) {
            showToast("Select Any Loose Connection/Bypass");
            return false;
        } else if (pmsAmfPiuEarthingStatus.isEmpty() || pmsAmfPiuEarthingStatus == null) {
            showToast("Select PMS/AMF/PIU Earthing Status");
            return false;
        } else return true;
    }

    private boolean checkDuplicationQrCodeNew() {
        for (int i = 0; i < pmsAmfPanelCheckPointsArrayList.size(); i++) {
            for (int j = i + 1; j < pmsAmfPanelCheckPointsArrayList.size(); j++) {
                //compare list.get(i) and list.get(j)
                if (pmsAmfPanelCheckPointsArrayList.get(i).getDetailsOfPmsAmfPiuQrCodeScan().toString().equals(pmsAmfPanelCheckPointsArrayList.get(j).getDetailsOfPmsAmfPiuQrCodeScan().toString())) {
                    int dup_pos = j + 1;
                    showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkValidtionForNoOfPmsAmfPuiAvailabelAtSite(String methodFlag) {

        String noOfPmsAmfPuiAvailableAtSite = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewNoOfPmsAmfPiuAvailableAtSiteVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSitePmsAmfPanelCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

        if (noOfPmsAmfPuiAvailableAtSite.isEmpty() || noOfPmsAmfPuiAvailableAtSite == null) {
            showToast("Select No Of PMS/AMF/PIU Available At Site");
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
        } else if (Integer.valueOf(noOfPmsAmfPuiAvailableAtSite) > 0) {
            if ((pmsAmfPanelCheckPointsArrayList.size() != Integer.valueOf(noOfPmsAmfPuiAvailableAtSite) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else
            return true;
    }


}
