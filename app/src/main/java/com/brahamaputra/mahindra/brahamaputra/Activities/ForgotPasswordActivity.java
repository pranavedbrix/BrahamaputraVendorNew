package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Volley.JsonRequest;
import com.brahamaputra.mahindra.brahamaputra.Volley.SettingsMy;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText mForgotPasswordEditTextUserName;
    //private EditText mForgotPasswordEditTextEmail;
    private Button mForgotPasswordButtonSubmit;
    private GlobalMethods globalMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        this.setTitle("Forgot Password");
        assignViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        globalMethods = new GlobalMethods();

        mForgotPasswordButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
        mForgotPasswordButtonSubmit.setAllCaps(false);
    }

    private void checkValidations() {
        String userName = mForgotPasswordEditTextUserName.getText().toString();
        Conditions.hideKeyboard(ForgotPasswordActivity.this);
        if (userName.isEmpty()) {
            mForgotPasswordEditTextUserName.setError("Field can not be empty");
        } else {
            doForgot(userName);
        }
        /*String email = mForgotPasswordEditTextEmail.getText().toString();
        Conditions.hideKeyboard(ForgotPasswordActivity.this);
        if (email.isEmpty()) {
            mForgotPasswordEditTextEmail.setError("Field can not be empty");
        } else {
            doForgot(email);
        }*/

    }

    private void doForgot(String userName) {
        //private void doForgot(String email) {

        showBusyProgress();
        try {
            JSONObject jo = new JSONObject();
            try {
                jo.put("APIKEY", Constants.APP_KEY__);
                jo.put("SECRETKEY", Constants.APP_SECRET__);
                jo.put("Username", userName);
                //jo.put("Email", email);

            } catch (JSONException e) {
                Log.e(ForgotPasswordActivity.class.getName(), e.getMessage().toString());
                return;
            }
            JsonRequest forgotPasswordReq = new JsonRequest(Request.Method.POST, Constants.forgotpassword, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(@NonNull JSONObject response) {
                            hideBusyProgress();
                            try {
                                if (response != null) {
                                    if (response.has("Success")) {
                                        int success = response.getInt("Success");
                                        if (success == 1) {
                                            showToast("Password sent on your email.");
                                            finish();
                                        } else {
                                            showToast("Problem while Password sending");
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
            forgotPasswordReq.setRetryPolicy(Application.getDefaultRetryPolice());
            forgotPasswordReq.setShouldCache(false);
            Application.getInstance().addToRequestQueue(forgotPasswordReq, "forgotPasswordReq");
        } catch (Exception e) {
            hideBusyProgress();
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void assignViews() {
        mForgotPasswordEditTextUserName = (EditText) findViewById(R.id.forgotPassword_editText_username);
        //mForgotPasswordEditTextEmail= (EditText) findViewById(R.id.forgotPassword_editText_email);
        mForgotPasswordButtonSubmit = (Button) findViewById(R.id.forgotPassword_button_submit);
    }
}
