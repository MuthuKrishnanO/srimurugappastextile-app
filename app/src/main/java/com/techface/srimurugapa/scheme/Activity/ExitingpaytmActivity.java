package com.techface.srimurugapa.scheme.Activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;
import com.techface.srimurugapa.scheme.network.ServiceWrapper;
import com.techface.srimurugapa.scheme.network.Token_Res;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class ExitingpaytmActivity extends AppCompatActivity {


    private Button startpayment;
    private EditText orderamount;
    private String TAG =" main";
    private  String userId;
    private String mEmail;
    private  String mPhone;
    private String Username;

    private  String SchemeId;
    private String mAmount;
    private  String mStardate;
    private  String mEnddate;
    private String mDuration;
    private String mSchemeName;
    private  String mSchemeType;

    @BindView(R.id.CB_terms)
    CheckBox mCheckbox;
    private ProgressDialog mProgressdialog;
    @BindView(R.id.Tv_username)
    TextView mUsernamedata;
    private AlertDialog mInfo;


    @BindView(R.id.Tv_terms)
    TextView mTV_terms;

    @BindView(R.id.Tv_privacypolicy)
    TextView mPrivacy_policy;

    @BindView(R.id.Tv_refundpolicy)
    TextView mRefundpolicy;


    private String privacypolicy;
    private String refundpolicy;
    private String cancelpolicy;
    private String termscondition;

    private  String EntryDate;
    private  String updateid;
    private String Bankapi;
    private String branchid;

    private String duration;
    private String count;
    private  int Schemetype;
//ebWdZS84184094551643
    private String TITLE;
    private String midString ="", txnAmountString="", orderIdString="", txnTokenString="";
    private Integer ActivityRequestCode = 2;
    private  String branch_id;
    String payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exitingpaytm);
        ButterKnife.bind(this);
        mProgressdialog = CommonUtils.getProgressDialog(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getString(Constants.UserId, "");
        Username=sharedPreferences.getString(Constants.USERNAME,"");
        mEmail=sharedPreferences.getString(Constants.EMAIL,"");
        mPhone=sharedPreferences.getString(Constants.PHONE,"");
        Bankapi = sharedPreferences.getString(Constants.BANKAPI, "");


        branchid=sharedPreferences.getString(Constants.BRANCHID,"");
        mUsernamedata.setText(Username);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =100000, max= 999999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        orderIdString =  date+String.valueOf(randomNum);

        if (getIntent().hasExtra("title")) {
            Intent intent = getIntent();

            Bundle bundle = getIntent().getExtras();
            TITLE=bundle.getString("title");
            mAmount=bundle.getString("amount");
            EntryDate=bundle.getString("entrydate");
            updateid=bundle.getString("schemeid");
            duration=bundle.getString("duration");
            count=bundle.getString("count");
            Schemetype=bundle.getInt("schemetype");


        }

        String termsLink = " <a href=https://techface.in/privacy/razorpay/termsofservice.html >Terms of Service</a>";
        String privacyLink = " <a href=https://techface.in/privacy/razorpay/privacystatement.html>Privacy Policy</a>";
        String refundpolicy="<a href=https://techface.in/privacy/razorpay/refundpolicy.html>Refund Policy & Cancellation Policy</a>";
      /*  String termsLink = " <a href=https://techface.in/privacy/razorpay/termsofservice.pdf >Terms of Service</a>";
        String privacyLink = " <a href=https://techface.in/privacy/razorpay/privacystatement.pdf >Privacy Policy</a>";
        String refundpolicy="<a href=https://techface.in/privacy/razorpay/refundpolicy.pdf>Refund Policy & Cancellation Policy</a>";*/
        String allText =  termsLink;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTV_terms.setMovementMethod(LinkMovementMethod.getInstance());
            mTV_terms.setText(Html.fromHtml(allText, Html.FROM_HTML_MODE_LEGACY));
            mTV_terms.setLinkTextColor(Color.BLUE);

            mPrivacy_policy.setMovementMethod(LinkMovementMethod.getInstance());
            mPrivacy_policy.setText(Html.fromHtml(privacyLink, Html.FROM_HTML_MODE_LEGACY));
            mPrivacy_policy.setLinkTextColor(Color.BLUE);

            mRefundpolicy.setMovementMethod(LinkMovementMethod.getInstance());
            mRefundpolicy.setText(Html.fromHtml(refundpolicy, Html.FROM_HTML_MODE_LEGACY));
            mRefundpolicy.setLinkTextColor(Color.BLUE);

        }
        else {
            mTV_terms.setMovementMethod(LinkMovementMethod.getInstance());
            mTV_terms.setText(Html.fromHtml(allText));
            mTV_terms.setLinkTextColor(Color.BLUE);

            mPrivacy_policy.setMovementMethod(LinkMovementMethod.getInstance());
            mPrivacy_policy.setText(Html.fromHtml(privacyLink));
            mPrivacy_policy.setLinkTextColor(Color.BLUE);

            mRefundpolicy.setMovementMethod(LinkMovementMethod.getInstance());
            mRefundpolicy.setText(Html.fromHtml(refundpolicy));
            mRefundpolicy.setLinkTextColor(Color.BLUE);
        }


        startpayment = (Button) findViewById(R.id.startpaytmpayment);
        orderamount = (EditText) findViewById(R.id.orderamount);
        orderamount.setEnabled(false);
        orderamount.setText(mAmount);

        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    startpayment.setEnabled(true);
                    startpayment.setBackgroundResource(R.color.colorAccent);
                } else {
                    startpayment.setBackgroundResource(R.color.greyColor);
                    startpayment.setEnabled(false);

                }
            }
        });

        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderamount.getText().toString().equals(""))
                {
                    Toast.makeText(ExitingpaytmActivity.this, "Amount is empty", Toast.LENGTH_LONG).show();
                }else {
                    startPayment();
                }
            }
        });

    }



    private void startPayment() {

        ServiceWrapper serviceWrapper = new ServiceWrapper(null);
        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", Bankapi, orderIdString, orderamount.getText().toString());
        call.enqueue(new Callback<Token_Res>() {
            @Override
            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
                Log.e(TAG, " respo "+ response.isSuccessful() );
                // progressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body()!=null){
                        if (response.body().getBody().getTxnToken()!="") {
                            Log.e(TAG, " transaction token : "+response.body().getBody().getTxnToken());
                            startPaytmPayment(response.body().getBody().getTxnToken());
                        }else {
                            Log.e(TAG, " Token status false");
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, " error in Token Res "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);
                Log.e(TAG, " response error "+t.toString());
            }
        });


    }

    private void startPaytmPayment(String txnToken) {
        txnTokenString = txnToken;
        // for test mode use it
  //      String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
         String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + txnAmountString;
        //Log.e(TAG, "order details "+ orderDetails);
        if (ContextCompat.checkSelfPermission(ExitingpaytmActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ExitingpaytmActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
        Log.e(TAG, " callback URL "+callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString,Bankapi, txnTokenString, payment, callBackUrl);

        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response (onTransactionResponse) : "+bundle.toString());
                String status1 = bundle.getString("STATUS");
                String txnId=bundle.getString("TXNID");
                Log.e(TAG,status1);
                if(status1.equals("TXN_SUCCESS"))
                {
                    try {

                        if (Schemetype==1) {

                            loadExistingscheme(String.valueOf(txnId));
                        } else if(Schemetype==2 ){
                            loadExistingGramscheme(String.valueOf(txnId));
                            // loadScheme(s);;
                        }
                        else
                        {
                            loadExistingCustomizeSchem(String.valueOf(txnId));
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Tranasaction Cancel by",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this,  ActivityRequestCode);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG +""," result code "+resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }


            try {

                if (mSchemeType.equals("1")) {

                    loadExistingscheme(String.valueOf(requestCode));
                } else if(mSchemeType.equals("2") ){
                    loadExistingGramscheme(String.valueOf(requestCode));
                    // loadScheme(s);;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            Log.e(TAG, " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e(TAG, " data response - "+data.getStringExtra("response"));
/*
 data response - {"BANKNAME":"WALLET","BANKTXNID":"1395841115",
 "CHECKSUMHASH":"7jRCFIk6eRmrep+IhnmQrlrL43KSCSXrmMP5pH0hekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
 "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAcR4116","ORDERID":"100620202152",
 "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
 "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"202006101112128001101683631290118"}
  */
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }else{
            Log.e(TAG, " payment failed");
        }
    }

    private void loadExistingCustomizeSchem(String s) {
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getPaymentCustomizeSchemeInfo(userId,updateid,EntryDate,mAmount,s,"Success",duration,count,branchid);
        mProgressdialog.show();
        call.enqueue(new Callback<List<DataStatus>>() {
            @Override
            public void onResponse(Call<List<DataStatus>> call, Response<List<DataStatus>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    loadData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DataStatus>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo= Responseinfodialog.alertshow(ExitingpaytmActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });

    }
    private void loadExistingGramscheme(String s) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getPaymentGramSchemeInfo(userId,updateid,EntryDate,mAmount,s,"Success",duration,count,branchid);
        mProgressdialog.show();
        call.enqueue(new Callback<List<DataStatus>>() {
            @Override
            public void onResponse(Call<List<DataStatus>> call, Response<List<DataStatus>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    loadData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DataStatus>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo= Responseinfodialog.alertshow(ExitingpaytmActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });
    }

    private void loadExistingscheme(String s) {


        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getPaymentSchemeInfoNew(userId,updateid,EntryDate,mAmount,s,"Success",duration,count,branch_id);
        mProgressdialog.show();
        call.enqueue(new Callback<List<DataStatus>>() {
            @Override
            public void onResponse(Call<List<DataStatus>> call, Response<List<DataStatus>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    loadData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DataStatus>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo= Responseinfodialog.alertshow(ExitingpaytmActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });
    /*    ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getPaymentSchemeInfo(userId,updateid,EntryDate,mAmount,s,"Success",duration,count,branchid);
        mProgressdialog.show();
        call.enqueue(new Callback<List<DataStatus>>() {
            @Override
            public void onResponse(Call<List<DataStatus>> call, Response<List<DataStatus>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    loadData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DataStatus>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo= Responseinfodialog.alertshow(ExitingpaytmActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });*/

    }







    private void loadData(List<DataStatus> body) {

        String s=body.get(0).getmStatus();
        String data=body.get(0).getmData();
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        finish();

    }


}