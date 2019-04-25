package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.Notification;
import com.brahamaputra.mahindra.brahamaputra.Data.UserDetailsParent;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.helper.DatabaseHelper;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends BaseActivity {

    //private ImageView mImageView2;
    private static final String TAG = "UserProfileActivity";
    CircleImageView mImageView2;
    private TextView mTextView;
    private TextView mTextView2;
    private ImageView mUserProfileImageViewEmail;
    private TextView mUserProfileTextViewEmail;
    private TextView mUserProfileTextViewEmailText;
    private ImageView mUserProfileImageViewMobileNo;
    private TextView mUserProfileTextViewMobileNo;
    private TextView mUserProfileTextViewMobileNoText;
    private TextView mUserProfileTextViewuserName;
    private TextView mUserProfileTextViewuserEmail;

    private ImageView mUserProfileImageViewCircle;
    private TextView mUserProfileTextViewCircle;
    private TextView mUserProfileTextViewCircleText;

    ImageView mUserProfileImageViewDesignation;
    TextView mUserProfileTextViewDesignation;
    TextView mUserProfileTextViewDesignationText;

    private ImageView mUserProfileImageViewState;
    private TextView mUserProfileTextViewState;
    private TextView mUserProfileTextViewStateText;

    private ImageView mUserProfileImageViewSsa;
    private TextView mUserProfileTextViewSsa;
    private TextView mUserProfileTextViewSsaText;

    private ImageView mUserProfileImageViewCustomer;
    private TextView mUserProfileTextViewCustomer;
    private TextView mUserProfileTextViewCustomerText;

    ImageView mUserProfileImageViewUserName;
    TextView mUserProfileTextViewUserName;
    TextView mUserProfileTextViewUserNameText;

    private SessionManager sessionManager;
    private AlertDialogManager alertDialogManager;
    private TextView textViewAppVersion;

    public static final int RESULT_UPDATE_PROFILE = 328;
    public static final int RESULT_NOTIFICATION = 328;

    private DatabaseHelper databaseHelper;
    TextView textCartItemCount;
    int mCartItemCount = 10;

    private BroadcastReceiver broadcastReceiver;
    private final String SERVICE_RESULT = "com.service.result";
    private final String SERVICE_MESSAGE = "com.service.message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c31432")));

        /*#C92143
        #94203D
        #c31432*/
        //getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));


        this.setTitle("Profile");
        assignViews();
        alertDialogManager = new AlertDialogManager(UserProfileActivity.this);
        sessionManager = new SessionManager(UserProfileActivity.this);
        prepareUserPersonalData();
        //setValues();
        textViewAppVersion.setText("App Version : " + BuildConfig.VERSION_NAME);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // do something here.
                String receiveFlag = intent.getStringExtra(SERVICE_MESSAGE);
                if (receiveFlag.equals("receive")) {
                    invalidateOptionsMenu();
                }
            }
        };
        invalidateOptionsMenu();
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver),
                new IntentFilter(SERVICE_RESULT));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private void setValues() {

        if (!sessionManager.getSessionUsername().equals("") && !sessionManager.getSessionUserId().equals("")) {
            mUserProfileTextViewuserName.setText(sessionManager.getSessionUserFirstName().toString() + " " + sessionManager.getSessionUserFirstLast().toString());
            mUserProfileTextViewuserEmail.setText(sessionManager.getSessionUsername().toString());
            mUserProfileTextViewUserNameText.setText(sessionManager.getSessionUsername().toString());
            mUserProfileTextViewEmailText.setText(sessionManager.getSessionUserEmail().toString());
            mUserProfileTextViewMobileNoText.setText(sessionManager.getSessionMobileNo().toString());
            mUserProfileTextViewDesignationText.setText(sessionManager.getSessionDesignation().toString());
            mUserProfileTextViewCircleText.setText(sessionManager.getSessionCircle().toString());
            mUserProfileTextViewStateText.setText(sessionManager.getUser_State().toString());
            mUserProfileTextViewSsaText.setText(sessionManager.getUser_Ssa().toString());
            mUserProfileTextViewCustomerText.setText(sessionManager.getSessionCustomer().toString());
            //mImageView2.setImageBitmap(getBitmapFromURL(sessionManager.getSessionProfileImageUrl().toString()));

            String imageUri = sessionManager.getSessionProfileImageUrl().toString();
            Glide.with(getApplicationContext())
                    .load(imageUri)
                    .into(mImageView2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.menuNotification);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }


    private void setupBadge() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            if (databaseHelper.getAllNotification() != null && databaseHelper.getAllNotification().size() > 0) {
                ArrayList<Notification> dd = new ArrayList<Notification>(databaseHelper.getAllNotification().size());
                dd.addAll(databaseHelper.getAllNotification());
                mCartItemCount = countUnreadNotifications(dd);
                //mCartItemCount = databaseHelper.getAllNotification().size();
            } else {
                mCartItemCount = 0;
            }
            if (textCartItemCount != null) {
            /*if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {*/
                //textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                textCartItemCount.setText(String.valueOf(mCartItemCount > 99 ? "99+" : mCartItemCount));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }

                if (mCartItemCount < 1) {
                    textCartItemCount.setVisibility(View.GONE);
                }
                /*}*/
            }
        } catch (Exception ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
        }
    }

    public int countUnreadNotifications(ArrayList<Notification> dd) {
        int duplicates = 0;
        for (int i = 0; i < dd.size(); i++) {
            if (dd.get(i).getIsRead() == 0)
                duplicates++;
        }
        return duplicates;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(UserProfileActivity.this, DashboardCircularActivity.class));
                return true;

            case R.id.menuLogout:
                /*sessionManager.clearSessionCredentials();
                finish();
                //moveTaskToBack(true);
                startActivity(new Intent(UserProfileActivity.this,LoginActivity.class)); Commented by 008 on 10-11-2018*/
                showSettingsAlert();
                return true;

            case R.id.menuEditProf:
                startActivityForResult(new Intent(UserProfileActivity.this, UserEditProfileActivity.class), RESULT_UPDATE_PROFILE);
                return true;

            case R.id.menuNotification:
                startActivityForResult(new Intent(UserProfileActivity.this, NotificationList.class), RESULT_NOTIFICATION);
                return true;

            case R.id.menuChangePassword:
                startActivity(new Intent(UserProfileActivity.this, UserChangePasswordActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ////added by 008 on 10-11-2018 Start


    private void showSettingsAlert() {

        alertDialogManager.Dialog("Confirmation", "Are you sure you want to logout?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                sessionManager.clearSessionCredentials();
                finish();
                startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
            }

            @Override
            public void onNegativeClick() {

            }
        }).show();

    }
////added by 008 on 10-11-2018 End

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(UserProfileActivity.this, DashboardCircularActivity.class));
    }

    private void assignViews() {
        mImageView2 = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageView2);

        //mImageView2 = (ImageView) findViewById(R.id.imageView2);
        //mTextView = (TextView) findViewById(R.id.textView);
        //mTextView2 = (TextView) findViewById(R.id.textView2);
        mUserProfileImageViewEmail = (ImageView) findViewById(R.id.userProfile_imageView_email);
        mUserProfileTextViewEmail = (TextView) findViewById(R.id.userProfile_textView_email);
        mUserProfileTextViewEmailText = (TextView) findViewById(R.id.userProfile_textView_emailText);

        mUserProfileImageViewMobileNo = (ImageView) findViewById(R.id.userProfile_imageView_mobileNo);
        mUserProfileTextViewMobileNo = (TextView) findViewById(R.id.userProfile_textView_mobileNo);
        mUserProfileTextViewMobileNoText = (TextView) findViewById(R.id.userProfile_textView_mobileNoText);

        mUserProfileTextViewuserName = (TextView) findViewById(R.id.textView_userProfile_name);
        mUserProfileTextViewuserEmail = (TextView) findViewById(R.id.textView_userProfile_userName);

        mUserProfileImageViewCircle = (ImageView) findViewById(R.id.userProfile_imageView_circle);
        mUserProfileTextViewCircle = (TextView) findViewById(R.id.userProfile_textView_circle);
        mUserProfileTextViewCircleText = (TextView) findViewById(R.id.userProfile_textView_circleText);

        mUserProfileImageViewUserName = (ImageView) findViewById(R.id.userProfile_imageView_userName);
        mUserProfileTextViewUserName = (TextView) findViewById(R.id.userProfile_textView_userName);
        mUserProfileTextViewUserNameText = (TextView) findViewById(R.id.userProfile_textView_userNameText);

        mUserProfileImageViewDesignation = (ImageView) findViewById(R.id.userProfile_imageView_designation);
        mUserProfileTextViewDesignation = (TextView) findViewById(R.id.userProfile_textView_designation);
        mUserProfileTextViewDesignationText = (TextView) findViewById(R.id.userProfile_textView_designationText);

        mUserProfileImageViewState = (ImageView) findViewById(R.id.userProfile_imageView_state);
        mUserProfileTextViewState = (TextView) findViewById(R.id.userProfile_textView_state);
        mUserProfileTextViewStateText = (TextView) findViewById(R.id.userProfile_textView_stateText);

        mUserProfileImageViewSsa = (ImageView) findViewById(R.id.userProfile_imageView_ssa);
        mUserProfileTextViewSsa = (TextView) findViewById(R.id.userProfile_textView_ssa);
        mUserProfileTextViewSsaText = (TextView) findViewById(R.id.userProfile_textView_ssaText);

        mUserProfileImageViewCustomer = (ImageView) findViewById(R.id.userProfile_imageView_customer);
        mUserProfileTextViewCustomer = (TextView) findViewById(R.id.userProfile_textView_customer);
        mUserProfileTextViewCustomerText = (TextView) findViewById(R.id.userProfile_textView_customerText);


        textViewAppVersion = (TextView) findViewById(R.id.textView_appVersion);
    }

    private void prepareUserPersonalData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());


            GsonRequest<UserDetailsParent> userProfileRequestGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.GetUserDetails, jo.toString(), UserDetailsParent.class,
                    new Response.Listener<UserDetailsParent>() {
                        @Override
                        public void onResponse(@NonNull UserDetailsParent response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {

                                if (response.getSuccess() == 1) {
                                    sessionManager.updateSessionUsername(response.getUserDetails().getUsername());
                                    sessionManager.updateSessionUserID(response.getUserDetails().getId());
                                    sessionManager.updateSessionUserFirstName(response.getUserDetails().getFirstName());
                                    sessionManager.updateSessionUserLastName(response.getUserDetails().getLastName());
                                    sessionManager.updateSessionUserEmail(response.getUserDetails().getEmail());
                                    sessionManager.updateSessionMobileNo(response.getUserDetails().getMobileNo());
                                    sessionManager.updateSessionDesignation(response.getUserDetails().getDesignation());
                                    sessionManager.updateSessionProfileImageUrl(response.getUserDetails().getProfileImageUrl());
                                    sessionManager.updateSessionCircle(response.getUserDetails().getUserAdditionalDetails().getCircleName());
                                    sessionManager.updateSessionState(response.getUserDetails().getUserAdditionalDetails().getStateName());
                                    sessionManager.updateSessionSsa(response.getUserDetails().getUserAdditionalDetails().getSsaName());
                                    sessionManager.updateSessionCustomer(response.getUserDetails().getUserAdditionalDetails().getCustomerName());

                                    setValues();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        showToast("No Internet Connection.");
                    }
                    hideBusyProgress();
                    setValues();
                }
            });
            userProfileRequestGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            userProfileRequestGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(userProfileRequestGsonRequest, "userProfileRequestGsonRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
            setValues();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_UPDATE_PROFILE && resultCode == RESULT_OK) {
            setValues();
        }
    }

}
