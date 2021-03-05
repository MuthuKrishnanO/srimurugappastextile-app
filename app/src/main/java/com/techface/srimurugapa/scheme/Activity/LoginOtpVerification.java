package com.techface.srimurugapa.scheme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.model.LoginResponse;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOtpVerification extends AppCompatActivity {
    private final static String TAG=VerifyPhoneActivity.class.getSimpleName();
    private ProgressDialog mProgressdialog;
    private String verificationId;
    private FirebaseAuth mAuth;

    String phoneNumber;
    ProgressBar progressBar;
    EditText editText;
    AppCompatButton buttonSignIn,sentotp;
    private AlertDialog mInfo;
    private List<DataStatus> mDatastaus;
    private  String withoutcountrycode;
    String firbase_reg_id;
    TextView mCount;
    public int counter=60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp_verification);
        mProgressdialog = CommonUtils.getProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        firbase_reg_id= FirebaseInstanceId.getInstance().getToken();
        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);
        buttonSignIn = findViewById(R.id.buttonSignIn);


        mCount=findViewById(R.id.counttime);


        phoneNumber = getIntent().getStringExtra("phoneNumber");
        withoutcountrycode=getIntent().getStringExtra("withoutcontrycode");
        sendVerificationCode(phoneNumber);






        Log.i("ehdhdh",phoneNumber+"++"+withoutcountrycode);

        // save phone number
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phoneNumber", phoneNumber);
        editor.apply();

        new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCount.setText(String.valueOf(counter));
                counter--;
            }
            @Override
            public void onFinish() {
                mCount.setText("0");
            }
        }.start();


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //  loadData();
                            checkLogin(withoutcountrycode);

                            //Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            //startActivity(intent);

                        } else {
                            Toast.makeText(LoginOtpVerification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void checkLogin(String userName) {
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());
        String androidId = sharedPreferences.getString(Constants.ANDROID_ID, "");
        String deviceId = sharedPreferences.getString(Constants.DEVICE_ID, "");
        System.out.println(androidId);
        System.out.println(deviceId);


        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<LoginResponse>> call = client.getLoginDetails(userName,firbase_reg_id);
        mProgressdialog.show();
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode: " + response.code());
                if (response.body() != null) {

                    checkStatus( response.body());
                    // getplan(response.body());
                } else {
                    Log.i(TAG, "Response is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getApplicationContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getApplicationContext(), "CONNECTION FAILURE", Color.RED);
                    //    mInfo.show();
                }
                t.printStackTrace();
            }
        });



    }

    private void checkStatus(List<LoginResponse> responses) {


        List<LoginResponse> LoginAllResponse= new ArrayList<>();
        LoginAllResponse = responses;
        int mStatus = Integer.parseInt(LoginAllResponse.get(0).getmStatus());
        String mStatusData=LoginAllResponse.get(0).getmSatusData();
        Log.i("status",String.valueOf(mStatus));
        String mUser_id=LoginAllResponse.get(0).getmUserId();
        String mAgentid=LoginAllResponse.get(0).getmAgentID();
        String mUserName=LoginAllResponse.get(0).getmName();
        String agentname=LoginAllResponse.get(0).getmAgentName();
        String agentpassword=LoginAllResponse.get(0).getmAgentPassword();



        if (mStatus==1) {
            showToast(mStatusData);
            saveToPreferences(mUser_id,mAgentid,mUserName,agentname,agentpassword);
            Intent intent=new Intent(LoginOtpVerification.this,HomeMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);


        } else {


            showToast(mStatusData);
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over

                    finish();
                }
            }, 1000);



        }


    }

    private void saveToPreferences(String mUser_id,String mAgentid,String username,String agentname,String agentpassword) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();

        editor.putString(Constants.UserId, mUser_id);
        editor.putString(Constants.AGENTID,mAgentid);
        editor.putString(Constants.USERNAME,username);
        editor.putString(Constants.AGENTNAME,agentname);
        editor.putString(Constants.AGENTPASSWORD,agentpassword);


        editor.apply();



    }


    private void showToast(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void sendVerificationCode(String phoneNumber) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

        progressBar.setVisibility(View.GONE);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginOtpVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };
}