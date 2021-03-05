package com.techface.srimurugapa.scheme.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyReport extends AppCompatActivity {
    private static final String TAG=DailyReport.class.getSimpleName();


    static EditText mStartDate;

    static EditText mEndDate;
    @BindView(R.id.rv_online_Reportrecyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.Btn_online_Rechargehistory_Btn)
    Button mget;

    @BindView(R.id.Tv_EmptyListonline)
    TextView mEmpty;


    private static Calendar myCalender;
    private static Calendar myCalender1;

    private static DatePickerDialog.OnDateSetListener dateSetListener;
    private static DatePickerDialog.OnDateSetListener startdateSetListener;

    private String Startdate="";
    private String EndDate="";

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        ButterKnife.bind(this);
        mStartDate = (EditText)findViewById(R.id.et_online_startdate);
        mEndDate=(EditText)findViewById(R.id.et_online_enddate);
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
        EndDate = df.format(c.getTime());
      //  c.add(Calendar.DATE, -10);  // number of days to add
        Startdate = df.format(c.getTime());


        Log.i(TAG,Startdate+EndDate);

        myCalender = Calendar.getInstance();
        myCalender1 = Calendar.getInstance();
        progressDialog= CommonUtils.getProgressDialog(DailyReport.this);

        //loadHistoryList(Startdate,EndDate);

        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragment dFragment = new StartDatePickerFragment();
                dFragment.show(getSupportFragmentManager(), "Start Date Picker");

            }
        });

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // DialogFragment dFragment = new EndDatePickerFragment();
            //    dFragment.show(getSupportFragmentManager(), "EndDate Picker");

            }
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };


        startdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalender1.set(Calendar.YEAR, year);
                myCalender1.set(Calendar.MONTH, month);
                myCalender1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };


        mget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Startdate=mStartDate.getText().toString();
                EndDate=mEndDate.getText().toString();

              //  loadHistoryList(Startdate,EndDate);

            }
        });
    }
    public static class StartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int year = myCalender1.get(Calendar.YEAR);
            int month = myCalender1.get(Calendar.MONTH);
            int day = myCalender1.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(getContext(),
                    android.R.style.Theme_Holo_Light_Dialog
                    , startdateSetListener, year, month, day);
            dpd.getDatePicker();
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() );
           // dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            dpd.getDatePicker().setSpinnersShown(true);
            dpd.getDatePicker().setCalendarViewShown(true);

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

    /*public static class EndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int year = myCalender.get(Calendar.YEAR);
            int month = myCalender.get(Calendar.MONTH);
            int day = myCalender.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    android.R.style.Theme_Holo_Light_Dialog
                    , dateSetListener, year, month, day);
            dpd.getDatePicker();
            //dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            dpd.getDatePicker().setSpinnersShown(true);
            dpd.getDatePicker().setCalendarViewShown(false);

            return dpd;
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            myCalender.set(Calendar.YEAR, year);
            myCalender.set(Calendar.MONTH, month);
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        }
    }*/



    private static void updateDateLabel() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mStartDate.setText(sdf.format(myCalender1.getTime()));
    }

    private static void updateLabel() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mEndDate.setText(sdf.format(myCalender.getTime()));
    }

}
