package com.techface.srimurugapa.scheme.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;

import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.Adapter.SugarProdcutAdapter;
import com.techface.srimurugapa.scheme.DialogueFragment.QuantityDialogFragment;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.Helper.SessionManager;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ProductList;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements QuantityDialogFragment.OnFragmentInteractionListener {


    private final static String TAG=HomeActivity.class.getSimpleName();
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    @BindView(R.id.rv_product_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.Tv_managepackage_empty)
    TextView mEmptyView;

    @BindView(R.id.txt_update)
     TextView mUpdate;

    @BindView(R.id.itemCount)
    TextView mItemCount;

    @BindView(R.id.ll_button_view)
    CardView mCartView;

    @BindView(R.id.toolbarLL)
    Toolbar mToolbar;


    @BindView(R.id.toolbarTextView)
    Button mBtnOverAllAmount;

    @BindView(R.id.toolbar1)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.username)
    TextView mUserName;



    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    List<ProductList.product>mCancelList;
    private ProgressDialog mProgressdialog;
    private  AlertDialog mInfo;
    private  List<ProductList>mProductList;
    private  List<ProductList.product>mProductValue;
    private  SugarProdcutAdapter mSugarProductAdapter;
    private  List<ProductList.product>updateList =new ArrayList<>();




    private MyHandler versionHandler;
    private Runnable versionRunnable;
    private  String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"OnCreated");

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mProgressdialog = CommonUtils.getProgressDialog(HomeActivity.this);
        mCartView.setVisibility(View.GONE);
        mToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        mToolbar.setTitleTextColor((Color.parseColor("#000000")));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        userId = sharedPreferences.getString(Constants.UserId, "");




        mUserName.setText(userId);

        mUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.logoutUser(getApplicationContext());
                finish();
            }
        });

        //mToolbar.setTitle("Admin");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make toolbar show navigation button (i.e back button with arrow icon)
        mToolbar.setNavigationIcon(R.drawable.ic_arrow);
//        if (getSupportActionBar() != null) {
//
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
//            getSupportActionBar().setTitle("PRODUCT DETAILS");
//
//        }


        startVersionTimer();
        loadProduct();



        mCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(getApplicationContext(),UpdatedListActivity.class);
                //intent.putExtra("selectedList", (Serializable) updateList);
                //startActivity(intent);
            }
        });

        mBtnOverAllAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DailyReport.class);
                startActivity(intent);
            }
        });


    }





    private void startVersionTimer() {
        versionHandler = new MyHandler(this);
        versionRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "======= Version Checking Loop Started ========");
                requestIDleApI();
                versionHandler.postDelayed(this, 900000);
            }
        };
        versionHandler.postDelayed(versionRunnable, 900000);
    }

    private void requestIDleApI() {


    }

    //Get the Product Details from API
    private void loadProduct() {
        //ApiClient client = ServiceGenerator.createService(ApiClient.class, base_url);
        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<ProductList>>call=service.getProductdetail();
        mProgressdialog.show();
        call.enqueue(new Callback<List<ProductList>>() {
            @Override
            public void onResponse(Call<List<ProductList>> call, Response<List<ProductList>> response) {
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
            public void onFailure(Call<List<ProductList>> call, Throwable t) {
                mProgressdialog.cancel();
                if(getApplicationContext() !=null) {
                    mInfo = Responseinfodialog.alertshow(getApplicationContext(), "CONNECTION FAILURE", Color.RED);
                    mInfo.show();
                }
                t.printStackTrace();
            }
        });

    }

    private void loadData(List<ProductList> body) {
        mProductList=body;

        mProductValue=mProductList.get(0).getmProducts();
        if(mProductList.isEmpty())
        {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else {
            mBtnOverAllAmount.setText("Cash In Hand");
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            mSugarProductAdapter= new SugarProdcutAdapter(getApplicationContext(),mProductList,mProductValue, new SugarProdcutAdapter.getQuantity() {
           @Override
           public void getQuantityData(String s, String s1, String s2, String s3,int s4) {
               dialogView(s,s1,s2,s3,s4);

           }
       } );


        mRecyclerView.setAdapter(mSugarProductAdapter);
        loadActivityData();

        }

    }

    private void loadActivityData() {
        if(getIntent().hasExtra("cancellist")) {
            mCancelList = (List<ProductList.product>) getIntent().getSerializableExtra("cancellist");
            Log.i(TAG,"CancelList"+mCancelList.get(0).getmProductId());

            for(int i=0;i<mCancelList.size();i++)
            {
                onFragmentInteraction(mCancelList.get(i).getmProductId(),mCancelList.get(i).getmItemName(),mCancelList.get(i).getmItemRate(),Integer.parseInt(mCancelList.get(i).getmQuantity()),mCancelList.get(i).getmTotal());
                Log.i(TAG,"CancelList"+mCancelList.get(i).getmProductId());
            }
        }
    }

    private void dialogView(String mId, String mProductName, String mItemRate, String quantity,int mTotal) {

        QuantityDialogFragment dialogue = QuantityDialogFragment.newInstance(mId,mProductName,mItemRate,quantity,mTotal);
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(((HomeActivity) this).getSupportFragmentManager(), "fragment_new_package");
        Log.i(TAG,mId+" -"+mProductName+"-"+mItemRate+"-"+quantity);


    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onResume()
    {
        super.onResume();
        Log.i("onResume","Called");

    }

    @Override
    public void onFragmentInteraction(String mId, String mProductName, String mItemRate, int minteger, int update) {


        Log.i(TAG,mId+"-"+mProductName+"-"+mItemRate+"-"+minteger+"--"+update);
        mCartView.setVisibility(View.VISIBLE);
        for(int i=0;i<mProductValue.size();i++) {
            if (mProductValue.get(i).getmProductId().equals(mId)) {
                mProductValue.get(i).setmQuantity(String.valueOf(minteger));
                mProductValue.get(i).setmTotal(update);
                break;


            }
        }

        mSugarProductAdapter.notifyDataSetChanged();

        if(updateList.isEmpty())
        {
            updateList.add(new ProductList.product(mId,mProductName,mItemRate,String.valueOf(minteger),update));
        }else if(1==LoadUpdateValue(mId,minteger)) {

            for (int i = 0; i <updateList.size(); i++) {

                if (updateList.get(i).getmProductId().equals(mId)) {


                    updateList.get(i).setmQuantity(String.valueOf(minteger));
                    updateList.get(i).setmTotal(update);

                    break;


                }
            }

        }
        else {
            updateList.add(new ProductList.product(mId,mProductName,mItemRate,String.valueOf(minteger),update));
        }

       loadData1();





    }

    private int LoadUpdateValue(String mId, int minteger) {
        for (int i = 0; i <updateList.size(); i++) {

            if (updateList.get(i).getmProductId().equals(mId)) {
                return 1;
                }
        }
        return 0;
    }

    private void loadData1() {
        Log.i(TAG,"LIST SIZE "+updateList.size());
        mItemCount.setText(String .valueOf(updateList.size())+" Items");
        int mTotal=0;
        for(int i=0;i<updateList.size();i++)
        {

            Log.i(TAG,"ID: "+String.valueOf(updateList.get(i).getmProductId()+" ITEMRATE: "+updateList.get(i).getmItemRate()+" ITEM QUANTITY: "+updateList.get(i).getmQuantity()+"TOTAL: "+updateList.get(i).getmTotal()));
           mTotal+=updateList.get(i).getmTotal();
        }
        mUpdate.setText("Total : ₹"+String.valueOf(mTotal));
    }


    private static class MyHandler extends Handler {
        private final WeakReference<HomeActivity> mActivity;

        public MyHandler(HomeActivity activity) {

            mActivity = new WeakReference<>(activity);
        }
    }


}
