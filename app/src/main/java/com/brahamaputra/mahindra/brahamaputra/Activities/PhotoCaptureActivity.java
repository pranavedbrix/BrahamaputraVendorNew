package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Base64;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.SitePhotoCaptureData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoCaptureActivity extends BaseActivity {

    private static final String TAG = PhotoCaptureActivity.class.getSimpleName();

    private ImageView mPhotoCaptureButtonSite;
    private ImageView mPhotoCaptureButtonShelter;
    private ImageView mPhotoCaptureButtonEbMeterBox;
    private ImageView mPhotoCaptureButtonSmps;
    private ImageView mPhotoCaptureButtonEbMeter;
    private ImageView mPhotoCaptureButtonDgHmr;
    private ImageView mPhotoCaptureButtonDgOverview;
    private ImageView mPhotoCaptureButtonSiteView;
    private ImageView mPhotoCaptureButtonShelterView;
    private ImageView mPhotoCaptureButtonEbMeterBoxView;
    private ImageView mPhotoCaptureButtonSmpsView;
    private ImageView mPhotoCaptureButtonEbMeterView;
    private ImageView mPhotoCaptureButtonDgHmrView;
    private ImageView mPhotoCaptureButtonDgOverviewView;
    private Switch mPhotoCaptureSwitchCaptureMode;
    private OfflineStorageWrapper offlineStorageWrapper;

    private SessionManager sessionManager;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private HotoTransactionData hotoTransactionData;
    private SitePhotoCaptureData sitePhotoCaptureData;




    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_SITE = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_SHELTER = 102;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_EB_METER_BOX = 103;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_SMPS = 104;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_EB_METER = 105;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_DG_HMR = 106;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_DG_OVERVIEW = 107;

    private String base64StringSite = "";
    private String base64StringShelter = "";
    private String base64StringEbMeterBox = "";
    private String base64StringSmps = "";
    private String base64StringEbMeter = "";
    private String base64StringDgHmr = "";
    private String base64StringDgOverview = "";

    private String imageFileNameOfSite;
    private String imageFileNameOfShelter;
    private String imageFileNameOfEbMeterBox;
    private String imageFileNameOfSmps;
    private String imageFileNameOfEbMeter;
    private String imageFileNameOfDgHmr;
    private String imageFileNameOfDgOverview;

    private Uri imageFileNameOfSiteUri = null;
    private Uri imageFileNameOfShelterUri = null;
    private Uri imageFileNameOfEbMeterBoxUri = null;
    private Uri imageFileNameOfSmpsUri = null;
    private Uri imageFileNameOfEbMeterUri = null;
    private Uri imageFileNameOfDgHmrUri = null;
    private Uri imageFileNameOfDgOverviewUri = null;

    private int ORIENTATION_ROTATE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);

        this.setTitle("Photos To Be Capture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(PhotoCaptureActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PhotoCaptureActivity.this, userId, ticketName);

        hotoTransactionData = new HotoTransactionData();
        assignViews();
        checkCameraPermission();
        setListner();
        setInputDetails();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void assignViews() {
        mPhotoCaptureButtonSite = (ImageView) findViewById(R.id.photoCapture_button_site);
        mPhotoCaptureButtonShelter = (ImageView) findViewById(R.id.photoCapture_button_shelter);
        mPhotoCaptureButtonEbMeterBox = (ImageView) findViewById(R.id.photoCapture_button_ebMeterBox);
        mPhotoCaptureButtonSmps = (ImageView) findViewById(R.id.photoCapture_button_smps);
        mPhotoCaptureButtonEbMeter = (ImageView) findViewById(R.id.photoCapture_button_ebMeter);
        mPhotoCaptureButtonDgHmr = (ImageView) findViewById(R.id.photoCapture_button_dgHmr);
        mPhotoCaptureButtonDgOverview = (ImageView) findViewById(R.id.photoCapture_button_dgOerview);

        mPhotoCaptureButtonSiteView = (ImageView) findViewById(R.id.photoCapture_button_siteView);
        mPhotoCaptureButtonShelterView = (ImageView) findViewById(R.id.photoCapture_button_shelterView);
        mPhotoCaptureButtonEbMeterBoxView = (ImageView) findViewById(R.id.photoCapture_button_ebMeterBoxView);
        mPhotoCaptureButtonSmpsView = (ImageView) findViewById(R.id.photoCapture_button_smpsView);
        mPhotoCaptureButtonEbMeterView = (ImageView) findViewById(R.id.photoCapture_button_ebMeterView);
        mPhotoCaptureButtonDgHmrView = (ImageView) findViewById(R.id.photoCapture_button_dgHmrView);
        mPhotoCaptureButtonDgOverviewView = (ImageView) findViewById(R.id.photoCapture_button_dgOerviewView);

        mPhotoCaptureSwitchCaptureMode = (Switch)findViewById(R.id.photoCapture_switch_captureMode);

    }

    private void setListner() {
        mPhotoCaptureButtonSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoOfSite();
                }
            }
        });

        mPhotoCaptureButtonShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoOfShelter();
                }
            }
        });

        mPhotoCaptureButtonEbMeterBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoEbMeterBox();
                }
            }
        });
        mPhotoCaptureButtonSmps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoSmps();
                }
            }
        });
        mPhotoCaptureButtonEbMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoEbMeter();
                }
            }
        });
        mPhotoCaptureButtonDgHmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoDgHmr();
                }
            }
        });
        mPhotoCaptureButtonDgOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    takePhotoDgOverview();
                }
            }
        });


        //image preview code
        mPhotoCaptureButtonSiteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //009
                if (imageFileNameOfSiteUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfSiteUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPhotoCaptureButtonShelterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameOfShelterUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfShelterUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPhotoCaptureButtonEbMeterBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameOfEbMeterBoxUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfEbMeterBoxUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPhotoCaptureButtonSmpsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameOfSmpsUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfSmpsUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPhotoCaptureButtonEbMeterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameOfEbMeterUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfEbMeterUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPhotoCaptureButtonDgHmrView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameOfDgHmrUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfDgHmrUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mPhotoCaptureButtonDgOverviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameOfDgOverviewUri != null) {
                    GlobalMethods.showImageDialog(PhotoCaptureActivity.this, imageFileNameOfDgOverviewUri);
                } else {
                    Toast.makeText(PhotoCaptureActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });


        mPhotoCaptureSwitchCaptureMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(!isChecked){
                   ORIENTATION_ROTATE = 90;///Portraite
                   //showToast(""+ORIENTATION_ROTATE);
               }else {
                   ORIENTATION_ROTATE = 180;///Landscape
                   //showToast(""+ORIENTATION_ROTATE);
               }
            }
        });
    }

    private void takePhotoDgOverview() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfDgOverview = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dgOverview.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfDgOverview);
            imageFileNameOfDgOverviewUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfDgOverviewUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_DG_OVERVIEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePhotoDgHmr() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfDgHmr = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_dgHmr.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfDgHmr);
            imageFileNameOfDgHmrUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfDgHmrUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_DG_HMR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePhotoEbMeter() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfEbMeter = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_ebMeter.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfEbMeter);
            imageFileNameOfEbMeterUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfEbMeterUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_EB_METER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePhotoSmps() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfSmps = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_smps.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfSmps);
            imageFileNameOfSmpsUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfSmpsUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_SMPS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void takePhotoEbMeterBox() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfEbMeterBox = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_ebMeterBox.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfEbMeterBox);
            imageFileNameOfEbMeterBoxUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfEbMeterBoxUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_EB_METER_BOX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePhotoOfShelter() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfShelter = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_shelter.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfShelter);
            imageFileNameOfShelterUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfShelterUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_SHELTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void takePhotoOfSite() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileNameOfSite = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_site.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfSite);
            imageFileNameOfSiteUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameOfSiteUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA_SITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, Context context, Uri selectedImage) throws IOException {

        if (selectedImage.getScheme().equals("content")) {
            String[] projection = { MediaStore.Images.ImageColumns.ORIENTATION };
            Cursor c = context.getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            //Timber.d("orientation: %s", orientation);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 90);
                default:
                    return img;
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_SITE:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfSiteUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfSiteUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfSiteUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfSiteUri), 180);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfSiteUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringSite = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonSiteView.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfSite = "";
                    imageFileNameOfSiteUri = null;
                    mPhotoCaptureButtonSiteView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_SHELTER:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfShelterUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfShelterUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfShelterUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfShelterUri), 90);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfShelterUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringShelter = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonShelterView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfShelter = "";
                    imageFileNameOfShelterUri = null;
                    mPhotoCaptureButtonShelterView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_EB_METER_BOX:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfEbMeterBoxUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfEbMeterBoxUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfEbMeterBoxUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfEbMeterBoxUri), 90);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfEbMeterBoxUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringEbMeterBox = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonEbMeterBoxView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfEbMeterBox = "";
                    imageFileNameOfEbMeterBoxUri = null;
                    mPhotoCaptureButtonEbMeterBoxView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_SMPS:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfSmpsUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfSmpsUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfSmpsUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfSmpsUri), 90);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfSmpsUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringSmps = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonSmpsView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfSmps = "";
                    imageFileNameOfSmpsUri = null;
                    mPhotoCaptureButtonSmpsView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_EB_METER:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfEbMeterUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfEbMeterUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfEbMeterUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfEbMeterUri), 90);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfEbMeterUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringEbMeter = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonEbMeterView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfEbMeter = "";
                    imageFileNameOfEbMeterUri = null;
                    mPhotoCaptureButtonEbMeterView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_DG_HMR:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfDgHmrUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfDgHmrUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfDgHmrUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfDgHmrUri), 90);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfDgHmrUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringDgHmr = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonDgHmrView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfDgHmr = "";
                    imageFileNameOfDgHmrUri = null;
                    mPhotoCaptureButtonDgHmrView.setVisibility(View.GONE);
                }
                break;

            case MY_PERMISSIONS_REQUEST_CAMERA_DG_OVERVIEW:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameOfDgOverviewUri != null) {
                        try {
                            //Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfDgOverviewUri);
                            Bitmap rotateImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfDgOverviewUri);
                            Bitmap imageBitmap = rotateImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameOfDgOverviewUri), 180);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);

                            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                            imageFileNameOfDgOverviewUri = Uri.parse(path);

                            byte[] bitmapDataArray = stream.toByteArray();
                            base64StringDgOverview = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mPhotoCaptureButtonDgOverviewView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileNameOfDgOverview = "";
                    imageFileNameOfDgOverviewUri = null;
                    mPhotoCaptureButtonDgOverviewView.setVisibility(View.GONE);
                }
                break;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean checkCameraPermission() {

        /*if (ContextCompat.checkSelfPermission(PhotoCaptureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {

            if (getFromPref(PhotoCaptureActivity.this, ALLOW_KEY)) {

                final Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + PhotoCaptureActivity.this.getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                PhotoCaptureActivity.this.startActivity(i);

            } else if (ContextCompat.checkSelfPermission(PhotoCaptureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoCaptureActivity.this, Manifest.permission.CAMERA))
                {
                    ActivityCompat.requestPermissions(PhotoCaptureActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(PhotoCaptureActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }*/

        if (ContextCompat.checkSelfPermission(PhotoCaptureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PhotoCaptureActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }


        return false;
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
                submitDetails();
                setResult(RESULT_OK);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);

                if (hotoTransactionData != null) {

                    sitePhotoCaptureData = hotoTransactionData.getSitePhotoCaptureData();

                    if (sitePhotoCaptureData != null) {

                        // set image preview if exist
                        imageFileNameOfSite = sitePhotoCaptureData.getImageFileNameOfSite();
                        mPhotoCaptureButtonSiteView.setVisibility(View.GONE);
                        if (imageFileNameOfSite != null && imageFileNameOfSite.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfSite);
                            imageFileNameOfSiteUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfSiteUri != null) {
                                mPhotoCaptureButtonSiteView.setVisibility(View.VISIBLE);
                            }
                        }


                        imageFileNameOfShelter = sitePhotoCaptureData.getImageFileNameOfShelter();
                        mPhotoCaptureButtonShelterView.setVisibility(View.GONE);
                        if (imageFileNameOfShelter != null && imageFileNameOfShelter.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfShelter);
                            imageFileNameOfShelterUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfShelterUri != null) {
                                mPhotoCaptureButtonShelterView.setVisibility(View.VISIBLE);
                            }
                        }


                        imageFileNameOfEbMeterBox = sitePhotoCaptureData.getImageFileNameOfEbMeterBox();
                        mPhotoCaptureButtonEbMeterBoxView.setVisibility(View.GONE);
                        if (imageFileNameOfEbMeterBox != null && imageFileNameOfEbMeterBox.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfEbMeterBox);
                            imageFileNameOfEbMeterBoxUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfEbMeterBoxUri != null) {
                                mPhotoCaptureButtonEbMeterBoxView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileNameOfSmps = sitePhotoCaptureData.getImageFileNameOfSmps();
                        mPhotoCaptureButtonSmpsView.setVisibility(View.GONE);
                        if (imageFileNameOfSmps != null && imageFileNameOfSmps.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfSmps);
                            imageFileNameOfSmpsUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfSmpsUri != null) {
                                mPhotoCaptureButtonSmpsView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileNameOfEbMeter = sitePhotoCaptureData.getImageFileNameOfEbMeter();
                        mPhotoCaptureButtonEbMeterView.setVisibility(View.GONE);
                        if (imageFileNameOfEbMeter != null && imageFileNameOfEbMeter.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfEbMeter);
                            imageFileNameOfEbMeterUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfEbMeterUri != null) {
                                mPhotoCaptureButtonEbMeterView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileNameOfDgHmr = sitePhotoCaptureData.getImageFileNameOfDgHmr();
                        mPhotoCaptureButtonDgHmrView.setVisibility(View.GONE);
                        if (imageFileNameOfDgHmr != null && imageFileNameOfDgHmr.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfDgHmr);
                            imageFileNameOfDgHmrUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfDgHmrUri != null) {
                                mPhotoCaptureButtonDgHmrView.setVisibility(View.VISIBLE);
                            }
                        }

                        imageFileNameOfDgOverview = sitePhotoCaptureData.getImageFileNameOfDgOverview();
                        mPhotoCaptureButtonDgOverviewView.setVisibility(View.GONE);
                        if (imageFileNameOfDgOverview != null && imageFileNameOfDgOverview.length() > 0) {
                            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileNameOfDgOverview);
                            imageFileNameOfDgOverviewUri = FileProvider.getUriForFile(PhotoCaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                            if (imageFileNameOfDgOverviewUri != null) {
                                mPhotoCaptureButtonDgOverviewView.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            } else {
                Toast.makeText(PhotoCaptureActivity.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
//            hotoTransactionData.setTicketNo(ticketName);

            sitePhotoCaptureData = new SitePhotoCaptureData(imageFileNameOfSite, base64StringSite,
                    imageFileNameOfShelter, base64StringShelter,
                    imageFileNameOfEbMeterBox, base64StringEbMeterBox,
                    imageFileNameOfSmps, base64StringSmps,
                    imageFileNameOfEbMeter, base64StringEbMeter,
                    imageFileNameOfDgHmr, base64StringDgHmr,
                    imageFileNameOfDgOverview, base64StringDgOverview);

            hotoTransactionData.setSitePhotoCaptureData(sitePhotoCaptureData);
            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();
            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
