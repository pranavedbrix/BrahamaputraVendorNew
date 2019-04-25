package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.brahamaputra.mahindra.brahamaputra.Adapters.SitePreventiveMaintenanceSectionListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Data.BatteryType;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.SitePreventiveMaintenanceSection;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_Selected_SiteType;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmNoOfAcAvailableAtSite;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.sitePmServoStabilizerWorkingStatus;

public class SitePreventiveMaintenanceSectionsListActivity extends BaseActivity {

    public ListView sitePreventiveMaintenanceSections_listView_sections;
    ArrayList<SitePreventiveMaintenanceSection> dataModels;
    String[] values;
    private static SitePreventiveMaintenanceSectionListAdapter adapter;
    public static final int RESULT_READING_COMPLETED = 650;

    //vinayak code start
    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";


    private PreventiveMaintanceSiteTransactionDetails preventiveMaintanceSiteTransactionDetails;
    ArrayList<BatteryType> batteryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_preventive_maintenance_sections_list);

        sitePreventiveMaintenanceSections_listView_sections = (ListView) findViewById(R.id.sitePreventiveMaintenanceSections_listView_sections);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        batteryType = new ArrayList<BatteryType>();
        Intent intent = getIntent();
        batteryType = (ArrayList<BatteryType>) intent.getSerializableExtra("batteryType");

        String tic_Name = intent.getStringExtra("ticketName");
        this.setTitle(tic_Name);

        this.setTitle("Site PM Sections List");


        values = getResources().getStringArray(R.array.listView_sitePreventiveMaintenanceSections_sections);
        dataModels = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (i / 2 == 0) {
                dataModels.add(new SitePreventiveMaintenanceSection("" + (i + 1), "" + values[i], 0));
            } else {
                dataModels.add(new SitePreventiveMaintenanceSection("" + (i + 1), "" + values[i], 0));
            }
            //dataModels.add(new SitePreventiveMaintenanceSection(""+(i+1),""+values[i],true));
        }

        adapter = new SitePreventiveMaintenanceSectionListAdapter(dataModels, getApplicationContext());

        sitePreventiveMaintenanceSections_listView_sections.setAdapter(adapter);
        sitePreventiveMaintenanceSections_listView_sections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        if (batteryType != null) {
                            if (batteryType.size() > 0) {
                                Intent intent = new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteHygieneGeneralSaftyActivity.class);
                                intent.putExtra("batteryType", batteryType);
                                startActivity(intent);
                            } else {
                                showToast("Battery Type not found, Please reopen ticket");
                            }
                        } else {
                            showToast("Battery Type not found, Please reopen ticket");
                        }
                        break;
                    case 1:
                        if (batteryType != null) {
                            if (batteryType.size() > 0) {
                                Intent intent = new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteAlarmCheckPointsActivity.class);
                                intent.putExtra("batteryType", batteryType);
                                startActivity(intent);
                            } else {
                                showToast("Battery Type not found, Please reopen ticket");
                            }
                        } else {
                            showToast("Battery Type not found, Please reopen ticket");
                        }
                        //startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteAlarmCheckPointsActivity.class));
                        break;
                    case 2:
                        if (batteryType != null) {
                            if (batteryType.size() > 0) {
                                Intent intent = new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.class);
                                intent.putExtra("batteryType", batteryType);
                                startActivity(intent);
                            } else {
                                showToast("Battery Type not available for that site");
                            }
                        } else {
                            showToast("Battery Type not found, Please reopen ticket");
                        }
                        //startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteEarthingCheckPointsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteEbMeterBoxActivity.class));
                        break;
                    case 5:
                        if (hototicket_sourceOfPower != null) {
                            if (!hototicket_sourceOfPower.equals("Non DG")) {
                                startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteDgCheckPointsActivity.class));
                            } else {
                                showToast("This site is Non DG");
                            }
                        } else {
                            showToast("Source Of Power not found, Please reopen ticket");
                            finish();
                        }
                        break;
                    case 6:
                        if (hototicket_sourceOfPower != null) {
                            if (!hototicket_sourceOfPower.equals("Non DG")) {
                                startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteDgBatteryCheckPointsActivity.class));
                            } else {
                                showToast("This site is Non DG");
                            }
                        } else {
                            showToast("Source Of Power not found, Please reopen ticket");
                            finish();
                        }
                        break;
                    case 7:
                        if (sitePmNoOfAcAvailableAtSite != null) {
                            if (!sitePmNoOfAcAvailableAtSite.equals("0")) {
                                startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteAcCheckPointsActivity.class));
                            } else {
                                showToast("No of AC not available for that site");
                            }

                        } else {
                            showToast("No of AC not available for that site");
                            finish();
                        }
                        break;
                    case 8:
                        startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteSmpsCheckPointsActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteRectifierModuleCheckPointActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSitePmsAmfPanelCheckPointsActivity.class));
                        break;
                    case 11:
                        if (sitePmServoStabilizerWorkingStatus != null) {
                            if (!sitePmServoStabilizerWorkingStatus.equals("Not Available")) {
                                startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteServoCheckPointsActivity.class));
                            } else {
                                showToast("Servo Stabilizer Working Status not available for that site");
                            }
                        } else {
                            showToast("Servo Stabilizer Working Status not available for that site");
                            finish();
                        }
                        break;
                    case 12:
                        if (hototicket_Selected_SiteType != null) {
                            if (!hototicket_Selected_SiteType.equals("Outdoor")) {
                                startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteShelterCheckPointsActivity.class));
                            } else {
                                showToast("Site Type of that site is outdoor");
                            }
                        } else {
                            showToast("Site Type not available for that site");
                            finish();
                        }

                        break;
                    case 13:
                        startActivity(new Intent(SitePreventiveMaintenanceSectionsListActivity.this, PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.class));
                        break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_READING_COMPLETED && resultCode == RESULT_OK) {
            onBackPressed();
        }
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

    @Override
    protected void onResume() {
        refreshList();
        super.onResume();
    }


    public void refreshList() {

        sessionManager = new SessionManager(SitePreventiveMaintenanceSectionsListActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(SitePreventiveMaintenanceSectionsListActivity.this, userId, ticketName);

        preventiveMaintanceSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        if (getHotoObj()) {


            sitePreventiveMaintenanceSections_listView_sections = (ListView) findViewById(R.id.sitePreventiveMaintenanceSections_listView_sections);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            values = getResources().getStringArray(R.array.listView_sitePreventiveMaintenanceSections_sections);
            dataModels = new ArrayList<>();
            //Boolean greenStatus = false;
            int status = 0;

            for (int i = 0; i < values.length; i++) {
                status = checkIsSubmited(i);

                if (i / 2 == 0) {
                    dataModels.add(new SitePreventiveMaintenanceSection("" + (i + 1), "" + values[i], status));
                } else {
                    dataModels.add(new SitePreventiveMaintenanceSection("" + (i + 1), "" + values[i], status));
                }
                //dataModels.add(new SitePreventiveMaintenanceSection(""+(i+1),""+values[i],true));

            }

            adapter = new SitePreventiveMaintenanceSectionListAdapter(dataModels, getApplicationContext());

            sitePreventiveMaintenanceSections_listView_sections.setAdapter(adapter);
        }

    }

    public Boolean getHotoObj() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                preventiveMaintanceSiteTransactionDetails = gson.fromJson(jsonInString, PreventiveMaintanceSiteTransactionDetails.class);

                if (preventiveMaintanceSiteTransactionDetails != null) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public int checkIsSubmited(Integer SecNo) {
        switch (SecNo) {
            case 0:
                return preventiveMaintanceSiteTransactionDetails.getSiteHygenieneGenralSeftyParameter().getSubmited();
            case 1:
                return preventiveMaintanceSiteTransactionDetails.getAlarmCheckPoints().getSubmited();
            case 2:
                return preventiveMaintanceSiteTransactionDetails.getBatteryBankCheckPointsParentData().getSubmited();
            case 3:
                return preventiveMaintanceSiteTransactionDetails.getEarthingCheckPointsParentData().getSubmited();
            case 4:
                return preventiveMaintanceSiteTransactionDetails.getEbMeterBox().getSubmited();
            case 5:
                return preventiveMaintanceSiteTransactionDetails.getDgCheckPointsParentData().getSubmited();
            case 6:
                return preventiveMaintanceSiteTransactionDetails.getDgBatteryCheckPointsParentData().getSubmited();
            case 7:
                return preventiveMaintanceSiteTransactionDetails.getAcCheckPointParentData().getSubmited();
            case 8:
                return preventiveMaintanceSiteTransactionDetails.getSmpsCheckPointParentData().getSubmited();
            case 9:
                return preventiveMaintanceSiteTransactionDetails.getRectifierModuleCheckPoint().getSubmited();
            case 10:
                return preventiveMaintanceSiteTransactionDetails.getPmsAmfPanelCheckPoints().getSubmited();
            case 11:
                return preventiveMaintanceSiteTransactionDetails.getServoCheckPoints().getSubmited();
            case 12:
                return preventiveMaintanceSiteTransactionDetails.getShelterCheckPoints().getSubmited();
            case 13:
                return preventiveMaintanceSiteTransactionDetails.getOtherElectricalCheckPoints().getSubmited();
        }
        return 0;
    }


}
