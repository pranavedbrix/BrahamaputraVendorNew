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

public class MaintenanceDashboardActivity extends AppCompatActivity {
    private RelativeLayout mMyPreventiveListRelativeLayoutAcPreventiveMaintenance;
    private ImageView mImgAcPreventiveMaintenance;
    private RelativeLayout mMyPreventiveListRelativeLayoutSitePreventiveMaintenance;
    private ImageView mImgSitePreventiveMaintenance;
    public GPSTracker gpsTracker;
    private AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_dashboard);
        this.setTitle("Maintenance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assignViews();

        mMyPreventiveListRelativeLayoutAcPreventiveMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conditions.isNetworkConnected(MaintenanceDashboardActivity.this)) {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        startActivity(new Intent(MaintenanceDashboardActivity.this, MyPreventiveListActivity.class));
                    } else {
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    Log.e(MaintenanceDashboardActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
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

        mMyPreventiveListRelativeLayoutSitePreventiveMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void assignViews() {
        mMyPreventiveListRelativeLayoutAcPreventiveMaintenance = (RelativeLayout) findViewById(R.id.MyPreventiveList_relativeLayout_acPreventiveMaintenance);
        mImgAcPreventiveMaintenance = (ImageView) findViewById(R.id.img_acPreventiveMaintenance);
        mMyPreventiveListRelativeLayoutSitePreventiveMaintenance = (RelativeLayout) findViewById(R.id.MyPreventiveList_relativeLayout_sitePreventiveMaintenance);
        mImgSitePreventiveMaintenance = (ImageView) findViewById(R.id.img_sitePreventiveMaintenance);
        gpsTracker = new GPSTracker(MaintenanceDashboardActivity.this);
        alertDialogManager = new AlertDialogManager(MaintenanceDashboardActivity.this);
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
