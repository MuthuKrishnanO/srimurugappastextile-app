package com.techface.srimurugapa.scheme.DialogueFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.techface.srimurugapa.scheme.Activity.paytmActivity;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Use the {@link JoinSchemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinSchemeFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5="param5";
    private static final String ARG_PARAM6="param6";
    // TODO: Rename and change types of parameters
    private String schemeid ;
    private String mSName;
    private  String mSAmount;
    private  String mSDuration;
    private  String mSchemetype;
    private  String mSchemevalue;
    private  String branch_Id;
    private  String branch_Name;
    private static DatePickerDialog.OnDateSetListener startdateSetListener;
    private static Calendar myCalender1;

    private static Calendar calendar;
    @BindView(R.id.branch_name)
    TextView mBranchName;


    @BindView(R.id.schemename)
    TextView mSchemName;

    @BindView(R.id.schemeamount)
    TextView mSchemeAmount;


    @BindView(R.id.schemeduration)
    TextView mSchemDuration;

    @BindView(R.id.et_online_enddate)
    TextView mDateend;

    @BindView(R.id.pay)
    Button mPay;


    @BindView(R.id.gold22)
    LinearLayout mGoldView22;

    @BindView(R.id.gold24)
    LinearLayout mGoldView24;

    @BindView(R.id.txtgold22k)
    TextView gold22ValueView;

    @BindView(R.id.txtgold24k)
    TextView gold24ValueView;

    private String userid;

    static  EditText mStartDate;
    static  String datevalue;
    static String  endvalue;
    private String goldvalue22k;
    private String goldvalue24k;


    @BindView(R.id.btn_cancel_activate)
    Button mCancel;
    private AlertDialog mInfo;
    private ProgressDialog mProgressdialog;
    private static DatePickerDialog.OnDateSetListener dateSetListener;


    public JoinSchemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinSchemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinSchemeFragment newInstance(String param1, String param2,String param3,String param4,String param5,String  param6) {
        JoinSchemeFragment fragment = new JoinSchemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5,param5);
        args.putString(ARG_PARAM6,param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            schemeid = getArguments().getString(ARG_PARAM1);
            mSName = getArguments().getString(ARG_PARAM2);
            mSAmount=getArguments().getString(ARG_PARAM3);
            mSDuration=getArguments().getString(ARG_PARAM4);
            mSchemetype=getArguments().getString(ARG_PARAM5);
            mSchemevalue=getArguments().getString(ARG_PARAM6);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_join_scheme, container, false);
        ButterKnife.bind(this,rootView);
        mStartDate=(EditText)rootView.findViewById(R.id.et_online_startdate);
        mProgressdialog = CommonUtils.getProgressDialog(getContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userid=sharedPreferences.getString (Constants.UserId,"");
        branch_Id = sharedPreferences.getString(Constants.BRANCHID, "");
        branch_Name=sharedPreferences.getString(Constants.BRANCHNAME, "");
        goldvalue22k=sharedPreferences.getString(Constants.GOLD_22k,"");
        goldvalue24k=sharedPreferences.getString(Constants.GOLD_24k,"");

        if(mSchemetype.equals("1"))
        {
            mGoldView22.setVisibility(View.GONE);
            mGoldView24.setVisibility(View.GONE);

        }
        else
        {
            mGoldView22.setVisibility(View.VISIBLE);
            mGoldView24.setVisibility(View.VISIBLE);
            if( Double.parseDouble(goldvalue22k)> 0){

                Log.i("goldamount",mSAmount+"---"+goldvalue22k);
                float gold221= (float) 0.0;
                float  gold241=(float)0.0;
                gold221 = Float.parseFloat(goldvalue22k)/1000;
                gold241 = Float.parseFloat(goldvalue24k)/1000;
                float  gold22amount= Float.parseFloat(mSAmount)/gold221;
                float  gold24amount= Float.parseFloat(mSAmount)/gold241;

                float gg22k=gold22amount/1000;
                float gg24k=gold24amount/1000;
                Log.i("goldamount1",mSAmount+"---"+goldvalue22k+"--"+String.valueOf(gold221)+"--"+gold22amount);
                String g22k=String.format("%.3f", gg22k);
                String g24k=String.format("%.3f", gg24k);
                gold22ValueView.setText(g22k+" gram");
                gold24ValueView.setText(g24k+" gram");




            }


            else{
                gold22ValueView.setText("gold rate Not Update ");
                gold24ValueView.setText("gold rate Not Update");
                mPay.setEnabled(false);

            }


        }


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        myCalender1 = Calendar.getInstance();
        calendar = Calendar.getInstance();
        Date today = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        datevalue= format2.format(today);

        String outputPattern = "dd-MMM-yyyy";
        String inputPattern = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        Date date = null;

        String str = null;

        try {
            date = inputFormat.parse(datevalue);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mStartDate.setText(str);
        String mEndDate=datevalue;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf1.parse(mEndDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MONTH, Integer.parseInt(mSDuration));
      //  sdf = new SimpleDateFormat("yyyy-MM-dd");
        String outputPattern1 = "dd-MMM-yyyy";
        String inputPattern1= "yyyy-MM-dd";
        Date resultdate = new Date(c.getTimeInMillis());
        Date date1 = null;
        SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
        SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
        String str1= null;

        date1 = resultdate;
        str1 = outputFormat1.format(date1);

        String dateInString = sdf1.format(resultdate);
        endvalue=dateInString;
        mDateend.setText(str1);

        mBranchName.setText(branch_Name+"("+branch_Id+")");
        mSchemName.setText(mSName);
        mSchemeAmount.setText(mSAmount);
        mSchemDuration.setText(mSDuration+" Month");

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
     /*   mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                DialogFragment dFragment = new JoinSchemeFragment.StartDatePickerFragment();
                dFragment.show(getFragmentManager(), "Start Date Picker");

            }
        });*/

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stardate=mDateend.getText().toString().trim();
                if (stardate.isEmpty()) {
                    mStartDate.setError("Start Date is Required");
                    mStartDate.requestFocus();
                    return;
                }




                loadSchemeCheck();



            }
        });

        return rootView;
    }

    private void loadSchemeCheck() {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call = client.getSchemeCheck(userid,schemeid,mSchemevalue);
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

        if(status.equals("1"))
        {

         AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            builder.setMessage(data)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getDialog().dismiss();
                            Log.i("22222",schemeid);
                            Intent intent=new Intent(getContext(), paytmActivity.class);
                            //Intent intent=new Intent(getContext(), paytmActivity.class);
                            intent.putExtra("title","addnewscheme");
                            intent.putExtra("amount",mSAmount);
                            intent.putExtra("schemid",schemeid);
                            intent.putExtra("startdate",datevalue);
                            intent.putExtra("enddate",endvalue);
                            intent.putExtra("duration",mSDuration);
                            intent.putExtra("schemname",mSName);
                            intent.putExtra("schemetype",mSchemetype);

                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
           AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            builder.setMessage(data)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getDialog().dismiss();
                            Log.i("22222",mSchemetype);
                            Intent intent=new Intent(getContext(), paytmActivity.class);
                            intent.putExtra("title","addnewscheme");
                            intent.putExtra("amount",mSAmount);
                            intent.putExtra("schemid",schemeid);
                            intent.putExtra("startdate",datevalue);
                            intent.putExtra("enddate",endvalue);
                            intent.putExtra("duration",mSDuration);
                            intent.putExtra("schemname",mSName);
                            intent.putExtra("schemetype",mSchemetype);

                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
           AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }


    }

    private void updateenddate() throws ParseException {
        String mEndDate=datevalue;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(mEndDate));
        c.add(Calendar.MONTH, Integer.parseInt(mSDuration));
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String outputPattern = "dd-MMM-yyyy";
        String inputPattern = "yyyy-MM-dd";
        Date resultdate = new Date(c.getTimeInMillis());
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        String str = null;

        date = resultdate;
        str = outputFormat.format(date);

        String dateInString = sdf.format(resultdate);
        endvalue=dateInString;
        mDateend.setText(str);


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
           dpd.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            //dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
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

        String outputPattern = "dd-MMM-yyyy";
        String inputPattern = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        Date date = null;

        String str = null;

        try {
            date = inputFormat.parse(sdf.format(myCalender1.getTime()));
            str = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


       String format1 = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(format1, Locale.US);
       /* mStartDate.setText(sdf.format(myCalender1.getTime()));*/
        datevalue=sdf.format(myCalender1.getTime());
        mStartDate.setText(str);
    }
}