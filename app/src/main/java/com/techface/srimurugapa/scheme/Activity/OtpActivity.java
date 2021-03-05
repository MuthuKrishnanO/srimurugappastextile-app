package com.techface.srimurugapa.scheme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.techface.srimurugapa.scheme.R;



import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    private String verificationId;
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseregid;
    private ProgressBar progressBar;
    private EditText otpeditText;
    private Button btnsignin;
    //MyApi myApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        btnsignin=(Button)findViewById(R.id.buttonsignin);


        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String mobilenumber = intent.getStringExtra("mobileno");
        final String pass = intent.getStringExtra("pass");

        mAuth = FirebaseAuth.getInstance();



        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        otpeditText = (EditText) findViewById(R.id.editTextCode);


        sendVerificationCode(mobilenumber);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String otpcode = otpeditText.getText().toString().trim();
                if (otpcode.isEmpty() || otpcode.length() < 6) {

                    otpeditText.setError("Enter code...");
                    otpeditText.requestFocus();
                    return;
                }
                verifyCode(otpcode);


            }
        });

    }

    private void verifyCode(String otpcode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpcode);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            rgisternewuser();




                        } else {
                            //Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void rgisternewuser() {
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String mobilenumber = "+"+"91"+" Mob. Nom";
        final String pass = intent.getStringExtra("pass");
      /*  myApi= MyClient.getInstance().getMyApi();

        Call<ResponseBody>call=myApi.registernewuser(name,email,mobilenumber,pass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String hi=response.body().string();
                    Toast.makeText(OtpActivity.this, hi, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/
    }

    private void sendVerificationCode(String mobilenumber) {

        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobilenumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

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
            String smscode = phoneAuthCredential.getSmsCode();
            if (smscode != null) {
                otpeditText.setText(smscode);
                verifyCode(smscode);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


}