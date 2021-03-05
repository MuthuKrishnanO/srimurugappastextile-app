package com.techface.srimurugapa.scheme.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ExitSchemeDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExitgramSchemeAdapter extends RecyclerView.Adapter<ExitgramSchemeAdapter.MyViewHolder>  {

    private static final String TAG=ExitgramSchemeAdapter.class.getSimpleName();
    private Context mContext;
    private List<ExitSchemeDetails> mExitSchemeList;
    private  ExitgramSchemeAdapter.getExitScheme mListener;
    private  String schemetype;

    public ExitgramSchemeAdapter(Context context, List<ExitSchemeDetails> mExitScheme,String s, getExitScheme getExitScheme) {
        this.mContext=context;
        this.mExitSchemeList=mExitScheme;
        mListener=getExitScheme;
        this.schemetype=s;



    }


    @NonNull
    @Override
    public ExitgramSchemeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapterexitinggramscheme,
                parent, false);



        return  new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExitgramSchemeAdapter.MyViewHolder holder, int position) {

        holder.mSchemename.setText(mExitSchemeList.get(position).getmScheme_name()+"(RS ."+mExitSchemeList.get(position).getScheme_amount()+"/-)");

        Log.i("Schemetype",schemetype);
        String outputPattern = "dd-MMM-yyyy";
        String inputPattern = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        Date date1=null;
        String str = null;
        String str1=null;
        try {
            date = inputFormat.parse(mExitSchemeList.get(position).getmScheme_startdate());
            str = outputFormat.format(date);
            date1=inputFormat.parse(mExitSchemeList.get(position).getmScheme_enddate());
            str1=outputFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mstartdate.setText(str);
        holder.menddate.setText(str1);
        holder.mduration.setText(mExitSchemeList.get(position).getmSchemecount()+"/"+mExitSchemeList.get(position).getmScheme_duration());
       holder.mPay.setText(mExitSchemeList.get(position).getmGold_22k());
        if(mExitSchemeList.get(position).getIsactive().equals("1")) {
            holder.mPay.setEnabled(true);
        }
        else if (mExitSchemeList.get(position).getIsactive().equals("2")){
            holder.mPay.setEnabled(false);
            holder.mClaim.setVisibility(View.VISIBLE);
            holder.mPay.setVisibility(View.GONE);
        }
        else {
            holder.mPay.setEnabled(false);
            holder.mClaim.setVisibility(View.VISIBLE);
            holder.mClaim.setText("purchased");
            holder.mClaim.setTextSize(10);
            holder.mPay.setVisibility(View.GONE);
        }


        holder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.getExitgramSchemeData(mExitSchemeList.get(position).getmUserSchemeId(), mExitSchemeList.get(position).getmScheme_name());

            }
        });

        holder.mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.getgramPayment(mExitSchemeList.get(position).getmUserSchemeId(), mExitSchemeList.get(position).getMusers_id(), mExitSchemeList.get(position).getScheme_amount(), mExitSchemeList.get(position).getmScheme_duration(), mExitSchemeList.get(position).getmSchemecount());

            }
        });


    }

    @Override
    public int getItemCount() {
        return mExitSchemeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mSchemeName)
        TextView mSchemename;

        @BindView(R.id.stardate)
        TextView mstartdate;


        @BindView(R.id.endate)
        TextView menddate;

        @BindView(R.id.smartCard_active_details)
        ImageView mInfo;


        @BindView(R.id.pay)
        Button mPay;

        @BindView(R.id.claim)
        Button mClaim;

        @BindView(R.id.duration)
        TextView mduration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
    public interface getExitScheme{
        void getExitgramSchemeData(String mId, String mSchemeName);
        void getgramPayment(String schemeid,String UserId,String amount,String duraion,String count);
    }
}
