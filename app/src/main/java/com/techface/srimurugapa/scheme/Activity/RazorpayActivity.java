package com.techface.srimurugapa.scheme.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {

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

    private  String EntryDate;
    private  String updateid;
    private String Bankapi;
    private String branchid;

    private String TITLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
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


        if (getIntent().hasExtra("title")) {
            Intent intent = getIntent();

            Bundle bundle = getIntent().getExtras();
            TITLE=bundle.getString("title");
            SchemeId=bundle.getString("schemid");

          //  Log.i("schemid",SchemeId+""+userId);
            mAmount=bundle.getString("amount");
            mStardate=bundle.getString("startdate");
            mEnddate=bundle.getString("enddate");
            mDuration=bundle.getString("duration");
            mSchemeName=bundle.getString("schemname");
            mSchemeType=bundle.getString("schemetype");
        }else {
            Intent intent = getIntent();

            Bundle bundle = getIntent().getExtras();
            TITLE=bundle.getString("title");
            mAmount=bundle.getString("amount");
            EntryDate=bundle.getString("entrydate");
            updateid=bundle.getString("schemeid");



        }


        startpayment = (Button) findViewById(R.id.startpayment);
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
                    Toast.makeText(RazorpayActivity.this, "Amount is empty", Toast.LENGTH_LONG).show();
                }else {
                    startPayment();
                }
            }
        });
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;


        final Checkout co = new Checkout();
        co.setKeyID(Bankapi);

        try {
            JSONObject options = new JSONObject();
            options.put("name", Username);
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            String payment = orderamount.getText().toString();
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", mEmail);
            preFill.put("contact", mPhone);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // payment successfull pay_DGU19rDsInjcF2
        Log.e(TAG, " payment successfull "+ s.toString());
      //  Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();

        try {

            if (mSchemeType.equals("1")) {
                loadScheme(s);
            } else if(mSchemeType.equals("2") ){
                loadGramScheme(s);
               // loadScheme(s);;
            }else
            {
                loadCustomizeScheme(s);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





    }

    private void loadCustomizeScheme(String s) {
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getJoinCustomizeScheme(userId,SchemeId,mSchemeName,mStardate,mEnddate,mDuration,mAmount,s,"success",branchid);
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
                    mInfo= Responseinfodialog.alertshow(RazorpayActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();

            }
        });

    }

    private void loadGramScheme(String s) {
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getJoinGramScheme(userId,SchemeId,mSchemeName,mStardate,mEnddate,mDuration,mAmount,s,"success",branchid);
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
                    mInfo= Responseinfodialog.alertshow(RazorpayActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();

            }
        });





    }

    /*private void loadExistingscheme(String s) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getPaymentSchemeInfo(userId,updateid,EntryDate,mAmount,s,"1");
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
                    mInfo= Responseinfodialog.alertshow(RazorpayActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });

    }*/

    private void loadScheme(String s) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=service.getJoinSchemeStatus(userId,SchemeId,mSchemeName,mStardate,mEnddate,mDuration,mAmount,s,"success",branchid);
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
                    mInfo= Responseinfodialog.alertshow(RazorpayActivity.this, "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();

            }
        });






    }

    private void loadData(List<DataStatus> body) {
       String s=body.get(0).getmStatus();
        String data=body.get(0).getmData();
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }

    }
}
