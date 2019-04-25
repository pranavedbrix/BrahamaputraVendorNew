package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.Data.PowerPlantDetailsData;
import com.brahamaputra.mahindra.brahamaputra.Data.PowerPlantDetailsModulesData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;

public class PowerPlantDetailsModulesReadingsActivity extends BaseActivity {

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private SessionManager sessionManager;

    //private OfflineStorageWrapper offlineStorageWrapper;
    private LinearLayout mLnrModulesPlantDetails;
    private TextView mPowerPlantDetailsModulesTextViewModuleNumber;
    private ImageView mPowerPlantDetailsButtonModuleQRCodeScan;
    private ImageView mPowerPlantDetailsButtonModuleQRCodeScanView;
    private TextView mPowerPlantDetailsTextViewModuleMakeVal;
    private EditText mPowerPlantDetailsEditTextModuleCapacity;
    private EditText mPowerPlantDetailsEditTextBookValueModule;
    private Button mBtnPrevReadingModulesPowerPlant;
    private Button mBtnNextReadingModulesPowerPlant;
    private ImageView button_ClearQRCodeScanView;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    //public static final int MY_FLAG_MODULE_RESULT = 200;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    private String base64StringQRCodeScan = "";
    private String str_makeModule = "";

    private ArrayList<PowerPlantDetailsModulesData> powerPlantDetailsModulesData;
    private ArrayList<PowerPlantDetailsModulesData> powerPlantDetailsModulesDataForDuplicity;
    private ArrayList<PowerPlantDetailsData> powerPlantDetailsDataList;
    private int totalCount = 0;
    private int currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_plant_details_modules_readings);

        this.setTitle("Modules");

        sessionManager = new SessionManager(PowerPlantDetailsModulesReadingsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        //offlineStorageWrapper = OfflineStorageWrapper.getInstance(PowerPlantDetailsModulesReadingsActivity.this, userId, ticketName);

        assignViews();
        initCombo();
        setListners();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        alertDialogManager = new AlertDialogManager(PowerPlantDetailsModulesReadingsActivity.this);
        powerPlantDetailsModulesData = new ArrayList<>();
        powerPlantDetailsDataList = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);

        Intent intent = getIntent();
        totalCount = intent.getIntExtra("numberOfModules", 0);

        if (totalCount > 0) {

            mPowerPlantDetailsModulesTextViewModuleNumber.setText("Reading: #1");
            //lnrPlantDetails.setVisibility(View.VISIBLE);
            //mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.VISIBLE);
            mBtnPrevReadingModulesPowerPlant.setVisibility(View.GONE);
            mBtnNextReadingModulesPowerPlant.setVisibility(View.VISIBLE);
            if (totalCount > 0 && totalCount == 1) {
                mBtnNextReadingModulesPowerPlant.setText("Finish");
            } else {
                mBtnNextReadingModulesPowerPlant.setText("Next Reading");
            }
        } else {
            //lnrPlantDetails.setVisibility(View.GONE);
            //mPowerPlantDetailsLinearLayoutNumberOfPowerPlantWorking.setVisibility(View.GONE);
        }

        button_ClearQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64StringQRCodeScan = "";
                button_ClearQRCodeScanView.setVisibility(View.GONE);
                mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.GONE);
                showToast("Cleared");

            }
        });
    }

    private void setListners() {
        mPowerPlantDetailsButtonModuleQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PowerPlantDetailsModulesReadingsActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(PowerPlantDetailsModulesReadingsActivity.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(PowerPlantDetailsModulesReadingsActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PowerPlantDetailsModulesReadingsActivity.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(PowerPlantDetailsModulesReadingsActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCamera();
                    onClicked(v);
                }
            }
        });


    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClicked(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    private void showAlert() {
        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(PowerPlantDetailsModulesReadingsActivity.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(PowerPlantDetailsModulesReadingsActivity.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(PowerPlantDetailsModulesReadingsActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
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

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(PowerPlantDetailsModulesReadingsActivity.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(PowerPlantDetailsModulesReadingsActivity.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(PowerPlantDetailsModulesReadingsActivity.this);
                            }
                        })
                        .setNegativeButton("DONT ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        }).show();

    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void assignViews() {
        mLnrModulesPlantDetails = (LinearLayout) findViewById(R.id.lnrModulesPlantDetails);
        mPowerPlantDetailsModulesTextViewModuleNumber = (TextView) findViewById(R.id.powerPlantDetails_modules_textView_ModuleNumber);
        mPowerPlantDetailsButtonModuleQRCodeScan = (ImageView) findViewById(R.id.powerPlantDetails_button_ModuleQRCodeScan);
        mPowerPlantDetailsButtonModuleQRCodeScanView = (ImageView) findViewById(R.id.powerPlantDetails_button_ModuleQRCodeScanView);
        mPowerPlantDetailsTextViewModuleMakeVal = (TextView) findViewById(R.id.powerPlantDetails_textView_ModuleMake_val);
        mPowerPlantDetailsEditTextModuleCapacity = (EditText) findViewById(R.id.powerPlantDetails_editText_ModuleCapacity);
        mPowerPlantDetailsEditTextBookValueModule = (EditText) findViewById(R.id.powerPlantDetails_editText_bookValueModule);
        mBtnPrevReadingModulesPowerPlant = (Button) findViewById(R.id.btnPrevReadingModulesPowerPlant);
        mBtnNextReadingModulesPowerPlant = (Button) findViewById(R.id.btnNextReadingModulesPowerPlant);
        button_ClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
    }

    private void initCombo() {
        mPowerPlantDetailsTextViewModuleMakeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PowerPlantDetailsModulesReadingsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_powerPlantDetails_moduleMake))),
                        "Asset Owner",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        str_makeModule = item.get(position);
                        mPowerPlantDetailsTextViewModuleMakeVal.setText(str_makeModule);
                    }
                });
            }
        });

        mBtnPrevReadingModulesPowerPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidtionForArrayFields() == true) {
                    if (currentPos > 0) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos - 1;
                        //move to Next reading
                        displayRecords(currentPos);
                    }
                }
            }
        });

        mBtnNextReadingModulesPowerPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidtionForArrayFields() == true) {
                    if (currentPos < (totalCount - 1)) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    } else if (currentPos == (totalCount - 1)) {
                        //Save Final current reading and submit all AC data
                        saveRecords(currentPos);
                        if (checkDuplicationQrCode() == false) {
                            submitDetails();
                        }
                        //startActivity(new Intent(DetailsOfUnusedMaterials.this, PhotoCaptureActivity.class));
                        //finish();
                    }
                }
            }
        });
    }

    private void submitDetails() {
        Intent data = new Intent();
        data.putExtra("powerPlantDetailsModulesData", powerPlantDetailsModulesData);
        //data.putExtra("powerPlantDetailsModulesData","eq");
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private boolean checkValidtionForArrayFields() {
        if (base64StringQRCodeScan.isEmpty()) {
            showToast("Please Scan QR Code");
            return false;
        } /*else if (checkDuplicationQrCode(currentPos)) {
            return false;
        }*/ else return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (result.getContents() == null) {
                base64StringQRCodeScan = "";
                showToast("Cancelled");
            } else {
                Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                if (!flagIsDuplicateQRcode) {
                    base64StringQRCodeScan = result.getContents();
                    //showToast(base64StringQRCodeScan);
                    if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                        mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                } else {
                    base64StringQRCodeScan = "";
                    showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                }
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void saveRecords(int pos) {

        String qrCode = base64StringQRCodeScan;
        String manufacturer = mPowerPlantDetailsTextViewModuleMakeVal.getText().toString().trim();
        String capacity = mPowerPlantDetailsEditTextModuleCapacity.getText().toString().trim();
        String bookValue = mPowerPlantDetailsEditTextBookValueModule.getText().toString().trim();

        PowerPlantDetailsModulesData obj_powerPlantDetailsModulesData = new PowerPlantDetailsModulesData(qrCode, manufacturer, capacity, bookValue);

        if (powerPlantDetailsModulesData.size() > 0) {
            if (pos == powerPlantDetailsModulesData.size()) {
                powerPlantDetailsModulesData.add(obj_powerPlantDetailsModulesData);
            } else if (pos < powerPlantDetailsModulesData.size()) {
                powerPlantDetailsModulesData.set(pos, obj_powerPlantDetailsModulesData);
            }
        } else {
            powerPlantDetailsModulesData.add(obj_powerPlantDetailsModulesData);
        }
    }

    private void displayRecords(int pos) {
        if (powerPlantDetailsModulesData.size() > 0 && pos < powerPlantDetailsModulesData.size()) {

            mPowerPlantDetailsModulesTextViewModuleNumber.setText("Reading: #" + (pos + 1));

            mPowerPlantDetailsTextViewModuleMakeVal.setText(powerPlantDetailsModulesData.get(pos).getModuleMake());
            mPowerPlantDetailsEditTextModuleCapacity.setText(powerPlantDetailsModulesData.get(pos).getModuleCapacity());
            mPowerPlantDetailsEditTextBookValueModule.setText(powerPlantDetailsModulesData.get(pos).getBookValueModules());
            base64StringQRCodeScan = powerPlantDetailsModulesData.get(pos).getModuleQrCodeScan();

            mBtnPrevReadingModulesPowerPlant.setVisibility(View.VISIBLE);
            mBtnNextReadingModulesPowerPlant.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            mBtnPrevReadingModulesPowerPlant.setVisibility(View.VISIBLE);
            mBtnNextReadingModulesPowerPlant.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            mBtnPrevReadingModulesPowerPlant.setVisibility(View.VISIBLE);
            mBtnNextReadingModulesPowerPlant.setText("Finish");
        } else if (pos == 0) {
            mBtnPrevReadingModulesPowerPlant.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                mBtnNextReadingModulesPowerPlant.setText("Finish");
            } else {
                mBtnNextReadingModulesPowerPlant.setText("Next Reading");
            }
        }
    }

    public void clearFields(int indexPos) {
        mPowerPlantDetailsModulesTextViewModuleNumber.setText("Reading: #" + (indexPos + 1));

        mPowerPlantDetailsTextViewModuleMakeVal.setText("");
        mPowerPlantDetailsEditTextModuleCapacity.setText("");
        mPowerPlantDetailsEditTextBookValueModule.setText("");
        mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.GONE);
        button_ClearQRCodeScanView.setVisibility(View.GONE);

        str_makeModule = "";
        base64StringQRCodeScan = "";
        if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
            mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.VISIBLE);
            button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
        } else {
            mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
        }
    }

    private void setInputDetails(int index) {
        try {
            /*if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
             */
            Intent intent = getIntent();
                /*String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                detailsOfUnusedMaterialsParentData = hotoTransactionData.getDetailsOfUnusedMaterialsParentData();*/

            powerPlantDetailsDataList = (ArrayList<PowerPlantDetailsData>) intent.getSerializableExtra("powerPlantDetailsDataList");
            powerPlantDetailsModulesData = (ArrayList<PowerPlantDetailsModulesData>) intent.getSerializableExtra("powerPlantDetailsModulesData");

            //detailsOfUnusedMaterialsData.addAll(detailsOfUnusedMaterialsParentData.getDetailsOfUnusedMaterialsData());

            totalCount = intent.getIntExtra("numberOfModules", 0);

            //mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal.setText(detailsOfUnusedMaterialsParentData.getNumberofUnusedAssetinSite());

            if (powerPlantDetailsModulesData != null && powerPlantDetailsModulesData.size() > 0) {

                //linearLayout_container.setVisibility(View.VISIBLE);
                mPowerPlantDetailsModulesTextViewModuleNumber.setText("Reading: #1");

                mPowerPlantDetailsEditTextModuleCapacity.setText(powerPlantDetailsModulesData.get(index).getModuleCapacity());
                mPowerPlantDetailsEditTextBookValueModule.setText(powerPlantDetailsModulesData.get(index).getBookValueModules());
                mPowerPlantDetailsTextViewModuleMakeVal.setText(powerPlantDetailsModulesData.get(index).getModuleMake());
                base64StringQRCodeScan = powerPlantDetailsModulesData.get(index).getModuleQrCodeScan();

                mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.GONE);
                button_ClearQRCodeScanView.setVisibility(View.GONE);

                if (!base64StringQRCodeScan.isEmpty() && base64StringQRCodeScan != null) {
                    mPowerPlantDetailsButtonModuleQRCodeScanView.setVisibility(View.VISIBLE);
                    button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                }
                //mDetailsOfUnusedMaterialsEditTextDescriptionVal.setText(detailsOfUnusedMaterialsData.get(index).getAssetDescription());

                mBtnPrevReadingModulesPowerPlant.setVisibility(View.GONE);
                mBtnNextReadingModulesPowerPlant.setVisibility(View.VISIBLE);

                //if (detailsOfUnusedMaterialsData.size() > 1) {
                if (totalCount > 1) {
                    mBtnNextReadingModulesPowerPlant.setText("Next Reading");
                } else {
                    mBtnNextReadingModulesPowerPlant.setText("Finish");
                }
            }

            /*} else {
                showToast("No previous saved data available");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkDuplicationQrCode() {
        if (powerPlantDetailsDataList != null) {
            for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
                for (int k = 0; k <= i; k++) {
                    PowerPlantDetailsData powerPlantDetailsData = powerPlantDetailsDataList.get(k);
                    powerPlantDetailsModulesDataForDuplicity = new ArrayList<>();
                    powerPlantDetailsModulesDataForDuplicity.addAll(powerPlantDetailsData.getPowerPlantDetailsModulesData());
                    if (powerPlantDetailsModulesDataForDuplicity != null) {
                        for (int j = 0; j < powerPlantDetailsModulesDataForDuplicity.size(); j++) {
                            //compare list.get(i) and list.get(j)
                            if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsModulesDataForDuplicity.get(j).getModuleQrCodeScan().toString())) {
                                int dup_pos = j + 1;
                                showToast("QR Code scanned in reading no: " + dup_pos + " was already scanned in Power Plant reading no:" + (i + 1));
                                return true;
                            }
                        }
                    }
                }
            }


            for (int i = 0; i < powerPlantDetailsDataList.size(); i++) {
                for (int j = 0; j < powerPlantDetailsModulesData.size(); j++) {
                    //compare list.get(i) and list.get(j)
                    if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsModulesData.get(j).getModuleQrCodeScan().toString())) {
                        int dup_pos = j + 1;
                        //showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                        showToast("QR Code scanned in reading no: " + dup_pos + " was already scanned in Power Plant reading no:" + (i + 1));

                        //showToast("QR Code already Scanned in Power Plant at " + dup_pos + " Reading No: " + (i + 1));
                        return true;
                    }
                }
            }
        }
        //For Child Array Comparision
        if (powerPlantDetailsModulesData != null) {
            for (int i = 0; i < powerPlantDetailsModulesData.size(); i++) {
                for (int j = i + 1; j < powerPlantDetailsModulesData.size(); j++) {
                    //compare list.get(i) and list.get(j)
                    if (powerPlantDetailsModulesData.get(i).getModuleQrCodeScan().toString().equals(powerPlantDetailsModulesData.get(j).getModuleQrCodeScan().toString())) {
                        int dup_pos = j + 1;
                        showToast("QR Code Scanned in Reading No: " + dup_pos + " was already scanned in reading no:" + (i + 1));
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean checkDuplicationQrCodeInChild() {

        for (int j = 0; j < powerPlantDetailsModulesData.size(); j++) {
            if (base64StringQRCodeScan.equals(powerPlantDetailsModulesData.get(j).getModuleQrCodeScan().toString())) {
                int dup_pos = j + 1;
                showToast("This QR Code Already scanned in Modules Reading Number: " + dup_pos);
                return true;
            }
        }
        return false;
    }


    //008
    private boolean checkDuplicationQrCode1() {
        if (powerPlantDetailsDataList != null) {
            for (int i = 0; i < Math.max(powerPlantDetailsDataList.size(), powerPlantDetailsModulesData.size()); i++) {
                if (powerPlantDetailsDataList.get(i).getqRCodeScan().toString().equals(powerPlantDetailsModulesData.get(i).getModuleQrCodeScan().toString())) {
                    showToast("QR Code Scanned in Reading No: " + i + " was already scanned in reading no:" + (i + 1));
                    return true;
                }
            }
        }
        return false;


        /*for (int i = 0; i < powerPlantDetailsModulesData.size(); i++) {
            if (base64StringQRCodeScan.equals(powerPlantDetailsModulesData.get(i).getModuleQrCodeScan().toString())) {
                if (i == curr_pos) {
                    return checkDuplicationQrCodeInParent();
                } else {
                    int dup_pos = i + 1;
                    showToast("This QR Code Already scanned at Reading Number: " + dup_pos);
                    return true;
                }
            } else if (powerPlantDetailsDataList.size() > 0) {
                return checkDuplicationQrCodeInParent();
            }
        }
        return false;*/
    }

    private boolean checkDuplicationQrCodeInParent() {
        for (int j = 0; j < powerPlantDetailsDataList.size(); j++) {
            if (base64StringQRCodeScan.equals(powerPlantDetailsDataList.get(j).getqRCodeScan().toString())) {
                int dup_pos = j + 1;
                showToast("This QR Code Already scanned in Power Plant Details Reading Number: " + dup_pos);
                return true;
            }
        }
        return false;
    }

}
