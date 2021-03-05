package com.techface.srimurugapa.scheme.Activity;

import android.content.Intent;
import android.graphics.Color;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ProductList;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    private  static  final String TAG=PaymentActivity.class.getSimpleName();

    @BindView(R.id.toolbarpayment)
    Toolbar mToolbar;


    @BindView(R.id.toolbarPayment)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.ll_totallayout)
    LinearLayout mAcceptedAmount;

    @BindView(R.id.mTotal)
    TextView mTotal;

    @BindView(R.id.mCash)
    EditText mCash;

    @BindView(R.id.mprintValue)
    TextView mprintValue;

    @BindView(R.id.Cashamount)
    TextView mCashAmount;


    @BindView(R.id.mChangedue)
    TextView mChangedue;

    private List<ProductList.product>mList;

    int sumTotal=0;
    int typed_amount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        mList = (List<ProductList.product>) getIntent().getSerializableExtra("printerlist");
        mToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        mToolbar.setTitleTextColor((Color.parseColor("#000000")));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make toolbar show navigation button (i.e back button with arrow icon)
        mToolbar.setNavigationIcon(R.drawable.ic_arrow);


            if(mList.size()!=0) {
                for (int j = 0; j < mList.size(); j++) {
                    sumTotal = sumTotal + mList.get(j).getmTotal();
                }


            }
          //  Log.i(TAG,mList.get(i).getmItemName());
        mprintValue.setText(String.valueOf(sumTotal));

        mCash.setText(String.valueOf(sumTotal));
        mCashAmount.setText(String.valueOf(sumTotal));
        mChangedue.setText("0.0");
        typed_amount=Integer.parseInt(String.valueOf(mCash.getText()));
        mCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mprintValue.setText(String.valueOf(s));
                mCashAmount.setText(String.valueOf(s));
                    if(!String.valueOf(mprintValue.getText()).isEmpty()) {
                        typed_amount = Integer.parseInt(String.valueOf(s));

                        if(0<typed_amount &&sumTotal<typed_amount )
                        {
                            mChangedue.setText(String.valueOf(typed_amount-sumTotal));
                        }
                        else {

                            mChangedue.setText("0.0");
                        }

                    }else {
                        mChangedue.setText("0.0");
                    }

            }
        });


        mTotal.setText(String.valueOf(sumTotal));

        mAcceptedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sumTotal<=typed_amount)
                {
                    Log.i(TAG,String.valueOf(sumTotal+"  "+typed_amount));
                            Intent intent=new Intent(PaymentActivity.this,Printer.class);
                            intent.putExtra("total",String.valueOf(sumTotal));
                            intent.putExtra("cash",String.valueOf(typed_amount));
                            intent.putExtra("changedue",String.valueOf(mChangedue.getText()));
                            intent.putExtra("printerlist", (Serializable) mList);
                           startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"PLEASE ENTER THE AMOUNT ABOVE TOTAL AMOUNT ",Toast.LENGTH_SHORT).show();
                }
            }
        });



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
}
