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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.ShelterCheckPoints;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
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

public class PreventiveMaintenanceSiteShelterCheckPointsActivity extends BaseActivity {
    private static final String TAG = PreventiveMaintenanceSiteShelterCheckPointsActivity.class.getSimpleName();
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaning;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakage;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealed;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatus;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatus;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal;
    private LinearLayout mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal;

    private LinearLayout mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutUploadPhotoOfRegisterFault;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFault;
    private ImageView mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView;

    String str_shelterCleaningVal;
    String str_shelterLeakageVal;
    String str_hatchPlateEntrySealedVal;
    String str_shelterFloorStatusVal;
    String str_shelterEarthingStatusVal;
    String str_registerFaultVal;
    String str_typeOfFaultVal;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private SessionManager sessionManager;
    private OfflineStorageWrapper offlineStorageWrapper;

    MultiSelectDialog multiSelectDialog;

    PreventiveMaintanceSiteTransactionDetails preventiveMaintanceSiteTransactionDetails;
    ShelterCheckPoints shelterCheckPoints;
    ArrayList<Integer> alreadySelectedTypeOfFaultList;
    ArrayList<MultiSelectModel> listOfFaultsTypes;
    ArrayList<String> typeOfFaultList;

    private String base64StringUploadPhotoOfRegisterFault = "";
    private String imageFileUploadPhotoOfRegisterFault;
    private Uri imageFileUriUploadPhotoOfRegisterFault = null;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault = 105;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_shelter_check_points);
        this.setTitle("Shelter Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
        setListner();
        sessionManager = new SessionManager(PreventiveMaintenanceSiteShelterCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, userId, ticketName);
        preventiveMaintanceSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();

        //Code For MultiSelect Type Of Fault
        listOfFaultsTypes = new ArrayList<>();
        alreadySelectedTypeOfFaultList = new ArrayList<>();
        typeOfFaultList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_typeOfFault)));
        int id = 1;
        for (int i = 0; i < typeOfFaultList.size(); i++) {
            listOfFaultsTypes.add(new MultiSelectModel(id, typeOfFaultList.get(i).toString()));
            id++;
        }
        setInputDetails();
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
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText(str_typeOfFaultVal);

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Dialog cancelled");

                    }
                });
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterCleaning);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterCleaningVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakage = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterLeakage);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterLeakageVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealed = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_hatchPlateEntrySealed);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_hatchPlateEntrySealedVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterFloorStatus);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterFloorStatusVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterEarthingStatus);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterEarthingStatusVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_linearLayout_typeOfFault);

        mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutUploadPhotoOfRegisterFault = (LinearLayout) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_linearLayout_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewUploadPhotoOfRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFault = (ImageView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_button_uploadPhotoOfRegisterFault);
        mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_button_uploadPhotoOfRegisterFaultView);
    }

    private void initCombo() {

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterCleaning))),
                        "Shelter Cleaning",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterCleaningVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.setText(str_shelterCleaningVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterLeakage))),
                        "Shelter Leakage",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterLeakageVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.setText(str_shelterLeakageVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_hatchPlateEntrySealed))),
                        "Hatch Plate Entry sealed",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_hatchPlateEntrySealedVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.setText(str_hatchPlateEntrySealedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterFloorStatus))),
                        "Shleter Floor Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterFloorStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.setText(str_shelterFloorStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterEarthingStatus))),
                        "Shelter Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.setText(str_shelterEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_registerFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.setText(str_registerFaultVal);

                        /*if(str_registerFaultVal.equals("Yes")){
                            mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal.setVisibility(View.VISIBLE);
                        }else if(str_registerFaultVal.equals("No")){
                            mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText("");
                            mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal.setVisibility(View.GONE);
                        }*/
                            /*mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText("");
                        mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal.setVisibility(View.GONE);*/
                        visibilityOfTypesOfFault(str_registerFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

                 /*SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText(str_typeOfFaultVal);
                    }
                });*/
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
                onBackPressed();
                return true;

            case R.id.menuSubmit:
                if (checkValidationOfArrayFields() == true) {
                    submitDetails();
                    startActivity(new Intent(this, PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.class));
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setListner() {
        mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    UploadPhotoOfRegisterFault();
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////

        mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUriUploadPhotoOfRegisterFault != null) {
                    GlobalMethods.showImageDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, imageFileUriUploadPhotoOfRegisterFault);
                } else {
                    Toast.makeText(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void UploadPhotoOfRegisterFault() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileUploadPhotoOfRegisterFault = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_sitePremises.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileUploadPhotoOfRegisterFault);
            imageFileUriUploadPhotoOfRegisterFault = FileProvider.getUriForFile(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUriUploadPhotoOfRegisterFault);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_UploadPhotoOfRegisterFault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
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

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                Gson gson = new Gson();
                preventiveMaintanceSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (preventiveMaintanceSiteTransactionDetails != null) {
                    shelterCheckPoints = preventiveMaintanceSiteTransactionDetails.getShelterCheckPoints();
                    if (shelterCheckPoints != null) {
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.setText(shelterCheckPoints.getShelterCleaning());
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.setText(shelterCheckPoints.getShelterLeakage());
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.setText(shelterCheckPoints.getHatchPlateEntrysealed());
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.setText(shelterCheckPoints.getShleterFloorStatus());
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.setText(shelterCheckPoints.getShelterEarthingStatus());
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.setText(shelterCheckPoints.getRegisterFault());
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText(shelterCheckPoints.getTypeOfFault());
                        this.base64StringUploadPhotoOfRegisterFault = shelterCheckPoints.getBase64StringUploadPhotoOfRegisterFault();

                        visibilityOfTypesOfFault(mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.getText().toString());

                        mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                        if (!this.base64StringUploadPhotoOfRegisterFault.isEmpty() && this.base64StringUploadPhotoOfRegisterFault != null) {
                            mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap inImage = decodeFromBase64ToBitmap(this.base64StringUploadPhotoOfRegisterFault);
                            inImage.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
                            imageFileUriUploadPhotoOfRegisterFault = Uri.parse(path);
                        }

                        if (shelterCheckPoints.getTypeOfFault() != null && shelterCheckPoints.getTypeOfFault().length() > 0 && listOfFaultsTypes.size() > 0) {
                            setArrayValuesOfTypeOfFault(mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.getText().toString().trim());
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

            String shelterCleaning = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.getText().toString().trim();
            String shelterLeakage = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.getText().toString().trim();
            String hatchPlateEntrySealed = mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.getText().toString().trim();
            String shelterFloorStatus = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.getText().toString().trim();
            String shelterEarthingStatus = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.getText().toString().trim();
            String resultFault = mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
            String typeOfFault = mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();
            String base64StringUploadPhotoOfRegisterFault = this.base64StringUploadPhotoOfRegisterFault;

            shelterCheckPoints = new ShelterCheckPoints(shelterCleaning, shelterLeakage, hatchPlateEntrySealed, shelterFloorStatus, shelterEarthingStatus, resultFault, typeOfFault, base64StringUploadPhotoOfRegisterFault);
            preventiveMaintanceSiteTransactionDetails.setShelterCheckPoints(shelterCheckPoints);
            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(preventiveMaintanceSiteTransactionDetails);
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileUploadPhotoOfRegisterFault = "";
                    imageFileUriUploadPhotoOfRegisterFault = null;
                    mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void visibilityOfTypesOfFault(String RegisterFault) {
        mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal.setVisibility(View.GONE);
        mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.GONE);
        if (RegisterFault.equals("Yes")) {
            mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutTypeOfFaultVal.setVisibility(View.VISIBLE);
            mPreventiveMaintenanceSiteShelterCheckPointsLinearLayoutUploadPhotoOfRegisterFault.setVisibility(View.VISIBLE);
        } else {
            alreadySelectedTypeOfFaultList = new ArrayList<>();
            setMultiSelectModel();
            mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText("");
            base64StringUploadPhotoOfRegisterFault = "";
            mPreventiveMaintenanceSiteShelterCheckPointsButtonUploadPhotoOfRegisterFaultView.setVisibility(View.GONE);
            imageFileUploadPhotoOfRegisterFault = "";
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public boolean checkValidationOfArrayFields() {
        String shelterCleaning = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.getText().toString().trim();
        String shelterLeakage = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.getText().toString().trim();
        String hatchPlateEntrySealed = mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.getText().toString().trim();
        String shelterEarthingStatus = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.getText().toString().trim();
        String shelterFloorStatus = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.getText().toString().trim();
        String registerFault = mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
        String typeOfFault = mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();


        if (shelterCleaning.isEmpty() || shelterCleaning == null) {
            showToast("Select Shelter Cleaning");
            return false;
        } else if (shelterLeakage.isEmpty() || shelterLeakage == null) {
            showToast("Select Shelter Leakage");
            return false;
        } else if (hatchPlateEntrySealed.isEmpty() || hatchPlateEntrySealed == null) {
            showToast("Select Hatch Plate Entry Sealed");
            return false;
        } else if (shelterFloorStatus.isEmpty() || shelterFloorStatus == null) {
            showToast("Select Shelter Floor Status");
            return false;
        } else if (shelterEarthingStatus.isEmpty() || shelterEarthingStatus == null) {
            showToast("Select Shelter Earthing Status");
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
