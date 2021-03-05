package com.techface.srimurugapa.scheme.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.techface.srimurugapa.scheme.Activity.HomeMainActivity;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.BranchDetails;
import com.techface.srimurugapa.scheme.model.Imageslide;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  static final String TAG=HomeFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SliderLayout sliderLayout;

    @BindView(R.id.text)
    TextView mSlidetext;

    @BindView(R.id.branchName)
    TextView mBranchname;

  /*  @BindView(R.id.gold22k)
    TextView mGold_22k;

    @BindView(R.id.gold24k)
    TextView mGold_24k;*/


    @BindView(R.id.branchadress)
    TextView mBranchaddress;


    @BindView(R.id.aboutus)
    TextView mAboutus;

    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;
    private String mBranchId;
    private String BranchName;
    private String mobilenumber;
    private String gold22k;
    private String gold24k;
    private String branchaddress;
    private String aboutus;
    private List<Imageslide>mImageSlide;
    private static final int REQUEST_PHONE_CALL = 10;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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



        View RootView=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,RootView);
        mProgressdialog = CommonUtils.getProgressDialog(getContext());

        if(savedInstanceState==null)
        {
            loadPlanName();
        }
       /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mBranchId = sharedPreferences.getString(Constants.BRANCHID, "");
        BranchName=sharedPreferences.getString(Constants.BRANCHNAME,"");
        mobilenumber=sharedPreferences.getString(Constants.MOBILENUMBER,"");
        gold22k=sharedPreferences.getString(Constants.GOLD_22k,"");
        gold24k=sharedPreferences.getString(Constants.GOLD_24k,"");
        branchaddress=sharedPreferences.getString(Constants.BRANCHADDRESS,"");
        aboutus=sharedPreferences.getString(Constants.ABOUTUS,"");*/

  /*      mBranchname.setText(BranchName);

        mBranchaddress.setText(branchaddress);
        if(!aboutus.isEmpty()) {
            mAboutus.setVisibility(View.VISIBLE);
            mAboutus.setText( aboutus);
        }


        mSlidetext.setText("+91 "+mobilenumber);*/
        mSlidetext.setSelected(true);
        mSlidetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customercall(mobilenumber);
            }
        });
        sliderLayout =RootView.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
       loadImage();
        //loadgold();

        return RootView;
    }

    private void loadPlanName() {

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        Call<List<BranchDetails>> call = client.getBranchDetailsNew();
        mProgressdialog.show();
        call.enqueue(new Callback<List<BranchDetails>>() {
            @Override
            public void onResponse(Call<List<BranchDetails>> call, Response<List<BranchDetails>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode: " + response.code());
                if (response.body() != null) {
                    getBranchdetails(response.body());
                } else {
                    Log.i(TAG, "Response is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<BranchDetails>> call, Throwable t) {
                mProgressdialog.cancel();
               /* if(getApplicationContext() !=null) {
                    //  mInfo = Responseinfodialog.alertshow(getApplicationContext(), "CONNECTION FAILURE", Color.RED);
                    //   mInfo.show();
                }*/
                t.printStackTrace();
            }
        });
    }

    private void getBranchdetails(List<BranchDetails> body) {


        mBranchname.setText(body.get(0).getmBranchName());

        mBranchaddress.setText(body.get(0).getmBranchaddress());

    mAboutus.setText( body.get(0).getAboutus());



        mSlidetext.setText("+91 "+body.get(0).getmMobileNumber());
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        editor.putString(Constants.BANKAPI,body.get(0).getmBankApi());
        editor.apply();

    }




    private void customercall(String mobilenumber) {

        if ((mobilenumber != null) || (!mobilenumber.equals(0))) {

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobilenumber, null));
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((HomeMainActivity) getContext(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                ((HomeMainActivity) getContext()).startActivity(intent);
            }
        } else {

            Toast.makeText(getContext(), "NO MOBILE NUMBER ", Toast.LENGTH_SHORT).show();

        }
    }
    private void loadImage() {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<Imageslide>> call=service.getImageDetailsNew();
        mProgressdialog.show();
        call.enqueue(new Callback<List<Imageslide>>() {
            @Override
            public void onResponse(Call<List<Imageslide>> call, Response<List<Imageslide>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    new Handler().postDelayed(new Runnable() {


                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            loadData(response.body());
                        }
                    }, 1000);

                }
            }

            @Override
            public void onFailure(Call<List<Imageslide>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });

    }

    private void loadData(List<Imageslide> body) {

        mImageSlide=body;

        if(mImageSlide.size()>0) {
            setSliderViews();
        }else {

            SliderView sliderView = new SliderView(getContext());
            //sliderView.setImageUrl("https://ak.picdn.net/shutterstock/videos/1008575479/thumb/8.jpg?ip=x480");
            sliderView.setImageUrl("https://scontent-maa2-1.xx.fbcdn.net/v/t1.0-9/75453493_159177325480624_7371407337392701440_n.jpg?_nc_cat=101&ccb=2&_nc_sid=110474&_nc_ohc=vYvkyBwXXREAX822GDy&_nc_ht=scontent-maa2-1.xx&oh=5a53a6a47c2e12d167e76771a807b2e1&oe=600847BD");

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            //at last add this view in your layout :
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    sliderLayout.addSliderView(sliderView);
                }
            }, 1000);
           // sliderLayout.addSliderView(sliderView);
        }
    }

    private void setSliderViews() {


        for (int i = 0; i < mImageSlide.size(); i++) {

            SliderView sliderView = new SliderView(getContext());
            sliderView.setImageUrl("http://bill.murugappasstores.com/sliderimage/"+mImageSlide.get(i).getFilename());

            /*switch (i) {
                case 0:
                    sliderView.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_8WE2iM-tj21M3VPY6AUR4mvPQv1nx_KApg&usqp=CAU");
                    break;
                case 1:
                    sliderView.setImageUrl("https://i.ytimg.com/vi/ezTx3IoKcp8/maxresdefault.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRACklN8iDwTr8gkjtKblUDcZCgUBBvdpboNQ&usqp=CAU");
                    break;
                case 3:
                    sliderView.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcScXEUpdvEJiOroUmDDHeDJO2vY8YMlZtTxyQ&usqp=CAU");
                    break;
            }*/

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
          //  sliderView.setDescription("setDescription " + (i + 1));
           /* final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    //Toast.makeText(getContext(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });*/

            //at last add this view in your layout :


            sliderLayout.addSliderView(sliderView);
        }
    }
}