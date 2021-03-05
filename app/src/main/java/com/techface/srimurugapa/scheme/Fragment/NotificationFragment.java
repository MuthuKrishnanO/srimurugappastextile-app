package com.techface.srimurugapa.scheme.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.SchemeDetails;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  static final String TAG=NotificationFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String title;
    private String message;
    private String imageurl;
    private String branch_id;
    private String userid;
    private String timesamp;


    @BindView(R.id.mtitle)
    TextView mtxtTilte;

    @BindView(R.id.message)
    TextView mtxtMessage;

    @BindView(R.id.image)
    ImageView mImageurl;

    @BindView(R.id.empty)
    TextView empty;

    @BindView(R.id.main)
    LinearLayout mMainview;



    @BindView(R.id.delete)
    ImageView mDelete;
    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

        View RootView=inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this,RootView);
        mProgressdialog = CommonUtils.getProgressDialog(getContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        branch_id=sharedPreferences.getString(Constants.BRANCHID,"");
       userid= sharedPreferences.getString(Constants.UserId,"");
        title=sharedPreferences.getString(Constants.MTITLE,"");
        message=sharedPreferences.getString(Constants.MMESSAGE,"");
        imageurl=sharedPreferences.getString(Constants.MIMAGEURL,"");
        timesamp=sharedPreferences.getString(Constants.MTIMES,"");

        //loadlist();
        if(title.isEmpty())
        {
         empty.setVisibility(View.VISIBLE);
         mMainview.setVisibility(View.GONE);
        }else
        {
            empty.setVisibility(View.GONE);
            mMainview.setVisibility(View.VISIBLE);
            mtxtTilte.setText(title);
            mtxtMessage.setText(message);

            Glide.with(this).load(imageurl).transform(new CenterCrop(),new RoundedCorners(25))
                    .into(mImageurl);




        }

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty.setVisibility(View.VISIBLE);
                mMainview.setVisibility(View.GONE);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

                //   mAddress.getText().toString(),mCity.getText().toString(),mPanno.getText().toString(),mProofnumber.getText().toString(),mNominee.getText().toString()
                editor.putString(Constants.MTITLE,  "");
                editor.putString(Constants.MMESSAGE, "" );
                editor.putString(Constants.MIMAGEURL,"");
                editor.putString(Constants.MTIMES,"");
                editor.apply();
            }
        });

        return RootView;
    }

    private void loadlist() {
       // ServiceGenerator service = new ServiceGenerator(null);
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<SchemeDetails>> call=service.getschemeDetails(branch_id,userid);
        mProgressdialog.show();
        call.enqueue(new Callback<List<SchemeDetails>>() {
            @Override
            public void onResponse(Call<List<SchemeDetails>> call, Response<List<SchemeDetails>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    loadData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SchemeDetails>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });

    }

    private void loadData(List<SchemeDetails> body) {


    }
}