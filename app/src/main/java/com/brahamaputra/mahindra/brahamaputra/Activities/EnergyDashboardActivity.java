package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EnergyDashboardActivity extends AppCompatActivity {


    private RelativeLayout mMyEnegyListRelativeLayoutDashboardEBPayment;
    private ImageView mImgEbPayment;
    private RelativeLayout mMyEnegyListRelativeLayoutDashboardDieselFilling;
    private ImageView mImgDieselFilling;

    public GPSTracker gpsTracker;
    private AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_dashboard);
        this.setTitle("Energy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assignViews();
        mMyEnegyListRelativeLayoutDashboardEBPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Conditions.isNetworkConnected(EnergyDashboardActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(EnergyDashboardActivity.this, ElectricBillPaymentActivity.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(EnergyDashboardActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
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

        mMyEnegyListRelativeLayoutDashboardDieselFilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Conditions.isNetworkConnected(EnergyDashboardActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(EnergyDashboardActivity.this, MyEnergyListActivity.class));
                    } else {
                        //showToast("Could not detecting location. Please try again later.");
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By Arjun on 10-11-2018
                                    Log.e(EnergyDashboardActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
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

    }

    private void assignViews() {
        mMyEnegyListRelativeLayoutDashboardEBPayment = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_dashboardEBPayment);
        mImgEbPayment = (ImageView) findViewById(R.id.img_ebPayment);
        mMyEnegyListRelativeLayoutDashboardDieselFilling = (RelativeLayout) findViewById(R.id.myEnegyList_relativeLayout_dashboardDieselFilling);
        mImgDieselFilling = (ImageView) findViewById(R.id.img_dieselFilling);
        gpsTracker = new GPSTracker(EnergyDashboardActivity.this);
        alertDialogManager = new AlertDialogManager(EnergyDashboardActivity.this);
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
