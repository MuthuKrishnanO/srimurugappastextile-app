package com.techface.srimurugapa.scheme.DialogueFragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;


import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.mCancel)
    Button mCancel;

    @BindView(R.id.mrequest)
    Button mRequset;

    @BindView(R.id.user_number)
    EditText mUsernumber;
    @BindView(R.id.password)
    TextView mPassword;

    @BindView(R.id.editnumber)
    EditText mEditnumber;

    @BindView(R.id.buttonSignIn)
    Button mVerfiy;
    ProgressBar progressBar;

    private String verificationId;

    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;
    private FirebaseAuth mAuth;

    public ForgetPasswordDialogFragment() {
        // Required empty public constructor
    }
    String mobilenumber;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordDialogFragment newInstance(String param1, String param2) {
        ForgetPasswordDialogFragment fragment = new ForgetPasswordDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rot=inflater.inflate(R.layout.fragment_forget_password_dialog, container, false);
        ButterKnife.bind(this,rot);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar = rot.findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        mProgressdialog = CommonUtils.getProgressDialog(getContext());


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        mRequset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobilenumber=mUsernumber.getText().toString();
                if (mobilenumber.isEmpty() || mobilenumber.length() < 10) {
                    mUsernumber.setError("Valid number is required");
                    mUsernumber.requestFocus();
                    return;
                }
                sendVerificationCode("+91"+mobilenumber);
                mEditnumber.setVisibility(View.VISIBLE);
                mVerfiy.setVisibility(View.VISIBLE);
            }
        });

        mVerfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mEditnumber.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    mEditnumber.setError("Enter code...");
                    mEditnumber.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });


        return  rot;
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

                            load(mobilenumber);


                            //Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            //startActivity(intent);

                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    private void load(String mobilenumber) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call = client.getForgetPassword(mobilenumber);
        mProgressdialog.show();
        call.enqueue(new Callback<List<DataStatus>>() {
            @Override
            public void onResponse(Call<List<DataStatus>> call, Response<List<DataStatus>> response) {
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
            public void onFailure(Call<List<DataStatus>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });


    }

    private void checkStatus(List<DataStatus> body) {
        String  status=body.get(0).getmStatus();
        String data=body.get(0).getmData();

        if(status.equals("1")) {
            mPassword.setVisibility(View.VISIBLE);
            mPassword.setText("Password is "+ data);
            mEditnumber.setVisibility(View.GONE);
            mVerfiy.setVisibility(View.GONE);
            //  Toast.makeText(getContext(),"Password is"+ data, Toast.LENGTH_SHORT).show();
        }else
        {
            mPassword.setVisibility(View.GONE);
            Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
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
                mEditnumber.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };
}