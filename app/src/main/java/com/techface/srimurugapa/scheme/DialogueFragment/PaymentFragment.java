package com.techface.srimurugapa.scheme.DialogueFragment;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.Activity.PaymentActivity;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ProductList;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentFragment extends BottomSheetDialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG=PaymentFragment.class.getSimpleName();

    @BindView(R.id.txt_total)
    TextView mTotal;

    @BindView(R.id.mCancel)
    TextView mCancel;

    @BindView(R.id.mCash)
    CardView mCash;






    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ProductList.product>mList;


    //private OnFragmentInteractionListener mListener;

    public PaymentFragment() {
        // Required empty public constructor
    }


    public static PaymentFragment newInstance(String param1, List<ProductList.product> param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, (Serializable) param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            mList= (List<ProductList.product>) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView=inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this,RootView);
        mTotal.setText(mParam1);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                        Intent intent=new Intent(getContext(),PaymentActivity.class);
                        intent.putExtra("printerlist", (Serializable) mList);
                        startActivity(intent);
            }
        });
        for(int i=0;i<mList.size();i++)
        {
            Log.i(TAG,mList.get(i).getmProductId());
        }


        return RootView;
    }



   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
