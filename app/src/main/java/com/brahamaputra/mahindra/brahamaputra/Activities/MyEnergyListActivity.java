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

public class MyEnergyListActivity extends AppCompatActivity {

    private RelativeLayout mMyEnegyListRelativeLayoutDieselFillingReq;
    private ImageView mImgDieselFillingReq;
    private RelativeLayout mMyEnegyListRelativeLayoutDieselFilling;
    private ImageView mImgDieselFilling;
    private RelativeLayout mMyEnegyListRelativeLayoutEbProcess;
    private ImageView mImgEbProcess;

    public GPSTracker gpsTracker;
    private AlertDialogManager alertDialogManager;

    private void assignViews() {
        mMyEnegyListRelativeLayoutDieselFillingReq = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_dieselFillingReq);
        mImgDieselFillingReq = (ImageView) findViewById(R.id.img_dieselFillingReq);

        mMyEnegyListRelativeLayoutDieselFilling = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_dieselFilling);
        mImgDieselFilling = (ImageView) findViewById(R.id.img_dieselFilling);
        mMyEnegyListRelativeLayoutEbProcess = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_ebProcess);
        mImgEbProcess = (ImageView) findViewById(R.id.img_ebProcess);

        gpsTracker = new GPSTracker(MyEnergyListActivity.this);
        alertDialogManager = new AlertDialogManager(MyEnergyListActivity.this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_energy_list);
        this.setTitle("Diesel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();

        mMyEnegyListRelativeLayoutDieselFillingReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Conditions.isNetworkConnected(MyEnergyListActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(MyEnergyListActivity.this, DieselFillingFundReqestList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(MyEnergyListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                }else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }

            }
        });

        mMyEnegyListRelativeLayoutDieselFilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Conditions.isNetworkConnected(MyEnergyListActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(MyEnergyListActivity.this, DieselFillingList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(MyEnergyListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                }else {
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
              //startActivity(new Intent(MyEnergyListActivity.this, ElectricBillProcessList.class));

               *//* if (Conditions.isNetworkConnected(MyEnergyListActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(MyEnergyListActivity.this, ElectricBillProcessList.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(MyEnergyListActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                }else {
                    alertDialogManager.Dialog("Information", "Device has no internet connection. Turn on internet", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }
                    }).show();
                }*//*
            }
        });*/
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