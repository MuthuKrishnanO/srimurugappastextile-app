package com.techface.srimurugapa.scheme.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.Helper.SearchableSpinner;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.BranchDetails;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private final static String TAG=RegisterActivity.class.getSimpleName();


    @BindView(R.id.user_mbnumber)
    EditText mMbnumber;










    @BindView(R.id.mRegister)
    Button mRegButton;
    private AlertDialog mInfo;
    private ProgressDialog mProgressdialog;
    private ArrayList<String> mSpinnerlist;
    private ArrayList<String> mBranchid = new ArrayList<>();
    private ArrayList<String> mAdminId = new ArrayList<>();
    private String branchid;
    private String adminid;
    private  List<DataStatus>mSingupData;
    String username;
    String email;
    String password;
    String mobilenumber;
    String livemobile;
    String firbase_reg_id;


    private  String countrycode="+91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mProgressdialog = CommonUtils.getProgressDialog(this);
        Log.d("FCMToken", "token "+ FirebaseInstanceId.getInstance().getToken());

        firbase_reg_id= FirebaseInstanceId.getInstance().getToken();
        Log.d("FCMToken", "token "+ FirebaseInstanceId.getInstance().getToken());



       mRegButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                 mobilenumber=mMbnumber.getText().toString();



               if (mobilenumber.isEmpty() || mobilenumber.length() < 10) {
                   mMbnumber.setError("Valid number is required");
                   mMbnumber.requestFocus();
                   return;
               }



             //  Log.i(TAG,"Branchid"+text+","+branchid);

               livemobile=countrycode+mobilenumber;

               loadData(username,email,password,mobilenumber,branchid);

               /*Intent intent=new Intent(RegisterActivity.this,VerifyPhoneActivity.class);
               intent.putExtra("name",username);
               intent.putExtra("email",email);
               intent.putExtra("phoneNumber",livemobile);
               intent.putExtra("pass",password);
               intent.putExtra("branchid",branchid);
               startActivity(intent);*/
           }
       });

    }

    private void loadData(String username, String email, String password, String mobilenumber,String branchid) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>>call=client.getSingnUpStatus(username,email,password,mobilenumber,branchid,firbase_reg_id);
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
                if(getApplicationContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getApplicationContext(), "CONNECTION FAILURE", Color.RED);
                    //    mInfo.show();
                }
                t.printStackTrace();
            }
        });
    }

    private void checkStatus(List<DataStatus> body) {
        mSingupData=body;
        int status=Integer.parseInt(mSingupData.get(0).getmStatus());
        String data=mSingupData.get(0).getmData();

        if(status==1)
        {
            Intent intent=new Intent(RegisterActivity.this,VerifyPhoneActivity.class);
            intent.putExtra("name",username);
            intent.putExtra("email",email);
            intent.putExtra("phoneNumber",livemobile);
            intent.putExtra("withoutcontrycode",mobilenumber);
            intent.putExtra("pass",password);
            intent.putExtra("branchid",branchid);
            startActivity(intent);

        }
        else {

            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();


        }




    }


    }






