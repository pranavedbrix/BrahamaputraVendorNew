package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.Data.DetailsOfUnusedMaterialsData;
import com.brahamaputra.mahindra.brahamaputra.Data.DetailsOfUnusedMaterialsParentData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;

import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailsOfUnusedMaterials extends BaseActivity {

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ArrayList<DetailsOfUnusedMaterialsData> detailsOfUnusedMaterialsData;
    private SessionManager sessionManager;

    private TextView mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSite;
    private TextView mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal;

    private TextView mDetailsOfUnusedMaterialsTextViewTypeOfAsset;
    private TextView mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal;
    private TextView mDetailsOfUnusedMaterialsTextViewAssetMake;
    private TextView mDetailsOfUnusedMaterialsTextViewAssetMakeVal;
    private TextView mDetailsOfUnusedMaterialsTextViewAssetStatus;
    private TextView mDetailsOfUnusedMaterialsTextViewAssetStatusVal;

    private EditText mDetailsOfUnusedMaterialsEditTextDescriptionVal;


    String str_numberofUnusedAssetinSite;
    String str_typeOfAsset;
    String str_assetMake;
    String str_assetStatus;

    private DetailsOfUnusedMaterialsParentData detailsOfUnusedMaterialsParentData;
    private int totalCount = 0;
    private int currentPos = 0;
    private Button detailsOfUnusedMaterials_button_previousReading;
    private Button detailsOfUnusedMaterials_button_nextReading;
    private TextView detailsOfUnusedMaterials_textView_Number;
    private LinearLayout linearLayout_container;
    private LinearLayout linearLayout_assetMake;
    private LinearLayout linearLayout_assetStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_unused_materials);
        this.setTitle("Details Of Unused Materials");

        sessionManager = new SessionManager(DetailsOfUnusedMaterials.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(DetailsOfUnusedMaterials.this, userId, ticketName);

        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();


        detailsOfUnusedMaterialsData = new ArrayList<>();
        currentPos = 0;
        setInputDetails(currentPos);
    }

    private void assignViews() {
        mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSite = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_NumberofUnusedAssetinSite);
        mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_NumberofUnusedAssetinSite_val);

        mDetailsOfUnusedMaterialsTextViewTypeOfAsset = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_TypeOfAsset);
        mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_TypeOfAsset_val);

        mDetailsOfUnusedMaterialsTextViewAssetMake = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_AssetMake);
        mDetailsOfUnusedMaterialsTextViewAssetMakeVal = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_AssetMake_val);
        mDetailsOfUnusedMaterialsTextViewAssetStatus = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_AssetStatus);
        mDetailsOfUnusedMaterialsTextViewAssetStatusVal = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_AssetStatus_val);
        mDetailsOfUnusedMaterialsEditTextDescriptionVal = (EditText) findViewById(R.id.detailsOfUnusedMaterials_editText_description_val);

        detailsOfUnusedMaterials_button_nextReading = (Button) findViewById(R.id.btnNextReading);
        detailsOfUnusedMaterials_button_previousReading = (Button) findViewById(R.id.btnPrevReading);
        detailsOfUnusedMaterials_textView_Number = (TextView) findViewById(R.id.detailsOfUnusedMaterials_textView_Number);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);
        linearLayout_assetMake = (LinearLayout) findViewById(R.id.linearLayout_assetMake);
        linearLayout_assetStatus = (LinearLayout) findViewById(R.id.linearLayout_assetStatus);

        linearLayout_container.setVisibility(View.GONE);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {
        mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_NumberofUnusedAssetinSite))),
                        "Number of Unused Asset in Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberofUnusedAssetinSite = item.get(position);
                        invalidateOptionsMenu();
                        mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal.setText(str_numberofUnusedAssetinSite);

                        //clear AC collection empty by select / changing value of No of Ac provided
                        if (detailsOfUnusedMaterialsData != null && detailsOfUnusedMaterialsData.size() > 0) {
                            detailsOfUnusedMaterialsData.clear();
                        }
                        currentPos = 0;
                        totalCount = 0;
                        clearFields(currentPos);
                        totalCount = Integer.parseInt(str_numberofUnusedAssetinSite);

                        // Clear all field value and hide layout If Non AC or O //
                        if (totalCount > 0) {

                            detailsOfUnusedMaterials_textView_Number.setText("Reading: #1");
                            linearLayout_container.setVisibility(View.VISIBLE);
                            detailsOfUnusedMaterials_button_previousReading.setVisibility(View.GONE);
                            detailsOfUnusedMaterials_button_nextReading.setVisibility(View.VISIBLE);
                            if (totalCount > 0 && totalCount == 1) {
                                detailsOfUnusedMaterials_button_nextReading.setText("Finish");
                            } else {
                                detailsOfUnusedMaterials_button_nextReading.setText("Next Reading");
                            }
                        } else {
                            linearLayout_container.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


        mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_TypeOfAsset))),
                        "Type of Asset",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfAsset = item.get(position);
                        mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.setText(str_typeOfAsset);

                        if (str_typeOfAsset.equals("General Item")) {
                            linearLayout_assetStatus.setVisibility(View.GONE);
                            linearLayout_assetMake.setVisibility(View.GONE);
                        } else {
                            linearLayout_assetStatus.setVisibility(View.VISIBLE);
                            linearLayout_assetMake.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });

        mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.getText().toString().trim().isEmpty() && mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.getText().toString().trim() != null) {
                    OnChangeTypeOfAssetsetArrayListAssetMake(mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.getText().toString());
                } else {
                    showToast("Select Type of Asset");
                }
            }
        });
        mDetailsOfUnusedMaterialsTextViewAssetStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mDetailsOfUnusedMaterialsTextViewAssetMakeVal.getText().toString().trim().isEmpty() && mDetailsOfUnusedMaterialsTextViewAssetMakeVal.getText().toString().trim() != null) {
                    OnChangeAssetMakeArrayListAssetStatus();
                } else {
                    showToast("Select Asset Make");
                }

            }
        });

        detailsOfUnusedMaterials_button_previousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPos > 0) {
                    //Save current ac reading
                    saveRecords(currentPos);
                    currentPos = currentPos - 1;
                    //move to Next reading
                    displayRecords(currentPos);
                }
            }
        });

        detailsOfUnusedMaterials_button_nextReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidtionForArrayFields()) {
                    if (currentPos < (totalCount - 1)) {
                        //Save current ac reading
                        saveRecords(currentPos);
                        currentPos = currentPos + 1;
                        //move to Next reading
                        displayRecords(currentPos);

                    } else if (currentPos == (totalCount - 1)) {
                        //Save Final current reading and submit all AC data
                        saveRecords(currentPos);
                        submitDetails();
                        startActivity(new Intent(DetailsOfUnusedMaterials.this, PhotoCaptureActivity.class));
                        finish();
                    }
                }
            }
        });
    }

    private void OnChangeAssetMakeArrayListAssetStatus() {
        SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetStatus))),
                "Asset Status",
                "close", "#000000");
        searchableSpinnerDialog.showSearchableSpinnerDialog();

        searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(ArrayList<String> item, int position) {

                str_assetStatus = item.get(position);
                mDetailsOfUnusedMaterialsTextViewAssetStatusVal.setText(str_assetStatus);
            }
        });
    }

    private void OnChangeTypeOfAssetsetArrayListAssetMake(String typeOfAsset) {
        if (typeOfAsset.equals("AC")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_AC))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } else if (typeOfAsset.equals("AMF Panel")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_AmfPanel))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } else if (typeOfAsset.equals("Battery Bank")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_BatteryBank))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } /*else if (typeOfAsset.equals("Battery Rack")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_BatteryRack))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        }*/ else if (typeOfAsset.equals("DG")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_Dg))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } /*else if (typeOfAsset.equals("DG Battery")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_DgBattery))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        }else if (typeOfAsset.equals("FCU")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_Fcu))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } else if (typeOfAsset.equals("Fire Alarm Panel")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_FireAlarmPanel))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } else if (typeOfAsset.equals("Fire Extinngusher")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_FireExtinngusher))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        }else if (typeOfAsset.equals("Out Door Cabinet")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_OutDoorCabinet))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        }else if (typeOfAsset.equals("PIU")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_Piu))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } */ else if (typeOfAsset.equals("Power Plant")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_PowerPlant))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } else if (typeOfAsset.equals("Rectifier Module")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_RectifierModule))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } else if (typeOfAsset.equals("Shelter")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_Shelter))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        } /*else if (typeOfAsset.equals("Solar Panel")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_SolarPanel))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        }*/ else if (typeOfAsset.equals("Stabilizer")) {
            SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(DetailsOfUnusedMaterials.this,
                    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_detailsOfUnusedMaterials_AssetMake_Stabilizer))),
                    "Asset Make",
                    "close", "#000000");
            searchableSpinnerDialog.showSearchableSpinnerDialog();

            searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                @Override
                public void onClick(ArrayList<String> item, int position) {

                    str_assetMake = item.get(position);
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(str_assetMake);
                }
            });
        }
    }

    private boolean checkValidtionForArrayFields() {
        String typeOfAsset = mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.getText().toString().trim();
        /* String assetMake = mDetailsOfUnusedMaterialsTextViewAssetMakeVal.getText().toString().trim();*/
        String assetStatus = mDetailsOfUnusedMaterialsTextViewAssetStatusVal.getText().toString().trim();
        String assetDescription = mDetailsOfUnusedMaterialsEditTextDescriptionVal.getText().toString().trim();

        /*if (typeOfAsset.isEmpty() || typeOfAsset == null) {
            showToast("Select Type of Asset ");
            return false;
        } else if (assetMake.isEmpty() || assetMake == null) {
            showToast("Select Asset Make ");
            return false;
        } else*/
        if (typeOfAsset.equals("General Item")) {
            return true;
        } else if ((assetStatus.isEmpty() || assetStatus == null) && (!assetDescription.isEmpty() && assetDescription != null)) {
            showToast("Select Asset Status");
            return false;
        } else/* if (assetDescription.isEmpty() || assetDescription == null) {
            showToast("Select Asset Description ");
            return false;
        } else*/
            return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuDone);

        // show the button when some condition is true
        shareItem.setVisible(true);
        if (str_numberofUnusedAssetinSite != null && !str_numberofUnusedAssetinSite.isEmpty()) {
            if (Integer.valueOf(str_numberofUnusedAssetinSite) > 0) {
                shareItem.setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuDone:
                if (checkValidationonSubmit("onSubmit")) {
                    submitDetails();
                    startActivity(new Intent(DetailsOfUnusedMaterials.this, PhotoCaptureActivity.class));
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean checkValidationonSubmit(String methodFlag) {
        String UnusedAssetNo = mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal.getText().toString().trim();
        /*if (!UnusedAssetNo.isEmpty() && UnusedAssetNo != null) {
            return true;
        } else {
            showToast("Enter Number of Unused Asset in Site ");
            return false;
        }*/

        if (UnusedAssetNo.isEmpty() || UnusedAssetNo == null) {
            showToast("Enter Number of Unused Asset in Site ");
            return false;
        } else if (Integer.valueOf(UnusedAssetNo) > 0) {
            if ((detailsOfUnusedMaterialsData.size() != Integer.valueOf(UnusedAssetNo) && methodFlag.equals("onSubmit"))) {
                showToast("Complete the all readings.");
                return false;
            } else return true;
        } else return true;
    }

    private void setInputDetails(int index) {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                detailsOfUnusedMaterialsParentData = hotoTransactionData.getDetailsOfUnusedMaterialsParentData();
                detailsOfUnusedMaterialsData.addAll(detailsOfUnusedMaterialsParentData.getDetailsOfUnusedMaterialsData());

                totalCount = Integer.parseInt(detailsOfUnusedMaterialsParentData.getNumberofUnusedAssetinSite());
                mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal.setText(detailsOfUnusedMaterialsParentData.getNumberofUnusedAssetinSite());

                if (detailsOfUnusedMaterialsData != null && detailsOfUnusedMaterialsData.size() > 0) {

                    linearLayout_container.setVisibility(View.VISIBLE);
                    detailsOfUnusedMaterials_textView_Number.setText("Reading: #1");

                    mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.setText(detailsOfUnusedMaterialsData.get(index).getTypeOfAsset());
                    mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(detailsOfUnusedMaterialsData.get(index).getAssetMake());
                    mDetailsOfUnusedMaterialsTextViewAssetStatusVal.setText(detailsOfUnusedMaterialsData.get(index).getAssetStatus());
                    mDetailsOfUnusedMaterialsEditTextDescriptionVal.setText(detailsOfUnusedMaterialsData.get(index).getAssetDescription());

                    detailsOfUnusedMaterials_button_previousReading.setVisibility(View.GONE);
                    detailsOfUnusedMaterials_button_nextReading.setVisibility(View.VISIBLE);

                    //if (detailsOfUnusedMaterialsData.size() > 1) {
                    if (totalCount > 1) {
                        detailsOfUnusedMaterials_button_nextReading.setText("Next Reading");
                    } else {
                        detailsOfUnusedMaterials_button_nextReading.setText("Finish");
                    }
                }

            } else {
                Toast.makeText(DetailsOfUnusedMaterials.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            // hotoTransactionData.setTicketNo(ticketId);
            String numberofUnusedAssetinSite = mDetailsOfUnusedMaterialsTextViewNumberofUnusedAssetinSiteVal.getText().toString().trim();

            //detailsOfUnusedMaterialsData = new DetailsOfUnusedMaterialsData(numberofUnusedAssetinSite, assetMake, assetStatus,assetDescription);
            //hotoTransactionData.setDetailsOfUnusedMaterialsData(detailsOfUnusedMaterialsData);

            detailsOfUnusedMaterialsParentData = new DetailsOfUnusedMaterialsParentData(numberofUnusedAssetinSite, detailsOfUnusedMaterialsData);
            hotoTransactionData.setDetailsOfUnusedMaterialsParentData(detailsOfUnusedMaterialsParentData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRecords(int pos) {

        String typeOfAsset = mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.getText().toString().trim();
        String assetMake = mDetailsOfUnusedMaterialsTextViewAssetMakeVal.getText().toString().trim();
        String assetStatus = mDetailsOfUnusedMaterialsTextViewAssetStatusVal.getText().toString().trim();
        String assetDescription = mDetailsOfUnusedMaterialsEditTextDescriptionVal.getText().toString().trim();

        DetailsOfUnusedMaterialsData obj_detailsOfUnusedMaterialsData = new DetailsOfUnusedMaterialsData(typeOfAsset, assetMake, assetStatus, assetDescription);
        if (detailsOfUnusedMaterialsData.size() > 0) {
            if (pos == detailsOfUnusedMaterialsData.size()) {
                detailsOfUnusedMaterialsData.add(obj_detailsOfUnusedMaterialsData);
            } else if (pos < detailsOfUnusedMaterialsData.size()) {
                detailsOfUnusedMaterialsData.set(pos, obj_detailsOfUnusedMaterialsData);
            }
        } else {
            detailsOfUnusedMaterialsData.add(obj_detailsOfUnusedMaterialsData);
        }
    }

    private void displayRecords(int pos) {
        if (detailsOfUnusedMaterialsData.size() > 0 && pos < detailsOfUnusedMaterialsData.size()) {

            detailsOfUnusedMaterials_textView_Number.setText("Reading: #" + (pos + 1));

            mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.setText(detailsOfUnusedMaterialsData.get(pos).getTypeOfAsset());
            mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText(detailsOfUnusedMaterialsData.get(pos).getAssetMake());
            mDetailsOfUnusedMaterialsTextViewAssetStatusVal.setText(detailsOfUnusedMaterialsData.get(pos).getAssetStatus());
            mDetailsOfUnusedMaterialsEditTextDescriptionVal.setText(detailsOfUnusedMaterialsData.get(pos).getAssetDescription());

            detailsOfUnusedMaterials_button_previousReading.setVisibility(View.VISIBLE);
            detailsOfUnusedMaterials_button_nextReading.setVisibility(View.VISIBLE);

        } else {
            clearFields(pos);
        }

        if (pos > 0 && pos < (totalCount - 1)) {
            detailsOfUnusedMaterials_button_previousReading.setVisibility(View.VISIBLE);
            detailsOfUnusedMaterials_button_nextReading.setText("Next Reading");
        } else if (pos > 0 && pos == (totalCount - 1)) {
            detailsOfUnusedMaterials_button_previousReading.setVisibility(View.VISIBLE);
            detailsOfUnusedMaterials_button_nextReading.setText("Finish");
        } else if (pos == 0) {
            detailsOfUnusedMaterials_button_previousReading.setVisibility(View.GONE);
            if (pos == (totalCount - 1)) {
                detailsOfUnusedMaterials_button_nextReading.setText("Finish");
            } else {
                detailsOfUnusedMaterials_button_nextReading.setText("Next Reading");
            }
        }
    }

    public void clearFields(int indexPos) {
        detailsOfUnusedMaterials_textView_Number.setText("Reading: #" + (indexPos + 1));

        mDetailsOfUnusedMaterialsTextViewTypeOfAssetVal.setText("");
        mDetailsOfUnusedMaterialsTextViewAssetMakeVal.setText("");
        mDetailsOfUnusedMaterialsTextViewAssetStatusVal.setText("");
        mDetailsOfUnusedMaterialsEditTextDescriptionVal.setText("");

        str_typeOfAsset = "";
        str_assetMake = "";
        str_assetStatus = "";
    }
}
