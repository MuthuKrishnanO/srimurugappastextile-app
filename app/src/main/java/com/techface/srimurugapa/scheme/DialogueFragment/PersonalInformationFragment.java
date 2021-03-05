package com.techface.srimurugapa.scheme.DialogueFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.model.LoginResponse;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalInformationFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    @BindView(R.id.state)
    EditText mNewState;

    @BindView(R.id.mCancel)
    Button mCancel;



    @BindView(R.id.mSave)
    Button mSave;

    // @BindView(R.id.dob)
    //  EditText mDOB;
    static  EditText mStartDate;
    private String dateofbirth;
    private static DatePickerDialog.OnDateSetListener startdateSetListener;
    private static Calendar myCalender1;
    @BindView(R.id.address)
    EditText mAddressText;

    private String address;

    @BindView(R.id.city)
    EditText mCity;





    private String city;



    private String panno;

    @BindView(R.id.nominee)
    EditText mNomineeRelationNew;

    @BindView(R.id.proofnumber)
    EditText mProofnumber;

    @BindView(R.id.nomineename)
    EditText mNomineeName;

    @BindView(R.id.user_name)
    EditText mUsernNewName;

    @BindView(R.id.email)
    EditText mEmailText;



    private String nominee;
    private String nomineerelation;

    private String prooftype;
    private String Proofnumber;
    private String username;
    String item;
    private  String UserId;
    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;
    private  List<DataStatus>mStatus;
    public PersonalInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalInformationFragment newInstance(String param1, String param2) {
        PersonalInformationFragment fragment = new PersonalInformationFragment();
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
        View rootView=inflater.inflate(R.layout.fragment_personal_information, container, false);

        ButterKnife.bind(this,rootView);

        mStartDate=(EditText)rootView.findViewById(R.id.dob);
        myCalender1 = Calendar.getInstance();
        mProgressdialog = CommonUtils.getProgressDialog(getContext());

        //loadPersonaljson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        UserId=sharedPreferences.getString(Constants.UserId,"");
        loadPersonaljson();



       /* if(dateofbirth.equals("0000-00-00"))
        {
            mStartDate.setHint("dd/mm/yyyy");
        }else
        {
            mStartDate.setText(dateofbirth);
        }*/



        ;




        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        startdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalender1.set(Calendar.YEAR, year);
                myCalender1.set(Calendar.MONTH, month);
                myCalender1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
                try {
                    updateenddate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        };
        mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                DialogFragment dFragment = new PersonalInformationFragment.StartDatePickerFragment();
                dFragment.show(getFragmentManager(), "Start Date Picker");

            }
        });


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (mStartDate.getText().toString().isEmpty()) {
                    mStartDate.setError("Dob is required");
                    mStartDate.requestFocus();
                    return;
                }
                if(mAddressText.getText().toString().isEmpty())
                {
                    mAddressText.setError("Address is required");
                    mAddressText.requestFocus();
                    return;
                }

                if(mCity.getText().toString().isEmpty())
                {
                    mCity.setError("city is required");
                    mCity.requestFocus();
                    return;
                }

                mStartDate.setEnabled(false);






                loadupdatevalue(mUsernNewName.getText().toString(),mEmailText.getText().toString(),mStartDate.getText().toString(),mAddressText.getText().toString(),mCity.getText().toString(),mNewState.getText().toString(),mProofnumber.getText().toString(),mNomineeName.getText().toString(),mNomineeRelationNew.getText().toString());

            }
        });


        return rootView;
    }

    private void updateenddate() throws ParseException {
        String mEndDate=mStartDate.getText().toString();
        String outputPattern = "dd-MMM-yyyy";
        String inputPattern = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;

        String str = null;

        try {
            date = inputFormat.parse(mEndDate);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mStartDate.setText(str);
    }

    public static class StartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int year = myCalender1.get(Calendar.YEAR);
            int month = myCalender1.get(Calendar.MONTH);
            int day = myCalender1.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(getContext(),
                    R.style.datepicker
                    , startdateSetListener, year, month, day);
            dpd.getDatePicker();
            // dpd.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            dpd.getDatePicker().setSpinnersShown(true);
            dpd.getDatePicker().setCalendarViewShown(false);

            return dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            myCalender1.set(Calendar.YEAR, year);
            myCalender1.set(Calendar.MONTH, month);
            myCalender1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();

        }
    }
    private static void updateDateLabel() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mStartDate.setText(sdf.format(myCalender1.getTime()));
    }

    private void loadPersonaljson() {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<LoginResponse>> call = client.getPersonalJsonNew(UserId);
        mProgressdialog.show();
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode: " + response.code());
                if (response.body() != null) {

                    checkStatus1( response.body());
                    // getplan(response.body());
                } else {
                    Log.i(TAG, "Response is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                    //    mInfo.show();
                }
                t.printStackTrace();
            }
        });

    }

    private void checkStatus1(List<LoginResponse> body) {

        List<LoginResponse> LoginAllResponse= new ArrayList<>();
        LoginAllResponse = body;
        int mStatus = Integer.parseInt(LoginAllResponse.get(0).getmStatus());
        String mStatusData=LoginAllResponse.get(0).getmSatusData();
        Log.i("status",String.valueOf(mStatus));
        String mUser_id=LoginAllResponse.get(0).getmUserId();
        String mUserName=LoginAllResponse.get(0).getmName();
        String mEmail=LoginAllResponse.get(0).getEmail();
        String mPhone=LoginAllResponse.get(0).getmPhone();

        String mAddress=LoginAllResponse.get(0).getmAddress();
        String mCity1=LoginAllResponse.get(0).getmCity();
        String mState=LoginAllResponse.get(0).getmState();
        String mPincode=LoginAllResponse.get(0).getmPincode();

        String mDob1=LoginAllResponse.get(0).getmDob();
        String mProofType=LoginAllResponse.get(0).getmProof();
        String mProofNumber=LoginAllResponse.get(0).getmProofNumber();
        String mNominee=LoginAllResponse.get(0).getmNominee();
        String mNomineeRelation=LoginAllResponse.get(0).getmNomineeRelation();


        mUsernNewName.setText(mUserName);
        mEmailText.setText(mEmail);
        mAddressText.setText(mAddress);
        mCity.setText(mCity1);
        mNewState.setText(mState);
        mStartDate.setText(mDob1);
        mProofnumber.setText(mProofNumber);
        mNomineeName.setText(mNominee);
        mNomineeRelationNew.setText(mNomineeRelation);






    }

    private void loadupdatevalue(String username,String email, String dob, String address, String city,String nestate, String proofnumber, String mnominee,String mNomineeName) {



        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call = client.getUpdatePersonalStatusNew(UserId,username,email,dob,address,city,nestate,proofnumber,mnominee,mNomineeName);
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
        mStatus=body;

/*
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

        //   mAddress.getText().toString(),mCity.getText().toString(),mPanno.getText().toString(),mProofnumber.getText().toString(),mNominee.getText().toString()
    //    editor.putString(Constants.USERNAME,  mUsername.getText().toString());
        //editor.putString(Constants.ADDRESS,  mAddress.getText().toString());
        editor.putString(Constants.CITY,mCity.getText().toString());

        editor.putString(Constants.DOB,mStartDate.getText().toString());
        editor.putString(Constants.PROOFTYPE,item);
        editor.putString(Constants.PROOFNUMBER,mProofnumber.getText().toString());
        editor.putString(Constants.NOMINEE,mNominee.getText().toString());
        editor.putString(Constants.NOMINEERELATION,mNomineeName.getText().toString());



        editor.apply();*/
        Toast.makeText(getContext(), mStatus.get(0).getmData(), Toast.LENGTH_LONG).show();
        getDialog().dismiss();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}