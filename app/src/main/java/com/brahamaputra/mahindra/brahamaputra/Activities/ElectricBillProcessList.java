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
import com.brahamaputra.mahindra.brahamaputra.Adapters.EbProcessTrasactionAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.EbPaymentRequest;
import com.brahamaputra.mahindra.brahamaputra.Data.EbPaymentRequestList;
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

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.eb_billing_process_flag;

public class ElectricBillProcessList extends BaseActivity {


    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private SessionManager sessionManager;
    private AlertDialogManager alertDialogManager;
    public static final int RESULT_TRAN_SUBMIT = 259;
    private EbPaymentRequest ebPaymentRequest;
    private EbProcessTrasactionAdapter ebProcessTrasactionAdapter;

    private ListView listViewElectricBill;
    private TextView txtNoTicketFound;

    public static final int RESULT_EB_REC_SUBMIT = 259;

    // Listview Pagingnation Purpose
    ArrayList<EbPaymentRequestList> ebPaymentRequestList;
    private int requestCount = 1;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_bill_process_list);

        if (eb_billing_process_flag.equals("0")) {
            this.setTitle("Bill Request");//Electricity Billing
        } else if (eb_billing_process_flag.equals("1")) {
            this.setTitle("Upload DD / Cheque");
        } else if (eb_billing_process_flag.equals("2")) {
            this.setTitle("Upload EB Receipt");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertDialogManager = new AlertDialogManager(ElectricBillProcessList.this);
        sessionManager = new SessionManager(ElectricBillProcessList.this);
        userId = sessionManager.getSessionUserId();
        assignViews();
        //prepareListData();

        ebPaymentRequestList = new ArrayList<EbPaymentRequestList>();
        if (requestCount < 2) {
            if (loading) {
                getListDataByPaging(String.valueOf(requestCount), 0);
            }
        }

        listViewElectricBill.setOnScrollListener(new EndlessScrollListener() {
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
        listViewElectricBill = (ListView) findViewById(R.id.listViewElectricBill);
        txtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
       /* listViewElectricBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(ebPaymentRequest.getEbPaymentRequestList().get(position).getRequestTicketeNo());
               // Object itemObject = parent.getAdapter().getItem(position);
                if(ebPaymentRequest.getEbPaymentRequestList().get(position).getStatusId().equals("1")) {
                    Intent intent = new Intent(ElectricBillProcessList.this, UploadEBReceiptActivity.class);
                    intent.putExtra("request_id", ebPaymentRequest.getEbPaymentRequestList().get(position).getId());
                    intent.putExtra("ticket_no", ebPaymentRequest.getEbPaymentRequestList().get(position).getRequestTicketeNo());
                    intent.putExtra("site_id", ebPaymentRequest.getEbPaymentRequestList().get(position).getSiteId());
                    intent.putExtra("site_name", ebPaymentRequest.getEbPaymentRequestList().get(position).getSiteName());

                    startActivityForResult(intent, RESULT_EB_REC_SUBMIT);
                }


            }
        });*/
    }

    public void getListDataByPaging(final String page, final int currentFirstVisibleItem) {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();
            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("PageNo", page);


            GsonRequest<EbPaymentRequest> ebPaymentRequestGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.GetElectriBillTransactionslist, jo.toString(), EbPaymentRequest.class,
                    new Response.Listener<EbPaymentRequest>() {
                        @Override
                        public void onResponse(@NonNull EbPaymentRequest response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    ebPaymentRequest = response;
                                    if (ebPaymentRequest.getEbPaymentRequestList() != null && ebPaymentRequest.getEbPaymentRequestList().size() > 0) {
                                        txtNoTicketFound.setVisibility(View.GONE);
                                        listViewElectricBill.setVisibility(View.VISIBLE);
                                        /*ArrayList<EbPaymentRequestList> dd = new ArrayList<EbPaymentRequestList>(ebPaymentRequest.getEbPaymentRequestList().size());
                                        dd.addAll(ebPaymentRequest.getEbPaymentRequestList());
                                        ebProcessTrasactionAdapter = new EbProcessTrasactionAdapter(dd, ElectricBillProcessList.this);
                                        listViewElectricBill.setAdapter(ebProcessTrasactionAdapter);*/
                                        if (ebPaymentRequest.getEbPaymentRequestList().size() < 15) {
                                            loading = false;
                                        }

                                        for (EbPaymentRequestList ebPaymentRequest :
                                                ebPaymentRequest.getEbPaymentRequestList()) {
                                            if (ebPaymentRequest.getStatusId().equals("0") && eb_billing_process_flag.equals("0")) {
                                                ebPaymentRequestList.add(ebPaymentRequest);
                                            } else if ((ebPaymentRequest.getStatusId().equals("1") || ebPaymentRequest.getStatusId().equals("2")) && eb_billing_process_flag.equals("1")) {
                                                ebPaymentRequestList.add(ebPaymentRequest);
                                            } else if ((ebPaymentRequest.getStatusId().equals("3") || ebPaymentRequest.getStatusId().equals("4")) && eb_billing_process_flag.equals("2")) {
                                                ebPaymentRequestList.add(ebPaymentRequest);
                                            }
                                        }
                                       /* if (ebPaymentRequest.getEbPaymentRequestList().get(0).getStatusId().equals("0") && eb_billing_process_flag.equals("0")) {

                                        }*/
                                        //ebPaymentRequestList.addAll(ebPaymentRequest.getEbPaymentRequestList());
                                        ebProcessTrasactionAdapter = new EbProcessTrasactionAdapter(ebPaymentRequestList, ElectricBillProcessList.this);
                                        listViewElectricBill.setAdapter(ebProcessTrasactionAdapter);
                                        listViewElectricBill.setSelectionFromTop(currentFirstVisibleItem, 0);
                                        ebProcessTrasactionAdapter.notifyDataSetChanged();


                                    } else {
                                        if (ebPaymentRequestList.size() < 1) {
                                            listViewElectricBill.setVisibility(View.GONE);
                                            txtNoTicketFound.setVisibility(View.VISIBLE);
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
            ebPaymentRequestGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            ebPaymentRequestGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(ebPaymentRequestGsonRequest, "ebPaymentRequestGsonRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.eb_add_item_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.menuAdd);
        // show the button when some condition is true
        shareItem.setVisible(false);
        if (eb_billing_process_flag.equals("0")) {
            shareItem.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuAdd:
                Intent intent = new Intent(ElectricBillProcessList.this, ElectricBillProcess.class);
                startActivityForResult(intent, RESULT_TRAN_SUBMIT);
                return true;
            case R.id.menuRefresh:
                //prepareListData();
                ebPaymentRequestList = new ArrayList<EbPaymentRequestList>();
                getListDataByPaging("1", 0);
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
     /*   if (resultCode == RESULT_OK) {
            prepareListData();
        }*/
        if (requestCode == RESULT_EB_REC_SUBMIT && resultCode == RESULT_OK) {
            //prepareListData();
            ebPaymentRequestList = new ArrayList<EbPaymentRequestList>();
            getListDataByPaging("1", 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //Out Of Code
    private void prepareListData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());


            GsonRequest<EbPaymentRequest> ebPaymentRequestGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.GetElectriBillTransactionslist, jo.toString(), EbPaymentRequest.class,
                    new Response.Listener<EbPaymentRequest>() {
                        @Override
                        public void onResponse(@NonNull EbPaymentRequest response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    ebPaymentRequest = response;
                                    if (ebPaymentRequest.getEbPaymentRequestList() != null && ebPaymentRequest.getEbPaymentRequestList().size() > 0) {
                                        txtNoTicketFound.setVisibility(View.GONE);
                                        listViewElectricBill.setVisibility(View.VISIBLE);
                                        ArrayList<EbPaymentRequestList> dd = new ArrayList<EbPaymentRequestList>(ebPaymentRequest.getEbPaymentRequestList().size());
                                        dd.addAll(ebPaymentRequest.getEbPaymentRequestList());
                                        ebProcessTrasactionAdapter = new EbProcessTrasactionAdapter(dd, ElectricBillProcessList.this);

                                        listViewElectricBill.setAdapter(ebProcessTrasactionAdapter);


                                    } else {
                                        listViewElectricBill.setVisibility(View.GONE);
                                        txtNoTicketFound.setVisibility(View.VISIBLE);
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
            ebPaymentRequestGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            ebPaymentRequestGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(ebPaymentRequestGsonRequest, "ebPaymentRequestGsonRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }
}
