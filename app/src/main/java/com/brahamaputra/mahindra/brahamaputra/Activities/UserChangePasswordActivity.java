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

public class UserChangePasswordActivity extends BaseActivity {

    private EditText mLoginEditTextPassword;
    private EditText mLoginEditTextConfirmPassword;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Change Password");
        sessionManager = new SessionManager(UserChangePasswordActivity.this);
        assignViews();

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
        String strPassword =  mLoginEditTextPassword.getText().toString().trim();
        String strConfirmPassword = mLoginEditTextConfirmPassword.getText().toString().trim();
        Conditions.hideKeyboard(UserChangePasswordActivity.this);

        if (strPassword.isEmpty()) {
            mLoginEditTextPassword.setError("Field can not be empty");
        } else if (strConfirmPassword.isEmpty()) {
            mLoginEditTextPassword.setError(null);
            mLoginEditTextConfirmPassword.setError("Field can not be empty");
        } else {
            mLoginEditTextPassword.setError(null);
            if (Conditions.isNetworkConnected(UserChangePasswordActivity.this)) {
                if(strConfirmPassword.equals(strPassword)){
                    doChange(strPassword,strConfirmPassword);
                }else{
                    showToast("Password did not match");
                }


            } else {
                showToast("Network Error");
            }
        }
    }

    private void doChange(String strPassword, String strConfirmPassword) {
        showBusyProgress();
        try{
            JSONObject jo = new JSONObject();
            try {
                jo.put("UserId", sessionManager.getSessionUserId());
                jo.put("AccessToken", sessionManager.getSessionDeviceToken());
                jo.put("Password", strPassword);
                jo.put("ConfirmPassword", strConfirmPassword);

            } catch (JSONException e) {
                Log.e(UserChangePasswordActivity.class.getName(), e.getMessage().toString());
                return;
            }

            JsonRequest changePasswordReq = new JsonRequest(Request.Method.POST, Constants.changepassword, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(@NonNull JSONObject response) {
                            hideBusyProgress();
                            try {
                                if (response != null) {
                                    if (response.has("Success")) {
                                        int success = response.getInt("Success");
                                        if (success == 1) {
                                            showToast("Password updated");
                                            finish();
                                        } else {
                                            showToast("Problem while Password updated");
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
            changePasswordReq.setRetryPolicy(Application.getDefaultRetryPolice());
            changePasswordReq.setShouldCache(false);
            Application.getInstance().addToRequestQueue(changePasswordReq, "changePasswordReq");

        }catch(Exception e){
            hideBusyProgress();
            e.printStackTrace();
        }
    }

    private void assignViews() {
        mLoginEditTextPassword = (EditText) findViewById(R.id.login_editText_password);
        mLoginEditTextConfirmPassword = (EditText) findViewById(R.id.login_editText_confirmPassword);
    }


}
