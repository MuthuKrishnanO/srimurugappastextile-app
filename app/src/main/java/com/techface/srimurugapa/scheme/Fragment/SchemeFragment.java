package com.techface.srimurugapa.scheme.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.Adapter.SchemeDetailsAdapter;
import com.techface.srimurugapa.scheme.DialogueFragment.CustomerSchemeFragment;
import com.techface.srimurugapa.scheme.DialogueFragment.JoinSchemeFragment;
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
 * Use the {@link SchemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchemeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String TAG=SchemeFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SchemeDetailsAdapter mSchemeDetailsAdapter;

    @BindView(R.id.rv_scheme_recyclerView)
    RecyclerView mSchemeRecycle;

    @BindView(R.id.txt_empty)
    TextView mEmptyView;


  /*  @BindView(R.id.mCash)
    Button mCash;

    @BindView(R.id.mGram)
    Button mGram;*/

    @BindView(R.id.radio_group)
    RadioGroup mRadio;

    private  List<SchemeDetails>mSchemeList;
    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;
    private  String branch_Id;
    public SchemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SchemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchemeFragment newInstance(String param1, String param2) {
        SchemeFragment fragment = new SchemeFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_scheme, container, false);
        ButterKnife.bind(this,rootView);
        mProgressdialog = CommonUtils.getProgressDialog(getContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        branch_Id = sharedPreferences.getString(Constants.BRANCHID, "");


        loadSchemeData(branch_Id,"1");

     /*   mGram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSchemeRecycle.setVisibility(View.GONE);
                loadSchemeData(branch_Id,"2");
            }
        });

        mCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSchemeRecycle.setVisibility(View.GONE);
                loadSchemeData(branch_Id,"1");
            }
        });
*/

        mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.i("ssss",String.valueOf(i));

                View radioButton = mRadio.findViewById(i);
                int index = mRadio.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button

                        mSchemeRecycle.setVisibility(View.GONE);
                        loadSchemeData(branch_Id,"1");
                        break;
                    case 1: // secondbutton

                        mSchemeRecycle.setVisibility(View.GONE);
                        loadSchemeData(branch_Id,"2");
                        break;

                    case 2: // secondbutton

                        mSchemeRecycle.setVisibility(View.GONE);
                        loadSchemeData(branch_Id,"3");
                        break;
                }

            }
        });


        return rootView;
    }

   /* private void loadschemegrame(String branch_id, String s) {
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<SchemeDetails>> call=service.getSchemeGramDetails(branch_id,s);
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

                    loadData(response.body(),s);
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

    }*/

    private void loadSchemeData(String branch_id,String s) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<SchemeDetails>> call=service.getschemeDetails(branch_id,s);
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
                    loadData(response.body(),s);
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

    private void loadData(List<SchemeDetails> body,String s) {
        mSchemeList=body;

        if(mSchemeList.isEmpty())
        {
            mEmptyView.setVisibility(View.VISIBLE);
            mSchemeRecycle.setVisibility(View.GONE);
        }
        else {

            mEmptyView.setVisibility(View.GONE);
            mSchemeRecycle.setVisibility(View.VISIBLE);

            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mSchemeRecycle.setLayoutManager(new GridLayoutManager(getContext(), 1));
            mSchemeDetailsAdapter=new SchemeDetailsAdapter(getContext(),mSchemeList, s,new SchemeDetailsAdapter.getOneScheme() {
                @Override
                public void getSchemeData(String mId, String mSchemeName, String mSchemeAmount, String mDuration, String schemetype,String value ) {
                    Log.i(TAG+"ggg",schemetype);
                    if(Integer.parseInt(schemetype)==3)
                    {   loadcustomerview(mId,mSchemeName,mSchemeAmount,mDuration,schemetype,value);


                    }else {
                        dialogView(mId,mSchemeName,mSchemeAmount,mDuration,schemetype,value );
                    }



                }


            });

      }
        mSchemeRecycle.setAdapter(mSchemeDetailsAdapter);

    }

    private void loadcustomerview(String mId, String mSchemeName, String mSchemeAmount, String mDuration, String schemetype, String value) {

        CustomerSchemeFragment dialogue1 = CustomerSchemeFragment.newInstance(mId,mSchemeName,mSchemeAmount,mDuration,schemetype,value);
        dialogue1.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue1.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue1.setCancelable(false);
        Log.i(TAG,mId+" -"+mSchemeName+"-"+mSchemeAmount+"-"+mDuration);

    }

    private void  dialogView(String mId, String mSchemeName, String mSchemeAmount, String duration,String schemetype,String value) {

        JoinSchemeFragment dialogue = JoinSchemeFragment.newInstance(mId,mSchemeName,mSchemeAmount,duration,schemetype,value);
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);
        Log.i(TAG,mId+" -"+mSchemeName+"-"+mSchemeAmount+"-"+duration);


    }
}