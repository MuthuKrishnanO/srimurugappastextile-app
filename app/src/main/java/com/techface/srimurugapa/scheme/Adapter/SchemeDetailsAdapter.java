package com.techface.srimurugapa.scheme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.SchemeDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchemeDetailsAdapter extends RecyclerView.Adapter<SchemeDetailsAdapter.MyViewHolder> {

    private static final String TAG=SchemeDetailsAdapter.class.getSimpleName();
    private Context mContext;
    private List<SchemeDetails> mSchemeList;
    private  SchemeDetailsAdapter.getOneScheme mListener;
    private String s;

    public SchemeDetailsAdapter(Context context, List<SchemeDetails> mSchemeList,String s, getOneScheme getOneScheme) {
        this.mContext=context;
        this.mSchemeList=mSchemeList;
        mListener=getOneScheme;
        this.s=s;
    }

    @NonNull
    @Override
    public SchemeDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_schemedetaails,
                parent, false);



        return  new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeDetailsAdapter.MyViewHolder holder, int pos) {

       holder.mSchemeName.setText(mSchemeList.get(pos).getmSchemeName());
       if(Integer.parseInt(mSchemeList.get(pos).getmSchemetype())==3)
       {
           holder.mSchemeAmount.setVisibility(View.GONE);
           holder.mAmountLayout.setVisibility(View.INVISIBLE);

       }else {

           holder.mAmountLayout.setVisibility(View.VISIBLE);
       }
        holder.mSchemeAmount.setText("₹."+mSchemeList.get(pos).getmSchemeAmount()+"/- " );
        holder.mDuration.setText("Duration - "+mSchemeList.get(pos).getmSchemeDuration()+"");
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.getSchemeData(mSchemeList.get(pos).getmSchemeId(),mSchemeList.get(pos).getmSchemeName(),mSchemeList.get(pos).getmSchemeAmount(),mSchemeList.get(pos).getmSchemeDuration(),mSchemeList.get(pos).getmSchemetype(),s);
            }
        });
        holder.mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.getSchemeData(mSchemeList.get(pos).getmSchemeId(),mSchemeList.get(pos).getmSchemeName(),mSchemeList.get(pos).getmSchemeAmount(),mSchemeList.get(pos).getmSchemeDuration(),mSchemeList.get(pos).getmSchemetype(),s);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mSchemeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

         @BindView(R.id.mSchemeAmount)
        TextView mSchemeAmount;

         @BindView(R.id.duration)
         TextView mDuration;

        @BindView(R.id.card_view)
        CardView mCardView;

        @BindView(R.id.schemename)
        TextView mSchemeName;


        @BindView(R.id.mButton)
        Button mNewButton;

        @BindView(R.id.amountlayout)
        LinearLayout mAmountLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface getOneScheme{
        void getSchemeData(String mId, String mSchemeName, String mSchemeAmount, String mDuration,String schemetype,String value);
    }
}
