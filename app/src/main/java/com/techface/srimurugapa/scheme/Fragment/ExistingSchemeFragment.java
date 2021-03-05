package com.techface.srimurugapa.scheme.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.techface.srimurugapa.scheme.Activity.ExitingPayActivity;
import com.techface.srimurugapa.scheme.Activity.ExitingpaytmActivity;
import com.techface.srimurugapa.scheme.Adapter.ExitSchemeAdapter;
import com.techface.srimurugapa.scheme.Adapter.ExitgramSchemeAdapter;
import com.techface.srimurugapa.scheme.DialogueFragment.SchemeiInfoFragment;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ExitSchemeDetails;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExistingSchemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExistingSchemeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  static final String TAG=ExistingSchemeFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.rv_update_recyclerView)
    RecyclerView mSchemeRecycle;

    @BindView(R.id.txt_empty)
    TextView mEmptyView;
    @BindView(R.id.radio_group)
    RadioGroup mRadio;

    @BindView(R.id.radio1)
    RadioButton mRadiobutton;

    private ProgressDialog mProgressdialog;
    String UserId;
    String Agent_id;

    private List<ExitSchemeDetails>mExitScheme;
    private AlertDialog mInfo;

    private ExitSchemeAdapter mExitSchemeAdapter;
    private ExitgramSchemeAdapter mExitgramSchemeAdapter;



    public ExistingSchemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExistingSchemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExistingSchemeFragment newInstance(String param1, String param2) {
        ExistingSchemeFragment fragment = new ExistingSchemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated","Created");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  rootView=inflater.inflate(R.layout.fragment_existing_scheme, container, false);
        ButterKnife.bind(this,rootView);
        mProgressdialog = CommonUtils.getProgressDialog(getContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        UserId = sharedPreferences.getString(Constants.UserId, "");
        Agent_id=sharedPreferences.getString(Constants.AGENTID,"");

        Log.i("agentid",Agent_id);
        loadExitScheme(UserId,1,Agent_id);
       /* mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.i("ssss",String.valueOf(i));

                View radioButton = mRadio.findViewById(i);
                int index = mRadio.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button

                        mSchemeRecycle.setVisibility(View.GONE);
                        loadExitScheme(UserId,1);
                      //  loadSchemeData(branch_Id,"1");
                        break;
                    case 1: // secondbutton

                        mSchemeRecycle.setVisibility(View.GONE);
                        loadExitScheme(UserId,2);
                       // loadSchemeData(branch_Id,"2");
                        break;
                    case 2: // secondbutton

                        mSchemeRecycle.setVisibility(View.GONE);
                        loadExitScheme(UserId,3);
                        // loadSchemeData(branch_Id,"2");
                        break;
                }

            }
        });*/

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExitScheme(UserId,1, Agent_id);
        mRadiobutton.setChecked(true);
        Log.i("onResume","Created");
    }

    public void loadExitScheme(String userId, int s, String agent_id) {
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<ExitSchemeDetails>> call=service.getExitisingSchemeNew(userId,s,agent_id);
        mProgressdialog.show();
        call.enqueue(new Callback<List<ExitSchemeDetails>>() {
            @Override
            public void onResponse(Call<List<ExitSchemeDetails>> call, Response<List<ExitSchemeDetails>> response) {
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
            public void onFailure(Call<List<ExitSchemeDetails>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                  //  mInfo = Responseinfodialog.alertshow(getContext(), "Add Any One Scheme and Check the Internet connection", Color.RED);
                    //mInfo.show();
                    mEmptyView.setVisibility(View.VISIBLE);
                    mSchemeRecycle.setVisibility(View.GONE);
                }
                t.printStackTrace();
            }
        });


    }

    private void loadData(List<ExitSchemeDetails> body,int s) {

        mExitScheme=body;
        if(mExitScheme.isEmpty())
        {
            mEmptyView.setVisibility(View.VISIBLE);
            mSchemeRecycle.setVisibility(View.GONE);
        }
        else {


            mEmptyView.setVisibility(View.GONE);
            mSchemeRecycle.setVisibility(View.VISIBLE);

            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mSchemeRecycle.setLayoutManager(new GridLayoutManager(getContext(), 1));

                mExitSchemeAdapter = new ExitSchemeAdapter(getContext(), mExitScheme, s, new ExitSchemeAdapter.getExitScheme() {
                    @Override
                    public void getExitSchemeData(String mId, String userid,int schemetype) {

                        indoDialog(mId, userid,schemetype);
                        Log.i("sssss", mId);


                    }

                    @Override
                    public void getPayment(String schemeid, String UserId, String amount, String duration, String count,int Schemetype) {

                         if(Schemetype==3)
                         {

                             AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.MyAlertTheme).create();
                             Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.source_sans_pro);
                             TextView title = new TextView(getContext());
                             title.setTextColor(Color.parseColor("#1976d2"));
                             title.setTextSize(20);

                             title.setTypeface(typeface);
                             title.setPadding(10, 10, 10, 70);
                             title.setText(Html.fromHtml("<b>" + "CUSTOMIZE SCHEME " + "</b>"));
                             title.setGravity(Gravity.CENTER);
                             alertDialog.setCustomTitle(title);
                             final EditText edittext = new EditText(getContext());
                             edittext.setTextColor(Color.parseColor("#1976d2"));
                             edittext.setBackgroundResource(R.drawable.rect_edit_text);
                             edittext.setWidth(200);
                             edittext.setHeight(100);
                            edittext.setHint("Enter your Scheme amount above 100");
                             TextView myMsg = new TextView(getContext());
                             myMsg.setTextColor(Color.parseColor("#1976d2"));
                             myMsg.setTextSize(25);

                             myMsg.setTypeface(typeface);
                            // myMsg.setBackgroundResource(R.drawable.card);
                             myMsg.setPadding(10, 70, 10, 70);
                             myMsg.setText(" ");
                             myMsg.setGravity(Gravity.CENTER);

                             alertDialog.setView(myMsg);
                             alertDialog.setView(edittext);

                             alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                     new DialogInterface.OnClickListener() {

                                         public void onClick(DialogInterface dialog, int which) {
                                             String YouEditTextValue = edittext.getText().toString();
                                             if (YouEditTextValue.isEmpty() || Integer.parseInt(YouEditTextValue) < 100) {
                                                 Toast.makeText(getContext(),"Enter valid Amount",Toast.LENGTH_SHORT).show();
                                                 return;
                                             }
                                             dialog.dismiss();
                                            loadPayment(schemeid, UserId, YouEditTextValue, duration, count,Schemetype);

                                         }
                                     });
                             alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                     new DialogInterface.OnClickListener() {

                                         public void onClick(DialogInterface dialog, int which) {

                                             dialog.dismiss();

                                         }
                                     });



                             alertDialog.show();
                             // loadcustomerview(schemeid,"",)
                         }else
                         {
                             loadPayment(schemeid, UserId, amount, duration, count,Schemetype);
                         }


                    }
                });
                mSchemeRecycle.setAdapter(mExitSchemeAdapter);
                mExitSchemeAdapter.notifyDataSetChanged();

               /* mExitgramSchemeAdapter=new ExitgramSchemeAdapter(getContext(), mExitScheme, s, new ExitgramSchemeAdapter.getExitScheme() {
                    @Override
                    public void getExitgramSchemeData(String mId, String mSchemeName) {

                    }

                    @Override
                    public void getgramPayment(String schemeid, String UserId, String amount, String duraion, String count) {
                        loadPayment(schemeid, UserId, amount, duraion, count,"2");
                    }
                });*/





        }



    }

/*    private void loadcustomerview(String mId, String mSchemeName, String mSchemeAmount, String mDuration, String schemetype, String value) {

        ExitingCustomerAmoutFragment dialogue1 = ExitingCustomerAmoutFragment.newInstance(mId,mSchemeName,mSchemeAmount,mDuration,schemetype,value);
        dialogue1.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue1.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue1.setCancelable(false);
        Log.i(TAG,mId+" -"+mSchemeName+"-"+mSchemeAmount+"-"+mDuration);

    }*/
    private void loadPayment(String schemeid, String userId,String amount,String duration,String count,int Schemetype) {


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
        String reg_date = df.format(c.getTime());
        Log.i("sssssaaaa",reg_date);
        Intent intent=new Intent(getContext(), ExitingpaytmActivity.class);
        intent.putExtra("title","exits");
        intent.putExtra("schemeid",schemeid);
        intent.putExtra("userid",UserId);
        intent.putExtra("amount",amount);
       intent.putExtra("entrydate",reg_date);
       intent.putExtra("duration",duration);
       intent.putExtra("count",count);
       intent.putExtra("schemetype",Schemetype);
        startActivity(intent);

    }

    private void indoDialog(String mId, String userid,int schemetype) {

        SchemeiInfoFragment dialogue = SchemeiInfoFragment.newInstance(mId,UserId,userid,schemetype);
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);



    }
}