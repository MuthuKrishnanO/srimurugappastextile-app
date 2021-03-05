package com.techface.srimurugapa.scheme.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.techface.srimurugapa.scheme.DialogueFragment.ForgetPasswordDialogFragment;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.ConnectivityReceiver;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.LoginResponse;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener,ActivityCompat.OnRequestPermissionsResultCallback,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private final static String TAG=LoginActivity.class.getSimpleName();
    private EditText etUsername;
    private TextView mAndroidId;
    private  View mMainLayout;
    private Button mLogin;
    private TextView mSignup;
    private ConnectivityReceiver mReceiver;
    private TextView mForget ;
    private CheckBox rem_userpass;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    public static final int READ_PHONE_REQUEST_CODE = 1112;
    String userName;
    String passWord;
    private ProgressDialog mProgressdialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private AlertDialog mInfo;
    String firbase_reg_id;
    private  String countrycode="+91";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String packageName = getApplicationContext().getPackageName();
        System.out.println(" PACKAGE NAME ====>  " + packageName);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        firbase_reg_id= FirebaseInstanceId.getInstance().getToken();
        mMainLayout=(View)findViewById(R.id.main_layout);
        mProgressdialog = CommonUtils.getProgressDialog(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        etUsername = (EditText)findViewById(R.id.user_name_et);

        mAndroidId=(TextView)findViewById(R.id.tv_android_id);
        rem_userpass = (CheckBox)findViewById(R.id.show_hide_password);
        mSignup=(TextView)findViewById(R.id.signup);
        mLogin=(Button)findViewById(R.id.btn_login);
        mForget=(TextView)findViewById(R.id.forgetpassword);
        // checkRunTimePermissions();
        mReceiver = new ConnectivityReceiver();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getString(Constants.UserId, "");



        if(!userId.isEmpty())
        {

           /* ApiClient client = ServiceGenerator.createService(ApiClient.class);
            Call<List<LoginResponse>> call = client.getLoginDetailsActiveStatus(userId);
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
            });*/



            Intent intent=new Intent(LoginActivity.this,HomeMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        etUsername.setText(sharedPreferences.getString(KEY_USERNAME,""));
       // etPass.setText(sharedPreferences.getString(KEY_PASS,""));

        etUsername.addTextChangedListener(this);
     //   etPass.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadForgetPassword();
            }
        });


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkConnectivity()) {
                    userName = etUsername.getText().toString().trim();
                  //  passWord = etPass.getText().toString().trim();

                    if (userName.equals("") || userName.length() < 10) {
                        showToast("Enter Your  Register Mobile Number");
                    } else {
                     //   checkLogin(userName);
                        Intent intent=new Intent(LoginActivity.this,LoginOtpVerification.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        intent.putExtra("phoneNumber",countrycode+userName);
                        intent.putExtra("withoutcontrycode",  userName);
                        startActivity(intent);
                    }



                } else {
                    Toast.makeText(LoginActivity.this, "Check Your Internet !!!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AgentLoginActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void loadForgetPassword() {


        ForgetPasswordDialogFragment dialogue = ForgetPasswordDialogFragment.newInstance("","");
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);






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
        String mUserName=LoginAllResponse.get(0).getmName();
        String mEmail=LoginAllResponse.get(0).getEmail();
        String mPhone=LoginAllResponse.get(0).getmPhone();
        String mBranchId=LoginAllResponse.get(0).getmBranchId();
        String mBranch_name=LoginAllResponse.get(0).getmBranchName();
        String mAddress=LoginAllResponse.get(0).getmAddress();
        String mCity=LoginAllResponse.get(0).getmCity();
        String mState=LoginAllResponse.get(0).getmState();
        String mPincode=LoginAllResponse.get(0).getmPincode();
        String mCountry=LoginAllResponse.get(0).getmCountry();
        String mDob=LoginAllResponse.get(0).getmDob();
        String mProofType=LoginAllResponse.get(0).getmProof();
        String mProofNumber=LoginAllResponse.get(0).getmProofNumber();
        String mNominee=LoginAllResponse.get(0).getmNominee();
        String mNomineeRelation=LoginAllResponse.get(0).getmNomineeRelation();
        String panno=LoginAllResponse.get(0).getmPanno();
        String bankapi=LoginAllResponse.get(0).getmBankapi();
        String mobilenumber=LoginAllResponse.get(0).getmMobilenumber();
        String date=LoginAllResponse.get(0).getDate();
        String gold_22k=LoginAllResponse.get(0).getGold_22k();
        String gold_24k=LoginAllResponse.get(0).getGold_24k();
        String branch_address=LoginAllResponse.get(0).getBranch_address();
        String aboutus=LoginAllResponse.get(0).getAboutus();


        if (mStatus==1) {
            showToast(mStatusData);
            saveToPreferences(mUser_id,mUserName,mEmail,mPhone,mBranchId,mBranch_name,mAddress,mCity,mState,mPincode,mCountry,mDob,mProofType,mProofNumber,mNominee,panno,bankapi,mobilenumber,date,gold_22k,gold_24k,branch_address,aboutus,mNomineeRelation);



        } else {


            showToast(mStatusData);


        }


    }

    private void saveToPreferences(String mUser_id, String mUserName, String mEmail, String mPhone, String mBranchId,String mBranchName,String mAddress,String mCity,String mState,String mPincode,String mCountry,String mDOb,String mProoftype,String mProffnumber,String mNominee,String panno,String bankapi,String mobilenumber,String date,String gold_22k,String gold_24k,String Branchaddress,String aboutus,String mNomineeRelation) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();

        editor.putString(Constants.UserId, mUser_id);
        editor.putString(Constants.USERNAME, mUserName);
        editor.putString(Constants.EMAIL,mEmail);
        editor.putString(Constants.PHONE,mPhone);
        editor.putString(Constants.BRANCHID,mBranchId);
        editor.putString(Constants.BRANCHNAME,mBranchName);
        editor.putString(Constants.ADDRESS,mAddress);
        editor.putString(Constants.CITY,mCity);
        editor.putString(Constants.STATE,mState);
        editor.putString(Constants.PINCODE,mPincode);
        editor.putString(Constants.COUNTRY,mCountry);
        editor.putString(Constants.DOB,mDOb);
        editor.putString(Constants.PROOFTYPE,mProoftype);
        editor.putString(Constants.PROOFNUMBER,mProffnumber);
        editor.putString(Constants.NOMINEE,mNominee);
        editor.putString(Constants.NOMINEERELATION,mNomineeRelation);
        editor.putString(Constants.PANNO,panno);
        editor.putString(Constants.BANKAPI,bankapi);
        editor.putString(Constants.MOBILENUMBER,mobilenumber);
        editor.putString(Constants.DATE,date);
        editor.putString(Constants.GOLD_22k,gold_22k);
        editor.putString(Constants.GOLD_24k,gold_24k);
        editor.putString(Constants.BRANCHADDRESS,Branchaddress);
        editor.putString(Constants.ABOUTUS,aboutus);

        editor.apply();



    }


    private void checkRunTimePermissions() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            //  saveAndroidId();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {

            Snackbar.make(mMainLayout, "Device Id Access is Required !!",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            READ_PHONE_REQUEST_CODE);
                }
            }).show();
        } else {
            /*initial user permission grant*/
            Snackbar.make(mMainLayout, "Device Id Access is Required !!",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == READ_PHONE_REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //    saveAndroidId();

            } else {

                Snackbar.make(mMainLayout, "Device Id Permission is Denied!!",
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    private void showToast(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, etUsername.getText().toString().trim());
           // editor.putString(KEY_PASS, etPass.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
           // editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }


    private boolean checkConnectivity() {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {

    }



}
