package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.eb_billing_process_flag;

public class ElectricBillMenuActivity extends AppCompatActivity {
    private RelativeLayout mMyEnegyListRelativeLayoutEbProcess;
    private RelativeLayout mMyEnegyListRelativeLayoutEbSiteElectrification;
    private ImageView mImgEbProcess;
    private ImageView mImgEbSiteElectrification;


    private RelativeLayout electricBillMenuRelativeLayoutEbBillRequest;
    private ImageView imgEbBillRequest;
    private RelativeLayout electricBillMenuRelativeLayoutEbUploadDdCheque;
    private ImageView imgEbUploadDdCheque;
    private RelativeLayout electricBillMenuRelativeLayoutEbUploadReceipt;
    private ImageView imgEbUploadReceipt;

    private void assignViews() {
        electricBillMenuRelativeLayoutEbBillRequest = (RelativeLayout) findViewById(R.id.electricBillMenu_relativeLayout_ebBillRequest);
        imgEbBillRequest = (ImageView) findViewById(R.id.img_ebBillRequest);
        electricBillMenuRelativeLayoutEbUploadDdCheque = (RelativeLayout) findViewById(R.id.electricBillMenu_relativeLayout_ebUploadDdCheque);
        imgEbUploadDdCheque = (ImageView) findViewById(R.id.img_ebUploadDdCheque);
        electricBillMenuRelativeLayoutEbUploadReceipt = (RelativeLayout) findViewById(R.id.electricBillMenu_relativeLayout_ebUploadReceipt);
        imgEbUploadReceipt = (ImageView) findViewById(R.id.img_ebUploadReceipt);

        gpsTracker = new GPSTracker(ElectricBillMenuActivity.this);
        alertDialogManager = new AlertDialogManager(ElectricBillMenuActivity.this);
    }


    public GPSTracker gpsTracker;
    private AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_bill_menu);
        this.setTitle("Electricity Billing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assignViews();

        electricBillMenuRelativeLayoutEbBillRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conditions.isNetworkConnected(ElectricBillMenuActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        eb_billing_process_flag = "0";
                        startActivity(new Intent(ElectricBillMenuActivity.this, ElectricBillProcessList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(ElectricBillMenuActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                } else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }
            }
        });

        electricBillMenuRelativeLayoutEbUploadDdCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conditions.isNetworkConnected(ElectricBillMenuActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        eb_billing_process_flag = "1";
                        startActivity(new Intent(ElectricBillMenuActivity.this, ElectricBillProcessList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(ElectricBillMenuActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                } else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }
            }
        });

        electricBillMenuRelativeLayoutEbUploadReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conditions.isNetworkConnected(ElectricBillMenuActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        eb_billing_process_flag = "2";
                        startActivity(new Intent(ElectricBillMenuActivity.this, ElectricBillProcessList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(ElectricBillMenuActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                } else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }
            }
        });

        /*mMyEnegyListRelativeLayoutEbProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conditions.isNetworkConnected(ElectricBillMenuActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(ElectricBillMenuActivity.this, ElectricBillProcessList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(ElectricBillMenuActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                } else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }
            }
        });

        mMyEnegyListRelativeLayoutEbSiteElectrification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conditions.isNetworkConnected(ElectricBillMenuActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(ElectricBillMenuActivity.this, EbSiteElectrificationList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(ElectricBillMenuActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                } else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }
            }
        });*/
    }

    private void assignViews1() {
        mMyEnegyListRelativeLayoutEbProcess = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_ebProcess);
        mImgEbProcess = (ImageView) findViewById(R.id.img_ebProcess);
        mMyEnegyListRelativeLayoutEbSiteElectrification = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_ebSiteElectrification);
        mImgEbSiteElectrification = (ImageView) findViewById(R.id.img_ebSiteElectrification);

        gpsTracker = new GPSTracker(ElectricBillMenuActivity.this);
        alertDialogManager = new AlertDialogManager(ElectricBillMenuActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_no_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuSubmit:
                onBackPressed();
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
