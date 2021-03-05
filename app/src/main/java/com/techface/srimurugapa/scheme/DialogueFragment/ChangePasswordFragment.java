package com.techface.srimurugapa.scheme.DialogueFragment;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AlertDialog mInfo;
    @BindView(R.id.mCancel)
    Button mCancel;

    @BindView(R.id.old_password)
    EditText mOldPassword;

    @BindView(R.id.new_password)
   EditText mNewPassword;


    @BindView(R.id.mYes)
    Button mChange;

    private String OldPassword;
    private String NewPassword;
    private  String UserId;
    private ProgressDialog mProgressdialog;
    private  List<DataStatus>mStatus;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this,rootView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        UserId = sharedPreferences.getString(Constants.UserId, "");
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressdialog = CommonUtils.getProgressDialog(getContext());
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OldPassword=mOldPassword.getText().toString().trim();
                NewPassword=mNewPassword.getText().toString().trim();

                if (OldPassword.isEmpty()) {
                    mOldPassword.setError("Old Password iis required");
                    mOldPassword.requestFocus();
                    return;
                }
                if (NewPassword.isEmpty() && NewPassword.length() < 5) {
                    mNewPassword.setError("New Password length 6 character");
                    mNewPassword.requestFocus();
                    return;
                }

                loadPassword(OldPassword,NewPassword);


            }
        });


        return rootView;
    }

    private void loadPassword(String oldPassword, String newPassword) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<DataStatus>> call = client.getChangePassword(UserId,oldPassword,newPassword);
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
            mStatus=body;
            Toast.makeText(getContext(), mStatus.get(0).getmData(), Toast.LENGTH_LONG).show();
            getDialog().dismiss();


        }


}