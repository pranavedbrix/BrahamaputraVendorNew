package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.view.View;

import android.widget.DatePicker;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.commons.EnglishNumberToWords;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.LandDetailsData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Land_Details extends BaseActivity {

    private static final String TAG = Land_Details.class.getSimpleName();

    private TextView mLandDetailsTextViewTypeOfLand;
    private TextView mLandDetailsTextViewTypeOfLandVal;
    private TextView mLandDetailsTextViewAreaOfLand;
    private EditText mLandDetailsEditTextAreaOfLand;
    private TextView mLandDetailsTextViewRentLeaseInNumber;
    private EditText mLandDetailsEditTextRentLeaseInNumber;
    private EditText mLandDetailsEditTextBookValueOfTheLand;
    private TextView mLandDetailsTextViewRentLeaseInWords;
    private TextView mLandDetailsTextViewRentLeaseInWords_val;
    private TextView mLandDetailsTextViewNameOfOwner;
    private EditText mLandDetailsEditTextNameOfOwner;
    private TextView mLandDetailsTextViewMobileNoOfOwner;
    private EditText mLandDetailsEditTextMobileNoOfOwner;
    private TextView mLandDetailsTextViewLayoutOfLand;
    private ImageView mLandDetailsButtonLayoutOfLand;
    private ImageView mLandDetailsButtonLayoutOfLandView;
    private TextView mLandDetailsTextViewCopyAgreementWithOwner;
    private TextView mLandDetailsTextViewCopyAgreementWithOwnerVal;
    private TextView mLandDetailsTextViewValidityOfAgreement;
    private EditText mLandDetailsEditTextDateOfvalidityOfAgreement;
    private OfflineStorageWrapper offlineStorageWrapper;
    private LinearLayout landDetails_LinearLayout_BookValueOfTheLand;

    private LinearLayout mLandDetailsLinearLayoutValidityOfAgreement;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private HotoTransactionData hotoTransactionData;
    private LandDetailsData landDetailsData;
    private String base64StringLayoutOfLand = "";

    final Calendar myCalendar = Calendar.getInstance();

    /////////////////////////
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private AlertDialogManager alertDialogManager;

    String str_landDetailsTypeOfLandVal;
    String str_copyAgreementWithOwnerVal;

    private SessionManager sessionManager;

    private Uri imageFileUri = null;
    private String imageFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);
        this.setTitle("Land Detail");
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(Land_Details.this);
        alertDialogManager = new AlertDialogManager(Land_Details.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Land_Details.this, userId, ticketName);

        hotoTransactionData = new HotoTransactionData();
        setInputDetails();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mLandDetailsEditTextDateOfvalidityOfAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Land_Details.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        mLandDetailsButtonLayoutOfLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Land_Details.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (getFromPref(Land_Details.this, ALLOW_KEY)) {
                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Land_Details.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Land_Details.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Land_Details.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    openCameraIntent();
                }

            }
        });

        mLandDetailsButtonLayoutOfLandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(Land_Details.this, imageFileUri);
                } else {
                    Toast.makeText(Land_Details.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mLandDetailsEditTextRentLeaseInNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLandDetailsTextViewRentLeaseInWords_val.setText("");
                String rentLease = mLandDetailsEditTextRentLeaseInNumber.getText().toString().trim();
                if (rentLease.toString().length() > 0) {
                    String str_rentLease = EnglishNumberToWords.convert(Long.valueOf(rentLease));
                    mLandDetailsTextViewRentLeaseInWords_val.setText(str_rentLease);
                    mLandDetailsTextViewRentLeaseInWords_val.setAllCaps(true);
                }
            }
        });
    }

    private void assignViews() {
        mLandDetailsTextViewTypeOfLand = (TextView) findViewById(R.id.landDetails_textView_typeOfLand);
        mLandDetailsTextViewTypeOfLandVal = (TextView) findViewById(R.id.landDetails_textView_typeOfLand_val);
        mLandDetailsTextViewAreaOfLand = (TextView) findViewById(R.id.landDetails_textView_areaOfLand);
        mLandDetailsEditTextAreaOfLand = (EditText) findViewById(R.id.landDetails_editText_areaOfLand);
        mLandDetailsTextViewRentLeaseInNumber = (TextView) findViewById(R.id.landDetails_textView_rentLeaseInNumber);
        mLandDetailsEditTextRentLeaseInNumber = (EditText) findViewById(R.id.landDetails_editText_rentLeaseInNumber);
        mLandDetailsEditTextBookValueOfTheLand = (EditText) findViewById(R.id.landDetails_editText_BookValueOfTheLand);
        landDetails_LinearLayout_BookValueOfTheLand = (LinearLayout) findViewById(R.id.landDetails_LinearLayout_BookValueOfTheLand);

        mLandDetailsTextViewRentLeaseInWords = (TextView) findViewById(R.id.landDetails_textView_rentLeaseInWords);
        mLandDetailsTextViewRentLeaseInWords_val = (TextView) findViewById(R.id.landDetails_textView_rentLeaseInWords_val);
        mLandDetailsTextViewNameOfOwner = (TextView) findViewById(R.id.landDetails_textView_nameOfOwner);
        mLandDetailsEditTextNameOfOwner = (EditText) findViewById(R.id.landDetails_editText_nameOfOwner);
        mLandDetailsTextViewMobileNoOfOwner = (TextView) findViewById(R.id.landDetails_textView_mobileNoOfOwner);
        mLandDetailsEditTextMobileNoOfOwner = (EditText) findViewById(R.id.landDetails_editText_mobileNoOfOwner);
        mLandDetailsTextViewLayoutOfLand = (TextView) findViewById(R.id.landDetails_textView_layoutOfLand);
        mLandDetailsButtonLayoutOfLand = (ImageView) findViewById(R.id.landDetails_button_layoutOfLand);
        mLandDetailsButtonLayoutOfLandView = (ImageView) findViewById(R.id.landDetails_button_layoutOfLandView);
        mLandDetailsTextViewCopyAgreementWithOwner = (TextView) findViewById(R.id.landDetails_textView_copyAgreementWithOwner);
        mLandDetailsTextViewCopyAgreementWithOwnerVal = (TextView) findViewById(R.id.landDetails_textView_copyAgreementWithOwner_val);
        mLandDetailsTextViewValidityOfAgreement = (TextView) findViewById(R.id.landDetails_textView_validityOfAgreement);
        mLandDetailsEditTextDateOfvalidityOfAgreement = (EditText) findViewById(R.id.landDetails_editText_dateOfvalidityOfAgreement);

        mLandDetailsLinearLayoutValidityOfAgreement = (LinearLayout) findViewById(R.id.landDetails_linearLayout_validityOfAgreement);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initCombo() {
        mLandDetailsTextViewTypeOfLandVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Land_Details.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_landDetails_LandType))),
                        "Type of Land",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_landDetailsTypeOfLandVal = item.get(position);
                        mLandDetailsTextViewTypeOfLandVal.setText(str_landDetailsTypeOfLandVal);
                        if (str_landDetailsTypeOfLandVal.equals("Free Hold")) {
                            landDetails_LinearLayout_BookValueOfTheLand.setVisibility(View.VISIBLE);
                        } else {
                            landDetails_LinearLayout_BookValueOfTheLand.setVisibility(View.GONE);
                        }//009
                    }
                });
            }
        });


        mLandDetailsTextViewCopyAgreementWithOwnerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Land_Details.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_landDetails_copyAgreementWithOwner))),
                        "Copy of the agreement with the landlord/owner",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_copyAgreementWithOwnerVal = item.get(position);
                        mLandDetailsTextViewCopyAgreementWithOwnerVal.setText(str_copyAgreementWithOwnerVal);
                        visibilityOfValidityOfAgreement(str_copyAgreementWithOwnerVal);
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mLandDetailsEditTextDateOfvalidityOfAgreement.setText(sdf.format(myCalendar.getTime()));
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
                submitDetails();
                startActivity(new Intent(this, Tower_Detail.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);

                if (hotoTransactionData != null) {

                    landDetailsData = hotoTransactionData.getLandDetailsData();

                    if (landDetailsData != null) {

                        mLandDetailsTextViewCopyAgreementWithOwnerVal.setText(landDetailsData.getLandAgreementCopy());
                        visibilityOfValidityOfAgreement(landDetailsData.getLandAgreementCopy());
                        mLandDetailsEditTextAreaOfLand.setText(landDetailsData.getLandArea());
                        mLandDetailsEditTextRentLeaseInNumber.setText(landDetailsData.getRentLeaseValue());

                        mLandDetailsEditTextBookValueOfTheLand.setText(landDetailsData.getBaseValueOfLand());

                        mLandDetailsTextViewRentLeaseInWords_val.setText(landDetailsData.getRentLeaseValueInWords().toString().toUpperCase());
                        mLandDetailsEditTextNameOfOwner.setText(landDetailsData.getLandOwnerName());
                        mLandDetailsEditTextMobileNoOfOwner.setText(landDetailsData.getLandOwnerMob());
                        base64StringLayoutOfLand = landDetailsData.getLandLayout();

                        // New added for image #ImageSet
                        imageFileName = landDetailsData.getLandLayoutFileName();
                        mLandDetailsButtonLayoutOfLandView.setVisibility(View.GONE);
                        if (imageFileName != null && imageFileName.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
//                             imageFileUri = Uri.fromFile(file);
                            imageFileUri = FileProvider.getUriForFile(Land_Details.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileUri != null) {
                                mLandDetailsButtonLayoutOfLandView.setVisibility(View.VISIBLE);
                            }
                        }
                        mLandDetailsTextViewTypeOfLandVal.setText(landDetailsData.getLandType());
                        mLandDetailsEditTextDateOfvalidityOfAgreement.setText(landDetailsData.getLandAgreementValidity());
                    }
                }
            } else {
                Toast.makeText(Land_Details.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*008 21112018*/
    private void visibilityOfValidityOfAgreement(String ValidityOfAgreement) {
        if (ValidityOfAgreement.equals("Available")) {
            mLandDetailsLinearLayoutValidityOfAgreement.setVisibility(View.VISIBLE);
        } else {
            mLandDetailsLinearLayoutValidityOfAgreement.setVisibility(View.GONE);
            mLandDetailsEditTextDateOfvalidityOfAgreement.setText("");

        }
    }


    private void submitDetails() {
        try {
            String landType = mLandDetailsTextViewTypeOfLandVal.getText().toString().trim();
            String landArea = mLandDetailsEditTextAreaOfLand.getText().toString().trim();
            String rentLeaseValue = mLandDetailsEditTextRentLeaseInNumber.getText().toString().trim();
            String baseValueOfLand = mLandDetailsEditTextBookValueOfTheLand.getText().toString().trim();

            String rentLeaseValueInWords = mLandDetailsTextViewRentLeaseInWords_val.getText().toString().trim().toUpperCase();
            String landOwnerName = mLandDetailsEditTextNameOfOwner.getText().toString().trim();
            String landOwnerMob = mLandDetailsEditTextMobileNoOfOwner.getText().toString().trim();
            String landLayout = base64StringLayoutOfLand;
            String landAgreementCopy = mLandDetailsTextViewCopyAgreementWithOwnerVal.getText().toString().trim();
            String landAgreementValidity = mLandDetailsEditTextDateOfvalidityOfAgreement.getText().toString();

            landDetailsData = new LandDetailsData(landType, landArea, rentLeaseValue, baseValueOfLand, rentLeaseValueInWords, landOwnerName, landOwnerMob, landLayout, landAgreementCopy, landAgreementValidity, imageFileName);

            hotoTransactionData.setLandDetailsData(landDetailsData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openCameraIntent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
//            imageFileUri = Uri.fromFile(file);

            imageFileUri = FileProvider.getUriForFile(Land_Details.this, BuildConfig.APPLICATION_ID + ".provider", file);

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA && resultCode == RESULT_OK) {
            if (imageFileUri != null) {
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                    byte[] bitmapDataArray = stream.toByteArray();
                    base64StringLayoutOfLand = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                    mLandDetailsButtonLayoutOfLandView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            imageFileName = "";
            imageFileUri = null;
            mLandDetailsButtonLayoutOfLandView.setVisibility(View.GONE);
        }
    }

    //Camera//
    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(Land_Details.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Land_Details.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(Land_Details.this);
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

    private void showAlert() {
        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(Land_Details.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Land_Details.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(Land_Details.this,
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

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(Land_Details.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }
}


