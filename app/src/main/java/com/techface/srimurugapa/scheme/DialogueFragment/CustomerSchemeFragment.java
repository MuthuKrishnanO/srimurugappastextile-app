package com.techface.srimurugapa.scheme.DialogueFragment;



import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerSchemeFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    @BindView(R.id.mCancel)
    Button mCancel;

    @BindView(R.id.mrequest)
    Button mRequset;

    @BindView(R.id.user_number)
    EditText mUsernumber;
    @BindView(R.id.password)
    TextView mPassword;
    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;

    public CustomerSchemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerSchemeFragment newInstance(String param1, String param2) {
        CustomerSchemeFragment fragment = new CustomerSchemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CustomerSchemeFragment newInstance(String mId, String mSchemeName, String mSchemeAmount, String mDuration, String schemetype, String value) {

        CustomerSchemeFragment fragment = new CustomerSchemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mId);
        args.putString(ARG_PARAM2, mSchemeName);
        args.putString(ARG_PARAM3, mSchemeAmount);
        args.putString(ARG_PARAM4, mDuration);
        args.putString(ARG_PARAM5, schemetype);
        args.putString(ARG_PARAM6, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rot=inflater.inflate(R.layout.fragment_customer_scheme, container, false);
        ButterKnife.bind(this,rot);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressdialog = CommonUtils.getProgressDialog(getContext());
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        mRequset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobilenumber=mUsernumber.getText().toString();
                if (mobilenumber.isEmpty() || Integer.parseInt(mobilenumber) < 100) {
                    mUsernumber.setError("Valid number is required");
                    mUsernumber.requestFocus();
                    return;
                }
                load(mobilenumber);
            }
        });

        return  rot;
    }

    private void load(String mobilenumber) {
        getDialog().dismiss();
        JoinSchemeFragment dialogue = JoinSchemeFragment.newInstance(mParam1,mParam2,mobilenumber,mParam4,"3",mParam6);
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);



    }


}
