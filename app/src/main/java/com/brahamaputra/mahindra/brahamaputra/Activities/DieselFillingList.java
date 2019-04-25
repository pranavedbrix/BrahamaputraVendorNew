package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Adapters.DieselTrasactionAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselFillingtransaction;
import com.brahamaputra.mahindra.brahamaputra.Data.DiselFillingTransactionList;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.EndlessScrollListener;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DieselFillingList extends BaseActivity {

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketName = "";
    private String ticketId = "";
    private DieselFillingtransaction dieselFillingtransaction;
    private SessionManager sessionManager;

    private ListView mDieselFillingListListViewTickets;
    private TextView mTxtNoTicketFound;
    private AlertDialogManager alertDialogManager;
    private DieselTrasactionAdapter dieselTrasactionAdapter;
    public static final int RESULT_TRAN_SUBMIT = 259;

    // Listview Pagingnation Purpose
    ArrayList<DiselFillingTransactionList> diselFillingTransactionList;
    private int requestCount = 1;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel_filling_list);
        this.setTitle("Diesel Filling");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertDialogManager = new AlertDialogManager(DieselFillingList.this);
        sessionManager = new SessionManager(DieselFillingList.this);
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(DieselFillingList.this, userId, ticketName);
        dieselFillingtransaction = new DieselFillingtransaction();
        assignViews();
        //prepareListData();

        diselFillingTransactionList = new ArrayList<DiselFillingTransactionList>();
        if (requestCount < 2) {
            if (loading) {
                getListDataByPaging(String.valueOf(requestCount), 0);
            }
        }

        mDieselFillingListListViewTickets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount, int firstVisibleItem) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if (loading) {
                    getListDataByPaging(String.valueOf(page), firstVisibleItem);
                }
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

    }

    private void assignViews() {
        mDieselFillingListListViewTickets = (ListView) findViewById(R.id.listViewDiesel);
        mTxtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
    }

    public void getListDataByPaging(final String page, final int currentFirstVisibleItem) {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("PageNo", page);


            GsonRequest<DieselFillingtransaction> dieselFillingtransactionRequest = new GsonRequest<>(Request.Method.POST, Constants.Getdieseltransactionticketlist, jo.toString(), DieselFillingtransaction.class,
                    new Response.Listener<DieselFillingtransaction>() {
                        @Override
                        public void onResponse(@NonNull DieselFillingtransaction response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dieselFillingtransaction = response;
                                    if (dieselFillingtransaction.getDiselFillingTransactionList() != null && dieselFillingtransaction.getDiselFillingTransactionList().size() > 0) {
                                        mTxtNoTicketFound.setVisibility(View.GONE);
                                        mDieselFillingListListViewTickets.setVisibility(View.VISIBLE);
                                        /*ArrayList<DiselFillingTransactionList> dd = new ArrayList<DiselFillingTransactionList>(dieselFillingtransaction.getDiselFillingTransactionList().size());
                                        dd.addAll(dieselFillingtransaction.getDiselFillingTransactionList());
                                        dieselTrasactionAdapter = new DieselTrasactionAdapter(dd, DieselFillingList.this);
                                        mDieselFillingListListViewTickets.setAdapter(dieselTrasactionAdapter);*/

                                        if (dieselFillingtransaction.getDiselFillingTransactionList().size() < 15) {
                                            loading = false;
                                        }
                                        diselFillingTransactionList.addAll(dieselFillingtransaction.getDiselFillingTransactionList());
                                        dieselTrasactionAdapter = new DieselTrasactionAdapter(diselFillingTransactionList, DieselFillingList.this);
                                        mDieselFillingListListViewTickets.setAdapter(dieselTrasactionAdapter);
                                        mDieselFillingListListViewTickets.setSelectionFromTop(currentFirstVisibleItem, 0);
                                        dieselTrasactionAdapter.notifyDataSetChanged();


                                    } else {
                                        if (diselFillingTransactionList.size() < 1) {
                                            mDieselFillingListListViewTickets.setVisibility(View.GONE);
                                            mTxtNoTicketFound.setVisibility(View.VISIBLE);
                                        }
                                    }
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

                }
            });
            dieselFillingtransactionRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            dieselFillingtransactionRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(dieselFillingtransactionRequest, "dieselFillingtransactionRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuAdd:
                /*Intent intent = new Intent(DieselFillingList.this, DieselFilling.class);
                startActivity(intent);*/
                Intent intent = new Intent(DieselFillingList.this, DieselFilling.class);
                startActivityForResult(intent, RESULT_TRAN_SUBMIT);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //prepareListData();
            diselFillingTransactionList = new ArrayList<DiselFillingTransactionList>();
            getListDataByPaging("1", 0);
        }
    }

    //Out Of Process
    private void prepareListData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());


            GsonRequest<DieselFillingtransaction> dieselFillingtransactionRequest = new GsonRequest<>(Request.Method.POST, Constants.Getdieseltransactionticketlist, jo.toString(), DieselFillingtransaction.class,
                    new Response.Listener<DieselFillingtransaction>() {
                        @Override
                        public void onResponse(@NonNull DieselFillingtransaction response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dieselFillingtransaction = response;
                                    if (dieselFillingtransaction.getDiselFillingTransactionList() != null && dieselFillingtransaction.getDiselFillingTransactionList().size() > 0) {
                                        mTxtNoTicketFound.setVisibility(View.GONE);
                                        mDieselFillingListListViewTickets.setVisibility(View.VISIBLE);
                                        ArrayList<DiselFillingTransactionList> dd = new ArrayList<DiselFillingTransactionList>(dieselFillingtransaction.getDiselFillingTransactionList().size());
                                        dd.addAll(dieselFillingtransaction.getDiselFillingTransactionList());
                                        dieselTrasactionAdapter = new DieselTrasactionAdapter(dd, DieselFillingList.this);
                                        mDieselFillingListListViewTickets.setAdapter(dieselTrasactionAdapter);

                                    } else {
                                        mDieselFillingListListViewTickets.setVisibility(View.GONE);
                                        mTxtNoTicketFound.setVisibility(View.VISIBLE);
                                    }
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

                }
            });
            dieselFillingtransactionRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            dieselFillingtransactionRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(dieselFillingtransactionRequest, "dieselFillingtransactionRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }
}
