package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.JsonRequest;
import com.brahamaputra.mahindra.brahamaputra.Volley.SettingsMy;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserEditProfileActivity extends BaseActivity {

    private EditText mUserEditProfileEditTextFirstName;
    private EditText mUserEditProfileEditTextLastName;
    private EditText mUserEditProfileEditTextEmail;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Edit Profile");
        sessionManager = new SessionManager(UserEditProfileActivity.this);
        assignViews();
        setValues();

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
                checkValidations();
                //finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkValidations() {
        String strFirstname =  mUserEditProfileEditTextFirstName.getText().toString().trim();
        String strLastname = mUserEditProfileEditTextLastName.getText().toString().trim();
        String strEmail = mUserEditProfileEditTextEmail.getText().toString().trim();

        Conditions.hideKeyboard(UserEditProfileActivity.this);

        if (strFirstname.isEmpty()) {
            mUserEditProfileEditTextFirstName.setError("Field can not be empty");
        } else if (strLastname.isEmpty()) {
            mUserEditProfileEditTextFirstName.setError(null);
            mUserEditProfileEditTextLastName.setError("Field can not be empty");
            mUserEditProfileEditTextEmail.setError(null);
        }else if (strEmail.isEmpty()) {
            mUserEditProfileEditTextFirstName.setError(null);
            mUserEditProfileEditTextLastName.setError(null);
            mUserEditProfileEditTextEmail.setError("Field can not be empty");
        } else {
           // mLoginEditTextPassword.setError(null);
            if (Conditions.isNetworkConnected(UserEditProfileActivity.this)) {

                if(!strLastname.equals("") && !strFirstname.equals("") && !strEmail.equals("")){
                    doUpdate(strFirstname,strLastname,strEmail);
                }else{
                    showToast("Please fill all details");
                }

            } else {
                showToast("Network Error");
            }
        }
    }

    private void doUpdate(final String strFirstname, final String strLastname, final String strEmail) {
        showBusyProgress();
        try{
            JSONObject jo = new JSONObject();
            try {
                jo.put("UserId", sessionManager.getSessionUserId());
                jo.put("AccessToken", sessionManager.getSessionDeviceToken());
                jo.put("FirstName", strFirstname);
                jo.put("LastName", strLastname);
                jo.put("Email", strEmail);

            } catch (JSONException e) {
                Log.e(UserChangePasswordActivity.class.getName(), e.getMessage().toString());
                return;
            }

            JsonRequest editProfileReq = new JsonRequest(Request.Method.POST, Constants.editprofile, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(@NonNull JSONObject response) {
                            hideBusyProgress();
                            try {
                                if (response != null) {
                                    if (response.has("Success")) {
                                        int success = response.getInt("Success");
                                        if (success == 1) {
                                            sessionManager.updateSessionUserFirstName(strFirstname);
                                            sessionManager.updateSessionUserLastName(strLastname);
                                            sessionManager.updateSessionUsername(strEmail);
                                            setResult(RESULT_OK);
                                            showToast("Profile updated");
                                            finish();
                                        } else {
                                            showToast("Problem while Profile updated");
                                            finish();
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                showToast("Exception :" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();
                    showToast(SettingsMy.getErrorMessage(error));
                }
            });
            editProfileReq.setRetryPolicy(Application.getDefaultRetryPolice());
            editProfileReq.setShouldCache(false);
            Application.getInstance().addToRequestQueue(editProfileReq, "editProfileReq");

        }catch(Exception e){
            hideBusyProgress();
            e.printStackTrace();
        }

    }

    private void assignViews() {
        mUserEditProfileEditTextFirstName = (EditText) findViewById(R.id.userEditProfile_editText_firstName);
        mUserEditProfileEditTextLastName = (EditText) findViewById(R.id.userEditProfile_editText_lastName);
        mUserEditProfileEditTextEmail = (EditText) findViewById(R.id.userEditProfile_editText_email);
    }

    private void setValues(){
        mUserEditProfileEditTextFirstName.setText(sessionManager.getSessionUserFirstName().toString());
        mUserEditProfileEditTextLastName.setText(sessionManager.getSessionUserFirstLast().toString());
        mUserEditProfileEditTextEmail.setText(sessionManager.getSessionUsername().toString());
    }
}
