/*
package com.techface.gold.scheme.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.techface.gold.scheme.R;
import com.techface.gold.scheme.model.ProductList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SugarQuantityAdapter extends RecyclerView.Adapter<SugarQuantityAdapter.MyViewHolder> {

 private Context mContext;
 private List<ProductList.product>mList;
 private SugarQuantityAdapter.IMethodCaller mListener;

    public SugarQuantityAdapter(UpdatedListActivity updatedListActivity, List<ProductList.product> mList,IMethodCaller mListener) {

        this.mContext=updatedListActivity;
        this.mList=mList;
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_sugarquantity_layout,
                viewGroup, false);
        return  new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.mProductName.setText(mList.get(i).getmItemName()+" ("+mList.get(i).getmProductId()+")");
       // myViewHolder.mProductId.setText(mList.get(i).getmProductId());
        myViewHolder.mQuantityCount.setText(mList.get(i).getmQuantity());
        myViewHolder.mProductAmount.setText(mList.get(i).getmItemRate());
        myViewHolder.mTotalAmount.setText(String.valueOf("TOTAL: ₹"+mList.get(i).getmTotal()));
        myViewHolder.mDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mquantity=Integer.parseInt(mList.get(i).getmQuantity());
                mquantity-=1;
                if(mquantity>0) {
                    mList.get(i).setmQuantity(String.valueOf(mquantity));
                    int total =Integer.parseInt(mList.get(i).getmItemRate());
                    total=total*mquantity;
                    mList.get(i).setmTotal(total);
                    myViewHolder.mTotalAmount.setText(" TOTAL: ₹"+String.valueOf(mList.get(i).getmTotal()));
                    notifyItemChanged(i);
                    mListener.grandList(mList);
                }else {
                    mList.get(i).setmQuantity(String.valueOf(1));
                    int total =Integer.parseInt(mList.get(i).getmItemRate());

                    mList.get(i).setmTotal(total);
                    myViewHolder.mTotalAmount.setText(" TOTAL: ₹"+String.valueOf(mList.get(i).getmTotal()));
                    notifyItemChanged(i);
                    mListener.grandList(mList);
                }
                myViewHolder.mTotalAmount.setText(" TOTAL: ₹"+String.valueOf(mList.get(i).getmTotal()));
            }
        });
        myViewHolder.mIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mquantity=Integer.parseInt(mList.get(i).getmQuantity());
                mquantity+=1;
                mList.get(i).setmQuantity(String.valueOf(mquantity));
                int total =Integer.parseInt(mList.get(i).getmItemRate());
                total=total*mquantity;
                mList.get(i).setmTotal(total);
                myViewHolder.mTotalAmount.setText(" TOTAL: ₹"+String.valueOf(mList.get(i).getmTotal()));
                notifyItemChanged(i);
                mListener.grandList(mList);

            }
        });

        myViewHolder.mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(i);
                notifyDataSetChanged();
                mListener.grandList(mList);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.mProductName)
        TextView mProductName;

        @BindView(R.id.mDeleteItem)
        ImageButton mDeleteItem;

//        @BindView(R.id.mProductId)
//        TextView mProductId;

        @BindView(R.id.mDecrease)
        ImageButton mDecrease;

        @BindView(R.id.mInteger_number)
        TextView mQuantityCount;

        @BindView(R.id.mIncrease)
        ImageButton mIncrease;

        @BindView(R.id.mProductAmount)
        TextView mProductAmount;

        @BindView(R.id.mTotalAmount)
        TextView mTotalAmount;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);




        }
    }

    public interface IMethodCaller {
        void grandList(List<ProductList.product> items);
    }
}
*/
