package com.techface.srimurugapa.scheme.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyPhoneActivity extends AppCompatActivity {

    private final static String TAG=VerifyPhoneActivity.class.getSimpleName();
    private ProgressDialog mProgressdialog;
    private String verificationId;
    private FirebaseAuth mAuth;
    String phoneNumber;
    ProgressBar progressBar;
    EditText editText;
    AppCompatButton buttonSignIn;
    private AlertDialog mInfo;
    private  List<DataStatus>mDatastaus;
    private  String withoutcountrycode;

    TextView mCount;
    public int counter=60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        mProgressdialog = CommonUtils.getProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        mCount=findViewById(R.id.counttime);


        String username=getIntent().getStringExtra("name");
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("pass");
        String branchid=getIntent().getStringExtra("branchid");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        withoutcountrycode=getIntent().getStringExtra("withoutcontrycode");
        sendVerificationCode(phoneNumber);



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

                          //loadData();


                            //Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            //startActivity(intent);

                        } else {
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void loadData() {


        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call=client.getOTPVerification(withoutcountrycode);
        mProgressdialog.show();
        call.enqueue(new Callback<List<DataStatus>>() {
            @Override
            public void onResponse(Call<List<DataStatus>> call, Response<List<DataStatus>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode: " + response.code());
                if (response.body() != null) {

                    checkStatus( response.body());

                } else {
                    Log.i(TAG, "Response is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<DataStatus>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getApplicationContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getApplicationContext(), "CONNECTION FAILURE", Color.RED);
                    //    mInfo.show();
                }
                t.printStackTrace();

            }
        });



    }

    private void checkStatus(List<DataStatus> body) {
        mDatastaus=body;

        int status=Integer.parseInt(mDatastaus.get(0).getmStatus());
        String Data=mDatastaus.get(0).getmData();

        if(status==1)
        {
            Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);


        }
        else {
            Toast.makeText(getApplicationContext(), Data, Toast.LENGTH_SHORT).show();
        }

    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
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
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };
}