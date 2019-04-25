package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.TowerDetailsData;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tower_Detail extends BaseActivity {

    private static final String TAG = Tower_Detail.class.getSimpleName();

    private TextView mTowerDetailTextViewTower;
    private TextView mTowerDetailTextViewTowerVal;
    private TextView mTowerDetailTextViewTypeOfTower;
    private TextView mTowerDetailTextViewTypeOfTowerVal;
    private TextView mTowerDetailTextViewHeightOfTower;
    private EditText mTowerDetailEditTextHeightOfTower;
    private EditText mTowerDetailEditTextBookValueOfTheTower;
    private TextView mTowerDetailTextViewDateOfPaintingOfTheTower;
    private EditText mTowerDetailEditTextDateOfPaintingOfTheTower;
    private TextView mTowerDetailTextViewSignboard;
    private TextView mTowerDetailTextViewSignboardVal;
    private TextView mTowerDetailTextViewDangerSignageboard;
    private TextView mTowerDetailSpinnerDangerSignageboardVal;
    private TextView mTowerDetailTextViewCautionSignageboard;
    private TextView mTowerDetailTextViewCautionSignageboardVal;
    private TextView mTowerDetailTextViewWarningSignageboard;
    private TextView mTowerDetailTextViewWarningSignageboardVal;

    final Calendar myCalendar = Calendar.getInstance();

    String str_tower;
    String str_typeOfTower;
    String str_signboard;
    String str_dangerSignageboard;
    String str_cautionSignageboard;
    String str_warningSignageboardVal;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";
    private HotoTransactionData hotoTransactionData;
    private TowerDetailsData towerDetailsData;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower__detail);
        this.setTitle("Tower Detail");
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();

        sessionManager = new SessionManager(Tower_Detail.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Tower_Detail.this, userId, ticketName);
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

        mTowerDetailEditTextDateOfPaintingOfTheTower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(Tower_Detail.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });
    }

    private void assignViews() {
        mTowerDetailTextViewTower = (TextView) findViewById(R.id.towerDetail_textView_tower);
        mTowerDetailTextViewTowerVal = (TextView) findViewById(R.id.towerDetail_textView_tower_val);
        mTowerDetailTextViewTypeOfTower = (TextView) findViewById(R.id.towerDetail_textView_typeOfTower);
        mTowerDetailTextViewTypeOfTowerVal = (TextView) findViewById(R.id.towerDetail_textView_typeOfTower_val);
        mTowerDetailEditTextBookValueOfTheTower = (EditText) findViewById(R.id.towerDetail_editText_BookValueOfTheTower);
        mTowerDetailTextViewHeightOfTower = (TextView) findViewById(R.id.towerDetail_textView_HeightOfTower);
        mTowerDetailEditTextHeightOfTower = (EditText) findViewById(R.id.towerDetail_editText_heightOfTower);
        mTowerDetailTextViewDateOfPaintingOfTheTower = (TextView) findViewById(R.id.towerDetail_textView_dateOfPaintingOfTheTower);
        mTowerDetailEditTextDateOfPaintingOfTheTower = (EditText) findViewById(R.id.towerDetail_editText_dateOfPaintingOfTheTower);
        mTowerDetailTextViewSignboard = (TextView) findViewById(R.id.towerDetail_textView_signboard);
        mTowerDetailTextViewSignboardVal = (TextView) findViewById(R.id.towerDetail_textView_signboard_val);
        mTowerDetailTextViewDangerSignageboard = (TextView) findViewById(R.id.towerDetail_textView_dangerSignageboard);
        mTowerDetailSpinnerDangerSignageboardVal = (TextView) findViewById(R.id.towerDetail_spinner_dangerSignageboard_val);
        mTowerDetailTextViewCautionSignageboard = (TextView) findViewById(R.id.towerDetail_textView_cautionSignageboard);
        mTowerDetailTextViewCautionSignageboardVal = (TextView) findViewById(R.id.towerDetail_textView_cautionSignageboard_val);
        mTowerDetailTextViewWarningSignageboard = (TextView) findViewById(R.id.towerDetail_textView_warningSignageboard);
        mTowerDetailTextViewWarningSignageboardVal = (TextView) findViewById(R.id.towerDetail_textView_warningSignageboard_val);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {
        mTowerDetailTextViewTowerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Tower_Detail.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_towerDetail_tower))),
                        "Tower",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_tower = item.get(position);
                        mTowerDetailTextViewTowerVal.setText(str_tower);

                    }
                });
            }
        });


        mTowerDetailTextViewTypeOfTowerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Tower_Detail.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_towerDetail_typeOfTower))),
                        "Type of tower",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfTower = item.get(position);
                        mTowerDetailTextViewTypeOfTowerVal.setText(str_typeOfTower);
                    }
                });
            }
        });

        mTowerDetailTextViewCautionSignageboardVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Tower_Detail.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_towerDetail_cautionSignageboard))),
                        "CAUTION Signage Board",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_cautionSignageboard = item.get(position);
                        mTowerDetailTextViewCautionSignageboardVal.setText(str_cautionSignageboard);
                        setSignboardVal();
                    }
                });
            }
        });

        mTowerDetailTextViewWarningSignageboardVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Tower_Detail.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_towerDetail_warningSignageboard))),
                        "WARNING Signage Board",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_warningSignageboardVal = item.get(position);
                        mTowerDetailTextViewWarningSignageboardVal.setText(str_warningSignageboardVal);
                        setSignboardVal();
                    }
                });
            }
        });

        mTowerDetailSpinnerDangerSignageboardVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Tower_Detail.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_towerDetail_dangerSignageboard))),
                        "DANGER Signage Board",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_dangerSignageboard = item.get(position);
                        mTowerDetailSpinnerDangerSignageboardVal.setText(str_dangerSignageboard);
                        setSignboardVal();
                    }
                });
            }
        });

        /*mTowerDetailTextViewSignboardVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Tower_Detail.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_towerDetail_signboard))),
                        "Sign Board",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_signboard = item.get(position);
                        mTowerDetailTextViewSignboardVal.setText(str_signboard);
                    }
                });
            }
        });*/

    }

    private void setSignboardVal() {
        str_signboard = "Not Available";
        if (mTowerDetailTextViewCautionSignageboardVal.getText().toString().trim().equals("Available") && mTowerDetailTextViewWarningSignageboardVal.getText().toString().trim().equals("Available") && mTowerDetailSpinnerDangerSignageboardVal.getText().toString().trim().equals("Available")) {
            str_signboard = "Available";
        }
        mTowerDetailTextViewSignboardVal.setText(str_signboard);

    }

    private void updateLabel() {
        String myFormat = "dd/MMM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTowerDetailEditTextDateOfPaintingOfTheTower.setText(sdf.format(myCalendar.getTime()));
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();
                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                towerDetailsData = hotoTransactionData.getTowerDetailsData();

                mTowerDetailTextViewTowerVal.setText(towerDetailsData.getTowerName());
                mTowerDetailTextViewTypeOfTowerVal.setText(towerDetailsData.getTowerType());
                mTowerDetailEditTextBookValueOfTheTower.setText(towerDetailsData.getBookValueOfTheTower());
                mTowerDetailEditTextHeightOfTower.setText(towerDetailsData.getTowerHeight());
                mTowerDetailEditTextDateOfPaintingOfTheTower.setText(towerDetailsData.getDateOfTowerPainting());
                mTowerDetailTextViewSignboardVal.setText(towerDetailsData.getBoardSign());
                mTowerDetailSpinnerDangerSignageboardVal.setText(towerDetailsData.getDangerSignBoard());
                mTowerDetailTextViewCautionSignageboardVal.setText(towerDetailsData.getCautionSignBoard());
                mTowerDetailTextViewWarningSignageboardVal.setText(towerDetailsData.getWarningSignBoard());

            } else {
                Toast.makeText(Tower_Detail.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {

            String towerName = mTowerDetailTextViewTowerVal.getText().toString().trim();
            String towerType = mTowerDetailTextViewTypeOfTowerVal.getText().toString().trim();
            String bookValueOfTheTower = mTowerDetailEditTextBookValueOfTheTower.getText().toString().trim();
            String towerHeight = mTowerDetailEditTextHeightOfTower.getText().toString().trim();
            String dateOfTowerPainting = mTowerDetailEditTextDateOfPaintingOfTheTower.getText().toString().trim();
            String boardSign = mTowerDetailTextViewSignboardVal.getText().toString().trim();
            String dangerSignBoard = mTowerDetailSpinnerDangerSignageboardVal.getText().toString().trim();
            String cautionSignBoard = mTowerDetailTextViewCautionSignageboardVal.getText().toString().trim();
            String warningSignBoard = mTowerDetailTextViewWarningSignageboardVal.getText().toString().trim();

            towerDetailsData = new TowerDetailsData(towerName, towerType, bookValueOfTheTower, towerHeight, dateOfTowerPainting, boardSign, dangerSignBoard, cautionSignBoard, warningSignBoard);
            hotoTransactionData.setTowerDetailsData(towerDetailsData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
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
                finish();
                return true;

            case R.id.menuSubmit:
                submitDetails();
                startActivity(new Intent(this, Earth_Resistance_Tower.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
