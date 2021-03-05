package com.techface.srimurugapa.scheme.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


public class ExitSchemeAdapter extends RecyclerView.Adapter<ExitSchemeAdapter.MyViewHolder>  {

    private static final String TAG=ExitSchemeAdapter.class.getSimpleName();
    private Context mContext;
    private List<ExitSchemeDetails> mExitSchemeList;
    private  ExitSchemeAdapter.getExitScheme mListener;
    private  int schemetype;

    public  ExitSchemeAdapter(Context context, List<ExitSchemeDetails> mExitScheme,int s, getExitScheme getExitScheme) {
        this.mContext=context;
        this.mExitSchemeList=mExitScheme;
        mListener=getExitScheme;
        this.schemetype=s;



    }


    @NonNull
    @Override
    public ExitSchemeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapterexitingscheme,
                parent, false);



        return  new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExitSchemeAdapter.MyViewHolder holder, int position) {

        if (schemetype==1) {
        holder.mGoldrate.setVisibility(View.GONE);

        }

        else {
            holder.mGoldrate.setVisibility(View.VISIBLE);
            holder.mgoldk.setText("22k gold - "+mExitSchemeList.get(position).getmGold_22k()+"gram");
           holder.mgold24k.setText("24k gold - "+mExitSchemeList.get(position).getmGold_24k()+"gram");
        }

        holder.mSchemename.setText(mExitSchemeList.get(position).getmScheme_name()+"   (RS ."+mExitSchemeList.get(position).getScheme_amount()+"/-)");




      Log.i("Schemetype",String.valueOf(schemetype));
        String outputPattern = "dd-MMM-yyyy";
        String inputPattern = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        Date date1=null;
        String str = null;
        String str1=null;
       /* try {
            date = inputFormat.parse(mExitSchemeList.get(position).getmScheme_startdate());
            str = outputFormat.format(date);
           date1=inputFormat.parse(mExitSchemeList.get(position).getmScheme_enddate());
            str1=outputFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        holder.mstartdate.setText("JAN");
        holder.menddate.setText("OCT");
        holder.mduration.setText(mExitSchemeList.get(position).getmSchemecount()+"/"+mExitSchemeList.get(position).getmScheme_duration());

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
           holder.mPay.setVisibility(View.GONE);
       }



       holder.mInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (schemetype==1) {
                   mListener.getExitSchemeData(mExitSchemeList.get(position).getmUserSchemeId(), mExitSchemeList.get(position).getmScheme_name(),schemetype);
               }
               else {
                   mListener.getExitSchemeData(mExitSchemeList.get(position).getmUserSchemeId(), mExitSchemeList.get(position).getmScheme_name(),schemetype);
               }
               }
       });

       holder.mPay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                    if(schemetype==1) {
                        mListener.getPayment(mExitSchemeList.get(position).getmUserSchemeId(), mExitSchemeList.get(position).getMusers_id(), mExitSchemeList.get(position).getScheme_amount(), mExitSchemeList.get(position).getmScheme_duration(), mExitSchemeList.get(position).getmSchemecount(),schemetype);
                    }
                    else {
                        mListener.getPayment(mExitSchemeList.get(position).getmUserSchemeId(), mExitSchemeList.get(position).getMusers_id(), mExitSchemeList.get(position).getScheme_amount(), mExitSchemeList.get(position).getmScheme_duration(), mExitSchemeList.get(position).getmSchemecount(),schemetype);

                    }
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

        @BindView(R.id.goldrate)
        LinearLayout mGoldrate;

        @BindView(R.id.mygold22k)
        TextView mgoldk;

        @BindView(R.id.mygold24k)
        TextView mgold24k;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
    public interface getExitScheme{
        void getExitSchemeData(String mId, String mSchemeName,int  schemetype);
        void getPayment(String schemeid,String UserId,String amount,String duraion,String count,int schemetype);
    }
}
