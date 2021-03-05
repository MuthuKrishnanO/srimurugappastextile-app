package com.techface.srimurugapa.scheme.DialogueFragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuantityDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuantityDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuantityDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG=QuantityDialogFragment.class.getSimpleName();


    private static final String ID="mId";
    private static final String PRODUCTNAME="mProductName";
    private static final String ITEMRATE="mItemRate";
    private static final String QUANTITY="mQuantity";
    private static final String TOTAL="mTotal";



    @BindView(R.id.mCancel)
    Button mBtn_Cancel;

    @BindView(R.id.btn_update)
    Button mBtn_Update;

    @BindView(R.id.txt_Amount)
    TextView mTotalAmount;

    @BindView(R.id.decrease)
    ImageButton mDecrease;

    @BindView(R.id.increase)
    ImageButton mIncrease;

    @BindView(R.id.integer_number)
    EditText mQuantityvalue;

    // TODO: Rename and change types of parameters
    private String mId;
    private String mProductName;
    private String mItemRate;
    private String mQuantity;
    private int mTotal;
    private  int minteger = 1;
    int Total;

    private OnFragmentInteractionListener mListener;

    public QuantityDialogFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static QuantityDialogFragment newInstance(String mId, String mProductName, String mItemrate, String mQuantity,int mTotal) {
        QuantityDialogFragment fragment = new QuantityDialogFragment();
        Bundle args = new Bundle();
        args.putString(ID, mId);
        args.putString(PRODUCTNAME,mProductName);
        args.putString(ITEMRATE,mItemrate);
        args.putString(QUANTITY,mQuantity);
        args.putInt(TOTAL,mTotal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ID);
            mProductName = getArguments().getString(PRODUCTNAME);
            mItemRate=getArguments().getString(ITEMRATE);
            mQuantity=getArguments().getString(QUANTITY);
            mTotal=getArguments().getInt(TOTAL);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_quantity_dialog, container, false);
        ButterKnife.bind(this, rootView);

        mTotalAmount.setText(String.valueOf(mTotal));
      minteger=Integer.parseInt(mQuantity);
       mQuantityvalue.setText(String.valueOf(minteger));

        mBtn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mBtn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Update=mTotalAmount.getText().toString();

                mListener.onFragmentInteraction(mId,mProductName,mItemRate,minteger,Integer.parseInt(Update));
                getDialog().dismiss();

            }
        });
        mIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger = minteger + 1;
                mQuantityvalue.setText(String.valueOf(minteger));
            }
        });

        mDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger = minteger - 1;
                if(minteger>1) {
                    mQuantityvalue.setText(String.valueOf(minteger));
                }else {
                    minteger=1;
                    mQuantityvalue.setText(String.valueOf(1));
                }

            }
        });

        mQuantityvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int amount= Integer.parseInt(s.toString());
                mTotal=amount*Integer.parseInt(mItemRate);
                mTotalAmount.setText(String.valueOf(mTotal));

            }
        });
        return rootView;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name


        void onFragmentInteraction(String mId, String mProductName, String mItemRate, int minteger, int update);
    }
}
