package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.Manifest;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;

import com.brahamaputra.mahindra.brahamaputra.Data.ServoStabilizerData;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ServoStabilizer extends BaseActivity {

    private static final String TAG = ServoStabilizer.class.getSimpleName();
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private ServoStabilizerData servoStabilizerData;
    private String base64StringServoStablizer = "";
    private SessionManager sessionManager;

    private TextView mServoStabilizerTextViewQRCodeScan;
    private ImageView mServoStabilizerbuttonQRCodeScan;
    private ImageView mServoStabilizerbuttonQRCodeScanView;
    private TextView mServoStabilizerTextViewServoStabilizerWorkingStatus;
    private TextView mServoStabilizerTextViewServoStabilizerWorkingStatusVal;
    private TextView mServoStabilizerTextViewMakeofServo;
    private TextView mServoStabilizerTextViewMakeofServoVal;
    private TextView mServoStabilizerTextViewRatingofServo;
    private TextView mServoStabilizerTextViewRatingofServoVal;
    private TextView mServoStabilizerTextViewWorkingCondition;
    private TextView mServoStabilizerTextViewWorkingConditionVal;
    private TextView mServoStabilizerTextViewNatureofProblem;
    private EditText mServoStabilizerEditTextNatureofProblem;
    private ImageView button_ClearQRCodeScanView;
    private LinearLayout linearLayoutServoDetails;



    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private Uri imageFileUri = null;
    private String imageFileName = "";

    private AlertDialogManager alertDialogManager;

    String str_ServoStabilizerWorkingStatus;
    String str_makeofServo;
    String str_ratingofServo;
    String str_workingCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servo_stabilizer);
        this.setTitle("SERVO STABILIZER");
        sessionManager = new SessionManager(ServoStabilizer.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(ServoStabilizer.this, userId, ticketName);
        assignViews();
        initCombo();
        alertDialogManager = new AlertDialogManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
        setInputDetails();


        mServoStabilizerbuttonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ServoStabilizer.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(ServoStabilizer.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(ServoStabilizer.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(ServoStabilizer.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(ServoStabilizer.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    //openCameraIntent();This Commented By 0008 on 15-11-2018 For QR Code Purpose
                    onClicked(v);
                }

            }
        });


    }

    private void assignViews() {
        linearLayoutServoDetails = (LinearLayout) findViewById(R.id.linearLayout_servoDetails);
        mServoStabilizerTextViewQRCodeScan = (TextView) findViewById(R.id.servoStabilizer_textView_QRCodeScan);
        mServoStabilizerbuttonQRCodeScan = (ImageView) findViewById(R.id.servoStabilizer_button_QRCodeScan);
        mServoStabilizerbuttonQRCodeScanView = (ImageView) findViewById(R.id.servoStabilizer_button_QRCodeScanView);
        mServoStabilizerTextViewServoStabilizerWorkingStatus = (TextView) findViewById(R.id.servoStabilizer_textView_ServoStabilizerWorkingStatus);
        mServoStabilizerTextViewServoStabilizerWorkingStatusVal = (TextView) findViewById(R.id.servoStabilizer_textView_ServoStabilizerWorkingStatus_val);
        mServoStabilizerTextViewMakeofServo = (TextView) findViewById(R.id.servoStabilizer_textView_MakeofServo);
        mServoStabilizerTextViewMakeofServoVal = (TextView) findViewById(R.id.servoStabilizer_textView_MakeofServo_val);
        mServoStabilizerTextViewRatingofServo = (TextView) findViewById(R.id.servoStabilizer_textView_RatingofServo);
        mServoStabilizerTextViewRatingofServoVal = (TextView) findViewById(R.id.servoStabilizer_textView_RatingofServo_val);
        mServoStabilizerTextViewWorkingCondition = (TextView) findViewById(R.id.servoStabilizer_textView_WorkingCondition);
        mServoStabilizerTextViewWorkingConditionVal = (TextView) findViewById(R.id.servoStabilizer_textView_WorkingCondition_val);
        mServoStabilizerTextViewNatureofProblem = (TextView) findViewById(R.id.servoStabilizer_textView_NatureofProblem);
        mServoStabilizerEditTextNatureofProblem = (EditText) findViewById(R.id.servoStabilizer_editText_NatureofProblem);
        button_ClearQRCodeScanView = (ImageView) findViewById(R.id.button_ClearQRCodeScanView);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {

        mServoStabilizerTextViewServoStabilizerWorkingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ServoStabilizer.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_ServoStabilizer_ServoStabilizerWorkingStatus))),
                        "Servo Stabilizer Working Status",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ServoStabilizerWorkingStatus = item.get(position);
                        mServoStabilizerTextViewServoStabilizerWorkingStatusVal.setText(str_ServoStabilizerWorkingStatus);

                        // Added Code By Pranav For Handling Field Visibility In Servo Stabilizer
                        if (str_ServoStabilizerWorkingStatus.equals("Not Available")) {
                            base64StringServoStablizer = "";
                            button_ClearQRCodeScanView.setVisibility(View.GONE);
                            mServoStabilizerbuttonQRCodeScanView.setVisibility(View.GONE);
                            mServoStabilizerTextViewMakeofServoVal.setText("");
                            mServoStabilizerTextViewRatingofServoVal.setText("");
                            mServoStabilizerTextViewWorkingConditionVal.setText("");
                            mServoStabilizerEditTextNatureofProblem.setText("");
                            linearLayoutServoDetails.setVisibility(View.GONE);
                        } else {
                            linearLayoutServoDetails.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
        mServoStabilizerTextViewMakeofServoVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ServoStabilizer.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_ServoStabilizer_MakeofServo))),
                        "Make of Servo",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_makeofServo = item.get(position);
                        mServoStabilizerTextViewMakeofServoVal.setText(str_makeofServo);
                    }
                });

            }
        });
        mServoStabilizerTextViewRatingofServoVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ServoStabilizer.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_ServoStabilizer_RatingofServo))),
                        "Rating of Servo",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_ratingofServo = item.get(position);
                        mServoStabilizerTextViewRatingofServoVal.setText(str_ratingofServo);
                    }
                });

            }
        });
        mServoStabilizerTextViewWorkingConditionVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ServoStabilizer.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_ServoStabilizer_WorkingCondition))),
                        "Working Condition",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_workingCondition = item.get(position);
                        mServoStabilizerTextViewWorkingConditionVal.setText(str_workingCondition);
                    }
                });

            }
        });

        /*This Commented By 0008 on 15-11-2018 For QR Code Purpose
        mServoStabilizerbuttonQRCodeScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileUri != null) {
                    GlobalMethods.showImageDialog(ServoStabilizer.this, imageFileUri);
                } else {
                    Toast.makeText(ServoStabilizer.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    public void onClicked(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QRcode");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

//        Use this for more customization
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("Scan a barcode");
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setBeepEnabled(false);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();

    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                servoStabilizerData = hotoTransactionData.getServoStabilizerData();

                if (servoStabilizerData.getServoStabilizerWorkingStatus().equals("Not Available")) {
                    mServoStabilizerTextViewServoStabilizerWorkingStatusVal.setText(servoStabilizerData.getServoStabilizerWorkingStatus());
                    linearLayoutServoDetails.setVisibility(View.GONE);
                } else {
                    base64StringServoStablizer = servoStabilizerData.getServoStabilizer_Qr();
                    mServoStabilizerTextViewServoStabilizerWorkingStatusVal.setText(servoStabilizerData.getServoStabilizerWorkingStatus());
                    mServoStabilizerTextViewMakeofServoVal.setText(servoStabilizerData.getMakeofServo());
                    mServoStabilizerTextViewRatingofServoVal.setText(servoStabilizerData.getRatingofServo());
                    mServoStabilizerTextViewWorkingConditionVal.setText(servoStabilizerData.getWorkingCondition());
                    mServoStabilizerEditTextNatureofProblem.setText(servoStabilizerData.getNatureofProblem());

                    mServoStabilizerbuttonQRCodeScanView.setVisibility(View.GONE);
                    button_ClearQRCodeScanView.setVisibility(View.GONE);
                    if (!base64StringServoStablizer.isEmpty() && base64StringServoStablizer != null) {
                        mServoStabilizerbuttonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                }


                // New added for image #ImageSet
                /*This Commented By 0008 on 15-11-2018 For QR Code Purpose
                imageFileName = servoStabilizerData.getQrCodeImageFileName();
                mServoStabilizerbuttonQRCodeScanView.setVisibility(View.GONE);
                if (imageFileName != null && imageFileName.length() > 0) {
                    File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
//                             imageFileUri = Uri.fromFile(file);
                    imageFileUri = FileProvider.getUriForFile(ServoStabilizer.this, BuildConfig.APPLICATION_ID + ".provider", file);
                    if (imageFileUri != null) {
                        mServoStabilizerbuttonQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                }*/

            } else {
                Toast.makeText(ServoStabilizer.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            // hotoTransactionData.setTicketNo(ticketId);

            String servoStabilizer_Qr = base64StringServoStablizer;
            String servoStabilizerWorkingStatus = mServoStabilizerTextViewServoStabilizerWorkingStatusVal.getText().toString().trim();
            String makeofServo = mServoStabilizerTextViewMakeofServoVal.getText().toString().trim();
            String ratingofServo = mServoStabilizerTextViewRatingofServoVal.getText().toString().trim();
            String workingCondition = mServoStabilizerTextViewWorkingConditionVal.getText().toString().trim();
            String natureofProblem = mServoStabilizerEditTextNatureofProblem.getText().toString().trim();


            servoStabilizerData = new ServoStabilizerData(servoStabilizer_Qr, servoStabilizerWorkingStatus, makeofServo, ratingofServo, workingCondition, natureofProblem, imageFileName);
            hotoTransactionData.setServoStabilizerData(servoStabilizerData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*0008 29112018*/
    public boolean checkValidation() {
        String servoStabilizerWorkingStatus = mServoStabilizerTextViewServoStabilizerWorkingStatusVal.getText().toString().trim();
        String qRCodeScan = base64StringServoStablizer;

        if (servoStabilizerWorkingStatus.isEmpty() || servoStabilizerWorkingStatus == null) {
            showToast("Select Servo Stabilizer Working Status");
            return false;
        } else if ((qRCodeScan.isEmpty() || qRCodeScan == null) && !servoStabilizerWorkingStatus.equals("Not Available")) {
            showToast("Please Scan QR Code");
            return false;
        } else return true;
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(ServoStabilizer.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(ServoStabilizer.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(ServoStabilizer.this);
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

                final EditText taskEditText = new EditText(ServoStabilizer.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(ServoStabilizer.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(ServoStabilizer.this,
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

    public void openCameraIntent() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + ".jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            //imageFileUri = Uri.fromFile(file);
            imageFileUri = FileProvider.getUriForFile(ServoStabilizer.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuDone:
                // Added Code By Pranav For Handling Field Visibility In Servo Stabilizer
                if (mServoStabilizerTextViewServoStabilizerWorkingStatusVal.getText().toString().equals("Not Available")) {
                    submitDetails();
                    finish();
                    startActivity(new Intent(this, DetailsOfUnusedMaterials.class));
                    return true;
                } else {
                    if (checkValidation() == true) {
                        submitDetails();
                        finish();
                        startActivity(new Intent(this, DetailsOfUnusedMaterials.class));
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            mServoStabilizerbuttonQRCodeScanView.setVisibility(View.GONE);
            button_ClearQRCodeScanView.setVisibility(View.GONE);
            if (result.getContents() == null) {
                base64StringServoStablizer = "";
                showToast("Cancelled");
            } else {
                Object[] isDuplicateQRcode = isDuplicateQRcode(result.getContents());
                boolean flagIsDuplicateQRcode = (boolean) isDuplicateQRcode[1];
                if (!flagIsDuplicateQRcode) {
                    base64StringServoStablizer = result.getContents();
                    if (!base64StringServoStablizer.isEmpty() && base64StringServoStablizer != null) {
                        mServoStabilizerbuttonQRCodeScanView.setVisibility(View.VISIBLE);
                        button_ClearQRCodeScanView.setVisibility(View.VISIBLE);
                    }
                } else {
                    base64StringServoStablizer = "";
                    showToast("This QR Code Already Used in " + isDuplicateQRcode[0] + " Section");
                }


            }
        }

        /*This Commented By 0008 on 15-11-2018 For QR Code Purpose
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA &&
                resultCode == RESULT_OK) {
            if (imageFileUri != null) {
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileUri);
//                            (Bitmap) data.getExtras().get("data");
//                mImageView.setImageBitmap(imageBitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] bitmapDataArray = stream.toByteArray();
                    base64StringServoStablizer = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                    mServoStabilizerbuttonQRCodeScanView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
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
                            saveToPreferences(ServoStabilizer.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }
}
