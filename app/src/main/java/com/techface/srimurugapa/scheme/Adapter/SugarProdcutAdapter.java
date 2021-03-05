package com.techface.srimurugapa.scheme.Adapter;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ProductList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SugarProdcutAdapter extends RecyclerView.Adapter<SugarProdcutAdapter.ViewHolder>
{
    private static final String TAG=SugarProdcutAdapter.class.getSimpleName();
    private Context mContext;
    private  int mquantity;
    private List<ProductList>mProductList;
    private List<ProductList.product>mProductValue;
    private  SugarProdcutAdapter.getQuantity mListener;




    public SugarProdcutAdapter(Context applicationContext, List<ProductList> mProductList, List<ProductList.product> mProductValue,getQuantity getQuantity) {

        this.mContext=applicationContext;
        this.mProductList=mProductList;
        this.mListener=getQuantity;
        this.mProductValue=mProductValue;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_sugar_layout,
                viewGroup, false);



        return  new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Log.i(TAG,String.valueOf(i));
        viewHolder.mItemName.setText(mProductValue.get(i).getmItemName());
        viewHolder.mItemrate.setText(mProductValue.get(i).getmItemRate());
        viewHolder.mItemQuantity.setText(mProductValue.get(i).getmQuantity());
        viewHolder.mVolume.setText(mProductValue.get(i).getmVolume());
       // Glide.with(mContext).load(mProductValue.get(i).getmImageUrl()).into(viewHolder.mImageView);

        if(i==0)
        {
           // viewHolder.mImageView.setImageResource(R.drawable.mljusice);
        }
        else if(i==1)
        {
      //      viewHolder.mImageView.setImageResource(R.drawable.imageedit);
        }
        else {
        //    viewHolder.mImageView.setImageResource(R.drawable.litre);
        }
        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                QuantityDialogFragment dialogue = QuantityDialogFragment.newInstance(mProductValue.get(i).getmProductId(),mProductName,mItemRate,quantity);
//                dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
//                dialogue.show(((HomeActivity) this).getSupportFragmentManager(), "fragment_new_package");
               mListener.getQuantityData(mProductValue.get(i).getmProductId(),mProductValue.get(i).getmItemName(),mProductValue.get(i).getmItemRate(),mProductValue.get(i).getmQuantity(),mProductValue.get(i).getmTotal());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mProductValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.itemname)
    TextView mItemName;

    @BindView(R.id.itemrate)
    TextView mItemrate;

    @BindView(R.id.itemquantity)
    TextView mItemQuantity;


    @BindView(R.id.img)
    ImageView mImageView;

    @BindView(R.id.mVolume)
    TextView mVolume;


    @BindView(R.id.card_view)
    CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public interface getQuantity{
        void getQuantityData(String mId, String mItemName, String mItemRate, String mQuantity,int mTotal);
    }
}

